module.exports = (grunt) ->
    # Project configuration.
    grunt.initConfig
        pkg: grunt.file.readJSON('package.json')

        coffee:
            dev:
                files:
                    'src/main/resources/assets/js/widget.js': 'client/coffee/widget.coffee'

        copy:
            dev:
                files:[
                    src: ['client/*.html']
                    dest: 'src/main/resources/assets/'
                ]

        clean: [
            'src/main/resources/assets/*'
        ]

        watch:
            html:
                files: ['client/**/*.html']
                tasks: ['copy']

            coffee:
                files: ['client/**/*.coffee']
                tasks: ['coffee']


    # Load the plugin that provides the "uglify" task.
    #grunt.loadNpmTasks('grunt-contrib-uglify')
    grunt.loadNpmTasks('grunt-contrib-coffee')
    grunt.loadNpmTasks('grunt-contrib-watch')
    grunt.loadNpmTasks('grunt-contrib-clean')
    grunt.loadNpmTasks('grunt-contrib-copy')
    # grunt.loadNpmTasks('grunt-coffeelint')

    # Default task(s).
    grunt.registerTask('default', ['clean', 'coffee', 'copy', 'watch'])
