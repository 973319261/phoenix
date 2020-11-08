cc.Class({
    extends: cc.Component,

    properties: {
        petImg: {
            default:null,
            type:cc.Sprite,
            tooltip: "宠物图片",
        },
        petName: {
            default:null,
            type:cc.Label,
            tooltip: "宠物名称",
        },
        petGrade: {
            default:null,
            type:cc.Label,
            tooltip: "宠物等级",
        },
        petCreate: {
            default:null,
            type:cc.Sprite,
            tooltip: "创建按钮图片",
        },
        goldIcon: {
            default:null,
            type:cc.Sprite,
            tooltip: "创建按钮图表",
        },
        goldLabel: {
            default:null,
            type:cc.Label,
            tooltip: "金币",
        },
    },

    // LIFE-CYCLE CALLBACKS:

    onLoad () {
      
    },

    start () {

    },

    // update (dt) {},
    /**
     * 绑定宠物item数据
     * @param {*} data 
     */
    init(data){
        cc.loader.loadRes("image/sprite_atlas_merge", cc.SpriteAtlas, function (err, atlas) {
                var frame = atlas.getSpriteFrame('merge_' + data.petImg);
                this.petImg.spriteFrame = frame;
        }.bind(this));//加载宠物图片
        this.petName.string=data.petName;//名称
        this.petGrade.string='Lv:' + data.petLevel;//等级
        if(data.isCreate==0){//不可创建
            this.petCreate.node.color=cc.Color.GRAY;//设置创建按钮颜色（改变精灵颜色）
            this.goldLabel.node.active=false;//隐藏创建按钮金币label
            cc.loader.loadRes("image/bg_dialog_close", function (err, img) {
                this.goldIcon.spriteFrame=new cc.SpriteFrame(img);//改变创建按钮图标
             }.bind(this));//加载锁图标
        }else{//可创建
            let goldGrade = data.petGoldLevel>=MergeManage.maxGoldGrade?MergeManage.maxGoldGrade:data.petGoldLevel;//计算金币等级（最高maxGoldGrade）
            let gold = goldGrade * data.petGold;//重新计算金币
            this.goldLabel.string=MergeManage.currencyUnit(gold);
            this.petCreate.node.on(cc.Node.EventType.TOUCH_START,function(event){//绑定创建按钮点击事件
                MergeManage.addItemByGradeNum(data.petLevel,this.goldLabel);//创建宠物
            }.bind(this));
        }
        if(data.isLocked==0){//未解锁
             this.node.getChildByName("bg").color = cc.Color.GRAY;//设置背景颜色（改变精灵颜色）
             this.petImg.node.color=cc.Color.BLACK;//设置宠物图片颜色（改变精灵颜色）
        }else{//已解锁
        }
    }
});
