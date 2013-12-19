# Copyright 2013 Andrey Antukh <niwi@niwi.be>
#
# Licensed under the Apache License, Version 2.0 (the "License")
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

ResourceProvider = ($http, $q, $gmStorage, $gmUrls, $model, config) ->
    service = {}
    headers = (diablePagination=true) ->
        data = {}
        token = $gmStorage.get('token')

        data["Authorization"] = "Bearer #{token}" if token
        data["X-Disable-Pagination"] = "true" if diablePagination

        return data

    queryMany = (name, params, options, urlParams) ->
        console.log("queryMany", name, params, options, urlParams)
        defaultHttpParams = {
            method: "GET",
            headers:  headers(),
            url: $gmUrls.api(name, urlParams)
        }
        if not _.isEmpty(params)
            defaultHttpParams.params = params

        httpParams = _.extend({}, defaultHttpParams, options)
        defered = $q.defer()

        promise = $http(httpParams)
        promise.success (data, status) ->
            models = _.map data, (attrs) -> $model.make_model(name, attrs)
            defered.resolve(models)

        promise.error (data, status) ->
            defered.reject(data, status)

        return defered.promise

    queryRaw = (name, id, params, options, cls) ->
        defaultHttpParams = {method: "GET", headers:  headers()}

        if id
            defaultHttpParams.url = "#{$gmUrls.api(name)}/#{id}"
        else
            defaultHttpParams.url = "#{$gmUrls.api(name)}"

        if not _.isEmpty(params)
            defaultHttpParams.params = params

        httpParams =  _.extend({}, defaultHttpParams, options)

        defered = $q.defer()

        promise = $http(httpParams)
        promise.success (data, status) ->
            defered.resolve(data, cls)

        promise.error (data, status) ->
            defered.reject()

        return defered.promise

    queryOne = (name, id, params, options, cls) ->
        defaultHttpParams = {method: "GET", headers:  headers()}

        if id
            defaultHttpParams.url = "#{$gmUrls.api(name)}/#{id}"
        else
            defaultHttpParams.url = "#{$gmUrls.api(name)}"

        if not _.isEmpty(params)
            defaultHttpParams.params = params

        httpParams =  _.extend({}, defaultHttpParams, options)

        defered = $q.defer()

        promise = $http(httpParams)
        promise.success (data, status) ->
            defered.resolve($model.make_model(name, data, cls))

        promise.error (data, status) ->
            defered.reject()

        return defered.promise

    queryManyPaginated = (name, params, options, cls, urlParams) ->
        defaultHttpParams = {
            method: "GET",
            headers: headers(false),
            url: $gmUrls.api(name, urlParams)
        }
        if not _.isEmpty(params)
            defaultHttpParams.params = params

        httpParams =  _.extend({}, defaultHttpParams, options)
        defered = $q.defer()

        promise = $http(httpParams)
        promise.success (data, status, headersFn) ->
            currentHeaders = headersFn()

            result = {}
            result.models = _.map(data, (attrs) -> $model.make_model(name, attrs, cls))
            result.count = parseInt(currentHeaders["x-pagination-count"], 10)
            result.current = parseInt(currentHeaders["x-pagination-current"] or 1, 10)
            result.paginatedBy = parseInt(currentHeaders["x-paginated-by"], 10)

            defered.resolve(result)

        promise.error (data, status) ->
            defered.reject()

        return defered.promise

#    # Resource Methods
#    service.register = (formdata) ->
#        defered = $q.defer()
#
#        onSuccess = (data, status) ->
#            $gmStorage.set("token", data["auth_token"])
#            user = $model.make_model("users", data)
#            defered.resolve(user)
#
#        onError = (data, status) ->
#            defered.reject(data)
#
#        promise = $http({method:'POST', url: $gmUrls.api('auth-register'), data: JSON.stringify(formdata)})
#        promise.success(onSuccess)
#        promise.error(onError)
#
#        return defered.promise

    # Login request
    service.login = (email, password) ->
        defered = $q.defer()

        onSuccess = (data, status) ->
            $gmStorage.set("token", data["token"])
            $gmStorage.set("uid", data["id"])
            user = $model.make_model("users", data)
            defered.resolve(user)

        onError = (data, status) ->
            defered.reject(data)

        postData =
            "email": email
            "password":password

        $http({method:'POST', url: $gmUrls.api('login'), data: JSON.stringify(postData)})
            .success(onSuccess)
            .error(onError)

        return defered.promise


    # Get a user info
    service.getUser = (userId) -> queryOne('users', userId)

    # Get users list
    service.getUsers = -> queryMany('users')

    # Create a trip
    service.createTrip = (data) ->
        return $model.create("trips", data)

    # Get a trip list
    service.getTrips = -> queryMany('trips')

    # Get a trip
    service.getTrip = (tripId) ->
        return queryOne("trips", tripId)

    # Get a trip tasks
    service.getTripTasks = (tripId) ->
        return queryOne("trips", "#{tripId}/tasks")

    # Get a task
    service.getTripTasks = (tripId, taskId) ->
        return queryOne("trips", "#{tripId}/tasks/#{taskId}")

    # Get the cards for a task
    service.getTaskCards = (tripId, taskId) ->
        return queryOne("trips", "#{tripId}/tasks/#{taskId}/cards")

    service.postComment = (tripId, taskId, cardId, data)->
        defered = $q.defer()

        return $http(
            method:'POST'
            headers: headers(false),
            url: "#{$gmUrls.api("trips")}/#{tripId}/tasks/#{taskId}/cards/#{cardId}/comments"
            data: JSON.stringify(data)
        )

    service.getWidgetData = (widgetData)->
        defered = $q.defer()

        return $http(
            method:'GET'
            headers: headers(false),
            url: "#{$gmUrls.api("root")}#{widgetData}"
        )


    ## Create a new card inside the task?
    #service.postTaskCards = (tripId, taskId) ->
    #    return queryOne("trips", "#{tripId}/tasks/#{taskId}/cards")





##    service.createTask = (form) ->
##        return $model.create("tasks", form)
##
##    service.createIssue = (projectId, form) ->
##        obj = _.extend({}, form, {project: projectId})
##        defered = $q.defer()
##
##        promise = $http.post($gmUrls.api("issues"), obj, {headers:headers()})
##        promise.success (data, status) ->
##            defered.resolve($model.make_model("issues", data))
##
##        promise.error (data, status) ->
##            defered.reject()
##
##        return defered.promise
##
##    service.createUserStory = (data) ->
##        return $model.create('userstories', data)
##
##
##    service.uploadTaskAttachment = (projectId, taskId, file, progress) ->
##        defered = $q.defer()
##
##        if file is undefined
##            defered.resolve(null)
##            return defered.promise
##
##        uploadComplete = (evt) ->
##            data = JSON.parse(evt.target.responseText)
##            defered.resolve(data)
##
##        uploadFailed = (evt) ->
##            defered.reject("fail")
##
##        formData = new FormData()
##        formData.append("project", projectId)
##        formData.append("object_id", taskId)
##        formData.append("attached_file", file)
##
##        xhr = new XMLHttpRequest()
##
##        if progress != undefined
##            xhr.upload.addEventListener("progress", uploadProgress, false)
##
##        xhr.addEventListener("load", uploadComplete, false)
##        xhr.addEventListener("error", uploadFailed, false)
##        xhr.open("POST", $gmUrls.api("tasks/attachments"))
##        xhr.setRequestHeader("Authorization", "Bearer #{$gmStorage.get('token')}")
##        xhr.send(formData)
##        return defered.promise
##
##    service.uploadUserStoryAttachment = (projectId, userStoryId, file, progress) ->
##        defered = $q.defer()
##
##        if file is undefined
##            defered.resolve(null)
##            return defered.promise
##
##        uploadComplete = (evt) ->
##            data = JSON.parse(evt.target.responseText)
##            defered.resolve(data)
##
##        uploadFailed = (evt) ->
##            defered.reject("fail")
##
##        formData = new FormData()
##        formData.append("project", projectId)
##        formData.append("object_id", userStoryId)
##        formData.append("attached_file", file)
##
##        xhr = new XMLHttpRequest()
##
##        if progress != undefined
##            xhr.upload.addEventListener("progress", uploadProgress, false)
##
##        xhr.addEventListener("load", uploadComplete, false)
##        xhr.addEventListener("error", uploadFailed, false)
##        xhr.open("POST", $gmUrls.api("userstories/attachments"))
##        xhr.setRequestHeader("Authorization", "Bearer #{$gmStorage.get('token')}")
##        xhr.send(formData)
##        return defered.promise
##
##
##    service.updateBulkPrioritiesOrder = (projectId, data) ->
##        obj = {
##            project: projectId
##            bulk_priorities: data
##        }
##        return $http.post($gmUrls.api("choices/priorities/bulk-update-order"), obj, {headers:headers()})
##

    return service

module = angular.module('mrfogg.services.resource', ['mrfogg.config'])
module.factory('resource', ['$http', '$q', '$gmStorage', '$gmUrls', '$model', 'config', ResourceProvider])
