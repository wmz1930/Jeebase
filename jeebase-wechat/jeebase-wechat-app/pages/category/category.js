const app = getApp()
Page({
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    Custom: app.globalData.Custom,
    TabCur: 0,
    VerticalNavTop: 0,
    categoryList: ['热门推荐',
      '手机数码',
      '大家电',
      '电脑办公',
      '厨卫大电',
      '生活家电',
      '食品酒水',
      '人群偏爱',
      '居家生活',
      '品质男装',
      '运动户外',
      '家装建材',
      '厨具水具',
      '二手优品',
      '苏宁国际',
      '苏宁生鲜',
      '奶粉尿裤',
      '中央空调',
      '智能数码',
      '钟表眼镜',
      '品质男鞋',
      '流行女装',
      '精品女鞋',
      '汽车生活',
      '珠宝首饰',
      '美妆护肤',
      '个人护理',
      '童装玩具',
      '皮具箱包',
      '内衣配饰',
      '图书音像',
      '苏宁极物',
      '礼品乐器',
      '艺术陶瓷',
      '医药馆',
      '特色馆'],
    iconList: [{
      icon: 'http://img.jeebase.com/phone.png',
      color: 'red',
      badge: 120,
      name: '手机'
    }, {
      icon: 'http://img.jeebase.com/market.png',
      color: 'orange',
      badge: 1,
      name: '超市'
    }, {
      icon: 'http://img.jeebase.com/jiadian.png',
      color: 'yellow',
      badge: 0,
      name: '生活家电'
    }, {
      icon: 'http://img.jeebase.com/shegnxian.png',
      color: 'olive',
      badge: 22,
      name: '生鲜'
    }, {
      icon: 'http://img.jeebase.com/muying.png',
      color: 'cyan',
      badge: 0,
      name: '母婴玩具'
    }, {
      icon: 'http://img.jeebase.com/qianggou.png',
      color: 'blue',
      badge: 0,
      name: '限时抢购'
    }, {
      icon: 'http://img.jeebase.com/zhuanqian.png',
      color: 'purple',
      badge: 0,
      name: '赚钱'
    }, {
      icon: 'http://img.jeebase.com/pintuan.png',
      color: 'mauve',
      badge: 0,
      name: '拼团'
    }, {
      icon: 'http://img.jeebase.com/jiadian2.png',
      color: 'purple',
      badge: 0,
      name: '家电'
    }, {
      icon: 'http://img.jeebase.com/qiandao.png',
      color: 'mauve',
      badge: 0,
      name: '签到有礼'
    }],
    qiangList: [{
      icon: 'http://img.jeebase.com/miaosha1.jpg',
      color: 'text-price text-red',
      badge: 120,
      name: '89'
    }, {
      icon: 'http://img.jeebase.com/miaosha2.jpg',
      color: 'text-price text-red',
      badge: 1,
      name: '15.8'
    }, {
      icon: 'http://img.jeebase.com/miaosha3.jpg',
      color: 'text-price text-red',
      badge: 0,
      name: '49'
    }],
    goodList: [{
      icon: 'http://img.jeebase.com/lining.jpg',
      color: 'text-red',
      badge: 120,
      name: 'LI-NING'
    }, {
      icon: 'http://img.jeebase.com/lankou.jpg',
      color: 'text-red',
      badge: 1,
      name: '兰蔻'
    }],
    tuanList: [{
      icon: 'http://img.jeebase.com/pintuan1.jpg',
      color: 'text-red',
      badge: 120,
      name: '2人拼30.8'
    }, {
      icon: 'http://img.jeebase.com/pintuan2.jpg',
      color: 'text-red',
      badge: 1,
      name: '2人拼7.9'
    }],
    gridCol: 3
  },
  tabSelect(e) {
    this.setData({
      TabCur: e.currentTarget.dataset.id,
      VerticalNavTop: (e.currentTarget.dataset.id - 1) * 50
    })
  },
  VerticalMain(e) {
    console.log(e);
    console.log(e.detail);
    console.log(this.data.TabCur);
    console.log(this.data.VerticalNavTop);
    

    let curId = '#main-id-' + this.data.TabCur;

    console.log(curId);

    const query = wx.createSelectorQuery()
    query.select(curId).boundingClientRect()
    query.selectViewport().scrollOffset()
    query.exec(function (res) {
      res[0].top // #the-id节点的上边界坐标
      console.log(res[0].top);
      res[1].scrollTop // 显示区域的竖直滚动位置
      console.log(res[0].scrollTop);
      console.log(res[0].height);
    })
  }
})
