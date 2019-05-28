let info = {
  pageSize: 20,
  params: {},
  building: {
    x11: {
      pageNo: 1,
      pageCount: 2,
      pageXiaoqu: 1,
      pageLhdm: '1J01',
    },
    x12: { pageNo: 2, pageCount: 2, pageXiaoqu: 1, pageLhdm: '1J01' },
    x31: { pageNo: 1, pageCount: 2, pageXiaoqu: 1, pageLhdm: '1J03' },
    x32: { pageNo: 2, pageCount: 2, pageXiaoqu: 1, pageLhdm: '1J03' },
    x41: { pageNo: 1, pageCount: 1, pageXiaoqu: 1, pageLhdm: '1J04' },
    x73: { pageNo: 3, pageCount: 4, pageXiaoqu: 1, pageLhdm: '1J07' },
    x74: { pageNo: 4, pageCount: 4, pageXiaoqu: 1, pageLhdm: '1J07' },
    x81: { pageNo: 1, pageCount: 8, pageXiaoqu: 1, pageLhdm: '1J08' },
    x82: {
      pageNo: 2,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: '1J08',
    },
    x83: {
      pageNo: 3,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: '1J08',
    },
    x84: {
      pageNo: 4,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: '1J08',
    },
    x85: {
      pageNo: 5,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: '1J08',
    },
    x86: {
      pageNo: 6,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: '1J08',
    },
    x87: {
      pageNo: 7,
      pageCount: 8,
      pageXiaoqu: 1,
      pageLhdm: '1J08',
    },
    s11: {
      pageNo: 1,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: '2J01',
    },
    s12: {
      pageNo: 2,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: '2J01',
    },
    s31: {
      pageNo: 1,
      pageCount: 3,
      pageXiaoqu: 2,
      pageLhdm: '2J03',
    },
    s32: {
      pageNo: 2,
      pageCount: 3,
      pageXiaoqu: 2,
      pageLhdm: '2J03',
    },
    s41: {
      pageNo: 1,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: '2J04',
    },
    s42: {
      pageNo: 2,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: '2J04',
    },
    s51: {
      pageNo: 1,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: '2J05',
    },
    s52: {
      pageNo: 2,
      pageCount: 2,
      pageXiaoqu: 2,
      pageLhdm: '2J05',
    },
  },
}

;[
  '1101',
  '1102',
  '1103',
  '1106',
  '1108',
  '1201',
  '1202',
  '1203',
  '1204',
  '1205',
  '1207',
  '1209',
  '1212',
  '1213',
  '1215',
  '1301',
  '1302',
  '1303',
  '1304',
  '1305',
].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      name: el.toLowerCase(),
      index: ind,
      building: 'x11',
    }),
)
;[
  '1306',
  '1307',
  '1309',
  '1313',
  '1315',
  '1316',
  '1317',
  '1402',
  '1405',
  '1407',
  '1214',
].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      name: el.toLowerCase(),
      index: ind,
      building: 'x12',
    }),
)
;[
  '3202',
  '3203',
  '3207',
  '3209',
  '3215',
  '3218',
  '3219',
  '3301',
  '3306',
  '3307',
  '3311',
  '3312',
  '3317',
  '3401',
  '3402',
  '3403',
  '3404',
  '3405',
].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      name: el.toLowerCase(),
      building: 'x31',
      index: ind,
    }),
)
;['3302', '3104', '3108', '3407', '3115', '3105'].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      name: el.toLowerCase(),
      index: ind,
      building: 'x32',
    }),
)
;[
  '4314',
  '4401',
  '4402',
  '4403',
  '4404',
  '4406',
  '4407',
  '4408',
  '4409',
].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      name: el.toLowerCase(),
      index: ind,
      building: 'x41',
    }),
)
;[
  'ZM202',
  'ZM204',
  'zm301',
  'zm302',
  'zm402',
  'zm403',
  'zm404',
  'zb201',
  'zb303',
  'zb305',
  'zb306',
  'zb307',
  'zb401',
  'zb402',
  'zb403',
  'zn201',
  'zn203',
  'zn210',
  'zn301',
  'zn302',
].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      name: el.toLowerCase(),
      index: ind,
      building: 'x73',
    }),
)
;['zn303', 'zn305', 'zb107', 'zb101', 'zb103', 'zb105', 'z125', 'z119'].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      name: el.toLowerCase(),
      index: ind,
      building: 'x74',
    }),
)
;[
  'b119',
  'f404',
  'f406',
  'f430',
  'f432',
  'f433',
  'f434',
  'f529',
  'f532',
  'f534',
  'f535',
  'd601',
  'd609',
].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      name: el.toLowerCase(),
      index: ind + 7,
      building: 'x81',
    }),
)

info.params['d611'] = {
  name: 'd611',
  index: 0,
  building: 'x82',
}

info.params['d617'] = {
  name: 'd617',
  building: 'x82',
  index: 1,
}

info.params['d619'] = {
  name: 'd619',
  building: 'x82',
  index: 1,
}
;['b204', 'd611', 'd609'].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      building: 'x82',
      name: el.toLowerCase(),
      index: ind + 4,
    }),
)
;['d601', 'f328', 'f327', 'f332', 'f333', 'f330', 'd408', 'c403'].forEach(
  (el, ind) =>
    (info.params[el.toLowerCase()] = {
      building: 'x82',
      name: el.toLowerCase(),
      index: ind + 8,
    }),
)

info.params['d110'] = {
  building: 'x82',
  name: 'd110',
  index: 18,
}

info.params['d111'] = {
  building: 'x82',
  name: 'd111',
  index: 18,
}

info.params['b319'] = {
  building: 'x82',
  name: 'd319',
  index: 19,
}

info.params['d105'] = {
  building: 'x83',
  name: 'd105',
  index: 0,
}

info.params['d106'] = {
  building: 'x83',
  name: 'd106',
  index: 0,
}
;['b307', 'b406', 'b404'].forEach(
  (el, ind) =>
    (info.params[el] = {
      building: 'x83',
      name: el.toLowerCase(),
      index: ind + 2,
    }),
)
;[
  'e616',
  'c102',
  'c201',
  'd215',
  'd218',
  'd221',
  'd225',
  'e207',
  'f101',
  'f102',
  'f103',
  'f117',
  'f118',
  'f121',
  'f122',
  'f201',
  'f203',
  'f205',
  'f207',
  'f213',
].forEach(
  (el, ind) =>
    (info.params[el] = {
      building: 'x84',
      name: el.toLowerCase(),
      index: ind,
    }),
)
;[
  'f219',
  'f222',
  'f224',
  'f227',
  'f228',
  'g101',
  'g201',
  'a728',
  'b402',
  'b307',
  'd1008',
  'b406',
].forEach(
  (el, ind) =>
    (info.params[el] = {
      building: 'x85',
      name: el.toLowerCase(),
      index: ind,
    }),
)
;['b403', 'd611', 'b508', 'b511'].forEach(
  (el, ind) =>
    (info.params[el] = {
      building: 'x85',
      name: el.toLowerCase(),
      index: ind + 13,
    }),
)
;['b627', 'b628', 'b630'].forEach(
  el =>
    (info.params[el] = {
      building: 'x85',
      name: el.toLowerCase(),
      index: 17,
    }),
)
;['f333', 'f334'].forEach(
  (el, ind) =>
    (info.params[el] = {
      building: 'x85',
      name: el.toLowerCase(),
      index: ind + 18,
    }),
)
;['f332', 'c401', 'd408', 'd111', 'a618', 'f328'].forEach(
  (el, ind) =>
    (info.params[el] = {
      building: 'x86',
      name: el.toLowerCase(),
      index: ind,
    }),
)
;['b506', 'a713'].forEach(
  (el, ind) =>
    (info.params[el] = {
      building: 'x86',
      name: el.toLowerCase(),
      index: ind + 9,
    }),
)
;['b316', 'f441', 'f536', 'f535', 'f534', 'f532', 'f529'].forEach(
  (el, ind) =>
    (info.params[el] = {
      building: 'x86',
      name: el.toLowerCase(),
      index: ind + 13,
    }),
)
;[
  'f434',
  'f433',
  'f432',
  'f430',
  'f404',
  'f406',
  'b304',
  'b308',
  'b303',
].forEach(
  (el, ind) =>
    (info.params[el] = {
      name: 'x87',
      building: el.toLowerCase(),
      index: ind,
    }),
)
;[
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
  210,
].forEach(
  (el, ind) =>
    (info.params[`j1-${el}`] = {
      building: 's11',
      name: `j1-${el}`,
      index: ind,
    }),
)
;['501', '509', '405', '406'].forEach(
  (el, ind) =>
    (info.params[`j1-${el}`] = {
      building: 's12',
      name: `j1-${el}`,
      index: ind,
    }),
)
;[101, 102, 103, 104, 105, 106, 107, 201, 202].forEach(
  (el, ind) =>
    (info.params[`j3-${el}`] = {
      building: 's31',
      name: `j3-${el}`,
      index: ind + 1,
    }),
)
;[205, 206].forEach(
  (el, ind) =>
    (info.params[`j3-${el}`] = {
      building: 's31',
      name: `j3-${el}`,
      index: ind + 11,
    }),
)
;[210, 211, 212, 301, 302].forEach(
  (el, ind) =>
    (info.params[`j3-${el}`] = {
      building: 's31',
      name: `j3-${el}`,
      index: ind + 15,
    }),
)
;[303, 304, 305, 306].forEach(
  (el, ind) =>
    (info.params[`j3-${el}`] = {
      building: 's32',
      name: `j3-${el}`,
      index: ind + 1,
    }),
)
;[310, 311, 312, 401, 402].forEach(
  (el, ind) =>
    (info.params[`j3-${el}`] = {
      building: 's32',
      name: `j3-${el}`,
      index: ind + 7,
    }),
)
;[405, 406].forEach(
  (el, ind) =>
    (info.params[`j3-${el}`] = {
      building: 's32',
      name: `j3-${el}`,
      index: ind + 13,
    }),
)
;[410, 411, 412].forEach(
  (el, ind) =>
    (info.params[`j3-${el}`] = {
      building: 's32',
      name: `j3-${el}`,
      index: ind + 17,
    }),
)
;[
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
  402,
].forEach(
  (el, ind) =>
    (info.params[`j4-${el}`] = {
      building: 's41',
      name: `j4-${el}`,
      index: ind,
    }),
)
;[403, 404, 406].forEach(
  (el, ind) =>
    (info.params[`j4-${el}`] = {
      building: 's42',
      name: `j4-${el}`,
      index: ind,
    }),
)
;[102, 103, 105, 202, 205, 302, 305, 402, 405].forEach(el => {
  delete info.params[`j4-${el}`]
})
;[
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
  306,
].forEach(
  (el, ind) =>
    (info.params[`j5-${el}`] = {
      building: 's51',
      name: `j5-${el}`,
      index: ind,
    }),
)
;[308, 309, 403, 404, 405, 406, 408, 409].forEach(
  (el, ind) =>
    (info.params[`j5-${el}`] = {
      building: 's52',
      name: `j5-${el}`,
      index: ind,
    }),
)

console.log(JSON.stringify(info))
