//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    SearchBar: app.globalData.SearchBar,
    CustomBarHeight: app.globalData.CustomBarHeight,
    searchBarClass: 'cu-bar bg-white search fixed search-bar-top',
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
    gridCol: 5,
    skin: false,
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
    this.setData({
      msgList: [
        { url: "url", title: "多地首套房贷利率上浮 热点城市渐迎零折扣时代" },
        { url: "url", title: "交了20多年的国内漫游费将取消 你能省多少话费？" },
        { url: "url", title: "北大教工合唱团出国演出遇尴尬:被要求给他人伴唱" }]
    });
  },
  onShow: function () {
    var that = this;
    var query = wx.createSelectorQuery()//创建节点查询器 query
    query.select('#searchBar').boundingClientRect()//这段代码的意思是选择Id= the-id的节点，获取节点位置信息的查询请求
    query.exec(function (res) {
      //console.log(res[0].top); // #affix节点的上边界坐
      that.setData({
        menuTop: res[0].top
      })
    });
  },
  // 2.监听页面滚动距离scrollTop
  onPageScroll: function (e) {
    console.log(e.scrollTop);
    var that = this;
    // 3.当页面滚动距离scrollTop > menuTop菜单栏距离文档顶部的距离时，菜单栏固定定位
    if (e.scrollTop > that.data.menuTop) {
      that.setData({
        searchBarClass: 'cu-bar bg-white search fixed search-bar-not-top'
      })
    } else {
      that.setData({
        searchBarClass: 'cu-bar bg-white search fixed search-bar-top'
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
