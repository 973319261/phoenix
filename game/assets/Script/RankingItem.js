cc.Class({
    extends: cc.Component,

    properties: {
        rankingNum: {
            default:null,
            type:cc.Label,
            tooltip: "排行号",
        },
        avatar: {
            default:null,
            type:cc.Sprite,
            tooltip: "用户头像",
        },
        nickName: {
            default:null,
            type:cc.Label,
            tooltip: "用户昵称",
        },
        level: {
            default:null,
            type:cc.Label,
            tooltip: "等级",
        },
        goldLabel: {
            default:null,
            type:cc.Label,
            tooltip: "金币",
        }
    },

    // LIFE-CYCLE CALLBACKS:

    onLoad () {
    },

    start () {

    },

    // update (dt) {},
    /**
     * 绑定排行item数据
     * @param {*} data 数据
     * @param rankingNum 排名号
     */
    init(data,rankingNum){
        if(rankingNum <= 3){//前三名
            this.rankingNum.node.color=cc.Color.RED;//
        }
        if(data.avatarUrl !=""){
            let url=HttpHelper.getServiceUrl()+'avatar/' + data.avatarUrl;//获取服务器图片
            cc.loader.load(url, function (err, img) {
              this.avatar.spriteFrame=new cc.SpriteFrame(img);//改变创建按钮图标
            }.bind(this));//加载用户头像
        }
       
        this.rankingNum.string=rankingNum;
        this.nickName.string=data.nickName.length>4?data.nickName.substring(0,4)+'...':data.nickName;
        this.level.string='Lv.'+data.level;
        this.goldLabel.string=MergeManage.currencyUnit(data.goldAll);
    }
});
