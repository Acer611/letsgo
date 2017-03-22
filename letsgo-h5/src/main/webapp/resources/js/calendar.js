/**
 * 移动webapp开发 日历组件
 * 可用于需要日历选择的场景
 *  - 日历范围选择
 *  - 节假日显示
 *  - 自由配置初始化生成多日历
 *  - 各种操作事件自由监听
 * @author samczhang@tencent.com
 * ----------------------------------------------
 * 对外调用接口及自定义事件
 * @method render 渲染日历
 * @method nextMonth 上一月
 * @method prevMonth 下一月
 * @method show 显示日历
 * @method hide 隐藏日历
 * @method setSelectDate 设置当前选中日期

 * @customEvent selectDate 选择日期时派发事件
 * @customEvent show 显示日历时派发事件
 * @customEvent hide 隐藏日历时派发事件
 */

( function( root, factory ) {
    if ( typeof define === 'function' ) {
        define( 'calendar', [ 'jqmobi' ], function($ ) {
            return factory( root, $ );
        } );
    } else {
        root.calendar = factory( root, root.$ );
    }
} )( window, function( root, $ ) {

    var util = {
        /**
         * 根据当前年月，计算下一个月的年月
         * @para year {int} eg: 2014 YYYY
         * @para month {int} eg: 12 MM/M
         * @return {object}
         */
        getNextMonthDate: function( year, month ) {
            if ( month > 12 ) {
                year = year + Math.floor( ( month - 1 ) / 12 );

                if ( month % 12 == 0 ) {
                    month = 12;
                } else {
                    month = month % 12;
                }
            }

            return {
                year: year,
                month: month
            }
        },

        /**
         * 处理小于10的数字前自动加0
         * @para num {num} int
         * return {string} '02'
         */
        formatNum: function( num ) {
            if ( num < 10 ) {
                num = '0' + num;
            }

            return num;
        },

        /**
         * 连接年月日，连接符为`-`
         * return {string} yyyy-mm-dd
         */
        joinDate: function( year, month, day ) {
            var formatNum = util.formatNum;

            return year + '-' + formatNum( month ) + '-' + formatNum( day );
        },

        /**
         * 将格式化后日期转化为数字，便于日期计算
         * @para date {string|date object} yyyy-mm-dd|日期对象
         * return {string} yyyymmdd
         */
        dateToNum: function( date ) {
            var rst = '';
            if ( typeof date == 'string' ) {
                rst = date.split( '-' ).join( '' );
            } else {
                rst = util.formatDate( date ).split( '-' ).join( '' );
            }

            return rst;
        },
        getNewDay:function(date, days) {
            if (days == undefined || days == '') {
                days = 1;
            }
            var date = new Date(date);
            date.setDate(date.getDate() + days);
            var month = date.getMonth() + 1;
            var day = date.getDate();
            return date.getFullYear() + '-' + util.getFormatDate(month) + '-' + util.getFormatDate(day);
        },
        // 日期月份/天的显示，如果是1位数，则在前面加上'0'
        getFormatDate:function(arg) {
        if (arg == undefined || arg == '') {
            return '';
        }

        var re = arg + '';
        if (re.length < 2) {
            re = '0' + re;
        }

        return re;
        },
        /**
         * 格式化日期对象
         * @para {date object} 日期对象
         * return {string} yyyy-mm-dd
         */
        formatDate: function( dateObj ) {
            var year = dateObj.getFullYear(),
                month = dateObj.getMonth() + 1,
                day = dateObj.getDate();

            return util.joinDate( year, month, day );
        },

        /**
         * 获取当前日期的下一天
         * @para date {string|date object} yyyy-mm-dd|日期对象
         * @para num {int} 获取指定日期之后的几天
         * return {string} yyyy-mm-dd
         */
        getNextDate: function( date, num ) {
            return util.formatDate( new Date( +new Date( date ) + num * 86400000 ) );
        },
    };

    var tpl = [
        '<div class="rl-month">',
        '<ul class="rl-month-title">',
        '<li>{%date%}</li>',
        '</ul>',
        '{%week%}',
        '<ul class="rl-month-day clearfix">',
        '{%day%}',
        '</ul>',
        '</div>'
    ].join( '' );

    var weekTpl = [
        '<ul class="week">',
        '<li>一</li>',
        '<li>二</li>',
        '<li>三</li>',
        '<li>四</li>',
        '<li>五</li>',
        '<li class="wk">六</li>',
        '<li class="wk">日</li>',
        '</ul>'
    ].join( '' );

    var holidaysMap = [
        {
            name: '今',
            date: [ util.formatDate( new Date() ) ]
        }
        /*{
         name: '明天',
         date: [ util.formatDate( new Date( +new Date() + 86400000 ) ) ]
         },
         {
         name: '后天',
         date: [ util.formatDate( new Date( +new Date() + 2 * 86400000 ) ) ]
         },
         {
         name: '圣诞节',
         date: [ '2014-12-25', '2015-12-25', '2016-12-25', '2017-12-25', '2018-12-25', '2019-12-25', '2020-12-25' ]
         },
         {
         name: '情人节',
         date: [ '2015-02-14', '2016-02-14', '2017-02-14', '2018-02-14', '2019-02-14', '2020-02-14' ]
         },
         {
         name: '元旦',
         date: [ '2015-01-01', '2016-01-01', '2017-01-01', '2018-01-01', '2019-01-01', '2020-01-01' ]
         },
         {
         name: '除夕',
         date: [ '2015-02-18', '2016-02-07', '2017-01-27', '2018-02-15', '2019-02-04', '2020-01-2' ]
         },
         {
         name: '春节',
         date: [ '2015-02-19', '2016-02-08', '2017-01-28', '2018-02-16', '2019-02-05', '2020-01-25' ]
         },
         {
         name: '元宵节',
         date: [ '2015-03-05', '2016-02-22', '2017-02-11', '2018-03-02', '2019-02-19', '2020-02-18' ]
         },
         {
         name: '清明节',
         date: [ '2015-04-05', '2016-04-04', '2017-04-04', '2018-04-05', '2019-04-05', '2020-04-04' ]
         },
         {
         name: '五一',
         date: [ '2015-05-01', '2016-05-01', '2017-05-01', '2018-05-01', '2019-05-01', '2020-05-01' ]
         },
         {
         name: '端午节',
         date: [ '2015-06-20', '2016-06-09', '2017-05-30', '2018-06-18', '2019-06-07', '2020-06-25' ]
         },
         {
         name: '中秋节',
         date: [ '2015-09-27', '2016-09-15', '2017-10-04', '2018-09-24', '2019-09-13', '2020-10-01' ]
         },
         {
         name: '国庆节',
         date: [ '2015-10-01', '2016-10-01', '2017-10-01', '2018-10-01', '2019-10-01', '2020-10-01' ]
         }*/
    ];

    var calendar = function( config ) {
        this.defaultConfig = {
            /**
             * 日历外层容器ID
             * type {string|jq object} id字符串或jq对象
             */
            el: '#calendar',

            /**
             * 初始化日历显示个数
             */
            count: 5,

            /**
             * 用于初始化日历开始年月
             * type {date object} 日期对象
             */
            date: new Date(),

            /**
             * 日期最小值，当小于此日期时日期不可点
             * type {date object} 日期对象
             */
            minDate: null,

            /**
             * 日期最大值，当大于此日期时日期不可点
             * type {date object} 日期对象
             */
            maxDate: null,  //日期对象

            /**
             * 初始化当前选中日期，用于高亮显示
             * type {date object} 日期对象
             */
            selectorangeDate: [],
            selectblueDate: [],
            /**
             * 选中日期时日期下方文案
             * type {string}
             */
            selectDateName: '空闲',

            /**
             * 是否显示节假日
             * type {boolean}
             */
            isShowHoliday: true,

            /**
             * 在日历中是否显示星期
             * type {boolean}
             */
            isShowWeek: true
        };

        this.config = $.extend( {}, this.defaultConfig, config || {} );
        this.el = ( typeof this.config.el === 'string' ) ? $( this.config.el ) : this.config.el;

        this.init.call( this );
    };

    $.extend( calendar.prototype, {
        init: function() {
            this._initDate();
            this.render( this.config.date );
            this._initEvent();
        },

        _initDate: function() {
            var me = this,
                config = this.config,
                dateObj = config.date,
                dateToNum = util.dateToNum;

            //初始化日历年月
            this.year = dateObj.getFullYear();
            this.month = dateObj.getMonth() + 1;

            this.minDate = config.minDate;
            this.maxDate = config.maxDate;
            this.selectorangeDate = config.selectorangeDate;
            this.selectblueDate = config.selectblueDate;
            //上下月切换步长，根据初始化日历个数决定
            this.step = config.count;
        },

        /**
         * 根据日期对象渲染日历
         * @para {date object} 日期对象
         */
        render: function( date ) {
            var me = this,
                config = this.config,
                date = date || config.date,
                year = date.getFullYear(),
                month = date.getMonth() + 1,
                tmpTplArr = [];

            for ( var i = 0; i < config.count; i++ ) {
                var curMonth = month + i,
                    curDate = util.getNextMonthDate( year, curMonth ),
                    dateStr = curDate.year + '年' + util.formatNum( curDate.month ) + '月',
                    dayList = this._getDayList( curDate.year, curDate.month ),
                    week = '';

                if ( config.isShowWeek ) {
                    week = weekTpl;
                }

                var curTpl = tpl.replace( '{%date%}', dateStr )
                    .replace( '{%week%}', week )
                    .replace( '{%day%}', dayList );

                tmpTplArr.push( curTpl );
            }

            this.el.html( tmpTplArr.join( '' ) );
            /*for(var k=0;k<this.selectorangeDate.length;k++){
             this.setSelectDate( this.selectorangeDate[k] );
             }*/
        },

        _initEvent: function() {
            var me = this,
                config = this.config;

            this.el.delegate( 'ul.rl-month-day li', 'click', function( event ) {
                var curItem = $( this ),
                    date = curItem.data( 'date' ),
                    dateName = $( curItem.find( 'i' )[ 1 ] ).text();

                //更新当前选中日期YYYY-MM-DD
                //me.selectorangeDate = date;

                //if ( !curItem.hasClass( 'iv' ) ) {
                    $.trigger( me, 'afterSelectDate', [ {
                        date: date,
                        dateName: dateName,
                        curItem: curItem
                    } ] );
               // }
            });
        },

        /**
         * 根据当前年月，获取日期列表html字体串
         * @para year {int} eg: 2014 YYYY
         * @para month {int} eg: 12 MM/M
         * @return {string}
         */
        _getDayList: function( year, month ) {
            var me = this,
                config = this.config,
                days = new Date( year, month, 0 ).getDate(),
                firstDay = Math.abs( new Date( year, month - 1, 1 ).getDay() ),
                dateToNum = util.dateToNum,
                joinDate = util.joinDate,
                getNewDay = util.getNewDay,
                tmpEptArr = [],
                tmpDayDataArr = [],
                tmpDayTplArr = [];

            //如果是星期天
            if ( firstDay == 0 ) {
                firstDay = 7;
            }

            //插入空白字符
            for ( var i = 0; i < firstDay - 1; i++ ) {
                tmpEptArr.push( '<li class="ept"><p></p></li>' );
            }

            for ( var i = 0; i < days; i++ ) {
                var day = i + 1,
                    date = joinDate( year, month, day ),
                    wkDay = new Date( date ).getDay(),
                    dateNum = dateToNum( date ),
                    jrClassName = 'dl jr';

                //默认不做任何处理
                tmpDayDataArr.push( {
                    class: '',
                    date: date,
                    day: day,
                    name: ''
                } );

                //双休单独标注出
                if ( wkDay == 6 || wkDay == 0 ) {
                    jrClassName = 'dl jr wk';
                    tmpDayDataArr[ i ][ 'class' ] = 'wk';
                }

                //不在指定范围内的日期置灰
                if ( ( me.minDate && dateNum < dateToNum( me.minDate ) ) ||
                    ( me.maxDate && dateNum > dateToNum( me.maxDate ) )
                ) {
                    jrClassName = 'dl iv';
                    tmpDayDataArr[ i ][ 'class' ] = 'iv';
                }

                //节假日处理
                if ( config.isShowHoliday ) {
                    for ( var k = 0, hlen = holidaysMap.length; k < hlen; k++ ) {
                        var name = holidaysMap[ k ][ 'name' ],
                            dateArr = holidaysMap[ k ][ 'date' ];

                        for ( var j = 0, len = dateArr.length; j < len; j++ ) {
                            var item = dateArr[ j ];

                            if ( dateNum == dateToNum( item ) ) {
                                tmpDayDataArr[ i ][ 'class' ] = jrClassName;
                                tmpDayDataArr[ i ][ 'name' ] = name;
                                break;
                            }
                        }
                    }
                }

                //初始化当前选中日期状态
                if ( config.selectorangeDate ) {
                    for(var arrselect=0;arrselect<me.selectorangeDate.length;arrselect++){
                        for(var m=0;m<me.selectorangeDate[arrselect].length;m++){
                            if ( dateNum == dateToNum( me.selectorangeDate[arrselect][m] ) ) {
                                if ( dateNum == dateToNum( me.selectorangeDate[arrselect][0] ) ) {
                                    tmpDayDataArr[ i ][ 'class' ] += ' active-orange-bg first';
                                }
                                else if ( dateNum == dateToNum( me.selectorangeDate[arrselect][me.selectorangeDate[arrselect].length-1] ) ) {
                                    tmpDayDataArr[ i ][ 'class' ] += ' active-orange-bg last';
                                }
                                else if(dateToNum( me.selectorangeDate[arrselect][0] )!=dateToNum( me.selectorangeDate[arrselect][me.selectorangeDate[arrselect].length-1] ) ){
                                    tmpDayDataArr[ i ][ 'class' ] += ' active-orange-bg';
                                }
                            }
                        }
                    }
                }
                //初始化当前选中日期状态
                if ( config.selectblueDate ) {
                    for(var arrselect=0;arrselect<me.selectblueDate.length;arrselect++){
                            for (var m = 0; m < me.selectblueDate[arrselect].length; m++) {
                                if (dateNum == dateToNum(me.selectblueDate[arrselect][m])) {
                                    if (dateNum == dateToNum(me.selectblueDate[arrselect][0])&&dateNum!=dateToNum(me.selectblueDate[arrselect][me.selectblueDate[arrselect].length - 1])) {
                                        tmpDayDataArr[i]['class'] += ' active-blue-bg first';
                                    }
                                    else if (dateNum == dateToNum(me.selectblueDate[arrselect][me.selectblueDate[arrselect].length - 1])&&dateNum!=dateToNum(me.selectblueDate[arrselect][0])) {
                                        tmpDayDataArr[i]['class'] += ' active-blue-bg last';
                                    }
                                    else if(dateToNum(me.selectblueDate[arrselect][0])==dateToNum(me.selectblueDate[arrselect][me.selectblueDate[arrselect].length - 1])) {
                                        tmpDayDataArr[i]['class'] += ' active-blue-bg first last';
                                    }else{
                                        tmpDayDataArr[i]['class'] += ' active-blue-bg';
                                    }
                                }
                            }
                    }
                }
            }

            //生成日期模板字符串
            for ( var i = 0, len = tmpDayDataArr.length; i < len; i++ ) {
                var item = tmpDayDataArr[ i ];
                if(item.class.indexOf('active-orange-bg')>0&&item.class.indexOf('active-blue-bg')<0&&item.class.indexOf('first')>0){
                    tmpDayTplArr.push(
                        '<li class="' + item.class + '" data-date="' + item.date + '">' +
                        '<b class="b-left"></b>'+
                        '<p class="active-orange">' + item.day + '<span>出发</span></p>' +
                        '</li>'
                    );
                }else if(item.class.indexOf('active-orange-bg')>0&&item.class.indexOf('last')>0){
                    tmpDayTplArr.push(
                        '<li class="' + item.class + '" data-date="' + item.date + '">' +
                        '<b class="b-right"></b>'+
                        '<p class="active-orange">' + item.day + '<span>返回</span></p>' +
                        '</li>'
                    );
                }else if(item.class.indexOf('active-blue-bg')>0&&item.class.indexOf('first')>0&&item.class.indexOf('last')<0){
                    tmpDayTplArr.push(
                        '<li class="' + item.class + '" data-date="' + item.date + '">' +
                        '<b class="b-left"></b>'+
                        '<p class="active-blue">' + item.day + '<span>忙</span></p>' +
                        '</li>'
                    );
                }else if(item.class.indexOf('active-blue-bg')>0&&item.class.indexOf('last')>0&&item.class.indexOf('first')<0){
                    tmpDayTplArr.push(
                        '<li class="' + item.class + '" data-date="' + item.date + '">' +
                        '<b class="b-right"></b>'+
                        '<p class="active-blue">' + item.day + '<span>忙</span></p>' +
                        '</li>'
                    );
                }else if(item.class.indexOf('active-blue-bg')>0&&item.class.indexOf('first')>0&&item.class.indexOf('last')>0){
                    tmpDayTplArr.push(
                        '<li class="' + item.class + '" data-date="' + item.date + '">' +
                        '<b class="b-right"></b>'+
                        '<b class="b-left"></b>'+
                        '<p class="active-blue">' + item.day + '<span>忙</span></p>' +
                        '</li>'
                    );
                }else if(item.name){
                    tmpDayTplArr.push(
                        '<li class="' + item.class + '" data-date="' + item.date + '">' +
                        '<p>' + item.name + '</p>' +
                        '</li>'
                    );
                }else{
                    tmpDayTplArr.push(
                        '<li class="' + item.class + '" data-date="' + item.date + '">' +
                        '<p>' + item.day + '</p>' +
                        '</li>'
                    );
                }
            }

            return tmpEptArr.concat( tmpDayTplArr ).join( '' );
        },

        /**
         * 设置选中日期格式
         * @para {date object|date string} YYYY-MM-DD
         */
        setSelectDate: function( date ) {
            var me = this,
                config = this.config,
                date = ( typeof date == 'string' ) ? date : util.formatDate( date ),
                dateNum = util.dateToNum( date ),
                getNewDay = util.getNewDay,
                lastSltItem = this.el.find( 'li.cur' ),
                curSltItem = $( this.el[ 0 ].querySelector( 'li[data-date="' + date + '"]' ) );

            //先移到上次选中日期高亮
            /*if ( lastSltItem.length ) {
             var lastDateNameEl = $( lastSltItem.find( 'i' )[ 1 ] );

             lastSltItem.removeClass( 'cur' );
             if ( !lastSltItem.hasClass( 'jr' ) ) {
             lastSltItem.removeClass( 'dl' );
             lastDateNameEl.text( '' );
             }
             }

             //添加当前选中日期高亮
             if ( curSltItem.length ) {
             var curDateNameEl = $( curSltItem.find( 'i' )[ 1 ] );

             curSltItem.addClass( 'cur' );
             if ( !curSltItem.hasClass( 'jr' ) ) {
             curSltItem.addClass( 'dl' );
             curDateNameEl.text( config.selectDateName );
             }
             }*/
        },
        /**
         *设置空闲
         */
        setSelectFreeDate: function( date) {
            var me = this,
                config = this.config,
                getNewDay = util.getNewDay,
                lastSltItem = this.el.find( 'li.cur' ),
                prevDate = getNewDay(date[0],-1),
                prevprevDate = getNewDay(date[0],-2),
                nextDate = getNewDay(date[0],1),
                lastnextDate = getNewDay(date[date.length-1],1),
                //curSltItem = $( this.el[ 0 ].querySelector( 'li[data-date="' + date + '"]' ) );
                curSltItemfirst=$( this.el[ 0 ].querySelector( 'li[data-date="' + date[0] + '"]' )),
                curSltItemlast=$( this.el[ 0 ].querySelector( 'li[data-date="' + date[date.length-1] + '"]' )),
                curSltItemprev=$( this.el[ 0 ].querySelector( 'li[data-date="' + prevDate + '"]' )),
                curSltItemprevprev=$( this.el[ 0 ].querySelector( 'li[data-date="' + prevprevDate + '"]' )),
                curSltItemnext=$( this.el[ 0 ].querySelector( 'li[data-date="' + nextDate + '"]' )),
                curSltItemlastnext=$( this.el[ 0 ].querySelector( 'li[data-date="' + lastnextDate + '"]' ) );
            //添加当前选中日期高亮
            if ( curSltItemfirst.length ) {

                var curDateNameEl = $( curSltItemfirst.find( 'p' )[ 1 ] );
                if(curSltItemprev.hasClass( 'active-blue-bg' )){
                    //curSltItemfirst.removeClass('active-blue-bg');
                    curSltItemprev.addClass( 'first' );
                    curSltItemprev.find( 'p' ).addClass( 'active-blue' );
                    curSltItemprev.prepend('<b class="b-right"></b>');
                    if( curSltItemprev.find( 'p').find("span").length>0){

                    }else{
                        curSltItemprev.find( 'p' ).append("<span>忙</span>");
                    }
                    curSltItemprev.addClass('active-blue-bg');
                }
            }
            //添加当前选中日期高亮
            if ( curSltItemlastnext.length ) {
                var curDateNameEl = $( curSltItemlast.find( 'p' )[ 1 ] );
               if(curSltItemlastnext.hasClass( 'active-blue-bg' )){
                   curSltItemlastnext.addClass('active-blue-bg');
                   curSltItemlastnext.addClass( 'last' );
                   curSltItemlastnext.prepend('<b class="b-left"></b>');
                   curSltItemlastnext.find( 'p' ).addClass( 'active-blue' );
                   if( curSltItemlastnext.find( 'p').find("span").length>0){

                   }else{
                       curSltItemlastnext.find( 'p' ).append("<span>忙</span>");
                   }

                }
            }
            //if(nextDate==lastnextDate&&curSltItemprev.hasClass( 'active-blue-bg' )&&!curSltItemprevprev.hasClass( 'active-blue-bg' )){
            //    curSltItemprev.removeClass('active-blue-bg');
            //    curSltItemprev.removeClass( 'first' );
            //    curSltItemprev.find("b").remove();
            //    curSltItemprev.find( 'p' ).removeClass( 'active-blue' );
            //    if( curSltItemprev.find( 'p').find("span").length>0){
            //
            //    }else{
            //        curSltItemprev.find( 'p' ).find("span").remove();
            //    }
            //}
            for(var d=0;d<date.length;d++){
                if( $( this.el[ 0 ].querySelector( 'li[data-date="' + date[d] + '"]' )).length>0) {
                    if(!$( this.el[ 0 ].querySelector( 'li[data-date="' + date[d] + '"]' )).hasClass("active-orange-bg")) {
                        $(this.el[0].querySelector('li[data-date="' + date[d] + '"]')).removeClass('first');
                        $(this.el[0].querySelector('li[data-date="' + date[d] + '"]')).removeClass('last');
                        $(this.el[0].querySelector('li[data-date="' + date[d] + '"]')).find('p').removeClass('active-blue');
                        $(this.el[0].querySelector('li[data-date="' + date[d] + '"]')).find("b").remove();
                        $(this.el[0].querySelector('li[data-date="' + date[d] + '"]')).find('p').find("span").remove();
                        $(this.el[0].querySelector('li[data-date="' + date[d] + '"]')).removeClass('active-blue-bg');
                    }
                }
            }
        },
        /**
         * 设置忙碌选中日期格式
         * @para {date object|date string} YYYY-MM-DD
         */
        setSelectBusyDate: function( date) {
           // console.log(date)
            var me = this,
                config = this.config,
                getNewDay = util.getNewDay,
                lastSltItem = this.el.find( 'li.cur' );
            var dateToNum = util.dateToNum;
            //date = ( typeof date == 'string' ) ? date : util.formatDate( date ),
            //dateNum = util.dateToNum( date ),
            //for(var s=0;s<date.length;s++) {
            //    var prevDate = getNewDay(date[s][0], -1),
            //        prevprevDate = getNewDay(date[s][0], -2),
            //        nextDate = getNewDay(date[s][0], 1),
            //        lastnextDate = getNewDay(date[s][date[s].length - 1], 1),
            //        lastnextnextDate = getNewDay(date[s][date[s].length - 1], 2),
            //        curSltItemfirst = $(this.el[0].querySelector('li[data-date="' + date[s][0] + '"]')),
            //        curSltItemlast = $(this.el[0].querySelector('li[data-date="' + date[s][date[s].length - 1] + '"]')),
            //        curSltItemprev = $(this.el[0].querySelector('li[data-date="' + prevDate + '"]')),
            //        curSltItemprevprev = $(this.el[0].querySelector('li[data-date="' + prevprevDate + '"]')),
            //        curSltItemnext = $(this.el[0].querySelector('li[data-date="' + nextDate + '"]')),
            //        curSltItemlastnext = $(this.el[0].querySelector('li[data-date="' + lastnextDate + '"]')),
            //        curSltItemlastnextnext = $(this.el[0].querySelector('li[data-date="' + lastnextnextDate + '"]'));
            //    //添加当前选中日期高亮
            //    if (curSltItemfirst.length) {
            //        curSltItemfirst.addClass('active-blue-bg');
            //        var curDateNameEl = $(curSltItemfirst.find('p')[1]);
            //        if (curSltItemprev.hasClass('active-blue-bg')) {
            //            //curSltItemfirst.removeClass('active-blue-bg');
            //            curSltItemprev.removeClass('first');
            //            curSltItemprev.find('p').removeClass('active-blue');
            //            curSltItemprev.find("b").remove();
            //            curSltItemprev.find('p').find("span").remove();
            //        }
            //        if (curSltItemnext.hasClass('active-blue-bg')) {
            //            //curSltItemfirst.removeClass('active-blue-bg');
            //            curSltItemnext.removeClass('first');
            //            curSltItemnext.find('p').removeClass('active-blue');
            //            curSltItemnext.find("b").remove();
            //            curSltItemnext.find('p').find("span").remove();
            //            //curSltItemlast.addClass('active-blue-bg');
            //        }
            //        if (!curSltItemprev.hasClass('active-blue-bg')) {
            //            curSltItemfirst.addClass('first');
            //            if(curSltItemfirst.find(".b-left").length>0) {
            //                alert("dddd")
            //                curSltItemfirst.prepend('<b class="b-left"></b>');
            //            }
            //            curSltItemfirst.find('p').addClass('active-blue');
            //            if (curSltItemfirst.find('p').find("span").length > 0) {
            //
            //            } else {
            //                curSltItemfirst.find('p').append("<span>忙</span>");
            //            }
            //
            //        }
            //
            //       // if (date.length == 1) {
            //            //curSltItemfirst.addClass( 'first' );
            //            //curSltItemfirst.prepend('<b class="b-left"></b>');
            //            //curSltItemfirst.find( 'p' ).addClass( 'active-blue' );
            //            //curSltItemfirst.find( 'p' ).append("<span>忙</span>");
            //       // }
            //    }
            //    //添加当前选中日期高亮
            //    if (curSltItemlast.length) {
            //        var curDateNameEl = $(curSltItemlast.find('p')[1]);
            //        curSltItemlast.addClass('active-blue-bg');
            //        if (curSltItemlastnext.hasClass('active-blue-bg')) {
            //            //curSltItemfirst.removeClass('active-blue-bg');
            //            curSltItemlastnext.removeClass('first');
            //            curSltItemlastnext.find('p').removeClass('active-blue');
            //            curSltItemlastnext.find("b").remove();
            //            curSltItemlastnext.find('p').find("span").remove();
            //
            //        } else if (!curSltItemlastnext.hasClass('active-blue-bg')) {
            //            curSltItemlast.addClass('last');
            //            if(curSltItemlast.find(".b-right").length>0) {
            //                alert("tttt")
            //                curSltItemlast.prepend('<b class="b-right"></b>');
            //            }
            //            curSltItemlast.find('p').addClass('active-blue');
            //            if (curSltItemlast.find('p').find("span").length > 0) {
            //
            //            } else {
            //                curSltItemlast.find('p').append("<span>忙</span>");
            //            }
            //
            //        }
            //        /*else if(!curSltItemlast.hasClass( 'active-blue-bg' )){
            //         curSltItemlast.addClass( 'last' );
            //         curSltItemlast.prepend('<b class="b-right"></b>');
            //         curSltItemlast.find( 'p' ).addClass( 'active-blue' );
            //         if( curSltItemlast.find( 'p').find("span").length>0){
            //
            //         }else{
            //         curSltItemlast.find( 'p' ).append("<span>忙</span>");
            //         }
            //         }*/
            //    }
            //    if (nextDate == lastnextDate && curSltItemprev.hasClass('active-blue-bg') && !curSltItemprevprev.hasClass('active-blue-bg')) {
            //        curSltItemprev.addClass('active-blue-bg');
            //        curSltItemprev.addClass('first');
            //        curSltItemprev.prepend('<b class="b-left"></b>');
            //        curSltItemprev.find('p').addClass('active-blue');
            //        if (curSltItemprev.find('p').find("span").length > 0) {
            //
            //        } else {
            //            curSltItemprev.find('p').append("<span>忙</span>");
            //        }
            //    }
            //    for (var d = 1; d < date[s].length - 1; d++) {
            //        if ($(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).length > 0) {
            //            if ($(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).hasClass("first")) {
            //                $(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).find("b").remove();
            //                $(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).find("p").removeClass('active-blue');
            //                $(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).find("p").find("span").remove();
            //            } else if ($(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).hasClass("last")) {
            //                $(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).find("b").remove();
            //                $(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).find("p").removeClass('active-blue');
            //                $(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).find("p").find("span").remove();
            //            }else {
            //                $(this.el[0].querySelector('li[data-date="' + date[s][d] + '"]')).addClass('active-blue-bg');
            //            }
            //        }
            //    }
           // }
            $(".rl-month").each(function(i){
                $(this).find("li").each(function(){
                    if ($(this).hasClass('active-blue-bg')) {
                        $(this).removeClass("active-blue-bg");
                        $(this).removeClass('first');
                        $(this).removeClass('last');
                        $(this).find('p').removeClass('active-blue');
                        $(this).find("b").remove();
                        $(this).find('p').find("span").remove();
                        if($(this).find(".b-right").length>0) {
                            $(this).find('b').remove();
                        }
                        if($(this).find(".b-left").length>0) {
                            $(this).find('b').remove();
                        }
                    }
                    //初始化当前选中日期状态
                    if ( date ) {
                        for(var arrselect=0;arrselect<date.length;arrselect++){
                            for (var m = 0; m < date[arrselect].length; m++) {
                                if ($(this).attr("data-date") == date[arrselect][m]) {
                                    if ($(this).attr("data-date") == date[arrselect][0]&&$(this).attr("data-date")!=date[arrselect][date[arrselect].length - 1]) {
                                        $(this).addClass(' active-blue-bg').addClass('first');
                                    }
                                    else if ($(this).attr("data-date") == date[arrselect][date[arrselect].length - 1]&&$(this).attr("data-date")!=date[arrselect][0]) {
                                        $(this).addClass(' active-blue-bg').addClass('last');
                                    }
                                    else if(date[arrselect][0]==date[arrselect][date[arrselect].length - 1]) {
                                        $(this).addClass(' active-blue-bg').addClass('last').addClass('first');
                                    }else{
                                        $(this).addClass(' active-blue-bg');
                                    }
                                }
                            }
                        }
                    }
                    if($(this).attr("class")){
                        var classAttr= $(this).attr("class");
                       if(classAttr.indexOf('active-blue-bg')>=0&&classAttr.indexOf('first')>=0&&classAttr.indexOf('last')<0){
                           $(this).prepend('<b class="b-left"></b>');
                           $(this).find('p').addClass('active-blue');
                           $(this).find( 'p' ).append("<span>忙</span>");
                        }else if(classAttr.indexOf('active-blue-bg')>=0&&classAttr.indexOf('last')>=0&&classAttr.indexOf('first')<0){
                           $(this).prepend('<b class="b-right"></b>');
                           $(this).find('p').addClass('active-blue');
                           $(this).find( 'p' ).append("<span>忙</span>");
                        }else if(classAttr.indexOf('active-blue-bg')>=0&&classAttr.indexOf('first')>=0&&classAttr.indexOf('last')>=0){
                           $(this).prepend('<b class="b-right"></b>');
                           $(this).prepend('<b class="b-left"></b>');
                           $(this).find('p').addClass('active-blue');
                           $(this).find( 'p' ).append("<span>忙</span>");
                        }
                    }
                })

            })


        },
        nextMonth: function() {
            var step = this.step;
            this.render( new Date( this.year, this.month + step - 1, 1 ) );
            this.month += step;
        },

        prevMonth: function() {
            var step = this.step;
            this.render( new Date( this.year, this.month - step - 1, 1 ) );
            this.month -= step;
        },

        show: function() {
            this.el.show();
            $.trigger( this, 'show' );
        },

        hide: function() {
            this.el.hide();
            $.trigger( this, 'hide' );
        }
    } );

    return {
        calendar: calendar,
        util: util
    };
} );
