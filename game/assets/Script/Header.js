cc.Class({
    extends: cc.Component,

    properties: {
        petSprite:{
            default:null,
            type:cc.Sprite,
            tooltip:'宠物头像'
        },
        goldLabel: {
            default:null,
            type:cc.Label,
            tooltip: "金币",
        },  
        speedLabel: {
            default:null,
            type:cc.Label,
            tooltip: "收益速度",
        },
    },

    // LIFE-CYCLE CALLBACKS:

    onLoad () {
        window.Header=this;
    },

    start () {

    },
    update (dt) {
    },
    /**
     * 设置金币
     * @param {*} totalGold 合计金币
     */
    setGold(totalGold){
        this.goldLabel.string=MergeManage.currencyUnit(totalGold);//设置金币
        var anim = this.goldLabel.node.getComponent(cc.Animation);//获取动画
        anim.play();//播放动画
    },
    /**
     * 初始化数据
     */
    init(levelImg,goldUsable,profitSpeed){
        if(levelImg){
            cc.loader.loadRes("image/sprite_atlas_grade", cc.SpriteAtlas, function (err, atlas) {
                if(userInfo){
                    var frame = atlas.getSpriteFrame('grade_'+ levelImg);
                    this.petSprite.spriteFrame = frame;
                }
            }.bind(this));
        }
        if(goldUsable){ 
            this.goldLabel.string=MergeManage.currencyUnit(goldUsable);//可用金币
        }
        if(profitSpeed){
            this.speedLabel.string=MergeManage.currencyUnit(profitSpeed) + " / s";//收益速度
        }
    }
  
});
