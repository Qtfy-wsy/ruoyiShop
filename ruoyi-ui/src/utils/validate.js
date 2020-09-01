/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase(str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase(str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets(str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
  if (typeof str === 'string' || str instanceof String) {
    return true
  }
  return false
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}

// 价格校验器
export const priceValidator = (rule, value, callback) => {
  if (!isMoney(value)) {
    callback(new Error('请输入合法金额'));
  } else {
    callback();
  }
}
// 0 - 100 整数校验器
export const zeroToHundred = (rule, value, callback) => {
  if (!isDigits(value)) {
    callback(new Error('请输入0-100之间的整数'));
  } else {
    if (value < 0 || value > 100) {
      callback(new Error('请输入0-100之间的整数'));
    } else {
      callback();
    }
  }
};


// 判断是否是金钱
export function isMoney(value) {
  return value && /^([0-9][0-9]*(\.[0-9]{1,2})?|0\.(?!0+$)[0-9]{1,2})$/.test(value);
}

// 整数校验器
export const digitsValidator = (rule, value, callback) => {
  if (!isDigits(value)) {
    callback(new Error('请输入非负整数'));
  } else {
    callback();
  }
}

//判断是否是整数
export function isDigits(value) {
  return value === 0 || (value && /^\d+$/.test(value));
}

// 特殊字符校验器
export const specstrValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('不能为空'));
  }
  if (!isSpecstr(value)) {
    callback(new Error('不能含有特殊字符'));
  } else {
    callback();
  }
};

// 判断是否含有特殊字符
export function isSpecstr(value) {
  return value && /^[^#$%&\\]*$/.test(value);
}

// 1-100整数校验器
export const oneToHundredDigitsValidator = (rule, value, callback) => {
  if (!isOneToHundredDigits(value)) {
    callback(new Error('请输入1-100之间的整数'));
  } else {
    callback();
  }
};

// 判断是否是1-100整数
export function isOneToHundredDigits(value) {
  return value && /^(1|([1-9]\d{0,1})|100)$/.test(value);
}

// 中文校验器
export const chineseValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('不能为空'));
  }
  if (isHasChinese(value)) {
    callback(new Error('不能含有中文'));
  } else {
    callback();
  }
};

// 校验身份证
export const checkIdCarNo = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('不能为空'));
  }
  if (!isIdCardNo(value)) {
    callback(new Error('身份证格式错误'));
  } else {
    callback();
  }
};

// 判断是否含有中文
export function isHasChinese(value) {
  return /.*[\u4e00-\u9fa5]+.*$/.test(value);
}

// 手机号码校验器
export const mobilePhoneValidator = (rule, value, callback) => {
  if (!isMobilePhone(value)) {
    callback(new Error('请输入正确的手机号码'));
  } else {
    callback();
  }
};

// 判断是否是手机号码
export function isMobilePhone(value) {
  return value && /^1\d{10}$/.test(value);
}

// 固定电话校验器（需带区号，例 010-123456)
export const telephoneValidator = (rule, value, callback) => {
  if (!isTelephone(value)) {
    callback(new Error('请输入正确的固定电话（需带区号，例 010-123456'));
  } else {
    callback();
  }
};

// 判断是否是固定电话（需带区号，例 010-123456)
export function isTelephone(value) {
  return value && /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/.test(value);
}

// 判断是否是邮件
export function isEmail(value) {
  return value && /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value);
}

// 身份证号码的验证规则
export function isIdCardNo(num) {
  // if (isNaN(num)) {alert("输入的不是数字！"); return false;}
  var len = num.length, re;
  if (len == 15)
    re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/);
  else if (len == 18)
    re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/);
  else {
    // alert("输入的数字位数不对。");
    return false;
  }
  var a = num.match(re);
  if (a != null) {
    if (len == 15) {
      var D = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
      var B = D.getYear() == a[3] && (D.getMonth() + 1) == a[4]
        && D.getDate() == a[5];
    } else {
      var D = new Date(a[3] + "/" + a[4] + "/" + a[5]);
      var B = D.getFullYear() == a[3] && (D.getMonth() + 1) == a[4]
        && D.getDate() == a[5];
    }
    if (D > new Date() || !B) {
      // alert("输入的身份证号 "+ a[0] +" 里出生日期不对。");
      return false;
    }
  }
  if (!re.test(num)) {
    //alert("身份证最后一位只能是数字和字母。");
    return false;
  }
  return true;
}

// 正整数校验器
export const plusIntegerValidator = (rule, value, callback) => {
  if (!integer(value)) {
    callback(new Error('请输入正整数'));
  } else {
    callback();
  }
}

// 正整数的判断
export function integer(value) {
  return value && /^[1-9][0-9]*$/.test(value);
}

// 整数校验器（包含负数）
export const integerValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('不能为空'));
  }
  if (!isInteger(value)) {
    callback(new Error('请输入整数（包含负数）'));
  } else {
    callback();
  }
};

// 判断是否是整数（包含负数）
export function isInteger(value) {
  return /^[-\+]?[1-9][0-9]*$/.test(value);
}

// 金额校验器（包含负数）
export const allMoneyValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('不能为空'));
  }
  if (!isAllMoney(value)) {
    callback(new Error('请输入正确的金额（包含负数）'));
  } else {
    callback();
  }
};

// 金额判断（包含负数）
export function isAllMoney(value) {
  return /^([-\+]?[0-9][0-9]*(\.[0-9]{1,2})?|0\.(?!0+$)[0-9]{1,2})$/.test(value);
}
