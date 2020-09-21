const { replaceIf } = require("./cljs_dist/node/index")
const web_cljs = require("./cljs_dist/web/index")

const json1 = {	
	key1: "1",
	key2: "2",
	key3: "3",
	key4: "4",
	key5: {
		key22: null,
		key11: "10",
		key33: "30"
	},
	special: "BORA"
}
const json2 = {	
	key1: "10",
	key3: null,
	key4: "40",
	key5: {
		key11: null,
		key22: "200",
		key33: null
	}, 
	key6: "6", 
	quit: -1
}

const res = replaceIf({src:json1, target:json2 })
console.log("res : ", res) 

// res => 
//
// {	
// 	key1: "10",
// 	key2: "2",
// 	key3: "3",
// 	key4: "40"
//  key5: {
// 		key11: "10", 
// 		key22: "200", 
// 		key33: "30", 
//	}
// 	key6: "6", 
//	special: "BORA"
//  quit: -1
// }

// vs
/*
res :  {
  key1: '10',
  key2: '2',
  key3: '3',
  key4: '40',
  key5: { key11: '10', key22: '200', key33: '30' },
  special: 'BORA',
  key6: '6',
  quit: -1
}
*/

// console.log(web_cljs.hello("web!"))

// if (
//   typeof module !== 'undefined' &&
//   typeof module.exports !== 'undefined'
// ) {
// 	exports.nodejs = nodejs
// } else {
// 	window = { web_cljs }
// }