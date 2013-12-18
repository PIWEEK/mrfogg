package net.kaleidos.gradle.watch

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchKey
import java.nio.file.WatchService

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection

class ContinuousTask extends DefaultTask {

    def WatchService watcher
    def ProjectConnection connection

    ContinuousTask() {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.connection =
            GradleConnector.newConnector().
                forProjectDirectory(project.projectDir).
                connect();
    }

    @TaskAction
    def init() {

        def requestedTasks = [project.tasks.compileGroovy] as Set
        def inputDirs = getInputDirs(requestedTasks)
        def leafTasks = getOnlyLeafTask(requestedTasks)

        installWatcher(inputDirs, leafTasks)

    }

    Set<Task> getOnlyLeafTask(Set<Task> requestedTasks) {

        def dependentTask = requestedTasks.collect { it.taskDependencies.getDependencies(it) }.flatten().unique()
        return requestedTasks - dependentTask

    }

    static Set<File> getInputDirs(Set<Task> tasks) {

        tasks.collect {
            it.inputs.files.files.collect {
                if (it.isDirectory()) it else it.parentFile
            }
        }.flatten().unique()
    }

    def installWatcher(Set<File> inputs, Set<Task> tasks) {

        inputs.each {
            installWatch(it.toPath())
        }

        while (true) {

            def key = watcher.take()
            def watchEvents = key.pollEvents()

            watchEvents.
                findAll { it.kind() == StandardWatchEventKinds.ENTRY_DELETE }.
                collect { it.context() as Path }.
                findAll { Files.isDirectory(it) }.
                each { uninstallWatch(key) }

            watchEvents.
                findAll { it.kind() == StandardWatchEventKinds.ENTRY_CREATE }.
                collect { it.context() as Path }.
                findAll { Files.isDirectory(it) }.
                each { installWatch(it) }


            launchTasks(tasks)
            key.reset()

        }
    }

    void installWatch(Path path) {

        logger.debug("Install watch on $path")

        path.register(
            watcher,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.ENTRY_DELETE
        )

    }

    void uninstallWatch(WatchKey key) {

        logger.debug("Remove watch on $path")
        key.cancel()

    }


    void launchTasks(Set<Task> tasks) {

        connection.newBuild().forTasks(tasks.collect { it.name } as String[]).run()

    }

}
