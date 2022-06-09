import './App.css';
import {useState, useEffect} from 'react';
import axios from "axios";
import { buildHouse, buildRoad, getStartingPosition, initChances, initState } from './axiosservice/axiosService';
import { random } from './util/rng';
import Swal from 'sweetalert2';

function App() {

  const[playerToMove, setPlayerToMove] = useState(0);

  const[players, setPlayers] = useState([]);

  const[inputState, setInputState] = useState({});

  const[phase, setPhase] = useState("start");

  const [moveCounter, setMoveCounter] = useState(1);

  function startGame(){
    getStartingPosition((response) => {
      console.log(response.data);
      var idStr = response.data.building.row+""+response.data.building.col;
      document.querySelector(`.btn-${idStr}`).classList.add("btn", `btn-${idStr}`, `kuca-${playerToMove}`);
      setPlayerToMove(playerToMove + 1);
    });
  }

  function addBuilding(row, col, btnId){
    buildHouse({row, col, playerName: players[playerToMove], type: 1}, (response) => {
      //proveri da li je kuca ili hotel pa povecaj dugme
      console.log(playerToMove + " BUILT ON POSITION : " + row + "-" + col);
      var idStr = row+""+col;
      console.log("FARBAJ NA: " + idStr);
      document.querySelector(`.btn-${idStr}`).classList.add("btn", `btn-${idStr}`, `kuca-${playerToMove}`);
    });//catch
  }

  function addRoad(row1, col1, row2, col2, roadId){
    buildRoad({row1, col1, row2, col2, player: players[playerToMove]}, (response) => {
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
    const dice1 = random()
    const dice2 = random()
    console.log(dice1+dice2);
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
      if(players[x] == "bot" && moveCounter <= 2){
        getStartingPosition((response) => {
          console.log(response.data);
            let idStr = response.data.building.row + "" + response.data.building.col
            document.querySelector(`.btn-${idStr}`).classList.add("btn", `btn-${idStr}`, `kuca-${x}`);
            //dodaj i put
            x = nextToMove(playerToMove + 1);
            setPlayerToMove(x);
            Swal.fire({title:`${players[x]} is on the move!` ,timer: 1000})
            return;
        });
      }
      else{
        setPlayerToMove(x);
      }
    });
    //if players[playerToMove+1] axios/getmove IZNAD SETOVANJA KO JE NA POTEZU IZVUCES TU INFO I ODRADIS SVE OVO AKO JE BOT SAMO STAVIS NA JOS JEDNOG ISPRED I TJT
    
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
  return (
    <>
    <div className="playersInfo">

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

      <button className="road road-1" value={{row1: 0, col1: 0, row2: 1, col2: 1}} onClick={() => addRoad(0, 0, 1, 1, 1)}></button>
      <button className="road road-2" value={{row1: 0, col1: 0, row2: 0, col2: 1}} onClick={() => addRoad(0, 0, 0, 1, 2)}></button>
      <button className="road road-3" value={{row1: 0, col1: 1, row2: 0, col2: 2}} onClick={() => addRoad(0, 1, 0, 2, 3)}></button>
      <button className="road road-4" value={{row1: 0, col1: 2, row2: 0, col2: 3}} onClick={() => addRoad(0, 2, 0, 3, 4)}></button>
      <button className="road road-5" value={{row1: 0, col1: 3, row2: 0, col2: 4}} onClick={() => addRoad(0, 3, 0, 4, 5)}></button>
      <button className="road road-6" value={{row1: 0, col1: 4, row2: 0, col2: 5}} onClick={() => addRoad(0, 4, 0, 5, 6)}></button>
      <button className="road road-7" value={{row1: 0, col1: 5, row2: 0, col2: 6}} onClick={() => addRoad(0, 5, 0, 6, 7)}></button>
      <button className="road road-8" value={{row1: 0, col1: 6, row2: 1, col2: 7}} onClick={() => addRoad(0, 6, 1, 7, 8)}></button>
      <button className="road road-9" value={{row1: 1, col1: 0, row2: 1, col2: 1}} onClick={() => addRoad(1, 0, 1, 1, 9)}></button>
      <button className="road road-10" value={{row1: 1, col1: 1, row2: 1, col2: 2}} onClick={() => addRoad(1, 1, 1, 2, 10)}></button>
      <button className="road road-11" value={{row1: 1, col1: 2, row2: 1, col2: 3}} onClick={() => addRoad(1, 2, 1, 3, 11)}></button>
      <button className="road road-12" value={{row1: 1, col1: 3, row2: 1, col2: 4}} onClick={() => addRoad(1, 3, 1, 4, 12)}></button>
      <button className="road road-13" value={{row1: 1, col1: 4, row2: 1, col2: 5}} onClick={() => addRoad(1, 4, 1, 5, 13)}></button>
      <button className="road road-14" value={{row1: 1, col1: 5, row2: 1, col2: 6}} onClick={() => addRoad(1, 5, 1, 6, 14)}></button>
      <button className="road road-15" value={{row1: 1, col1: 6, row2: 1, col2: 7}} onClick={() => addRoad(1, 6, 1, 7, 15)}></button>
      <button className="road road-16" value={{row1: 1, col1: 7, row2: 1, col2: 8}} onClick={() => addRoad(1, 7, 1, 8, 16)}></button>
      <button className="road road-17" value={{row1: 2, col1: 1, row2: 2, col2: 2}} onClick={() => addRoad(2, 1, 2, 2, 17)}></button>
      <button className="road road-18" value={{row1: 2, col1: 2, row2: 2, col2: 3}} onClick={() => addRoad(2, 2, 2, 3, 18)}></button>
      <button className="road road-19" value={{row1: 2, col1: 3, row2: 2, col2: 4}} onClick={() => addRoad(2, 3, 2, 4, 19)}></button>
      <button className="road road-20" value={{row1: 2, col1: 4, row2: 2, col2: 5}} onClick={() => addRoad(2, 4, 2, 5, 20)}></button>
      <button className="road road-21" value={{row1: 2, col1: 5, row2: 2, col2: 6}} onClick={() => addRoad(2, 5, 2, 6, 21)}></button>
      <button className="road road-22" value={{row1: 2, col1: 6, row2: 2, col2: 7}} onClick={() => addRoad(2, 6, 2, 7, 22)}></button>
      <button className="road road-23" value={{row1: 2, col1: 7, row2: 2, col2: 8}} onClick={() => addRoad(2, 7, 2, 8, 23)}></button>
      <button className="road road-24" value={{row1: 2, col1: 8, row2: 2, col2: 9}} onClick={() => addRoad(2, 8, 2, 9, 24)}></button>
      <button className="road road-25" value={{row1: 2, col1: 9, row2: 2, col2: 10}} onClick={() => addRoad(2, 9, 2, 10, 25)}></button>
      <button className="road road-26" value={{row1: 2, col1: 0, row2: 2, col2: 1}} onClick={() => addRoad(2, 0, 2, 1, 26)}></button>
      <button className="road road-27" value={{row1: 3, col1: 0, row2: 3, col2: 1}} onClick={() => addRoad(3, 0, 3, 1, 27)}></button>
      <button className="road road-28" value={{row1: 3, col1: 1, row2: 3, col2: 2}} onClick={() => addRoad(3, 1, 3, 2, 28)}></button>
      <button className="road road-29" value={{row1: 3, col1: 2, row2: 3, col2: 3}} onClick={() => addRoad(3, 2, 3, 3, 29)}></button>
      <button className="road road-30" value={{row1: 3, col1: 3, row2: 3, col2: 4}} onClick={() => addRoad(3, 3, 3, 4, 30)}></button>
      <button className="road road-31" value={{row1: 3, col1: 4, row2: 3, col2: 5}} onClick={() => addRoad(3, 4, 3, 5, 31)}></button>
      <button className="road road-32" value={{row1: 3, col1: 5, row2: 3, col2: 6}} onClick={() => addRoad(3, 5, 3, 6, 32)}></button>
      <button className="road road-33" value={{row1: 3, col1: 6, row2: 3, col2: 7}} onClick={() => addRoad(3, 6, 3, 7, 33)}></button>
      <button className="road road-34" value={{row1: 3, col1: 7, row2: 3, col2: 8}} onClick={() => addRoad(3, 7, 3, 8, 34)}></button>
      <button className="road road-35" value={{row1: 3, col1: 8, row2: 3, col2: 9}} onClick={() => addRoad(3, 8, 3, 9, 35)}></button>
      <button className="road road-36" value={{row1: 3, col1: 9, row2: 3, col2: 10}} onClick={() => addRoad(3, 9, 3, 10, 36)}></button>

      <button className="road road-37" value={{row1: 4, col1: 0, row2: 4, col2: 1}} onClick={() => addRoad(4, 0, 4, 1, 37)}></button>
      <button className="road road-38" value={{row1: 4, col1: 1, row2: 4, col2: 2}} onClick={() => addRoad(4, 1, 4, 2, 38)}></button>
      <button className="road road-39" value={{row1: 4, col1: 2, row2: 4, col2: 3}} onClick={() => addRoad(4, 2, 4, 3, 39)}></button>
      <button className="road road-40" value={{row1: 4, col1: 3, row2: 4, col2: 4}} onClick={() => addRoad(4, 3, 4, 4, 40)}></button>
      <button className="road road-41" value={{row1: 4, col1: 4, row2: 4, col2: 5}} onClick={() => addRoad(4, 4, 4, 5, 41)}></button>
      <button className="road road-42" value={{row1: 4, col1: 5, row2: 4, col2: 6}} onClick={() => addRoad(4, 5, 4, 6, 42)}></button>
      <button className="road road-43" value={{row1: 4, col1: 6, row2: 4, col2: 7}} onClick={() => addRoad(4, 6, 4, 7, 43)}></button>
      <button className="road road-44" value={{row1: 4, col1: 7, row2: 4, col2: 8}} onClick={() => addRoad(4, 7, 4, 8, 44)}></button>

      <button className="road road-45" value={{row1: 5, col1: 0, row2: 5, col2: 1}} onClick={() => addRoad(5, 0, 5, 1, 45)}></button>
      <button className="road road-46" value={{row1: 5, col1: 1, row2: 5, col2: 2}} onClick={() => addRoad(5, 1, 5, 2, 46)}></button>
      <button className="road road-47" value={{row1: 5, col1: 2, row2: 5, col2: 3}} onClick={() => addRoad(5, 2, 5, 3, 47)}></button>
      <button className="road road-48" value={{row1: 5, col1: 3, row2: 5, col2: 4}} onClick={() => addRoad(5, 3, 5, 4, 48)}></button>
      <button className="road road-49" value={{row1: 5, col1: 4, row2: 5, col2: 5}} onClick={() => addRoad(5, 4, 5, 5, 49)}></button>
      <button className="road road-50" value={{row1: 5, col1: 5, row2: 5, col2: 6}} onClick={() => addRoad(5, 5, 5, 6, 50)}></button>

      <button className="road road-52" value={{row1: 0, col1: 2, row2: 1, col2: 3}} onClick={() => addRoad(0, 2, 1, 3, 52)}></button>
      <button className="road road-53" value={{row1: 0, col1: 4, row2: 1, col2: 5}} onClick={() => addRoad(0, 4, 1, 5, 53)}></button>
      <button className="road road-54" value={{row1: 1, col1: 0, row2: 2, col2: 1}} onClick={() => addRoad(1, 0, 2, 1, 54)}></button>
      <button className="road road-55" value={{row1: 1, col1: 2, row2: 2, col2: 3}} onClick={() => addRoad(1, 2, 2, 3, 55)}></button>
      <button className="road road-56" value={{row1: 1, col1: 4, row2: 2, col2: 5}} onClick={() => addRoad(1, 4, 2, 5, 56)}></button>
      <button className="road road-57" value={{row1: 1, col1: 6, row2: 2, col2: 7}} onClick={() => addRoad(1, 6, 2, 7, 57)}></button>
      <button className="road road-58" value={{row1: 1, col1: 8, row2: 2, col2: 9}} onClick={() => addRoad(1, 8, 2, 9, 58)}></button>

      <button className="road road-59" value={{row1: 2, col1: 0, row2: 3, col2: 0}} onClick={() => addRoad(2, 0, 3, 0, 59)}></button>
      <button className="road road-60" value={{row1: 2, col1: 2, row2: 3, col2: 2}} onClick={() => addRoad(2, 2, 3, 2, 60)}></button>
      <button className="road road-61" value={{row1: 2, col1: 4, row2: 3, col2: 4}} onClick={() => addRoad(2, 4, 3, 4, 61)}></button>
      <button className="road road-62" value={{row1: 2, col1: 6, row2: 3, col2: 6}} onClick={() => addRoad(2, 6, 3, 6, 62)}></button>
      <button className="road road-63" value={{row1: 2, col1: 8, row2: 3, col2: 8}} onClick={() => addRoad(2, 8, 3, 8, 63)}></button>
      <button className="road road-64" value={{row1: 2, col1: 10, row2: 3, col2: 10}} onClick={() => addRoad(2, 10, 3, 10, 64)}></button>

      <button className="road road-65" value={{row1: 3, col1: 1, row2: 4, col2: 0}} onClick={() => addRoad(3, 1, 4, 0, 65)}></button>
      <button className="road road-66" value={{row1: 3, col1: 5, row2: 4, col2: 4}} onClick={() => addRoad(3, 5, 4, 4, 66)}></button>
      <button className="road road-67" value={{row1: 3, col1: 7, row2: 4, col2: 6}} onClick={() => addRoad(3, 7, 4, 6, 67)}></button>
      <button className="road road-68" value={{row1: 3, col1: 9, row2: 4, col2: 8}} onClick={() => addRoad(3, 9, 4, 8, 68)}></button>
      <button className="road road-69" value={{row1: 4, col1: 1, row2: 5, col2: 0}} onClick={() => addRoad(4, 1, 5, 0, 69)}></button>
      <button className="road road-70" value={{row1: 4, col1: 3, row2: 5, col2: 2}} onClick={() => addRoad(4, 3, 5, 2, 70)}></button>
      <button className="road road-71" value={{row1: 4, col1: 5, row2: 5, col2: 4}} onClick={() => addRoad(4, 5, 5, 4, 71)}></button>
      <button className="road road-72" value={{row1: 4, col1: 7, row2: 5, col2: 6}} onClick={() => addRoad(4, 7, 5, 6, 72)}></button>
      <button className="road road-73" value={{row1: 3, col1: 3, row2: 4, col2: 2}} onClick={() => addRoad(3, 3, 4, 2, 73)}></button>

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
        <div className="shape pasture"></div>
        <span className="number">6</span>
      </div>
      <div className="part part3">
        <div className="shape stone"></div>
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
      {/* end turn build house build road build hotel play crazy */}
      <button className={players[0] == "bot" ? 'dice' : 'hidden'} onClick={startGame}>START GAME</button>
      <button className='dice' onClick={rollDice}>Roll dice</button>
      <button className='endTurn' onClick={endTurn}>End turn</button>
    </div>
    </>
  );
}

export default App;
