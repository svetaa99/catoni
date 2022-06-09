import axios from "axios";

const API_URL = "http://localhost:8080"


export function getStartingPosition(callback=defaultCallback, errorCallback=defaultErrorCallback){
    axios
    .get(`${API_URL}/position/starting-house`)
    .then(response => {
        callback(response);
    })
    .catch(error => {
        errorCallback(error);
    });
}

export function buildHouse(dto, callback=defaultCallback, errorCallback=defaultErrorCallback){
    axios
    .post(`${API_URL}/position/building`, dto)
    .then(response => {
        callback(response);
    })
    .catch(error => {
        errorCallback(error);
    });
}

export function buildRoad(dto, callback=defaultCallback, errorCallback=defaultErrorCallback){
    axios
    .post(`${API_URL}/position/road`, dto)
    .then(response => {
        callback(response);
    })
    .catch(error => {
        errorCallback(error);
    });
}

export function initState(playerCount, callback=defaultCallback, errorCallback=defaultErrorCallback){

    axios
    .get(`${API_URL}/position/init-players/${playerCount}`)
    .then(response => {
        callback(response);
    })
    .catch(error => {
        errorCallback(error)
    });
}

export function initChances(callback=defaultCallback, errorCallback=defaultErrorCallback){

    axios
    .post(`${API_URL}/position/init-chances`, 
        {
    "building": [
        {"row": 0, "col": 0, "type": 0},
        {"row": 0, "col": 1, "type": 0},
        {"row": 0, "col": 2, "type": 0},
        {"row": 0, "col": 3, "type": 0},
        {"row": 0, "col": 4, "type": 0},
        {"row": 0, "col": 5, "type": 0},
        {"row": 0, "col": 6, "type": 0},
        {"row": 1, "col": 0, "type": 0},
        {"row": 1, "col": 1, "type": 0},
        {"row": 1, "col": 2, "type": 0},
        {"row": 1, "col": 3, "type": 0},
        {"row": 1, "col": 4, "type": 0},
        {"row": 1, "col": 5, "type": 0},
        {"row": 1, "col": 6, "type": 0},
        {"row": 1, "col": 7, "type": 0},
        {"row": 1, "col": 8, "type": 0},
        {"row": 2, "col": 0, "type": 0},
        {"row": 2, "col": 1, "type": 0},
        {"row": 2, "col": 2, "type": 0},
        {"row": 2, "col": 3, "type": 0},
        {"row": 2, "col": 4, "type": 0},
        {"row": 2, "col": 5, "type": 0},
        {"row": 2, "col": 6, "type": 0},
        {"row": 2, "col": 7, "type": 0},
        {"row": 2, "col": 8, "type": 0},
        {"row": 2, "col": 9, "type": 0},
        {"row": 2, "col": 10, "type": 0},
        {"row": 3, "col": 0, "type": 0},
        {"row": 3, "col": 1, "type": 0},
        {"row": 3, "col": 2, "type": 0},
        {"row": 3, "col": 3, "type": 0},
        {"row": 3, "col": 4, "type": 0},
        {"row": 3, "col": 5, "type": 0},
        {"row": 3, "col": 6, "type": 0},
        {"row": 3, "col": 7, "type": 0},
        {"row": 3, "col": 8, "type": 0},
        {"row": 3, "col": 9, "type": 0},
        {"row": 3, "col": 10, "type": 0},
        {"row": 4, "col": 0, "type": 0},
        {"row": 4, "col": 1, "type": 0},
        {"row": 4, "col": 2, "type": 0},
        {"row": 4, "col": 3, "type": 0},
        {"row": 4, "col": 4, "type": 0},
        {"row": 4, "col": 5, "type": 0},
        {"row": 4, "col": 6, "type": 0},
        {"row": 4, "col": 7, "type": 0},
        {"row": 4, "col": 8, "type": 0},
        {"row": 5, "col": 0, "type": 0},
        {"row": 5, "col": 1, "type": 0},
        {"row": 5, "col": 2, "type": 0},
        {"row": 5, "col": 3, "type": 0},
        {"row": 5, "col": 4, "type": 0},
        {"row": 5, "col": 5, "type": 0},
        {"row": 5, "col": 6, "type": 0}
    ],
    "chances": [
        [{"type": 0, "chance": 0.0833}],
        [{"type": 0, "chance": 0.0833}],
        [{"type": 0, "chance": 0.0833}, {"type": 4, "chance": 0.1389}],
        [{"type": 4, "chance": 0.1389}],
        [{"type": 4, "chance": 0.1389}, {"type": 2, "chance": 0.0833}],
        [{"type": 2, "chance": 0.0833}],
        [{"type": 2, "chance": 0.0833}],

        [{"type": 3, "chance": 0.1389}],
        [{"type": 3, "chance": 0.1389}, {"type": 0, "chance": 0.0833}],
        [{"type": 3, "chance": 0.1389}, {"type": 0, "chance": 0.0833}, {"type": 4, "chance": 0.1389}],
        [{"type": 4, "chance": 0.1389}, {"type": 0, "chance": 0.0833}, {"type": 4, "chance": 0.1389}],
        [{"type": 4, "chance": 0.1389}, {"type": 0, "chance": 0.1111}, {"type": 4, "chance": 0.1389}],
        [{"type": 2, "chance": 0.0833}, {"type": 0, "chance": 0.1111}, {"type": 4, "chance": 0.1389}],
        [{"type": 2, "chance": 0.0833}, {"type": 1, "chance": 0.0556}, {"type": 0, "chance": 0.1111}],
        [{"type": 2, "chance": 0.0833}, {"type": 1, "chance": 0.0556}],
        [{"type": 1, "chance": 0.0556}],

        [{"type": 0, "chance": 0.0556}],
        [{"type": 0, "chance": 0.0556}, {"type": 3, "chance": 0.1389}],
        [{"type": 0, "chance": 0.0833}, {"type": 3, "chance": 0.1389}, {"type": 1, "chance": 0.1111}],
        [{"type": 4, "chance": 0.1389}, {"type": 3, "chance": 0.1389}, {"type": 1, "chance": 0.1111}],
        [{"type": 4, "chance": 0.1389}, {"type": 4, "chance": 0.1389}, {"type": 1, "chance": 0.1111}],
        [{"type": 4, "chance": 0.1389}, {"type": 4, "chance": 0.1389}, {"type": 0, "chance": 0.1111}],
        [{"type": 1, "chance": 0.0277}, {"type": 4, "chance": 0.1389}, {"type": 0, "chance": 0.1111}],
        [{"type": 1, "chance": 0.0277}, {"type": 1, "chance": 0.0556}, {"type": 0, "chance": 0.1111}],
        [{"type": 1, "chance": 0.0277}, {"type": 1, "chance": 0.0556}, {"type": 3, "chance": 0.0833}],
        [{"type": 1, "chance": 0.0556}, {"type": 3, "chance": 0.0833}],
        [{"type": 3, "chance": 0.0833}],

        [{"type": 0, "chance": 0.0556}],
        [{"type": 0, "chance": 0.0556}, {"type": 0, "chance": 0.1111}],
        [{"type": 0, "chance": 0.0556}, {"type": 0, "chance": 0.1111}, {"type": 1, "chance": 0.1111}],
        [{"type": 1, "chance": 0.0277}, {"type": 0, "chance": 0.1111}, {"type": 1, "chance": 0.1111}],
        [{"type": 4, "chance": 0.0277}, {"type": 4, "chance": 0.1389}, {"type": 1, "chance": 0.1111}],
        [{"type": 4, "chance": 0.0277}, {"type": 4, "chance": 0.1389}, {"type": 3, "chance": 0.1111}],
        [{"type": 1, "chance": 0.0277}, {"type": 4, "chance": 0.1389}, {"type": 3, "chance": 0.1111}],
        [{"type": 1, "chance": 0.0277}, {"type": 2, "chance": 0.0556}, {"type": 3, "chance": 0.1111}],
        [{"type": 1, "chance": 0.0277}, {"type": 2, "chance": 0.1389}, {"type": 3, "chance": 0.0556}],
        [{"type": 2, "chance": 0.1389}, {"type": 3, "chance": 0.0556}],
        [{"type": 3, "chance": 0.0556}],

        [{"type": 0, "chance": 0.1111}],
        [{"type": 0, "chance": 0.1111}, {"type": 3, "chance": 0.0833}],
        [{"type": 0, "chance": 0.1111}, {"type": 3, "chance": 0.0833}, {"type": 4, "chance": 0.02778}],
        [{"type": 2, "chance": 0.0833}, {"type": 3, "chance": 0.0833}, {"type": 4, "chance": 0.02778}],
        [{"type": 2, "chance": 0.0833}, {"type": 3, "chance": 0.1111}, {"type": 4, "chance": 0.02778}],
        [{"type": 2, "chance": 0.0833}, {"type": 3, "chance": 0.1111}],
        [{"type": 2, "chance": 0.0556}, {"type": 3, "chance": 0.1111}],
        [{"type": 2, "chance": 0.0556}],
        [{"type": 2, "chance": 0.0556}],

        [{"type": 3, "chance": 0.0833}],
        [{"type": 3, "chance": 0.0833}],
        [{"type": 3, "chance": 0.0833}, {"type": 2, "chance": 0.0833}],
        [{"type": 2, "chance": 0.0833}],
        [{"type": 2, "chance": 0.0833}],
        [{"type": 5, "chance": 0}],
        [{"type": 5, "chance": 0}]
    ]
    }
    )
    .then(response => {
        callback(response);
    })
    .catch(error => {
        errorCallback(error)
    });
}

function defaultCallback(response){
    console.log(response.status);
}

function defaultErrorCallback(error){
    console.log(error);
}