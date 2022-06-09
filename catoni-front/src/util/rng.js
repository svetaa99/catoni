export function random(min = 1, max = 6) { // min and max included 
  return Math.floor(Math.random() * (max - min + 1) + min)
}