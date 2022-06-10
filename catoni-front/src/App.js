import './App.css';
import {useState, useEffect} from 'react';
import axios from "axios";
import { addCrazy, addResourcesToPositions, answerTrade, buildHouse, buildRoad, getCraziesForPlayerName, getMove, getResourcesForPlayerName, getStartingPosition, initChances, initState } from './axiosservice/axiosService';
import { getResourcesForNumber, random } from './util/rng';
import Swal from 'sweetalert2';
import { INIT_STATE } from './constants/inputstate';
import { shuffle } from './util/shuffle';
import { CRAZIES_LIST } from './constants/crazies';
import { printResourcesInHand } from './util/print';

function App() {

  const[playerToMove, setPlayerToMove] = useState(0);

  const[players, setPlayers] = useState([]);

  const[inputState, setInputState] = useState(INIT_STATE);

  const[phase, setPhase] = useState("start");

  const [moveCounter, setMoveCounter] = useState(1);

  const [resourcesInHand, setResourcesInHand] = useState([])

  function startGame(){
    getStartingPosition((response) => {
      console.log(response.data);
      var idStr = response.data.building.row+""+response.data.building.col; //moze u funkciju van fajla
      document.querySelector(`.btn-${idStr}`).classList.add("btn", `btn-${idStr}`, `kuca-${playerToMove}`);
      var rIdStr = response.data.road.row1+""+response.data.road.col1+""+response.data.road.row2+""+response.data.road.col2;
      document.querySelector(`.road-${rIdStr}`).classList.add("road", `road-${rIdStr}`, `put-${playerToMove}`);
      setPlayerToMove(nextToMove(playerToMove));
      Swal.fire({title:`${players[playerToMove + 1]} is on the move!` ,timer: 1000});
    });
  }

  function addBuilding(row, col, btnId){
    buildHouse({row, col, playerName: players[playerToMove]}, (response) => {
      console.log(playerToMove + " BUILT ON POSITION : " + row + "-" + col); //moze u funkciju van fajla
      var idStr = row+""+col;
      console.log("FARBAJ NA: " + idStr);
      document.querySelector(`.btn-${idStr}`).classList.add("btn", `btn-${idStr}`, `kuca-${playerToMove}`);
      var selected = document.querySelector(`.btn-${idStr}`)
      if(response.data.type == "HOTEL")
        selected.textContent = "H";
    });//catch
  }

  function addRoad(row1, col1, row2, col2){
    buildRoad({row1, col1, row2, col2, player: players[playerToMove]}, (response) => {
      var roadId = row1+""+col1+""+row2+""+col2; //funkcija van fajla
      console.log(playerToMove + " BUILT A ROAD : " + roadId);
      document.querySelector(`.road-${roadId}`).classList.add("road", `road-${roadId}`, `put-${playerToMove}`);
    });//catch
  }

  function playKnight(e){

  }

  function playMonopoly(e){

  }

  function playYop(e){

  }

  function playRoadBuilder(e){

  }

  function rollDice(e){
    e.preventDefault();
    const dice1 = random();
    const dice2 = random();
    const fallen = dice1 + dice2;
    getResourcesForNumber(fallen, (resourcesPositions) => {
      addResourcesToPositions(resourcesPositions, (response) => {
        setInputState(response.data);
        getResourcesForPlayerName(players[playerToMove], (response) => {
          setResourcesInHand(response.data);
        })
      })
    });
    Swal.fire({title:`${fallen}` ,timer: 1000, showConfirmButton: false, width: '100px'});
  }

  function nextToMove(current){
    if(current + 1 > players.length - 1){
      setMoveCounter(moveCounter + 1);
      return 0;
    }
    return current + 1;
  }

  function endTurn(e){
    e.preventDefault();
    var x = nextToMove(playerToMove);
    Swal.fire({title:`${players[x]} is on the move!` ,timer: 1000}).then(()=>{
      var y = players[0] == "bot" ? moveCounter+1: moveCounter;
      console.log("Potez: " + y);
      if(players[x] == "bot"){
        if(y <= 2){
          getStartingPosition((response) => {
            console.log(response.data);
            let idStr = response.data.building.row + "" + response.data.building.col
            document.querySelector(`.btn-${idStr}`).classList.add("btn", `btn-${idStr}`, `kuca-${x}`);
            var rIdStr = response.data.road.row1+""+response.data.road.col1+""+response.data.road.row2+""+response.data.road.col2;
            document.querySelector(`.road-${rIdStr}`).classList.add("road", `road-${rIdStr}`, `put-${x}`);
            x = nextToMove(x);
            setPlayerToMove(x);
            Swal.fire({title:`${players[x]} is on the move!` ,timer: 1000});
            return;
          });
        }
        else{
          rollDice(e);
          moveHandler();
        }
      }
      else{
        setPlayerToMove(x);
      }
    });
  }

  function moveHandler(){
    getMove(response => {
      // console.log(response.data);
      response.data.moveList.map(move => {
        if(move == 'START_TURN'){
          console.log(response.data);
        }
        else if(move == 'OFFER_TRADE_WITH_PLAYER'){
          //DIALOG N TIMES (len(list))
          console.log(response.data.trade);
          var acceptedTrade = [];
          var askedResource = Object.keys(response.data.trade.tradeOffer.receive)[0];
          players.map(p => {
            if(p != "bot"){
              getResourcesForPlayerName(p, (resources) => {
                var isEligible = false;
                console.log(`ITERATING THROUGH LIST OF ${p}'S RESOURCES`);
                resources.data.map(res => {
                  // console.log(`${res} == ${askedResource}`);
                  if(res == askedResource){
                    isEligible = true;
                    console.log(`${p} IS ELIGIBLE FOR THE TRADE!`);
                  }
                })
                if(isEligible){
                  Swal.fire({text: `${p} do you accept the trade? YOU GET: ${response.data.trade.tradeOffer.offer} FOR ${askedResource}`, showCancelButton:true, confirmButtonText: "Yes", cancelButtonText: "No"})
                    .then((result) => {
                      if(result.isConfirmed){
                        acceptedTrade.push(p);
                        answerTrade({... response.data.trade, acceptedTrade, status: "ACCEPTED"}, (r) => {
                          console.log("ACCEPT TRADE");
                          console.log(r.data);
                          // moveHandler();//WRONG BEHAVIOUR ?
                        });
                      }
                      else if(result.isDismissed){
                        console.log("DECLINE TRADE");
                        console.log(response.data);
                      }
                    });
                  }
              })
            }
          })
          
          // answerTrade({... response.data.trade, acceptedTrade, status: acceptedTrade.length > 0 ? "ACCEPTED" : "DECLINED"},
          // (response) => {
          //   console.log(response.data);
          // });
        }
        else if(move == 'BUILD_HOUSE'){
          // let buildingPosition = response.data.buildings[0];
          response.data.buildings.map((buildingPosition) =>{
            if(buildingPosition.type == "NONE" && buildingPosition.status == "TAKEN"){
              var idStr = buildingPosition.row+""+buildingPosition.column; //moze u funkciju van fajla SVE ODOLE
              document.querySelector(`.btn-${idStr}`).classList.add("btn", `btn-${idStr}`, `kuca-${players.indexOf("bot")}`);
            }
          })
        }
        else if(move == 'BUILD_HOTEL'){
          response.data.buildings.map((buildingPosition) => {
            if(buildingPosition.type == "HOTEL"){ //MOZDA TREBA HOUSE
              var idStr = buildingPosition.row+""+buildingPosition.column;
              var selected = document.querySelector(`.btn-${idStr}`)
              selected.textContent = "H";
            }
          })
        }
        
        else if(move == 'BUILD_ROAD'){
          let roadPosition = response.data.roads[0];
          var roadId = roadPosition.row1+""+roadPosition.col1+""+roadPosition.row2+""+roadPosition.col2; //funkcija van fajla
          console.log("BOT BUILT A ROAD : " + roadId);
          document.querySelector(`.road-${roadId}`).classList.add("road", `road-${roadId}`, `put-${players.indexOf("bot")}`);
        }
        else if(move == 'BUY_CRAZY'){
          Swal.fire({text: `Bot bought a wildcard`, timer: 1000});
        }
        else if(move == 'END_TURN'){
          console.log('END_TURN');
          let x = players.indexOf("bot") + 1;
          if(x == players.length){
            x = 0;
          }
          setPlayerToMove(x);
          Swal.fire({title:`${players[x]} is on the move!` ,timer: 1000});
        }
      })
    });
  }

  const buyCrazy = (e) => {
    e.preventDefault();
    const randomNumber = random(0, 24);
    const pickedCrazy = shuffle(CRAZIES_LIST)[randomNumber];
    addCrazy(players[playerToMove], pickedCrazy);
  }

  useEffect(() => {
    //GET-INPUT-STATE (bcs of reload)|| INIT-INPUT-STATE + INIT-POSITION
    Swal.fire({title: "Welcome to CATONI", text: "How many players are playing(one spot is reserved for our bot so type in 2 or 3):", input: 'number'})
    .then((result) => {
      const numOfPlayers = Number(result.value);
        if (numOfPlayers) {
            console.log("Number of players: " + result.value);
            Swal.fire({text: `What position do you want bot to play?(1 - ${numOfPlayers + 1})`, input: 'number'})
            .then((result) => {
              const botPosition = result.value;
              if(botPosition){
                console.log("Bot plays " + botPosition);
                var pls = []
                var plcnt = 0;
                for(let i = 0; i <= numOfPlayers; i++){
                  if(i == botPosition - 1){
                    pls.push("bot");
                  }
                  else{
                    pls.push(`player${++plcnt}`);
                  }
                }
                console.log(pls);
                setPlayers(pls);
                initState(numOfPlayers, (response) => {
                  setInputState(response.data);
                });
                initChances();
              }
            })
        }
    });
    
  }, []);


  useEffect(() => {
    getResourcesForPlayerName(players[playerToMove], (response) => {
      const resources = response.data;
      getCraziesForPlayerName(players[playerToMove], (resp) => {
        setResourcesInHand([...resources, ...resp.data])
      })
    })
  }, [playerToMove, players])


  return (
    <>
    <div className="playersInfo">
        {
          players.map(p => {
            return(
              <div className={"card card-"+ players.indexOf(p)}>
                <h3>{p}</h3>
                <span className='cardInfo'>Roads: {inputState.playerStates[p].numberOfRoads}</span>
                <br />
                <span className='cardInfo'>Houses: {inputState.playerStates[p].numberOfHouses}</span>
                <br />
                <span className='cardInfo'>Hotels: {inputState.playerStates[p].numberOfHotels}</span>
                <br />
                <span className='cardInfo'>Knights: {inputState.playerStates[p].numberOfKnights}</span>
                <br />
                <span className='cardInfo'>Resources: {inputState.playerStates[p].resources.length}</span>
                <br />
                <span className='cardInfo'>Wildcards: {inputState.playerStates[p].craziesList.length}</span>
              </div>
            )
          })
        }
    </div>
    <div className="board">
      <button className="btn btn-00" value={{row: 0, column: 0, type: 0}} onClick={() => addBuilding(0, 0, 4)}></button>
      <button className="btn btn-01" value={{row: 0, column: 1, type: 0}} onClick={() => addBuilding(0, 1, 1)}></button>
      <button className="btn btn-02" value={{row: 0, column: 2, type: 0}} onClick={() => addBuilding(0, 2, 5)}></button>
      <button className="btn btn-03" value={{row: 0, column: 3, type: 0}} onClick={() => addBuilding(0, 3, 2)}></button>
      <button className="btn btn-04" value={{row: 0, column: 4, type: 0}} onClick={() => addBuilding(0, 4, 6)}></button>
      <button className="btn btn-05" value={{row: 0, column: 5, type: 0}} onClick={() => addBuilding(0, 5, 3)}></button>
      <button className="btn btn-06" value={{row: 0, column: 6, type: 0}} onClick={() => addBuilding(0, 6, 7)}></button>

      <button className="btn btn-10" value={{row: 1, column: 0, type: 0}} onClick={() => addBuilding(1, 0, 12)}></button>
      <button className="btn btn-11" value={{row: 1, column: 1, type: 0}} onClick={() => addBuilding(1, 1, 8)}></button>
      <button className="btn btn-12" value={{row: 1, column: 2, type: 0}} onClick={() => addBuilding(1, 2, 13)}></button>
      <button className="btn btn-13" value={{row: 1, column: 3, type: 0}} onClick={() => addBuilding(1, 3, 9)}></button>
      <button className="btn btn-14" value={{row: 1, column: 4, type: 0}} onClick={() => addBuilding(1, 4, 14)}></button>
      <button className="btn btn-15" value={{row: 1, column: 5, type: 0}} onClick={() => addBuilding(1, 5, 10)}></button>
      <button className="btn btn-16" value={{row: 1, column: 6, type: 0}} onClick={() => addBuilding(1, 6, 15)}></button>
      <button className="btn btn-17" value={{row: 1, column: 7, type: 0}} onClick={() => addBuilding(1, 7, 11)}></button>
      <button className="btn btn-18" value={{row: 1, column: 8, type: 0}} onClick={() => addBuilding(1, 8, 16)}></button>

      <button className="btn btn-20" value={{row: 2, column: 0, type: 0}} onClick={() => addBuilding(2, 0, 22)}></button>
      <button className="btn btn-21" value={{row: 2, column: 1, type: 0}} onClick={() => addBuilding(2, 1, 17)}></button>
      <button className="btn btn-22" value={{row: 2, column: 2, type: 0}} onClick={() => addBuilding(2, 2, 23)}></button>
      <button className="btn btn-23" value={{row: 2, column: 3, type: 0}} onClick={() => addBuilding(2, 3, 18)}></button>
      <button className="btn btn-24" value={{row: 2, column: 4, type: 0}} onClick={() => addBuilding(2, 4, 24)}></button>
      <button className="btn btn-25" value={{row: 2, column: 5, type: 0}} onClick={() => addBuilding(2, 5, 19)}></button>
      <button className="btn btn-26" value={{row: 2, column: 6, type: 0}} onClick={() => addBuilding(2, 6, 25)}></button>
      <button className="btn btn-27" value={{row: 2, column: 7, type: 0}} onClick={() => addBuilding(2, 7, 20)}></button>
      <button className="btn btn-28" value={{row: 2, column: 8, type: 0}} onClick={() => addBuilding(2, 8, 26)}></button>
      <button className="btn btn-29" value={{row: 2, column: 9, type: 0}} onClick={() => addBuilding(2, 9, 21)}></button>
      <button className="btn btn-210" value={{row: 2, column: 10, type: 0}} onClick={() => addBuilding(2, 10, 27)}></button>

      <button className="btn btn-30" value={{row: 3, column: 0, type: 0}} onClick={() => addBuilding(3, 0, 28)}></button>
      <button className="btn btn-31" value={{row: 3, column: 1, type: 0}} onClick={() => addBuilding(3, 1, 34)}></button>
      <button className="btn btn-32" value={{row: 3, column: 2, type: 0}} onClick={() => addBuilding(3, 2, 29)}></button>
      <button className="btn btn-33" value={{row: 3, column: 3, type: 0}} onClick={() => addBuilding(3, 3, 35)}></button>
      <button className="btn btn-34" value={{row: 3, column: 4, type: 0}} onClick={() => addBuilding(3, 4, 30)}></button>
      <button className="btn btn-35" value={{row: 3, column: 5, type: 0}} onClick={() => addBuilding(3, 5, 36)}></button>
      <button className="btn btn-36" value={{row: 3, column: 6, type: 0}} onClick={() => addBuilding(3, 6, 31)}></button>
      <button className="btn btn-37" value={{row: 3, column: 7, type: 0}} onClick={() => addBuilding(3, 7, 37)}></button>
      <button className="btn btn-38" value={{row: 3, column: 8, type: 0}} onClick={() => addBuilding(3, 8, 32)}></button>
      <button className="btn btn-39" value={{row: 3, column: 9, type: 0}} onClick={() => addBuilding(3, 9, 38)}></button>
      <button className="btn btn-310" value={{row: 3, column: 10, type: 0}} onClick={() => addBuilding(3, 10, 33)}></button>

      <button className="btn btn-40" value={{row: 4, column: 0, type: 0}} onClick={() => addBuilding(4, 0, 39)}></button>
      <button className="btn btn-41" value={{row: 4, column: 1, type: 0}} onClick={() => addBuilding(4, 1, 44)}></button>
      <button className="btn btn-42" value={{row: 4, column: 2, type: 0}} onClick={() => addBuilding(4, 2, 40)}></button>
      <button className="btn btn-43" value={{row: 4, column: 3, type: 0}} onClick={() => addBuilding(4, 3, 43)}></button>
      <button className="btn btn-44" value={{row: 4, column: 4, type: 0}} onClick={() => addBuilding(4, 4, 41)}></button>
      <button className="btn btn-45" value={{row: 4, column: 5, type: 0}} onClick={() => addBuilding(4, 5, 46)}></button>
      <button className="btn btn-46" value={{row: 4, column: 6, type: 0}} onClick={() => addBuilding(4, 6, 42)}></button>
      <button className="btn btn-47" value={{row: 4, column: 7, type: 0}} onClick={() => addBuilding(4, 7, 47)}></button>
      <button className="btn btn-48" value={{row: 4, column: 8, type: 0}} onClick={() => addBuilding(4, 8, 43)}></button>

      <button className="btn btn-50" value={{row: 5, column: 0, type: 0}} onClick={() => addBuilding(5, 0, 48)}></button>
      <button className="btn btn-51" value={{row: 5, column: 1, type: 0}} onClick={() => addBuilding(5, 1, 52)}></button>
      <button className="btn btn-52" value={{row: 5, column: 2, type: 0}} onClick={() => addBuilding(5, 2, 49)}></button>
      <button className="btn btn-53" value={{row: 5, column: 3, type: 0}} onClick={() => addBuilding(5, 3, 53)}></button>
      <button className="btn btn-54" value={{row: 5, column: 4, type: 0}} onClick={() => addBuilding(5, 4, 50)}></button>
      <button className="btn btn-55" value={{row: 5, column: 5, type: 0}} onClick={() => addBuilding(5, 5, 54)}></button>
      <button className="btn btn-56" value={{row: 5, column: 6, type: 0}} onClick={() => addBuilding(5, 6, 51)}></button>

      <button className="road road-0011" value={{row1: 0, col1: 0, row2: 1, col2: 1}} onClick={() => addRoad(0, 0, 1, 1, 1)}></button>
      <button className="road road-0001" value={{row1: 0, col1: 0, row2: 0, col2: 1}} onClick={() => addRoad(0, 0, 0, 1, 2)}></button>
      <button className="road road-0102" value={{row1: 0, col1: 1, row2: 0, col2: 2}} onClick={() => addRoad(0, 1, 0, 2, 3)}></button>
      <button className="road road-0203" value={{row1: 0, col1: 2, row2: 0, col2: 3}} onClick={() => addRoad(0, 2, 0, 3, 4)}></button>
      <button className="road road-0304" value={{row1: 0, col1: 3, row2: 0, col2: 4}} onClick={() => addRoad(0, 3, 0, 4, 5)}></button>
      <button className="road road-0405" value={{row1: 0, col1: 4, row2: 0, col2: 5}} onClick={() => addRoad(0, 4, 0, 5, 6)}></button>
      <button className="road road-0506" value={{row1: 0, col1: 5, row2: 0, col2: 6}} onClick={() => addRoad(0, 5, 0, 6, 7)}></button>
      <button className="road road-0617" value={{row1: 0, col1: 6, row2: 1, col2: 7}} onClick={() => addRoad(0, 6, 1, 7, 8)}></button>
      <button className="road road-1011" value={{row1: 1, col1: 0, row2: 1, col2: 1}} onClick={() => addRoad(1, 0, 1, 1, 9)}></button>
      <button className="road road-1112" value={{row1: 1, col1: 1, row2: 1, col2: 2}} onClick={() => addRoad(1, 1, 1, 2, 10)}></button>
      <button className="road road-1213" value={{row1: 1, col1: 2, row2: 1, col2: 3}} onClick={() => addRoad(1, 2, 1, 3, 11)}></button>
      <button className="road road-1314" value={{row1: 1, col1: 3, row2: 1, col2: 4}} onClick={() => addRoad(1, 3, 1, 4, 12)}></button>
      <button className="road road-1415" value={{row1: 1, col1: 4, row2: 1, col2: 5}} onClick={() => addRoad(1, 4, 1, 5, 13)}></button>
      <button className="road road-1516" value={{row1: 1, col1: 5, row2: 1, col2: 6}} onClick={() => addRoad(1, 5, 1, 6, 14)}></button>
      <button className="road road-1617" value={{row1: 1, col1: 6, row2: 1, col2: 7}} onClick={() => addRoad(1, 6, 1, 7, 15)}></button>
      <button className="road road-1718" value={{row1: 1, col1: 7, row2: 1, col2: 8}} onClick={() => addRoad(1, 7, 1, 8, 16)}></button>
      <button className="road road-2122" value={{row1: 2, col1: 1, row2: 2, col2: 2}} onClick={() => addRoad(2, 1, 2, 2, 17)}></button>
      <button className="road road-2223" value={{row1: 2, col1: 2, row2: 2, col2: 3}} onClick={() => addRoad(2, 2, 2, 3, 18)}></button>
      <button className="road road-2324" value={{row1: 2, col1: 3, row2: 2, col2: 4}} onClick={() => addRoad(2, 3, 2, 4, 19)}></button>
      <button className="road road-2425" value={{row1: 2, col1: 4, row2: 2, col2: 5}} onClick={() => addRoad(2, 4, 2, 5, 20)}></button>
      <button className="road road-2526" value={{row1: 2, col1: 5, row2: 2, col2: 6}} onClick={() => addRoad(2, 5, 2, 6, 21)}></button>
      <button className="road road-2627" value={{row1: 2, col1: 6, row2: 2, col2: 7}} onClick={() => addRoad(2, 6, 2, 7, 22)}></button>
      <button className="road road-2728" value={{row1: 2, col1: 7, row2: 2, col2: 8}} onClick={() => addRoad(2, 7, 2, 8, 23)}></button>
      <button className="road road-2829" value={{row1: 2, col1: 8, row2: 2, col2: 9}} onClick={() => addRoad(2, 8, 2, 9, 24)}></button>
      <button className="road road-29210" value={{row1: 2, col1: 9, row2: 2, col2: 10}} onClick={() => addRoad(2, 9, 2, 10, 25)}></button>
      <button className="road road-2021" value={{row1: 2, col1: 0, row2: 2, col2: 1}} onClick={() => addRoad(2, 0, 2, 1, 26)}></button>
      <button className="road road-3031" value={{row1: 3, col1: 0, row2: 3, col2: 1}} onClick={() => addRoad(3, 0, 3, 1, 27)}></button>
      <button className="road road-3132" value={{row1: 3, col1: 1, row2: 3, col2: 2}} onClick={() => addRoad(3, 1, 3, 2, 28)}></button>
      <button className="road road-3233" value={{row1: 3, col1: 2, row2: 3, col2: 3}} onClick={() => addRoad(3, 2, 3, 3, 29)}></button>
      <button className="road road-3334" value={{row1: 3, col1: 3, row2: 3, col2: 4}} onClick={() => addRoad(3, 3, 3, 4, 30)}></button>
      <button className="road road-3435" value={{row1: 3, col1: 4, row2: 3, col2: 5}} onClick={() => addRoad(3, 4, 3, 5, 31)}></button>
      <button className="road road-3536" value={{row1: 3, col1: 5, row2: 3, col2: 6}} onClick={() => addRoad(3, 5, 3, 6, 32)}></button>
      <button className="road road-3637" value={{row1: 3, col1: 6, row2: 3, col2: 7}} onClick={() => addRoad(3, 6, 3, 7, 33)}></button>
      <button className="road road-3738" value={{row1: 3, col1: 7, row2: 3, col2: 8}} onClick={() => addRoad(3, 7, 3, 8, 34)}></button>
      <button className="road road-3839" value={{row1: 3, col1: 8, row2: 3, col2: 9}} onClick={() => addRoad(3, 8, 3, 9, 35)}></button>
      <button className="road road-39310" value={{row1: 3, col1: 9, row2: 3, col2: 10}} onClick={() => addRoad(3, 9, 3, 10, 36)}></button>

      <button className="road road-4041" value={{row1: 4, col1: 0, row2: 4, col2: 1}} onClick={() => addRoad(4, 0, 4, 1, 37)}></button>
      <button className="road road-4142" value={{row1: 4, col1: 1, row2: 4, col2: 2}} onClick={() => addRoad(4, 1, 4, 2, 38)}></button>
      <button className="road road-4243" value={{row1: 4, col1: 2, row2: 4, col2: 3}} onClick={() => addRoad(4, 2, 4, 3, 39)}></button>
      <button className="road road-4344" value={{row1: 4, col1: 3, row2: 4, col2: 4}} onClick={() => addRoad(4, 3, 4, 4, 40)}></button>
      <button className="road road-4445" value={{row1: 4, col1: 4, row2: 4, col2: 5}} onClick={() => addRoad(4, 4, 4, 5, 41)}></button>
      <button className="road road-4546" value={{row1: 4, col1: 5, row2: 4, col2: 6}} onClick={() => addRoad(4, 5, 4, 6, 42)}></button>
      <button className="road road-4647" value={{row1: 4, col1: 6, row2: 4, col2: 7}} onClick={() => addRoad(4, 6, 4, 7, 43)}></button>
      <button className="road road-4748" value={{row1: 4, col1: 7, row2: 4, col2: 8}} onClick={() => addRoad(4, 7, 4, 8, 44)}></button>

      <button className="road road-5051" value={{row1: 5, col1: 0, row2: 5, col2: 1}} onClick={() => addRoad(5, 0, 5, 1, 45)}></button>
      <button className="road road-5152" value={{row1: 5, col1: 1, row2: 5, col2: 2}} onClick={() => addRoad(5, 1, 5, 2, 46)}></button>
      <button className="road road-5253" value={{row1: 5, col1: 2, row2: 5, col2: 3}} onClick={() => addRoad(5, 2, 5, 3, 47)}></button>
      <button className="road road-5354" value={{row1: 5, col1: 3, row2: 5, col2: 4}} onClick={() => addRoad(5, 3, 5, 4, 48)}></button>
      <button className="road road-5455" value={{row1: 5, col1: 4, row2: 5, col2: 5}} onClick={() => addRoad(5, 4, 5, 5, 49)}></button>
      <button className="road road-5556" value={{row1: 5, col1: 5, row2: 5, col2: 6}} onClick={() => addRoad(5, 5, 5, 6, 50)}></button>

      <button className="road road-0213" value={{row1: 0, col1: 2, row2: 1, col2: 3}} onClick={() => addRoad(0, 2, 1, 3, 52)}></button>
      <button className="road road-0415" value={{row1: 0, col1: 4, row2: 1, col2: 5}} onClick={() => addRoad(0, 4, 1, 5, 53)}></button>
      <button className="road road-1021" value={{row1: 1, col1: 0, row2: 2, col2: 1}} onClick={() => addRoad(1, 0, 2, 1, 54)}></button>
      <button className="road road-1223" value={{row1: 1, col1: 2, row2: 2, col2: 3}} onClick={() => addRoad(1, 2, 2, 3, 55)}></button>
      <button className="road road-1425" value={{row1: 1, col1: 4, row2: 2, col2: 5}} onClick={() => addRoad(1, 4, 2, 5, 56)}></button>
      <button className="road road-1627" value={{row1: 1, col1: 6, row2: 2, col2: 7}} onClick={() => addRoad(1, 6, 2, 7, 57)}></button>
      <button className="road road-1829" value={{row1: 1, col1: 8, row2: 2, col2: 9}} onClick={() => addRoad(1, 8, 2, 9, 58)}></button>

      <button className="road road-2030" value={{row1: 2, col1: 0, row2: 3, col2: 0}} onClick={() => addRoad(2, 0, 3, 0, 59)}></button>
      <button className="road road-2232" value={{row1: 2, col1: 2, row2: 3, col2: 2}} onClick={() => addRoad(2, 2, 3, 2, 60)}></button>
      <button className="road road-2434" value={{row1: 2, col1: 4, row2: 3, col2: 4}} onClick={() => addRoad(2, 4, 3, 4, 61)}></button>
      <button className="road road-2636" value={{row1: 2, col1: 6, row2: 3, col2: 6}} onClick={() => addRoad(2, 6, 3, 6, 62)}></button>
      <button className="road road-2838" value={{row1: 2, col1: 8, row2: 3, col2: 8}} onClick={() => addRoad(2, 8, 3, 8, 63)}></button>
      <button className="road road-210310" value={{row1: 2, col1: 10, row2: 3, col2: 10}} onClick={() => addRoad(2, 10, 3, 10, 64)}></button>

      <button className="road road-3140" value={{row1: 3, col1: 1, row2: 4, col2: 0}} onClick={() => addRoad(3, 1, 4, 0, 65)}></button>
      <button className="road road-3544" value={{row1: 3, col1: 5, row2: 4, col2: 4}} onClick={() => addRoad(3, 5, 4, 4, 66)}></button>
      <button className="road road-3746" value={{row1: 3, col1: 7, row2: 4, col2: 6}} onClick={() => addRoad(3, 7, 4, 6, 67)}></button>
      <button className="road road-3948" value={{row1: 3, col1: 9, row2: 4, col2: 8}} onClick={() => addRoad(3, 9, 4, 8, 68)}></button>
      <button className="road road-4150" value={{row1: 4, col1: 1, row2: 5, col2: 0}} onClick={() => addRoad(4, 1, 5, 0, 69)}></button>
      <button className="road road-4352" value={{row1: 4, col1: 3, row2: 5, col2: 2}} onClick={() => addRoad(4, 3, 5, 2, 70)}></button>
      <button className="road road-4554" value={{row1: 4, col1: 5, row2: 5, col2: 4}} onClick={() => addRoad(4, 5, 5, 4, 71)}></button>
      <button className="road road-4756" value={{row1: 4, col1: 7, row2: 5, col2: 6}} onClick={() => addRoad(4, 7, 5, 6, 72)}></button>
      <button className="road road-3342" value={{row1: 3, col1: 3, row2: 4, col2: 2}} onClick={() => addRoad(3, 3, 4, 2, 73)}></button>

      <div className="port port-1"></div>
      <div className="port port-2"></div>
      <div className="port port-3"></div>
      <div className="port port-4"></div>
      <div className="port port-5"></div>
      <div className="port port-6"></div>
      <div className="port port-7"></div>
      <div className="port port-8"></div>
      <div className="port port-9"></div>
      <div className="port port-10"></div>
      <div className="port port-11"></div>
      <div className="port port-12"></div>
      <div className="port port-13"></div>
      <div className="port port-14"></div>
      <div className="port port-15"></div>
      <div className="port port-16"></div>
      <div className="port port-17"></div>
      <div className="port port-18"></div>

      <div className="part part1">
        <div className="shape forest"></div>
        <span className="number">10</span>
      </div>
      <div className="part part2">
        <div className="shape stone"></div>
        <span className="number">6</span>
      </div>
      <div className="part part3">
        <div className="shape pasture"></div>
        <span className="number">10</span>
      </div>

      <div className="part part4">
        <div className="shape field"></div>
        <span className="number">8</span>
      </div>
      <div className="part part5">
        <div className="shape pasture"></div>
        <span className="number">6</span>
      </div>
      <div className="part part6">
        <div className="shape forest"></div>
        <span className="number">5</span>
      </div>
      <div className="part part7">
        <div className="shape mine"></div>
        <span className="number">11</span>
      </div>

      <div className="part part8">
        <div className="shape forest"></div>
        <span className="number">3</span>
      </div>
      <div className="part part9">
        <div className="shape mine"></div>
        <span className="number">9</span>
      </div>
      <div className="part part10">
        <div className="shape pasture"></div>
        <span className="number">8</span>
      </div>
      <div className="part part11">
        <div className="shape mine"></div>
        <span className="number">12</span>
      </div>
      <div className="part part12">
        <div className="shape field"></div>
        <span className="number">3</span>
      </div>

      <div className="part part13">
        <div className="shape forest"></div>
        <span className="number">9</span>
      </div>
      <div className="part part14">
        <div className="shape pasture"></div>
        <span className="number">2</span>
      </div>
      <div className="part part15">
        <div className="shape field"></div>
        <span className="number">5</span>
      </div>
      <div className="part part16">
        <div className="shape stone"></div>
        <span className="number">11</span>
      </div>

      <div className="part part17">
        <div className="shape field"></div>
        <span className="number">4</span>
      </div>
      <div className="part part18">
        <div className="shape stone"></div>
        <span className="number">4</span>
      </div>
      <div className="part part19">
        <div className="shape desert"></div>
      </div>
    </div>
    <div className="bottom">
      <p>{printResourcesInHand(resourcesInHand)}</p>
      {/* end turn build house build road build hotel play crazy */}
      <button className={players[0] == "bot" ? 'dice' : 'hidden'} onClick={startGame}>START GAME</button>
      <button className='dice' onClick={rollDice}>Roll dice</button>
      <button className='endTurn' onClick={endTurn}>End turn</button>
      <button className='crazy' onClick={buyCrazy}>Buy crazy</button>
    </div>
    </>
  );
}

export default App;
