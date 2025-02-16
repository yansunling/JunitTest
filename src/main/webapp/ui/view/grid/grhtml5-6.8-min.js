var gr = {}
    , DEBUG = 0;
!function() {
    "use strict";
    gr.const_ = {
        VERSION: "6.8.23.0315",
        ID_REPORT_STYLE: "_gridcss",
        ID_DETAILGRID: "_griddg",
        ATTR_CONTENT_RECNO: "_grRecNo",
        ATTR_GROUPH_INDEX: "_grGHIndex",
        ATTR_GROUPF_INDEX: "_grGFIndex",
        ATTR_GROUP_RECNO: "_grGRecNo",
        ATTR_FIELD: "_grfld",
        CSS_DG: "_grdg",
        CSS_SH: "_grcsssh",
        CSS_CB: "_grcsscb",
        BIG_AMT_NUMBER: "零壹贰叁肆伍陆柒捌玖",
        HZ_NUMBER: "一二三四五六七八九十零",
        HZ_NEGATIVE: "负",
        HZ_ZERO_YUAN: "零元整",
        HZ_AMT_STEP: "元拾佰仟万拾佰仟亿拾佰仟",
        HZ_AMT_UNIT: "整角分",
        HZ_DATETIME_UNIT: "周日星期天",
        MILLISECONDS_DAY: 864e5,
        TIMEZONE_OFFSET: 6e4 * (new Date).getTimezoneOffset()
    },
        gr.enum_ = {
            PenStyle: {
                Solid: 0,
                Dash: 1,
                Dot: 2,
                DashDot: 3,
                DashDotDot: 4
            },
            BorderStyle: {
                DrawLeft: 1,
                DrawTop: 2,
                DrawRight: 4,
                DrawBottom: 8
            },
            BackStyle: {
                Normal: 1,
                Transparent: 2
            },
            TextAlign: {
                TopLeft: 17,
                TopCenter: 18,
                TopRight: 20,
                TopJustiy: 144,
                BottomLeft: 65,
                BottomCenter: 66,
                BottomRight: 68,
                BottomJustiy: 192,
                MiddleLeft: 33,
                MiddleCenter: 34,
                MiddleRight: 36,
                MiddleJustiy: 160
            },
            TextOrientation: {
                Default: 0,
                U2DL2R0: 5,
                D2UL2R1: 22,
                U2DR2L0: 9,
                U2DR2L1: 25,
                Invert: 58,
                L2RD2U0: 38,
                L2RD2U1: 54
            },
            ParameterDataType: {
                String: 1,
                Integer: 2,
                Float: 3,
                Boolean: 5,
                DateTime: 6
            },
            FieldType: {
                String: 1,
                Integer: 2,
                Float: 3,
                Currency: 4,
                Boolean: 5,
                DateTime: 6,
                Binary: 7
            },
            ControlType: {
                StaticBox: 1,
                ShapeBox: 2,
                SystemVarBox: 3,
                FieldBox: 4,
                SummaryBox: 5,
                RichTextBox: 6,
                PictureBox: 7,
                MemoBox: 8,
                SubReport: 9,
                Line: 10,
                Chart: 11,
                Barcode: 12,
                FreeGrid: 13
            },
            SummaryFun: {
                Sum: 1,
                Avg: 2,
                Count: 3,
                Min: 4,
                Max: 5,
                Var: 6,
                VarP: 7,
                StdDev: 8,
                StdDevP: 9,
                AveDev: 10,
                DevSq: 11,
                CountBlank: 12,
                CountA: 13,
                Distinct: 14,
                AvgA: 15,
                MinN: 16,
                MaxN: 17,
                StrMin: 18,
                StrMax: 19,
                VarA: 20,
                VarPA: 21,
                StdDevA: 22,
                StdDevPA: 23,
                AveDevA: 24,
                DevSqA: 25,
                SumAbs: 26,
                SumAcc: 27,
                GroupSumAcc: 28
            },
            MathFun: {
                round45: 1,
                round465: 2,
                abs: 5,
                acos: 6,
                asin: 7,
                atan: 8,
                atan2: 9,
                ceil: 15,
                cos: 16,
                exp: 17,
                floor: 18,
                log: 19,
                maxArray: 20,
                minArray: 21,
                pow: 22,
                round: 23,
                sin: 24,
                sqrt: 25,
                tan: 26
            },
            SystemVarType: {
                CurrentDateTime: 1,
                RecordNo: 4,
                RowNo: 8,
                RecordCount: 19,
                GroupNo: 20,
                GroupCount: 21,
                GroupRowNo: 22,
                GroupRowCount: 23
            },
            AnchorStyle: {
                Left: 1,
                Top: 2,
                Right: 4,
                Bottom: 8
            },
            AlignColumnSideStyle: {
                Left: 1,
                Right: 2,
                Both: 3
            },
            DockStyle: {
                None: 0,
                Left: 1,
                Top: 2,
                Right: 3,
                Bottom: 4,
                Fill: 5
            },
            CenterStyle: {
                None: 0,
                Horizontal: 1,
                Vertical: 2,
                Both: 3
            },
            ShiftMode: {
                Never: 0,
                Always: 1,
                WhenOverLapped: 2
            },
            Unit: {
                Cm: 1,
                Inch: 2
            },
            ShapeBoxType: {
                Circle: 1,
                Ellipse: 2,
                Rectangle: 3,
                RoundRect: 4,
                RoundSquare: 5,
                Square: 6,
                Line: 7
            },
            PictureAlignment: {
                TopLeft: 1,
                TopRight: 2,
                Center: 3,
                BottomLeft: 4,
                BottomRight: 5
            },
            PictureSizeMode: {
                Clip: 1,
                Stretch: 2,
                Zoom: 3,
                EnsureFullView: 4,
                Tile: 5
            },
            PictureTransparentMode: {
                None: 0,
                Overlying: 1,
                Background: 2
            },
            PictureRotateMode: {
                None: 0,
                Left: 1,
                Right: 2,
                Flip: 3,
                Mirror: 4
            },
            ChartType: {
                BarChart: 1,
                PieChart: 2,
                LineChart: 3,
                StackedBarChart: 4,
                XYScatterChart: 5,
                XYLineChart: 6,
                CurveLineChart: 7,
                XYCurveLineChart: 8,
                Bubble: 9,
                StackedBar100Chart: 10,
                ColumnChart: 11,
                StackedColumnChart: 12,
                StackedColumn100Chart: 13
            },
            ChartVarType: {
                XVal: 1,
                YVal: 2,
                ZVal: 3,
                YVal100ByGroup: 4,
                YVal100BySeries: 5,
                YValTotalByGroup: 6,
                YValTotalBySeries: 7,
                SeriesLabel: 8,
                GroupLabel: 9
            },
            PointMarkerStyle: {
                None: -1,
                Square: 0,
                SquareCheck: 1,
                SquareCross: 2,
                Circle: 3,
                CirclePoint: 4,
                CircleCross: 5,
                Dimond: 6,
                Triangle: 7,
                Cross: 8,
                Cross4: 9,
                Star4: 10,
                Star5: 11,
                Star6: 12,
                Star10: 13
            },
            BarcodeType: {
                Code25Intlv: 1,
                Code25Ind: 2,
                Code25Matrix: 3,
                Code39: 4,
                Code39X: 5,
                Code128A: 6,
                Code128B: 7,
                Code128C: 8,
                Code93: 9,
                Code93X: 10,
                CodeMSI: 11,
                CodePostNet: 12,
                CodeCodabar: 13,
                CodeEAN8: 14,
                CodeEAN13: 15,
                CodeUPC_A: 16,
                CodeUPC_E0: 17,
                CodeUPC_E1: 18,
                CodeUPC_Supp2: 19,
                CodeUPC_Supp5: 20,
                CodeEAN128A: 21,
                CodeEAN128B: 22,
                CodeEAN128C: 23,
                Code128Auto: 24,
                PDF417: 50,
                QRCode: 51,
                DataMatrix: 52,
                GS1DataMatrix: 53,
                GS1QRCode: 54
            },
            BarcodeCaptionPosition: {
                None: 1,
                Above: 2,
                Below: 3
            },
            BarcodeDirection: {
                LeftToRight: 1,
                RightToLeft: 2,
                TopToBottom: 3,
                BottomToTop: 4
            },
            DtmxEncoding: {
                Auto: 2,
                Ascii: 3,
                C40: 4,
                Text: 5,
                X12: 6,
                Edifact: 7,
                Base256: 8
            },
            DtmxMatrixSize: {
                Auto: 2,
                "10x10": 4,
                "12x12": 5,
                "14x14": 6,
                "16x16": 7,
                "18x18": 8,
                "20x20": 9,
                "22x22": 10,
                "24x24": 11,
                "26x26": 12,
                "32x32": 13,
                "36x36": 14,
                "40x40": 15,
                "44x44": 16,
                "48x48": 17,
                "52x52": 18,
                "64x64": 19,
                "72x72": 20,
                "80x80": 21,
                "88x88": 22,
                "96x96": 23,
                "104x104": 24,
                "120x120": 25,
                "132x132": 26,
                "144x144": 27,
                "8x18": 28,
                "8x32": 29,
                "12x26": 30,
                "12x36": 31,
                "16x36": 32,
                "16x48": 33
            },
            StringAlignment: {
                Near: 1,
                Center: 2,
                Far: 3
            },
            PeriodType: {
                None: 0,
                Day: 1,
                Week: 2,
                ThirdMonth: 3,
                HalfMonth: 4,
                Month: 5,
                Quarter: 6,
                HalfYear: 7,
                Year: 8,
                Calendar: 9
            },
            OCGroupHeaderVAlign: {
                Top: 1,
                Bottom: 2,
                Middle: 3
            },
            controlLayout: {
                auto: 0,
                table: 1,
                absolute: 2
            },
            detailgridResize: {
                asDesign: 0,
                fitWidth: 1,
                onlyGrow: 2
            }
        },
        gr.locale = {
            supportIntl: !!window.Intl,
            decimalSep: ".",
            thousandSep: ",",
            dateSep: "-",
            timeSep: ":",
            textAM: "上午",
            textPM: "下午",
            currencySymbol: "￥"
        },
        gr.script = {
            calcExp: function(expText) {
                var result = NaN;
                expText = "result=" + expText;
                try {
                    eval(expText)
                } catch (e) {
                    result = NaN
                }
                return result
            },
            execEvent: function(scriptText, scriptName, Sender, Report) {
                var globalScript = Report.GlobalScript;
                try {
                    globalScript && (scriptText += "\n" + globalScript),
                        eval(scriptText)
                } catch (e) {
                    Sender.report.root._scriptFailed || (alert("执行报表脚本 '" + scriptName + "' 失败" + (Sender.Name ? "\n对象名称: " + Sender.Name : "") + "\n错误信息: " + e.toString() + "\n脚本代码:\n" + scriptText),
                        Sender.report.root._scriptFailed = 1)
                }
            }
        }
}(),
    function(e) {
        "use strict";
        var t = gr.enum_
            , r = gr.const_
            , o = gr.helper = {}
            , n = gr.common = {}
            , i = r.MILLISECONDS_DAY
            , a = r.TIMEZONE_OFFSET
            , l = o.assignJSONMembers = function(t, r, o) {
            var n, i, a;
            o === e && (o = t.report.isWR),
            o && (a = gr.wr.wrPropNameDecode);
            for (i in r)
                n = r[i],
                "object" != typeof n && (o && (i = a(i)),
                t.hasOwnProperty(i) && (t[i] = n))
        }
            , s = o.enumMemberValid = function(e, t, r) {
            "number" != typeof e[t] && (e[t] = v(r, e[t]))
        }
            , c = o.enumBitMemberValid = function(e, t, r) {
            "number" != typeof e[t] && (e[t] = T(r, e[t]))
        }
            , u = o.colorMemberValid = function(e, t, r) {
            "number" != typeof e[t] && (e[t] = parseInt("0x" + e[t], 16) | Math.max(0, Math.min(Math.round(255 * r), 255)) << 24)
        }
            , d = o.penStyleText = function(e) {
            var r = {
                Solid: "solid",
                Dash: "dashed",
                Dot: "dotted",
                DashDot: "dashed",
                DashDotDot: "dotted"
            };
            return g(e.Width) + " " + r[x(t.PenStyle, e.Style)] + " " + w(e.Color)
        }
            , h = o.fontHeight = function(e) {
            return 4 * e.Size / 3e4
        }
            , f = (o.fontString = function(e, t, r) {
                return t + " " + g(e) + " " + r
            }
                ,
                o.fontCSSText = function(e) {
                    var t = ""
                        , r = [];
                    return e.Italic && r.push("italic"),
                    e.Bold && r.push("bold"),
                        r.push(g(h(e)), e.Name),
                        r.forEach(function(e) {
                            t && (t += " "),
                                t += e
                        }),
                        t
                }
        )
            , p = (o.intFixed = function(e, t) {
                var r, o = e + "";
                if (t > 0)
                    if (r = t - o.length,
                    0 > r)
                        o = o.substr(-r);
                    else
                        for (; r-- > 0; )
                            o = "0" + o;
                return o
            }
                ,
                function(e) {
                    var t = e.toFixed(2)
                        , r = t.length - 1;
                    return "0" === t.charAt(r) && ("0" === t.charAt(r - 1) && (r -= 2),
                        t = t.substr(0, r)),
                        t
                }
        )
            , g = o.pixelsToHtml = function(e) {
            return p(e) + "px"
        }
            , m = (o.percentToHtml = function(e) {
                return p(e) + "%"
            }
                ,
                o.cloneDate = function(e) {
                    return new Date(e)
                }
        )
            , C = o.confirmDateValue = function(t) {
            function o() {
                function o() {
                    var e = t.indexOf(":")
                        , o = e > 0 && 8 > e
                        , n = t.split(/[\:\-\/ ]/g)
                        , i = n.length
                        , a = i > 0 ? +n[0] : 0
                        , l = i > 1 ? +n[1] : 0
                        , s = i > 2 ? +n[2] : 0
                        , c = i > 3 ? +n[3] : 0
                        , u = i > 4 ? +n[4] : 0
                        , d = i > 5 ? +n[5] : 0;
                    return isNaN(l) && (e = n[1],
                        l = r.HZ_NUMBER.indexOf(e.charAt(0)) + 1,
                    e.length >= 3 && (l += r.HZ_NUMBER.indexOf(e.charAt(1)) + 1)),
                    o && (e = a,
                        a = c,
                        c = e,
                        e = l,
                        l = u,
                        u = e,
                        e = s,
                        s = d,
                        d = e),
                    s > 31 && (e = a,
                        a = s,
                        s = e,
                        e = l,
                        l = s,
                        s = e),
                    l > 12 && (e = l,
                        l = s,
                        s = e),
                        new Date(a,--l,s,c,u,d)
                }
                var n;
                return "string" == typeof t ? (n = Date.parse(t),
                    t = t ? isNaN(n) ? o() : new Date(n) : e) : (n = new M,
                    n.AsFloat = +t,
                    t = n.value),
                    t
            }
            return Date.prototype.isPrototypeOf(t) ? t : o()
        }
            , b = (o.confirmCloneDateValue = function(e) {
                return Date.prototype.isPrototypeOf(e) ? m(e) : C(e)
            }
                ,
                o.strimDate = function(e) {
                    var t = e.getTime();
                    e.setTime(t - (t - 6e4 * e.getTimezoneOffset()) % i)
                }
                ,
                o.incDate = function(e) {
                    e.setTime(e.getTime() + i)
                }
                ,
                o.incDate2 = function(e, t) {
                    e.setTime(e.getTime() + t * i)
                }
        )
            , v = (o.periodRangeBy = function(e, t) {
                function r() {
                    11 > s ? ++s : (++c,
                        s = 0)
                }
                var o, n = e.getDate(), i = e.getMonth(), a = e.getFullYear(), l = n, s = i, c = a;
                if (2 >= t)
                    o = {
                        begin: m(e),
                        end: m(e)
                    },
                        b(o.end, 1 === t ? 1 : 7);
                else {
                    switch (t) {
                        case 3:
                            10 >= n ? (n = 1,
                                l = 11) : 20 >= n ? (n = 11,
                                l = 21) : (n = 21,
                                r(),
                                l = 1);
                            break;
                        case 4:
                            15 >= n ? (n = 1,
                                l = 16) : (n = 16,
                                r(),
                                l = 1);
                            break;
                        case 5:
                            n = l = 1,
                                r();
                            break;
                        case 6:
                            n = l = 1,
                                i -= i % 3,
                                s = i,
                                r(),
                                r(),
                                r();
                            break;
                        case 7:
                            n = l = 1,
                                i = 6 > i ? 0 : 6,
                                s = 6 > i ? 6 : 0,
                            s || c++;
                            break;
                        case 8:
                            n = l = 1,
                                i = s = 0,
                                c++
                    }
                    o = {
                        begin: new Date(a,i,n),
                        end: new Date(c,s,l)
                    }
                }
                return o
            }
                ,
                o.periodRangeNext = function(e, t) {
                    function r() {
                        11 > s ? ++s : (++c,
                            s = 0)
                    }
                    var o, n = e.begin.getDate(), i = e.begin.getMonth(), a = e.begin.getFullYear(), l = n, s = i, c = a;
                    if (2 >= t)
                        o = {
                            begin: m(e.end),
                            end: m(e.end)
                        },
                            b(o.end, 1 === t ? 1 : 7);
                    else {
                        switch (t) {
                            case 3:
                                10 >= n ? (n = 11,
                                    l = 21) : 20 >= n ? (n = 21,
                                    l = 1,
                                    r()) : (n = 1,
                                    l = 11,
                                    r(),
                                    a = c,
                                    i = s);
                                break;
                            case 4:
                                15 >= n ? (n = 16,
                                    l = 1,
                                    r()) : (n = 1,
                                    l = 16,
                                    r(),
                                    a = c,
                                    i = s);
                                break;
                            case 5:
                                r(),
                                    a = c,
                                    i = s,
                                    r();
                                break;
                            case 6:
                                r(),
                                    r(),
                                    r(),
                                    a = c,
                                    i = s,
                                    n = l,
                                    r(),
                                    r(),
                                    r();
                                break;
                            case 7:
                                i ? (i = 0,
                                    a++,
                                    s = 6) : (i = 6,
                                    s = 0),
                                    c++;
                                break;
                            case 8:
                                a++,
                                    c++,
                                    c++
                        }
                        o = {
                            begin: new Date(a,i,n),
                            end: new Date(c,s,l)
                        }
                    }
                    return o
                }
                ,
                o.confirmBooleanValue = function(e) {
                    if (e.constructor === Boolean.prototype.constructor)
                        return e;
                    if ("string" == typeof e) {
                        if (e.toLowerCase(),
                        "TRUE" === e || "true" === e)
                            return !0;
                        e = parseInt(e)
                    }
                    return !!e
                }
                ,
                o.ensureNameText = function(e) {
                    return /^[A-Za-z_\u0100-\uffff][\w\u0100-\uffff]*$/.test(e) || (e = "[" + e + "]"),
                        e
                }
                ,
                o.enumName2Value = function(t, r) {
                    var o = t[r];
                    if (o === e) {
                        r = r.toUpperCase();
                        for (var n in t)
                            if (n.toUpperCase() === r)
                                return t[n];
                        o = -1
                    }
                    return o
                }
        )
            , x = o.enumValue2Name = function(e, t) {
            for (var r in e)
                if (e[r] === t)
                    return r;
            return ""
        }
            , T = function(e, t) {
            var r, o, n, i = 0;
            for (t = t.substring(1, t.length - 1),
                     r = t.split("|"),
                     n = r.length,
                     o = 0; n > o; o++)
                i += e[r[o]] || 0;
            return i
        }
            , y = o.rgba2color = function(e, t, r, o) {
            return S(e | t << 8 | r << 16, o)
        }
            , S = o.colorAlpha = function(e, t) {
            return e | Math.max(0, Math.min(Math.round(255 * t))) << 24
        }
            , F = o.color2rgba = function(e) {
            return {
                r: 255 & e,
                g: e >> 8 & 255,
                b: e >> 16 & 255,
                a: 4278190080 & e ? (e >> 24 & 255) / 255 : 1
            }
        }
            , w = o.colorValue2Html = function(e) {
            function t(e) {
                return (16 > e ? "0" : "") + e.toString(16)
            }
            var r = F(e);
            return 1 === r.a ? "#" + t(r.r) + t(r.g) + t(r.b) : "rgba(" + r.r + "," + r.g + "," + r.b + "," + p(r.a) + ")"
        }
            , D = o.colorIsBlack = function(e) {
            return 16777215 & e
        }
            , P = (o.decodeTextColor = function(e) {
                var t, r, o, n, i, a = 4;
                return "rgb(" === e.substr(0, 4) && (t = e.indexOf(")", a),
                -1 !== t && (e = e.substring(4, t),
                    r = e.split(","),
                3 === r.length && (o = parseInt(r[0]),
                    n = parseInt(r[1]),
                    i = parseInt(r[2]),
                o >= 0 && 255 >= o && n >= 0 && 255 >= n && i >= 0 && 255 >= i))) ? {
                    color: rgb(o, n, i),
                    index: t + 1
                } : !1
            }
                ,
                o.colorGradientLight = function(e) {
                    var t = F(e)
                        , r = 120 / 255;
                    return t.r += (255 - t.r) * r,
                        t.g += (255 - t.g) * r,
                        t.b += (255 - t.b) * r,
                    t.r > 255 && (t.r = 255),
                    t.g > 255 && (t.g = 255),
                    t.b > 255 && (t.b = 255),
                        y(t.r, t.g, t.b, t.a)
                }
                ,
                o.colorGradientDark = function(e) {
                    var t = F(e)
                        , r = 30 / 255;
                    return t.r -= t.r * r,
                        t.g -= t.g * r,
                        t.b -= t.b * r,
                    t.r < 0 && (t.r = 0),
                    t.g < 0 && (t.g = 0),
                    t.b < 0 && (t.b = 0),
                        y(t.r, t.g, t.b, t.a)
                }
                ,
                o.prototypeLinkExtend = function(e, t) {
                    function r() {}
                    var o, n = e.prototype;
                    r.prototype = t.prototype,
                        o = new r;
                    for (var i in n)
                        o[i] = n[i];
                    e.prototype = o,
                        e.prototype.constructor = e
                }
                ,
                o.prototypeCopyExtend = function(e, t) {
                    var r = t.prototype
                        , o = e.prototype;
                    for (var n in r)
                        o.hasOwnProperty(n) || (o[n] = r[n]);
                    o.constructor = e
                }
                ,
                o.createArray = function(e, t) {
                    for (var r = [], o = 0; o++ < e; )
                        r.push(t);
                    return r
                }
                ,
                o.assignObjectEx = function(e, t, r) {
                    var o, n;
                    for (var i in t)
                        t.hasOwnProperty(i) && (o = t[i],
                            n = typeof o,
                            r && "object" === n ? (e[i] || (e[i] = {}),
                                R(e[i], o)) : e[i] = o);
                    return e
                }
        )
            , R = o.assignObject = function(e, t) {
            P(e, t, 1)
        }
            , A = (o.assignObjectAtom = function(e, t) {
                var r;
                for (var o in t)
                    t.hasOwnProperty(o) && (r = t[o],
                    "object" != typeof r && (e[o] = r));
                return e
            }
                ,
                o.compareObject = function(e, t) {
                    var r;
                    if (e === t)
                        return 1;
                    if (!e || !t)
                        return 0;
                    for (var o in e)
                        if (r = typeof e[o],
                        "object" === r) {
                            if (!A(e[o], t[o]))
                                return 0
                        } else if ("function" !== r && e[o] !== t[o])
                            return 0;
                    return 1
                }
        )
            , B = (o.parseXML = function(t) {
                function r() {
                    for (var e, r = 0, o = t.indexOf("<", r), n = []; o >= 0; )
                        o > r && (e = t.substring(r, o),
                        /\S/g.test(e) && n.push(e)),
                            r = o,
                            o = t.indexOf(">", r + 1),
                            n.push(t.substring(r, ++o)),
                            r = o,
                            o = t.indexOf("<", r);
                    return n
                }
                var o;
                /\s/g.test(t) && (t = r().join(""));
                try {
                    o = (new DOMParser).parseFromString(t, "text/xml")
                } catch (n) {
                    o = e
                }
                return o
            }
                ,
                o.xmlToReportDataJSON = function(e) {
                    var t, r, o, n, i = "", a = {};
                    for (o = e.childNodes[0].firstChild; o; ) {
                        for (o.nodeName !== i && (i = o.nodeName,
                            r = [],
                            a[o.nodeName] = r),
                                 t = {},
                                 n = o.firstChild; n; )
                            t[n.nodeName] = n.hasOwnProperty("text") ? n.text : n.textContent,
                                n = n.nextSibling;
                        r.push(t),
                            o = o.nextSibling
                    }
                    return a
                }
                ,
                o.getRelativePosition = function(e) {
                    var t, r, o = e.originalEvent || e, n = e.currentTarget || e.srcElement, i = n.getBoundingClientRect();
                    return o.touches ? (t = o.touches[0].clientX - i.left,
                        r = o.touches[0].clientY - i.top) : (t = o.clientX - i.left,
                        r = o.clientY - i.top),
                        {
                            x: t,
                            y: r
                        }
                }
                ,
                o.addEvent = function(e, t, r) {
                    e.addEventListener ? e.addEventListener(t, r) : e.attachEvent ? e.attachEvent("on" + t, r) : e["on" + t] = r
                }
        )
            , N = o.removeEvent = function(e, t, r) {
            e.removeEventListener ? e.removeEventListener(t, r, !1) : e.detachEvent ? e.detachEvent("on" + t, r) : e["on" + t] = noop
        }
            , E = (o.bindEvents = function(e, t, r) {
                e.events || (e.events = {}),
                    t.forEach(function(t) {
                        e.events[t] = function() {
                            r.apply(e, arguments)
                        }
                            ,
                            B(e.canvas, t, e.events[t])
                    })
            }
                ,
                o.unbindEvents = function(e, t) {
                    t.forEach(function(t, r) {
                        N(e.canvas, r, t)
                    })
                }
                ,
                o.toRadians = function(e) {
                    return e * Math.PI / 180
                }
        )
            , M = (o.toDegree = function(e) {
                return 180 * e / Math.PI
            }
                ,
                n.DateTime = function() {
                    this.value = new Date
                }
        );
        n.DateTime.prototype = {
            get Year() {
                return this.value.getFullYear()
            },
            get Month() {
                return this.value.getMonth() + 1
            },
            get Day() {
                return this.value.getDate()
            },
            get Hour() {
                return this.value.getHours()
            },
            get Minute() {
                return this.value.getMinutes()
            },
            get Second() {
                return this.value.getSeconds()
            },
            get DayOfWeek() {
                return this.value.getDay()
            },
            get DayOfYear() {
                var e = this.value
                    , t = e.getFullYear();
                return (new Date(t,e.getMonth(),e.getDate()).getTime() - new Date(t,0,1).getTime()) / i + 1
            },
            get AsFloat() {
                return (this.value.getTime() + new Date(1970,0,1).getTime() - new Date(1899,11,30).getTime() - a) / i
            },
            set AsFloat(e) {
                this.value.setTime(e * i + new Date(1899,11,30).getTime() - new Date(1970,0,1).getTime() + a)
            },
            ValueFromDate: function(e, t, r) {
                this.value = new Date(e,t - 1,r)
            },
            ValueFromDateTime: function(e, t, r, o, n, i) {
                this.value = new Date(e,t - 1,r,o,n,i)
            },
            Format: function(e) {
                return new gr.format.DateTimeFormatter(e).format(this.value)
            }
        };
        var V = n.Rect = function(e, t, r, o) {
                var n = this;
                n.left = e,
                    n.top = t,
                    n.right = r,
                    n.bottom = o
            }
        ;
        n.Rect.prototype = {
            clone: function() {
                var e = this;
                return new n.Rect(e.left,e.top,e.right,e.bottom)
            },
            IsRectEmpty: function() {
                var e = this;
                return e.left >= e.right || e.top >= e.bottom
            },
            PtInRect: function(e, t) {
                var r = this;
                return r.left <= e && e < r.right && r.top <= t && t < r.bottom
            },
            Width: function() {
                var e = this;
                return e.right - e.left
            },
            Height: function() {
                var e = this;
                return e.bottom - e.top
            },
            SetRect: function(e, t, r, o) {
                var n = this;
                n.left = e,
                    n.top = t,
                    n.right = r,
                    n.bottom = o
            },
            InflateRect: function(e, t) {
                var r = this;
                r.left -= e,
                    r.top -= t,
                    r.right += e,
                    r.bottom += t
            },
            OffsetRect: function(e, t) {
                var r = this;
                r.left += e,
                    r.top += t,
                    r.right += e,
                    r.bottom += t
            },
            IntersectRect: function(e) {
                var t = this;
                t.left = Math.max(t.left, e.left),
                    t.top = Math.max(t.top, e.top),
                    t.right = Math.min(t.right, e.right),
                    t.bottom = Math.min(t.bottom, e.bottom)
            }
        };
        var O = n.Pen = function(t, r, o) {
                var n = this;
                t === e && (t = 1),
                r === e && (r = 0),
                o === e && (o = 0),
                    n.Width = t,
                    n.Color = S(r, 1),
                    n.Style = o
            }
        ;
        O.prototype = {
            clone: function() {
                var e = this
                    , t = new O;
                return t.Width = e.Width,
                    t.Style = e.Style,
                    t.Color = e.Color,
                    t
            },
            loadFromJSON: function(e, r, o) {
                e && (l(this, e, o),
                    s(this, "Style", t.PenStyle),
                    u(this, "Color", r))
            },
            getDashStyle: function() {
                var e;
                switch (this.Style) {
                    case 1:
                        e = [2, 2];
                        break;
                    case 2:
                        e = [1, 1];
                        break;
                    case 3:
                        e = [2, 1, 1, 1];
                        break;
                    case 4:
                        e = [2, 1, 1, 1, 1, 1];
                        break;
                    default:
                        e = []
                }
                return e
            }
        };
        var k = n.Border = function(e) {
                var t = this;
                t.Styles = e,
                    t.Shadow = !1,
                    t.ShadowWidth = 4,
                    t.ShadowColor = 0,
                    t.Pen = new O
            }
        ;
        n.Border.prototype = {
            loadFromJSON: function(e, r, o) {
                var n = this;
                e && (l(n, e, o),
                    c(n, "Styles", t.BorderStyle),
                    u(n, "ShadowColor"),
                e.Pen && n.Pen.loadFromJSON(e.Pen, r, o))
            },
            clone: function() {
                var e = this
                    , t = new k;
                return t.Styles = e.Styles,
                    t.Shadow = e.Shadow,
                    t.ShadowWidth = e.ShadowWidth,
                    t.ShadowColor = e.ShadowColor,
                    t.Pen = e.Pen.clone(),
                    t
            },
            getLeftWidth: function() {
                var e = this;
                return 1 & e.Styles ? e.Pen.Width : 0
            },
            getRightWidth: function() {
                var e = this;
                return (4 & e.Styles ? e.Pen.Width : 0) + (e.Shadow ? e.ShadowWidth : 0)
            },
            getTopWidth: function() {
                var e = this;
                return 2 & e.Styles ? e.Pen.Width : 0
            },
            getBottomWidth: function() {
                var e = this;
                return (8 & e.Styles ? e.Pen.Width : 0) + (e.Shadow ? e.ShadowWidth : 0)
            }
        };
        var I = n.Font = function() {
                var e = this;
                e.Size = 97500,
                    e.Bold = !1,
                    e.Italic = !1,
                    e.Underline = !1,
                    e.Strikethrough = !1,
                    e.Name = ""
            }
        ;
        I.prototype = {
            loadFromJSON: function(e, t) {
                e && l(this, e, t)
            },
            clone: function() {
                var e = this
                    , t = new I;
                return t.Size = e.Size,
                    t.Bold = e.Bold,
                    t.Italic = e.Italic,
                    t.Underline = e.Underline,
                    t.Strikethrough = e.Strikethrough,
                    t.Name = e.Name,
                    t
            }
        },
            n.FontWrapper = function(e) {
                this.parentFont = e
            }
            ,
            n.FontWrapper.prototype = {
                loadFromJSON: function(e, t) {
                    var r = this;
                    e && (r.prepareModify(),
                        r.font.loadFromJSON(e, t))
                },
                assign: function(e) {
                    var t = this;
                    e.font ? t.font = e.font.clone() : delete t.font
                },
                prepareModify: function() {
                    var e = this;
                    e.font || (e.font = e.parentFont.UsingFont().clone())
                },
                Clone: function() {
                    var e = this
                        , t = new n.FontWrapper(e.parentFont);
                    return t.font = e.UsingFont().clone(),
                        t
                },
                UsingFont: function() {
                    var e = this;
                    return e.font ? e.font : e.parentFont.UsingFont()
                },
                get Name() {
                    return this.UsingFont().Name
                },
                set Name(e) {
                    var t = this;
                    t.prepareModify(),
                        t.font.Name = e
                },
                get Point() {
                    return this.UsingFont().Size / 1e4
                },
                set Point(e) {
                    var t = this;
                    t.prepareModify(),
                        t.font.Size = 1e4 * e
                },
                get Bold() {
                    return this.UsingFont().Bold
                },
                set Bold(e) {
                    var t = this;
                    t.prepareModify(),
                        t.font.Bold = e
                },
                get Italic() {
                    return this.UsingFont().Italic
                },
                set Italic(e) {
                    var t = this;
                    t.prepareModify(),
                        t.font.Italic = e
                },
                get Underline() {
                    return this.UsingFont().Underline
                },
                set Underline(e) {
                    var t = this;
                    t.prepareModify(),
                        t.font.Underline = e
                },
                get Strikethrough() {
                    return this.UsingFont().Strikethrough
                },
                set Strikethrough(e) {
                    var t = this;
                    t.prepareModify(),
                        t.font.Strikethrough = e
                }
            };
        var L = n.TextFormat = function() {
                var e = this;
                e.WordWrap = !1,
                    e.EndEllipsis = !1,
                    e.TextAlign = 33,
                    e.TextOrientation = 0,
                    e.TextAngle = 0,
                    e.CharacterSpacing = 0,
                    e.LineSpacing = 0,
                    e.FirstCharIndent = 0,
                    e.HtmlTags = !1
            }
        ;
        L.prototype = {
            loadFromJSON: function(e, r) {
                var o = this
                    , n = "CharSpacing"
                    , i = "LnSpacing";
                e && (l(o, e, r),
                r && (n = gr.wr.wrPropNameEncode(n),
                    i = gr.wr.wrPropNameEncode(i)),
                    n = e[n],
                n && (o.CharacterSpacing = n),
                    i = e[i],
                i && (o.LineSpacing = i),
                    s(o, "TextAlign", t.TextAlign),
                    s(o, "TextOrientation", t.TextOrientation))
            },
            Clone: function() {
                var e = this
                    , t = new L;
                return t.WordWrap = e.WordWrap,
                    t.EndEllipsis = e.EndEllipsis,
                    t.TextAlign = e.TextAlign,
                    e.TextOrientation = e.TextOrientation,
                    e.TextAngle = e.TextAngle,
                    t.CharacterSpacing = e.CharacterSpacing,
                    t.LineSpacing = e.LineSpacing,
                    t.FirstCharIndent = e.FirstCharIndent,
                    t
            }
        },
            n.Context = function(e) {
                var t = this;
                t.ctx = e,
                    e.textBaseline = "top",
                    t.pens = [new O],
                    t.fills = [e.fillStyle],
                    t.fonts = [],
                    t.clips = []
            }
            ,
            n.Context.prototype = {
                get width() {
                    return this.ctx.canvas.width
                },
                get height() {
                    return this.ctx.canvas.height
                },
                get usingFont() {
                    var e = this.fonts
                        , t = e.length;
                    return e[t - 1]
                },
                selectFillStyle: function(e) {
                    var t = this;
                    t.fills.push(e),
                        t.ctx.fillStyle = e
                },
                selectFillColor: function(e) {
                    this.selectFillStyle(w(e))
                },
                restoreFillStyle: function() {
                    var e = this
                        , t = e.fills;
                    t.pop(),
                        e.ctx.fillStyle = t[t.length - 1]
                },
                selectPen: function(e) {
                    var t = this;
                    t.pens.push(e),
                        t._applyPen()
                },
                selectPen2: function(e, t, r) {
                    this.selectPen(new O(e,t,r))
                },
                restorePen: function() {
                    var e = this;
                    e.pens.pop(),
                        e._applyPen()
                },
                selectFont: function(e) {
                    var t = this
                        , r = t.fonts
                        , o = r[r.length - 1];
                    e !== o && (t.ctx.font = f(e)),
                        r.push(e)
                },
                restoreFont: function() {
                    var e = this
                        , t = e.fonts
                        , r = t.pop()
                        , o = t[t.length - 1];
                    t.length > 0 ? o !== r && (e.ctx.font = f(o)) : e.ctx.font = ""
                },
                fontSizeTo: function(e) {
                    var t = this
                        , r = t.usingFont.clone();
                    r.Size = e,
                        t.selectFont(r)
                },
                pushClipRect: function(e, t, r, o) {
                    var n = this
                        , i = this.ctx
                        , a = n.clips
                        , l = a.length
                        , s = new V(e,t,e + r,t + o);
                    l && s.IntersectRect(a[l - 1]),
                        a.push(s),
                        i.beginPath(),
                        i.rect(s.left, s.top, s.Width(), s.Height()),
                        i.clip()
                },
                popClipRect: function() {
                    var e = this
                        , t = this.ctx
                        , r = t.canvas
                        , o = e.clips
                        , n = o.length;
                    rect,
                        o.pop(rect),
                        rect = n > 1 ? o[n - 2] : new V(0,0,r.width,r.height),
                        t.beginPath(),
                        t.rect(rect.left, rect.top, rect.Width(), rect.Height()),
                        t.clip()
                },
                measureTextWidth: function(e) {
                    return this.ctx.measureText(e).width
                },
                measureTextHeight: function(e) {
                    return h(this.usingFont)
                },
                drawText: function(e, t, r) {
                    this.ctx.fillText(e, t, r)
                },
                drawTextCenter: function(e, t, r) {
                    var o = this.ctx;
                    o.textAlign = "center",
                        o.fillText(e, t, r),
                        o.textAlign = "start"
                },
                drawTextAlign: function(e, t, r, o, n, i, a) {
                    var l, s = this, c = h(s.usingFont), u = 2 & i, d = 4 & i, f = 32 & i, p = 64 & i;
                    f ? r += (n - c) / 2 : p && (r += n - c),
                    (u || d) && (l = s.measureTextWidth(e),
                    (!a || o > l) && (t += u ? (o - l) / 2 : o - l)),
                        a ? s.ctx.fillText(e, t, r, o) : s.ctx.fillText(e, t, r)
                },
                drawTextAlign2: function(e, t, r, o) {
                    this.drawTextAlign(e, t.left, t.top, t.Width(), t.Height(), r, o)
                },
                drawFormatText: function(e, t, r, o, n, i) {
                    var a = this;
                    0 === i.TextOrientation ? i.TextAngle ? a.drawTextRotate(e, t, r, i.TextAngle) : i.CharacterSpacing || i.LineSpacing || i.FirstCharIndent || i.ParagraphSpacing || 1 !== i.FontWidthRatio || 128 & i.TextAlign ? a.drawTextAlign(e, t, r, o, n, i.TextAlign) : a.drawTextAlign(e, t, r, o, n, i.TextAlign) : a.drawOrientationText(e, t, r, o, n, i)
                },
                drawOrientationText: function(e, t, r, o, n, i) {
                    var a = this
                        , l = a.ctx
                        , s = 0
                        , c = e.length
                        , u = h(a.usingFont)
                        , d = i.TextOrientation
                        , f = 32 & d;
                    if (f)
                        a.drawTextAlign(e, t, r, o, n, i.TextAlign);
                    else
                        for (; c > s; )
                            l.fillText(e[s++], t, r),
                                r += u
                },
                drawTextRotate: function(e, t, r, o) {
                    var n = this.ctx;
                    n.translate(t, r),
                        n.rotate(E(-o)),
                        this.ctx.fillText(e, 0, 0),
                        n.setTransform(1, 0, 0, 1, 0, 0)
                },
                drawEqualSpaceText: function(e, t, r, o) {
                    for (var n, i, a = this, l = t, s = e.length, c = 0, u = (r - t) / s; s > c; )
                        n = t + u * (c + 1),
                            i = e.substr(c++, 1),
                            a.drawText(i, (n + l - a.measureTextWidth(i)) / 2, o),
                            l = n
                },
                beginPath: function() {
                    var e = this;
                    e.ctx.beginPath(),
                        e.inpath = 1
                },
                closePath: function() {
                    this.ctx.closePath()
                },
                stroke: function() {
                    var e = this;
                    e.ctx.stroke(),
                        e.inpath = 0
                },
                fill: function() {
                    var e = this;
                    e.ctx.fill(),
                        e.inpath = 0
                },
                strokefill: function() {
                    var e = this;
                    e.ctx.stroke(),
                        e.ctx.fill(),
                        e.inpath = 0
                },
                fillRect: function(e, t, r, o, n) {
                    var i = this;
                    i.selectFillColor(n),
                        i.ctx.fillRect(e, t, r, o),
                        i.restoreFillStyle(n)
                },
                fillRect2: function(e, t) {
                    this.fillRect(e.left, e.top, e.Width(), e.Height(), t)
                },
                moveTo: function(e, t) {
                    var r = this
                        , o = r.ctx;
                    !r.inpath && o.beginPath(),
                        o.moveTo(e, t)
                },
                lineTo: function(e, t) {
                    var r = this
                        , o = r.ctx;
                    o.lineTo(e, t),
                    !r.inpath && o.stroke()
                },
                drawLine: function(e, t, r, o) {
                    var n = this.ctx;
                    n.beginPath(),
                        n.moveTo(e, t),
                        n.lineTo(r, o),
                        n.stroke()
                },
                drawPolyLine: function(e, t, r) {
                    var o = this.ctx
                        , n = 0
                        , i = e.length;
                    for (o.beginPath(),
                             o.moveTo(e[n++], e[n++]); i > n; )
                        o.lineTo(e[n++], e[n++]);
                    r && o.closePath(),
                        o.stroke(),
                    t && o.fill()
                },
                bezierCurveTo: function(e, t, r, o, n, i) {
                    this.ctx.bezierCurveTo(e, t, r, o, n, i)
                },
                polyBezier: function(e) {
                    var t, r = this.ctx, o = e.length;
                    for (r.beginPath(),
                             r.moveTo(e[0].x, e[0].y),
                             t = 1; o > t; t++)
                        r.bezierCurveTo(e[t].x, e[t++].y, e[t].x, e[t++].y, e[t].x, e[t].y);
                    r.stroke()
                },
                circle: function(e, t, r, o, n) {
                    var i = this.ctx
                        , a = (o > r ? r : o) / 2;
                    e += r / 2,
                        t += o / 2,
                        i.beginPath(),
                        i.arc(e, t, a, 0, 2 * Math.PI),
                        i.stroke(),
                    n && i.fill()
                },
                ellipse: function(e, t, r, o, n) {
                    var i = this.ctx
                        , a = r > o ? r : o
                        , l = r / a
                        , s = o / a;
                    e += r / 2,
                        t += o / 2,
                        a /= 2,
                        i.beginPath(),
                        i.save(),
                        i.scale(l, s),
                        i.arc(e / l, t / s, a, 0, 2 * Math.PI),
                        i.restore(),
                        i.stroke(),
                    n && i.fill()
                },
                rectangle: function(e, t, r, o, n) {
                    var i = this;
                    i.ctx.strokeRect(e, t, r, o),
                    n && i.ctx.fillRect(e, t, r, o)
                },
                rectangle2: function(e, t) {
                    this.rectangle(e.left, e.top, e.Width(), e.Height(), t)
                },
                square: function(e, t, r, o, n) {
                    var i = o > r ? r : o;
                    e += (r - i) / 2,
                        t += (o - i) / 2,
                        this.rectangle(e, t, i, i, n)
                },
                roundRectangle: function(e, t, r, o, n, i, a) {
                    var l = this.ctx;
                    l.beginPath(),
                        l.moveTo(e + n, t),
                        l.lineTo(e + r - n, t),
                        l.quadraticCurveTo(e + r, t, e + r, t + i),
                        l.lineTo(e + r, t + o - i),
                        l.quadraticCurveTo(e + r, t + o, e + r - n, t + o),
                        l.lineTo(e + n, t + o),
                        l.quadraticCurveTo(e, t + o, e, t + o - i),
                        l.lineTo(e, t + i),
                        l.quadraticCurveTo(e, t, e + n, t),
                        l.stroke(),
                    a && l.fill()
                },
                roundSquare: function(e, t, r, o, n, i, a) {
                    var l = o > r ? r : o;
                    e += (r - l) / 2,
                        t += (o - l) / 2,
                        this.roundRectangle(e, t, l, l, n, i, a)
                },
                arc: function(e, t, r, o, n) {
                    var i = this
                        , a = i.ctx
                        , l = !i.inpath;
                    l && a.beginPath(),
                        a.arc(e, t, r, -E(o), -E(n), n >= o),
                    l && a.stroke()
                },
                ellipseArc: function(e, t, r, o, n, i) {
                    var a = this
                        , l = a.ctx
                        , s = !a.inpath
                        , c = l.ellipse
                        , u = r / 2
                        , d = o / 2
                        , h = e + u
                        , f = t + d
                        , p = i >= n
                        , n = -E(n)
                        , i = -E(i);
                    s && l.beginPath(),
                        c ? l.ellipse(h, f, u, d, 0, n, i, p) : l.arc(h, f, Math.min(u, d), n, i, p),
                    s && l.stroke()
                },
                pie: function(e, t, r, o, n, i) {
                    var a = this
                        , l = a.ctx;
                    o = E(o),
                        n = E(n),
                        l.beginPath(),
                        l.moveTo(e, t),
                        l.arc(e, t, r, -o, -n, 1),
                        l.closePath(),
                        l.stroke(),
                    i && l.fill()
                },
                ellipsePie: function(e, t, r, o, n, i, a) {
                    var l, s, c = this.ctx, u = c.ellipse, d = r / 2, h = o / 2, f = e + d, p = t + h, g = i >= n;
                    n = E(n),
                        i = E(i),
                    u || (d = h = Math.min(d, h)),
                        l = f + Math.cos(n) * d,
                        s = p - Math.sin(n) * h,
                        c.beginPath(),
                        c.moveTo(f, p),
                        c.lineTo(l, s),
                        u ? c.ellipse(f, p, d, h, 0, -n, -i, g) : c.arc(f, p, d, -n, -i, g),
                        c.closePath(),
                        c.stroke(),
                    a && c.fill()
                },
                drawBar: function(e, t, r, o) {
                    var n, i = this, a = i.ctx, l = [{
                        x: e.left,
                        y: e.bottom
                    }, {
                        x: e.left,
                        y: e.top
                    }, {
                        x: e.right,
                        y: e.top
                    }, {
                        x: e.right,
                        y: e.bottom
                    }];
                    i.selectFillColor(r),
                        i.selectPen(t),
                        a.fillRect(e.left, e.top, e.Width(), e.Height()),
                        a.beginPath(),
                        n = l[o],
                        a.moveTo(n.x, n.y),
                        n = l[++o % 4],
                        a.lineTo(n.x, n.y),
                        n = l[++o % 4],
                        a.lineTo(n.x, n.y),
                        n = l[++o % 4],
                        a.lineTo(n.x, n.y),
                        a.stroke(),
                        i.restorePen(),
                        i.restoreFillStyle()
                },
                DrawHorzLine: function(e, t, r) {
                    this.drawLine(t, e, r, e)
                },
                DrawVertLine: function(e, t, r) {
                    this.drawLine(e, t, e, r)
                },
                DrawPointMarker: function(t, r, o, n) {
                    function i() {
                        var e, o = 10 === r ? 4 : 11 === r ? 5 : 12 === r ? 6 : 10, n = 2 * Math.PI / o, i = n / 2, a = t.Width() / 2, l = a / 2, s = t.left + a, u = t.top + a, d = Math.PI / 2, h = [];
                        for (e = 0; o > e; e++)
                            h.push(s + Math.cos(d) * a, u - Math.sin(d) * a),
                                d += i,
                                h.push(s + Math.cos(d) * l, u - Math.sin(d) * l),
                                d += i;
                        c.drawPolyLine(h, 1, 1)
                    }
                    function a() {
                        var e, i = o && u;
                        i && (e = o.clone(),
                            e.Color = n,
                            c.selectPen(e)),
                            c.drawLine(t.left, t.top, t.right, t.bottom),
                            c.drawLine(t.left, t.bottom, t.right, t.top),
                        9 === r && (l = (t.left + t.right) / 2,
                            c.drawLine(l, t.top, l, t.bottom),
                            s = (t.top + t.bottom) / 2,
                            c.drawLine(t.left, s, t.right, s)),
                        i && c.restorePen()
                    }
                    var l, s, c = this, u = n !== e, d = t.Width(), h = Math.min(4, (d - 4) / 2);
                    switch (u && c.selectFillColor(n),
                    o && c.selectPen(o),
                        r) {
                        case 3:
                        case 4:
                        case 5:
                            c.ellipse(t.left, t.top, t.Width(), t.Height(), 1),
                                4 === r ? c.circle((t.left + t.right) / 2 - 1, (t.top + t.bottom) / 2 - 1, 2, 2, 0) : 5 === r && (d = Math.min(4, d / 3),
                                    c.drawLine(t.left + d, t.top + d, t.right - d, t.bottom - d),
                                    c.drawLine(t.right - d, t.top + d, t.left + d, t.bottom - d));
                            break;
                        case 0:
                        case 1:
                        case 2:
                            c.rectangle(t.left, t.top, t.Width(), t.Height(), 1),
                                1 === r ? c.drawPolyLine([t.left + h, t.top + d / 2, t.left + d / 2, t.bottom - h, t.right - h, t.top + h]) : 2 === r && (c.drawLine(t.left + h, t.top + h, t.right - h, t.bottom - h),
                                    c.drawLine(t.right - h, t.top + h, t.left + h, t.bottom - h));
                            break;
                        case 8:
                        case 9:
                            a();
                            break;
                        case 6:
                            c.drawPolyLine([(t.left + t.right) / 2, t.top, t.left, (t.top + t.bottom) / 2, (t.left + t.right) / 2, t.bottom, t.right, (t.top + t.bottom) / 2], 1, 1);
                            break;
                        case 7:
                            c.drawPolyLine([(t.left + t.right) / 2, t.top, t.left, t.bottom, t.right, t.bottom], 1, 1);
                            break;
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            i()
                    }
                    o && c.restorePen(),
                    u && c.restoreFillStyle()
                },
                _applyPen: function() {
                    var e = this
                        , t = e.ctx
                        , r = e.pens
                        , o = r[r.length - 1];
                    t.lineWidth = o.Width,
                        t.strokeStyle = w(o.Color),
                        t.setLineDash(o.getDashStyle())
                }
            },
            n.Graphics = function(e) {
                var t = this;
                t.report = e,
                    t.ByUnit = 0
            }
            ,
            n.Graphics.prototype = {
                toPixels: function(e) {
                    var t = this;
                    return t.ByUnit ? t.report.UnitToPixels(e) : e
                },
                pixelsTo: function(e) {
                    var t = this;
                    return t.ByUnit ? t.report.PixelsToUnit(e) : e
                },
                get Left() {
                    return 0
                },
                get Top() {
                    return 0
                },
                get Width() {
                    var e = this;
                    return e.pixelsTo(e.ctx.width)
                },
                get Height() {
                    var e = this;
                    return e.pixelsTo(e.ctx.height)
                },
                attach: function(e) {
                    this.ctx = e
                },
                MoveTo: function(e, t) {
                    var r = this;
                    r.ctx.moveTo(r.toPixels(e), r.toPixels(t))
                },
                LineTo: function(e, t) {
                    var r = this;
                    r.ctx.lineTo(r.toPixels(e), r.toPixels(t))
                },
                FillRect: function(e, t, r, o, n) {
                    var i = this;
                    i.ctx.fillRect(i.toPixels(e), i.toPixels(t), i.toPixels(r), i.toPixels(o), n)
                },
                Rectangle: function(e, t, r, o, n) {
                    var i = this;
                    i.ctx.rectangle(i.toPixels(e), i.toPixels(t), i.toPixels(r), i.toPixels(o), n)
                },
                RoundRect: function(e, t, r, o, n, i, a) {
                    var l = this;
                    l.ctx.roundRectangle(l.toPixels(e), l.toPixels(t), l.toPixels(r), l.toPixels(o), l.toPixels(n), l.toPixels(i), a)
                },
                Ellipse: function(e, t, r, o, n) {
                    var i = this;
                    i.ctx.ellipse(i.toPixels(e), i.toPixels(t), i.toPixels(r), i.toPixels(o), n)
                },
                Pie: function(e, t, r, o, n, i) {
                    var a = this;
                    a.ctx.pie(a.toPixels(e), a.toPixels(t), a.toPixels(r), o, n, i)
                },
                Arc: function(e, t, r, o, n) {
                    var i = this;
                    i.ctx.arc(i.toPixels(e), i.toPixels(t), i.toPixels(r), o, n)
                },
                EllipseArc: function(e, t, r, o, n, i) {
                    var a = this;
                    a.ctx.ellipseArc(a.toPixels(e), a.toPixels(t), a.toPixels(r), a.toPixels(o), n, i)
                },
                EllipsePie: function(e, t, r, o, n, i, a) {
                    var l = this;
                    l.ctx.ellipsePie(l.toPixels(e), l.toPixels(t), l.toPixels(r), l.toPixels(o), n, i, a)
                },
                DrawLabelText: function(e, t, r) {
                    var o = this;
                    o.ctx.drawText(e, o.toPixels(t), o.toPixels(r))
                },
                DrawRotateText: function(e, t, r, o) {
                    var n = this;
                    n.ctx.drawTextRotate(e, n.toPixels(t), n.toPixels(r), o)
                },
                DrawText: function(e, t, r, o, n, i, a) {
                    var l = this;
                    l.ctx.drawTextAlign(e, l.toPixels(t), l.toPixels(r), l.toPixels(o), l.toPixels(n), i)
                },
                DrawFormatText: function(e, t, r, o, n, i) {
                    var a = this;
                    a.ctx.drawFormatText(e, a.toPixels(t), a.toPixels(r), a.toPixels(o), a.toPixels(n), i)
                },
                DrawFormatTextShrinkToFit: function(e, t, r, o, n, i) {
                    var a = this;
                    a.ctx.drawTextAlign(e, a.toPixels(t), a.toPixels(r), a.toPixels(o), a.toPixels(n), i.TextAlign, 1)
                },
                CalcLabelTextWidth: function(e) {
                    var t = this;
                    return t.pixelsTo(t.ctx.measureTextWidth(e))
                },
                CalcLabelTextHeight: function(e) {
                    var t = this;
                    return t.pixelsTo(t.ctx.measureTextHeight(e))
                },
                CalcTextOutLen: function(e, t, r, o) {
                    return r.length
                },
                CalcDrawFormatTextHeight: function(e, t, r) {
                    var o = this;
                    return o.pixelsTo(o.ctx.measureTextHeight(e))
                },
                CalcDrawFormatTextWidth: function(e, t) {
                    var r = this;
                    return r.pixelsTo(r.ctx.measureTextWidth(e))
                },
                CalcDrawFormatTextOutLen: function(e, t, r, o) {
                    return e.length
                },
                DrawPicture: function(e, t, r, o, n, i, a, l) {},
                SelectFont: function(e) {
                    this.ctx.selectFont(e.font)
                },
                RestoreFont: function() {
                    this.ctx.restoreFont()
                },
                FontPointChangeTo: function(e) {
                    this.ctx.fontSizeTo(e)
                },
                FontSizeRestore: function() {
                    this.ctx.restoreFont()
                },
                SelectTextColor: function(e) {
                    this.ctx.selectFillColor(e)
                },
                RestoreTextColor: function() {
                    this.ctx.restoreFillStyle()
                },
                SelectPen: function(e, t, r) {
                    this.ctx.selectPen2(e, t, r)
                },
                RestorePen: function() {
                    this.ctx.restorePen()
                },
                SelectFillColor: function(e) {
                    this.ctx.selectFillColor(e)
                },
                RestoreFillColor: function() {
                    this.ctx.restoreFillStyle()
                },
                PushClipRect: function(e, t, r, o) {
                    var n = this;
                    n.ctx.pushClipRect(n.toPixels(e), n.toPixels(t), n.toPixels(r), n.toPixels(o))
                },
                PopClipRect: function() {
                    this.ctx.popClipRect()
                },
                CurveTo: function(e, t, r, o, n, i) {
                    var a = this;
                    a.ctx.bezierCurveTo(a.toPixels(e), a.toPixels(t), a.toPixels(r), a.toPixels(o), a.toPixels(n), a.toPixels(i))
                },
                CalcCurveControlPoints: function(e, t, r, o, n, i) {
                    var a = this
                        , l = .4
                        , s = Math.sqrt(Math.pow(r - e, 2) + Math.pow(o - t, 2))
                        , c = Math.sqrt(Math.pow(n - r, 2) + Math.pow(i - o, 2))
                        , u = s + c
                        , d = 1
                        , h = 1;
                    u > 0 && (d = l * s / u,
                        h = l * c / u),
                        a.CCPInnerX = r - d * (n - e),
                        a.CCPInnerY = o - d * (i - t),
                        a.CCPOuterX = r + h * (n - e),
                        a.CCPOuterY = o + h * (i - t)
                },
                DrawPointMarker: function(e, t, r, o) {
                    var n = this;
                    t = n.toPixels(t),
                        r = n.toPixels(r),
                        o = n.toPixels(o / 2),
                        n.ctx.DrawPointMarker(new V(t - o,r - o,t + o,r + o), e)
                },
                BeginPath: function() {
                    this.ctx.beginPath()
                },
                ClosePath: function() {
                    this.ctx.closePath()
                },
                EndPath: function() {},
                StrokePath: function() {
                    this.stroke()
                },
                FillPath: function() {
                    this.fill()
                },
                StrokeAndFillPath: function() {
                    this.strokefill()
                }
            },
            n.HtmlStyles = function() {
                this.items = []
            }
            ,
            n.HtmlStyles.prototype = {
                add: function(e, t) {
                    this.items.push({
                        name: e,
                        value: t
                    })
                },
                addBackColor: function(e) {
                    this.add("background-color", w(e))
                },
                addCellBorder: function(e) {
                    var t = this;
                    e.ShowColLine && e.ShowRowLine && A(e.ColLinePen, e.RowLinePen) ? t.add("border", d(e.ColLinePen)) : (e.ShowRowLine && t.add("border-bottom", d(e.ColLinePen)),
                    e.ShowColLine && t.add("border-right", d(e.RowLinePen)))
                },
                addObjectPadding: function(e) {
                    var t = e.padding
                        , r = "";
                    return t.Right === t.Left && t.Top === t.Bottom ? (t.Right !== t.Top && (r = g(t.Top) + " "),
                        r += g(t.Right)) : r = g(t.Top) + " " + g(t.Right) + " " + g(t.Bottom) + " " + g(t.Left),
                        this.add("padding", r)
                },
                addBorder: function(e) {
                    var t, r = this;
                    e.Styles && (t = d(e.Pen),
                        15 === e.Styles ? r.add("border", t) : (1 & e.Styles && r.add("border-left", t),
                        4 & e.Styles && r.add("border-right", t),
                        2 & e.Styles && r.add("border-top", t),
                        8 & e.Styles && r.add("border-bottom", t))),
                    e.Shadow && (t = g(e.ShadowWidth) + " ",
                        r.add("box-shadow", t + t + w(e.ShadowColor)))
                },
                addTextFormat: function(e, t) {
                    var r, o = this, n = e.TextFormat, i = n.LineSpacing, a = "text-align", l = n.WordWrap;
                    1 & n.TextAlign ? o.add(a, "left") : 2 & n.TextAlign ? o.add(a, "center") : 4 & n.TextAlign ? o.add(a, "right") : o.add(a, "justify"),
                    t && (a = "vertical-align",
                        16 & n.TextAlign ? o.add(a, "top") : 32 & n.TextAlign ? o.add(a, "middle") : o.add(a, "bottom")),
                    n.EndEllipsis && (o.add("text-overflow", "ellipsis"),
                        l = !1),
                        l ? o.add("white-space", "normal") : (o.add("overflow", "hidden"),
                            o.add("white-space", "nowrap")),
                    n.CharacterSpacing && o.add("letter-spacing", g(n.CharacterSpacing)),
                    i && (r = h(e.obj.getUsingFont()),
                        o.add("line-height", p((r + i) / r))),
                    n.FirstCharIndent && o.add("text-indent", g(n.FirstCharIndent)),
                    D(n.ForeColor) && o.add("color", w(n.ForeColor))
                },
                getText: function() {
                    var e = "";
                    return this.items.forEach(function(t) {
                        e += t.name + ":" + t.value + ";"
                    }),
                        e
                },
                clear: function() {
                    this.items = []
                }
            };
        var G = n.HtmlStyles;
        n.HtmlElement = function(e, t) {
            var r = this;
            r.tag = e,
                r.classes = [],
                t.addChild(this)
        }
            ,
            n.HtmlElement.prototype = {
                get styles() {
                    var e = this;
                    return e._styles || (e._styles = new G),
                        e._styles
                },
                addClass: function(e) {
                    this.classes.push(e)
                },
                addStyle: function(e, t) {
                    this.styles.add(e, t)
                },
                addBackColorStyle: function(e) {
                    this.addStyle("background-color", w(e))
                },
                addAttribute: function(e, t) {
                    var r = this;
                    r._attributes || (r._attributes = []),
                        r._attributes.push({
                            name: e,
                            value: t
                        })
                },
                addChild: function(e) {
                    var t = this;
                    t.children || (t.children = []),
                        t.children.push(e)
                },
                beginText: function() {
                    var t = this
                        , r = "<" + t.tag;
                    return t.classes.length > 0 && (r += ' class="',
                        t.classes.forEach(function(e, t) {
                            t > 0 && (r += " "),
                                r += e
                        }),
                        r += '"'),
                    t._styles && (r += ' style="' + t._styles.getText() + '"'),
                    t._attributes && t._attributes.forEach(function(t) {
                        var o = t.value;
                        r += " " + t.name,
                        o !== e && (r += '="' + o + '"')
                    }),
                    t.skipend && (r += "/"),
                        r += ">"
                },
                endText: function() {
                    var e = this;
                    return e.skipend ? "" : "</" + e.tag + ">"
                }
            }
    }(),
    function(e) {
        "use strict";
        gr.wr = {
            WRZipSource: ['":"', '","', '"},{"', '":{"', '},{"', '":', ',"', "false"],
            WRZipDest: "!$%&@^`~",
            WRAsciiMap_R: "r<~VH6SAZ?yiO %1#Ek>]`+|F_t/$RgQnX=YbqL)7uU9,oaB5}-'ev4;JD.2WsP@3zhl\"cfC^(K0mx!&Td{*GM:[pIj8\\Nw",
            propNameMap: {
                AlternatingBackColor: "G",
                BackColor: "Q",
                BarcodeType: "b",
                BorderCustom: "i",
                CharSpacing: "x",
                DBFieldName: "WA",
                FreeCell: "AB",
                GroupLabel: "OB",
                GroupTitle: "PB",
                Height: "ZB",
                IsCrossTab: "jB",
                LeftMargin: "tB",
                LnSpacing: "JC",
                Name: "YC",
                Oriention: "eC",
                RightMargin: "qD",
                SeriesLabel: "HE",
                Type: "LF",
                Value: "UF",
                Width: "eF"
            },
            propNameMap_R: {
                A: "AdjustRowHeight",
                B: "AlignColumn",
                C: "AlignColumnEx",
                D: "AlignColumnSide",
                E: "AlignToGrid",
                F: "Alignment",
                G: "AlternatingBackColor",
                H: "AlwaysShowHScrollBar",
                I: "AlwaysShowVScrollBar",
                J: "Anchor",
                K: "AppendBlankCol",
                L: "AppendBlankColWidth",
                M: "AppendBlankRow",
                N: "AppendBlankRowAtLast",
                O: "AppendBlankRowExclude",
                P: "Author",
                Q: "BackColor",
                R: "BackImage",
                S: "BackImageFile",
                T: "BackImagePreview",
                U: "BackImagePrint",
                V: "BackStyle",
                W: "BackgroundColor",
                X: "BarRatio",
                Y: "BarSingleSeriesColor",
                Z: "BarWidth",
                a: "BarWidthPercent",
                b: "BarcodeType",
                c: "BeforePostRecordScript",
                d: "BeforeSortScript",
                e: "BeginDateParameter",
                f: "Bold",
                g: "BookmarkText",
                h: "BorderColor",
                i: "BorderCustom",
                j: "BorderPrintType",
                k: "BorderStyles",
                l: "BorderWidth",
                m: "BottomMargin",
                n: "BubbleScalePerCm",
                o: "ByFields",
                p: "ByY2Axis",
                q: "CanGrow",
                r: "CanShrink",
                s: "CaptionAlignment",
                t: "CaptionPosition",
                u: "Center",
                v: "CenterView",
                w: "CenterWithDetailGrid",
                x: "CharSpacing",
                y: "CharacterSpacing",
                z: "Charset",
                AA: "Chart3D",
                BA: "ChartType",
                CA: "CheckSum",
                DA: "CodePage",
                EA: "ColLineColor",
                FA: "ColLineWidth",
                GA: "ColSpan",
                HA: "Color",
                IA: "Column",
                JA: "ColumnCount",
                KA: "ColumnMove",
                LA: "ColumnResize",
                MA: "ConditionScript",
                NA: "ConnectionString",
                OA: "ContinuePrint",
                PA: "CoordLineColor",
                QA: "CoordLineVisible",
                RA: "CornerDx",
                SA: "CornerDy",
                TA: "Cursor",
                UA: "CustomDraw",
                VA: "CustomDrawScript",
                WA: "DBFieldName",
                XA: "DataField",
                YA: "DataName",
                ZA: "DataType",
                aA: "Description",
                bA: "Direction",
                cA: "DisabledSumFields",
                dA: "DisplayField",
                eA: "Dock",
                fA: "DtmxEncoding",
                gA: "DtmxMatrixSize",
                hA: "DtmxModuleSize",
                iA: "Editable",
                jA: "EndDateParameter",
                kA: "EndEllipsis",
                lA: "ExportBeginScript",
                mA: "ExportEndScript",
                nA: "FalseText",
                oA: "FetchRecordScript",
                pA: "FillColor",
                qA: "FillColorAuto",
                rA: "FillStyle",
                sA: "FirstCharIndent",
                tA: "FixCols",
                uA: "FixedWidth",
                vA: "FlowTo",
                wA: "FontWidthRatio",
                xA: "ForeColor",
                yA: "Format",
                zA: "FormatScript",
                AB: "FreeCell",
                BB: "GetDisplayTextScript",
                CB: "GlobalScript",
                DB: "GridColsPerUnit",
                EB: "GridLinePrintType",
                FB: "GridRowsPerUnit",
                GB: "GroupAuto",
                HB: "GroupAutoSum",
                IB: "GroupBeginScript",
                JB: "GroupCount",
                KB: "GroupEndScript",
                LB: "GroupField",
                MB: "GroupIndex",
                NB: "GroupKeepTogether",
                OB: "GroupLabel",
                PB: "GroupTitle",
                QB: "GrowToBottom",
                RB: "GrowToNextRow",
                SB: "HCrossFields",
                TB: "HCrossPeriodType",
                UB: "HNewPageFixed",
                VB: "HPercentColumns",
                WB: "HResort",
                XB: "HSortAsc",
                YB: "HTotalAtFirst",
                ZB: "Height",
                aB: "HideOnRecordsetEmpty",
                bB: "HtmlTags",
                cB: "Image",
                dB: "ImageFile",
                eB: "ImageIndex",
                fB: "IncludeFooter",
                gB: "InitializeScript",
                hB: "InnerIndent",
                iB: "InnerStyles",
                jB: "IsCrossTab",
                kB: "Italic",
                lB: "KeepTogether",
                mB: "L2R",
                nB: "Label",
                oB: "LabelAsGroup",
                pB: "LabelInBar",
                qB: "LabelText",
                rB: "LabelTextAngle",
                sB: "Left",
                tB: "LeftMargin",
                uB: "LegendAtBottom",
                vB: "LegendColumnCount",
                wB: "LegendCursor",
                xB: "LegendShowSum",
                yB: "LegendSumLabel",
                zB: "LegendValueVisible",
                AC: "LegendVisible",
                BC: "Length",
                CC: "LimitsPerPage",
                DC: "LineColor",
                EC: "LineSpacing",
                FC: "LineType",
                GC: "LineVisible",
                HC: "LineWeight",
                IC: "ListCols",
                JC: "LnSpacing",
                KC: "Lock",
                LC: "Locked",
                MC: "MarginBeginWeight",
                NC: "MarginEndWeight",
                OC: "MarginGap",
                PC: "MarkerColor",
                QC: "MarkerColorAuto",
                RC: "MarkerLegendShow",
                SC: "MarkerSize",
                TC: "MarkerStyle",
                UC: "Max",
                VC: "Min",
                WC: "MirrorMargins",
                XC: "MonoPrint",
                YC: "Name",
                ZC: "NegativeAsZero",
                aC: "NewPage",
                bC: "NewPageColumn",
                cC: "OccupiedColumns",
                dC: "OccupyColumn",
                eC: "Oriention",
                fC: "PDF417Columns",
                gC: "PDF417ErrorLevel",
                hC: "PDF417Rows",
                iC: "PDF417Simple",
                jC: "PaddingBottom",
                kC: "PaddingLeft",
                lC: "PaddingRight",
                mC: "PaddingTop",
                nC: "PageColumnCount",
                oC: "PageColumnDirection",
                pC: "PageColumnGroupNA",
                qC: "PageColumnSpacing",
                rC: "PageDivideCount",
                sC: "PageDivideLine",
                tC: "PageDivideSpacing",
                uC: "PageEndScript",
                vC: "PageGroup",
                wC: "PageProcessRecordScript",
                xC: "PageStartScript",
                yC: "ParagraphSpacing",
                zC: "Parameter",
                AD: "ParentPageSettings",
                BD: "PenColor",
                CD: "PenStyle",
                DD: "PenWidth",
                ED: "PercentFormat",
                FD: "Picture",
                GD: "Pitch",
                HD: "PointWeight",
                ID: "PrintAdaptFitText",
                JD: "PrintAdaptMethod",
                KD: "PrintAdaptRFCStep",
                LD: "PrintAdaptRepeat",
                MD: "PrintAdaptTryToOnePage",
                ND: "PrintAsDesignPaper",
                OD: "PrintAtBottom",
                PD: "PrintBeginScript",
                QD: "PrintEndScript",
                RD: "PrintGridBorder",
                SD: "PrintOffsetSaveToLocal",
                TD: "PrintOffsetX",
                UD: "PrintOffsetY",
                VD: "PrintPageScript",
                WD: "PrintRotation",
                XD: "PrintToStretch",
                YD: "PrintType",
                ZD: "PrinterName",
                aD: "ProcessBeginScript",
                bD: "ProcessEndScript",
                cD: "ProcessRecordScript",
                dD: "QRCodeErrorLevel",
                eD: "QRCodeMask",
                fD: "QRCodeVersion",
                gD: "QuerySQL",
                hD: "RTF",
                iD: "RTrimBlankChars",
                jD: "RankNo",
                kD: "RegisterNo",
                lD: "RelationFields",
                mD: "RepeatOnPage",
                nD: "RepeatStyle",
                oD: "ReportFile",
                pD: "ResetPageNumber",
                qD: "RightMargin",
                rD: "RotateMode",
                sD: "RowCount",
                tD: "RowLineColor",
                uD: "RowLineWidth",
                vD: "RowSelection",
                wD: "RowSpan",
                xD: "RowsIncludeGroup",
                yD: "RowsPerPage",
                zD: "SameAsColumn",
                AE: "ScriptType",
                BE: "SelectionBackColor",
                CE: "SelectionForeColor",
                DE: "SeriesAuto",
                EE: "SeriesCount",
                FE: "SeriesCursor",
                GE: "SeriesField",
                HE: "SeriesLabel",
                IE: "SeriesName",
                JE: "Shadow",
                KE: "ShadowColor",
                LE: "ShadowWidth",
                ME: "ShapeType",
                NE: "SharePrintSetupOptions",
                OE: "ShiftMode",
                PE: "ShowColLine",
                QE: "ShowGrid",
                RE: "ShowMoneyDigit",
                SE: "ShowMoneyLabel",
                TE: "ShowMoneyLine",
                UE: "ShowMoneyLineColor",
                VE: "ShowMoneySepLineColor",
                WE: "ShowMoneyWidth",
                XE: "ShowPreviewWndScript",
                YE: "ShowRowLine",
                ZE: "ShrinkFontToFit",
                aE: "SingleSeriesColored",
                bE: "Size",
                cE: "SizeMode",
                dE: "SkipQuery",
                eE: "SortAsc",
                fE: "SortCaseSensitive",
                gE: "SortFields",
                hE: "SortSummaryBox",
                iE: "Space",
                jE: "SpanToNewPage",
                kE: "Strikethrough",
                lE: "Style",
                mE: "Styles",
                nE: "SubtotalCols",
                oE: "SummaryFun",
                pE: "SystemVar",
                qE: "Tag",
                rE: "Text",
                sE: "TextAlign",
                tE: "TextAngle",
                uE: "TextFormat",
                vE: "TextOrientation",
                wE: "TextVisible",
                xE: "Title",
                yE: "TitleRepeat",
                zE: "TitleRows",
                AF: "ToNewExcelSheet",
                BF: "TooltipText",
                CF: "Top",
                DF: "TopMargin",
                EF: "TotalCols",
                FF: "TotalExcludeColumns",
                GF: "TotalHPercentColumns",
                HF: "TotalVPercentColumns",
                IF: "Transparent",
                JF: "TransparentMode",
                KF: "TrueText",
                LF: "Type",
                MF: "U2D",
                NF: "Underline",
                OF: "Unit",
                PF: "VAlign",
                QF: "VCrossFields",
                RF: "VPercentColumns",
                SF: "VResort",
                TF: "VSortAsc",
                UF: "Value",
                VF: "ValueAsPercent",
                WF: "ValueFormat",
                XF: "ValueVisible",
                YF: "Version",
                ZF: "Visible",
                aF: "Watermark",
                bF: "WatermarkAlignment",
                cF: "WatermarkSizeMode",
                dF: "Weight",
                eF: "Width",
                fF: "WordWrap",
                gF: "XAxisLabel",
                hF: "XAxisMaximum",
                iF: "XAxisMinimum",
                jF: "XAxisSpace",
                kF: "XAxisTextAngle",
                lF: "XAxisTextFormat",
                mF: "XAxisTextVisible",
                nF: "XAxisVisible",
                oF: "XValueField",
                pF: "XmlTableName",
                qF: "YAxisLabel",
                rF: "YAxisMaximum",
                sF: "YAxisMinimum",
                tF: "YAxisSpace",
                uF: "YAxisTextFormat",
                vF: "YAxisTextVisible",
                wF: "YAxisVisible",
                xF: "YValueField",
                yF: "ZValueField",
                zF: "ZValueFormat"
            },
            wrPropNameEncode: function(e) {
                var t = gr.wr.propNameMap[e];
                return t ? t : e
            },
            wrPropNameDecode: function(e) {
                var t = gr.wr.propNameMap_R[e];
                return t ? t : e
            },
            decodeWR: function(e) {
                function t(e) {
                    return e >= " " && "~" >= e && (e = d[e.charCodeAt(0) - 32]),
                        e
                }
                for (var r, o, n, i, a, l = "", s = 4, c = e.length, u = gr.wr, d = u.WRAsciiMap_R, h = u.WRZipSource, f = u.WRZipDest, p = []; c > s; )
                    l += t(e[s++]);
                for (s = 0,
                         c = 0,
                         i = l[s]; "[" == i; ) {
                    for (a = {},
                             r = ++s; "," != i && (i = l[++s],
                        !("0" > i || i > "9")); )
                        ;
                    if ("," != i)
                        break;
                    for (a.f = +l.substring(r, s),
                             r = ++s; "]" != i && (i = l[++s],
                        !("0" > i || i > "9")); )
                        ;
                    if ("]" != i)
                        break;
                    a.s = +l.substring(r, s++),
                        p.push(a),
                        c = s,
                        i = l[s]
                }
                for (e = l.substr(c).split(""),
                         a = p.length; a-- > 0; )
                    o = p[a].f,
                        n = p[a].s,
                        i = e[o],
                        e[o] = e[n],
                        e[n] = i;
                for (c = e.length,
                         l = "",
                         s = 0; c > s; s++)
                    i = e[s],
                        a = f.indexOf(i),
                        a >= 0 ? l += h[a] : ("#" == i && (i = e[++s]),
                            l += i);
                return l
            }
        }
    }(),
    function(e) {
        "use strict";
        var t = (gr.enum_,
            gr.const_)
            , r = gr.common
            , o = gr.locale
            , n = gr.helper
            , i = r.DateTime
            , a = n.intFixed
            , l = n.confirmDateValue
            , s = n.confirmBooleanValue
            , c = n.rgba2color
            , u = n.color2rgba
            , d = n.decodeTextColor
            , h = gr.format = {};
        h.BooleanFormatter = function(e) {
            this.parse(e)
        }
            ,
            h.BooleanFormatter.prototype = {
                parse: function(e) {
                    var t, r = this;
                    e ? (t = e.indexOf(":"),
                        -1 === t ? (r.trueValueText = e,
                            r.falseValueText = "") : (r.trueValueText = e.substr(0, t),
                            r.falseValueText = e.substr(t + 1))) : (r.trueValueText = "true",
                        r.falseValueText = "false")
                },
                format: function(e) {
                    var t = this;
                    return e = s(e),
                        e ? t.trueValueText : t.falseValueText
                }
            };
        var f = function() {
            this.defaultMe()
        };
        f.prototype = {
            defaultMe: function() {
                var e = this;
                e.precision = 9,
                    e.leastPrecision = 0,
                    e.leastIntegerWidth = 1,
                    e.showPositive = !1,
                    e.showNegative = !0,
                    e.showSymbolAtHead = !0,
                    e.negativeBracket = !1,
                    e.intSepStep = 0,
                    e.asPercent = !1,
                    e.asCurrency = !1,
                    e.blankSepSymbol = !1,
                    e.firstCurrencySymbol = !0,
                    e.exponent = !1,
                    e.textColor = 0,
                    e.prefix = "",
                    e.suffix = ""
            },
            isCustomTextColor: function() {
                return 0 !== this.textColor
            },
            parse: function(e) {
                function t(e) {
                    return -1 !== "0#.,+-()$%".indexOf(e)
                }
                var r, o, n, i, a, l = this;
                if (l.defaultMe(),
                    e) {
                    if (r = d(e),
                    r && (e = e.substr(r.index),
                        l.textColor = r.color),
                        a = e.length,
                        l.exponent = "e" === e.charAt(0),
                        !l.exponent) {
                        for (o = 0; a > o && !t(e.charAt(o)); )
                            ++o;
                        if (o > 0 && (l.prefix = e.substr(0, o),
                            e = e.substr(o),
                            a = e.length),
                        a > o) {
                            for (o = a - 1; !t(e.charAt(o)) && o > 0; )
                                --o;
                            a - 1 > o && (l.suffix = e.substr(o + 1),
                                e = e.substr(0, o + 1),
                                a = e.length)
                        }
                    }
                    for (i = e.indexOf("."),
                         -1 === i && (i = a),
                             o = e.indexOf(","),
                         o >= 0 && (l.intSepStep = i - o - 1,
                         l.intSepStep < 0 && (l.intSepStep = 3)),
                             l.negativeBracket = e.indexOf("(") >= 0 && e.indexOf(")") >= 0,
                             l.asPercent = e.indexOf("%") >= 0,
                             l.blankSepSymbol = e.indexOf(" ") >= 0,
                             o = e.indexOf("+"),
                             l.showPositive = o >= 0,
                             n = e.indexOf("-"),
                             l.showNegative = n >= 0 || !l.negativeBracket && !l.isCustomTextColor(),
                         n > o && (o = n),
                             n = e.indexOf("$"),
                             l.asCurrency = n >= 0,
                         n > o && (o = n),
                             l.showSymbolAtHead = -1 === o || i > o,
                         l.asCurrency && (l.negativeBracket ? (o = e.indexOf(l.showSymbolAtHead ? "(" : ")"),
                             l.firstCurrencySymbol = o > n) : l.firstCurrencySymbol = o >= n),
                             l.leastIntegerWidth = 0,
                             o = 0; i > o; ++o)
                        "0" === e.charAt(o) && ++l.leastIntegerWidth;
                    for (l.leastPrecision = 0,
                             l.precision = 0,
                             o = i + 1; a > o; ++o)
                        "0" === e.charAt(o) ? (++l.leastPrecision,
                            ++l.precision) : "#" === e.charAt(o) && ++l.precision
                }
            },
            format: function(e) {
                var t, r, n, i, a, l, s, c, d, h, f, p = this, g = "";
                if (e = +e,
                p.isCustomTextColor() && (t = u(p.textColor),
                    g = "[_color#=rgb(" + t.r + "," + t.g + "," + t.b + ")]"),
                p.asPercent && (e *= 100),
                    p.exponent) {
                    if (r = e,
                    0 > r && (r = -r),
                        n = 0,
                    0 !== r) {
                        for (; r >= 10; )
                            r /= 10,
                                ++n;
                        for (; 1 > r; )
                            r *= 10,
                                --n
                    }
                    return p.exponent = !1,
                    0 > e && (r = -r),
                        g += p.format(r),
                        g += "e",
                        n >= 0 ? g += "+" : (g += "-",
                            n = -n),
                        g += n,
                        p.exponent = !0,
                        g
                }
                if (f = e.toFixed(p.precision),
                    h = f.length,
                p.precision > p.leastPrecision) {
                    for (i = h - 1,
                             a = i - p.precision + p.leastPrecision; i > a && "0" === f.charAt(i); i--)
                        ;
                    "." !== f.charAt(i) && i++,
                    i < f.length && (f = f.substr(0, i),
                        h = i)
                }
                if (l = f.indexOf("."),
                0 > l && (l = h),
                    g += p.prefix,
                    p.showSymbolAtHead ? (p.asCurrency && p.firstCurrencySymbol && (g += o.currencySymbol),
                        0 > e ? p.negativeBracket && (g += "(") : p.showPositive && e > 0 && (g += "+"),
                    p.asCurrency && !p.firstCurrencySymbol && (g += o.currencySymbol),
                    p.blankSepSymbol && (g += " ")) : p.negativeBracket && 0 > e && (g += "("),
                    i = 0,
                    s = l,
                0 > e && (--s,
                    ++i,
                p.showNegative && (g += "-")),
                    c = p.leastIntegerWidth - s,
                    d = s,
                c > 0)
                    for (d += c; d > s; )
                        g += "0",
                            d--,
                        p.intSepStep > 0 && d % p.intSepStep === 0 && (g += o.thousandSep);
                else
                    for (; c++ < 0 && "0" === f.charAt(i); )
                        i++;
                if (p.intSepStep > 0)
                    for (; d > 0; )
                        g += f.charAt(i++),
                        --d % p.intSepStep === 0 && 0 !== d && (g += o.thousandSep);
                else
                    g += f.substring(i, l);
                return h > l && (g += f.substr(l)),
                    p.showSymbolAtHead ? p.negativeBracket && 0 > e && (g += ")") : (p.blankSepSymbol && (g += " "),
                    p.asCurrency && p.firstCurrencySymbol && (g += o.currencySymbol),
                        0 > e ? g += p.negativeBracket ? ")" : "-" : p.showPositive && e > 0 && (g += "+"),
                    p.asCurrency && !p.firstCurrencySymbol && (g += o.currencySymbol)),
                p.asPercent && (g += "%"),
                    g += p.suffix
            }
        };
        var p = function(e) {
            e = e.substring(1, e.length - 1);
            for (var t = e.split(","), r = 0; r < t.length; r++) {
                var o = t[r]
                    , n = o.indexOf("=");
                this[o.substr(0, n)] = o.substr(n + 1)
            }
        };
        p.prototype = {
            format: function(t) {
                var r = this[t];
                return r === e && (r = ""),
                    r
            }
        };
        var g = "$$"
            , m = "$$$";
        h.NumericFormatter = function(e) {
            this.parse(e)
        }
            ,
            h.NumericFormatter.prototype = {
                defaultMe: function() {
                    var t = this;
                    t.formatType = 0,
                        t.positiveAnalyser = new f,
                    t.negativeAnalyser && (t.negativeAnalyser = e),
                    t.zeroAnalyser && (t.zeroAnalyser = e),
                    t.intEnumAnalyser && (t.intEnumAnalyser = e)
                },
                parse: function(e) {
                    function t(e) {
                        var t, r;
                        t = e.split(";"),
                            r = t.length,
                            o.positiveAnalyser.parse(t[0]),
                        r > 1 && "" !== t[1] && (o.negativeAnalyser = new f,
                            o.negativeAnalyser.parse(t[1])),
                        r > 2 && "" !== t[2] && (o.zeroAnalyser = new f,
                            o.zeroAnalyser.parse(t[2]))
                    }
                    var r, o = this;
                    if (o.defaultMe(),
                        e) {
                        var n = e.length;
                        e && (r = e.indexOf(m),
                            -1 !== r ? (o.formatType = 1,
                                e = e.substr(0, r) + e.substr(r + m.length),
                            "" !== e && t(e)) : n >= 2 && e.substr(0, 2) === g ? 2 === n ? o.formatType = 2 : (o.formatType = 3,
                                t(e.substr(2))) : "{" === e.charAt(0) && "}" === e.charAt(n - 1) ? (o.formatType = 4,
                                o.intEnumAnalyser = new p(e)) : t(e))
                    }
                },
                format: function(r) {
                    function o(e, r) {
                        var o, n, i = !1, a = !1, l = 0, s = "";
                        for (o = r - 1; o >= 0; o--,
                            l++)
                            n = parseInt(e.charAt(o)),
                            l % 4 === 0 && l > 0 && (i = !0),
                                0 === n ? a = !0 : (a && ("" !== s && (s = t.BIG_AMT_NUMBER.charAt(0) + s),
                                    a = !1),
                                l > 0 && (i && l % 4 !== 0 && (s = t.HZ_AMT_STEP.charAt(l - l % 4) + s),
                                    i = !1,
                                    s = t.HZ_AMT_STEP.charAt(l) + s),
                                    s = t.BIG_AMT_NUMBER.charAt(n) + s);
                        return s
                    }
                    function n(e) {
                        var r, o, n = "", i = e.length;
                        for (o = 0; i > o; o++)
                            r = parseInt(e.charAt(o)),
                            isNaN(r) || (n += t.BIG_AMT_NUMBER.charAt(r));
                        return n
                    }
                    function i(e) {
                        var r, n, i, a, l, s = 0 >= e;
                        return e > -.005 && .005 > e ? t.HZ_ZERO_YUAN : (s && (e = -e),
                            a = e.toFixed(2),
                            r = a.indexOf("."),
                            l = o(a, r),
                        "" !== l && (l += t.HZ_AMT_STEP.charAt(0)),
                            n = parseInt(a.charAt(r + 1)),
                            i = parseInt(a.charAt(r + 2)),
                            0 === n && 0 === i ? l += t.HZ_AMT_UNIT[0] : (0 === n && "" === l || (l += t.BIG_AMT_NUMBER.charAt(n)),
                            0 !== n && (l += t.HZ_AMT_UNIT.charAt(1)),
                                0 !== i ? (l += t.BIG_AMT_NUMBER.charAt(i),
                                    l += t.HZ_AMT_UNIT.charAt(2)) : l += t.HZ_AMT_UNIT.charAt(0)),
                        s && (l = t.HZ_NEGATIVE + l),
                            l)
                    }
                    function a(e, r) {
                        var n, i, a, l, s = Math.max(0, r.precision), c = Math.min(s, r.leastPrecision), u = -.5 * Math.pow(.1, s), d = u >= e;
                        for (0 > e && (e = -e),
                                 a = e.toFixed(s),
                                 n = a.length,
                                 i = a.indexOf("."),
                             -1 === i && (i = n),
                                 l = o(a, i),
                             "" === l && (l = t.BIG_AMT_NUMBER.charAt(0)),
                                 n--; n > i + c && "0" === a.charAt(n); n--)
                            ;
                        if (n > i)
                            for (l += "点",
                                     i++; n >= i; i++)
                                l += t.BIG_AMT_NUMBER.charAt(parseInt(a.charAt(i)));
                        return d && (l = t.HZ_NEGATIVE + l),
                        "" !== r.prefix && (l = r.prefix + l),
                        "" !== r.suffix && (l += r.suffix),
                            l
                    }
                    var l, s = this;
                    if (r = +r,
                        isNaN(r))
                        l = "";
                    else if (s.formatType <= 1) {
                        if (s.zeroAnalyser && r > -.5 && .5 > r) {
                            var c = s.positiveAnalyser.precision;
                            s.positiveAnalyser.asPercent && (c += 2);
                            var u = c;
                            s.negativeAnalyser && (u = s.negativeAnalyser.precision,
                            s.negativeAnalyser.asPercent && (u += 2));
                            var d = Math.pow(.1, u) * -.5
                                , h = .5 * Math.pow(.1, c);
                            r > d && h > r && (l = s.zeroAnalyser.format(0))
                        }
                        l === e && (l = s.negativeAnalyser && 0 > r ? s.negativeAnalyser.format(r) : s.positiveAnalyser.format(r)),
                        1 === s.formatType && (l = n(l))
                    } else
                        l = 4 === s.formatType ? s.intEnumAnalyser.format(r) : 2 === s.formatType ? i(r) : a(r, s.positiveAnalyser);
                    return l
                }
            },
            h.DateTimeFormatter = function(e) {
                this.parse(e)
            }
            ,
            h.DateTimeFormatter.prototype = {
                parse: function(r) {
                    function n() {
                        for (w = 1; D[F + 1] === P; )
                            F++,
                                w++
                    }
                    function i() {
                        var e = w;
                        w %= 2,
                            e = (e - w) / 2,
                        e > 0 && l(e)
                    }
                    function l(e) {
                        var t = ""
                            , r = B.length;
                        do
                            t += P;
                        while (--e > 0);
                        r > 0 && 15 === B[r - 1].type ? B[r - 1].data += t : B.push({
                            type: 15,
                            data: t,
                            funDisplayText: v
                        })
                    }
                    function s(e, t) {
                        return a(e.getFullYear(), t.subtype)
                    }
                    function c(r, n) {
                        var i = r.getMonth();
                        return n.subtype <= 4 ? a(i + 1, n.subtype) : o.supportIntl ? 5 === n.subtype ? new Intl.DateTimeFormat(e,{
                            month: "short"
                        }).format(r) : new Intl.DateTimeFormat(e,{
                            month: "long"
                        }).format(r) : 10 > i ? t.HZ_NUMBER.charAt(i) : t.HZ_NUMBER.charAt(9) + t.HZ_NUMBER.charAt(i - 10) + t.HZ_DATETIME_UNIT.charAt(5)
                    }
                    function u(e, t) {
                        return a(e.getDate(), t.subtype)
                    }
                    function d(r, n) {
                        var i;
                        return o.supportIntl ? 5 === n.subtype ? new Intl.DateTimeFormat(e,{
                            weekday: "short"
                        }).format(r) : new Intl.DateTimeFormat(e,{
                            weekday: "long"
                        }).format(r) : (i = r.getDay(),
                        (5 === n.subtype ? t.HZ_DATETIME_UNIT.substr(0, 1) : t.HZ_DATETIME_UNIT.substring(2, 4)) + (i > 0 ? t.HZ_NUMBER.charAt(i - 1) : t.HZ_DATETIME_UNIT.charAt(1)))
                    }
                    function h(e, t) {
                        var r = e.getHours() % 12;
                        return r || (r = 12),
                            a(r, t.subtype)
                    }
                    function f(e, t) {
                        return a(e.getHours(), t.subtype)
                    }
                    function p(e, t) {
                        return a(e.getMinutes(), t.subtype)
                    }
                    function g(e, t) {
                        return a(e.getSeconds(), t.subtype)
                    }
                    function m(e, t) {
                        return e.toLocaleDateString()
                    }
                    function C(e, t) {
                        return e.toLocaleTimeString()
                    }
                    function b(e, t) {
                        return e.toLocaleString()
                    }
                    function v(e, t) {
                        return t.data
                    }
                    function x(e, t) {
                        return e.getHours() < 12 ? o.textAM : o.textPM
                    }
                    function T(e, t) {
                        return o.dateSep
                    }
                    function y(e, t) {
                        return o.timeSep
                    }
                    var S, F, w, D, P, R, A, B = this.items = [];
                    if (r) {
                        D = r.split(""),
                            S = D.length,
                            F = 0;
                        do {
                            switch (P = D[F],
                                n(),
                                A = {},
                                P) {
                                case "d":
                                    3 > w ? (A.type = 3,
                                        A.funDisplayText = u,
                                        A.subtype = 1 === w ? 0 : 2) : (A.type = 4,
                                        A.funDisplayText = d,
                                        A.subtype = 3 === w ? 5 : 6);
                                    break;
                                case "M":
                                    A.type = 2,
                                        A.funDisplayText = c,
                                        1 === w ? A.subtype = 0 : 2 === w ? A.subtype = 2 : 3 === w ? A.subtype = 5 : A.subtype = 6;
                                    break;
                                case "y":
                                    A.type = 1,
                                        A.funDisplayText = s,
                                        A.subtype = 3 > w ? 2 : 0;
                                    break;
                                case "h":
                                    A.type = 5,
                                        A.funDisplayText = h,
                                        A.subtype = 1 === w ? 0 : 2;
                                    break;
                                case "H":
                                    A.type = 6,
                                        A.funDisplayText = f,
                                        A.subtype = 1 === w ? 0 : 2;
                                    break;
                                case "m":
                                    A.type = 7,
                                        A.funDisplayText = p,
                                        A.subtype = 1 === w ? 0 : 2;
                                    break;
                                case "s":
                                    A.type = 8,
                                        A.funDisplayText = g,
                                        A.subtype = 1 === w ? 0 : 2;
                                    break;
                                case "t":
                                    2 === w && (A.type = 20,
                                        A.funDisplayText = x);
                                    break;
                                case "%":
                                    i(),
                                    w && (R = D[F + 1],
                                        ++F,
                                        "d" === R ? (A.type = 10,
                                            A.funDisplayText = m) : "t" === R ? (A.type = 11,
                                            A.funDisplayText = C) : "g" === R ? (A.type = 12,
                                            A.funDisplayText = b) : F--);
                                    break;
                                case ":":
                                    i(),
                                    w && (A.type = 22,
                                        A.funDisplayText = y);
                                    break;
                                case "/":
                                    i(),
                                    w && (A.type = 21,
                                        A.funDisplayText = T);
                                    break;
                                default:
                                    l(w)
                            }
                            A.type && 15 !== A.type && B.push(A)
                        } while (++F < S)
                    } else
                        B.push({
                            type: 12,
                            funDisplayText: b
                        })
                },
                format: function(t) {
                    var r = "";
                    return t !== e && (t = l(t),
                        this.items.forEach(function(e) {
                            r += e.funDisplayText(t, e)
                        })),
                        r
                }
            };
        var C = gr.utility = {
            CreateDateTime: function() {
                return new i
            },
            DateTimeFormat: function(e, t) {
                return new h.DateTimeFormatter(t).format(l(e))
            },
            NumberFormat: function(e, t) {
                return new h.NumericFormatter(t).format(e)
            },
            NumberFormatToBigHZ: function(e, t) {
                var r, o = "$$0";
                if (t > 0)
                    for (o += ".",
                             r = 0; t > r; r++)
                        o += "0";
                return C.NumberFormat(e, o)
            },
            NumberFormatToBigHZAmount: function(e) {
                return C.NumberFormat(e, "$$")
            },
            NumberRound45: function(e, t) {
                var r = Math.pow(10, t);
                return Math.round(e * r) / r
            },
            NumberRound465: function(e, t) {
                var r, o, n, i = Math.pow(10, t + 2);
                return e = Math.floor(e * i),
                    r = e % 10,
                    o = e % 100 - r,
                    e -= o + r,
                    o >= 60 ? e += 100 : 50 === o && (r > 0 ? e += 100 : (n = e % 1e3 / 100,
                    n % 2 && (e += 100))),
                e / i
            },
            ColorFromRGB: function(t, r, o, n) {
                return n === e && (n = 1),
                    c(t, r, o, n)
            }
        }
    }(),
    function(e) {
        "use strict";
        var t = gr.enum_
            , r = gr.helper
            , o = gr.format
            , n = gr.utility
            , i = r.ensureNameText
            , a = r.enumName2Value
            , l = r.enumValue2Name
            , s = r.prototypeCopyExtend
            , c = (o.BooleanFormatter,
                o.DateTimeFormatter)
            , u = o.NumericFormatter
            , d = gr.exp = {}
            , h = "[#"
            , f = "#]"
            , p = 2
            , g = function(e) {
                for (var t, r, o = e.length, n = 0, i = 0, a = 0, l = []; o > n; )
                    t = e[n],
                        a ? ")" === t ? a-- : "(" === t && a++ : "," === t ? (r = e.substring(i, n),
                        r && l.push(r),
                            i = n + 1) : "(" === t && ++a,
                        ++n;
                return r = e.substring(i, n),
                r && l.push(r),
                    l
            }
            , m = d.Summary = function() {}
        ;
        m.prototype = {
            init: function(e, t) {
                var r = this;
                e && (r.paramExp = new w(r.report,e)),
                t && (r.displayField = r.report.RunningFieldByName(t))
            },
            getAsFloat: function() {
                var e = this;
                return e.valueField ? e.valueField.AsFloat : e.value
            },
            getValue: function() {
                return this.getAsFloat()
            },
            setValue: function(e) {
                var t = this;
                t.valueField ? t.valueField.AsFloat = e : t.value = e
            },
            isDateTimeValue: function() {
                var e = this;
                return e.argExp && e.argExp.isSingleVariable() && e.argExp.varItems[0].varField.isDateTimeValue()
            },
            beginSummaryValue: function() {
                var t = this
                    , r = t.SummaryFun;
                t.recordCount = 0,
                    4 === r || 5 === r ? t.value = e : 27 !== r && 28 !== r && (t.value = 0),
                    t.values = []
            },
            summaryCurRecord: function() {
                var t = this
                    , r = t.values
                    , o = t.SummaryFun
                    , n = t.paramExp
                    , i = n ? n.calculate() : 0;
                switch (++t.recordCount,
                    o) {
                    case 1:
                    case 2:
                    case 27:
                    case 28:
                        isNaN(i) || (t.value += i);
                        break;
                    case 4:
                    case 5:
                        n && !n.isNull() && (t.value === e ? t.value = i : (5 === o && t.value < i || 4 === o && t.value > i) && (t.value = i,
                        t.displayField && (t.stringValue = t.displayField.DisplayText)));
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        r.push_back(i);
                        break;
                    case 12:
                    case 13:
                        n && n.isNull() && (t.value += 1);
                        break;
                    case 16:
                    case 17:
                    case 15:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                        n && !n.isNull() && r.push(i);
                        break;
                    case 14:
                        n && r.push(n.getTextForDistinct());
                        break;
                    case 19:
                    case 18:
                        n && (i = n.getTextForDistinct(),
                            0 === t.value ? t.value = i : (19 === o && t.value.localeCompare(i) < 0 || 18 === o && t.value.localeCompare(i) > 0) && (t.value = i));
                        break;
                    case 26:
                        t.value += Math.abs(t.Calculate())
                }
            },
            endSummaryValue: function() {
                var t = this
                    , r = t.values ? t.values.length : 0;
                switch (t.SummaryFun) {
                    case 2:
                        t.recordCount > 0 ? t.value /= t.recordCount : t.value = e;
                        break;
                    case 3:
                        t.value = t.recordCount;
                        break;
                    case 6:
                    case 20:
                        t.value = r > 1 ? t.devSq() / (r - 1) : e;
                        break;
                    case 7:
                    case 21:
                        t.value = r > 1 ? t.devSq() / r : e;
                        break;
                    case 8:
                    case 22:
                        t.value = r > 1 ? Math.sqrt(t.devSq() / (r - 1)) : e;
                        break;
                    case 9:
                    case 23:
                        t.value = r > 1 ? Math.sqrt(t.devSq() / r) : e;
                        break;
                    case 10:
                    case 24:
                        t.value = t.aveDev();
                        break;
                    case 11:
                    case 25:
                        t.value = t.devSq();
                        break;
                    case 16:
                    case 17:
                        if (t.values.sort(function(e, t) {
                            return e - t
                        }),
                        r < t.rankNo)
                            return e;
                        t.value = t.values[17 === t.SummaryFun ? r - t.rankNo : t.rankNo];
                        break;
                    case 13:
                        t.value = t.recordCount - t.value;
                        break;
                    case 15:
                        t.value = t.average();
                        break;
                    case 14:
                        t.value = t.distinct()
                }
            },
            average: function() {
                var t = this
                    , r = t.values.length
                    , o = 0;
                return t.values.forEach(function(e) {
                    o += e
                }),
                    r ? o / r : e
            },
            aveDev: function() {
                var t = this
                    , r = t.values.length
                    , o = average()
                    , n = 0;
                return t.values.forEach(function(e) {
                    n += Math.abs(e - o)
                }),
                    r ? n / r : e
            },
            devSq: function() {
                var e = average()
                    , t = 0;
                return this.values.forEach(function(r) {
                    var o = r - e;
                    t += o * o
                }),
                    t
            },
            distinct: function() {
                var e, t, r = this, o = 1, n = r.values.length, i = n;
                for (r.values.sort(function(e, t) {
                    return e.localeCompare(t)
                }),
                         e = r.values[0]; n > o; )
                    t = r.values[o++],
                        e === t ? i-- : e = t;
                return i
            }
        };
        var C = function(e, t, r) {
            var o, n = this, i = g(r), a = "";
            n.report = e,
                n.SummaryFun = t,
            i.length > 0 && (o = i[0]),
            i.length > 1 && (n.rankNo = parseInt(i[1])),
                n.init(o),
            n.paramExp && (i = n.paramExp.varItems,
            1 === i.length && (a = i[0].varField.object.Format)),
                n.formater = new u(a)
        };
        C.prototype = {
            type: 4,
            getDisplayText: function() {
                var e = this;
                return e.formater.format(e.getAsFloat())
            },
            getExpText: function() {
                var e = this;
                return l(t.SummaryFun, e.SummaryFun) + "(" + e.paramExp ? e.paramExp.getExpText() : "" + e.RankNo ? "," + e.RankNo : ")"
            }
        },
            s(C, m);
        var b = function(e, t, r) {
            var o = this
                , n = g(r);
            o.report = e,
                o.MathFun = t,
                o.paramExps = [],
                n.forEach(function(t) {
                    o.paramExps.push(new w(e,t))
                })
        };
        b.prototype = {
            type: 5,
            getAsFloat: function() {
                var e, r, o = this, i = o.MathFun, a = o.paramExps, s = a.length, c = 1 === s, u = 0;
                if (1 === i || 2 === i || 22 === i || 9 === i ? c = 2 == s : 20 !== i && 21 !== i || (c = s > 1),
                    !c)
                    return alert("表达式中函数 '" + l(t.MathFun, i) + "' 的参数未正确提供。"),
                        NaN;
                switch (e = a[0].calculate(),
                    r = 2 === s ? a[1].calculate() : 0,
                    i) {
                    case 1:
                        u = n.NumberRound45(e, r);
                        break;
                    case 2:
                        u = n.NumberRound465(e, r);
                        break;
                    case 5:
                        u = Math.abs(e);
                        break;
                    case 6:
                        u = Math.acos(e);
                        break;
                    case 7:
                        u = Math.asin(e);
                        break;
                    case 8:
                        u = Math.atan(e);
                        break;
                    case 9:
                        u = Math.atan2(e, r);
                        break;
                    case 15:
                        u = Math.ceil(e);
                        break;
                    case 16:
                        u = Math.cos(e);
                        break;
                    case 17:
                        u = Math.exp(e);
                        break;
                    case 18:
                        u = Math.floor(e);
                        break;
                    case 19:
                        u = Math.log(e);
                        break;
                    case 20:
                        if (u = e,
                        s > 1 && (r > e && (u = r),
                        s > 2))
                            for (; s-- > 2; )
                                e = a[s].calculate(),
                                e > u && (u = e);
                        break;
                    case 21:
                        if (u = e,
                        s > 1 && (e > r && (u = r),
                        s > 2))
                            for (; s-- > 2; )
                                e = a[s].calculate(),
                                u > e && (u = e);
                        break;
                    case 22:
                        u = Math.pow(e, r);
                        break;
                    case 23:
                        u = Math.round(e);
                        break;
                    case 24:
                        u = Math.sin(e);
                        break;
                    case 25:
                        u = Math.sqrt(e);
                        break;
                    case 26:
                        u = Math.tan(e)
                }
                return u
            },
            getValue: function() {
                var e = this
                    , t = e.valueField;
                return t ? t.AsFloat : e.getAsFloat()
            },
            getDisplayText: function() {
                var e = this
                    , t = e.valueField;
                return t ? t.DisplayText : e.getAsFloat() + ""
            },
            getExpText: function() {
                for (var e = this, r = e.paramExps, o = r.length, n = 0, i = l(t.MathFun, e.MathFun) + "("; o > n; )
                    n > 0 && (i += ","),
                        i += r[n].getExpText();
                return i += ")"
            },
            isDateTimeValue: function() {
                return 0
            }
        };
        var v = function(e, r) {
            var o = this
                , n = r.split(",");
            o["var"] = a(t.SystemVarType, n[0]),
            n.length > 1 && (o.groupIndex = parseInt(n[1])),
                o.report = e
        };
        v.prototype = {
            type: 3,
            getAsFloat: function() {
                var e = this;
                return e.report.SystemVarValue2(e["var"], e.groupIndex)
            },
            getValue: function() {
                return this.getAsFloat()
            },
            getDisplayText: function() {
                var e = this;
                return e["var"] > 0 ? e.getAsFloat() + "" : ""
            },
            getExpText: function() {
                var e = this;
                return "SystemVar(" + l(t.SystemVarType, e["var"]) + e.groupIndex ? "," + e.groupIndex : ")"
            },
            isDateTimeValue: function() {
                return 1 === this["var"]
            }
        };
        var x = function(e) {
            this.oParameter = e
        };
        x.prototype = {
            type: 2,
            getAsFloat: function() {
                return this.oParameter.AsFloat
            },
            getValue: function() {
                return this.oParameter.Value
            },
            getDisplayText: function() {
                var e = this;
                return e.valueField ? e.valueField.DisplayText : e.oParameter.DisplayText
            },
            getExpText: function() {
                return i(this.oParameter.Name)
            },
            isDateTimeValue: function() {
                return 6 === this.oParameter.DataType
            }
        };
        var T = function(e) {
            this.object = e
        };
        T.prototype = {
            type: 1,
            getAsFloat: function() {
                return this.object.AsFloat
            },
            getValue: function() {
                return this.object.Value
            },
            getDisplayText: function() {
                return this.object.DisplayText
            },
            getExpText: function() {
                return i(this.object.Name)
            },
            isDateTimeValue: function() {
                return 6 === this.object.FieldType
            }
        };
        var y = function(e) {
            this.object = e
        };
        y.prototype = {
            type: 6,
            getAsFloat: function() {
                return parseFloat(this.object.DisplayText)
            },
            getValue: function() {
                return this.getDisplayText()
            },
            getDisplayText: function() {
                return this.object.DisplayText
            },
            getExpText: function() {
                return i(this.object.Name)
            },
            isDateTimeValue: function() {
                return 0
            }
        };
        var S = function(e, t, r) {
            var o = this;
            o.varField = e,
                o.beginIndex = t,
                o.endIndex = r
        }
            , F = function(e, t) {
            function r() {
                for (c = u[n]; C.test(c); )
                    c = u[++n]
            }
            function o(e, r) {
                var o = c === e;
                return o && (n = t.indexOf(r, i),
                    -1 === n ? m = !1 : (d = t.substring(i + 1, n),
                        a = ++n)),
                    o
            }
            var n, i, a, l, s, c, u, d, h, f, p, g = this, m = !0, C = /\s/, b = /[\d.]/, v = /[()+-\/%*]/, x = /[()+-\/%*\s]/;
            for (u = t.split(""),
                     s = u.length,
                     g.expText = t,
                     g.varItems = [],
                     n = 0; s > n && m; )
                if (r(),
                v.test(c) || b.test(c))
                    n++;
                else {
                    if (i = n,
                        o("{", "}"))
                        f = g.decodeVariable(e, d, 1);
                    else {
                        if (!o("[", "]")) {
                            for (; s > n && !x.test(u[n]); )
                                n++;
                            if (a = n,
                                d = t.substring(i, a),
                                !d)
                                continue
                        }
                        if (r(),
                        "(" === c) {
                            for (l = ++n,
                                     p = 1; s > n; )
                                if (c = u[n++],
                                ")" === c) {
                                    if (p--,
                                    0 >= p)
                                        break
                                } else
                                    "(" === c && p++;
                            h = t.substring(l, n - 1),
                                a = n,
                                f = g.decodeFunction(e, d, h)
                        } else
                            f = g.decodeVariable(e, d, 0)
                    }
                    f ? g.varItems.push(new S(f,i,a)) : m = !1
                }
            g.valid = m
        };
        F.prototype = {
            calculate: function() {
                var e = this
                    , t = 0
                    , r = ""
                    , o = NaN;
                return e.valid && (e.varItems.forEach(function(o) {
                    t < o.beginIndex && (r += e.expText.substring(t, o.beginIndex)),
                        t = o.endIndex,
                        r += o.varField.getAsFloat() + ""
                }),
                    r += e.expText.substr(t),
                    o = gr.script.calcExp(r),
                (o >= Number.POSITIVE_INFINITY || o <= Number.NEGATIVE_INFINITY) && (o = NaN)),
                    o
            },
            isSingleVariable: function() {
                var e = this;
                return 1 === e.varItems.length && 0 === e.varItems[0].beginIndex && e.varItems[0].endIndex === e.expText.length
            }
        };
        var w = function(e, t) {
            F.call(this, e, t)
        };
        w.prototype = {
            decodeVariable: function(t, r, o) {
                var n, i = e;
                return o ? (n = t.ParameterByName(r)) && (i = new x(n)) : (n = t.RunningFieldByName(r)) ? i = new T(n) : (n = t.ParameterByName(r)) ? i = new x(n) : (n = t.ControlByName(r)) && (i = new y(n)),
                    i
            },
            decodeFunction: function(e, r, o) {
                var n, i = a(t.SummaryFun, r);
                return i >= 0 ? n = new C(e,i,o) : "SystemVar" === r ? n = new v(e,o) : (i = a(t.MathFun, r),
                i >= 0 && (n = new b(e,i,o))),
                    n
            },
            getExpText: function() {
                var e = this
                    , t = 0
                    , r = "";
                return e.varItems.forEach(function(o) {
                    t < o.beginIndex && (r += e.expText.substring(t, o.beginIndex)),
                        t = o.endIndex,
                        r += o.varField.getExpText()
                }),
                    r += e.expText.substr(t)
            },
            getTextForDistinct: function() {
                var e = this;
                return e.isSingleVariable() ? e.varItems[0].varField.getDisplayText() : e.calculate() + ""
            },
            isNull: function() {
                return this.varItems.some(function(e) {
                    return e.IsNull
                })
            }
        },
            s(w, F);
        var D = function(e, t) {
            var r, o, n, i = this, a = 0, l = "";
            for (r = t.indexOf(":"); r >= 0 && (o = t.indexOf("[", a),
                !(-1 === o || o > r)) && (o = t.indexOf("]", r),
            -1 !== o); )
                a = o + 1,
                    r = t.indexOf(":", a);
            r >= 0 && (l = i.format = t.substr(r + 1),
                t = t.substr(0, r)),
                F.call(i, e, t),
            !l && i.isSingleVariable() || (n = i.isSingleVariable() && i.varItems[0].varField.isDateTimeValue(),
            !n && l && (n = /[yMdthHms]/.test(l) && !/[0#e$]/.test(l)),
                i.formater = n ? new c(l) : new u(l))
        };
        D.prototype = {
            getDisplayText: function() {
                function e(e) {
                    return '"' + e.expText + '" 不是有效的运算表达式'
                }
                var t, r = this;
                return r.valid ? r.formater ? (t = r.isSingleVariable() ? r.varItems[0].varField.getValue() : r.calculate(),
                    r.formater.format(t)) : r.varItems[0].varField.getDisplayText() : e(this)
            }
        },
            s(D, F);
        var P = function(e, t) {
            D.call(this, e, t)
        };
        P.prototype = {
            decodeVariable: w.prototype.decodeVariable,
            decodeFunction: w.prototype.decodeFunction,
            isPureNumeric: function() {
                var e = this;
                return u.prototype.isPrototypeOf(e.formater) || e.isSingleVariable() && !e.varItems[0].varField.isDateTimeValue()
            }
        },
            s(P, D);
        var R = function(e) {
            this.oParameter = e
        };
        R.prototype = {
            getAsFloat: function() {
                return this.oParameter.AsFloat
            },
            getValue: function() {
                return this.oParameter.Value
            },
            getDisplayText: function() {
                return this.oParameter.DisplayText
            },
            isDateTimeValue: function() {
                return 6 === this.oParameter.DataType
            }
        };
        var A = function(e, t) {
            var r = this;
            r.textBuilder = e,
                r.varType = t
        };
        A.prototype = {
            getAsFloat: function() {
                function e() {
                    var e, t, r = n.graphs, o = r.length;
                    for (e = 0; o > e && (t = r[e],
                        !t.some(function(e) {
                            return e === i
                        })); e++)
                        ;
                    return t
                }
                var t, r = this, o = r.textBuilder, n = o.chart, i = o.seriesIndex, a = o.groupIndex;
                if (n.IsScatterGraph())
                    switch (t = n.Value(i, a),
                        r.varType) {
                        case 1:
                            t = t.x;
                            break;
                        case 2:
                            t = t.y;
                            break;
                        case 3:
                            t = t.z;
                            break;
                        case 5:
                            t = t.z / n.GetSeriesTotalValue(i);
                            break;
                        case 7:
                            t = n.GetSeriesTotalValue(i)
                    }
                else
                    switch (t = n.Value(i, a),
                        r.varType) {
                        case 4:
                            t /= n.GetGroupTotalValue(e(), a);
                            break;
                        case 6:
                            t = n.GetGroupTotalValue(e(), a);
                            break;
                        case 5:
                            t /= n.GetSeriesTotalValue(i);
                            break;
                        case 7:
                            Val = n.GetSeriesTotalValue(i)
                    }
                return t
            },
            getValue: function() {
                return this.getAsFloat()
            },
            getDisplayText: function() {
                var e = this
                    , t = e.varType
                    , r = e.textBuilder
                    , o = r.seriesIndex
                    , i = r.groupIndex
                    , a = r.chart
                    , l = a.IsScatterGraph()
                    , s = a.graphSerieses[o]
                    , c = e.getAsFloat()
                    , u = 0;
                if (l)
                    switch (t) {
                        case 1:
                            u = 1;
                            break;
                        case 2:
                            u = 2;
                            break;
                        case 3:
                        case 7:
                            u = 3;
                            break;
                        case 5:
                            u = 5;
                            break;
                        case 8:
                            u = 4
                    }
                else
                    switch (t) {
                        case 1:
                        case 8:
                        case 9:
                            u = 4;
                            break;
                        case 2:
                        case 6:
                        case 7:
                            u = 2;
                            break;
                        case 4:
                        case 5:
                            u = 5
                    }
                switch (u) {
                    case 1:
                        c = a.XAxis.ScaleFormatParser.format(c);
                    case 2:
                        c = l ? a.YAxisOfSeries(s).ScaleFormatParser.format(c) : s.ValueFormatParser.format(c);
                        break;
                    case 3:
                        c = s.ValueFormatParser.format(c);
                        break;
                    case 4:
                        c = 8 === t ? a.SeriesLabel(o) : a.GroupLabel(i);
                        break;
                    case 5:
                        c = n.NumberFormat(c, "0.00%")
                }
                return c
            },
            isDateTimeValue: function() {
                return 0
            }
        };
        var B = function(e, t) {
            D.call(this, e, t)
        };
        B.prototype = {
            decodeVariable: function(t, r, o) {
                var n = t.chart.report.ParameterByName(r);
                return n ? new R(n) : e
            },
            decodeFunction: function(e, r, o) {
                var n;
                return "chartvar" === r.toLowerCase() && (o = a(t.ChartVarType, o),
                o > 0 && (n = new A(e,o))),
                    n
            }
        },
            s(B, D);
        var N = function(e) {
            this.text = e
        };
        N.prototype = {
            getDisplayText: function() {
                return this.text
            }
        };
        var E = d.TextBuilder = function(e, t) {
                var r, o = this, n = e.XAxis ? B : P, i = 0, a = 0, l = 0;
                for (o.items = [],
                     e.XAxis && (o.chart = e,
                         e = this); (a = t.indexOf(h, l)) >= 0; )
                    l = t.indexOf(f, a + p),
                    l >= 0 && (r = new n(e,t.substring(a + p, l)),
                        l += p,
                    r.valid && (a > i && o.items.push(new N(t.substring(i, a))),
                        i = l,
                        o.items.push(r)),
                        a = l);
                i < t.length && o.items.push(new N(t.substr(i)))
            }
        ;
        E.prototype = {
            forEach: function(e) {
                this.items.forEach(e)
            },
            generateDisplayText: function() {
                for (var e, t, r, o, n, i = this, a = i.items, l = 0, s = a.length, c = ""; s > l; ) {
                    if (e = a[l++],
                        t = e.varItems)
                        for (n = t.length,
                                 o = 0; n > o; ++o)
                            if (r = t[o].varField,
                            3 === r.type && r["var"] < 0)
                                return "";
                    c += e.getDisplayText()
                }
                return c
            },
            generateChartDisplayText: function(e, t) {
                var r = this;
                return r.seriesIndex = e,
                    r.groupIndex = t,
                    r.generateDisplayText()
            },
            isStaticText: function() {
                var e = this;
                return 1 == e.items.length && N.prototype.isPrototypeOf(e.items[0])
            }
        }
    }(),
    function(e) {
        "use strict";
        function t(e) {
            switch (e) {
                case 2:
                    return lt;
                case 15:
                    return st;
                case 3:
                    return ct;
                case 13:
                    return ut;
                case 4:
                    return dt;
                case 5:
                    return ht
            }
            return ""
        }
        function r(e, t, r) {
            return new m(e - r,t - r,e + r,t + r)
        }
        gr.dom = {};
        var n = gr.enum_
            , i = gr.const_
            , a = gr.common
            , l = gr.helper
            , s = gr.format
            , c = gr.exp
            , u = s.BooleanFormatter
            , d = s.DateTimeFormatter
            , h = s.NumericFormatter
            , f = c.Summary
            , p = c.TextBuilder
            , g = a.DateTime
            , m = a.Rect
            , C = a.Pen
            , b = a.Border
            , v = a.Font
            , x = a.FontWrapper
            , T = a.TextFormat
            , y = a.Context
            , S = a.Graphics
            , F = a.HtmlStyles
            , w = a.HtmlElement
            , D = l.assignJSONMembers
            , P = l.enumMemberValid
            , R = l.enumBitMemberValid
            , A = l.colorMemberValid
            , B = l.fontString
            , N = l.fontHeight
            , E = l.intFixed
            , M = l.pixelsToHtml
            , V = l.percentToHtml
            , O = l.cloneDate
            , k = l.confirmDateValue
            , I = l.confirmCloneDateValue
            , L = l.strimDate
            , G = l.incDate
            , H = (l.incDate2,
            l.confirmBooleanValue)
            , _ = l.ensureNameText
            , j = l.enumName2Value
            , W = l.enumValue2Name
            , U = l.rgba2color
            , z = l.colorAlpha
            , J = l.color2rgba
            , Y = l.colorGradientLight
            , X = l.colorGradientDark
            , Z = l.prototypeLinkExtend
            , q = l.prototypeCopyExtend
            , Q = l.createArray
            , K = l.assignObjectEx
            , $ = l.assignObject
            , ee = l.assignObjectAtom
            , te = l.parseXML
            , re = l.getRelativePosition
            , oe = l.addEvent
            , ne = l.bindEvents
            , ie = gr.wr ? gr.wr.wrPropNameEncode : e
            , ae = l.toDegree
            , le = l.toRadians
            , se = function() {
            var e = 0;
            return function() {
                return "-gr-canvas-" + e++
            }
        }()
            , ce = function(e) {
            var t = 0
                , r = 0;
            e.length && (e.forEach(function(e) {
                t += e.pxHeight
            }),
                e.forEach(function(e) {
                    e.pctHeight = Math.round(100 * e.pxHeight / t),
                        r += e.pctHeight
                }),
                e[0].pctHeight += 100 - r)
        }
            , ue = function(e) {
            var t = this;
            t.owner = e,
                t.items = []
        };
        ue.prototype = {
            loadFromJSON: function(e) {
                var t, r = 0;
                if (e)
                    for (t = e.length; t > r; )
                        this._doloadItem(e[r++])
            },
            getJsonMember: function(e) {
                return this.owner.report.isWR ? ie(e) : e
            },
            assign: function(e) {
                var t = this;
                t.RemoveAll(),
                    e.forEach(function(e) {
                        t.Add().assign(e)
                    })
            },
            attachData: function() {
                for (var e = this.items, t = e.length; t--; )
                    e[t].attachData()
            },
            prepare: function() {
                for (var e = this.items, t = 0, r = e.length; r > t; )
                    e[t++].prepare()
            },
            unprepare: function() {
                for (var e = this.items, t = e.length; t--; )
                    e[t].unprepare()
            },
            generate: function(e) {
                for (var t = this.items, r = 0, o = t.length; o > r; )
                    t[r++].generate(e)
            },
            itemByName: function(t) {
                var r, o = this.items, n = o.length;
                if (t)
                    for (t = t.toUpperCase(); n--; )
                        if (r = o[n],
                        r.Name.toUpperCase() === t)
                            return r;
                return e
            },
            indexOfCOMIndex: function(e) {
                return e ? ("number" != typeof e && (e = this.IndexByName(e || "")),
                    --e) : e = -1,
                    e
            },
            indexOf: function(e) {
                for (var t = this.items, r = t.length; r--; )
                    if (e === t[r])
                        return r;
                return -1
            },
            decodeItems: function(e) {
                var t, r = this, o = [];
                return e && (t = e.split(";"),
                    t.forEach(function(e) {
                        var t;
                        e = e.trim(),
                            t = r.itemByName(e),
                        t && o.push(t)
                    })),
                    o
            },
            forEach: function(e) {
                this.items.forEach(e)
            },
            _doloadItem: function(e) {
                this.Add().loadFromJSON(e)
            },
            get Count() {
                return this.items.length
            },
            Item: function(e) {
                var t = this;
                return t.items[t.indexOfCOMIndex(e)]
            },
            Add: function() {
                var e = this
                    , t = e._createItem();
                return e.items.push(t),
                    t
            },
            Remove: function(e) {
                var t = this;
                e = t.indexOfCOMIndex(e),
                e >= 0 && t.items.splice(e, 1)
            },
            RemoveAll: function() {
                this.items = []
            },
            IndexByName: function(e) {
                var t = this.items
                    , r = t.length;
                if (e)
                    for (; r--; )
                        if (t[r].Name === e)
                            return r + 1;
                return -1
            },
            ItemAt: function(t) {
                var r = this.items;
                return t--,
                    t >= 0 && t < r.length ? r[t] : e
            },
            ChangeItemOrder: function(e, t) {
                var r = this.items;
                if (o,
                    len = r.length,
                    t = Math.max(1, Math.min(len, t)) - 1,
                e != t && e > 0 && e-- <= len)
                    if (o = r[e],
                    t > e) {
                        do
                            r[e] = r[e + 1];
                        while (++e < t)
                    } else {
                        do
                            r[e] = r[e - 1];
                        while (--e > t);
                        r[t] = o
                    }
            }
        };
        var de = function(e) {
            ue.call(this, e)
        };
        de.prototype = {
            _doloadItem: function(e) {
                var t = this
                    , r = t.Add(j(n.ControlType, e[t.getJsonMember("Type")]));
                r && r.loadFromJSON(e)
            },
            assign: function(e) {
                var t = this;
                t.RemoveAll(),
                    e.forEach(function(e) {
                        t.Add(e.ControlType).assign(e)
                    })
            },
            layout: function() {
                for (var e = this, t = e.items, r = e.owner.getRect(), o = 0, n = t.length; n > o; )
                    t[o++].layout(r)
            },
            generate: function(e) {
                for (var t, r = this.items, o = r.length; o--; )
                    t = r[o],
                    t.Visible && t.generate(e)
            },
            Add: function(t) {
                function r(t, r) {
                    var o = e;
                    switch (t) {
                        case 1:
                            o = new Re(r);
                            break;
                        case 2:
                            o = new Oe(r);
                            break;
                        case 10:
                            o = new Ve(r);
                            break;
                        case 3:
                            o = new Ee(r);
                            break;
                        case 4:
                            o = new Be(r);
                            break;
                        case 5:
                            o = new Ne(r);
                            break;
                        case 7:
                            o = new Ie(r);
                            break;
                        case 8:
                            o = new Ae(r);
                            break;
                        case 9:
                            o = new Ge(r);
                            break;
                        case 11:
                            o = new It(r);
                            break;
                        case 12:
                            o = new ke(r);
                            break;
                        case 13:
                            o = new je(r);
                            break;
                        case 6:
                            o = new Le(r)
                    }
                    return o
                }
                var o = this
                    , n = r(t, o.owner);
                return n && o.items.push(n),
                    n
            }
        },
            Z(de, ue);
        var he = function(e) {
            ue.call(this, e)
        };
        he.prototype = {
            _doloadItem: function(e) {
                var t = this
                    , r = new tt(t.owner);
                r.loadFromJSON(e),
                    t.items.push(r)
            },
            addTo: function(e) {
                var t, r, o = this, n = o.owner.ColumnContent.ContentCells, i = new tt(o.owner);
                return o.items.push(i),
                    t = new we(e,!1),
                    e.items.push(t),
                    i.TitleCell = t,
                    t.Column = i,
                    r = new Fe(n),
                    n.items.push(r),
                    i.ContentCell = r,
                    r.Column = i,
                    i
            },
            Add: function() {
                var e = this;
                return e.addTo(e.owner.ColumnTitle.TitleCells)
            },
            Remove: function(e) {
                function t(e, r) {
                    var o, n, i = e.indexOf(e);
                    if (i >= 0)
                        return e.splice(i, 1),
                            1;
                    for (i = e.length,
                             n = 0; i > n; n++)
                        if (o = e[n],
                        o.GroupTitle && t(o.SubTitles.items, r))
                            return 1;
                    return 0
                }
                var r, o = this, n = o.owner, i = n.ColumnContent.ContentCells.items;
                e = o.indexOfCOMIndex(e),
                e >= 0 && (r = o.items[e],
                    i.splice(i.indexOf(r.ContentCell), 1),
                    t(n.ColumnTitle.TitleCells.items, r.TitleCell),
                    o.items.splice(e, 1))
            },
            RemoveAll: function() {
                var e = this
                    , t = e.owner;
                e.items = [],
                    t.ColumnContent.ContentCells.items = [],
                    t.ColumnTitle.TitleCells.items = []
            }
        },
            Z(he, ue);
        var fe = function(e, t) {
            var r = this;
            ue.call(r, e),
                r.supcell = t
        };
        fe.prototype = {
            _doloadItem: function(e) {
                var t = this
                    , r = new we(t,e[t.getJsonMember("GroupTitle")]);
                r.loadFromJSON(e),
                    t.items.push(r)
            },
            assign: function(e) {
                var t = this;
                t.items = [],
                    e.forEach(function(e) {
                        var r = new we(t,e.GroupTitle);
                        t.items.push(r),
                            r.assign(e),
                        e.GroupTitle && r.SubTitles.assign(e.SubTitles)
                    })
            },
            AddGroup: function(e, t) {
                var r = this
                    , o = new we(r,!0);
                return o.Name = e,
                    o.Text = t,
                    r.items.push(o),
                    o
            },
            RemoveGroup: function(e) {
                var t = this
                    , r = t.Item(e);
                r && r.GroupTitle && (ue.prototype.Remove.call(t, e),
                    t.items = t.items.concat(r.SubTitles.items))
            },
            Add: function() {},
            Remove: function(e) {},
            RemoveAll: function() {}
        },
            Z(fe, ue);
        var pe = function(e) {
            ue.call(this, e)
        };
        pe.prototype = {
            _doloadItem: function(e) {
                var t = this
                    , r = new Fe(t);
                r.loadFromJSON(e),
                    t.items.push(r)
            },
            assign: function(e) {
                var t = this;
                t.items = [],
                    e.forEach(function(e) {
                        var r = new Fe(t);
                        t.items.push(r),
                            r.assign(e)
                    })
            },
            Add: function() {},
            ChangeItemOrder: function(e, t) {},
            Remove: function(e) {},
            RemoveAll: function() {}
        },
            Z(pe, ue);
        var ge = function(e) {
            ue.call(this, e)
        };
        ge.prototype._createItem = function() {
            return new $e(this.owner)
        }
            ,
            Z(ge, ue);
        var me = function(e) {
            ue.call(this, e)
        };
        me.prototype = {
            _createItem: function() {
                return new rt(this.owner)
            },
            _doloadItem: function(e) {
                !e.PageGroup && this.Add().loadFromJSON(e)
            }
        },
            Z(me, ue);
        var Ce = function(e) {
            ue.call(this, e)
        };
        Ce.prototype._createItem = function() {
            return new ze(this.owner)
        }
            ,
            Z(Ce, ue);
        var be = function(e) {
            ue.call(this, e)
        };
        be.prototype._createItem = function() {
            return new Je(this.owner)
        }
            ,
            Z(be, ue);
        var ve = function(e) {
            ue.call(this, e)
        };
        ve.prototype._createItem = function() {
            return new nt(this.owner)
        }
            ,
            Z(ve, ue);
        var xe = function(e) {
            var t = this;
            t.owner = e,
                t.report = e.report,
                t.Tag = ""
        };
        xe.prototype = {
            afterLoad: function(e) {},
            loadFromJSON: function(e) {
                var t = this;
                e && (D(t, e),
                    t.afterLoad(e))
            },
            assign: function(e) {
                var t, r, o = this, n = o.Font, i = e.onclick, a = e.ondblclick;
                for (var l in e)
                    o.hasOwnProperty(l) && (t = e[l],
                        r = typeof t,
                    "object" !== r && "array" !== r && (o[l] = t));
                n && n.assign(e.Font),
                i && (o.onclick = i),
                a && (o.ondblclick = a),
                o.children && o.children.forEach(function(t) {
                    var r = e[t];
                    r && (r.owner ? o[t].assign(r) : $(o[t], r))
                })
            },
            registerEventClass: function() {
                var e = this
                    , t = e.report.viewer._domevents
                    , r = e.onclick
                    , o = e.ondblclick;
                r && !e.onclickClassID && (t.push({
                    obj: e,
                    type: 1,
                    fun: r
                }),
                    e.onclickClassID = t.length),
                o && !e.ondblclickClassID && (t.push({
                    obj: e,
                    type: 2,
                    fun: o
                }),
                    e.ondblclickClassID = t.length)
            },
            resetEventClass: function() {
                var e = this;
                e.onclickClassID && delete e.onclickClassID,
                e.ondblclickClassID && delete e.ondblclickClassID
            },
            addElementEventClass: function(e) {
                var t = this
                    , r = t.report.viewer
                    , o = t.onclickClassID
                    , n = t.ondblclickClassID;
                o && e.addClass(r._getEventCSSName(o)),
                n && e.addClass(r._getEventCSSName(n))
            },
            getUsingFont: function() {
                return this.Font.UsingFont()
            },
            getJsonMember: function(e) {
                return this.report.isWR ? ie(e) : e
            },
            toFillBack: function() {
                var e = this;
                return e.BackColor !== e.owner.BackColor
            },
            getRunningNo: function() {
                var e = this
                    , t = e.report.ownerSR
                    , r = t ? 1e4 * t.getRunningNo() : 0;
                return r + e._runningNo()
            },
            _runningNo: function() {
                return 0
            }
        };
        var Te = function(e) {
            var t = this;
            xe.call(t, e),
                t._borderCustom = !1,
                t.BackColor = e.BackColor,
                t.CanGrow = !1,
                t.CanShrink = !1,
                t.Font = new x(e.Font),
                t._freeCell = !0,
                t.setFreeCell(!1)
        };
        Te.prototype = {
            loadFromJSON: function(e) {
                var t = this;
                e && (t.setFreeCell(!!e[t.getJsonMember("FreeCell")]),
                    D(t, e),
                    t.afterLoad(e))
            },
            afterLoad: function(e) {
                var t = this
                    , r = t.report
                    , o = r.viewer.alpha
                    , n = r.isWR;
                A(t, "BackColor", o.background),
                    A(t, "ForeColor", o.text),
                    t.Font.loadFromJSON(e.Font, n),
                    t.setBorderCustom(e[t.getJsonMember("BorderCustom")]),
                t._borderCustom && t.Border.loadFromJSON(e.Border, o.border, n),
                    t.FreeCell ? t.Controls.loadFromJSON(e.Control) : t.TextFormat.loadFromJSON(e, n)
            },
            assign: function(e) {
                var t, r = this;
                r.setFreeCell(e.FreeCell),
                    r.setBorderCustom(e.BorderCustom),
                    xe.prototype.assign.call(r, e),
                    r.Font.assign(e.Font),
                e.Border && $(r.Border, e.Border),
                    e.FreeCell ? (r.Controls.assign(e.Controls),
                    r.isSingleDockControl() && (t = r.Controls.items[0],
                    !t.onclick && r.onclick && (t.onclick = r.onclick),
                    !t.ondblclick && r.ondblclick && (t.ondblclick = r.ondblclick))) : $(r.TextFormat, e.TextFormat)
            },
            attachData: function() {
                var e = this;
                e.FreeCell ? e.Controls.attachData() : e._getWrapperClass().attachData.call(e)
            },
            prepare: function() {
                var e, t = this, r = t.report.viewer, o = t.Controls, n = t.Font.font;
                t.registerEventClass(),
                t.FreeCell && (o.layout(),
                t.isSingleDockControl() || 2 === r.options.controlPosition || (e = t.getRect(),
                    Ue.prototype.buildTableLayout.call(t, e.Width(), e.Height())),
                    o.prepare(),
                t.isSingleDockControl() && (e = o.items[0],
                !e.onclick && t.onclick && (e.onclick = t.onclick,
                    e.onclickClassID = t.onclickClassID),
                !e.ondblclick && t.ondblclick && (e.ondblclick = t.ondblclick,
                    e.ondblclickClassID = t.ondblclickClassID))),
                n && (t.defaultFontStyle = r.selectFontItem(n)),
                t.tableRows && t.tableRows.forEach(function(e) {
                    e.defaultStyle = r.selectSectionItem(e)
                }),
                    t.defaultStyle = r.selectCellItem(t)
            },
            unprepare: function() {
                var e = this;
                e.FreeCell ? e.Controls.unprepare() : e._getWrapperClass().unprepare.call(e),
                    e.resetEventClass(),
                    delete e.defaultFontStyle,
                    delete e.defaultStyle
            },
            doGenerate: function(e) {
                var t = this
                    , r = t.report.viewer
                    , o = t.Controls
                    , n = t.Font.font
                    , i = t.defaultFontStyle;
                e.addClass(r.selectCell(t)),
                n && i && e.addClass(r.selectFont(n, i)),
                t.addStyles && t.addStyles(e),
                    t.FreeCell ? t.isSingleDockControl() ? o.items[0].generateInCell(e) : (t.addElementEventClass(e),
                        t.tableRows ? (e.addStyle("padding", "0px"),
                            e = new w("table",e),
                            e.addStyle("border-collapse", "collapse"),
                            Ue.prototype.generateTableRows.call(t, e)) : (e.addStyle("position", "relative"),
                            o.generate(e))) : (t.addElementEventClass(e),
                        Pe.prototype.generateNormal.call(t, e))
            },
            setFreeCell: function(e) {
                var t, r = this;
                r._freeCell !== e && (r._freeCell = e,
                    e ? (r.Controls = new de(r),
                        delete r.ControlType) : (t = r._getWrapperClass(),
                        t.createWrapper.call(r),
                        r.ControlType = t.ControlType,
                        delete r.Controls))
            },
            setBorderCustom: function(e) {
                var t = this;
                t._borderCustom !== e && (t._borderCustom = e,
                    e ? t.Border = new b(12) : delete t.Border)
            },
            setForeColor: function(e) {
                var t = this;
                t.FreeCell ? t.Controls.forEach(function(t) {
                    t.ForeColor = e
                }) : t.ForeColor = e
            },
            getDisplayText: function() {
                var e = this;
                return e._getWrapperClass().getDisplayText.call(e)
            },
            setDisplayText: function(e) {
                var t = this;
                return t._getWrapperClass().setDisplayText.call(t, e)
            },
            getControls: function() {
                var e = this;
                return e.FreeCell ? e.Controls.items : [e]
            },
            isSingleDockControl: function() {
                var e = this.Controls.items;
                return 1 === e.length && 5 === e[0].Dock
            },
            isControlPositionClass: function() {
                return 0
            },
            _getDisplayText: function() {
                var e = this;
                return e._getWrapperClass()._getDisplayText.call(e)
            }
        },
            q(Te, xe);
        var ye = function(e, t, r) {
            var o = this;
            Te.call(o, e),
                o.row = t,
                o.col = r,
                o.ColSpan = 1,
                o.RowSpan = 1,
                o.Text = "",
                o.DataName = ""
        };
        ye.prototype = {
            generate: function(e) {
                var t = this;
                e = new w("td",e),
                t.ColSpan > 1 && e.addAttribute("colspan", t.ColSpan + ""),
                t.RowSpan > 1 && e.addAttribute("rowspan", t.RowSpan + ""),
                    t.doGenerate(e)
            },
            getOwnerGrid: function() {
                return this.owner
            },
            _getWrapperClass: function() {
                return Ae.prototype
            },
            getRect: function() {
                for (var e = this, t = e.owner, r = e.col, o = e.row, n = r + e.ColSpan, i = o + e.RowSpan, a = 0, l = 0; n > r; )
                    a += t.columns[r++].pxWidth;
                for (; i > o; )
                    l += t.rows[o++].pxHeight;
                return new m(0,0,a,l)
            },
            _runningNo: function() {
                return this.owner.owner._runningNo()
            },
            get FreeCell() {
                return !!this._freeCell
            },
            set FreeCell(e) {
                this.setFreeCell(e)
            },
            get BorderCustom() {
                return !!this._borderCustom
            },
            set BorderCustom(e) {
                this.setBorderCustom(e)
            },
            get DisplayText() {
                var e = this;
                return e.FreeCell ? "" : e.getDisplayText()
            },
            set DisplayText(e) {
                var t = this;
                !t.FreeCell && t.setDisplayText(e)
            }
        },
            q(ye, Te);
        var Se = function(e) {
            var t = this;
            Te.call(t, e),
                t.Column = ""
        };
        Se.prototype = {
            afterLoad: function(e) {
                var t = this;
                Te.prototype.afterLoad.call(t, e),
                    t.Column = t.owner.owner.Columns.itemByName(t.Column)
            },
            prepare: function() {
                var e = this;
                Te.prototype.prepare.call(e),
                e.CanShrink && (e.owner.CanShrink = 1)
            },
            getOwnerGrid: function() {
                return this.owner.owner
            }
        },
            q(Se, Te);
        var Fe = function(e) {
            var t = this;
            Se.call(t, e.owner),
                t.DataField = ""
        };
        Fe.prototype = {
            _getWrapperClass: function() {
                return Be.prototype
            },
            afterLoad: function(e) {
                var t = this;
                Se.prototype.afterLoad.call(t, e),
                    t.Column.ContentCell = t
            },
            generate: function(e) {
                var t = this
                    , r = t.Column._crossOrderNo;
                e = new w("td",e),
                r && e.addAttribute("_grcrossno", r),
                    t.doGenerate(e)
            },
            generateMerge: function(e, t) {
                var r = this
                    , o = r.report.viewer
                    , n = r.Font.font
                    , i = r.Column._crossOrderNo;
                e = new w("td",e),
                i && e.addAttribute("_grcrossno", i),
                    e.addClass(o.selectCell(r)),
                n && e.addClass(o.selectFont(n, r.defaultFontStyle)),
                    t.generateInCell(e)
            },
            isControlPositionClass: function() {
                return this.FreeCell
            },
            get Name() {
                return this.Column.Name
            },
            set Name(e) {
                this.Column.Name = e
            },
            get FreeCell() {
                return !!this._freeCell
            },
            set FreeCell(e) {
                this.setFreeCell(e)
            },
            get BorderCustom() {
                return !!this._borderCustom
            },
            set BorderCustom(e) {
                this.setBorderCustom(e)
            },
            get DisplayText() {
                var e = this;
                return e.FreeCell ? "" : e.getDisplayText()
            },
            set DisplayText(e) {
                var t = this;
                !t.FreeCell && t.setDisplayText(e)
            },
            getRect: function() {
                var e = this;
                return new m(0,0,e.Column.pxWidth,e.owner.pxHeight)
            },
            _runningNo: function() {
                return this.getOwnerGrid().Recordset.RecordNo + 1
            }
        },
            q(Fe, Se);
        var we = function(e, t) {
            var r = this;
            Se.call(r, e.owner),
                r.SupCell = e.supcell,
                r.Visible = !0,
                r.GroupTitle = t,
                r.Text = "",
                r.Height = 0,
            t && (r._name = "",
                r.SubTitles = new fe(r.owner,r))
        };
        we.prototype = {
            _getWrapperClass: function() {
                return Ae.prototype
            },
            afterLoad: function(e) {
                var t = this;
                Se.prototype.afterLoad.call(t, e),
                    t.GroupTitle ? (t._name = e[t.getJsonMember("Name")],
                        t.SubTitles.loadFromJSON(e.ColumnTitleCell)) : t.Column.TitleCell = t
            },
            prepare: function() {
                var e = this;
                Se.prototype.prepare.call(e),
                e.SubTitles && e.SubTitles.prepare()
            },
            generate: function(e) {
                var t = this;
                e = new w("th",e),
                    t.doGenerate(e)
            },
            addStyles: function(e) {
                var t = this
                    , r = t.GroupTitle ? 1 : t.owner.layerCount - t.owner.generatingLayer;
                t.colspan > 1 && e.addAttribute("colspan", t.colspan + ""),
                r > 1 && e.addAttribute("rowspan", r + "")
            },
            getRect: function() {
                var e = this
                    , t = e.owner.layerCount - e.layer
                    , r = e.Column
                    , o = 0;
                return e.GroupTitle ? (t = 1,
                    r = e.findFirstColumn(),
                r && (o = e.findLastColumn().pxRight - r.pxLeft)) : o = r.pxWidth,
                    new m(0,0,o,e.owner.pxHeight * t)
            },
            findFirstColumn: function() {
                var t, r = this, o = r.Column;
                return r.GroupTitle && (t = r.SubTitles.items,
                    o = t.length > 0 ? t[0].findFirstColumn() : e),
                    o
            },
            findLastColumn: function() {
                var t, r = this, o = r.Column;
                return r.GroupTitle && (t = r.SubTitles.items,
                    o = t.length > 0 ? t[t.length - 1].findLastColumn() : e),
                    o
            },
            encloseColumnObject: function(e) {
                var t = this;
                t.owner.owner._ColumnMoveTo(e, t)
            },
            get FreeCell() {
                return !!this._freeCell
            },
            set FreeCell(e) {
                this.setFreeCell(e)
            },
            get BorderCustom() {
                return !!this._borderCustom
            },
            set BorderCustom(e) {
                this.setBorderCustom(e)
            },
            get DisplayText() {
                var e = this;
                return e.FreeCell ? "" : e.getDisplayText()
            },
            set DisplayText(e) {
                var t = this;
                !t.FreeCell && t.setDisplayText(e)
            },
            get Name() {
                var e = this;
                return e.GroupTitle ? e._name : e.Column.Name
            },
            set Name(e) {
                var t = this;
                t.GroupTitle ? t._name = e : t.Column.Name = e
            },
            EncloseColumn: function(e) {
                var t = this
                    , r = t.owner.owner
                    , o = r.Columns.Item(e);
                o && r._ColumnMoveTo(o, t)
            },
            AddSubGroupTitle: function(e, t) {
                return this.SubTitles.AddGroup(e, t)
            }
        },
            q(we, Se);
        var De = function(e) {
            var t = this;
            xe.call(t, e),
                t.Left = 0,
                t.Top = 0,
                t.Width = 0,
                t.Height = 0,
                t.Anchor = 3,
                t.Dock = 0,
                t.Center = 0,
                t.AlignColumnSide = 3,
                t.AlignColumn = "",
                t.AlignColumnEx = "",
                t.Locked = !1,
                t.ShiftMode = 1,
                t.PaddingLeft = 0,
                t.PaddingRight = 0,
                t.PaddingTop = 0,
                t.PaddingBottom = 0,
                t.BackColor = e.BackColor,
                t.BackStyle = 2,
                t.ForeColor = e.ForeColor ? e.ForeColor : z(0, t.report.viewer.alpha.text),
                t.Visible = !0,
                t.CustomDraw = !1,
                t.Border = new b(0),
                t.Font = new x(e.Font),
                t.Name = "",
                t.CustomDrawScript = ""
        };
        De.prototype = {
            children: ["Border"],
            attachData: function() {},
            afterLoad: function(e) {
                var t = this
                    , r = t.report.isWR
                    , o = t.report.viewer.alpha;
                P(t, "Dock", n.DockStyle),
                    P(t, "Center", n.CenterStyle),
                    P(t, "AlignColumnSide", n.AlignColumnSideStyle),
                    P(t, "ShiftMode", n.ShiftMode),
                    P(t, "BackStyle", n.BackStyle),
                    R(t, "Anchor", n.AnchorStyle),
                    A(t, "BackColor", o.background),
                    A(t, "ForeColor", o.text),
                    t.Font.loadFromJSON(e.Font, r),
                    t.Border.loadFromJSON(e.Border, o.border, r)
            },
            layout: function(t) {
                var r, o = this, n = o.report, i = n.size2Pixel(o.Left), a = n.size2Pixel(o.Top), l = n.size2Pixel(o.Width), s = n.size2Pixel(o.Height), c = new m(i,a,i + l,a + s), u = n.getRunningColumn(o.AlignColumn), d = n.getRunningColumn(o.AlignColumnEx);
                if (u && !u.Visible && (u = e),
                d && !d.Visible && (d = e),
                u || d)
                    switch (u && d ? d.pxLeft < u.pxLeft && (r = d,
                        d = u,
                        u = r) : d ? u = d : d = u,
                        o.AlignColumnSide) {
                        case 1:
                            c.left = u.pxLeft,
                                c.right = c.left + l;
                            break;
                        case 2:
                            c.right = d.pxRight,
                                c.left = c.right - l;
                            break;
                        default:
                            c.left = u.pxLeft,
                                c.right = d.pxRight
                    }
                else {
                    if (0 !== o.Dock)
                        switch (c = t.clone(),
                            o.Dock) {
                            case 5:
                                t.left = t.right,
                                    t.top = t.bottom;
                                break;
                            case 1:
                                c.right = Math.min(t.right, t.left + l),
                                    t.left = c.right;
                                break;
                            case 2:
                                c.bottom = Math.min(t.bottom, t.top + s),
                                    t.top = c.bottom;
                                break;
                            case 3:
                                c.left = Math.max(t.left, t.right - l),
                                    t.right = c.left;
                                break;
                            case 4:
                                c.top = Math.max(t.top, t.bottom - s),
                                    t.bottom = c.top
                        }
                    0 !== o.Center && 5 !== o.Dock && (1 !== o.Center && 3 !== o.Center || (l = c.Width(),
                        c.left = (t.left + t.right - l) / 2,
                        c.right = c.left + l),
                    2 !== o.Center && 3 !== o.Center || (s = c.Height(),
                        c.top = (t.top + t.bottom - s) / 2,
                        c.bottom = c.top + s))
                }
                o.pxRect = c,
                    o.oAlignColumn = u,
                    o.oAlignColumnEx = d
            },
            prepare: function() {
                var e = this
                    , t = e.report.viewer
                    , r = !e.owner.isSingleDockControl && e.owner.isControlPositionClass();
                e.registerEventClass(),
                    e.defaultStyle = t.selectControlItem(e),
                e.Font.font && (e.defaultFontStyle = t.selectFontItem(e.Font.font)),
                r && (e.defaultPositionStyle = t.selectPositionItem(e))
            },
            unprepare: function() {
                var e = this;
                e.resetEventClass(),
                    delete e.defaultStyle,
                    delete e.defaultFontStyle,
                    delete e.defaultPositionStyle,
                    delete e.oAlignColumn,
                    delete e.oAlignColumnEx
            },
            prepareLayout: function(e) {
                return e
            },
            generate: function(e) {
                var t, r = this, o = r.report.viewer, n = r.pxRect;
                t = new w("span",e),
                    r.addElementEventClass(t),
                    t.addClass(o.selectControl(r)),
                r.Font.font && t.addClass(o.selectFont(r.Font.font, r.defaultFontStyle)),
                    r.defaultPositionStyle ? t.addClass(o.selectPosition(r)) : (t.addStyle("left", M(n.left)),
                        t.addStyle("top", M(n.top)),
                        t.addStyle("width", M(r.getContentWidth())),
                        t.addStyle("height", M(r.getContentHeight()))),
                    r.generateContent(r.prepareLayout(t))
            },
            generateInCell: function(e) {
                var t = this;
                t.addElementEventClass(e),
                t.oAlignColumn && t.setCrossNoAttr(e),
                    t.generateContent(e)
            },
            generateContent: function(e) {
                var t = this;
                t.CustomDraw ? t.generateCanvas(e) : t.generateNormal(e)
            },
            generateCanvas: function(e) {
                var t = this
                    , r = t.getRunningNo()
                    , o = r ? t.cloneCanvas(r) : t;
                return t.report.registerCanvas(o, e),
                    o
            },
            cloneCanvas: function(e) {
                var t, r, o = this, n = new o.constructor(o.owner);
                n.runningNo = e,
                    n.origin = o,
                    n.assign(o);
                for (t in o)
                    r = o[t],
                    "object" != typeof r || n[t] || (n[t] = r);
                return n
            },
            resizeCanvas: function() {
                var e = this
                    , t = e.canvas;
                t.width = Math.min(t.parentNode.clientWidth, window.innerWidth),
                    t.height = Math.min(t.parentNode.clientHeight, window.innerHeight),
                    e.drawCanvas(1)
            },
            drawCanvas: function(e) {
                var t = this
                    , r = t.getOwnerDetailGrid();
                r && r.syncElementData(t.canvas),
                    t.report.fireControlCustomDraw(t)
            },
            setCrossNoAttr: function(e) {
                var t = this.oAlignColumn._crossOrderNo;
                t && e.addAttribute("_grcrossno", t)
            },
            toFillBack: function() {
                var e = this;
                return 1 === e.BackStyle && e.BackColor !== e.owner.BackColor
            },
            getRunningNo: function() {
                return this.owner.getRunningNo()
            },
            getContentWidth: function() {
                var e = this
                    , t = e.Border;
                return e.pxRect.Width() - e.PaddingLeft - e.PaddingRight - t.getLeftWidth() - t.getRightWidth()
            },
            getContentHeight: function() {
                var e = this
                    , t = e.Border;
                return e.pxRect.Height() - e.PaddingTop - e.PaddingBottom - t.getTopWidth() - t.getBottomWidth()
            },
            getOwnerDetailGrid: function() {
                var t, r = this, o = r.owner, n = o.owner, i = n ? n.owner : e;
                return i && (i.ColumnTitle ? t = i : n.isPrototypeOf(je) && (t = i.getOwnerDetailGrid())),
                    t
            },
            SetBounds: function(e, t, r, o) {
                var n = this;
                n.Left = e,
                    n.Top = t,
                    n.Width = r - e,
                    n.Height = o - t
            },
            BringToFront: function() {
                var e = this
                    , t = e.owner.Controls;
                t.ChangeItemOrder(t.indexOf(e), t.length)
            },
            SendToBack: function() {
                var e = this
                    , t = e.owner.Controls;
                t.ChangeItemOrder(t.indexOf(e), 1)
            },
            DrawDefault: function() {}
        },
            q(De, xe);
        var Pe = function(e) {
            var t = this;
            De.call(t, e),
                t.createWrapper(),
                t.AsTextBox = t
        };
        Pe.prototype = {
            children: ["Border", "TextFormat"],
            afterLoad: function(e) {
                var t = this;
                De.prototype.afterLoad.call(t, e),
                    t.TextFormat.loadFromJSON(e, t.report.isWR)
            },
            createWrapper: function() {
                var e = this;
                e.PaddingLeft = 2,
                    e.PaddingRight = 1,
                    e.PaddingTop = 1,
                    e.PaddingBottom = 1,
                    e.CanGrow = !1,
                    e.CanShrink = !1,
                    e.ShrinkFontToFit = !1,
                    e.ForeColor = e.owner.ForeColor ? e.owner.ForeColor : z(0, e.report.viewer.alpha.text),
                    e.TextFormat = new T,
                    e.GetDisplayTextScript = ""
            },
            prepareLayout: function(e) {
                var t = this
                    , r = t.TextFormat
                    , o = r.TextAlign;
                return 16 & o || (r.WordWrap || 64 & o ? (e.addStyle("display", "table"),
                    e = new w("span",e),
                    e.addStyle("display", "table-cell"),
                    e.addStyle("vertical-align", 32 & o ? "middle" : "bottom")) : e.addStyle("line-height", M(t.getContentHeight()))),
                    e
            },
            generateNormal: function(e) {
                function t() {
                    var e, t = n.split(""), r = t.length, o = 0;
                    for (n = ""; r > o; )
                        if (e = t[o],
                        " " === e && r - 1 > o && " " === t[o + 1])
                            for (; r > o && " " === t[o]; )
                                n += "&nbsp;",
                                    o++;
                        else
                            n += e,
                                o++
                }
                var r = this
                    , o = "<br />"
                    , n = r.getDisplayText();
                r.TextFormat.HtmlTags || (/[\r\n&<>"]/.test(n) && (n = n.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/\r\n/g, o).replace(/\r/g, o).replace(/\n/g, o)),
                /  /.test(n) && t()),
                    e.innerText = n
            },
            getDisplayText: function() {
                var e = this;
                return e.displayTextAssigned = !1,
                e.doingGetDisplayText || (e.doingGetDisplayText = !0,
                    e.report.fireTextBoxGetDisplayText(e),
                    e.doingGetDisplayText = !1),
                    e.displayTextAssigned ? e.assignedDisplayText : e._getDisplayText()
            },
            setDisplayText: function(e) {
                var t = this;
                t.assignedDisplayText = e,
                    t.displayTextAssigned = !0
            },
            DrawDefault: function() {
                var e = this
                    , t = e.getDisplayText()
                    , r = e.canvas;
                e.report.Graphics.DrawFormatText(t, 0, 0, r.width, r.height, e.TextFormat)
            }
        },
            q(Pe, De);
        var Re = function(e) {
            var t = this;
            Pe.call(t, e),
                t.Text = "",
                t.Parameter = ""
        };
        Re.prototype = {
            ControlType: 1,
            _getDisplayText: function() {
                var e = this;
                return e.valueField ? e.valueField.DisplayText : e.oParameter ? e.oParameter.DisplayText : e.Text
            },
            attachData: function() {
                var e = this;
                e.Parameter && (e.oParameter = e.report.ParameterByName(e.Parameter))
            },
            unprepare: function() {
                var e = this;
                Pe.prototype.unprepare.call(e),
                    delete e.oParameter
            },
            get DisplayText() {
                return this.getDisplayText()
            },
            set DisplayText(e) {
                this.setDisplayText(e)
            },
            get AsStaticBox() {
                return this
            }
        },
            q(Re, Pe);
        var Ae = function(e) {
            var t = this;
            Pe.call(t, e),
                t.Text = ""
        };
        Ae.prototype = {
            ControlType: 8,
            createWrapper: function() {
                var e = this;
                Pe.prototype.createWrapper.call(e),
                    e.Text = ""
            },
            _getDisplayText: function() {
                return this.textBuilder.generateDisplayText()
            },
            attachData: function() {
                var e = this;
                e.textBuilder = new p(e.report,e.Text)
            },
            getGroupVars: function() {
                function e(r) {
                    r.varItems && r.varItems.forEach(function(r) {
                        var o = r.varField
                            , n = o.type;
                        4 !== n && 2 !== n && 5 !== n || t.push(o),
                            4 === n ? o.paramExp && e(o.paramExp) : 5 === n && o.paramExps.forEach(e)
                    })
                }
                var t = [];
                return this.textBuilder.forEach(e),
                    t
            },
            isPureNumericExpression: function() {
                var e = this;
                return e.prepare(),
                e.textBuilder && 1 === e.textBuilder.items.length && e.textBuilder.items[0].isPureNumeric()
            },
            get DisplayText() {
                return this.getDisplayText()
            },
            set DisplayText(e) {
                this.setDisplayText(e)
            },
            get AsFloat() {
                var e = this.textBuilder.items[0]
                    , t = e.__proto__.hasOwnProperty("calculate") ? e.calculate() : 0;
                return isNaN(t) ? 0 : t
            },
            get Value() {
                return this.getDisplayText()
            },
            get AsMemoBox() {
                return this
            }
        },
            q(Ae, Pe);
        var Be = function(e) {
            var t = this;
            Pe.call(t, e),
                t.DataField = ""
        };
        Be.prototype = {
            ControlType: 4,
            createWrapper: function() {
                var e = this;
                Pe.prototype.createWrapper.call(e),
                    e.DataField = ""
            },
            attachData: function() {
                var e = this;
                e.oDataField = e.report.RunningFieldByName(e.DataField)
            },
            unprepare: function() {
                var e = this;
                Pe.prototype.unprepare.call(e),
                    delete e.oDataField
            },
            _getDisplayText: function() {
                var e = this.oDataField;
                return e ? e.DisplayText : ""
            },
            get DisplayText() {
                return this.getDisplayText()
            },
            set DisplayText(e) {
                this.setDisplayText(e)
            },
            get AsFieldBox() {
                return this
            }
        },
            q(Be, Pe);
        var Ne = function(e) {
            var t = this;
            Pe.call(t, e),
                t.SummaryFun = 1,
                t.RankNo = 1,
                t.DataField = "",
                t.DisplayField = "",
                t.Format = ""
        };
        Ne.prototype = {
            ControlType: 5,
            afterLoad: function(e) {
                var t = this;
                Pe.prototype.afterLoad.call(t, e),
                    P(t, "SummaryFun", n.SummaryFun)
            },
            attachData: function() {
                var e, t, r = this, o = r.Format;
                r.init(r.DataField, r.DisplayField),
                o || (e = r.report.RunningFieldByName(r.DataField),
                e && (o = e.Format)),
                    t = r.paramExp,
                    t && 1 === t.varItems.length && 1 === t.varItems[0].type && 6 === t.varItems[0].object.FieldType ? r.formater = new d(o) : r.formater = new h(o)
            },
            _getDisplayText: function() {
                var e, t = this, r = t.SummaryFun, o = t.valueField;
                if (18 === r || 19 === r || t.displayField)
                    e = o ? o.DisplayText : t.value;
                else {
                    if (o) {
                        if (o.IsNull)
                            return "";
                        e = o.AsFloat
                    } else
                        e = t.value;
                    e = t.formater.format(e)
                }
                return e
            },
            get DisplayText() {
                return this.getDisplayText()
            },
            set DisplayText(e) {
                this.setDisplayText(e)
            },
            get Value() {
                return this.getValue()
            },
            set Value(e) {
                this.setValue(e)
            },
            get AsSummaryBox() {
                return this
            }
        },
            q(Ne, Pe),
            q(Ne, f);
        var Ee = function(e) {
            var t = this;
            Pe.call(t, e),
                t.SystemVar = -1,
                t.GroupIndex = 1,
                t.Format = ""
        };
        Ee.prototype = {
            ControlType: 3,
            afterLoad: function(e) {
                var t = this;
                Pe.prototype.afterLoad.call(t, e),
                    P(t, "SystemVar", n.SystemVarType)
            },
            attachData: function() {
                var e = this
                    , t = e.Format;
                1 === e.SystemVar ? e.formater = new d(t) : e.formater = new h(t)
            },
            _getDisplayText: function() {
                var e = this
                    , t = e.SystemVar;
                return t > 0 ? e.formater.format(e.report.SystemVarValue2(t, e.GroupIndex)) : ""
            },
            get DisplayText() {
                return this.getDisplayText()
            },
            set DisplayText(e) {
                this.setDisplayText(e)
            },
            get AsSystemVarBox() {
                return this
            }
        },
            q(Ee, Pe);
        var Me = function(e) {
            De.call(this, e)
        };
        Me.prototype = {
            generateContent: function(e) {
                this.generateCanvas(e)
            },
            drawCanvas: function(e) {
                var t = this;
                t.draw(e),
                t.CustomDraw && De.prototype.drawCanvas.call(t, e)
            },
            DrawDefault: function() {
                this.draw()
            }
        },
            q(Me, De);
        var Ve = function(e) {
            var t = this;
            Me.call(t, e),
                t.L2R = !0,
                t.U2D = !0,
                t.LinePen = new C
        };
        Ve.prototype = {
            ControlType: 10,
            children: ["LinePen"],
            afterLoad: function(e) {
                var t = this
                    , r = t.report
                    , o = r.PixelsToUnit(t.LinePen.Width);
                Me.prototype.afterLoad.call(t, e),
                    t.LinePen.loadFromJSON(e.Pen, r.viewer.alpha.stroke, r.isWR),
                t.Height < o && (t.Height = o,
                    t.HL = 1),
                t.Width < o && (t.Width = o,
                    t.VL = 1)
            },
            draw: function(e) {
                var t, r = this, o = r.canvas, n = new y(o.getContext("2d")), i = 0, a = 0, l = r.VL ? 0 : o.width, s = r.HL ? 0 : o.height;
                n.selectPen(r.LinePen),
                r.L2R || (t = i,
                    i = l,
                    l = t),
                r.U2D || (t = a,
                    a = s,
                    s = t),
                    n.drawLine(i, a, l, s)
            },
            get AsLine() {
                return this
            }
        },
            q(Ve, Me);
        var Oe = function(e) {
            var t = this;
            Me.call(t, e),
                t.ShapeType = 4,
                t.FillColor = 0,
                t.FillStyle = 2,
                t.CornerDx = 4,
                t.CornerDy = 4,
                t.LinePen = new C
        };
        Oe.prototype = {
            ControlType: 2,
            children: ["LinePen"],
            afterLoad: function(e) {
                var t = this
                    , r = t.report
                    , o = t.report.viewer.alpha;
                Me.prototype.afterLoad.call(t, e),
                    P(t, "ShapeType", n.ShapeBoxType),
                    P(t, "FillStyle", n.BackStyle),
                    A(t, "FillColor", o.background),
                    t.LinePen.loadFromJSON(e.Pen, o.stroke, r.isWR)
            },
            draw: function(e) {
                var t, r, o, n = this, i = n.canvas, a = i.width, l = i.height, s = new y(i.getContext("2d")), c = n.LinePen.Width / 2, u = 1 === n.FillStyle;
                switch (s.selectPen(n.LinePen),
                u && s.selectFillColor(n.FillColor),
                    n.ShapeType) {
                    case 7:
                        s.drawLine(0, c, a, c);
                        break;
                    case 1:
                        t = s.circle;
                        break;
                    case 2:
                        t = s.ellipse;
                        break;
                    case 3:
                        t = s.rectangle;
                        break;
                    case 6:
                        t = s.square;
                        break;
                    case 4:
                        t = s.roundRectangle,
                            r = 1;
                        break;
                    case 5:
                        t = s.roundSquare,
                            r = 1
                }
                t && (o = [c, c, a - 2 * c, l - 2 * c],
                r && o.push(n.CornerDx, n.CornerDy),
                    o.push(u),
                    t.apply(s, o)),
                u && s.restoreFillStyle(),
                    s.restorePen()
            },
            get AsShapeBox() {
                return this
            }
        },
            q(Oe, Me);
        var ke = function(e) {
            var t = this;
            Me.call(t, e),
                t.Text = "",
                t.BarcodeType = 4,
                t.BarWidth = 0,
                t.BarRatio = 2,
                t.CheckSum = !1,
                t.Direction = 1,
                t.Alignment = 2,
                t.CaptionPosition = 3,
                t.CaptionAlignment = 2,
                t.PDF417Rows = 0,
                t.PDF417Columns = 0,
                t.PDF417ErrorLevel = 0,
                t.PDF417Simple = !1,
                t.QRCodeVersion = 0,
                t.QRCodeErrorLevel = 1,
                t.QRCodeMask = 0,
                t.DtmxEncoding = 2,
                t.DtmxMatrixSize = 2,
                t.DtmxModuleSize = 0
        };
        ke.prototype = {
            ControlType: 12,
            attachData: function() {
                var e = this;
                e.textBuilder = new p(e.report,e.Text)
            },
            afterLoad: function(e) {
                var t = this
                    , r = e[t.getJsonMember("BarcodeType")];
                switch (Me.prototype.afterLoad.call(t, e),
                    r) {
                    case "Code25_Interleaved":
                        t.BarcodeType = 1;
                        break;
                    case "Code25_Industrial":
                        t.BarcodeType = 2;
                        break;
                    case "Code25_Matrix":
                        t.BarcodeType = 3;
                        break;
                    case "Code39Extended":
                        t.BarcodeType = 5;
                        break;
                    case "Code93Extended":
                        t.BarcodeType = 10;
                        break;
                    default:
                        P(t, "BarcodeType", n.BarcodeType)
                }
                P(t, "Direction", n.BarcodeDirection),
                    P(t, "Alignment", n.StringAlignment),
                    P(t, "CaptionPosition", n.BarcodeCaptionPosition),
                    P(t, "CaptionAlignment", n.StringAlignment),
                    P(t, "DtmxEncoding", n.DtmxEncoding),
                    P(t, "DtmxMatrixSize", n.DtmxMatrixSize)
            },
            getDisplayText: function() {
                return this.textBuilder.generateDisplayText()
            },
            generateCanvas: function(e) {
                var t, r = this, o = r.BarcodeType, n = encodeURI(window.rubylong.grhtml5.barcodeURL), i = {
                    type: o,
                    text: r.DisplayText,
                    codepage: r.report.CodePage
                };
                t = Me.prototype.generateCanvas.call(r, e),
                1 !== r.CaptionPosition && (i.requireShowText = !0),
                n && i.text && (51 === o || 54 === o ? (i.Version = r.QRCodeVersion,
                    i.ErrorLevel = r.QRCodeErrorLevel,
                    i.Mask = r.QRCodeMask) : 50 === o ? (i.Rows = r.PDF417Rows,
                    i.Columns = r.PDF417Columns,
                    i.ErrorLevel = r.PDF417ErrorLevel,
                    i.Simple = r.PDF417Simple) : 52 === o || 53 === o ? (i.Encoding = r.DtmxEncoding,
                    i.MatrixSize = r.DtmxMatrixSize,
                    i.ModuleSize = r.DtmxModuleSize) : i.CheckSum = r.CheckSum,
                    n += "?params=" + encodeURIComponent(JSON.stringify(i)),
                    window.rubylong.ajax(n, function(e, t) {
                        var r, o = this;
                        t && (r = o.getOwnerDetailGrid(),
                        r && r.syncElementData(o.canvas),
                            o._dodraw(JSON.parse(e.responseText)))
                    }, t, "POST"))
            },
            draw: function(e) {},
            _dodraw: function(e) {
                function t(e) {
                    return 50 === e || 51 === e || 54 === e || 52 === e || 53 === e
                }
                function r(e) {
                    return 15 === e || 14 === e || 16 === e || 17 === e || 18 === e
                }
                function o(e) {
                    return parseInt(e, 16)
                }
                function n() {
                    var e, t, r, n, i, a, l, u = s / P, m = c / R, C = {
                        init: function() {
                            p.right = J.left,
                                p.bottom = J.top + m
                        },
                        draw: function() {
                            p.right += u * i,
                                v.fillRect2(p, f),
                                p.left = p.right
                        },
                        end: function() {
                            p.right += u * i,
                                v.fillRect2(p, f),
                                p.top = p.bottom,
                                p.bottom += m,
                                p.left = J.left,
                                p.right = J.left
                        }
                    }, b = {
                        init: function() {
                            p.left = J.right,
                                p.top = J.bottom - m
                        },
                        draw: function() {
                            p.left -= u * i,
                                v.fillRect2(p, f),
                                p.right = p.left
                        },
                        end: function() {
                            p.left -= u * i,
                                v.fillRect2(p, f),
                                p.bottom = p.top,
                                p.top -= m,
                                p.left = J.right,
                                p.right = J.right
                        }
                    }, x = {
                        init: function() {
                            p.bottom = J.top,
                                p.left = J.right - m
                        },
                        draw: function() {
                            p.bottom += u * i,
                                v.fillRect2(p, f),
                                p.top = p.bottom
                        },
                        end: function() {
                            p.bottom += u * i,
                                v.fillRect2(p, f),
                                p.right = p.left,
                                p.left -= m,
                                p.top = J.top,
                                p.bottom = J.top
                        }
                    }, y = {
                        init: function() {
                            p.top = J.bottom,
                                p.right = J.left + m
                        },
                        draw: function() {
                            p.top -= u * i,
                                v.fillRect2(p, f),
                                p.bottom = p.top
                        },
                        end: function() {
                            p.top -= u * i,
                                v.fillRect2(p, f),
                                p.left = p.right,
                                p.right += m,
                                p.top = J.bottom,
                                p.bottom = J.bottom
                        }
                    };
                    switch (50 != F && (u > m ? u = m : m = u),
                        d = u * P,
                        h = m * R,
                        W = (s - d) / 2,
                        U = (c - h) / 2,
                    M || (g = W,
                        W = U,
                        U = g),
                        J.left += W,
                        J.right = J.left + d,
                        J.top += U,
                        J.bottom = J.top + h,
                        w) {
                        case 1:
                            l = C;
                            break;
                        case 2:
                            l = b;
                            break;
                        case 3:
                            l = x;
                            break;
                        default:
                            l = y
                    }
                    for (p = J.clone(),
                             l.init(),
                             B = A.length,
                             g = 0,
                             t = 0; R > t; ++t) {
                        for (r = o(A[g++]) << 4 | o(A[g++]),
                                 n = 128,
                                 f = r & n ? S : T,
                                 i = 1,
                                 n >>= 1,
                                 e = 1; P > e; ++e)
                            0 == e % 8 && (r = o(A[g++]) << 4 | o(A[g++]),
                                n = 128),
                                a = r & n ? S : T,
                                f === a ? ++i : (l.draw(),
                                    f = a,
                                    i = 1),
                                n >>= 1;
                        l.end()
                    }
                }
                function i() {
                    switch (F) {
                        case 15:
                            L = 15,
                                G = 0;
                            break;
                        case 16:
                        case 17:
                        case 18:
                            L = 12,
                                G = 12
                    }
                }
                function a() {
                    function e() {
                        var e = C.BarRatio;
                        switch (F) {
                            case 1:
                            case 2:
                            case 4:
                            case 14:
                            case 15:
                            case 5:
                            case 13:
                            case 16:
                            case 17:
                            case 18:
                            case 19:
                            case 20:
                                2 > e && (e = 2),
                                e > 3 && (e = 3);
                                break;
                            case 3:
                                2.25 > e && (e = 2.25),
                                e > 3 && (e = 3)
                        }
                        return [1, e, 1.5 * e, 2 * e]
                    }
                    function t(e) {
                        var t = o(e);
                        return {
                            w: f[t % 4],
                            lt: Math.floor(t / 4)
                        }
                    }
                    function r(e) {
                        for (var r = e.length, o = 0; r-- > 0; )
                            o += t(e[r]).w;
                        return o
                    }
                    var n, i, a, l, h = C.Alignment, f = e(), m = r(A), i = C.BarWidth, b = O / E.length / 10, x = 2 * c / 5, y = V ? c + 2 * k / 3 : c;
                    if (H = b * L,
                        _ = b * G,
                        u = i ? report.UnitToPixels(i) : (s - H - _) / m,
                        d = u * m + H + _,
                        j = d > s + 1,
                        !j) {
                        switch (h) {
                            case 1:
                                W = H;
                                break;
                            case 2:
                                W = (s - d) / 2 + H;
                                break;
                            default:
                                W = s - d + H
                        }
                        switch (p = J,
                            w) {
                            case 1:
                                p.left += W;
                                break;
                            case 2:
                                p.right -= W;
                                break;
                            case 3:
                                p.top += W;
                                break;
                            default:
                                p.bottom -= W
                        }
                        for (g = 0; B > g; ++g)
                            switch (n = t(A[g]),
                                i = n.w * u,
                                a = 2 === n.lt ? x : 3 == n.lt ? y : c,
                                l = 0 == n.lt ? T : S,
                                w) {
                                case 1:
                                    p.right = p.left + i,
                                        p.bottom = p.top + a,
                                        v.fillRect2(p, l),
                                        p.left = p.right;
                                    break;
                                case 2:
                                    p.left = p.right - i,
                                        p.bottom = p.top + a,
                                        v.fillRect2(p, l),
                                        p.right = p.left;
                                    break;
                                case 3:
                                    p.bottom = p.top + i,
                                        p.left = p.right - a,
                                        v.fillRect2(p, l),
                                        p.top = p.bottom;
                                    break;
                                default:
                                    p.top = p.bottom - i,
                                        p.left = p.right - a,
                                        v.fillRect2(p, l),
                                        p.bottom = p.top
                            }
                    }
                }
                function l() {
                    var e, t, r, o, n = C.CaptionAlignment, i = 1 === n ? 33 : 2 === n ? 34 : 36, a = C.Alignment, l = Y.left + W;
                    if (M ? (3 === D && (U = -U),
                        Y.top += U,
                        Y.bottom += U) : (2 === D && (W = -W),
                        Y.left += W,
                        Y.right += W),
                        V)
                        switch (g = 0,
                            F) {
                            case 15:
                                l -= H,
                                    v.drawText(E.substr(g++, 1), l, Y.top),
                                    l = Y.left + W + 4 * u,
                                    o = l + 42 * u,
                                    v.drawEqualSpaceText(E.substr(g, 6), l, o, Y.top),
                                    g += 6,
                                    l = Y.left + W + 50 * u,
                                    o = l + 41 * u,
                                    v.drawEqualSpaceText(E.substr(g, 6), l, o, Y.top);
                                break;
                            case 14:
                                l += 4 * u,
                                    o = l + 28 * u,
                                    v.drawEqualSpaceText(E.substr(g, 4), l, o, Y.top),
                                    g += 4,
                                    l = Y.left + W + 36 * u,
                                    o = l + 27 * u,
                                    v.drawEqualSpaceText(E.substr(g, 4), l, o, Y.top);
                                break;
                            case 17:
                            case 18:
                                v.fontSizeTo(Math.floor(.77 * v.usingFont.Size)),
                                    k = N(v.usingFont),
                                    O = v.measureTextWidth("0"),
                                    l -= H,
                                    v.drawText("0", l, Y.bottom - k),
                                    e = E.substr(6),
                                    O = v.measureTextWidth(e),
                                    l = Y.right - W + _ - O,
                                    v.drawText(e, l, Y.bottom - k),
                                    v.restoreFont(),
                                    l = Y.left + W + 4 * u,
                                    o = l + 42 * u,
                                    v.drawEqualSpaceText(E.substr(0, 6), l, o, Y.top);
                                break;
                            case 16:
                                v.fontSizeTo(Math.floor(.77 * v.usingFont.Point)),
                                    k = N(v.usingFont),
                                    e = E.substr(0, 1),
                                    O = v.measureTextWidth(e),
                                    l -= H,
                                    v.drawText(e, l, Y.bottom - k),
                                    e = E.substr(11),
                                    O = v.measureTextWidth(e),
                                    l = Y.left + W + 95 * u + Math.max(_ - O, 2),
                                    v.drawText(e, l, Y.bottom - k),
                                    v.restoreFont(),
                                    l = Y.left + W + 11 * u,
                                    o = l + 35 * u,
                                    ++g,
                                    v.drawEqualSpaceText(E.substr(g, 5), l, o, Y.top),
                                    l = o + 5 * u,
                                    o = l + 35 * u,
                                    g += 5,
                                    v.drawEqualSpaceText(E.substr(g, 5), l, o, Y.top)
                        }
                    else if (M) {
                        switch (a) {
                            case 1:
                                Y.right = Y.left + d;
                                break;
                            case 2:
                                Y.left = (Y.left + Y.right - d) / 2,
                                    Y.right = Y.left + d;
                                break;
                            default:
                                Y.left = Y.right - d
                        }
                        v.drawTextAlign2(E, Y, i)
                    } else {
                        switch (a) {
                            case 1:
                                Y.bottom = Y.top + d;
                                break;
                            case 2:
                                Y.top = (Y.top + Y.bottom - d) / 2,
                                    Y.bottom = Y.top + d;
                                break;
                            default:
                                Y.top = Y.bottom - d
                        }
                        switch (t = Y.right,
                            n) {
                            case 1:
                                r = Y.top;
                                break;
                            case 2:
                                r = (Y.top + Y.bottom - O) / 2;
                                break;
                            default:
                                r = Y.bottom - O
                        }
                        v.drawTextRotate(E, t, r, 270)
                    }
                }
                var s, c, u, d, h, f, p, g, C = this, b = C.canvas, v = new y(b.getContext("2d")), x = C.getUsingFont(), T = C.BackColor, S = C.ForeColor, F = C.BarcodeType, w = C.Direction, D = C.CaptionPosition, P = e.cols, R = e.rows, A = e.graph, B = A ? A.length : 0, E = e.text ? e.text : C.DisplayText, t = t(F), M = 1 === w || 2 === w, V = r(F) && 1 === w && 3 === D, O = 0, k = N(x), I = t ? Math.max(k, 10) : 0, L = 0, G = 0, H = 0, _ = 0, j = !1, W = 0, U = 0, z = new m(0,0,b.width,b.height), J = z.clone(), Y = z.clone();
                if (P) {
                    if (v.selectFont(x),
                    1 !== D && (M ? 2 === D ? (Y.bottom = Y.top + k,
                        J.top = Y.bottom + I) : (Y.top = Y.bottom - k,
                        J.bottom = Y.top - I) : 2 === D ? (Y.left = Y.right - k,
                        J.right = Y.left - I) : (Y.right = Y.left + k,
                        J.left = Y.right + I)),
                        s = M ? J.Width() : J.Height(),
                        c = M ? J.Height() : J.Width(),
                    10 > s || 10 > c)
                        return void v.restoreFont();
                    1 != D && (O = v.measureTextWidth(E)),
                        t ? n() : (i(),
                            a()),
                        j ? pdd.drawTextCenter("条宽度太大，条码无法完整显示", (z.left + z.right) / 2, (z.top + z.bottom - k) / 2) : 1 != D && l(),
                        v.restoreFont()
                }
            },
            get Value() {
                return this.getDisplayText()
            },
            get DisplayText() {
                return this.getDisplayText()
            },
            get AsBarcode() {
                return this
            }
        },
            q(ke, Me);
        var Ie = function(e) {
            var t = this;
            De.call(t, e),
                t.Alignment = 3,
                t.SizeMode = 3,
                t.TransparentMode = 0,
                t.RotateMode = 0,
                t.ImageIndex = 0,
                t.Picture = "",
                t.ImageFile = "",
                t.DataField = ""
        };
        Ie.prototype = {
            ControlType: 7,
            afterLoad: function(e) {
                var t = this;
                De.prototype.afterLoad.call(t, e),
                    P(t, "SizeMode", n.PictureSizeMode),
                    P(t, "TransparentMode", n.PictureTransparentMode),
                    P(t, "RotateMode", n.PictureRotateMode)
            },
            attachData: function() {
                var e = this;
                e.oDataField = e.report.RunningFieldByName(e.DataField)
            },
            generateNormal: function(e) {
                var t, r, o, n, a, l, s, c, u = this, d = u.Picture, h = u.oDataField, f = u.ImageFiles, p = f ? f[u.getOwnerDetailGrid().Recordset.RecordNo] : u.ImageFile, g = u.ImageIndex, m = u.SizeMode;
                if (d ? n = "data:image;base64," + d : h ? (n = h.AsString,
                n.length > 256 && (n = "data:image;base64," + n)) : p ? n = p : g && (n = g),
                    n)
                    if (isNaN(n))
                        if (5 === m)
                            e.addStyle("background-image", "url(" + n + ")");
                        else {
                            if (t = u.pxRect,
                            t || (t = u.owner.getRect()),
                                r = t.Width(),
                                o = t.Height(),
                                e.addStyle("text-align", "center"),
                                e.addStyle("vertical-align", "middle"),
                                a = new w("img",e),
                                a.skipend = 1,
                            2 === u.SizeMode)
                                s = r,
                                    c = o;
                            else
                                try {
                                    l = document.createElement("img"),
                                        l.src = n,
                                        3 === u.SizeMode || l.width > r || l.height > o ? l.width * o > r * l.height ? s = r : c = o : l.width && l.height ? (s = l.width,
                                            c = l.height) : (s = r,
                                            c = o)
                                } catch (C) {
                                    s = r,
                                        c = o
                                }
                            s && a.addAttribute("width", Math.round(s)),
                            c && a.addAttribute("height", Math.round(c)),
                                a.addAttribute("src", n)
                        }
                    else
                        n = +n,
                            e.addStyle("text-align", "center"),
                            e.addStyle("vertical-align", "middle"),
                            a = new w("input",e),
                            a.skipend = 1,
                            a.addAttribute("type", "checkbox"),
                            a.addAttribute("id", u.getCheckBoxID()),
                            a.addClass(i.CSS_CB),
                        -1 !== n && -3 !== n || a.addAttribute("checked"),
                        h && a.addAttribute(i.ATTR_FIELD, h.Name),
                            u.report._has_cb = 1
            },
            getCheckBoxID: function() {
                var e, t = this, r = t.owner.owner;
                return e = r.ContentCells ? "CBC" + r.owner.Recordset.RecordNo : r.Header ? "CBG" + r.recordset.recordNo : t.Name
            },
            set Text(e) {
                this[e.length > 256 ? "Picture" : "ImageFile"] = e
            },
            get checked() {
                var e = this
                    , t = e.oDataField
                    , r = t ? t.AsInteger : e.ImageIndex;
                return -1 === r || -3 === r
            },
            set checked(e) {
                var t, r = this, o = r.oDataField, n = e ? -1 : -2;
                o ? (t = o.owner,
                    t.Edit(),
                    o.AsInteger = n,
                    t.Post()) : r.ImageIndex = n,
                    document.getElementById(r.getCheckBoxID()).checked = e
            },
            get AsPictureBox() {
                return this
            },
            LoadFromFile: function(e) {
                var t = this
                    , r = t.getOwnerDetailGrid();
                r ? (t.ImageFiles || (t.ImageFiles = {}),
                    t.ImageFiles[r.Recordset.RecordNo] = e) : t.ImageFile = e
            }
        },
            q(Ie, De);
        var Le = function(e) {
            var t = this;
            De.call(t, e)
        };
        Le.prototype = {
            ControlType: 6,
            get AsRichTextBox() {
                return this
            },
            generateNormal: function(e) {
                e.innerText = "不支持RichTextBox"
            }
        },
            q(Le, De);
        var Ge = function(e) {
            var t = this;
            De.call(t, e),
                t.HideOnRecordsetEmpty = !1,
                t.RelationFields = "",
                t.Report = new it(e.report.viewer,t)
        };
        Ge.prototype = {
            ControlType: 9,
            afterLoad: function(e) {
                var t = this
                    , r = e.Report;
                De.prototype.afterLoad.call(t, e),
                    t.Report.isWR = t.report.isWR,
                r && t.Report.loadFromJSON(r)
            },
            prepare: function() {
                var e = this
                    , t = e.report;
                De.prototype.prepare.call(e),
                t._srChildren || (t._srChildren = []),
                    t._srChildren.push(e),
                    e.prepareRelationRecordset()
            },
            prepareRelationRecordset: function() {
                var e, t, r, o, n, i, a = this, l = a.RelationFields, s = a.report, c = s.DetailGrid, u = a.Report, d = u.DetailGrid;
                if (l && d && c && (e = c.Recordset,
                    r = d.Recordset,
                    t = e.decodeFields(l),
                    o = r.decodeFields(l),
                t.length === o.length && r.RecordCount)) {
                    for (a._rmFields = t,
                             a._rdFields = o,
                             i = a._relations = [],
                             r.First(),
                             n = [],
                             i.push(n),
                             n.push(r.curRecord),
                             r.keepValue(),
                             r.Next(); !r.Eof(); )
                        r.fieldsKeepedValueChanged(o) && (n = [],
                            i.push(n),
                            r.keepValue()),
                            n.push(r.curRecord),
                            r.Next();
                    i.sort(function(e, t) {
                        for (var r, n, i, a = 0, l = o.length; l > a; ) {
                            if (r = o[a++]._tableMember,
                                n = e[0][r],
                                i = t[0][r],
                            i > n)
                                return -1;
                            if (n > i)
                                return 1
                        }
                        return 0
                    })
                }
            },
            generateNormal: function(t) {
                for (var r, o, n, i = this, a = i.Report.DetailGrid ? i.Report.DetailGrid.Recordset : e, l = i._relations, s = i.report, c = s.DetailGrid, u = i.getOwnerDetailGrid(), d = i.Report.Parameters, h = d.Count; h > 0; )
                    o = d.Item(h--),
                        n = o.Name,
                        u ? (r = s.FieldByName(n),
                            r ? o.Value = r.Value : (r = s.ParameterByName(n),
                            r && (o.Value = r.Value))) : (r = s.ParameterByName(n),
                            r ? o.Value = r.Value : c && (r = s.FieldByName(n),
                            r && (o.Value = r.Value)));
                l && i.attachRelationTable(),
                (!i.HideOnRecordsetEmpty || !a || a.RecordCount) && i.Report.generate(t),
                l && i.detachRelationTable()
            },
            detachRelationTable: function() {
                var e = this.Report.DetailGrid.Recordset;
                e._dataTable = e._bakTable,
                    e._relTblIndex = -1,
                    delete e._bakTable
            },
            attachRelationTable: function() {
                function e() {
                    for (var e, r, o = n._rmFields, i = n._rdFields, a = o.length, s = 0; a > s; ) {
                        if (r = t[i[s]._tableMember],
                            e = l[o[s++]._tableMember],
                        e > r)
                            return -1;
                        if (r > e)
                            return 1
                    }
                    return 0
                }
                var t, r, o, n = this, i = n._relations, a = n.Report.DetailGrid.Recordset, l = n.report.DetailGrid.Recordset.curRecord, s = i.length - 1, c = 0;
                for (a._bakTable = a._dataTable,
                         a._dataTable = []; s >= c; )
                    if (r = Math.floor((c + s) / 2),
                        t = i[r][0],
                        o = e(),
                    0 > o)
                        c = r + 1;
                    else {
                        if (!(o > 0)) {
                            a._dataTable = i[r],
                                a._relTblIndex = r;
                            break
                        }
                        s = r - 1
                    }
                return !0
            },
            attachRelationTable2: function(e) {
                var t = this;
                t.Report.DetailGrid.Recordset._dataTable = t._relations[e]
            },
            get AsSubReport() {
                return this
            }
        },
            q(Ge, De);
        var He = function(e) {
            var t = this;
            xe.call(t, e),
                t.Visible = !0,
                t.FixedWidth = !1,
                t.Width = t.report.cm2Size(3)
        };
        He.prototype = {
            prepare: function() {
                self.pxWidth = this.report.size2Pixel(self.Width)
            },
            get FreeGrid() {
                return this.owner
            }
        },
            q(He, xe);
        var _e = function(e) {
            var t = this;
            xe.call(t, e),
                t.Visible = !0,
                t.Height = t.report.cm2Size(.58)
        };
        _e.prototype = {
            prepare: function() {
                self.pxHeight = this.report.size2Pixel(self.Height)
            },
            get FreeGrid() {
                return this.owner
            }
        },
            q(_e, xe);
        var je = function(e) {
            var t = this;
            De.call(t, e),
                t.ColumnCount = 5,
                t.RowCount = 5,
                t.TitleRows = 1,
                t.ShowColLine = !0,
                t.ShowRowLine = !0,
                t.ColLinePen = new C,
                t.RowLinePen = new C,
                t.columns = [],
                t.rows = [],
                t.cells = []
        };
        je.prototype = {
            ControlType: 13,
            attachData: function() {
                var e, t, r, o = this;
                for (t = o.cells.length,
                         e = 0; t > e; e++)
                    r = o.cells[e],
                    r && r.forEach(function(e) {
                        e.attachData(),
                            e.prepare()
                    })
            },
            prepareChildren: function() {
                var e, t = this, r = t.columns, o = t.rows, n = t.cells;
                for (e = r.length = t.ColumnCount; e--; )
                    r[e] || (r[e] = new He(t));
                for (e = n.length = o.length = t.RowCount; e--; )
                    o[e] || (o[e] = new _e(t)),
                    n[e] || (n[e] = [],
                        n[e].length = t.ColumnCount)
            },
            prepare: function() {
                var e = this
                    , t = new F;
                e.columns.length === e.ColumnCount && e.rows.length === e.RowCount && e.cells.length === e.RowCount || e.prepareChildren(),
                    De.prototype.prepare.call(e),
                    t.addCellBorder(e),
                    e.blankCellStyle = e.report.viewer.addCustomStyle(t)
            },
            assign: function(e) {
                var t = this;
                De.prototype.assign.call(t, e),
                    e.columns.forEach(function(e, r) {
                        var o = new He(t);
                        o.assign(e),
                            o.pxWidth = e.pxWidth,
                            t.columns[r] = o
                    }),
                    e.rows.forEach(function(e, r) {
                        var o = new _e(t);
                        o.assign(e),
                            o.pxHeight = e.pxHeight,
                            t.rows[r] = o
                    }),
                    e.cells.forEach(function(e, r) {
                        e.forEach(function(e, o) {
                            var n = new ye(t,r,o);
                            n.assign(e),
                            t.cells[r] || (t.cells[r] = []),
                                t.cells[r][o] = n
                        })
                    }),
                    t.Height = e.Height
            },
            afterLoad: function(e) {
                var t, r, o, n, i, a = this, l = a.report, s = a.Border, c = a.columns = [], u = a.rows = [], d = a.cells = [];
                if (De.prototype.afterLoad.call(a, e),
                    a.prepareChildren(),
                    e.FreeGridColumn)
                    for (n = e.FreeGridColumn.length,
                             o = 0; n > o; o++)
                        r = e.FreeGridColumn[o],
                            c[r.index - 1].loadFromJSON(r);
                if (e.FreeGridRow)
                    for (n = e.FreeGridRow.length,
                             o = 0; n > o; o++)
                        r = e.FreeGridRow[o],
                            u[r.index - 1].loadFromJSON(r);
                if (e.FreeGridCell)
                    for (n = e.FreeGridCell.length,
                             o = 0; n > o; o++)
                        r = e.FreeGridCell[o],
                            --r.row,
                            --r.col,
                            t = d[r.row][r.col] = new ye(a,r.row,r.col),
                            t.loadFromJSON(r);
                for (n = c.length,
                         i = 0,
                         o = 0; n > o; o++)
                    t = c[o],
                        t.pxWidth = l.size2Pixel(t.Width),
                        i += t.Width;
                for (a.Width = i + l.pixel2Size(s.getLeftWidth() + s.getRightWidth()),
                         n = u.length,
                         i = 0,
                         o = 0; n > o; o++)
                    t = u[o],
                        t.pxHeight = l.size2Pixel(t.Height),
                        i += t.Height;
                a.Height = i + l.pixel2Size(s.getTopWidth() + s.getBottomWidth())
            },
            generateNormal: function(e) {
                var t, r, o, n, i = this, a = i.report.viewer, l = new w("table",e), s = new w("colgroup",l), c = [];
                for (i.Font.font && l.addClass(a.selectFont2(i.Font.font)),
                         l.styles.addBorder(i.Border),
                         l.addStyle("border-collapse", "collapse"),
                         l.addStyle("width", "100%"),
                     i.toFillBack() && l.addBackColorStyle(i.BackColor),
                         n = i.ColumnCount,
                         r = 0,
                         o = 0; n > o; o++)
                    r += i.columns[o].pxWidth;
                for (o = 0; n > o; o++)
                    t = new w("col",s),
                        t.skipend = 1,
                        t.addStyle("width", V(100 * i.columns[o].pxWidth / r));
                for (o = 0; o < i.RowCount; o++)
                    r = [],
                        r.length = i.ColumnCount,
                        c.push(r);
                i.cells.forEach(function(e, t) {
                    e.forEach(function(e) {
                        function t() {
                            for (var t, r = e.row, o = r + e.RowSpan, n = e.col + e.ColSpan; o > r; r++)
                                for (t = e.col; n > t; t++)
                                    c[r][t] = 1
                        }
                        (e.ColSpan > 1 || e.RowSpan > 1) && t(),
                            c[e.row][e.col] = e
                    })
                }),
                    c.forEach(function(e, t) {
                        var r, o = i.ColumnCount, n = 0, s = new w("tr",l);
                        s.addStyle("height", M(i.rows[t].pxHeight));
                        do {
                            for (; o > n && !e[n]; )
                                r = new w("td",s),
                                    r.addClass(a._getCSSName(i.blankCellStyle)),
                                    n++;
                            for (; o > n && 1 === e[n]; )
                                n++;
                            o > n && (r = e[n]) && (r.generate(s),
                                n = r.col + r.ColSpan)
                        } while (o > n)
                    })
            },
            get AsFreeGrid() {
                return this
            },
            ColumnAt: function(e) {
                var t = this
                    , r = t.columns;
                return r.length !== t.ColumnCount && t.prepareChildren(),
                    r[e - 1]
            },
            RowAt: function(e) {
                var t = this
                    , r = t.rows;
                return r.length !== t.RowCount && t.prepareChildren(),
                    r[e - 1]
            },
            CellAt: function(t, r) {
                var o, n = this, i = n.cells, a = e;
                return i.length !== n.RowCount && n.prepareChildren(),
                t > 0 && t <= n.RowCount && r > 0 && r <= n.ColumnCount && (o = i[--t],
                    a = o[--r],
                a || (a = o[r] = new ye(n,t,r))),
                    a
            },
            CellByDataName: function(t) {
                for (var r, o, n, i = this, a = i.cells, l = a.length, s = l; s--; )
                    for (r = a[s],
                             o = r.length; o--; )
                        if (n = r[o],
                        n && n.DataName === t)
                            return n;
                return e
            }
        },
            q(je, De);
        var We = function(e) {
            var t = this;
            xe.call(t, e),
                t.Visible = !0,
                t.CanGrow = !1,
                t.CanShrink = !1,
                t.Height = e.report.cm2Size(t.defaultHeight()),
                t.BackColor = e.BackColor,
                t.FormatScript = "",
                t.Font = new x(t.getParentFont())
        };
        We.prototype = {
            afterLoad: function(e) {
                var t = this
                    , r = t.report;
                e[t.getJsonMember("BackColor")] || (t.BackColor = t.getParentBackColor()),
                    A(t, "BackColor", r.viewer.alpha.background),
                    t.Font.loadFromJSON(e.Font, r.isWR)
            },
            prepare: function() {
                var e = this
                    , t = e.Controls;
                e.registerEventClass(),
                    e.pxHeight = e.report.size2Pixel(e.Height),
                    e._singleHidableSR = e.getSingleHidableSR(),
                    e._singleSR = t && 1 === t.Count && 9 === t.Item(1).ControlType
            },
            unprepare: function() {
                var e = this;
                e.resetEventClass(),
                    delete e.defaultStyle
            },
            generate: function(e) {
                var t = this;
                t.report.fireSectionFormat(t),
                    t.pxHeight = t.report.size2Pixel(t.Height),
                t.isToGenerate() && (t._singleSR && !t.Group ? t.Controls.Item(1).generateContent(e) : t.doGenerate(e))
            },
            defaultHeight: function() {
                return 3
            },
            getParentBackColor: function(e) {
                return this.owner.BackColor
            },
            getParentFont: function() {
                return this.owner.Font
            },
            getSingleHidableSR: function() {
                return e
            },
            isControlPositionClass: function() {
                return 0
            },
            isToGenerate: function() {
                function e() {
                    var e, r = t._singleHidableSR, o = r._relations;
                    return o && r.attachRelationTable(),
                        e = !r.Report.DetailGrid || r.Report.DetailGrid.Recordset.RecordCount,
                    o && r.detachRelationTable(),
                        e
                }
                var t = this
                    , r = t.Visible && t.Height > 0;
                return r && t._singleHidableSR && (r = e()),
                    r
            }
        },
            q(We, xe);
        var Ue = function(e) {
            var t = this;
            We.call(t, e),
                t.Controls = new de(t)
        };
        Ue.prototype = {
            children: ["Controls"],
            afterLoad: function(e) {
                var t = this;
                We.prototype.afterLoad.call(t, e),
                    t.Controls.loadFromJSON(e.Control)
            },
            attachData: function() {
                this.Controls.attachData()
            },
            prepare: function() {
                var e = this
                    , t = e.Controls
                    , r = e.report
                    , o = r.viewer;
                We.prototype.prepare.call(e),
                    t.layout(),
                2 !== o.options.controlPosition && e.buildTableLayout(r.getReportWidth(), e.pxHeight),
                    t.prepare(),
                e.tableRows && (r.reportFitHeight && ce(e.tableRows),
                    e.tableRows.forEach(function(e) {
                        e.defaultStyle = o.selectSectionItem(e)
                    })),
                    e.defaultStyle = o.selectSectionItem(e)
            },
            unprepare: function() {
                var e = this;
                We.prototype.unprepare.call(e),
                    e.Controls.unprepare()
            },
            buildTableLayout: function(t, r) {
                function o(t, r, o, n) {
                    var i, a, l, s = [];
                    for (c.forEach(function(e) {
                        s.push({
                            pos: e.pxRect[t],
                            count: 1
                        }, {
                            pos: e.pxRect[r],
                            count: 1
                        })
                    }),
                             s.sort(function(e, t) {
                                 return e.pos - t.pos
                             }),
                             i = s.length,
                             a = 0; i > a && s[a].pos < o; )
                        s[a++] = e;
                    if (a > 0 && (s.unshift({
                        pos: o,
                        count: a
                    }),
                        ++i),
                        !isNaN(n)) {
                        for (a = i - 1; a >= 0 && s[a] && s[a].pos > n; )
                            s[a--] = e;
                        a = i - 1 - a,
                        a >= 0 && s.push({
                            pos: n,
                            count: a
                        })
                    }
                    return l = {
                        pos: -98765
                    },
                        s.forEach(function(t, r) {
                            t && (l.pos === t.pos ? (l.count++,
                                s[r] = e) : l = t)
                        }),
                        l = {
                            pos: -98765
                        },
                        s.forEach(function(t, r) {
                            t && (t.pos - l.pos < 8 ? l.count > t.count ? (l.count += t.count,
                                s[r] = e) : (t.count += l.count,
                                s[a] = e,
                                l = t,
                                a = r) : (l = t,
                                a = r))
                        }),
                        s = s.filter(function(e) {
                            return e
                        }),
                        i = s.length,
                    i > 1 && (s[0].pos < o + 4 && (s[0].pos = o),
                    s[0].pos !== o && (s.unshift({
                        pos: o,
                        count: 0
                    }),
                        i++),
                    isNaN(n) || (i--,
                    n - s[i].pos < 4 && (s[i].pos = n),
                    s[i].pos !== n && s.push({
                        pos: n,
                        count: 0
                    }))),
                        s
                }
                function n(e, t, r) {
                    var o = 0
                        , n = r.length;
                    c.sort(function(t, r) {
                        return t.pxRect[e] - r.pxRect[e]
                    }),
                        c.forEach(function(i) {
                            for (var a = i.pxRect[e]; n > o && r[o].pos < a; )
                                o++;
                            (o >= n || o > 0 && a - r[o - 1].pos < r[o].pos - a) && --o,
                                i.cell[t] = o
                        })
                }
                var i, a, l, s, c, u, d, h, f = this, p = f.report.viewer, g = f.getTableColEdges, m = f.getMergedColumns ? f.getMergedColumns() : 0, C = [], b = [];
                if (c = f.Controls.items.filter(function(e) {
                    return e.cell = {
                        control: e
                    },
                        e.Visible
                }),
                    a = c.length,
                    u = o("top", "bottom", 0, r),
                    l = u.length - 1,
                    g ? (d = f.getTableColEdges(),
                        s = d.length - 1,
                    8 > s && (h = o("left", "right", 0, t),
                    h.length > 2 * s && (d = h,
                        g = e))) : d = o("left", "right", 0, t),
                    s = d.length - 1,
                    !g)
                    for (f.tableCols = [],
                             i = 0; s > i; )
                        f.tableCols.push(d[i + 1].pos - d[i++].pos);
                for (n("top", "beginRow", u),
                         n("bottom", "endRow", u),
                         n("left", "beginCol", d),
                         n("right", "endCol", d),
                         c.forEach(function(e) {
                             var t = e.cell;
                             t.endCol <= t.beginCol && t.endCol < s && t.endCol++,
                             t.endRow <= t.beginRow && t.endRow < l && t.endRow++
                         }),
                         i = 0; l > i; )
                    b.push({
                        section: f,
                        pxHeight: u[i + 1].pos - u[i++].pos,
                        cells: []
                    });
                for (c = c.filter(function(e) {
                    var t = e.cell;
                    return t.beginCol < t.endCol && t.beginRow < t.endRow
                }),
                         c.sort(function(e, t) {
                             var r = e.cell.beginRow - t.cell.beginRow;
                             return r ? r : e.cell.beginCol - t.cell.beginCol
                         }),
                         i = 0; l > i; i++)
                    h = [],
                        h.length = s,
                        C.push(h);
                m && m.forEach(function(e) {
                    var t = e.index;
                    C.forEach(function(e) {
                        e[t] = 1
                    })
                }),
                    i = 0,
                    c.forEach(function(e) {
                        for (var t, r, o = e.cell, n = C[o.beginRow]; o.beginCol < o.endCol && n[o.beginCol]; )
                            o.beginCol++;
                        for (; o.endCol > o.beginCol && n[o.endCol - 1]; )
                            o.endCol--;
                        if (o.beginCol < o.endCol) {
                            for (t = o.beginRow; t < o.endRow; t++)
                                for (r = o.beginCol; r < o.endCol; r++)
                                    C[t][r] = 1;
                            n[o.beginCol] = e,
                                i++
                        }
                    }),
                    1 === p.options.controlPosition || m || a && i / a > .8 ? (C.forEach(function(e, t) {
                        var r, o, n, i = b[t].cells;
                        r = o = 0;
                        do {
                            for (; s > o && !e[o]; )
                                o++;
                            for (o > r && (i.push({
                                beginCol: r,
                                endCol: o
                            }),
                                r = o); s > o && 1 === e[o]; )
                                o++;
                            r = o,
                            s > r && (n = e[r]) && (n = n.cell,
                                i.push(n),
                                r = o = n.endCol)
                        } while (s > o)
                    }),
                        b.forEach(function(e) {
                            var t = 0
                                , r = 0;
                            e.cells.forEach(function(e) {
                                e.control && (t++,
                                e.control.CanShrink && r++)
                            }),
                            t && r === t && delete e.pxHeight
                        }),
                        f.tableRows = b) : (delete f.tableCols,
                        delete f.tableRows,
                        f.Controls.items.forEach(function(e) {
                            delete e.cell
                        }))
            },
            doGenerate: function(e) {
                var t = this
                    , r = t.report.viewer;
                e = new w(t.tableRows ? "table" : "div",e),
                    t.addElementEventClass(e),
                    e.addClass(r.selectSection(t)),
                    t.tableRows ? (e.addStyle("border-collapse", "collapse"),
                        t.generateTableRows(e)) : (e.addStyle("position", "relative"),
                        t.Controls.generate(e))
            },
            generateTableRows: function(e, t) {
                function r() {
                    var e, t, r = d.length - 1, o = d[r], n = s.showingColumns, i = s.Border;
                    n.length > 0 && (e = n[n.length - 1],
                        t = i.getLeftWidth() + i.getRightWidth(),
                    Math.abs(o - e.pxWidth - t) < .5 && (d[r] -= t))
                }
                var o, n, i = this, a = i.owner, l = i.report, s = a.RunningDetailGrid, c = l.viewer, u = i.owner !== l || l.reportFitWidth, d = i.tableCols, h = 0;
                d && (d.forEach(function(e) {
                    h += e
                }),
                a == i.report && s && r(),
                    o = new w("colgroup",e),
                    e.addStyle("width", u ? "100%" : M(h)),
                    d.forEach(function(e) {
                        n = new w("col",o),
                            n.skipend = 1,
                            n.addStyle("width", V(100 * e / h))
                    })),
                    i.tableRows.forEach(function(r, o) {
                        function n(e) {
                            e.ContentCell.generate(a),
                                e.mergingElement = a.children[a.children.length - 1],
                            e.ContentCell.getUsingFont() !== i.getUsingFont() && e.mergingElement.addClass(c.selectFont2(e.ContentCell.getUsingFont())),
                            e.ContentCell.BackColor !== i.BackColor && e.mergingElement.addBackColorStyle(e.ContentCell.BackColor),
                                e.mergedHeader.todoMerge = 0
                        }
                        var a;
                        a = new w("tr",e),
                            a.addClass(c.selectSection(r)),
                            r.cells.forEach(function(e) {
                                var r, i, l, s = e.control;
                                if (!o && t)
                                    for (; (l = t[0]) && l.index <= e.beginCol; )
                                        n(l),
                                            t.shift();
                                r = new w("td",a),
                                    i = e.endCol - e.beginCol,
                                i > 1 && r.addAttribute("colspan", i + ""),
                                e.endRow && (i = e.endRow - e.beginRow,
                                i > 1 && r.addAttribute("rowspan", i + ""),
                                s.Visible && (13 !== s.ControlType ? (r.addClass(c.selectControl(s)),
                                s.Font.font && r.addClass(c.selectFont(s.Font.font, s.defaultFontStyle))) : r.addStyle("padding", "0px"),
                                    s.generateInCell(r)))
                            }),
                        !o && t && t.forEach(function(e) {
                            n(e)
                        })
                    })
            },
            getRect: function() {
                var e = this;
                return new m(0,0,e.report.getReportWidth(),e.pxHeight)
            },
            getSingleHidableSR: function() {
                var t = this
                    , r = t.Controls.items
                    , o = r[0];
                return 1 === r.length && 9 === o.ControlType && o.HideOnRecordsetEmpty ? o : e
            },
            _runningNo: function() {
                return 0
            }
        },
            q(Ue, We);
        var ze = function(e) {
            var t = this;
            Ue.call(t, e),
                t.Name = ""
        };
        ze.prototype = {},
            q(ze, Ue);
        var Je = function(e) {
            var t = this;
            Ue.call(t, e),
                t.Name = ""
        };
        Je.prototype = {},
            q(Je, Ue);
        var Ye = function(e) {
            var t = this;
            We.call(t, e),
                t.Height = t.report.cm2Size(.58)
        };
        Ye.prototype = {
            defaultHeight: function() {
                return .58
            }
        },
            q(Ye, We);
        var Xe = function(e) {
            var t = this;
            Ye.call(t, e),
                t.ContentCells = new pe(t),
                t.AlternatingBackColor = t.BackColor
        };
        Xe.prototype = {
            afterLoad: function(e) {
                var t = this;
                Ye.prototype.afterLoad.call(t, e),
                    e[t.getJsonMember("AlternatingBackColor")] ? A(t, "AlternatingBackColor", t.report.viewer.alpha.background) : t.AlternatingBackColor = t.BackColor,
                    t.ContentCells.loadFromJSON(e.ColumnContentCell)
            },
            prepare: function() {
                var e, t = this, r = t.report.viewer;
                Ye.prototype.prepare.call(t),
                    t.defaultStyle = r.selectSectionItem(t),
                t.AlternatingBackColor !== t.BackColor && (e = t.BackColor,
                    t.BackColor = t.AlternatingBackColor,
                    t.altSectionStyle = r.selectSectionItem(t),
                    t.BackColor = e),
                    t.ContentCells.prepare()
            },
            unprepare: function() {
                var e = this;
                Ye.prototype.unprepare.call(e),
                    e.ContentCells.unprepare()
            },
            generate: function(e, t) {
                var r = this;
                r.owner.Recordset.MoveTo(t),
                    Ye.prototype.generate.call(r, e)
            },
            doGenerate: function(e) {
                var t = this
                    , r = t.report.viewer
                    , o = t.owner
                    , n = o.showingColumns
                    , a = o.Recordset.RecordNo;
                e = new w("tr",e),
                    t.addElementEventClass(e),
                    e.addAttribute(i.ATTR_CONTENT_RECNO, a + ""),
                    t.altSectionStyle && a % 2 ? (t._BackColor = t.BackColor,
                        t._defaultStyle = t.defaultStyle,
                        t.BackColor = t.AlternatingBackColor,
                        t.defaultStyle = t.altSectionStyle,
                        e.addClass(r.selectSection(t)),
                        t.BackColor = t._BackColor,
                        t.defaultStyle = t._defaultStyle) : e.addClass(r.selectSection(t)),
                    o.hasMergedColumn ? n.forEach(function(t) {
                        var r, o, n, i = t.ContentCell, a = t.mergedHeader;
                        if (a) {
                            if (a.todoMerge) {
                                if (a.SameAsColumn)
                                    i.generate(e);
                                else
                                    for (r = a.Controls,
                                             o = r.Count; o > 0; )
                                        if (n = r.Item(o--),
                                        n.oAlignColumn === t) {
                                            i.generateMerge(e, n);
                                            break
                                        }
                                e.children && (t.mergingElement = e.children[e.children.length - 1])
                            }
                        } else
                            i.generate(e)
                    }) : n.forEach(function(t) {
                        t.ContentCell.generate(e)
                    }),
                o.hasMergedColumn && o.Groups.forEach(function(e) {
                    e.Header.todoMerge && (e.Header.todoMerge = 0)
                })
            },
            getSingleHidableSR: function() {
                for (var t, r, o = this, n = o.ContentCells.items, i = n.length, a = 0, l = 0, s = 0; i > a && (t = n[a++],
                    t._freeCell); )
                    if (s = t.Controls.Count,
                    s > 0)
                        if (l += s,
                        1 === l)
                            r = t.Controls.Item(1);
                        else if (l > 1)
                            break;
                return 1 === l && 9 === r.ControlType && r.HideOnRecordsetEmpty ? r : e
            },
            SetCellsBackColor: function(e) {
                this.ContentCells.forEach(function(t) {
                    t.BackColor = e
                })
            },
            SetCellsForeColor: function(e) {
                this.ContentCells.forEach(function(t) {
                    t.setForeColor(e)
                })
            }
        },
            q(Xe, Ye);
        var Ze = function(e) {
            var t = this;
            Ye.call(t, e),
                t.TitleCells = new fe(t)
        };
        Ze.prototype = {
            afterLoad: function(e) {
                var t = this;
                Ye.prototype.afterLoad.call(t, e),
                    t.TitleCells.loadFromJSON(e.ColumnTitleCell)
            },
            prepare: function() {
                function e(t) {
                    var n = 1;
                    return t.showingItems = [],
                        t.forEach(function(i) {
                            var a, l, s;
                            i.layer = o,
                                i.GroupTitle ? i.Visible && (o++,
                                    a = e(i.SubTitles) + 1,
                                    o--,
                                    l = i.SubTitles.showingItems,
                                l.length > 0 && (t.showingItems.push(i),
                                a > n && (n = a),
                                    i.colspan = 0,
                                    l.forEach(function(e) {
                                        i.colspan += e.colspan
                                    }))) : (s = i.Column,
                                s.Visible && s.pxWidth > 0 && (t.showingItems.push(i),
                                    i.colspan = 1,
                                    s.pxLeft = r.columnsTotalWidth,
                                    s.index = r.showingColumns.length,
                                    r.columnsTotalWidth += s.pxWidth,
                                    r.showingColumns.push(s)))
                        }),
                        n
                }
                var t = this
                    , r = t.owner
                    , o = 0;
                Ye.prototype.prepare.call(t),
                    r.columnsTotalWidth = 0,
                    r.showingColumns = [],
                    t.layerCount = e(t.TitleCells),
                    t.pxHeight /= t.layerCount,
                    t.TitleCells.prepare(),
                    t.defaultStyle = t.report.viewer.selectSectionItem(t)
            },
            unprepare: function() {
                var e = this;
                Ye.prototype.unprepare.call(e),
                    e.TitleCells.unprepare()
            },
            doGenerate: function(e) {
                function t(e) {
                    e.forEach(function(e) {
                        e.layer === o.generatingLayer && e.generate(r),
                        e.GroupTitle && t(e.SubTitles.showingItems)
                    })
                }
                var r, o = this, n = o.report.viewer;
                for (o.pxHeight /= o.layerCount,
                         o.generatingLayer = 0; o.generatingLayer < o.layerCount; )
                    r = new w("tr",e),
                        o.addElementEventClass(r),
                        r.addClass(n.selectSection(o)),
                        t(o.TitleCells.showingItems),
                        o.generatingLayer++
            },
            FindColumnTitlesOfTitleCell: function(t) {
                function r(t, o) {
                    var n, i, a, l = t.items.length;
                    for (n = 0; l > n; n++) {
                        if (i = t.items[n],
                        i === o)
                            return t;
                        if (i.GroupTitle && (a = r(i.SubTitles, o)))
                            return a
                    }
                    return e
                }
                return r(this.TitleCells, t)
            },
            SetCellsBackColor: function(e) {
                function t(r) {
                    r.forEach(function(r) {
                        r.BackColor = e,
                        r.GroupTitle && t(r.SubTitles)
                    })
                }
                t(this.TitleCells)
            },
            SetCellsForeColor: function(e) {
                function t(r) {
                    r.forEach(function(r) {
                        r.setForeColor(e),
                        r.GroupTitle && t(r.SubTitles)
                    })
                }
                t(this.TitleCells)
            }
        },
            q(Ze, Ye);
        var qe = function(e) {
            var t = this;
            Ue.call(t, e),
                t.Height = t.report.cm2Size(1.2)
        };
        qe.prototype = {
            generate: function(e, t) {
                var r = this
                    , o = r.owner;
                o.recordset.MoveTo(t),
                    o.owner.Recordset.MoveTo((r === o.Header ? o.beginNoField : o.endNoField).Value),
                    Ue.prototype.generate.call(r, e)
            },
            doGenerate: function(t) {
                function r(e) {
                    var t = n.owner
                        , r = n === t.Header ? i.ATTR_GROUPH_INDEX : i.ATTR_GROUPF_INDEX;
                    e.addAttribute(i.ATTR_GROUP_RECNO, t.recordset.recordNo),
                        e.addAttribute(r, t.index)
                }
                var o, n = this, a = n.report.viewer, l = n.owner.owner, s = l.showingColumns, c = s.length;
                l.hasMergedColumn && (o = [],
                    s.forEach(function(e) {
                        e.mergedHeader && e.mergedHeader.todoMerge && (n.owner.Footer !== n || n.owner === l.Groups.Item(l.Groups.Count) && e.mergedHeader !== n.owner.Header) && o.push(e)
                    }),
                o.length || (o = e)),
                    n.tableRows && !n.tableCols ? (n.generateTableRows(t, o),
                        t.children.slice(-n.tableRows.length).forEach(function(e) {
                            n.addElementEventClass(e),
                                r(e)
                        })) : (t = new w("tr",t),
                        t.addClass(a.selectSection(n)),
                        n.addElementEventClass(t),
                        r(t),
                        t = new w("td",t),
                    c > 1 && t.addAttribute("colspan", c),
                        n.tableCols ? (t = new w("table",t),
                            t.addClass(a.selectSection(n)),
                            t.addStyle("border-collapse", "collapse"),
                            n.generateTableRows(t)) : (t.addStyle("position", "relative"),
                            n.Controls.generate(t)))
            },
            getTableColEdges: function() {
                var e, t = this.owner.owner.showingColumns;
                return e = t.map(function(e) {
                    return {
                        pos: e.pxLeft,
                        count: 1
                    }
                }),
                    e.push({
                        pos: t[t.length - 1].pxRight,
                        count: 1
                    }),
                    e
            },
            getMergedColumns: function() {
                var e, t, r = this;
                return e = r.owner.index,
                    t = r.owner.owner.showingColumns.filter(function(t) {
                        var o, n, i = t.mergedHeader;
                        return i && ((n = (o = i.owner).index) < e || n === e && r === o.Footer && o.Header.IncludeFooter)
                    }),
                0 === t.length && (t = 0),
                    t
            },
            getParentBackColor: function(e) {
                return this.owner.owner.BackColor
            },
            getParentFont: function() {
                return this.owner.owner.Font
            },
            _runningNo: function() {
                return this.owner.recordset.RecordNo + 1
            }
        },
            q(qe, Ue);
        var Qe = function(e) {
            var t = this;
            qe.call(t, e),
                t.OccupiedColumns = "",
                t.OccupyColumn = !1,
                t.SameAsColumn = !0,
                t.IncludeFooter = !1,
                t.VAlign = 1
        };
        Qe.prototype = {
            afterLoad: function(e) {
                var t = this;
                qe.prototype.afterLoad.call(t, e),
                    P(t, "VAlign", n.OCGroupHeaderVAlign)
            },
            prepare: function() {
                var e, t = this;
                t.OccupiedColumns && (e = t.owner.owner.Columns.decodeItems(t.OccupiedColumns),
                    e = e.filter(function(e) {
                        return e.Visible
                    }),
                e.length > 0 && (t.groupMergedColumns = e,
                    e.forEach(function(e) {
                        e.mergedHeader = t,
                            t.owner.owner.hasMergedColumn = 1
                    }))),
                !(t.groupMergedColumns && t.SameAsColumn) && qe.prototype.prepare.call(t)
            },
            unprepare: function() {},
            generate: function(e, t) {
                var r = this;
                r.groupMergedColumns ? (r.owner.recordset.MoveTo(t),
                    r.curItemBegin = e.children ? e.children.length : 0,
                    r.todoMerge = 1) : qe.prototype.generate.call(r, e, t)
            },
            get Group() {
                return this.owner
            }
        },
            q(Qe, qe);
        var Ke = function(e) {
            qe.call(this, e)
        };
        Ke.prototype = {
            generate: function(e, t) {
                var r, o = this, n = o.owner.Header, i = n.groupMergedColumns;
                qe.prototype.generate.call(o, e, t),
                i && e.children && (r = e.children.length - n.curItemBegin,
                !n.IncludeFooter && o.isToGenerate() && r--,
                r > 1 && i.forEach(function(e) {
                    e.mergingElement && e.mergingElement.addAttribute("rowspan", r + "")
                }))
            },
            get Group() {
                return this.owner
            }
        },
            q(Ke, qe);
        var $e = function(t) {
            var r = this;
            xe.call(r, t),
                r.FieldType = 1,
                r._name = "",
                r._dbFieldName = "",
                r._tableMember = e,
                r.Format = "",
                r.Length = 0,
                r.RTrimBlankChars = !1,
                r.GetDisplayTextScript = ""
        };
        $e.prototype = {
            afterLoad: function(e) {
                var t, r = this;
                t = e[r.getJsonMember("Type")],
                t && (r.FieldType = t),
                    t = e[r.getJsonMember("Name")],
                t && (r.Name = t),
                    t = e[r.getJsonMember("DBFieldName")],
                t && (r.DBFieldName = t),
                    P(r, "FieldType", n.FieldType)
            },
            prepare: function() {
                var e = this
                    , t = e.FieldType
                    , r = e.Format;
                6 === t ? e.formater = new d(r) : 5 === t ? e.formater = new u(r) : 2 !== t && 3 !== t && 4 !== t || (e.formater = new h(r))
            },
            unprepare: function() {
                var e = this;
                delete e.formater,
                    delete e._fts
            },
            isNumeric: function() {
                var e = this.FieldType;
                return 2 === e || 3 === e || 4 === e
            },
            valUeq: function(t) {
                var r = this.Value
                    , o = t.Value;
                return o > r || r > o || (r === e || o === e) && r !== o
            },
            get Value() {
                var t = this
                    , r = t.owner.curRecord;
                return r ? r[t._tableMember] : e
            },
            set Value(t) {
                var r = this
                    , o = r.owner.curRecord;
                if (o) {
                    if (t !== e && null !== t)
                        switch (r.FieldType) {
                            case 2:
                            case 3:
                            case 4:
                                t = +t;
                                break;
                            case 5:
                                t = H(t);
                                break;
                            case 6:
                                t = I(t);
                                break;
                            default:
                                t += ""
                        }
                    else
                        t = e;
                    o[r._tableMember] = t
                }
            },
            get AsBoolean() {
                return H(this.Value)
            },
            set AsBoolean(e) {
                this.Value = e
            },
            get AsCurrency() {
                return +this.Value
            },
            set AsCurrency(e) {
                this.Value = e
            },
            get AsDateTime() {
                return O(this.Value)
            },
            set AsDateTime(e) {
                this.Value = e
            },
            get AsFloat() {
                var e, t = this;
                if (t.IsNull)
                    e = 0;
                else if (6 === t.FieldType) {
                    var r = new g;
                    r.value = t.Value,
                        e = r.AsFloat
                } else
                    e = +t.Value;
                return isNaN(e) ? 0 : e
            },
            set AsFloat(e) {
                var t, r = this;
                6 === r.FieldType && (t = new g,
                    t.AsFloat = e,
                    e = t.value),
                    r.Value = e
            },
            get AsInteger() {
                return Math.floor(this.AsFloat)
            },
            set AsInteger(e) {
                this.AsFloat = Math.floor(+e)
            },
            get AsString() {
                var e = this;
                return e.IsNull ? "" : e.Value + ""
            },
            set AsString(e) {
                this.Value = e
            },
            get DisplayText() {
                var e = this
                    , t = e.FieldType
                    , r = e.Value;
                return e.displayTextAssigned = 0,
                e.doingGetDisplayText || (e.doingGetDisplayText = 1,
                    e.report.fireFieldGetDisplayText(e),
                    e.doingGetDisplayText = 0),
                    e.displayTextAssigned ? e.assignedDisplayText : e.IsNull ? "" : 1 === t || 7 === t ? r + "" : e.formater.format(r)
            },
            set DisplayText(e) {
                var t = this;
                t.assignedDisplayText = e + "",
                    t.displayTextAssigned = 1
            },
            get IsNull() {
                var t = this.Value;
                return t === e || null === t
            },
            get DataSize() {
                return 0
            },
            get DataBuffer() {
                return e
            },
            get Name() {
                return this._name
            },
            set Name(e) {
                var t = this;
                t._tableMember && t._tableMember !== t._name || (t._tableMember = e),
                    t._name = e
            },
            get DBFieldName() {
                return this._name
            },
            set DBFieldName(e) {
                var t = this;
                t._dbFieldName = e,
                e && (t._tableMember = e)
            },
            get RunningDBField() {
                var e = this;
                return e._dbFieldName || e._name
            },
            Clear: function() {
                this.Value = e
            }
        },
            q($e, xe);
        var et = function(e) {
            var t = this;
            xe.call(t, e),
                t.QuerySQL = "",
                t.Fields = new ge(t),
                t.SortAsc = !0,
                t.SortCaseSensitive = !1,
                t.SortFields = "",
                t.XmlTableName = "",
                t.FetchRecordScript = "",
                t.BeforePostRecordScript = "",
                t.ProcessRecordScript = "",
                t._dataTable = [],
                t.recordNo = 0
        };
        et.prototype = {
            children: ["Fields"],
            afterLoad: function(e) {
                this.Fields.loadFromJSON(e.Field)
            },
            attachDataTable: function(e) {
                var t = this;
                t._dataTable = e,
                    t.MoveTo(0)
            },
            appendRefRecord: function(e) {
                this._dataTable.push(e)
            },
            prepareTableData: function() {
                function t(t, r) {
                    if (t.hasOwnProperty(r))
                        return r;
                    r = r.toUpperCase();
                    for (var o in t)
                        if (r === o.toUpperCase())
                            return o;
                    return e
                }
                var r, o, n, i, a, l = this, s = l._dataTable, c = l.Fields.items, u = s.length, d = c.length;
                if (DEBUG)
                    for (; d--; )
                        o = c[d];
                for (; u--; )
                    for (r = s[u],
                             d = c.length; d--; ) {
                        if (o = c[d],
                            a = o._fts,
                            i = o._tableMember,
                        a === e && (i = t(r, i))) {
                            switch (o.FieldType) {
                                case 1:
                                    a = "string" != typeof r[i];
                                    break;
                                case 2:
                                case 3:
                                case 4:
                                    a = "number" != typeof r[i];
                                    break;
                                case 5:
                                    a = "boolean" != typeof r[i];
                                    break;
                                case 6:
                                    a = !Date.prototype.isPrototypeOf(r[i]);
                                    break;
                                default:
                                    a = !1
                            }
                            o._fts = a,
                                o._tableMember = i
                        }
                        if (i && (n = r[i],
                        null !== n && "" !== n || (n = e,
                            r[i] = n),
                        a && n !== e))
                            switch (o.FieldType) {
                                case 1:
                                    r[i] = n + "";
                                    break;
                                case 2:
                                case 3:
                                case 4:
                                    r[i] = +n;
                                    break;
                                case 5:
                                    r[i] = H(n);
                                    break;
                                case 6:
                                    r[i] = k(n)
                            }
                    }
                l.SortFields && l.Resort(l.SortFields, l.SortAsc, l.SortCaseSensitive)
            },
            prepare: function() {
                var e = this
                    , t = e._dataTable.length
                    , r = 0;
                for (e.Fields.prepare(),
                         e.prepareTableData(); t > r; )
                    e.MoveTo(r++),
                        e.beforePost();
                e.MoveTo(0)
            },
            unprepare: function() {
                var e = this;
                e.Fields.unprepare(),
                    e._dataTable = []
            },
            beforePost: function() {
                var e = this
                    , t = e.report
                    , r = e.BeforePostRecordScript;
                r && t.executeEventScript(r, "BeforePostRecordScript", e),
                e.isDetailGridRecordset() && t.fireBeforePostRecord()
            },
            decodeFields: function(e) {
                return this.Fields.decodeItems(e)
            },
            isDetailGridRecordset: function() {
                var e = this;
                return e.owner === e.report.DetailGrid
            },
            isAppendingRecord: function() {
                return 1 === this.editStatus
            },
            keepValue: function() {
                var e = this;
                e.keepedRecord = e.curRecord
            },
            fieldsKeepedValueChanged: function(t) {
                var r = this;
                return t.some(function(t) {
                    var o = t._tableMember
                        , n = r.keepedRecord[o]
                        , i = r.curRecord[o];
                    return i > n || n > i || (n === e || i === e) && n !== i
                })
            },
            sortRecords: function(t, r) {
                for (var o = this, n = o._dataTable, i = n.length, a = 0, l = []; i > a; )
                    l.push(a++);
                return t.length > 0 && (t.forEach(function(e) {
                    e.fieldName = e.field._tableMember
                }),
                    l.sort(function(r, o) {
                        var i;
                        return t.some(function(t) {
                            var a = n[r][t.fieldName]
                                , l = n[o][t.fieldName];
                            return a !== e && l !== e ? 1 === t.field.FieldType ? (t["case"] || (a = a.toUpperCase(),
                                l = l.toUpperCase()),
                                i = a.localeCompare(l)) : i = l > a ? -1 : a > l ? 1 : 0 : i = a === e && l === e ? 0 : a === e ? -1 : 1,
                            t.asc || (i = -i),
                                i
                        }),
                            i
                    })),
                    r ? l : (a = [],
                        l.forEach(function(e) {
                            a.push(n[e])
                        }),
                        o._dataTable = a,
                        void o.MoveTo(0))
            },
            get RecordCount() {
                var e = this;
                return e._dataTable ? e._dataTable.length : 0
            },
            get RecordNo() {
                return this.recordNo
            },
            FieldByName: function(e) {
                return this.Fields.itemByName(e)
            },
            FieldByDBName: function(t) {
                var r, o = this.Fields.items, n = o.length;
                for (t = t.toUpperCase(),
                         r = 0; n > r; r++)
                    if (o[r].RunningDBField.toUpperCase() === t)
                        return o[r];
                return e
            },
            Append: function() {
                var e = this;
                e.editStatus = 1,
                    e.recordNo = e._dataTable.length,
                    e.curRecord = {}
            },
            Edit: function() {
                var t, r, o = this, n = o.Fields.items, i = n.length, a = o.curRecord, l = {};
                if (o.recordNo >= 0 && o.recordNo < o._dataTable.length) {
                    for (o.editStatus = 2; i--; )
                        t = n[i],
                            r = t._tableMember,
                        a[r] !== e && (6 === t.FieldType ? l[r] = I(a[r]) : l[r] = a[r]);
                    o.curRecord = l
                }
            },
            Cancel: function() {
                var e = this;
                e.editStatus && (1 === e.editStatus ? e.Last() : e.curRecord = e._dataTable[e.recordNo],
                    e.editStatus = 0)
            },
            Post: function() {
                var e = this
                    , t = e._dataTable;
                e.editStatus && (e.beforePost(),
                    1 === e.editStatus ? t.push(e.curRecord) : t[e.recordNo] = e.curRecord,
                    e.editStatus = 0)
            },
            First: function() {
                this.MoveTo(0)
            },
            Next: function() {
                var e = this;
                e.MoveTo(e.recordNo + 1)
            },
            Prior: function() {
                var e = this;
                e.MoveTo(e.recordNo - 1)
            },
            Last: function() {
                var e = this;
                e.MoveTo(e._dataTable.length - 1)
            },
            MoveBy: function(e) {
                var t = this;
                t.MoveTo(t.recordNo + e)
            },
            Bof: function() {
                var e = this;
                return e.recordNo < 0 || !e._dataTable.length
            },
            Eof: function() {
                var e = this;
                return e.recordNo >= e._dataTable.length
            },
            MoveTo: function(e) {
                var t = this
                    , r = t._dataTable;
                t.recordNo = e,
                    t.curRecord = r[Math.min(Math.max(0, e), r.length - 1)]
            },
            Empty: function() {
                this.attachDataTable([])
            },
            AddField: function(e, t) {
                var r = this.Fields.Add();
                return r.Name = e,
                    r.FieldType = t,
                    r
            },
            RemoveAllFields: function() {
                this.Fields.RemoveAll()
            },
            Resort: function(e, t, r) {
                function o() {
                    var o = []
                        , n = e.split(";");
                    return n.forEach(function(e) {
                        function n(e) {
                            e = e.trim().toUpperCase(),
                                "ASC" === e ? l.asc = !0 : "DESC" === e ? l.asc = !1 : "CASE" === e ? l["case"] = !0 : "CASEIN" === e && (l["case"] = !1)
                        }
                        var i, l = {};
                        e = e.trim(),
                            i = e.split(" "),
                            i[0] = i[0].trim(),
                            l.field = a.Fields.Item(i[0]),
                        l.field && (l.asc = t,
                            l["case"] = r,
                        i.length > 1 && n(i[1]),
                        i.length > 2 && n(i[2]),
                            o.push(l))
                    }),
                        o
                }
                var n, i, a = this, l = [];
                e && (n = o(),
                    i = n.length,
                i > 0 && (a.report.fireBeforeSort(e, a),
                a.isDetailGridRecordset() && (a.owner.Groups.forEach(function(e) {
                    a.decodeFields(e.ByFields).forEach(function(e) {
                        l.indexOf(e) < 0 && l.push(e)
                    })
                }),
                    n.forEach(function(e) {
                        var t = l.indexOf(e.field);
                        0 > t ? l.push(e) : l[t] = e
                    }),
                    l.forEach(function(e, o) {
                        e.field || (l[o] = {
                            field: e,
                            asc: t,
                            "case": r
                        })
                    }),
                    n = l,
                    i = n.length)),
                    a.sortRecords(n, 0))
            },
            FilterByStep: function(e, t) {
                var r = this
                    , o = r.RecordCount()
                    , n = 0;
                if (r._dataTable && !r.isAppendingRecord() && e > 0) {
                    for (r.FilterBegin(); o > n; )
                        r.MoveTo(n),
                            r.FilterSelect(),
                            n += e;
                    t && n - e + 1 != o && (r.Last(),
                        r.FilterSelect()),
                        r.FilterEnd()
                }
            },
            FilterByCount: function(e) {
                var t, r, o = this, n = o.RecordCount(), i = 0;
                if (o._dataTable && !o.isAppendingRecord() && e > 1 & n > e) {
                    for (t = (n - 1) / (e - 1),
                             o.FilterBegin(); n > i; )
                        r = Math.round(i),
                            o.MoveTo(r),
                            o.FilterSelect(),
                            i += t;
                    o.FilterEnd()
                }
            },
            FilterBegin: function() {
                this._dtf = []
            },
            FilterEnd: function() {
                var e = this
                    , t = e._dataTable;
                e._dtf && (e._dataTable = e._dtf,
                    e._dtf = t)
            },
            FilterSelect: function() {
                var e = this;
                !(!e._dtf || e.isAppendingRecord() || e.Bof() || e.Eof()) && e._dtf.push(e.curRecord)
            },
            FilterReset: function() {
                var e = this;
                e._dtf && (e._dataTable = e._dtf,
                    delete e._dtf)
            }
        },
            q(et, xe);
        var tt = function(e) {
            var t = this;
            xe.call(t, e),
                t.Name = "",
                t.Visible = !0,
                t.FixedWidth = !1,
                t.Width = t.report.cm2Size(3)
        };
        tt.prototype = {
            prepare: function() {
                var e = this;
                e.pxWidth = e.report.size2Pixel(e.Width)
            },
            get pxRight() {
                var e = this;
                return e.pxLeft + e.pxWidth
            }
        },
            q(tt, xe);
        var rt = function(e) {
            var t = this;
            xe.call(t, e),
                t.Header = new Qe(t),
                t.Footer = new Ke(t),
                t.Name = "",
                t.ByFields = "",
                t.SortSummaryBox = "",
                t.SortAsc = !1,
                t.GroupBeginScript = "",
                t.GroupEndScript = ""
        };
        rt.prototype = {
            children: ["Header", "Footer"],
            afterLoad: function(e) {
                var t = this;
                t.Header.loadFromJSON(e.GroupHeader),
                    t.Footer.loadFromJSON(e.GroupFooter)
            },
            prepare: function(e) {
                function t(e) {
                    e.forEach(function(e) {
                        function o(e) {
                            function n(e) {
                                var t, o, n, a = i.length + "", l = "";
                                i.push(e),
                                    e.SummaryFun ? (e.value = 0,
                                    28 === e.SummaryFun && (t = r.owner.Groups.indexOf(r),
                                    t > 0 && (o = new f,
                                        o.SummaryFun = -1,
                                        o.init(),
                                        r.owner.Groups.items[t - 1].summaryItems.push(o))),
                                        e.displayField ? (n = e.displayField.FieldType,
                                            l = e.displayField.Format) : (n = 18 === e.SummaryFun || 19 === e.SummaryFun ? 1 : 3,
                                        e.paramExp && (o = e.paramExp.varItems,
                                        1 === o.length && (o = o[0].varField.object,
                                        o.Format && (l = o.Format))))) : e.oParameter ? (n = e.oParameter.DataType,
                                        l = e.oParameter.Format) : n = 3,
                                    e.valueField = r.recordset.AddField(a, n),
                                    e.valueField.Format = l
                            }
                            var i = r.summaryItems
                                , a = e.ControlType;
                            5 === a ? n(e) : 8 === a ? Ae.prototype.getGroupVars.call(e).forEach(function(e) {
                                n(e)
                            }) : 1 === a ? e.oParameter && n(e) : 13 === a && e.cells.forEach(function(e) {
                                e.forEach(function(e) {
                                    e.FreeCell ? t(e.Controls) : o(e)
                                })
                            })
                        }
                        o(e)
                    })
                }
                var r = this;
                r.index = e,
                    r.Header.prepare(),
                    r.Footer.prepare(),
                    r.oByFields = r.owner.Recordset.decodeFields(r.ByFields),
                r.SortSummaryBox && (r.sortSummaryBoxObj = r.Footer.Controls.itemByName(r.SortSummaryBox),
                r.sortSummaryBoxObj || (r.sortSummaryBoxObj = r.Header.Controls.itemByName(r.SortSummaryBox)),
                r.sortSummaryBoxObj && 5 !== r.sortSummaryBoxObj.ControlType && delete r.sortSummaryBoxObj),
                    r.recordset = new et(r),
                    r.beginNoField = r.recordset.AddField("b", 2),
                    r.endNoField = r.recordset.AddField("e", 2),
                    r.groupItemNo = 0,
                    r.summaryItems = [],
                    t(r.Header.Controls),
                    t(r.Footer.Controls),
                    r.recordset.prepare()
            },
            unprepare: function() {},
            inphaseByDetail: function(e) {
                var t = this
                    , r = t.recordset;
                if (!r.isAppendingRecord() && r.RecordCount) {
                    for (; t.beginNoField.Value > e && !r.Bof(); )
                        r.Prior();
                    for (; t.endNoField.Value < e && !r.Eof(); )
                        r.Next()
                }
            },
            get Parent() {
                return this.owner
            }
        },
            q(rt, xe);
        var ot = function(e) {
            var t = this;
            xe.call(t, e),
                t.FixCols = 0,
                t.BackColor = e.BackColor,
                t.ShowColLine = !0,
                t.ShowRowLine = !0,
                t.Font = new x(e.Font),
                t.Border = new b(15),
                t.ColLinePen = new C,
                t.RowLinePen = new C,
                t.Recordset = new et(t),
                t.ColumnContent = new Xe(t),
                t.ColumnTitle = new Ze(t),
                t.Columns = new he(t),
                t.Groups = new me(t)
        };
        ot.prototype = {
            children: ["Border", "ColLinePen", "RowLinePen", "Recordset", "Groups"],
            afterLoad: function(e) {
                var t = this
                    , r = t.report.viewer.alpha
                    , o = t.report.isWR;
                A(t, "BackColor", r.background),
                    t.Border.loadFromJSON(e.Border, r.border, o),
                    t.ColLinePen.loadFromJSON(e.ColLine, r.stroke, o),
                    t.RowLinePen.loadFromJSON(e.RowLine, r.stroke, o),
                    t.Font.loadFromJSON(e.Font, o),
                    t.Recordset.loadFromJSON(e.Recordset),
                    t.Groups.loadFromJSON(e.Group),
                    t.Columns.loadFromJSON(e.Column),
                    t.ColumnContent.loadFromJSON(e.ColumnContent),
                    t.ColumnTitle.loadFromJSON(e.ColumnTitle),
                    t.IsCrossTab = e[t.getJsonMember("IsCrossTab")],
                t.IsCrossTab && t.CrossTab.loadFromJSON(e.CrossTab)
            },
            assign: function(e) {
                function t(e, o) {
                    o.forEach(function(o) {
                        var n;
                        o.GroupTitle ? (n = e.AddGroup("", ""),
                            n.assign(o),
                            t(n.SubTitles, o.SubTitles)) : (n = r.Columns.addTo(e),
                            n.assign(o.Column),
                            n.TitleCell.assign(o),
                            n.ContentCell.assign(o.Column.ContentCell))
                    })
                }
                var r = this;
                xe.prototype.assign.call(r, e),
                    r.ColumnContent.assign(e.ColumnContent),
                    r.ColumnTitle.assign(e.ColumnTitle),
                    t(r.ColumnTitle.TitleCells, e.ColumnTitle.TitleCells),
                    r.IsCrossTab = e.IsCrossTab,
                r.IsCrossTab && r.CrossTab.assign(e.CrossTab)
            },
            attachData: function() {
                function e(t) {
                    t.forEach(function(t) {
                        t.attachData(),
                        t.GroupTitle && e(t.SubTitles)
                    })
                }
                var t = this;
                t.Recordset.prepare(),
                    t.ColumnContent.ContentCells.forEach(function(e) {
                        e.attachData()
                    }),
                    e(t.ColumnTitle.TitleCells),
                    t.Groups.forEach(function(e) {
                        e.Header.Controls.attachData(),
                            e.Footer.Controls.attachData()
                    })
            },
            buildColumnsOrder: function() {
                function e(t) {
                    t.forEach(function(t) {
                        t.GroupTitle ? e(t.SubTitles) : (t.Column.orderNo = r.length,
                            r.push(t.Column))
                    })
                }
                var t = this
                    , r = [];
                e(t.ColumnTitle.TitleCells),
                    t.Columns.items = r
            },
            beginGroupItem: function(e) {
                var t = this;
                t.rows.push({
                    header: e.Header,
                    recordNo: e.groupItemNo
                }),
                    e.recordset.Append(),
                    e.beginNoField.Value = t.Recordset.recordNo,
                    e.groupRecordCount = 0,
                    e.summaryItems.forEach(function(e) {
                        e.SummaryFun && e.beginSummaryValue()
                    }),
                    t.report.fireGroupBegin(e)
            },
            endGroupItem: function(t) {
                var r, o, n = this, i = n.report, a = t.summaryItems, l = a.length;
                for (n.rows.push({
                    footer: t.Footer,
                    recordNo: t.groupItemNo++
                }),
                         i.fireGroupEnd(t),
                         a.forEach(function(e) {
                             e.SummaryFun && e.endSummaryValue()
                         }); l-- > 0; )
                    r = a[l],
                        o = r.SummaryFun,
                        o ? -1 !== o && r.value !== e && (r.valueField.AsFloat = r.value) : r.MathFun ? r.valueField.Value = r.getAsFloat() : r.valueField.Value = r.oParameter.Value;
                i.DetailGrid.CrossTab && i.DetailGrid.CrossTab.GroupEndProcess(t),
                    t.endNoField.Value = Math.min(n.Recordset.recordNo, n.Recordset.RecordCount - 1),
                    t.recordset.Post()
            },
            prepare: function() {
                function e(r) {
                    r.forEach(function(r) {
                        function o(r) {
                            var n = t.globalSummaryItems
                                , i = r.ControlType;
                            5 === i ? n.push(r) : 8 === i ? Ae.prototype.getGroupVars.call(r).forEach(function(e) {
                                e.SummaryFun && n.push(e)
                            }) : 13 === i && r.cells.forEach(function(t) {
                                t.forEach(function(t) {
                                    t.FreeCell ? e(t.Controls) : o(t)
                                })
                            })
                        }
                        o(r)
                    })
                }
                var t = this
                    , r = t.report;
                t.CrossTab ? (t.buildColumnsOrder(),
                    t.CrossTab.prepare()) : (t.Columns.prepare(),
                    t.ColumnContent.prepare(),
                    t.ColumnTitle.prepare(),
                    t.Groups.forEach(function(e, t) {
                        e.prepare(t)
                    }),
                    t.globalSummaryItems = [],
                    r.ReportHeaders.items.concat(r.ReportFooters.items).forEach(function(t) {
                        e(t.Controls)
                    }))
            },
            unprepare: function() {
                var e = this;
                e.CrossTab && e.CrossTab.unprepare(),
                    e.Recordset.unprepare(),
                    e.ColumnContent.unprepare(),
                    e.ColumnTitle.unprepare(),
                    e.Groups.forEach(function(e, t) {
                        e.unprepare(t)
                    })
            },
            generate: function(e, t) {
                function r() {
                    var e = a.ownerReport
                        , t = a.detailgridResize;
                    return 1 === t || 2 === t && window.innerWidth && n.width < window.innerWidth && (!e || e.owner._singleSR)
                }
                var o, n = this, a = n.report, l = n.Recordset, s = n.Border, c = a.viewer, u = c._detailgrids, d = "title" == t, h = "content" == t, f = new w("table",e), p = new w("colgroup",f);
                f.addClass(i.CSS_DG),
                n.Font.font && f.addClass(c.selectFont2(n.Font.font)),
                d && (s = s.clone(),
                    s.Styles &= -9),
                h && (s = s.clone(),
                    s.Styles &= -3),
                    f.styles.addBorder(s),
                    f.addStyle("border-collapse", "collapse"),
                    f.addStyle("width", r() ? "100%" : M(n.width)),
                n.toFillBack() && f.addBackColorStyle(n.BackColor),
                    n.showingColumns.forEach(function(e) {
                        var t = new w("col",p);
                        t.skipend = 1,
                            t.addStyle("width", V(100 * e.pxWidth / n.columnsTotalWidth))
                    }),
                h || n.ColumnTitle.generate(new w("thead",f)),
                d || (f.addAttribute("id", c._getDetailGridID(u.length)),
                    u.push(n),
                l._relTblIndex >= 0 && f.addAttribute("_grrelTblIndex", l._relTblIndex),
                    o = new w("tbody",f),
                    n.generatingRowNo = 1,
                    n.rows.forEach(function(e, t) {
                        var r;
                        e.header ? (r = e.header,
                            r.generate(o, e.recordNo)) : e.footer ? (r = e.footer,
                            r.generate(o, e.recordNo)) : (r = n.ColumnContent,
                            r.generate(o, e)),
                        r.Visible && r.Height > 0 && ++n.generatingRowNo
                    }))
            },
            buildRows: function() {
                function e(e) {
                    e.summaryItems.forEach(function(e) {
                        e.SummaryFun && e.summaryCurRecord()
                    }),
                        e.groupRecordCount++
                }
                function t(e) {
                    function t() {
                        var t, r = l.length, o = e.owner.rows, n = [];
                        1 >= r || (t = l[0].beginRowNo,
                            l.forEach(function(t) {
                                e.recordset.MoveTo(t.groupItemNo),
                                    t.sortValue = e.sortSummaryBoxObj.valueField.AsFloat
                            }),
                            l.sort(function(t, r) {
                                return e.SortAsc ? t.sortValue - r.sortValue : r.sortValue - t.sortValue
                            }),
                            l.forEach(function(e) {
                                n = n.concat(o.slice(e.beginRowNo, e.endRowNo))
                            }),
                            n.forEach(function(e) {
                                o[t++] = e
                            }))
                    }
                    var r, o, n, i = e.owner.rows, a = i.length, l = [];
                    for (r = 0; a > r; ) {
                        for (n = {},
                                 o = i[r],
                             o.footer && o.footer.owner.index < e.index && (t(),
                                 l = [],
                                 r++); a > r; )
                            if (o = i[r++],
                            o.header && o.header.owner.index === e.index) {
                                n.beginRowNo = r - 1,
                                    n.groupItemNo = o.recordNo;
                                break
                            }
                        for (; a > r; )
                            if (o = i[r++],
                            o.footer && o.footer.owner.index === e.index) {
                                n.endRowNo = r,
                                    l.push(n);
                                break
                            }
                    }
                    t()
                }
                var r, o, n, i = this, a = i.report, l = i.Recordset, s = i.Groups.items, c = s.length, u = 0;
                for (i.rows = [],
                         i.globalSummaryItems.forEach(function(e) {
                             e.beginSummaryValue()
                         }),
                         l.First(),
                         l.keepValue(),
                         s.forEach(function(e) {
                             i.beginGroupItem(e)
                         }); !l.Eof(); ) {
                    for (r = 0; c > r && !l.fieldsKeepedValueChanged(s[r].oByFields); r++)
                        ;
                    if (c > r) {
                        for (l.Prior(),
                                 o = c - 1; o >= r; o--)
                            i.endGroupItem(s[o]);
                        for (l.Next(),
                                 o = r; c > o; o++)
                            i.beginGroupItem(s[o])
                    }
                    a.fireProcessRecord(l),
                        s.forEach(function(t) {
                            e(t)
                        }),
                        i.globalSummaryItems.forEach(function(e) {
                            e.summaryCurRecord()
                        }),
                        i.rows.push(l.recordNo),
                        l.keepValue(),
                        l.Next()
                }
                for (a.fireProcessEnd(),
                         o = c - 1; o >= 0; o--)
                    i.endGroupItem(s[o]);
                i.globalSummaryItems.forEach(function(e) {
                    e.endSummaryValue()
                }),
                    s.forEach(function(e) {
                        e.sortSummaryBoxObj && (t(e),
                            u = 1)
                    }),
                u && (n = [],
                    o = 0,
                    i.sortedTable = [],
                    s.forEach(function(e) {
                        e.sortedTable = [],
                            e.groupItemNo = 0
                    }),
                    i.rows.forEach(function(e) {
                        var t;
                        e.header ? (t = e.header.owner,
                            t.recordset.MoveTo(e.recordNo),
                            t.recordset.Edit(),
                            t.beginNoField.Value = o,
                            e.recordNo = t.groupItemNo) : e.footer ? (t = e.footer.owner,
                            t.endNoField.Value = o,
                            t.recordset.Post(),
                            t.sortedTable.push(t.recordset._dataTable[e.recordNo]),
                            e.recordNo = t.groupItemNo++) : (i.sortedTable.push(l._dataTable[e]),
                            e = o++),
                            n.push(e)
                    }),
                    i.rows = n,
                    l.attachDataTable(i.sortedTable),
                    delete i.sortedTable,
                    s.forEach(function(e) {
                        e.recordset.attachDataTable(e.sortedTable),
                            delete e.sortedTable
                    }))
            },
            syncTRData: function(e) {
                function t(t) {
                    var r = e.parentNode.parentNode;
                    r.hasAttribute("_grrelTblIndex") && a.report.ownerSR.attachRelationTable2(r.getAttribute("_grrelTblIndex")),
                        a.Recordset.MoveTo(t)
                }
                var r, o, n, a = this, l = 1, s = {};
                return (o = e.getAttribute(i.ATTR_CONTENT_RECNO)) ? (t(o),
                    s.content = a.ColumnContent) : (o = e.getAttribute(i.ATTR_GROUP_RECNO)) && (n = e.getAttribute(i.ATTR_GROUPH_INDEX),
                n || (n = e.getAttribute(i.ATTR_GROUPF_INDEX),
                    l = 0),
                    r = s.group = a.Groups.items[n],
                    r.recordset.MoveTo(o),
                    t((l ? r.beginNoField : r.endNoField).Value)),
                    s
            },
            syncElementData: function(e, t) {
                function r(e, t) {
                    var r, o = e.tagName.toUpperCase();
                    if ("TH" === o || "TABLE" === o)
                        return 0;
                    do {
                        if (r && (e = r.parentNode,
                            o = e.tagName.toUpperCase(),
                        "TH" === o))
                            return 0;
                        for (; "TD" !== o; ) {
                            if (e = e.parentNode,
                                !e)
                                return 0;
                            if (o = e.tagName.toUpperCase(),
                            "TH" === o)
                                return 0
                        }
                        if (r = e.parentNode.parentNode.parentNode,
                        !t && r.classList.contains(i.CSS_DG))
                            break
                    } while (t !== r);
                    return e
                }
                var o = this
                    , n = r(e, t);
                n && o.syncTRData(n.parentNode)
            },
            onmousedown: function(e) {
                function t() {
                    var t, r, o, n = e.target;
                    if ("TABLE" === n.tagName.toUpperCase())
                        return 0;
                    for (; !o; ) {
                        if (r && (n = r.parentNode.parentNode.parentNode),
                            t = n.tagName.toUpperCase(),
                        "TH" === t)
                            return 0;
                        for (; "TD" !== t; )
                            if (n = n.parentNode,
                                t = n.tagName.toUpperCase(),
                            "TH" === t)
                                return 0;
                        r = n.parentNode,
                            o = r.hasAttribute(i.ATTR_CONTENT_RECNO) || r.hasAttribute(i.ATTR_GROUP_RECNO)
                    }
                    return n
                }
                function r(t) {
                    var r = o.curSelObj
                        , n = o.report.viewer.options
                        , a = t.parentNode
                        , l = n.selectionCell ? t : a;
                    a.parentNode.parentNode === e.currentTarget && n.selectionHighlight && (r === l ? l.classList.toggle(i.CSS_SH) : (r && r.classList.remove(i.CSS_SH),
                        l.classList.add(i.CSS_SH),
                        o.curSelObj = l)),
                        o.clickedCell = t
                }
                var o = this
                    , n = t();
                n && r(n),
                    o.syncElementData(e.target, e.currentTarget)
            },
            getClickedRow: function() {
                var e, t, r, o = this, n = o.clickedCell, a = {};
                return n && (e = n.parentNode,
                    (r = e.getAttribute(i.ATTR_CONTENT_RECNO)) ? (a.type = "content",
                        a.recordNo = r) : (r = e.getAttribute(i.ATTR_GROUP_RECNO)) && (t = e.hasAttribute(i.ATTR_GROUPH_INDEX),
                        a.type = t ? "groupheader" : "groupfooter",
                        a.groupIndex = +e.getAttribute(t ? i.ATTR_GROUPH_INDEX : i.ATTR_GROUPF_INDEX))),
                    a
            },
            clearColumns: function() {
                var e = this;
                e.ColumnContent.ContentCells.RemoveAll(),
                    e.ColumnTitle.TitleCells.RemoveAll(),
                    e.Columns.RemoveAll()
            },
            get width() {
                var e = this
                    , t = e.Border;
                return e.columnsTotalWidth + t.getLeftWidth() + t.getRightWidth()
            },
            _ColumnMoveTo: function(e, t) {
                var r = this
                    , o = r.ColumnTitle.FindColumnTitlesOfTitleCell(e.TitleCell)
                    , n = t ? t.SubTitles : r.ColumnTitle.TitleCells;
                r.MoveTitleTo(e.TitleCell, o, n, -1)
            },
            MoveTitleTo: function(e, t, r, o) {
                t.items.splice(t.items.indexOf(e), 1),
                    0 > o ? r.items.push(e) : r.items.splice(o, 0, e)
            },
            get IsCrossTab() {
                return !!this.CrossTab
            },
            set IsCrossTab(e) {
                var t = this;
                e ? t.CrossTab || (t.CrossTab = new Tt(t)) : delete t.CrossTab
            },
            AddColumn: function(e, t, r, o) {
                var n = this.Columns.Add();
                return n.Name = e,
                    n.Width = o,
                    n.ContentCell.DataField = r,
                    n.TitleCell.Text = t,
                    n
            },
            ClearColumns: function() {
                this.Columns.RemoveAll()
            },
            FindGroupTitleCell: function(e) {
                function t(r) {
                    var o, n = r.items.length, i = r.itemByName(e);
                    if (!i)
                        for (o = 0; n > o; o++)
                            if (i = r.items[o],
                            i.GroupTitle && (i = t(i.SubTitles, e)))
                                return i;
                    return i
                }
                return t(this.ColumnTitle.TitleCells, e)
            },
            AddGroupTitle: function(e, t) {
                return this.ColumnTitle.TitleCells.AddGroup(e, t)
            },
            ClearGroupTitles: function() {
                var e, t, r = this, o = r.Columns.Count, n = r.ColumnTitle.TitleCells, i = [];
                for (e = 1; o >= e; e++)
                    ColumnMoveToEnd(e);
                for (o = n.Count,
                         e = 0; o > e; e++)
                    t = n.items[e],
                    !t.GroupTitle && i.push(t);
                n.items = i
            },
            ColumnMoveToEnd: function(t) {
                var r = this
                    , o = r.Columns.Item(t);
                o && sel._ColumnMoveTo(o, e)
            },
            ColumnMoveTo: function(t, r, o) {
                var n, i = this, a = i.Columns.Item(t), l = a ? a.TitleCell : i.FindGroupTitleCell(t), s = r ? i.FindGroupTitleCell(r) : e, c = r ? s ? s.SubTitles : e : i.ColumnTitle.TitleCells;
                l && c && (n = i.ColumnTitle.FindColumnTitlesOfTitleCell(l),
                    i.MoveTitleTo(l, n, c, o))
            },
            StartNewGroup: function(e) {
                var t, r = this, o = r.Recordset, n = r.Groups.items, i = n.length, a = r.Groups.Item(e);
                if (a) {
                    for (o.Prior(),
                             t = i - 1; t >= a.index; t--)
                        r.endGroupItem(n[t]);
                    for (o.Next(),
                             t = a.index; i > t; t++)
                        r.beginGroupItem(n[t])
                }
            },
            ColumnByShowOrder: function(t) {
                function r(t, o, n) {
                    var i, a, l = t.Count;
                    for (i = 0; l > i; i++)
                        if (a = t.items[i],
                            a.GroupTitle) {
                            if (a = r(a.SubTitles, o, n))
                                return a
                        } else {
                            if (o == n)
                                return a;
                            o++
                        }
                    return e
                }
                var o = 0;
                return r(this.ColumnTitle.TitleCells, o, t)
            }
        },
            q(ot, xe);
        var nt = function(t) {
            var r = this;
            xe.call(r, t),
                r.DataType = 1,
                r.Name = "",
                r.Format = "",
                r.Value = e
        };
        nt.prototype = {
            afterLoad: function(t) {
                var r = this;
                P(r, "DataType", n.ParameterDataType),
                r.Value !== e && r.setValue(r.Value)
            },
            prepare: function() {
                var e = this
                    , t = e.DataType
                    , r = e.Format;
                2 === t || 3 === t ? e.formater = new h(r) : 6 === t ? e.formater = new d(r) : 5 === t && (e.formater = new u(r))
            },
            unprepare: function() {
                delete this.formater
            },
            setValue: function(t) {
                var r = this;
                if (null !== t && t !== e)
                    switch (r.DataType) {
                        case 2:
                            t = Math.floor(+t);
                            break;
                        case 3:
                            t = +t;
                            break;
                        case 5:
                            t = H(t);
                            break;
                        case 6:
                            t = k(t);
                            break;
                        default:
                            t += ""
                    }
                else
                    t = e;
                r.Value = t
            },
            get AsBoolean() {
                return !!this.Value
            },
            set AsBoolean(e) {
                this.setValue(e)
            },
            get AsDateTime() {
                return I(this.Value)
            },
            set AsDateTime(e) {
                this.setValue(e)
            },
            get AsFloat() {
                var e = this
                    , t = e.Value;
                if (6 === e.DataType) {
                    var r = new g;
                    r.value = t,
                        t = r.AsFloat
                } else
                    t = +t;
                return isNaN(t) ? 0 : t
            },
            set AsFloat(e) {
                var t, r = this;
                6 === r.DataType && (t = new g,
                    t.AsFloat = e,
                    e = t.value),
                    r.setValue(e)
            },
            get AsInteger() {
                return Math.floor(this.AsFloat)
            },
            set AsInteger(e) {
                this.AsFloat = Math.floor(+e)
            },
            get AsString() {
                var t = this.Value;
                return t === e ? "" : t + ""
            },
            set AsString(e) {
                this.setValue(e)
            },
            get DisplayText() {
                var t = this
                    , r = t.Value;
                if (r === e)
                    return "";
                switch (t.DataType) {
                    case 1:
                        return r + "";
                    default:
                        return t.formater.format(r)
                }
            },
            get IsNull() {
                return this.Value === e
            },
            Clear: function() {
                this.Value = e
            }
        },
            q(nt, xe);
        var it = function(t, r) {
            var o = this;
            t = t || {
                alpha: {
                    background: 1
                }
            },
                o.viewer = t,
                o.report = o,
            r && (o.ownerSR = r),
                o.Font = new x(e),
                o.Font.font = new v,
                o.Parameters = new ve(o),
                o.ReportHeaders = new Ce(o),
                o.ReportFooters = new be(o),
                o.XmlTableName = "",
                o.Unit = 1,
                o.CodePage = 65001,
                o.BackColor = 16777215 | t.alpha.background << 24,
                o.QuerySQL = "",
                o.Title = "",
                o.Author = "",
                o.Description = "",
                o.GlobalScript = "",
                o.InitializeScript = "",
                o.ProcessBeginScript = "",
                o.ProcessEndScript = "",
                o.BeforeSortScript = "",
                o.Printer = {
                    DesignPaperWidth: 21,
                    DesignPaperLength: 29.7,
                    DesignPaperLeftMargin: 2.5,
                    DesignPaperRightMargin: 2.5,
                    DesignPaperOrientation: 1
                }
        };
        it.prototype = {
            load: function(e) {
                var t = this;
                "string" == typeof e && (t.isWR = "_WR_" === e.substr(0, 4),
                t.isWR && (e = gr.wr.decodeWR(e)),
                    e = JSON.parse(e)),
                    t.loadFromJSON(e)
            },
            loadFromJSON: function(t) {
                function r(e) {
                    return l[i ? ie(e) : e]
                }
                var o, n = this, i = n.isWR, a = n.Printer, l = t.Printer;
                n.Clear(),
                    D(n, t),
                    A(n, "BackColor", n.viewer.alpha.background),
                    n.Font.loadFromJSON(t.Font, i),
                    n.Parameters.loadFromJSON(t.Parameter),
                    n.ReportHeaders.loadFromJSON(t.ReportHeader),
                    n.ReportFooters.loadFromJSON(t.ReportFooter),
                t.DetailGrid && n.InsertDetailGrid().loadFromJSON(t.DetailGrid),
                l && (o = r("Width"),
                o && (a.DesignPaperWidth = o),
                    o = r("Height"),
                o && (a.DesignPaperLength = o),
                    o = r("LeftMargin"),
                o !== e && (a.DesignPaperLeftMargin = o),
                    o = r("RightMargin"),
                o !== e && (a.DesignPaperRightMargin = o),
                    o = r("Oriention"),
                o && (a.DesignPaperOrientation = 2),
                    n.Printer = a)
            },
            generateHtml: function() {
                function e(t, r) {
                    r && (t.push(r.beginText()),
                        r.children ? r.children.forEach(function(r) {
                            e(t, r)
                        }) : t.push(r.innerText),
                        t.push(r.endText()),
                    DEBUG && t.push("\r\n"))
                }
                var t = this
                    , r = []
                    , o = {
                    htmlElements: [],
                    addChild: function(e) {
                        o.htmlElements.push(e)
                    }
                };
                return t.generate(o),
                    o.htmlElements.forEach(function(t) {
                        e(r, t)
                    }),
                    r.join("")
            },
            generate: function(e) {
                var t, r, o, n, i, a = this, l = a.viewer, s = a.fixedHeaderFooter, c = a.ReportHeaders, u = a.ReportFooters;
                s && (r = new F,
                    r.add("padding-right", "17px"),
                    o = l.addCustomStyle(r),
                    l.fixedDivClass = o,
                    r = new F,
                    r.add("overflow-y", "scroll"),
                    n = l.addCustomStyle(r),
                    l.bodyDivClass = n),
                    a.fireInitialize(),
                !a.ownerSR && a.matchTables(),
                a.DetailGrid && a.fireFetchRecord(),
                    a.fireProcessBegin(),
                !a.ownerSR && a.prepare(),
                    a.singleChart ? a.singleChart.generateContent(e) : (t = a.RunningDetailGrid,
                    a.reportFitHeight && ce(c.items.concat(u.items)),
                    t && t.buildRows(a),
                        s ? (i = new w("div",e),
                            i.addClass(l._getCSSName(o)),
                            c.generate(i),
                            t.generate(i, "title"),
                            i = new w("div",e),
                            i.addClass(l._getCSSName(n)),
                            t.generate(i, "content"),
                        u.Count && (i = new w("div",e),
                            i.addClass(l._getCSSName(o)),
                            u.generate(i))) : (c.generate(e),
                        t && t.generate(e),
                            u.generate(e))),
                !t && a.fireProcessEnd()
            },
            matchTables: function() {
                function e(e, t) {
                    function r(e) {
                        function t(e, t) {
                            var r = this;
                            r.isRecordset = e,
                                r.tableName = t.XmlTableName,
                                r.object = t
                        }
                        var o, n, i;
                        if (e)
                            for (x.push(new t(!1,e)),
                                 e.DetailGrid && x.push(new t(!0,e.DetailGrid.Recordset)),
                                     n = e.FindFirstControl(); n; )
                                i = n.ControlType,
                                    11 === i ? (o = n.Recordset,
                                    o.Fields.Count > 0 && x.push(new t(!0,o))) : 9 === i && r(n.Report),
                                    n = e.FindNextControl()
                    }
                    function o(e, t, r) {
                        function o(e, t) {
                            var r, o = e.length, n = 0;
                            for (r = 0; o > r; r++)
                                t.indexOf(e[r]) >= 0 && n++;
                            return 100 * n / o
                        }
                        var n = t.recordCount
                            , i = t.fieldNames
                            , a = 0;
                        if (e.length && t.fieldNames.length) {
                            if (a = o(e, i),
                                !a)
                                return 0;
                            100 === a && e.length === i.length ? a = 200 : a += o(i, e),
                                a += r && n > 1 || !r && 1 === n ? 5 : -5
                        }
                        return a
                    }
                    function n(e) {
                        return e >= 200
                    }
                    function i(e) {
                        var t, r, o, n = [];
                        for (t = e.Parameters.items.length; t--; )
                            n.push(e.Parameters.items[t].Name.toUpperCase());
                        for (r = e.FindFirstControl(); r; )
                            o = r.ControlType,
                                1 === o || 8 === o || 7 === o || 12 === o ? n.push(r.Name.toUpperCase()) : 13 === o && r.cells.forEach(function(e) {
                                    e.forEach(function(e) {
                                        e.DataName && n.push(e.DataName.toUpperCase())
                                    })
                                }),
                                r = e.FindNextControl();
                        return n
                    }
                    function a(e, t) {
                        for (var r, o, n = 0, i = e.length; i > n; )
                            o = e[n++],
                            (!r || o.weight > r.weight) && o.dataTableInfo.matchWeight < 50 && (r = o);
                        r && l(r.weight, t, r.dataTableInfo)
                    }
                    function l(e, t, r) {
                        r.matchWeight < e && (r.matchWeight = e,
                            r.reportTableInfo = t)
                    }
                    var s, c, u, d, h, f, p, g, m, C, b, v, x = [];
                    for (r(e),
                             u = x.length,
                             d = t.length,
                             s = 0; u > s; ++s) {
                        if (h = x[s],
                            p = h.tableName) {
                            for (c = 0; d > c && t[c].tableName != p; )
                                ++c;
                            if (d > c) {
                                t[c].turnToFullMatched(h);
                                continue
                            }
                            h.tableName = ""
                        }
                        if (h.isRecordset) {
                            for (b = h.object.Fields.items,
                                     v = [],
                                     c = 0,
                                     d = b.length; d > c; )
                                v.push(b[c++].RunningDBField.toUpperCase());
                            for (m = [],
                                     d = t.length,
                                     c = 0; d > c; )
                                if (g = t[c++],
                                    !g.isFullMatched()) {
                                    if (C = o(v, g, !0),
                                        n(C)) {
                                        l(C, h, g),
                                            m = [];
                                        break
                                    }
                                    C > 0 && m.push({
                                        dataTableInfo: g,
                                        weight: C
                                    })
                                }
                            m.length && a(m, h)
                        }
                    }
                    if (t.some(function(e) {
                        return !e.isMatched()
                    }))
                        for (u = x.length,
                                 s = 0; u > s; ++s)
                            if (h = x[s],
                            !h.isRecordset && !h.tableName) {
                                for (v = i(h.object),
                                         m = [],
                                         d = t.length,
                                         c = 0; d > c; )
                                    if (g = t[c++],
                                        !g.isFullMatched()) {
                                        if (C = o(v, g, !1),
                                            n(C)) {
                                            l(C, h, g),
                                                m = [];
                                            break
                                        }
                                        f = g.reportTableInfo,
                                        C > 0 && (!f || f.isRecordset && f.weight + 50 < C || !f.isRecordset && f.weight < C) && m.push({
                                            dataTableInfo: g,
                                            weight: C
                                        })
                                    }
                                m.length && a(m, h)
                            }
                    for (u = t.length,
                             s = 0; u > s; )
                        g = t[s++],
                            h = g.reportTableInfo,
                        h && h.object.attachDataTable(g.table)
                }
                var t = this
                    , r = t.viewer.tables
                    , o = []
                    , n = function(e, t) {
                    var r, o = this, n = o.fieldNames = [], i = Math.min(t.length, 50);
                    for (o.tableName = e,
                             o.table = t,
                             o.matchWeight = 0; i--; ) {
                        r = t[i];
                        for (var a in r)
                            a = a.toUpperCase(),
                            n.indexOf(a) < 0 && n.push(a)
                    }
                };
                n.prototype = {
                    isFullMatched: function() {
                        return this.matchWeight >= 200
                    },
                    turnToFullMatched: function(e) {
                        var t = this;
                        t.matchWeight = 200,
                            t.reportTableInfo = e
                    },
                    isMatched: function() {
                        return this.matchWeight >= 40
                    },
                    get recordCount() {
                        return this.table.length
                    }
                };
                for (var i in r) {
                    var a = r[i];
                    a && a instanceof Array && o.push(new n(i,a))
                }
                1 === o.length && o[0].recordCount > 1 && t.DetailGrid ? t.DetailGrid.Recordset.attachDataTable(o[0].table) : e(t, o)
            },
            attachDataTable: function(e) {
                var t, r, o = this, n = e[0];
                for (var i in n)
                    t = n[i],
                        r = o.ParameterByName(i),
                        r ? r.setValue(t) : (r = o.ControlByName(i),
                        r && (r.hasOwnProperty("Text") || 7 === r.ControlType) && (r.Text = t + ""))
            },
            prepare: function() {
                var e = this
                    , t = e.Printer
                    , r = e.ReportHeaders
                    , o = e.ReportFooters;
                e._scriptFailed = 0,
                    e.designWidth = e.size2Pixel((1 === t.DesignPaperOrientation ? t.DesignPaperWidth : t.DesignPaperLength) - t.DesignPaperLeftMargin - t.DesignPaperRightMargin),
                e.DetailGrid && e.DetailGrid.attachData(),
                    r.attachData(),
                    o.attachData(),
                    e.Parameters.prepare(),
                e.DetailGrid && e.DetailGrid.prepare(),
                    r.prepare(),
                    o.prepare(),
                e._srChildren && e._srChildren.forEach(function(e) {
                    e.Report.prepare()
                }),
                e.ownerSR || !e.viewer.options.singleChartFill || e.DetailGrid || r.Count + o.Count !== 1 || 1 !== (r.items[0] || o.items[0]).Controls.Count || (e.singleChart = (r.items[0] || o.items[0]).Controls.items[0],
                11 !== e.singleChart.ControlType && delete e.singleChart)
            },
            unprepare: function() {
                var e = this;
                e.Parameters.unprepare(),
                e.DetailGrid && (e.DetailGrid.unprepare(),
                    e.RunningDetailGrid = e.DetailGrid),
                    e.ReportHeaders.unprepare(),
                    e.ReportFooters.unprepare(),
                    delete e._srChildren,
                    delete e._has_cb,
                    delete e.singleChart,
                    delete e.canvasControls
            },
            getUsingFont: function() {
                return this.Font.font
            },
            getRunningColumn: function(e) {
                var t, r, o = this.RunningDetailGrid;
                return e && o && (t = o.Columns,
                    r = t.itemByName(e),
                r || ("(GridLeft)" === e ? r = t.Item(1) : "(GridRight)" === e && (r = t.Item(t.Count)))),
                    r
            },
            getReportWidth: function() {
                var e = this
                    , t = e.designWidth
                    , r = this.RunningDetailGrid
                    , o = r ? r.width : 0;
                return 2 * o > t ? o : t
            },
            get reportFitHeight() {
                var e = this;
                return !e.RunningDetailGrid && e.viewer.options.reportFitHeight
            },
            get reportFitWidth() {
                var e = this.viewer.options;
                return e.reportFitWidth || e.fixedHeaderFooter
            },
            get detailgridResize() {
                var e = this.viewer.options;
                return e.fixedHeaderFooter ? gr.enum_.detailgridResize.fitWidth : e.detailgridResize
            },
            get fixedHeaderFooter() {
                var e = this;
                return e.DetailGrid && e.viewer.options.fixedHeaderFooter
            },
            get toFillHolder() {
                var e = this;
                return e.reportFitHeight || e.singleChart
            },
            get isDynamicSize() {
                var e = this;
                return e.toFillHolder || e.reportFitWidth || e.detailgridResize
            },
            pixel2Size: function(e) {
                return e /= 96,
                1 === this.Unit && (e *= 2.54),
                    e
            },
            size2Pixel: function(e) {
                return 1 === this.Unit && (e /= 2.54),
                96 * e
            },
            cm2Size: function(e) {
                return 1 === this.Unit ? e : e / 2.54
            },
            registerCanvas: function(e, t) {
                var r = this
                    , o = new w("canvas",t)
                    , n = se();
                o.addAttribute("id", n),
                    r.isDynamicSize || !e.pxRect ? (o.addStyle("width", "100%"),
                        o.addStyle("height", "100%"),
                        o.addStyle("display", "block")) : (o.addAttribute("width", Math.round(e.pxRect.Width()) + ""),
                        o.addAttribute("height", Math.round(e.pxRect.Height()) + "")),
                    e.canvasID = n,
                r.canvasControls || (r.canvasControls = []),
                    r.canvasControls.push(e)
            },
            prepareCanvas: function() {
                for (var e, t = this, r = t.canvasControls, o = r.length; o-- > 0; )
                    e = r[o],
                        e.canvas = document.getElementById(e.canvasID),
                    e.prepareCanvas && e.prepareCanvas()
            },
            resizeCanvas: function() {
                for (var e = this, t = e.canvasControls, r = t.length; r-- > 0; )
                    t[r].resizeCanvas()
            },
            renderCanvas: function() {
                for (var e = this, t = e.canvasControls, r = t.length; r-- > 0; )
                    t[r].drawCanvas(1)
            },
            startCanvas: function() {
                var e = this;
                e.canvasControls && (e.prepareCanvas(),
                    e.isDynamicSize ? (oe(window, "resize", function(t) {
                        e.resizeCanvas()
                    }),
                        e.resizeCanvas()) : e.renderCanvas()),
                e._srChildren && e._srChildren.forEach(function(e) {
                    e.Report.startCanvas()
                })
            },
            executeEventScript: function(e, t, r) {
                gr.script.execEvent(e, t, r, this)
            },
            fireInitialize: function() {
                var e = this;
                e.InitializeScript && e.executeEventScript(e.InitializeScript, "InitializeScript", e),
                e.OnInitialize && e.OnInitialize()
            },
            fireFetchRecord: function() {
                var e = this;
                e.FetchRecordScript && e.executeEventScript(e.FetchRecordScript, "FetchRecordScript", e.DetailGrid.Recordset),
                e.OnFetchRecord && e.OnFetchRecord()
            },
            fireBeforePostRecord: function() {
                var e = this;
                e.OnBeforePostRecord && e.OnBeforePostRecord()
            },
            fireBeforeSort: function(e, t) {
                var r = this;
                r.BeforeSortScript && r.executeEventScript(r.BeforeSortScript, "BeforeSortScript", t),
                r.OnBeforeSort && t.isDetailGridRecordset() && r.OnBeforeSort()
            },
            fireProcessBegin: function() {
                var e = this;
                e.ProcessBeginScript && e.executeEventScript(e.ProcessBeginScript, "ProcessBeginScript", e),
                e.OnProcessBegin && e.OnProcessBegin()
            },
            fireProcessEnd: function() {
                var e = this;
                e.ProcessEndScript && e.executeEventScript(e.ProcessEndScript, "ProcessEndScript", e),
                e.OnProcessEnd && e.OnProcessEnd()
            },
            fireProcessRecord: function(e) {
                var t = this;
                e.ProcessRecordScript && t.executeEventScript(e.ProcessRecordScript, "ProcessRecordScript", e),
                t.OnProcessRecord && t.OnProcessRecord()
            },
            fireGroupBegin: function(e) {
                var t = this;
                e.GroupBeginScript && t.executeEventScript(e.GroupBeginScript, "GroupBeginScript", e),
                t.OnGroupBegin && t.OnGroupBegin(e)
            },
            fireGroupEnd: function(e) {
                var t = this;
                e.GroupEndScript && t.executeEventScript(e.GroupEndScript, "GroupEndScript", e),
                t.OnGroupEnd && t.OnGroupEnd(e)
            },
            fireSectionFormat: function(e) {
                var t = this;
                e.FormatScript && t.executeEventScript(e.FormatScript, "FormatScript", e),
                t.OnSectionFormat && t.OnSectionFormat(e)
            },
            fireFieldGetDisplayText: function(e) {
                var t = this;
                e.GetDisplayTextScript && t.executeEventScript(e.GetDisplayTextScript, "FieldGetDisplayTextScript", e),
                t.OnFieldGetDisplayText && t.DetailGrid && t.RunningDetailGrid.Recordset === e.owner && t.OnFieldGetDisplayText(e)
            },
            fireTextBoxGetDisplayText: function(e) {
                var t = this;
                e.GetDisplayTextScript && t.executeEventScript(e.GetDisplayTextScript, "TextBoxGetDisplayTextScript", e),
                t.OnTextBoxGetDisplayText && t.OnTextBoxGetDisplayText(e)
            },
            fireControlCustomDraw: function(e) {
                var t = this
                    , r = t.Graphics
                    , o = e.Font
                    , n = o ? o.UsingFont() : 0
                    , i = new y(e.canvas.getContext("2d"));
                !r && (r = t.Graphics = new S(t)),
                    r.attach(i),
                n && i.selectFont(n),
                e.CustomDrawScript && t.executeEventScript(e.CustomDrawScript, "CustomDrawScript", e),
                t.OnControlCustomDraw && t.OnControlCustomDraw(e, r),
                n && i.restoreFont()
            },
            fireChartRequestData: function(e) {
                var t = this;
                t.OnChartRequestData && t.OnChartRequestData(e)
            },
            onCheckBoxClick: function(e) {
                var t, r, o, n, a, l, s, c, u, d, h = this, f = h.DetailGrid, p = f.Recordset, g = e.target, m = g.id, C = g.checked, b = C ? -1 : -2, v = g.parentNode.parentNode;
                if (f && (t = f.syncTRData(v),
                    a = g.getAttribute(i.ATTR_FIELD),
                    a ? (p.Edit(),
                        p.FieldByName(a).AsInteger = b,
                        p.Post()) : (c = h.report.ControlByName(m),
                    c && (c.ImageIndex = b)),
                    r = t.group))
                    for (o = r.beginNoField.AsInteger,
                             n = r.endNoField.AsInteger,
                             s = document.querySelectorAll("." + i.CSS_CB),
                             l = s.length; l-- > 0; )
                        c = s[l],
                            m = c.id,
                            u = "CBC" === m.substr(0, 3),
                        u && (d = +m.substr(3),
                        d >= o && n >= d && (c.checked = C,
                            a = c.getAttribute(i.ATTR_FIELD),
                        a && (p.MoveTo(d),
                            p.Edit(),
                            p.FieldByName(a).AsInteger = b,
                            p.Post())))
            },
            get root() {
                for (var e = this; e.ownerSR; )
                    e = e.ownerSR.report;
                return e
            },
            get Version() {
                return i.VERSION
            },
            get FloatControls() {
                var e = this;
                return e._floatControls || (e._floatControls = new de(e)),
                    e._floatControls
            },
            get ImageList() {
                return {}
            },
            get ParentReport() {
                var t = this
                    , r = t.ownerSR;
                return r ? r.report : e
            },
            get Utility() {
                return gr.utility
            },
            get Running() {
                return !!this.viewer.running
            },
            get DisplayMode() {
                return 1
            },
            get IsBlank() {
                var e = this;
                return e.DetailGrid && e.PageHeader && e.PageFooter && !e.ReportHeaders.Count && !e.ReportFooters.Count
            },
            ControlByName: function(t) {
                function r(o) {
                    function n(o) {
                        function n(o) {
                            return o.FreeCell ? r(o.Controls) : o.DataName.toUpperCase() === t ? o : e
                        }
                        for (var i, a, l, s, c = o.length; c--; )
                            if (l = o[c])
                                for (i = l.length; i--; )
                                    if (s = l[i],
                                        a = s ? n(s) : 0)
                                        return a;
                        return e
                    }
                    for (var i, a = o.items, l = a.length; l--; ) {
                        if (i = a[l],
                        i.Name.toUpperCase() === t)
                            return i;
                        if (13 === i.ControlType && (i = n(i.cells)))
                            return i
                    }
                    return e
                }
                function o(t) {
                    return t.FreeCell ? r(t.Controls) : e
                }
                function n(t) {
                    for (var r, i, a = t.items.length; a--; )
                        if (r = t.items[a],
                            i = o(r),
                        r.GroupTitle && !i && (i = n(r.SubTitles)),
                            i)
                            return i;
                    return e
                }
                function i(e) {
                    for (var t = e.length; t--; )
                        if (c = r(e[t].Controls))
                            return c
                }
                var a, l, s, c, u = this;
                if (t = t.toUpperCase(),
                    c = i(u.ReportHeaders.items) || i(u.ReportFooters.items))
                    return c;
                if (u.DetailGrid) {
                    for (a = u.DetailGrid.Groups.items,
                             l = a.length; l--; )
                        if (s = a[l],
                            c = r(s.Header.Controls) || r(s.Footer.Controls))
                            return c;
                    for (a = u.DetailGrid.ColumnContent.ContentCells.items,
                             l = a.length; l--; )
                        if (c = o(a[l]))
                            return c;
                    return n(u.DetailGrid.ColumnTitle.TitleCells)
                }
                return e
            },
            ColumnByName: function(t) {
                var r = this;
                return r.DetailGrid ? r.DetailGrid.Columns.itemByName(t) : e
            },
            FieldByName: function(e) {
                return this.RunningFieldByName(e)
            },
            FieldByDBName: function(t) {
                var r = this.RunningDetailGrid;
                return r ? r.Recordset.FieldByDBName(t) : e
            },
            ParameterByName: function(e) {
                return this.Parameters.itemByName(e)
            },
            FindFirstControl: function() {
                function e(e) {
                    e.FreeCell && (l = l.concat(e.Controls.items))
                }
                function t(r) {
                    r.forEach(function(r) {
                        e(r),
                        r.GroupTitle && t(r.SubTitles)
                    })
                }
                var r, o, n, i = this, a = i.DetailGrid, l = [];
                for (o = i.ReportHeaders.Count,
                         r = 0; o > r; r++)
                    l = l.concat(i.ReportHeaders.items[r].Controls.items);
                for (o = i.ReportFooters.Count,
                         r = 0; o > r; r++)
                    l = l.concat(i.ReportFooters.items[r].Controls.items);
                if (a) {
                    for (o = a.Groups.Count,
                             r = 0; o > r; r++)
                        n = a.Groups.items[r],
                            l = l.concat(n.Header.Controls.items),
                            l = l.concat(n.Footer.Controls.items);
                    a.ColumnContent.ContentCells.forEach(function(t) {
                        e(t)
                    }),
                        t(a.ColumnTitle.TitleCells)
                }
                return l.length > 0 && (i.findControls = l,
                    i.findControlIndex = -1),
                    i.FindNextControl()
            },
            FindNextControl: function() {
                var t = this;
                return t.findControls && (++t.findControlIndex,
                t.findControlIndex >= t.findControls.length && (delete t.findControls,
                    delete t.findControlIndex)),
                    t.findControls ? t.findControls[t.findControlIndex] : e
            },
            RunningFieldByName: function(t) {
                var r = this.RunningDetailGrid;
                return r ? r.Recordset.FieldByName(t) : e
            },
            PixelsToUnit: function(e) {
                return this.pixel2Size(e)
            },
            UnitToPixels: function(e) {
                return this.size2Pixel(e)
            },
            SystemVarValue: function(e) {
                return this.SystemVarValue2(e, 0)
            },
            SystemVarValue2: function(e, t) {
                var r, o, n, i = this, a = i.RunningDetailGrid;
                if (1 === e)
                    r = new Date;
                else if (a)
                    switch (o = a.Recordset,
                        e) {
                        case 4:
                            r = o.RecordNo + 1;
                            break;
                        case 19:
                            r = o.RecordCount;
                            break;
                        case 8:
                            r = a.generatingRowNo;
                            break;
                        default:
                            n = a.Groups.items[t - 1],
                            n && (21 === e ? r = n.recordset.RecordCount : (n.inphaseByDetail(o.RecordNo),
                                r = 20 === e ? n.recordset.isAppendingRecord() ? n.recordset.RecordCount : n.recordset.RecordNo : 22 === e ? o.RecordNo - n.beginNoField.Value : n.endNoField.Value - n.beginNoField.Value,
                                r++))
                    }
                return r
            },
            AddParameter: function(e, t) {
                var r = this.Parameters.Add();
                return r.Name = e,
                    r.DataType = t,
                    r
            },
            InsertDetailGrid: function() {
                var e = this;
                return e.DetailGrid || (e.DetailGrid = new ot(e),
                    e.RunningDetailGrid = e.DetailGrid),
                    e.DetailGrid
            },
            InsertPageHeader: function() {
                return new ze(this)
            },
            InsertPageFooter: function() {
                return new ze(this)
            },
            Clear: function() {
                var e = this;
                it.call(e, e.viewer, e.ownerSR),
                    e.DeleteDetailGrid()
            },
            DeleteDetailGrid: function() {
                var t = this;
                t.DetailGrid = e,
                    t.RunningDetailGrid = e
            },
            DeletePageHeader: function() {},
            DeletePageFooter: function() {}
        };
        var at = ""
            , lt = "_hta"
            , st = "_htaa"
            , ct = "_htc"
            , ut = "_htca"
            , dt = "_htn"
            , ht = "_htx"
            , ft = "_hp"
            , pt = "_vp"
            , gt = "_htp"
            , mt = "_vtp"
            , Ct = function() {
            this.Flag = 0
        };
        Ct.prototype = {
            get CalcHTotal() {
                return 1 & this.Flag
            },
            set CalcHTotal(e) {
                var t = this;
                e ? t.Flag |= 1 : t.Flag &= -2
            },
            get CalcHAverage() {
                return 2 & this.Flag
            },
            set CalcHAverage(e) {
                var t = this;
                e ? t.Flag |= 2 : t.Flag &= -3
            },
            get CalcHAverageA() {
                return 4 & this.Flag
            },
            set CalcHAverageA(e) {
                var t = this;
                e ? t.Flag |= 4 : t.Flag &= -5
            },
            get CalcHCount() {
                return 8 & this.Flag
            },
            set CalcHCount(e) {
                var t = this;
                e ? t.Flag |= 8 : t.Flag &= -9
            },
            get CalcHCountA() {
                return 16 & this.Flag
            },
            set CalcHCountA(e) {
                var t = this;
                e ? t.Flag |= 16 : t.Flag &= -17
            },
            get CalcHMin() {
                return 32 & this.Flag
            },
            set CalcHMin(e) {
                var t = this;
                e ? t.Flag |= 32 : t.Flag &= -33
            },
            get CalcHMax() {
                return 64 & self.Flag
            },
            set CalcHMax(e) {
                var t = this;
                e ? t.Flag |= 64 : t.Flag &= -65
            },
            onBySummaryFun: function(e) {
                var t = this;
                t.CalcHTotal = !0,
                    2 === e ? t.CalcHAverage = !0 : 15 === e ? t.CalcHAverageA = !0 : 3 === e ? t.CalcHCount = !0 : 13 === e ? t.CalcHCountA = !0 : 4 === e ? t.CalcHMin = !0 : 5 === e && (t.CalcHMax = !0)
            }
        };
        var bt = function(t) {
            var r = this;
            r.originObject = e,
                r.HTotalFlag = new Ct,
                r.HSubtotalFlag = new Ct,
                r.MiscFlag = 0,
                r.DatafieldIndex = -1,
                r.TotalValue = 0,
                r.EntireTotalValue = 0,
            t && K(r, t, 0)
        };
        bt.prototype = {
            CopyAddField: function(e, t) {
                var r = this
                    , o = e.Fields.Add();
                return r.originObject.FieldType ? (o.assign(r.originObject),
                r.originObject.isNumeric() && (o.FieldType = 3)) : r.NumericField && (o.FieldType = 3,
                    o.Format = r.originObject.textBuilder.items[0].format),
                    o.Name = t,
                    o.DBFieldName = "",
                    o._tableMember = t,
                    o
            },
            CombineCalcFlag: function(e) {
                var t = this;
                t.HTotalFlag.Flag |= e.HTotalFlag.Flag,
                    t.HSubtotalFlag.Flag |= e.HSubtotalFlag.Flag,
                    t.MiscFlag |= e.MiscFlag
            },
            SetFlagBySummaryFun: function(e, t) {
                var r = this;
                3 === t ? r.HSubtotalFlag.onBySummaryFun(e) : r.HTotalFlag.onBySummaryFun(e)
            },
            get CalcHPercent() {
                return 1 & this.MiscFlag
            },
            set CalcHPercent(e) {
                var t = this;
                e ? t.MiscFlag |= 1 : t.MiscFlag &= -2
            },
            get CalcVPercent() {
                return 2 & this.MiscFlag
            },
            set CalcVPercent(e) {
                var t = this;
                e ? t.MiscFlag |= 2 : t.MiscFlag &= -3
            },
            get CalcTotalHPercent() {
                return 4 & this.MiscFlag
            },
            set CalcTotalHPercent(e) {
                var t = this;
                e ? t.MiscFlag |= 4 : t.MiscFlag &= -5
            },
            get CalcTotalVPercent() {
                return 8 & this.MiscFlag
            },
            set CalcTotalVPercent(e) {
                var t = this;
                e ? t.MiscFlag |= 8 : t.MiscFlag &= -9
            },
            get CalcHTotalExclude() {
                return 16 & this.MiscFlag
            },
            set CalcHTotalExclude(e) {
                var t = this;
                e ? t.MiscFlag |= 16 : t.MiscFlag &= -17
            },
            get NumericField() {
                return 32 & this.MiscFlag
            },
            set NumericField(e) {
                var t = this;
                e ? t.MiscFlag |= 32 : t.MiscFlag &= -33
            }
        };
        var vt = function(e, t) {
            var r = this;
            r.Group = e,
                r.DataFieldInfo = t,
                r.DataSumBoxes = []
        }
            , xt = function(t) {
            var r = this;
            r.PriorCrossTitleCell = e,
                r.OriginTextBox = t,
                r.PriorTitleText = ""
        }
            , Tt = function(e) {
            var t = this;
            xe.call(t, e),
                t.HCrossFields = "",
                t.VCrossFields = "",
                t.ListCols = 1,
                t.TotalCols = 0,
                t.SubtotalCols = 0,
                t.HResort = !0,
                t.HSortAsc = !0,
                t.VResort = !0,
                t.VSortAsc = !0,
                t.HTotalAtFirst = !1,
                t.HPercentColumns = "",
                t.VPercentColumns = "",
                t.TotalExcludeColumns = "",
                t.TotalHPercentColumns = "",
                t.TotalVPercentColumns = "",
                t.DisabledSumFields = "",
                t.PercentFormat = "0.##%",
                t.HCrossPeriodType = 0,
                t.BeginDateParameter = "",
                t.EndDateParameter = "",
                t.GroupAutoSum = !0
        };
        Tt.prototype = {
            loadFromJSON: function(e) {
                var t = this;
                e && (xe.prototype.loadFromJSON.call(t, e),
                    P(t, "HCrossPeriodType", n.PeriodType))
            },
            prepare: function() {
                function r() {
                    var e = d.ParameterByName(u.BeginDateParameter)
                        , t = d.ParameterByName(u.EndDateParameter)
                        , r = {};
                    return e && (r.begin = e.AsDateTime),
                        t ? (r.end = t.AsDateTime,
                        e || (r.begin = O(r.end))) : r.end = O(r.begin),
                    r.end < r.begin && (e = r.begin,
                        r.begin = r.end,
                        r.end = e),
                        r
                }
                function o() {
                    function o() {
                        function e(e) {
                            var t = e.orderNo;
                            return ie > t ? 1 : ae > t ? 2 : le > t ? 3 : 4
                        }
                        function t(t, r) {
                            function o(e) {
                                ve.indexOf(e.originObject) < 0 && (D.some(function(t) {
                                    return t.originObject !== e.originObject ? !1 : (t.CombineCalcFlag(e),
                                        !0)
                                }) || (e.NumericField = e.originObject.isNumeric(),
                                    D.push(e)))
                            }
                            function n(e, t) {
                                e ? e.varItems.forEach(function(e) {
                                    var r = e.varField;
                                    1 === r.type && (t.originObject = r.object,
                                        o(t))
                                }) : (t.originObject = D[0].originObject,
                                    o(t))
                            }
                            var i, a = new bt, l = e(r), s = 1 !== l, c = t.ControlType;
                            4 === l ? (a.HTotalFlag.CalcHTotal = 1,
                            be.indexOf(r) >= 0 && (a.CalcHTotalExclude = 1),
                            me.indexOf(r) >= 0 && (a.CalcTotalHPercent = 1),
                            Ce.indexOf(r) >= 0 && (a.CalcTotalVPercent = 1)) : 3 === l ? a.HSubtotalFlag.CalcHTotal = 1 : 2 === l && (pe.indexOf(r) >= 0 && (a.CalcHPercent = 1),
                            ge.indexOf(r) >= 0 && (a.CalcVPercent = 1),
                            a.CalcHPercent && (a.HTotalFlag.CalcHTotal = 1)),
                                4 === c || 7 === c ? s && (a.originObject = t.oDataField,
                                a.originObject && o(a)) : 5 === c ? (a.SetFlagBySummaryFun(t.SummaryFun, l),
                                    n(t.paramExp, a)) : 12 === c ? s && (a.originObject = t,
                                    D.push(a)) : 8 === c && (i = !1,
                                    t.textBuilder.forEach(function(e) {
                                        e.varItems && e.varItems.forEach(function(e) {
                                            var t, r = e.varField;
                                            4 === r.type ? (t = new bt(a),
                                                t.SetFlagBySummaryFun(r.SummaryFun, l),
                                                n(r.paramExp, t),
                                                i = !0) : 1 === r.type && s && (t = new bt(a),
                                                t.originObject = r.object,
                                                o(t))
                                        }),
                                        !i && s && (a.originObject = t,
                                            a.NumericField = t.isPureNumericExpression(),
                                            D.push(a))
                                    }))
                        }
                        function r(e) {
                            e.forEach(function(e) {
                                e.GroupTitle ? r(e.SubTitles.items) : e.Column.ContentCell.getControls().forEach(function(r) {
                                    t(r, e.Column)
                                })
                            })
                        }
                        function o(r) {
                            r.Controls.forEach(function(r) {
                                var o = x.itemByName(r.AlignColumn) || x.itemByName(r.AlignColumnEx);
                                o && 1 !== e(o) && t(r, o)
                            })
                        }
                        r(y),
                            T.forEach(function(e) {
                                o(e.Footer),
                                    o(e.Header)
                            }),
                            c = 0,
                            D.forEach(function(e) {
                                e.DatafieldIndex = c,
                                    ++c,
                                e.CalcHPercent && ++c,
                                e.CalcVPercent && ++c
                            })
                    }
                    function i() {
                        D.forEach(function(e) {
                            e.originObject.FieldType && p.Fields.Remove(e.originObject.Name)
                        })
                    }
                    function M(e) {
                        var t = p.Fields.Add();
                        return t.Name = e,
                            t.FieldType = 3,
                            t
                    }
                    function V() {
                        function e(e) {
                            e.Controls.items = e.Controls.items.filter(function(e) {
                                var t = C.itemByName(e.AlignColumn) || C.itemByName(e.AlignColumnEx);
                                return !t || t.orderNo < u.ListCols
                            })
                        }
                        f.buildColumnsOrder(),
                            s.forEach(function(t) {
                                e(t.Footer),
                                    e(t.Header)
                            })
                    }
                    function O(r, o, i, a, l, c, v) {
                        function x(e, t) {
                            var r, o;
                            e.FreeCell ? (t.setFreeCell(!0),
                                ee(t, e),
                                e.Controls.forEach(function(e) {
                                    function n() {
                                        return 2 === u.HCrossPeriodType || 3 === u.HCrossPeriodType || 4 === u.HCrossPeriodType || 6 === u.HCrossPeriodType || 7 === u.HCrossPeriodType
                                    }
                                    function i() {
                                        function e(e) {
                                            var t = new g;
                                            return t.value = e,
                                                Math.floor(t.AsFloat)
                                        }
                                        var t, r = u.curPeriod.begin.getFullYear(), o = u.curPeriod.begin.getMonth() + 1, n = u.curPeriod.begin.getDate();
                                        switch (u.HCrossPeriodType) {
                                            case 2:
                                                t = "第" + Math.floor((e(u.curPeriod.begin) - e(re.begin)) / 7 + 1) + "周";
                                                break;
                                            case 3:
                                                t = 11 > n ? "上" : 21 > n ? "中" : "下",
                                                    t = o + "月" + t + "旬";
                                                break;
                                            case 4:
                                                t = 16 > n ? "上" : "下",
                                                    t = o + "月" + t + "半月";
                                                break;
                                            case 6:
                                                t = E(r, 2) + "-" + (o + 2) / 3 + "季度";
                                                break;
                                            case 7:
                                                t = 7 > o ? "上" : "下",
                                                    t = E(r, "00") + "-" + t + "半年"
                                        }
                                        return t
                                    }
                                    var a, l;
                                    r = e.ControlType,
                                        e.TextFormat ? (o = t.Controls.Add(1),
                                            o.assign(e),
                                            a = n() && 4 === r && e.DataField === u.HCrossFields ? i() : e.getDisplayText(),
                                            o.Text = a) : (o = t.Controls.Add(r),
                                            o.assign(e),
                                            7 === r ? e.DataField && (o.DataField = "",
                                                l = d.FieldByName(e.DataField),
                                            l && (7 === l.FieldType ? o.Picture = l.Value : (a = l.DisplayText,
                                                o.ImageIndex = parseInt(a),
                                            isNaN(o.ImageIndex) && (o.ImageIndex = 0,
                                                o.ImageFile = a)))) : 12 === r && (o.Text = e.get_DisplayText()))
                                })) : (t.assign(e),
                                t.Text = e.getDisplayText())
                        }
                        function y() {
                            var t, n, s, u = 0;
                            2 === i ? (t = xe[v - se],
                                n = t ? t[l] : 0,
                                u = n && n.PriorCrossTitleCell && n.PriorTitleText === n.OriginTextBox.getDisplayText() && !c) : 3 === i ? (u = ce == ue,
                                n = xe[xe.length - 1],
                                n = l < n.length ? n[l] : e,
                                u ? u = n && !!n.PriorCrossTitleCell : 0 == l && (s = r.Controls,
                                    u = s && 1 === s.Count && s.Item(1).getDisplayText && s.Item(1).getDisplayText() === n.PriorTitleText)) : 4 === i && (n = xe[xe.length - 1][l],
                                u = de == fe,
                            u && (u = !!n.PriorCrossTitleCell)),
                                u ? o = n.PriorCrossTitleCell : (o = (o ? o.SubTitles : m).AddGroup("", ""),
                                    x(r, o),
                                    o.Name = P,
                                n && (n.PriorCrossTitleCell = o,
                                    n.PriorTitleText = n.OriginTextBox.getDisplayText())),
                                r.SubTitles.forEach(function(e) {
                                    O(e, o, i, a, l + 1, !u, v)
                                })
                        }
                        function S() {
                            function e(e) {
                                var t;
                                return D.some(function(r) {
                                    return r.originObject.Name !== e ? !1 : (t = r,
                                        !0)
                                }),
                                    t
                            }
                            function a(e, t) {
                                var r;
                                return u.HPercentSums.some(function(o) {
                                    return o.Group !== e || o.DataFieldInfo !== t ? !1 : (r = o,
                                        !0)
                                }),
                                    r
                            }
                            function l(e, r) {
                                var o, n = t(e);
                                return 4 === i || 1 === i ? n ? (r += at + n,
                                    o = r) : V.CalcHPercent ? (o = r + at,
                                    r += gt) : V.CalcVPercent ? (r += mt,
                                    o = r) : (r += at,
                                    o = r) : 3 === i ? (r += F + n,
                                    o = r) : (r += F,
                                    o = r,
                                    V.CalcHPercent ? r += ft : V.CalcVPercent && (r += pt,
                                        o = r)),
                                    o = _(o),
                                    {
                                        DataField: r,
                                        SummaryDataField: o
                                    }
                            }
                            function c(e, t) {
                                var r, o = 0, n = "";
                                return e.varItems.forEach(function(i) {
                                    o < i.beginIndex && (n += e.expText.substring(o, i.beginIndex)),
                                        1 === i.varField.type ? (r = l(t, i.varField.object.Name),
                                            n += r.SummaryDataField) : n += i.varField.getExpText(),
                                        o = i.endIndex
                                }),
                                    n += e.expText.substr(o)
                            }
                            function g(e, t) {
                                return e = "[#" + e,
                                t && (e += ":" + t),
                                    e += "#]"
                            }
                            function m(e, t) {
                                var r, o = 0, i = "";
                                return e.varItems.forEach(function(a) {
                                    var l = a.varField
                                        , s = l.type;
                                    o < a.beginIndex && (i += e.expText.substring(o, a.beginIndex)),
                                        4 === s ? (r = c(l.paramExp, l.SummaryFun),
                                            i += t ? W(n.SummaryFun, l.SummaryFun) + "(" + r + (l.RankNo ? "," + l.RankNo : "") + ")" : r) : i += l.getExpText(),
                                        o = a.endIndex
                                }),
                                    i += e.expText.substr(o)
                            }
                            function v() {
                                s.forEach(function(t, r) {
                                    function o(e, t) {
                                        t.isToGenerate() && e.Controls.forEach(function(e) {
                                            var r, o, n, a = e.ControlType, s = e.AlignColumn || e.AlignColumnEx;
                                            s === E.Name && (r = t.Controls.Add(a),
                                                r.assign(e),
                                                r.AlignColumn = y.Name,
                                                r.AlignColumnEx = "",
                                                5 === a ? e.DataField && (r.DataField = c(e.paramExp, e.SummaryFun),
                                                    r.Tag = e.DataField,
                                                    i.push(r)) : 8 === a ? (o = "",
                                                    e.textBuilder.forEach(function(e) {
                                                        o += e.varItems ? g(m(e, !0), e.format) : e.text
                                                    }),
                                                    r.Text = o) : 4 === a && (n = d.FieldByName(r.DataField),
                                                n && (r.DataField = l(0, originFieldName).SummaryDataField)))
                                        })
                                    }
                                    var n, i = [];
                                    n = T[r],
                                        o(n.Footer, t.Footer),
                                        o(n.Header, t.Header),
                                        i.forEach(function(r) {
                                            var o, n;
                                            V.CalcHPercent && (r.Format = u.PercentFormat,
                                                o = e(r.Tag),
                                                n = a(t, o),
                                            n || (n = new vt(t,o),
                                                u.HPercentSums.push(n)),
                                                B ? n.TotalSumBox = r : n.DataSumBoxes.push(r))
                                        })
                                })
                            }
                            var y, S, R, A = 1 !== i, B = 4 === i, N = u.GroupAutoSum && A, E = r.Column, M = E.orderNo, V = new bt;
                            ce === ue && (2 == i && M >= ae || 3 == i && ae > M) || de === fe && (3 == i && M >= le || 4 == i && le > M) || (y = C.addTo(o ? o.SubTitles : f.ColumnTitle.TitleCells),
                                y.assign(E),
                                y.Name = P,
                            2 !== i && 3 !== i || (y._crossOrderNo = w),
                                x(r, y.TitleCell),
                                y.ContentCell.assign(E.ContentCell),
                                S = y.ContentCell.getControls(),
                                R = 1 === S.length,
                                B ? (me.indexOf(E) >= 0 && (V.CalcHPercent = 1),
                                Ce.indexOf(E) >= 0 && (V.CalcVPercent = 1)) : (pe.indexOf(E) >= 0 && (V.CalcHPercent = 1),
                                ge.indexOf(E) >= 0 && (V.CalcVPercent = 1)),
                                S.forEach(function(t, r) {
                                    function o(r) {
                                        s.forEach(function(o, n) {
                                            function i(e, t) {
                                                return e.isToGenerate() && e.Controls.items.some(function(e) {
                                                    return e.AlignColumn === t.Name || e.AlignColumnEx === t.Name
                                                })
                                            }
                                            function l(e) {
                                                var o, n, i, a;
                                                return e.isToGenerate() && (o = e.Controls.Add(5),
                                                    o.assign(t),
                                                    o.Dock = 0,
                                                    o.TextAlign = t.TextAlign,
                                                    o.AlignColumn = y.Name,
                                                    o.DataField = r.summaryDataField,
                                                b.ShowColLine && (n = o.Border,
                                                    n.Styles |= 4,
                                                    i = b.ColLinePen.Width,
                                                    a = t.Border,
                                                    a && 4 & a.Styles && a.Pen.Width > 0 ? i += a.Pen.Width : n.Pen.Color = b.ColLinePen.Color,
                                                    n.Pen.Width = i),
                                                    R ? (o.Top = 0,
                                                        o.Height = e.Height,
                                                    16777215 !== y.ContentCell.BackColor && 16777215 === e.BackColor && (o.BackStyle = 1,
                                                        o.BackColor = y.ContentCell.BackColor)) : (o.Top = t.Top,
                                                        o.Height = t.Height,
                                                    1 === t.BackStyle() && (o.BackStyle = 1,
                                                        o.BackColor = t.BackColor))),
                                                    o
                                            }
                                            var s, c, d, h = T[n];
                                            i(h.Footer, E) || i(h.Header, E) || (s = l(o.Footer),
                                            s || (s = l(o.Header)),
                                            s && (s.Format = r.format,
                                            V.CalcHPercent && (s.Format = u.PercentFormat,
                                                c = e(r.originDataField),
                                                d = a(o, c),
                                            d || (d = new vt(o,c),
                                                u.HPercentSums.push(d)),
                                                B ? d.TotalSumBox = s : d.DataSumBoxes.push(s))))
                                        })
                                    }
                                    function n() {
                                        var e, n, i = t.DataField, a = t.SummaryFun;
                                        5 === f && (i || (i = D[0].originObject.Name),
                                            n = t.Format,
                                            y.ContentCell.Controls ? (t = new Be(y.ContentCell),
                                                t.assign(S[r]),
                                                y.ContentCell.Controls.items[r] = t) : y.ContentCell.assign(S[r])),
                                            d = l(a, i),
                                            t.DataField = d.DataField,
                                        7 !== f && (n && (e = p.Fields.Item(d.DataField),
                                        e && (e.Format = n)),
                                        N && o({
                                            summaryDataField: d.SummaryDataField,
                                            originDataField: i,
                                            format: ""
                                        }))
                                    }
                                    function c() {
                                        var e, n, i, a, s = !1;
                                        s = h && q(t, F),
                                        s || (n = E.ContentCell.getControls()[r],
                                            e = (i = n.textBuilder.items[0]) && i.varItems && i.varItems.some(function(e) {
                                                return 4 === e.varField.type
                                            }),
                                            e ? (a = m(i, !1),
                                                t.Text = g(a, i.format),
                                            N && o({
                                                summaryDataField: _(a),
                                                originDataField: "",
                                                format: i.format
                                            })) : A && (d = l(0, t.Name),
                                                t.Text = "[#" + d.DataField + "#]",
                                            N && o({
                                                summaryDataField: d.SummaryDataField,
                                                originDataField: t.Name,
                                                format: ""
                                            })))
                                    }
                                    var d, f = t.ControlType;
                                    4 === f || 5 === f || 7 === f ? n() : 12 === f ? 1 !== i && (d = l(t.Name, 0),
                                        t.Text = "[#" + d.DataField + "#]") : 8 === f && c()
                                }),
                            A && v())
                        }
                        var F = a ? (2 === i ? "_" : "_S") + a : ""
                            , P = r.Name + F;
                        r.GroupTitle ? y() : r.Column.Visible && S()
                    }
                    function k() {
                        var e = v.Fields;
                        p.Fields.forEach(function(t) {
                            P.push({
                                crossField: t,
                                originField: e.itemByName(t.Name)
                            })
                        })
                    }
                    function I() {
                        D.forEach(function(e) {
                            function t(e, t, o) {
                                r = e.CopyAddField(p, t + o),
                                    r.GetDisplayTextScript = "",
                                    R.push(r)
                            }
                            var r, o = e.HTotalFlag, n = e.originObject.Name;
                            o.CalcHTotal && t(e, n, at),
                            o.CalcHAverage && t(e, n, lt),
                            o.CalcHAverageA && t(e, n, st),
                            o.CalcHCount && t(e, n, ct),
                            o.CalcHCountA && t(e, n, ut),
                            o.CalcHMin && t(e, n, dt),
                            o.CalcHMax && t(e, n, ht),
                            e.CalcTotalHPercent && (r = M(n + gt),
                                r.Format = u.PercentFormat,
                                R.push(r)),
                            e.CalcTotalVPercent && (r = M(n + mt),
                                r.Format = u.PercentFormat,
                                R.push(r))
                        })
                    }
                    function H() {
                        var t = y.length;
                        for (a = fe; t > a; a++)
                            O(y[a], e, 4, e, 0, !0, -1)
                    }
                    function j(e) {
                        var t, r = e.getControls();
                        e.GroupTitle && 1 === r.length && (t = r[0],
                        t.TextFormat && (xe[xe.length - 1].push(new xt(t)),
                        e.SubTitles.items.length >= 1 && j(e.SubTitles.items[0])))
                    }
                    function U() {
                        var e = [];
                        u.HResort && S.forEach(function(t) {
                            e.push({
                                field: t,
                                asc: u.HSortAsc,
                                "case": 1
                            })
                        }),
                            $ = v.sortRecords(e, 1)
                    }
                    function z(t) {
                        var r, o;
                        for (r = "_" + t,
                                 S.forEach(function(e) {
                                     o = p.Fields.Add(),
                                         o.assign(e),
                                         o._tableMember = "",
                                         o.DBFieldName = "",
                                         o.Name += r,
                                         o._hfval = e.Value,
                                         A.push(o)
                                 }),
                                 D.forEach(function(e) {
                                     var t = e.originObject.Name + r;
                                     R.push(e.CopyAddField(p, t)),
                                     e.CalcHPercent && (o = M(t + ft),
                                         o.Format = u.PercentFormat,
                                         R.push(o)),
                                     e.CalcVPercent && (o = M(t + pt),
                                         o.Format = u.PercentFormat,
                                         R.push(o))
                                 }),
                                 a = se; ce >= a; a++)
                            O(y[a], e, 2, t, 0, !1, a)
                    }
                    function J() {
                        function t() {
                            var e, t, r = !0, o = xe[xe.length - 1], n = y[se], i = 0;
                            if (xe.length > 1)
                                return !1;
                            for (; n.GroupTitle && !(i >= o.length) && (e = o[i],
                                r = !!e.PriorCrossTitleCell && e.PriorTitleText == e.OriginTextBox.getDisplayText()) && (t = n.SubTitles,
                                !(t.Count <= 0)) && !(++i >= he); )
                                n = t.Item(1);
                            return !r
                        }
                        function r() {
                            var t, r, o;
                            for (B.push(w),
                                     t = B.length,
                                     r = "_S" + t,
                                     D.forEach(function(e) {
                                         o = e.originObject.Name + r,
                                             N.push(e.CopyAddField(p, o + at)),
                                         e.HSubtotalFlag.CalcHAverage && N.push(e.CopyAddField(p, o + lt)),
                                         e.HSubtotalFlag.CalcHAverageA && N.push(e.CopyAddField(p, o + st)),
                                         e.HSubtotalFlag.CalcHCount && N.push(e.CopyAddField(p, o + ct)),
                                         e.HSubtotalFlag.CalcHCountA && N.push(e.CopyAddField(p, o + ut)),
                                         e.HSubtotalFlag.CalcHMin && N.push(e.CopyAddField(p, o + dt)),
                                         e.HSubtotalFlag.IsCalcHMax && N.push(e.CopyAddField(p, o + ht))
                                     }),
                                     a = ue; de >= a; a++)
                                O(y[a], e, 3, t, 0, !1, -1)
                        }
                        $.forEach(function(e, o) {
                            (e < u.VAddedItemRecordOffsetBegin || e >= u.VAddedItemRecordOffsetEnd) && (v.MoveTo(e),
                            o && !v.fieldsKeepedValueChanged(S) || (u.SubtotalCols > 0 && o && t() && (v.MoveTo($[o - 1]),
                                r(),
                                v.MoveTo(e)),
                                v.keepValue(),
                                z(++w)),
                                v.curRecord._CrossNo_ = w)
                        }),
                        u.SubtotalCols > 0 && r()
                    }
                    function Y() {
                        function e() {
                            return re ? u.curPeriod = l.periodRangeNext(u.curPeriod, u.HCrossPeriodType) : (re = r(),
                                L(re.begin),
                                L(re.end),
                                G(re.end),
                                u.curPeriod = l.periodRangeBy(re.begin, u.HCrossPeriodType)),
                            u.curPeriod.begin < re.end
                        }
                        for (var t, o, n = $.length, i = 0; e(); )
                            for (t = S[0].AsDateTime,
                                     S[0].AsDateTime = u.curPeriod.begin,
                                     z(++w),
                                     S[0].AsDateTime = t; n > i && (v.MoveTo($[i]),
                                o = S[0].AsDateTime,
                                !(o < u.curPeriod.begin || o >= u.curPeriod.end)); )
                                v.curRecord._CrossNo_ = w,
                                    i++
                    }
                    function X() {
                        u.HPercentSums.forEach(function(e) {
                            function t(e) {
                                e.isToGenerate() && (r = e.Controls.Add(5),
                                    r.DataField = o)
                            }
                            var r, o;
                            e.TotalSumBox || (o = e.DataFieldInfo.originObject.Name + at,
                                t(e.Group.Footer),
                            !r && t(e.Group.Header),
                                r.Visible = !1,
                                e.TotalSumBox = r)
                        })
                    }
                    function Z() {
                        var e = R.splice(0, K);
                        R = R.concat(e)
                    }
                    function q(e, t) {
                        var r, o = e.Text, n = o.indexOf(":");
                        return n > 0 && o.substring(2, n) === S[0].Name ? (r = "G" === t ? "[#" + ne : o.substr(0, n) + t,
                            r += o.substr(n),
                            e.Text = r,
                            1) : 0
                    }
                    function Q() {
                        function e() {
                            var e, t = S[0], r = new Date(2009,7,30), o = t.AsDateTime;
                            for (e = 0; 7 > e; ++e)
                                t && (t.AsDateTime = r),
                                    z(++w),
                                    G(r);
                            t.AsDateTime = o
                        }
                        function t(e) {
                            var t = S[0];
                            e.Controls.forEach(function(e) {
                                4 === e.ControlType ? t.Name === e.DataField && (e.DataField = ne) : 8 === e.ControlType && q(e, "G")
                            })
                        }
                        var r, o, n = f.Groups.items[0];
                        e(),
                        n && (r = p.Fields.Add(),
                            r.FieldType = 2,
                            r.Name = oe,
                            F.push(r),
                            n.ByFields = oe,
                            r = p.Fields.Add(),
                            r.FieldType = 6,
                            r.Format = S[0].Format,
                            r.Name = ne,
                            F.push(r),
                            t(n.Header),
                            t(n.Footer)),
                        x.Count > 1 && (o = x.items[1].ContentCell.BackColor,
                            C.Item(1).ContentCell.BackColor = o,
                            C.Item(7).ContentCell.BackColor = o)
                    }
                    var K, $, te, re, oe = "_Month_", ne = "_Date_", ie = 0, ae = 1, le = 1, se = -1, ce = -1, ue = -1, de = -1, he = 0, fe = -1, pe = [], ge = [], me = [], Ce = [], be = [], ve = [], xe = [];
                    if (h) {
                        if (u.GroupAutoSum = !1,
                            v.Fields.forEach(function(e) {
                                6 === e.FieldType && S.push(e)
                            }),
                            !S.length)
                            throw new Error("None date field!")
                    } else {
                        if (pe = x.decodeItems(u.HPercentColumns),
                            ge = x.decodeItems(u.VPercentColumns),
                            me = x.decodeItems(u.TotalHPercentColumns),
                            Ce = x.decodeItems(u.TotalVPercentColumns),
                            be = x.decodeItems(u.TotalExcludeColumns),
                            S = v.decodeFields(u.HCrossFields),
                            !S.length)
                            throw new Error("生成交叉表不成功，没有定义'横向交叉数据字段'！");
                        F = v.decodeFields(u.VCrossFields),
                            ve = v.decodeFields(u.DisabledSumFields),
                            ie = u.ListCols,
                            le = x.Count - u.TotalCols,
                            ae = le - u.SubtotalCols
                    }
                    if (y.forEach(function(e, t) {
                        var r, o = e.findLastColumn();
                        o && (r = o.orderNo,
                        0 > se && r >= ie && (se = t),
                        0 > ce && r + 1 >= ae && (ce = t),
                        0 > ue && r >= ae && (ue = t),
                        0 > de && r + 1 >= le && (de = t),
                        0 > fe && r >= le && (fe = t))
                    }),
                    0 > ue && (ue = ce + 1),
                    0 > fe && (fe = de + 1),
                    le > ae && ce >= ue)
                        for (te = x.items[ae].TitleCell; te.SupCell; )
                            ++he,
                                te = te.SupCell;
                    if (o(),
                        i(),
                        V(),
                        f.clearColumns(),
                        h)
                        Q();
                    else {
                        if (!D.length)
                            throw new Error("生成交叉表不成功，不存在交叉数据项！");
                        if (0 >= se)
                            throw new Error("生成交叉表不成功，不存在交叉数据列！");
                        for (U(),
                                 a = 0; se > a; ++a)
                            O(y[a], e, 1, e, 0, !0, -1);
                        for (k(),
                                 I(),
                                 K = R.length,
                             u.HTotalAtFirst && H(),
                                 a = se; ce >= a; a++)
                            xe.push([]),
                                j(y[a]);
                        0 === u.HCrossPeriodType ? J() : Y(),
                        !u.HTotalAtFirst && H(),
                        K > 0 && Z(),
                            X()
                    }
                }
                function i() {
                    function t() {
                        var e = [];
                        u.VResort && (F.forEach(function(t) {
                            e.push({
                                field: t,
                                asc: u.VSortAsc,
                                "case": 1
                            })
                        }),
                            S.forEach(function(t) {
                                e.push({
                                    field: t,
                                    asc: u.HSortAsc,
                                    "case": 1
                                })
                            })),
                            s = v.sortRecords(e, 1)
                    }
                    function o(e, t) {
                        D.forEach(function(r, o) {
                            var n, i = c * e + r.DatafieldIndex;
                            r.NumericField ? (n = r.originObject.AsFloat,
                                R[i].AsFloat += n,
                            r.CalcHTotalExclude && r.TotalValue || (r.TotalValue += n,
                            t && (t[e * D.length + o] += n))) : R[i].IsNull && (R[i].Value = r.originObject.Value)
                        })
                    }
                    function n() {
                        p.Append(),
                            P.forEach(function(e) {
                                e.crossField.Value = e.originField.Value
                            }),
                            A.forEach(function(e) {
                                e.Value = e._hfval
                            }),
                            D.forEach(function(e) {
                                e.TotalValue = 0
                            })
                    }
                    function i() {
                        function e(e, t, i, a, l) {
                            function s() {
                                var l, s, u = Number.MAX_VALUE, d = -Number.MAX_VALUE;
                                for (r = e; t > r; ++r)
                                    l = R[c * r + i.DatafieldIndex],
                                    l.IsNull || (s = l.AsFloat,
                                    isNaN(s) || (u > s && (u = s),
                                    s > d && (d = s)));
                                a.CalcHMin && (u < Number.MAX_VALUE && (n[o].AsFloat = u),
                                    o++),
                                a.CalcHMax && (d > -Number.MAX_VALUE && (n[o].AsFloat = d),
                                    o++)
                            }
                            var u = t - e
                                , d = u;
                            if (a.CalcHAverageA || a.CalcHCountA)
                                for (r = e; t > r; r++)
                                    R[c * r + i.DatafieldIndex].IsNull && --d;
                            n[o].AsFloat = l,
                                o++,
                            a.CalcHAverage && (u > 0 && (n[o].AsFloat = l / u),
                                o++),
                            a.CalcHAverageA && (d > 0 && (n[o].AsFloat = l / d),
                                o++),
                            a.CalcHCount && (n[o].AsFloat = u,
                                o++),
                            a.CalcHCountA && (n[o].AsFloat = d,
                                o++),
                            (a.CalcHMin || a.CalcHMax) && s()
                        }
                        function t() {
                            var t = N.length / B.length
                                , i = 0;
                            B.forEach(function(a, l) {
                                var s = Q(D.length, 0);
                                for (r = i; a > r; ++r)
                                    D.forEach(function(e, t) {
                                        s[t] += R[c * r + e.DatafieldIndex].AsFloat
                                    });
                                n = N,
                                    o = t * l,
                                    D.forEach(function(t, r) {
                                        e(i, a, t, t.HSubtotalFlag, s[r])
                                    }),
                                    i = a
                            })
                        }
                        var r, o = c * w, n = R;
                        for (D.forEach(function(t) {
                            t.HTotalFlag.Flag && (e(0, w, t, t.HTotalFlag, t.TotalValue, R),
                            t.CalcTotalHPercent && (n[o].AsFloat = 1,
                                o++),
                            t.CalcTotalVPercent && o++,
                                t.EntireTotalValue += t.TotalValue)
                        }),
                                 r = 0; w > r; ++r)
                            D.forEach(function(e) {
                                e.CalcHPercent && (o = c * r + e.DatafieldIndex,
                                    0 != e.TotalValue ? R[o + 1].AsFloat = R[o].AsFloat / e.TotalValue : R[o + 1].Clear())
                            });
                        B.length && t(),
                            p.Post()
                    }
                    function a() {
                        var e, t, r, o, n, i, a;
                        for (p.First(); !p.Eof(); ) {
                            for (p.Edit(),
                                     e = 0,
                                     t = 0,
                                     r = 0,
                                     o = 0; w > o; ++o)
                                D.forEach(function(r, l) {
                                    r.CalcVPercent && (n = c * o + r.DatafieldIndex,
                                        i = R[n].AsFloat,
                                        a = d[o * D.length + l],
                                    r.CalcHPercent && ++n,
                                        ++n,
                                    a && (R[n].AsFloat = i / a),
                                        e += i,
                                        t += a)
                                }),
                                o === B[r] && (D.forEach(function(o) {
                                    o.CalcVPercent && (n = c * r + o.DatafieldIndex + 1 + (o.CalcHPercent ? 1 : 0),
                                    t && (N[n].AsFloat = e / t))
                                }),
                                    r++);
                            n = c * w,
                                D.forEach(function(e) {
                                    var t = e.HTotalFlag;
                                    n < R.length && (i = R[n].AsFloat,
                                    t.CalcHTotal && ++n,
                                    t.CalcHAverage && ++n,
                                    t.CalcHAverageA && ++n,
                                    t.CalcHCount && ++n,
                                    t.CalcHCountA && ++n,
                                    t.CalcHMin && ++n,
                                    t.CalcHMax && ++n,
                                    e.CalcTotalHPercent && ++n,
                                    e.CalcTotalVPercent && (e.EntireTotalValue && (R[n].AsFloat = i / e.EntireTotalValue),
                                        ++n))
                                }),
                                p.Post(),
                                p.Next()
                        }
                    }
                    function l() {
                        var t, a, l, c, u = r(), d = [], h = 0;
                        for (d.push({
                            field: S[0],
                            asc: 1,
                            "case": 1
                        }),
                                 s = v.sortRecords(d, 1),
                                 t = s.length; u.begin <= u.end; ) {
                            for (; t > h && (v.MoveTo(s[h]),
                                !(S[0].AsDateTime >= u.begin)); )
                                h++;
                            for (a = u.begin.getDay(),
                                     l = u.begin.getMonth(),
                                     n(),
                                 F.length && (F[0].AsInteger = l + 1,
                                     F[1].AsDateTime = u.begin),
                                     c = a; 7 > c; ++c) {
                                for (A[c].AsDateTime = u.begin; t > h && (v.MoveTo(s[h]),
                                    !(S[0].AsDateTime > u.begin)); )
                                    o(c, e),
                                        h++;
                                if (G(u.begin),
                                u.begin > u.end || F.length && l !== u.begin.getMonth())
                                    break
                            }
                            i()
                        }
                    }
                    var s, d = Q(w * D.length, 0);
                    h ? l() : (t(),
                        s.forEach(function(e, t) {
                            (e < u.HAddedItemRecordOffsetBegin || e >= u.HAddedItemRecordOffsetEnd) && (v.MoveTo(e),
                            t && !v.fieldsKeepedValueChanged(F) || (v.keepValue(),
                            t && i(),
                                n())),
                            v.curRecord._CrossNo_ && o(v.curRecord._CrossNo_ - 1, d)
                        }),
                        i(),
                    D.some(function(e) {
                        return e.CalcVPercent || e.CalcTotalVPercent
                    }) && a())
                }
                var a, s, c, u = this, d = u.report, h = 9 === u.HCrossPeriodType, f = new ot(d), p = f.Recordset, m = f.ColumnTitle.TitleCells, C = f.Columns, b = u.owner, v = b.Recordset, x = b.Columns, T = b.Groups.items, y = b.ColumnTitle.TitleCells.items, S = [], F = [], w = 0, D = [], P = [], R = [], A = [], B = [], N = [];
                u.HAddedItemRecordOffsetBegin = -1,
                    u.HAddedItemRecordOffsetEnd = -1,
                    u.VAddedItemRecordOffsetBegin = -1,
                    u.VAddedItemRecordOffsetEnd = -1,
                    u.HPercentSums = [],
                    f.assign(b),
                    f.IsCrossTab = 0,
                    f.Recordset.BeforePostRecordScript = "",
                    s = f.Groups.items,
                    o(),
                    i(),
                    d.RunningDetailGrid = f,
                    f.attachData(),
                    f.prepare()
            },
            unprepare: function() {},
            GroupEndProcess: function(e) {
                var t = this;
                t.HPercentSums.forEach(function(t) {
                    var r;
                    t.Group === e && (r = t.TotalSumBox.Value,
                        t.DataSumBoxes.forEach(function(e) {
                            r ? e.Value /= r : e.Value = 0
                        }),
                        t.TotalSumBox.Value = 1)
                })
            },
            getClickedCellHCrossFieldValue: function(e) {
                var t, r, o = this.report.RunningDetailGrid, n = o.clickedCell, i = n ? n.getAttribute("_grcrossno") : 0;
                return i && (t = o.Recordset.Fields.Item(e + "_" + i),
                t && (r = t.Value)),
                    r
            },
            getClickedCellHCrossPeriod: function() {
                var e, t = this, r = t.report.RunningDetailGrid, o = r.clickedCell, n = o ? "_" + o.getAttribute("_grcrossno") : 0;
                return n && (e = l.periodRangeBy(r.Recordset.Fields.Item(t.HCrossFields + n).AsDateTime, t.HCrossPeriodType)),
                    e
            },
            get Recordset() {
                var t = this.report
                    , r = t.RunningDetailGrid;
                return r != t.DetailGrid ? r.Recordset : e
            },
            HBeginAddItem: function() {
                var e = this;
                e.HAddedItemRecordOffsetBegin = e.report.RunningDetailGrid.Recordset.RecordCount
            },
            HEndAddItem: function() {
                var e = this;
                e.HAddedItemRecordOffsetEnd = e.report.RunningDetailGrid.Recordset.RecordCount
            },
            VBeginAddItem: function() {
                var e = this;
                e.VAddedItemRecordOffsetBegin = e.report.RunningDetailGrid.Recordset.RecordCount
            },
            VEndAddItem: function() {
                var e = this;
                e.VAddedItemRecordOffsetEnd = e.report.RunningDetailGrid.Recordset.RecordCount
            },
            GetCurPeriodBeginDate: function() {
                return this.curPeriod.begin
            },
            GetCurPeriodEndDate: function() {
                return this.curPeriod.end
            }
        },
            q(Tt, xe);
        var yt = .25
            , St = 96 / 2.54
            , Ft = [3381606, 39423, 16737996, 3394764, 10079232, 6737100, 10040319, 3407718, 16737843, 13421568, 6723993, 14535833, 3342489, 8965273, 13369497, 14535833, 13395507, 10092424, 10066278, 14522879]
            , wt = Ft.length
            , Dt = 4
            , Pt = 8
            , Rt = 4
            , At = function(e, t) {
            var r, o, n = 0;
            for (e >= 1 && (t = 0),
                     r = 0; t > r; r++)
                e *= 10;
            for (o = e; o > 1; )
                ++n,
                    o /= 10;
            if (o = .15 > o ? .1 : .3 > o ? .2 : .75 > o ? .5 : 1,
            t > 0 && (n -= t),
            n > 0)
                for (r = 0; n > r; r++)
                    o *= 10;
            else
                for (r = n; 0 > r; r++)
                    o /= 10;
            return o
        }
            , Bt = function(e) {
            ue.call(this, e)
        };
        Z(Bt, ue),
            Bt.prototype._createItem = function() {
                return new kt(this.owner)
            }
        ;
        var Nt = function(e, t) {
            var r = this;
            r.series = e,
                r.group = t
        }
            , Et = function(e, t, r) {
            var o = this;
            Nt.call(o, e, t),
                o.rect = r
        };
        Et.prototype = {
            inRange: function(e, t) {
                var r = this.rect
                    , o = r.Width()
                    , n = r.Height();
                return (4 > o || 4 > n) && (r = r.clone(),
                4 > o && (r.left -= 2,
                    r.right += 2),
                4 > n && (r.top -= 2,
                    r.bottom += 2)),
                    r.PtInRect(e, t)
            },
            tooltipPos: function() {
                var e = this.rect;
                return {
                    x: (e.left + e.right) / 2,
                    y: (e.top + e.bottom) / 2
                }
            }
        };
        var Mt = function(e, t, r, o, n, i, a) {
            var l = this;
            Nt.call(l, e, t),
                l.x = r,
                l.y = o,
                l.r = n,
                l.beginAngle = i,
                l.endAngle = a
        };
        Mt.prototype = {
            inRange: function(e, t) {
                function r() {
                    var r = e - o.x
                        , n = t - o.y
                        , i = Math.sqrt(r * r + n * n)
                        , a = Math.atan2(n, r);
                    return 0 > n && (a += 2 * Math.PI),
                        {
                            angle: 360 - ae(a),
                            distance: i
                        }
                }
                var o = this
                    , n = r();
                return o.beginAngle <= n.angle && n.angle < o.endAngle && n.distance < o.r
            },
            tooltipPos: function() {
                var e = this
                    , t = -le(e.beginAngle + e.endAngle) / 2
                    , r = e.r / 2;
                return {
                    x: e.x + Math.cos(t) * r,
                    y: e.y + Math.sin(t) * r
                }
            }
        };
        var Vt = function(e, t, r, o, n) {
            var i = this;
            Nt.call(i, e, t),
                i.x = r,
                i.y = o,
                i.r = n
        };
        Vt.prototype = {
            inRange: function(e, t) {
                var r = this
                    , o = e - r.x
                    , n = t - r.y;
                return Math.sqrt(o * o + n * n) <= r.r
            },
            tooltipPos: function() {
                var e = this;
                return {
                    x: e.x + 4,
                    y: e.y - 4 - It.prototype.options.tooltipFontSize
                }
            }
        };
        var Ot = function(e) {
            var t = this;
            xe.call(t, e),
                t.LinePen = new C,
                t.CoordLinePen = new C,
                t.Label = "",
                t.Max = 0,
                t.Min = 0,
                t.Space = 0,
                t.TextAngle = 0,
                t.TextVisible = !0,
                t.TextFormat = "0.##",
                t.LineVisible = !0,
                t.CoordLineVisible = !1,
                t.MarginBeginWeight = 25,
                t.MarginEndWeight = 25,
                t.Scales = [],
                t.CustomCoordLines = []
        };
        Ot.prototype = {
            children: ["LinePen", "CoordLinePen"],
            afterLoad: function(e) {
                var t = this
                    , r = t.report.viewer.alpha.chartStroke
                    , o = t.report.isWR;
                t.LinePen.loadFromJSON(e.LinePen, r, o),
                    t.CoordLinePen.loadFromJSON(e.CoordLinePen, r, o)
            },
            CalcLabelHeight: function() {
                var e = this;
                return e.Used && e.Label ? e.owner.fontHeight + Rt / 2 : 0
            },
            CalcHorzAxisHeight: function(e) {
                return this.DoCalcAxisSize(!0, e)
            },
            CalcVertAxisWidth: function() {
                return this.DoCalcAxisSize(!1, !1)
            },
            DoCalcAxisSize: function(e, t) {
                var r, o = this, n = o.owner, i = [], a = o.owner.fontHeight, l = 0;
                if (o.Used) {
                    if (o.TextVisible && (!e || !n.IsDrawNegativeGraph())) {
                        if (t)
                            i.push("1Agf");
                        else if (o.IsValueAxis)
                            if (o.Scales.length)
                                o.Scales.forEach(function(e) {
                                    i.push(e.Text)
                                });
                            else
                                for (r = o.DrawMin; r < o.DrawMax + o.GetScaleTiny(); r += o.DrawSpace)
                                    i.push(o.ScaleFormatParser.format(r));
                        else
                            for (r = 0; r < n.GroupCount; r++)
                                i.push(n.GroupLabels[r]);
                        -270 === o.TextAngle ? (i.forEach(function(e) {
                            l = Math.max(l, e.length)
                        }),
                            l *= a) : (i.forEach(function(e) {
                            l = Math.max(l, n.context.measureTextWidth(e))
                        }),
                        e && (l = Math.abs(Math.sin(le(o.TextAngle)) * l) + 1 + a))
                    }
                    n.Chart3DReal && n.IsHorzGraph() && n.YAxis === o && (l += n.yView3DDepth),
                    o.LineVisible && (l += Dt),
                    l && (l += Rt)
                }
                return l
            },
            CalcGroupPos: function(e, t) {
                var r = this
                    , o = r.owner
                    , n = o.IsHorzGraph() ? o.CalcColumnBarCount() : o.CalcBarCount()
                    , i = r.MarginBeginWeight + r.MarginEndWeight + 100 * (o.GroupCount - 1) + (n > 0 ? o.BarWidthPercent : 0)
                    , a = i ? e * r.MarginBeginWeight / i : 0
                    , l = i ? e * r.MarginEndWeight / i : 0
                    , s = e - a - l
                    , c = n > 0 ? o.BarWidthPercent : 0
                    , u = 100 * t
                    , d = u + c
                    , h = {};
                return i -= r.MarginBeginWeight + r.MarginEndWeight,
                    h.BeginPos = a,
                    h.EndPos = a,
                i > 0 && (h.BeginPos += s * u / i,
                    h.EndPos += s * d / i),
                    h.LabelMiddlePos = (h.BeginPos + h.EndPos) / 2,
                    h
            },
            DrawHorzAxisLabel: function(e) {
                var t = this
                    , r = t.owner.context;
                !e.IsRectEmpty() && t.Label && r.drawText(t.Label, (e.left + e.right - r.measureTextWidth(t.Label)) / 2, (e.top + e.bottom - t.owner.fontHeight) / 2)
            },
            DrawVertAxisLabel: function(e) {
                var t = this
                    , r = t.owner.context;
                !e.IsRectEmpty() && t.Label && r.drawTextRotate(t.Label, (e.left + e.right - t.owner.fontHeight) / 2, (e.top + e.bottom + r.measureTextWidth(t.Label)) / 2, 90)
            },
            DrawAxisTextsPrepare: function(e) {
                var t, r = this, o = r.owner, n = [];
                if (r.IsValueAxis)
                    if (r.Scales.length)
                        r.Scales.forEach(function(t) {
                            n.push({
                                Text: t.Text,
                                PosVal: r.CalcValuePos(t.PosVal, e)
                            })
                        });
                    else
                        for (t = r.DrawMin; t < r.DrawMax + r.GetScaleTiny(); t += r.DrawSpace)
                            n.push({
                                Text: r.ScaleFormatParser.format(t),
                                PosVal: r.CalcValuePos(t, e)
                            });
                else
                    for (t = 0; t < o.GroupCount; t++)
                        n.push({
                            Text: o.GroupLabels[t] || "",
                            PosVal: r.CalcGroupPos(e, t).LabelMiddlePos
                        });
                return n
            },
            DrawHorzAxis: function(e, t, r) {
                function o(e, t, r, o, n) {
                    var i, a, l, s, c = h.owner.context, u = r.length, d = h.owner.fontHeight;
                    if (o ? (a = e.bottom - 1,
                    h.LineVisible && (a -= Dt),
                    f.Chart3DReal && (a -= f.yView3DDepth),
                        t += f.xView3DDepth) : (a = e.top + 1,
                    h.LineVisible && (a += Dt)),
                    -270 === h.TextAngle)
                        for (s = c.measureTextWidth("A"),
                                 i = e.left + t - s,
                             o && (a -= d * u),
                                 l = 0; u > l; l++)
                            c.drawText(r.charAt(l), i, a),
                                a += d;
                    else
                        s = c.measureTextWidth(r),
                            i = e.left + t - Math.abs(s / 2 * Math.cos(le(h.TextAngle))) + Math.abs(d / 2 * Math.sin(le(h.TextAngle))),
                        o && (a -= d),
                        n || (a -= d + 2 * Dt - 1),
                            c.drawTextRotate(r, i, a, h.TextAngle)
                }
                var n, i, a, l, s, c, u, d, h = this, f = h.owner, p = f.context, g = e.Width() - f.xView3DDepth, m = f.IsDrawNegativeGraph(), C = h.IsValueAxis, b = h.LinePen, v = b.clone(), x = b.clone();
                h.Used && !e.IsRectEmpty() && (v.Width = .5,
                    x.Width = .5,
                    x.Color = Y(b.Color),
                h.LineVisible && (p.selectPen(b),
                    i = e.left,
                    a = i + f.xView3DDepth,
                    l = i + g,
                    s = l + f.xView3DDepth,
                    c = r ? e.bottom : e.top,
                    u = c - f.yView3DDepth,
                    d = r ? t.bottom - 2 * f.yView3DDepth : t.top,
                    p.DrawHorzLine(c, i, l),
                f.Chart3DReal && (f.YAxis.Used || !f.Y2Axis.Used || f.IsHorzGraph() ? (p.drawPolyLine([l, c, s, u, a, u], 0),
                    p.drawPolyLine([s, u, s, d, a, d], 0)) : (p.drawPolyLine([s, u, a, u, i, c], 0),
                    p.drawPolyLine([s, d, a, d, a, u], 0))),
                    p.restorePen()),
                    n = h.DrawAxisTextsPrepare(g),
                    p.selectPen(v),
                    n.forEach(function(n, i) {
                        var a, l, s = f.IsScatterGraph() || !m || (h.IsValueAxis ? n.PosVal >= 0 : f.Value(0, i) >= 0), c = e.left + n.PosVal;
                        h.LineVisible && (r ? (c += f.xView3DDepth,
                            l = e.bottom - f.yView3DDepth,
                            a = l - Dt) : (a = e.top + 1,
                            l = s ? e.top + 1 + Dt : e.top + 1 - Dt),
                            p.DrawVertLine(c, a, l),
                        f.Chart3DReal && !C && (r && (c -= f.xView3DDepth,
                            a += f.yView3DDepth + Dt),
                            p.selectPen(x),
                            p.drawLine(c, a, c + f.xView3DDepth, a - f.yView3DDepth),
                            p.restorePen())),
                        h.TextVisible && o(e, n.PosVal, n.Text, r, s),
                        h.CoordLineVisible && !C && h.IsValueAxis && (p.selectPen(h.CoordLinePen),
                            a = t.top + 1,
                            l = t.bottom - f.yView3DDepth,
                        r && (a -= f.yView3DDepth,
                            l -= f.yView3DDepth),
                            p.DrawVertLine(c + f.xView3DDepth, a, l),
                            p.restorePen()),
                            C = !1
                    }),
                    p.restorePen(),
                    h.CustomCoordLines.forEach(function(n) {
                        var i = h.CalcValuePos(n.PosVal, g);
                        n.Text && o(e, i, n.Text, r, !0),
                            p.selectPen(n.LinePen),
                            p.DrawVertLine(e.left + i, t.top + 1, t.bottom),
                            p.restorePen()
                    }))
            },
            DrawVertAxis: function(e, t, r, o) {
                function n(e, t, r, o) {
                    var n = d.owner.context;
                    n.drawText(r, o ? e.left + (d.LineVisible ? Dt : 0) : e.right - n.measureTextWidth(r) - (d.LineVisible ? Dt : 0), t - d.owner.fontHeight / 2)
                }
                var i, a, l, s, c, u, d = this, h = d.owner, f = h.context, p = e.Height() - h.yView3DDepth, g = d.LinePen, m = g.clone(), C = g.clone();
                !e.IsRectEmpty() && d.Used && (m.Width = .5,
                    C.Width = .5,
                    C.Color = Y(g.Color),
                d.LineVisible && (f.selectPen(g),
                    i = r ? e.left - h.xView3DDepth : e.right,
                    a = e.bottom,
                o && (a -= h.yView3DDepth),
                    l = a - p,
                    f.DrawVertLine(i, l, a + 1),
                h.Chart3DReal && (s = i + h.xView3DDepth,
                    c = a - h.yView3DDepth,
                    f.drawPolyLine([i, a, s, c, s, c - p, i, l], 0)),
                    f.restorePen()),
                    f.selectPen(m),
                    u = d.DrawAxisTextsPrepare(p),
                    u.forEach(function(i, a) {
                        var l, s, c = d.IsValueAxis ? e.bottom - i.PosVal : e.top + i.PosVal + (o ? 0 : h.yView3DDepth), u = c;
                        d.LineVisible && i.PosVal != d.DrawMin && (l = e.right - Dt,
                            s = e.right,
                        r && (l = e.left,
                            s = e.left + Dt),
                            f.DrawHorzLine(u, l, s),
                        h.Chart3DReal && (f.selectPen(C),
                            r ? (l = e.left - h.xView3DDepth,
                                u += h.yView3DDepth) : l = e.right,
                            f.drawLine(l, u, l + h.xView3DDepth, u - h.yView3DDepth),
                            f.restorePen())),
                        d.TextVisible && n(e, c, i.Text, r),
                        d.CoordLineVisible && d.IsValueAxis && (f.selectPen(d.CoordLinePen),
                            u -= h.yView3DDepth,
                            f.DrawHorzLine(u, t.left + h.xView3DDepth + g.Width, t.right),
                            f.restorePen())
                    }),
                    f.restorePen(),
                    d.CustomCoordLines.forEach(function(o) {
                        var i = e.bottom - d.CalcValuePos(o.PosVal, p);
                        o.Text && n(e, i, o.Text, r),
                            f.selectPen(o.LinePen),
                            f.DrawHorzLine(i - h.yView3DDepth, t.left + h.xView3DDepth + g.Width, t.right),
                            f.restorePen()
                    }))
            },
            PrepareDraw: function() {
                var e = this;
                e.Used = !1,
                    e.IsValueAxis = !1,
                    e.DrawMax = -Number.MAX_VALUE,
                    e.DrawMin = Number.MAX_VALUE,
                    e.DrawSpace = 0,
                    e.ScaleFormatParser = new h(e.TextFormat)
            },
            PrepareDrawRange: function() {
                var e = this;
                e.Max ? e.DrawMax = e.Max : e.Scales.forEach(function(t) {
                    e.DrawMax = Math.max(e.DrawMax, t.PosVal)
                }),
                    e.Min ? e.DrawMin = e.Min : e.DrawMin > 0 && e.DrawMax > 0 ? e.DrawMin = 0 : e.DrawMin < 0 && e.DrawMax < 0 ? e.DrawMax = 0 : e.owner.NegativeAsZero && (e.DrawMin = 0),
                e.DrawMax <= e.DrawMin && (e.DrawMin = 0,
                    e.DrawMax = 100)
            },
            PrepareDrawSpace: function(e, t, r) {
                var o, n = this;
                n.Space ? n.DrawSpace = n.Space : (o = n.owner.fontHeight * t,
                    n.DrawSpace = (n.DrawMax - n.DrawMin) * o / e,
                    n.DrawSpace = At(n.DrawSpace, n.ScaleFormatParser.positiveAnalyser.precision)),
                n.DrawMax <= n.DrawMin && (n.DrawMax = n.DrawMin + 1,
                    n.DrawSpace = .2),
                n.DrawSpace < 2 * n.GetScaleTiny() && (n.DrawSpace = 2 * n.GetScaleTiny()),
                n.Max || n.Min || r || (n.DrawMax > 0 && (n.DrawMax = n.DrawSpace * Math.ceil(n.DrawMax / n.DrawSpace)),
                n.DrawMin < 0 && (n.DrawMin = n.DrawSpace * Math.floor(n.DrawMin / n.DrawSpace)))
            },
            GetScaleTiny: function() {
                var e = this;
                return (e.DrawMax - e.DrawMin) / 1e4
            },
            CalcValuePos: function(e, t) {
                var r = this;
                return (e - r.DrawMin) * t / (r.DrawMax - r.DrawMin)
            },
            get ScaleCount() {
                return this.Scales.length
            },
            AddCustomScale: function(e, t) {
                this.Scales.push({
                    PosVal: e,
                    Text: t
                })
            },
            EmptyCustomScale: function() {
                this.Scales = []
            },
            GetScaleText: function(e) {
                return this.Scales[e].Text
            },
            GetScaleValue: function(e) {
                return this.Scales[e].PosVal
            },
            AddCustomCoordLine: function(e, t, r, o, n) {
                var i = new C;
                i.Width = r,
                    i.Style = n,
                    i.Color = o,
                    this.CustomCoordLines.push({
                        PosVal: e,
                        Text: t,
                        LinePen: i
                    })
            },
            EmptyCustomCoordLine: function() {
                this.CustomCoordLines = []
            }
        },
            q(Ot, xe);
        var kt = function(e) {
            var t = this;
            xe.call(t, e),
                t.ChartType = 1,
                t.ByY2Axis = !1,
                t.SeriesName = "",
                t.XValueField = "",
                t.YValueField = "",
                t.ZValueField = "",
                t.FillColor = 0,
                t.FillColorAuto = !0,
                t.LabelText = "",
                t.LabelAsGroup = !1,
                t.LabelInBar = !1,
                t.LabelTextAngle = 0,
                t.TooltipText = "",
                t.ValueFormat = "0.##",
                t.MarkerStyle = 3,
                t.MarkerSize = 4,
                t.MarkerColor = 16777215,
                t.MarkerColorAuto = !0,
                t.MarkerLegendShow = !0,
                t.BorderPen = new C,
                t.MarkerPen = new C
        };
        kt.prototype = {
            afterLoad: function(e) {
                var t = this
                    , r = t.report.viewer.alpha
                    , o = r.chartGraph
                    , i = r.chartStroke
                    , a = t.report.isWR;
                "StepBarChart" === t.ChartType ? t.ChartType = 4 : P(t, "ChartType", n.ChartType),
                    P(t, "MarkerStyle", n.PointMarkerStyle),
                    A(t, "FillColor", o),
                    A(t, "MarkerColor", o),
                    t.BorderPen.loadFromJSON(e.BorderPen, i, a),
                    t.MarkerPen.loadFromJSON(e.MarkerPen, i, a)
            },
            PrepareDraw: function() {
                var e = this;
                e.ValueFormatParser = new h(e.ValueFormat)
            },
            IsHorzGraph: function() {
                var e = this;
                return 11 === e.ChartType || 12 === e.ChartType || 13 === e.ChartType
            },
            IsPercent100Graph: function() {
                var e = this;
                return 2 === e.ChartType || 10 === e.ChartType || 13 === e.ChartType
            },
            IsBarChart: function() {
                var e = this;
                return 1 === e.ChartType || 4 === e.ChartType || 10 === e.ChartType || 11 === e.ChartType || 12 === e.ChartType || 13 === e.ChartType
            },
            IsScatterGraph: function() {
                var e = this;
                return 5 === e.ChartType || 6 === e.ChartType || 8 === e.ChartType || 9 === e.ChartType
            },
            CanGroupLabelChart: function() {
                var e = this;
                return 1 === e.ChartType || 11 === e.ChartType
            },
            HasPointMarker: function() {
                var e = this;
                return 3 === e.ChartType || 7 === e.ChartType || 5 === e.ChartType || 6 === e.ChartType || 8 === e.ChartType || 9 === e.ChartType
            },
            Support3D: function() {
                var e = this;
                return 1 === e.ChartType || 4 === e.ChartType || 10 === e.ChartType || 11 === e.ChartType || 12 === e.ChartType || 13 === e.ChartType || 2 === e.ChartType
            }
        },
            q(kt, xe);
        var It = function(e) {
            var t = this;
            Me.call(t, e),
                t.XAxis = new Ot(t),
                t.YAxis = new Ot(t),
                t.Y2Axis = new Ot(t),
                t.Series = new Bt(t),
                t.Recordset = new et(t),
                t.TitleFont = new x(t.Font),
                t.ValueFont = new x(t.Font),
                t.Title = "",
                t.Chart3D = !1,
                t.Chart3DViewAngle = 35,
                t.LegendVisible = !0,
                t.LegendValueVisible = !1,
                t.LegendShowSum = !1,
                t.LegendAtBottom = !1,
                t.LegendColumnCount = 0,
                t.LegendSumLabel = "",
                t.SeriesField = "",
                t.SeriesAuto = !0,
                t.GroupField = "",
                t.GroupAuto = !0,
                t.GroupCount = 5,
                t.SeriesCount = 2,
                t.AbsNegativeValue = !1,
                t.NegativeBarColor = 255,
                t.BarWidthPercent = 70,
                t.NegativeAsZero = !1,
                t.SingleSeriesColored = !0,
                t.Bar3DAxisDepth = 25,
                t.Pie3DHeightDepth = 25,
                t.BubbleScalePerCm = 0,
                t.SeriesLabels = [],
                t.GroupLabels = [],
                t.values = []
        };
        It.prototype = {
            ControlType: 11,
            children: ["Border", "XAxis", "YAxis", "Y2Axis", "Series"],
            options: {
                tooltipBoxColor: "rgba(0,0,0,.8)",
                tooltipBoxCorner: 4,
                tooltipFontSize: 14,
                tooltipFontStyle: "normal",
                tooltipTitleFontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
                tooltipFontColor: "#fff"
            },
            afterLoad: function(e) {
                var t, r, o = this, n = o.report.viewer.alpha, i = n.chartGraph, a = n.chartStroke, l = o.report.isWR;
                if (Me.prototype.afterLoad.call(o, e),
                    A(o, "NegativeBarColor", i),
                    o.XAxis.loadFromJSON(e.XAxis, a),
                    o.YAxis.loadFromJSON(e.YAxis, a),
                    o.Y2Axis.loadFromJSON(e.Y2Axis, a),
                    o.Series.loadFromJSON(e.ChartSeries),
                    o.Recordset.loadFromJSON(e.Recordset),
                    o.TitleFont.loadFromJSON(e.TitleFont, l),
                    o.ValueFont.loadFromJSON(e.ValueFont, l),
                    o.prepareValues(),
                    t = e[o.getJsonMember("GroupLabel")],
                t && (o.GroupLabels = t.split("\r")),
                    t = e[o.getJsonMember("SeriesLabel")],
                t && (o.SeriesLabels = t.split("\r")),
                    t = e[o.getJsonMember("Value")]) {
                    for (t = t.split(","),
                             r = t.length; r-- > 0; )
                        t[r] = +t[r];
                    for (o.values = [],
                             r = 0; r < o.SeriesCount; )
                        o.values.push(t.slice(r++ * o.GroupCount, r * o.GroupCount))
                }
            },
            assign: function(e) {
                var t = this;
                Me.prototype.assign.call(t, e),
                    t.TitleFont.assign(e.TitleFont),
                    t.ValueFont.assign(e.ValueFont),
                    t.XAxis.assign(e.XAxis),
                    t.YAxis.assign(e.YAxis),
                    t.Y2Axis.assign(e.Y2Axis),
                    t.Series.assign(e.Series),
                    t.Recordset.assign(e.Recordset)
            },
            bindRecordset: function() {
                var e = this
                    , t = e.Recordset;
                t.prepare(),
                    e.oSeriesField = t.FieldByName(e.SeriesField),
                    e.oGroupField = t.FieldByName(e.GroupField),
                    e.Series.forEach(function(e) {
                        e.oYValueField = t.FieldByName(e.YValueField),
                        e.IsScatterGraph() && (e.oXValueField = t.FieldByName(e.XValueField),
                        9 === e.ChartType && (e.oZValueField = t.FieldByName(e.ZValueField)))
                    })
            },
            unprepare: function() {
                var e = this;
                Me.prototype.unprepare.call(e),
                    e.Recordset.unprepare()
            },
            loadDrawingData: function() {
                function e() {
                    return s.Series.items.every(function(e) {
                        return e.oYValueField && (!e.IsScatterGraph() || e.oXValueField && (9 !== e.ChartType || e.oZValueField))
                    })
                }
                function t(e, t, r) {
                    var o = 0
                        , n = e.RecordCount;
                    for (t && (t.recordset.MoveTo(r - 1),
                        o = t.beginNoField.AsInteger,
                        n = t.endNoField.AsInteger + 1); n > o; )
                        e.MoveTo(o++),
                            u.appendRefRecord(e.curRecord)
                }
                function r(e, t) {
                    var r, n = p.Groups.items, i = [], l = "", s = [];
                    for (e.recordset.MoveTo(t - 1),
                             a.MoveTo(e.beginNoField.AsInteger),
                             r = e.index; r >= 0; r--)
                        l && (l += ";"),
                            l += n[r].ByFields;
                    for (o.decodeFields(l).forEach(function(e) {
                        i.indexOf(e) < 0 && i.push(e)
                    }),
                             i.forEach(function(e) {
                                 var t = a.Fields.itemByName(e.Name);
                                 t && s.push({
                                     origin: e,
                                     detail: t
                                 })
                             }),
                             o.First(); !o.Eof(); )
                        s.every(function(e) {
                            return !e.detail.valUeq(e.origin)
                        }) && u.appendRefRecord(o.curRecord),
                            o.Next()
                }
                var o, n, i, a, l, s = this, c = s.origin, u = s.Recordset, d = s.owner.owner, h = s.runningNo, f = s.report, p = f.DetailGrid;
                s.bindRecordset(),
                    e() ? (u.RecordCount || (p && (a = p.Recordset),
                    d.ByFields && (l = d),
                    h >= 1e4 && (h = Math.floor(h / 1e4),
                        n = f.ownerSR,
                        d = n.owner.owner,
                        f = d.report,
                        l || p ? n._relations && (--h,
                        d.ByFields && (d.recordset.MoveTo(h),
                            h = d.beginNoField.AsInteger),
                            f.DetailGrid.Recordset.MoveTo(h),
                            i = 1,
                            n.attachRelationTable()) : (d.ByFields && (l = d),
                            p = f.DetailGrid,
                        p && (a = p.Recordset))),
                    c && (o = c.Recordset),
                        o && o.RecordCount ? l ? r(l, h) : u._dataTable = c._dataTable : t(a, l, h),
                    i && n.detachRelationTable()),
                        s.applyDataFromRecordset()) : s.values || s.EmptyValues()
            },
            applyDataFromRecordset: function() {
                var t, r, o, n, i, a, l = this, s = l.Recordset, c = l.Series.items, u = c[0].IsScatterGraph();
                for (l.SeriesAuto && l.oSeriesField && (l.SeriesCount = 0,
                    l.SeriesLabels = []),
                     !u && l.GroupAuto && l.oGroupField && (l.GroupCount = 0,
                         l.GroupLabels = []),
                         l.EmptyValues(),
                         s.First(); !s.Eof(); )
                    t = 0,
                    l.oSeriesField && (r = l.oSeriesField.DisplayText,
                        t = l.GetSeriesIndexByLabel(r, !1),
                    0 > t && l.SeriesAuto && (t = l.SeriesCount,
                        l.SeriesLabels.push(r),
                        ++l.SeriesCount)),
                        o = 0,
                    l.oGroupField && !u && (n = l.oGroupField.DisplayText,
                        o = l.GetGroupIndexByLabel(n, !1),
                    0 > o && l.GroupAuto && (o = l.GroupCount,
                        l.GroupLabels.push(n),
                        ++l.GroupCount)),
                        c.forEach(function(r, n) {
                            n && (t = n),
                                u ? l.AddXYZValue(t, r.oXValueField.AsFloat, r.oYValueField.AsFloat, r.oZValueField ? r.oZValueField.AsFloat : e) : (i = r.oYValueField.AsFloat,
                                    a = l.Value(t, o),
                                a && (i += a),
                                    l.SetValue(t, o, i))
                        }),
                        s.Next()
            },
            showTooltip: function(t, r) {
                var o = this
                    , n = o.options
                    , i = o.context;
                if (t || (t = e),
                o.activeShape !== t && (o.activeShape = t,
                    i.ctx.clearRect(0, 0, o.canvas.width, o.canvas.height),
                    o.draw(),
                    t)) {
                    var a = o.graphSerieses[t.series];
                    if (a.TooltipTextBuilder) {
                        var l, s = i.ctx.font, c = a.TooltipTextBuilder.generateChartDisplayText(t.series, t.group), u = t.tooltipPos(), d = n.tooltipFontSize + 2, h = 0;
                        if (i.ctx.font = B(n.tooltipFontSize, n.tooltipFontStyle, n.tooltipTitleFontFamily),
                        a.tooltipLines > 1) {
                            l = [];
                            for (var f, p, g = 0; (f = c.indexOf("\n", g)) >= 0; )
                                p = c.substring(g, f - ("\r" === c.charAt(f - 1) ? 1 : 0)),
                                    l.push(p),
                                    h = Math.max(h, i.measureTextWidth(p)),
                                    g = f + 1;
                            p = c.substr(g),
                                l.push(p),
                                h = Math.max(h, i.measureTextWidth(p))
                        } else
                            h = i.measureTextWidth(c);
                        var C = new m(u.x,u.y,u.x + h,u.y + d * a.tooltipLines - 2);
                        if (C.InflateRect(n.tooltipBoxCorner, n.tooltipBoxCorner),
                            C.OffsetRect(Math.min(o.canvas.width - C.right, 0), Math.min(o.canvas.height - C.bottom, 0)),
                            i.selectFillStyle(n.tooltipBoxColor),
                            i.roundRectangle(C.left, C.top, C.Width(), C.Height(), n.tooltipBoxCorner, n.tooltipBoxCorner, 1),
                            i.restoreFillStyle(),
                            i.selectFillStyle(n.tooltipFontColor),
                            l) {
                            var b = C.left + n.tooltipBoxCorner
                                , v = C.top + n.tooltipBoxCorner;
                            l.forEach(function(e) {
                                i.drawText(e, b, v),
                                    v += d
                            })
                        } else
                            i.drawTextCenter(c, (C.left + C.right) / 2, (C.top + C.bottom - n.tooltipFontSize) / 2);
                        i.restoreFillStyle(),
                            i.ctx.font = s
                    }
                }
            },
            prepareCanvas: function() {
                function e() {
                    var e, t, r = i.Series.items, o = r.length;
                    for (e = 0; o > e; e++)
                        if (t = r[e],
                        t.LabelText && (t.LabelTextBuilder = new p(i,t.LabelText)),
                            t.TooltipText) {
                            t.TooltipTextBuilder = new p(i,t.TooltipText),
                                t.tooltipLines = 1;
                            for (var n = 0; (n = t.TooltipText.indexOf("\n", n)) >= 0; )
                                ++n,
                                    ++t.tooltipLines;
                            u = 1
                        }
                }
                function t() {
                    var e = le(i.Chart3DViewAngle);
                    i.xView3DDepth = Math.cos(e) * i.Bar3DAxisDepth,
                        i.yView3DDepth = Math.sin(e) * i.Bar3DAxisDepth
                }
                function r() {
                    var e, t, r = i.Series.items, o = i.graphs = [], n = i.graphSerieses = [];
                    for (e = 0; e < i.SeriesCount; e++) {
                        t = e < r.length ? r[e] : r[0],
                            n.push(t);
                        for (var a = 0; a < o.length; a++)
                            if (t.ChartType === n[o[a][0]].ChartType) {
                                o[a].push(e);
                                break
                            }
                        a === o.length && o.push([e])
                    }
                }
                function o(e) {
                    var t, r, o = i.shapes.length;
                    if (i.graphs.length > 1)
                        for (t = 0; o > t; t++)
                            if (r = i.shapes[t],
                            !r.rect && r.inRange(e.x, e.y))
                                return r;
                    for (t = 0; o > t; t++)
                        if (r = i.shapes[t],
                            r.inRange(e.x, e.y))
                            return r;
                    return 0
                }
                function n(e) {
                    for (var t, r = i.shapesL.length; r-- > 0; )
                        if (t = i.shapesL[r],
                            t.inRange(e.x, e.y))
                            return t;
                    return 0
                }
                var i = this
                    , a = i.onclickGraph
                    , l = i.onclickLegend
                    , s = i.ondblclickGraph
                    , c = i.ondblclickLegend
                    , u = 0;
                i.Chart3DReal = i.Chart3D && i.Support3D(),
                    i.xView3DDepth = 0,
                    i.yView3DDepth = 0,
                i.Chart3DReal && t(),
                    i.loadDrawingData(),
                    e(),
                    r(),
                u && ne(i, ["mousemove", "touchstart", "touchmove", "mouseout"], function(e) {
                    var t, r;
                    "mouseout" !== e.type && (t = re(e),
                        r = o(t)),
                        i.showTooltip(r, t)
                }),
                (a || l) && oe(i.canvas, "click", function(e) {
                    var t, r = re(e);
                    a && (t = o(r),
                    t && a(e, i, t.series, t.group)),
                    l && (t = n(r),
                    t && l(e, i, t.series, t.group))
                }),
                (s || c) && oe(i.canvas, "dblclick", function(e) {
                    var t, r = re(e);
                    s && (t = o(r),
                    t && s(e, i, t.series, t.group)),
                    c && (t = n(r),
                    t && c(e, i, t.series, t.group))
                })
            },
            draw: function(t) {
                function r() {
                    function e() {
                        var e, o, n;
                        u.DrawBubbleScalePerCm = u.BubbleScalePerCm,
                        u.DrawBubbleScalePerCm <= 0 && (e = r * t * yt,
                            o = e / (St * St),
                            u.DrawBubbleScalePerCm = p / o),
                            n = Math.sqrt(d / u.DrawBubbleScalePerCm) * St,
                        0 === T.Max && (T.DrawMax += (T.DrawMax - T.DrawMin) * (n / 2) / t),
                        0 == x.Max && (x.DrawMax += (x.DrawMax - x.DrawMin) * (n / 2) / r)
                    }
                    var t, r, o = u.IsScatterGraph(), n = u.Series.items, i = n[0].ChartType, a = 10 === i || 13 === i, d = 0, p = 0;
                    for (x.PrepareDraw(),
                             T.PrepareDraw(),
                             S.PrepareDraw(),
                             s = n.length,
                             l = 0; s > l; l++)
                        n[l].PrepareDraw(this);
                    x.Used = !0,
                        x.IsValueAxis = !1,
                        T.IsValueAxis = !0,
                        S.IsValueAxis = !0,
                    u.Title && (F = N(u.getUsingTitleFont()) + Pt),
                        o ? (x.IsValueAxis = !0,
                            b.forEach(function(e) {
                                e.forEach(function(e) {
                                    var t, r = u.values[e], o = r ? r.length : 0, n = x, i = u.YAxisOfSeries(v[e]);
                                    for (i.Used = !0,
                                             l = 0; o > l; l++)
                                        t = r[l],
                                        t.y < i.DrawMin && (i.DrawMin = t.y),
                                        t.y > i.DrawMax && (i.DrawMax = t.y),
                                        t.x < n.DrawMin && (n.DrawMin = t.x),
                                        t.x > n.DrawMax && (n.DrawMax = t.x),
                                        d < t.z && (d = t.z),
                                            p += t.z
                                })
                            }),
                            x.PrepareDrawRange()) : b.forEach(function(e) {
                            var t, r, o = v[e[0]].ChartType, n = 4 === o || 10 === o || 12 === o || 13 === o;
                            for (t = 0; t < u.GroupCount; t++)
                                if (r = 0,
                                    e.forEach(function(e) {
                                        var o = u.Value(e, t)
                                            , i = u.YAxisOfSeries(v[e]);
                                        n ? r += o : (i.Used = !0,
                                        u.AbsNegativeValue && (o = Math.abs(o)),
                                        o < i.DrawMin && (i.DrawMin = o),
                                        o > i.DrawMax && (i.DrawMax = o))
                                    }),
                                    n) {
                                    var i = v[e[0]]
                                        , l = u.YAxisOfSeries(i);
                                    l.Used = !0,
                                        a ? (l.DrawMin = 0,
                                            l.DrawMax = 100) : (r < l.DrawMin && (l.DrawMin = r),
                                        r > l.DrawMax && (l.DrawMax = r))
                                }
                        }),
                    T.Used || S.Used || (T.Used = !0),
                    T.Used && (c = T.DrawMax),
                    S.Used && c < S.DrawMax && (c = S.DrawMax),
                        T.PrepareDrawRange(),
                        S.PrepareDrawRange(),
                        t = f - F - x.CalcLabelHeight() - x.CalcHorzAxisHeight(!0),
                        t = Math.max(t, 1),
                        T.PrepareDrawSpace(t, 3, a),
                        S.PrepareDrawSpace(t, 3, a),
                    o && (r = h - T.CalcLabelHeight() - T.CalcVertAxisWidth() - S.CalcLabelHeight() - S.CalcVertAxisWidth(),
                        r = Math.max(r, 1),
                        x.PrepareDrawSpace(r, 6, !1),
                        t = f - F - x.CalcLabelHeight() - x.CalcHorzAxisHeight(!1),
                        t = Math.max(t, 1),
                    9 === n[0].ChartType && e())
                }
                function o(t) {
                    function r() {
                        var e = t.Series.items[0].ChartType;
                        return 1 === t.SeriesCount && t.SingleSeriesColored && (1 === e || 3 === e || 7 === e || 11 === e)
                    }
                    function o() {
                        return t.LegendValueVisible && (!t.IsScatterGraph() || 9 === t.Series.items[0].ChartType)
                    }
                    function n() {
                        return t.LegendShowSum && o()
                    }
                    function i() {
                        return (r() ? t.GroupCount : t.SeriesCount) + (n() ? 1 : 0)
                    }
                    function a(e) {
                        return r() ? e === t.GroupCount : e === t.SeriesCount
                    }
                    function l(e) {
                        return a(e) ? t.LegendSumLabel || "合计" : r() ? t.GroupLabels[e] : t.getSeriesShowLabel(e)
                    }
                    function s(o) {
                        var n, i = t.graphSerieses[0];
                        return a(o) ? n = t.GetTotalValue() : r() ? n = t.Value(0, o) : (n = t.GetSeriesTotalValue(o),
                            i = t.graphSerieses[o]),
                            n === e ? "" : i.ValueFormatParser.format(n)
                    }
                    function c(e) {
                        var t = {
                            series: -1,
                            group: -1
                        };
                        return a(e) || (r() ? t.group = e : t.series = e),
                            t
                    }
                    function u(e, o) {
                        var n, i, a = t.context, l = t.GetGraphFillColor(e);
                        r() || (i = t.graphSerieses[e],
                            n = i.HasPointMarker() && i.MarkerLegendShow,
                        n && a.DrawPointMarker(o, i.MarkerStyle, i.MarkerPen, l)),
                        n || (a.selectFillColor(l),
                            a.rectangle2(o, 1),
                            a.restoreFillStyle())
                    }
                    function d(e) {
                        var r, n, d, h, f, p, g, C, b = t.context, v = o(), x = t.LegendColumnCount, T = x > 0 ? x : 999, y = e.Width(), S = 2 * t.fontHeight, F = 3 * S / 4, w = i(), D = y, P = [], R = {
                            Count: 0,
                            Width: 0
                        };
                        if (!w)
                            return 0;
                        for (n = 0; w > n; ++n)
                            r = Pt + F + Rt,
                                r += b.measureTextWidth(l(n)),
                            v && (r += Rt + b.measureTextWidth(s(n))),
                            (r > D || R.Count >= T) && R.Width > 0 && (P.push(R),
                                R = {
                                    Count: 0,
                                    Width: 0
                                },
                                D = y),
                                R.Count++,
                                R.Width += r,
                                D -= r;
                        for (R.Count > 0 && P.push(R),
                                 h = Rt + P.length * S,
                                 d = 0,
                                 R = P[d],
                                 n = e.bottom - h + Rt,
                                 f = new m((e.left + e.right - R.Width) / 2,e.bottom - h + Rt,0,n + S),
                                 n = 0; w > n; ++n)
                            R.Count <= 0 && (d++,
                                R = P[d],
                                f.left = (e.left + e.right - R.Width) / 2,
                                f.top = f.bottom,
                                f.bottom += S),
                                R.Count--,
                                f.left += Pt,
                                y = f.left,
                            (w - 1 > n || !a(n)) && (r = (f.top + f.bottom - F) / 2,
                                p = new m(f.left,r,f.left + F,r + F),
                                u(n, p)),
                                f.left += F,
                                g = (f.top + f.bottom - t.fontHeight) / 2,
                                C = l(n),
                                f.left += Rt,
                                f.right = f.left + b.measureTextWidth(C),
                                b.drawText(C, f.left, g),
                                f.left = f.right,
                            v && (C = s(n),
                                f.left += Rt,
                                f.right = f.left + b.measureTextWidth(C),
                                b.drawText(C, f.left, g),
                                f.left = f.right),
                            t.toUpdateShapes && (T = c(n),
                                t.shapesL.push(new Et(T.series,T.group,new m(y,f.top,f.right,f.bottom))));
                        return h
                    }
                    function h(e) {
                        var r, a, d, h, f, p, g, b, v, x, T, y, S, F, w = t.context, D = o(), P = n(), R = i(), A = t.LegendColumnCount, B = 2 * t.fontHeight, N = 3 * B / 4, E = e.Height() - 2 * Rt, M = 0, V = 0, O = 2 * Rt, k = [], I = [];
                        if (!R)
                            return 0;
                        for (h = 0; R > h; ++h)
                            p = w.measureTextWidth(l(h)),
                            p > M && (M = p),
                                g = B,
                                O += g,
                                k.push(g),
                            D && (p = w.measureTextWidth(s(h)),
                            p > V && (V = p));
                        if (r = M + Rt,
                        V > 0 && (r += V + Rt),
                        0 >= A && (A = 1,
                        O > e.Height()))
                            for (p = 0,
                                     h = 0; R > h; ++h)
                                p > E && (A++,
                                    p = k[h]);
                        if (E < k[0])
                            return 0;
                        for (h = 0,
                                 p = 0; A > p; p++)
                            for (g = E; R > h && g > k[h]; )
                                g -= k[h++];
                        for (a = R > h,
                             a && (p = R,
                             P && (h--,
                                 p--),
                                 k.splice(h, p - h)),
                                 p = k.length,
                                 g = A,
                                 b = Math.ceil(p / A),
                                 I = Q(A, 0),
                             P && (I[A - 1] = b,
                                 p -= b,
                                 g--,
                             g > 0 && (b = Math.ceil(p / g))),
                                 h = 0; g > h; h++)
                            I[h] = Math.min(b, p),
                                p -= I[h];
                        for (g = 0,
                                 h = 0,
                                 p = 0; A > p; p++) {
                            for (b = 0,
                                     f = 0; f < I[p]; f++)
                                b += k[h++];
                            b > g && (g = b)
                        }
                        for (O = Math.min(e.Height(), g + 2 * Rt),
                                 v = Rt + N + Rt + r,
                                 x = v * A + Rt,
                             x > 2 * e.Width() / 3 && (x = 2 * e.Width() / 3,
                                 g = v,
                                 v = (x - Rt) / A,
                                 M -= g - v),
                                 p = e.right - x,
                                 g = (e.top + e.bottom - O) / 2,
                                 T = new m(p,g,p + x,g + O),
                                 w.rectangle2(T),
                                 p = T.left + Rt,
                                 g = v - Rt,
                                 y = new m(p,0,p + N,0),
                                 p += y.Width() + Rt,
                                 g -= y.Width() + Rt,
                                 S = new m(p,T.top + Rt,p + Math.min(g, M + Rt),0),
                                 p += S.Width() + Rt,
                                 g -= S.Width() + Rt,
                                 F = new m(p,0,p + g,0),
                                 p = k.length - 1,
                             P && p--,
                                 g = 0,
                                 h = 0; A > h; h++) {
                            for (S.top = T.top + Rt,
                                     f = 0; f < I[h]; ++f)
                                S.bottom = S.top + k[g],
                                    b = (S.top + S.bottom - t.fontHeight) / 2,
                                    a && p === g ? w.drawText("... ...", y.left, b) : (y.top = (S.top + S.bottom - N) / 2,
                                        y.bottom = y.top + N,
                                        d = P && g === k.length - 1,
                                        d ? g = R - 1 : u(g, y),
                                        w.drawText(l(g), S.left, b),
                                    D && w.drawTextAlign(s(g), F.left, b, F.Width(), t.fontHeight, 20),
                                    d && (w.selectPen(new C(.5,0,0)),
                                        w.DrawHorzLine(S.top, y.left, F.right),
                                        w.restorePen())),
                                t.toUpdateShapes && (M = c(g),
                                    t.shapesL.push(new Et(M.series,M.group,new m(y.left,y.top,F.right,y.bottom)))),
                                    S.top += k[g++];
                            y.left += v,
                                y.right += v,
                                S.left += v,
                                S.right += v,
                                F.left += v,
                                F.right += v
                        }
                        return x
                    }
                    var f;
                    t.LegendAtBottom ? (f = d(g),
                        g.bottom -= f + Pt) : (f = h(g),
                        g.right -= f + Pt)
                }
                function n() {
                    p.selectFont(u.getUsingTitleFont()),
                        p.drawTextCenter(u.Title, (g.left + g.right) / 2, g.top + Pt / 2),
                        p.restoreFont()
                }
                function i(e) {
                    var t, r, o, n, i, a, l, s, d = u.Series.items, h = x.CalcLabelHeight(), f = x.CalcHorzAxisHeight(!1), g = T.CalcLabelHeight(), C = T.CalcVertAxisWidth(), y = S.CalcLabelHeight(), F = S.CalcVertAxisWidth(), w = 0, D = 0, P = !1;
                    for (r = d.length,
                             t = 0; r > t; t++)
                        if (o = d[t],
                        o.LabelTextBuilder && !o.LabelInBar) {
                            P = !0;
                            break
                        }
                    if (!P && u.Title || (w = N(u.getUsingValueFont()) / (o.LabelTextBuilder ? 1 : 2),
                    u.IsDrawNegativeGraph() && (D = w),
                    o.LabelTextAngle && (p.selectFont(u.getUsingValueFont()),
                        w = Math.max(Math.abs(p.measureTextWidth(o.ValueFormatParser.format(c)) * Math.sin(le(o.LabelTextAngle))), w),
                        p.restoreFont())),
                        i = new m(e.left + g + C,e.top + w,e.right - y - F,e.bottom - h - f - D),
                        l = new m(e.left,e.top + w,e.left + g,e.bottom - h - f - D),
                        T.DrawVertAxisLabel(l),
                        s = new m(e.right - y,l.top,e.right,l.bottom),
                        S.DrawVertAxisLabel(s),
                        l.left = l.right,
                        l.right += C,
                        T.DrawVertAxis(l, i, !1),
                        s.right = s.left,
                        s.left -= F,
                        S.DrawVertAxis(s, i, !0),
                        a = new m(e.left + g + C,e.bottom - h,e.right - y - F,e.bottom),
                        x.DrawHorzAxisLabel(a),
                        u.IsDrawNegativeGraph() ? (a.top = l.bottom - T.CalcValuePos(0, l.Height()),
                            a.bottom = a.top + f) : (a.top = i.bottom,
                            a.bottom = a.top + f),
                    u.Chart3D && x.DrawHorzAxis(a, i, !1),
                    !i.IsRectEmpty() && (u.IsScatterGraph() || u.GroupCount)) {
                        for (p.selectFont(u.getUsingValueFont()),
                                 r = b.length,
                                 t = 0; r > t; t++)
                            switch (o = b[t],
                                n = v[o[0]].ChartType) {
                                case 1:
                                    u.DrawBar(o, i);
                                    break;
                                case 4:
                                case 10:
                                    u.DrawStackedBar(o, n, i);
                                    break;
                                case 3:
                                case 7:
                                    u.DrawLine(o, n, i);
                                    break;
                                case 9:
                                    u.DrawBubble(o, i);
                                    break;
                                default:
                                    u.DrawXYScatterGraph(o, n, i)
                            }
                        p.restoreFont()
                    }
                    !u.Chart3D && x.DrawHorzAxis(a, i, !1)
                }
                function a(e) {
                    var t, r, o, n, i, a, l, s, d = u.Series.items, h = x.CalcLabelHeight(), f = x.CalcVertAxisWidth(), g = T.CalcLabelHeight(), C = T.CalcHorzAxisHeight(!1), y = S.CalcLabelHeight(), F = S.CalcHorzAxisHeight(!1), w = 0, D = 0, P = !1;
                    for (r = d.length,
                             t = 0; r > t; t++)
                        if (o = d[t],
                        o.LabelTextBuilder && !o.LabelInBar) {
                            P = !0;
                            break
                        }
                    if (P && (p.selectFont(u.getUsingValueFont()),
                        w = p.measureTextWidth(o.ValueFormatParser.format(c)),
                    u.IsDrawNegativeGraph() && (D = w),
                        p.restoreFont()),
                        i = new m(e.left + h + f + D,e.top + g + C,e.right - w,e.bottom - y - F),
                        l = new m(i.left,e.top,i.right,e.top + g),
                        T.DrawHorzAxisLabel(l),
                        s = new m(l.left,e.bottom - y,l.right,e.bottom),
                        S.DrawHorzAxisLabel(s),
                        l.top = l.bottom,
                        l.bottom += C,
                        T.DrawHorzAxis(l, i, !0),
                        s.bottom = s.top,
                        s.top -= F,
                        S.DrawHorzAxis(s, i, !1),
                        a = new m(e.left,i.top,e.left + h,i.bottom),
                        x.DrawVertAxisLabel(a),
                        a.left = a.right,
                        a.right += f,
                    u.Chart3D && x.DrawVertAxis(a, i, !1, T.Used),
                    !i.IsRectEmpty() && u.GroupCount) {
                        for (p.selectFont(u.getUsingValueFont()),
                                 r = b.length,
                                 t = 0; r > t; t++)
                            switch (o = b[t],
                                n = v[o[0]].ChartType) {
                                case 11:
                                    u.DrawColumnBar(o, i);
                                    break;
                                case 12:
                                case 13:
                                    u.DrawStackedColumnBar(o, n, i)
                            }
                        p.restoreFont()
                    }
                    !u.Chart3D && x.DrawVertAxis(a, i, !1)
                }
                var l, s, c, u = this, d = u.canvas, h = d.width, f = d.height, p = u.context = new y(d.getContext("2d")), g = new m(0,0,h,f), b = u.graphs, v = u.graphSerieses, x = u.XAxis, T = u.YAxis, S = u.Y2Axis, F = 0;
                u.fontHeight = N(u.getUsingFont()),
                    u.valueFontHeight = N(u.getUsingValueFont()),
                    p.selectFont(u.getUsingFont()),
                    p.selectFillColor(u.ForeColor),
                u.report.singleChart && (g.left += u.PaddingLeft,
                    g.top += u.PaddingTop,
                    g.right -= u.PaddingRight,
                    g.bottom -= u.PaddingBottom),
                    u.toUpdateShapes = t,
                t && (u.shapes = [],
                    u.shapesL = []),
                    r(),
                u.LegendVisible && u.SeriesCount && o(u),
                u.Title && n(),
                    g.top += F,
                g.IsRectEmpty() || (2 === u.Series.items[0].ChartType ? u.DrawPie(g) : u.IsHorzGraph() ? a(g) : i(g)),
                    p.restoreFillStyle(),
                    p.restoreFont()
            },
            DrawPie: function(e) {
                var t, r, o, n, i = this, a = i.context, l = 0, s = 0, c = e.Height(), u = e.Width(), d = u * c, h = Number.MAX_VALUE, f = -1, p = -1;
                for (t = 0; t < i.GroupCount; t++) {
                    for (r = 0; r < i.SeriesCount; r++)
                        a.selectFont(i.getUsingValueFont()),
                        i.graphSerieses[r].LabelTextBuilder && (l = Math.max(l, a.measureTextWidth(i.GetDrawValueText(r, t)))),
                            a.restoreFont();
                    s = Math.max(s, a.measureTextWidth(i.GroupLabel(t)))
                }
                for (t = 1; t <= i.GroupCount; t++)
                    r = Math.floor((i.GroupCount + t - 1) / t),
                        o = Math.min(u / r, c / t),
                        o = d - o * o * i.GroupCount,
                    h > o && (h = o,
                        f = r,
                        p = t);
                for (a.selectFont(i.getUsingValueFont()),
                         o = 0; o < i.GroupCount; o++)
                    t = Math.floor(o / f),
                        r = o % f,
                        n = new m(e.left + u * r / f,e.top + c * t / p,e.left + u * (r + 1) / f,e.top + c * (t + 1) / p),
                        i.Chart3DReal ? a.drawTextCenter("三维饼图暂不支持!!!", (n.left + n.right) / 2, (n.top + n.bottom - 16) / 2) : i.DrawOne2DPie(o, n, l, s);
                a.restoreFont()
            },
            DrawOne2DPie: function(e, t, r) {
                function o() {
                    var r, o, p, C, v, x, T = u.getUsingValueFont(), y = T.Italic ? h(T) : 0, S = new m(0,0,0,0);
                    for (c = 0,
                             n = 0; g > n; n++)
                        s = le(b[n]),
                            o = a + Math.cos(s) * i,
                            p = l - Math.sin(s) * i,
                            x = u.GetDrawValueText(n, e),
                            v = d.measureTextWidth(x) + y,
                            r = new m(o,p,o + v,p + f),
                            s < Math.PI / 2 ? (r.OffsetRect(2, -f),
                            1 === c && r.top < S.bottom && (C = S.right - r.left + 2,
                                r.right + C < t.right ? r.OffsetRect(C, 0) : r.OffsetRect(0, S.bottom - r.top),
                                d.drawLine(o, p, r.left, r.bottom - v.cy / 3)),
                                c = 1) : s < Math.PI ? (r.OffsetRect(-v - 2, -f),
                            2 === c && r.top < S.bottom && (C = r.right - S.left + 2,
                                r.left - C > t.left ? r.OffsetRect(-C, 0) : r.OffsetRect(0, S.bottom - r.top),
                                d.drawLine(o, p, r.right, r.bottom - v.cy / 3)),
                                c = 2) : s < 1.5 * Math.PI ? (r.OffsetRect(-v - 2, 0),
                            3 === c && r.bottom > S.top && (C = r.right - S.left + 2,
                                r.left - C > t.left ? r.OffsetRect(-C, 0) : r.OffsetRect(0, r.bottom - S.top),
                                d.drawLine(o, p, r.right, r.top + v.cy / 3)),
                                c = 3) : (r.OffsetRect(2, 0),
                            4 === c && r.bottom > S.top && (C = S.right - r.left + 2,
                                r.right - C < t.right ? r.OffsetRect(C, 0) : r.OffsetRect(0, r.bottom - S.top),
                                d.drawLine(o, p, r.left, r.top + v.cy / 3)),
                                c = 4),
                            u.DrawLabelText(x, (r.left + r.right) / 2, r.top),
                            S = r
                }
                var n, i, a, l, s, c, u = this, d = u.context, h = u.fontHeight, f = u.valueFontHeight, p = !1, g = u.SeriesCount, C = 0, b = [];
                for (n = 0; g > n; n++)
                    C += u.Value(n, e),
                    u.graphSerieses[n].LabelTextBuilder && (p = !0);
                if (!(0 >= C || (a = t.Width() - 2 * r - 2 * Rt,
                    l = t.Height() - h - 2 * f - 2 * Rt,
                0 >= a || 0 >= l || (i = Math.min(a, l) / 2,
                    p ? t.InflateRect(-Rt, -Rt) : t.InflateRect(i - a / 2, i - l / 2),
                    t.bottom -= h,
                    t.IsRectEmpty())))) {
                    for (u.DrawLabelText(u.GroupLabel(e), (t.left + t.right) / 2, t.bottom),
                             a = (t.left + t.right) / 2,
                             l = (t.top + t.bottom) / 2,
                             s = 0,
                             n = 0; g > n; n++)
                        c = s + 360 * u.Value(n, e) / C,
                        u.toUpdateShapes && u.shapes.push(new Mt(n,e,a,l,i,s,c)),
                            d.selectFillColor(u.GetGraphFillColor(n)),
                            d.pie(a, l, i, s, c, 1),
                            d.restoreFillStyle(),
                        p && b.push((s + c) / 2),
                            s = c;
                    p && o()
                }
            },
            DrawColumnBar: function(e, t) {
                var r, o, n, i, a, l, s, c, u, d, h, f, p, g = this, C = g.context, b = g.graphSerieses, v = g.CalcColumnBarCount(), x = g.CalcFirtColumnBarIndex(e), T = t.Width() - g.xView3DDepth, y = t.Height() - g.yView3DDepth, S = g.IsDrawNegativeGraph() ? g.YAxis.CalcValuePos(0, T) : t.left + 1, F = e.length, w = g.IsDrawNegativeGraph(), D = [];
                for (i = 0; i < g.GroupCount; i++)
                    for (c = g.XAxis.CalcGroupPos(y, i),
                             u = c.EndPos - c.BeginPos,
                             r = t.top + c.BeginPos,
                         g.Y2Axis.Used && (r += g.yView3DDepth),
                             o = F; o-- > 0; )
                        a = e[o],
                            l = b[a],
                            n = g.Value(a, i),
                            d = g.AbsNegativeValue && 0 > n,
                        d && (n = -n),
                            p = new m(0,r + u * (x + o) / v,0,r + u * (x + o + 1) / v),
                            f = g.YAxisOfSeries(l).CalcValuePos(n, T),
                            w ? (p.left = t.left + S,
                                p.right = Math.min(t.left + f, t.right),
                            S > f && (f = p.left,
                                p.left = p.right,
                                p.right = f)) : (p.left = t.left,
                                p.right = Math.min(t.left + Math.max(0, f), t.right)),
                            D.push(p),
                        g.toUpdateShapes && g.shapes.push(new Et(a,i,p)),
                            h = d ? g.NegativeBarColor : g.GetGraphFillColor(g.IsColorSingleBar() ? i : a),
                            g.DrawRectBar(p, h, l.BorderPen, !0, !0, n >= 0);
                for (i = 0; i < g.GroupCount; i++)
                    if (l = b[e[0]],
                        l.LabelAsGroup) {
                        for (s = g.GetDrawValueText(e[0], i),
                                 f = -9999,
                                 o = 0; F > o; o++)
                            p = D[i * F + o],
                            f < p.right && (f = p.right);
                        g.DrawValueText(l, s, f + Rt + C.measureTextWidth(s) / 2, (D[i * F].top + D[i * F + F - 1].bottom + g.valueFontHeight) / 2)
                    } else
                        for (o = 0; F > o; o++)
                            a = e[o],
                                l = b[a],
                            l.LabelTextBuilder && (s = g.GetDrawValueText(a, i),
                                p = D[i * F + o],
                                l.LabelInBar ? g.DrawHorzBarText(s, p) : g.DrawValueText(l, s, p.right + Rt + C.measureTextWidth(s) / 2, (p.top + p.bottom + g.valueFontHeight) / 2))
            },
            DrawStackedColumnBar: function(e, t, r) {
                var o, n, i, a, l, s, c, u, d, h, f, p, g, C, b = this, v = b.context, x = b.graphSerieses, T = b.CalcColumnBarCount(), y = b.CalcFirtColumnBarIndex(e), S = r.Width() - b.xView3DDepth, F = r.Height() - b.yView3DDepth, w = e.length, D = [];
                for (i = 0; i < b.GroupCount; i++) {
                    for (s = b.XAxis.CalcGroupPos(F, i),
                             c = s.EndPos - s.BeginPos,
                             d = r.top + s.BeginPos,
                         b.Y2Axis.Used && (d += b.yView3DDepth),
                             p = d + c * y / T,
                             g = d + c * (y + 1) / T,
                             h = r.left,
                             o = 0; w > o; o++)
                        a = e[o],
                            l = x[a],
                            n = b.Value(a, i),
                        13 === t && (n *= 100 / b.CalcGroupTotalValue(e, i)),
                            f = Math.max(0, b.YAxisOfSeries(l).CalcValuePos(n, S)),
                            C = new m(h,p,Math.min(r.right, h + f),g),
                            D.push(C),
                        b.toUpdateShapes && b.shapes.push(new Et(a,i,C)),
                            u = b.GetGraphFillColor(a),
                            b.DrawRectBar(C, u, l.BorderPen, !0, o === w - 1, !0),
                            h += f;
                    if (l.LabelInBar)
                        for (o = 0; w > o; o++)
                            a = e[o],
                                l = x[a],
                            l.LabelTextBuilder && b.DrawHorzBarText(b.GetDrawValueText(a, i), D[i * w + o]);
                    else
                        a = e[0],
                            l = x[a],
                            n = b.GetDrawValueText(a, i),
                            b.DrawValueText(l, n, h + Rt + v.measureTextWidth(n) / 2, (p + g + b.valueFontHeight) / 2)
                }
            },
            DrawBar: function(e, t) {
                var r, o, n, i, a, l, s, c, u, d, h, f, p, g = this, C = g.graphSerieses, b = g.CalcBarCount(), v = g.CalcFirtBarIndex(e), x = g.IsDrawNegativeGraph(), T = t.Height() - g.yView3DDepth, y = x ? g.YAxis.CalcValuePos(0, T) : t.bottom + 1, S = e.length, F = [];
                for (i = 0; i < g.GroupCount; i++)
                    for (c = g.XAxis.CalcGroupPos(t.Width() - g.xView3DDepth, i),
                             u = c.EndPos - c.BeginPos,
                             n = v,
                             r = 0; S > r; r++)
                        a = e[r],
                            l = C[a],
                            o = g.Value(a, i),
                            d = g.AbsNegativeValue && 0 > o,
                        d && (o = -o),
                            p = new m(t.left + c.BeginPos + u * n / b,0,t.left + c.BeginPos + u * (n + 1) / b,0),
                            f = g.YAxisOfSeries(l).CalcValuePos(o, T),
                            x ? (p.top = Math.max(t.bottom - f, t.top),
                                p.bottom = t.bottom - y,
                            y > f && (f = p.top,
                                p.top = p.bottom,
                                p.bottom = f)) : (p.top = t.bottom - f,
                                p.bottom = t.bottom),
                            F.push(p),
                        g.toUpdateShapes && g.shapes.push(new Et(a,i,p)),
                            h = d ? g.NegativeBarColor : g.GetGraphFillColor(g.IsColorSingleBar() ? i : a),
                            g.DrawRectBar(p, h, l.BorderPen, !1, !0, o >= 0),
                            ++n;
                for (i = 0; i < g.GroupCount; i++)
                    if (a = e[0],
                        l = C[a],
                        l.LabelAsGroup) {
                        for (s = g.GetDrawValueText(a, i),
                                 f = 9999,
                                 r = 0; S > r; r++)
                            p = F[i * S + r],
                            f > p.top && (f = p.top);
                        g.DrawValueText(l, s, (F[i * S].left + F[i * S + S - 1].right + g.xView3DDepth) / 2, f - Rt / 2 - g.yView3DDepth)
                    } else
                        for (r = 0; S > r; r++)
                            a = e[r],
                                l = C[a],
                            l.LabelTextBuilder && (s = g.GetDrawValueText(a, i),
                                p = F[i * S + r],
                                l.LabelInBar ? g.DrawVertBarText(s, p) : (f = p.top - Rt / 2 - g.yView3DDepth,
                                g.IsDrawNegativeGraph() && t.bottom - y < p.bottom && (f = p.bottom + g.valueFontHeight),
                                    g.DrawValueText(l, s, (p.left + p.right + g.xView3DDepth) / 2, f)))
            },
            DrawStackedBar: function(e, t, r) {
                var o, n, i, a, l, s, c, u, d, h, f, p, g, C = this, b = C.graphSerieses, v = C.CalcBarCount(), x = C.CalcFirtBarIndex(e), T = r.Width() - C.xView3DDepth, y = r.Height() - C.yView3DDepth, S = e.length, F = [];
                for (i = 0; i < C.GroupCount; i++) {
                    for (s = C.XAxis.CalcGroupPos(T, i),
                             c = s.EndPos - s.BeginPos,
                             h = r.left + s.BeginPos + c * x / v,
                             f = r.left + s.BeginPos + c * (x + 1) / v,
                             p = r.bottom,
                             o = 0; S > o; o++)
                        a = e[o],
                            l = b[a],
                            n = C.Value(a, i),
                        10 === t && (n *= 100 / C.CalcGroupTotalValue(e, i)),
                            d = C.YAxisOfSeries(l).CalcValuePos(n, y),
                            g = new m(h,Math.min(Math.max(p - d, r.top), p),f,p),
                            F.push(g),
                        C.toUpdateShapes && C.shapes.push(new Et(a,i,g)),
                            u = C.GetGraphFillColor(a),
                            C.DrawRectBar(g, u, l.BorderPen, !1, o === S - 1, !0),
                            p -= d;
                    if (l.LabelInBar)
                        for (o = 0; S > o; o++)
                            a = e[o],
                                l = b[a],
                            l.LabelTextBuilder && C.DrawVertBarText(C.GetDrawValueText(a, i), F[i * S + o]);
                    else
                        a = e[0],
                            l = b[a],
                            C.DrawValueText(l, C.GetDrawValueText(a, i), (h + f + C.xView3DDepth) / 2, p - Rt / 2 - C.yView3DDepth)
                }
            },
            DrawRectBar: function(e, t, r, o, n, i) {
                function a(e, t) {
                    s.selectFillColor(e),
                        s.selectPen(new C(1,e,1)),
                        s.drawPolyLine(t, 1, 1),
                        s.restorePen(),
                        s.restoreFillStyle()
                }
                var l = this
                    , s = l.context
                    , c = Y(t)
                    , u = X(t)
                    , d = [];
                l.Chart3DReal && (d.length = 8,
                    d[0] = e.right - 1,
                    d[1] = e.top,
                    d[2] = d[0] + l.xView3DDepth,
                    d[3] = d[1] - l.yView3DDepth,
                (n || o) && (d[4] = d[2] - e.Width(),
                    d[5] = d[3],
                    d[6] = e.left,
                    d[7] = d[1],
                    a(c, d)),
                !n && o || (d[4] = d[2],
                    d[5] = e.bottom - l.yView3DDepth,
                    d[6] = e.right - 1,
                    d[7] = e.bottom,
                    a(u, d))),
                    l.Chart3DReal ? (s.selectFillColor(t),
                        s.rectangle2(e, 1),
                        s.restoreFillStyle()) : s.drawBar(e, r, t, o ? i ? 1 : 3 : i ? 0 : 2)
            },
            DrawLine: function(e, t, r) {
                var o, n, i, a, l, s, c, u, d = this, h = d.context, f = e.length, p = [];
                for (o = 0; f > o; o++)
                    p.push([]);
                for (n = 0; n < d.GroupCount; n++)
                    for (c = r.left + d.XAxis.CalcGroupPos(r.Width() - d.xView3DDepth, n).LabelMiddlePos,
                             o = 0; f > o; o++) {
                        i = e[o],
                            a = d.graphSerieses[i];
                        var g = {
                            x: c,
                            y: r.bottom - d.YAxisOfSeries(a).CalcValuePos(d.Value(i, n), r.Height())
                        };
                        p[o].push(g),
                        d.toUpdateShapes && d.shapes.push(new Vt(i,n,g.x,g.y,a.MarkerSize))
                    }
                for (o = 0; f > o; o++)
                    (7 === t ? d.DrawCurveLine : d.DrawPolyLine).call(d, p[o], e[o]);
                for (o = 0; f > o; o++)
                    d.DrawPointsMarker(p[o], e[o]);
                for (n = 0; n < d.GroupCount; n++)
                    for (o = 0; f > o; o++)
                        i = e[o],
                            a = d.graphSerieses[i],
                        a.LabelTextBuilder && (u = r.bottom - d.YAxisOfSeries(a).CalcValuePos(d.Value(i, n), r.Height()),
                        -1 !== a.MarkerStyle && (u -= a.MarkerSize / 2),
                            l = d.GetDrawValueText(i, n),
                            s = h.measureTextWidth(l),
                            c = p[o][n].x + s / 2,
                        d.GroupCount > 1 && (c -= s * n / (d.GroupCount - 1)),
                            d.DrawValueText(a, l, c, u))
            },
            DrawBubble: function(e, t) {
                var r, o, n, i, a, l, s, c, u, d = this, h = e.length, f = [], p = [];
                for (r = 0; h > r; r++)
                    for (i = e[r],
                             a = d.graphSerieses[i],
                             c = [],
                             u = [],
                             f.push(c),
                             p.push(u),
                             s = d.ValueCount(i),
                             n = 0; s > n; n++) {
                        o = d.Value(i, n),
                            l = Math.sqrt(o.z / d.DrawBubbleScalePerCm) * St / 2;
                        var g = {
                            x: t.left + d.XAxis.CalcValuePos(o.x, t.Width()),
                            y: t.bottom - d.YAxisOfSeries(a).CalcValuePos(o.y, t.Height())
                        };
                        c.push(g),
                            u.push(l),
                        d.toUpdateShapes && d.shapes.push(new Vt(i,n,g.x,g.y,l)),
                        a.LabelTextBuilder && d.DrawValueText(a, d.GetDrawValueText(i, n), g.x, g.y - l)
                    }
                for (r = 0; h > r; r++)
                    i = e[r],
                        d.DrawPointsMarker(f[i], i, p[i])
            },
            DrawXYScatterGraph: function(e, t, r) {
                var o, n, i, a, l, s, c, u = this, d = e.length, h = [];
                for (o = 0; d > o; o++)
                    for (a = e[o],
                             l = u.graphSerieses[a],
                             c = [],
                             h.push(c),
                             s = u.ValueCount(a),
                             i = 0; s > i; i++) {
                        n = u.Value(a, i);
                        var f = {
                            x: r.left + u.XAxis.CalcValuePos(n.x, r.Width()),
                            y: r.bottom - u.YAxisOfSeries(l).CalcValuePos(n.y, r.Height())
                        };
                        c.push(f),
                        u.toUpdateShapes && u.shapes.push(new Vt(a,i,f.x,f.y,l.MarkerSize)),
                        l.LabelTextBuilder && u.DrawValueText(l, u.GetDrawValueText(a, i), f.x, f.y - l.MarkerSize)
                    }
                if (6 === t || 8 === t)
                    for (o = 0; d > o; o++)
                        a = e[o],
                            l = u.graphSerieses[a],
                            (8 === t ? u.DrawCurveLine : u.DrawPolyLine).call(u, h[a], a);
                for (o = 0; d > o; o++)
                    a = e[o],
                        u.DrawPointsMarker(h[a], a)
            },
            DrawPolyLine: function(e, t) {
                var r, o, n = this, i = n.context, a = e.length, l = [];
                for (r = 0; a > r; r++)
                    l.push(e[r].x, e[r].y);
                o = n.graphSerieses[t].BorderPen.clone(),
                    o.Color = n.GetGraphFillColor(t),
                    i.selectPen(o),
                    i.drawPolyLine(l),
                    i.restorePen()
            },
            DrawCurveLine: function(e, t) {
                function r(e, t, r) {
                    var o = .4
                        , n = Math.sqrt(Math.pow(t.x - e.x, 2) + Math.pow(t.y - e.y, 2))
                        , i = Math.sqrt(Math.pow(r.x - t.x, 2) + Math.pow(r.y - t.y, 2))
                        , a = n + i
                        , l = a > 0 ? o * n / a : 1
                        , s = a > 0 ? o * i / a : 1;
                    return {
                        inner: {
                            x: t.x - l * (r.x - e.x),
                            y: t.y - l * (r.y - e.y)
                        },
                        outer: {
                            x: t.x + s * (r.x - e.x),
                            y: t.y + s * (r.y - e.y)
                        }
                    }
                }
                var o, n, i, a, l = this, s = l.context, c = e.length, u = [];
                if (c > 1) {
                    for (i = e[0],
                             a = r(i, i, e[1]),
                             u.push(i, a.outer),
                             o = 1; c - 1 > o; o++)
                        i = e[o],
                            a = r(e[o - 1], i, e[o + 1]),
                            u.push(a.inner, i, a.outer);
                    i = e[o],
                        a = r(e[o - 1], i, i),
                        u.push(a.inner, i),
                        n = l.graphSerieses[t].BorderPen.clone(),
                        n.Color = l.GetGraphFillColor(t),
                        s.selectPen(n),
                        s.polyBezier(u),
                        s.restorePen()
                }
            },
            DrawPointsMarker: function(e, t, o) {
                var n, i = this, a = i.context, l = i.graphSerieses[t], s = e.length;
                if (-1 !== l.MarkerStyle)
                    for (n = 0; s > n; n++)
                        a.DrawPointMarker(r(e[n].x, e[n].y, o ? o[n] : l.MarkerSize), l.MarkerStyle, l.MarkerPen, l.MarkerColorAuto ? i.GetGraphFillColor(t) : l.MarkerColor)
            },
            DrawValueText: function(e, t, r, o) {
                var n, i = this, a = i.context, l = i.valueFontHeight;
                e.LabelTextAngle ? (n = le(e.LabelTextAngle),
                    r -= (a.measureTextWidth(t) * Math.cos(n) + Math.abs(l * Math.sin(n))) / 2,
                    o -= Math.abs(l * Math.cos(n)),
                    a.drawTextRotate(t, r, o, e.LabelTextAngle)) : a.drawTextCenter(t, r, o - l)
            },
            DrawLabelText: function(e, t, r) {
                var o = this.context;
                o.drawTextCenter(e, t, r)
            },
            DrawVertBarText: function(e, t) {
                var r = this
                    , o = r.context;
                o.drawTextRotate(e, (t.left + t.right - r.fontHeight) / 2, (t.top + t.bottom + o.measureTextWidth(e)) / 2, 90)
            },
            DrawHorzBarText: function(e, t) {
                var r = this
                    , o = r.context
                    , n = o.measureTextWidth(e)
                    , i = r.fontHeight
                    , a = (t.left + t.right - n) / 2
                    , l = (t.top + t.bottom - i) / 2;
                o.drawText(e, a, l)
            },
            CalcBarCount: function() {
                var e, t, r, o = this, n = o.graphs.length, i = 0;
                for (e = 0; n > e; e++)
                    t = o.graphs[e],
                        r = o.graphSerieses[t[0]].ChartType,
                        1 === r ? i += t.length : 4 !== r && 10 !== r || i++;
                return i
            },
            CalcColumnBarCount: function() {
                var e, t, r, o = this, n = o.graphs.length, i = 0;
                for (e = 0; n > e; e++)
                    t = o.graphs[e],
                        r = o.graphSerieses[t[0]].ChartType,
                        11 === r ? i += t.length : 12 !== r && 13 !== r || i++;
                return i
            },
            CalcFirtBarIndex: function(e) {
                var t, r, o, n = this, i = n.graphs, a = i.length, l = 0;
                for (t = 0; a > t && (r = i[t],
                r !== e); t++)
                    o = n.graphSerieses[r[0]].ChartType,
                        1 === o ? l += r.length : 4 !== o && 10 !== o || ++l;
                return l
            },
            CalcFirtColumnBarIndex: function(e) {
                var t, r, o, n = this, i = n.graphs, a = i.length, l = 0;
                for (t = 0; a > t && (r = i[t],
                r !== e); t++)
                    o = n.graphSerieses[r[0]].ChartType,
                        11 === o ? l += r.length : 12 !== o && 13 !== o || ++l;
                return l
            },
            CalcGroupTotalValue: function(e, t) {
                var r, o = this, n = e.length, i = 0;
                for (r = 0; n > r; r++)
                    i += o.Value(e[r], t);
                return i
            },
            IsScatterGraph: function() {
                return this.Series.items[0].IsScatterGraph()
            },
            IsHorzGraph: function() {
                return this.Series.items[0].IsHorzGraph()
            },
            IsColorSingleBar: function() {
                var e = this;
                return 1 == e.SeriesCount && e.SingleSeriesColored
            },
            IsDrawNegativeGraph: function() {
                var e = this;
                return !e.Series.items.some(function(e) {
                    return e.ByY2Axis
                }) && e.YAxis.DrawMax >= 0 && e.YAxis.DrawMin < 0 && !e.AbsNegativeValue
            },
            Support3D: function() {
                return this.Series.items.every(function(e) {
                    return e.Support3D()
                })
            },
            YAxisOfSeries: function(e) {
                var t = this;
                return e.ByY2Axis ? t.Y2Axis : t.YAxis
            },
            getSeriesShowLabel: function(e) {
                var t = this;
                return t.SeriesLabels[e] || (t.graphSerieses[e] ? t.graphSerieses[e].SeriesName : "")
            },
            getUsingTitleFont: function() {
                return this.TitleFont.UsingFont()
            },
            getUsingValueFont: function() {
                return this.ValueFont.UsingFont()
            },
            GetGraphFillColor: function(e) {
                var t, r, o, n, i = this, a = i.graphSerieses[e], l = i.FillColors ? i.FillColors.length : wt;
                return i.IsColorSingleBar() || a.FillColorAuto ? (t = Math.floor(e / l),
                    r = e % l,
                    o = i.FillColors ? i.FillColors[r] : z(Ft[r], i.report.viewer.alpha.chartGraph),
                t > 0 && (n = J(o),
                    o = U((n.r + 73 * r + 196 * t) % 255, (n.g + 197 * r + 120 * t) % 255, (n.b + 117 * r + 90 * t) % 255, n.a)),
                    o) : a.FillColor
            },
            GetSeriesIndexByLabel: function(e, t) {
                var r = this
                    , o = r.SeriesLabels.indexOf(e);
                return 0 > o && t && (o = +e,
                (!isNaN(o) || o >= r.SeriesCount) && (o = -1)),
                    o
            },
            GetGroupIndexByLabel: function(e, t) {
                var r = this
                    , o = r.GroupLabels.indexOf(e);
                return 0 > o && t && (o = +e,
                (!isNaN(o) || o >= r.GroupCount) && (o = -1)),
                    o
            },
            GetDrawValueText: function(e, t) {
                var r = this.graphSerieses[e];
                return r.LabelTextBuilder ? r.LabelTextBuilder.generateChartDisplayText(e, t) : ""
            },
            GetGroupTotalValue: function(e, t) {
                var r, o = this, n = e.length, i = 0;
                if (!o.IsScatterGraph())
                    for (r = 0; n > r; r++)
                        i += o.Value(e[r], t);
                return i
            },
            GetSeriesTotalValue: function(e) {
                var t, r, o = this, n = 0;
                if (o.IsScatterGraph())
                    for (r = o.ValueCount(e),
                             t = 0; r > t; t++)
                        n += o.Value(e, t).z;
                else
                    for (t = 0; t < o.GroupCount; t++)
                        n += o.Value(e, t);
                return n
            },
            GetTotalValue: function() {
                var e, t = this, r = 0;
                for (e = 0; e < t.SeriesCount; e++)
                    r += t.GetSeriesTotalValue(e);
                return r
            },
            prepareValues: function() {
                for (var e = this, t = e.values.length; t++ < e.SeriesCount; )
                    e.values.push([])
            },
            get AsChart() {
                return this
            },
            GroupLabel: function(e) {
                return this.GroupLabels[e]
            },
            SetGroupLabel: function(e, t) {
                this.GroupLabels[e] = t
            },
            SeriesLabel: function(e) {
                return this.SeriesLabels[e]
            },
            SetSeriesLabel: function(e, t) {
                this.SeriesLabels[e] = t
            },
            Value: function(t, r) {
                var o, n = this;
                return n.prepareValues(),
                    o = n.values[t][r],
                o === e && (o = 0),
                    o
            },
            SetValue: function(e, t, r) {
                var o = this;
                o.prepareValues(),
                    o.values[e][t] = r
            },
            ValueCount: function(e) {
                var t = this;
                return t.prepareValues(),
                    t.values[e].length
            },
            get FillColorCount() {
                var e = this;
                return e.FillColors ? e.FillColors.length : 0
            },
            getFillColor: function(t) {
                var r = this;
                return r.FillColors ? r.FillColors[t] : e
            },
            AddFillColor: function(e) {
                var t = this;
                t.FillColors || (t.FillColors = []),
                    t.FillColors.push(e)
            },
            EmptyFillColors: function() {
                delete self.FillColors
            },
            LoadDataFromXML: function(e, t, r, o) {
                function n() {
                    return l ? a.text : a.textContent
                }
                var i, a, l, s, c, u, d = this, h = 1, f = te(e);
                if (!dom)
                    return 0;
                for (o && (d.GroupCount = 0,
                    d.GroupLabels = []),
                     r && (d.SeriesCount = 0,
                         d.SeriesLabels = []),
                         d.values = [],
                         i = f.childNodes[0].firstChild,
                         nodeText = i.hasOwnProperty("text"); i; ) {
                    if (a = i.firstChild,
                    t && (s = n(),
                        a = a.nextSibling,
                        c = d.GetSeriesIndexByLabel(s, !r),
                    0 > c && r && (c = d.SeriesCount,
                        d.SeriesCount++,
                        d.SeriesLabels.push(s)),
                    0 > c))
                        throw 0;
                    if (s = n(),
                        a = a.nextSibling,
                        u = d.GetGroupIndexByLabel(s, !o),
                    0 > u && o && (u = d.GroupCount,
                        d.GroupCount++,
                        d.GroupLabels.push(s)),
                    0 > u)
                        throw 0;
                    if (t)
                        d.SetValue(c, u, +n() + d.Value(c, u));
                    else
                        for (c = 0; a; )
                            d.SetValue(c, u, +n() + d.Value(c, u)),
                                c++,
                                a = a.nextSibling;
                    i = i.nextSibling
                }
                return h
            },
            DoLoadXYZDataFromXML: function(e, t, r) {
                function o() {
                    return a ? i.text : i.textContent
                }
                var n, i, a, l, s, c, u, d, h = this, f = 1, p = te(e);
                if (!dom)
                    return 0;
                for (t && (h.SeriesCount = 0,
                    h.SeriesLabels = []),
                         h.values = [],
                         n = p.childNodes[0].firstChild,
                         nodeText = n.hasOwnProperty("text"); n; ) {
                    if (i = n.firstChild,
                        FirstSeries ? (l = o(),
                            d = h.GetSeriesIndexByLabel(l, !t),
                        0 > d && t && (d = m_SeriesCount,
                            ++m_SeriesCount,
                            m_SeriesLabels[d] = l),
                            i = i.nextSibling) : d = 0,
                    0 > d)
                        throw 0;
                    s = +o(),
                        i = i.nextSibling,
                        c = +o(),
                    r || (i = i.nextSibling,
                        u = +o()),
                        h.AddXYZValue(d, s, c, u),
                        n = n.nextSibling
                }
                return f
            },
            LoadXYDataFromXML: function(e, t) {
                return this.DoLoadXYZDataFromXML(e, t, 1)
            },
            LoadXYZDataFromXML: function(e, t) {
                return DoLoadXYZDataFromXML(e, t, 0)
            },
            AddXYZValue: function(e, t, r, o) {
                var n = this;
                n.prepareValues(),
                    n.values[e].push({
                        x: t,
                        y: r,
                        z: o
                    })
            },
            EmptyValues: function() {
                var e = this;
                e.values = [],
                    e.prepareValues()
            }
        },
            q(It, Me),
            gr.dom.Report = it
    }(),
    function(e, t) {
        "use strict";
        var r = gr.helper
            , o = gr.common
            , n = (gr.enum_,
            gr.const_)
            , i = r.penStyleText
            , a = r.fontCSSText
            , l = r.pixelsToHtml
            , s = r.colorValue2Html
            , c = r.assignObject
            , u = r.compareObject
            , d = r.parseXML
            , h = r.xmlToReportDataJSON
            , f = r.addEvent
            , p = o.HtmlStyles
            , g = function(e) {
            var t, r, o = this;
            o.obj = e,
                e.FreeCell ? e.isSingleDockControl() && (r = e.Controls.items[0],
                r.TextFormat && (o.TextFormat = {},
                    o.TextFormat.ForeColor = r.ForeColor,
                    c(o.TextFormat, r.TextFormat)),
                    t = o.padding = {},
                    t.Left = r.PaddingLeft,
                    t.Right = r.PaddingRight,
                    t.Top = r.PaddingTop,
                    t.Bottom = r.PaddingBottom) : (o.TextFormat = {},
                    c(o.TextFormat, e.TextFormat),
                    o.TextFormat.ForeColor = e.ForeColor,
                    t = o.padding = {},
                    t.Left = e.PaddingLeft,
                    t.Right = e.PaddingRight,
                    t.Top = e.PaddingTop,
                    t.Bottom = e.PaddingBottom),
                e.BorderCustom ? (o.border = {},
                    c(o.border, e.Border)) : o.ownerGrid = e.getOwnerGrid(),
                o.toFillBack = e.toFillBack(),
            o.toFillBack && (o.BackColor = e.BackColor)
        };
        g.prototype = {
            compare: function(e) {
                var t = this
                    , r = new g(e);
                return u(t.padding, r.padding) && u(t.TextFormat, r.TextFormat) && u(t.border, r.border) && t.toFillBack === r.toFillBack && t.BackColor === r.BackColor && t.ownerGrid === r.ownerGrid
            },
            getStyleName: function() {
                return "-gr-cell" + this.index
            },
            getStyles: function() {
                var e = this
                    , t = new p
                    , r = e.TextFormat;
                return r && (t.addTextFormat(e, 1),
                    t.addObjectPadding(e)),
                    e.border ? t.addBorder(e.border) : t.addCellBorder(e.ownerGrid),
                e.toFillBack && t.addBackColor(e.BackColor),
                    t
            }
        };
        var m = function(e) {
            var t = this
                , r = t.padding = {}
                , o = e.getUsingFont();
            t.obj = e,
                t.asCell = !!e.cell,
            e.TextFormat && (t.TextFormat = {},
                t.TextFormat.ForeColor = e.ForeColor,
                c(t.TextFormat, e.TextFormat)),
                r.Left = e.PaddingLeft,
                r.Right = e.PaddingRight,
                r.Top = e.PaddingTop,
                r.Bottom = e.PaddingBottom,
                t.border = {},
                c(t.border, e.Border),
                t.toFillBack = e.toFillBack(),
            t.toFillBack && (t.BackColor = e.BackColor),
                t.textdecoration = {
                    Underline: o.Underline,
                    Strikethrough: o.Strikethrough
                }
        };
        m.prototype = {
            compare: function(e) {
                var t = this
                    , r = new m(e);
                return u(t.padding, r.padding) && u(t.TextFormat, r.TextFormat) && u(t.border, r.border) && u(t.textdecoration, r.textdecoration) && t.toFillBack === r.toFillBack && t.BackColor === r.BackColor && t.asCell === r.asCell
            },
            getStyleName: function() {
                return "-gr-ctrl" + this.index
            },
            getStyles: function() {
                var e = this
                    , t = new p
                    , r = e.textdecoration;
                return !e.asCell && t.add("position", "absolute"),
                    t.addObjectPadding(e),
                    t.addBorder(e.border),
                e.TextFormat && t.addTextFormat(e, e.asCell),
                e.toFillBack && t.addBackColor(e.BackColor),
                (r.Underline || r.Strikethrough) && t.add("text-decoration", (r.Underline ? "underline" : "") + (r.Underline && r.Strikethrough ? " " : "") + (r.Strikethrough ? "line-through" : "")),
                    t
            }
        };
        var C = function(e) {
            var t = this;
            t.Left = e.pxRect.left,
                t.Top = e.pxRect.top,
                t.Width = e.getContentWidth(),
                t.Height = e.getContentHeight()
        };
        C.prototype = {
            compare: function(e) {
                var t = this;
                return t.Left === e.pxRect.left && t.Top === e.pxRect.top && t.Width === e.getContentWidth() && t.Height === e.getContentHeight()
            },
            getStyleName: function() {
                return "-gr-pos" + this.index
            },
            getStyles: function() {
                var e = this
                    , t = new p;
                return t.add("left", l(e.Left)),
                    t.add("top", l(e.Top)),
                    t.add("width", l(e.Width)),
                    t.add("height", l(e.Height)),
                    t
            }
        };
        var b = function(e) {
            var t = this
                , r = e.cells ? e : 0;
            t.section = e,
                t.isLastRow = 1,
                t.Height = e.CanShrink ? 0 : e.pxHeight,
            r && (e = r.section,
                t.isLastRow = e.tableRows[e.tableRows.length - 1] === r),
            e.Font.font && (t.Font = {},
                c(t.Font, e.Font.font)),
            e.owner.Header && (t.detailgrid = e.owner.owner),
            !e.toFillBack() || r && !t.detailgrid || (t.BackColor = e.BackColor)
        };
        b.prototype = {
            compare: function(e) {
                var t, r, o = this, n = e.cells ? e : 0, i = e.CanShrink ? 0 : e.pxHeight, a = 1;
                return n && (e = e.section,
                    a = e.tableRows[e.tableRows.length - 1] === n),
                    t = e.toFillBack() && (!n || e.owner.Header),
                    r = e.Font.font,
                o.Height === i && (r && o.Font && u(r, o.Font) || !r && !o.Font) && (t && o.BackColor && o.BackColor === e.BackColor || !t && !o.BackColor) && (e.owner.Header && e.owner.owner === o.detailgrid || !e.owner.Header && !o.detailgrid) && o.isLastRow === a
            },
            getStyleName: function() {
                return "-gr-section" + this.index
            },
            getStyles: function() {
                var e = this
                    , t = e.section
                    , r = t.pctHeight
                    , o = e.Height
                    , n = new p
                    , a = e.detailgrid;
                return (r || o) && n.add("height", r ? r + "%" : l(o)),
                (r || !t.cells && t.report.viewer.options.reportFitWidth) && n.add("width", "100%"),
                e.Font && (n.items = n.items.concat(v.prototype.getStyles.call(e.Font).items)),
                e.BackColor && n.addBackColor(e.BackColor),
                e.isLastRow && a && a.ShowRowLine && n.add("border-bottom", i(a.ColLinePen)),
                    n
            }
        };
        var v = function(e) {
            var t = this;
            t.Size = e.Size,
                t.Bold = e.Bold,
                t.Italic = e.Italic,
                t.Name = e.Name
        };
        v.prototype = {
            compare: function(e) {
                var t = this;
                return t.Size === e.Size && t.Bold === e.Bold && t.Italic === e.Italic && t.Name === e.Name
            },
            getStyleName: function() {
                return "-gr-fs" + this.index
            },
            getStyles: function() {
                var e = new p;
                return e.add("font", a(this)),
                    e
            }
        };
        var x = function(e) {
            this.htmlStyles = e
        };
        x.prototype = {
            getStyleName: function() {
                return "-gr-cs" + this.index
            },
            getStyles: function() {
                return this.htmlStyles
            }
        };
        var T = function(e, t) {
            var r = this;
            r.items = [],
                r.viewer = e,
                r.classStyle = t
        };
        T.prototype = {
            toString: function() {
                for (var e, t = this, r = t.items, o = r.length, n = ""; o-- > 0; )
                    e = r[o],
                        n += "." + t.viewer._getCSSName(e) + "{" + e.getStyles().getText() + "}",
                    DEBUG && (n += "\r\n");
                return n
            },
            select: function(e, t) {
                var r = this;
                return r.viewer._getCSSName(r.selectItem(e, t))
            },
            selectItem: function(e, t) {
                var r, o, n = this, i = n.items.length;
                if (t && t.compare(e))
                    return t;
                for (r = 0; i > r; r++)
                    if (o = n.items[r],
                        o.compare(e))
                        return o;
                return o = new n.classStyle(e),
                    o.index = i,
                    n.items.push(o),
                    o
            }
        };
        var y = function(e) {
            var t, r = 0, o = e.length;
            if ("string" != typeof e)
                return 0;
            for (; o > r && (t = e[r],
                /\s/g.test(t)); )
                r++;
            return o > r && "{" !== t && "<" !== t
        }
            , S = function(e) {
            return "string" == typeof e && "_WR_" == e.substr(0, 4) ? 0 : y(e)
        }
            , F = function(e, t, r, o, n) {
            var i = this;
            i.running = !1,
                i.generated = !1,
                i.events = {},
                i.viewerNo = e,
                i.viewerHolderID = t,
                i.reportSource = r,
                i.dataSource = o,
                i.alpha = {
                    background: 1,
                    border: .8,
                    stroke: .8,
                    pen: .8,
                    text: .8,
                    chartGraph: 0,
                    chartStroke: 1
                },
                i.options = {
                    controlPosition: gr.enum_.controlLayout.auto,
                    reportFitWidth: !1,
                    reportFitHeight: !1,
                    detailgridResize: gr.enum_.detailgridResize.asDesign,
                    singleChartFill: !0,
                    fixedHeaderFooter: !1,
                    fixedFitTo: "window",
                    selectionHighlight: !0,
                    selectionCell: !1,
                    selectionTextColor: "rgba(255,255,255,1)",
                    selectionBackColor: "rgba(20,20,127,1)",
                    hoverEnabled: !0,
                    hoverBackColor: "#eee",
                    hoverTextColor: "#000"
                },
                i.attachOptions(n),
                i.report = new gr.dom.Report(i)
        };
        F.prototype = {
            attachOptions: function(e) {
                var t = this;
                t.stop(),
                e && c(t.options, e)
            },
            loadReport: function(e, r, o) {
                var n, i = this, a = i.report, l = e;
                if (r = r || r === t,
                    o = o || o === t) {
                    try {
                        l.responseText !== t && (l = l.responseText),
                            i._rawReport = l,
                            a.load(l),
                        i.events.onReportLoaded && i.events.onReportLoaded(i)
                    } catch (s) {
                        o = 0,
                            n = "解析报表模板"
                    }
                    o && (i.reportPrepared = !0,
                    r && !i.waitData && i.generate())
                } else
                    n = "获取报表模板";
                !o && i.displayError(n, e, 1)
            },
            loadData: function(e, r, o) {
                var n, i = this, a = e;
                if (r = r || r === t,
                    o = o || o === t) {
                    try {
                        if (a.responseText !== t && (a = a.responseText),
                            i._rawData = a,
                        "string" == typeof a)
                            if ("" === a)
                                a = {};
                            else if ("{" === a.charAt(0))
                                a = JSON.parse(a);
                            else {
                                if (a = a.replace(/\r\n/g, ""),
                                    a = d(a),
                                    !a)
                                    throw 0;
                                a = h(a)
                            }
                        i.tables = a
                    } catch (l) {
                        o = 0,
                            n = "解析报表数据"
                    }
                    o && (i.dataPrepared = !0,
                    r && i.generate())
                } else
                    n = "获取报表数据";
                !o && i.displayError(n, e, 0)
            },
            start: function() {
                var t = this;
                t.waitData && (t.waitData = 0),
                t.generated || t.running || (t.running = !0,
                t.events.onGenerateBegin && t.events.onGenerateBegin(t),
                    t.reportPrepared = t.reportPrepared || !t._rs,
                    t.dataPrepared = t.dataPrepared || !t._ds,
                    t.reportPrepared && t.dataPrepared ? (t.events.onReportLoaded && t.events.onReportLoaded(t),
                    !t.waitData && t.generate()) : (t.reportPrepared || (t._rs.url ? e.rubylong.ajax(t._rs, function(e, r) {
                        t.loadReport(e, 1, r)
                    }, t) : t.loadReport(t._rs)),
                    t.dataPrepared || (t._ds.url ? e.rubylong.ajax(t._ds, function(e, r) {
                        t.loadData(e, 1, r)
                    }, t) : t.loadData(t._ds))))
            },
            stop: function(e, r) {
                var o, n = this, i = n.report;
                return n.running ? !1 : (n.generated && (e || (i.Clear(),
                    n.reportPrepared = !1,
                    n._rawReport = t),
                r || (n.tables = t,
                    n.dataPrepared = !1,
                    n._rawData = t),
                n.viewerHolderID && (o = document.getElementById(n.viewerHolderID),
                o && (o.innerHTML = "",
                DEBUG && (document.getElementById("htmlText") && (document.getElementById("htmlText").innerText = "htmlText"),
                document.getElementById("stylesText") && (document.getElementById("stylesText").innerText = "stylesText")))),
                    i.unprepare(),
                    n.generated = !1),
                    !0)
            },
            update: function() {
                var e = this;
                e.stop(1, 1),
                    e.generate()
            },
            generate: function() {
                function t() {
                    a.fontStyles = new T(a,v),
                        a.controlStyles = new T(a,m),
                        a.cellStyles = new T(a,g),
                        a.sectionStyles = new T(a,b),
                        a.positionStyles = new T(a,C),
                        a.customStyles = new T(a,x)
                }
                function r() {
                    function t(t) {
                        var r = e.innerWidth - document.body.clientWidth;
                        r || (r = 16),
                            o.setAttribute("style", "max-height:" + (e.innerHeight - i - r) + "px")
                    }
                    var r = u.getElementsByClassName(a._getCSSName(a.fixedDivClass))
                        , o = u.getElementsByClassName(a._getCSSName(a.bodyDivClass))[0]
                        , n = r[0]
                        , i = n ? n.clientHeight : 0
                        , l = n.clientWidth - o.clientWidth;
                    17 != l && l > 0 && (n && n.setAttribute("style", "padding-right:" + l + "px"),
                        n = r[1],
                    n && n.setAttribute("style", "padding-right:" + l + "px")),
                        n = r[1],
                    n && (i += n.clientHeight),
                        "holder" == c ? o.setAttribute("style", "max-height:" + (u.clientHeight - i) + "px") : (f(e, "resize", t),
                            t())
                }
                var o, i, a = this, l = a.report, c = a.options.fixedFitTo, u = document.getElementById(a.viewerHolderID);
                if (a.stop(1, 1),
                a.reportPrepared && a.dataPrepared) {
                    try {
                        if (u.innerHTML = "",
                        DEBUG && (a.stylesText = ""),
                            a._detailgrids = [],
                            a._domevents = [],
                            a.html = "",
                            t(),
                            u.classList.add(a.selectFont2(l.Font.font)),
                        16777215 !== l.BackColor && (u.style.backgroundColor = s(l.BackColor)),
                            a.html = l.generateHtml(),
                            a.generateStyles(),
                            u.innerHTML = a.html,
                        l.toFillHolder && (u.style.height = "100%"),
                        l.singleChart && (u.style.backgroundColor = s(l.singleChart.BackColor)),
                            l.startCanvas(),
                        l.fixedHeaderFooter && r(),
                            a._detailgrids.forEach(function(e, t) {
                                var r = document.getElementById(a._getDetailGridID(t));
                                f(r, "mousedown", function(t) {
                                    e.onmousedown(t)
                                })
                            }),
                            l._has_cb)
                            for (o = document.querySelectorAll("." + n.CSS_CB),
                                     i = o.length; i-- > 0; )
                                f(o[i], "click", function(e) {
                                    l.onCheckBoxClick(e)
                                });
                        a._domevents.forEach(function(e, t) {
                            for (o = document.querySelectorAll("." + a._getEventCSSName(t + 1)),
                                     t = o.length; t-- > 0; )
                                switch (e.type) {
                                    case 1:
                                        f(o[t], "click", function(t) {
                                            e.fun(t, e.obj)
                                        });
                                        break;
                                    default:
                                        f(o[t], "dblclick", function(t) {
                                            e.fun(t, e.obj)
                                        })
                                }
                        }),
                        DEBUG && (document.getElementById("htmlText") && (document.getElementById("htmlText").innerText = a.html),
                        document.getElementById("stylesText") && (document.getElementById("stylesText").innerText = a.stylesText))
                    } catch (d) {
                        u.innerHTML = "生成报表时发生异常错误!<br />Error Code: " + d.number + "<br />Error Name: " + d.name + "<br />Error Message: " + d.message
                    }
                    a.running = !1,
                        a.generated = !0,
                    a.events.onGenerateEnd && a.events.onGenerateEnd(a)
                }
            },
            generateStyles: function() {
                function e(e) {
                    var t = ">tbody>tr";
                    e && (t += ">td"),
                        t += ":hover",
                        s += "." + n.CSS_DG + t + (i ? ":not(." + n.CSS_SH + ")" : "") + " {background-color:" + o.hoverBackColor + ";color:" + o.hoverTextColor + ";}\n"
                }
                var t = this
                    , r = t.report
                    , o = t.options
                    , i = o.selectionHighlight
                    , a = n.ID_REPORT_STYLE + t.viewerNo
                    , l = document.getElementById(a)
                    , s = r.toFillHolder ? "html,body{height:100%;}\n*{margin:0;padding:0;}" : "";
                l || (l = document.createElement("STYLE"),
                    l.id = a,
                    document.head.appendChild(l)),
                r._has_cb && (s += "." + n.CSS_CB + "{}\n"),
                    s += "." + n.CSS_DG + "{table-layout:fixed;}\n",
                o.hoverEnabled && (e(0),
                    e(1)),
                i && (s += "." + n.CSS_SH + "{ background-color:" + o.selectionBackColor + ";color:" + o.selectionTextColor + ";}\n"),
                    l.innerHTML = [t.fontStyles, t.positionStyles, t.sectionStyles, t.cellStyles, t.controlStyles, t.customStyles, t.getDomEventStyles(), s].join(""),
                DEBUG && (t.stylesText = l.innerHTML)
            },
            getDomEventStyles: function() {
                for (var e = this, t = e._domevents.length, r = ""; t > 0; )
                    r += "." + e._getEventCSSName(t--) + "{}\n";
                return r
            },
            addCustomStyle: function(e) {
                var t = this.customStyles.items
                    , r = new x(e);
                return r.index = t.length,
                    t.push(r),
                    r
            },
            selectCell: function(e) {
                return this.cellStyles.select(e, e.defaultStyle)
            },
            selectCellItem: function(e) {
                return this.cellStyles.selectItem(e)
            },
            selectControl: function(e) {
                return this.controlStyles.select(e, e.defaultStyle)
            },
            selectControlItem: function(e) {
                return this.controlStyles.selectItem(e)
            },
            selectSection: function(e) {
                return this.sectionStyles.select(e, e.defaultStyle)
            },
            selectSectionItem: function(e) {
                return this.sectionStyles.selectItem(e)
            },
            selectPosition: function(e) {
                return this.positionStyles.select(e, e.defaultPositionStyle)
            },
            selectPositionItem: function(e) {
                return this.positionStyles.selectItem(e)
            },
            selectFont: function(e, t) {
                return this.fontStyles.select(e, t)
            },
            selectFont2: function(e) {
                var t = this;
                return t._getCSSName(t.selectFontItem(e))
            },
            selectFontItem: function(e) {
                return this.fontStyles.selectItem(e)
            },
            displayError: function(e, r, o) {
                var i, a = this, l = r.responseText !== t, s = l ? o ? a._rs : a._ds : t, c = document.getElementById(a.viewerHolderID);
                i = '<div style="background-color:#bbffbb"><h3>' + e + "失败，锐浪HTML5报表(" + n.VERSION + ")</h3>",
                s && (i += '<div>URL: <a href="' + s.url + '">' + s.url + "</a></div>"),
                    l ? (i += "<div>HTTP Status: " + r.status + "</div><div>HTTP Status Text: " + r.statusText + "</div>",
                    r.responseText && (i += "<div>HTTP Response Text: <pre>" + r.responseText + "</pre></div>")) : i += "string" == typeof r ? "<div>Loaded Text: <pre>" + r + "</pre></div>" : "<div>Loaded Json Object: <pre>" + JSON.stringify(r) + "</pre></div>",
                    i += "</div>",
                    c.innerHTML = i
            },
            _getEventCSSName: function(e) {
                var t = this.viewerNo
                    , r = "_grec" + e;
                return t && (r += "-" + t),
                    r
            },
            _getCSSName: function(e) {
                var t = this.viewerNo
                    , r = e.getStyleName();
                return t && (r += "-" + t),
                    r
            },
            _getDetailGridID: function(e) {
                var t = this.viewerNo
                    , r = n.ID_DETAILGRID + e;
                return t && (r += "-" + t),
                    r
            },
            get reportURL() {
                var e = this._rs;
                return e ? e.url : ""
            },
            set reportURL(e) {
                var t = this;
                t._rs = {},
                    t._rs.url = e
            },
            get dataURL() {
                var e = this._ds;
                return e ? e.url : ""
            },
            set dataURL(e) {
                var t = this;
                t._ds = {},
                    t._ds.url = e
            },
            get reportSource() {
                return this._rs
            },
            set reportSource(e) {
                var t = this;
                e && (t._rs = {},
                    S(e) ? t._rs.url = e : e.url ? c(t._rs, e) : t._rs = e)
            },
            get dataSource() {
                return this._ds
            },
            set dataSource(e) {
                var t = this;
                e && (t._ds = {},
                    y(e) ? t._ds.url = e : e.url ? c(t._ds, e) : t._ds = e)
            },
            get reportText() {
                var e = this._rawReport;
                return "string" != typeof e && (e = JSON.stringify(e)),
                    e
            },
            get dataText() {
                var e = this._rawData;
                return "string" != typeof e && (e = JSON.stringify(e)),
                    e
            }
        },
            e.rubylong = {
                grhtml5: {
                    controlLayout: gr.enum_.controlLayout,
                    detailgridResize: gr.enum_.detailgridResize,
                    barcodeURL: "/html5/General/Barcode.ashx",
                    Viewer: F,
                    helper: r,
                    utility: gr.utility,
                    holderIDs: [],
                    insertReportViewer: function(t, r, o, n) {
                        var i = e.rubylong.grhtml5.holderIDs
                            , a = i.indexOf(t);
                        return 0 > a && (a = i.length,
                            i.push(t)),
                            new F(a,t,r,o,n)
                    },
                    createReport: function() {
                        return new gr.dom.Report
                    }
                },
                ajax: function(e, t, r, o) {
                    function n(e, t) {
                        return t ? t : /.grf|.txt|.xml|.json/.test(e) ? "GET" : "POST"
                    }
                    var i, a = new XMLHttpRequest;
                    if (a.onreadystatechange = function() {
                        4 == a.readyState && a.status > 0 && t.call(r, a, 200 == a.status)
                    }
                        ,
                        a.onerror = function() {
                            t.call(r, a, 0)
                        }
                        ,
                    "string" == typeof e && (e = {
                        url: e
                    }),
                    e.method || (e.method = n(e.url, o)),
                        a.open(e.method, e.url, !0),
                        i = e.headers,
                    i && "object" == typeof i)
                        for (var l in i)
                            a.setRequestHeader(l, i[l]);
                    a.send(e.data)
                }
            },
        "classList"in document.documentElement || Object.defineProperty(HTMLElement.prototype, "classList", {
            get: function() {
                function e(e) {
                    return function(r) {
                        var o = t.className.split(/\s+/g)
                            , n = o.indexOf(r);
                        e(o, n, r),
                            t.className = o.join(" ")
                    }
                }
                var t = this;
                return {
                    add: e(function(e, t, r) {
                        ~t || e.push(r)
                    }),
                    remove: e(function(e, t) {
                        ~t && e.splice(t, 1)
                    }),
                    toggle: e(function(e, t, r) {
                        ~t ? e.splice(t, 1) : e.push(r)
                    }),
                    contains: function(e) {
                        return !!~t.className.split(/\s+/g).indexOf(e)
                    },
                    item: function(e) {
                        return t.className.split(/\s+/g)[e] || null
                    }
                }
            }
        })
    }(window);
