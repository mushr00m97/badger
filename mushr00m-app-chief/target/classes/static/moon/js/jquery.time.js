(function($) {
	$
			.extend({
				// 日期格式
				dtfmt : {
					// 当前日期/日期格式[2014-01-01][2014-01-01 20:20:20]
					// 北京时间timeZone=8
					cur : function(isFull, timeZone) {
						var unixTime = Date.parse(new Date());
						if (typeof (timeZone) == 'number') {
							unixTime = parseInt(unixTime) + parseInt(timeZone)
									* 60 * 60 * 1000;
						}
						var time = new Date(unixTime);
						var ymdhms = "";
						ymdhms += time.getUTCFullYear() + "-";
						ymdhms += (time.getUTCMonth() + 1) + "-";
						ymdhms += time.getUTCDate();
						if (isFull === true) {
							ymdhms += " " + time.getUTCHours() + ":";
							ymdhms += time.getUTCMinutes() + ":";
							ymdhms += time.getUTCSeconds();
						}
						return ymdhms;
					}
				},
				// 时间戳格式/秒
				sfmt : {
					// 当前时间戳
					cur : function() {
						return Date.parse(new Date()) / 1000;
					},
					// 日期转时间戳/日期格式[2014-01-01 20:20:20]
					// 北京时间
					tounix : function(string) {
						var f = string.split(' ', 2);
						var d = (f[0] ? f[0] : '').split('-', 3);
						var t = (f[1] ? f[1] : '').split(':', 3);
						return (new Date(parseInt(d[0], 10) || null, (parseInt(
								d[1], 10) || 1) - 1,
								parseInt(d[2], 10) || null, parseInt(t[0], 10)
										|| null, parseInt(t[1], 10) || null,
								parseInt(t[2], 10) || null)).getTime() / 1000;
					},
					// 时间戳转日期/日期格式[2014-01-01][2014-01-01 20:20:20]
					// 北京时间timeZone=8
					todate : function(unixTime, isFull, timeZone) {
						if (typeof (timeZone) == 'number') {
							unixTime = parseInt(unixTime) + parseInt(timeZone)
									* 60 * 60;
						}
						var time = new Date(unixTime * 1000);
						var ymdhms = "";
						ymdhms += time.getUTCFullYear() + "-";
						ymdhms += (time.getUTCMonth() + 1) + "-";
						ymdhms += time.getUTCDate();
						if (isFull === true) {
							ymdhms += " " + time.getUTCHours() + ":";
							ymdhms += time.getUTCMinutes() + ":";
							ymdhms += time.getUTCSeconds();
						}
						return ymdhms;
					}
				},
				// 时间戳格式/毫秒
				msfmt : {
					// 当前时间戳
					cur : function() {
						return Date.parse(new Date());
					},
					// 日期转时间戳/日期格式[2014-01-01 20:20:20]
					// 北京时间
					tounix : function(string) {
						var f = string.split(' ', 2);
						var d = (f[0] ? f[0] : '').split('-', 3);
						var t = (f[1] ? f[1] : '').split(':', 3);
						return (new Date(parseInt(d[0], 10) || null, (parseInt(
								d[1], 10) || 1) - 1,
								parseInt(d[2], 10) || null, parseInt(t[0], 10)
										|| null, parseInt(t[1], 10) || null,
								parseInt(t[2], 10) || null)).getTime();
					},
					// 时间戳转日期/日期格式[2014-01-01][2014-01-01 20:20:20]
					// 北京时间timeZone=8
					todate : function(unixTime, isFull, timeZone) {
						if (typeof (timeZone) == 'number') {
							unixTime = parseInt(unixTime) + parseInt(timeZone)
									* 60 * 60 * 1000;
						}
						var time = new Date(unixTime);
						var ymdhms = "";
						ymdhms += time.getUTCFullYear() + "-";
						ymdhms += (time.getUTCMonth() + 1) + "-";
						ymdhms += time.getUTCDate();
						if (isFull === true) {
							ymdhms += " " + time.getUTCHours() + ":";
							ymdhms += time.getUTCMinutes() + ":";
							ymdhms += time.getUTCSeconds();
						}
						return ymdhms;
					}
				}
			});
})(jQuery);