//Item脚本
cc.Class({
    extends: cc.Component,

    properties: {
        itemData:null,//item数据
        goldPrefab:{
            default:null,
            type:cc.Prefab,
            tooltip: "宠物生成金币预制体",
        },
    },
  
    // LIFE-CYCLE CALLBACKS:

    onLoad () {
    },

    start () {

    },

    update (dt) {},
    /**
     * 加载item数据（设置样式）
     * @param {*} data item数据
     * @param {*} parent 当前item的底座(父节点)
     */
    init(data,parent,isAnimation){
        var grade=parent.getChildByName('grade');//获取等级精灵
        if(data){
            this.itemData=data;
            this.loadItemSprite(isAnimation);
            grade.active=true;//显示背景
            grade.getChildByName('label').getComponent(cc.Label).string=this.itemData.gradeNum.toString();//设置等级数
        }else{
            grade.active=false;//显示背景
            grade.getChildByName('label').getComponent(cc.Label).string=""
        }
    },
    /**
     * 初始化item图片
     * @param isAnimation 是否播放动画
     */
    loadItemSprite(isAnimation){
        var sprite = this.node.getComponent(cc.Sprite);//item图片
        sprite.spriteFrame=null;//先清空原来的图片
        cc.loader.loadRes("image/sprite_atlas_merge", cc.SpriteAtlas, function (err, atlas) {
            if(this.itemData){
                var frame = atlas.getSpriteFrame(this.itemData.img);
                sprite.spriteFrame = frame;
            }
        }.bind(this));
        if(isAnimation){
            var anim = this.node.getComponent(cc.Animation);//获取动画
            anim.play();//播放动画
        }
    },
    // 是否正在拖动
    setDragState(isDraging){
        if(!isDraging){
             //console.log("没有拖动");
        }else{
             //console.log("正在拖动");
        }
    },
    /**
     * 播放动画
     */
    playAnimation(goldString){
        let gold = cc.instantiate(this.goldPrefab);//克隆宠物生成金币预制体节点
        gold.getComponent('GoldAnimation').init(goldString);//初始化物生成金币数据
        gold.parent=this.node;//设置父节点
        gold.y=-50;//设置位置
        var spawn = cc.spawn(cc.moveBy(1, 0, 150), cc.scaleTo(1, 1, 0));/// 三个参数 duration x y
        gold.runAction(spawn);//设置金币动画
        var anim = this.node.getComponent(cc.Animation);//获取动画
        anim.play();//播放动画
    },
   
});
