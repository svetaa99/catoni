export function random(min = 1, max = 6) { // min and max included 
  return Math.floor(Math.random() * (max - min + 1) + min)
}

export function getResourcesForNumber(number, callback){
  var resourcePositions = [];
  switch(number){
    case 2:
      resourcePositions = [{resource: "SHEEP", positions: [{"row": 3, "col": 3}, {"row": 3, "col": 4}, {"row": 3, "col": 5}, {"row": 4, "col": 2}, {"row": 4, "col": 3}, {"row": 4, "col": 4}]}]
      callback(resourcePositions);
      break;
    case 3:
      resourcePositions = [{resource: "WOOD", positions: [{"row": 2, "col": 0}, {"row": 2, "col": 1}, {"row": 2, "col": 2}, {"row": 3, "col": 0}, {"row": 3, "col": 1}, {"row": 3, "col": 2}]}, 
                           {resource: "GRAIN", positions: [{"row": 2, "col": 8}, {"row": 2, "col": 9}, {"row": 2, "col": 10}, {"row": 3, "col": 8}, {"row": 3, "col": 9}, {"row": 3, "col": 10}]}]
      callback(resourcePositions);
      break;
    case 4:
      resourcePositions = [{resource: "GRAIN", positions: [{"row": 4, "col": 1}, {"row": 4, "col": 2}, {"row": 4, "col": 3}, {"row": 5, "col": 0}, {"row": 5, "col": 1}, {"row": 5, "col": 2}]}, 
                           {resource: "ROCK", positions: [{"row": 4, "col": 3}, {"row": 4, "col": 4}, {"row": 4, "col": 5}, {"row": 5, "col": 2}, {"row": 5, "col": 3}, {"row": 5, "col": 4}]}]
      callback(resourcePositions);
      break;
    case 5:
      resourcePositions = [{resource: "GRAIN", positions: [{"row": 3, "col": 5}, {"row": 3, "col": 6}, {"row": 3, "col": 7}, {"row": 4, "col": 4}, {"row": 4, "col": 5}, {"row": 4, "col": 6}]},
                           {resource: "WOOD", positions: [{"row": 1, "col": 4}, {"row": 1, "col": 5}, {"row": 1, "col": 6}, {"row": 2, "col": 5}, {"row": 2, "col": 6}, {"row": 2, "col": 7}]}]
      break;
    case 6:
      resourcePositions = [{resource: "ROCK", positions: [{"row": 0, "col": 2}, {"row": 0, "col": 3}, {"row": 0, "col": 4}, {"row": 1, "col": 3}, {"row": 1, "col": 4}, {"row": 1, "col": 5}]}, 
                           {resource: "SHEEP", positions: [{"row": 1, "col": 2}, {"row": 1, "col": 3}, {"row": 1, "col": 4}, {"row": 2, "col": 3}, {"row": 2, "col": 4}, {"row": 2, "col": 5}]}]  
      callback(resourcePositions);
      break;
    case 7:
      return;
    case 8:
      resourcePositions = [{resource: "GRAIN", positions: [{"row": 1, "col": 0}, {"row": 1, "col": 1}, {"row": 1, "col": 2}, {"row": 2, "col": 1}, {"row": 2, "col": 2}, {"row": 2, "col": 3}]},
                           {resource: "SHEEP", positions: [{"row": 2, "col": 4}, {"row": 2, "col": 5}, {"row": 2, "col": 6}, {"row": 3, "col": 4}, {"row": 3, "col": 5}, {"row": 3, "col": 6}]}]
      callback(resourcePositions);
      break;
    case 9:
      resourcePositions = [{resource: "CLAY", positions: [{"row": 2, "col": 2}, {"row": 2, "col": 3}, {"row": 2, "col": 4}, {"row": 3, "col": 2}, {"row": 3, "col": 3}, {"row": 3, "col": 4}]},
                           {resource: "WOOD", positions: [{"row": 3, "col": 1}, {"row": 3, "col": 2}, {"row": 3, "col": 3}, {"row": 4, "col": 0}, {"row": 4, "col": 1}, {"row": 4, "col": 2}]}]
      callback(resourcePositions);
      break;
    case 10:
      resourcePositions = [{resource: "SHEEP", positions: [{"row": 0, "col": 4}, {"row": 0, "col": 5}, {"row": 0, "col": 6}, {"row": 1, "col": 5}, {"row": 1, "col": 6}, {"row": 1, "col": 7}]}, 
                           {resource: "WOOD", positions: [{"row": 0, "col": 0}, {"row": 0, "col": 1}, {"row": 0, "col": 2}, {"row": 1, "col": 1}, {"row": 1, "col": 2}, {"row": 1, "col": 3}]}]
      callback(resourcePositions);
      break;
    case 11:
      resourcePositions = [{resource: "CLAY", positions: [{"row": 1, "col": 6}, {"row": 1, "col": 7}, {"row": 1, "col": 8}, {"row": 2, "col": 7}, {"row": 2, "col": 8}, {"row": 2, "col": 9}]},
                           {resource: "ROCK", positions: [{"row": 3, "col": 7}, {"row": 3, "col": 8}, {"row": 3, "col": 9}, {"row": 4, "col": 6}, {"row": 4, "col": 7}, {"row": 4, "col": 8}]}]
      
      callback(resourcePositions);
      break;
    case 12:
      resourcePositions = [{resource: "CLAY", positions: [{"row": 2, "col": 6},{"row": 2, "col": 7},{"row": 2, "col": 8},{"row": 3, "col": 6},{"row": 3, "col": 7},{"row": 3, "col": 8}]}]
      callback(resourcePositions);
      break;
    default:
      return;
    callback(resourcePositions);
  }
}