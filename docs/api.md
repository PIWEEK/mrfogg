FORMAT: 1A
HOST: http://www.google.com

# MR. FOGG
Descripcion de MR. FOGG (TODO)

# Group Authentication
## Login [/login]
### Login into the application [POST]
+ Request (application/json)

        {"username": "alotor@gmail.com", "password": "dragon"}

+ Response 200 (application/json)

        {"todo": "todo"}

## Logout [/logout]
### Login into the application [POST]
+ Response 204

## Login [/register]

# Group Users API
## Users [/users]
### List all users [GET]
+ Response 200 (application/json)

        [{ "id": 1, "username": "alotor@gmail.com", "avatar": "http://gravatar.com/alotor" },
         { "id": 2, "username": "mgdelacroix@gmail.com", "avatar": "http://gravatar.com/mgdelacroix" }]

## User detail [/users/{userId}]
### Retrieve the user details [GET]

+ Parameters
    + userId (required, number, `1`) ... User id

+ Response 200 (application/json)

        { "id": 1, "username": "alotor@gmail.com", "avatar": "http://gravatar.com/alotor" }

# Group Trip API
## Trips [/trips]
## Trips Detail [/trips/{tripId}]
### Retrieve the trip details [GET]

+ Parameters
    + tripId (required, number, `1`) ... Trip id

+ Response 200 (application/json)

        {
            "id": 1,
            "name": "Viaje a Londres",
            "description": "Nos vamos de viaje con motivo del GGX lo vamos a pasar mu bien!!",
            "ownerId": 1
        }

## Add user to trip [/trips/{tripId}/addUser/{userId}]
## Tasks [/trips/{tripId}/tasks]
### Retrieve the tasks for a trip [GET]

+ Parameters
    + tripId (required, number, `1`) ... Trip id

+ Response 200 (application/json)

        [
            { "id": 1, "name": "Comprar billetes", "status": "PENDING" },
            { "id": 2, "name": "Escoger hotel", "status": "DONE" }
        ]

# Group Tasks API
## Task detail [/trips/{tripId}/tasks/{taskId}]
### Update task information [POST]

+ Parameters
    + tripId (required, number, `1`) ... Trip id
    + taskId (required, number, `1`) ... Task id

+ Request (application/json)

        { "name": "Comprar los billetes de avión", "status": "DONE" }

+ Response 204

# Group Cards API
## Cards [/trips/{tripId}/tasks/{taskId}/cards]
### Retrieve the cards for a defined task [GET]

+ Parameters
    + tripId (required, number, `1`) ... Trip id
    + taskId (required, number, `1`) ... Task id

+ Response 200 (application/json)

        [
            {
                "id": 1,
                "title": "Encuesta de bares",
                "description": "LoremBacon ipsum dolor sit amet ground round filet mignon pig pork chop, short loin frankfurter venison.",
                "owner": 1,
                "widget": "/widget/poll/10001",
                "comments": [
                    { "userId": 1, "Lorem ipsum" },
                    { "userId": 2, "Lorem ipsum" }
                ]
            }
        ]

### Create a new card inside the task [POST]

+ Parameters
    + tripId (required, number, `1`) ... Trip id
    + taskId (required, number, `1`) ... Task id

+ Request (application/json)

        {
            "title": "Encuesta de bares",
            "description": "LoremBacon ipsum dolor sit amet ground round filet mignon pig pork chop, short loin frankfurter venison.",
            "widget": "/widget/poll/10001"
        }

+ Response 200 (application/json)

        [
            {
                "id": 1,
                "title": "Encuesta de bares",
                "description": "LoremBacon ipsum dolor sit amet ground round filet mignon pig pork chop, short loin frankfurter venison.",
                "owner": 1,
                "widget": {
                    "id": "1001",
                    "type": "/widget/poll"
                },
                "comments": [
                    { "userId": 1, "Lorem ipsum" },
                    { "userId": 2, "Lorem ipsum" }
                ]
            }
        ]

## Card detail [/trips/{tripId}/tasks/{taskId}/cards/{cardId}]

+ Parameters
    + tripId (required, number, `1`) ... Trip id
    + taskId (required, number, `1`) ... Task id
    + cardId (required, number, `1`) ... Card id

