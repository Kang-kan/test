import axios from 'axios'
import x2js from 'x2js';
class xmlObj {
    constructor() {
    }
    async run() {
        let s = this;
        s.hasDone = false;
        let x2 = new x2js();
        await axios.get('../itemConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.itemConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../equipConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.equipConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../chopConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.chopConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../designationConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.designationConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../chengjiu.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.chengjiuConfig = x2.xml2js(res.data).def;
        });


        await axios.get('../sysCostItemLevelUpConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysCostItemLevelUpConfig = x2.xml2js(res.data).def;
        });
        await axios.get('../sysAttributeContainerConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysAttributeContainerConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../sysCostItemAddExpConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysCostItemAddExpConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../sysCostItemLearnSkillConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysCostItemLearnSkillConfig = x2.xml2js(res.data).def;
        })
        await axios.get('../sysGridConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.sysGridConfig = x2.xml2js(res.data).def;
        });
        await axios.get('../rechargeConfig.xml').then(res => {
            if (!res || !res.data) {
                return;
            }
            s.rechargeConfig = x2.xml2js(res.data).def;
        });
        let obj1 = {};
        let obj2 = {};
        let itemArr = s.itemConfig.unit;

        let equipArr = s.equipConfig.unit;
        itemArr.forEach(el => {
            obj1[el._id] = el._name;
        })
        this.itemNameObj = obj1;
        equipArr.forEach(el => {
            obj2[el._id] = el._name;
        })
        this.equipNameObj = obj2;

        let obj3 = {};
        let chopArr = s.chopConfig.unit;
        chopArr.forEach(el => {
            obj3[el._id] = el._name;
        })
        this.chopNameObj = obj3;

        let obj4 = {};
        let designationArr = s.designationConfig.unit;
        designationArr.forEach(el => {
            obj4[el._id] = el._name;
        })
        this.designationObj = obj4;

        let obj5 = {};
        let chengjiuArr = s.chengjiuConfig.unit;
        chengjiuArr.forEach(el => {
            obj5[el._id] = el._desc;
        })
        this.chengjiuObj = obj5;
        s.hasDone = true;
    }
    /**
     * 返回成就描述
     * @param {*} id 
     */
    async getChenjiuDes(id) {
        let s = this;
        if (s.hasDone == true) {
            if (!s.chengjiuObj) {
                await s.run();
                s.getChenjiuDes(id);
            }
            let des = s.chengjiuObj[id];
            if (!des) {
                console.error('============成就表中找不到这个id======\n', id);
                return id;
            }
            return des;

        } else {
            await s.run();
            s.getChenjiuDes(id);
        }
    }
    /**
     * 返回神器名
     * @param {*} id 
     */
    async getChopName(id) {
        let s = this;
        if (s.hasDone == true) {
            if (!s.chopNameObj) {
                await s.run();
                s.getChopName(id);
            }
            let name = s.chopNameObj[id];
            if (!name) {
                console.error('============神器表中找不到这个id======\n', id);
                return id;
            }
            return name;

        } else {
            await s.run();
            s.getChopName(id);
        }
    }
    /**
     * 返回称号名
     * @param {*} id 
     */
    async getDesignationName(id) {
        let s = this;
        if (s.hasDone == true) {
            if (!s.designationObj) {
                await s.run();
                s.getDesignationName(id);
            }
            let name = s.designationObj[id];
            if (!name) {
                console.error('============称号表中找不到这个id======\n', id);
                return id;
            }
            return name;

        } else {
            await s.run();
            s.getDesignationName(id);
        }
    }
    /**获取bones奖励名 */
    async getName(id) {
        let s = this;
        if (s.hasDone == true) {
            if (Number(id) >= 15001 && Number(id) <= 20000) {
                return s.equipNameObj[id];
            } else {
                let name = s.itemNameObj[id];
                if (!name) {
                    console.error('============道具表中找不到这个id======\n', id);
                    return id;
                }
                return name;
            }
        } else {
            await s.run();
            s.getName(id);
        }
    }
    /**等级bonus字符串返回道具名道具数量 */
    async getBonus(bonus) {
        let s = this;
        if (s.hasDone == true) {
            if (!bonus) {
                return null;
            }
            if (Number(bonus.id) >= 15001 && Number(bonus.id) <= 20000) {
                return s.equipNameObj[bonus.id + ''] + ':' + bonus.count;
            } else {
                let name = s.itemNameObj[bonus.id + ''];
                if (!name) {
                    console.error('============道具表中找不到这个id======\n', bonus.id);
                    return bonus.id + ':' + bonus.count;
                }
                return name + ':' + bonus.count;
            }
        } else {
            await s.run();
            s.getBonus(bonus);
        }
    }
    /**等级reward字符串返回道具名道具数量 */
    async getReward(bonus) {
        let s = this;
        if (s.hasDone == true) {
            if(!bonus) {
                return null;
            }
            if (Number(bonus.id) >= 15001 && Number(bonus.id) <= 20000) {
                return s.equipNameObj[bonus.id + ''] + ':' + bonus.num;
            } else {
                let name = s.itemNameObj[bonus.id + ''];
                if (!name) {
                    console.error('============道具表中找不到这个id======\n', bonus.id);
                    return bonus.id + ':' + bonus.num;
                }
                return name + ':' + bonus.num;
            }
        } else {
            await s.run();
            s.getReward(bonus);
        }
    }
    /**根据id获取configName */
    getConfigName(config, id) {
        let item = null;
        config.unit.forEach(el => {
            if (el._id == id) {
                item = el;
                return;
            }
        });
        let name = item ? `${item._name}(${id})` : id
        return name;
    };
    /**根据config名 id 获取对应的字段名 */
    getConfigNameByIdConfigName(_name,id) {
        let item = null;
        s[_name].unit.forEach(el => {
            if (el._id == id) {
                item = el;
                return;
            }
        });
        let name = item ? `${item._name}(${id})` : id
        return name;
    }
    /**获取玩家充值商品名 */
    async getRechargeNameById(id) {
        let s = this;
        if (s.hasDone == true) {
            return s.getConfigName(s.rechargeConfig, id);
        } else {
            await s.run();
            s.getRechargeNameById(id);
        }
    }
    /**获取伙伴阵法守护兽日志id对应字段名 */
    async getHuoBanName(opValue, configId, levelId, childConfigId) {
        let s = this;
        let obj = {};
        if (s.hasDone == true) {
            switch (opValue) {
                case 0:
                    obj.configName = s.getConfigName(s.sysAttributeContainerConfig, configId);
                    obj.levelName = s.getConfigName(s.sysAttributeContainerConfig, levelId);
                    obj.opName = '激活时';
                    break;
                case 1:
                    obj.configName = s.getConfigName(s.sysAttributeContainerConfig, configId);
                    obj.levelName = s.getConfigName(s.sysAttributeContainerConfig, levelId);
                    obj.childConfigName = s.getConfigName(s.sysCostItemAddExpConfig, childConfigId);
                    obj.opName = '激活子功能,，圣魂-激活各部位装备';
                    break;
                case 2:
                    obj.configName = s.getConfigName(s.sysCostItemLevelUpConfig, configId);
                    obj.levelName = s.getConfigName(s.sysCostItemLevelUpConfig, levelId);
                    obj.opName = '提升属性等级, 伙伴升阶';
                    break;
                case 3:
                    obj.configName = s.getConfigName(s.sysCostItemAddExpConfig, configId);
                    obj.levelName = s.getConfigName(s.sysCostItemAddExpConfig, levelId);
                    obj.opName = '升星，圣魂';
                    break;
                case 4:
                    obj.configName = s.getConfigName(s.sysCostItemLearnSkillConfig, configId);
                    obj.levelName = s.getConfigName(s.sysCostItemLearnSkillConfig, levelId);
                    obj.opName = '消耗道具升级技能';
                    break;
                case 5:
                    obj.configName = s.getConfigName(s.sysGridConfig, configId);
                    obj.levelName = s.getConfigName(s.sysGridConfig, levelId);
                    obj.opName = '装备，伙伴、阵法';
                    break;
                case 6:
                    obj.configName = s.getConfigName(s.sysGridConfig, configId);
                    obj.levelName = s.getConfigName(s.sysGridConfig, levelId);
                    obj.opName = '格子内装备升级';
                    break;
            }
            return obj;
        } else {
            await s.run();
            s.getHuoBanName(id, opValue)
        }
    }
}
var xml = new xmlObj();
export default xml;
