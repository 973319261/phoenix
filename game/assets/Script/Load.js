const MergeManage = require('MergeManage');
const Header = require('Header');
const Navigation = require('Navigation');
cc.Class({
    extends: cc.Component,
    properties: {
        header: Header,
        mergeManage: MergeManage,
        navigation: Navigation,
    },

    // LIFE-CYCLE CALLBACKS:

    onLoad () {
        window.userId = MyJSInterface.getUserId();//获取用户ID
        window.topParent=cc.find("Canvas/bg");//查找根节点（主要是用来显示添加弹窗到该节点中）
        this.getGameInfo();//获取宠物信息
    },

    start () {

    },

    // update (dt) {},
    /**
     * 获取宠物信息
     */
    getGameInfo(){
        HttpHelper.request({
            url: 'getGameInfo',
            method: 'GET',
            type: 'json',
            data: {userId:userId},
            success: function(res) {
                var code=res.code;//状态码
                if(code==200){
                    var data=res.data;
                    if(data!=null){
                        window.userInfo=data.user;//把用户数据保存到全局变量
                        window.petInfo=data.petList;//把宠物列表数据保存到全局变量
                        window.rankingInfo=data.rankingList;//把批号榜数据保存到全局变量
                        this.header.init(userInfo.levelImg,userInfo.goldUsable,userInfo.profitSpeed);//加载头部数据
                        this.mergeManage.init();//加载宠物item数据
                        this.navigation.init();//加载导航(创建按钮)数据
                    }else{
                        MyJSInterface.showHint("数据异常")
                    }
                }
            }.bind(this),
            erro: function(err) {
                console.log(err)
            } 
        });
    },
});
