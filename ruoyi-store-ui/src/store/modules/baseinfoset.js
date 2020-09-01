/**
 * Created by 魔金商城 on 2019/5/14.
 * 基础设置
 */

import {queryBaseInfoSet} from '@/api/basicinfo'

const baseinfoset = {
  state: {
    baseinfoset: false,
  },
  mutations: {
    SET_BASEINFO_SET: (state, baseinfoset) => {
      state.baseinfoset = baseinfoset;
    }
  },
  actions: {

    /**
     * 查询基础配置信息
     */
    getBaseInfoSet({commit}) {
      return new Promise((resolve, reject) => {
        queryBaseInfoSet().then(response => {
          commit('SET_BASEINFO_SET', response);
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }
  }

}

export default baseinfoset
