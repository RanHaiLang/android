cordova.define("com.jufanshare.testPlugin.TestPlugin", function(require, exports, module) {
var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'TestPlugin', 'coolMethod', [arg0]);
};

});
