let info = {
  pageSize: 20,
  rooms: {}
};

[
  "1101",
  "1102",
  "1103",
  "1106",
  "1108",
  "1201",
  "1202",
  "1203",
  "1204",
  "1205",
  "1207",
  "1209",
  "1212",
  "1213",
  "1215",
  "1301",
  "1302",
  "1303",
  "1304",
  "1305"
].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      pageNo: 1,
      pageCount: 2,
      pageXiaoqu: 1,
      pageLhdm: "1J01",
      index: ind
    })
);

[
  "1306",
  "1307",
  "1309",
  "1313",
  "1315",
  "1316",
  "1317",
  "1402",
  "1405",
  "1407",
  "1214"
].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      pageNo: 2,
      pageCount: 2,
      pageXiaoqu: 1,
      pageLhdm: "1J01",
      index: ind
    })
);

const rooms = {
  x31: { pageNo: 1, pageCount: 2, pageXiaoqu: 1, pageLhdm: "1J03" },
  x32: { pageNo: 2, pageCount: 2, pageXiaoqu: 1, pageLhdm: "1J03" },
  x41: { pageNo: 1, pageCount: 1, pageXiaoqu: 1, pageLhdm: "1J04" },
};

[
  "3202",
  "3203",
  "3207",
  "3209",
  "3215",
  "3218",
  "3219",
  "3301",
  "3306",
  "3307",
  "3311",
  "3312",
  "3317",
  "3401",
  "3402",
  "3403",
  "3404",
  "3405"
].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      name: el.toLowerCase(),
      queryParams: "x31",
      index: ind
    })
);

["3302", "3104", "3108", "3407", "3115", "3105"].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      name: el.toLowerCase(),
      index: ind,
      queryParams: "x32"
    })
);

[
  "4314",
  "4401",
  "4402",
  "4403",
  "4404",
  "4406",
  "4407",
  "4408",
  "4409"
].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      name: el.toLowerCase(),
      index: ind,
      queryParams: "x41"
    })
);

[
  "ZM202",
  "ZM204",
  "zm301",
  "zm302",
  "zm402",
  "zm403",
  "zm404",
  "zb201",
  "zb303",
  "zb305",
  "zb306",
  "zb307",
  "zb401",
  "zb402",
  "zb403",
  "zn201",
  "zn203",
  "zn210",
  "zn301",
  "zn302"
].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      pageNo: 3,
      pageCount: 4,
      pageXiaoqu: 1,
      pageLhdm: "1J07",
      index: ind
    })
);

["zn303", "zn305", "zb107", "zb101", "zb103", "zb105", "z125", "z119"].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      pageNo: 4,
      pageCount: 4,
      pageXiaoqu: 1,
      pageLhdm: "1J07",
      index: ind
    })
);

[
  "b119",
  "f404",
  "f406",
  "f430",
  "f432",
  "f433",
  "f434",
  "f529",
  "f532",
  "f534",
  "f535",
  "d601",
  "d609"
].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      pageNo: 1,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind + 7
    })
);

info.rooms["d611"] = {
  pageNo: 2,
  pageCount: 8,
  pageXiaoqu: 1,
  pageLhdm: "1J08",
  index: 0
};

info.rooms["d617"] = info.rooms["d619"] = {
  pageNo: 2,
  pageCount: 8,
  pageXiaoqu: 1,
  pageLhdm: "1J08",
  index: 1
};

["b204", "d611", "d609"].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      pageNo: 2,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind + 4
    })
);

["d601", "f328", "f327", "f332", "f333", "f330", "d408", "c403"].forEach(
  (el, ind) =>
    (info.rooms[el.toLowerCase()] = {
      pageNo: 2,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind + 8
    })
);

info.rooms["d110"] = info.rooms["d111"] = {
  pageNo: 2,
  pageCount: 8,
  pageXiaoqu: 1,
  pageLhdm: "1J08",
  index: 18
};

info.rooms["b319"] = {
  pageNo: 2,
  pageCount: 8,
  pageXiaoqu: 1,
  pageLhdm: "1J08",
  index: 19
};

info.rooms["d105"] = info.rooms["d106"] = {
  pageNo: 3,
  pageCount: 8,
  pageXiaoqu: 1,
  pageLhdm: "1J08",
  index: 0
};

["b307", "b406", "b404"].forEach(
  (el, ind) =>
    (info.rooms[el] = {
      pageNo: 3,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind + 2
    })
);

[
  "e616",
  "c102",
  "c201",
  "d215",
  "d218",
  "d221",
  "d225",
  "e207",
  "f101",
  "f102",
  "f103",
  "f117",
  "f118",
  "f121",
  "f122",
  "f201",
  "f203",
  "f205",
  "f207",
  "f213"
].forEach(
  (el, ind) =>
    (info.rooms[el] = {
      pageNo: 4,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind
    })
);

[
  "f219",
  "f222",
  "f224",
  "f227",
  "f228",
  "g101",
  "g201",
  "a728",
  "b402",
  "b307",
  "d1008",
  "b406"
].forEach(
  (el, ind) =>
    (info.rooms[el] = {
      pageNo: 5,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind
    })
);

["b403", "d611", "b508", "b511"].forEach(
  (el, ind) =>
    (info.rooms[el] = {
      pageNo: 5,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind + 13
    })
);

["b627", "b628", "b630"].forEach(
  el =>
    (info.rooms[el] = {
      pageNo: 5,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: 17
    })
);

["f333", "f334"].forEach(
  (el, ind) =>
    (info.rooms[el] = {
      pageNo: 5,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind + 18
    })
);

["f332", "c401", "d408", "d111", "a618", "f328"].forEach(
  (el, ind) =>
    (info.rooms[el] = {
      pageNo: 6,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind
    })
);

["b506", "a713"].forEach(
  (el, ind) =>
    (info.rooms[el] = {
      pageNo: 6,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind + 9
    })
);

["b316", "f441", "f536", "f535", "f534", "f532", "f529"].forEach(
  (el, ind) =>
    (info.rooms[el] = {
      pageNo: 6,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J01",
      index: ind + 13
    })
);

[
  "f434",
  "f433",
  "f432",
  "f430",
  "f404",
  "f406",
  "b304",
  "b308",
  "b303"
].forEach(
  (el, ind) =>
    (info.rooms[el] = {
      pageNo: 7,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: "1J08",
      index: ind
    })
);

[
  101,
  102,
  103,
  104,
  105,
  106,
  107,
  108,
  109,
  110,
  201,
  202,
  203,
  204,
  205,
  206,
  207,
  208,
  209,
  210
].forEach(
  (el, ind) =>
    (info.rooms[`j1-${el}`] = {
      pageNo: 1,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: "2J01",
      index: ind
    })
);

["501", "509", "405", "406"].forEach(
  (el, ind) =>
    (info.rooms[`j1-${el}`] = {
      pageNo: 2,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: "2J01",
      index: ind
    })
);

[101, 102, 103, 104, 105, 106, 107, 201, 202].forEach(
  (el, ind) =>
    (info.rooms[`j3-${el}`] = {
      pageNo: 1,
      pageCount: 3,
      pageXiaoqu: 2,
      pageLhdm: "2J03",
      index: ind + 1
    })
);

[205, 206].forEach(
  (el, ind) =>
    (info.rooms[`j3-${el}`] = {
      pageNo: 1,
      pageCount: 3,
      pageXiaoqu: 2,
      pageLhdm: "2J03",
      index: ind + 11
    })
);

[210, 211, 212, 301, 302].forEach(
  (el, ind) =>
    (info.rooms[`j3-${el}`] = {
      pageNo: 1,
      pageCount: 3,
      pageXiaoqu: 2,
      pageLhdm: "2J03",
      index: ind + 15
    })
);

[303, 304, 305, 306].forEach(
  (el, ind) =>
    (info.rooms[`j3-${el}`] = {
      pageNo: 2,
      pageCount: 3,
      pageXiaoqu: 2,
      pageLhdm: "2J03",
      index: ind + 1
    })
);

[310, 311, 312, 401, 402].forEach(
  (el, ind) =>
    (info.rooms[`j3-${el}`] = {
      pageNo: 2,
      pageCount: 3,
      pageXiaoqu: 2,
      pageLhdm: "2J03",
      index: ind + 7
    })
);

[405, 406].forEach(
  (el, ind) =>
    (info.rooms[`j3-${el}`] = {
      pageNo: 2,
      pageCount: 3,
      pageXiaoqu: 2,
      pageLhdm: "2J03",
      index: ind + 13
    })
);

[410, 411, 412].forEach(
  (el, ind) =>
    (info.rooms[`j3-${el}`] = {
      pageNo: 2,
      pageCount: 3,
      pageXiaoqu: 2,
      pageLhdm: "2J03",
      index: ind + 17
    })
);

[
  101,
  102,
  103,
  104,
  105,
  106,
  201,
  202,
  203,
  204,
  205,
  206,
  301,
  302,
  303,
  304,
  305,
  306,
  401,
  402
].forEach(
  (el, ind) =>
    (info.rooms[`j4-${el}`] = {
      pageNo: 1,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: "2J04",
      index: ind
    })
);

[403, 404, 406].forEach(
  (el, ind) =>
    (info.rooms[`j4-${el}`] = {
      pageNo: 2,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: "2J04",
      index: ind
    })
);

[102, 103, 105, 202, 205, 302, 305, 402, 405].forEach(el => {
  delete info.rooms[`j4-${el}`];
});

[
  101,
  102,
  103,
  104,
  105,
  106,
  201,
  202,
  203,
  204,
  205,
  206,
  208,
  209,
  210,
  211,
  303,
  304,
  305,
  306
].forEach(
  (el, ind) =>
    (info.rooms[`j5-${el}`] = {
      pageNo: 1,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: "2J05",
      index: ind
    })
);

[308, 309, 403, 404, 405, 406, 408, 409].forEach(
  (el, ind) =>
    (info.rooms[`j5-${el}`] = {
      pageNo: 2,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: "2J05",
      index: ind
    })
);

console.log(JSON.stringify(info));
