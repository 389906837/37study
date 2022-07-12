// (c) 2010-2013 Thomas Fuchs

// Zepto.js may be freely distributed under the MIT license.

// Copyright 2006 Google Inc.

//   http://www.apache.org/licenses/LICENSE-2.0

/*!
 * ZRender, a lightweight canvas library with a MVC architecture, data-driven 
 * and provides an event model like DOM.
 *  
 * Copyright (c) 2013, Baidu Inc.
 * All rights reserved.
 * 
 * LICENSE
 * https://github.com/ecomfe/zrender/blob/master/LICENSE.txt
 */

/*!
 * ECharts, a javascript interactive chart library.
 *  
 * Copyright (c) 2013, Baidu Inc.
 * All rights reserved.
 * 
 * LICENSE
 * https://github.com/ecomfe/echarts/blob/master/LICENSE.txt
 */

define("zrender/tool/env", [], function () {
    function e(e) {
        var t = this.os = {}, n = this.browser = {}, r = e.match(/Web[kK]it[\/]{0,1}([\d.]+)/),
            i = e.match(/(Android);?[\s\/]+([\d.]+)?/), s = e.match(/(iPad).*OS\s([\d_]+)/),
            o = e.match(/(iPod)(.*OS\s([\d_]+))?/), u = !s && e.match(/(iPhone\sOS)\s([\d_]+)/),
            a = e.match(/(webOS|hpwOS)[\s\/]([\d.]+)/), f = a && e.match(/TouchPad/), l = e.match(/Kindle\/([\d.]+)/),
            c = e.match(/Silk\/([\d._]+)/), h = e.match(/(BlackBerry).*Version\/([\d.]+)/),
            p = e.match(/(BB10).*Version\/([\d.]+)/), d = e.match(/(RIM\sTablet\sOS)\s([\d.]+)/),
            v = e.match(/PlayBook/), m = e.match(/Chrome\/([\d.]+)/) || e.match(/CriOS\/([\d.]+)/),
            g = e.match(/Firefox\/([\d.]+)/), y = e.match(/MSIE ([\d.]+)/), b = r && e.match(/Mobile\//) && !m,
            w = e.match(/(iPhone|iPod|iPad).*AppleWebKit(?!.*Safari)/) && !m, y = e.match(/MSIE\s([\d.]+)/);
        if (n.webkit = !!r) n.version = r[1];
        return i && (t.android = !0, t.version = i[2]), u && !o && (t.ios = t.iphone = !0, t.version = u[2].replace(/_/g, ".")), s && (t.ios = t.ipad = !0, t.version = s[2].replace(/_/g, ".")), o && (t.ios = t.ipod = !0, t.version = o[3] ? o[3].replace(/_/g, ".") : null), a && (t.webos = !0, t.version = a[2]), f && (t.touchpad = !0), h && (t.blackberry = !0, t.version = h[2]), p && (t.bb10 = !0, t.version = p[2]), d && (t.rimtabletos = !0, t.version = d[2]), v && (n.playbook = !0), l && (t.kindle = !0, t.version = l[1]), c && (n.silk = !0, n.version = c[1]), !c && t.android && e.match(/Kindle Fire/) && (n.silk = !0), m && (n.chrome = !0, n.version = m[1]), g && (n.firefox = !0, n.version = g[1]), y && (n.ie = !0, n.version = y[1]), b && (e.match(/Safari/) || !!t.ios) && (n.safari = !0), w && (n.webview = !0), y && (n.ie = !0, n.version = y[1]), t.tablet = !!(s || v || i && !e.match(/Mobile/) || g && e.match(/Tablet/) || y && !e.match(/Phone/) && e.match(/Touch/)), t.phone = !!(!t.tablet && !t.ipod && (i || u || a || h || p || m && e.match(/Android/) || m && e.match(/CriOS\/([\d.]+)/) || g && e.match(/Mobile/) || y && e.match(/Touch/))), {
            browser: n,
            os: t,
            canvasSupported: document.createElement("canvas").getContext ? !0 : !1
        }
    }

    return e(navigator.userAgent)
}), define("echarts/config", [], function () {
    var e = {
        CHART_TYPE_LINE: "line",
        CHART_TYPE_BAR: "bar",
        CHART_TYPE_SCATTER: "scatter",
        CHART_TYPE_PIE: "pie",
        CHART_TYPE_RADAR: "radar",
        CHART_TYPE_MAP: "map",
        CHART_TYPE_K: "k",
        CHART_TYPE_ISLAND: "island",
        CHART_TYPE_FORCE: "force",
        CHART_TYPE_CHORD: "chord",
        COMPONENT_TYPE_TITLE: "title",
        COMPONENT_TYPE_LEGEND: "legend",
        COMPONENT_TYPE_DATARANGE: "dataRange",
        COMPONENT_TYPE_DATAVIEW: "dataView",
        COMPONENT_TYPE_DATAZOOM: "dataZoom",
        COMPONENT_TYPE_TOOLBOX: "toolbox",
        COMPONENT_TYPE_TOOLTIP: "tooltip",
        COMPONENT_TYPE_GRID: "grid",
        COMPONENT_TYPE_AXIS: "axis",
        COMPONENT_TYPE_POLAR: "polar",
        COMPONENT_TYPE_X_AXIS: "xAxis",
        COMPONENT_TYPE_Y_AXIS: "yAxis",
        COMPONENT_TYPE_AXIS_CATEGORY: "categoryAxis",
        COMPONENT_TYPE_AXIS_VALUE: "valueAxis",
        color: ["#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"],
        title: {
            text: "",
            subtext: "",
            x: "left",
            y: "top",
            backgroundColor: "rgba(0,0,0,0)",
            borderColor: "#ccc",
            borderWidth: 0,
            padding: 5,
            itemGap: 10,
            textStyle: {fontSize: 18, fontWeight: "bolder", color: "#333"},
            subtextStyle: {color: "#aaa"}
        },
        legend: {
            orient: "horizontal",
            x: "center",
            y: "top",
            backgroundColor: "rgba(0,0,0,0)",
            borderColor: "#ccc",
            borderWidth: 0,
            padding: 5,
            itemGap: 10,
            itemWidth: 20,
            itemHeight: 14,
            textStyle: {color: "#333"},
            selectedMode: !0
        },
        dataRange: {
            orient: "vertical",
            x: "left",
            y: "bottom",
            backgroundColor: "rgba(0,0,0,0)",
            borderColor: "#ccc",
            borderWidth: 0,
            padding: 5,
            itemGap: 10,
            itemWidth: 20,
            itemHeight: 14,
            precision: 0,
            splitNumber: 5,
            calculable: !1,
            realtime: !0,
            color: ["#006edd", "#e0ffff"],
            textStyle: {color: "#333"}
        },
        toolbox: {
            show: !1,
            orient: "horizontal",
            x: "right",
            y: "top",
            color: ["#1e90ff", "#22bb22", "#4b0082", "#d2691e"],
            backgroundColor: "rgba(0,0,0,0)",
            borderColor: "#ccc",
            borderWidth: 0,
            padding: 5,
            itemGap: 10,
            itemSize: 16,
            showTitle: !0,
            feature: {
                mark: {
                    show: !1,
                    title: {mark: "辅助线开关", markUndo: "删除辅助线", markClear: "清空辅助线"},
                    lineStyle: {width: 1, color: "#1e90ff", type: "dashed"}
                },
                dataZoom: {show: !1, title: {dataZoom: "区域缩放", dataZoomReset: "区域缩放后退"}},
                dataView: {show: !1, title: "数据视图", readOnly: !1, lang: ["Data View", "close", "refresh"]},
                magicType: {show: !1, title: {line: "折线图切换", bar: "柱形图切换", stack: "堆叠", tiled: "平铺"}, type: []},
                restore: {show: !1, title: "还原"},
                saveAsImage: {show: !1, title: "保存为图片", type: "png", lang: ["点击保存"]}
            }
        },
        tooltip: {
            show: !0,
            showContent: !0,
            trigger: "item",
            islandFormatter: "{a} <br/>{b} : {c}",
            showDelay: 20,
            hideDelay: 100,
            transitionDuration: .4,
            backgroundColor: "rgba(0,0,0,0.7)",
            borderColor: "#333",
            borderRadius: 4,
            borderWidth: 0,
            padding: 5,
            axisPointer: {
                type: "line",
                lineStyle: {color: "#48b", width: 2, type: "solid"},
                areaStyle: {size: "auto", color: "rgba(150,150,150,0.3)"}
            },
            textStyle: {color: "#fff"}
        },
        dataZoom: {
            show: !1,
            orient: "horizontal",
            backgroundColor: "rgba(0,0,0,0)",
            dataBackgroundColor: "#eee",
            fillerColor: "rgba(144,197,237,0.2)",
            handleColor: "rgba(70,130,180,0.8)",
            realtime: !0
        },
        grid: {x: 80, y: 60, x2: 80, y2: 60, backgroundColor: "rgba(0,0,0,0)", borderWidth: 1, borderColor: "#ccc"},
        categoryAxis: {
            position: "bottom",
            name: "",
            nameLocation: "end",
            nameTextStyle: {},
            boundaryGap: !0,
            axisLine: {show: !0, lineStyle: {color: "#48b", width: 2, type: "solid"}},
            axisTick: {show: !0, interval: "auto", inside: !1, length: 5, lineStyle: {color: "#333", width: 1}},
            axisLabel: {show: !0, interval: "auto", rotate: 0, margin: 8, textStyle: {color: "#333"}},
            splitLine: {show: !0, lineStyle: {color: ["#ccc"], width: 1, type: "solid"}},
            splitArea: {show: !1, areaStyle: {color: ["rgba(250,250,250,0.3)", "rgba(200,200,200,0.3)"]}}
        },
        valueAxis: {
            position: "left",
            name: "",
            nameLocation: "end",
            nameTextStyle: {},
            boundaryGap: [0, 0],
            precision: 0,
            power: 100,
            splitNumber: 5,
            axisLine: {show: !0, lineStyle: {color: "#48b", width: 2, type: "solid"}},
            axisTick: {show: !1, inside: !1, length: 5, lineStyle: {color: "#333", width: 1}},
            axisLabel: {show: !0, rotate: 0, margin: 8, textStyle: {color: "#333"}},
            splitLine: {show: !0, lineStyle: {color: ["#ccc"], width: 1, type: "solid"}},
            splitArea: {show: !1, areaStyle: {color: ["rgba(250,250,250,0.3)", "rgba(200,200,200,0.3)"]}}
        },
        polar: {
            center: ["50%", "50%"],
            radius: "75%",
            startAngle: 90,
            splitNumber: 5,
            name: {show: !0, textStyle: {color: "#333"}},
            axisLine: {show: !0, lineStyle: {color: "#ccc", width: 1, type: "solid"}},
            axisLabel: {show: !1, textStyle: {color: "#333"}},
            splitArea: {show: !0, areaStyle: {color: ["rgba(250,250,250,0.3)", "rgba(200,200,200,0.3)"]}},
            splitLine: {show: !0, lineStyle: {width: 1, color: "#ccc"}}
        },
        bar: {
            xAxisIndex: 0,
            yAxisIndex: 0,
            barMinHeight: 0,
            barGap: "30%",
            barCategoryGap: "20%",
            itemStyle: {
                normal: {borderColor: "#fff", borderRadius: 0, borderWidth: 0, label: {show: !1}},
                emphasis: {borderColor: "#fff", borderRadius: 0, borderWidth: 0, label: {show: !1}}
            }
        },
        line: {
            xAxisIndex: 0,
            yAxisIndex: 0,
            itemStyle: {
                normal: {
                    label: {show: !1},
                    lineStyle: {
                        width: 2,
                        type: "solid",
                        shadowColor: "rgba(0,0,0,0)",
                        shadowBlur: 0,
                        shadowOffsetX: 0,
                        shadowOffsetY: 0
                    }
                }, emphasis: {label: {show: !1}}
            },
            symbolSize: 2,
            showAllSymbol: !1
        },
        k: {
            xAxisIndex: 0,
            yAxisIndex: 0,
            itemStyle: {
                normal: {
                    color: "#fff",
                    color0: "#00aa11",
                    lineStyle: {width: 1, color: "#ff3200", color0: "#00aa11"}
                }, emphasis: {}
            }
        },
        scatter: {
            xAxisIndex: 0,
            yAxisIndex: 0,
            symbolSize: 4,
            large: !1,
            largeThreshold: 2e3,
            itemStyle: {
                normal: {
                    label: {
                        show: !1, formatter: function (e, t, n) {
                            return typeof n[2] != "undefined" ? n[2] : n[0] + " , " + n[1]
                        }
                    }
                }, emphasis: {
                    label: {
                        show: !1, formatter: function (e, t, n) {
                            return typeof n[2] != "undefined" ? n[2] : n[0] + " , " + n[1]
                        }
                    }
                }
            }
        },
        radar: {
            polarIndex: 0,
            itemStyle: {
                normal: {label: {show: !1}, lineStyle: {width: 2, type: "solid"}},
                emphasis: {label: {show: !1}}
            },
            symbolSize: 2
        },
        pie: {
            center: ["50%", "50%"],
            radius: [0, "75%"],
            clockWise: !0,
            startAngle: 90,
            minAngle: 0,
            selectedOffset: 10,
            itemStyle: {
                normal: {
                    borderColor: "#fff",
                    borderWidth: 1,
                    label: {show: !0, position: "outer"},
                    labelLine: {show: !0, length: 20, lineStyle: {width: 1, type: "solid"}}
                },
                emphasis: {
                    borderColor: "rgba(0,0,0,0)",
                    borderWidth: 1,
                    label: {show: !1},
                    labelLine: {show: !1, length: 20, lineStyle: {width: 1, type: "solid"}}
                }
            }
        },
        map: {
            mapType: "china",
            mapLocation: {x: "center", y: "center"},
            mapValuePrecision: 0,
            showLegendSymbol: !0,
            hoverable: !0,
            itemStyle: {
                normal: {
                    borderColor: "#fff",
                    borderWidth: 1,
                    areaStyle: {color: "#ccc"},
                    label: {show: !1, textStyle: {color: "rgb(139,69,19)"}}
                },
                emphasis: {
                    borderColor: "rgba(0,0,0,0)",
                    borderWidth: 1,
                    areaStyle: {color: "rgba(255,215,0,0.8)"},
                    label: {show: !1, textStyle: {color: "rgb(100,0,0)"}}
                }
            }
        },
        force: {
            minRadius: 10,
            maxRadius: 20,
            density: 1,
            attractiveness: 1,
            initSize: 300,
            centripetal: 1,
            coolDown: .99,
            categories: [],
            itemStyle: {
                normal: {
                    label: {show: !1},
                    nodeStyle: {brushType: "both", color: "#f08c2e", strokeColor: "#5182ab"},
                    linkStyle: {strokeColor: "#5182ab"}
                }, emphasis: {label: {show: !1}, nodeStyle: {}, linkStyle: {}}
            }
        },
        chord: {
            radius: ["65%", "75%"],
            center: ["50%", "50%"],
            padding: 2,
            sort: "none",
            sortSub: "none",
            startAngle: 90,
            clockWise: !1,
            showScale: !1,
            showScaleText: !1,
            itemStyle: {
                normal: {
                    label: {show: !0},
                    lineStyle: {width: 0, color: "#000"},
                    chordStyle: {lineStyle: {width: 1, color: "#666"}}
                }, emphasis: {lineStyle: {width: 0, color: "#000"}, chordStyle: {lineStyle: {width: 2, color: "#333"}}}
            },
            matrix: []
        },
        island: {r: 15, calculateStep: .1},
        markPoint: {
            symbol: "pin",
            symbolSize: 10,
            effect: {show: !1, period: 15, scaleSize: 2},
            itemStyle: {normal: {borderWidth: 2, label: {show: !0, position: "inside"}}, emphasis: {label: {show: !0}}}
        },
        markLine: {
            symbol: ["circle", "arrow"],
            symbolSize: [2, 4],
            effect: {show: !1, period: 15, scaleSize: 2},
            itemStyle: {
                normal: {
                    borderWidth: 1.5,
                    label: {show: !0, position: "end"},
                    lineStyle: {
                        type: "dashed",
                        shadowColor: "rgba(0,0,0,0)",
                        shadowBlur: 0,
                        shadowOffsetX: 0,
                        shadowOffsetY: 0
                    }
                }, emphasis: {label: {show: !1}, lineStyle: {}}
            }
        },
        textStyle: {
            decoration: "none",
            fontFamily: "Arial, Verdana, sans-serif",
            fontFamily2: "微软雅黑",
            fontSize: 12,
            fontStyle: "normal",
            fontWeight: "normal"
        },
        EVENT: {
            REFRESH: "refresh",
            RESTORE: "restore",
            RESIZE: "resize",
            CLICK: "click",
            HOVER: "hover",
            DATA_CHANGED: "dataChanged",
            DATA_ZOOM: "dataZoom",
            DATA_RANGE: "dataRange",
            LEGEND_SELECTED: "legendSelected",
            MAP_SELECTED: "mapSelected",
            PIE_SELECTED: "pieSelected",
            MAGIC_TYPE_CHANGED: "magicTypeChanged",
            DATA_VIEW_CHANGED: "dataViewChanged",
            MAP_ROAM: "mapRoam",
            TOOLTIP_HOVER: "tooltipHover",
            TOOLTIP_IN_GRID: "tooltipInGrid",
            TOOLTIP_OUT_GRID: "tooltipOutGrid"
        },
        DRAG_ENABLE_TIME: 150,
        symbolList: ["circle", "rectangle", "triangle", "diamond", "emptyCircle", "emptyRectangle", "emptyTriangle", "emptyDiamond"],
        loadingText: "Loading...",
        calculable: !1,
        calculableColor: "rgba(255,165,0,0.6)",
        calculableHolderColor: "#ccc",
        nameConnector: " & ",
        valueConnector: " : ",
        animation: !0,
        addDataAnimation: !0,
        animationThreshold: 2500,
        animationDuration: 2e3,
        animationEasing: "ExponentialOut"
    };
    return e
}), define("zrender/tool/vector", [], function () {
    var e = typeof Float32Array == "undefined" ? Array : Float32Array, t = {
        create: function (t, n) {
            var r = new e(2);
            return r[0] = t || 0, r[1] = n || 0, r
        }, copy: function (e, t) {
            e[0] = t[0], e[1] = t[1]
        }, set: function (e, t, n) {
            e[0] = t, e[1] = n
        }, add: function (e, t, n) {
            return e[0] = t[0] + n[0], e[1] = t[1] + n[1], e
        }, scaleAndAdd: function (e, t, n, r) {
            return e[0] = t[0] + n[0] * r, e[1] = t[1] + n[1] * r, e
        }, sub: function (e, t, n) {
            return e[0] = t[0] - n[0], e[1] = t[1] - n[1], e
        }, length: function (e) {
            return Math.sqrt(this.lengthSquare(e))
        }, lengthSquare: function (e) {
            return e[0] * e[0] + e[1] * e[1]
        }, mul: function (e, t, n) {
            return e[0] = t[0] * n[0], e[1] = t[1] * n[1], e
        }, dot: function (e, t) {
            return e[0] * t[0] + e[1] * t[1]
        }, scale: function (e, t, n) {
            return e[0] = t[0] * n, e[1] = t[1] * n, e
        }, normalize: function (e, n) {
            var r = t.length(n);
            return r === 0 ? (e[0] = 0, e[1] = 0) : (e[0] = n[0] / r, e[1] = n[1] / r), e
        }, distance: function (e, t) {
            return Math.sqrt((e[0] - t[0]) * (e[0] - t[0]) + (e[1] - t[1]) * (e[1] - t[1]))
        }, negate: function (e, t) {
            e[0] = -t[0], e[1] = -t[1]
        }, middle: function (e, t, n) {
            return e[0] = (t[0] + n[0]) / 2, e[1] = (t[1] + n[1]) / 2, e
        }
    };
    return t
}), define("zrender/lib/excanvas", ["require"], function (e) {
    return document.createElement("canvas").getContext ? G_vmlCanvasManager = !1 : function () {
        function f() {
            return this.context_ || (this.context_ = new I(this))
        }

        function c(e, t, n) {
            var r = l.call(arguments, 2);
            return function () {
                return e.apply(t, r.concat(l.call(arguments)))
            }
        }

        function h(e) {
            return String(e).replace(/&/g, "&amp;").replace(/"/g, "&quot;")
        }

        function p(e, t, n) {
            e.namespaces[t] || e.namespaces.add(t, n, "#default#VML")
        }

        function d(e) {
            p(e, "g_vml_", "urn:schemas-microsoft-com:vml"), p(e, "g_o_", "urn:schemas-microsoft-com:office:office");
            if (!e.styleSheets.ex_canvas_) {
                var t = e.createStyleSheet();
                t.owningElement.id = "ex_canvas_", t.cssText = "canvas{display:inline-block;overflow:hidden;text-align:left;width:300px;height:150px}"
            }
        }

        function m(e) {
            var t = e.srcElement;
            switch (e.propertyName) {
                case"width":
                    t.getContext().clearRect(), t.style.width = t.attributes.width.nodeValue + "px", t.firstChild.style.width = t.clientWidth + "px";
                    break;
                case"height":
                    t.getContext().clearRect(), t.style.height = t.attributes.height.nodeValue + "px", t.firstChild.style.height = t.clientHeight + "px"
            }
        }

        function g(e) {
            var t = e.srcElement;
            t.firstChild && (t.firstChild.style.width = t.clientWidth + "px", t.firstChild.style.height = t.clientHeight + "px")
        }

        function E() {
            return [[1, 0, 0], [0, 1, 0], [0, 0, 1]]
        }

        function S(e, t) {
            var n = E();
            for (var r = 0; r < 3; r++)for (var i = 0; i < 3; i++) {
                var s = 0;
                for (var o = 0; o < 3; o++)s += e[r][o] * t[o][i];
                n[r][i] = s
            }
            return n
        }

        function x(e, t) {
            t.fillStyle = e.fillStyle, t.lineCap = e.lineCap, t.lineJoin = e.lineJoin, t.lineWidth = e.lineWidth, t.miterLimit = e.miterLimit, t.shadowBlur = e.shadowBlur, t.shadowColor = e.shadowColor, t.shadowOffsetX = e.shadowOffsetX, t.shadowOffsetY = e.shadowOffsetY, t.strokeStyle = e.strokeStyle, t.globalAlpha = e.globalAlpha, t.font = e.font, t.textAlign = e.textAlign, t.textBaseline = e.textBaseline, t.arcScaleX_ = e.arcScaleX_, t.arcScaleY_ = e.arcScaleY_, t.lineScale_ = e.lineScale_
        }

        function N(e) {
            var t = e.indexOf("(", 3), n = e.indexOf(")", t + 1), r = e.substring(t + 1, n).split(",");
            if (r.length != 4 || e.charAt(3) != "a") r[3] = 1;
            return r
        }

        function C(e) {
            return parseFloat(e) / 100
        }

        function k(e, t, n) {
            return Math.min(n, Math.max(t, e))
        }

        function L(e) {
            var t, n, r, i, s, o;
            i = parseFloat(e[0]) / 360 % 360, i < 0 && i++, s = k(C(e[1]), 0, 1), o = k(C(e[2]), 0, 1);
            if (s == 0) t = n = r = o; else {
                var u = o < .5 ? o * (1 + s) : o + s - o * s, a = 2 * o - u;
                t = A(a, u, i + 1 / 3), n = A(a, u, i), r = A(a, u, i - 1 / 3)
            }
            return "#" + y[Math.floor(t * 255)] + y[Math.floor(n * 255)] + y[Math.floor(r * 255)]
        }

        function A(e, t, n) {
            return n < 0 && n++, n > 1 && n--, 6 * n < 1 ? e + (t - e) * 6 * n : 2 * n < 1 ? t : 3 * n < 2 ? e + (t - e) * (2 / 3 - n) * 6 : e
        }

        function M(e) {
            if (e in O)return O[e];
            var t, n = 1;
            e = String(e);
            if (e.charAt(0) == "#") t = e; else if (/^rgb/.test(e)) {
                var r = N(e), t = "#", i;
                for (var s = 0; s < 3; s++)r[s].indexOf("%") != -1 ? i = Math.floor(C(r[s]) * 255) : i = +r[s], t += y[k(i, 0, 255)];
                n = +r[3]
            } else if (/^hsl/.test(e)) {
                var r = N(e);
                t = L(r), n = r[3]
            } else t = T[e] || e;
            return O[e] = {color: t, alpha: n}
        }

        function P(e) {
            if (D[e])return D[e];
            var t = document.createElement("div"), n = t.style, r;
            try {
                n.font = e, r = n.fontFamily.split(",")[0]
            } catch (i) {
            }
            return D[e] = {
                style: n.fontStyle || _.style,
                variant: n.fontVariant || _.variant,
                weight: n.fontWeight || _.weight,
                size: n.fontSize || _.size,
                family: r || _.family
            }
        }

        function H(e, t) {
            var n = {};
            for (var r in e)n[r] = e[r];
            var i = parseFloat(t.currentStyle.fontSize), s = parseFloat(e.size);
            return typeof e.size == "number" ? n.size = e.size : e.size.indexOf("px") != -1 ? n.size = s : e.size.indexOf("em") != -1 ? n.size = i * s : e.size.indexOf("%") != -1 ? n.size = i / 100 * s : e.size.indexOf("pt") != -1 ? n.size = s / .75 : n.size = i, n
        }

        function B(e) {
            return e.style + " " + e.variant + " " + e.weight + " " + e.size + "px '" + e.family + "'"
        }

        function F(e) {
            return j[e] || "square"
        }

        function I(e) {
            this.m_ = E(), this.mStack_ = [], this.aStack_ = [], this.currentPath_ = [], this.strokeStyle = "#000", this.fillStyle = "#000", this.lineWidth = 1, this.lineJoin = "miter", this.lineCap = "butt", this.miterLimit = o * 1, this.globalAlpha = 1, this.font = "12px 微软雅黑", this.textAlign = "left", this.textBaseline = "alphabetic", this.canvas = e;
            var t = "width:" + e.clientWidth + "px;height:" + e.clientHeight + "px;overflow:hidden;position:absolute",
                n = e.ownerDocument.createElement("div");
            n.style.cssText = t, e.appendChild(n);
            var r = n.cloneNode(!1);
            r.style.backgroundColor = "#fff", r.style.filter = "alpha(opacity=0)", e.appendChild(r), this.element_ = n, this.arcScaleX_ = 1, this.arcScaleY_ = 1, this.lineScale_ = 1
        }

        function R(e, t, n, r) {
            e.currentPath_.push({
                type: "bezierCurveTo",
                cp1x: t.x,
                cp1y: t.y,
                cp2x: n.x,
                cp2y: n.y,
                x: r.x,
                y: r.y
            }), e.currentX_ = r.x, e.currentY_ = r.y
        }

        function U(e, t) {
            var n = M(e.strokeStyle), r = n.color, i = n.alpha * e.globalAlpha, s = e.lineScale_ * e.lineWidth;
            s < 1 && (i *= s), t.push("<g_vml_:stroke", ' opacity="', i, '"', ' joinstyle="', e.lineJoin, '"', ' miterlimit="', e.miterLimit, '"', ' endcap="', F(e.lineCap), '"', ' weight="', s, 'px"', ' color="', r, '" />')
        }

        function z(t, n, r, i) {
            var s = t.fillStyle, u = t.arcScaleX_, a = t.arcScaleY_, f = i.x - r.x, l = i.y - r.y;
            if (s instanceof $) {
                var c = 0, h = {x: 0, y: 0}, p = 0, d = 1;
                if (s.type_ == "gradient") {
                    var v = s.x0_ / u, m = s.y0_ / a, g = s.x1_ / u, y = s.y1_ / a, b = W(t, v, m), w = W(t, g, y),
                        E = w.x - b.x, S = w.y - b.y;
                    c = Math.atan2(E, S) * 180 / Math.PI, c < 0 && (c += 360), c < 1e-6 && (c = 0)
                } else {
                    var b = W(t, s.x0_, s.y0_);
                    h = {x: (b.x - r.x) / f, y: (b.y - r.y) / l}, f /= u * o, l /= a * o;
                    var x = e.max(f, l);
                    p = 2 * s.r0_ / x, d = 2 * s.r1_ / x - p
                }
                var T = s.colors_;
                T.sort(function (e, t) {
                    return e.offset - t.offset
                });
                var N = T.length, C = T[0].color, k = T[N - 1].color, L = T[0].alpha * t.globalAlpha,
                    A = T[N - 1].alpha * t.globalAlpha, O = [];
                for (var _ = 0; _ < N; _++) {
                    var D = T[_];
                    O.push(D.offset * d + p + " " + D.color)
                }
                n.push('<g_vml_:fill type="', s.type_, '"', ' method="none" focus="100%"', ' color="', C, '"', ' color2="', k, '"', ' colors="', O.join(","), '"', ' opacity="', A, '"', ' g_o_:opacity2="', L, '"', ' angle="', c, '"', ' focusposition="', h.x, ",", h.y, '" />')
            } else if (s instanceof J) {
                if (f && l) {
                    var P = -r.x, H = -r.y;
                    n.push("<g_vml_:fill", ' position="', P / f * u * u, ",", H / l * a * a, '"', ' type="tile"', ' src="', s.src_, '" />')
                }
            } else {
                var B = M(t.fillStyle), j = B.color, F = B.alpha * t.globalAlpha;
                n.push('<g_vml_:fill color="', j, '" opacity="', F, '" />')
            }
        }

        function W(e, t, n) {
            var r = e.m_;
            return {x: o * (t * r[0][0] + n * r[1][0] + r[2][0]) - u, y: o * (t * r[0][1] + n * r[1][1] + r[2][1]) - u}
        }

        function X(e) {
            return isFinite(e[0][0]) && isFinite(e[0][1]) && isFinite(e[1][0]) && isFinite(e[1][1]) && isFinite(e[2][0]) && isFinite(e[2][1])
        }

        function V(e, t, n) {
            if (!X(t))return;
            e.m_ = t;
            if (n) {
                var r = t[0][0] * t[1][1] - t[0][1] * t[1][0];
                e.lineScale_ = s(i(r))
            }
        }

        function $(e) {
            this.type_ = e, this.x0_ = 0, this.y0_ = 0, this.r0_ = 0, this.x1_ = 0, this.y1_ = 0, this.r1_ = 0, this.colors_ = []
        }

        function J(e, t) {
            Q(e);
            switch (t) {
                case"repeat":
                case null:
                case"":
                    this.repetition_ = "repeat";
                    break;
                case"repeat-x":
                case"repeat-y":
                case"no-repeat":
                    this.repetition_ = t;
                    break;
                default:
                    K("SYNTAX_ERR")
            }
            this.src_ = e.src, this.width_ = e.width, this.height_ = e.height
        }

        function K(e) {
            throw new G(e)
        }

        function Q(e) {
            (!e || e.nodeType != 1 || e.tagName != "IMG") && K("TYPE_MISMATCH_ERR"), e.readyState != "complete" && K("INVALID_STATE_ERR")
        }

        function G(e) {
            this.code = this[e], this.message = e + ": DOM Exception " + this.code
        }

        var e = Math, t = e.round, n = e.sin, r = e.cos, i = e.abs, s = e.sqrt, o = 10, u = o / 2,
            a = +navigator.userAgent.match(/MSIE ([\d.]+)?/)[1], l = Array.prototype.slice;
        d(document);
        var v = {
            init: function (e) {
                var t = e || document;
                t.createElement("canvas"), t.attachEvent("onreadystatechange", c(this.init_, this, t))
            }, init_: function (e) {
                var t = e.getElementsByTagName("canvas");
                for (var n = 0; n < t.length; n++)this.initElement(t[n])
            }, initElement: function (e) {
                if (!e.getContext) {
                    e.getContext = f, d(e.ownerDocument), e.innerHTML = "", e.attachEvent("onpropertychange", m), e.attachEvent("onresize", g);
                    var t = e.attributes;
                    t.width && t.width.specified ? e.style.width = t.width.nodeValue + "px" : e.width = e.clientWidth, t.height && t.height.specified ? e.style.height = t.height.nodeValue + "px" : e.height = e.clientHeight
                }
                return e
            }
        };
        v.init();
        var y = [];
        for (var b = 0; b < 16; b++)for (var w = 0; w < 16; w++)y[b * 16 + w] = b.toString(16) + w.toString(16);
        var T = {
                aliceblue: "#F0F8FF",
                antiquewhite: "#FAEBD7",
                aquamarine: "#7FFFD4",
                azure: "#F0FFFF",
                beige: "#F5F5DC",
                bisque: "#FFE4C4",
                black: "#000000",
                blanchedalmond: "#FFEBCD",
                blueviolet: "#8A2BE2",
                brown: "#A52A2A",
                burlywood: "#DEB887",
                cadetblue: "#5F9EA0",
                chartreuse: "#7FFF00",
                chocolate: "#D2691E",
                coral: "#FF7F50",
                cornflowerblue: "#6495ED",
                cornsilk: "#FFF8DC",
                crimson: "#DC143C",
                cyan: "#00FFFF",
                darkblue: "#00008B",
                darkcyan: "#008B8B",
                darkgoldenrod: "#B8860B",
                darkgray: "#A9A9A9",
                darkgreen: "#006400",
                darkgrey: "#A9A9A9",
                darkkhaki: "#BDB76B",
                darkmagenta: "#8B008B",
                darkolivegreen: "#556B2F",
                darkorange: "#FF8C00",
                darkorchid: "#9932CC",
                darkred: "#8B0000",
                darksalmon: "#E9967A",
                darkseagreen: "#8FBC8F",
                darkslateblue: "#483D8B",
                darkslategray: "#2F4F4F",
                darkslategrey: "#2F4F4F",
                darkturquoise: "#00CED1",
                darkviolet: "#9400D3",
                deeppink: "#FF1493",
                deepskyblue: "#00BFFF",
                dimgray: "#696969",
                dimgrey: "#696969",
                dodgerblue: "#1E90FF",
                firebrick: "#B22222",
                floralwhite: "#FFFAF0",
                forestgreen: "#228B22",
                gainsboro: "#DCDCDC",
                ghostwhite: "#F8F8FF",
                gold: "#FFD700",
                goldenrod: "#DAA520",
                grey: "#808080",
                greenyellow: "#ADFF2F",
                honeydew: "#F0FFF0",
                hotpink: "#FF69B4",
                indianred: "#CD5C5C",
                indigo: "#4B0082",
                ivory: "#FFFFF0",
                khaki: "#F0E68C",
                lavender: "#E6E6FA",
                lavenderblush: "#FFF0F5",
                lawngreen: "#7CFC00",
                lemonchiffon: "#FFFACD",
                lightblue: "#ADD8E6",
                lightcoral: "#F08080",
                lightcyan: "#E0FFFF",
                lightgoldenrodyellow: "#FAFAD2",
                lightgreen: "#90EE90",
                lightgrey: "#D3D3D3",
                lightpink: "#FFB6C1",
                lightsalmon: "#FFA07A",
                lightseagreen: "#20B2AA",
                lightskyblue: "#87CEFA",
                lightslategray: "#778899",
                lightslategrey: "#778899",
                lightsteelblue: "#B0C4DE",
                lightyellow: "#FFFFE0",
                limegreen: "#32CD32",
                linen: "#FAF0E6",
                magenta: "#FF00FF",
                mediumaquamarine: "#66CDAA",
                mediumblue: "#0000CD",
                mediumorchid: "#BA55D3",
                mediumpurple: "#9370DB",
                mediumseagreen: "#3CB371",
                mediumslateblue: "#7B68EE",
                mediumspringgreen: "#00FA9A",
                mediumturquoise: "#48D1CC",
                mediumvioletred: "#C71585",
                midnightblue: "#191970",
                mintcream: "#F5FFFA",
                mistyrose: "#FFE4E1",
                moccasin: "#FFE4B5",
                navajowhite: "#FFDEAD",
                oldlace: "#FDF5E6",
                olivedrab: "#6B8E23",
                orange: "#FFA500",
                orangered: "#FF4500",
                orchid: "#DA70D6",
                palegoldenrod: "#EEE8AA",
                palegreen: "#98FB98",
                paleturquoise: "#AFEEEE",
                palevioletred: "#DB7093",
                papayawhip: "#FFEFD5",
                peachpuff: "#FFDAB9",
                peru: "#CD853F",
                pink: "#FFC0CB",
                plum: "#DDA0DD",
                powderblue: "#B0E0E6",
                rosybrown: "#BC8F8F",
                royalblue: "#4169E1",
                saddlebrown: "#8B4513",
                salmon: "#FA8072",
                sandybrown: "#F4A460",
                seagreen: "#2E8B57",
                seashell: "#FFF5EE",
                sienna: "#A0522D",
                skyblue: "#87CEEB",
                slateblue: "#6A5ACD",
                slategray: "#708090",
                slategrey: "#708090",
                snow: "#FFFAFA",
                springgreen: "#00FF7F",
                steelblue: "#4682B4",
                tan: "#D2B48C",
                thistle: "#D8BFD8",
                tomato: "#FF6347",
                turquoise: "#40E0D0",
                violet: "#EE82EE",
                wheat: "#F5DEB3",
                whitesmoke: "#F5F5F5",
                yellowgreen: "#9ACD32"
            }, O = {}, _ = {style: "normal", variant: "normal", weight: "normal", size: 12, family: "微软雅黑"}, D = {},
            j = {butt: "flat", round: "round"}, q = I.prototype;
        q.clearRect = function () {
            this.textMeasureEl_ && (this.textMeasureEl_.removeNode(!0), this.textMeasureEl_ = null), this.element_.innerHTML = ""
        }, q.beginPath = function () {
            this.currentPath_ = []
        }, q.moveTo = function (e, t) {
            var n = W(this, e, t);
            this.currentPath_.push({type: "moveTo", x: n.x, y: n.y}), this.currentX_ = n.x, this.currentY_ = n.y
        }, q.lineTo = function (e, t) {
            var n = W(this, e, t);
            this.currentPath_.push({type: "lineTo", x: n.x, y: n.y}), this.currentX_ = n.x, this.currentY_ = n.y
        }, q.bezierCurveTo = function (e, t, n, r, i, s) {
            var o = W(this, i, s), u = W(this, e, t), a = W(this, n, r);
            R(this, u, a, o)
        }, q.quadraticCurveTo = function (e, t, n, r) {
            var i = W(this, e, t), s = W(this, n, r), o = {
                x: this.currentX_ + 2 / 3 * (i.x - this.currentX_),
                y: this.currentY_ + 2 / 3 * (i.y - this.currentY_)
            }, u = {x: o.x + (s.x - this.currentX_) / 3, y: o.y + (s.y - this.currentY_) / 3};
            R(this, o, u, s)
        }, q.arc = function (e, t, i, s, a, f) {
            i *= o;
            var l = f ? "at" : "wa", c = e + r(s) * i - u, h = t + n(s) * i - u, p = e + r(a) * i - u,
                d = t + n(a) * i - u;
            c == p && !f && (c += .125);
            var v = W(this, e, t), m = W(this, c, h), g = W(this, p, d);
            this.currentPath_.push({type: l, x: v.x, y: v.y, radius: i, xStart: m.x, yStart: m.y, xEnd: g.x, yEnd: g.y})
        }, q.rect = function (e, t, n, r) {
            this.moveTo(e, t), this.lineTo(e + n, t), this.lineTo(e + n, t + r), this.lineTo(e, t + r), this.closePath()
        }, q.strokeRect = function (e, t, n, r) {
            var i = this.currentPath_;
            this.beginPath(), this.moveTo(e, t), this.lineTo(e + n, t), this.lineTo(e + n, t + r), this.lineTo(e, t + r), this.closePath(), this.stroke(), this.currentPath_ = i
        }, q.fillRect = function (e, t, n, r) {
            var i = this.currentPath_;
            this.beginPath(), this.moveTo(e, t), this.lineTo(e + n, t), this.lineTo(e + n, t + r), this.lineTo(e, t + r), this.closePath(), this.fill(), this.currentPath_ = i
        }, q.createLinearGradient = function (e, t, n, r) {
            var i = new $("gradient");
            return i.x0_ = e, i.y0_ = t, i.x1_ = n, i.y1_ = r, i
        }, q.createRadialGradient = function (e, t, n, r, i, s) {
            var o = new $("gradientradial");
            return o.x0_ = e, o.y0_ = t, o.r0_ = n, o.x1_ = r, o.y1_ = i, o.r1_ = s, o
        }, q.drawImage = function (n, r) {
            var i, s, u, a, f, l, c, h, p = n.runtimeStyle.width, d = n.runtimeStyle.height;
            n.runtimeStyle.width = "auto", n.runtimeStyle.height = "auto";
            var v = n.width, m = n.height;
            n.runtimeStyle.width = p, n.runtimeStyle.height = d;
            if (arguments.length == 3) i = arguments[1], s = arguments[2], f = l = 0, c = u = v, h = a = m; else if (arguments.length == 5) i = arguments[1], s = arguments[2], u = arguments[3], a = arguments[4], f = l = 0, c = v, h = m; else {
                if (arguments.length != 9)throw Error("Invalid number of arguments");
                f = arguments[1], l = arguments[2], c = arguments[3], h = arguments[4], i = arguments[5], s = arguments[6], u = arguments[7], a = arguments[8]
            }
            var g = W(this, i, s), y = c / 2, b = h / 2, w = [], E = 10, S = 10, x = scaleY = 1;
            w.push(" <g_vml_:group", ' coordsize="', o * E, ",", o * S, '"', ' coordorigin="0,0"', ' style="width:', E, "px;height:", S, "px;position:absolute;");
            if (this.m_[0][0] != 1 || this.m_[0][1] || this.m_[1][1] != 1 || this.m_[1][0]) {
                var T = [];
                x = Math.sqrt(this.m_[0][0] * this.m_[0][0] + this.m_[0][1] * this.m_[0][1]), scaleY = Math.sqrt(this.m_[1][0] * this.m_[1][0] + this.m_[1][1] * this.m_[1][1]), T.push("M11=", this.m_[0][0] / x, ",", "M12=", this.m_[1][0] / scaleY, ",", "M21=", this.m_[0][1] / x, ",", "M22=", this.m_[1][1] / scaleY, ",", "Dx=", t(g.x / o), ",", "Dy=", t(g.y / o), "");
                var N = g, C = W(this, i + u, s), k = W(this, i, s + a), L = W(this, i + u, s + a);
                N.x = e.max(N.x, C.x, k.x, L.x), N.y = e.max(N.y, C.y, k.y, L.y), w.push("padding:0 ", t(N.x / o), "px ", t(N.y / o), "px 0;filter:progid:DXImageTransform.Microsoft.Matrix(", T.join(""), ", sizingmethod='clip');")
            } else w.push("top:", t(g.y / o), "px;left:", t(g.x / o), "px;");
            w.push(' ">'), (f || l) && w.push('<div style="overflow: hidden; width:', Math.ceil((u + f * u / c) * x), "px;", " height:", Math.ceil((a + l * a / h) * scaleY), "px;", " filter:progid:DxImageTransform.Microsoft.Matrix(Dx=", -f * u / c * x, ",Dy=", -l * a / h * scaleY, ');">'), w.push('<div style="width:', Math.round(x * v * u / c), "px;", " height:", Math.round(scaleY * m * a / h), "px;", " filter:"), this.globalAlpha < 1 && w.push(" progid:DXImageTransform.Microsoft.Alpha(opacity=" + this.globalAlpha * 100 + ")"), w.push(" progid:DXImageTransform.Microsoft.AlphaImageLoader(src=", n.src, ',sizingMethod=scale)">'), (f || l) && w.push("</div>"), w.push("</div></div>"), this.element_.insertAdjacentHTML("BeforeEnd", w.join(""))
        }, q.stroke = function (e) {
            var n = [], r = !1, i = 10, s = 10;
            n.push("<g_vml_:shape", ' filled="', !!e, '"', ' style="position:absolute;width:', i, "px;height:", s, 'px;"', ' coordorigin="0,0"', ' coordsize="', o * i, ",", o * s, '"', ' stroked="', !e, '"', ' path="');
            var u = !1, a = {x: null, y: null}, f = {x: null, y: null};
            for (var l = 0; l < this.currentPath_.length; l++) {
                var c = this.currentPath_[l], h;
                switch (c.type) {
                    case"moveTo":
                        h = c, n.push(" m ", t(c.x), ",", t(c.y));
                        break;
                    case"lineTo":
                        n.push(" l ", t(c.x), ",", t(c.y));
                        break;
                    case"close":
                        n.push(" x "), c = null;
                        break;
                    case"bezierCurveTo":
                        n.push(" c ", t(c.cp1x), ",", t(c.cp1y), ",", t(c.cp2x), ",", t(c.cp2y), ",", t(c.x), ",", t(c.y));
                        break;
                    case"at":
                    case"wa":
                        n.push(" ", c.type, " ", t(c.x - this.arcScaleX_ * c.radius), ",", t(c.y - this.arcScaleY_ * c.radius), " ", t(c.x + this.arcScaleX_ * c.radius), ",", t(c.y + this.arcScaleY_ * c.radius), " ", t(c.xStart), ",", t(c.yStart), " ", t(c.xEnd), ",", t(c.yEnd))
                }
                if (c) {
                    if (a.x == null || c.x < a.x) a.x = c.x;
                    if (f.x == null || c.x > f.x) f.x = c.x;
                    if (a.y == null || c.y < a.y) a.y = c.y;
                    if (f.y == null || c.y > f.y) f.y = c.y
                }
            }
            n.push(' ">'), e ? z(this, n, a, f) : U(this, n), n.push("</g_vml_:shape>"), this.element_.insertAdjacentHTML("beforeEnd", n.join(""))
        }, q.fill = function () {
            this.stroke(!0)
        }, q.closePath = function () {
            this.currentPath_.push({type: "close"})
        }, q.save = function () {
            var e = {};
            x(this, e), this.aStack_.push(e), this.mStack_.push(this.m_), this.m_ = S(E(), this.m_)
        }, q.restore = function () {
            this.aStack_.length && (x(this.aStack_.pop(), this), this.m_ = this.mStack_.pop())
        }, q.translate = function (e, t) {
            var n = [[1, 0, 0], [0, 1, 0], [e, t, 1]];
            V(this, S(n, this.m_), !1)
        }, q.rotate = function (e) {
            var t = r(e), i = n(e), s = [[t, i, 0], [-i, t, 0], [0, 0, 1]];
            V(this, S(s, this.m_), !1)
        }, q.scale = function (e, t) {
            this.arcScaleX_ *= e, this.arcScaleY_ *= t;
            var n = [[e, 0, 0], [0, t, 0], [0, 0, 1]];
            V(this, S(n, this.m_), !0)
        }, q.transform = function (e, t, n, r, i, s) {
            var o = [[e, t, 0], [n, r, 0], [i, s, 1]];
            V(this, S(o, this.m_), !0)
        }, q.setTransform = function (e, t, n, r, i, s) {
            var o = [[e, t, 0], [n, r, 0], [i, s, 1]];
            V(this, o, !0)
        }, q.drawText_ = function (e, n, r, i, s) {
            var u = this.m_, a = 1e3, f = 0, l = a, c = {x: 0, y: 0}, p = [], d = H(P(this.font), this.element_),
                v = B(d), m = this.element_.currentStyle, g = this.textAlign.toLowerCase();
            switch (g) {
                case"left":
                case"center":
                case"right":
                    break;
                case"end":
                    g = m.direction == "ltr" ? "right" : "left";
                    break;
                case"start":
                    g = m.direction == "rtl" ? "right" : "left";
                    break;
                default:
                    g = "left"
            }
            switch (this.textBaseline) {
                case"hanging":
                case"top":
                    c.y = d.size / 1.75;
                    break;
                case"middle":
                    break;
                default:
                case null:
                case"alphabetic":
                case"ideographic":
                case"bottom":
                    c.y = -d.size / 2.25
            }
            switch (g) {
                case"right":
                    f = a, l = .05;
                    break;
                case"center":
                    f = l = a / 2
            }
            var y = W(this, n + c.x, r + c.y);
            p.push('<g_vml_:line from="', -f, ' 0" to="', l, ' 0.05" ', ' coordsize="100 100" coordorigin="0 0"', ' filled="', !s, '" stroked="', !!s, '" style="position:absolute;width:1px;height:1px;">'), s ? U(this, p) : z(this, p, {
                x: -f,
                y: 0
            }, {x: l, y: d.size});
            var b = u[0][0].toFixed(3) + "," + u[1][0].toFixed(3) + "," + u[0][1].toFixed(3) + "," + u[1][1].toFixed(3) + ",0,0",
                w = t(y.x / o) + "," + t(y.y / o);
            p.push('<g_vml_:skew on="t" matrix="', b, '" ', ' offset="', w, '" origin="', f, ' 0" />', '<g_vml_:path textpathok="true" />', '<g_vml_:textpath on="true" string="', h(e), '" style="v-text-align:', g, ";font:", h(v), '" /></g_vml_:line>'), this.element_.insertAdjacentHTML("beforeEnd", p.join(""))
        }, q.fillText = function (e, t, n, r) {
            this.drawText_(e, t, n, r, !1)
        }, q.strokeText = function (e, t, n, r) {
            this.drawText_(e, t, n, r, !0)
        }, q.measureText = function (e) {
            if (!this.textMeasureEl_) {
                var t = '<span style="position:absolute;top:-20000px;left:0;padding:0;margin:0;border:none;white-space:pre;"></span>';
                this.element_.insertAdjacentHTML("beforeEnd", t), this.textMeasureEl_ = this.element_.lastChild
            }
            var n = this.element_.ownerDocument;
            return this.textMeasureEl_.innerHTML = "", this.textMeasureEl_.style.font = this.font, this.textMeasureEl_.appendChild(n.createTextNode(e)), {width: this.textMeasureEl_.offsetWidth}
        }, q.clip = function () {
        }, q.arcTo = function () {
        }, q.createPattern = function (e, t) {
            return new J(e, t)
        }, $.prototype.addColorStop = function (e, t) {
            t = M(t), this.colors_.push({offset: e, color: t.color, alpha: t.alpha})
        };
        var Y = G.prototype = new Error;
        Y.INDEX_SIZE_ERR = 1, Y.DOMSTRING_SIZE_ERR = 2, Y.HIERARCHY_REQUEST_ERR = 3, Y.WRONG_DOCUMENT_ERR = 4, Y.INVALID_CHARACTER_ERR = 5, Y.NO_DATA_ALLOWED_ERR = 6, Y.NO_MODIFICATION_ALLOWED_ERR = 7, Y.NOT_FOUND_ERR = 8, Y.NOT_SUPPORTED_ERR = 9, Y.INUSE_ATTRIBUTE_ERR = 10, Y.INVALID_STATE_ERR = 11, Y.SYNTAX_ERR = 12, Y.INVALID_MODIFICATION_ERR = 13, Y.NAMESPACE_ERR = 14, Y.INVALID_ACCESS_ERR = 15, Y.VALIDATION_ERR = 16, Y.TYPE_MISMATCH_ERR = 17, G_vmlCanvasManager = v, CanvasRenderingContext2D = I, CanvasGradient = $, CanvasPattern = J, DOMException = G
    }(), G_vmlCanvasManager
}), define("zrender/tool/util", ["require", "./vector", "../lib/excanvas"], function (e) {
    function n(e) {
        var t = {
            "[object Function]": 1,
            "[object RegExp]": 1,
            "[object Date]": 1,
            "[object Error]": 1,
            "[object CanvasGradient]": 1
        }, n = e, r, i;
        if (!e || e instanceof Number || e instanceof String || e instanceof Boolean)return n;
        if (e instanceof Array) {
            n = [];
            var s = 0;
            for (r = 0, i = e.length; r < i; r++)n[s++] = this.clone(e[r])
        } else if ("object" == typeof e) {
            if (t[Object.prototype.toString.call(e)] || e.__nonRecursion)return n;
            n = {};
            for (r in e)e.hasOwnProperty(r) && (n[r] = this.clone(e[r]))
        }
        return n
    }

    function i(e, t, n, r) {
        if (!e || !t)return;
        if (t instanceof Object)for (var s in t)if (t.hasOwnProperty(s))if (t[s] instanceof Object && r && e[s]) i(e[s], t[s], n, r); else if (n || !e.hasOwnProperty(s)) e[s] = t[s]
    }

    function o() {
        if (!s) {
            e("../lib/excanvas");
            if (G_vmlCanvasManager) {
                var t = document.createElement("div");
                t.style.position = "absolute", t.style.top = "-1000px", document.body.appendChild(t), s = G_vmlCanvasManager.initElement(t).getContext("2d")
            } else s = document.createElement("canvas").getContext("2d")
        }
        return s
    }

    function p() {
        return a || (u = document.createElement("canvas"), f = u.width, l = u.height, a = u.getContext("2d")), a
    }

    function d(e, t) {
        var n = 100, r = !1;
        e + c > f && (f = e + c + n, u.width = f, r = !0), t + h > l && (l = t + h + n, u.height = l, r = !0), e < -c && (c = Math.ceil(-e / n) * n, f += c, u.width = f, r = !0), t < -h && (h = Math.ceil(-t / n) * n, l += h, u.height = l, r = !0), r && a.translate(c, h)
    }

    function v() {
        return {x: c, y: h}
    }

    function m(e, t) {
        if (e.indexOf)return e.indexOf(t);
        for (var n = 0, r = e.length; n < r; n++)if (e[n] === t)return n;
        return -1
    }

    function g(e, t, n) {
        if (e.length === 0)return;
        var r = e[0][0], i = e[0][0], s = e[0][1], o = e[0][1];
        for (var u = 1; u < e.length; u++) {
            var a = e[u];
            a[0] < r && (r = a[0]), a[0] > i && (i = a[0]), a[1] < s && (s = a[1]), a[1] > o && (o = a[1])
        }
        t[0] = r, t[1] = s, n[0] = i, n[1] = o
    }

    function y(e, t, n, r, i, s) {
        var o = b(e[0], t[0], n[0], r[0]), u = b(e[1], t[1], n[1], r[1]);
        o.push(e[0], r[0]), u.push(e[1], r[1]);
        var a = Math.min.apply(null, o), f = Math.max.apply(null, o), l = Math.min.apply(null, u),
            c = Math.max.apply(null, u);
        i[0] = a, i[1] = l, s[0] = f, s[1] = c
    }

    function b(e, t, n, r) {
        var i = [], s = 6 * n - 12 * t + 6 * e, o = 9 * t + 3 * r - 3 * e - 9 * n, u = 3 * t - 3 * e,
            a = s * s - 4 * o * u;
        if (a > 0) {
            var f = Math.sqrt(a), l = (-s + f) / (2 * o), c = (-s - f) / (2 * o);
            i.push(l, c)
        } else a === 0 && i.push(-s / (2 * o));
        var h = [];
        for (var p = 0; p < i.length; p++) {
            var d = i[p];
            if (Math.abs(2 * o * d + s) > 1e-4 && d < 1 && d > 0) {
                var v = 1 - d, m = v * v * v * e + 3 * v * v * d * t + 3 * v * d * d * n + d * d * d * r;
                h.push(m)
            }
        }
        return h
    }

    function w(e, t, n, r, i) {
        var s = e[0] + n[0] - 2 * t[0], o;
        s === 0 ? o = .5 : o = (e[0] - t[0]) / s, s = e[1] + n[1] - 2 * t[1];
        var u;
        s === 0 ? u = .5 : u = (e[1] - t[1]) / s, o = Math.max(Math.min(o, 1), 0), u = Math.max(Math.min(u, 1), 0);
        var a = 1 - o, f = 1 - u, l = a * a * e[0] + 2 * a * o * t[0] + o * o * n[0],
            c = a * a * e[1] + 2 * a * o * t[1] + o * o * n[1], h = f * f * e[0] + 2 * f * u * t[0] + u * u * n[0],
            p = f * f * e[1] + 2 * f * u * t[1] + u * u * n[1];
        return g([e.slice(), n.slice(), [l, c], [h, p]], r, i)
    }

    var t = e("./vector"), r = function () {
        function t(t, n, i, s, o) {
            if (n.hasOwnProperty(i))if (o && typeof t[i] == "object" && e[Object.prototype.toString.call(t[i])] != 1) r(t[i], n[i], {
                overwrite: s,
                recursive: o
            }); else if (s || !(i in t)) t[i] = n[i]
        }

        var e = {
            "[object Function]": 1,
            "[object RegExp]": 1,
            "[object Date]": 1,
            "[object Error]": 1,
            "[object CanvasGradient]": 1
        };
        return function (e, n, r) {
            var i = 0, s = r || {}, o = s.overwrite, u = s.whiteList, a = s.recursive, f;
            if (u && u.length) {
                f = u.length;
                for (; i < f; ++i)t(e, n, u[i], o, a)
            } else for (i in n)t(e, n, i, o, a);
            return e
        }
    }(), s, u, a, f, l, c = 0, h = 0, E = function () {
        var e = [], n = [], r = [[], [], [], []];
        return function (i, s, o, u, a, f, l) {
            a = a ? 1 : -1, e[0] = Math.cos(o), e[1] = Math.sin(o) * a, t.scale(e, e, s), t.add(e, e, i), n[0] = Math.cos(u), n[1] = Math.sin(u) * a, t.scale(n, n, s), t.add(n, n, i), o %= Math.PI * 2, o < 0 && (o += Math.PI * 2), u %= Math.PI * 2, u < 0 && (u += Math.PI * 2), o > u && (u += Math.PI * 2);
            var c = 0;
            for (var h = 0; h < u; h += Math.PI / 2)if (h > o) {
                var p = r[c++];
                p[0] = Math.cos(h), p[1] = Math.sin(h) * a, t.scale(p, p, s), t.add(p, p, i)
            }
            var d = r.slice(0, c);
            d.push(e, n), g(d, f, l)
        }
    }();
    return {
        clone: n,
        merge: r,
        mergeFast: i,
        getContext: o,
        getPixelContext: p,
        getPixelOffset: v,
        adjustCanvasSize: d,
        computeBoundingBox: g,
        computeCubeBezierBoundingBox: y,
        computeQuadraticBezierBoundingBox: w,
        computeArcBoundingBox: E,
        indexOf: m
    }
}), define("zrender/shape", [], function () {
    var e = {}, t = {};
    return e.define = function (n, r) {
        return t[n] = r, e
    }, e.get = function (e) {
        return t[e]
    }, e
}), define("zrender/tool/area", ["require", "../tool/util", "../shape"], function (e) {
    function r(e, r, u, a) {
        if (!r || !e)return !1;
        var f = e.type;
        n || (n = t.getContext());
        if (!h(r.__rect || e.getRect(r), u, a))return !1;
        var l = i(f, r, u, a);
        if (typeof l != "undefined")return l;
        if (f != "beziercurve" && e.buildPath && n.isPointInPath)return s(e, n, r, u, a);
        if (n.getImageData)return o(e, r, u, a);
        switch (f) {
            case"heart":
                return !0;
            case"droplet":
                return !0;
            case"ellipse":
                return !0;
            case"trochoid":
                var c = r.location == "out" ? r.r1 + r.r2 + r.d : r.r1 - r.r2 + r.d;
                return p(r, u, a, c);
            case"rose":
                return p(r, u, a, r.maxr);
            default:
                return !1
        }
    }

    function i(e, t, n, r) {
        switch (e) {
            case"line":
                return f(t, n, r);
            case"brokenLine":
                return l(t, n, r);
            case"text":
                return !0;
            case"ring":
                return c(t, n, r);
            case"rectangle":
                return !0;
            case"circle":
                return p(t, n, r, t.r);
            case"sector":
                return d(t, n, r);
            case"path":
                return m(t, n, r);
            case"polygon":
            case"star":
            case"isogon":
                return v(t, n, r);
            case"image":
                return !0
        }
    }

    function s(e, t, n, r, i) {
        return t.beginPath(), e.buildPath(t, n), t.closePath(), t.isPointInPath(r, i)
    }

    function o(e, n, r, i) {
        var s = n.__rect || e.getRect(n), o = t.getPixelContext(), a = t.getPixelOffset();
        return t.adjustCanvasSize(r, i), o.clearRect(s.x, s.y, s.width, s.height), o.beginPath(), e.brush(o, {style: n}), o.closePath(), u(o, r + a.x, i + a.y)
    }

    function u(e, t, n, r) {
        var i;
        typeof r != "undefined" ? (r = Math.floor((r || 1) / 2), i = e.getImageData(t - r, n - r, r + r, r + r).data) : i = e.getImageData(t, n, 1, 1).data;
        var s = i.length;
        while (s--)if (i[s] !== 0)return !0;
        return !1
    }

    function a(e, t, n, i) {
        return !r(e, t, n, i)
    }

    function f(e, t, n) {
        var r = e.xStart, i = e.yStart, s = e.xEnd, o = e.yEnd, u = Math.max(e.lineWidth, 5), a = 0, f = r;
        if (r === s)return Math.abs(t - r) <= u / 2;
        a = (i - o) / (r - s), f = (r * o - s * i) / (r - s);
        var l = (a * t - n + f) * (a * t - n + f) / (a * a + 1);
        return l <= u / 2 * u / 2
    }

    function l(e, t, n) {
        var r = e.pointList, i, s = !1;
        for (var o = 0, u = r.length - 1; o < u; o++) {
            i = {
                xStart: r[o][0],
                yStart: r[o][1],
                xEnd: r[o + 1][0],
                yEnd: r[o + 1][1],
                lineWidth: Math.max(e.lineWidth, 10)
            };
            if (!h({
                    x: Math.min(i.xStart, i.xEnd) - i.lineWidth,
                    y: Math.min(i.yStart, i.yEnd) - i.lineWidth,
                    width: Math.abs(i.xStart - i.xEnd) + i.lineWidth,
                    height: Math.abs(i.yStart - i.yEnd) + i.lineWidth
                }, t, n))continue;
            s = f(i, t, n);
            if (s)break
        }
        return s
    }

    function c(e, t, n) {
        return p(e, t, n, e.r) && !p({x: e.x, y: e.y}, t, n, e.r0 || 0) ? !0 : !1
    }

    function h(e, t, n) {
        return t >= e.x && t <= e.x + e.width && n >= e.y && n <= e.y + e.height ? !0 : !1
    }

    function p(e, t, n, r) {
        return (t - e.x) * (t - e.x) + (n - e.y) * (n - e.y) < r * r
    }

    function d(e, t, n) {
        if (!p(e, t, n, e.r) || e.r0 > 0 && p({x: e.x, y: e.y}, t, n, e.r0))return !1;
        if (Math.abs(e.endAngle - e.startAngle) >= 360)return !0;
        var r = (360 - Math.atan2(n - e.y, t - e.x) / Math.PI * 180) % 360, i = (360 + e.endAngle) % 360,
            s = (360 + e.startAngle) % 360;
        return i > s ? r >= s && r <= i : !(r >= i && r <= s)
    }

    function v(e, t, n) {
        var r, i, s = e.pointList, o = s.length, u = !1, a = !0, f;
        for (r = 0; r < o; ++r)if (s[r][0] == t && s[r][1] == n) {
            a = !1, u = !0;
            break
        }
        if (a) {
            a = !1, u = !1;
            for (r = 0, i = o - 1; r < o; i = r++)if (s[r][1] < n && n < s[i][1] || s[i][1] < n && n < s[r][1]) {
                if (t <= s[r][0] || t <= s[i][0]) {
                    f = (n - s[r][1]) * (s[i][0] - s[r][0]) / (s[i][1] - s[r][1]) + s[r][0];
                    if (t < f) u = !u; else if (t == f) {
                        u = !0;
                        break
                    }
                }
            } else if (n == s[r][1]) {
                if (t < s[r][0]) {
                    s[r][1] > s[i][1] ? --n : ++n;
                    break
                }
            } else if (s[r][1] == s[i][1] && n == s[r][1] && (s[r][0] < t && t < s[i][0] || s[i][0] < t && t < s[r][0])) {
                u = !0;
                break
            }
        }
        return u
    }

    function m(t, r, i) {
        t.pointList || e("../shape").get("path").buildPath(n, t);
        var s = t.pointList, o = !1;
        for (var u = 0, a = s.length; u < a; u++) {
            o = v({pointList: s[u]}, r, i);
            if (o)break
        }
        return o
    }

    function g(e, r) {
        n || (n = t.getContext()), n.save(), r && (n.font = r), e = (e + "").split("\n");
        var i = 0;
        for (var s = 0, o = e.length; s < o; s++)i = Math.max(n.measureText(e[s]).width, i);
        return n.restore(), i
    }

    function y(e, r) {
        n || (n = t.getContext()), n.save(), r && (n.font = r), e = (e + "").split("\n");
        var i = (n.measureText("国").width + 2) * e.length;
        return n.restore(), i
    }

    var t = e("../tool/util"), n;
    return {isInside: r, isOutside: a, getTextWidth: g, getTextHeight: y}
}), define("zrender/tool/matrix", [], function () {
    var e = {
        create: function () {
            return [1, 0, 0, 1, 0, 0]
        }, identity: function (e) {
            e[0] = 1, e[1] = 0, e[2] = 0, e[3] = 1, e[4] = 0, e[5] = 0
        }, mul: function (e, t, n) {
            return e[0] = t[0] * n[0] + t[2] * n[1], e[1] = t[1] * n[0] + t[3] * n[1], e[2] = t[0] * n[2] + t[2] * n[3], e[3] = t[1] * n[2] + t[3] * n[3], e[4] = t[0] * n[4] + t[2] * n[5] + t[4], e[5] = t[1] * n[4] + t[3] * n[5] + t[5], e
        }, translate: function (e, t, n) {
            return e[0] = t[0], e[1] = t[1], e[2] = t[2], e[3] = t[3], e[4] = t[4] + n[0], e[5] = t[5] + n[1], e
        }, rotate: function (e, t, n) {
            var r = t[0], i = t[2], s = t[4], o = t[1], u = t[3], a = t[5], f = Math.sin(n), l = Math.cos(n);
            return e[0] = r * l + o * f, e[1] = -r * f + o * l, e[2] = i * l + u * f, e[3] = -i * f + l * u, e[4] = l * s + f * a, e[5] = l * a - f * s, e
        }, scale: function (e, t, n) {
            var r = n[0], i = n[1];
            return e[0] = t[0] * r, e[1] = t[1] * i, e[2] = t[2] * r, e[3] = t[3] * i, e[4] = t[4] * r, e[5] = t[5] * i, e
        }, invert: function (e, t) {
            var n = t[0], r = t[2], i = t[4], s = t[1], o = t[3], u = t[5], a = n * o - s * r;
            return a ? (a = 1 / a, e[0] = o * a, e[1] = -s * a, e[2] = -r * a, e[3] = n * a, e[4] = (r * u - o * i) * a, e[5] = (s * i - n * u) * a, e) : null
        }, mulVector: function (e, t, n) {
            var r = t[0], i = t[2], s = t[4], o = t[1], u = t[3], a = t[5];
            return e[0] = n[0] * r + n[1] * i + s, e[1] = n[0] * o + n[1] * u + a, e
        }
    };
    return e
}), define("zrender/tool/color", ["require", "../tool/util"], function (e) {
    function f(e) {
        r = e
    }

    function l() {
        r = i
    }

    function c(e, t) {
        return e = +e || 0, t = t || r, t[e % t.length]
    }

    function h(e) {
        s = e
    }

    function p() {
        o = s
    }

    function d() {
        return s
    }

    function v(e, r, i, s, o, u, a) {
        n || (n = t.getContext());
        var f = n.createRadialGradient(e, r, i, s, o, u);
        for (var l = 0, c = a.length; l < c; l++)f.addColorStop(a[l][0], a[l][1]);
        return f.__nonRecursion = !0, f
    }

    function m(e, r, i, s, o) {
        n || (n = t.getContext());
        var u = n.createLinearGradient(e, r, i, s);
        for (var a = 0, f = o.length; a < f; a++)u.addColorStop(o[a][0], o[a][1]);
        return u.__nonRecursion = !0, u
    }

    function g(e, t, n) {
        e = S(e), t = S(t), e = F(e), t = F(t);
        var r = [], i = (t[0] - e[0]) / n, s = (t[1] - e[1]) / n, o = (t[2] - e[2]) / n;
        for (var u = 0, a = e[0], f = e[1], l = e[2]; u < n; u++)r[u] = b([R(Math.floor(a), [0, 255]), R(Math.floor(f), [0, 255]), R(Math.floor(l), [0, 255])]), a += i, f += s, l += o;
        return a = t[0], f = t[1], l = t[2], r[u] = b([a, f, l]), r
    }

    function y(e, t) {
        var n = [], r = e.length;
        t === undefined && (t = 20);
        if (r === 1) n = g(e[0], e[0], t); else if (r > 1)for (var i = 0, s = r - 1; i < s; i++) {
            var o = g(e[i], e[i + 1], t);
            i < s - 1 && o.pop(), n = n.concat(o)
        }
        return n
    }

    function b(e, t) {
        t = t || "rgb";
        if (e && (e.length === 3 || e.length === 4)) {
            e = q(e, function (e) {
                return e > 1 ? Math.ceil(e) : e
            });
            if (t.indexOf("hex") > -1)return e = q(e.slice(0, 3), function (e) {
                return e = Number(e).toString(16), e.length === 1 ? "0" + e : e
            }), "#" + e.join("");
            if (t.indexOf("hs") > -1) {
                var n = q(e.slice(1, 3), function (e) {
                    return e + "%"
                });
                e[1] = n[0], e[2] = n[1]
            }
            return t.indexOf("a") > -1 ? (e.length === 3 && e.push(1), e[3] = R(e[3], [0, 1]), t + "(" + e.slice(0, 4).join(",") + ")") : t + "(" + e.slice(0, 3).join(",") + ")"
        }
    }

    function w(e) {
        e = _(e), e.indexOf("#") > -1 && (e = x(e));
        var t = e.replace(/[rgbahsvl%\(\)]/ig, "").split(",");
        return t = q(t, function (e) {
            return Number(e)
        }), t
    }

    function E(e, t) {
        var n = F(e), r = n[3];
        return typeof r == "undefined" && (r = 1), e.indexOf("hsb") > -1 ? n = U(n) : e.indexOf("hsl") > -1 && (n = z(n)), t.indexOf("hsb") > -1 || t.indexOf("hsv") > -1 ? n = X(n) : t.indexOf("hsl") > -1 && (n = V(n)), n[3] = r, b(n, t)
    }

    function S(e) {
        return E(e, "rgba")
    }

    function x(e) {
        return E(e, "rgb")
    }

    function T(e) {
        return E(e, "hex")
    }

    function N(e) {
        return E(e, "hsva")
    }

    function C(e) {
        return E(e, "hsv")
    }

    function k(e) {
        return E(e, "hsba")
    }

    function L(e) {
        return E(e, "hsb")
    }

    function A(e) {
        return E(e, "hsla")
    }

    function O(e) {
        return E(e, "hsl")
    }

    function M(e) {
        for (var t in a)if (T(a[t]) === T(e))return t;
        return null
    }

    function _(e) {
        return e = String(e), e = e.replace(/(^\s*)|(\s*$)/g, ""), /^[^#]*?$/i.test(e) && (e = e.replace(/\s/g, "")), e
    }

    function D(e) {
        a[e] && (e = a[e]), e = _(e), e = e.replace(/hsv/i, "hsb");
        if (/^#[0-9a-f]{3}$/i.test(e)) {
            var t = e.replace("#", "").split("");
            e = "#" + t[0] + t[0] + t[1] + t[1] + t[2] + t[2]
        }
        return e
    }

    function P(e, t) {
        var n = t > 0 ? 1 : -1;
        typeof t == "undefined" && (t = 0), t = Math.abs(t) > 1 ? 1 : Math.abs(t), e = x(e);
        var r = F(e);
        for (var i = 0; i < 3; i++)n === 1 ? r[i] = Math.floor(r[i] * (1 - t)) : r[i] = Math.floor((255 - r[i]) * t + r[i]);
        return "rgb(" + r.join(",") + ")"
    }

    function H(e) {
        var t = F(S(e));
        return t = q(t, function (e) {
            return 255 - e
        }), b(t, "rgb")
    }

    function B(e, t, n) {
        typeof n == "undefined" && (n = .5), n = 1 - R(n, [0, 1]);
        var r = n * 2 - 1, i = F(S(e)), s = F(S(t)), o = i[3] - s[3],
            u = ((r * o === -1 ? r : (r + o) / (1 + r * o)) + 1) / 2, a = 1 - u, f = [];
        for (var l = 0; l < 3; l++)f[l] = i[l] * u + s[l] * a;
        var c = i[3] * n + s[3] * (1 - n);
        return c = Math.max(0, Math.min(1, c)), i[3] === 1 && s[3] === 1 ? b(f, "rgb") : (f[3] = c, b(f, "rgba"))
    }

    function j() {
        return T("rgb(" + Math.round(Math.random() * 256) + "," + Math.round(Math.random() * 256) + "," + Math.round(Math.random() * 256) + ")")
    }

    function F(e) {
        e = D(e);
        var t = e.match(u);
        if (t === null)throw new Error("The color format error");
        var n, r, i = [], s;
        if (t[2]) n = t[2].replace("#", "").split(""), s = [n[0] + n[1], n[2] + n[3], n[4] + n[5]], i = q(s, function (e) {
            return R(parseInt(e, 16), [0, 255])
        }); else if (t[4]) {
            var o = t[4].split(",");
            r = o[3], s = o.slice(0, 3), i = q(s, function (e) {
                return e = Math.floor(e.indexOf("%") > 0 ? parseInt(e, 0) * 2.55 : e), R(e, [0, 255])
            }), typeof r != "undefined" && i.push(R(parseFloat(r), [0, 1]))
        } else if (t[5] || t[6]) {
            var a = (t[5] || t[6]).split(","), f = parseInt(a[0], 0) / 360, l = a[1], c = a[2];
            r = a[3], i = q([l, c], function (e) {
                return R(parseFloat(e) / 100, [0, 1])
            }), i.unshift(f), typeof r != "undefined" && i.push(R(parseFloat(r), [0, 1]))
        }
        return i
    }

    function I(e, t) {
        t === null && (t = 1);
        var n = F(S(e));
        return n[3] = R(Number(t).toFixed(4), [0, 1]), b(n, "rgba")
    }

    function q(e, t) {
        if (typeof t != "function")throw new TypeError;
        var n = e ? e.length : 0;
        for (var r = 0; r < n; r++)e[r] = t(e[r]);
        return e
    }

    function R(e, t) {
        return e <= t[0] ? e = t[0] : e >= t[1] && (e = t[1]), e
    }

    function U(e) {
        var t = e[0], n = e[1], r = e[2], i, s, o;
        if (n === 0) i = r * 255, s = r * 255, o = r * 255; else {
            var u = t * 6;
            u === 6 && (u = 0);
            var a = Math.floor(u), f = r * (1 - n), l = r * (1 - n * (u - a)), c = r * (1 - n * (1 - (u - a))), h = 0,
                p = 0, d = 0;
            a === 0 ? (h = r, p = c, d = f) : a === 1 ? (h = l, p = r, d = f) : a === 2 ? (h = f, p = r, d = c) : a === 3 ? (h = f, p = l, d = r) : a === 4 ? (h = c, p = f, d = r) : (h = r, p = f, d = l), i = h * 255, s = p * 255, o = d * 255
        }
        return [i, s, o]
    }

    function z(e) {
        var t = e[0], n = e[1], r = e[2], i, s, o;
        if (n === 0) i = r * 255, s = r * 255, o = r * 255; else {
            var u;
            r < .5 ? u = r * (1 + n) : u = r + n - n * r;
            var a = 2 * r - u;
            i = 255 * W(a, u, t + 1 / 3), s = 255 * W(a, u, t), o = 255 * W(a, u, t - 1 / 3)
        }
        return [i, s, o]
    }

    function W(e, t, n) {
        return n < 0 && (n += 1), n > 1 && (n -= 1), 6 * n < 1 ? e + (t - e) * 6 * n : 2 * n < 1 ? t : 3 * n < 2 ? e + (t - e) * (2 / 3 - n) * 6 : e
    }

    function X(e) {
        var t = e[0] / 255, n = e[1] / 255, r = e[2] / 255, i = Math.min(t, n, r), s = Math.max(t, n, r), o = s - i,
            u = s, a, f;
        if (o === 0) a = 0, f = 0; else {
            f = o / s;
            var l = ((s - t) / 6 + o / 2) / o, c = ((s - n) / 6 + o / 2) / o, h = ((s - r) / 6 + o / 2) / o;
            t === s ? a = h - c : n === s ? a = 1 / 3 + l - h : r === s && (a = 2 / 3 + c - l), a < 0 && (a += 1), a > 1 && (a -= 1)
        }
        return a *= 360, f *= 100, u *= 100, [a, f, u]
    }

    function V(e) {
        var t = e[0] / 255, n = e[1] / 255, r = e[2] / 255, i = Math.min(t, n, r), s = Math.max(t, n, r), o = s - i,
            u = (s + i) / 2, a, f;
        if (o === 0) a = 0, f = 0; else {
            u < .5 ? f = o / (s + i) : f = o / (2 - s - i);
            var l = ((s - t) / 6 + o / 2) / o, c = ((s - n) / 6 + o / 2) / o, h = ((s - r) / 6 + o / 2) / o;
            t === s ? a = h - c : n === s ? a = 1 / 3 + l - h : r === s && (a = 2 / 3 + c - l), a < 0 && (a += 1), a > 1 && (a -= 1)
        }
        return a *= 360, f *= 100, u *= 100, [a, f, u]
    }

    var t = e("../tool/util"), n,
        r = ["#ff9277", " #dddd00", " #ffc877", " #bbe3ff", " #d5ffbb", "#bbbbff", " #ddb000", " #b0dd00", " #e2bbff", " #ffbbe3", "#ff7777", " #ff9900", " #83dd00", " #77e3ff", " #778fff", "#c877ff", " #ff77ab", " #ff6600", " #aa8800", " #77c7ff", "#ad77ff", " #ff77ff", " #dd0083", " #777700", " #00aa00", "#0088aa", " #8400dd", " #aa0088", " #dd0000", " #772e00"],
        i = r, s = "rgba(255,255,0,0.5)", o = s,
        u = /^\s*((#[a-f\d]{6})|(#[a-f\d]{3})|rgba?\(\s*([\d\.]+%?\s*,\s*[\d\.]+%?\s*,\s*[\d\.]+%?(?:\s*,\s*[\d\.]+%?)?)\s*\)|hsba?\(\s*([\d\.]+(?:deg|\xb0|%)?\s*,\s*[\d\.]+%?\s*,\s*[\d\.]+%?(?:\s*,\s*[\d\.]+)?)%?\s*\)|hsla?\(\s*([\d\.]+(?:deg|\xb0|%)?\s*,\s*[\d\.]+%?\s*,\s*[\d\.]+%?(?:\s*,\s*[\d\.]+)?)%?\s*\))\s*$/i,
        a = {
            aliceblue: "#f0f8ff",
            antiquewhite: "#faebd7",
            aqua: "#0ff",
            aquamarine: "#7fffd4",
            azure: "#f0ffff",
            beige: "#f5f5dc",
            bisque: "#ffe4c4",
            black: "#000",
            blanchedalmond: "#ffebcd",
            blue: "#00f",
            blueviolet: "#8a2be2",
            brown: "#a52a2a",
            burlywood: "#deb887",
            cadetblue: "#5f9ea0",
            chartreuse: "#7fff00",
            chocolate: "#d2691e",
            coral: "#ff7f50",
            cornflowerblue: "#6495ed",
            cornsilk: "#fff8dc",
            crimson: "#dc143c",
            cyan: "#0ff",
            darkblue: "#00008b",
            darkcyan: "#008b8b",
            darkgoldenrod: "#b8860b",
            darkgray: "#a9a9a9",
            darkgrey: "#a9a9a9",
            darkgreen: "#006400",
            darkkhaki: "#bdb76b",
            darkmagenta: "#8b008b",
            darkolivegreen: "#556b2f",
            darkorange: "#ff8c00",
            darkorchid: "#9932cc",
            darkred: "#8b0000",
            darksalmon: "#e9967a",
            darkseagreen: "#8fbc8f",
            darkslateblue: "#483d8b",
            darkslategray: "#2f4f4f",
            darkslategrey: "#2f4f4f",
            darkturquoise: "#00ced1",
            darkviolet: "#9400d3",
            deeppink: "#ff1493",
            deepskyblue: "#00bfff",
            dimgray: "#696969",
            dimgrey: "#696969",
            dodgerblue: "#1e90ff",
            firebrick: "#b22222",
            floralwhite: "#fffaf0",
            forestgreen: "#228b22",
            fuchsia: "#f0f",
            gainsboro: "#dcdcdc",
            ghostwhite: "#f8f8ff",
            gold: "#ffd700",
            goldenrod: "#daa520",
            gray: "#808080",
            grey: "#808080",
            green: "#008000",
            greenyellow: "#adff2f",
            honeydew: "#f0fff0",
            hotpink: "#ff69b4",
            indianred: "#cd5c5c",
            indigo: "#4b0082",
            ivory: "#fffff0",
            khaki: "#f0e68c",
            lavender: "#e6e6fa",
            lavenderblush: "#fff0f5",
            lawngreen: "#7cfc00",
            lemonchiffon: "#fffacd",
            lightblue: "#add8e6",
            lightcoral: "#f08080",
            lightcyan: "#e0ffff",
            lightgoldenrodyellow: "#fafad2",
            lightgray: "#d3d3d3",
            lightgrey: "#d3d3d3",
            lightgreen: "#90ee90",
            lightpink: "#ffb6c1",
            lightsalmon: "#ffa07a",
            lightseagreen: "#20b2aa",
            lightskyblue: "#87cefa",
            lightslategray: "#789",
            lightslategrey: "#789",
            lightsteelblue: "#b0c4de",
            lightyellow: "#ffffe0",
            lime: "#0f0",
            limegreen: "#32cd32",
            linen: "#faf0e6",
            magenta: "#f0f",
            maroon: "#800000",
            mediumaquamarine: "#66cdaa",
            mediumblue: "#0000cd",
            mediumorchid: "#ba55d3",
            mediumpurple: "#9370d8",
            mediumseagreen: "#3cb371",
            mediumslateblue: "#7b68ee",
            mediumspringgreen: "#00fa9a",
            mediumturquoise: "#48d1cc",
            mediumvioletred: "#c71585",
            midnightblue: "#191970",
            mintcream: "#f5fffa",
            mistyrose: "#ffe4e1",
            moccasin: "#ffe4b5",
            navajowhite: "#ffdead",
            navy: "#000080",
            oldlace: "#fdf5e6",
            olive: "#808000",
            olivedrab: "#6b8e23",
            orange: "#ffa500",
            orangered: "#ff4500",
            orchid: "#da70d6",
            palegoldenrod: "#eee8aa",
            palegreen: "#98fb98",
            paleturquoise: "#afeeee",
            palevioletred: "#d87093",
            papayawhip: "#ffefd5",
            peachpuff: "#ffdab9",
            peru: "#cd853f",
            pink: "#ffc0cb",
            plum: "#dda0dd",
            powderblue: "#b0e0e6",
            purple: "#800080",
            red: "#f00",
            rosybrown: "#bc8f8f",
            royalblue: "#4169e1",
            saddlebrown: "#8b4513",
            salmon: "#fa8072",
            sandybrown: "#f4a460",
            seagreen: "#2e8b57",
            seashell: "#fff5ee",
            sienna: "#a0522d",
            silver: "#c0c0c0",
            skyblue: "#87ceeb",
            slateblue: "#6a5acd",
            slategray: "#708090",
            slategrey: "#708090",
            snow: "#fffafa",
            springgreen: "#00ff7f",
            steelblue: "#4682b4",
            tan: "#d2b48c",
            teal: "#008080",
            thistle: "#d8bfd8",
            tomato: "#ff6347",
            turquoise: "#40e0d0",
            violet: "#ee82ee",
            wheat: "#f5deb3",
            white: "#fff",
            whitesmoke: "#f5f5f5",
            yellow: "#ff0",
            yellowgreen: "#9acd32"
        };
    return {
        customPalette: f,
        resetPalette: l,
        getColor: c,
        getHighlightColor: d,
        customHighlight: h,
        resetHighlight: p,
        getRadialGradient: v,
        getLinearGradient: m,
        getGradientColors: y,
        getStepColors: g,
        reverse: H,
        mix: B,
        lift: P,
        trim: _,
        random: j,
        toRGB: x,
        toRGBA: S,
        toHex: T,
        toHSL: O,
        toHSLA: A,
        toHSB: L,
        toHSBA: k,
        toHSV: C,
        toHSVA: N,
        toName: M,
        toColor: b,
        toArray: w,
        alpha: I,
        getData: F
    }
}), define("zrender/shape/base", ["require", "../tool/area", "../tool/matrix", "../tool/vector", "../tool/color"], function (e) {
    function s(e) {
        var n = ["brush", "setContext", "dashedLineTo", "smoothBezier", "smoothSpline", "drawText", "getHighlightStyle", "getHighlightZoom", "drift", "isCover", "updateTransform"],
            r = n.length, i = e.prototype, s = 0, o;
        for (; s < r; s++)o = n[s], i[o] || (i[o] = t[o])
    }

    function o(e, t, n) {
        var r = t.style || {};
        this.brushTypeOnly && (r.brushType = this.brushTypeOnly), n && (r = this.getHighlightStyle(r, t.highlightStyle || {}, this.brushTypeOnly)), this.brushTypeOnly == "stroke" && (r.strokeColor = r.strokeColor || r.color), e.save(), this.setContext(e, r), t.__needTransform && e.transform.apply(e, this.updateTransform(t)), e.beginPath(), this.buildPath(e, r), this.brushTypeOnly != "stroke" && e.closePath();
        switch (r.brushType) {
            case"fill":
                e.fill();
                break;
            case"stroke":
                r.lineWidth > 0 && e.stroke();
                break;
            case"both":
                e.fill(), r.lineWidth > 0 && e.stroke();
                break;
            default:
                e.fill()
        }
        typeof r.text != "undefined" && this.drawText(e, r, t.style), e.restore();
        return
    }

    function u(e, t) {
        t.color && (e.fillStyle = t.color), t.strokeColor && (e.strokeStyle = t.strokeColor), typeof t.opacity != "undefined" && (e.globalAlpha = t.opacity), t.lineCap && (e.lineCap = t.lineCap), t.lineJoin && (e.lineJoin = t.lineJoin), t.miterLimit && (e.miterLimit = t.miterLimit), typeof t.lineWidth != "undefined" && (e.lineWidth = t.lineWidth), typeof t.shadowBlur != "undefined" && (e.shadowBlur = t.shadowBlur), t.shadowColor && (e.shadowColor = t.shadowColor), typeof t.shadowOffsetX != "undefined" && (e.shadowOffsetX = t.shadowOffsetX), typeof t.shadowOffsetY != "undefined" && (e.shadowOffsetY = t.shadowOffsetY)
    }

    function a(e, t, n, r, i, s) {
        s = typeof s == "undefined" ? 5 : s;
        var o = r - t, u = i - n, a = Math.floor(Math.sqrt(o * o + u * u) / s);
        for (var f = 0; f < a; ++f)e[f % 2 === 0 ? "moveTo" : "lineTo"](t + o / a * f, n + u / a * f)
    }

    function f(e, t, n) {
        var r = e.length, s = [], o = [], u = [], a = [], f, l;
        for (var c = 0; c < r; c++) {
            var h = e[c], f, l;
            if (n) f = e[c === 0 ? r - 1 : c - 1], l = e[(c + 1) % r]; else {
                if (c === 0 || c === r - 1) {
                    s.push(e[c]);
                    continue
                }
                f = e[c - 1], l = e[c + 1]
            }
            i.sub(o, l, f), i.scale(o, o, t);
            var p = i.distance(h, f), d = i.distance(h, l), v = p + d;
            p /= v, d /= v, i.scale(u, o, -p), i.scale(a, o, d), s.push(i.add([], h, u)), s.push(i.add([], h, a))
        }
        return n && s.push(s.shift()), s
    }

    function l(e, t) {
        var n = e.length, r = [], s = 0;
        for (var o = 1; o < n; o++)s += i.distance(e[o - 1], e[o]);
        var u = s / 5;
        u = u < n ? n : u;
        for (var o = 0; o < u; o++) {
            var a;
            t ? a = o / (u - 1) * n : a = o / (u - 1) * (n - 1);
            var f = Math.floor(a), l = a - f, h, p = e[f % n], d, v;
            t ? (h = e[(f - 1 + n) % n], d = e[(f + 1) % n], v = e[(f + 2) % n]) : (h = e[f === 0 ? f : f - 1], d = e[f > n - 2 ? n - 1 : f + 1], v = e[f > n - 3 ? n - 1 : f + 2]);
            var m = l * l, g = l * m;
            r.push([c(h[0], p[0], d[0], v[0], l, m, g), c(h[1], p[1], d[1], v[1], l, m, g)])
        }
        return r
    }

    function c(e, t, n, r, i, s, o) {
        var u = (n - e) * .5, a = (r - t) * .5;
        return (2 * (t - n) + u + a) * o + (-3 * (t - n) - 2 * u - a) * s + u * i + t
    }

    function h(e, t, n) {
        t.textColor = t.textColor || t.color || t.strokeColor, e.fillStyle = t.textColor, t.textPosition == "inside" && (e.shadowColor = "rgba(0,0,0,0)");
        var r = 10, i, s, o, u, a = t.textPosition || this.textPosition || "top";
        if (a != "inside" && a != "top" && a != "bottom" && a != "left" && a != "right" || !this.getRect)if (a == "start" || a == "end") {
            var l, c, h, d;
            if (typeof t.pointList != "undefined") {
                var v = t.pointList;
                if (v.length < 2)return;
                var m = v.length;
                switch (a) {
                    case"start":
                        l = v[0][0], c = v[1][0], h = v[0][1], d = v[1][1];
                        break;
                    case"end":
                        l = v[m - 2][0], c = v[m - 1][0], h = v[m - 2][1], d = v[m - 1][1]
                }
            } else l = t.xStart || 0, c = t.xEnd || 0, h = t.yStart || 0, d = t.yEnd || 0;
            switch (a) {
                case"start":
                    i = l < c ? "end" : "start", s = h < d ? "bottom" : "top", o = l, u = h;
                    break;
                case"end":
                    i = l < c ? "start" : "end", s = h < d ? "top" : "bottom", o = c, u = d
            }
            r -= 4, l != c ? o -= i == "end" ? r : -r : i = "center", h != d ? u -= s == "bottom" ? r : -r : s = "middle"
        } else a == "specific" && (o = t.textX || 0, u = t.textY || 0, i = "start", s = "middle"); else {
            var f = (n || t).__rect || this.getRect(n || t);
            switch (a) {
                case"inside":
                    o = f.x + f.width / 2, u = f.y + f.height / 2, i = "center", s = "middle", t.brushType != "stroke" && t.textColor == t.color && (e.fillStyle = "#fff");
                    break;
                case"left":
                    o = f.x - r, u = f.y + f.height / 2, i = "end", s = "middle";
                    break;
                case"right":
                    o = f.x + f.width + r, u = f.y + f.height / 2, i = "start", s = "middle";
                    break;
                case"top":
                    o = f.x + f.width / 2, u = f.y - r, i = "center", s = "bottom";
                    break;
                case"bottom":
                    o = f.x + f.width / 2, u = f.y + f.height + r, i = "center", s = "top"
            }
        }
        typeof o != "undefined" && typeof u != "undefined" && p(e, t.text, o, u, t.textFont, t.textAlign || i, t.textBaseline || s)
    }

    function p(e, t, r, i, s, o, u) {
        s && (e.font = s), e.textAlign = o, e.textBaseline = u;
        var a = d(t, r, i, s, o, u);
        t = (t + "").split("\n");
        var f = n.getTextHeight("国", s), r = r, i;
        u == "top" ? i = a.y : u == "bottom" ? i = a.y + f : i = a.y + f / 2;
        for (var l = 0, c = t.length; l < c; l++)e.fillText(t[l], r, i), i += f
    }

    function d(e, t, r, i, s, o) {
        var u = n.getTextWidth(e, i), a = n.getTextHeight("国", i);
        e = (e + "").split("\n");
        var f = t;
        s == "end" || s == "right" ? f -= u : s == "center" && (f -= u / 2);
        var l;
        return o == "top" ? l = r : o == "bottom" ? l = r - a * e.length : l = r - a * e.length / 2, {
            x: f,
            y: l,
            width: u,
            height: a * e.length
        }
    }

    function v(t, n, r) {
        var i = {};
        for (var s in t)i[s] = t[s];
        var o = e("../tool/color"), u = o.getHighlightColor();
        t.brushType != "stroke" ? (i.strokeColor = u, i.lineWidth = (t.lineWidth || 1) + this.getHighlightZoom(), i.brushType = "both") : r != "stroke" ? (i.strokeColor = u, i.lineWidth = (t.lineWidth || 1) + this.getHighlightZoom()) : i.strokeColor = n.strokeColor || o.mix(t.strokeColor, o.toRGB(u));
        for (var s in n)typeof n[s] != "undefined" && (i[s] = n[s]);
        return i
    }

    function m() {
        return this.type != "text" ? 6 : 2
    }

    function g(e, t, n) {
        e.position[0] += t, e.position[1] += n
    }

    function y(e, t, i) {
        if (e.__needTransform && e._transform) {
            var s = [];
            r.invert(s, e._transform);
            var o = [t, i];
            r.mulVector(o, s, [t, i, 1]), t == o[0] && i == o[1] && (Math.abs(e.rotation[0]) > 1e-4 || Math.abs(e.position[0]) > 1e-4 || Math.abs(e.position[1]) > 1e-4 || Math.abs(e.scale[0] - 1) > 1e-4 || Math.abs(e.scale[1] - 1) > 1e-4 ? e.__needTransform = !0 : e.__needTransform = !1), t = o[0], i = o[1]
        }
        var u;
        return e.style.__rect ? u = e.style.__rect : (u = this.getRect(e.style), e.style.__rect = u), t >= u.x && t <= u.x + u.width && i >= u.y && i <= u.y + u.height ? n.isInside(this, e.style, t, i) : !1
    }

    function b(e) {
        var t = e._transform || r.create();
        r.identity(t);
        if (e.scale && (e.scale[0] !== 1 || e.scale[1] !== 1)) {
            var n = e.scale[2] || 0, i = e.scale[3] || 0;
            (n || i) && r.translate(t, t, [-n, -i]), r.scale(t, t, e.scale), (n || i) && r.translate(t, t, [n, i])
        }
        if (e.rotation)if (e.rotation instanceof Array) {
            if (e.rotation[0] !== 0) {
                var n = e.rotation[1] || 0, i = e.rotation[2] || 0;
                (n || i) && r.translate(t, t, [-n, -i]), r.rotate(t, t, e.rotation[0]), (n || i) && r.translate(t, t, [n, i])
            }
        } else e.rotation !== 0 && r.rotate(t, t, e.rotation);
        return e.position && (e.position[0] !== 0 || e.position[1] !== 0) && r.translate(t, t, e.position), e._transform = t, t
    }

    var t, n = e("../tool/area"), r = e("../tool/matrix"), i = e("../tool/vector");
    return t = {
        derive: s,
        brush: o,
        setContext: u,
        dashedLineTo: a,
        smoothBezier: f,
        smoothSpline: l,
        drawText: h,
        getHighlightStyle: v,
        getHighlightZoom: m,
        drift: g,
        isCover: y,
        updateTransform: b
    }, t
}), define("zrender/shape/circle", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "circle"
    }

    t.prototype = {
        buildPath: function (e, t) {
            e.arc(t.x, t.y, t.r, 0, Math.PI * 2, !0);
            return
        }, getRect: function (e) {
            var t;
            return e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0, {
                x: Math.round(e.x - e.r - t / 2),
                y: Math.round(e.y - e.r - t / 2),
                width: e.r * 2 + t,
                height: e.r * 2 + t
            }
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("circle", new t), t
}), define("zrender/shape/ellipse", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "ellipse"
    }

    t.prototype = {
        buildPath: function (e, t) {
            var n = .5522848, r = t.x, i = t.y, s = t.a, o = t.b, u = s * n, a = o * n;
            e.moveTo(r - s, i), e.bezierCurveTo(r - s, i - a, r - u, i - o, r, i - o), e.bezierCurveTo(r + u, i - o, r + s, i - a, r + s, i), e.bezierCurveTo(r + s, i + a, r + u, i + o, r, i + o), e.bezierCurveTo(r - u, i + o, r - s, i + a, r - s, i);
            return
        }, getRect: function (e) {
            var t;
            return e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0, {
                x: Math.round(e.x - e.a - t / 2),
                y: Math.round(e.y - e.b - t / 2),
                width: e.a * 2 + t,
                height: e.b * 2 + t
            }
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("ellipse", new t), t
}), define("zrender/shape/line", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "line", this.brushTypeOnly = "stroke", this.textPosition = "end"
    }

    t.prototype = {
        buildPath: function (e, t) {
            if (!t.lineType || t.lineType == "solid") e.moveTo(t.xStart, t.yStart), e.lineTo(t.xEnd, t.yEnd); else if (t.lineType == "dashed" || t.lineType == "dotted") {
                var n = (t.lineWidth || 1) * (t.lineType == "dashed" ? 5 : 1);
                this.dashedLineTo(e, t.xStart, t.yStart, t.xEnd, t.yEnd, n)
            }
        }, getRect: function (e) {
            var t = e.lineWidth || 1;
            return {
                x: Math.min(e.xStart, e.xEnd) - t,
                y: Math.min(e.yStart, e.yEnd) - t,
                width: Math.abs(e.xStart - e.xEnd) + t,
                height: Math.abs(e.yStart - e.yEnd) + t
            }
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("line", new t), t
}), define("zrender/shape/polygon", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "polygon"
    }

    t.prototype = {
        brush: function (e, t, n) {
            var r = t.style || {};
            n && (r = this.getHighlightStyle(r, t.highlightStyle || {})), e.save(), this.setContext(e, r), t.__needTransform && e.transform.apply(e, this.updateTransform(t));
            var i = !1;
            if (r.brushType == "fill" || r.brushType == "both" || typeof r.brushType == "undefined") e.beginPath(), r.lineType == "dashed" || r.lineType == "dotted" ? (this.buildPath(e, {
                lineType: "solid",
                lineWidth: r.lineWidth,
                pointList: r.pointList
            }), i = !1) : (this.buildPath(e, r), i = !0), e.closePath(), e.fill();
            r.lineWidth > 0 && (r.brushType == "stroke" || r.brushType == "both") && (i || (e.beginPath(), this.buildPath(e, r), e.closePath()), e.stroke()), r.text && this.drawText(e, r, t.style), e.restore();
            return
        }, buildPath: function (e, t) {
            var n = t.pointList, r = n[0], i = n[n.length - 1];
            r && i && r[0] == i[0] && r[1] == i[1] && n.pop();
            if (n.length < 2)return;
            if (t.smooth && t.smooth !== "spline") {
                var s = this.smoothBezier(n, t.smooth, !0);
                e.moveTo(n[0][0], n[0][1]);
                var o, u, a, f = n.length;
                for (var l = 0; l < f; l++)o = s[l * 2], u = s[l * 2 + 1], a = n[(l + 1) % f], e.bezierCurveTo(o[0], o[1], u[0], u[1], a[0], a[1])
            } else {
                t.smooth === "spline" && (n = this.smoothSpline(n, !0));
                if (!t.lineType || t.lineType == "solid") {
                    e.moveTo(n[0][0], n[0][1]);
                    for (var l = 1, c = n.length; l < c; l++)e.lineTo(n[l][0], n[l][1]);
                    e.lineTo(n[0][0], n[0][1])
                } else if (t.lineType == "dashed" || t.lineType == "dotted") {
                    var h = t._dashLength || (t.lineWidth || 1) * (t.lineType == "dashed" ? 5 : 1);
                    t._dashLength = h, e.moveTo(n[0][0], n[0][1]);
                    for (var l = 1, c = n.length; l < c; l++)this.dashedLineTo(e, n[l - 1][0], n[l - 1][1], n[l][0], n[l][1], h);
                    this.dashedLineTo(e, n[n.length - 1][0], n[n.length - 1][1], n[0][0], n[0][1], h)
                }
            }
            return
        }, getRect: function (e) {
            var t = Number.MAX_VALUE, n = Number.MIN_VALUE, r = Number.MAX_VALUE, i = Number.MIN_VALUE, s = e.pointList;
            for (var o = 0, u = s.length; o < u; o++)s[o][0] < t && (t = s[o][0]), s[o][0] > n && (n = s[o][0]), s[o][1] < r && (r = s[o][1]), s[o][1] > i && (i = s[o][1]);
            var a;
            return e.brushType == "stroke" || e.brushType == "fill" ? a = e.lineWidth || 1 : a = 0, {
                x: Math.round(t - a / 2),
                y: Math.round(r - a / 2),
                width: n - t + a,
                height: i - r + a
            }
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("polygon", new t), t
}), define("zrender/shape/brokenLine", ["require", "../shape", "./base", "../shape"], function (e) {
    function t() {
        this.type = "brokenLine", this.brushTypeOnly = "stroke", this.textPosition = "end"
    }

    t.prototype = {
        buildPath: function (e, t) {
            var n = t.pointList;
            if (n.length < 2)return;
            if (t.smooth && t.smooth !== "spline") {
                var r = this.smoothBezier(n, t.smooth, !1);
                e.moveTo(n[0][0], n[0][1]);
                var i, s, o;
                for (var u = 0, a = n.length; u < a - 1; u++)i = r[u * 2], s = r[u * 2 + 1], o = n[u + 1], e.bezierCurveTo(i[0], i[1], s[0], s[1], o[0], o[1])
            } else {
                t.smooth === "spline" && (n = this.smoothSpline(n, !1));
                if (!t.lineType || t.lineType == "solid") {
                    e.moveTo(n[0][0], n[0][1]);
                    for (var u = 1, a = n.length; u < a; u++)e.lineTo(n[u][0], n[u][1])
                } else if (t.lineType == "dashed" || t.lineType == "dotted") {
                    var f = (t.lineWidth || 1) * (t.lineType == "dashed" ? 5 : 1);
                    e.moveTo(n[0][0], n[0][1]);
                    for (var u = 1, a = n.length; u < a; u++)this.dashedLineTo(e, n[u - 1][0], n[u - 1][1], n[u][0], n[u][1], f)
                }
            }
            return
        }, getRect: function (t) {
            var n = e("../shape");
            return n.get("polygon").getRect(t)
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("brokenLine", new t), t
}), define("zrender/shape/rectangle", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "rectangle"
    }

    t.prototype = {
        _buildRadiusPath: function (e, t) {
            var n = t.x, r = t.y, i = t.width, s = t.height, o = t.radius, u, a, f, l;
            typeof o == "number" ? u = a = f = l = o : o instanceof Array ? o.length === 1 ? u = a = f = l = o[0] : o.length === 2 ? (u = f = o[0], a = l = o[1]) : o.length === 3 ? (u = o[0], a = l = o[1], f = o[2]) : (u = o[0], a = o[1], f = o[2], l = o[3]) : u = a = f = l = 0, e.moveTo(n + u, r), e.lineTo(n + i - a, r), a !== 0 && e.quadraticCurveTo(n + i, r, n + i, r + a), e.lineTo(n + i, r + s - f), f !== 0 && e.quadraticCurveTo(n + i, r + s, n + i - f, r + s), e.lineTo(n + l, r + s), l !== 0 && e.quadraticCurveTo(n, r + s, n, r + s - l), e.lineTo(n, r + u), u !== 0 && e.quadraticCurveTo(n, r, n + u, r)
        }, buildPath: function (e, t) {
            t.radius ? this._buildRadiusPath(e, t) : (e.moveTo(t.x, t.y), e.lineTo(t.x + t.width, t.y), e.lineTo(t.x + t.width, t.y + t.height), e.lineTo(t.x, t.y + t.height), e.lineTo(t.x, t.y));
            return
        }, getRect: function (e) {
            var t;
            return e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0, {
                x: Math.round(e.x - t / 2),
                y: Math.round(e.y - t / 2),
                width: e.width + t,
                height: e.height + t
            }
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("rectangle", new t), t
}), define("zrender/shape/ring", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "ring"
    }

    t.prototype = {
        buildPath: function (e, t) {
            e.arc(t.x, t.y, t.r, 0, Math.PI * 2, !1), e.moveTo(t.x + t.r0, t.y), e.arc(t.x, t.y, t.r0, 0, Math.PI * 2, !0);
            return
        }, getRect: function (e) {
            var t;
            return e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0, {
                x: Math.round(e.x - e.r - t / 2),
                y: Math.round(e.y - e.r - t / 2),
                width: e.r * 2 + t,
                height: e.r * 2 + t
            }
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("ring", new t), t
}), define("zrender/tool/math", [], function () {
    function t(t, n) {
        return Math.sin(n ? t * e : t)
    }

    function n(t, n) {
        return Math.cos(n ? t * e : t)
    }

    function r(t) {
        return t * e
    }

    function i(t) {
        return t / e
    }

    var e = Math.PI / 180;
    return {sin: t, cos: n, degreeToRadian: r, radianToDegree: i}
}), define("zrender/shape/sector", ["require", "../tool/math", "../shape", "./base", "../shape"], function (e) {
    function n() {
        this.type = "sector"
    }

    var t = e("../tool/math");
    n.prototype = {
        buildPath: function (e, n) {
            var r = n.x, i = n.y, s = typeof n.r0 == "undefined" ? 0 : n.r0, o = n.r, u = n.startAngle, a = n.endAngle;
            if (Math.abs(a - u) >= 360) {
                e.arc(r, i, o, 0, Math.PI * 2, !1), s !== 0 && (e.moveTo(r + s, i), e.arc(r, i, s, 0, Math.PI * 2, !0));
                return
            }
            u = t.degreeToRadian(u), a = t.degreeToRadian(a);
            var f = Math.PI * 2, l = t.cos(u), c = t.sin(u);
            e.moveTo(l * s + r, i - c * s), e.lineTo(l * o + r, i - c * o), e.arc(r, i, o, f - u, f - a, !0), e.lineTo(t.cos(a) * s + r, i - t.sin(a) * s), s !== 0 && e.arc(r, i, s, f - a, f - u, !1);
            return
        }, getRect: function (n) {
            var r = e("../shape"), i = n.x, s = n.y, o = typeof n.r0 == "undefined" ? 0 : n.r0, u = n.r,
                a = n.startAngle, f = n.endAngle;
            if (Math.abs(f - a) >= 360)return r.get("ring").getRect(n);
            a = (720 + a) % 360, f = (720 + f) % 360, f <= a && (f += 360);
            var l = [];
            return a <= 90 && f >= 90 && l.push([i, s - u]), a <= 180 && f >= 180 && l.push([i - u, s]), a <= 270 && f >= 270 && l.push([i, s + u]), a <= 360 && f >= 360 && l.push([i + u, s]), a = t.degreeToRadian(a), f = t.degreeToRadian(f), l.push([t.cos(a) * o + i, s - t.sin(a) * o]), l.push([t.cos(a) * u + i, s - t.sin(a) * u]), l.push([t.cos(f) * u + i, s - t.sin(f) * u]), l.push([t.cos(f) * o + i, s - t.sin(f) * o]), r.get("polygon").getRect({
                brushType: n.brushType,
                lineWidth: n.lineWidth,
                pointList: l
            })
        }
    };
    var r = e("./base");
    r.derive(n);
    var i = e("../shape");
    return i.define("sector", new n), n
}), define("zrender/shape/text", ["require", "../tool/area", "./base", "../shape"], function (e) {
    function n() {
        this.type = "text"
    }

    var t = e("../tool/area");
    n.prototype = {
        brush: function (e, n, r) {
            var i = n.style || {};
            r && (i = this.getHighlightStyle(i, n.highlightStyle || {}));
            if (typeof i.text == "undefined")return;
            e.save(), this.setContext(e, i), n.__needTransform && e.transform.apply(e, this.updateTransform(n)), i.textFont && (e.font = i.textFont), e.textAlign = i.textAlign || "start", e.textBaseline = i.textBaseline || "middle";
            var s = (i.text + "").split("\n"), o = t.getTextHeight("国", i.textFont), u = this.getRect(i), a = i.x, f;
            i.textBaseline == "top" ? f = u.y : i.textBaseline == "bottom" ? f = u.y + o : f = u.y + o / 2;
            for (var l = 0, c = s.length; l < c; l++) {
                if (i.maxWidth)switch (i.brushType) {
                    case"fill":
                        e.fillText(s[l], a, f, i.maxWidth);
                        break;
                    case"stroke":
                        e.strokeText(s[l], a, f, i.maxWidth);
                        break;
                    case"both":
                        e.fillText(s[l], a, f, i.maxWidth), e.strokeText(s[l], a, f, i.maxWidth);
                        break;
                    default:
                        e.fillText(s[l], a, f, i.maxWidth)
                } else switch (i.brushType) {
                    case"fill":
                        e.fillText(s[l], a, f);
                        break;
                    case"stroke":
                        e.strokeText(s[l], a, f);
                        break;
                    case"both":
                        e.fillText(s[l], a, f), e.strokeText(s[l], a, f);
                        break;
                    default:
                        e.fillText(s[l], a, f)
                }
                f += o
            }
            e.restore();
            return
        }, getRect: function (e) {
            var n = t.getTextWidth(e.text, e.textFont), r = t.getTextHeight(e.text, e.textFont), i = e.x;
            e.textAlign == "end" || e.textAlign == "right" ? i -= n : e.textAlign == "center" && (i -= n / 2);
            var s;
            return e.textBaseline == "top" ? s = e.y : e.textBaseline == "bottom" ? s = e.y - r : s = e.y - r / 2, {
                x: i,
                y: s,
                width: n,
                height: r
            }
        }
    };
    var r = e("./base");
    r.derive(n);
    var i = e("../shape");
    return i.define("text", new n), n
}), define("zrender/shape/heart", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "heart"
    }

    t.prototype = {
        buildPath: function (e, t) {
            e.moveTo(t.x, t.y), e.bezierCurveTo(t.x + t.a / 2, t.y - t.b * 2 / 3, t.x + t.a * 2, t.y + t.b / 3, t.x, t.y + t.b), e.bezierCurveTo(t.x - t.a * 2, t.y + t.b / 3, t.x - t.a / 2, t.y - t.b * 2 / 3, t.x, t.y);
            return
        }, getRect: function (e) {
            var t;
            return e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0, {
                x: Math.round(e.x - e.a - t / 2),
                y: Math.round(e.y - e.b / 4 - t / 2),
                width: e.a * 2 + t,
                height: e.b * 5 / 4 + t
            }
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("heart", new t), t
}), define("zrender/shape/droplet", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "droplet"
    }

    t.prototype = {
        buildPath: function (e, t) {
            e.moveTo(t.x, t.y + t.a), e.bezierCurveTo(t.x + t.a, t.y + t.a, t.x + t.a * 3 / 2, t.y - t.a / 3, t.x, t.y - t.b), e.bezierCurveTo(t.x - t.a * 3 / 2, t.y - t.a / 3, t.x - t.a, t.y + t.a, t.x, t.y + t.a);
            return
        }, getRect: function (e) {
            var t;
            return e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0, {
                x: Math.round(e.x - e.a - t / 2),
                y: Math.round(e.y - e.b - t / 2),
                width: e.a * 2 + t,
                height: e.a + e.b + t
            }
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("droplet", new t), t
}), define("zrender/shape/path", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "path"
    }

    t.prototype = {
        _parsePathData: function (e) {
            if (!e)return [];
            var t = e,
                n = ["m", "M", "l", "L", "v", "V", "h", "H", "z", "Z", "c", "C", "q", "Q", "t", "T", "s", "S", "a", "A"];
            t = t.replace(/-/g, " -"), t = t.replace(/  /g, " "), t = t.replace(/ /g, ","), t = t.replace(/,,/g, ",");
            var r;
            for (r = 0; r < n.length; r++)t = t.replace(new RegExp(n[r], "g"), "|" + n[r]);
            var i = t.split("|"), s = [], o = 0, u = 0;
            for (r = 1; r < i.length; r++) {
                var a = i[r], f = a.charAt(0);
                a = a.slice(1), a = a.replace(new RegExp("e,-", "g"), "e-");
                var l = a.split(",");
                l.length > 0 && l[0] === "" && l.shift();
                for (var c = 0; c < l.length; c++)l[c] = parseFloat(l[c]);
                while (l.length > 0) {
                    if (isNaN(l[0]))break;
                    var h = null, p = [], d, v, m, g, y, b, w, E, S = o, x = u;
                    switch (f) {
                        case"l":
                            o += l.shift(), u += l.shift(), h = "L", p.push(o, u);
                            break;
                        case"L":
                            o = l.shift(), u = l.shift(), p.push(o, u);
                            break;
                        case"m":
                            o += l.shift(), u += l.shift(), h = "M", p.push(o, u), f = "l";
                            break;
                        case"M":
                            o = l.shift(), u = l.shift(), h = "M", p.push(o, u), f = "L";
                            break;
                        case"h":
                            o += l.shift(), h = "L", p.push(o, u);
                            break;
                        case"H":
                            o = l.shift(), h = "L", p.push(o, u);
                            break;
                        case"v":
                            u += l.shift(), h = "L", p.push(o, u);
                            break;
                        case"V":
                            u = l.shift(), h = "L", p.push(o, u);
                            break;
                        case"C":
                            p.push(l.shift(), l.shift(), l.shift(), l.shift()), o = l.shift(), u = l.shift(), p.push(o, u);
                            break;
                        case"c":
                            p.push(o + l.shift(), u + l.shift(), o + l.shift(), u + l.shift()), o += l.shift(), u += l.shift(), h = "C", p.push(o, u);
                            break;
                        case"S":
                            d = o, v = u, m = s[s.length - 1], m.command === "C" && (d = o + (o - m.points[2]), v = u + (u - m.points[3])), p.push(d, v, l.shift(), l.shift()), o = l.shift(), u = l.shift(), h = "C", p.push(o, u);
                            break;
                        case"s":
                            d = o, v = u, m = s[s.length - 1], m.command === "C" && (d = o + (o - m.points[2]), v = u + (u - m.points[3])), p.push(d, v, o + l.shift(), u + l.shift()), o += l.shift(), u += l.shift(), h = "C", p.push(o, u);
                            break;
                        case"Q":
                            p.push(l.shift(), l.shift()), o = l.shift(), u = l.shift(), p.push(o, u);
                            break;
                        case"q":
                            p.push(o + l.shift(), u + l.shift()), o += l.shift(), u += l.shift(), h = "Q", p.push(o, u);
                            break;
                        case"T":
                            d = o, v = u, m = s[s.length - 1], m.command === "Q" && (d = o + (o - m.points[0]), v = u + (u - m.points[1])), o = l.shift(), u = l.shift(), h = "Q", p.push(d, v, o, u);
                            break;
                        case"t":
                            d = o, v = u, m = s[s.length - 1], m.command === "Q" && (d = o + (o - m.points[0]), v = u + (u - m.points[1])), o += l.shift(), u += l.shift(), h = "Q", p.push(d, v, o, u);
                            break;
                        case"A":
                            g = l.shift(), y = l.shift(), b = l.shift(), w = l.shift(), E = l.shift(), S = o, x = u, o = l.shift(), u = l.shift(), h = "A", p = this._convertPoint(S, x, o, u, w, E, g, y, b);
                            break;
                        case"a":
                            g = l.shift(), y = l.shift(), b = l.shift(), w = l.shift(), E = l.shift(), S = o, x = u, o += l.shift(), u += l.shift(), h = "A", p = this._convertPoint(S, x, o, u, w, E, g, y, b)
                    }
                    s.push({command: h || f, points: p})
                }
                (f === "z" || f === "Z") && s.push({command: "z", points: []})
            }
            return s
        }, _convertPoint: function (e, t, n, r, i, s, o, u, a) {
            var f = a * (Math.PI / 180), l = Math.cos(f) * (e - n) / 2 + Math.sin(f) * (t - r) / 2,
                c = -1 * Math.sin(f) * (e - n) / 2 + Math.cos(f) * (t - r) / 2, h = l * l / (o * o) + c * c / (u * u);
            h > 1 && (o *= Math.sqrt(h), u *= Math.sqrt(h));
            var p = Math.sqrt((o * o * u * u - o * o * c * c - u * u * l * l) / (o * o * c * c + u * u * l * l));
            i === s && (p *= -1), isNaN(p) && (p = 0);
            var d = p * o * c / u, v = p * -u * l / o, m = (e + n) / 2 + Math.cos(f) * d - Math.sin(f) * v,
                g = (t + r) / 2 + Math.sin(f) * d + Math.cos(f) * v, y = function (e) {
                    return Math.sqrt(e[0] * e[0] + e[1] * e[1])
                }, b = function (e, t) {
                    return (e[0] * t[0] + e[1] * t[1]) / (y(e) * y(t))
                }, w = function (e, t) {
                    return (e[0] * t[1] < e[1] * t[0] ? -1 : 1) * Math.acos(b(e, t))
                }, E = w([1, 0], [(l - d) / o, (c - v) / u]), S = [(l - d) / o, (c - v) / u],
                x = [(-1 * l - d) / o, (-1 * c - v) / u], T = w(S, x);
            return b(S, x) <= -1 && (T = Math.PI), b(S, x) >= 1 && (T = 0), s === 0 && T > 0 && (T -= 2 * Math.PI), s === 1 && T < 0 && (T += 2 * Math.PI), [m, g, o, u, E, T, f, s]
        }, buildPath: function (e, t) {
            var n = t.path, r = this._parsePathData(n), i = t.x || 0, s = t.y || 0, o, u = t.pointList = [], a = [];
            for (var f = 0, l = r.length; f < l; f++) {
                r[f].command.toUpperCase() == "M" && (a.length > 0 && u.push(a), a = []), o = r[f].points;
                for (var c = 0, h = o.length; c < h; c += 2)a.push([o[c] + i, o[c + 1] + s])
            }
            a.length > 0 && u.push(a);
            var p;
            for (var f = 0, l = r.length; f < l; f++) {
                p = r[f].command, o = r[f].points;
                for (var c = 0, h = o.length; c < h; c++)c % 2 === 0 ? o[c] += i : o[c] += s;
                switch (p) {
                    case"L":
                        e.lineTo(o[0], o[1]);
                        break;
                    case"M":
                        e.moveTo(o[0], o[1]);
                        break;
                    case"C":
                        e.bezierCurveTo(o[0], o[1], o[2], o[3], o[4], o[5]);
                        break;
                    case"Q":
                        e.quadraticCurveTo(o[0], o[1], o[2], o[3]);
                        break;
                    case"A":
                        var d = o[0], v = o[1], m = o[2], g = o[3], y = o[4], b = o[5], w = o[6], E = o[7],
                            S = m > g ? m : g, x = m > g ? 1 : m / g, T = m > g ? g / m : 1;
                        e.translate(d, v), e.rotate(w), e.scale(x, T), e.arc(0, 0, S, y, y + b, 1 - E), e.scale(1 / x, 1 / T), e.rotate(-w), e.translate(-d, -v);
                        break;
                    case"z":
                        e.closePath()
                }
            }
            return
        }, getRect: function (e) {
            var t;
            e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0;
            var n = Number.MAX_VALUE, r = Number.MIN_VALUE, i = Number.MAX_VALUE, s = Number.MIN_VALUE, o = e.x || 0,
                u = e.y || 0, a = this._parsePathData(e.path);
            for (var f = 0; f < a.length; f++) {
                var l = a[f].points;
                for (var c = 0; c < l.length; c++)c % 2 === 0 ? (l[c] + o < n && (n = l[c] + o), l[c] + o > r && (r = l[c] + o)) : (l[c] + u < i && (i = l[c] + u), l[c] + u > s && (s = l[c] + u))
            }
            var h;
            return n === Number.MAX_VALUE || r === Number.MIN_VALUE || i === Number.MAX_VALUE || s === Number.MIN_VALUE ? h = {
                x: 0,
                y: 0,
                width: 0,
                height: 0
            } : h = {x: Math.round(n - t / 2), y: Math.round(i - t / 2), width: r - n + t, height: s - i + t}, h
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("path", new t), t
}), define("zrender/shape/image", ["require", "./base", "../shape"], function (e) {
    function i() {
        this.type = "image"
    }

    var t = {}, n = [], r;
    i.prototype = {
        brush: function (e, i, s, o) {
            var u = i.style || {};
            s && (u = this.getHighlightStyle(u, i.highlightStyle || {}));
            var a = u.image;
            if (typeof a == "string") {
                var f = a;
                t[f] ? a = t[f] : (a = new Image, a.onload = function () {
                    a.onload = null, clearTimeout(r), n.push(i), r = setTimeout(function () {
                        o(n), n = []
                    }, 10)
                }, t[f] = a, a.src = f)
            }
            if (a) {
                if (window.ActiveXObject) {
                    if (a.readyState != "complete")return
                } else if (!a.complete)return;
                e.save(), this.setContext(e, u), i.__needTransform && e.transform.apply(e, this.updateTransform(i));
                var l = u.width || a.width, c = u.height || a.height, h = u.x, p = u.y;
                if (u.sWidth && u.sHeight) {
                    var d = u.sx || 0, v = u.sy || 0;
                    e.drawImage(a, d, v, u.sWidth, u.sHeight, h, p, l, c)
                } else if (u.sx && u.sy) {
                    var d = u.sx, v = u.sy, m = l - d, g = c - v;
                    e.drawImage(a, d, v, m, g, h, p, l, c)
                } else e.drawImage(a, h, p, l, c);
                u.width = l, u.height = c, i.style.width = l, i.style.height = c, u.text && this.drawText(e, u, i.style), e.restore()
            }
            return
        }, buildPath: function (e, t) {
            e.rect(t.x, t.y, t.width, t.height);
            return
        }, getRect: function (e) {
            return {x: e.x, y: e.y, width: e.width, height: e.height}
        }
    };
    var s = e("./base");
    s.derive(i);
    var o = e("../shape");
    return o.define("image", new i), i
}), define("zrender/shape/beziercurve", ["require", "./base", "../shape"], function (e) {
    function t() {
        this.type = "beziercurve", this.brushTypeOnly = "stroke", this.textPosition = "end"
    }

    t.prototype = {
        buildPath: function (e, t) {
            e.moveTo(t.xStart, t.yStart), typeof t.cpX2 != "undefined" && typeof t.cpY2 != "undefined" ? e.bezierCurveTo(t.cpX1, t.cpY1, t.cpX2, t.cpY2, t.xEnd, t.yEnd) : e.quadraticCurveTo(t.cpX1, t.cpY1, t.xEnd, t.yEnd)
        }, getRect: function (e) {
            var t = Math.min(e.xStart, e.xEnd, e.cpX1), n = Math.min(e.yStart, e.yEnd, e.cpY1),
                r = Math.max(e.xStart, e.xEnd, e.cpX1), i = Math.max(e.yStart, e.yEnd, e.cpY1), s = e.cpX2, o = e.cpY2;
            typeof s != "undefined" && typeof o != "undefined" && (t = Math.min(t, s), n = Math.min(n, o), r = Math.max(r, s), i = Math.max(i, o));
            var u = e.lineWidth || 1;
            return {x: t - u, y: n - u, width: r - t + u, height: i - n + u}
        }
    };
    var n = e("./base");
    n.derive(t);
    var r = e("../shape");
    return r.define("beziercurve", new t), t
}), define("zrender/shape/star", ["require", "../tool/math", "./base", "../shape"], function (e) {
    function s() {
        this.type = "heart"
    }

    var t = e("../tool/math"), n = t.sin, r = t.cos, i = Math.PI;
    s.prototype = {
        buildPath: function (e, t) {
            var s = t.n;
            if (!s || s < 2)return;
            var o = t.x, u = t.y, a = t.r, f = t.r0;
            f == null && (f = s > 4 ? a * r(2 * i / s) / r(i / s) : a / 3);
            var l = i / s, c = -i / 2, h = o + a * r(c), p = u + a * n(c);
            c += l;
            var d = t.pointList = [];
            d.push([h, p]);
            for (var v = 0, m = s * 2 - 1, g; v < m; v++)g = v % 2 === 0 ? f : a, d.push([o + g * r(c), u + g * n(c)]), c += l;
            d.push([h, p]), e.moveTo(d[0][0], d[0][1]);
            for (var v = 0; v < d.length; v++)e.lineTo(d[v][0], d[v][1]);
            return
        }, getRect: function (e) {
            var t;
            return e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0, {
                x: Math.round(e.x - e.r - t / 2),
                y: Math.round(e.y - e.r - t / 2),
                width: e.r * 2 + t,
                height: e.r * 2 + t
            }
        }
    };
    var o = e("./base");
    o.derive(s);
    var u = e("../shape");
    return u.define("star", new s), s
}), define("zrender/shape/isogon", ["require", "../tool/math", "./base", "../shape"], function (e) {
    function s() {
        this.type = "isogon"
    }

    var t = e("../tool/math"), n = t.sin, r = t.cos, i = Math.PI;
    s.prototype = {
        buildPath: function (e, t) {
            var s = t.n;
            if (!s || s < 2)return;
            var o = t.x, u = t.y, a = t.r, f = 2 * i / s, l = -i / 2, c = o + a * r(l), h = u + a * n(l);
            l += f;
            var p = t.pointList = [];
            p.push([c, h]);
            for (var d = 0, v = s - 1; d < v; d++)p.push([o + a * r(l), u + a * n(l)]), l += f;
            p.push([c, h]), e.moveTo(p[0][0], p[0][1]);
            for (var d = 0; d < p.length; d++)e.lineTo(p[d][0], p[d][1]);
            return
        }, getRect: function (e) {
            var t;
            return e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0, {
                x: Math.round(e.x - e.r - t / 2),
                y: Math.round(e.y - e.r - t / 2),
                width: e.r * 2 + t,
                height: e.r * 2 + t
            }
        }
    };
    var o = e("./base");
    o.derive(s);
    var u = e("../shape");
    return u.define("isogon", new s), s
}), define("zrender/animation/easing", [], function () {
    var e = {
        Linear: function (e) {
            return e
        }, QuadraticIn: function (e) {
            return e * e
        }, QuadraticOut: function (e) {
            return e * (2 - e)
        }, QuadraticInOut: function (e) {
            return (e *= 2) < 1 ? .5 * e * e : -0.5 * (--e * (e - 2) - 1)
        }, CubicIn: function (e) {
            return e * e * e
        }, CubicOut: function (e) {
            return --e * e * e + 1
        }, CubicInOut: function (e) {
            return (e *= 2) < 1 ? .5 * e * e * e : .5 * ((e -= 2) * e * e + 2)
        }, QuarticIn: function (e) {
            return e * e * e * e
        }, QuarticOut: function (e) {
            return 1 - --e * e * e * e
        }, QuarticInOut: function (e) {
            return (e *= 2) < 1 ? .5 * e * e * e * e : -0.5 * ((e -= 2) * e * e * e - 2)
        }, QuinticIn: function (e) {
            return e * e * e * e * e
        }, QuinticOut: function (e) {
            return --e * e * e * e * e + 1
        }, QuinticInOut: function (e) {
            return (e *= 2) < 1 ? .5 * e * e * e * e * e : .5 * ((e -= 2) * e * e * e * e + 2)
        }, SinusoidalIn: function (e) {
            return 1 - Math.cos(e * Math.PI / 2)
        }, SinusoidalOut: function (e) {
            return Math.sin(e * Math.PI / 2)
        }, SinusoidalInOut: function (e) {
            return .5 * (1 - Math.cos(Math.PI * e))
        }, ExponentialIn: function (e) {
            return e === 0 ? 0 : Math.pow(1024, e - 1)
        }, ExponentialOut: function (e) {
            return e === 1 ? 1 : 1 - Math.pow(2, -10 * e)
        }, ExponentialInOut: function (e) {
            return e === 0 ? 0 : e === 1 ? 1 : (e *= 2) < 1 ? .5 * Math.pow(1024, e - 1) : .5 * (-Math.pow(2, -10 * (e - 1)) + 2)
        }, CircularIn: function (e) {
            return 1 - Math.sqrt(1 - e * e)
        }, CircularOut: function (e) {
            return Math.sqrt(1 - --e * e)
        }, CircularInOut: function (e) {
            return (e *= 2) < 1 ? -0.5 * (Math.sqrt(1 - e * e) - 1) : .5 * (Math.sqrt(1 - (e -= 2) * e) + 1)
        }, ElasticIn: function (e) {
            var t, n = .1, r = .4;
            return e === 0 ? 0 : e === 1 ? 1 : (!n || n < 1 ? (n = 1, t = r / 4) : t = r * Math.asin(1 / n) / (2 * Math.PI), -(n * Math.pow(2, 10 * (e -= 1)) * Math.sin((e - t) * 2 * Math.PI / r)))
        }, ElasticOut: function (e) {
            var t, n = .1, r = .4;
            return e === 0 ? 0 : e === 1 ? 1 : (!n || n < 1 ? (n = 1, t = r / 4) : t = r * Math.asin(1 / n) / (2 * Math.PI), n * Math.pow(2, -10 * e) * Math.sin((e - t) * 2 * Math.PI / r) + 1)
        }, ElasticInOut: function (e) {
            var t, n = .1, r = .4;
            return e === 0 ? 0 : e === 1 ? 1 : (!n || n < 1 ? (n = 1, t = r / 4) : t = r * Math.asin(1 / n) / (2 * Math.PI), (e *= 2) < 1 ? -0.5 * n * Math.pow(2, 10 * (e -= 1)) * Math.sin((e - t) * 2 * Math.PI / r) : n * Math.pow(2, -10 * (e -= 1)) * Math.sin((e - t) * 2 * Math.PI / r) * .5 + 1)
        }, BackIn: function (e) {
            var t = 1.70158;
            return e * e * ((t + 1) * e - t)
        }, BackOut: function (e) {
            var t = 1.70158;
            return --e * e * ((t + 1) * e + t) + 1
        }, BackInOut: function (e) {
            var t = 2.5949095;
            return (e *= 2) < 1 ? .5 * e * e * ((t + 1) * e - t) : .5 * ((e -= 2) * e * ((t + 1) * e + t) + 2)
        }, BounceIn: function (t) {
            return 1 - e.BounceOut(1 - t)
        }, BounceOut: function (e) {
            return e < 1 / 2.75 ? 7.5625 * e * e : e < 2 / 2.75 ? 7.5625 * (e -= 1.5 / 2.75) * e + .75 : e < 2.5 / 2.75 ? 7.5625 * (e -= 2.25 / 2.75) * e + .9375 : 7.5625 * (e -= 2.625 / 2.75) * e + .984375
        }, BounceInOut: function (t) {
            return t < .5 ? e.BounceIn(t * 2) * .5 : e.BounceOut(t * 2 - 1) * .5 + .5
        }
    };
    return e
}), define("zrender/animation/clip", ["require", "./easing"], function (e) {
    var t = e("./easing"), n = function (e) {
        this._targetPool = e.target || {}, this._targetPool.constructor != Array && (this._targetPool = [this._targetPool]), this._life = e.life || 1e3, this._delay = e.delay || 0, this._startTime = (new Date).getTime() + this._delay, this._endTime = this._startTime + this._life * 1e3, this.loop = typeof e.loop == "undefined" ? !1 : e.loop, this.gap = e.gap || 0, this.easing = e.easing || "Linear", this.onframe = e.onframe || null, this.ondestroy = e.ondestroy || null, this.onrestart = e.onrestart || null
    };
    return n.prototype = {
        step: function (e) {
            var n = (e - this._startTime) / this._life;
            if (n < 0)return;
            n = Math.min(n, 1);
            var r = typeof this.easing == "string" ? t[this.easing] : this.easing, i;
            return typeof r == "function" ? i = r(n) : i = n, this.fire("frame", i), n == 1 ? this.loop ? (this.restart(), "restart") : (this._needsRemove = !0, "destroy") : null
        }, restart: function () {
            this._startTime = (new Date).getTime() + this.gap
        }, fire: function (e, t) {
            for (var n = 0, r = this._targetPool.length; n < r; n++)this["on" + e] && this["on" + e](this._targetPool[n], t)
        }
    }, n.prototype.constructor = n, n
}), define("zrender/animation/animation", ["require", "./clip"], function (e) {
    function s(e, t) {
        return e[t]
    }

    function o(e, t, n) {
        e[t] = n
    }

    function u(e, t, n) {
        return (t - e) * n + e
    }

    function a(e, t, n, r, i) {
        var s = e.length;
        if (i == 1)for (var o = 0; o < s; o++)r[o] = u(e[o], t[o], n); else {
            var a = e[0].length;
            for (var o = 0; o < s; o++)for (var f = 0; f < a; f++)r[o][f] = u(e[o][f], t[o][f], n)
        }
    }

    function f(e) {
        return typeof e == "undefined" ? !1 : typeof e == "string" ? !1 : typeof e.length != "undefined"
    }

    function l(e, t, n, r, i, s, o, u, a) {
        var f = e.length;
        if (a == 1)for (var l = 0; l < f; l++)u[l] = c(e[l], t[l], n[l], r[l], i, s, o); else {
            var h = e[0].length;
            for (var l = 0; l < f; l++)for (var p = 0; p < h; p++)u[l][p] = c(e[l][p], t[l][p], n[l][p], r[l][p], i, s, o)
        }
    }

    function c(e, t, n, r, i, s, o) {
        var u = (n - e) * .5, a = (r - t) * .5;
        return (2 * (t - n) + u + a) * o + (-3 * (t - n) - 2 * u - a) * s + u * i + t
    }

    function h(e, t, n, r) {
        this._tracks = {}, this._target = e, this._loop = t || !1, this._getter = n || s, this._setter = r || o, this._clipCount = 0, this._delay = 0, this._doneList = [], this._onframeList = [], this._clipList = []
    }

    var t = e("./clip"),
        n = window.requestAnimationFrame || window.msRequestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || function (e) {
                setTimeout(e, 16)
            }, r = Array.prototype.slice, i = function (e) {
            e = e || {}, this.stage = e.stage || {}, this.onframe = e.onframe || function () {
                }, this._clips = [], this._running = !1, this._time = 0
        };
    return i.prototype = {
        add: function (e) {
            this._clips.push(e)
        }, remove: function (e) {
            var t = this._clips.indexOf(e);
            t >= 0 && this._clips.splice(t, 1)
        }, update: function () {
            var e = (new Date).getTime(), t = this._clips, n = t.length, r = [], i = [];
            for (var s = 0; s < n; s++) {
                var o = t[s], u = o.step(e);
                u && (r.push(u), i.push(o))
            }
            this.stage && this.stage.update && this._clips.length && this.stage.update();
            for (var s = 0; s < n;)t[s]._needsRemove ? (t[s] = t[n - 1], t.pop(), n--) : s++;
            n = r.length;
            for (var s = 0; s < n; s++)i[s].fire(r[s]);
            this._time = e, this.onframe()
        }, start: function () {
            function t() {
                e._running && (e.update(), n(t))
            }

            var e = this;
            this._running = !0, n(t)
        }, stop: function () {
            this._running = !1
        }, clear: function () {
            this._clips = []
        }, animate: function (e, t) {
            t = t || {};
            var n = new h(e, t.loop, t.getter, t.setter);
            return n.animation = this, n
        }
    }, i.prototype.constructor = i, h.prototype = {
        when: function (e, t) {
            for (var n in t)this._tracks[n] || (this._tracks[n] = [], this._tracks[n].push({
                time: 0,
                value: this._getter(this._target, n)
            })), this._tracks[n].push({time: parseInt(e, 10), value: t[n]});
            return this
        }, during: function (e) {
            return this._onframeList.push(e), this
        }, start: function (e) {
            var n = this, i = this._setter, s = this._getter, o = n._onframeList.length, h = e === "spline",
                p = function () {
                    n._clipCount--;
                    if (n._clipCount === 0) {
                        n._tracks = {};
                        var e = n._doneList.length;
                        for (var t = 0; t < e; t++)n._doneList[t].call(n)
                    }
                }, d = function (d, v) {
                    var m = d.length;
                    if (!m)return;
                    var g = d[0].value, y = f(g), b = y && f(g[0]) ? 2 : 1;
                    d.sort(function (e, t) {
                        return e.time - t.time
                    });
                    var w;
                    if (!m)return;
                    w = d[m - 1].time;
                    var E = [], S = [];
                    for (var x = 0; x < m; x++) {
                        E.push(d[x].time / w);
                        if (y)if (b == 2) {
                            S[x] = [];
                            for (var T = 0; T < g.length; T++)S[x].push(r.call(d[x].value[T]))
                        } else S.push(r.call(d[x].value)); else S.push(d[x].value)
                    }
                    var N = 0, C = 0, k, x, L, A, O, M, _, D = function (e, t) {
                        if (t < C) {
                            k = Math.min(N + 1, m - 1);
                            for (x = k; x >= 0; x--)if (E[x] <= t)break;
                            x = Math.min(x, m - 2)
                        } else {
                            for (x = N; x < m; x++)if (E[x] > t)break;
                            x = Math.min(x - 1, m - 2)
                        }
                        N = x, C = t;
                        var r = E[x + 1] - E[x];
                        if (r === 0)return;
                        L = (t - E[x]) / r, L < 0 && console.log(L), h ? (O = S[x], A = S[x === 0 ? x : x - 1], M = S[x > m - 2 ? m - 1 : x + 1], _ = S[x > m - 3 ? m - 1 : x + 2], y ? l(A, O, M, _, L, L * L, L * L * L, s(e, v), b) : i(e, v, c(A, O, M, _, L, L * L, L * L * L))) : y ? a(S[x], S[x + 1], L, s(e, v), b) : i(e, v, u(S[x], S[x + 1], L));
                        for (x = 0; x < o; x++)n._onframeList[x](e, t)
                    }, P = new t({target: n._target, life: w, loop: n._loop, delay: n._delay, onframe: D, ondestroy: p});
                    e && e !== "spline" && (P.easing = e), n._clipList.push(P), n._clipCount++, n.animation.add(P)
                };
            for (var v in this._tracks)d(this._tracks[v], v);
            return this
        }, stop: function () {
            for (var e = 0; e < this._clipList.length; e++) {
                var t = this._clipList[e];
                this.animation.remove(t)
            }
            this._clipList = []
        }, delay: function (e) {
            return this._delay = e, this
        }, done: function (e) {
            return this._doneList.push(e), this
        }
    }, i
}), define("zrender/config", {
    loadingEffect: "spin",
    EVENT: {
        RESIZE: "resize",
        CLICK: "click",
        MOUSEWHEEL: "mousewheel",
        MOUSEMOVE: "mousemove",
        MOUSEOVER: "mouseover",
        MOUSEOUT: "mouseout",
        MOUSEDOWN: "mousedown",
        MOUSEUP: "mouseup",
        GLOBALOUT: "globalout",
        DRAGSTART: "dragstart",
        DRAGEND: "dragend",
        DRAGENTER: "dragenter",
        DRAGOVER: "dragover",
        DRAGLEAVE: "dragleave",
        DROP: "drop",
        touchClickDelay: 300
    }
}), define("zrender/tool/loadingEffect", ["require", "./util", "./color", "./color", "./color", "./color", "./color", "./math"], function (e) {
    function u(e, t) {
        n[e] = t
    }

    function a(e) {
        return {
            shape: "text",
            highlightStyle: t.merge({
                x: s / 2,
                y: o / 2,
                text: r,
                textAlign: "center",
                textBaseline: "middle",
                textFont: i,
                color: "#333",
                brushType: "fill"
            }, e, {overwrite: !0, recursive: !0})
        }
    }

    function f(e) {
        return {shape: "rectangle", highlightStyle: {x: 0, y: 0, width: s, height: o, brushType: "fill", color: e}}
    }

    function l(e, t) {
        return e <= t[0] ? e = t[0] : e >= t[1] && (e = t[1]), e
    }

    function c(n, r, i) {
        var u = e("./color");
        n = t.merge(n, {
            textStyle: {color: "#888"},
            backgroundColor: "rgba(250, 250, 250, 0.8)",
            effectOption: {x: 0, y: o / 2 - 30, width: s, height: 5, brushType: "fill", timeInterval: 100}
        }, {overwrite: !1, recursive: !0});
        var c = a(n.textStyle), h = f(n.backgroundColor), p = n.effectOption,
            d = {shape: "rectangle", highlightStyle: t.clone(p)};
        d.highlightStyle.color = p.color || u.getLinearGradient(p.x, p.y, p.x + p.width, p.y + p.height, [[0, "#ff6400"], [.5, "#ffe100"], [1, "#b1ff00"]]);
        if (typeof n.progress != "undefined") {
            r(h), d.highlightStyle.width = l(n.progress, [0, 1]) * n.effectOption.width, r(d), r(c), i();
            return
        }
        return d.highlightStyle.width = 0, setInterval(function () {
            r(h), d.highlightStyle.width < n.effectOption.width ? d.highlightStyle.width += 8 : d.highlightStyle.width = 0, r(d), r(c), i()
        }, p.timeInterval)
    }

    function h(e, n, r) {
        e.effectOption = t.merge(e.effectOption || {}, {
            x: s / 2 - 80,
            y: o / 2,
            r: 18,
            colorIn: "#fff",
            colorOut: "#555",
            colorWhirl: "#6cf",
            timeInterval: 50
        });
        var i = e.effectOption;
        e = t.merge(e, {
            textStyle: {color: "#888", x: i.x + i.r + 10, y: i.y, textAlign: "start"},
            backgroundColor: "rgba(250, 250, 250, 0.8)"
        }, {overwrite: !1, recursive: !0});
        var u = a(e.textStyle), l = f(e.backgroundColor), c = {
            shape: "droplet",
            highlightStyle: {
                a: Math.round(i.r / 2),
                b: Math.round(i.r - i.r / 6),
                brushType: "fill",
                color: i.colorWhirl
            }
        }, h = {shape: "circle", highlightStyle: {r: Math.round(i.r / 6), brushType: "fill", color: i.colorIn}}, p = {
            shape: "ring",
            highlightStyle: {r0: Math.round(i.r - i.r / 3), r: i.r, brushType: "fill", color: i.colorOut}
        }, d = [0, i.x, i.y];
        return c.highlightStyle.x = h.highlightStyle.x = p.highlightStyle.x = d[1], c.highlightStyle.y = h.highlightStyle.y = p.highlightStyle.y = d[2], setInterval(function () {
            n(l), n(p), d[0] -= .3, c.rotation = d, n(c), n(h), n(u), r()
        }, i.timeInterval)
    }

    function p(n, r, i) {
        var u = e("./color");
        n = t.merge(n, {
            textStyle: {color: "#fff"},
            backgroundColor: "rgba(0, 0, 0, 0.8)",
            effectOption: {n: 30, lineWidth: 1, color: "random", timeInterval: 100}
        }, {overwrite: !1, recursive: !0});
        var l = a(n.textStyle), c = f(n.backgroundColor), h = n.effectOption, p = h.n, d = h.lineWidth, v = [], m, g, y,
            b;
        for (var w = 0; w < p; w++)y = -Math.ceil(Math.random() * 1e3), g = Math.ceil(Math.random() * 400), m = Math.ceil(Math.random() * o), h.color == "random" ? b = u.random() : b = h.color, v[w] = {
            shape: "line",
            highlightStyle: {xStart: y, yStart: m, xEnd: y + g, yEnd: m, strokeColor: b, lineWidth: d},
            animationX: Math.ceil(Math.random() * 100),
            len: g
        };
        return setInterval(function () {
            r(c);
            var e;
            for (var t = 0; t < p; t++)e = v[t].highlightStyle, e.xStart >= s && (v[t].len = Math.ceil(Math.random() * 400), v[t].highlightStyle.xStart = -400, v[t].highlightStyle.xEnd = -400 + v[t].len, v[t].highlightStyle.yStart = Math.ceil(Math.random() * o), v[t].highlightStyle.yEnd = v[t].highlightStyle.yStart), v[t].highlightStyle.xStart += v[t].animationX, v[t].highlightStyle.xEnd += v[t].animationX, r(v[t]);
            r(l), i()
        }, h.timeInterval)
    }

    function d(n, r, i) {
        var u = e("./color");
        n = t.merge(n, {
            textStyle: {color: "#888"},
            backgroundColor: "rgba(250, 250, 250, 0.8)",
            effectOption: {n: 50, lineWidth: 2, brushType: "stroke", color: "random", timeInterval: 100}
        }, {overwrite: !1, recursive: !0});
        var l = a(n.textStyle), c = f(n.backgroundColor), h = n.effectOption, p = h.n, d = h.brushType, v = h.lineWidth,
            m = [], g;
        for (var y = 0; y < p; y++)h.color == "random" ? g = u.alpha(u.random(), .3) : g = h.color, m[y] = {
            shape: "circle",
            highlightStyle: {
                x: Math.ceil(Math.random() * s),
                y: Math.ceil(Math.random() * o),
                r: Math.ceil(Math.random() * 40),
                brushType: d,
                color: g,
                strokeColor: g,
                lineWidth: v
            },
            animationY: Math.ceil(Math.random() * 20)
        };
        return setInterval(function () {
            r(c);
            var e;
            for (var t = 0; t < p; t++)e = m[t].highlightStyle, e.y - m[t].animationY + e.r <= 0 && (m[t].highlightStyle.y = o + e.r, m[t].highlightStyle.x = Math.ceil(Math.random() * s)), m[t].highlightStyle.y -= m[t].animationY, r(m[t]);
            r(l), i()
        }, h.timeInterval)
    }

    function v(n, r, i) {
        var u = e("./color");
        n.effectOption = t.merge(n.effectOption || {}, {
            x: s / 2 - 80,
            y: o / 2,
            r0: 9,
            r: 15,
            n: 18,
            color: "#fff",
            timeInterval: 100
        });
        var l = n.effectOption;
        n = t.merge(n, {
            textStyle: {color: "#fff", x: l.x + l.r + 10, y: l.y, textAlign: "start"},
            backgroundColor: "rgba(0, 0, 0, 0.8)"
        }, {overwrite: !1, recursive: !0});
        var c = a(n.textStyle), h = f(n.backgroundColor), p = l.n, d = l.x, v = l.y, m = l.r0, g = l.r, y = l.color,
            b = [], w = Math.round(180 / p);
        for (var E = 0; E < p; E++)b[E] = {
            shape: "sector",
            highlightStyle: {
                x: d,
                y: v,
                r0: m,
                r: g,
                startAngle: w * E * 2,
                endAngle: w * E * 2 + w,
                color: u.alpha(y, (E + 1) / p),
                brushType: "fill"
            }
        };
        var S = [0, d, v];
        return setInterval(function () {
            r(h), S[0] -= .3;
            for (var e = 0; e < p; e++)b[e].rotation = S, r(b[e]);
            r(c), i()
        }, l.timeInterval)
    }

    function m(n, r, i) {
        var u = e("./color"), c = e("./math");
        n = t.merge(n, {
            textStyle: {color: "#07a"},
            backgroundColor: "rgba(250, 250, 250, 0.8)",
            effectOption: {
                x: s / 2,
                y: o / 2,
                r0: 60,
                r: 100,
                color: "#bbdcff",
                brushType: "fill",
                textPosition: "inside",
                textFont: "normal 30px verdana",
                textColor: "rgba(30, 144, 255, 0.6)",
                timeInterval: 100
            }
        }, {overwrite: !1, recursive: !0});
        var h = n.effectOption, p = n.textStyle;
        p.x = typeof p.x != "undefined" ? p.x : h.x, p.y = typeof p.y != "undefined" ? p.y : h.y + (h.r0 + h.r) / 2 - 5;
        var d = a(n.textStyle), v = f(n.backgroundColor), m = h.x, g = h.y, y = h.r0 + 6, b = h.r - 6, w = h.color,
            E = u.lift(w, .1), S = {shape: "ring", highlightStyle: t.clone(h)}, x = [],
            T = u.getGradientColors(["#ff6400", "#ffe100", "#97ff00"], 25), N = 15, C = 240;
        for (var k = 0; k < 16; k++)x.push({
            shape: "sector",
            highlightStyle: {x: m, y: g, r0: y, r: b, startAngle: C - N, endAngle: C, brushType: "fill", color: E},
            _color: u.getLinearGradient(m + y * c.cos(C, !0), g - y * c.sin(C, !0), m + y * c.cos(C - N, !0), g - y * c.sin(C - N, !0), [[0, T[k * 2]], [1, T[k * 2 + 1]]])
        }), C -= N;
        C = 360;
        for (var k = 0; k < 4; k++)x.push({
            shape: "sector",
            highlightStyle: {x: m, y: g, r0: y, r: b, startAngle: C - N, endAngle: C, brushType: "fill", color: E},
            _color: u.getLinearGradient(m + y * c.cos(C, !0), g - y * c.sin(C, !0), m + y * c.cos(C - N, !0), g - y * c.sin(C - N, !0), [[0, T[k * 2 + 32]], [1, T[k * 2 + 33]]])
        }), C -= N;
        var L = 0;
        if (typeof n.progress != "undefined") {
            r(v), L = l(n.progress, [0, 1]).toFixed(2) * 100 / 5, S.highlightStyle.text = L * 5 + "%", r(S);
            for (var k = 0; k < 20; k++)x[k].highlightStyle.color = k < L ? x[k]._color : E, r(x[k]);
            r(d), i();
            return
        }
        return setInterval(function () {
            r(v), L += L >= 20 ? -20 : 1, r(S);
            for (var e = 0; e < 20; e++)x[e].highlightStyle.color = e < L ? x[e]._color : E, r(x[e]);
            r(d), i()
        }, h.timeInterval)
    }

    function g(e, t, r) {
        var i = n.ring;
        return typeof e.effect == "function" ? i = e.effect : typeof n[e.effect] == "function" && (i = n[e.effect]), s = e.canvasSize.width, o = e.canvasSize.height, i(e, t, r)
    }

    function y(e) {
        clearInterval(e)
    }

    var t = e("./util"), n, r = "Loading...", i = "normal 16px Arial", s, o;
    return n = {
        getBackgroundShape: f,
        getTextShape: a,
        define: u,
        bar: c,
        whirling: h,
        dynamicLine: p,
        bubble: d,
        spin: v,
        ring: m,
        start: g,
        stop: y
    }, n
}), define("zrender/tool/event", [], function () {
    function e(e) {
        return typeof e.zrenderX != "undefined" && e.zrenderX || typeof e.offsetX != "undefined" && e.offsetX || typeof e.layerX != "undefined" && e.layerX || typeof e.clientX != "undefined" && e.clientX
    }

    function t(e) {
        return typeof e.zrenderY != "undefined" && e.zrenderY || typeof e.offsetY != "undefined" && e.offsetY || typeof e.layerY != "undefined" && e.layerY || typeof e.clientY != "undefined" && e.clientY
    }

    function n(e) {
        return typeof e.wheelDelta != "undefined" && e.wheelDelta || typeof e.detail != "undefined" && -e.detail
    }

    function r(e) {
        e.preventDefault ? (e.preventDefault(), e.stopPropagation()) : (e.returnValue = !1, e.cancelBubble = !0)
    }

    function i() {
        function n(n, r) {
            return !r || !n ? e : (t[n] || (t[n] = []), t[n].push({h: r, one: !0}), e)
        }

        function r(n, r) {
            return !r || !n ? e : (t[n] || (t[n] = []), t[n].push({h: r, one: !1}), e)
        }

        function i(n, r) {
            if (!n)return t = {}, e;
            if (r) {
                if (t[n]) {
                    var i = [];
                    for (var s = 0, o = t[n].length; s < o; s++)t[n][s]["h"] != r && i.push(t[n][s]);
                    t[n] = i
                }
                t[n] && t[n].length === 0 && delete t[n]
            } else delete t[n];
            return e
        }

        function s(n, r, i) {
            if (t[n]) {
                var s = [], o = i || {};
                o.type = n, o.event = r;
                for (var u = 0, a = t[n].length; u < a; u++)t[n][u].h(o), t[n][u].one || s.push(t[n][u]);
                s.length != t[n].length && (t[n] = s)
            }
            return e
        }

        var e = this, t = {};
        e.one = n, e.bind = r, e.unbind = i, e.dispatch = s
    }

    return {getX: e, getY: t, getDelta: n, stop: r, Dispatcher: i}
}), define("zrender/zrender", ["require", "./lib/excanvas", "./tool/util", "./tool/env", "./shape", "./shape/circle", "./shape/ellipse", "./shape/line", "./shape/polygon", "./shape/brokenLine", "./shape/rectangle", "./shape/ring", "./shape/sector", "./shape/text", "./shape/heart", "./shape/droplet", "./shape/path", "./shape/image", "./shape/beziercurve", "./shape/star", "./shape/isogon", "./animation/animation", "./config", "./tool/loadingEffect", "./tool/loadingEffect", "./config", "./tool/env", "./tool/event"], function (e) {
    function o(n, i, s) {
        var o = this;
        o.env = e("./tool/env");
        var l = e("./shape");
        e("./shape/circle"), e("./shape/ellipse"), e("./shape/line"), e("./shape/polygon"), e("./shape/brokenLine"), e("./shape/rectangle"), e("./shape/ring"), e("./shape/sector"), e("./shape/text"), e("./shape/heart"), e("./shape/droplet"), e("./shape/path"), e("./shape/image"), e("./shape/beziercurve"), e("./shape/star"), e("./shape/isogon");
        var c;
        if (typeof s.shape == "undefined") c = l; else {
            c = {};
            for (var h in s.shape)c[h] = s.shape[h];
            c.get = function (e) {
                return c[e] || l.get(e)
            }
        }
        var p = new u(c), d = new a(i, p, c), v = new f(i, p, d, c), m = e("./animation/animation"), g = [], y = new m({
            stage: {
                update: function () {
                    var e = g;
                    for (var t = 0, n = e.length; t < n; t++)p.mod(e[t].id);
                    e.length > 0 && d.refresh()
                }
            }
        });
        y.start(), o.getId = function () {
            return n
        }, o.addShape = function (e) {
            return p.add(e), o
        }, o.delShape = function (e) {
            return p.del(e), o
        }, o.modShape = function (e, t, n) {
            return p.mod(e, t, n), o
        }, o.modLayer = function (e, t) {
            d.modLayer(e, t)
        }, o.addHoverShape = function (e) {
            return p.addHover(e), o
        }, o.render = function (e) {
            return d.render(e), o
        }, o.refresh = function (e) {
            return d.refresh(e), o
        }, o.refreshHover = function (e) {
            return d.refreshHover(e), o
        }, o.update = function (e, t) {
            return d.update(e, t), o
        }, o.resize = function () {
            return d.resize(), o
        }, o.animate = function (e, n, i) {
            var s = p.get(e);
            if (s) {
                var o;
                if (n) {
                    var u = n.split("."), a = s;
                    for (var f = 0, l = u.length; f < l; f++) {
                        if (!a)continue;
                        a = a[u[f]]
                    }
                    a && (o = a)
                } else o = s;
                if (!o) {
                    r.log('Property "' + n + '" is not existed in shape ' + e);
                    return
                }
                return typeof s.__aniCount == "undefined" && (s.__aniCount = 0), s.__aniCount === 0 && g.push(s), s.__aniCount++, y.animate(o, {loop: i}).done(function () {
                    s.__aniCount--;
                    if (s.__aniCount === 0) {
                        var e = t.indexOf(g, s);
                        g.splice(e, 1)
                    }
                })
            }
            r.log('Shape "' + e + '" not existed')
        }, o.clearAnimation = function () {
            y.clear()
        }, o.showLoading = function (e) {
            return d.showLoading(e), o
        }, o.hideLoading = function () {
            return d.hideLoading(), o
        }, o.newShapeId = function (e) {
            return p.newShapeId(e)
        }, o.getWidth = function () {
            return d.getWidth()
        }, o.getHeight = function () {
            return d.getHeight()
        }, o.toDataURL = function (e, t, n) {
            return d.toDataURL(e, t, n)
        }, o.shapeToImage = function (e, t, n) {
            var r = o.newShapeId("image");
            return d.shapeToImage(r, e, t, n)
        }, o.on = function (e, t) {
            return v.on(e, t), o
        }, o.un = function (e, t) {
            return v.un(e, t), o
        }, o.trigger = function (e, t) {
            return v.trigger(e, t), o
        }, o.clear = function () {
            return p.del(), d.clear(), o
        }, o.dispose = function () {
            y.stop(), y = null, g = null, o.clear(), o = null, p.dispose(), p = null, d.dispose(), d = null, v.dispose(), v = null, r.delInstance(n);
            return
        }
    }

    function u(e) {
        function l(e) {
            e.hoverable || e.onclick || e.draggable || e.onmousemove || e.onmouseover || e.onmouseout || e.onmousedown || e.onmouseup || e.ondragenter || e.ondragover || e.ondragleave || e.ondrop ? e.__silent = !1 : e.__silent = !0, Math.abs(e.rotation[0]) > 1e-4 || Math.abs(e.position[0]) > 1e-4 || Math.abs(e.position[1]) > 1e-4 || Math.abs(e.scale[0] - 1) > 1e-4 || Math.abs(e.scale[1] - 1) > 1e-4 ? e.__needTransform = !0 : e.__needTransform = !1, e.style = e.style || {}, e.style.__rect = null
        }

        function c(e) {
            return (e || "") + ++i
        }

        function h(e) {
            var r = {
                shape: "circle",
                id: e.id || n.newShapeId(),
                zlevel: 0,
                draggable: !1,
                clickable: !1,
                hoverable: !0,
                position: [0, 0],
                rotation: [0, 0, 0],
                scale: [1, 1, 0, 0]
            };
            return t.merge(r, e, {
                overwrite: !0,
                recursive: !0
            }), l(r), s[r.id] = r, o[r.zlevel] = o[r.zlevel] || [], o[r.zlevel].push(r), a = Math.max(a, r.zlevel), f[r.zlevel] = !0, n
        }

        function p(e) {
            return s[e]
        }

        function d(e) {
            if (typeof e != "undefined") {
                var t = {};
                if (e instanceof Array) {
                    if (e.lenth < 1)return;
                    for (var r = 0, i = e.length; r < i; r++)t[e[r].id] = !0
                } else t[e] = !0;
                var l, c, h, p = {};
                for (var d in t)if (s[d]) {
                    h = s[d].zlevel, f[h] = !0;
                    if (!p[h]) {
                        c = o[h], l = [];
                        for (var r = 0, i = c.length; r < i; r++)t[c[r].id] || l.push(c[r]);
                        o[h] = l, p[h] = !0
                    }
                    delete s[d]
                }
            } else s = {}, o = [], u = [], a = 0, f = {all: !0};
            return n
        }

        function v(e, r, i) {
            var o = s[e];
            return o && (f[o.zlevel] = !0, r && (i ? t.mergeFast(o, r, !0, !0) : t.merge(o, r, {
                overwrite: !0,
                recursive: !0
            })), l(o), f[o.zlevel] = !0, a = Math.max(a, o.zlevel)), n
        }

        function m(t, i, o) {
            var u = s[t];
            if (!u)return;
            u.__needTransform = !0;
            if (!u.ondrift || u.ondrift && !u.ondrift(u, i, o))if (r.catchBrushException)try {
                e.get(u.shape).drift(u, i, o)
            } catch (a) {
                r.log(a, "drift error of " + u.shape, u)
            } else e.get(u.shape).drift(u, i, o);
            return f[u.zlevel] = !0, n
        }

        function g(e) {
            return e.rotation && Math.abs(e.rotation[0]) > 1e-4 || e.position && (Math.abs(e.position[0]) > 1e-4 || Math.abs(e.position[1]) > 1e-4) || e.scale && (Math.abs(e.scale[0] - 1) > 1e-4 || Math.abs(e.scale[1] - 1) > 1e-4) ? e.__needTransform = !0 : e.__needTransform = !1, u.push(e), n
        }

        function y() {
            return u = [], n
        }

        function b() {
            return u.length > 0
        }

        function w(e, t) {
            t || (t = {hover: !1, normal: "down"});
            if (t.hover)for (var r = 0, i = u.length; r < i; r++)if (e(u[r]))return n;
            var a, f;
            if (typeof t.normal != "undefined")switch (t.normal) {
                case"down":
                    for (var i = o.length - 1; i >= 0; i--) {
                        a = o[i];
                        if (a) {
                            f = a.length;
                            while (f--)if (e(a[f]))return n
                        }
                    }
                    break;
                case"up":
                    for (var r = 0, i = o.length; r < i; r++) {
                        a = o[r];
                        if (a) {
                            f = a.length;
                            for (var l = 0; l < f; l++)if (e(a[l]))return n
                        }
                    }
                    break;
                default:
                    for (var r in s)if (e(s[r]))return n
            }
            return n
        }

        function E() {
            return a
        }

        function S() {
            return f
        }

        function x() {
            return f = {}, n
        }

        function T(e) {
            return f[e] = !0, n
        }

        function N() {
            s = null, o = null, u = null, n = null;
            return
        }

        var n = this, i = 0, s = {}, o = [], u = [], a = 0, f = {};
        n.newShapeId = c, n.add = h, n.get = p, n.del = d, n.addHover = g, n.delHover = y, n.hasHoverShape = b, n.mod = v, n.drift = m, n.iterShape = w, n.getMaxZlevel = E, n.getChangedZlevel = S, n.clearChangedZlevel = x, n.setChangedZlevle = T, n.dispose = N
    }

    function a(n, i, s) {
        function b() {
            var e = n.currentStyle || document.defaultView.getComputedStyle(n);
            return ((n.clientWidth || parseInt(e.width, 10)) - parseInt(e.paddingLeft, 10) - parseInt(e.paddingRight, 10)).toFixed(0) - 0
        }

        function w() {
            var e = n.currentStyle || document.defaultView.getComputedStyle(n);
            return ((n.clientHeight || parseInt(e.height, 10)) - parseInt(e.paddingTop, 10) - parseInt(e.paddingBottom, 10)).toFixed(0) - 0
        }

        function E() {
            v.innerHTML = "", n.innerHTML = "", m = b(), g = w(), v.style.position = "relative", v.style.overflow = "hidden", v.style.width = m + "px", v.style.height = g + "px", n.appendChild(v), a = {}, f = {}, l = {}, c = {}, p = i.getMaxZlevel(), a.bg = x("bg", "div"), v.appendChild(a.bg);
            for (var e = 0; e <= p; e++)a[e] = x(e, "canvas"), v.appendChild(a[e]), G_vmlCanvasManager && G_vmlCanvasManager.initElement(a[e]), f[e] = a[e].getContext("2d"), y != 1 && f[e].scale(y, y);
            a.hover = x("hover", "canvas"), a.hover.id = "_zrender_hover_", v.appendChild(a.hover), G_vmlCanvasManager && G_vmlCanvasManager.initElement(a.hover), f.hover = a.hover.getContext("2d"), y != 1 && f.hover.scale(y, y)
        }

        function S() {
            var e = i.getMaxZlevel();
            if (p < e) {
                for (var t = p + 1; t <= e; t++)a[t] = x(t, "canvas"), v.insertBefore(a[t], a.hover), G_vmlCanvasManager && G_vmlCanvasManager.initElement(a[t]), f[t] = a[t].getContext("2d"), y != 1 && f[t].scale(y, y);
                p = e
            }
        }

        function x(e, t) {
            var n = document.createElement(t);
            return n.style.position = "absolute", n.style.left = 0, n.style.top = 0, n.style.width = m + "px", n.style.height = g + "px", n.setAttribute("width", m * y), n.setAttribute("height", g * y), n.setAttribute("data-id", e), n
        }

        function T(e) {
            return function (t) {
                if ((e.all || e[t.zlevel]) && !t.invisible) {
                    var n = f[t.zlevel];
                    if (n) {
                        if (!t.onbrush || t.onbrush && !t.onbrush(n, t, !1))if (r.catchBrushException)try {
                            s.get(t.shape).brush(n, t, !1, L)
                        } catch (i) {
                            r.log(i, "brush error of " + t.shape, t)
                        } else s.get(t.shape).brush(n, t, !1, L)
                    } else r.log("can not find the specific zlevel canvas!")
                }
            }
        }

        function N(e) {
            var t = f.hover;
            if (!e.onbrush || e.onbrush && !e.onbrush(t, e, !0))if (r.catchBrushException)try {
                s.get(e.shape).brush(t, e, !0, L)
            } catch (n) {
                r.log(n, "hoverBrush error of " + e.shape, e)
            } else s.get(e.shape).brush(t, e, !0, L)
        }

        function C(e) {
            return B() && H(), S(), A(), i.iterShape(T({all: !0}), {normal: "up"}), i.clearChangedZlevel(), typeof e == "function" && e(), u
        }

        function k(e) {
            S();
            var t = i.getChangedZlevel();
            if (t.all) A(); else for (var n in t)f[n] && O(n);
            return i.iterShape(T(t), {normal: "up"}), i.clearChangedZlevel(), typeof e == "function" && e(), u
        }

        function L(e, t) {
            var n;
            for (var r = 0, s = e.length; r < s; r++)n = e[r], i.mod(n.id, n);
            return k(t), u
        }

        function A() {
            for (var e in f) {
                if (e == "hover")continue;
                O(e)
            }
            return u
        }

        function O(e) {
            if (h[e]) {
                var t = typeof h[e].clearColor != "undefined", n = h[e].motionBlur, r = h[e].lastFrameAlpha;
                typeof r == "undefined" && (r = .7);
                if (n) {
                    if (typeof l[e] == "undefined") {
                        var i = x("back-" + e, "canvas");
                        i.width = a[e].width, i.height = a[e].height, i.style.width = a[e].style.width, i.style.height = a[e].style.height, l[e] = i, c[e] = i.getContext("2d"), y != 1 && c[e].scale(y, y)
                    }
                    c[e].globalCompositeOperation = "copy", c[e].drawImage(a[e], 0, 0, a[e].width / y, a[e].height / y)
                }
                t ? (f[e].save(), f[e].fillStyle = h[e].clearColor, f[e].fillRect(0, 0, m * y, g * y), f[e].restore()) : f[e].clearRect(0, 0, m * y, g * y);
                if (n) {
                    var i = l[e], s = f[e];
                    s.save(), s.globalAlpha = r, s.drawImage(i, 0, 0, i.width / y, i.height / y), s.restore()
                }
            } else f[e].clearRect(0, 0, m * y, g * y)
        }

        function M(e, n) {
            n && (typeof h[e] == "undefined" && (h[e] = {}), t.merge(h[e], n, {recursive: !0, overwrite: !0}))
        }

        function _() {
            return D(), i.iterShape(N, {hover: !0}), i.delHover(), u
        }

        function D() {
            return f && f.hover && f.hover.clearRect(0, 0, m * y, g * y), u
        }

        function P(t) {
            var n = e("./tool/loadingEffect");
            return n.stop(d), t = t || {}, t.effect = t.effect || o.loadingEffect, t.canvasSize = {
                width: m,
                height: g
            }, d = n.start(t, i.addHover, _), u.loading = !0, u
        }

        function H() {
            var t = e("./tool/loadingEffect");
            return t.stop(d), D(), u.loading = !1, u
        }

        function B() {
            return u.loading
        }

        function j() {
            return m
        }

        function F() {
            return g
        }

        function I() {
            var e, t, n;
            v.style.display = "none", e = b(), t = w(), v.style.display = "";
            if (m != e || t != g) {
                m = e, g = t, v.style.width = m + "px", v.style.height = g + "px";
                for (var r in a)n = a[r], n.setAttribute("width", m), n.setAttribute("height", g), n.style.width = m + "px", n.style.height = g + "px";
                i.setChangedZlevle("all"), k()
            }
            return u
        }

        function q() {
            B() && H(), n.innerHTML = "", n = null, i = null, s = null, v = null, a = null, f = null, c = null, l = null, u = null;
            return
        }

        function R() {
            return a.hover
        }

        function U(e, t, n) {
            if (G_vmlCanvasManager)return null;
            var o = x("image", "canvas");
            a.bg.appendChild(o);
            var u = o.getContext("2d");
            y != 1 && u.scale(y, y), u.fillStyle = t || "#fff", u.rect(0, 0, m * y, g * y), u.fill(), i.iterShape(function (e) {
                if (!e.invisible)if (!e.onbrush || e.onbrush && !e.onbrush(u, e, !1))if (r.catchBrushException)try {
                    s.get(e.shape).brush(u, e, !1, L)
                } catch (t) {
                    r.log(t, "brush error of " + e.shape, e)
                } else s.get(e.shape).brush(u, e, !1, L)
            }, {normal: "up"});
            var f = o.toDataURL(e, n);
            return u = null, a.bg.removeChild(o), f
        }

        var o = e("./config"), u = this, a = {}, f = {}, l = {}, c = {}, h = {}, p = 0, d,
            v = document.createElement("div");
        v.onselectstart = function () {
            return !1
        };
        var m, g, y = window.devicePixelRatio || 1, z = function () {
            if (G_vmlCanvasManager)return function () {
            };
            var e = document.createElement("canvas"), t = e.getContext("2d"), n = window.devicePixelRatio || 1;
            return function (r, i, o, u) {
                e.style.width = o + "px", e.style.height = u + "px", e.setAttribute("width", o * n), e.setAttribute("height", u * n), t.clearRect(0, 0, o * n, u * n);
                var a = s.get(i.shape), f = {position: i.position, rotation: i.rotation, scale: i.scale};
                i.position = [0, 0, 0], i.rotation = 0, i.scale = [1, 1], a && a.brush(t, i, !1);
                var l = {shape: "image", id: r, style: {x: 0, y: 0, image: e.toDataURL()}};
                return typeof f.position != "undefined" && (l.position = i.position = f.position), typeof f.rotation != "undefined" && (l.rotation = i.rotation = f.rotation), typeof f.scale != "undefined" && (l.scale = i.scale = f.scale), l
            }
        }();
        u.render = C, u.refresh = k, u.update = L, u.clear = A, u.modLayer = M, u.refreshHover = _, u.clearHover = D, u.showLoading = P, u.hideLoading = H, u.isLoading = B, u.getWidth = j, u.getHeight = F, u.resize = I, u.dispose = q, u.getDomHover = R, u.toDataURL = U, u.shapeToImage = z, E()
    }

    function f(t, n, r, i) {
        function C() {
            window.addEventListener ? (window.addEventListener("resize", k), !o.os.tablet && !o.os.phone ? (t.addEventListener("click", L), t.addEventListener("mousewheel", A), t.addEventListener("mousemove", O), t.addEventListener("mousedown", P), t.addEventListener("mouseup", H)) : (t.addEventListener("touchstart", B), t.addEventListener("touchmove", j), t.addEventListener("touchend", F)), t.addEventListener("DOMMouseScroll", A), t.addEventListener("mouseout", M)) : (window.attachEvent("onresize", k), t.attachEvent("onclick", L), t.attachEvent("onmousewheel", A), t.attachEvent("onmousemove", O), t.attachEvent("onmouseout", M), t.attachEvent("onmousedown", P), t.attachEvent("onmouseup", H))
        }

        function k(e) {
            c = e || window.event, p = null, m = !1, a.dispatch(s.EVENT.RESIZE, c)
        }

        function L(e) {
            c = J(e), p ? p && p.clickable && X(p, s.EVENT.CLICK) : X(p, s.EVENT.CLICK), O(c)
        }

        function A(e) {
            c = J(e), X(p, s.EVENT.MOUSEWHEEL), O(c)
        }

        function O(e) {
            if (r.isLoading())return;
            c = J(e), E = x, S = T, x = f(c), T = l(c), I(), h = !1, n.iterShape($, {normal: "down"});
            if (!h) {
                if (!v || p && p.id != v.id) D(), U();
                p = null, n.delHover(), r.clearHover()
            }
            v && (n.drift(v.id, x - E, T - S), n.addHover(v)), v || h && p.draggable ? t.style.cursor = "move" : h && p.clickable ? t.style.cursor = "pointer" : t.style.cursor = "default", X(p, s.EVENT.MOUSEMOVE), (v || h || n.hasHoverShape()) && r.refreshHover()
        }

        function M(e) {
            c = J(e);
            var n = c.toElement || c.relatedTarget;
            if (n != t)while (n && n.nodeType != 9) {
                if (n == t) {
                    O(e);
                    return
                }
                n = n.parentNode
            }
            c.zrenderX = E, c.zrenderY = S, t.style.cursor = "default", m = !1, D(), z(), W(), r.isLoading() || r.refreshHover(), a.dispatch(s.EVENT.GLOBALOUT, c)
        }

        function _() {
            X(p, s.EVENT.MOUSEOVER)
        }

        function D() {
            X(p, s.EVENT.MOUSEOUT)
        }

        function P(e) {
            if (w == 2) {
                w = e.button, d = null;
                return
            }
            y = new Date, c = J(e), m = !0, d = p, X(p, s.EVENT.MOUSEDOWN), w = e.button
        }

        function H(e) {
            c = J(e), t.style.cursor = "default", m = !1, d = null, X(p, s.EVENT.MOUSEUP), z(), W()
        }

        function B(e) {
            c = J(e, !0), b = new Date, V(), P(c)
        }

        function j(e) {
            c = J(e, !0), O(c), g && u.stop(e)
        }

        function F(e) {
            c = J(e, !0), H(c), new Date - b < s.EVENT.touchClickDelay && (V(), L(c)), r.clearHover()
        }

        function I() {
            if (m && p && p.draggable && !v && d == p) {
                if (p.dragEnableTime && new Date - y < p.dragEnableTime)return;
                v = p, g = !0, v.invisible = !0, n.mod(v.id, v), X(v, s.EVENT.DRAGSTART), r.refresh()
            }
        }

        function q() {
            v && X(p, s.EVENT.DRAGENTER, v)
        }

        function R() {
            v && X(p, s.EVENT.DRAGOVER, v)
        }

        function U() {
            v && X(p, s.EVENT.DRAGLEAVE, v)
        }

        function z() {
            v && (v.invisible = !1, n.mod(v.id, v), r.refresh(), X(p, s.EVENT.DROP, v))
        }

        function W() {
            v && (X(v, s.EVENT.DRAGEND), p = null), g = !1, v = null
        }

        function X(e, t, n) {
            var r = "on" + t, i = {type: t, event: c, target: e};
            n && (i.dragged = n), e ? (!e[r] || !e[r](i)) && a.dispatch(t, c, i) : n || a.dispatch(t, c)
        }

        function V() {
            p = null, x = c.zrenderX, T = c.zrenderY, n.iterShape($, {normal: "down"}), p || (x += 10, n.iterShape($, {normal: "down"})), p || (x -= 20, n.iterShape($, {normal: "down"})), p || (x += 10, T += 10, n.iterShape($, {normal: "down"})), p || (T -= 20, n.iterShape($, {normal: "down"})), p && (c.zrenderX = x, c.zrenderY = T)
        }

        function $(e) {
            if (v && v.id == e.id)return !1;
            if (e.__silent)return !1;
            var t = i.get(e.shape);
            return t.isCover(e, x, T) ? (e.hoverable && n.addHover(e), p != e && (D(), U(), p = e, q()), _(), R(), h = !0, !0) : !1
        }

        function J(e, n) {
            if (!n) {
                c = e || window.event;
                var r = c.toElement || c.relatedTarget || c.srcElement || c.target;
                r && r != N && (c.zrenderX = (typeof c.offsetX != "undefined" ? c.offsetX : c.layerX) + r.offsetLeft, c.zrenderY = (typeof c.offsetY != "undefined" ? c.offsetY : c.layerY) + r.offsetTop)
            } else {
                c = e;
                var i = c.type != "touchend" ? c.targetTouches[0] : c.changedTouches[0];
                i && (c.zrenderX = i.clientX - t.offsetLeft + document.body.scrollLeft, c.zrenderY = i.clientY - t.offsetTop + document.body.scrollTop)
            }
            return c
        }

        function K(e, t) {
            return a.bind(e, t), a
        }

        function Q(e, t) {
            return a.unbind(e, t), a
        }

        function G(e, t) {
            switch (e) {
                case s.EVENT.RESIZE:
                    k(t);
                    break;
                case s.EVENT.CLICK:
                    L(t);
                    break;
                case s.EVENT.MOUSEWHEEL:
                    A(t);
                    break;
                case s.EVENT.MOUSEMOVE:
                    O(t);
                    break;
                case s.EVENT.MOUSEDOWN:
                    P(t);
                    break;
                case s.EVENT.MOUSEUP:
                    _mouseUpHandleru(t);
                    break;
                case s.EVENT.MOUSEOUT:
                    M(t)
            }
        }

        function Y() {
            window.removeEventListener ? (window.removeEventListener("resize", k), !o.os.tablet && !o.os.phone ? (t.removeEventListener("click", L), t.removeEventListener("mousewheel", A), t.removeEventListener("mousemove", O), t.removeEventListener("mousedown", P), t.removeEventListener("mouseup", H)) : (t.removeEventListener("touchstart", B), t.removeEventListener("touchmove", j), t.removeEventListener("touchend", F)), t.removeEventListener("DOMMouseScroll", A), t.removeEventListener("mouseout", M)) : (window.detachEvent("onresize", k), t.detachEvent("onclick", L), t.detachEvent("onmousewheel", A), t.detachEvent("onmousemove", O), t.detachEvent("onmouseout", M), t.detachEvent("onmousedown", P), t.detachEvent("onmouseup", H)), t = null, N = null, n = null, r = null, i = null, Q(), a = null;
            return
        }

        var s = e("./config"), o = e("./tool/env"), u = e("./tool/event");
        u.Dispatcher.call(this);
        var a = this, f = u.getX, l = u.getY, c, h = !1, p = null, d = null, v = null, m = !1, g = !1, y, b, w, E = 0,
            S = 0, x = 0, T = 0, N = r.getDomHover();
        a.on = K, a.un = Q, a.trigger = G, a.dispose = Y, C()
    }

    e("./lib/excanvas");
    var t = e("./tool/util"), n = {}, r = n, i = 0, s = {};
    return n.version = "1.1.2", n.init = function (e, t) {
        var n = new o(++i + "", e, t || {});
        return s[i] = n, n
    }, n.dispose = function (e) {
        if (e) e.dispose(); else {
            for (var t in s)s[t].dispose();
            s = {}
        }
        return n
    }, n.getInstance = function (e) {
        return s[e]
    }, n.delInstance = function (e) {
        return s[e] && (s[e] = null, delete s[e]), n
    }, n.catchBrushException = !1, n.debugMode = 0, n.log = function () {
        if (n.debugMode === 0)return;
        if (n.debugMode == 1)for (var e in arguments)throw new Error(arguments[e]); else if (n.debugMode > 1)for (var e in arguments)console.log(arguments[e]);
        return n
    }, n
}), define("zrender", ["zrender/zrender"], function (e) {
    return e
}), define("echarts/util/shape/icon", ["require", "zrender/tool/matrix", "zrender/shape", "zrender/shape", "zrender/shape", "zrender/shape", "zrender/shape/base", "zrender/shape"], function (e) {
    function n() {
        this.type = "icon", this._iconLibrary = {
            mark: r,
            markUndo: i,
            markClear: s,
            dataZoom: o,
            dataZoomReset: u,
            restore: a,
            lineChart: f,
            barChart: l,
            stackChart: c,
            tiledChart: h,
            dataView: p,
            saveAsImage: d,
            cross: v,
            circle: m,
            rectangle: g,
            triangle: y,
            diamond: b,
            arrow: w,
            star: E,
            heart: S,
            droplet: x,
            pin: T,
            image: N
        }
    }

    function r(e, t) {
        var n = t.width / 16, r = t.height / 16;
        e.moveTo(t.x, t.y + t.height), e.lineTo(t.x + 5 * n, t.y + 14 * r), e.lineTo(t.x + t.width, t.y + 3 * r), e.lineTo(t.x + 13 * n, t.y), e.lineTo(t.x + 2 * n, t.y + 11 * r), e.lineTo(t.x, t.y + t.height), e.moveTo(t.x + 6 * n, t.y + 10 * r), e.lineTo(t.x + 14 * n, t.y + 2 * r), e.moveTo(t.x + 10 * n, t.y + 13 * r), e.lineTo(t.x + t.width, t.y + 13 * r), e.moveTo(t.x + 13 * n, t.y + 10 * r), e.lineTo(t.x + 13 * n, t.y + t.height)
    }

    function i(e, t) {
        var n = t.width / 16, r = t.height / 16;
        e.moveTo(t.x, t.y + t.height), e.lineTo(t.x + 5 * n, t.y + 14 * r), e.lineTo(t.x + t.width, t.y + 3 * r), e.lineTo(t.x + 13 * n, t.y), e.lineTo(t.x + 2 * n, t.y + 11 * r), e.lineTo(t.x, t.y + t.height), e.moveTo(t.x + 6 * n, t.y + 10 * r), e.lineTo(t.x + 14 * n, t.y + 2 * r), e.moveTo(t.x + 10 * n, t.y + 13 * r), e.lineTo(t.x + t.width, t.y + 13 * r)
    }

    function s(e, t) {
        var n = t.width / 16, r = t.height / 16;
        e.moveTo(t.x + 4 * n, t.y + 15 * r), e.lineTo(t.x + 9 * n, t.y + 13 * r), e.lineTo(t.x + 14 * n, t.y + 8 * r), e.lineTo(t.x + 11 * n, t.y + 5 * r), e.lineTo(t.x + 6 * n, t.y + 10 * r), e.lineTo(t.x + 4 * n, t.y + 15 * r), e.moveTo(t.x + 5 * n, t.y), e.lineTo(t.x + 11 * n, t.y), e.moveTo(t.x + 5 * n, t.y + r), e.lineTo(t.x + 11 * n, t.y + r), e.moveTo(t.x, t.y + 2 * r), e.lineTo(t.x + t.width, t.y + 2 * r), e.moveTo(t.x, t.y + 5 * r), e.lineTo(t.x + 3 * n, t.y + t.height), e.lineTo(t.x + 13 * n, t.y + t.height), e.lineTo(t.x + t.width, t.y + 5 * r)
    }

    function o(e, t) {
        var n = t.width / 16, r = t.height / 16;
        e.moveTo(t.x, t.y + 3 * r), e.lineTo(t.x + 6 * n, t.y + 3 * r), e.moveTo(t.x + 3 * n, t.y), e.lineTo(t.x + 3 * n, t.y + 6 * r), e.moveTo(t.x + 3 * n, t.y + 8 * r), e.lineTo(t.x + 3 * n, t.y + t.height), e.lineTo(t.x + t.width, t.y + t.height), e.lineTo(t.x + t.width, t.y + 3 * r), e.lineTo(t.x + 8 * n, t.y + 3 * r), e.moveTo(t.x, t.y), e.lineTo(t.x, t.y)
    }

    function u(e, t) {
        var n = t.width / 16, r = t.height / 16;
        e.moveTo(t.x + 6 * n, t.y), e.lineTo(t.x + 2 * n, t.y + 3 * r), e.lineTo(t.x + 6 * n, t.y + 6 * r), e.moveTo(t.x + 2 * n, t.y + 3 * r), e.lineTo(t.x + 14 * n, t.y + 3 * r), e.lineTo(t.x + 14 * n, t.y + 11 * r), e.moveTo(t.x + 2 * n, t.y + 5 * r), e.lineTo(t.x + 2 * n, t.y + 13 * r), e.lineTo(t.x + 14 * n, t.y + 13 * r), e.moveTo(t.x + 10 * n, t.y + 10 * r), e.lineTo(t.x + 14 * n, t.y + 13 * r), e.lineTo(t.x + 10 * n, t.y + t.height), e.moveTo(t.x, t.y), e.lineTo(t.x, t.y)
    }

    function a(e, t) {
        var n = t.width / 16, r = t.height / 16, i = t.width / 2;
        e.lineWidth = 1.5, e.arc(t.x + i, t.y + i, i - n, 0, Math.PI * 2 / 3), e.moveTo(t.x + 3 * n, t.y + t.height), e.lineTo(t.x + 0 * n, t.y + 12 * r), e.lineTo(t.x + 5 * n, t.y + 11 * r), e.moveTo(t.x, t.y + 8 * r), e.arc(t.x + i, t.y + i, i - n, Math.PI, Math.PI * 5 / 3), e.moveTo(t.x + 13 * n, t.y), e.lineTo(t.x + t.width, t.y + 4 * r), e.lineTo(t.x + 11 * n, t.y + 5 * r), e.moveTo(t.x, t.y), e.lineTo(t.x, t.y)
    }

    function f(e, t) {
        var n = t.width / 16, r = t.height / 16;
        e.moveTo(t.x, t.y), e.lineTo(t.x, t.y + t.height), e.lineTo(t.x + t.width, t.y + t.height), e.moveTo(t.x + 2 * n, t.y + 14 * r), e.lineTo(t.x + 7 * n, t.y + 6 * r), e.lineTo(t.x + 11 * n, t.y + 11 * r), e.lineTo(t.x + 15 * n, t.y + 2 * r), e.moveTo(t.x, t.y), e.lineTo(t.x, t.y)
    }

    function l(e, t) {
        var n = t.width / 16, r = t.height / 16;
        e.moveTo(t.x, t.y), e.lineTo(t.x, t.y + t.height), e.lineTo(t.x + t.width, t.y + t.height), e.moveTo(t.x + 3 * n, t.y + 14 * r), e.lineTo(t.x + 3 * n, t.y + 6 * r), e.lineTo(t.x + 4 * n, t.y + 6 * r), e.lineTo(t.x + 4 * n, t.y + 14 * r), e.moveTo(t.x + 7 * n, t.y + 14 * r), e.lineTo(t.x + 7 * n, t.y + 2 * r), e.lineTo(t.x + 8 * n, t.y + 2 * r), e.lineTo(t.x + 8 * n, t.y + 14 * r), e.moveTo(t.x + 11 * n, t.y + 14 * r), e.lineTo(t.x + 11 * n, t.y + 9 * r), e.lineTo(t.x + 12 * n, t.y + 9 * r), e.lineTo(t.x + 12 * n, t.y + 14 * r)
    }

    function c(e, t) {
        var n = t.x, r = t.y, i = t.width, s = t.height, o = Math.round(s / 3), u = 3;
        while (u--)e.rect(n, r + o * u + 2, i, 2)
    }

    function h(e, t) {
        var n = t.x, r = t.y, i = t.width, s = t.height, o = Math.round(i / 3), u = 3;
        while (u--)e.rect(n + o * u, r, 2, s)
    }

    function p(e, t) {
        var n = t.width / 16;
        e.moveTo(t.x + n, t.y), e.lineTo(t.x + n, t.y + t.height), e.lineTo(t.x + 15 * n, t.y + t.height), e.lineTo(t.x + 15 * n, t.y), e.lineTo(t.x + n, t.y), e.moveTo(t.x + 3 * n, t.y + 3 * n), e.lineTo(t.x + 13 * n, t.y + 3 * n), e.moveTo(t.x + 3 * n, t.y + 6 * n), e.lineTo(t.x + 13 * n, t.y + 6 * n), e.moveTo(t.x + 3 * n, t.y + 9 * n), e.lineTo(t.x + 13 * n, t.y + 9 * n), e.moveTo(t.x + 3 * n, t.y + 12 * n), e.lineTo(t.x + 9 * n, t.y + 12 * n)
    }

    function d(e, t) {
        var n = t.width / 16, r = t.height / 16;
        e.moveTo(t.x, t.y), e.lineTo(t.x, t.y + t.height), e.lineTo(t.x + t.width, t.y + t.height), e.lineTo(t.x + t.width, t.y), e.lineTo(t.x, t.y), e.moveTo(t.x + 4 * n, t.y), e.lineTo(t.x + 4 * n, t.y + 8 * r), e.lineTo(t.x + 12 * n, t.y + 8 * r), e.lineTo(t.x + 12 * n, t.y), e.moveTo(t.x + 6 * n, t.y + 11 * r), e.lineTo(t.x + 6 * n, t.y + 13 * r), e.lineTo(t.x + 10 * n, t.y + 13 * r), e.lineTo(t.x + 10 * n, t.y + 11 * r), e.lineTo(t.x + 6 * n, t.y + 11 * r), e.moveTo(t.x, t.y), e.lineTo(t.x, t.y)
    }

    function v(e, t) {
        var n = t.x, r = t.y, i = t.width, s = t.height;
        e.moveTo(n, r + s / 2), e.lineTo(n + i, r + s / 2), e.moveTo(n + i / 2, r), e.lineTo(n + i / 2, r + s)
    }

    function m(e, t) {
        var n = t.width / 2, r = t.height / 2, i = Math.min(n, r);
        e.moveTo(t.x + n + i, t.y + r), e.arc(t.x + n, t.y + r, i, 0, Math.PI * 2)
    }

    function g(e, t) {
        e.rect(t.x, t.y, t.width, t.height)
    }

    function y(e, t) {
        var n = t.width / 2, r = t.height / 2, i = t.x + n, s = t.y + r, o = Math.min(n, r);
        e.moveTo(i, s - o), e.lineTo(i + o, s + o), e.lineTo(i - o, s + o), e.lineTo(i, s - o)
    }

    function b(e, t) {
        var n = t.width / 2, r = t.height / 2, i = t.x + n, s = t.y + r, o = Math.min(n, r);
        e.moveTo(i, s - o), e.lineTo(i + o, s), e.lineTo(i, s + o), e.lineTo(i - o, s), e.lineTo(i, s - o)
    }

    function w(e, t) {
        var n = t.x, r = t.y, i = t.width / 16;
        e.moveTo(n + 8 * i, r), e.lineTo(n + i, r + t.height), e.lineTo(n + 8 * i, r + t.height / 4 * 3), e.lineTo(n + 15 * i, r + t.height), e.lineTo(n + 8 * i, r)
    }

    function E(t, n) {
        var r = n.width / 2, i = n.height / 2, s = e("zrender/shape").get("star");
        s.buildPath(t, {x: n.x + r, y: n.y + i, r: Math.min(r, i), n: n.n || 5})
    }

    function S(t, n) {
        var r = e("zrender/shape").get("heart");
        r.buildPath(t, {x: n.x + n.width / 2, y: n.y + n.height * .2, a: n.width / 2, b: n.height * .8})
    }

    function x(t, n) {
        var r = e("zrender/shape").get("droplet");
        r.buildPath(t, {x: n.x + n.width * .5, y: n.y + n.height * .5, a: n.width * .5, b: n.height * .8})
    }

    function T(e, t) {
        var n = t.x, r = t.y - t.height / 2 * 1.5, i = t.width / 2, s = t.height / 2, o = Math.min(i, s);
        e.arc(n + i, r + s, o, Math.PI / 5 * 4, Math.PI / 5), e.lineTo(n + i, r + s + o * 1.5)
    }

    function N(t, n) {
        setTimeout(function () {
            e("zrender/shape").get("image").brush(t, {style: n})
        }, 100)
    }

    var t = e("zrender/tool/matrix");
    return n.prototype = {
        buildPath: function (e, t) {
            this._iconLibrary[t.iconType] ? this._iconLibrary[t.iconType](e, t) : (e.moveTo(t.x, t.y), e.lineTo(t.x + t.width, t.y), e.lineTo(t.x + t.width, t.y + t.height), e.lineTo(t.x, t.y + t.height), e.lineTo(t.x, t.y));
            return
        }, getRect: function (e) {
            return {
                x: Math.round(e.x),
                y: Math.round(e.y - (e.iconType == "pin" ? e.height / 2 * 1.5 : 0)),
                width: e.width,
                height: e.height
            }
        }, isCover: function (e, n, r) {
            if (e.__needTransform && e._transform) {
                var i = [];
                t.invert(i, e._transform);
                var s = [n, r];
                t.mulVector(s, i, [n, r, 1]), n == s[0] && r == s[1] && (Math.abs(e.rotation[0]) > 1e-4 || Math.abs(e.position[0]) > 1e-4 || Math.abs(e.position[1]) > 1e-4 || Math.abs(e.scale[0] - 1) > 1e-4 || Math.abs(e.scale[1] - 1) > 1e-4 ? e.__needTransform = !0 : e.__needTransform = !1), n = s[0], r = s[1]
            }
            var o;
            e.style.__rect ? o = e.style.__rect : (o = this.getRect(e.style), e.style.__rect = o);
            var u = o.height < 8 || o.width < 8 ? 4 : 0;
            return n >= o.x - u && n <= o.x + o.width + u && r >= o.y - u && r <= o.y + o.height + u ? !0 : !1
        }, define: function (e, t) {
            this._iconLibrary[e] = t
        }, get: function (e) {
            return this._iconLibrary[e]
        }
    }, e("zrender/shape/base").derive(n), e("zrender/shape").define("icon", new n), n
}), define("echarts/util/shape/markLine", ["require", "zrender/tool/matrix", "zrender/shape", "zrender/shape", "zrender/shape/base", "zrender/shape"], function (e) {
    function n() {
        this.type = "markLine"
    }

    var t = e("zrender/tool/matrix");
    return n.prototype = {
        brush: function (e, t, n) {
            var r = t.style || {};
            n && (r = this.getHighlightStyle(r, t.highlightStyle || {})), e.save(), this.setContext(e, r), t.__needTransform && e.transform.apply(e, this.updateTransform(t)), e.beginPath(), this.buildLinePath(e, r), e.stroke(), this.brushSymbol(t, e, r, 0), this.brushSymbol(t, e, r, 1), typeof r.text != "undefined" && this.drawText(e, r, t.style), e.restore();
            return
        }, buildLinePath: function (e, t) {
            var n = t.pointList || this.getPointList(t);
            t.pointList = n, typeof t.pointListLength == "undefined" && (t.pointListLength = n.length);
            var r = Math.round(t.pointListLength);
            if (!t.lineType || t.lineType == "solid") {
                e.moveTo(n[0][0], n[0][1]);
                for (var i = 1; i < r; i++)e.lineTo(n[i][0], n[i][1])
            } else if (t.lineType == "dashed" || t.lineType == "dotted")if (t.smooth !== "spline") {
                var s = (t.lineWidth || 1) * (t.lineType == "dashed" ? 5 : 1);
                e.moveTo(n[0][0], n[0][1]);
                for (var i = 1; i < r; i++)this.dashedLineTo(e, n[i - 1][0], n[i - 1][1], n[i][0], n[i][1], s)
            } else for (var i = 0; i < r - 1; i += 2)e.moveTo(n[i][0], n[i][1]), e.lineTo(n[i + 1][0], n[i + 1][1])
        }, brushSymbol: function (n, r, i, s) {
            if (i.symbol[s] == "none")return;
            r.save(), r.beginPath(), r.lineWidth = i.symbolBorder, r.strokeStyle = i.symbolBorderColor, i.iconType = i.symbol[s].replace("empty", "").toLowerCase(), i.symbol[s].match("empty") && (r.fillStyle = "#fff");
            var o = Math.round(i.pointListLength || i.pointList.length),
                u = s === 0 ? i.pointList[0][0] : i.pointList[o - 1][0],
                a = s === 0 ? i.pointList[0][1] : i.pointList[o - 1][1],
                f = typeof i.symbolRotate[s] != "undefined" ? i.symbolRotate[s] - 0 : 0, l;
            f !== 0 && (l = t.create(), t.identity(l), (u || a) && t.translate(l, l, [-u, -a]), t.rotate(l, l, f * Math.PI / 180), (u || a) && t.translate(l, l, [u, a]), r.transform.apply(r, l));
            if (i.iconType == "arrow" && f === 0) this.buildArrawPath(r, i, s); else {
                var c = i.symbolSize[s];
                i.x = u - c, i.y = a - c, i.width = c * 2, i.height = c * 2, e("zrender/shape").get("icon").buildPath(r, i)
            }
            r.closePath(), r.fill(), r.stroke(), r.restore()
        }, buildArrawPath: function (e, t, n) {
            var r = Math.round(t.pointListLength || t.pointList.length), i = t.symbolSize[n] * 2, s = t.pointList[0][0],
                o = t.pointList[r - 1][0], u = t.pointList[0][1], a = t.pointList[r - 1][1], f = 0;
            t.smooth === "spline" && (f = .2);
            var l = Math.atan(Math.abs((a - u) / (s - o)));
            n === 0 ? o > s ? a > u ? l = Math.PI * 2 - l + f : l += f : a > u ? l += Math.PI - f : l = Math.PI - l - f : s > o ? u > a ? l = Math.PI * 2 - l + f : l += f : u > a ? l += Math.PI - f : l = Math.PI - l - f;
            var c = Math.PI / 8, h = n === 0 ? s : o, p = n === 0 ? u : a,
                d = [[h + i * Math.cos(l - c), p - i * Math.sin(l - c)], [h + i * .6 * Math.cos(l), p - i * .6 * Math.sin(l)], [h + i * Math.cos(l + c), p - i * Math.sin(l + c)]];
            e.moveTo(h, p);
            for (var v = 0, m = d.length; v < m; v++)e.lineTo(d[v][0], d[v][1]);
            e.lineTo(h, p)
        }, getPointList: function (e) {
            var t = [[e.xStart, e.yStart], [e.xEnd, e.yEnd]];
            if (e.smooth === "spline") {
                var n = t[1][0], r = t[1][1];
                t[3] = [n, r], t[1] = this.getOffetPoint(t[0], t[3]), t[2] = this.getOffetPoint(t[3], t[0]), t = this.smoothSpline(t, !1), t[t.length - 1] = [n, r]
            }
            return t
        }, getOffetPoint: function (e, t) {
            var n = Math.sqrt(Math.round((e[0] - t[0]) * (e[0] - t[0]) + (e[1] - t[1]) * (e[1] - t[1]))) / 3,
                r = [e[0], e[1]], i, s = .2;
            if (e[0] != t[0] && e[1] != t[1]) {
                var o = (t[1] - e[1]) / (t[0] - e[0]);
                i = Math.atan(o)
            } else e[0] == t[0] ? i = (e[1] <= t[1] ? 1 : -1) * Math.PI / 2 : i = 0;
            var u, a;
            return e[0] <= t[0] ? (i -= s, u = Math.round(Math.cos(i) * n), a = Math.round(Math.sin(i) * n), r[0] += u, r[1] += a) : (i += s, u = Math.round(Math.cos(i) * n), a = Math.round(Math.sin(i) * n), r[0] -= u, r[1] -= a), r
        }, getRect: function (e) {
            var t = e.lineWidth || 1;
            return {
                x: Math.min(e.xStart, e.xEnd) - t,
                y: Math.min(e.yStart, e.yEnd) - t,
                width: Math.abs(e.xStart - e.xEnd) + t,
                height: Math.abs(e.yStart - e.yEnd) + t
            }
        }, isCover: function (t, n, r) {
            return e("zrender/shape").get(t.style.smooth !== "spline" ? "line" : "brokenLine").isCover(t, n, r)
        }
    }, e("zrender/shape/base").derive(n), e("zrender/shape").define("markLine", new n), n
}), define("echarts/chart", [], function () {
    var e = {}, t = {};
    return e.define = function (n, r) {
        return t[n] = r, e
    }, e.get = function (e) {
        return t[e]
    }, e
}), define("echarts/util/ecData", [], function () {
    function e(e, t, n, r, i, s, o, u) {
        var a;
        return typeof r != "undefined" && (typeof r.value != "undefined" ? a = r.value : a = r), e._echartsData = {
            _series: t,
            _seriesIndex: n,
            _data: r,
            _dataIndex: i,
            _name: s,
            _value: a,
            _special: o,
            _special2: u
        }, e._echartsData
    }

    function t(e, t) {
        var n = e._echartsData;
        if (!t)return n;
        switch (t) {
            case"series":
                return n && n._series;
            case"seriesIndex":
                return n && n._seriesIndex;
            case"data":
                return n && n._data;
            case"dataIndex":
                return n && n._dataIndex;
            case"name":
                return n && n._name;
            case"value":
                return n && n._value;
            case"special":
                return n && n._special;
            case"special2":
                return n && n._special2
        }
        return null
    }

    function n(e, t, n) {
        e._echartsData = e._echartsData || {};
        switch (t) {
            case"series":
                e._echartsData._series = n;
                break;
            case"seriesIndex":
                e._echartsData._seriesIndex = n;
                break;
            case"data":
                e._echartsData._data = n;
                break;
            case"dataIndex":
                e._echartsData._dataIndex = n;
                break;
            case"name":
                e._echartsData._name = n;
                break;
            case"value":
                e._echartsData._value = n;
                break;
            case"special":
                e._echartsData._special = n;
                break;
            case"special2":
                e._echartsData._special2 = n
        }
    }

    return {pack: e, set: n, get: t}
}), define("echarts/util/ecQuery", ["zrender/tool/util"], function () {
    function t(e, t) {
        if (typeof e == "undefined")return undefined;
        if (!t)return e;
        t = t.split(".");
        var n = t.length, r = 0;
        while (r < n) {
            e = e[t[r]];
            if (typeof e == "undefined")return undefined;
            r++
        }
        return e
    }

    function n(e, n) {
        var r;
        for (var i = 0, s = e.length; i < s; i++) {
            r = t(e[i], n);
            if (typeof r != "undefined")return r
        }
        return undefined
    }

    function r(n, r) {
        var i, s, o = n.length;
        while (o--)s = t(n[o], r), typeof s != "undefined" && (typeof i == "undefined" ? i = e.clone(s) : e.merge(i, s, {
            overwrite: !0,
            recursive: !0
        }));
        return i
    }

    var e = require("zrender/tool/util");
    return {query: t, deepQuery: n, deepMerge: r}
}), define("echarts/util/number", [], function () {
    function e(e) {
        return e.replace(/^\s\s*/, "").replace(/\s\s*$/, "")
    }

    function t(t, n) {
        return typeof t == "string" ? e(t).match(/%$/) ? parseFloat(t) / 100 * n : parseFloat(t) : t
    }

    function n(e, n) {
        return [t(n[0], e.getWidth()), t(n[1], e.getHeight())]
    }

    function r(e, n) {
        n instanceof Array || (n = [0, n]);
        var r = Math.min(e.getWidth(), e.getHeight()) / 2;
        return [t(n[0], r), t(n[1], r)]
    }

    function i(e) {
        return isNaN(e) ? "-" : (e = (e + "").split("."), e[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, "$1,") + (e.length > 1 ? "." + e[1] : ""))
    }

    return {parsePercent: t, parseCenter: n, parseRadius: r, addCommas: i}
}), define("echarts/component/base", ["require", "../util/ecData", "../util/ecQuery", "../util/number", "zrender/tool/util", "zrender/tool/area", "zrender/tool/env"], function (e) {
    function t(t, n) {
        function h(e) {
            e = e || a.type + "";
            switch (e) {
                case t.COMPONENT_TYPE_GRID:
                case t.COMPONENT_TYPE_AXIS_CATEGORY:
                case t.COMPONENT_TYPE_AXIS_VALUE:
                    return 0;
                case t.CHART_TYPE_LINE:
                case t.CHART_TYPE_BAR:
                case t.CHART_TYPE_SCATTER:
                case t.CHART_TYPE_PIE:
                case t.CHART_TYPE_RADAR:
                case t.CHART_TYPE_MAP:
                case t.CHART_TYPE_K:
                case t.CHART_TYPE_CHORD:
                    return 2;
                case t.COMPONENT_TYPE_LEGEND:
                case t.COMPONENT_TYPE_DATARANGE:
                case t.COMPONENT_TYPE_DATAZOOM:
                    return 4;
                case t.CHART_TYPE_ISLAND:
                    return 5;
                case t.COMPONENT_TYPE_TOOLBOX:
                case t.COMPONENT_TYPE_TITLE:
                    return 6;
                case t.COMPONENT_TYPE_TOOLTIP:
                    return 8;
                default:
                    return 0
            }
        }

        function p(e) {
            return o.merge(e || {}, o.clone(t[a.type] || {}), {overwrite: !1, recursive: !0})
        }

        function d(e) {
            if (!(e instanceof Array))return [e, e, e, e];
            switch (e.length + "") {
                case"4":
                    return e;
                case"3":
                    return [e[0], e[1], e[2], e[1]];
                case"2":
                    return [e[0], e[1], e[0], e[1]];
                case"1":
                    return [e[0], e[0], e[0], e[0]];
                case"0":
                    return [0, 0, 0, 0]
            }
        }

        function v(e) {
            var n = o.merge(o.clone(e) || {}, t.textStyle, {overwrite: !1});
            return n.fontStyle + " " + n.fontWeight + " " + n.fontSize + "px " + n.fontFamily
        }

        function m(e, t, n, r, i) {
            var s = [n, t], o = a.deepMerge(s, "itemStyle.normal.label"),
                u = a.deepMerge(s, "itemStyle.emphasis.label"), f = o.textStyle || {}, l = u.textStyle || {};
            return o.show && (e.style.text = g(t, n, r, "normal"), e.style.textPosition = typeof o.position == "undefined" ? i == "horizontal" ? "right" : "top" : o.position, e.style.textColor = f.color, e.style.textFont = a.getFont(f)), u.show && (e.highlightStyle.text = g(t, n, r, "emphasis"), e.highlightStyle.textPosition = o.show ? e.style.textPosition : typeof u.position == "undefined" ? i == "horizontal" ? "right" : "top" : u.position, e.highlightStyle.textColor = l.color, e.highlightStyle.textFont = a.getFont(l)), e
        }

        function g(e, t, n, r) {
            var i = a.deepQuery([t, e], "itemStyle." + r + ".label.formatter");
            !i && r == "emphasis" && (i = a.deepQuery([t, e], "itemStyle.normal.label.formatter"));
            var s = typeof t != "undefined" ? typeof t.value != "undefined" ? t.value : t : "-";
            if (!i)return s;
            if (typeof i == "function")return i(e.name, n, s);
            if (typeof i == "string")return i = i.replace("{a}", "{a0}").replace("{b}", "{b0}").replace("{c}", "{c0}"), i = i.replace("{a0}", e.name).replace("{b0}", n).replace("{c0}", s), i
        }

        function y(e, t, n, r, i) {
            a.selectedMap[e.name] && (e.markPoint && b(e, t, n, r, i), e.markLine && w(e, t, n, r, i))
        }

        function b(e, n, r, i, s) {
            var f = a.getZlevelBase(), l, c, h = o.clone(e.markPoint);
            for (var p = 0, d = h.data.length; p < d; p++)l = h.data[p], c = a.getMarkCoord(e, n, l, i), h.data[p].x = typeof l.x != "undefined" ? l.x : c[0], h.data[p].y = typeof l.y != "undefined" ? l.y : c[1], l.type && (l.type == "max" || l.type == "min") && (h.data[p].value = c[3], h.data[p].name = l.name || l.type, h.data[p].symbolSize = h.data[p].symbolSize || u.getTextWidth(c[3], a.getFont()) / 2 + 5);
            var v = E(e, n, h, r);
            for (var p = 0, d = v.length; p < d; p++) {
                v[p].zlevel = f + 1;
                for (var m in s)v[p][m] = s[m];
                a.shapeList.push(v[p])
            }
            if (a.type == t.CHART_TYPE_FORCE || a.type == t.CHART_TYPE_CHORD)for (var p = 0, d = v.length; p < d; p++)v[p].id = a.zr.newShapeId(a.type), a.zr.addShape(v[p])
        }

        function w(e, n, r, i, s) {
            var u = a.getZlevelBase(), f, l, c = o.clone(e.markLine);
            for (var h = 0, p = c.data.length; h < p; h++)f = c.data[h], !f.type || f.type != "max" && f.type != "min" && f.type != "average" ? l = [a.getMarkCoord(e, n, f[0], i), a.getMarkCoord(e, n, f[1], i)] : (l = a.getMarkCoord(e, n, f, i), c.data[h] = [o.clone(f), {}], c.data[h][0].name = f.name || f.type, c.data[h][0].value = l[3], l = l[2], f = [{}, {}]), c.data[h][0].x = typeof f[0].x != "undefined" ? f[0].x : l[0][0], c.data[h][0].y = typeof f[0].y != "undefined" ? f[0].y : l[0][1], c.data[h][1].x = typeof f[1].x != "undefined" ? f[1].x : l[1][0], c.data[h][1].y = typeof f[1].y != "undefined" ? f[1].y : l[1][1];
            var d = S(e, n, c, r);
            for (var h = 0, p = d.length; h < p; h++) {
                d[h].zlevel = u + 1;
                for (var v in s)d[h][v] = s[v];
                a.shapeList.push(d[h])
            }
            if (a.type == t.CHART_TYPE_FORCE || a.type == t.CHART_TYPE_CHORD)for (var h = 0, p = d.length; h < p; h++)d[h].id = a.zr.newShapeId(a.type), a.zr.addShape(d[h])
        }

        function E(e, n, i, s) {
            o.merge(i, t.markPoint, {overwrite: !1, recursive: !0}), i.name = e.name;
            var u = [], f = i.data, l, c = s.dataRange, h = s.legend, p, d, v, m, g, y, b = a.zr.getWidth(),
                w = a.zr.getHeight();
            for (var E = 0, S = f.length; E < S; E++) {
                h && (p = h.getColor(e.name));
                if (c) {
                    d = typeof f[E] != "undefined" ? typeof f[E].value != "undefined" ? f[E].value : f[E] : "-", p = isNaN(d) ? p : c.getColor(d), v = [f[E], i], m = a.deepQuery(v, "itemStyle.normal.color") || p, g = a.deepQuery(v, "itemStyle.emphasis.color") || m;
                    if (m == null && g == null)continue
                }
                f[E].tooltip = f[E].tooltip || {trigger: "item"}, f[E].name = typeof f[E].name != "undefined" ? f[E].name : "", f[E].value = typeof f[E].value != "undefined" ? f[E].value : "", l = T(i, n, f[E], E, f[E].name, a.parsePercent(f[E].x, b), a.parsePercent(f[E].y, w), "pin", p, "rgba(0,0,0,0)", "horizontal"), y = a.deepMerge([f[E], i], "effect"), y.show && (l.effect = y), r.pack(l, e, n, f[E], 0, f[E].name), u.push(l)
            }
            return u
        }

        function S(e, n, i, s) {
            o.merge(i, t.markLine, {
                overwrite: !1,
                recursive: !0
            }), i.symbol = i.symbol instanceof Array ? i.symbol.length > 1 ? i.symbol : [i.symbol[0], i.symbol[0]] : [i.symbol, i.symbol], i.symbolSize = i.symbolSize instanceof Array ? i.symbolSize.length > 1 ? i.symbolSize : [i.symbolSize[0], i.symbolSize[0]] : [i.symbolSize, i.symbolSize], i.symbolRotate = i.symbolRotate instanceof Array ? i.symbolRotate.length > 1 ? i.symbolRotate : [i.symbolRotate[0], i.symbolRotate[0]] : [i.symbolRotate, i.symbolRotate], i.name = e.name;
            var u = [], f = i.data, l, c = s.dataRange, h = s.legend, p, d, v, m, g, y, b = a.zr.getWidth(),
                w = a.zr.getHeight(), E;
            for (var S = 0, x = f.length; S < x; S++) {
                h && (p = h.getColor(e.name)), E = a.deepMerge(f[S]);
                if (c) {
                    d = typeof E != "undefined" ? typeof E.value != "undefined" ? E.value : E : "-", p = isNaN(d) ? p : c.getColor(d), v = [E, i], m = a.deepQuery(v, "itemStyle.normal.color") || p, g = a.deepQuery(v, "itemStyle.emphasis.color") || m;
                    if (m == null && g == null)continue
                }
                f[S][0].tooltip = E.tooltip || {trigger: "item"}, f[S][0].name = typeof f[S][0].name != "undefined" ? f[S][0].name : "", f[S][1].name = typeof f[S][1].name != "undefined" ? f[S][1].name : "", f[S][0].value = typeof f[S][0].value != "undefined" ? f[S][0].value : "", l = N(i, n, f[S], S, a.parsePercent(f[S][0].x, b), a.parsePercent(f[S][0].y, w), a.parsePercent(f[S][1].x, b), a.parsePercent(f[S][1].y, w), p), y = a.deepMerge([E, i], "effect"), y.show && (l.effect = y), r.pack(l, e, n, f[S][0], 0, f[S][0].name + (f[S][1].name !== "" ? " > " + f[S][1].name : "")), u.push(l)
            }
            return u
        }

        function x() {
            return [0, 0]
        }

        function T(e, t, n, i, s, o, u, f, l, c, h) {
            var p = [n, e], d = typeof n != "undefined" ? typeof n.value != "undefined" ? n.value : n : "-";
            f = a.deepQuery(p, "symbol") || f;
            var v = a.deepQuery(p, "symbolSize");
            v = typeof v == "function" ? v(d) : v;
            var m = a.deepQuery(p, "symbolRotate"), g = a.deepMerge(p, "itemStyle.normal"),
                y = a.deepMerge(p, "itemStyle.emphasis"),
                b = typeof g.borderWidth != "undefined" ? g.borderWidth : g.lineStyle && g.lineStyle.width;
            typeof b == "undefined" && (b = 0);
            var w = typeof y.borderWidth != "undefined" ? y.borderWidth : y.lineStyle && y.lineStyle.width;
            typeof w == "undefined" && (w = b + 2);
            var E = {
                shape: "icon",
                style: {
                    iconType: f.replace("empty", "").toLowerCase(),
                    x: o - v,
                    y: u - v,
                    width: v * 2,
                    height: v * 2,
                    brushType: "both",
                    color: f.match("empty") ? c : a.getItemStyleColor(g.color, t, i, n) || l,
                    strokeColor: g.borderColor || a.getItemStyleColor(g.color, t, i, n) || l,
                    lineWidth: b
                },
                highlightStyle: {
                    color: f.match("empty") ? c : a.getItemStyleColor(y.color, t, i, n),
                    strokeColor: y.borderColor || g.borderColor || a.getItemStyleColor(g.color, t, i, n) || l,
                    lineWidth: w
                },
                clickable: !0
            };
            return f.match("image") && (E.style.image = f.replace(new RegExp("^image:\\/\\/"), ""), E.shape = "image"), typeof m != "undefined" && (E.rotation = [m * Math.PI / 180, o, u]), f.match("star") && (E.style.iconType = "star", E.style.n = f.replace("empty", "").replace("star", "") - 0 || 5), f == "none" && (E.invisible = !0, E.hoverable = !1), E = a.addLabel(E, e, n, s, h), f.match("empty") && (typeof E.style.textColor == "undefined" && (E.style.textColor = E.style.strokeColor), typeof E.highlightStyle.textColor == "undefined" && (E.highlightStyle.textColor = E.highlightStyle.strokeColor)), r.pack(E, e, t, n, i, s), E._mark = "point", E._x = o, E._y = u, E._dataIndex = i, E._seriesIndex = t, E
        }

        function N(e, t, n, r, i, s, o, u, f) {
            var l = typeof n[0] != "undefined" ? typeof n[0].value != "undefined" ? n[0].value : n[0] : "-",
                c = typeof n[1] != "undefined" ? typeof n[1].value != "undefined" ? n[1].value : n[1] : "-",
                h = [a.query(n[0], "symbol") || e.symbol[0], a.query(n[1], "symbol") || e.symbol[1]],
                p = [a.query(n[0], "symbolSize") || e.symbolSize[0], a.query(n[1], "symbolSize") || e.symbolSize[1]];
            p[0] = typeof p[0] == "function" ? p[0](l) : p[0], p[1] = typeof p[1] == "function" ? p[1](c) : p[1];
            var d = [a.query(n[0], "symbolRotate") || e.symbolRotate[0], a.query(n[1], "symbolRotate") || e.symbolRotate[1]],
                v = [n[0], e], m = a.deepMerge(v, "itemStyle.normal");
            m.color = a.getItemStyleColor(m.color, t, r, n);
            var g = a.deepMerge(v, "itemStyle.emphasis");
            g.color = a.getItemStyleColor(g.color, t, r, n);
            var y = m.lineStyle, b = g.lineStyle, w = y.width;
            typeof w == "undefined" && (w = m.borderWidth);
            var E = b.width;
            typeof E == "undefined" && (typeof g.borderWidth != "undefined" ? E = g.borderWidth : E = w + 2);
            var S = {
                shape: "markLine",
                style: {
                    smooth: e.smooth ? "spline" : !1,
                    symbol: h,
                    symbolSize: p,
                    symbolRotate: d,
                    xStart: i,
                    yStart: s,
                    xEnd: o,
                    yEnd: u,
                    brushType: "both",
                    lineType: y.type,
                    shadowColor: y.shadowColor,
                    shadowBlur: y.shadowBlur,
                    shadowOffsetX: y.shadowOffsetX,
                    shadowOffsetY: y.shadowOffsetY,
                    color: m.color || f,
                    strokeColor: y.color || m.borderColor || m.color || f,
                    lineWidth: w,
                    symbolBorderColor: m.borderColor || m.color || f,
                    symbolBorder: m.borderWidth
                },
                highlightStyle: {
                    shadowColor: b.shadowColor,
                    shadowBlur: b.shadowBlur,
                    shadowOffsetX: b.shadowOffsetX,
                    shadowOffsetY: b.shadowOffsetY,
                    color: g.color || m.color || f,
                    strokeColor: b.color || y.color || g.borderColor || m.borderColor || g.color || m.color || f,
                    lineWidth: E,
                    symbolBorderColor: g.borderColor || m.borderColor || g.color || m.color || f,
                    symbolBorder: typeof g.borderWidth == "undefined" ? m.borderWidth + 2 : g.borderWidth
                },
                clickable: !0
            };
            return S = a.addLabel(S, e, n[0], n[0].name + " : " + n[1].name), S._mark = "line", S._x = o, S._y = u, S
        }

        function C(e, t, n, r) {
            return typeof e == "function" ? e(t, n, r) : e
        }

        function k(e, t) {
            return t % 2 == 1 ? e = Math.floor(e) + .5 : e = Math.round(e), e
        }

        function L() {
            c[a.type] ? a.animationMark(t.animationDuration) : a.animationEffect()
        }

        function A(e, t) {
            var r, i;
            for (var s = 0, o = a.shapeList.length; s < o; s++) {
                if (!a.shapeList[s]._mark)continue;
                r = a.shapeList[s]._x || 0, i = a.shapeList[s]._y || 0, a.shapeList[s]._mark == "point" ? (n.modShape(a.shapeList[s].id, {scale: [0, 0, r, i]}, !0), n.animate(a.shapeList[s].id, "").when(e, {scale: [1, 1, r, i]}).start(t || "QuinticOut")) : a.shapeList[s]._mark == "line" && (a.shapeList[s].style.smooth ? (n.modShape(a.shapeList[s].id, {style: {pointListLength: 1}}, !0), n.animate(a.shapeList[s].id, "style").when(e, {pointListLength: a.shapeList[s].style.pointList.length}).start(t || "QuinticOut")) : (n.modShape(a.shapeList[s].id, {style: {pointList: [[a.shapeList[s].style.xStart, a.shapeList[s].style.yStart], [a.shapeList[s].style.xStart, a.shapeList[s].style.yStart]]}}, !0), n.animate(a.shapeList[s].id, "style").when(e, {pointList: [[a.shapeList[s].style.xStart, a.shapeList[s].style.yStart], [r, i]]}).start(t || "QuinticOut")))
            }
            a.animationEffect()
        }

        function O() {
            _();
            var e = f;
            l && n.modLayer(e, {motionBlur: !0, lastFrameAlpha: .95});
            var t, r, i, s;
            for (var o = 0, u = a.shapeList.length; o < u; o++) {
                shape = a.shapeList[o];
                if (!shape._mark || !shape.effect || !shape.effect.show)continue;
                s = shape.effect, t = s.color || shape.style.strokeColor || shape.style.color, r = s.shadowColor || t;
                var c, h;
                switch (shape._mark) {
                    case"point":
                        i = s.scaleSize, shadowBlur = typeof s.shadowBlur != "undefined" ? s.shadowBlur : i, c = {
                            shape: shape.shape,
                            id: n.newShapeId(),
                            zlevel: e,
                            style: {
                                brushType: "stroke",
                                iconType: shape.style.iconType != "pin" && shape.style.iconType != "droplet" ? shape.style.iconType : "circle",
                                x: shadowBlur + 1,
                                y: shadowBlur + 1,
                                n: shape.style.n,
                                width: shape.style.width * i,
                                height: shape.style.height * i,
                                lineWidth: 1,
                                strokeColor: t,
                                shadowColor: r,
                                shadowBlur: shadowBlur
                            },
                            draggable: !1,
                            hoverable: !1
                        }, l && (c.style.image = n.shapeToImage(c, c.style.width + shadowBlur * 2 + 2, c.style.height + shadowBlur * 2 + 2).style.image, c.shape = "image"), h = (c.style.width - shape.style.width) / 2;
                        break;
                    case"line":
                        i = shape.style.lineWidth * s.scaleSize, shadowBlur = typeof s.shadowBlur != "undefined" ? s.shadowBlur : i, c = {
                            shape: "circle",
                            id: n.newShapeId(),
                            zlevel: e,
                            style: {
                                x: shadowBlur,
                                y: shadowBlur,
                                r: i,
                                color: t,
                                shadowColor: r,
                                shadowBlur: shadowBlur
                            },
                            draggable: !1,
                            hoverable: !1
                        }, l ? (c.style.image = n.shapeToImage(c, (i + shadowBlur) * 2, (i + shadowBlur) * 2).style.image, c.shape = "image", h = shadowBlur) : h = 0
                }
                var p;
                c.position = shape.position;
                if (shape._mark === "point") c.style.x = shape.style.x - h, c.style.y = shape.style.y - h, p = (s.period + Math.random() * 10) * 100; else if (shape._mark === "line") {
                    c.style.x = shape.style.xStart - h, c.style.y = shape.style.yStart - h;
                    var d = (shape.style.xStart - shape._x) * (shape.style.xStart - shape._x) + (shape.style.yStart - shape._y) * (shape.style.yStart - shape._y);
                    p = Math.round(Math.sqrt(Math.round(d * s.period * s.period)))
                }
                a.effectList.push(c), n.addShape(c);
                if (shape._mark === "point") {
                    n.modShape(shape.id, {invisible: !0}, !0);
                    var v = c.style.x + c.style.width / 2, m = c.style.y + c.style.height / 2;
                    n.modShape(c.id, {scale: [.1, .1, v, m]}, !0), n.animate(c.id, "", !0).when(p, {scale: [1, 1, v, m]}).start()
                } else if (shape._mark === "line")if (!shape.style.smooth) n.animate(c.id, "style", !0).when(p, {
                    x: shape._x - h,
                    y: shape._y - h
                }).start(); else {
                    var g = shape.style.pointList, y = g.length;
                    p = Math.round(p / y);
                    var b = n.animate(c.id, "style", !0);
                    for (var w = 0; w < y; w++)b.when(p * (w + 1), {x: g[w][0] - h, y: g[w][1] - h});
                    b.start()
                }
            }
        }

        function M() {
            a.refresh && a.refresh()
        }

        function _() {
            a.zr && a.effectList.length > 0 && (a.zr.modLayer(f, {motionBlur: !1}), a.zr.delShape(a.effectList)), a.effectList = []
        }

        function D() {
            _(), a.zr && a.zr.delShape(a.shapeList), a.shapeList = []
        }

        function P() {
            a.clear(), a.shapeList = null, a.effectList = null, a = null
        }

        var r = e("../util/ecData"), i = e("../util/ecQuery"), s = e("../util/number"), o = e("zrender/tool/util"),
            u = e("zrender/tool/area"), a = this;
        a.zr = n, a.shapeList = [], a.effectList = [];
        var f = 7, l = e("zrender/tool/env").canvasSupported, c = {};
        c[t.CHART_TYPE_LINE] = !0, c[t.CHART_TYPE_BAR] = !0, c[t.CHART_TYPE_SCATTER] = !0, c[t.CHART_TYPE_PIE] = !0, c[t.CHART_TYPE_RADAR] = !0, c[t.CHART_TYPE_MAP] = !0, c[t.CHART_TYPE_K] = !0, c[t.CHART_TYPE_CHORD] = !0, a.getZlevelBase = h, a.reformOption = p, a.reformCssArray = d, a.query = i.query, a.deepQuery = i.deepQuery, a.deepMerge = i.deepMerge, a.getFont = v, a.addLabel = m, a.buildMark = y, a.getMarkCoord = x, a.getSymbolShape = T, a.parsePercent = s.parsePercent, a.parseCenter = s.parseCenter, a.parseRadius = s.parseRadius, a.numAddCommas = s.addCommas, a.getItemStyleColor = C, a.subPixelOptimize = k, a.animation = L, a.animationMark = A, a.animationEffect = O, a.resize = M, a.clearAnimationShape = _, a.clear = D, a.dispose = P
    }

    return t
}), define("echarts/util/accMath", [], function () {
    function e(e, n) {
        return t(e, 1 / n)
    }

    function t(e, t) {
        var n = 0, r = e.toString(), i = t.toString();
        try {
            n += r.split(".")[1].length
        } catch (s) {
        }
        try {
            n += i.split(".")[1].length
        } catch (s) {
        }
        return Number(r.replace(".", "")) * Number(i.replace(".", "")) / Math.pow(10, n)
    }

    function n(e, t) {
        var n, r, i;
        try {
            n = e.toString().split(".")[1].length
        } catch (s) {
            n = 0
        }
        try {
            r = t.toString().split(".")[1].length
        } catch (s) {
            r = 0
        }
        return i = Math.pow(10, Math.max(n, r)), (Math.round(e * i) + Math.round(t * i)) / i
    }

    function r(e, t) {
        return n(e, -t)
    }

    return {accDiv: e, accMul: t, accAdd: n, accSub: r}
}), define("echarts/chart/calculableBase", ["require", "../util/ecData", "../util/accMath", "zrender/tool/util"], function (e) {
    function t(t, n) {
        function u(e) {
            return e.dragEnableTime = n.DRAG_ENABLE_TIME, e.ondragover = o.shapeHandler.ondragover, e.ondragend = o.shapeHandler.ondragend, e.ondrop = o.shapeHandler.ondrop, e
        }

        function a(e, t) {
            if (!o.isDrop || !e.target)return;
            var s = e.target, u = e.dragged, a = r.get(s, "seriesIndex"), f = r.get(s, "dataIndex"),
                l = n.series[a].data[f] || "-";
            l.value ? l.value != "-" ? n.series[a].data[f].value = i.accAdd(n.series[a].data[f].value, r.get(u, "value")) : n.series[a].data[f].value = r.get(u, "value") : l != "-" ? n.series[a].data[f] = i.accAdd(n.series[a].data[f], r.get(u, "value")) : n.series[a].data[f] = r.get(u, "value"), t.dragIn = t.dragIn || !0, o.isDrop = !1;
            return
        }

        function f(e, t) {
            if (!o.isDragend || !e.target)return;
            var i = e.target, s = r.get(i, "seriesIndex"), u = r.get(i, "dataIndex");
            n.series[s].data[u] = "-", t.dragOut = !0, t.needRefresh = !0, o.isDragend = !1;
            return
        }

        function l(e, t) {
            var n = e.selected;
            for (var r in o.selectedMap)o.selectedMap[r] != n[r] && (t.needRefresh = !0), o.selectedMap[r] = n[r];
            return
        }

        var r = e("../util/ecData"), i = e("../util/accMath"), s = e("zrender/tool/util"), o = this;
        o.selectedMap = {}, o.shapeHandler = {
            onclick: function () {
                o.isClick = !0
            }, ondragover: function (e) {
                var t = s.clone(e.target);
                t.highlightStyle = {
                    text: "",
                    r: t.style.r + 5,
                    brushType: "stroke",
                    strokeColor: n.calculableColor,
                    lineWidth: (t.style.lineWidth || 1) + 12
                }, o.zr.addHoverShape(t)
            }, ondrop: function (e) {
                typeof r.get(e.dragged, "data") != "undefined" && (o.isDrop = !0)
            }, ondragend: function () {
                o.isDragend = !0
            }
        }, o.setCalculable = u, o.ondrop = a, o.ondragend = f, o.onlegendSelected = l
    }

    return t
}), define("echarts/chart/island", ["require", "../component/base", "./calculableBase", "../util/ecData", "zrender/tool/event", "zrender/tool/color", "../util/accMath", "../chart"], function (e) {
    function t(t, n, r) {
        function v(t, n) {
            var r = e("zrender/tool/color"), i = e("../util/accMath"),
                s = i.accAdd(o.get(t, "value"), o.get(n, "value")), u = o.get(t, "name") + c + o.get(n, "name");
            t.style.text = u + h + s, o.set(t, "value", s), o.set(t, "name", u), t.style.r = f.island.r, t.style.color = r.mix(t.style.color, n.style.color)
        }

        function m(e) {
            e && (e.island = a.reformOption(e.island), f = e, c = f.nameConnector, h = f.valueConnector)
        }

        function g(e) {
            m(e);
            for (var t = 0, n = a.shapeList.length; t < n; t++)r.addShape(a.shapeList[t])
        }

        function y() {
            return f
        }

        function b() {
            var e = r.getWidth(), t = r.getHeight(), n = e / (d || e), i = t / (p || t);
            if (n == 1 && i == 1)return;
            d = e, p = t;
            for (var s = 0, o = a.shapeList.length; s < o; s++)r.modShape(a.shapeList[s].id, {
                style: {
                    x: Math.round(a.shapeList[s].style.x * n),
                    y: Math.round(a.shapeList[s].style.y * i)
                }
            }, !0)
        }

        function w(e) {
            var t = o.get(e, "name"), n = o.get(e, "value"),
                i = typeof o.get(e, "series") != "undefined" ? o.get(e, "series").name : "",
                s = a.getFont(f.island.textStyle), u = {
                    shape: "circle",
                    id: r.newShapeId(a.type),
                    zlevel: l,
                    style: {
                        x: e.style.x,
                        y: e.style.y,
                        r: f.island.r,
                        color: e.style.color || e.style.strokeColor,
                        text: t + h + n,
                        textFont: s
                    },
                    draggable: !0,
                    hoverable: !0,
                    onmousewheel: a.shapeHandler.onmousewheel,
                    _type: "island"
                };
            u.style.color == "#fff" && (u.style.color = e.style.strokeColor), a.setCalculable(u), u.dragEnableTime = 0, o.pack(u, {name: i}, -1, n, -1, t), a.shapeList.push(u), r.addShape(u)
        }

        function E(e) {
            r.delShape(e.id);
            var t = [];
            for (var n = 0, i = a.shapeList.length; n < i; n++)a.shapeList[n].id != e.id && t.push(a.shapeList[n]);
            a.shapeList = t
        }

        function S(e, t) {
            if (!a.isDrop || !e.target)return;
            var n = e.target, i = e.dragged;
            v(n, i), r.modShape(n.id, n), t.dragIn = !0, a.isDrop = !1;
            return
        }

        function x(e, t) {
            var n = e.target;
            a.isDragend ? t.dragIn && (E(n), t.needRefresh = !0) : t.dragIn || (n.style.x = u.getX(e.event), n.style.y = u.getY(e.event), w(n), t.needRefresh = !0), a.isDragend = !1;
            return
        }

        var i = e("../component/base");
        i.call(this, t, r);
        var s = e("./calculableBase");
        s.call(this, r, t);
        var o = e("../util/ecData"), u = e("zrender/tool/event"), a = this;
        a.type = t.CHART_TYPE_ISLAND;
        var f, l = a.getZlevelBase(), c, h, p = r.getHeight(), d = r.getWidth();
        a.shapeHandler.onmousewheel = function (e) {
            var t = e.target, n = e.event, i = u.getDelta(n);
            i = i > 0 ? -1 : 1, t.style.r -= i, t.style.r = t.style.r < 5 ? 5 : t.style.r;
            var s = o.get(t, "value"), a = s * f.island.calculateStep;
            a > 1 ? s = Math.round(s - a * i) : s = (s - a * i).toFixed(2) - 0;
            var l = o.get(t, "name");
            t.style.text = l + ":" + s, o.set(t, "value", s), o.set(t, "name", l), r.modShape(t.id, t), r.refresh(), u.stop(n)
        }, a.refresh = m, a.render = g, a.resize = b, a.getOption = y, a.add = w, a.del = E, a.ondrop = S, a.ondragend = x
    }

    return e("../chart").define("island", t), t
}), define("echarts/component", [], function () {
    var e = {}, t = {};
    return e.define = function (n, r) {
        return t[n] = r, e
    }, e.get = function (e) {
        return t[e]
    }, e
}), define("echarts/component/title", ["require", "./base", "zrender/tool/area", "zrender/tool/util", "../component"], function (e) {
    function t(t, n, r, i) {
        function h() {
            c = v(), d(), p();
            for (var e = 0, t = a.shapeList.length; e < t; e++)a.shapeList[e].id = r.newShapeId(a.type), r.addShape(a.shapeList[e])
        }

        function p() {
            var e = f.text, t = f.link, n = f.subtext, r = f.sublink, i = a.getFont(f.textStyle),
                s = a.getFont(f.subtextStyle), o = c.x, u = c.y, h = c.width, p = c.height, d = {
                    shape: "text",
                    zlevel: l,
                    style: {y: u, color: f.textStyle.color, text: e, textFont: i, textBaseline: "top"},
                    highlightStyle: {brushType: "fill"},
                    hoverable: !1
                };
            t && (d.hoverable = !0, d.clickable = !0, d.onclick = function () {
                window.open(t)
            });
            var v = {
                shape: "text",
                zlevel: l,
                style: {y: u + p, color: f.subtextStyle.color, text: n, textFont: s, textBaseline: "bottom"},
                highlightStyle: {brushType: "fill"},
                hoverable: !1
            };
            r && (v.hoverable = !0, v.clickable = !0, v.onclick = function () {
                window.open(r)
            });
            switch (f.x) {
                case"center":
                    d.style.x = v.style.x = o + h / 2, d.style.textAlign = v.style.textAlign = "center";
                    break;
                case"left":
                    d.style.x = v.style.x = o, d.style.textAlign = v.style.textAlign = "left";
                    break;
                case"right":
                    d.style.x = v.style.x = o + h, d.style.textAlign = v.style.textAlign = "right";
                    break;
                default:
                    o = f.x - 0, o = isNaN(o) ? 0 : o, d.style.x = v.style.x = o
            }
            f.textAlign && (d.style.textAlign = v.style.textAlign = f.textAlign), a.shapeList.push(d), n !== "" && a.shapeList.push(v)
        }

        function d() {
            var e = f.padding[0], t = f.padding[1], n = f.padding[2], r = f.padding[3];
            a.shapeList.push({
                shape: "rectangle",
                zlevel: l,
                hoverable: !1,
                style: {
                    x: c.x - r,
                    y: c.y - e,
                    width: c.width + r + t,
                    height: c.height + e + n,
                    brushType: f.borderWidth === 0 ? "fill" : "both",
                    color: f.backgroundColor,
                    strokeColor: f.borderColor,
                    lineWidth: f.borderWidth
                }
            })
        }

        function v() {
            var e = f.text, t = f.subtext, n = a.getFont(f.textStyle), i = a.getFont(f.subtextStyle),
                s = Math.max(o.getTextWidth(e, n), o.getTextWidth(t, i)),
                u = o.getTextHeight(e, n) + (t === "" ? 0 : f.itemGap + o.getTextHeight(t, i)), l, c = r.getWidth();
            switch (f.x) {
                case"center":
                    l = Math.floor((c - s) / 2);
                    break;
                case"left":
                    l = f.padding[3] + f.borderWidth;
                    break;
                case"right":
                    l = c - s - f.padding[1] - f.borderWidth;
                    break;
                default:
                    l = f.x - 0, l = isNaN(l) ? 0 : l
            }
            var h, p = r.getHeight();
            switch (f.y) {
                case"top":
                    h = f.padding[0] + f.borderWidth;
                    break;
                case"bottom":
                    h = p - u - f.padding[2] - f.borderWidth;
                    break;
                case"center":
                    h = Math.floor((p - u) / 2);
                    break;
                default:
                    h = f.y - 0, h = isNaN(h) ? 0 : h
            }
            return {x: l, y: h, width: s, height: u}
        }

        function m(e) {
            g(e)
        }

        function g(e) {
            e && (i = e, i.title = a.reformOption(i.title), i.title.padding = a.reformCssArray(i.title.padding), f = i.title, f.textStyle = u.merge(f.textStyle, t.textStyle, {
                overwrite: !1,
                recursive: !1
            }), f.subtextStyle = u.merge(f.subtextStyle, t.textStyle, {overwrite: !1, recursive: !1}), a.clear(), h())
        }

        function y() {
            a.clear(), h()
        }

        var s = e("./base");
        s.call(this, t, r);
        var o = e("zrender/tool/area"), u = e("zrender/tool/util"), a = this;
        a.type = t.COMPONENT_TYPE_TITLE;
        var f, l = a.getZlevelBase(), c = {};
        a.init = m, a.refresh = g, a.resize = y, m(i)
    }

    return e("../component").define("title", t), t
}), define("echarts/component/categoryAxis", ["require", "./base", "zrender/tool/util", "zrender/tool/area", "../component"], function (e) {
    function t(t, n, r, i, s) {
        function d() {
            var e = u.clone(i.data), t = i.axisLabel.formatter, n;
            for (var r = 0, s = e.length; r < s; r++)n = e[r].formatter || t, n && (typeof n == "function" ? typeof e[r].value != "undefined" ? e[r].value = n(e[r].value) : e[r] = n(e[r]) : typeof n == "string" && (typeof e[r].value != "undefined" ? e[r].value = n.replace("{value}", e[r].value) : e[r] = n.replace("{value}", e[r])));
            return e
        }

        function v() {
            var e = i.axisLabel.interval;
            if (e == "auto") {
                var t = i.axisLabel.textStyle.fontSize, n = f.getFont(i.axisLabel.textStyle), r = i.data,
                    s = i.data.length;
                if (i.position == "bottom" || i.position == "top")if (s > 3) {
                    var o = T(), l = !1, c, h;
                    e = 0;
                    while (!l && e < s) {
                        e++, l = !0, c = o * e - 10;
                        for (var d = 0; d < s; d += e) {
                            i.axisLabel.rotate !== 0 ? h = t : r[d].textStyle ? h = a.getTextWidth(p[d].value || p[d], f.getFont(u.merge(r[d].textStyle, i.axisLabel.textStyle, {
                                overwrite: !1,
                                recursive: !0
                            }))) : h = a.getTextWidth(p[d].value || p[d], n);
                            if (c < h) {
                                l = !1;
                                break
                            }
                        }
                    }
                } else e = 1; else if (s > 3) {
                    var o = T();
                    e = 1;
                    while (o * e - 6 < t && e < s)e++
                } else e = 1
            } else e += 1;
            return e
        }

        function m() {
            p = d(), h = v(), i.splitArea.show && E(), i.splitLine.show && w(), i.axisLine.show && g(), i.axisTick.show && y(), i.axisLabel.show && b();
            for (var e = 0, t = f.shapeList.length; e < t; e++)f.shapeList[e].id = r.newShapeId(f.type), r.addShape(f.shapeList[e])
        }

        function g() {
            var e = i.axisLine.lineStyle.width, t = e / 2, n = {shape: "line", zlevel: c + 1, hoverable: !1};
            switch (i.position) {
                case"left":
                    n.style = {xStart: l.getX() - t, yStart: l.getYend() + t, xEnd: l.getX() - t, yEnd: l.getY() - t};
                    break;
                case"right":
                    n.style = {
                        xStart: l.getXend() + t,
                        yStart: l.getYend() + t,
                        xEnd: l.getXend() + t,
                        yEnd: l.getY() - t
                    };
                    break;
                case"bottom":
                    n.style = {
                        xStart: l.getX() - t,
                        yStart: l.getYend() + t,
                        xEnd: l.getXend() + t,
                        yEnd: l.getYend() + t
                    };
                    break;
                case"top":
                    n.style = {xStart: l.getX() - t, yStart: l.getY() - t, xEnd: l.getXend() + t, yEnd: l.getY() - t}
            }
            i.name !== "" && (n.style.text = i.name, n.style.textPosition = i.nameLocation, n.style.textFont = f.getFont(i.nameTextStyle), i.nameTextStyle.align && (n.style.textAlign = i.nameTextStyle.align), i.nameTextStyle.baseline && (n.style.textBaseline = i.nameTextStyle.baseline), i.nameTextStyle.color && (n.style.textColor = i.nameTextStyle.color)), n.style.strokeColor = i.axisLine.lineStyle.color, n.style.lineWidth = e, i.position == "left" || i.position == "right" ? n.style.xStart = n.style.xEnd = f.subPixelOptimize(n.style.xEnd, e) : n.style.yStart = n.style.yEnd = f.subPixelOptimize(n.style.yEnd, e), n.style.lineType = i.axisLine.lineStyle.type, f.shapeList.push(n)
        }

        function y() {
            var e, t = i.data.length, n = i.axisTick, r = n.length, s = n.lineStyle.color, o = n.lineStyle.width,
                u = n.interval == "auto" ? h : n.interval - 0 + 1, a = n.onGap,
                p = a ? T() / 2 : typeof a == "undefined" ? i.boundaryGap ? T() / 2 : 0 : 0, d = p > 0 ? -u : 0;
            if (i.position == "bottom" || i.position == "top") {
                var v = i.position == "bottom" ? n.inside ? l.getYend() - r : l.getYend() : n.inside ? l.getY() : l.getY() - r,
                    m;
                for (var g = d; g < t; g += u)m = f.subPixelOptimize(C(g) + (g >= 0 ? p : 0), o), e = {
                    shape: "line",
                    zlevel: c,
                    hoverable: !1,
                    style: {xStart: m, yStart: v, xEnd: m, yEnd: v + r, strokeColor: s, lineWidth: o}
                }, f.shapeList.push(e)
            } else {
                var y = i.position == "left" ? n.inside ? l.getX() : l.getX() - r : n.inside ? l.getXend() - r : l.getXend(),
                    b;
                for (var g = d; g < t; g += u)b = f.subPixelOptimize(C(g) - (g >= 0 ? p : 0), o), e = {
                    shape: "line",
                    zlevel: c,
                    hoverable: !1,
                    style: {xStart: y, yStart: b, xEnd: y + r, yEnd: b, strokeColor: s, lineWidth: o}
                }, f.shapeList.push(e)
            }
        }

        function b() {
            var e, t = i.data, n = i.data.length, r = i.axisLabel.rotate, s = i.axisLabel.margin,
                o = i.axisLabel.textStyle, a;
            if (i.position == "bottom" || i.position == "top") {
                var d, v;
                i.position == "bottom" ? (d = l.getYend() + s, v = "top") : (d = l.getY() - s, v = "bottom");
                for (var m = 0; m < n; m += h) {
                    if ((p[m].value || p[m]) === "")continue;
                    a = u.merge(t[m].textStyle || {}, o, {overwrite: !1}), e = {
                        shape: "text",
                        zlevel: c,
                        hoverable: !1,
                        style: {
                            x: C(m),
                            y: d,
                            color: a.color,
                            text: p[m].value || p[m],
                            textFont: f.getFont(a),
                            textAlign: "center",
                            textBaseline: v
                        }
                    }, r && (e.style.textAlign = r > 0 ? i.position == "bottom" ? "right" : "left" : i.position == "bottom" ? "left" : "right", e.rotation = [r * Math.PI / 180, e.style.x, e.style.y]), f.shapeList.push(e)
                }
            } else {
                var g, y;
                i.position == "left" ? (g = l.getX() - s, y = "right") : (g = l.getXend() + s, y = "left");
                for (var m = 0; m < n; m += h) {
                    if ((p[m].value || p[m]) === "")continue;
                    a = u.merge(t[m].textStyle || {}, o, {overwrite: !1}), e = {
                        shape: "text",
                        zlevel: c,
                        hoverable: !1,
                        style: {
                            x: g,
                            y: C(m),
                            color: a.color,
                            text: p[m].value || p[m],
                            textFont: f.getFont(a),
                            textAlign: y,
                            textBaseline: m === 0 && i.name !== "" ? "bottom" : m == n - 1 && i.name !== "" ? "top" : "middle"
                        }
                    }, r && (e.rotation = [r * Math.PI / 180, e.style.x, e.style.y]), f.shapeList.push(e)
                }
            }
        }

        function w() {
            var e, t = i.data.length, n = i.splitLine, r = n.lineStyle.type, s = n.lineStyle.width,
                o = n.lineStyle.color;
            o = o instanceof Array ? o : [o];
            var u = o.length, a = n.onGap, p = a ? T() / 2 : typeof a == "undefined" ? i.boundaryGap ? T() / 2 : 0 : 0;
            t -= a || typeof a == "undefined" && i.boundaryGap ? 1 : 0;
            if (i.position == "bottom" || i.position == "top") {
                var d = l.getY(), v = l.getYend(), m;
                for (var g = 0; g < t; g += h)m = f.subPixelOptimize(C(g) + p, s), e = {
                    shape: "line",
                    zlevel: c,
                    hoverable: !1,
                    style: {
                        xStart: m,
                        yStart: d,
                        xEnd: m,
                        yEnd: v,
                        strokeColor: o[g / h % u],
                        lineType: r,
                        lineWidth: s
                    }
                }, f.shapeList.push(e)
            } else {
                var y = l.getX(), b = l.getXend(), w;
                for (var g = 0; g < t; g += h)w = f.subPixelOptimize(C(g) - p, s), e = {
                    shape: "line",
                    zlevel: c,
                    hoverable: !1,
                    style: {
                        xStart: y,
                        yStart: w,
                        xEnd: b,
                        yEnd: w,
                        strokeColor: o[g / h % u],
                        linetype: r,
                        lineWidth: s
                    }
                }, f.shapeList.push(e)
            }
        }

        function E() {
            var e, t = i.splitArea, n = t.areaStyle.color;
            if (n instanceof Array) {
                var r = n.length, s = i.data.length, o = t.onGap,
                    u = o ? T() / 2 : typeof o == "undefined" ? i.boundaryGap ? T() / 2 : 0 : 0;
                if (i.position == "bottom" || i.position == "top") {
                    var a = l.getY(), p = l.getHeight(), d = l.getX(), v;
                    for (var m = 0; m <= s; m += h)v = m < s ? C(m) + u : l.getXend(), e = {
                        shape: "rectangle",
                        zlevel: c,
                        hoverable: !1,
                        style: {x: d, y: a, width: v - d, height: p, color: n[m / h % r]}
                    }, f.shapeList.push(e), d = v
                } else {
                    var g = l.getX(), y = l.getWidth(), b = l.getYend(), w;
                    for (var m = 0; m <= s; m += h)w = m < s ? C(m) - u : l.getY(), e = {
                        shape: "rectangle",
                        zlevel: c,
                        hoverable: !1,
                        style: {x: g, y: w, width: y, height: b - w, color: n[m / h % r]}
                    }, f.shapeList.push(e), b = w
                }
            } else e = {
                shape: "rectangle",
                zlevel: c,
                hoverable: !1,
                style: {x: l.getX(), y: l.getY(), width: l.getWidth(), height: l.getHeight(), color: n}
            }, f.shapeList.push(e)
        }

        function S(e, t) {
            if (e.data.length < 1)return;
            l = t, x(e)
        }

        function x(e) {
            e && (i = f.reformOption(e), i.axisLabel.textStyle = u.merge(i.axisLabel.textStyle || {}, t.textStyle, {
                overwrite: !1,
                recursive: !0
            }), i.axisLabel.textStyle = u.merge(i.axisLabel.textStyle || {}, t.textStyle, {
                overwrite: !1,
                recursive: !0
            })), f.clear(), m()
        }

        function T() {
            var e = i.data.length, t = i.position == "bottom" || i.position == "top" ? l.getWidth() : l.getHeight();
            return i.boundaryGap ? t / e : t / (e > 1 ? e - 1 : 1)
        }

        function N(e) {
            var t = i.data, n = t.length, r = T(), s = i.boundaryGap ? r / 2 : 0;
            for (var o = 0; o < n; o++) {
                if (t[o] == e || typeof t[o].value != "undefined" && t[o].value == e)return i.position == "bottom" || i.position == "top" ? s = l.getX() + s : s = l.getYend() - s, s;
                s += r
            }
        }

        function C(e) {
            if (e < 0)return i.position == "bottom" || i.position == "top" ? l.getX() : l.getYend();
            if (e > i.data.length - 1)return i.position == "bottom" || i.position == "top" ? l.getXend() : l.getY();
            var t = T(), n = i.boundaryGap ? t / 2 : 0;
            return n += e * t, i.position == "bottom" || i.position == "top" ? n = l.getX() + n : n = l.getYend() - n, n
        }

        function k(e) {
            var t = i.data[e];
            return typeof t != "undefined" && typeof t.value != "undefined" ? t.value : t
        }

        function L(e) {
            var t = i.data, n = t.length;
            for (var r = 0; r < n; r++)if (t[r] == e || typeof t[r].value != "undefined" && t[r].value == e)return r
        }

        function A(e) {
            return e % h === 0
        }

        function O() {
            return i.position
        }

        var o = e("./base");
        o.call(this, t, r);
        var u = e("zrender/tool/util"), a = e("zrender/tool/area"), f = this;
        f.type = t.COMPONENT_TYPE_AXIS_CATEGORY;
        var l = s.grid, c = f.getZlevelBase(), h, p;
        f.init = S, f.refresh = x, f.getGap = T, f.getCoord = N, f.getCoordByIndex = C, f.getNameByIndex = k, f.getIndexByName = L, f.isMainAxis = A, f.getPosition = O, S(i, l)
    }

    return e("../component").define("categoryAxis", t), t
}), define("echarts/component/valueAxis", ["require", "./base", "zrender/tool/util", "../component"], function (e) {
    function t(t, n, r, i, s, o) {
        function g() {
            d = !1, x();
            if (!d)return;
            i.splitArea.show && S(), i.splitLine.show && E(), i.axisLine.show && y(), i.axisTick.show && b(), i.axisLabel.show && w();
            for (var e = 0, t = f.shapeList.length; e < t; e++)f.shapeList[e].id = r.newShapeId(f.type), r.addShape(f.shapeList[e])
        }

        function y() {
            var e = i.axisLine.lineStyle.width, t = e / 2, n = {shape: "line", zlevel: c + 1, hoverable: !1};
            switch (i.position) {
                case"left":
                    n.style = {xStart: l.getX() - t, yStart: l.getYend() + t, xEnd: l.getX() - t, yEnd: l.getY() - t};
                    break;
                case"right":
                    n.style = {
                        xStart: l.getXend() + t,
                        yStart: l.getYend() + t,
                        xEnd: l.getXend() + t,
                        yEnd: l.getY() - t
                    };
                    break;
                case"bottom":
                    n.style = {
                        xStart: l.getX() - t,
                        yStart: l.getYend() + t,
                        xEnd: l.getXend() + t,
                        yEnd: l.getYend() + t
                    };
                    break;
                case"top":
                    n.style = {xStart: l.getX() - t, yStart: l.getY() - t, xEnd: l.getXend() + t, yEnd: l.getY() - t}
            }
            i.name !== "" && (n.style.text = i.name, n.style.textPosition = i.nameLocation, n.style.textFont = f.getFont(i.nameTextStyle), i.nameTextStyle.align && (n.style.textAlign = i.nameTextStyle.align), i.nameTextStyle.baseline && (n.style.textBaseline = i.nameTextStyle.baseline), i.nameTextStyle.color && (n.style.textColor = i.nameTextStyle.color)), n.style.strokeColor = i.axisLine.lineStyle.color;
            var e = i.axisLine.lineStyle.width;
            n.style.lineWidth = e, i.position == "left" || i.position == "right" ? n.style.xStart = n.style.xEnd = f.subPixelOptimize(n.style.xEnd, e) : n.style.yStart = n.style.yEnd = f.subPixelOptimize(n.style.yEnd, e), n.style.lineType = i.axisLine.lineStyle.type, f.shapeList.push(n)
        }

        function b() {
            var e, t = v, n = v.length, r = i.axisTick, s = r.length, o = r.lineStyle.color, u = r.lineStyle.width;
            if (i.position == "bottom" || i.position == "top") {
                var a = i.position == "bottom" ? r.inside ? l.getYend() - s : l.getYend() : r.inside ? l.getY() : l.getY() - s,
                    h;
                for (var p = 0; p < n; p++)h = f.subPixelOptimize(O(t[p]), u), e = {
                    shape: "line",
                    zlevel: c,
                    hoverable: !1,
                    style: {xStart: h, yStart: a, xEnd: h, yEnd: a + s, strokeColor: o, lineWidth: u}
                }, f.shapeList.push(e)
            } else {
                var d = i.position == "left" ? r.inside ? l.getX() : l.getX() - s : r.inside ? l.getXend() - s : l.getXend(),
                    m;
                for (var p = 0; p < n; p++)m = f.subPixelOptimize(O(t[p]), u), e = {
                    shape: "line",
                    zlevel: c,
                    hoverable: !1,
                    style: {xStart: d, yStart: m, xEnd: d + s, yEnd: m, strokeColor: o, lineWidth: u}
                }, f.shapeList.push(e)
            }
        }

        function w() {
            var e, t = v, n = v.length, r = i.axisLabel.rotate, s = i.axisLabel.margin, o = i.axisLabel.textStyle;
            if (i.position == "bottom" || i.position == "top") {
                var u, a;
                i.position == "bottom" ? (u = l.getYend() + s, a = "top") : (u = l.getY() - s, a = "bottom");
                for (var h = 0; h < n; h++)e = {
                    shape: "text",
                    zlevel: c,
                    hoverable: !1,
                    style: {
                        x: O(t[h]),
                        y: u,
                        color: typeof o.color == "function" ? o.color(t[h]) : o.color,
                        text: m[h],
                        textFont: f.getFont(o),
                        textAlign: "center",
                        textBaseline: a
                    }
                }, r && (e.style.textAlign = r > 0 ? i.position == "bottom" ? "right" : "left" : i.position == "bottom" ? "left" : "right", e.rotation = [r * Math.PI / 180, e.style.x, e.style.y]), f.shapeList.push(e)
            } else {
                var p, d;
                i.position == "left" ? (p = l.getX() - s, d = "right") : (p = l.getXend() + s, d = "left");
                for (var h = 0; h < n; h++)e = {
                    shape: "text",
                    zlevel: c,
                    hoverable: !1,
                    style: {
                        x: p,
                        y: O(t[h]),
                        color: typeof o.color == "function" ? o.color(t[h]) : o.color,
                        text: m[h],
                        textFont: f.getFont(o),
                        textAlign: d,
                        textBaseline: h === 0 && i.name !== "" ? "bottom" : h == n - 1 && i.name !== "" ? "top" : "middle"
                    }
                }, r && (e.rotation = [r * Math.PI / 180, e.style.x, e.style.y]), f.shapeList.push(e)
            }
        }

        function E() {
            var e, t = v, n = v.length, r = i.splitLine, s = r.lineStyle.type, o = r.lineStyle.width,
                u = r.lineStyle.color;
            u = u instanceof Array ? u : [u];
            var a = u.length;
            if (i.position == "bottom" || i.position == "top") {
                var h = l.getY(), p = l.getYend(), d;
                for (var m = 1; m < n - 1; m++)d = f.subPixelOptimize(O(t[m]), o), e = {
                    shape: "line",
                    zlevel: c,
                    hoverable: !1,
                    style: {xStart: d, yStart: h, xEnd: d, yEnd: p, strokeColor: u[m % a], lineType: s, lineWidth: o}
                }, f.shapeList.push(e)
            } else {
                var g = l.getX(), y = l.getXend(), b;
                for (var m = 1; m < n - 1; m++)b = f.subPixelOptimize(O(t[m]), o), e = {
                    shape: "line",
                    zlevel: c,
                    hoverable: !1,
                    style: {xStart: g, yStart: b, xEnd: y, yEnd: b, strokeColor: u[m % a], lineType: s, lineWidth: o}
                }, f.shapeList.push(e)
            }
        }

        function S() {
            var e, t = i.splitArea.areaStyle.color;
            if (t instanceof Array) {
                var n = t.length, r = v, s = v.length;
                if (i.position == "bottom" || i.position == "top") {
                    var o = l.getY(), u = l.getHeight(), a = l.getX(), h;
                    for (var p = 0; p <= s; p++)h = p < s ? O(r[p]) : l.getXend(), e = {
                        shape: "rectangle",
                        zlevel: c,
                        hoverable: !1,
                        style: {x: a, y: o, width: h - a, height: u, color: t[p % n]}
                    }, f.shapeList.push(e), a = h
                } else {
                    var d = l.getX(), m = l.getWidth(), g = l.getYend(), y;
                    for (var p = 0; p <= s; p++)y = p < s ? O(r[p]) : l.getY(), e = {
                        shape: "rectangle",
                        zlevel: c,
                        hoverable: !1,
                        style: {x: d, y: y, width: m, height: g - y, color: t[p % n]}
                    }, f.shapeList.push(e), g = y
                }
            } else e = {
                shape: "rectangle",
                zlevel: c,
                hoverable: !1,
                style: {x: l.getX(), y: l.getY(), width: l.getWidth(), height: l.getHeight(), color: t}
            }, f.shapeList.push(e)
        }

        function x() {
            if (isNaN(i.min - 0) || isNaN(i.max - 0)) {
                var e, n = {}, r, u, a, f = s.legend;
                for (var l = 0, c = o.length; l < c; l++) {
                    if (o[l].type != t.CHART_TYPE_LINE && o[l].type != t.CHART_TYPE_BAR && o[l].type != t.CHART_TYPE_SCATTER && o[l].type != t.CHART_TYPE_K)continue;
                    if (f && !f.isSelected(o[l].name))continue;
                    u = o[l].xAxisIndex || 0, a = o[l].yAxisIndex || 0;
                    if (i.xAxisIndex != u && i.yAxisIndex != a)continue;
                    var v = o[l].name || "kener";
                    if (!o[l].stack) {
                        n[v] = n[v] || [], e = o[l].data;
                        for (var m = 0, g = e.length; m < g; m++)r = typeof e[m].value != "undefined" ? e[m].value : e[m], o[l].type == t.CHART_TYPE_SCATTER ? (i.xAxisIndex != -1 && n[v].push(r[0]), i.yAxisIndex != -1 && n[v].push(r[1])) : o[l].type == t.CHART_TYPE_K ? (n[v].push(r[0]), n[v].push(r[1]), n[v].push(r[2]), n[v].push(r[3])) : n[v].push(r)
                    } else {
                        var y = "__Magic_Key_Positive__" + o[l].stack, b = "__Magic_Key_Negative__" + o[l].stack;
                        n[y] = n[y] || [], n[b] = n[b] || [], n[v] = n[v] || [], e = o[l].data;
                        for (var m = 0, g = e.length; m < g; m++) {
                            r = typeof e[m].value != "undefined" ? e[m].value : e[m];
                            if (r == "-")continue;
                            r -= 0, r >= 0 ? typeof n[y][m] != "undefined" ? n[y][m] += r : n[y][m] = r : typeof n[b][m] != "undefined" ? n[b][m] += r : n[b][m] = r, i.scale && n[v].push(r)
                        }
                    }
                }
                for (var l in n) {
                    e = n[l];
                    for (var m = 0, g = e.length; m < g; m++)if (!isNaN(e[m])) {
                        d = !0, h = e[m], p = e[m];
                        break
                    }
                    if (d)break
                }
                for (var l in n) {
                    e = n[l];
                    for (var m = 0, g = e.length; m < g; m++)isNaN(e[m]) || (h = Math.min(h, e[m]), p = Math.max(p, e[m]))
                }
                h = isNaN(i.min - 0) ? h - Math.abs(h * i.boundaryGap[0]) : i.min - 0, p = isNaN(i.max - 0) ? p + Math.abs(p * i.boundaryGap[1]) : i.max - 0, h == p && (p === 0 ? p = i.power > 0 ? i.power : 1 : p > 0 ? h = p / i.splitNumber : p /= i.splitNumber), T(i.scale)
            } else d = !0, h = i.min - 0, p = i.max - 0, customerDefine = !0, N()
        }

        function T(e) {
            var t = i.splitNumber, n = i.precision, r, s;
            n === 0 ? s = i.power > 1 ? i.power : 1 : (s = Math.pow(10, n), h *= s, p *= s, s = i.power);
            var o;
            if (h >= 0 && p >= 0) {
                if (!e) {
                    while (p / s < t && s != 1)s /= 10;
                    h = 0
                } else {
                    while (h < s && s != 1)s /= 10;
                    n === 0 && (h = Math.floor(h / s) * s, p = Math.ceil(p / s) * s)
                }
                s = s > 1 ? s / 10 : 1, o = p - h, r = Math.ceil(o / t / s) * s, p = h + r * t
            } else if (h <= 0 && p <= 0) {
                s = -s;
                if (!e) {
                    while (h / s < t && s != -1)s /= 10;
                    p = 0
                } else {
                    while (p > s && s != -1)s /= 10;
                    n === 0 && (h = Math.ceil(h / s) * s, p = Math.floor(p / s) * s)
                }
                s = s < -1 ? s / 10 : -1, o = h - p, r = -Math.ceil(o / t / s) * s, h = -r * t + p
            } else {
                o = p - h;
                while (o / s < t && s != 1)s /= 10;
                var u = Math.round(p / o * t);
                u -= u == t ? 1 : 0, u += u === 0 ? 1 : 0, r = Math.ceil(Math.max(p / u, h / (u - t)) / s) * s, p = r * u, h = r * (u - t)
            }
            v = [];
            for (var a = 0; a <= t; a++)v.push(h + r * a);
            if (n !== 0) {
                s = Math.pow(10, n), h = (h / s).toFixed(n) - 0, p = (p / s).toFixed(n) - 0;
                for (var a = 0; a <= t; a++)v[a] = (v[a] / s).toFixed(n) - 0
            }
            C()
        }

        function N() {
            var e = i.splitNumber, t = i.precision, n = (p - h) / e;
            v = [];
            for (var r = 0; r <= e; r++)v.push((h + n * r).toFixed(t) - 0);
            C()
        }

        function C() {
            m = [];
            var e = i.axisLabel.formatter;
            if (e)for (var t = 0, n = v.length; t < n; t++)typeof e == "function" ? m.push(e(v[t])) : typeof e == "string" && m.push(e.replace("{value}", v[t])); else for (var t = 0, n = v.length; t < n; t++)m.push(f.numAddCommas(v[t]))
        }

        function k() {
            return x(), {min: h, max: p}
        }

        function L(e, t, n) {
            if (!n || n.length === 0)return;
            l = t, A(e, n)
        }

        function A(e, n) {
            e && (i = f.reformOption(e), i.axisLabel.textStyle = a.merge(i.axisLabel.textStyle || {}, t.textStyle, {
                overwrite: !1,
                recursive: !0
            }), i.axisLabel.textStyle = a.merge(i.axisLabel.textStyle || {}, t.textStyle, {
                overwrite: !1,
                recursive: !0
            }), o = n), r && (f.clear(), g())
        }

        function O(e) {
            e = e < h ? h : e, e = e > p ? p : e;
            var t = p - h, n, r;
            return i.position == "left" || i.position == "right" ? (n = l.getHeight(), r = l.getYend() - (e - h) / t * n) : (n = l.getWidth(), r = (e - h) / t * n + l.getX()), r
        }

        function M(e) {
            return i.position == "left" || i.position == "right" ? Math.abs(e / (p - h) * l.getHeight()) : Math.abs(e / (p - h) * l.getWidth())
        }

        function _() {
            return i.position
        }

        var u = e("./base");
        u.call(this, t, r);
        var a = e("zrender/tool/util"), f = this;
        f.type = t.COMPONENT_TYPE_AXIS_VALUE;
        var l = s.grid, c = f.getZlevelBase(), h, p, d, v, m;
        f.init = L, f.refresh = A, f.getExtremum = k, f.getCoord = O, f.getCoordSize = M, f.getPosition = _, L(i, l, o)
    }

    return e("../component").define("valueAxis", t), t
}), define("echarts/component/axis", ["require", "./base", "./categoryAxis", "./valueAxis", "../component"], function (e) {
    function t(t, n, r, i, s, o) {
        function l(e) {
            !e || e instanceof Array && e.length === 0 ? e = [{type: t.COMPONENT_TYPE_AXIS_VALUE}] : e instanceof Array || (e = [e]), e.length > 2 && (e = [e[0], e[1]]);
            if (o == "xAxis") {
                if (!e[0].position || e[0].position != "bottom" && e[0].position != "top") e[0].position = "bottom";
                e.length > 1 && (e[1].position = e[0].position == "bottom" ? "top" : "bottom");
                for (var n = 0, r = e.length; n < r; n++)e[n].type = e[n].type || "category", e[n].xAxisIndex = n, e[n].yAxisIndex = -1
            } else {
                if (!e[0].position || e[0].position != "left" && e[0].position != "right") e[0].position = "left";
                e.length > 1 && (e[1].position = e[0].position == "left" ? "right" : "left");
                for (var n = 0, r = e.length; n < r; n++)e[n].type = e[n].type || "value", e[n].xAxisIndex = -1, e[n].yAxisIndex = n
            }
            return e
        }

        function c(u, l, c) {
            s = l, o = c, a.clear();
            var h;
            o == "xAxis" ? (i.xAxis = a.reformOption(u.xAxis), h = i.xAxis) : (i.yAxis = a.reformOption(u.yAxis), h = i.yAxis);
            var p = e("./categoryAxis"), d = e("./valueAxis");
            for (var v = 0, m = h.length; v < m; v++)f.push(h[v].type == "category" ? new p(t, n, r, h[v], s) : new d(t, n, r, h[v], s, i.series))
        }

        function h(e) {
            var t, n;
            e && (o == "xAxis" ? (i.xAxis = a.reformOption(e.xAxis), t = i.xAxis) : (i.yAxis = l(e.yAxis), t = i.yAxis), n = e.series);
            for (var r = 0, s = f.length; r < s; r++)f[r].refresh && f[r].refresh(t ? t[r] : !1, n)
        }

        function p(e) {
            return f[e]
        }

        function d() {
            for (var e = 0, t = f.length; e < t; e++)f[e].dispose && f[e].dispose();
            f = []
        }

        var u = e("./base");
        u.call(this, t, r);
        var a = this;
        a.type = t.COMPONENT_TYPE_AXIS;
        var f = [];
        a.clear = d, a.reformOption = l, a.init = c, a.refresh = h, a.getAxis = p, c(i, s, o)
    }

    return e("../component").define("axis", t), t
}), define("echarts/component/grid", ["require", "./base", "../component"], function (e) {
    function t(t, n, r, i) {
        function d(e) {
            i = e, i.grid = o.reformOption(i.grid);
            var t = i.grid;
            h = r.getWidth(), p = r.getHeight(), a = o.parsePercent(t.x, h), f = o.parsePercent(t.y, p);
            var n = o.parsePercent(t.x2, h), s = o.parsePercent(t.y2, p);
            typeof t.width == "undefined" ? l = h - a - n : l = o.parsePercent(t.width, h), typeof t.height == "undefined" ? c = p - f - s : c = o.parsePercent(t.height, p), a = o.subPixelOptimize(a, t.borderWidth), f = o.subPixelOptimize(f, t.borderWidth), o.shapeList.push({
                shape: "rectangle",
                id: r.newShapeId("grid"),
                zlevel: u,
                hoverable: !1,
                style: {
                    x: a,
                    y: f,
                    width: l,
                    height: c,
                    brushType: t.borderWidth > 0 ? "both" : "fill",
                    color: t.backgroundColor,
                    strokeColor: t.borderColor,
                    lineWidth: t.borderWidth
                }
            }), r.addShape(o.shapeList[0])
        }

        function v() {
            return a
        }

        function m() {
            return f
        }

        function g() {
            return l
        }

        function y() {
            return c
        }

        function b() {
            return a + l
        }

        function w() {
            return f + c
        }

        function E() {
            return {x: a, y: f, width: l, height: c}
        }

        function S(e) {
            if (h != r.getWidth() || p != r.getHeight() || e) o.clear(), d(e || i)
        }

        var s = e("./base");
        s.call(this, t, r);
        var o = this;
        o.type = t.COMPONENT_TYPE_GRID;
        var u = o.getZlevelBase(), a, f, l, c, h, p;
        o.init = d, o.getX = v, o.getY = m, o.getWidth = g, o.getHeight = y, o.getXend = b, o.getYend = w, o.getArea = E, o.refresh = S, d(i)
    }

    return e("../component").define("grid", t), t
}), define("echarts/component/dataZoom", ["require", "./base", "../component", "zrender/tool/util", "zrender/tool/util", "zrender/tool/util", "../component"], function (e) {
    function t(t, n, r, i, s) {
        function S() {
            k(), L(), A(), O();
            for (var e = 0, t = u.shapeList.length; e < t; e++)u.shapeList[e].id = r.newShapeId(u.type), r.addShape(u.shapeList[e]);
            P()
        }

        function x() {
            var e, t, n, i, o = s.grid;
            return f.orient == "horizontal" ? (n = f.width || o.getWidth(), i = f.height || l, e = typeof f.x != "undefined" ? f.x : o.getX(), t = typeof f.y != "undefined" ? f.y : r.getHeight() - i - 2) : (n = f.width || l, i = f.height || o.getHeight(), e = typeof f.x != "undefined" ? f.x : 2, t = typeof f.y != "undefined" ? f.y : o.getY()), {
                x: e,
                y: t,
                width: n,
                height: i
            }
        }

        function T() {
            var e = i.series, n = i.xAxis;
            n && !(n instanceof Array) && (n = [n], i.xAxis = n);
            var r = i.yAxis;
            r && !(r instanceof Array) && (r = [r], i.yAxis = r);
            var s = [], o, u, a = f.xAxisIndex;
            if (n && typeof a == "undefined") {
                o = [];
                for (var l = 0, c = n.length; l < c; l++)(n[l].type == "category" || typeof n[l].type == "undefined") && o.push(l)
            } else a instanceof Array ? o = a : typeof a != "undefined" ? o = [a] : o = [];
            a = f.yAxisIndex;
            if (r && typeof a == "undefined") {
                u = [];
                for (var l = 0, c = r.length; l < c; l++)r[l].type == "category" && u.push(l)
            } else a instanceof Array ? u = a : typeof a != "undefined" ? u = [a] : u = [];
            for (var l = 0, c = e.length; l < c; l++) {
                if (e[l].type != t.CHART_TYPE_LINE && e[l].type != t.CHART_TYPE_BAR && e[l].type != t.CHART_TYPE_SCATTER && e[l].type != t.CHART_TYPE_K)continue;
                for (var p = 0, d = o.length; p < d; p++)if (o[p] == (e[l].xAxisIndex || 0)) {
                    s.push(l);
                    break
                }
                for (var p = 0, d = u.length; p < d; p++)if (u[p] == (e[l].yAxisIndex || 0)) {
                    s.push(l);
                    break
                }
                e[l].type == t.CHART_TYPE_SCATTER && typeof f.xAxisIndex == "undefined" && typeof f.yAxisIndex == "undefined" && s.push(l)
            }
            var v = typeof f.start != "undefined" && f.start >= 0 && f.start <= 100 ? f.start : 0,
                m = typeof f.end != "undefined" && f.end >= 0 && f.end <= 100 ? f.end : 100;
            v > m && (v += m, m = v - m, v -= m);
            var g = Math.round((m - v) / 100 * (f.orient == "horizontal" ? h.width : h.height));
            return {start: v, end: m, start2: 0, end2: 100, size: g, xAxisIndex: o, yAxisIndex: u, seriesIndex: s}
        }

        function N() {
            E = {xAxis: {}, yAxis: {}, series: {}};
            var e = i.xAxis, n = p.xAxisIndex;
            for (var r = 0, s = n.length; r < s; r++)E.xAxis[n[r]] = e[n[r]].data;
            var o = i.yAxis, u = p.yAxisIndex;
            for (var r = 0, s = u.length; r < s; r++)E.yAxis[u[r]] = o[u[r]].data;
            var a = i.series, f = p.seriesIndex, l;
            for (var r = 0, s = f.length; r < s; r++)l = a[f[r]], E.series[f[r]] = l.data, l.type == t.CHART_TYPE_SCATTER && C(f[r])
        }

        function C(n) {
            p.scatterMap = p.scatterMap || {}, p.scatterMap[n] = p.scatterMap[n] || {};
            var r = e("../component"), o = e("zrender/tool/util"), u = r.get("axis"), a = o.clone(i.xAxis);
            a instanceof Array ? (a[0].type = "value", a[1] && (a[1].type = "value")) : a.type = "value";
            var f = new u(t, null, !1, {xAxis: a, series: i.series}, s, "xAxis"), l = i.series[n].xAxisIndex || 0;
            p.scatterMap[n].x = f.getAxis(l).getExtremum(), f.dispose(), a = o.clone(i.yAxis), a instanceof Array ? (a[0].type = "value", a[1] && (a[1].type = "value")) : a.type = "value", f = new u(t, null, !1, {
                yAxis: a,
                series: i.series
            }, s, "yAxis"), l = i.series[n].yAxisIndex || 0, p.scatterMap[n].y = f.getAxis(l).getExtremum(), f.dispose()
        }

        function k() {
            u.shapeList.push({
                shape: "rectangle",
                zlevel: a,
                hoverable: !1,
                style: {x: h.x, y: h.y, width: h.width, height: h.height, color: f.backgroundColor}
            });
            var e = 0, n = E.xAxis, r = p.xAxisIndex;
            for (var s = 0, o = r.length; s < o; s++)e = Math.max(e, n[r[s]].length);
            var l = E.yAxis, c = p.yAxisIndex;
            for (var s = 0, o = c.length; s < o; s++)e = Math.max(e, l[c[s]].length);
            var d = p.seriesIndex[0], v = E.series[d], m = Number.MIN_VALUE, g = Number.MAX_VALUE, y;
            for (var s = 0, o = v.length; s < o; s++)y = typeof v[s] != "undefined" ? typeof v[s].value != "undefined" ? v[s].value : v[s] : 0, i.series[d].type == t.CHART_TYPE_K && (y = y[1]), isNaN(y) && (y = 0), m = Math.max(m, y), g = Math.min(g, y);
            var b = [], w = h.width / (e - (e > 1 ? 1 : 0)), S = h.height / (e - (e > 1 ? 1 : 0));
            for (var s = 0, o = e; s < o; s++)y = typeof v[s] != "undefined" ? typeof v[s].value != "undefined" ? v[s].value : v[s] : 0, i.series[d].type == t.CHART_TYPE_K && (y = y[1]), isNaN(y) && (y = 0), f.orient == "horizontal" ? b.push([h.x + w * s, h.y + h.height - 5 - Math.round((y - g) / (m - g) * (h.height - 10))]) : b.push([h.x + 5 + Math.round((y - g) / (m - g) * (h.width - 10)), h.y + S * s]);
            f.orient == "horizontal" ? (b.push([h.x + h.width, h.y + h.height]), b.push([h.x, h.y + h.height])) : (b.push([h.x, h.y + h.height]), b.push([h.x, h.y])), u.shapeList.push({
                shape: "polygon",
                zlevel: a,
                style: {pointList: b, color: f.dataBackgroundColor},
                hoverable: !1
            })
        }

        function L() {
            d = {
                shape: "rectangle",
                zlevel: a,
                draggable: !0,
                ondrift: M,
                ondragend: F,
                _type: "filler"
            }, f.orient == "horizontal" ? d.style = {
                x: h.x + Math.round(p.start / 100 * h.width) + c,
                y: h.y,
                width: p.size - c * 2,
                height: h.height,
                color: f.fillerColor,
                text: ":::",
                textPosition: "inside"
            } : d.style = {
                x: h.x,
                y: h.y + Math.round(p.start / 100 * h.height) + c,
                width: h.width,
                height: p.size - c * 2,
                color: f.fillerColor,
                text: "::",
                textPosition: "inside"
            }, d.highlightStyle = {brushType: "fill", color: "rgba(0,0,0,0)"}, u.shapeList.push(d)
        }

        function A() {
            var t = e("zrender/tool/util");
            v = {
                shape: "icon",
                zlevel: a,
                draggable: !0,
                style: {
                    iconType: "rectangle",
                    x: h.x,
                    y: h.y,
                    width: c,
                    height: c,
                    color: f.handleColor,
                    text: "=",
                    textPosition: "inside"
                },
                highlightStyle: {brushType: "fill"},
                ondrift: M,
                ondragend: F
            }, f.orient == "horizontal" ? (v.style.height = h.height, m = t.clone(v), v.style.x = d.style.x - c, m.style.x = d.style.x + d.style.width) : (v.style.width = h.width, m = t.clone(v), v.style.y = d.style.y - c, m.style.y = d.style.y + d.style.height), u.shapeList.push(v), u.shapeList.push(m)
        }

        function O() {
            var t = e("zrender/tool/util"), n = u.subPixelOptimize(h.x, 1), r = u.subPixelOptimize(h.y, 1);
            g = {
                shape: "rectangle",
                zlevel: a,
                hoverable: !1,
                style: {
                    x: n,
                    y: r,
                    width: h.width - (n > h.x ? 1 : 0),
                    height: h.height - (r > h.y ? 1 : 0),
                    lineWidth: 1,
                    brushType: "stroke",
                    strokeColor: f.handleColor
                }
            }, y = t.clone(g), u.shapeList.push(g), u.shapeList.push(y);
            return
        }

        function M(e, t, n) {
            f.zoomLock && (e = d);
            var r = e._type == "filler" ? c : 0;
            return f.orient == "horizontal" ? e.style.x + t - r <= h.x ? e.style.x = h.x + r : e.style.x + t + e.style.width + r >= h.x + h.width ? e.style.x = h.x + h.width - e.style.width - r : e.style.x += t : e.style.y + n - r <= h.y ? e.style.y = h.y + r : e.style.y + n + e.style.height + r >= h.y + h.height ? e.style.y = h.y + h.height - e.style.height - r : e.style.y += n, e._type == "filler" ? _() : D(), f.realtime ? B() : (clearTimeout(b), b = setTimeout(B, 200)), !0
        }

        function _() {
            f.orient == "horizontal" ? (v.style.x = d.style.x - c, m.style.x = d.style.x + d.style.width, p.start = Math.floor((v.style.x - h.x) / h.width * 100), p.end = Math.ceil((m.style.x + c - h.x) / h.width * 100)) : (v.style.y = d.style.y - c, m.style.y = d.style.y + d.style.height, p.start = Math.floor((v.style.y - h.y) / h.height * 100), p.end = Math.ceil((m.style.y + c - h.y) / h.height * 100)), r.modShape(v.id, v), r.modShape(m.id, m), P(), r.refresh()
        }

        function D() {
            var e, t;
            f.orient == "horizontal" ? (e = v.style.x, t = m.style.x, d.style.x = Math.min(e, t) + c, d.style.width = Math.abs(e - t) - c, p.start = Math.floor((Math.min(e, t) - h.x) / h.width * 100), p.end = Math.ceil((Math.max(e, t) + c - h.x) / h.width * 100)) : (e = v.style.y, t = m.style.y, d.style.y = Math.min(e, t) + c, d.style.height = Math.abs(e - t) - c, p.start = Math.floor((Math.min(e, t) - h.y) / h.height * 100), p.end = Math.ceil((Math.max(e, t) + c - h.y) / h.height * 100)), r.modShape(d.id, d), P(), r.refresh()
        }

        function P() {
            f.orient == "horizontal" ? (g.style.width = d.style.x - h.x, y.style.x = d.style.x + d.style.width, y.style.width = h.x + h.width - y.style.x) : (g.style.height = d.style.y - h.y, y.style.y = d.style.y + d.style.height, y.style.height = h.y + h.height - y.style.y), r.modShape(g.id, g), r.modShape(y.id, y)
        }

        function H() {
            if (!f.show)return;
            f.orient == "horizontal" ? (v.style.x = h.x + p.start / 100 * h.width, m.style.x = h.x + p.end / 100 * h.width - c, d.style.x = v.style.x + c, d.style.width = m.style.x - v.style.x - c) : (v.style.y = h.y + p.start / 100 * h.height, m.style.y = h.y + p.end / 100 * h.height - c, d.style.y = v.style.y + c, d.style.height = m.style.y - v.style.y - c), r.modShape(v.id, v), r.modShape(m.id, m), r.modShape(d.id, d), P(), r.refresh()
        }

        function B(e) {
            var r, s, o, u, a;
            for (var l in E) {
                r = E[l];
                for (var c in r)a = r[c], u = a.length, s = Math.floor(p.start / 100 * u), o = Math.ceil(p.end / 100 * u), i[l][c].type != t.CHART_TYPE_SCATTER ? i[l][c].data = a.slice(s, o) : i[l][c].data = j(c, a)
            }
            !w && (f.realtime || e) && n.dispatch(t.EVENT.DATA_ZOOM, null, {zoom: p}), f.start = p.start, f.end = p.end
        }

        function j(e, t) {
            var n = [], r = p.scatterMap[e], i, s, o, u, a;
            f.orient == "horizontal" ? (i = r.x.max - r.x.min, s = p.start / 100 * i + r.x.min, o = p.end / 100 * i + r.x.min, i = r.y.max - r.y.min, u = p.start2 / 100 * i + r.y.min, a = p.end2 / 100 * i + r.y.min) : (i = r.x.max - r.x.min, s = p.start2 / 100 * i + r.x.min, o = p.end2 / 100 * i + r.x.min, i = r.y.max - r.y.min, u = p.start / 100 * i + r.y.min, a = p.end / 100 * i + r.y.min);
            var l;
            for (var c = 0, h = t.length; c < h; c++)l = t[c].value || t[c], l[0] >= s && l[0] <= o && l[1] >= u && l[1] <= a && n.push(t[c]);
            return n
        }

        function F() {
            u.isDragend = !0
        }

        function I(e, r) {
            if (!u.isDragend || !e.target)return;
            B(), r.dragOut = !0, r.dragIn = !0, !w && !f.realtime && n.dispatch(t.EVENT.DATA_ZOOM, null, {zoom: p}), r.needRefresh = !1, u.isDragend = !1;
            return
        }

        function q(e, t) {
            t.needRefresh = !0;
            return
        }

        function R(e) {
            f.start = p.start = e.start, f.end = p.end = e.end, f.start2 = p.start2 = e.start2, f.end2 = p.end2 = e.end2, H(), B(!0);
            return
        }

        function U(e) {
            if (!e)return f.start = f.start2 = p.start = p.start2 = 0, f.end = f.end2 = p.end = p.end2 = 100, H(), B(!0), p;
            var t = s.grid.getArea(), n = {x: e.x, y: e.y, width: e.width, height: e.height};
            n.width < 0 && (n.x += n.width, n.width = -n.width), n.height < 0 && (n.y += n.height, n.height = -n.height);
            if (n.x > t.x + t.width || n.y > t.y + t.height)return !1;
            n.x < t.x && (n.x = t.x), n.x + n.width > t.x + t.width && (n.width = t.x + t.width - n.x), n.y + n.height > t.y + t.height && (n.height = t.y + t.height - n.y);
            var r, i = (n.x - t.x) / t.width, o = 1 - (n.x + n.width - t.x) / t.width,
                u = 1 - (n.y + n.height - t.y) / t.height, a = (n.y - t.y) / t.height;
            return f.orient == "horizontal" ? (r = p.end - p.start, p.start += r * i, p.end -= r * o, r = p.end2 - p.start2, p.start2 += r * u, p.end2 -= r * a) : (r = p.end - p.start, p.start += r * u, p.end -= r * a, r = p.end2 - p.start2, p.start2 += r * i, p.end2 -= r * o), f.start = p.start, f.end = p.end, f.start2 = p.start2, f.end2 = p.end2, H(), B(!0), p
        }

        function z(e, t) {
            var n, r = E.series, i = e.series, s;
            for (var o = 0, u = i.length; o < u; o++) {
                s = i[o].data, r[o] ? n = Math.floor(p.start / 100 * r[o].length) : n = 0;
                for (var a = 0, f = s.length; a < f; a++)t.series[o].data[a + n] = s[a], r[o] && (r[o][a + n] = s[a])
            }
        }

        function W(e) {
            w = e
        }

        function X(e, t) {
            if (!E)return t;
            var n = E.series;
            return n[e] ? Math.floor(p.start / 100 * n[e].length) + t : -1
        }

        function V(e) {
            i = e, i.dataZoom = u.reformOption(i.dataZoom), f = i.dataZoom, u.clear();
            if (i.dataZoom.show || u.query(i, "toolbox.show") && u.query(i, "toolbox.feature.dataZoom.show")) h = x(), p = T(), N();
            i.dataZoom.show && (S(), B())
        }

        function $() {
            u.clear();
            if (i.dataZoom.show || u.query(i, "toolbox.show") && u.query(i, "toolbox.feature.dataZoom.show")) h = x(), p = T();
            i.dataZoom.show && S()
        }

        var o = e("./base");
        o.call(this, t, r);
        var u = this;
        u.type = t.COMPONENT_TYPE_DATAZOOM;
        var a = u.getZlevelBase(), f, l = 28, c = 8, h, p, d, v, m, g, y, b, w = !1, E;
        u.init = V, u.resize = $, u.syncBackupData = z, u.absoluteZoom = R, u.rectZoom = U, u.ondragend = I, u.ondataZoom = q, u.silence = W, u.getRealDataIndex = X, V(i)
    }

    return e("../component").define("dataZoom", t), t
}), define("echarts/component/legend", ["require", "./base", "zrender/tool/util", "zrender/tool/area", "zrender/tool/color", "zrender/shape", "zrender/shape", "zrender/shape", "zrender/shape", "zrender/shape", "../component"], function (e) {
    function t(t, r, i, s, o) {
        function w() {
            d = T(), x(), E();
            for (var e = 0, t = c.shapeList.length; e < t; e++)c.shapeList[e].id = i.newShapeId(c.type), i.addShape(c.shapeList[e])
        }

        function E() {
            var e = h.data, t = e.length, n, r, s, o, u = h.textStyle, l, v, m = i.getWidth(), y = i.getHeight(),
                b = d.x, w = d.y, E = h.itemWidth, x = h.itemHeight, T = h.itemGap, L;
            h.orient == "vertical" && h.x == "right" && (b = d.x + d.width - E);
            for (var A = 0; A < t; A++) {
                l = a.merge(e[A].textStyle || {}, u, {overwrite: !1}), v = c.getFont(l), n = e[A].name || e[A];
                if (n === "") {
                    h.orient == "horizontal" ? (b = d.x, w += x + T) : (h.x == "right" ? b -= d.maxWidth + T : b += d.maxWidth + T, w = d.y);
                    continue
                }
                r = N(n).type, L = M(n), h.orient == "horizontal" ? m - b < 200 && E + 5 + f.getTextWidth(n, v) + (A == t - 1 || e[A + 1] === "" ? 0 : T) >= m - b && (b = d.x, w += x + T) : y - w < 200 && x + (A == t - 1 || e[A + 1] === "" ? 0 : T) >= y - w && (h.x == "right" ? b -= d.maxWidth + T : b += d.maxWidth + T, w = d.y), s = C(b, w, E, x, g[n] ? L : "#ccc", r, L), s._name = n, h.selectedMode && (s.onclick = k), c.shapeList.push(s), o = {
                    shape: "text",
                    zlevel: p,
                    style: {
                        x: b + E + 5,
                        y: w,
                        color: g[n] ? l.color === "auto" ? L : l.color : "#ccc",
                        text: n,
                        textFont: v,
                        textBaseline: "top"
                    },
                    highlightStyle: {color: L, brushType: "fill"},
                    hoverable: !!h.selectedMode,
                    clickable: !!h.selectedMode
                }, h.orient == "vertical" && h.x == "right" && (o.style.x -= E + 10, o.style.textAlign = "right"), o._name = n, h.selectedMode && (o.onclick = k), c.shapeList.push(o), h.orient == "horizontal" ? b += E + 5 + f.getTextWidth(n, v) + T : w += x + T
            }
            h.orient == "horizontal" && h.x == "center" && w != d.y && S()
        }

        function S() {
            var e = [], t = d.x;
            for (var n = 2, r = c.shapeList.length; n < r; n++)c.shapeList[n].style.x == t ? e.push((d.width - (c.shapeList[n - 1].style.x + f.getTextWidth(c.shapeList[n - 1].style.text, c.shapeList[n - 1].style.textFont) - t)) / 2) : n == r - 1 && e.push((d.width - (c.shapeList[n].style.x + f.getTextWidth(c.shapeList[n].style.text, c.shapeList[n].style.textFont) - t)) / 2);
            var i = -1;
            for (var n = 1, r = c.shapeList.length; n < r; n++) {
                c.shapeList[n].style.x == t && i++;
                if (e[i] === 0)continue;
                c.shapeList[n].style.x += e[i]
            }
        }

        function x() {
            var e = h.padding[0], t = h.padding[1], n = h.padding[2], r = h.padding[3];
            c.shapeList.push({
                shape: "rectangle",
                zlevel: p,
                hoverable: !1,
                style: {
                    x: d.x - r,
                    y: d.y - e,
                    width: d.width + r + t,
                    height: d.height + e + n,
                    brushType: h.borderWidth === 0 ? "fill" : "both",
                    color: h.backgroundColor,
                    strokeColor: h.borderColor,
                    lineWidth: h.borderWidth
                }
            })
        }

        function T() {
            var e = h.data, t = e.length, n = h.itemGap, r = h.itemWidth + 5, s = h.itemHeight, o = h.textStyle,
                u = c.getFont(o), l = 0, p = 0, d = h.padding, v = i.getWidth() - d[1] - d[3],
                m = i.getHeight() - d[0] - d[2], g = 0, y = 0;
            if (h.orient == "horizontal") {
                p = s;
                for (var b = 0; b < t; b++) {
                    if (e[b] === "") {
                        g -= n, g > v ? (l = v, p += s + n) : l = Math.max(l, g), p += s + n, g = 0;
                        continue
                    }
                    dataTextStyle = a.merge(e[b].textStyle || {}, o, {overwrite: !1}), g += r + f.getTextWidth(e[b].name || e[b], e[b].textStyle ? c.getFont(a.merge(e[b].textStyle || {}, o, {overwrite: !1})) : u) + n
                }
                p = Math.max(p, s), g -= n, g > v ? (l = v, p += s + n) : l = Math.max(l, g)
            } else {
                for (var b = 0; b < t; b++)y = Math.max(y, f.getTextWidth(e[b].name || e[b], e[b].textStyle ? c.getFont(a.merge(e[b].textStyle || {}, o, {overwrite: !1})) : u));
                y += r, l = y;
                for (var b = 0; b < t; b++) {
                    if (e[b] === "") {
                        g -= n, g > m ? (p = m, l += y + n) : p = Math.max(p, g), l += y + n, g = 0;
                        continue
                    }
                    g += s + n
                }
                l = Math.max(l, y), g -= n, g > m ? (p = m, l += y + n) : p = Math.max(p, g)
            }
            v = i.getWidth(), m = i.getHeight();
            var w;
            switch (h.x) {
                case"center":
                    w = Math.floor((v - l) / 2);
                    break;
                case"left":
                    w = h.padding[3] + h.borderWidth;
                    break;
                case"right":
                    w = v - l - h.padding[1] - h.padding[3] - h.borderWidth * 2;
                    break;
                default:
                    w = h.x - 0, w = isNaN(w) ? 0 : w
            }
            var E;
            switch (h.y) {
                case"top":
                    E = h.padding[0] + h.borderWidth;
                    break;
                case"bottom":
                    E = m - p - h.padding[0] - h.padding[2] - h.borderWidth * 2;
                    break;
                case"center":
                    E = Math.floor((m - p) / 2);
                    break;
                default:
                    E = h.y - 0, E = isNaN(E) ? 0 : E
            }
            return {x: w, y: E, width: l, height: p, maxWidth: y}
        }

        function N(e) {
            var n = s.series, r;
            for (var i = 0, o = n.length; i < o; i++) {
                if (n[i].name == e)return {type: n[i].type, series: n[i], seriesIndex: i, data: null, dataIndex: -1};
                if (n[i].type == t.CHART_TYPE_PIE || n[i].type == t.CHART_TYPE_RADAR || n[i].type == t.CHART_TYPE_CHORD || n[i].type == t.CHART_TYPE_FORCE) {
                    r = n[i].type != t.CHART_TYPE_FORCE ? n[i].data : n[i].categories;
                    for (var u = 0, a = r.length; u < a; u++)if (r[u].name == e)return {
                        type: n[i].type,
                        series: n[i],
                        seriesIndex: i,
                        data: r[u],
                        dataIndex: u
                    }
                }
            }
            return {type: "bar", series: null, seriesIndex: -1, data: null, dataIndex: -1}
        }

        function C(e, n, r, i, s, o, u) {
            var a = s === "#ccc" ? u : typeof s == "string" && s != "#ccc" ? l.lift(s, -0.3) : s, f = {
                shape: "icon",
                zlevel: p,
                style: {
                    iconType: "legendicon" + (o != t.CHART_TYPE_CHORD ? o : t.CHART_TYPE_PIE),
                    x: e,
                    y: n,
                    width: r,
                    height: i,
                    color: s,
                    strokeColor: s,
                    lineWidth: 2
                },
                highlightStyle: {color: a, strokeColor: a, lineWidth: 1},
                hoverable: h.selectedMode,
                clickable: h.selectedMode
            };
            switch (o) {
                case"line":
                    f.style.brushType = "stroke", f.highlightStyle.lineWidth = 3;
                    break;
                case"radar":
                case"scatter":
                    f.highlightStyle.lineWidth = 3;
                    break;
                case"k":
                    f.style.brushType = "both", f.highlightStyle.lineWidth = 3, f.highlightStyle.color = f.style.color = c.query(t, "k.itemStyle.normal.color") || "#fff", f.style.strokeColor = s != "#ccc" ? c.query(t, "k.itemStyle.normal.lineStyle.color") || "#ff3200" : s
            }
            return f
        }

        function k(e) {
            var n = e.target._name;
            if (h.selectedMode === "single")for (var i in g)g[i] = !1;
            g[n] = !g[n], r.dispatch(t.EVENT.LEGEND_SELECTED, e.event, {selected: g, target: n})
        }

        function L(e) {
            if (!c.query(e, "legend.data"))return;
            s = e, s.legend = c.reformOption(s.legend), s.legend.padding = c.reformCssArray(s.legend.padding), h = s.legend, c.clear(), g = {};
            var n = h.data || [], r, i, u, a;
            for (var f = 0, l = n.length; f < l; f++) {
                r = n[f].name || n[f];
                if (r === "")continue;
                i = N(r), i.series ? (!i.data || i.type != t.CHART_TYPE_PIE && i.type != t.CHART_TYPE_FORCE ? a = [i.series] : a = [i.data, i.series], u = c.getItemStyleColor(c.deepQuery(a, "itemStyle.normal.color"), i.seriesIndex, i.dataIndex, i.data), u && i.type != t.CHART_TYPE_K && O(r, u), g[r] = !0) : g[r] = !1
            }
            if (o)for (var p in o)g[p] = o[p];
            w()
        }

        function A(e) {
            if (e) {
                s = e, s.legend = c.reformOption(s.legend), s.legend.padding = c.reformCssArray(s.legend.padding);
                if (s.legend.selected)for (var t in s.legend.selected)g[t] = s.legend.selected[t]
            }
            h = s.legend, c.clear(), w()
        }

        function O(e, t) {
            m[e] = t
        }

        function M(e) {
            return m[e] || (m[e] = i.getColor(v++)), m[e]
        }

        function _(e) {
            return m[e] ? m[e] : !1
        }

        function D(e, t) {
            h.data.push(e), O(e, t), g[e] = !0
        }

        function P(e) {
            var t = h.data, n = [], r = !1;
            for (var i = 0, s = t.length; i < s; i++) {
                if (!r && t[i] == e) {
                    r = !0;
                    continue
                }
                n.push(t[i])
            }
            h.data = n
        }

        function H(e) {
            if (typeof e == "undefined")return;
            var t;
            for (var n = 0, r = c.shapeList.length; n < r; n++) {
                t = c.shapeList[n];
                if (t._name == e && t.shape != "text")return t
            }
        }

        function B(e, t) {
            var n;
            for (var r = 0, s = c.shapeList.length; r < s; r++)n = c.shapeList[r], n._name == e && n.shape != "text" && (g[e] || (t.style.color = "#ccc", t.style.strokeColor = "#ccc"), i.modShape(n.id, t))
        }

        function j(e) {
            return typeof g[e] != "undefined" ? g[e] : !0
        }

        function F() {
            return g
        }

        function I(e, t) {
            var n = e.selected;
            for (var r in g)g[r] != n[r] && (t.needRefresh = !0), g[r] = n[r];
            return
        }

        var u = e("./base");
        u.call(this, t, i);
        var a = e("zrender/tool/util"), f = e("zrender/tool/area"), l = e("zrender/tool/color"), c = this;
        c.type = t.COMPONENT_TYPE_LEGEND;
        var h, p = c.getZlevelBase(), d = {}, v = 0, m = {}, g = {}, y = e("zrender/shape").get("icon");
        for (var b in n)y.define("legendicon" + b, n[b]);
        c.init = L, c.refresh = A, c.setColor = O, c.getColor = M, c.hasColor = _, c.add = D, c.del = P, c.getItemShape = H, c.setItemShape = B, c.isSelected = j, c.getSelectedMap = F, c.onlegendSelected = I, L(s)
    }

    var n = {
        line: function (e, t) {
            var n = t.height / 2;
            e.moveTo(t.x, t.y + n), e.lineTo(t.x + t.width, t.y + n)
        }, pie: function (t, n) {
            var r = n.x, i = n.y, s = n.width, o = n.height, u = e("zrender/shape").get("sector");
            u.buildPath(t, {x: r + s / 2, y: i + o + 2, r: o + 2, r0: 6, startAngle: 45, endAngle: 135})
        }, chord: function (t, n) {
            var r = n.x, i = n.y, s = n.width, o = n.height, u = e("zrender/shape").get("beziercurve");
            t.moveTo(r, i + o), u.buildPath(t, {
                xStart: r,
                yStart: i + o,
                cpX1: r + s,
                cpY1: i + o,
                cpX2: r,
                cpY2: i + 4,
                xEnd: r + s,
                yEnd: i + 4
            }), t.lineTo(r + s, i), u.buildPath(t, {
                xStart: r + s,
                yStart: i,
                cpX1: r,
                cpY1: i,
                cpX2: r + s,
                cpY2: i + o - 4,
                xEnd: r,
                yEnd: i + o - 4
            }), t.lineTo(r, i + o)
        }, k: function (t, n) {
            var r = n.x, i = n.y, s = n.width, o = n.height, u = e("zrender/shape").get("candle");
            u.buildPath(t, {x: r + s / 2, y: [i + 1, i + 1, i + o - 6, i + o], width: s - 6})
        }, bar: function (e, t) {
            var n = t.x, r = t.y + 1, i = t.width, s = t.height - 2, o = 3;
            e.moveTo(n + o, r), e.lineTo(n + i - o, r), e.quadraticCurveTo(n + i, r, n + i, r + o), e.lineTo(n + i, r + s - o), e.quadraticCurveTo(n + i, r + s, n + i - o, r + s), e.lineTo(n + o, r + s), e.quadraticCurveTo(n, r + s, n, r + s - o), e.lineTo(n, r + o), e.quadraticCurveTo(n, r, n + o, r)
        }, force: function (t, n) {
            e("zrender/shape").get("icon").get("circle")(t, n)
        }, radar: function (e, t) {
            var n = 6, r = t.x + t.width / 2, i = t.y + t.height / 2, s = t.height / 2, o = 2 * Math.PI / n,
                u = -Math.PI / 2, a = r + s * Math.cos(u), f = i + s * Math.sin(u);
            e.moveTo(a, f), u += o;
            for (var l = 0, c = n - 1; l < c; l++)e.lineTo(r + s * Math.cos(u), i + s * Math.sin(u)), u += o;
            e.lineTo(a, f)
        }
    };
    return e("../component").define("legend", t), t
}), define("echarts/util/shape/handlePolygon", ["require", "zrender/tool/matrix", "zrender/shape", "zrender/shape/base", "zrender/shape"], function (e) {
    function n() {
        this.type = "handlePolygon"
    }

    var t = e("zrender/tool/matrix");
    return n.prototype = {
        buildPath: function (t, n) {
            e("zrender/shape").get("polygon").buildPath(t, n);
            return
        }, isCover: function (e, n, r) {
            if (e.__needTransform && e._transform) {
                var i = [];
                t.invert(i, e._transform);
                var s = [n, r];
                t.mulVector(s, i, [n, r, 1]), n == s[0] && r == s[1] && (Math.abs(e.rotation[0]) > 1e-4 || Math.abs(e.position[0]) > 1e-4 || Math.abs(e.position[1]) > 1e-4 || Math.abs(e.scale[0] - 1) > 1e-4 || Math.abs(e.scale[1] - 1) > 1e-4 ? e.__needTransform = !0 : e.__needTransform = !1), n = s[0], r = s[1]
            }
            var o = e.style.rect;
            return n >= o.x && n <= o.x + o.width && r >= o.y && r <= o.y + o.height ? !0 : !1
        }
    }, e("zrender/shape/base").derive(n), e("zrender/shape").define("handlePolygon", new n), n
}), define("echarts/component/dataRange", ["require", "./base", "zrender/tool/area", "zrender/tool/color", "zrender/tool/color", "../util/shape/handlePolygon", "../component"], function (e) {
    function t(t, n, r, i) {
        function T() {
            l = M(), O(), a.splitNumber <= 0 || a.calculable ? C() : N();
            for (var e = 0, t = u.shapeList.length; e < t; e++)u.shapeList[e].id = r.newShapeId(u.type), r.addShape(u.shapeList[e]);
            j()
        }

        function N() {
            var e = S, t = e.length, n, r, i, s = u.getFont(a.textStyle), c = l.x, h = l.y, p = a.itemWidth,
                d = a.itemHeight, v = a.itemGap, m = o.getTextHeight("国", s), g;
            a.orient == "vertical" && a.x == "right" && (c = l.x + l.width - p);
            var y = !0;
            a.text && (y = !1, a.text[0] && (i = _(c, h, a.text[0]), a.orient == "horizontal" ? c += o.getTextWidth(a.text[0], s) + b : (h += m + b, i.style.y += m / 2 + b, i.style.textBaseline = "bottom"), u.shapeList.push(i)));
            for (var E = 0; E < t; E++)n = e[E], g = X((t - E) * w + a.min), r = D(c, h, p, d, x[E] ? g : "#ccc"), r._idx = E, r.onclick = U, u.shapeList.push(r), y && (i = {
                shape: "text",
                zlevel: f,
                style: {
                    x: c + p + 5,
                    y: h,
                    color: x[E] ? a.textStyle.color : "#ccc",
                    text: e[E],
                    textFont: s,
                    textBaseline: "top"
                },
                highlightStyle: {brushType: "fill"},
                clickable: !0
            }, a.orient == "vertical" && a.x == "right" && (i.style.x -= p + 10, i.style.textAlign = "right"), i._idx = E, i.onclick = U, u.shapeList.push(i)), a.orient == "horizontal" ? c += p + (y ? 5 : 0) + (y ? o.getTextWidth(n, s) : 0) + v : h += d + v;
            !y && a.text[1] && (a.orient == "horizontal" ? c = c - v + b : h = h - v + b, i = _(c, h, a.text[1]), a.orient != "horizontal" && (i.style.y -= 5, i.style.textBaseline = "top"), u.shapeList.push(i))
        }

        function C() {
            var t, n, r = u.getFont(a.textStyle), i = l.x, s = l.y, h = a.itemWidth, p = a.itemHeight,
                d = o.getTextHeight("国", r), v = !0;
            a.text && (v = !1, a.text[0] && (n = _(i, s, a.text[0]), a.orient == "horizontal" ? i += o.getTextWidth(a.text[0], r) + b : (s += d + b, n.style.y += d / 2 + b, n.style.textBaseline = "bottom"), u.shapeList.push(n)));
            var m = e("zrender/tool/color"), g = 1 / (a.color.length - 1), y = [];
            for (var w = 0, E = a.color.length; w < E; w++)y.push([w * g, a.color[w]]);
            a.orient == "horizontal" ? (t = {
                shape: "rectangle",
                zlevel: f,
                style: {x: i, y: s, width: h * 10, height: p, color: m.getLinearGradient(i, s, i + h * 10, s, y)},
                hoverable: !1
            }, i += h * 10 + b) : (t = {
                shape: "rectangle",
                zlevel: f,
                style: {x: i, y: s, width: h, height: p * 10, color: m.getLinearGradient(i, s, i, s + p * 10, y)},
                hoverable: !1
            }, s += p * 10 + b), u.shapeList.push(t), a.calculable && (c = t.style, k(), A(), L()), !v && a.text[1] && (n = _(i, s, a.text[1]), u.shapeList.push(n))
        }

        function k() {
            m = {
                shape: "rectangle",
                zlevel: f + 1,
                style: {x: c.x, y: c.y, width: c.width, height: c.height, color: "rgba(255,255,255,0)"},
                highlightStyle: {strokeColor: "rgba(255,255,255,0.5)", lineWidth: 1},
                draggable: !0,
                ondrift: P,
                ondragend: H,
                _type: "filler"
            }, u.shapeList.push(m)
        }

        function L() {
            var e = c.x, t = c.y, n = c.width, r = c.height, i = u.getFont(a.textStyle), s = o.getTextHeight("国", i),
                l = Math.max(o.getTextWidth(a.max.toFixed(a.precision), i), o.getTextWidth(a.min.toFixed(a.precision), i)) + 2,
                p, v, m, g, y, b, w, E;
            a.orient == "horizontal" ? a.y != "bottom" ? (p = [[e, t], [e, t + r + s], [e - s, t + r + s], [e - 1, t + r], [e - 1, t]], v = e - l / 2 - s, m = t + r + s / 2 + 2, g = {
                x: e - l - s,
                y: t + r,
                width: l + s,
                height: s
            }, y = [[e + n, t], [e + n, t + r + s], [e + n + s, t + r + s], [e + n + 1, t + r], [e + n + 1, t]], b = e + n + l / 2 + s, w = m, E = {
                x: e + n,
                y: t + r,
                width: l + s,
                height: s
            }) : (p = [[e, t + r], [e, t - s], [e - s, t - s], [e - 1, t], [e - 1, t + r]], v = e - l / 2 - s, m = t - s / 2 - 2, g = {
                x: e - l - s,
                y: t - s,
                width: l + s,
                height: s
            }, y = [[e + n, t + r], [e + n, t - s], [e + n + s, t - s], [e + n + 1, t], [e + n + 1, t + r]], b = e + n + l / 2 + s, w = m, E = {
                x: e + n,
                y: t - s,
                width: l + s,
                height: s
            }) : (l += s, a.x != "right" ? (p = [[e, t], [e + n + s, t], [e + n + s, t - s], [e + n, t - 1], [e, t - 1]], v = e + n + l / 2 + s / 2, m = t - s / 2, g = {
                x: e + n,
                y: t - s,
                width: l + s,
                height: s
            }, y = [[e, t + r], [e + n + s, t + r], [e + n + s, t + s + r], [e + n, t + 1 + r], [e, t + r + 1]], b = v, w = t + r + s / 2, E = {
                x: e + n,
                y: t + r,
                width: l + s,
                height: s
            }) : (p = [[e + n, t], [e - s, t], [e - s, t - s], [e, t - 1], [e + n, t - 1]], v = e - l / 2 - s / 2, m = t - s / 2, g = {
                x: e - l - s,
                y: t - s,
                width: l + s,
                height: s
            }, y = [[e + n, t + r], [e - s, t + r], [e - s, t + s + r], [e, t + 1 + r], [e + n, t + r + 1]], b = v, w = t + r + s / 2, E = {
                x: e - l - s,
                y: t + r,
                width: l + s,
                height: s
            })), h = {
                shape: "handlePolygon",
                style: {
                    pointList: p,
                    text: a.max.toFixed(a.precision),
                    textX: v,
                    textY: m,
                    color: X(a.max),
                    rect: g,
                    x: p[0][0],
                    y: p[0][1],
                    _x: p[0][0],
                    _y: p[0][1]
                }
            }, h.highlightStyle = {strokeColor: h.style.color, lineWidth: 1}, d = {
                shape: "handlePolygon",
                style: {
                    pointList: y,
                    text: a.min.toFixed(a.precision),
                    textX: b,
                    textY: w,
                    color: X(a.min),
                    rect: E,
                    x: y[0][0],
                    y: y[0][1],
                    _x: y[0][0],
                    _y: y[0][1]
                }
            }, d.highlightStyle = {
                strokeColor: d.style.color,
                lineWidth: 1
            }, h.zlevel = d.zlevel = f + 1, h.draggable = d.draggable = !0, h.ondrift = d.ondrift = P, h.ondragend = d.ondragend = H, h.style.textColor = d.style.textColor = a.textStyle.color, h.style.textAlign = d.style.textAlign = "center", h.style.textPosition = d.style.textPosition = "specific", h.style.textBaseline = d.style.textBaseline = "middle", h.style.width = d.style.width = 0, h.style.height = d.style.height = 0, h.style.textPosition = d.style.textPosition = "specific", u.shapeList.push(h), u.shapeList.push(d)
        }

        function A() {
            var e = c.x, t = c.y, n = c.width, r = c.height;
            p = {
                shape: "rectangle",
                zlevel: f + 1,
                style: {
                    x: e,
                    y: t,
                    width: a.orient == "horizontal" ? 0 : n,
                    height: a.orient == "horizontal" ? r : 0,
                    color: "#ccc"
                },
                hoverable: !1
            }, v = {
                shape: "rectangle",
                zlevel: f + 1,
                style: {
                    x: a.orient == "horizontal" ? e + n : e,
                    y: a.orient == "horizontal" ? t : t + r,
                    width: a.orient == "horizontal" ? 0 : n,
                    height: a.orient == "horizontal" ? r : 0,
                    color: "#ccc"
                },
                hoverable: !1
            }, u.shapeList.push(p), u.shapeList.push(v)
        }

        function O() {
            var e = a.padding[0], t = a.padding[1], n = a.padding[2], r = a.padding[3];
            u.shapeList.push({
                shape: "rectangle",
                zlevel: f,
                hoverable: !1,
                style: {
                    x: l.x - r,
                    y: l.y - e,
                    width: l.width + r + t,
                    height: l.height + e + n,
                    brushType: a.borderWidth === 0 ? "fill" : "both",
                    color: a.backgroundColor,
                    strokeColor: a.borderColor,
                    lineWidth: a.borderWidth
                }
            })
        }

        function M() {
            var e = S, t = e.length, n = a.itemGap, i = a.itemWidth, s = a.itemHeight, f = 0, l = 0,
                c = u.getFont(a.textStyle), h = o.getTextHeight("国", c);
            if (a.orient == "horizontal") {
                if (a.text || a.splitNumber <= 0 || a.calculable) f = (a.splitNumber <= 0 || a.calculable ? i * 10 + n : t * (i + n)) + (a.text && typeof a.text[0] != "undefined" ? o.getTextWidth(a.text[0], c) + b : 0) + (a.text && typeof a.text[1] != "undefined" ? o.getTextWidth(a.text[1], c) + b : 0); else {
                    i += 5;
                    for (var p = 0; p < t; p++)f += i + o.getTextWidth(e[p], c) + n
                }
                f -= n, l = Math.max(h, s)
            } else {
                var d;
                if (a.text || a.splitNumber <= 0 || a.calculable) l = (a.splitNumber <= 0 || a.calculable ? s * 10 + n : t * (s + n)) + (a.text && typeof a.text[0] != "undefined" ? b + h : 0) + (a.text && typeof a.text[1] != "undefined" ? b + h : 0), d = Math.max(o.getTextWidth(a.text && a.text[0] || "", c), o.getTextWidth(a.text && a.text[1] || "", c)), f = Math.max(i, d); else {
                    l = (s + n) * t, i += 5, d = 0;
                    for (var p = 0; p < t; p++)d = Math.max(d, o.getTextWidth(e[p], c));
                    f = i + d
                }
                l -= n
            }
            var v, m = r.getWidth();
            switch (a.x) {
                case"center":
                    v = Math.floor((m - f) / 2);
                    break;
                case"left":
                    v = a.padding[3] + a.borderWidth;
                    break;
                case"right":
                    v = m - f - a.padding[1] - a.borderWidth;
                    break;
                default:
                    v = u.parsePercent(a.x, m), v = isNaN(v) ? 0 : v
            }
            var g, y = r.getHeight();
            switch (a.y) {
                case"top":
                    g = a.padding[0] + a.borderWidth;
                    break;
                case"bottom":
                    g = y - l - a.padding[2] - a.borderWidth;
                    break;
                case"center":
                    g = Math.floor((y - l) / 2);
                    break;
                default:
                    g = u.parsePercent(a.y, y), g = isNaN(g) ? 0 : g
            }
            if (a.calculable) {
                var w = Math.max(o.getTextWidth(a.max, c), o.getTextWidth(a.min, c)) + h;
                a.orient == "horizontal" ? (v < w && (v = w), v + f + w > m && (v -= w)) : (g < h && (g = h), g + l + h > y && (g -= h))
            }
            return {x: v, y: g, width: f, height: l}
        }

        function _(e, t, n) {
            return {
                shape: "text",
                zlevel: f,
                style: {
                    x: a.orient == "horizontal" ? e : l.x + l.width / 2,
                    y: a.orient == "horizontal" ? l.y + l.height / 2 : t,
                    color: a.textStyle.color,
                    text: n,
                    textFont: u.getFont(a.textStyle),
                    textBaseline: a.orient == "horizontal" ? "middle" : "top",
                    textAlign: a.orient == "horizontal" ? "left" : "center"
                },
                hoverable: !1
            }
        }

        function D(e, t, n, r, i) {
            return {
                shape: "rectangle",
                zlevel: f,
                style: {x: e, y: t + 1, width: n, height: r - 2, color: i},
                highlightStyle: {strokeColor: i, lineWidth: 1},
                clickable: !0
            }
        }

        function P(e, t, n) {
            var r = c.x, i = c.y, s = c.width, o = c.height;
            return a.orient == "horizontal" ? e.style.x + t <= r ? e.style.x = r : e.style.x + t + e.style.width >= r + s ? e.style.x = r + s - e.style.width : e.style.x += t : e.style.y + n <= i ? e.style.y = i : e.style.y + n + e.style.height >= i + o ? e.style.y = i + o - e.style.height : e.style.y += n, e._type == "filler" ? F() : I(e), a.realtime ? R() : (clearTimeout(y), y = setTimeout(R, 200)), !0
        }

        function H() {
            u.isDragend = !0
        }

        function B(e, r) {
            if (!u.isDragend || !e.target)return;
            R(), r.dragOut = !0, r.dragIn = !0, a.realtime || n.dispatch(t.EVENT.DATA_RANGE, null, {
                range: {
                    start: g.end,
                    end: g.start
                }
            }), r.needRefresh = !1, u.isDragend = !1;
            return
        }

        function j() {
            if (a.range) {
                typeof a.range.start != "undefined" && (g.end = a.range.start), typeof a.range.end != "undefined" && (g.start = a.range.end);
                if (g.start != 100 || g.end !== 0) {
                    if (a.orient == "horizontal") {
                        var e = m.style.width;
                        m.style.x += e * (100 - g.start) / 100, m.style.width = e * (g.start - g.end) / 100
                    } else {
                        var t = m.style.height;
                        m.style.y += t * (100 - g.start) / 100, m.style.height = t * (g.start - g.end) / 100
                    }
                    r.modShape(m.id, m), F()
                }
            }
        }

        function F() {
            var e = c.x, t = c.y, n = c.width, r = c.height;
            a.orient == "horizontal" ? (h.style.x = m.style.x, p.style.width = h.style.x - e, d.style.x = m.style.x + m.style.width, v.style.x = d.style.x, v.style.width = e + n - d.style.x, g.start = Math.ceil(100 - (h.style.x - e) / n * 100), g.end = Math.floor(100 - (d.style.x - e) / n * 100)) : (h.style.y = m.style.y, p.style.height = h.style.y - t, d.style.y = m.style.y + m.style.height, v.style.y = d.style.y, v.style.height = t + r - d.style.y, g.start = Math.ceil(100 - (h.style.y - t) / r * 100), g.end = Math.floor(100 - (d.style.y - t) / r * 100)), q(!1)
        }

        function I(e) {
            var t = c.x, n = c.y, r = c.width, i = c.height, s, o;
            a.orient == "horizontal" ? (s = h.style.x, o = d.style.x, e.id == h.id && s >= o ? (o = s, d.style.x = s) : e.id == d.id && s >= o && (s = o, h.style.x = s), m.style.x = s, m.style.width = o - s, p.style.width = s - t, v.style.x = o, v.style.width = t + r - o, g.start = Math.ceil(100 - (s - t) / r * 100), g.end = Math.floor(100 - (o - t) / r * 100)) : (s = h.style.y, o = d.style.y, e.id == h.id && s >= o ? (o = s, d.style.y = s) : e.id == d.id && s >= o && (s = o, h.style.y = s), m.style.y = s, m.style.height = o - s, p.style.height = s - n, v.style.y = o, v.style.height = n + i - o, g.start = Math.ceil(100 - (s - n) / i * 100), g.end = Math.floor(100 - (o - n) / i * 100)), q(!0)
        }

        function q(e) {
            h.position = [h.style.x - h.style._x, h.style.y - h.style._y], a.precision === 0 ? h.style.text = Math.round(w * g.start + a.min) + "" : h.style.text = (w * g.start + a.min).toFixed(a.precision), h.style.color = h.highlightStyle.strokeColor = X(w * g.start + a.min), r.modShape(h.id, h), d.position = [d.style.x - d.style._x, d.style.y - d.style._y], a.precision === 0 ? d.style.text = Math.round(w * g.end + a.min) + "" : d.style.text = (w * g.end + a.min).toFixed(a.precision), d.style.color = d.highlightStyle.strokeColor = X(w * g.end + a.min), r.modShape(d.id, d), r.modShape(p.id, p), r.modShape(v.id, v), e && r.modShape(m.id, m), r.refresh()
        }

        function R() {
            a.realtime && n.dispatch(t.EVENT.DATA_RANGE, null, {range: {start: g.end, end: g.start}})
        }

        function U(e) {
            var r = e.target._idx;
            x[r] = !x[r], n.dispatch(t.EVENT.REFRESH)
        }

        function z(t) {
            if (typeof u.query(t, "dataRange.min") == "undefined" || typeof u.query(t, "dataRange.max") == "undefined")return;
            i = t, i.dataRange = u.reformOption(i.dataRange), i.dataRange.padding = u.reformCssArray(i.dataRange.padding), a = i.dataRange, u.clear(), x = {};
            var n = e("zrender/tool/color"), r = a.splitNumber <= 0 || a.calculable ? 100 : a.splitNumber;
            E = n.getGradientColors(a.color, Math.max((r - a.color.length) / (a.color.length - 1), 0) + 1);
            if (E.length > r) {
                var s = E.length, o = [E[0]], f = s / (r - 1);
                for (var l = 1; l < r - 1; l++)o.push(E[Math.floor(l * f)]);
                o.push(E[s - 1]), E = o
            }
            a.precision === 0 ? w = Math.round((a.max - a.min) / r) || 1 : (w = (a.max - a.min) / r, w = w.toFixed(a.precision) - 0), S = [];
            for (var l = 0; l < r; l++)x[l] = !0, S.unshift((l * w + a.min).toFixed(a.precision) + " - " + ((l + 1) * w + a.min).toFixed(a.precision));
            g = {start: 100, end: 0}, T()
        }

        function W(e) {
            e && (i = e, i.dataRange = u.reformOption(i.dataRange), i.dataRange.padding = u.reformCssArray(i.dataRange.padding)), a = i.dataRange, a.range = {
                start: g.end,
                end: g.start
            }, u.clear(), T()
        }

        function X(e) {
            if (isNaN(e))return null;
            e < a.min ? e = a.min : e > a.max && (e = a.max);
            if (a.calculable)if (e > w * g.start + a.min || e < w * g.end + a.min)return null;
            var t = E.length - Math.ceil((e - a.min) / (a.max - a.min) * E.length);
            return t == E.length && t--, x[t] ? E[t] : null
        }

        var s = e("./base");
        s.call(this, t, r);
        var o = e("zrender/tool/area"), u = this;
        u.type = t.COMPONENT_TYPE_DATARANGE;
        var a, f = u.getZlevelBase(), l = {}, c, h, p, d, v, m, g, y, b = 10, w, E, S, x = {};
        u.init = z, u.refresh = W, u.getColor = X, u.ondragend = B, z(i)
    }

    return e("../util/shape/handlePolygon"), e("../component").define("dataRange", t), t
}), define("echarts/component/tooltip", ["require", "./base", "../util/ecData", "zrender/config", "zrender/shape", "zrender/tool/event", "zrender/tool/area", "zrender/tool/color", "zrender/tool/util", "zrender/shape/base", "../component"], function (e) {
    function t(t, n, r, i, s, o) {
        function U(e) {
            if (!e)return "";
            var t = [];
            if (e.transitionDuration) {
                var n = "left " + e.transitionDuration + "s," + "top " + e.transitionDuration + "s";
                t.push("transition:" + n), t.push("-moz-transition:" + n), t.push("-webkit-transition:" + n), t.push("-o-transition:" + n)
            }
            e.backgroundColor && (t.push("background-Color:" + p.toHex(e.backgroundColor)), t.push("filter:alpha(opacity=70)"), t.push("background-Color:" + e.backgroundColor)), typeof e.borderWidth != "undefined" && t.push("border-width:" + e.borderWidth + "px"), typeof e.borderColor != "undefined" && t.push("border-color:" + e.borderColor), typeof e.borderRadius != "undefined" && (t.push("border-radius:" + e.borderRadius + "px"), t.push("-moz-border-radius:" + e.borderRadius + "px"), t.push("-webkit-border-radius:" + e.borderRadius + "px"), t.push("-o-border-radius:" + e.borderRadius + "px"));
            var r = e.textStyle;
            r && (r.color && t.push("color:" + r.color), r.decoration && t.push("text-decoration:" + r.decoration), r.align && t.push("text-align:" + r.align), r.fontFamily && t.push("font-family:" + r.fontFamily), r.fontSize && t.push("font-size:" + r.fontSize + "px"), r.fontSize && t.push("line-height:" + Math.round(r.fontSize * 3 / 2) + "px"), r.fontStyle && t.push("font-style:" + r.fontStyle), r.fontWeight && t.push("font-weight:" + r.fontWeight));
            var i = e.padding;
            return typeof i != "undefined" && (i = g.reformCssArray(i), t.push("padding:" + i[0] + "px " + i[1] + "px " + i[2] + "px " + i[3] + "px")), t = t.join(";") + ";", t
        }

        function z() {
            N && (N.style.display = "none");
            var e = !1;
            q.invisible || (q.invisible = !0, r.modShape(q.id, q), e = !0), R.invisible || (R.invisible = !0, r.modShape(R.id, R), e = !0), F && F.tipShape.length > 0 && (r.delShape(F.tipShape), F = !1), e && r.refresh()
        }

        function W(e, t, n) {
            var r = N.offsetHeight, i = N.offsetWidth;
            e + i > j && (e -= i + 40), t + r > B && (t -= r - 20), t < 20 && (t = 0), N.style.cssText = C + k + (n ? n : "") + "left:" + e + "px;top:" + t + "px;", (r < 10 || i < 10) && setTimeout(X, 20)
        }

        function X() {
            if (N) {
                var e = "", t = N.offsetHeight, n = N.offsetWidth;
                N.offsetLeft + n > j && (e += "left:" + (j - n - 20) + "px;"), N.offsetTop + t > B && (e += "top:" + (B - t - 10) + "px;"), e !== "" && (N.style.cssText += e)
            }
        }

        function V() {
            var e, t;
            if (!D) J() || $(); else {
                if (D._type == "island" && i.tooltip.show) {
                    Y();
                    return
                }
                var n = a.get(D, "series"), r = a.get(D, "data");
                e = g.deepQuery([r, n, i], "tooltip.show"), typeof n == "undefined" || typeof r == "undefined" || e === !1 ? (clearTimeout(A), clearTimeout(M), A = setTimeout(z, O)) : (t = g.deepQuery([r, n, i], "tooltip.trigger"), t == "axis" ? Q(n.xAxisIndex, n.yAxisIndex, a.get(D, "dataIndex")) : Y())
            }
        }

        function $() {
            if (!E || !S) {
                A = setTimeout(z, O);
                return
            }
            var e = i.series, n, r;
            for (var s = 0, o = e.length; s < o; s++)if (g.deepQuery([e[s], i], "tooltip.trigger") == "axis") {
                n = e[s].xAxisIndex || 0, r = e[s].yAxisIndex || 0;
                if (E.getAxis(n) && E.getAxis(n).type == t.COMPONENT_TYPE_AXIS_CATEGORY) {
                    Q(n, r, K("x", E.getAxis(n)));
                    return
                }
                if (S.getAxis(r) && S.getAxis(r).type == t.COMPONENT_TYPE_AXIS_CATEGORY) {
                    Q(n, r, K("y", S.getAxis(r)));
                    return
                }
            }
        }

        function J() {
            if (!x)return !1;
            var e = c.getX(P), t = c.getY(P), n = x.getNearestIndex([e, t]), r;
            return n ? (r = n.valueIndex, n = n.polarIndex) : n = -1, n != -1 ? G(n, r) : !1
        }

        function K(e, t) {
            var n = -1, r = c.getX(P), i = c.getY(P);
            if (e == "x") {
                var s, o, u = w.getXend(), a = t.getCoordByIndex(n);
                while (a < u) {
                    a <= r && (s = a);
                    if (a >= r)break;
                    a = t.getCoordByIndex(++n), o = a
                }
                return r - s < o - r ? n -= n !== 0 ? 1 : 0 : typeof t.getNameByIndex(n) == "undefined" && (n -= 1), n
            }
            var f, l, h = w.getY(), a = t.getCoordByIndex(n);
            while (a > h) {
                a >= i && (l = a);
                if (a <= i)break;
                a = t.getCoordByIndex(++n), f = a
            }
            return i - f > l - i ? n -= n !== 0 ? 1 : 0 : typeof t.getNameByIndex(n) == "undefined" && (n -= 1), n
        }

        function Q(e, r, o) {
            !P.connectTrigger && n.dispatch(t.EVENT.TOOLTIP_IN_GRID, P);
            if (typeof E == "undefined" || typeof S == "undefined" || typeof e == "undefined" || typeof r == "undefined" || o < 0) {
                clearTimeout(A), clearTimeout(M), A = setTimeout(z, O);
                return
            }
            var u = i.series, a = [], f = [], l, h, p, d, v, m = "";
            if (i.tooltip.trigger == "axis") {
                if (i.tooltip.show === !1)return;
                d = i.tooltip.formatter
            }
            if (e != -1 && E.getAxis(e).type == t.COMPONENT_TYPE_AXIS_CATEGORY) {
                l = E.getAxis(e);
                for (var y = 0, x = u.length; y < x; y++) {
                    if (!at(u[y].name))continue;
                    u[y].xAxisIndex == e && g.deepQuery([u[y], i], "tooltip.trigger") == "axis" && (v = g.query(u[y], "tooltip.showContent") || v, d = g.query(u[y], "tooltip.formatter") || d, m += U(g.query(u[y], "tooltip")), a.push(u[y]), f.push(y))
                }
                n.dispatch(t.EVENT.TOOLTIP_HOVER, P, {
                    seriesIndex: f,
                    dataIndex: b.dataZoom ? b.dataZoom.getRealDataIndex(f, o) : o
                }), p = c.getY(P) + 10, h = g.subPixelOptimize(l.getCoordByIndex(o), I), Z(a, h, w.getY(), h, w.getYend(), l.getGap()), h += 10
            } else if (r != -1 && S.getAxis(r).type == t.COMPONENT_TYPE_AXIS_CATEGORY) {
                l = S.getAxis(r);
                for (var y = 0, x = u.length; y < x; y++) {
                    if (!at(u[y].name))continue;
                    u[y].yAxisIndex == r && g.deepQuery([u[y], i], "tooltip.trigger") == "axis" && (v = g.query(u[y], "tooltip.showContent") || v, d = g.query(u[y], "tooltip.formatter") || d, m += U(g.query(u[y], "tooltip")), a.push(u[y]), f.push(y))
                }
                n.dispatch(t.EVENT.TOOLTIP_HOVER, P, {
                    seriesIndex: f,
                    dataIndex: b.dataZoom ? b.dataZoom.getRealDataIndex(f, o) : o
                }), h = c.getX(P) + 10, p = g.subPixelOptimize(l.getCoordByIndex(o), I), Z(a, w.getX(), p, w.getXend(), p, l.getGap()), p += 10
            }
            if (a.length > 0) {
                var T;
                if (typeof d == "function") {
                    var C = [];
                    for (var y = 0, x = a.length; y < x; y++)T = a[y].data[o], T = typeof T != "undefined" ? typeof T.value != "undefined" ? T.value : T : "-", C.push([a[y].name || "", l.getNameByIndex(o), T]);
                    H = "axis:" + o, N.innerHTML = d(C, H, nt)
                } else if (typeof d == "string") {
                    H = NaN, d = d.replace("{a}", "{a0}").replace("{b}", "{b0}").replace("{c}", "{c0}");
                    for (var y = 0, x = a.length; y < x; y++)d = d.replace("{a" + y + "}", vt(a[y].name || "")), d = d.replace("{b" + y + "}", vt(l.getNameByIndex(o))), T = a[y].data[o], T = typeof T != "undefined" ? typeof T.value != "undefined" ? T.value : T : "-", d = d.replace("{c" + y + "}", T instanceof Array ? T : g.numAddCommas(T));
                    N.innerHTML = d
                } else {
                    H = NaN, d = vt(l.getNameByIndex(o));
                    for (var y = 0, x = a.length; y < x; y++)d += "<br/>" + vt(a[y].name || "") + " : ", T = a[y].data[o], T = typeof T != "undefined" ? typeof T.value != "undefined" ? T.value : T : "-", d += T instanceof Array ? T : g.numAddCommas(T);
                    N.innerHTML = d
                }
                if (v === !1 || !i.tooltip.showContent)return;
                g.hasAppend || (N.style.left = j / 2 + "px", N.style.top = B / 2 + "px", s.firstChild.appendChild(N), g.hasAppend = !0), W(h, p, m)
            }
        }

        function G(e, t) {
            if (typeof x == "undefined" || typeof e == "undefined" || typeof t == "undefined" || t < 0)return !1;
            var n = i.series, r = [], o, u, a = "";
            if (i.tooltip.trigger == "axis") {
                if (i.tooltip.show === !1)return !1;
                o = i.tooltip.formatter
            }
            var f = i.polar[e].indicator[t].text;
            for (var l = 0, h = n.length; l < h; l++) {
                if (!at(n[l].name))continue;
                n[l].polarIndex == e && g.deepQuery([n[l], i], "tooltip.trigger") == "axis" && (u = g.query(n[l], "tooltip.showContent") || u, o = g.query(n[l], "tooltip.formatter") || o, a += U(g.query(n[l], "tooltip")), r.push(n[l]))
            }
            if (r.length > 0) {
                var p, d, v = [];
                for (var l = 0, h = r.length; l < h; l++) {
                    p = r[l].data;
                    for (var m = 0, y = p.length; m < y; m++) {
                        d = p[m];
                        if (!at(d.name))continue;
                        d = typeof d != "undefined" ? d : {
                            name: "",
                            value: {dataIndex: "-"}
                        }, v.push([r[l].name || "", d.name, d.value[t], f])
                    }
                }
                if (v.length <= 0)return;
                if (typeof o == "function") H = "axis:" + t, N.innerHTML = o(v, H, nt); else if (typeof o == "string") {
                    o = o.replace("{a}", "{a0}").replace("{b}", "{b0}").replace("{c}", "{c0}").replace("{d}", "{d0}");
                    for (var l = 0, h = v.length; l < h; l++)o = o.replace("{a" + l + "}", vt(v[l][0])), o = o.replace("{b" + l + "}", vt(v[l][1])), o = o.replace("{c" + l + "}", g.numAddCommas(v[l][2])), o = o.replace("{d" + l + "}", vt(v[l][3]));
                    N.innerHTML = o
                } else {
                    o = vt(v[0][1]) + "<br/>" + vt(v[0][3]) + " : " + g.numAddCommas(v[0][2]);
                    for (var l = 1, h = v.length; l < h; l++)o += "<br/>" + vt(v[l][1]) + "<br/>", o += vt(v[l][3]) + " : " + g.numAddCommas(v[l][2]);
                    N.innerHTML = o
                }
                if (u === !1 || !i.tooltip.showContent)return;
                return g.hasAppend || (N.style.left = j / 2 + "px", N.style.top = B / 2 + "px", s.firstChild.appendChild(N), g.hasAppend = !0), W(c.getX(P), c.getY(P), a), !0
            }
        }

        function Y() {
            var e = a.get(D, "series"), n = a.get(D, "data"), o = a.get(D, "name"), u = a.get(D, "value"),
                f = a.get(D, "special"), l = a.get(D, "special2"), h, p, d = "", v, m = "";
            D._type != "island" ? (i.tooltip.trigger == "item" && (h = i.tooltip.formatter), g.query(e, "tooltip.trigger") == "item" && (p = g.query(e, "tooltip.showContent") || p, h = g.query(e, "tooltip.formatter") || h, d += U(g.query(e, "tooltip"))), p = g.query(n, "tooltip.showContent") || p, h = g.query(n, "tooltip.formatter") || h, d += U(g.query(n, "tooltip"))) : (p = g.deepQuery([n, e, i], "tooltip.showContent"), h = g.deepQuery([n, e, i], "tooltip.islandFormatter"));
            if (typeof h == "function") H = (e.name || "") + ":" + a.get(D, "dataIndex"), N.innerHTML = h([e.name || "", o, u, f, l], H, nt); else if (typeof h == "string") H = NaN, h = h.replace("{a}", "{a0}").replace("{b}", "{b0}").replace("{c}", "{c0}"), h = h.replace("{a0}", vt(e.name || "")).replace("{b0}", vt(o)).replace("{c0}", u instanceof Array ? u : g.numAddCommas(u)), h = h.replace("{d}", "{d0}").replace("{d0}", f || ""), h = h.replace("{e}", "{e0}").replace("{e0}", a.get(D, "special2") || ""), N.innerHTML = h; else {
                H = NaN;
                if (e.type == t.CHART_TYPE_SCATTER) N.innerHTML = (typeof e.name != "undefined" ? vt(e.name) + "<br/>" : "") + (o === "" ? "" : vt(o) + " : ") + u + (typeof f == "undefined" ? "" : " (" + f + ")"); else if (e.type == t.CHART_TYPE_RADAR && f) {
                    v = f, m += vt(o === "" ? e.name || "" : o), m += m === "" ? "" : "<br />";
                    for (var y = 0; y < v.length; y++)m += vt(v[y].text) + " : " + g.numAddCommas(u[y]) + "<br />";
                    N.innerHTML = m
                } else if (e.type == t.CHART_TYPE_CHORD)if (typeof l == "undefined") N.innerHTML = vt(o) + " (" + g.numAddCommas(u) + ")"; else {
                    var b = vt(o), w = vt(f);
                    N.innerHTML = (typeof e.name != "undefined" ? vt(e.name) + "<br/>" : "") + b + " -> " + w + " (" + g.numAddCommas(u) + ")" + "<br />" + w + " -> " + b + " (" + g.numAddCommas(l) + ")"
                } else N.innerHTML = (typeof e.name != "undefined" ? vt(e.name) + "<br/>" : "") + vt(o) + " : " + g.numAddCommas(u) + (typeof f == "undefined" ? "" : " (" + g.numAddCommas(f) + ")")
            }
            q.invisible || (q.invisible = !0, r.modShape(q.id, q), r.refresh());
            if (p === !1 || !i.tooltip.showContent)return;
            g.hasAppend || (N.style.left = j / 2 + "px", N.style.top = B / 2 + "px", s.firstChild.appendChild(N), g.hasAppend = !0), W(c.getX(P) + 20, c.getY(P) - 20, d)
        }

        function Z(e, t, n, s, o, u) {
            if (e.length > 0) {
                var a, f, l = i.tooltip.axisPointer, c = l.type, h = l.lineStyle.color, p = l.lineStyle.width,
                    d = l.lineStyle.type, v = l.areaStyle.size, m = l.areaStyle.color;
                for (var y = 0, b = e.length; y < b; y++)g.deepQuery([e[y], i], "tooltip.trigger") == "axis" && (a = e[y], f = g.query(a, "tooltip.axisPointer.type"), c = f || c, f == "line" ? (h = g.query(a, "tooltip.axisPointer.lineStyle.color") || h, p = g.query(a, "tooltip.axisPointer.lineStyle.width") || p, d = g.query(a, "tooltip.axisPointer.lineStyle.type") || d) : f == "shadow" && (v = g.query(a, "tooltip.axisPointer.areaStyle.size") || v, m = g.query(a, "tooltip.axisPointer.areaStyle.color") || m));
                c == "line" ? (q.style = {
                    xStart: t,
                    yStart: n,
                    xEnd: s,
                    yEnd: o,
                    strokeColor: h,
                    lineWidth: p,
                    lineType: d
                }, q.invisible = !1, r.modShape(q.id, q)) : c == "shadow" && (typeof v == "undefined" || v == "auto" || isNaN(v) ? p = u : p = v, t == s ? Math.abs(w.getX() - t) < 2 ? (p /= 2, t = s += p / 2) : Math.abs(w.getXend() - t) < 2 && (p /= 2, t = s -= p / 2) : n == o && (Math.abs(w.getY() - n) < 2 ? (p /= 2, n = o += p / 2) : Math.abs(w.getYend() - n) < 2 && (p /= 2, n = o -= p / 2)), R.style = {
                        xStart: t,
                        yStart: n,
                        xEnd: s,
                        yEnd: o,
                        strokeColor: m,
                        lineWidth: p
                    }, R.invisible = !1, r.modShape(R.id, R)), r.refresh()
            }
        }

        function et(e) {
            clearTimeout(A), clearTimeout(M);
            var r = e.target, s = c.getX(e.event), o = c.getY(e.event);
            if (!r) D = !1, P = e.event, P._target = P.target || P.toElement, P.zrenderX = s, P.zrenderY = o, L && w && h.isInside(m, w.getArea(), s, o) ? M = setTimeout(V, _) : L && x && x.isInside([s, o]) != -1 ? M = setTimeout(V, _) : (!P.connectTrigger && n.dispatch(t.EVENT.TOOLTIP_OUT_GRID, P), A = setTimeout(z, O)); else {
                D = r, P = e.event, P._target = P.target || P.toElement, P.zrenderX = s, P.zrenderY = o;
                var u;
                if (L && x && (u = x.isInside([s, o])) != -1) {
                    var a = i.series;
                    for (var f = 0, l = a.length; f < l; f++)if (a[f].polarIndex == u && g.deepQuery([a[f], i], "tooltip.trigger") == "axis") {
                        D = null;
                        break
                    }
                }
                M = setTimeout(V, _)
            }
        }

        function tt() {
            clearTimeout(A), clearTimeout(M), A = setTimeout(z, O)
        }

        function nt(e, t) {
            if (!N)return;
            e == H && (N.innerHTML = t), setTimeout(X, 20)
        }

        function rt() {
            b = o.component, w = b.grid, E = b.xAxis, S = b.yAxis, x = b.polar
        }

        function it(e, t) {
            if (!F || F && F.dataIndex != e.dataIndex) {
                F && F.tipShape.length > 0 && r.delShape(F.tipShape);
                for (var n = 0, i = t.length; n < i; n++)t[n].id = r.newShapeId("tooltip"), t[n].zlevel = y, t[n].style = v.getHighlightStyle(t[n].style, t[n].highlightStyle), t[n].draggable = !1, t[n].hoverable = !1, t[n].clickable = !1, t[n].ondragend = null, t[n].ondragover = null, t[n].ondrop = null, r.addShape(t[n]);
                F = {dataIndex: e.dataIndex, tipShape: t}
            }
        }

        function st() {
            z()
        }

        function ot(e) {
            T = e.selected
        }

        function ut() {
            i.legend && i.legend.selected ? T = i.legend.selected : T = {}
        }

        function at(e) {
            return typeof T[e] != "undefined" ? T[e] : !0
        }

        function ft(e) {
            if (!e)return;
            var n, s = i.series;
            if (typeof e.seriesIndex != "undefined") n = e.seriesIndex; else {
                var u = e.seriesName;
                for (var l = 0, c = s.length; l < c; l++)if (s[l].name == u) {
                    n = l;
                    break
                }
            }
            var h = s[n];
            if (typeof h == "undefined")return;
            var p = o.chart[h.type], d = g.deepQuery([h, i], "tooltip.trigger") == "axis";
            if (!p)return;
            if (d) {
                var v = e.dataIndex;
                switch (p.type) {
                    case t.CHART_TYPE_LINE:
                    case t.CHART_TYPE_BAR:
                    case t.CHART_TYPE_K:
                        if (typeof E == "undefined" || typeof S == "undefined" || h.data.length <= v)return;
                        var m = h.xAxisIndex || 0, y = h.yAxisIndex || 0;
                        E.getAxis(m).type == t.COMPONENT_TYPE_AXIS_CATEGORY ? P = {
                            zrenderX: E.getAxis(m).getCoordByIndex(v),
                            zrenderY: w.getY() + (w.getYend() - w.getY()) / 4
                        } : P = {
                            zrenderX: w.getX() + (w.getXend() - w.getX()) / 4,
                            zrenderY: S.getAxis(y).getCoordByIndex(v)
                        }, Q(m, y, v);
                        break;
                    case t.CHART_TYPE_RADAR:
                        if (typeof x == "undefined" || h.data[0].value.length <= v)return;
                        var b = h.polarIndex || 0, T = x.getVector(b, v, "max");
                        P = {zrenderX: T[0], zrenderY: T[1]}, G(b, v)
                }
            } else {
                var N = p.shapeList, C, k;
                switch (p.type) {
                    case t.CHART_TYPE_LINE:
                    case t.CHART_TYPE_BAR:
                    case t.CHART_TYPE_K:
                    case t.CHART_TYPE_SCATTER:
                        var v = e.dataIndex;
                        for (var l = 0, c = N.length; l < c; l++)if (a.get(N[l], "seriesIndex") == n && a.get(N[l], "dataIndex") == v) {
                            D = N[l], C = N[l].style.x, k = p.type != t.CHART_TYPE_K ? N[l].style.y : N[l].style.y[0];
                            break
                        }
                        break;
                    case t.CHART_TYPE_RADAR:
                        var v = e.dataIndex;
                        for (var l = 0, c = N.length; l < c; l++)if (N[l].shape == "polygon" && a.get(N[l], "seriesIndex") == n && a.get(N[l], "dataIndex") == v) {
                            D = N[l];
                            var T = x.getCenter(h.polarIndex || 0);
                            C = T[0], k = T[1];
                            break
                        }
                        break;
                    case t.CHART_TYPE_PIE:
                        var L = e.name;
                        for (var l = 0, c = N.length; l < c; l++)if (N[l].shape == "sector" && a.get(N[l], "seriesIndex") == n && a.get(N[l], "name") == L) {
                            D = N[l];
                            var A = D.style, O = (A.startAngle + A.endAngle) / 2 * Math.PI / 180;
                            C = D.style.x + Math.cos(O) * A.r / 1.5, k = D.style.y - Math.sin(O) * A.r / 1.5;
                            break
                        }
                        break;
                    case t.CHART_TYPE_MAP:
                        var L = e.name, M = h.mapType;
                        for (var l = 0, c = N.length; l < c; l++)if (N[l].shape == "text" && N[l]._mapType == M && N[l].style._text == L) {
                            D = N[l], C = D.style.x + D.position[0], k = D.style.y + D.position[1];
                            break
                        }
                        break;
                    case t.CHART_TYPE_CHORD:
                        var L = e.name;
                        for (var l = 0, c = N.length; l < c; l++)if (N[l].shape == "sector" && a.get(N[l], "name") == L) {
                            D = N[l];
                            var A = D.style, O = (A.startAngle + A.endAngle) / 2 * Math.PI / 180;
                            C = D.style.x + Math.cos(O) * (A.r - 2), k = D.style.y - Math.sin(O) * (A.r - 2), r.trigger(f.EVENT.MOUSEMOVE, {
                                zrenderX: C,
                                zrenderY: k
                            });
                            return
                        }
                        break;
                    case t.CHART_TYPE_FORCE:
                        var L = e.name;
                        for (var l = 0, c = N.length; l < c; l++)if (N[l].shape == "circle" && a.get(N[l], "name") == L) {
                            D = N[l], C = D.position[0], k = D.position[1];
                            break
                        }
                }
                typeof C != "undefined" && typeof k != "undefined" && (P = {
                    zrenderX: C,
                    zrenderY: k
                }, r.addHoverShape(D), r.refreshHover(), Y())
            }
        }

        function lt() {
            z()
        }

        function ct(e, n) {
            i = e, s = n, i.tooltip = g.reformOption(i.tooltip), i.tooltip.textStyle = d.merge(i.tooltip.textStyle, t.textStyle, {
                overwrite: !1,
                recursive: !0
            }), i.tooltip.padding = g.reformCssArray(i.tooltip.padding), L = !1, i.tooltip.trigger == "axis" && (L = !0);
            var r = i.series;
            for (var o = 0, u = r.length; o < u; o++)if (g.query(r[o], "tooltip.trigger") == "axis") {
                L = !0;
                break
            }
            _ = i.tooltip.showDelay, O = i.tooltip.hideDelay, k = U(i.tooltip), N.style.position = "absolute", g.hasAppend = !1, ut(), I = i.tooltip.axisPointer.lineStyle.width
        }

        function ht(e) {
            e && (i = e, i.tooltip = g.reformOption(i.tooltip), i.tooltip.textStyle = d.merge(i.tooltip.textStyle, t.textStyle, {
                overwrite: !1,
                recursive: !0
            }), i.tooltip.padding = g.reformCssArray(i.tooltip.padding), ut(), I = i.tooltip.axisPointer.lineStyle.width)
        }

        function pt() {
            B = r.getHeight(), j = r.getWidth()
        }

        function dt() {
            clearTimeout(A), clearTimeout(M), r.un(f.EVENT.MOUSEMOVE, et), r.un(f.EVENT.GLOBALOUT, tt), g.hasAppend && s.firstChild.removeChild(N), N = null, g.shapeList = null, g = null
        }

        function vt(e) {
            return String(e).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&#39;")
        }

        var u = e("./base");
        u.call(this, t, r);
        var a = e("../util/ecData"), f = e("zrender/config"), l = e("zrender/shape"), c = e("zrender/tool/event"),
            h = e("zrender/tool/area"), p = e("zrender/tool/color"), d = e("zrender/tool/util"),
            v = e("zrender/shape/base"), m = l.get("rectangle"), g = this;
        g.type = t.COMPONENT_TYPE_TOOLTIP;
        var y = g.getZlevelBase(), b = {}, w, E, S, x, T = {}, N = document.createElement("div"),
            C = "position:absolute;display:block;border-style:solid;white-space:nowrap;", k, L, A, O, M, _, D, P, H,
            B = r.getHeight(), j = r.getWidth(), F = !1, I = 0,
            q = {shape: "line", id: r.newShapeId("tooltip"), zlevel: y, invisible: !0, hoverable: !1, style: {}},
            R = {shape: "line", id: r.newShapeId("tooltip"), zlevel: 1, invisible: !0, hoverable: !1, style: {}};
        r.addShape(q), r.addShape(R), r.on(f.EVENT.MOUSEMOVE, et), r.on(f.EVENT.GLOBALOUT, tt), g.dispose = dt, g.init = ct, g.refresh = ht, g.resize = pt, g.setComponent = rt, g.ontooltipHover = it, g.ondragend = st, g.onlegendSelected = ot, g.showTip = ft, g.hideTip = lt, ct(i, s)
    }

    return e("../component").define("tooltip", t), t
}), define("echarts/component/toolbox", ["require", "./base", "zrender/config", "zrender/tool/util", "zrender/tool/event", "zrender/tool/env", "../component", "../component"], function (e) {
    function t(t, n, r, i, s) {
        function B() {
            y = [];
            var e = l.toolbox.feature, t = [];
            for (var n in e)if (e[n].show)switch (n) {
                case"mark":
                    t.push({key: n, name: "mark"}), t.push({key: n, name: "markUndo"}), t.push({
                        key: n,
                        name: "markClear"
                    });
                    break;
                case"magicType":
                    for (var i = 0, s = e[n].type.length; i < s; i++)e[n].title[e[n].type[i] + "Chart"] = e[n].title[e[n].type[i]], t.push({
                        key: n,
                        name: e[n].type[i] + "Chart"
                    });
                    break;
                case"dataZoom":
                    t.push({key: n, name: "dataZoom"}), t.push({key: n, name: "dataZoomReset"});
                    break;
                case"saveAsImage":
                    p && t.push({key: n, name: "saveAsImage"});
                    break;
                default:
                    t.push({key: n, name: n})
            }
            if (t.length > 0) {
                var o, n;
                for (var i = 0, s = t.length; i < s; i++)o = t[i].name, n = t[i].key, y.push(o), E[o] = e[n].title[o] || e[n].title, e[n].icon && (S[o] = e[n].icon[o] || e[n].icon), e[n].color && (x[o] = e[n].color[o] || e[n].color);
                w = I(), F(), j();
                for (var i = 0, s = h.shapeList.length; i < s; i++)h.shapeList[i].id = r.newShapeId(h.type), r.addShape(h.shapeList[i]);
                b.mark && (G(b.markUndo), G(b.markClear)), b.dataZoomReset && _.length === 0 && G(b.dataZoomReset)
            }
        }

        function j() {
            var s = l.toolbox, o = y.length, u = w.x, a = w.y, f = s.itemSize, c = s.itemGap, p,
                m = s.color instanceof Array ? s.color : [s.color], g = h.getFont(s.textStyle), N, C, k;
            s.orient == "horizontal" ? (N = w.y / r.getHeight() < .5 ? "bottom" : "top", C = w.x / r.getWidth() < .5 ? "left" : "right", k = w.y / r.getHeight() < .5 ? "top" : "bottom") : N = w.x / r.getWidth() < .5 ? "right" : "left", b = {};
            for (var L = 0; L < o; L++) {
                p = {
                    shape: "icon",
                    zlevel: d,
                    style: {
                        x: u,
                        y: a,
                        width: f,
                        height: f,
                        iconType: y[L],
                        lineWidth: 1,
                        strokeColor: x[y[L]] || m[L % m.length],
                        brushType: "stroke"
                    },
                    highlightStyle: {
                        lineWidth: 2,
                        text: s.showTitle ? E[y[L]] : undefined,
                        textFont: g,
                        textPosition: N,
                        strokeColor: x[y[L]] || m[L % m.length]
                    },
                    hoverable: !0,
                    clickable: !0
                }, S[y[L]] && (p.style.image = S[y[L]].replace(new RegExp("^image:\\/\\/"), ""), p.style.opacity = .8, p.highlightStyle.opacity = 1, p.shape = "image"), s.orient == "horizontal" && (L === 0 && C == "left" && (p.highlightStyle.textPosition = "specific", p.highlightStyle.textAlign = C, p.highlightStyle.textBaseline = k, p.highlightStyle.textX = u, p.highlightStyle.textY = k == "top" ? a + f + 10 : a - 10), L == o - 1 && C == "right" && (p.highlightStyle.textPosition = "specific", p.highlightStyle.textAlign = C, p.highlightStyle.textBaseline = k, p.highlightStyle.textX = u + f, p.highlightStyle.textY = k == "top" ? a + f + 10 : a - 10));
                switch (y[L]) {
                    case"mark":
                        p.onclick = q;
                        break;
                    case"markUndo":
                        p.onclick = V;
                        break;
                    case"markClear":
                        p.onclick = $;
                        break;
                    case"dataZoom":
                        p.onclick = R;
                        break;
                    case"dataZoomReset":
                        p.onclick = J;
                        break;
                    case"dataView":
                        if (!D) {
                            var A = e("../component"), O = A.get("dataView");
                            D = new O(t, n, r, l, i)
                        }
                        p.onclick = Z;
                        break;
                    case"restore":
                        p.onclick = et;
                        break;
                    case"saveAsImage":
                        p.onclick = tt;
                        break;
                    default:
                        y[L].match("Chart") ? (p._name = y[L].replace("Chart", ""), v[p._name] && (p.style.strokeColor = T), p.onclick = nt) : p.onclick = it
                }
                h.shapeList.push(p), b[y[L]] = p, s.orient == "horizontal" ? u += f + c : a += f + c
            }
        }

        function F() {
            var e = l.toolbox, t = e.padding[0], n = e.padding[1], r = e.padding[2], i = e.padding[3];
            h.shapeList.push({
                shape: "rectangle",
                zlevel: d,
                hoverable: !1,
                style: {
                    x: w.x - i,
                    y: w.y - t,
                    width: w.width + i + n,
                    height: w.height + t + r,
                    brushType: e.borderWidth === 0 ? "fill" : "both",
                    color: e.backgroundColor,
                    strokeColor: e.borderColor,
                    lineWidth: e.borderWidth
                }
            })
        }

        function I() {
            var e = l.toolbox, t = y.length, n = e.itemGap, i = e.itemSize, s = 0, o = 0;
            e.orient == "horizontal" ? (s = (i + n) * t - n, o = i) : (o = (i + n) * t - n, s = i);
            var u, a = r.getWidth();
            switch (e.x) {
                case"center":
                    u = Math.floor((a - s) / 2);
                    break;
                case"left":
                    u = e.padding[3] + e.borderWidth;
                    break;
                case"right":
                    u = a - s - e.padding[1] - e.borderWidth;
                    break;
                default:
                    u = e.x - 0, u = isNaN(u) ? 0 : u
            }
            var f, c = r.getHeight();
            switch (e.y) {
                case"top":
                    f = e.padding[0] + e.borderWidth;
                    break;
                case"bottom":
                    f = c - o - e.padding[2] - e.borderWidth;
                    break;
                case"center":
                    f = Math.floor((c - o) / 2);
                    break;
                default:
                    f = e.y - 0, f = isNaN(f) ? 0 : f
            }
            return {x: u, y: f, width: s, height: o}
        }

        function q(e) {
            var t = e.target;
            return k || C ? (K(), r.refresh()) : (Q(), r.modShape(t.id, {style: {strokeColor: T}}), r.refresh(), C = !0, setTimeout(function () {
                r && r.on(u.EVENT.CLICK, X) && r.on(u.EVENT.MOUSEMOVE, U)
            }, 10)), !0
        }

        function R(e) {
            var t = e.target;
            return O || A ? (Q(), r.refresh(), i.style.cursor = "default") : (K(), r.modShape(t.id, {style: {strokeColor: T}}), r.refresh(), A = !0, setTimeout(function () {
                r && r.on(u.EVENT.MOUSEDOWN, z) && r.on(u.EVENT.MOUSEUP, W) && r.on(u.EVENT.MOUSEMOVE, U)
            }, 10), i.style.cursor = "crosshair"), !0
        }

        function U(e) {
            k && (L.style.xEnd = f.getX(e.event), L.style.yEnd = f.getY(e.event), r.addHoverShape(L)), O && (M.style.width = f.getX(e.event) - M.style.x, M.style.height = f.getY(e.event) - M.style.y, r.addHoverShape(M), i.style.cursor = "crosshair"), A && i.style.cursor != "pointer" && i.style.cursor != "move" && (i.style.cursor = "crosshair")
        }

        function z(e) {
            if (e.target)return;
            O = !0;
            var n = f.getX(e.event), i = f.getY(e.event), s = l.dataZoom || {};
            return M = {
                shape: "rectangle",
                id: r.newShapeId("zoom"),
                zlevel: d,
                style: {x: n, y: i, width: 1, height: 1, brushType: "both"},
                highlightStyle: {
                    lineWidth: 2,
                    color: s.fillerColor || t.dataZoom.fillerColor,
                    strokeColor: s.handleColor || t.dataZoom.handleColor,
                    brushType: "both"
                }
            }, r.addHoverShape(M), !0
        }

        function W() {
            if (!M || Math.abs(M.style.width) < 10 || Math.abs(M.style.height) < 10)return O = !1, !0;
            if (O && c.dataZoom) {
                O = !1;
                var e = c.dataZoom.rectZoom(M.style);
                e && (_.push({
                    start: e.start,
                    end: e.end,
                    start2: e.start2,
                    end2: e.end2
                }), Y(b.dataZoomReset), r.refresh())
            }
            return !0
        }

        function X(e) {
            if (k) k = !1, h.shapeList.push(L), Y(b.markUndo), Y(b.markClear), r.addShape(L), r.refresh(); else if (C) {
                k = !0;
                var t = f.getX(e.event), n = f.getY(e.event);
                L = {
                    shape: "line",
                    id: r.newShapeId("mark"),
                    zlevel: d,
                    style: {
                        xStart: t,
                        yStart: n,
                        xEnd: t,
                        yEnd: n,
                        lineWidth: h.query(l, "toolbox.feature.mark.lineStyle.width"),
                        strokeColor: h.query(l, "toolbox.feature.mark.lineStyle.color"),
                        lineType: h.query(l, "toolbox.feature.mark.lineStyle.type")
                    }
                }, r.addHoverShape(L)
            }
        }

        function V() {
            if (k) k = !1; else {
                var e = h.shapeList.length - 1;
                y.length == e - 1 && (G(b.markUndo), G(b.markClear));
                if (y.length < e) {
                    var t = h.shapeList[h.shapeList.length - 1];
                    r.delShape(t.id), r.refresh(), h.shapeList.pop()
                }
            }
            return !0
        }

        function $() {
            k && (k = !1);
            var e = h.shapeList.length - y.length - 1, t = !1;
            while (e--)r.delShape(h.shapeList.pop().id), t = !0;
            return t && (G(b.markUndo), G(b.markClear), r.refresh()), !0
        }

        function J() {
            return O && (O = !1), _.pop(), _.length > 0 ? c.dataZoom.absoluteZoom(_[_.length - 1]) : (c.dataZoom.rectZoom(), G(b.dataZoomReset), r.refresh()), !0
        }

        function K() {
            k = !1, C && (C = !1, b.mark && r.modShape(b.mark.id, {style: {strokeColor: b.mark.highlightStyle.strokeColor}}), r.un(u.EVENT.CLICK, X), r.un(u.EVENT.MOUSEMOVE, U))
        }

        function Q() {
            O = !1, A && (A = !1, b.dataZoom && r.modShape(b.dataZoom.id, {style: {strokeColor: b.dataZoom.highlightStyle.strokeColor}}), r.un(u.EVENT.MOUSEDOWN, z), r.un(u.EVENT.MOUSEUP, W), r.un(u.EVENT.MOUSEMOVE, U))
        }

        function G(e) {
            e.shape != "image" ? r.modShape(e.id, {
                hoverable: !1,
                clickable: !1,
                style: {strokeColor: N}
            }) : r.modShape(e.id, {hoverable: !1, clickable: !1, style: {opacity: .3}})
        }

        function Y(e) {
            e.shape != "image" ? r.modShape(e.id, {
                hoverable: !0,
                clickable: !0,
                style: {strokeColor: e.highlightStyle.strokeColor}
            }) : r.modShape(e.id, {hoverable: !0, clickable: !0, style: {opacity: .8}})
        }

        function Z() {
            return D.show(l), !0
        }

        function et() {
            return K(), Q(), n.dispatch(t.EVENT.RESTORE), !0
        }

        function tt() {
            var e = l.toolbox.feature.saveAsImage, t = e.type || "png";
            t != "png" && t != "jpeg" && (t = "png");
            var n;
            s.isConnected() ? n = s.getConnectedDataURL(t) : n = r.toDataURL("image/" + t, l.backgroundColor && l.backgroundColor.replace(" ", "") == "rgba(0,0,0,0)" ? "#fff" : l.backgroundColor);
            var i = document.createElement("div");
            i.id = "__echarts_download_wrap__", i.style.cssText = "position:fixed;z-index:99999;display:block;top:0;left:0;background-color:rgba(33,33,33,0.5);text-align:center;width:100%;height:100%;line-height:" + document.documentElement.clientHeight + "px;";
            var o = document.createElement("a");
            o.href = n, o.setAttribute("download", (e.name ? e.name : l.title && (l.title.text || l.title.subtext) ? l.title.text || l.title.subtext : "ECharts") + "." + t), o.innerHTML = '<img style="vertical-align:middle" src="' + n + '" title="' + (!window.attachEvent || navigator.userAgent.indexOf("Opera") !== -1 ? e.lang ? e.lang[0] : "点击保存" : "右键->图片另存为") + '"/>', i.appendChild(o), document.body.appendChild(i), o = null, i = null, setTimeout(function () {
                var e = document.getElementById("__echarts_download_wrap__");
                e && (e.onclick = function () {
                    var e = document.getElementById("__echarts_download_wrap__");
                    e.onclick = null, e.innerHTML = "", document.body.removeChild(e), e = null
                }, e = null)
            }, 500);
            return
        }

        function nt(e) {
            K();
            var r = e.target._name;
            return v[r] ? v[r] = !1 : (v[r] = !0, r == t.CHART_TYPE_LINE ? v[t.CHART_TYPE_BAR] = !1 : r == t.CHART_TYPE_BAR && (v[t.CHART_TYPE_LINE] = !1), r == P ? v[H] = !1 : r == H && (v[P] = !1)), n.dispatch(t.EVENT.MAGIC_TYPE_CHANGED, e.event, {magicType: v}), !0
        }

        function rt(e) {
            K(), v = e, !g && n.dispatch(t.EVENT.MAGIC_TYPE_CHANGED, null, {magicType: v})
        }

        function it(e) {
            var t = e.target.style.iconType, n = l.toolbox.feature[t].onclick;
            typeof n == "function" && n(l)
        }

        function st(e) {
            if (h.query(e, "toolbox.show") && h.query(e, "toolbox.feature.magicType.show")) {
                var t = e.toolbox.feature.magicType.type, n = t.length;
                m = {};
                while (n--)m[t[n]] = !0;
                n = e.series.length;
                var r, i;
                while (n--) {
                    r = e.series[n].type, m[r] && (i = e.xAxis instanceof Array ? e.xAxis[e.series[n].xAxisIndex || 0] : e.xAxis, i && (i.type || "category") == "category" && (i.__boundaryGap = typeof i.boundaryGap != "undefined" ? i.boundaryGap : !0), i = e.yAxis instanceof Array ? e.yAxis[e.series[n].yAxisIndex || 0] : e.yAxis, i && i.type == "category" && (i.__boundaryGap = typeof i.boundaryGap != "undefined" ? i.boundaryGap : !0), e.series[n].__type = r, e.series[n].__itemStyle = e.series[n].itemStyle ? a.clone(e.series[n].itemStyle) : {});
                    if (m[P] || m[H]) e.series[n].__stack = e.series[n].stack
                }
            }
            v = {};
            var s = e.dataZoom;
            if (s && s.show) {
                var o = typeof s.start != "undefined" && s.start >= 0 && s.start <= 100 ? s.start : 0,
                    u = typeof s.end != "undefined" && s.end >= 0 && s.end <= 100 ? s.end : 100;
                o > u && (o += u, u = o - u, o -= u), _ = [{start: o, end: u, start2: 0, end2: 100}]
            } else _ = []
        }

        function ot() {
            var e;
            if (v[t.CHART_TYPE_LINE] || v[t.CHART_TYPE_BAR]) {
                var n = v[t.CHART_TYPE_LINE] ? !1 : !0;
                for (var r = 0, i = l.series.length; r < i; r++)m[l.series[r].type] && (l.series[r].type = v[t.CHART_TYPE_LINE] ? t.CHART_TYPE_LINE : t.CHART_TYPE_BAR, l.series[r].itemStyle = a.clone(l.series[r].__itemStyle), e = l.xAxis instanceof Array ? l.xAxis[l.series[r].xAxisIndex || 0] : l.xAxis, e && (e.type || "category") == "category" && (e.boundaryGap = n ? !0 : e.__boundaryGap), e = l.yAxis instanceof Array ? l.yAxis[l.series[r].yAxisIndex || 0] : l.yAxis, e && e.type == "category" && (e.boundaryGap = n ? !0 : e.__boundaryGap))
            } else for (var r = 0, i = l.series.length; r < i; r++)m[l.series[r].type] && (l.series[r].type = l.series[r].__type, l.series[r].itemStyle = l.series[r].__itemStyle, e = l.xAxis instanceof Array ? l.xAxis[l.series[r].xAxisIndex || 0] : l.xAxis, e && (e.type || "category") == "category" && (e.boundaryGap = e.__boundaryGap), e = l.yAxis instanceof Array ? l.yAxis[l.series[r].yAxisIndex || 0] : l.yAxis, e && e.type == "category" && (e.boundaryGap = e.__boundaryGap));
            if (v[P] || v[H])for (var r = 0, i = l.series.length; r < i; r++)v[P] ? l.series[r].stack = "_ECHARTS_STACK_KENER_2014_" : v[H] && (l.series[r].stack = null); else for (var r = 0, i = l.series.length; r < i; r++)l.series[r].stack = l.series[r].__stack;
            return l
        }

        function ut(e) {
            g = e
        }

        function at(e, t) {
            K(), Q(), e.toolbox = h.reformOption(e.toolbox), e.toolbox.padding = h.reformCssArray(e.toolbox.padding), l = e, c = t, h.shapeList = [], e.toolbox.show && B(), lt()
        }

        function ft() {
            K(), h.clear(), l && l.toolbox && l.toolbox.show && B(), D && D.resize()
        }

        function lt() {
            D && D.hide()
        }

        function ct() {
            D && (D.dispose(), D = null), h.clear(), h.shapeList = null, h = null
        }

        function ht(e) {
            e && (e.toolbox = h.reformOption(e.toolbox), e.toolbox.padding = h.reformCssArray(e.toolbox.padding), l = e)
        }

        var o = e("./base");
        o.call(this, t, r);
        var u = e("zrender/config"), a = e("zrender/tool/util"), f = e("zrender/tool/event"), l, c, h = this;
        h.type = t.COMPONENT_TYPE_TOOLBOX;
        var p = e("zrender/tool/env").canvasSupported, d = h.getZlevelBase(), v = {}, m, g = !1, y, b = {}, w, E = {},
            S = {}, x = {}, T = "red", N = "#ccc", C, k, L, A, O, M, _, D, P = "stack", H = "tiled";
        h.dispose = ct, h.render = at, h.resize = ft, h.hideDataView = lt, h.getMagicOption = ot, h.silence = ut, h.setMagicType = rt, h.reset = st, h.refresh = ht
    }

    return e("../component").define("toolbox", t), t
}), define("echarts/component/dataView", ["require", "./base", "zrender/tool/env", "../component"], function (e) {
    function t(t, n, r, i, s) {
        function w() {
            m = "width:" + b + "px;" + "height:" + 0 + "px;" + "background-color:#f0ffff;", l.style.cssText = v + m, s.onselectstart = function () {
                return !1
            }
        }

        function E(e) {
            d = !0;
            var t = u.query(i, "toolbox.feature.dataView.lang") || a;
            i = e, l.innerHTML = '<p style="padding:8px 0;margin:0 0 10px 0;border-bottom:1px solid #eee">' + (t[0] || a[0]) + "</p>", c.style.cssText = "display:block;margin:0 0 8px 0;padding:4px 6px;overflow:auto;width:" + (b - 15) + "px;" + "height:" + (y - 100) + "px;";
            var n = u.query(i, "toolbox.feature.dataView.optionToContent");
            typeof n != "function" ? c.value = S() : c.value = n(i), l.appendChild(c), p.style.cssText = "float:right;padding:1px 6px;", p.innerHTML = t[1] || a[1], p.onclick = w, l.appendChild(p), u.query(i, "toolbox.feature.dataView.readOnly") === !1 ? (h.style.cssText = "float:right;margin-right:10px;padding:1px 6px;", h.innerHTML = t[2] || a[2], h.onclick = x, l.appendChild(h), c.readOnly = !1, c.style.cursor = "default") : (c.readOnly = !0, c.style.cursor = "text"), m = "width:" + b + "px;" + "height:" + y + "px;" + "background-color:#fff;", l.style.cssText = v + m, s.onselectstart = function () {
                return !0
            }
        }

        function S() {
            var e, n, r, s, o, u, a = [], f = "";
            if (i.xAxis) {
                i.xAxis instanceof Array ? a = i.xAxis : a = [i.xAxis];
                for (e = 0, s = a.length; e < s; e++)if ((a[e].type || "category") == "category") {
                    u = [];
                    for (n = 0, r = a[e].data.length; n < r; n++)o = a[e].data[n], u.push(typeof o.value != "undefined" ? o.value : o);
                    f += u.join(", ") + "\n\n"
                }
            }
            if (i.yAxis) {
                i.yAxis instanceof Array ? a = i.yAxis : a = [i.yAxis];
                for (e = 0, s = a.length; e < s; e++)if (a[e].type == "category") {
                    u = [];
                    for (n = 0, r = a[e].data.length; n < r; n++)o = a[e].data[n], u.push(typeof o.value != "undefined" ? o.value : o);
                    f += u.join(", ") + "\n\n"
                }
            }
            var l = i.series, c;
            for (e = 0, s = l.length; e < s; e++) {
                u = [];
                for (n = 0, r = l[e].data.length; n < r; n++)o = l[e].data[n], l[e].type == t.CHART_TYPE_PIE || l[e].type == t.CHART_TYPE_MAP ? c = (o.name || "-") + ":" : c = "", l[e].type == t.CHART_TYPE_SCATTER && (o = typeof o.value != "undefined" ? o.value : o, o = o.join(", ")), u.push(c + (typeof o.value != "undefined" ? o.value : o));
                f += (l[e].name || "-") + " : \n", f += u.join(l[e].type == t.CHART_TYPE_SCATTER ? "\n" : ", "), f += "\n\n"
            }
            return f
        }

        function x() {
            var e = c.value, r = u.query(i, "toolbox.feature.dataView.contentToOption");
            if (typeof r != "function") {
                e = e.split("\n");
                var s = [];
                for (var o = 0, a = e.length; o < a; o++)e[o] = N(e[o]), e[o] !== "" && s.push(e[o]);
                T(s)
            } else r(e, i);
            w(), setTimeout(function () {
                n && n.dispatch(t.EVENT.DATA_VIEW_CHANGED, null, {option: i})
            }, f ? 800 : 100)
        }

        function T(e) {
            var n, r, s, o, u, a = [], f = 0, l, c;
            if (i.xAxis) {
                i.xAxis instanceof Array ? a = i.xAxis : a = [i.xAxis];
                for (n = 0, o = a.length; n < o; n++)if ((a[n].type || "category") == "category") {
                    l = e[f].split(",");
                    for (r = 0, s = a[n].data.length; r < s; r++)c = N(l[r] || ""), u = a[n].data[r], typeof a[n].data[r].value != "undefined" ? a[n].data[r].value = c : a[n].data[r] = c;
                    f++
                }
            }
            if (i.yAxis) {
                i.yAxis instanceof Array ? a = i.yAxis : a = [i.yAxis];
                for (n = 0, o = a.length; n < o; n++)if (a[n].type == "category") {
                    l = e[f].split(",");
                    for (r = 0, s = a[n].data.length; r < s; r++)c = N(l[r] || ""), u = a[n].data[r], typeof a[n].data[r].value != "undefined" ? a[n].data[r].value = c : a[n].data[r] = c;
                    f++
                }
            }
            var h = i.series;
            for (n = 0, o = h.length; n < o; n++) {
                f++;
                if (h[n].type == t.CHART_TYPE_SCATTER)for (var r = 0, s = h[n].data.length; r < s; r++)l = e[f], c = l.replace(" ", "").split(","), typeof h[n].data[r].value != "undefined" ? h[n].data[r].value = c : h[n].data[r] = c, f++; else {
                    l = e[f].split(",");
                    for (var r = 0, s = h[n].data.length; r < s; r++)c = (l[r] || "").replace(/.*:/, ""), c = N(c), c = c != "-" && c !== "" ? c - 0 : "-", typeof h[n].data[r].value != "undefined" ? h[n].data[r].value = c : h[n].data[r] = c;
                    f++
                }
            }
        }

        function N(e) {
            var t = new RegExp("(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+$)", "g");
            return e.replace(t, "")
        }

        function C(e) {
            e = e || window.event, e.stopPropagation ? e.stopPropagation() : e.cancelBubble = !0
        }

        function k() {
            l.className = g, w(), s.firstChild.appendChild(l), window.addEventListener ? (l.addEventListener("click", C), l.addEventListener("mousewheel", C), l.addEventListener("mousemove", C), l.addEventListener("mousedown", C), l.addEventListener("mouseup", C), l.addEventListener("touchstart", C), l.addEventListener("touchmove", C), l.addEventListener("touchend", C)) : (l.attachEvent("onclick", C), l.attachEvent("onmousewheel", C), l.attachEvent("onmousemove", C), l.attachEvent("onmousedown", C), l.attachEvent("onmouseup", C))
        }

        function L() {
            y = r.getHeight(), b = r.getWidth(), l.offsetHeight > 10 && (m = "width:" + b + "px;" + "height:" + y + "px;" + "background-color:#fff;", l.style.cssText = v + m, c.style.cssText = "display:block;margin:0 0 8px 0;padding:4px 6px;overflow:auto;width:" + (b - 15) + "px;" + "height:" + (y - 100) + "px;")
        }

        function A() {
            window.removeEventListener ? (l.removeEventListener("click", C), l.removeEventListener("mousewheel", C), l.removeEventListener("mousemove", C), l.removeEventListener("mousedown", C), l.removeEventListener("mouseup", C), l.removeEventListener("touchstart", C), l.removeEventListener("touchmove", C), l.removeEventListener("touchend", C)) : (l.detachEvent("onclick", C), l.detachEvent("onmousewheel", C), l.detachEvent("onmousemove", C), l.detachEvent("onmousedown", C), l.detachEvent("onmouseup", C)), h.onclick = null, p.onclick = null, d && (l.removeChild(c), l.removeChild(h), l.removeChild(p)), c = null, h = null, p = null, s.firstChild.removeChild(l), l = null, u = null
        }

        var o = e("./base");
        o.call(this, t, r);
        var u = this;
        u.type = t.COMPONENT_TYPE_DATAVIEW;
        var a = ["Data View", "close", "refresh"], f = e("zrender/tool/env").canvasSupported,
            l = document.createElement("div"), c = document.createElement("textArea"),
            h = document.createElement("button"), p = document.createElement("button"), d = !1,
            v = "position:absolute;display:block;overflow:hidden;transition:height 0.8s,background-color 1s;-moz-transition:height 0.8s,background-color 1s;-webkit-transition:height 0.8s,background-color 1s;-o-transition:height 0.8s,background-color 1s;z-index:1;left:0;top:0;",
            m, g = "echarts-dataview", y = r.getHeight(), b = r.getWidth();
        u.dispose = A, u.resize = L, u.show = E, u.hide = w, k()
    }

    return e("../component").define("dataView", t), t
}), define("echarts/util/coordinates", ["require", "zrender/tool/math"], function (e) {
    function n(e, n) {
        return [e * t.sin(n), e * t.cos(n)]
    }

    function r(e, t) {
        return [Math.sqrt(e * e + t * t), Math.atan(t / e)]
    }

    var t = e("zrender/tool/math");
    return {polar2cartesian: n, cartesian2polar: r}
}), define("echarts/component/polar", ["require", "./base", "../util/coordinates", "zrender/tool/util", "../component"], function (e) {
    function t(t, n, r, i, s) {
        function p(e, t) {
            s = t, B(e)
        }

        function d() {
            for (var e = 0; e < l.length; e++)f.reformOption(l[e]), h = [l[e], i], v(e), m(e), y(e), L(e), g(e);
            for (var e = 0; e < f.shapeList.length; e++)f.shapeList[e].id = r.newShapeId(f.type), r.addShape(f.shapeList[e])
        }

        function v(e) {
            var t = l[e], n = f.deepQuery(h, "indicator"), i = n.length, s = t.startAngle, o = 2 * Math.PI / i,
                a = f.parsePercent(t.radius, Math.min(r.getWidth(), r.getHeight()) / 2), c = t.__ecIndicator = [], p;
            for (var d = 0; d < i; d++)p = u.polar2cartesian(a, s * Math.PI / 180 + o * d), c.push({vector: [p[1], -p[0]]})
        }

        function m(e) {
            var t = l[e], n = t.__ecIndicator, r = t.splitArea, i = t.splitLine, s = N(e), o = t.splitNumber,
                u = i.lineStyle.color, a = i.lineStyle.width, c = i.show, p = f.deepQuery(h, "axisLine");
            w(n, o, s, r, u, a, c), C(n, s, p)
        }

        function g(e) {
            var t = l[e], n = f.deepQuery(h, "indicator"), r = t.__ecIndicator, s, o, u, c,
                p = f.deepQuery(h, "splitNumber"), d = N(e), o, v, m, g, y, b = f.deepQuery(h, "precision");
            for (var w = 0; w < n.length; w++) {
                s = f.deepQuery([n[w], t, i], "axisLabel");
                if (s.show) {
                    u = {}, u.textFont = f.getFont(), u = a.merge(u, s), u.lineWidth = u.width, o = r[w].vector, v = r[w].value, g = w / n.length * 2 * Math.PI, y = s.offset || 10;
                    for (var E = 1; E <= p; E++)c = a.merge({}, u), m = E * (v.max - v.min) / p + v.min, b && (m = m.toFixed(b)), c.text = f.numAddCommas(m), c.x = E * o[0] / p + Math.cos(g) * y + d[0], c.y = E * o[1] / p + Math.sin(g) * y + d[1], f.shapeList.push({
                        shape: "text",
                        style: c,
                        draggable: !1,
                        hoverable: !1
                    })
                }
            }
        }

        function y(e) {
            var t = l[e], n = t.__ecIndicator, r, s = f.deepQuery(h, "indicator"), o = N(e), u, a, c, p, d = 0, v = 0,
                m, g;
            for (var y = 0; y < s.length; y++) {
                c = f.deepQuery([s[y], t, i], "name");
                if (!c.show)continue;
                g = f.deepQuery([c, t, i], "textStyle"), u = {}, u.textFont = f.getFont(g), u.color = g.color, typeof c.formatter == "function" ? u.text = c.formatter(s[y].text, y) : typeof c.formatter == "string" ? u.text = c.formatter.replace("{value}", s[y].text) : u.text = s[y].text, r = n[y].vector, Math.round(r[0]) > 0 ? a = "left" : Math.round(r[0]) < 0 ? a = "right" : a = "center", c.margin ? (m = c.margin, d = r[0] > 0 ? m : -m, v = r[1] > 0 ? m : -m, d = r[0] === 0 ? 0 : d, v = r[1] === 0 ? 0 : v, r = T(r, o, 1)) : r = T(r, o, 1.2), u.textAlign = a, u.x = r[0] + d, u.y = r[1] + v, c.rotate && (p = [c.rotate / 180 * Math.PI, r[0], r[1]]), f.shapeList.push({
                    shape: "text",
                    style: u,
                    draggable: !1,
                    hoverable: !1,
                    rotation: p
                })
            }
        }

        function b(e) {
            var e = e || 0, t = l[e], n = N(e), r = t.__ecIndicator, i = r.length, s = [], o, u;
            for (var a = 0; a < i; a++)o = r[a].vector, s.push(T(o, n, 1.2));
            return u = S(s, "fill", "rgba(0,0,0,0)", "", 1), u
        }

        function w(e, t, n, r, i, s, o) {
            var u, a, l, c;
            for (var h = 0; h < t; h++)a = (t - h) / t, c = E(e, a, n), o && (u = S(c, "stroke", "", i, s), f.shapeList.push(u)), r.show && (l = (t - h - 1) / t, x(e, r, a, l, n, h))
        }

        function E(e, t, n) {
            var r = [], i = e.length, s;
            for (var o = 0; o < i; o++)s = e[o].vector, r.push(T(s, n, t));
            return r
        }

        function S(e, t, n, r, i, s, o) {
            return {
                shape: "polygon",
                style: {pointList: e, brushType: t, color: n, strokeColor: r, lineWidth: i},
                hoverable: s || !1,
                draggable: o || !1
            }
        }

        function x(e, t, n, r, i, s) {
            var o = e.length, u, a = t.areaStyle.color, l, c, h, p = [], o = e.length, d;
            typeof a == "string" && (a = [a]), l = a.length, u = a[s % l];
            for (var v = 0; v < o; v++)p = [], c = e[v].vector, h = e[(v + 1) % o].vector, p.push(T(c, i, n)), p.push(T(c, i, r)), p.push(T(h, i, r)), p.push(T(h, i, n)), d = S(p, "fill", u, "", 1), f.shapeList.push(d)
        }

        function T(e, t, n) {
            return [e[0] * n + t[0], e[1] * n + t[1]]
        }

        function N(e) {
            var e = e || 0;
            return f.parseCenter(r, l[e].center)
        }

        function C(e, t, n) {
            var r = e.length, i, s, o = n.lineStyle, u = o.color, a = o.width, l = o.type;
            for (var c = 0; c < r; c++)s = e[c].vector, i = k(t[0], t[1], s[0] + t[0], s[1] + t[1], u, a, l), f.shapeList.push(i)
        }

        function k(e, t, n, r, i, s, o) {
            return {
                shape: "line",
                style: {xStart: e, yStart: t, xEnd: n, yEnd: r, strokeColor: i, lineWidth: s, lineType: o},
                hoverable: !1
            }
        }

        function L(e) {
            var t = l[e], n = f.deepQuery(h, "indicator"), r = n.length, i = t.__ecIndicator, s, o, u, a = A(e),
                c = t.splitNumber, p = f.deepQuery(h, "boundaryGap"), d = f.deepQuery(h, "precision"),
                v = f.deepQuery(h, "power"), m = f.deepQuery(h, "scale");
            for (var g = 0; g < r; g++)typeof n[g].max == "number" ? (o = n[g].max, u = n[g].min || 0, s = {
                max: o,
                min: u
            }) : s = O(a, g, c, p, d, v, m), i[g].value = s
        }

        function A(e) {
            var n = [], r, o, u = s.legend;
            for (var a = 0; a < c.length; a++) {
                r = c[a];
                if (r.type != t.CHART_TYPE_RADAR)continue;
                o = r.data || [];
                for (var l = 0; l < o.length; l++)polarIndex = f.deepQuery([o[l], r, i], "polarIndex") || 0, polarIndex == e && (!u || u.isSelected(o[l].name)) && n.push(o[l])
            }
            return n
        }

        function O(e, t, n, r, i, s, o) {
            function m(e) {
                (e > u || u === undefined) && (u = e), (e < a || a === undefined) && (a = e)
            }

            var u, a, f, l, c, h = 0, p, d, v;
            if (!e || e.length === 0)return;
            e.length == 1 && (a = 0);
            if (e.length != 1)for (var g = 0; g < e.length; g++)f = e[g].value[t], m(f); else {
                v = e[0];
                for (var g = 0; g < v.value.length; g++)m(v.value[g])
            }
            if (e.length != 1)if (o) {
                l = M(u, a, n, i, s);
                if (l >= 1) a = Math.floor(a / l) * l - l; else {
                    if (l === 0)return u > 0 ? (d = 0, p = 2 * u) : u === 0 ? (d = 0, p = 100) : (p = 0, d = 2 * a), {
                        max: p,
                        min: d
                    };
                    c = (l + "").split(".")[1], h = c.length, a = Math.floor(a * Math.pow(10, h)) / Math.pow(10, h) - l
                }
                Math.abs(a) <= l && (a = 0), u = a + Math.floor(l * Math.pow(10, h) * (n + 1)) / Math.pow(10, h)
            } else a = a > 0 ? 0 : a;
            return r && (u = u > 0 ? u * 1.2 : u * .8, a = a > 0 ? a * .8 : a * 1.2), {max: u, min: a}
        }

        function M(e, t, n, r, i) {
            var s = (e - t) / n, o, u;
            if (s > 1)return i ? (s = Math.ceil(s), s % i > 0 ? (Math.ceil(s / i) + 1) * i : s) : (o = (s + "").split(".")[0], u = o.length, o.charAt(0) >= 5 ? Math.pow(10, u) : (o.charAt(0) - 0 + 1) * Math.pow(10, u - 1));
            if (s == 1)return 1;
            if (s === 0)return 0;
            if (!r) {
                o = (s + "").split(".")[1], u = 0;
                while (o[u] == "0")u++;
                return o[u] >= 5 ? "0." + o.substring(0, u + 1) - 0 + 1 / Math.pow(10, u) : "0." + o.substring(0, u + 1) - 0 + 1 / Math.pow(10, u + 1)
            }
            return Math.ceil(s * Math.pow(10, r)) / Math.pow(10, r)
        }

        function _(e, t, n) {
            e = e || 0, t = t || 0;
            var r = l[e].__ecIndicator;
            if (t >= r.length)return;
            var i = l[e].__ecIndicator[t], s = N(e), o = i.vector, u = i.value.max, a = i.value.min, f;
            if (typeof n == "undefined")return s;
            switch (n) {
                case"min":
                    n = a;
                    break;
                case"max":
                    n = u;
                    break;
                case"center":
                    n = (u + a) / 2
            }
            return u != a ? f = (n - a) / (u - a) : f = .5, T(o, s, f)
        }

        function D(e) {
            var t = P(e);
            return t ? t.polarIndex : -1
        }

        function P(e) {
            var t, n, i, s, o, a, c, h, p, d = Math.min(r.getWidth(), r.getHeight()) / 2;
            for (var v = 0; v < l.length; v++) {
                t = l[v], n = N(v);
                if (e[0] == n[0] && e[1] == n[1])return {polarIndex: v, valueIndex: 0};
                i = f.parsePercent(t.radius, d), o = t.startAngle, a = t.indicator, c = a.length, h = 2 * Math.PI / c, s = u.cartesian2polar(e[0] - n[0], n[1] - e[1]), e[0] - n[0] < 0 && (s[1] += Math.PI), s[1] < 0 && (s[1] += 2 * Math.PI), p = s[1] - o / 180 * Math.PI + Math.PI * 2;
                if (Math.abs(Math.cos(p % (h / 2))) * i > s[0])return {
                    polarIndex: v,
                    valueIndex: Math.floor((p + h / 2) / h) % c
                }
            }
        }

        function H(e) {
            var e = e || 0;
            return l[e].indicator
        }

        function B(e) {
            e && (i = e, l = i.polar, c = i.series), f.clear(), d()
        }

        var o = e("./base");
        o.call(this, t, r);
        var u = e("../util/coordinates"), a = e("zrender/tool/util"), f = this;
        f.type = t.COMPONENT_TYPE_POLAR;
        var l, c, h;
        f.refresh = B, f.getVector = _, f.getDropBox = b, f.getCenter = N, f.getIndicator = H, f.isInside = D, f.getNearestIndex = P, p(i, s)
    }

    return e("../component").define("polar", t), t
}), define("echarts/theme/default", [], function () {
    var e = {};
    return e
}), define("echarts/echarts", ["require", "zrender/tool/env", "./config", "zrender/tool/util", "zrender", "zrender/tool/event", "zrender/config", "./util/shape/icon", "./util/shape/markLine", "./chart", "./chart/island", "./component", "./component/title", "./component/axis", "./component/categoryAxis", "./component/valueAxis", "./component/grid", "./component/dataZoom", "./component/legend", "./component/dataRange", "./component/tooltip", "./component/toolbox", "./component/dataView", "./component/polar", "./util/ecQuery", "./util/ecData", "./chart", "./component", "zrender/tool/util", "./util/ecQuery", "zrender/tool/util", "zrender/tool/color", "zrender/tool/util", "./util/ecQuery", "zrender/tool/util", "./util/ecQuery", "zrender/tool/util", "zrender", "zrender/tool/util", "zrender/tool/util", "./theme/default", "./theme/default"], function (e) {
    function u(t) {
        function E() {
            var r = e("zrender");
            (r.version || "1.0.3").replace(".", "") - 0 < n.dependencies.zrender.replace(".", "") - 0 && console.error("ZRender " + (r.version || "1.0.3-") + " is too old for ECharts " + n.version + ". Current version need ZRender " + n.dependencies.zrender + "+"), f = r.init(t), l = {}, p = [], d = {};
            var s = e("zrender/tool/event");
            s.Dispatcher.call(d);
            for (var o in i.EVENT)o != "CLICK" && o != "HOVER" && o != "MAP_ROAM" && d.bind(i.EVENT[o], x);
            var c = e("zrender/config");
            f.on(c.EVENT.CLICK, T), f.on(c.EVENT.MOUSEOVER, N), f.on(c.EVENT.DRAGSTART, C), f.on(c.EVENT.DRAGEND, M), f.on(c.EVENT.DRAGENTER, k), f.on(c.EVENT.DRAGOVER, L), f.on(c.EVENT.DRAGLEAVE, A), f.on(c.EVENT.DROP, O), e("./util/shape/icon"), e("./util/shape/markLine");
            var h = e("./chart");
            e("./chart/island");
            var v = h.get("island");
            y = new v(u, d, f);
            var m = e("./component");
            e("./component/title"), e("./component/axis"), e("./component/categoryAxis"), e("./component/valueAxis"), e("./component/grid"), e("./component/dataZoom"), e("./component/legend"), e("./component/dataRange"), e("./component/tooltip"), e("./component/toolbox"), e("./component/dataView"), e("./component/polar");
            var g = m.get("toolbox");
            b = new g(u, d, f, t, a), V()
        }

        function x(e) {
            e.__echartsId = e.__echartsId || a.id;
            var t = !0;
            e.__echartsId != a.id && (t = !1), S || (S = e.type);
            switch (e.type) {
                case i.EVENT.LEGEND_SELECTED:
                    _(e);
                    break;
                case i.EVENT.DATA_ZOOM:
                    if (!t) {
                        var n = a.component.dataZoom;
                        n && (n.silence(!0), n.absoluteZoom(e.zoom), n.silence(!1))
                    }
                    D(e);
                    break;
                case i.EVENT.DATA_RANGE:
                    t && P(e);
                    break;
                case i.EVENT.MAGIC_TYPE_CHANGED:
                    if (!t) {
                        var r = a.component.toolbox;
                        r && (r.silence(!0), r.setMagicType(e.magicType), r.silence(!1))
                    }
                    H(e);
                    break;
                case i.EVENT.DATA_VIEW_CHANGED:
                    t && B(e);
                    break;
                case i.EVENT.TOOLTIP_HOVER:
                    t && j(e);
                    break;
                case i.EVENT.RESTORE:
                    F();
                    break;
                case i.EVENT.REFRESH:
                    t && I(e);
                    break;
                case i.EVENT.TOOLTIP_IN_GRID:
                case i.EVENT.TOOLTIP_OUT_GRID:
                    if (!t) {
                        var s = a.component.grid;
                        s && f.trigger("mousemove", {
                            connectTrigger: !0,
                            zrenderX: s.getX() + e.x * s.getWidth(),
                            zrenderY: s.getY() + e.y * s.getHeight()
                        })
                    } else if (v) {
                        var s = a.component.grid;
                        s && (e.x = (e.event.zrenderX - s.getX()) / s.getWidth(), e.y = (e.event.zrenderY - s.getY()) / s.getHeight())
                    }
            }
            if (v && t && S == e.type) {
                for (var o in v)v[o].connectedEventHandler(e);
                S = null
            }
            if (!t || !v && t) S = null
        }

        function T(e) {
            var t = p.length;
            while (t--)p[t] && p[t].onclick && p[t].onclick(e);
            if (e.target) {
                var n = U(e.target);
                n && typeof n.seriesIndex != "undefined" && d.dispatch(i.EVENT.CLICK, e.event, n)
            }
        }

        function N(e) {
            if (e.target) {
                var t = U(e.target);
                t && typeof t.seriesIndex != "undefined" && d.dispatch(i.EVENT.HOVER, e.event, t)
            }
        }

        function C(e) {
            m = {dragIn: !1, dragOut: !1, needRefresh: !1};
            var t = p.length;
            while (t--)p[t] && p[t].ondragstart && p[t].ondragstart(e)
        }

        function k(e) {
            var t = p.length;
            while (t--)p[t] && p[t].ondragenter && p[t].ondragenter(e)
        }

        function L(e) {
            var t = p.length;
            while (t--)p[t] && p[t].ondragover && p[t].ondragover(e)
        }

        function A(e) {
            var t = p.length;
            while (t--)p[t] && p[t].ondragleave && p[t].ondragleave(e)
        }

        function O(e) {
            var t = p.length;
            while (t--)p[t] && p[t].ondrop && p[t].ondrop(e, m);
            y.ondrop(e, m)
        }

        function M(e) {
            var t = p.length;
            while (t--)p[t] && p[t].ondragend && p[t].ondragend(e, m);
            y.ondragend(e, m), m.needRefresh && (R(y.getOption()), d.dispatch(i.EVENT.DATA_CHANGED, e.event, U(e.target)), d.dispatch(i.EVENT.REFRESH))
        }

        function _(e) {
            m.needRefresh = !1;
            var t = p.length;
            while (t--)p[t] && p[t].onlegendSelected && p[t].onlegendSelected(e, m);
            g = e.selected, m.needRefresh && d.dispatch(i.EVENT.REFRESH)
        }

        function D(e) {
            m.needRefresh = !1;
            var t = p.length;
            while (t--)p[t] && p[t].ondataZoom && p[t].ondataZoom(e, m);
            m.needRefresh && d.dispatch(i.EVENT.REFRESH)
        }

        function P(e) {
            m.needRefresh = !1;
            var t = p.length;
            while (t--)p[t] && p[t].ondataRange && p[t].ondataRange(e, m);
            m.needRefresh && f.refresh()
        }

        function H() {
            z(q())
        }

        function B(e) {
            R(e.option), d.dispatch(i.EVENT.DATA_CHANGED, null, e), d.dispatch(i.EVENT.REFRESH)
        }

        function j(e) {
            var t = p.length, n = [];
            while (t--)p[t] && p[t].ontooltipHover && p[t].ontooltipHover(e, n)
        }

        function F() {
            a.restore()
        }

        function I(e) {
            w = !0, a.refresh(e), w = !1
        }

        function q(e) {
            var t = e || b.getMagicOption(), n;
            if (c.xAxis)if (c.xAxis instanceof Array) {
                n = c.xAxis.length;
                while (n--)t.xAxis[n].data = c.xAxis[n].data
            } else t.xAxis.data = c.xAxis.data;
            if (c.yAxis)if (c.yAxis instanceof Array) {
                n = c.yAxis.length;
                while (n--)t.yAxis[n].data = c.yAxis[n].data
            } else t.yAxis.data = c.yAxis.data;
            n = t.series.length;
            while (n--)t.series[n].data = c.series[n].data;
            return t
        }

        function R(t) {
            var n = e("./util/ecQuery");
            if (n.query(t, "dataZoom.show") || n.query(t, "toolbox.show") && n.query(t, "toolbox.feature.dataZoom.show")) {
                for (var r = 0, s = p.length; r < s; r++)if (p[r].type == i.COMPONENT_TYPE_DATAZOOM) {
                    p[r].syncBackupData(t, c);
                    return
                }
            } else {
                var o = t.series, u;
                for (var r = 0, s = o.length; r < s; r++) {
                    u = o[r].data;
                    for (var a = 0, f = u.length; a < f; a++)c.series[r].data[a] = u[a]
                }
            }
        }

        function U(t) {
            if (t) {
                var n = e("./util/ecData"), r = n.get(t, "seriesIndex"), i = n.get(t, "dataIndex");
                return i = a.component.dataZoom ? a.component.dataZoom.getRealDataIndex(r, i) : i, {
                    seriesIndex: r,
                    dataIndex: i,
                    data: n.get(t, "data"),
                    name: n.get(t, "name"),
                    value: n.get(t, "value")
                }
            }
            return
        }

        function z(n) {
            $(n);
            if (n.backgroundColor)if (!r && n.backgroundColor.indexOf("rgba") != -1) {
                var i = n.backgroundColor.split(",");
                t.style.filter = "alpha(opacity=" + i[3].substring(0, i[3].lastIndexOf(")")) * 100 + ")", i.length = 3, i[0] = i[0].replace("a", ""), t.style.backgroundColor = i.join(",") + ")"
            } else t.style.backgroundColor = n.backgroundColor;
            V(), f.clear();
            var s = e("./chart"), o = e("./component"), l;
            if (n.title) {
                var c = o.get("title");
                l = new c(u, d, f, n), p.push(l), a.component.title = l
            }
            var h;
            if (n.tooltip) {
                var v = o.get("tooltip");
                h = new v(u, d, f, n, t, a), p.push(h), a.component.tooltip = h
            }
            var m;
            if (n.legend) {
                var w = o.get("legend");
                m = new w(u, d, f, n, g), p.push(m), a.component.legend = m
            }
            var E;
            if (n.dataRange) {
                var S = o.get("dataRange");
                E = new S(u, d, f, n), p.push(E), a.component.dataRange = E
            }
            var x, T, N, C;
            if (n.grid || n.xAxis || n.yAxis) {
                var k = o.get("grid");
                x = new k(u, d, f, n), p.push(x), a.component.grid = x;
                var L = o.get("dataZoom");
                T = new L(u, d, f, n, {legend: m, grid: x}), p.push(T), a.component.dataZoom = T;
                var A = o.get("axis");
                N = new A(u, d, f, n, {
                    legend: m,
                    grid: x
                }, "xAxis"), p.push(N), a.component.xAxis = N, C = new A(u, d, f, n, {
                    legend: m,
                    grid: x
                }, "yAxis"), p.push(C), a.component.yAxis = C
            }
            var O;
            if (n.polar) {
                var M = o.get("polar");
                O = new M(u, d, f, n, {legend: m}), p.push(O), a.component.polar = O
            }
            h && h.setComponent();
            var _, D, P, H = {};
            for (var B = 0, j = n.series.length; B < j; B++) {
                D = n.series[B].type;
                if (!D) {
                    console.error("series[" + B + "] chart type has not been defined.");
                    continue
                }
                H[D] || (H[D] = !0, _ = s.get(D), _ ? (P = new _(u, d, f, n, {
                    tooltip: h,
                    legend: m,
                    dataRange: E,
                    grid: x,
                    xAxis: N,
                    yAxis: C,
                    polar: O
                }), p.push(P), a.chart[D] = P) : console.error(D + " has not been required."))
            }
            y.render(n), b.render(n, {dataZoom: T});
            if (n.animation && !n.renderAsImage) {
                var F = p.length;
                while (F--)P = p[F], P && P.animation && P.shapeList && P.shapeList.length < n.animationThreshold && P.animation();
                f.refresh()
            } else f.render();
            var I = "IMG" + a.id, q = document.getElementById(I);
            n.renderAsImage && r ? (q ? q.src = tt(n.renderAsImage) : (q = nt(n.renderAsImage), q.id = I, q.style.position = "absolute", q.style.left = 0, q.style.top = 0, t.firstChild.appendChild(q)), ot(), f.un(), V(), f.clear()) : q && q.parentNode.removeChild(q), q = null
        }

        function W() {
            var t = e("zrender/tool/util");
            h.legend && h.legend.selected ? g = h.legend.selected : g = {}, c = t.clone(h), l = t.clone(h), y.clear(), b.reset(l), z(l)
        }

        function X(t) {
            t = t || {};
            var n = t.option;
            if (!w && t.option) {
                var r = e("./util/ecQuery");
                r.query(c, "toolbox.show") && r.query(c, "toolbox.feature.magicType.show") ? n = q() : n = q(y.getOption());
                var i = e("zrender/tool/util");
                i.merge(n, t.option, {overwrite: !0, recursive: !0}), i.merge(c, t.option, {
                    overwrite: !0,
                    recursive: !0
                }), i.merge(h, t.option, {overwrite: !0, recursive: !0}), y.refresh(n), b.refresh(n)
            }
            f.clearAnimation();
            for (var s = 0, o = p.length; s < o; s++)p[s].refresh && p[s].refresh(n);
            f.refresh()
        }

        function V() {
            f.clearAnimation();
            var e = p.length;
            while (e--)p[e] && p[e].dispose && p[e].dispose();
            p = [], a.chart = {island: y}, a.component = {toolbox: b}
        }

        function $(t) {
            typeof t.backgroundColor == "undefined" && (t.backgroundColor = u.backgroundColor), typeof t.calculable == "undefined" && (t.calculable = u.calculable), typeof t.calculableColor == "undefined" && (t.calculableColor = u.calculableColor), typeof t.calculableHolderColor == "undefined" && (t.calculableHolderColor = u.calculableHolderColor), typeof t.nameConnector == "undefined" && (t.nameConnector = u.nameConnector), typeof t.valueConnector == "undefined" && (t.valueConnector = u.valueConnector), typeof t.animation == "undefined" && (t.animation = u.animation), typeof t.animationThreshold == "undefined" && (t.animationThreshold = u.animationThreshold), typeof t.animationDuration == "undefined" && (t.animationDuration = u.animationDuration), typeof t.animationEasing == "undefined" && (t.animationEasing = u.animationEasing), typeof t.addDataAnimation == "undefined" && (t.addDataAnimation = u.addDataAnimation);
            var n = e("zrender/tool/color");
            t.color && t.color.length > 0 ? f.getColor = function (e) {
                return n.getColor(e, t.color)
            } : f.getColor = function (e) {
                return n.getColor(e, u.color)
            }, typeof t.DRAG_ENABLE_TIME == "undefined" && (t.DRAG_ENABLE_TIME = u.DRAG_ENABLE_TIME)
        }

        function J(t, n) {
            var r = e("zrender/tool/util");
            n ? l = r.clone(t) : r.merge(l, r.clone(t), {overwrite: !0, recursive: !0});
            if (!l.series || l.series.length === 0)return;
            return c = r.clone(l), h = r.clone(l), l.legend && l.legend.selected ? g = l.legend.selected : g = {}, y.clear(), b.reset(l), z(l), a
        }

        function K() {
            var t = e("./util/ecQuery"), n = e("zrender/tool/util");
            return t.query(c, "toolbox.show") && t.query(c, "toolbox.feature.magicType.show") ? n.clone(q()) : n.clone(q(y.getOption()))
        }

        function Q(e, t) {
            return t ? (l.series = e, a.setOption(l, t)) : a.setOption({series: e}), a
        }

        function G() {
            return K().series
        }

        function Y(t, n, r, s, o) {
            var u = e("./util/ecQuery"), f;
            u.query(c, "toolbox.show") && u.query(c, "toolbox.feature.magicType.show") ? f = q() : f = q(y.getOption());
            var l = e("zrender/tool/util"), v = t instanceof Array ? t : [[t, n, r, s, o]], m, w;
            for (var E = 0, S = v.length; E < S; E++) {
                t = v[E][0], n = v[E][1], r = v[E][2], s = v[E][3], o = v[E][4];
                if (h.series[t]) {
                    r ? (h.series[t].data.unshift(n), c.series[t].data.unshift(n), s || (h.series[t].data.pop(), n = c.series[t].data.pop())) : (h.series[t].data.push(n), c.series[t].data.push(n), s || (h.series[t].data.shift(), n = c.series[t].data.shift()));
                    if (typeof o != "undefined" && h.series[t].type == i.CHART_TYPE_PIE && c.legend && c.legend.data) f.legend.data = c.legend.data, r ? (h.legend.data.unshift(o), c.legend.data.unshift(o)) : (h.legend.data.push(o), c.legend.data.push(o)), s || (w = l.indexOf(c.legend.data, n.name), w != -1 && (h.legend.data.splice(w, 1), c.legend.data.splice(w, 1))), g[o] = !0; else if (typeof o != "undefined" && typeof h.xAxis != "undefined" && typeof h.yAxis != "undefined") {
                        m = h.series[t].xAxisIndex || 0;
                        if (typeof h.xAxis[m].type == "undefined" || h.xAxis[m].type == "category") r ? (h.xAxis[m].data.unshift(o), c.xAxis[m].data.unshift(o), s || (h.xAxis[m].data.pop(), c.xAxis[m].data.pop())) : (h.xAxis[m].data.push(o), c.xAxis[m].data.push(o), s || (h.xAxis[m].data.shift(), c.xAxis[m].data.shift()));
                        m = h.series[t].yAxisIndex || 0, h.yAxis[m].type == "category" && (r ? (h.yAxis[m].data.unshift(o), c.yAxis[m].data.unshift(o), s || (h.yAxis[m].data.pop(), c.yAxis[m].data.pop())) : (h.yAxis[m].data.push(o), c.yAxis[m].data.push(o), s || (h.yAxis[m].data.shift(), c.yAxis[m].data.shift())))
                    }
                }
            }
            f.legend && (f.legend.selected = g);
            for (var E = 0, S = p.length; E < S; E++)f.addDataAnimation && p[E].addDataAnimation && p[E].addDataAnimation(v), p[E].type == i.COMPONENT_TYPE_DATAZOOM && (p[E].silence(!0), p[E].init(f), p[E].silence(!1));
            return y.refresh(f), b.refresh(f), setTimeout(function () {
                d.dispatch(i.EVENT.REFRESH, "", {option: f})
            }, f.addDataAnimation ? 500 : 0), a
        }

        function Z() {
            return t
        }

        function et() {
            return f
        }

        function tt(e) {
            if (!r)return "";
            if (p.length === 0) {
                var t = "IMG" + a.id, n = document.getElementById(t);
                if (n)return n.src
            }
            a.component.tooltip && a.component.tooltip.hideTip(), e = e || "png", e != "png" && e != "jpeg" && (e = "png");
            var i = l.backgroundColor && l.backgroundColor.replace(" ", "") == "rgba(0,0,0,0)" ? "#fff" : l.backgroundColor;
            return f.toDataURL("image/" + e, i)
        }

        function nt(e) {
            var t = document.createElement("img");
            return t.src = tt(e), t.title = h.title && h.title.text || "ECharts", t
        }

        function rt(n) {
            if (!lt())return tt(n);
            var r, i = [t.offsetLeft, t.offsetTop, t.offsetWidth, t.offsetHeight],
                s = {self: {img: a.getDataURL(n), left: i[0], top: i[1], right: i[0] + i[2], bottom: i[1] + i[3]}},
                o = s.self.left, u = s.self.top, f = s.self.right, c = s.self.bottom;
            for (var h in v)r = v[h].getDom(), i = [r.offsetLeft, r.offsetTop, r.offsetWidth, r.offsetHeight], s[h] = {
                img: v[h].getDataURL(n),
                left: i[0],
                top: i[1],
                right: i[0] + i[2],
                bottom: i[1] + i[3]
            }, o = Math.min(o, s[h].left), u = Math.min(u, s[h].top), f = Math.max(f, s[h].right), c = Math.max(c, s[h].bottom);
            var p = document.createElement("div");
            p.style.position = "absolute", p.style.left = "-4000px", p.style.width = f - o + "px", p.style.height = c - u + "px", document.body.appendChild(p);
            var d = e("zrender").init(p);
            for (var h in s)d.addShape({shape: "image", style: {x: s[h].left - o, y: s[h].top - u, image: s[h].img}});
            d.render();
            var m = l.backgroundColor && l.backgroundColor.replace(" ", "") == "rgba(0,0,0,0)" ? "#fff" : l.backgroundColor,
                g = d.toDataURL("image/png", m);
            return setTimeout(function () {
                d.dispose(), p.parentNode.removeChild(p), p = null
            }, 100), g
        }

        function it(e) {
            var t = document.createElement("img");
            return t.src = rt(e), t.title = h.title && h.title.text || "ECharts", t
        }

        function st(e, t) {
            return d.bind(e, t), a
        }

        function ot(e, t) {
            return d.unbind(e, t), a
        }

        function ut(e) {
            if (!e)return a;
            v || (v = {});
            if (e instanceof Array)for (var t = 0, n = e.length; t < n; t++)v[e[t].id] = e[t]; else v[e.id] = e;
            return a
        }

        function at(e) {
            if (!e || !v)return a;
            if (e instanceof Array)for (var t = 0, n = e.length; t < n; t++)delete v[e[t].id]; else delete v[e.id];
            for (var r in v)return r, a;
            return v = !1, a
        }

        function ft(e) {
            e.__echartsId != a.id && x(e)
        }

        function lt() {
            return !!v
        }

        function ct(t) {
            b.hideDataView();
            var n = e("zrender/tool/util");
            t = t || {}, t.textStyle = t.textStyle || {};
            var r = n.merge(n.clone(t.textStyle), u.textStyle, {overwrite: !1});
            return t.textStyle.textFont = r.fontStyle + " " + r.fontWeight + " " + r.fontSize + "px " + r.fontFamily, t.textStyle.text = t.text || u.loadingText, typeof t.x != "undefined" && (t.textStyle.x = t.x), typeof t.y != "undefined" && (t.textStyle.y = t.y), f.showLoading(t), a
        }

        function ht() {
            return f.hideLoading(), a
        }

        function pt(t) {
            var n = e("zrender/tool/util");
            if (t) {
                if (typeof t == "string")switch (t) {
                    case"default":
                        t = e("./theme/default");
                        break;
                    default:
                        t = e("./theme/default")
                } else t = t || {};
                for (var s in u)delete u[s];
                for (var s in i)u[s] = n.clone(i[s]);
                t.color && (u.color = []), t.symbolList && (u.symbolList = []), n.merge(u, n.clone(t), {
                    overwrite: !0,
                    recursive: !0
                })
            }
            r || (u.textStyle.fontFamily = u.textStyle.fontFamily2), h && a.restore()
        }

        function dt() {
            f.resize();
            if (l.renderAsImage && r)return z(l), a;
            f.clearAnimation(), y.resize(), b.resize();
            for (var e = 0, t = p.length; e < t; e++)p[e].resize && p[e].resize();
            return f.refresh(), d.dispatch(i.EVENT.RESIZE), a
        }

        function vt() {
            return V(), f.clear(), l = {}, c = {}, h = {}, a
        }

        function mt() {
            var e = t.getAttribute(o);
            e && delete s[e], y.dispose(), b.dispose(), d.unbind(), a.clear(), f.dispose(), f = null, a = null;
            return
        }

        var i = e("./config"), u = e("zrender/tool/util").clone(i), a = this, f, l, c, h, p, d, v = !1,
            m = {dragIn: !1, dragOut: !1, needRefresh: !1}, g, y, b, w;
        E();
        var S = null;
        a.setOption = J, a.setSeries = Q, a.addData = Y, a.getOption = K, a.getSeries = G, a.getDom = Z, a.getZrender = et, a.getDataURL = tt, a.getImage = nt, a.getConnectedDataURL = rt, a.getConnectedImage = it, a.on = st, a.un = ot, a.connect = ut, a.disConnect = at, a.connectedEventHandler = ft, a.isConnected = lt, a.showLoading = ct, a.hideLoading = ht, a.setTheme = pt, a.resize = dt, a.refresh = X, a.restore = W, a.clear = vt, a.dispose = mt
    }

    var t = {}, n = t, r = e("zrender/tool/env").canvasSupported, i = new Date - 0, s = {}, o = "_echarts_instance_";
    return t.version = "1.4.1", t.dependencies = {zrender: "1.1.2"}, t.init = function (e, t) {
        e = e instanceof Array ? e[0] : e;
        var n = e.getAttribute(o);
        return n || (n = i++, e.setAttribute(o, n)), s[n] && s[n].dispose(), s[n] = new u(e), s[n].id = n, s[n].setTheme(t), s[n]
    }, t.getInstanceById = function (e) {
        return s[e]
    }, t
}), define("echarts", ["echarts/echarts"], function (e) {
    return e
}), define("echarts/util/shape/symbol", ["require", "zrender/tool/color", "zrender/shape", "zrender/shape/base", "zrender/shape"], function (e) {
    function t() {
        this.type = "symbol"
    }

    return t.prototype = {
        buildPath: function (t, n) {
            var r = n.pointList, i = this.getRect(n), s = window.devicePixelRatio || 1;
            i = {x: Math.floor(i.x), y: Math.floor(i.y), width: Math.floor(i.width), height: Math.floor(i.height)};
            var o = t.getImageData(i.x * s, i.y * s, i.width * s, i.height * s), u = o.data, a,
                f = e("zrender/tool/color"), l = f.toArray(n.color), c = l[0], h = l[1], p = l[2], d = i.width;
            for (var v = 1, m = r.length; v < m; v++)a = ((Math.floor(r[v][0]) - i.x) * s + (Math.floor(r[v][1]) - i.y) * d * s * s) * 4, u[a] = c, u[a + 1] = h, u[a + 2] = p, u[a + 3] = 255;
            t.putImageData(o, i.x * s, i.y * s);
            return
        }, getRect: function (t) {
            var n = e("zrender/shape");
            return n.get("polygon").getRect(t)
        }, isCover: function () {
            return !1
        }
    }, e("zrender/shape/base").derive(t), e("zrender/shape").define("symbol", new t), t
}), define("echarts/chart/scatter", ["require", "../component/base", "./calculableBase", "zrender/tool/color", "../util/shape/symbol", "../chart"], function (e) {
    function t(t, n, r, i, s) {
        function v() {
            f.selectedMap = {};
            var e = s.legend, n = [], i, o, u, c;
            for (var v = 0, g = l.length; v < g; v++) {
                i = l[v], o = i.name;
                if (i.type == t.CHART_TYPE_SCATTER) {
                    l[v] = f.reformOption(l[v]), d[v] = f.query(i, "symbol") || p[v % p.length];
                    if (e) {
                        f.selectedMap[o] = e.isSelected(o), h[v] = a.alpha(e.getColor(o), .5), u = e.getItemShape(o);
                        if (u) {
                            u.shape = "icon";
                            var c = d[v];
                            u.style.brushType = c.match("empty") ? "stroke" : "both", c = c.replace("empty", "").toLowerCase(), c.match("star") && (u.style.n = c.replace("star", "") - 0 || 5, c = "star"), c.match("image") && (u.style.image = c.replace(new RegExp("^image:\\/\\/"), ""), u.style.x += Math.round((u.style.width - u.style.height) / 2), u.style.width = u.style.height, c = "image"), u.style.iconType = c, e.setItemShape(o, u)
                        }
                    } else f.selectedMap[o] = !0, h[v] = r.getColor(v);
                    f.selectedMap[o] && n.push(v)
                }
            }
            if (n.length === 0)return;
            m(n);
            for (var v = 0, g = f.shapeList.length; v < g; v++)f.shapeList[v].id = r.newShapeId(f.type), r.addShape(f.shapeList[v])
        }

        function m(e) {
            var t, n, r, i, o, u, a = {}, c, h;
            for (var p = 0, d = e.length; p < d; p++) {
                t = e[p], n = l[t];
                if (n.data.length === 0)continue;
                o = s.xAxis.getAxis(n.xAxisIndex || 0), u = s.yAxis.getAxis(n.yAxisIndex || 0), a[t] = [];
                for (var v = 0, m = n.data.length; v < m; v++) {
                    r = n.data[v], i = typeof r != "undefined" ? typeof r.value != "undefined" ? r.value : r : "-";
                    if (i == "-" || i.length < 2)continue;
                    c = o.getCoord(i[0]), h = u.getCoord(i[1]), a[t].push([c, h, v, r.name || ""])
                }
                y(o, u, n.data, a[t]), f.buildMark(n, t, s, {xMarkMap: g(t) ? y(o, u, n.data, a[t]) : {}})
            }
            b(a)
        }

        function g(e) {
            var t = l[e], n = [];
            t.markPoint && t.markPoint.data && n.push(t.markPoint.data), t.markLine && t.markLine.data && n.push(t.markLine.data);
            var r, i = n.length;
            while (i--) {
                r = n[i];
                for (var s = 0, o = r.length; s < o; s++)if (r[s].type == "max" || r[s].type == "min" || r[s].type == "average")return !0
            }
            return !1
        }

        function y(e, t, n, r) {
            var i = {
                min0: Number.POSITIVE_INFINITY,
                max0: Number.NEGATIVE_INFINITY,
                sum0: 0,
                counter0: 0,
                average0: 0,
                min1: Number.POSITIVE_INFINITY,
                max1: Number.NEGATIVE_INFINITY,
                sum1: 0,
                counter1: 0,
                average1: 0
            }, o;
            for (var u = 0, a = r.length; u < a; u++)o = n[r[u][2]].value || n[r[u][2]], i.min0 > o[0] && (i.min0 = o[0], i.minY0 = r[u][1], i.minX0 = r[u][0]), i.max0 < o[0] && (i.max0 = o[0], i.maxY0 = r[u][1], i.maxX0 = r[u][0]), i.sum0 += o[0], i.counter0++, i.min1 > o[1] && (i.min1 = o[1], i.minY1 = r[u][1], i.minX1 = r[u][0]), i.max1 < o[1] && (i.max1 = o[1], i.maxY1 = r[u][1], i.maxX1 = r[u][0]), i.sum1 += o[1], i.counter1++;
            var f = s.grid.getX(), l = s.grid.getXend(), c = s.grid.getY(), h = s.grid.getYend();
            i.average0 = (i.sum0 / i.counter0).toFixed(2) - 0;
            var p = e.getCoord(i.average0);
            i.averageLine0 = [[p, h], [p, c]], i.minLine0 = [[i.minX0, h], [i.minX0, c]], i.maxLine0 = [[i.maxX0, h], [i.maxX0, c]], i.average1 = (i.sum1 / i.counter1).toFixed(2) - 0;
            var d = t.getCoord(i.average1);
            return i.averageLine1 = [[f, d], [l, d]], i.minLine1 = [[f, i.minY1], [l, i.minY1]], i.maxLine1 = [[f, i.maxY1], [l, i.maxY1]], i
        }

        function b(e) {
            var t, n, r, i;
            for (var s in e) {
                t = l[s], n = e[s];
                if (t.large && t.data.length > t.largeThreshold) {
                    f.shapeList.push(E(n, f.getItemStyleColor(f.query(t, "itemStyle.normal.color"), s, -1) || h[s]));
                    continue
                }
                for (var o = 0, u = n.length; o < u; o++)r = n[o], i = w(s, r[2], r[3], r[0], r[1]), i && f.shapeList.push(i)
            }
        }

        function w(e, t, n, r, i) {
            var o = l[e], u = o.data[t], a = s.dataRange, p;
            if (a) {
                p = isNaN(u[2]) ? h[e] : a.getColor(u[2]);
                if (!p)return null
            } else p = h[e];
            var v = f.getSymbolShape(o, e, u, t, n, r, i, d[e], p, "rgba(0,0,0,0)", "vertical");
            return v.zlevel = c, v._mark = !1, v._main = !0, v
        }

        function E(e, t) {
            return {
                shape: "symbol",
                zlevel: c,
                _main: !0,
                hoverable: !1,
                style: {pointList: e, color: t, strokeColor: t}
            }
        }

        function S(e, t, n, r) {
            var i = s.xAxis.getAxis(e.xAxisIndex), o = s.yAxis.getAxis(e.yAxisIndex), u;
            if (!n.type || n.type != "max" && n.type != "min" && n.type != "average") u = [typeof n.xAxis != "string" && i.getCoordByIndex ? i.getCoordByIndex(n.xAxis || 0) : i.getCoord(n.xAxis || 0), typeof n.yAxis != "string" && o.getCoordByIndex ? o.getCoordByIndex(n.yAxis || 0) : o.getCoord(n.yAxis || 0)]; else {
                var a = typeof n.valueIndex != "undefined" ? n.valueIndex : 1;
                u = [r.xMarkMap[n.type + "X" + a], r.xMarkMap[n.type + "Y" + a], r.xMarkMap[n.type + "Line" + a], r.xMarkMap[n.type + a]]
            }
            return u
        }

        function x(e, t) {
            s = t, T(e)
        }

        function T(e) {
            e && (i = e, l = i.series), f.clear(), v()
        }

        function N(e, t) {
            s.dataRange && (T(), t.needRefresh = !0);
            return
        }

        function C() {
            var e = f.query(i, "animationDuration"), t = f.query(i, "animationEasing"), n, s, o;
            for (var u = 0, a = f.shapeList.length; u < a; u++)if (f.shapeList[u]._main) {
                if (f.shapeList[u].shape == "symbol")continue;
                o = l[f.shapeList[u]._seriesIndex], n = f.shapeList[u]._x || 0, s = f.shapeList[u]._y || 0, r.modShape(f.shapeList[u].id, {scale: [0, 0, n, s]}, !0), r.animate(f.shapeList[u].id, "").when(f.query(o, "animationDuration") || e, {scale: [1, 1, n, s]}).start(f.query(o, "animationEasing") || t)
            }
            f.animationMark(e, t)
        }

        var o = e("../component/base");
        o.call(this, t, r);
        var u = e("./calculableBase");
        u.call(this, r, i);
        var a = e("zrender/tool/color"), f = this;
        f.type = t.CHART_TYPE_SCATTER;
        var l, c = f.getZlevelBase(), h = {}, p = t.symbolList, d = {};
        f.getMarkCoord = S, f.animation = C, f.init = x, f.refresh = T, f.ondataRange = N, x(i, s)
    }

    return e("../util/shape/symbol"), e("../chart").define("scatter", t), t
}), define("echarts/util/shape/candle", ["require", "zrender/tool/matrix", "zrender/shape/base", "zrender/shape"], function (e) {
    function n() {
        this.type = "candle"
    }

    var t = e("zrender/tool/matrix");
    return n.prototype = {
        _numberOrder: function (e, t) {
            return t - e
        }, buildPath: function (e, t) {
            t.y.sort(this._numberOrder), e.moveTo(t.x, t.y[3]), e.lineTo(t.x, t.y[2]), e.moveTo(t.x - t.width / 2, t.y[2]), e.rect(t.x - t.width / 2, t.y[2], t.width, t.y[1] - t.y[2]), e.moveTo(t.x, t.y[1]), e.lineTo(t.x, t.y[0]);
            return
        }, getRect: function (e) {
            var t;
            return e.brushType == "stroke" || e.brushType == "fill" ? t = e.lineWidth || 1 : t = 0, {
                x: Math.round(e.x - e.width / 2 - t / 2),
                y: Math.round(e.y[3] - t / 2),
                width: e.width + t,
                height: e.y[0] - e.y[3] + t
            }
        }, isCover: function (e, n, r) {
            if (e.__needTransform && e._transform) {
                var i = [];
                t.invert(i, e._transform);
                var s = [n, r];
                t.mulVector(s, i, [n, r, 1]), n == s[0] && r == s[1] && (Math.abs(e.rotation[0]) > 1e-4 || Math.abs(e.position[0]) > 1e-4 || Math.abs(e.position[1]) > 1e-4 || Math.abs(e.scale[0] - 1) > 1e-4 || Math.abs(e.scale[1] - 1) > 1e-4 ? e.__needTransform = !0 : e.__needTransform = !1), n = s[0], r = s[1]
            }
            var o;
            return e.style.__rect ? o = e.style.__rect : (o = this.getRect(e.style), o = [o.x, o.x + o.width, o.y, o.y + o.height], e.style.__rect = o), n >= o[0] && n <= o[1] && r >= o[2] && r <= o[3] ? !0 : !1
        }
    }, e("zrender/shape/base").derive(n), e("zrender/shape").define("candle", new n), n
}), define("echarts/chart/k", ["require", "../component/base", "./calculableBase", "../util/ecData", "../util/shape/candle", "../chart"], function (e) {
    function t(t, n, r, i, s) {
        function h() {
            f.selectedMap = {};
            var e = {top: [], bottom: []}, n;
            for (var i = 0, o = l.length; i < o; i++)l[i].type == t.CHART_TYPE_K && (l[i] = f.reformOption(l[i]), n = s.xAxis.getAxis(l[i].xAxisIndex), n.type == t.COMPONENT_TYPE_AXIS_CATEGORY && e[n.getPosition()].push(i));
            for (var u in e)e[u].length > 0 && p(u, e[u]);
            for (var i = 0, o = f.shapeList.length; i < o; i++)f.shapeList[i].id = r.newShapeId(f.type), r.addShape(f.shapeList[i])
        }

        function p(e, t) {
            var n = d(t), r = n.locationMap, i = n.maxDataLength;
            if (i === 0 || r.length === 0)return;
            v(i, r);
            for (var o = 0, u = t.length; o < u; o++)f.buildMark(l[t[o]], t[o], s)
        }

        function d(e) {
            var t, n, r = s.legend, i = [], o = 0;
            for (var u = 0, a = e.length; u < a; u++)t = l[e[u]], n = t.name, r ? f.selectedMap[n] = r.isSelected(n) : f.selectedMap[n] = !0, f.selectedMap[n] && i.push(e[u]), o = Math.max(o, t.data.length);
            return {locationMap: i, maxDataLength: o}
        }

        function v(e, t) {
            var n, r, i, o, u, a, f = {}, c, h, p, d;
            for (var v = 0, g = t.length; v < g; v++) {
                n = t[v], r = l[n], i = r.xAxisIndex || 0, o = s.xAxis.getAxis(i), c = r.barWidth || Math.floor(o.getGap() / 2), d = r.barMaxWidth, d && d < c && (c = d), u = r.yAxisIndex || 0, a = s.yAxis.getAxis(u), f[n] = [];
                for (var y = 0, b = e; y < b; y++) {
                    if (typeof o.getNameByIndex(y) == "undefined")break;
                    h = r.data[y], p = typeof h != "undefined" ? typeof h.value != "undefined" ? h.value : h : "-";
                    if (p == "-" || p.length != 4)continue;
                    f[n].push([o.getCoordByIndex(y), c, a.getCoord(p[0]), a.getCoord(p[1]), a.getCoord(p[2]), a.getCoord(p[3]), y, o.getNameByIndex(y)])
                }
            }
            m(f)
        }

        function m(e) {
            var n, r, i, s, o, u, a, c, h, p, d, v, m, y, b, w;
            for (var E = 0, S = l.length; E < S; E++) {
                d = l[E], y = e[E];
                if (d.type == t.CHART_TYPE_K && typeof y != "undefined") {
                    v = d, n = f.query(v, "itemStyle.normal.lineStyle.width"), r = f.query(v, "itemStyle.normal.lineStyle.color"), i = f.query(v, "itemStyle.normal.lineStyle.color0"), s = f.query(v, "itemStyle.normal.color"), o = f.query(v, "itemStyle.normal.color0"), u = f.query(v, "itemStyle.emphasis.lineStyle.width"), a = f.query(v, "itemStyle.emphasis.lineStyle.color"), c = f.query(v, "itemStyle.emphasis.lineStyle.color0"), h = f.query(v, "itemStyle.emphasis.color"), p = f.query(v, "itemStyle.emphasis.color0");
                    for (var x = 0, T = y.length; x < T; x++)b = y[x], m = d.data[b[6]], v = m, w = b[3] < b[2], f.shapeList.push(g(E, b[6], b[7], b[0], b[1], b[2], b[3], b[4], b[5], w ? f.query(v, "itemStyle.normal.color") || s : f.query(v, "itemStyle.normal.color0") || o, f.query(v, "itemStyle.normal.lineStyle.width") || n, w ? f.query(v, "itemStyle.normal.lineStyle.color") || r : f.query(v, "itemStyle.normal.lineStyle.color0") || i, w ? f.query(v, "itemStyle.emphasis.color") || h || s : f.query(v, "itemStyle.emphasis.color0") || p || o, f.query(v, "itemStyle.emphasis.lineStyle.width") || u || n, w ? f.query(v, "itemStyle.emphasis.lineStyle.color") || a || r : f.query(v, "itemStyle.emphasis.lineStyle.color0") || c || i))
                }
            }
        }

        function g(e, t, n, r, i, s, o, u, f, h, p, d, v, m, g) {
            var y = {
                shape: "candle",
                zlevel: c,
                clickable: !0,
                style: {x: r, y: [s, o, u, f], width: i, color: h, strokeColor: d, lineWidth: p, brushType: "both"},
                highlightStyle: {color: v, strokeColor: g, lineWidth: m},
                _seriesIndex: e
            };
            return a.pack(y, l[e], e, l[e].data[t], t, n), y
        }

        function y(e, t, n) {
            var r = s.xAxis.getAxis(e.xAxisIndex), i = s.yAxis.getAxis(e.yAxisIndex);
            return [typeof n.xAxis != "string" && r.getCoordByIndex ? r.getCoordByIndex(n.xAxis || 0) : r.getCoord(n.xAxis || 0), typeof n.yAxis != "string" && i.getCoordByIndex ? i.getCoordByIndex(n.yAxis || 0) : i.getCoord(n.yAxis || 0)]
        }

        function b(e, t) {
            s = t, w(e)
        }

        function w(e) {
            e && (i = e, l = i.series), f.clear(), h()
        }

        function E(e) {
            var t = {};
            for (var n = 0, i = e.length; n < i; n++)t[e[n][0]] = e[n];
            var o, u, c, h, p, d;
            for (var n = 0, i = f.shapeList.length; n < i; n++) {
                p = f.shapeList[n]._seriesIndex;
                if (t[p] && !t[p][3] && f.shapeList[n].shape == "candle") {
                    d = a.get(f.shapeList[n], "dataIndex"), h = l[p];
                    if (t[p][2] && d == h.data.length - 1) {
                        r.delShape(f.shapeList[n].id);
                        continue
                    }
                    if (!t[p][2] && d === 0) {
                        r.delShape(f.shapeList[n].id);
                        continue
                    }
                    u = s.xAxis.getAxis(h.xAxisIndex || 0).getGap(), o = t[p][2] ? u : -u, c = 0, r.animate(f.shapeList[n].id, "").when(500, {position: [o, c]}).start()
                }
            }
        }

        function S() {
            var e = f.query(i, "animationDuration"), t = f.query(i, "animationEasing"), n, s, o;
            for (var u = 0, a = f.shapeList.length; u < a; u++)f.shapeList[u].shape == "candle" && (o = l[f.shapeList[u]._seriesIndex], n = f.shapeList[u].style.x, s = f.shapeList[u].style.y[0], r.modShape(f.shapeList[u].id, {scale: [1, 0, n, s]}, !0), r.animate(f.shapeList[u].id, "").when(f.query(o, "animationDuration") || e, {scale: [1, 1, n, s]}).start(f.query(o, "animationEasing") || t));
            f.animationMark(e, t)
        }

        var o = e("../component/base");
        o.call(this, t, r);
        var u = e("./calculableBase");
        u.call(this, r, i);
        var a = e("../util/ecData"), f = this;
        f.type = t.CHART_TYPE_K;
        var l, c = f.getZlevelBase();
        f.getMarkCoord = y, f.animation = S, f.init = b, f.refresh = w, f.addDataAnimation = E, b(i, s)
    }

    return e("../util/shape/candle"), e("../chart").define("k", t), t
}), define("echarts/chart/radar", ["require", "../component/base", "./calculableBase", "../util/ecData", "zrender/tool/color", "../util/accMath", "../chart"], function (e) {
    function t(t, n, r, i, s) {
        function y() {
            var e = s.legend;
            l.selectedMap = {}, v = [], g = 0;
            var n;
            for (var o = 0, u = c.length; o < u; o++)c[o].type == t.CHART_TYPE_RADAR && (h = l.reformOption(c[o]), n = h.name || "", l.selectedMap[n] = e ? e.isSelected(n) : !0, l.selectedMap[n] && (d = [h, i], l.deepQuery(d, "calculable") && x(o), b(o), l.buildMark(c[o], o, s)));
            for (var o = 0, u = l.shapeList.length; o < u; o++)l.shapeList[o].id = r.newShapeId(l.type), r.addShape(l.shapeList[o])
        }

        function b(e) {
            var t = s.legend, n, i = h.data, o, u, a, f = l.deepQuery(d, "calculable");
            for (var c = 0; c < i.length; c++) {
                u = i[c].name || "", l.selectedMap[u] = t ? t.isSelected(u) : !0;
                if (!l.selectedMap[u])continue;
                t ? (o = t.getColor(u), n = t.getItemShape(u), n && (n.style.brushType = l.deepQuery([i[c], h], "itemStyle.normal.areaStyle") ? "both" : "stroke", t.setItemShape(u, n))) : o = r.getColor(c), a = w(h.polarIndex, i[c]), E(a, o, c, e), S(a, o, i[c], e, c, f), g++
            }
        }

        function w(e, t) {
            var n = [], r, i = s.polar;
            for (var o = 0, u = t.value.length; o < u; o++)r = i.getVector(e, o, t.value[o]), r && n.push(r);
            return n
        }

        function E(e, t, n, r) {
            var i;
            for (var s = 0, o = e.length; s < o; s++)i = l.getSymbolShape(c[r], r, c[r].data[n], n, "", e[s][0], e[s][1], m[g % m.length], t, "#fff", "vertical"), i.zlevel = p + 1, l.shapeList.push(i)
        }

        function S(e, t, n, r, i, o) {
            var u = [n, h], d = l.getItemStyleColor(l.deepQuery(u, "itemStyle.normal.color"), r, i, n),
                v = l.deepQuery(u, "itemStyle.normal.lineStyle.width"),
                m = l.deepQuery(u, "itemStyle.normal.lineStyle.type"),
                g = l.deepQuery(u, "itemStyle.normal.areaStyle.color"),
                y = l.deepQuery(u, "itemStyle.normal.areaStyle"), b = {
                    shape: "polygon",
                    zlevel: p,
                    style: {
                        pointList: e,
                        brushType: y ? "both" : "stroke",
                        color: g || d || f.alpha(t, .5),
                        strokeColor: d || t,
                        lineWidth: v,
                        lineType: m
                    },
                    highlightStyle: {
                        brushType: l.deepQuery(u, "itemStyle.emphasis.areaStyle") || y ? "both" : "stroke",
                        color: l.deepQuery(u, "itemStyle.emphasis.areaStyle.color") || g || d || f.alpha(t, .5),
                        strokeColor: l.getItemStyleColor(l.deepQuery(u, "itemStyle.emphasis.color"), r, i, n) || d || t,
                        lineWidth: l.deepQuery(u, "itemStyle.emphasis.lineStyle.width") || v,
                        lineType: l.deepQuery(u, "itemStyle.emphasis.lineStyle.type") || m
                    }
                };
            a.pack(b, c[r], r, n, i, n.name, s.polar.getIndicator(c[r].polarIndex)), o && (b.draggable = !0, l.setCalculable(b)), l.shapeList.push(b)
        }

        function x(e) {
            var t = l.deepQuery(d, "polarIndex");
            if (!v[t]) {
                var n = s.polar.getDropBox(t);
                n.zlevel = p, l.setCalculable(n), a.pack(n, c, e, undefined, -1), l.shapeList.push(n), v[t] = !0
            }
        }

        function T(e, t) {
            if (!l.isDragend || !e.target)return;
            var n = e.target, r = a.get(n, "seriesIndex"), i = a.get(n, "dataIndex");
            s.legend && s.legend.del(c[r].data[i].name), c[r].data.splice(i, 1), t.dragOut = !0, t.needRefresh = !0, l.isDragend = !1;
            return
        }

        function N(t, n) {
            if (!l.isDrop || !t.target)return;
            var r = t.target, o = t.dragged, u = a.get(r, "seriesIndex"), f = a.get(r, "dataIndex"), h, p = s.legend, d;
            if (f == -1) h = {
                value: a.get(o, "value"),
                name: a.get(o, "name")
            }, c[u].data.push(h), p && p.add(h.name, o.style.color || o.style.strokeColor); else {
                var v = e("../util/accMath");
                h = c[u].data[f], p && p.del(h.name), h.name += i.nameConnector + a.get(o, "name"), d = a.get(o, "value");
                for (var m = 0; m < d.length; m++)h.value[m] = v.accAdd(h.value[m], d[m]);
                p && p.add(h.name, o.style.color || o.style.strokeColor)
            }
            n.dragIn = n.dragIn || !0, l.isDrop = !1;
            return
        }

        function C(e, t) {
            s = t, k(e)
        }

        function k(e) {
            e && (i = e, c = i.series), l.clear(), y()
        }

        function L() {
            var e = l.query(i, "animationDuration"), t = l.query(i, "animationEasing"), n, o, u, f, h, p = s.polar, d,
                v, m, g;
            for (var y = 0, b = l.shapeList.length; y < b; y++)l.shapeList[y].shape == "polygon" && (v = l.shapeList[y], o = a.get(v, "seriesIndex"), n = a.get(v, "dataIndex"), f = c[o], u = f.data[n], h = l.deepQuery([u, f, i], "polarIndex"), d = p.getCenter(h), m = d[0], g = d[1], r.modShape(l.shapeList[y].id, {scale: [.1, .1, m, g]}, !0), r.animate(v.id, "").when((l.query(f, "animationDuration") || e) + n * 100, {scale: [1, 1, m, g]}).start(l.query(f, "animationEasing") || t));
            l.animationMark(e, t)
        }

        var o = e("../component/base");
        o.call(this, t, r);
        var u = e("./calculableBase");
        u.call(this, r, i);
        var a = e("../util/ecData"), f = e("zrender/tool/color"), l = this;
        l.type = t.CHART_TYPE_RADAR;
        var c, h, p = l.getZlevelBase(), d, v, m = t.symbolList, g;
        l.animation = L, l.init = C, l.refresh = k, l.ondrop = N, l.ondragend = T, C(i, s)
    }

    return e("../chart").define("radar", t), t
}), define("echarts/util/shape/chord", ["require", "zrender/tool/util", "zrender/shape/base", "zrender/shape"], function (e) {
    function n() {
        this.type = "chord"
    }

    var t = e("zrender/tool/util"), r = t.getContext();
    return n.prototype = {
        buildPath: function (e, t) {
            var n = Math.PI * 2, r = t.center[0], i = t.center[1], s = t.r, o = t.source0 / 180 * Math.PI,
                u = t.source1 / 180 * Math.PI, a = t.target0 / 180 * Math.PI, f = t.target1 / 180 * Math.PI,
                l = r + Math.cos(n - o) * s, c = i - Math.sin(n - o) * s, h = r + Math.cos(n - u) * s,
                p = i - Math.sin(n - u) * s, d = r + Math.cos(n - a) * s, v = i - Math.sin(n - a) * s,
                m = r + Math.cos(n - f) * s, g = i - Math.sin(n - f) * s;
            e.moveTo(l, c), e.arc(r, i, t.r, o, u, !1), e.bezierCurveTo((r - h) * .7 + h, (i - p) * .7 + p, (r - d) * .7 + d, (i - v) * .7 + v, d, v);
            if (t.source0 === t.target0 && t.source1 === t.target1)return;
            e.arc(r, i, t.r, a, f, !1), e.bezierCurveTo((r - m) * .7 + m, (i - g) * .7 + g, (r - l) * .7 + l, (i - c) * .7 + c, l, c)
        }, getRect: function () {
            return {x: 0, y: 0, width: 0, height: 0}
        }, isCover: function (e, t, i) {
            if (!r.isPointInPath)return !1;
            if (e.__needTransform && e._transform) {
                var s = [];
                matrix.invert(s, e._transform);
                var o = [t, i];
                matrix.mulVector(o, s, [t, i, 1]), t == o[0] && i == o[1] && (Math.abs(e.rotation[0]) > 1e-4 || Math.abs(e.position[0]) > 1e-4 || Math.abs(e.position[1]) > 1e-4 || Math.abs(e.scale[0] - 1) > 1e-4 || Math.abs(e.scale[1] - 1) > 1e-4 ? e.__needTransform = !0 : e.__needTransform = !1), t = o[0], i = o[1]
            }
            return r.beginPath(), n.prototype.buildPath.call(null, r, e.style), r.closePath(), r.isPointInPath(t, i)
        }
    }, e("zrender/shape/base").derive(n), e("zrender/shape").define("chord", new n), n
}), define("echarts/util/kwargs", [], function () {
    function e(e, t) {
        var n = new RegExp("(\\/\\*[\\w\\'\\,\\(\\)\\s\\r\\n\\*]*\\*\\/)|(\\/\\/[\\w\\s\\'][^\\n\\r]*$)|(<![\\-\\-\\s\\w\\>\\/]*>)", "gim"),
            r = new RegExp("\\s+", "gim"), i = new RegExp("function.*?\\((.*?)\\)", "i"),
            s = e.toString().replace(n, "").replace(r, "").match(i)[1].split(",");
        return t !== Object(t) && (t = {}), function () {
            var n = Array.prototype.slice.call(arguments), r = n[n.length - 1];
            r && r.constructor === Object ? n.pop() : r = {};
            for (var i = 0; i < s.length; i++) {
                var o = s[i];
                o in r ? n[i] = r[o] : o in t && n[i] === undefined && (n[i] = t[o])
            }
            return e.apply(this, n)
        }
    }

    return e
}), define("echarts/util/ndarray", ["require", "./kwargs"], function (e) {
    function d(e) {
        if (typeof e == "undefined")return "number";
        switch (Object.prototype.toString.call(e)) {
            case"[object Int32Array]":
                return "int32";
            case"[object Int16Array]":
                return "int16";
            case"[object Int8Array]":
                return "int8";
            case"[object Uint32Array]":
                return "uint32";
            case"[object Uint16Array]":
                return "uint16";
            case"[object Uint8Array]":
                return "uint8";
            case"[object Uint8ClampedArray]":
                return "uint8c";
            case"[object Float32Array]":
                return "float32";
            case"[object Float64Array]":
                return "float64";
            default:
                return "number"
        }
    }

    function m(e, t) {
        if (e.indexOf(":") >= 0) {
            var n = e.split(/\s*:\s*/), r = parseInt(n[2] || 1, 10), i, s;
            if (r === 0)throw new Error("Slice step cannot be zero");
            return r > 0 ? (i = parseInt(n[0] || 0, 10), s = parseInt(n[1] || t, 10)) : (i = parseInt(n[0] || t - 1, 10), s = parseInt(n[1] || -1, 10)), i < 0 && (i = t + i), s < 0 && n[1] && (s = t + s), r > 0 ? (i = Math.max(Math.min(t, i), 0), s = Math.max(Math.min(t, s), 0)) : (i = Math.max(Math.min(t - 1, i), -1), s = Math.max(Math.min(t - 1, s), -1)), [i, s, r]
        }
        var i = parseInt(e, 10);
        i < 0 && (i = t + i);
        if (i < 0 || i > t)throw new Error(N(e));
        return i = Math.max(Math.min(t - 1, i), 0), [i, i + 1, 1]
    }

    function g(e) {
        var t = e[0];
        for (var n = 1; n < e.length; n++)t *= e[n];
        return t
    }

    function y(e) {
        var t = 1, n = e[0];
        while (n instanceof Array)n = n[0], t++;
        return t
    }

    function b(e) {
        var t = [e.length], n = e[0];
        while (n instanceof Array)t.push(n.length), n = n[0];
        return t
    }

    function w(e, t) {
        if (t == e.length - 1)return 1;
        var n = e[t + 1];
        for (var r = t + 2; r < e.length; r++)n *= e[r];
        return n
    }

    function E(e) {
        var t = [], n = 1, r = g(e);
        for (var i = 0; i < e.length; i++)n *= e[i], t.push(r / n);
        return t
    }

    function S(e, t) {
        if (e.length !== t.length)return !1;
        for (var n = 0; n < e.length; n++)if (e[n] !== t[n])return !1;
        return !0
    }

    function x(e, t) {
        return "Shape (" + e.toString() + ") (" + t.toString() + ") could not be broadcast together"
    }

    function T(e) {
        return "Axis " + e + " out of bounds"
    }

    function N(e) {
        return "Index " + e + " out of bounds"
    }

    var t = e("./kwargs"), n = Array.prototype.slice;
    this.Int32Array = window.Int32Array || Array, this.Int16Array = window.Int16Array || Array, this.Int8Array = window.Int8Array || Array, this.Uint32Array = window.Uint32Array || Array, this.Uint16Array = window.Uint16Array || Array, this.Uint8Array = window.Uint8Array || Array, this.Float32Array = window.Float32Array || Array, this.Float64Array = window.Float64Array || Array;
    var r = {
            int32: this.Int32Array,
            int16: this.Int16Array,
            int8: this.Int8Array,
            uint32: this.Uint32Array,
            uint16: this.Uint16Array,
            uint8: this.Uint8Array,
            uint8c: this.Uint8ClampedArray,
            float32: this.Float32Array,
            float64: this.Float64Array,
            number: Array
        }, i = {int32: 4, int16: 2, int8: 1, uint32: 4, uint16: 2, uint8: 1, uint8c: 1, float32: 4, float64: 8, number: 1},
        s = 0, o = 1, u = 2, a = 3, f = 4, l = 5, c = 6, h = 7, p = 8, v = function (e) {
            var t = arguments[arguments.length - 1];
            typeof t == "string" ? this._dtype = t : this._dtype = d(e);
            if (e && typeof e != "string") {
                if (e instanceof v)return e._dtype = this._dtype, e;
                typeof e.length != "undefined" ? this.initFromArray(e) : typeof e == "number" && this.initFromShape.apply(this, arguments)
            } else this._array = new r[this._dtype], this._shape = [0], this._size = 0
        };
    return v.prototype = {
        initFromArray: function (e) {
            function i(e, r, s) {
                var o = s.length;
                for (var u = 0; u < o; u++)e < t - 1 ? i(e + 1, r, s[u]) : r[n++] = s[u]
            }

            var t = y(e), n = 0, s = b(e), o = g(s);
            return this._array = new r[this._dtype](o), i(0, this._array, e), this._shape = s, this._size = o, this
        }, initFromShape: function (e) {
            typeof e == "number" && (e = Array.prototype.slice.call(arguments));
            if (e) {
                var t = g(e);
                if (this._dtype === "number") {
                    this._array = [];
                    var n = this._array;
                    for (var i = 0; i < t; i++)n[i] = 0
                } else this._array = new r[this._dtype](t)
            }
            return this._shape = e, this._size = g(e), this
        }, fill: function (e) {
            var t = this._array;
            for (var n = 0; n < t.length; n++)t[n] = e;
            return this
        }, shape: function () {
            return this._shape.slice()
        }, size: function () {
            return this._size
        }, dtype: function () {
            return this._dtype
        }, dimension: function () {
            return this._shape.length
        }, strides: function () {
            var e = E(this._shape), t = i[this._dtype];
            for (var n = 0; n < e.length; n++)e[n] *= t;
            return e
        }, reshape: function (e) {
            typeof e == "number" && (e = Array.prototype.slice.call(arguments));
            if (!this._isShapeValid(e))throw new Error("Total size of new array must be unchanged");
            return this._shape = e, this
        }, _isShapeValid: function (e) {
            return g(e) === this._size
        }, resize: function (e) {
            typeof e == "number" && (e = Array.prototype.slice.call(arguments));
            var t = g(e);
            if (t < this._size) this._dtype === "number" && (this._array.length = t); else if (this._dtype === "number")for (var n = this._array.length; n < t; n++)this._array[n] = 0; else {
                var i = new r[this._dtype](t), s = this._array;
                for (var n = 0; n < s.length; n++)i[n] = s[n];
                this._array = i
            }
            return this._shape = e, this._size = t, this
        }, transpose: t(function (e, t) {
            var n = [];
            for (var r = 0; r < this._shape.length; r++)n.push(r);
            typeof e == "undefined" && (e = n.slice());
            for (var r = 0; r < e.length; r++)if (e[r] >= this._shape.length)throw new Error(T(e[r]));
            if (e.length <= 1)return this;
            var i = n.slice();
            for (var r = 0; r < Math.floor(e.length / 2); r++)for (var s = e.length - 1; s >= Math.ceil(e.length / 2); s--)i[e[r]] = e[s], i[e[s]] = e[r];
            return this._transposelike(i, t)
        }), swapaxes: t(function (e, t, n) {
            return this.transpose([e, t], n)
        }), rollaxis: t(function (e, t, n) {
            if (e >= this._shape.length)throw new Error(T(e));
            var r = [];
            for (var i = 0; i < this._shape.length; i++)r.push(i);
            return r.splice(e, 1), r.splice(t, 0, e), this._transposelike(r, n)
        }, {start: 0}), _transposelike: function (e, t) {
            function p(e, t, r) {
                var u = i[e], a = s[e], f = c[e];
                if (e < o - 1)for (var l = 0; l < u; l++)p(e + 1, t + a * l, r + f * l); else for (var l = 0; l < u; l++)h[r + l] = n[t + a * l]
            }

            var n = this._array, i = this._shape.slice(), s = E(this._shape), o = i.length, u = [], a = [];
            for (var f = 0; f < e.length; f++) {
                var l = e[f];
                a[f] = i[l], u[f] = s[l]
            }
            s = u, i = a, this._shape = i;
            var c = E(this._shape);
            t || (t = new v, t._shape = this._shape.slice(), t._dtype = this._dtype, t._size = this._size);
            var h = new r[this._dtype](this._size);
            return t._array = h, p(0, 0, 0), t
        }, repeat: t(function (e, t, n) {
            var r;
            typeof t == "undefined" ? (r = [this._size], t = 0) : r = this._shape.slice();
            var i = r.slice();
            r[t] *= e;
            if (!n) n = new v(this._dtype), n.initFromShape(r); else if (!S(r, n._shape))throw new Error(x(r, n._shape));
            var s = n._array, o = w(i, t), u = i[t], a = this._array, f = o * u;
            for (var l = 0; l < this._size; l += f)for (var c = 0; c < o; c++) {
                var h = l + c, p = l * e + c;
                for (var d = 0; d < u; d++) {
                    for (var m = 0; m < e; m++)s[p] = a[h], p += o;
                    h += o
                }
            }
            return n
        }), choose: function () {
            console.warn("TODO")
        }, take: function () {
            console.warn("TODO")
        }, tile: function () {
            console.warn("TODO")
        }, _withPreprocess1: function (e, t, n, r) {
            var i = this._array;
            if (!this._size)return;
            if (typeof e != "undefined") {
                e < 0 && (e = this._shape.length + e);
                if (e >= this._shape.length || e < 0)throw new Error(T(e));
                var s = this._shape.slice();
                s.splice(e, 1);
                if (t && !S(s, t._shape))throw new Error(x(s, t._shape));
                t || (t = new v(this._dtype), t.initFromShape(s));
                var o = t._array, u = w(this._shape, e), a = this._shape[e], f = u * a;
                return n.call(this, o, i, f, a, u), t
            }
            return r.call(this, i)
        }, _withPreprocess2: function (e, t, n, r) {
            var i = this._array;
            if (!this._size)return;
            if (t && !S(this._shape, t._shape))throw new Error(x(this._shape, t._shape));
            t || (t = new v(this._dtype), t.initFromShape(this._shape));
            var s = t._array;
            if (typeof e != "undefined") {
                e < 0 && (e = this._shape.length + e);
                if (e >= this._shape.length || e < 0)throw new Error(T(e));
                if (e >= this._shape.length)throw new Error(T(e));
                var o = w(this._shape, e), u = this._shape[e], a = o * u;
                n.call(this, s, i, a, u, o)
            } else t.reshape([this._size]), r.call(this, s, i);
            return t
        }, max: t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = u + o, f = t[a];
                    for (var l = 0; l < r; l++) {
                        var c = t[a];
                        c > f && (f = c), a += i
                    }
                    e[s++] = f
                }
            }

            function t(e) {
                var t = e[0];
                for (var n = 1; n < this._size; n++)e[n] > t && (t = e[n]);
                return t
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), min: t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = u + o, f = t[a];
                    for (var l = 0; l < r; l++) {
                        var c = t[a];
                        c < f && (f = c), a += i
                    }
                    e[s++] = f
                }
            }

            function t(e) {
                var t = e[0];
                for (var n = 1; n < this._size; n++)e[n] < t && (t = e[n]);
                return t
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), argmax: t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = 0, f = u + o, l = t[f];
                    for (var c = 0; c < r; c++) {
                        var h = t[f];
                        h > l && (l = h, a = c), f += i
                    }
                    e[s++] = a
                }
            }

            function t(e) {
                var t = e[0], n = 0;
                for (var r = 1; r < this._size; r++)e[r] > t && (n = r, t = e[r]);
                return n
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), argmin: t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = 0, f = u + o, l = t[f];
                    for (var c = 0; c < r; c++) {
                        var h = t[f];
                        h < l && (l = h, a = c), f += i
                    }
                    e[s++] = a
                }
            }

            function t(e) {
                var t = e[0], n = 0;
                for (var r = 1; r < this._size; r++)e[r] < t && (n = r, t = e[r]);
                return n
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), sum: t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = 0, f = u + o;
                    for (var l = 0; l < r; l++)a += t[f], f += i;
                    e[s++] = a
                }
            }

            function t(e) {
                var t = 0;
                for (var n = 0; n < this._size; n++)t += e[n];
                return t
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), prod: t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = 1, f = u + o;
                    for (var l = 0; l < r; l++)a *= t[f], f += i;
                    e[s++] = a
                }
            }

            function t(e) {
                var t = 1;
                for (var n = 0; n < this._size; n++)t *= e[n];
                return t
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), mean: t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = 0, f = u + o;
                    for (var l = 0; l < r; l++)a += t[f], f += i;
                    var c = a / r;
                    e[s++] = c
                }
            }

            function t(e) {
                var t = 0, n = e.length;
                for (var r = 0; r < n; r++)t += e[r];
                var i = t / n;
                return i
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), "var": t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = 0, f = u + o;
                    for (var l = 0; l < r; l++)a += t[f], f += i;
                    var c = a / r, h = 0;
                    f = u + o;
                    for (var l = 0; l < r; l++) {
                        var p = t[f] - c;
                        h += p * p, f += i
                    }
                    e[s++] = h / r
                }
            }

            function t(e) {
                var t = 0, n = e.length;
                for (var r = 0; r < n; r++)t += e[r];
                var i = t / n, s = 0;
                for (var r = 0; r < n; r++) {
                    var o = e[r] - i;
                    s += o * o
                }
                return s / n
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), std: t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = 0, f = u + o;
                    for (var l = 0; l < r; l++)a += t[f], f += i;
                    var c = a / r, h = 0;
                    f = u + o;
                    for (var l = 0; l < r; l++) {
                        var p = t[f] - c;
                        h += p * p, f += i
                    }
                    e[s++] = Math.sqrt(h / r)
                }
            }

            function t(e) {
                var t = 0, n = e.length;
                for (var r = 0; r < n; r++)t += e[r];
                var i = t / n, s = 0;
                for (var r = 0; r < n; r++) {
                    var o = e[r] - i;
                    s += o * o
                }
                return Math.sqrt(s / n)
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), ptp: t(function () {
            function e(e, t, n, r, i) {
                var s = 0;
                for (var o = 0; o < this._size; o += n)for (var u = 0; u < i; u++) {
                    var a = o + u, f = t[a], l = t[a];
                    for (var c = 0; c < r; c++) {
                        var h = t[a];
                        h < f && (f = h), h > l && (l = h), a += i
                    }
                    e[s++] = l - f
                }
            }

            function t(e) {
                var t = e[0], n = e[0];
                for (var r = 1; r < this._size; r++)e[r] < t && (t = e[r]), e[r] > n && (n = e[r]);
                return n - t
            }

            return function (n, r) {
                return this._withPreprocess1(n, r, e, t)
            }
        }()), sort: t(function (e, t) {
            e < 0 && (e = this._shape.length + e);
            var n;
            t === "ascending" ? n = function (e, t) {
                return e - t
            } : t === "descending" && (n = function (e, t) {
                    return t - e
                });
            var r = this._array, i = w(this._shape, e), s = this._shape[e], o = i * s, u = new Array(s);
            for (var a = 0; a < this._size; a += o)for (var f = 0; f < i; f++) {
                var l = a + f;
                for (var c = 0; c < s; c++)u[c] = r[l], l += i;
                u.sort(n);
                var l = a + f;
                for (var c = 0; c < s; c++)r[l] = u[c], l += i
            }
            return this
        }, {axis: -1, order: "ascending"}), argsort: t(function (e, t, n) {
            e < 0 && (e = this._shape.length + e);
            if (!this._size)return;
            if (n && !S(this._shape, n._shape))throw new Error(x(this._shape, n._shape));
            n || (n = new v(this._dtype), n.initFromShape(this._shape));
            var r = n._array, i;
            t === "ascending" ? i = function (e, t) {
                return f[e] - f[t]
            } : t === "descending" && (i = function (e, t) {
                    return f[t] - f[e]
                });
            var s = this._array, o = w(this._shape, e), u = this._shape[e], a = o * u, f = new Array(u),
                l = new Array(u);
            for (var c = 0; c < this._size; c += a)for (var h = 0; h < o; h++) {
                var p = c + h;
                for (var d = 0; d < u; d++)f[d] = s[p], l[d] = d, p += o;
                l.sort(i);
                var p = c + h;
                for (var d = 0; d < u; d++)r[p] = l[d], p += o
            }
            return n
        }, {axis: -1, order: "ascending"}), cumsum: t(function () {
            function e(e, t, n, r, i) {
                for (var s = 0; s < this._size; s += n)for (var o = 0; o < i; o++) {
                    var u = s + o, a = u;
                    e[u] = t[u];
                    for (var f = 1; f < r; f++)a = u, u += i, e[u] = e[a] + t[u]
                }
            }

            function t(e, t) {
                e[0] = t[0];
                for (var n = 1; n < e.length; n++)e[n] = e[n - 1] + t[n]
            }

            return function (n, r) {
                return this._withPreprocess2(n, r, e, t)
            }
        }()), cumprod: t(function () {
            function e(e, t, n, r, i) {
                for (var s = 0; s < this._size; s += n)for (var o = 0; o < i; o++) {
                    var u = s + o, a = u;
                    e[u] = t[u];
                    for (var f = 1; f < r; f++)a = u, u += i, e[u] = e[a] * t[u]
                }
            }

            function t(e, t) {
                e[0] = t[0];
                for (var n = 1; n < e.length; n++)e[n] = e[n - 1] * t[n]
            }

            return function (n, r) {
                return this._withPreprocess2(n, r, e, t)
            }
        }()), dot: function () {
            console.warn("TODO")
        }, map: function (e, t) {
            var n = this._array, r = this._array, i = n[0], s = n[0], o = this._size;
            for (var u = 1; u < o; u++) {
                var a = n[u];
                a < i && (i = a), a > s && (s = a)
            }
            var f = s - i, l = t - e;
            for (var u = 0; u < o; u++)if (f === 0) r[u] = e; else {
                var a = n[u], c = (a - i) / f;
                r[u] = l * c + e
            }
            return this
        }, add: function (e, t) {
            return this.binaryOperation(this, e, s, t)
        }, sub: function (e, t) {
            return this.binaryOperation(this, e, o, t)
        }, mul: function (e, t) {
            return this.binaryOperation(this, e, u, t)
        }, div: function (e, t) {
            return this.binaryOperation(this, e, a, t)
        }, mod: function (e, t) {
            return this.binaryOperation(this, e, f, t)
        }, and: function (e, t) {
            return this.binaryOperation(this, e, l, t)
        }, or: function (e, t) {
            return this.binaryOperation(this, e, c, t)
        }, xor: function (e, t) {
            return this.binaryOperation(this, e, h, t)
        }, equal: function (e) {
            return this.binaryOperation(this, e, p, out)
        }, binaryOperation: function (e, t, n, r) {
            var i = [], d = typeof e == "number", m = typeof t == "number";
            if (d) i = t._shape.slice(); else if (m) i = e._shape.slice(); else {
                var g = e._shape.length - 1, y = t._shape.length - 1, b = e, E = t;
                while (g >= 0 && y >= 0) {
                    if (e._shape[g] == 1) i.unshift(t._shape[y]), b = e.repeat(t._shape[y], g); else if (t._shape[y] == 1) i.unshift(e._shape[g]), E = t.repeat(e._shape[g], y); else {
                        if (t._shape[y] != e._shape[g])throw new Error(x(e._shape, t._shape));
                        i.unshift(e._shape[g])
                    }
                    g--, y--
                }
                for (var T = g; T >= 0; T--)i.unshift(e._shape[T]);
                for (var T = y; T >= 0; T--)i.unshift(t._shape[T]);
                e = b, t = E
            }
            if (!r) r = new v(this._dtype), r.initFromShape(i); else if (!S(i, r._shape))throw new Error(x(i, r._shape));
            var N = r._array, C, k, L, A;
            d ? (C = t._shape.length - 1, k = !1, L = e, A = t._array) : m ? (C = e._shape.length - 1, k = !0, A = t, L = e._array) : (C = Math.abs(e._shape.length - t._shape.length), k = e._shape.length >= t._shape.length, L = e._array, A = t._array);
            var O = w(i, C), M = i[C], _ = O * M, D = r._size / _, P, H, B, j = 0;
            if (k)if (m)for (var F = 0; F < D; F++)for (var T = 0; T < _; T++) {
                P = L[j], H = A;
                switch (n) {
                    case s:
                        B = P + H;
                        break;
                    case o:
                        B = P - H;
                        break;
                    case u:
                        B = P * H;
                        break;
                    case a:
                        B = P / H;
                        break;
                    case f:
                        B = P % H;
                        break;
                    case l:
                        B = P & H;
                        break;
                    case c:
                        B = P | H;
                        break;
                    case h:
                        B = P ^ H;
                        break;
                    case p:
                        B = P == H;
                        break;
                    default:
                        throw new Error("Unkown operation " + n)
                }
                N[j] = B, j++
            } else for (var F = 0; F < D; F++)for (var T = 0; T < _; T++) {
                P = L[j], H = A[T];
                switch (n) {
                    case s:
                        B = P + H;
                        break;
                    case o:
                        B = P - H;
                        break;
                    case u:
                        B = P * H;
                        break;
                    case a:
                        B = P / H;
                        break;
                    case f:
                        B = P % H;
                        break;
                    case l:
                        B = P & H;
                        break;
                    case c:
                        B = P | H;
                        break;
                    case h:
                        B = P ^ H;
                        break;
                    case p:
                        B = P == H;
                        break;
                    default:
                        throw new Error("Unkown operation " + n)
                }
                N[j] = B, j++
            } else if (d)for (var F = 0; F < D; F++)for (var T = 0; T < _; T++) {
                P = L, H = A[j];
                switch (n) {
                    case s:
                        B = P + H;
                        break;
                    case o:
                        B = P - H;
                        break;
                    case u:
                        B = P * H;
                        break;
                    case a:
                        B = P / H;
                        break;
                    case f:
                        B = P % H;
                        break;
                    case l:
                        B = P & H;
                        break;
                    case c:
                        B = P | H;
                        break;
                    case h:
                        B = P ^ H;
                        break;
                    case p:
                        B = P == H;
                        break;
                    default:
                        throw new Error("Unkown operation " + n)
                }
                N[j] = B, j++
            } else for (var F = 0; F < D; F++)for (var T = 0; T < _; T++) {
                P = L[j], H = A[T];
                switch (n) {
                    case s:
                        B = P + H;
                        break;
                    case o:
                        B = P - H;
                        break;
                    case u:
                        B = P * H;
                        break;
                    case a:
                        B = P / H;
                        break;
                    case f:
                        B = P % H;
                        break;
                    case l:
                        B = P & H;
                        break;
                    case c:
                        B = P | H;
                        break;
                    case h:
                        B = P ^ H;
                        break;
                    case p:
                        B = P == H;
                        break;
                    default:
                        throw new Error("Unkown operation " + n)
                }
                N[j] = B, j++
            }
            return r
        }, neg: function () {
            var e = this._array;
            for (var t = 0; t < this._size; t++)e[t] = -e[t];
            return this
        }, sin: function () {
            return this._mathAdapter(Math.sin)
        }, cos: function () {
            return this._mathAdapter(Math.cos)
        }, tan: function () {
            return this._mathAdapter(Math.tan)
        }, abs: function () {
            return this._mathAdapter(Math.abs)
        }, log: function () {
            return this._mathAdapter(Math.log)
        }, sqrt: function () {
            return this._mathAdapter(Math.sqrt)
        }, ceil: function () {
            return this._mathAdapter(Math.ceil)
        }, floor: function () {
            return this._mathAdapter(Math.floor)
        }, pow: function (e) {
            var t = this._array;
            for (var n = 0; n < this._size; n++)t[n] = Math.pow(t[n], e);
            return this
        }, _mathAdapter: function (e) {
            var t = this._array;
            for (var n = 0; n < this._size; n++)t[n] = e(t[n]);
            return this
        }, round: function (e) {
            e = Math.floor(e || 0);
            var t = Math.pow(10, e), n = this._array;
            if (e === 0)for (var r = 0; r < this._size; r++)n[r] = Math.round(n[r]); else for (var r = 0; r < this._size; r++)n[r] = Math.round(n[r] * t) / t;
            return this
        }, clip: function (e, t) {
            var n = this._array;
            for (var r = 0; r < this._size; r++)n[r] = Math.max(Math.min(n[r], t), e);
            return this
        }, get: function (e, t) {
            function l(e, t) {
                var r = i[e], s = n[e];
                if (e < o - 1)if (r[2] > 0)for (var c = r[0]; c < r[1]; c += r[2])l(e + 1, t + s * c); else for (var c = r[0]; c > r[1]; c += r[2])l(e + 1, t + s * c); else if (r[2] > 0)for (var c = r[0]; c < r[1]; c += r[2])for (var h = 0; h < s; h++)u[f++] = a[c * s + h + t]; else for (var c = r[0]; c > r[1]; c += r[2])for (var h = 0; h < s; h++)u[f++] = a[c * s + h + t]
            }

            typeof e == "number" && (e = e.toString());
            var n = E(this._shape), r = this._parseRanges(e), i = r[0], s = r[1];
            if (i.length > this._shape.length)throw new Error("Too many indices");
            var o = i.length, u;
            s.length ? (t = new v(this._dtype), t.initFromShape(s), u = t._array) : u = [];
            var a = this._array, f = 0;
            return l(0, 0), s.length ? t : u[0]
        }, set: function (e, t) {
            typeof e == "number" && (e = e.toString());
            var n = E(this._shape), r = this._parseRanges(e), i = r[0], s = r[1];
            if (i.length > this._shape.length)throw new Error("Too many indices");
            var o = typeof t == "number", u = i.length, a = this._array;
            if (o)var f = t; else {
                if (!S(s, t.shape()))throw new Error(x(s, t.shape()));
                var f = t._array
            }
            var l = 0, c = function (e, t) {
                var r = i[e], s = n[e];
                if (e < u - 1)if (r[2] > 0)for (var h = r[0]; h < r[1]; h += r[2])c(e + 1, t + s * h); else for (var h = r[0]; h > r[1]; h += r[2])c(e + 1, t + s * h); else if (r[2] > 0)for (var h = r[0]; h < r[1]; h += r[2])for (var p = 0; p < s; p++)o ? a[h * s + p + t] = f : a[h * s + p + t] = f[l++]; else for (var h = r[0]; h > r[1]; h += r[2])for (var p = 0; p < s; p++)o ? a[h * s + p + t] = f : a[h * s + p + t] = f[l++]
            };
            return c(0, 0), this
        }, insert: t(function (e, t, n) {
            var i = this._array, s = !1;
            typeof e == "number" && (e = [e], s = !0), typeof t == "number" ? t = new v([t]) : t instanceof Array && (t = new v(t)), typeof n == "undefined" && (this._shape = [this._size], n = 0);
            var o = e[0], u = this._shape[n];
            for (var a = 0; a < e.length; a++) {
                e[a] < 0 && (e[a] = u + e[a]);
                if (e[a] > u)throw new Error(N(e[a]));
                if (e[a] < o)throw new Error("Index must be in ascending order");
                o = e[a]
            }
            var f = this._shape.slice();
            s ? f.splice(n, 1) : f[n] = e.length;
            var l = t._shape, c = l.length - 1, h = f.length - 1, p = t;
            while (c >= 0 && h >= 0) {
                if (l[c] === 1) p = t.repeat(f[h], c); else if (l[c] !== f[h])throw new Error(x(l, f));
                c--, h--
            }
            t = p;
            var d = w(this._shape, n), u = this._shape[n], m = u * d, y = this._size / m, b = e.length,
                E = new Uint32Array(y * b), S = 0;
            for (var T = 0; T < this._size; T += m)for (var a = 0; a < b; a++) {
                var C = e[a];
                E[S++] = T + C * d
            }
            var k = this._shape.slice();
            k[n] += e.length;
            var L = g(k);
            if (this._array.length < L)var i = new r[this._dtype](L); else var i = this._array;
            var A = this._array, O = t._array, M = E.length - 1, _ = this._size, D = E[M], P = L - 1, H = t._size - 1;
            while (M >= 0) {
                for (var a = _ - 1; a >= D; a--)i[P--] = A[a];
                _ = D, D = E[--M];
                for (var a = 0; a < d; a++)H < 0 && (H = t._size - 1), i[P--] = O[H--]
            }
            for (var a = _ - 1; a >= 0; a--)i[P--] = A[a];
            return this._array = i, this._shape = k, this._size = L, this
        }), append: function () {
            console.warn("TODO")
        }, "delete": t(function (e, t) {
            var n = this._array;
            typeof e == "number" && (e = [e]);
            var r = this._size;
            typeof t == "undefined" && (this._shape = [r], t = 0);
            var i = w(this._shape, t), s = this._shape[t], o = i * s, u = 0;
            for (var a = 0; a < r; a += o) {
                var f = 0, l = e[0], c = 0;
                while (c < e.length) {
                    l < 0 && (l += s);
                    if (l > s)throw new Error(N(l));
                    if (l < f)throw new Error("Index must be in ascending order");
                    for (var h = f; h < l; h++)for (var p = 0; p < i; p++)n[u++] = n[h * i + p + a];
                    f = l + 1, l = e[++c]
                }
                for (var h = f; h < s; h++)for (var p = 0; p < i; p++)n[u++] = n[h * i + p + a]
            }
            return this._shape[t] -= e.length, this._size = g(this._shape), this
        }), _parseRanges: function (e) {
            var t = e.split(/\s*,\s*/), n = [], r = [], i = 0;
            for (var s = 0; s < t.length; s++)if (t[s] === "...") {
                var o = this._shape.length - (t.length - s);
                while (i <= o)n.push([0, this._shape[i], 1]), r.push(this._shape[i]), i++
            } else {
                var u = m(t[s], this._shape[i]);
                n.push(u);
                if (t[s].indexOf(":") >= 0) {
                    var a = Math.floor((u[1] - u[0]) / u[2]);
                    a = a < 0 ? 0 : a, r.push(a)
                }
                i++
            }
            for (; i < this._shape.length; i++)r.push(this._shape[i]);
            return [n, r]
        }, toArray: function () {
            function i(s, o) {
                var u = n[s];
                for (var a = 0; a < u; a++)s < r - 1 ? i(s + 1, o[a] = []) : o[a] = e[t++]
            }

            var e = this._array, t = 0, n = this._shape, r = n.length, s = [];
            return i(0, s), s
        }, copy: function () {
            var e = new v;
            return e._array = n.call(this._array), e._shape = this._shape.slice(), e._dtype = this._dtype, e._size = this._size, e
        }, constructor: v
    }, v.range = t(function (e, t, i, s) {
        var o = n.call(arguments), u = o[o.length - 1];
        if (typeof u == "string") {
            var s = u;
            o.pop()
        }
        o.length === 1 ? (t = o[0], i = 1, e = 0) : o.length == 2 && (i = 1), s = s || "number";
        var a = new r[s](Math.ceil((t - e) / i)), f = 0;
        for (var l = e; l < t; l += i)a[f++] = l;
        var c = new v;
        return c._array = a, c._shape = [a.length], c._dtype = s, c._size = a.length, c
    }), v.zeros = t(function (e, t) {
        var n = new v(t);
        return n.initFromShape(e), n
    }), v
}), define("echarts/chart/chord", ["require", "../util/shape/chord", "../component/base", "./calculableBase", "../util/ecData", "zrender/tool/util", "zrender/tool/vector", "../util/ndarray", "../chart"], function (e) {
    function n(n, r, i, s, o) {
        function F() {
            u.selectedMap = {}, w = [], b = null;
            var e = [], n = 0;
            for (var r = 0, s = g.length; r < s; r++)if (g[r].type === u.type) {
                b || (b = g[r], u.reformOption(b));
                var a = m(g[r].name);
                u.selectedMap[g[r].name] = a;
                if (!a)continue;
                w.push(g[r]), u.buildMark(g[r], r, o), e.push(g[r].matrix), n++
            }
            if (!b)return;
            if (!w.length)return;
            var f = i.getWidth(), l = i.getHeight(), c = Math.min(f, l);
            E = b.data, S = b.startAngle, S %= 360, S < 0 && (S += 360), x = b.clockWise, T = u.parsePercent(b.radius[0], c / 2), N = u.parsePercent(b.radius[1], c / 2), C = b.padding, k = b.sort, L = b.sortSub, O = b.showScale, M = b.showScaleText, A = [u.parsePercent(b.center[0], f), u.parsePercent(b.center[1], l)];
            var h = b.itemStyle.normal.chordStyle.lineStyle.width - b.itemStyle.normal.lineStyle.width;
            _ = h / t / T / Math.PI * 180, D = new p(e), D = D._transposelike([1, 2, 0]);
            var d = I(D, E);
            D = d[0], E = d[1];
            var v = D.shape();
            if (v[0] !== v[1] || v[0] !== E.length)throw new Error("Data not valid");
            if (v[0] === 0 || v[2] === 0)return;
            D.reshape(v[0], v[1] * v[2]);
            var y = D.sum(1), B = y.mul(1 / y.sum()), j = v[0], F = v[1] * v[2], W = B.mul(360 - C * j),
                X = D.div(D.sum(1).reshape(j, 1));
            X = X.mul(W.sub(_ * 2).reshape(j, 1));
            switch (k) {
                case"ascending":
                case"descending":
                    var V = W.argsort(0, k);
                    W.sort(0, k), y.sort(0, k);
                    break;
                default:
                    var V = p.range(v[0])
            }
            switch (L) {
                case"ascending":
                case"descending":
                    var $ = X.argsort(1, L);
                    X.sort(1, L);
                    break;
                default:
                    var $ = p.range(F).reshape(1, F).repeat(j, 0)
            }
            var J = V.toArray(), K = W.toArray(), Q = $.toArray(), G = X.toArray(), Y = y.toArray(), Z = [],
                et = (new p(j, F)).toArray(), tt = [], nt = 0, rt = 0;
            for (var r = 0; r < j; r++) {
                var it = J[r];
                tt[it] = Y[r], rt = nt + K[r], Z[it] = [nt, rt];
                var st = nt + _, ot = st;
                for (var ut = 0; ut < F; ut++) {
                    ot = st + G[it][ut];
                    var at = Q[it][ut];
                    et[it][at] = [st, ot], st = ot
                }
                nt = rt + C
            }
            H = (new p(j, j, n)).toArray(), P = [], q(Z, tt), et = (new p(et)).reshape(j, j, n, 2).toArray(), R(et, D.reshape(v).toArray());
            var d = z(tt);
            O && U(d[0], d[1], Z, (new p(d[0])).sum() / (360 - C * j))
        }

        function I(e, t) {
            var n = [], r = [];
            for (var i = 0; i < t.length; i++) {
                var s = t[i].name;
                u.selectedMap[s] = m(s), u.selectedMap[s] ? r.push(t[i]) : n.push(i)
            }
            n.length && (e = e["delete"](n, 0), e = e["delete"](n, 1));
            if (!e.size())return [e, r];
            n = [];
            var o = [], a = e.shape();
            e.reshape(a[0], a[1] * a[2]);
            var f = e.sum(1).toArray();
            e.reshape(a);
            for (var i = 0; i < r.length; i++)f[i] === 0 ? n.push(i) : o.push(r[i]);
            return n.length && (e = e["delete"](n, 0), e = e["delete"](n, 1)), [e, o]
        }

        function q(e, t) {
            function f(e) {
                return function () {
                    s && clearTimeout(s), s = setTimeout(function () {
                        for (var t = 0; t < n; t++) {
                            P[t].style.opacity = t === e ? 1 : .1, i.modShape(P[t].id, P[t]);
                            for (var s = 0; s < n; s++)for (var o = 0; o < r; o++) {
                                var u = H[t][s][o];
                                u && (u.style.opacity = t === e || s === e ? .5 : .03, i.modShape(u.id, u))
                            }
                        }
                        i.refresh()
                    }, 50)
                }
            }

            function c() {
                return function () {
                    s && clearTimeout(s), s = setTimeout(function () {
                        for (var e = 0; e < n; e++) {
                            P[e].style.opacity = 1, i.modShape(P[e].id, P[e]);
                            for (var t = 0; t < n; t++)for (var s = 0; s < r; s++) {
                                var o = H[e][t][s];
                                o && (o.style.opacity = .5, i.modShape(o.id, o))
                            }
                        }
                        i.refresh()
                    }, 50)
                }
            }

            var n = E.length, r = w.length, s, o = u.query(b, "itemStyle.normal.label.show"),
                a = u.query(b, "itemStyle.normal.label.color");
            for (var p = 0; p < n; p++) {
                var d = E[p], m = e[p], g = (x ? 360 - m[1] : m[0]) + S, C = (x ? 360 - m[0] : m[1]) + S, k = {
                    id: i.newShapeId(u.type),
                    shape: "sector",
                    zlevel: y,
                    style: {
                        x: A[0],
                        y: A[1],
                        r0: T,
                        r: N,
                        startAngle: g,
                        endAngle: C,
                        brushType: "fill",
                        opacity: 1,
                        color: v(d.name)
                    },
                    highlightStyle: {brushType: "fill"}
                };
                k.style.lineWidth = u.deepQuery([d, b], "itemStyle.normal.lineStyle.width"), k.highlightStyle.lineWidth = u.deepQuery([d, b], "itemStyle.emphasis.lineStyle.width"), k.style.strokeColor = u.deepQuery([d, b], "itemStyle.normal.lineStyle.color"), k.highlightStyle.strokeColor = u.deepQuery([d, b], "itemStyle.emphasis.lineStyle.color"), k.style.lineWidth > 0 && (k.style.brushType = "both"), k.highlightStyle.lineWidth > 0 && (k.highlightStyle.brushType = "both"), l.pack(k, w[0], 0, t[p], 0, d.name);
                if (o) {
                    var L = [g + C] / 2;
                    L %= 360;
                    var O = L <= 90 || L >= 270;
                    L = L * Math.PI / 180;
                    var _ = [Math.cos(L), -Math.sin(L)], D = M ? 45 : 20, B = h.scale([], _, N + D);
                    h.add(B, B, A);
                    var j = {
                        shape: "text",
                        id: i.newShapeId(u.type),
                        zlevel: y - 1,
                        hoverable: !1,
                        style: {x: B[0], y: B[1], text: d.name, textAlign: O ? "left" : "right", color: a}
                    };
                    j.style.textColor = u.deepQuery([d, b], "itemStyle.normal.label.textStyle.color") || "#fff", j.style.textFont = u.getFont(u.deepQuery([d, b], "itemStyle.normal.label.textStyle")), i.addShape(j), u.shapeList.push(j)
                }
                k.onmouseover = f(p), k.onmouseout = c(), u.shapeList.push(k), P.push(k), i.addShape(k)
            }
        }

        function R(e, t) {
            var n = e.length;
            if (!n)return;
            var r = e[0][0].length, s = b.itemStyle.normal.chordStyle.lineStyle,
                o = b.itemStyle.emphasis.chordStyle.lineStyle;
            for (var a = 0; a < n; a++)for (var f = 0; f < n; f++)for (var c = 0; c < r; c++) {
                if (H[f][a][c])continue;
                var h = e[a][f][c][0], p = e[f][a][c][0], d = e[a][f][c][1], m = e[f][a][c][1];
                if (h - m === 0 || p - m === 0) {
                    H[a][f][c] = null;
                    continue
                }
                var g;
                r === 1 ? d - h <= m - p ? g = v(E[a].name) : g = v(E[f].name) : g = v(w[c].name);
                var N = x ? h : 360 - d, C = x ? d : 360 - h, k = x ? p : 360 - m, L = x ? m : 360 - p, O = {
                    id: i.newShapeId(u.type),
                    shape: "chord",
                    zlevel: y,
                    style: {
                        center: A,
                        r: T,
                        source0: N - S,
                        source1: C - S,
                        target0: k - S,
                        target1: L - S,
                        brushType: "both",
                        opacity: .5,
                        color: g,
                        lineWidth: s.width,
                        strokeColor: s.color
                    },
                    highlightStyle: {brushType: "both", lineWidth: o.width, strokeColor: o.color}
                };
                l.pack(O, w[c], c, t[a][f][c], 0, E[a].name, E[f].name, t[f][a][c]), H[a][f][c] = O, u.shapeList.push(O), i.addShape(O)
            }
        }

        function U(e, t, n, r) {
            for (var s = 0; s < n.length; s++) {
                var o = n[s][0], a = n[s][1], f = o;
                while (f < a) {
                    var l = ((x ? 360 - f : f) + S) / 180 * Math.PI, c = [Math.cos(l), -Math.sin(l)],
                        d = h.scale([], c, N + 1);
                    h.add(d, d, A);
                    var v = h.scale([], c, N + B);
                    h.add(v, v, A);
                    var m = {
                        shape: "line",
                        id: i.newShapeId(u.type),
                        zlevel: y - 1,
                        hoverable: !1,
                        style: {
                            xStart: d[0],
                            yStart: d[1],
                            xEnd: v[0],
                            yEnd: v[1],
                            lineCap: "round",
                            brushType: "stroke",
                            strokeColor: "#666"
                        }
                    };
                    u.shapeList.push(m), i.addShape(m), f += j
                }
                if (!M)continue;
                var g = o, b = r * 5 * j, w = p.range(0, e[s], b).toArray();
                while (g < a) {
                    var l = x ? 360 - g : g;
                    l = (l + S) % 360;
                    var E = l <= 90 || l >= 270, T = {
                        shape: "text",
                        id: i.newShapeId(u.type),
                        zlevel: y - 1,
                        hoverable: !1,
                        style: {
                            x: E ? N + B + 4 : -N - B - 4,
                            y: 0,
                            text: Math.round(w.shift() * 10) / 10 + t,
                            textAlign: E ? "left" : "right"
                        },
                        position: A.slice(),
                        rotation: E ? [l / 180 * Math.PI, 0, 0] : [(l + 180) / 180 * Math.PI, 0, 0]
                    };
                    u.shapeList.push(T), i.addShape(T), g += j * 5
                }
            }
        }

        function z(e) {
            var t = [], n = (new p(e)).max(), r, i;
            n > 1e4 ? (r = "k", i = .001) : n > 1e7 ? (r = "m", i = 1e-6) : n > 1e10 ? (r = "b", i = 1e-9) : (r = "", i = 1);
            for (var s = 0; s < e.length; s++)t[s] = e[s] * i;
            return [t, r]
        }

        function W(e, t) {
            o = t, X(e)
        }

        function X(e) {
            e && (s = e, g = s.series), u.clear(), d = o.legend;
            if (d) v = d.getColor, m = d.isSelected; else {
                var t = {}, n = {}, r = 0;
                v = function (e) {
                    if (n[e])return n[e];
                    t[e] === undefined && (t[e] = r++);
                    for (var s = 0; s < w.length; s++)if (w[s].name === e) {
                        n[e] = u.query(w[s], "itemStyle.normal.color");
                        break
                    }
                    if (!n[e]) {
                        var o = E.length;
                        for (var s = 0; s < o; s++)if (E[s].name === e) {
                            n[e] = u.query(E[s], "itemStyle.normal.color");
                            break
                        }
                    }
                    return n[e] || (n[e] = i.getColor(t[e])), n[e]
                }, m = function () {
                    return !0
                }
            }
            F()
        }

        function V(e) {
            var t = c.merge;
            e = t(e || {}, n.chord, {
                overwrite: !1,
                recursive: !0
            }), e.itemStyle.normal.label.textStyle = t(e.itemStyle.normal.label.textStyle || {}, n.textStyle, {
                overwrite: !1,
                recursive: !0
            })
        }

        var u = this, a = e("../component/base");
        a.call(this, n, i);
        var f = e("./calculableBase");
        f.call(this, i, s);
        var l = e("../util/ecData"), c = e("zrender/tool/util"), h = e("zrender/tool/vector"), p = e("../util/ndarray"),
            d, v, m, g;
        this.type = n.CHART_TYPE_CHORD;
        var y = u.getZlevelBase(), b, w = [], E, S, x, T, N, C, k, L, A, O, M, _ = 0, D, P = [], H = [], B = 4, j = 4;
        u.init = W, u.refresh = X, u.reformOption = V, W(s, o)
    }

    e("../util/shape/chord");
    var t = window.devicePixelRatio || 1;
    return e("../chart").define("chord", n), n
}), define("echarts/chart/force", ["require", "../component/base", "./calculableBase", "../util/ecData", "zrender/config", "zrender/tool/event", "zrender/tool/util", "zrender/tool/vector", "../util/ndarray", "../chart"], function (e) {
    function r(r, o, u, a, f) {
        function Y() {
            y = f.legend, I = 1, $ = u.getWidth(), J = u.getHeight(), K = [$ / 2, J / 2];
            var e;
            for (var t = 0, n = w.length; t < n; t++) {
                var i = w[t];
                if (i.type === r.CHART_TYPE_FORCE) {
                    w[t] = b.reformOption(w[t]), e = w[t].name || "", b.selectedMap[e] = y ? y.isSelected(e) : !0;
                    if (!b.selectedMap[e])continue;
                    b.buildMark(w[t], t, f), E = i;
                    var s = b.query(i, "minRadius"), o = b.query(i, "maxRadius");
                    X = b.query(i, "attractiveness"), R = b.query(i, "density"), U = b.query(i, "initSize"), W = b.query(i, "centripetal"), z = b.query(i, "coolDown"), T = b.query(i, "categories");
                    for (var a = 0, l = T.length; a < l; a++)T[a].name && (y ? b.selectedMap[a] = y.isSelected(T[a].name) : b.selectedMap[a] = !0);
                    k = b.query(i, "itemStyle.normal.linkStyle"), L = b.query(i, "itemStyle.emphasis.linkStyle"), N = b.query(i, "itemStyle.normal.nodeStyle"), C = b.query(i, "itemStyle.emphasis.nodeStyle"), A = b.query(i, "nodes"), O = v.clone(b.query(i, "links")), Z(A, O), B = [], j = [], F = [], D = [], P = [], F = [], S = [], x = [];
                    var c = $ * J;
                    q = .5 / X * Math.sqrt(c / M.length), tt(M, _), et(M, s, o)
                }
            }
        }

        function Z(e, t) {
            var n = [], r = 0;
            M = s(e, function (e, t) {
                if (!e)return;
                if (e.ignore)return;
                if (b.selectedMap[e.category])return n[t] = r++, !0;
                n[t] = -1
            });
            var i, o, u;
            _ = s(t, function (e, t) {
                return i = e.source, o = e.target, u = !0, n[i] >= 0 ? e.source = n[i] : u = !1, n[o] >= 0 ? e.target = n[o] : u = !1, e.rawIndex = t, u
            })
        }

        function et(e, t, r) {
            var s = [], o = e.length;
            for (var a = 0; a < o; a++) {
                var f = e[a];
                f.value !== undefined ? s.push(f.value) : s.push(1)
            }
            var l = new g(s);
            s = l.map(t, r).toArray();
            var c = l.max();
            c !== 0 && (D = l.mul(1 / c, l).toArray());
            for (var a = 0; a < o; a++) {
                var f = e[a], p, d, w = s[a], x;
                f.initial !== undefined ? x = f.initial : n[f.name] !== undefined ? x = n[f.name] : x = i($ / 2, J / 2, U);
                var p = x[0], d = x[1];
                B[a] = m.create(p, d), j[a] = m.create(p, d), H[a] = m.create(0, 0), F[a] = w * w * R * .035;
                var k = {
                    id: u.newShapeId(b.type),
                    shape: "circle",
                    style: {r: w, x: 0, y: 0},
                    clickable: !0,
                    highlightStyle: {},
                    position: [p, d],
                    __forceIndex: a
                }, L;
                b.query(E, "itemStyle.normal.label.show") && (k.style.text = f.name, k.style.textPosition = "inside", L = b.query(E, "itemStyle.normal.label.textStyle") || {}, k.style.textColor = L.color || "#fff", k.style.textAlign = L.align || "center", k.style.textBaseline = L.baseline || "middle", k.style.textFont = b.getFont(L)), b.query(E, "itemStyle.emphasis.label.show") && (k.highlightStyle.text = f.name, k.highlightStyle.textPosition = "inside", L = b.query(E, "itemStyle.emphasis.label.textStyle") || {}, k.highlightStyle.textColor = L.color || "#fff", k.highlightStyle.textAlign = L.align || "center", k.highlightStyle.textBaseline = L.baseline || "middle", k.highlightStyle.textFont = b.getFont(L)), v.merge(k.style, N), v.merge(k.highlightStyle, C);
                if (typeof f.category != "undefined") {
                    var O = T[f.category];
                    if (O) {
                        y && (k.style.color = y.getColor(O.name));
                        var M = O.itemStyle;
                        M && (M.normal && v.merge(k.style, M.normal, {overwrite: !0}), M.emphasis && v.merge(k.highlightStyle, M.emphasis, {overwrite: !0}))
                    }
                }
                if (typeof f.itemStyle != "undefined") {
                    var M = f.itemStyle;
                    M.normal && v.merge(k.style, M.normal, {overwrite: !0}), M.normal && v.merge(k.highlightStyle, M.emphasis, {overwrite: !0})
                }
                b.setCalculable(k), k.dragEnableTime = 0, k.ondragstart = b.shapeHandler.ondragstart, k.draggable = !0, S.push(k), b.shapeList.push(k);
                var _ = "";
                if (typeof f.category != "undefined") {
                    var O = T[f.category];
                    _ = O && O.name || ""
                }
                h.pack(k, {name: _}, 0, f, v.indexOf(A, f), f.name || "", f.value), u.addShape(k)
            }
        }

        function tt(e, t) {
            var n = t.length;
            for (var r = 0; r < n; r++) {
                var i = t[r];
                i.weight !== undefined ? P.push(i.weight) : P.push(1);
                var s = {
                    id: u.newShapeId(b.type),
                    shape: "line",
                    style: {xStart: 0, yStart: 0, xEnd: 0, yEnd: 0, lineWidth: 1},
                    clickable: !0,
                    highlightStyle: {}
                };
                v.merge(s.style, k), v.merge(s.highlightStyle, L), typeof i.itemStyle != "undefined" && (i.itemStyle.normal && v.merge(s.style, i.itemStyle.normal, {overwrite: !0}), i.itemStyle.emphasis && v.merge(s.highlightStyle, i.itemStyle.emphasis, {overwrite: !0})), x.push(s), b.shapeList.push(s);
                var o = M[i.source], a = M[i.target], i = O[i.rawIndex];
                h.pack(s, E, 0, {
                    source: i.source,
                    target: i.target,
                    weight: i.weight || 0
                }, i.rawIndex, o.name + " - " + a.name, i.weight || 0, !0), u.addShape(s)
            }
            var f = new g(P), l = f.max();
            l !== 0 && (P = f.mul(1 / l, f).toArray())
        }

        function nt() {
            for (var e = 0, t = _.length; e < t; e++) {
                var n = _[e], r = x[e], i = S[n.source], s = S[n.target];
                r.style.xStart = i.position[0], r.style.yStart = i.position[1], r.style.xEnd = s.position[0], r.style.yEnd = s.position[1]
            }
        }

        function rt(e) {
            var t = B.length, r = [], i = q * q;
            for (var s = 0; s < t; s++)H[s][0] = 0, H[s][1] = 0;
            for (var s = 0; s < t; s++)for (var o = s + 1; o < t; o++) {
                var u = D[s], a = D[o], f = B[s], l = B[o];
                m.sub(r, l, f);
                var c = m.length(r);
                if (c > 500)continue;
                c < 5 && (c = 5), m.scale(r, r, 1 / c);
                var h = 1 * (u + a) * i / c;
                m.scaleAndAdd(H[s], H[s], r, -h), m.scaleAndAdd(H[o], H[o], r, h)
            }
            for (var s = 0, p = _.length; s < p; s++) {
                var d = _[s], v = P[s], g = d.source, y = d.target, f = B[g], l = B[y];
                m.sub(r, l, f);
                var b = m.lengthSquare(r);
                if (b === 0)continue;
                var h = v * b / q / Math.sqrt(b);
                m.scaleAndAdd(H[g], H[g], r, h), m.scaleAndAdd(H[y], H[y], r, -h)
            }
            for (var s = 0, p = M.length; s < p; s++) {
                var w = B[s];
                m.sub(r, K, w);
                var b = m.lengthSquare(r), h = b * W / (100 * Math.sqrt(b));
                m.scaleAndAdd(H[s], H[s], r, h)
            }
            var E = [];
            for (var s = 0, p = B.length; s < p; s++) {
                var x = M[s].name;
                if (M[s].fixed) {
                    m.set(B[s], Q, G), m.set(j[s], Q, G), m.set(S[s].position, Q, G), M[s].initial !== undefined && m.set(M[s].initial, Q, G), n[x] !== undefined && m.set(n[x], Q, G);
                    continue
                }
                var w = B[s], T = j[s];
                m.sub(E, w, T), T[0] = w[0], T[1] = w[1], m.scaleAndAdd(E, E, H[s], e / F[s]), m.scale(E, E, I), E[0] = Math.max(Math.min(E[0], 100), -100), E[1] = Math.max(Math.min(E[1], 100), -100), m.add(w, w, E), m.copy(S[s].position, w), x ? (n[x] === undefined && (n[x] = m.create()), m.copy(n[x], w)) : (M[s].initial === undefined && (M[s].initial = m.create()), m.copy(M[s].initial, w))
            }
        }

        function it() {
            if (I < .01)return;
            rt(V), nt();
            var e = {};
            for (var t = 0; t < S.length; t++) {
                var n = S[t];
                e.position = n.position, u.modShape(n.id, e, !0)
            }
            e = {};
            for (var t = 0; t < x.length; t++) {
                var n = x[t];
                e.style = n.style, u.modShape(n.id, e, !0)
            }
            u.refresh(), I *= z
        }

        function ot(e, n) {
            function r() {
                st && (it(), t(r))
            }

            a = e, f = n, w = a.series, b.clear(), Y(), st = !0, t(r)
        }

        function ut(e) {
            e && (a = e, w = a.series), b.clear(), Y(), I = 1
        }

        function at() {
            st = !1
        }

        function ft() {
        }

        function lt(e) {
            if (!b.isDragstart || !e.target)return;
            var t = e.target, n = t.__forceIndex, r = M[n];
            r.fixed = !0, b.isDragstart = !1, u.on(p.EVENT.MOUSEMOVE, ht)
        }

        function ct(e, t) {
            if (!b.isDragend || !e.target)return;
            var n = e.target, r = n.__forceIndex, i = M[r];
            i.fixed = !1, t.dragIn = !0, t.needRefresh = !1, b.isDragend = !1, u.un(p.EVENT.MOUSEMOVE, ht)
        }

        function ht(e) {
            I = .8, Q = d.getX(e.event), G = d.getY(e.event)
        }

        var l = e("../component/base");
        l.call(this, r, u);
        var c = e("./calculableBase");
        c.call(this, u, a);
        var h = e("../util/ecData"), p = e("zrender/config"), d = e("zrender/tool/event"), v = e("zrender/tool/util"),
            m = e("zrender/tool/vector"), g = e("../util/ndarray"), y, b = this;
        b.type = r.CHART_TYPE_FORCE;
        var w, E, S = [], x = [], T = [], N, C, k, L, A, O, M = [], _ = [], D = [], P = [], H = [], B = [], j = [],
            F = [], I, q, R, U, z, W, X, V = 1 / 60, $, J, K = [], Q, G, st;
        b.shapeHandler.ondragstart = function () {
            b.isDragstart = !0
        }, b.init = ot, b.refresh = ut, b.ondragstart = lt, b.ondragend = ct, b.dispose = at, b.onclick = ft, ot(a, f)
    }

    function i(e, t, n) {
        return [(Math.random() - .5) * n + e, (Math.random() - .5) * n + t]
    }

    function s(e, t) {
        var n = e.length, r = [];
        for (var i = 0; i < n; i++)t(e[i], i) && r.push(e[i]);
        return r
    }

    var t = window.requestAnimationFrame || window.msRequestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || function (e) {
            setTimeout(e, 16)
        }, n = {};
    return e("../chart").define("force", r), r
}), define("echarts/util/mapData/params", ["require"], function (e) {
    function t(e) {
        if (!e.UTF8Encoding)return e;
        var t = e.features;
        for (var r = 0; r < t.length; r++) {
            var i = t[r], s = i.geometry.coordinates, o = i.geometry.encodeOffsets;
            for (var u = 0; u < s.length; u++) {
                var a = s[u];
                if (i.geometry.type === "Polygon") s[u] = n(a, o[u]); else if (i.geometry.type === "MultiPolygon")for (var f = 0; f < a.length; f++) {
                    var l = a[f];
                    a[f] = n(l, o[u][f])
                }
            }
        }
        return e.UTF8Encoding = !1, e
    }

    function n(e, t) {
        var n = [], r = t[0], i = t[1];
        for (var s = 0; s < e.length; s += 2) {
            var o = e.charCodeAt(s) - 64, u = e.charCodeAt(s + 1) - 64;
            o = o >> 1 ^ -(o & 1), u = u >> 1 ^ -(u & 1), o += r, u += i, r = o, i = u, n.push([o / 1024, u / 1024])
        }
        return n
    }

    var r = {
        world: {
            getGeoJson: function (n) {
                e(["./geoJson/world_geo"], function (e) {
                    n(t(e))
                })
            }
        }, china: {
            getGeoJson: function (n) {
                e(["./geoJson/china_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "南海诸岛": {
            textCoord: [126, 25], getPath: function (e, t) {
                var n = [[[0, 3.5], [7, 11.2], [15, 11.9], [30, 7], [42, .7], [52, .7], [56, 7.7], [59, .7], [64, .7], [64, 0], [5, 0], [0, 3.5]], [[13, 16.1], [19, 14.7], [16, 21.7], [11, 23.1], [13, 16.1]], [[12, 32.2], [14, 38.5], [15, 38.5], [13, 32.2], [12, 32.2]], [[16, 47.6], [12, 53.2], [13, 53.2], [18, 47.6], [16, 47.6]], [[6, 64.4], [8, 70], [9, 70], [8, 64.4], [6, 64.4]], [[23, 82.6], [29, 79.8], [30, 79.8], [25, 82.6], [23, 82.6]], [[37, 70.7], [43, 62.3], [44, 62.3], [39, 70.7], [37, 70.7]], [[48, 51.1], [51, 45.5], [53, 45.5], [50, 51.1], [48, 51.1]], [[51, 35], [51, 28.7], [53, 28.7], [53, 35], [51, 35]], [[52, 22.4], [55, 17.5], [56, 17.5], [53, 22.4], [52, 22.4]], [[58, 12.6], [62, 7], [63, 7], [60, 12.6], [58, 12.6]], [[0, 3.5], [0, 93.1], [64, 93.1], [64, 0], [63, 0], [63, 92.4], [1, 92.4], [1, 3.5], [0, 3.5]]],
                    r = "", i = e[0], s = e[1];
                for (var o = 0, u = n.length; o < u; o++) {
                    r += "M " + ((n[o][0][0] * t + i).toFixed(2) - 0) + " " + ((n[o][0][1] * t + s).toFixed(2) - 0) + " ";
                    for (var a = 1, f = n[o].length; a < f; a++)r += "L " + ((n[o][a][0] * t + i).toFixed(2) - 0) + " " + ((n[o][a][1] * t + s).toFixed(2) - 0) + " "
                }
                return r + " Z"
            }
        }, "新疆": {
            getGeoJson: function (n) {
                e(["./geoJson/xin_jiang_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "西藏": {
            getGeoJson: function (n) {
                e(["./geoJson/xi_zang_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "内蒙古": {
            getGeoJson: function (n) {
                e(["./geoJson/nei_meng_gu_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "青海": {
            getGeoJson: function (n) {
                e(["./geoJson/qing_hai_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "四川": {
            getGeoJson: function (n) {
                e(["./geoJson/si_chuan_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "黑龙江": {
            getGeoJson: function (n) {
                e(["./geoJson/hei_long_jiang_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "甘肃": {
            getGeoJson: function (n) {
                e(["./geoJson/gan_su_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "云南": {
            getGeoJson: function (n) {
                e(["./geoJson/yun_nan_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "广西": {
            getGeoJson: function (n) {
                e(["./geoJson/guang_xi_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "湖南": {
            getGeoJson: function (n) {
                e(["./geoJson/hu_nan_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "陕西": {
            getGeoJson: function (n) {
                e(["./geoJson/shan_xi_1_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "广东": {
            getGeoJson: function (n) {
                e(["./geoJson/guang_dong_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "吉林": {
            getGeoJson: function (n) {
                e(["./geoJson/ji_lin_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "河北": {
            getGeoJson: function (n) {
                e(["./geoJson/he_bei_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "湖北": {
            getGeoJson: function (n) {
                e(["./geoJson/hu_bei_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "贵州": {
            getGeoJson: function (n) {
                e(["./geoJson/gui_zhou_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "山东": {
            getGeoJson: function (n) {
                e(["./geoJson/shan_dong_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "江西": {
            getGeoJson: function (n) {
                e(["./geoJson/jiang_xi_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "河南": {
            getGeoJson: function (n) {
                e(["./geoJson/he_nan_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "辽宁": {
            getGeoJson: function (n) {
                e(["./geoJson/liao_ning_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "山西": {
            getGeoJson: function (n) {
                e(["./geoJson/shan_xi_2_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "安徽": {
            getGeoJson: function (n) {
                e(["./geoJson/an_hui_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "福建": {
            getGeoJson: function (n) {
                e(["./geoJson/fu_jian_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "浙江": {
            getGeoJson: function (n) {
                e(["./geoJson/zhe_jiang_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "江苏": {
            getGeoJson: function (n) {
                e(["./geoJson/jiang_su_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "重庆": {
            getGeoJson: function (n) {
                e(["./geoJson/chong_qing_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "宁夏": {
            getGeoJson: function (n) {
                e(["./geoJson/ning_xia_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "海南": {
            getGeoJson: function (n) {
                e(["./geoJson/hai_nan_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "台湾": {
            getGeoJson: function (n) {
                e(["./geoJson/tai_wan_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "北京": {
            getGeoJson: function (n) {
                e(["./geoJson/bei_jing_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "天津": {
            getGeoJson: function (n) {
                e(["./geoJson/tian_jin_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "上海": {
            getGeoJson: function (n) {
                e(["./geoJson/shang_hai_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "香港": {
            getGeoJson: function (n) {
                e(["./geoJson/xiang_gang_geo"], function (e) {
                    n(t(e))
                })
            }
        }, "澳门": {
            getGeoJson: function (n) {
                e(["./geoJson/ao_men_geo"], function (e) {
                    n(t(e))
                })
            }
        }
    };
    return {decode: t, params: r}
}), define("echarts/util/mapData/textFixed", [], function () {
    return {
        "广东": [0, -10],
        "香港": [10, 10],
        "澳门": [-10, 18],
        "黑龙江": [0, 20],
        "天津": [5, 5],
        "深圳市": [-35, 0],
        "红河哈尼族彝族自治州": [0, 20],
        "楚雄彝族自治州": [-5, 15],
        "石河子市": [-5, 5],
        "五家渠市": [0, -10],
        "昌吉回族自治州": [10, 10],
        "昌江黎族自治县": [0, 20],
        "陵水黎族自治县": [0, 20],
        "东方市": [0, 20],
        "渭南市": [0, 20]
    }
}), define("echarts/util/mapData/geoCoord", [], function () {
    return {Russia: [100, 60], "United States of America": [-99, 38]}
}), define("echarts/util/projection/normal", [], function () {
    function e(e, n) {
        return n = n || {}, e.srcSize || t(e, n), e.srcSize
    }

    function t(e, t) {
        t = t || {}, r.xmin = 360, r.xmax = -360, r.ymin = 180, r.ymax = -180;
        var n = e.features, i, s;
        for (var o = 0, u = n.length; o < u; o++) {
            s = n[o];
            if (s.properties.name && t[s.properties.name])continue;
            if (s.type == "Feature") r[s.geometry.type](s.geometry.coordinates); else if (s.type == "GeometryCollection") {
                i = s.geometries;
                for (var a = 0, f = i.length; a < f; a++)r[i[a].type](i[a].coordinates)
            }
        }
        return e.srcSize = {
            left: r.xmin.toFixed(4) * 1,
            top: r.ymin.toFixed(4) * 1,
            width: (r.xmax - r.xmin).toFixed(4) * 1,
            height: (r.ymax - r.ymin).toFixed(4) * 1
        }, e
    }

    function i(e, r, i) {
        function g(e, t) {
            d = e.type, v = e.coordinates, n._bbox = {
                xmin: 360,
                xmax: -360,
                ymin: 180,
                ymax: -180
            }, m = n[d](v), u.push({
                path: m,
                cp: t.properties.cp ? n.makePoint(t.properties.cp) : n.makePoint([(n._bbox.xmin + n._bbox.xmax) / 2, (n._bbox.ymin + n._bbox.ymax) / 2]),
                properties: t.properties,
                id: t.id
            })
        }

        i = i || {}, n.scale = null, n.offset = null, e.srcSize || t(e, i), r.offset = {
            x: e.srcSize.left,
            y: e.srcSize.top,
            left: r.OffsetLeft || 0,
            top: r.OffsetTop || 0
        }, n.scale = r.scale, n.offset = r.offset;
        var s = e.features, o, u = [], a, f;
        for (var l = 0, c = s.length; l < c; l++) {
            f = s[l];
            if (f.properties.name && i[f.properties.name])continue;
            if (f.type == "Feature") g(f.geometry, f); else if (f.type == "GeometryCollection") {
                o = f.geometries;
                for (var h = 0, p = o.length; h < p; h++)a = o[h], g(a, a)
            }
        }
        var d, v, m;
        return u
    }

    function s(e, t) {
        var n, r;
        return t instanceof Array ? (n = t[0] * 1, r = t[1] * 1) : (n = t.x * 1, r = t.y * 1), n = n / e.scale.x + e.offset.x - 168.5, n = n > 180 ? n - 360 : n, r = 90 - (r / e.scale.y + e.offset.y), [n, r]
    }

    function o(e, t) {
        return n.offset = e.offset, n.scale = e.scale, t instanceof Array ? n.makePoint([t[0] * 1, t[1] * 1]) : n.makePoint([t.x * 1, t.y * 1])
    }

    var n = {
        formatPoint: function (e) {
            return [(e[0] < -168.5 && e[1] > 63.8 ? e[0] + 360 : e[0]) + 168.5, 90 - e[1]]
        }, makePoint: function (e) {
            var t = this, r = t.formatPoint(e);
            t._bbox.xmin > e[0] && (t._bbox.xmin = e[0]), t._bbox.xmax < e[0] && (t._bbox.xmax = e[0]), t._bbox.ymin > e[1] && (t._bbox.ymin = e[1]), t._bbox.ymax < e[1] && (t._bbox.ymax = e[1]);
            var i = (r[0] - n.offset.x) * n.scale.x + n.offset.left, s = (r[1] - n.offset.y) * n.scale.y + n.offset.top;
            return [i, s]
        }, Point: function (e) {
            return e = this.makePoint(e), e.join(",")
        }, LineString: function (e) {
            var t = "", r;
            for (var i = 0, s = e.length; i < s; i++)r = n.makePoint(e[i]), i === 0 ? t = "M" + r.join(",") : t = t + "L" + r.join(",");
            return t
        }, Polygon: function (e) {
            var t = "";
            for (var r = 0, i = e.length; r < i; r++)t = t + n.LineString(e[r]) + "z";
            return t
        }, MultiPoint: function (e) {
            var t = [];
            for (var r = 0, i = e.length; r < i; r++)t.push(n.Point(e[r]));
            return t
        }, MultiLineString: function (e) {
            var t = "";
            for (var r = 0, i = e.length; r < i; r++)t += n.LineString(e[r]);
            return t
        }, MultiPolygon: function (e) {
            var t = "";
            for (var r = 0, i = e.length; r < i; r++)t += n.Polygon(e[r]);
            return t
        }
    }, r = {
        formatPoint: n.formatPoint, makePoint: function (e) {
            var t = this, n = t.formatPoint(e), r = n[0], i = n[1];
            t.xmin > r && (t.xmin = r), t.xmax < r && (t.xmax = r), t.ymin > i && (t.ymin = i), t.ymax < i && (t.ymax = i)
        }, Point: function (e) {
            this.makePoint(e)
        }, LineString: function (e) {
            for (var t = 0, n = e.length; t < n; t++)this.makePoint(e[t])
        }, Polygon: function (e) {
            for (var t = 0, n = e.length; t < n; t++)this.LineString(e[t])
        }, MultiPoint: function (e) {
            for (var t = 0, n = e.length; t < n; t++)this.Point(e[t])
        }, MultiLineString: function (e) {
            for (var t = 0, n = e.length; t < n; t++)this.LineString(e[t])
        }, MultiPolygon: function (e) {
            for (var t = 0, n = e.length; t < n; t++)this.Polygon(e[t])
        }
    };
    return {getBbox: e, geoJson2Path: i, pos2geo: s, geo2pos: o}
}), define("echarts/chart/map", ["require", "../component/base", "./calculableBase", "../util/ecData", "zrender/config", "zrender/tool/util", "zrender/tool/event", "../util/mapData/params", "../util/mapData/textFixed", "../util/mapData/geoCoord", "../util/projection/normal", "../util/projection/normal", "../util/projection/normal", "../util/projection/normal", "../chart"], function (e) {
    function t(t, n, r, i, s) {
        function j() {
            h.selectedMap = {};
            var e = s.legend, n, r = {}, i, o, u, a = {}, f = {};
            v = {}, m = {}, g = {};
            var c = {};
            O = !1;
            for (var d = 0, y = p.length; d < y; d++)if (p[d].type == t.CHART_TYPE_MAP) {
                p[d] = h.reformOption(p[d]), i = p[d].mapType, a[i] = a[i] || {}, a[i][d] = !0, f[i] = f[i] || p[d].mapValuePrecision, A[i] = p[d].roam || A[i], O = O || A[i], S[i] = p[d].nameMap || S[i] || {}, p[d].textFixed && l.mergeFast(k, p[d].textFixed, !0, !1), p[d].geoCoord && l.mergeFast(L, p[d].geoCoord, !0, !1), v[i] = v[i] || p[d].selectedMode;
                if (typeof m[i] == "undefined" || m[i]) m[i] = p[d].hoverable;
                if (typeof g[i] == "undefined" || g[i]) g[i] = p[d].showLegendSymbol;
                c[i] = c[i] || p[d].mapValueCalculation, n = p[d].name, h.selectedMap[n] = e ? e.isSelected(n) : !0;
                if (h.selectedMap[n]) {
                    r[i] = r[i] || {}, o = p[d].data;
                    for (var b = 0, E = o.length; b < E; b++) {
                        u = $(i, o[b].name), r[i][u] = r[i][u] || {seriesIndex: []};
                        for (var T in o[b])T != "value" ? r[i][u][T] = o[b][T] : isNaN(o[b].value) || (typeof r[i][u].value == "undefined" && (r[i][u].value = 0), r[i][u].value += o[b].value);
                        r[i][u].seriesIndex.push(d)
                    }
                }
            }
            N = 0;
            for (var M in r)N++;
            for (var M in r) {
                if (c[M] && c[M] == "average")for (var E in r[M])r[M][E].value = (r[M][E].value / r[M][E].seriesIndex.length).toFixed(f[M]) - 0;
                w[M] = w[M] || {}, w[M].mapData ? F(M, r[M], a[M])(w[M].mapData) : C[M.replace(/\|.*/, "")].getGeoJson && (x[M] = C[M.replace(/\|.*/, "")].specialArea || x[M], C[M.replace(/\|.*/, "")].getGeoJson(F(M, r[M], a[M])))
            }
        }

        function F(e, t, n) {
            return function (s) {
                if (!h)return;
                e.indexOf("|") != -1 && (s = I(e, s)), w[e].mapData = s, W(e, q(e, s, n), t, n), X(e, n);
                if (--N <= 0) {
                    E = {};
                    for (var o = 0, u = h.shapeList.length; o < u; o++)h.shapeList[o].id = r.newShapeId(h.type), r.addShape(h.shapeList[o]), h.shapeList[o].shape == "path" && typeof h.shapeList[o].style._text != "undefined" && (E[h.shapeList[o].style._text] = h.shapeList[o]);
                    r.refresh(), B ? h.animationEffect() : (B = !0, i.animation && !i.renderAsImage && h.animationMark(i.animationDuration))
                }
            }
        }

        function I(e, t) {
            var n = e.replace(/^.*\|/, ""), r = t.features;
            for (var i = 0, s = r.length; i < s; i++)if (r[i].properties && r[i].properties.name == n) {
                r = r[i], n == "United States of America" && r.geometry.coordinates.length > 1 && (r = {
                    geometry: {
                        coordinates: r.geometry.coordinates.slice(5, 6),
                        type: r.geometry.type
                    }, id: r.id, properties: r.properties, type: r.type
                });
                break
            }
            return {type: "FeatureCollection", features: [r]}
        }

        function q(t, n, r) {
            var i = e("../util/projection/normal"), s = [], o = w[t].bbox || i.getBbox(n, x[t]), u;
            w[t].hasRoam ? u = w[t].transform : u = z(o, r);
            var a = w[t].lastTransform || {scale: {}}, f;
            u.left != a.left || u.top != a.top || u.scale.x != a.scale.x || u.scale.y != a.scale.y ? (f = i.geoJson2Path(n, u, x[t]), a = l.clone(u)) : (u = w[t].transform, f = w[t].pathArray), w[t].bbox = o, w[t].transform = u, w[t].lastTransform = a, w[t].pathArray = f;
            var c = [u.left, u.top];
            for (var h = 0, p = f.length; h < p; h++)s.push(U(t, f[h], c));
            if (x[t])for (var d in x[t])s.push(R(t, n, d, x[t][d], c));
            if (t == "china") {
                var v = ot(t, L["南海诸岛"] || C["南海诸岛"].textCoord), m = u.scale.x / 10.5,
                    g = [32 * m + v[0], 83 * m + v[1]];
                k["南海诸岛"] && (g[0] += k["南海诸岛"][0], g[1] += k["南海诸岛"][1]), s.push({
                    text: $(t, "南海诸岛"),
                    path: C["南海诸岛"].getPath(v, m),
                    position: c,
                    textX: g[0],
                    textY: g[1]
                })
            }
            return s
        }

        function R(t, n, r, i, s) {
            n = I("x|" + r, n);
            var o = e("../util/projection/normal"), u = o.getBbox(n), a = ot(t, [i.left, i.top]),
                f = ot(t, [i.left + i.width, i.top + i.height]), l = Math.abs(f[0] - a[0]), c = Math.abs(f[1] - a[1]),
                h = u.width, p = u.height, d = l / .75 / h, v = c / p;
            d > v ? (d = v * .75, l = h * d) : (v = d, d = v * .75, c = p * v);
            var m = {OffsetLeft: a[0], OffsetTop: a[1], scale: {x: d, y: v}}, g = o.geoJson2Path(n, m);
            return U(t, g[0], s)
        }

        function U(e, t, n) {
            var r, i = t.properties.name;
            return L[i] ? r = ot(e, L[i]) : t.cp && (r = [t.cp[0], t.cp[1]]), k[i] && (r[0] += k[i][0], r[1] += k[i][1]), {
                text: $(e, i),
                path: t.path,
                position: n,
                textX: r[0],
                textY: r[1]
            }
        }

        function z(e, t) {
            var n, i, s, o, u, a, f, l = r.getWidth(), c = r.getHeight(), d = Math.round(Math.min(l, c) * .02);
            for (var v in t)n = p[v].mapLocation, s = n.x || s, u = n.y || u, a = n.width || a, f = n.height || f;
            i = h.parsePercent(s, l), i = isNaN(i) ? d : i, o = h.parsePercent(u, c), o = isNaN(o) ? d : o, typeof a == "undefined" ? a = isNaN(s) ? l - 2 * d : l - i - 2 * d : a = h.parsePercent(a, l), typeof f == "undefined" ? f = isNaN(u) ? c - 2 * d : c - o - 2 * d : f = h.parsePercent(f, c);
            var m = e.width, g = e.height, y = a / .75 / m, b = f / g;
            y > b ? (y = b * .75, a = m * y) : (b = y, y = b * .75, f = g * b);
            if (isNaN(s))switch (s + "") {
                case"center":
                    i = Math.floor((l - a) / 2);
                    break;
                case"right":
                    i = l - a;
                    break;
                default:
            }
            if (isNaN(u))switch (u + "") {
                case"center":
                    o = Math.floor((c - f) / 2);
                    break;
                case"bottom":
                    o = c - f;
                    break;
                default:
            }
            return {left: i, top: o, width: a, height: f, scale: {x: y, y: b}}
        }

        function W(e, n, r, i) {
            var o = s.legend, u = s.dataRange, f, c, w, E, S, x = t.map, T, N, C, k, L, A;
            for (var O = 0, M = n.length; O < M; O++) {
                C = l.clone(n[O]), k = l.clone(C), c = C.text, w = r[c];
                if (w) {
                    S = [w], f = "";
                    for (var _ = 0, D = w.seriesIndex.length; _ < D; _++)S.push(p[w.seriesIndex[_]]), f += p[w.seriesIndex[_]].name + " ", o && g[e] && o.hasColor(p[w.seriesIndex[_]].name) && h.shapeList.push({
                        shape: "circle",
                        zlevel: d + 1,
                        position: C.position,
                        _mapType: e,
                        style: {
                            x: C.textX + 3 + _ * 7,
                            y: C.textY - 10,
                            r: 3,
                            color: o.getColor(p[w.seriesIndex[_]].name)
                        },
                        hoverable: !1
                    });
                    S.push(x), E = w.value
                } else {
                    w = "-", f = "", S = [];
                    for (var P in i)S.push(p[P]);
                    S.push(x), E = "-"
                }
                T = u && !isNaN(E) ? u.getColor(E) : null, C.brushType = "both", C.color = T || h.getItemStyleColor(h.deepQuery(S, "itemStyle.normal.color"), w.seriesIndex, -1, w) || h.deepQuery(S, "itemStyle.normal.areaStyle.color"), C.strokeColor = h.deepQuery(S, "itemStyle.normal.borderColor"), C.lineWidth = h.deepQuery(S, "itemStyle.normal.borderWidth"), C.lineJoin = "round", C.text = J(c, E, S, "normal"), C._text = c, C.textAlign = "center", C.textColor = h.deepQuery(S, "itemStyle.normal.label.textStyle.color"), N = h.deepQuery(S, "itemStyle.normal.label.textStyle"), C.textFont = h.getFont(N), h.deepQuery(S, "itemStyle.normal.label.show") || (C.textColor = "rgba(0,0,0,0)"), A = {
                    shape: "text",
                    zlevel: d + 1,
                    hoverable: m[e],
                    position: C.position,
                    _mapType: e,
                    style: {
                        brushType: "both",
                        x: C.textX,
                        y: C.textY,
                        text: J(c, E, S, "normal"),
                        _text: c,
                        textAlign: "center",
                        color: C.textColor,
                        strokeColor: "rgba(0,0,0,0)",
                        textFont: C.textFont
                    }
                }, A._style = l.clone(A.style), A.highlightStyle = l.clone(A.style), C.textColor = "rgba(0,0,0,0)", k.brushType = "both", k.color = h.getItemStyleColor(h.deepQuery(S, "itemStyle.emphasis.color"), w.seriesIndex, -1, w) || h.deepQuery(S, "itemStyle.emphasis.areaStyle.color") || C.color, k.strokeColor = h.deepQuery(S, "itemStyle.emphasis.borderColor") || C.strokeColor, k.lineWidth = h.deepQuery(S, "itemStyle.emphasis.borderWidth") || C.lineWidth, k._text = c, h.deepQuery(S, "itemStyle.emphasis.label.show") ? (k.text = J(c, E, S, "emphasis"), k.textAlign = "center", k.textColor = h.deepQuery(S, "itemStyle.emphasis.label.textStyle.color") || C.textColor, N = h.deepQuery(S, "itemStyle.emphasis.label.textStyle") || N, k.textFont = h.getFont(N), k.textPosition = "specific", A.highlightStyle.color = k.textColor, A.highlightStyle.textFont = k.textFont) : k.textColor = "rgba(0,0,0,0)", L = {
                    shape: "path",
                    zlevel: d,
                    hoverable: m[e],
                    position: C.position,
                    style: C,
                    highlightStyle: k,
                    _style: l.clone(C),
                    _mapType: e
                };
                if (v[e] && y[c] || w.selected && y[c] !== !1) A.style = l.clone(A.highlightStyle), L.style = l.clone(L.highlightStyle);
                if (v[e]) {
                    y[c] = typeof y[c] != "undefined" ? y[c] : w.selected, b[c] = e;
                    if (typeof w.selectable == "undefined" || w.selectable) A.clickable = !0, A.onclick = h.shapeHandler.onclick, L.clickable = !0, L.onclick = h.shapeHandler.onclick
                }
                typeof w.hoverable != "undefined" ? (A.hoverable = L.hoverable = w.hoverable, w.hoverable && (A.onmouseover = h.shapeHandler.onmouseover)) : m[e] && (A.onmouseover = h.shapeHandler.onmouseover), a.pack(A, {
                    name: f,
                    tooltip: h.deepQuery(S, "tooltip")
                }, 0, w, 0, c), h.shapeList.push(A), a.pack(L, {
                    name: f,
                    tooltip: h.deepQuery(S, "tooltip")
                }, 0, w, 0, c), h.shapeList.push(L)
            }
        }

        function X(e, t) {
            var n = [w[e].transform.left, w[e].transform.top];
            for (var r in t)h.buildMark(p[r], r, s, {mapType: e}, {position: n, _mapType: e})
        }

        function V(e, t, n, r) {
            return L[n.name] ? ot(r.mapType, L[n.name]) : [0, 0]
        }

        function $(e, t) {
            return S[e][t] || t
        }

        function J(e, t, n, r) {
            var i = h.deepQuery(n, "itemStyle." + r + ".label.formatter");
            if (!i)return e;
            if (typeof i == "function")return i(e, t);
            if (typeof i == "string")return i = i.replace("{a}", "{a0}").replace("{b}", "{b0}"), i = i.replace("{a0}", e).replace("{b0}", t), i
        }

        function K(e, t) {
            var n, r, i, s, o;
            for (var u in w) {
                n = w[u].transform;
                if (!n || !A[u])continue;
                r = n.left, i = n.top, s = n.width, o = n.height;
                if (e >= r && e <= r + s && t >= i && t <= i + o)return u
            }
            return
        }

        function Q(e) {
            var r = e.event, i = c.getX(r), s = c.getY(r), o = c.getDelta(r), u = K(i, s);
            if (u) {
                var a = w[u].transform, f = a.left, l = a.top, h = a.width, p = a.height;
                geoAndPos = it(u, [i - f, s - l]), o > 0 ? (o = 1.2, a.scale.x *= o, a.scale.y *= o, a.width = h * o, a.height = p * o) : (o = 1.2, a.scale.x /= o, a.scale.y /= o, a.width = h / o, a.height = p / o), w[u].hasRoam = !0, w[u].transform = a, geoAndPos = ot(u, geoAndPos), a.left -= geoAndPos[0] - (i - f), a.top -= geoAndPos[1] - (s - l), w[u].transform = a, clearTimeout(T), T = setTimeout(nt, 100), n.dispatch(t.EVENT.MAP_ROAM, e.event, {type: "scale"}), c.stop(r)
            }
        }

        function G(e) {
            var t = e.target;
            if (t && t.draggable)return;
            var n = e.event, i = c.getX(n), s = c.getY(n), o = K(i, s);
            o && (D = !0, M = i, _ = s, H = o, setTimeout(function () {
                r.on(f.EVENT.MOUSEMOVE, Y), r.on(f.EVENT.MOUSEUP, Z)
            }, 50))
        }

        function Y(e) {
            if (!D || !h)return;
            var i = e.event, s = c.getX(i), o = c.getY(i), u = w[H].transform;
            u.hasRoam = !0, u.left -= M - s, u.top -= _ - o, M = s, _ = o, w[H].transform = u;
            var a = [u.left, u.top], f = {position: [u.left, u.top]};
            for (var l = 0, p = h.shapeList.length; l < p; l++)h.shapeList[l]._mapType == H && (h.shapeList[l].position = a, r.modShape(h.shapeList[l].id, f, !0));
            n.dispatch(t.EVENT.MAP_ROAM, e.event, {type: "move"}), h.clearAnimationShape(), r.refresh(), P = !0, c.stop(i)
        }

        function Z(e) {
            var t = e.event;
            M = c.getX(t), _ = c.getY(t), D = !1, setTimeout(function () {
                P && h.animationEffect(), P = !1, r.un(f.EVENT.MOUSEMOVE, Y), r.un(f.EVENT.MOUSEUP, Z)
            }, 100)
        }

        function et(e) {
            if (!h.isClick || !e.target || P)return;
            var i = e.target, s = i.style._text, o = h.shapeList.length, u = i._mapType || "";
            if (v[u] == "single")for (var a in y)if (y[a] && b[a] == u) {
                for (var f = 0; f < o; f++)h.shapeList[f].style._text == a && (h.shapeList[f].style = h.shapeList[f]._style, r.modShape(h.shapeList[f].id, h.shapeList[f]));
                a != s && (y[a] = !1)
            }
            y[s] = !y[s];
            for (var f = 0; f < o; f++)h.shapeList[f].style._text == s && (y[s] ? h.shapeList[f].style = l.clone(h.shapeList[f].highlightStyle) : h.shapeList[f].style = h.shapeList[f]._style, r.modShape(h.shapeList[f].id, {style: h.shapeList[f].style}));
            n.dispatch(t.EVENT.MAP_SELECTED, e.event, {selected: y}), r.refresh()
        }

        function tt(e, t) {
            s = t, y = {}, b = {}, w = {}, S = {}, A = {}, x = {}, B = !1, nt(e), O && (r.on(f.EVENT.MOUSEWHEEL, Q), r.on(f.EVENT.MOUSEDOWN, G))
        }

        function nt(e) {
            e && (i = e, p = i.series), h.clear(), j(), r.refreshHover()
        }

        function rt(e, t) {
            s.dataRange && (nt(), t.needRefresh = !0);
            return
        }

        function it(t, n) {
            return w[t].transform ? e("../util/projection/normal").pos2geo(w[t].transform, n) : null
        }

        function st(e, t) {
            if (!w[e].transform)return null;
            var n = [w[e].transform.left, w[e].transform.top];
            return t instanceof Array ? (t[0] -= n[0], t[1] -= n[1]) : (t.x -= n[0], t.y -= n[1]), it(e, t)
        }

        function ot(t, n) {
            return w[t].transform ? e("../util/projection/normal").geo2pos(w[t].transform, n) : null
        }

        function ut(e, t) {
            if (!w[e].transform)return null;
            var n = ot(e, t);
            return n[0] += w[e].transform.left, n[1] += w[e].transform.top, n
        }

        function at(e) {
            return w[e].transform ? [w[e].transform.left, w[e].transform.top] : null
        }

        function ft() {
            h.clear(), h.shapeList = null, h = null, O && (r.un(f.EVENT.MOUSEWHEEL, Q), r.un(f.EVENT.MOUSEDOWN, G))
        }

        var o = e("../component/base");
        o.call(this, t, r);
        var u = e("./calculableBase");
        u.call(this, r, i);
        var a = e("../util/ecData"), f = e("zrender/config"), l = e("zrender/tool/util"), c = e("zrender/tool/event"),
            h = this;
        h.type = t.CHART_TYPE_MAP;
        var p, d = h.getZlevelBase(), v, m, g, y = {}, b = {}, w = {}, E = {}, S = {}, x = {}, T, N,
            C = e("../util/mapData/params").params, k = e("../util/mapData/textFixed"),
            L = e("../util/mapData/geoCoord"), A = {}, O, M, _, D, P, H, B = !1;
        h.shapeHandler.onmouseover = function (e) {
            var t = e.target, n = t.style._text;
            E[n] && r.addHoverShape(E[n])
        }, h.getMarkCoord = V, h.dispose = ft, h.init = tt, h.refresh = nt, h.ondataRange = rt, h.onclick = et, h.pos2geo = it, h.geo2pos = ot, h.getMapPosition = at, h.getPosByGeo = ut, h.getGeoByPos = st, tt(i, s)
    }

    return e("../chart").define("map", t), t
}), define("echarts/util/mapData/geoJson/an_hui_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "3415",
            properties: {name: "六安市", cp: [116.3123, 31.8329], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nJUXUV°UÑnU@mlLVaVln@@bn@VU@xlb@lLnKlVIJUVxnI@lVL@b°VX@bxnVVUnVVnU@kX@VwV@al¥UUnUWa@@wĸULU¥lKUa@aUI@alLVaU¯anWkUKm@XV@VaXlW@aU_UWVUI¯@ma¯W¯I@UU@WWU@U@@UU@VkV@@WUUm@UaU@lK@IUKL@KWmXUWaXI@@a@a@U@U@KV¥lwk°b²JVIVKlV@UXlaUl`UVLVVVUJU@Lnm@_VK@KUIW@J@Xk@WW@UmmXmWk@kK@aUUVmmkUwUmWL@WmU@UJmUULkKWakLWVkIlwULW@X°lUJ@°ULWVwmJ@bmb¯Vkm@@WkWm¯wL@lkXWmXym¯UImJUbkV@Vn¯@V@lUb@mk@maUxmlUbULWn@JLmKUkWKkwUKbmXWxkVUKmLkVV@JUUWL@xkJUUV@X@VVlUbVX@xk¤x¼xWxnnn@Þ¼JVb°aVn@mlnXUJlbVlkz@lUlXJmxVxXnWxXÈWlU@UxU@VX@xUL@UÆmLnV@lWXk@@JlbXblnlJ"],
                encodeOffsets: [[118710, 33351]]
            }
        }, {
            type: "Feature",
            id: "3408",
            properties: {name: "安庆市", cp: [116.7517, 30.5255], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@n°znWXlW@kK°xXnl@Xn@l°Una@anIxXUVK@¯VIkW¯X@VKxklJXUlKXblLVKnVVIV@Xn@XKVnVxlnnUlmV@²óUkVlWbln@VVVIn@lw@WVIXblV@ÈxaUaVIVVnKVLKln@b²K@»U£ÑķġÝÅbKa@Im@Û@kWÓkkmKÅnóJUÅ£W@wĕ@wĉţ¯¯UkK±l¯U¥UÑkÝUķ»Ý¥¯JIUVbUl¯ÈV¼VJU¼Vb@bkLUl@VJ@bUXÇ@lkVmXmKkLVxVL@VkVVVlzWkbmLUUUbVbUVlÒnJlUnLllUL@bUVxlLXVÆ¦ÈVU¦WJ"],
                encodeOffsets: [[118834, 31759]]
            }
        }, {
            type: "Feature",
            id: "3411",
            properties: {name: "滁州市", cp: [118.1909, 32.536], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@`nnl@xK@X°KXVIXVlbXVWnXlL@È»LVan@VJêVVn@X@laÞbVayn@_xnWVXnWl@VnUVkI@lnXKVLVV@V@kW@LlVô@J@bVnnKnkVa@»lç@nwKmaUUUVÑ@nmWXalI@alVn@VwUaVU@nlaôJnUVVXlJaXXVK@UV@VWx@nXVWXVUlLUbVULVVnUVbUbVb@@aKÆnnKVK@U@UU@@a@V°¯ÈJVIlķ@aaUaVKU_@mkxUI@aUlyU@@wkKWmUbUnUVWbkJW_J@bn@Vm@@KULk@V@@bVbÅm@LW@UVVbkK@UkKWL@VULUKWIUJUbkK@_WVXUJka@XVa@ky@aVIUUW@@mUlLKWÑUKVan@UkVmmIXKaVaUwVU@UmykU¯@±UUL@WUIVUU@KkIWaaU@kUUaÇUó»mKk¯@y@kWK@bkI¯`mnl¯XWlkVUzUJlbUbVJl@nnm@VULV`XnWÆbmUUnJmUknJ¯km@yk@kUxL@VUbmnn¤lX@`z@JmaULUVl@Xn@xllkXWaaW@UVmUb@mVXWxXbWbUÒnVVnVVUL"],
                encodeOffsets: [[120004, 33520]]
            }
        }, {
            type: "Feature",
            id: "3418",
            properties: {name: "宣城市", cp: [118.8062, 30.6244], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Vb@XLJXxlIXxlVlV@I²¤nlUnVU@VULWVUJ@Lnb@lV@UnV@@VVVlLnbnJUVkUUVWn@@anUVnVJVIV@@nUJVbUb@VUbVK@bn@VbnIlxkllXVlXKWUXUlL°¤UVVb@bUlkXWxXz@IlaUlnUlJVInVÆJULVUnVK°@VnlVnxV@XLlK@wVL@KnUlJXUbnKVLXlUw@VWlLXKm@@a@VLnmlIVVnKn@kVaVlwk@@a@k@VIUa@maUa@wna@kmWUUmVUIVÇ@aKmakUJ@InmUUaVaklX@Vk@m@VU@wnK@alKVUkUkKbmUkm@U£WVk@@UÝbbaÇx@b@WVUa¯@wVwUUV@VwnK@KWaÅ@KIUyUI@WmXóUbWaKm@km@IUyIUaWKx@zUKUL@llVUnkLVVkJWX@VUKUVIkVWakb@VWb@n@JkXUlmL@xkL@`VxLUÈUJ@Vm@@bmIUlUL@VUVVbknm@mKUwKVÈ@J@LV±kkJUIl"],
                encodeOffsets: [[120803, 31247]]
            }
        }, {
            type: "Feature",
            id: "3412",
            properties: {name: "阜阳市", cp: [115.7629, 32.9919], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Vn@ak°a±@¥@UUI@aUmlwUUxb@¥XU@mmI@a@Kn@@_W@@WI@mUVVXUl@XaV@K@I@aLX@aVI°K@KVLUUwyXkK@kKÆbXnlK@k@aJlU@w@U@»@aXKWn_JXkVKn@°LlKXW@¯U@aUK@kmJUwVIUJkmLK@kka@wUVm@@am@UkUbkK@nmVÒ¯VUWVVmIULk@ma@kkK@nUbUamU`UUVUkKVkkW@@bkmnmUXVKXVL@VbUmbVXJ@nmKÅI@KWKUXVJUL@VUKUX@KUKWL@LUJmaXXm@kVVV@L@VUL@VlK@L@V@LUK@VUb@UUU@°@nVxU`Lkn@`@XVJ@XVmk@UKmV¯LVVn±Wm@Ub@JlLUl@VLk@lmVVn@bnV@V°IVaVJXI°K°V@XXVlVVUnKVlUbWXnV@bV`U@@m@@@nxmn@bXVlL@¤nbUl¦VVUnJVUVl@@bÞL"],
                encodeOffsets: [[118418, 34392]]
            }
        }, {
            type: "Feature",
            id: "3413",
            properties: {name: "宿州市", cp: [117.5208, 33.6841], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@UWU@bkW@aWU@aUIkWVlLXblVIUVV@mn@V_n@VaUK@I@UaanJVU@lVUVnnKVVlaUaI@wnKLnll@nVlk@wVKXkl@@bbUJ@VU@UUUyVk@aVUXwlWXXWU¹@aU@WUI@mlUnJ@Il@aXbV@VKl@XxVL@WIJlb@al@IUUm@@aVK@¥¯@mUķ¯bWk£Vm@akm@VaÅ@UVWa@UJWkJUbWbU@UlXk@amV@K¯nk@lU@Uxmz@bU`ÇbUbÅVm£U@Wwx@akLUK@UlakwUJWVkLmaUal@n_mVUnKVUUmÅXWa@kJmx@XUJ@bVLXxl@VVUVVUbkLWbU@@lUVVVVXK@XkJ@nU@@bV@VxUVlbU@xXLWn@UxVbVĊV@b@XV`mnkJ@kUKmbaU@VbnbÆx@XU@@`k@@bl@@bkL@WakXWaU@Vmkx@XWW@@wUUUbJU¯V@¯ÞU@WxXlL@bkb@lVlnbJW@kkU@mbkaWJIVlmz¯`UnU@mb@@`@bkVlnV@b@V@aVxn@VxKXnl@nbVKbVK@a_V@Vw@WLlwnK@UmIU@VW@UÈ@lKnalw@@V°@aUmlUUw@V@@UXK"],
                encodeOffsets: [[119836, 35061]]
            }
        }, {
            type: "Feature",
            id: "3410",
            properties: {name: "黄山市", cp: [118.0481, 29.9542], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lXnlWX@VUJVnUJVzXJVxkVJlI²lU@K@IUÇLVxnLn@lmUaVU@UVKVknJ@an@@UVIVÇKUw@_lK@wnKVklW@I@mXa@UlaXblUJVUVL@UXWlIUUlKVmkU@kVKVL@ywXLVbJVz@Jln@nLXbVaônW@la@UVWUa@@a@mk@WIk@VwUa¯¥m@UUVK@ImK@aX£kKÅVa_@±akXWWLnU@@a@¯mK@LJUWwUVVmbXX@lWLn`mzUJUbLk@makVWmkXambkKkna@ab@U@Unm@WV@VbUbUJWIk@@lmL@°UVUVmn@@kmWkb@x_m@@aU@b@JlUzlWxXn@b²@l`IVlUlL@VKnVbUl@VlIn@@bbVWUk@@bX@Valb@bnb°Vn@xVKlbVnV@VxL@ln@UXVVL"],
                encodeOffsets: [[120747, 31095]]
            }
        }, {
            type: "Feature",
            id: "3414",
            properties: {name: "巢湖市", cp: [117.7734, 31.4978], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VV@blL@XlWnnn@VXXl@@WIX@VJ@LxŎxln@bXJVblX@VVbUVn@VbUVlb@LnJVbVLVXLÒVLÒVbVIVylUXk°Wknm°_lJ@aXL@lz°@lnLô¼VÈVUUaVKU@WW@@UUa@knmVLlaV@a@kak±@UmwkKmkǉÝUUkL@mlIVmnÝWkkUÝ@KƑĉa@»mma@mX¤¯Uw@@UU@bU±±L@akmLUKmLUUUJVbbÇw@kUWaUJ@Xkxm@UJUUm@kakXUVl±ôU@kn"],
                encodeOffsets: [[119847, 32007]]
            }
        }, {
            type: "Feature",
            id: "3416",
            properties: {name: "亳州市", cp: [116.1914, 33.4698], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lU@Un@@anUlw@KVmUwlaX_lKna@KU@@kWKUU@ankWXK@@V²VVIÈU@al@VaÈamK@wU@klaUV@XVUU»WUUbkmUkVmk@aÈw@mWU@VkIkVWKUÑķXȭºU¯l@kkLWmÅaL@lLWlzVxVUK@L¯LUJ@bWK@b@JLU@Wbk@WVUUV@nJ@XX@@`m@@L@bnJ@nWV@¦awVVkxVn@bVJ@V¦@²¯blb@mUU@¼¦XbUV`@nnxUxWLkUkVWKkV@XV@@VVL@VX@lVV@L@blL@`L@xXKVL@VnU@lwnU@ml@XnV@@UVW°LnalUI@aUK@aa@UkXW@I@mWL@UXK@UVW@U@@kWn@@V@XblaVxL@bVKXbIlJ"],
                encodeOffsets: [[119183, 34594]]
            }
        }, {
            type: "Feature",
            id: "3417",
            properties: {name: "池州市", cp: [117.3889, 30.2014], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@V°°ĊŤ@xĖ@xXÆ¤VôIÆmnLllXÔ@lÜn@@JbLÆaĢÞĸ°VVUUKVanK@UV@VLVVnln@xnklxXamk@WV@Xa@naVkKlk@mkUWwkJWwIWK@UaUwWIUyVIUmVI@UXWmkkWKUUVWm@@kKw@UUUmkaULwm@¯Uma@akaUbW@@a@VlUXa@am@kJ@UVkUamL@UkKVUkJk_±@a@WmXwÇkkaVaUa±wV@VkwnyUaW@UU¯amLk@m@kmmU¯K@L@lUX¯WlkXVbbVUL@J@LVKnlJXnlb@`nXlalV@bnL@Vnb¼@lXbWlkLK@zUJmIUxUVUVmX", "@@llUL@VlxL@a@UwXa¯@"],
                encodeOffsets: [[119543, 30781], [120061, 31152]]
            }
        }, {
            type: "Feature",
            id: "3401",
            properties: {name: "合肥市", cp: [117.29, 32.0581], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@LxVĊLÞkVlVVXaWaXwWnU@anVVUX@bXblWkk@wWmk@VUVKnb@Iy@_kWm£nmVa@UKwlVl@zn@°lIlmnVIVmnVaXÅWmU_VK@Unmmk@UIVakaaUÑUKÑWKUUKUamI@KkaVUUam@VUUa@UkWUaWI@akmōwwUL@`mn@KVIUVUUUKVk_VkbW@VkUULUJ±I¯alkxU¦@L@V@V@b@b@WJXbWVXn@LKVL@JkLV@Vbn@VV@XU@UlV@@VV@V@XXV@@VJ°°Xnb°@JUVVXV`@bkXWUbU@Wn@VLXlm°bVUbkK@bVJ@bVbkLV¦KķV@x@XbmVVVk¦"],
                encodeOffsets: [[119678, 33323]]
            }
        }, {
            type: "Feature",
            id: "3403",
            properties: {name: "蚌埠市", cp: [117.4109, 33.1073], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VÒXLlUlJ@UXV@nÇx@bnlUVllnVaXVV¼UVWU@V²wVV@Vl@VnwlI@XbÆWVnUVmLUVnm`k@VbnblKXUVIlxkb@VVLlK@bwXxV@n¤ÆUVaÈaV_@anyVwV@kl@°m@LnUbl@WVkV@XaaVIXlIV@XbVUÆ@XKWwUkmW@_UmnIlJXkWKXmV@w@_XV@Kl@kU@KlX@@UUUUKWLm@klJVUUmk@mXUWmXw`m@zUbÝakbW@m@UUéUIm@UbKÇ¼@kKWXmWUkaWUJWU¯L@WLwk@mm@_ÅlUVkmWUnV@VWLUbbƑĬ¯l"],
                encodeOffsets: [[119543, 33722]]
            }
        }, {
            type: "Feature",
            id: "3402",
            properties: {name: "芜湖市", cp: [118.3557, 31.0858], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@bVaV@XllLXU°lL@V@VUnVl¯IkVUVU@@b@lUXUWmbn@¼bƒĊLÞ@lVXlmÞUnkJ@nlKVVÞXklWVaVI@aUKn»lL@Kn@XXwlm@mn°@V@WywXlWVk@aUaVU¯£kKWVXVWLUkkWlkkwmJUam@@aULVa@UVaUaVI@m@UUJUIUmmV@bm@UXVVUlVmImakKUU@UU@VmU@@kma@KVIXUVK@UVmUkVm±£@JkU@nlkLUlmb@WbU@@XnlWb"],
                encodeOffsets: [[120814, 31585]]
            }
        }, {
            type: "Feature",
            id: "3406",
            properties: {name: "淮北市", cp: [116.6968, 33.6896], childNum: 3},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@lnnK@¦n@@VV@@VV@nIVV@VW²a@b@bVnUVVV@Vz@l@°UVIVaVV@x@XX@WlwUnV@XblWb@XlK@a@k@al@@_V@@WÅwmaUaV@bnaVL@llInmU_@W@aUUĉUaVwm@XWK@wVkaVUUwU@@aV@@mlI@WLWUUUVU@kV@XalKVaUVUUUk@WwUK@aVI@WUk@@UUU±xkb@lV@xnLÇbUbk@@bÇVUJ±U@U@WLXml@bVVXL@lV@@LmbkLW`kbVxUn@LkxmV@bm@@VkV"], ["@@VVVkV@¥@UV@U@VUUJkWakKUlXVJ@bXV@blX@aXV@V"]],
                encodeOffsets: [[[119183, 34594]], [[119836, 35061]]]
            }
        }, {
            type: "Feature",
            id: "3404",
            properties: {name: "淮南市", cp: [116.7847, 32.7722], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°kƒīaVaXK@UUVmnXUlVÆkVKUUUmmUÑkUUÝlĉKUwKbU@UxW@@lmVUUVmUUmwaWkL¯K@mULWlIm`XWL@b@¼@V@xkVI@b@l@lkV°Ȯ¹ĸW"],
                encodeOffsets: [[119543, 33722]]
            }
        }, {
            type: "Feature",
            id: "3405",
            properties: {name: "马鞍山市", cp: [118.6304, 31.5363], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ǊnllLnxV@laXLVKmaaXbVIbVKVVVIVyn@n_W@@UnJlUVVXlLnaUWlV@VVIXW@_W@XK@K@UVUUwVamÑXmmwwKUnUKçU@JU¯@m@nknWxWm@@LkKm¼VL@bUJUbkXWl"],
                encodeOffsets: [[121219, 32288]]
            }
        }, {
            type: "Feature",
            id: "3407",
            properties: {name: "铜陵市", cp: [117.9382, 30.9375], childNum: 3},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ÒV¤@¼V²@aVV@@x°V£nW@nbnaVXVW@k@aV@VUUl°JUkVm@U@UkK¯WVkKWkU@Ubakwmlwm@kUmUUKU@@VmLUbVLUV¯U"], ["@@LllUL@VlxL@a@UwXamK"]],
                encodeOffsets: [[[120522, 31529]], [[120094, 31146]]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/ao_men_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "8200",
            properties: {name: "澳门", cp: [113.5715, 22.1583], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@HQFMDIDGBI@E@EEKEGCEIGGEKEMGSEU@CBEDAJAP@F@LBT@JCHMPOdADCFADAB@LFLDFFP@DAB@@AF@D@B@@FBD@FADHBBHAD@FAJ@JEDCJI`gFIJW"],
                encodeOffsets: [[116325, 22699]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/bei_jing_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "110228",
            properties: {name: "密云县", cp: [117.0923, 40.5121], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@vIHZDZQtDLNMXIbHRCXXITbJ@H`LGPRDDJNCLHTOCWFGvGBUJMKGFO^IHWXITQCIY^AXGfRDXF`DJOLB~G\\DZIHHpErUVMhHb]\\MBVF@FTP`@zTbD\\@~M\\K`H^EVODWICAakAQXoIcCOCIgGYNWFWNGGKKGaJEGMEIKYJUT_J_Go@_SyQaSFMEGTcYOQLIIi@EKAUPCV[EEXQCW|aMUMAaYCYNIDGGACIMGGSKDQGaF_C[GaB@GOIiOKAYLmI@CN]F[SWWAcKKI@HMUimEKbeYQYISNUOcBKPIFBNgvDPGZYFSf]CMSIWGEUFgDIQ[MeDMJS@RR@LphFPCHaBAJKF@J]IBJO@HlO@@RKAMPJHCNDJTHFP@ZGNANBRFH@J_fM^ONJNF\\VTDJHDON@XRND\\XRCPVETCLBVKDFJINHRGPRV@\\CLJN@VbXbLVT"],
                encodeOffsets: [[119561, 41684]]
            }
        }, {
            type: "Feature",
            id: "110116",
            properties: {name: "怀柔区", cp: [116.6377, 40.6219], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@JHTVHXCHPfnDJGHNDJSB[JSBGVSAOH@PMPuDEHHXZN@PHF@ZLJ@LHVYJA\\OFWP]BMtMBSRGV[JeVAPQVIFENMD¡@^NV\\JH@NNL@NM\\kTQ\\I^FNIpBHGTBFFAZQfKDIXQTLXFXNNVMVHRGpCFLlRLEVBBH`IVO\\G`RDPAXLXBXORHZEHTDLLN@VGTMrQNFPeASKG@GMOAKBYMK@GTUHUXSHMVDNMOUEOZMJML@^KRACMZEZMRQLUHE@OFENPR@DI\\ChMHIDG\\GJMDWHCKGMDCIQCHO_K@GaIJSWWQDaGWJMNCKRsCYGYuJUSaKaW@UIMDK@[QUHOGQJMEILCAUDKFSOUQD[WMCQ@WPMGCCIUSE[IMPMN]`e@IEGAQBMHM@YEOSGCIDMIGNOLB@QP@GkP@AI^J@ILEBIbADGEOog@KQQWSekWQQUOFKZLF@PUNmIaHIUeBCTSHENcJa@_IWSaGu`GLSBKJQFOXGDXVQVOBIHcDSJWBEFGTMH[^mLaXcHiKElTRKtFXZ`MHMPCNRDxZB\\ICIHK@KHbIVFZ@BPnGTGbDXRDJaZKRiGEFSFEJhjFNZFjn"],
                encodeOffsets: [[119314, 41552]]
            }
        }, {
            type: "Feature",
            id: "110111",
            properties: {name: "房山区", cp: [115.8453, 39.7163], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@h@bl@HRJDZ``TA\\VVD^H`\\pF\\J`JGv@ZO\\GPSTEjPTR`FnEbDTDHEhLFMTK@ETSPULKEI@OVISKSJACEQNQbVIXGDIN@dMB[IIBcN]ZHNLP@XOWCFWCNRHTpATD@^NVNLED@Rh@jCEF}E[OOHUEW]W@QGGDIQSH_MmFmCUT_K]i@MHCMWFCFE{BMHMPOHKS]CFNGBELDH_@BcAKOACESAOBELaXAROB@FODMEDWJAG[aE@UM@DImEWJMC@OeCA{aE[@{L@MINUCQXKfUJORCHqJBF@TCXWNQX]M[EAJO@@KMBQJIC]EWMCCUBEBFHKDOTMBGNGF]MWDBRDdMDQVyE@LPVHDCP@JVVMTG~HNSH[CmRUvHPHBbA\\PTNRC\\YNJPRARPJDDR"],
                encodeOffsets: [[118343, 40770]]
            }
        }, {
            type: "Feature",
            id: "110229",
            properties: {name: "延庆县", cp: [116.1543, 40.5286], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@^AXOPEB[ZIGU@KKI@YGE@OYMGWFGvCNO@OPGTBHUTA\\ITACIGMIHmCOeDGGWSUIGimYEEMgiFITEFEjHLQbYCIWQaCSHmHAOY@UEaJG@LGLDJ[JAwYQCDMNONGY_EWLsSQFkMO[NWAIGaIYL@HMBOKiOQDWEUDMQSF_QIUBWdg@[NaAKQ@M]OQ@WhgLUMMFYQDIRCEUZOOCIOJ[KIUMKL@HIDKVEBM`HJAJSJUdBLGNEdMBMO[BYEWJSNKNaD]PE\\SjOT_RQVEZPpNQXfNA~lNG`@PNLp¼RFLfbdKbATUh@FSNWjGFZVLFHVA~X¨PPROfFJbNJPLFbENJPrEFNPFRHDDJdENJLVEPBJTVTHGHFRFH@PXP\\ORQHW\\BjWFDERLPPBbB\\E`B\\D\\L`@F]FCnJ^AZL"],
                encodeOffsets: [[119262, 41751]]
            }
        }, {
            type: "Feature",
            id: "110109",
            properties: {name: "门头沟区", cp: [115.8, 39.9957], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@V@XMnGPY²JQNEhH\\AZMPDVTTDZCPiJkHSHCjIdFtEHITCNITQEKUAMCEIKCECABYESKFWAKBEIIHABGDCKCAIHMHALKEI\\CFIBILIJQZS]BBEECS@E@@C]COKI@CABAAEEDMGCH]A[M@CJWHJaUMRFRBDTITLUJ@PFJKLOVST@FSLENgKGFSCaCmF_ESQiOSFOT[HYPu@IH_[IoE_[]GUC[USB__CYQI@Gakg@qZeHQNMNV\\FVLPgJAFJPRLCH[XcPELUT[JiV_EELFTADBXRTRLJC@fHXHHbPd`fR@NfT`@TLplHMpCEJHJBVLF@JTVnG^KXDXHNVGRLRXFJVdDHSNWLGfEzA"],
                encodeOffsets: [[118635, 41113]]
            }
        }, {
            type: "Feature",
            id: "110114",
            properties: {name: "昌平区", cp: [116.1777, 40.2134], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VNLJI\\JPPDYPFVQDCJZRNEVNhKXgR@^P@NLRbB\\Mh@XcVARJE`RTCNFVXRCjPPLNA@GZKbJJHXB\\MNPjLdGbWnK\\]NGHSFEXATIdCJGPARUWUHCPWRELITAHKv_E@iYCaW_BQ\\Y@QIO@QDCIGZCEMWGFMFAFgHEDOCSqKCCFGAMKEAC@ODGCGs@WH@KQA@EE@CE@GEA@EH@GGUEEJEAYD@JM@@DAA@FHD@FTJEHUC@JUBKCKG@G[CIIQReAYhO@OXGDO@@FF@IHJFCPEBACBIAAKDOABXARHPNEHGbQAAKQFGIAM[C@WHKaGiCEGOAHUKCIokSCUSOCYN[BgGMFIR±OZmHWNU@ShbbXDHVXXGJ^lZ@PZ\\Nb@\\FHJAD"],
                encodeOffsets: [[118750, 41232]]
            }
        }, {
            type: "Feature",
            id: "110115",
            properties: {name: "大兴区", cp: [116.4716, 39.6352], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@F\\E~DFN@BDFEpHFCHBBEGCDCJBHUDSBB@ELCPbF@B\\J@BJVAFJ\\ADKTCBGECFMT@BMN@@FH@DaNBEnvB@FPBATK@FHEFIAKFBFL@@PKBFJHC@FXBRAFCDMPDTOL@JIVFDHH@DDH@BGRFCDLD@N^@@CNA@KNOAEBCECFEGCFGMGFIPMOEJOLBADBBHGG@GCHIECY@INC@DMGS\\AIOZAAEYA@GT@KKMBEETCGMVINFxA@MJADB@FlA@HJA@NND@DFA@DVAZBBOFKH_JA@K^GBC@EFEG@gAENMXKJigC@IbSJMqGOP£RGSMGE@kbQFDPEFiBSGGSBK]I{CDWCIDOic[C_G@SuSO@EWKCO@MNY@\\uZOPENQD[LKESSKGBKEG@EJGAGHoH¥CqhifeJkX_XFFGHFNEDFPENKHM^IFIVL^S`DVEnNnG`RTCJHH@R^XFXGVPP"],
                encodeOffsets: [[119042, 40704]]
            }
        }, {
            type: "Feature",
            id: "110113",
            properties: {name: "顺义区", cp: [116.7242, 40.1619], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@EhEBENXHFNYDJHCD@RJP@R[ZARX`DbjZF@bHXT`Jb@dIFMTGDSfAJVbGnJVM@OKELYPERVXRflXTT@NIfC\\NJRhCVEHFJXNT^DTeZEHYCOhuAMJELOdAVPTMOWBWNMNEJgl]@WGUFIC[T{EEDEHGCIGMI@SECUQI[D{A{GQESPUH]CsiMCmHUeoHENcAaDGCMDGMQCACCBaCGLMAHB@DIEQLOAAEEJ@CW@CDINGAAGKQOCgV@LG@BEGDKNeREFBNCFIDOPKD[@YRW@GFWDAFE@EHDDrLDTCPGF", "@@KrJEH[\\B@FF@CHFBHUNAJKADGECBCMAG^E@EbI@BEGP"],
                encodeOffsets: [[119283, 41084], [119377, 41046]]
            }
        }, {
            type: "Feature",
            id: "110117",
            properties: {name: "平谷区", cp: [117.1706, 40.2052], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ZJZRafFLjnVGNJ@LLBdXX\\T^EDMJ@nZKLBjPPJ@HbA\\H`DbERHLCFK^BZaFWXQLAGMHa\\OLO@SBIpBdCLVQfElO@GSAKEDQTC@GEBKG@ORIJBDAPDFA@CaOq@GGQAAEJK@KMUGAAGEAa@MGMBGCGSIIW@WSUCMDOJeWOM@IUF{WMWaDIMgIoRoCOKeEOEAG_I[cg@wLIFENQFDVTFJ@HNDJGHCFFFS|D\\EJHV@Xk^IhMFMNAXPX"],
                encodeOffsets: [[119748, 41190]]
            }
        }, {
            type: "Feature",
            id: "110112",
            properties: {name: "通州区", cp: [116.7297, 39.8131], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@FDAJTGDNDCTDDEDBBE@DT@@EHCDGJ@EIZ@@FDBR@ATFBBVFFE@@HNA\\VE@CLIFNJFNJBCP]A@LJFA@HJEDD\\C@DBCHLAEPF@@DH@APHAERDF\\GIxDTM@CFLBBFJ@CNUPMHECGDBF]BMFPDLRBHHBJMDCX@@DFIBFPBRKJF@CGANBHKbDDABDRDHNNCHDbCdBFMpGHiOYMefKJMC}HWAUNW\\NNBNAkNU|]HMTMN@MZBLFFF@RIRUTBMFIEGaAGGAOIIUGTSFcYKS@MSLYPKRUBU]EWDOI]CKGASgW@MTWKIMCS@uMAKKADMECGAKVUTSDy@IjWLMNBF@hHEF@FAD]H@LIBG`ELAPYAUB@CEB@CMC@MIB@GkB@ECAIB@NwBMEUJHNSDFFNALLS@@HZBBFYBJP[BHTCND@JMZ@FDGJHDH@GHAABCKAIPPFONEJNHEHHDEFFDADBFMP@L"],
                encodeOffsets: [[119329, 40782]]
            }
        }, {
            type: "Feature",
            id: "110105",
            properties: {name: "朝阳区", cp: [116.4977, 39.949], childNum: 2},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@bFGHBHFBFIVFHHG@@FFB@HDFF@@FRB@LXGt@DHCH@PBDLFBNF@BEXCHEX@ZQ\\@LCPOJCDEAMFEfQLMHCAFH@@KhUNE^AAEHCFDNGVODMI@AEKADEN@CSJw[HCEFQGBBOG@@CE@FOKBDGCAD@C[FCGIB@IE@K^BDOIAEMMIJEDKF@[UMB@GF@EEAUEABSQ@CA@EY@FJI@CHGD@FS@@CAFCACFSCCDCMSHBIECMB@D]@@MKCDCQEAHG@CCG@CGUEIJK@SPOCCNEDQBDNDB@DJCDLFCBBALJB@BVGPBKVO@KHCCCD@FE@BNA@FNCTDDJA@FGB@NBDW@CL@hT@@ZHHQDDDAFSAANBC@HG@EFS@@DE@@PCB@Ue@CADNJB@FCBWA@LI^ix@FIHrH"], ["@@HUNAJKADGECBCMAG^E@EbI@BEGPKrJEH[\\B@FF@CHFB"]],
                encodeOffsets: [[[119169, 40992]], [[119398, 41063]]]
            }
        }, {
            type: "Feature",
            id: "110108",
            properties: {name: "海淀区", cp: [116.2202, 40.0239], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@plDJVLGPBFHjDbHGL@X\\DBNHJREBLRBHaFGMGOBQAWPBLCBBAJBDFADOIEJGE@@EP@HCPWP@ZgfBRQJJ\\D@HLHLDVA@IVDFGSI@EGC@EBB@CN@@IZCAGHGaEqGJG@EjwJ]@K@GSA@e_I@NE@CA@Kg@KC@ENCFAKQAW@WIMK@V@I@@F@^EDFB@HcIaDYCBRRDCHD@EFLN@FE@CJUPEJOJMTBPEDIFCMIAKNOGMRFJNDVBFLSRMJSDGJsFcEiJGDGTIlOjYD"],
                encodeOffsets: [[118834, 41050]]
            }
        }, {
            type: "Feature",
            id: "110106",
            properties: {name: "丰台区", cp: [116.2683, 39.8309], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@hMN@NFTQCFRCBJFA@HJ@@HJ@HJ\\FTACD@@UNLXJX@@MA@@IECAQlDFEHBDI~D@GXCFMVDFCH@@NF@ANJC@FnAB@AMF@@EDCDDLGP@LUOAUH@AIABKAAEDCKID@CCACMWA@EGDEILA@OK@AELEJBFEEGL@BSOA@EuAFmMACbG@@EM@ANS@ENFDAHSDCL[BEIUBAII@A[E@OaKD@FAACTGVIACDHDAFGAEDoGEFACM@ig@@QFCMKMU@]SCoBGSMQDEXXDWPO@MKYGM^AdJJA\\cNB\\G^DNHFCBFABDBJ@PL^D@DF@T@FDAF^A"],
                encodeOffsets: [[118958, 40846]]
            }
        }, {
            type: "Feature",
            id: "110107",
            properties: {name: "石景山区", cp: [116.1887, 39.9346], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@NQPHLMJBDNJEFCAONSPIFIVODIF@@EKMFEC@DGQCAQZDbCdJ@GEAFC@]@EJ@DCSB[EGII@@GI@@GEBAIQDDESRMEM@gNYTIRKJAJEJ[DFJKLGBGNBJLDCDAHGBJJAFBLEXTLZFBAFDLD"],
                encodeOffsets: [[118940, 40953]]
            }
        }, {
            type: "Feature",
            id: "110102",
            properties: {name: "西城区", cp: [116.3631, 39.9353], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XBDA@EIACM@IJAD]BC@SFABISAD]H@@OAEDQEW@BLEMD@FLDh@@LDBF@@M`J@fTB@H"],
                encodeOffsets: [[119175, 40932]]
            }
        }, {
            type: "Feature",
            id: "110101",
            properties: {name: "东城区", cp: [116.418, 39.9367], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@DBf@@VDA@OF@@CT@FEH@@GADBMTBBECCRCGG@YS@@gDK@AC@PG@C^TBAJEB@TADC^IB@J"],
                encodeOffsets: [[119182, 40921]]
            }
        }, {
            type: "Feature",
            id: "110104",
            properties: {name: "宣武区", cp: [116.3603, 39.8852], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@RBX@RFFCBFU@aK@WA}CCJGAEFkCBRFD@JB@@N"],
                encodeOffsets: [[119118, 40855]]
            }
        }, {
            type: "Feature",
            id: "110103",
            properties: {name: "崇文区", cp: [116.4166, 39.8811], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XBL@@bEVD@BX@AC@MHA@EIBCCDSEMmB@EIDBME@@MG@EDUCENWD@H"],
                encodeOffsets: [[119175, 40829]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/china_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "xin_jiang",
            properties: {name: "新疆", cp: [84.9023, 41.748], childNum: 18},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@ρȁôƧƦóəʵסʵóƪԫʵѵͩƧͩړυࡓɛʵ@ȃ@óᇑѵƨɝɚôóНѺͩɜ̏ԭʵôƧɞñ@υƩ݇ȂóƩƧ@ѵȂυƥŌਗ॥ɛóʵѵƧѹ݇̍ࢯəɞυρͩ̏óਙƨƧŋôōó̍ͩóʵןóŋړͪƧѶ@ɜԭԫƦɛȄ̍ɝȄöςƩȂ̏ñȀ̏ƩóóŎə@Ő̎@ɞȀɝŎôƨóנѵȄƧ@óŏɝóɜôŎ̍ͨςŎ@ƨóôƨɞ݈ʶóƨφó̎Ȁƨ̍ԮòѸԮמ@ѺȀ@ƪၬֆòȂñ̐òȂɜóƨ̒Ŏ̑߼@φρȀ@Ő๐ς̎Ƨφ@ɝφڔ೦Ԯǿࢰ@ƦŏԮƨƨȄƧ۬ɜʶڔŐɚɚóŐôƨ߼ôƧƧó̐ƥóŏѺǿƦȁφƧςƨƧ̒@ɜƥƦυ̐ɛƪͩƩəƪʷ̑ə@ȃƨʵנŋྸōਚԭԪ@ɝƨŋ̒օςʵôƧ"],
                encodeOffsets: [[98730, 43786]]
            }
        }, {
            type: "Feature",
            id: "xi_zang",
            properties: {name: "西藏", cp: [88.7695, 31.6846], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôŌנôʶ̎ͪôóŎƨŌਚƧ̐ôςͪφɚɝࢰ݈̎ѺѶƨôʶ०ɜਘƦŋφѶȁ̍ôŏɚŋ@̑ə@ŏò̍ɜóƥôʷƧ̍φѹԪ̍ע@Ѹʷɜ@ôñנ@Ѷɛɞô̐ŏѶƨѸƧƥōƦôŏô@ƧôƩ̒ŋƨŌƦǿô̎ɜȁ̒óʶѶôôО̒ςƥɜНφσɛȁ̎υƨఱƧŏ@ʵƥ@ŌóóóͩƨƧóŋ̑õóɞóɝԩͪɝρôƧ̍ƧѹͨڑŎ̑ōóƧࢭͩ̏ѵɝóఱóóԪυô@̒ƥŌ̏Ƨ̑Ȅ݇ŎƧѵӏ@ɛõŏɛȄôӒƧŌѵǿɝƧŋԫ@̏ʴƥ@óǿ̑Ȁóǿ̍ςóóυô@ʶɛñρƦƩŐó̎óѵó̑ͪࢯОóɜןƧ̏ƥȄ߻̎̏̐ןŎɝɜöɞƩȀôöɛȀóͪ̐ƨƪ̍̎ȂƥԪυО@φɞôƪ"],
                encodeOffsets: [[80911, 35146]]
            }
        }, {
            type: "Feature",
            id: "nei_meng_gu",
            properties: {name: "内蒙古", cp: [117.5977, 44.3408], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ኊȁ૊ö߼ƩɜɛנñԮɛѶóԮô@ȁѸóמ̎ගѺၬ@߼ʶԮӒ߼̎@ŐѹӒ̒Ԫƨöග̑ѶȄ̒ς।ѶɚöɞɜʴڔôôȂ̎ѺȀςƨƪóԪɜôɛОਕڔԭѵ̍ѹȂԫɛƥ̍Ȃóɜ̎ô@ʶ݊ੲࢮʵږͪנƨôȂƧ̐ͪ@ŐƦƨφԬѶɜôƦ@ŐƧôôƦəŐ̏@ŐڒѶԬô̐ʳԩНςōôŏɞ@ƨȂѶəóƧ̒ػ̎ó̐Őנóƨô̒@ƨɚɚ@עԫɛɛ@ȁυͩƥʳòևρ̑ࡗƧͪ༃ॣԮփ̎Ʀ@ôô@ôō@@ȁѵóƨ̍υȃóʵɛƨƥóυȂóəƪ̐ρƧͩɜԭڔȄ̎عƧȁ̐ŏó̍ɛƥƧ̑óρŐ@Ƨ̏ɝəɛ߻ͩ̍ͩɝО̍ƪƧóóӓƨóƧʳ݇@ɝςƪ@ʴƩƧƦôƨɛȄəƧŋυóͩѵ@ɝǿóŌן̍ɛóО̍̑̏ôȁ̍ŏòȁñóƦͩ@ǿə@ɛƧ̑ρȁυô̍օѹóȃə@ȂσʵѷƪòƩ̍ôó߻ۯôʳƧóõʵѵóѹɜ̍ȂѹôɛŌφֈƩͨρóυӑóޟఱ̑݇ͪóƪƨŌóȄڔԬƩςםñ̑ȃѵŐԭŏƨȁɛǿρôõɚɛóƧОə@ѹ̐ѵöԪͨôͪɛ̒ןŏƧƥóôƥƧɛŌôóɝó@̒݇Ӓ̒Ō@Ŏԭࢰ"],
                encodeOffsets: [[99540, 43830]]
            }
        }, {
            type: "Feature",
            id: "qing_hai",
            properties: {name: "青海", cp: [96.2402, 35.4199], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƨ@ôƪ݈ȁƪ@φɝòóƨԮʶɛ̐ѹͪôОəóƧɞᇒѶ@ôږô@ǿѶƪȁςɜͩφςŋɞôѶɛƨŌɞ@ɚςŐñԪॢͩƨȂɞóƨŐ̎ŏעӏ̎óƧƦô̒ȁɜςͩ̒ɚɛƨôƨɝφɛóȁƨŋóóɚͩƨóóƩ@ƧəŋƦƩ̍@ƧƧôǿυ̑@ȁɞǿõŏρƥסɚƧóτԫɞôƧƦ@ñȃòñƥóυôôѹѵ@ŏ̏Ȅɝó@ȂəŌóəѹƦ@Ő̍Ōυ݈ԩŐƧóôƧ̑ôʵɞƧ̑ѵôƩɞƧ̑óНѵóôʵ̑ɛȂó̍ƥȀƧŋ̑Ōóƪ@ƨóóŐƥƦŎѷƨѵƧ̏Őɝóѵɜן@óòɛ@ѷʸס@ԩ̎υѺƨ̎óʸôƦɛñ̎@Őɚ@̒əŌóŐ̎"],
                encodeOffsets: [[91890, 36945]]
            }
        }, {
            type: "Feature",
            id: "si_chuan",
            properties: {name: "四川", cp: [102.9199, 30.1904], childNum: 21},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôôŋó̑Ԯ̒ɛОמͪƨōöͫ߼ƥôȃƨóóñôƧóƧôōڔŏƨŐ@ŎôòƥѺŎ@ōɜóנôǿôƦôԮ̍ɜôɚƧñɛɚȁ̍Ƨɛևυ@óóôŋρԭɝ@Ƨʸ̍ŏυɜƧƧóƧƨȁρ̍ƨȃɚôʵφóô̑̏Ȃ̑ʵɜʵɞ@ƨʳסƩóŎəóɜƧôƩƧρóôôô@ŎƧƨƨƪѹó̍̍Ʃ@̏ѹНôޟ̍ƩóƪυɝɛəƨôŎɛȀ@Ȃ@ñɝʶ@Ōρנ̏õóɛͨƨȂѵОɛʵ@̏ƩŐó߼Ƨల̍φɜȂυτɛОρƦɝƨóƪ̒Ѷɝƨóʶ̒óƨƨôԪŏφ݇̎ŋ@ŏѺƥôɚɚŋ@ȁɞô̐ȃ@ŐѶóѺφóƦôñòòȄ"],
                encodeOffsets: [[104220, 34336]]
            }
        }, {
            type: "Feature",
            id: "hei_long_jiang",
            properties: {name: "黑龙江", cp: [128.1445, 48.5156], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ᇔȂਚНƨŐѶŏöƥςŏñƧƦóƨȁ@óƨóȁφӑóóƨóǿ̎̑ôНɞó̑ɜə߼̎ǿ̒ôڒӑφ@Ƨȁ̎̏ƥƩ̎ρశôȂςƨφ@נɞ݈̑ƥƧɛƨʵƧȃƥ@Ƨƥ@ŏ̑ԩôɝρρóɛƧƩͩƧó߻ʸ̍ʷѹƥɞڕõ̍öɝυ̍ȂƧ̐̑ŏóƨñŋѹóóȁ̍̏Ԭõʸ̏ŏ@ǿ̍@ƧОυ@ñƨòȀƥŎ̑ŐѵóɛŌóȂԫōƧŎѹñ̍ʶóОן@Ƨ̎Ѷô@Ȃ@óŎó@@ó̍ƥԭք༄।ƨͩ̒ࡘςñֈƦʴφͪ@ȂɜɜסԬə@Ƨə̑@Ƨóןô̏ŏ̍ô̑ؼôƨѵɚƧȁɝ@óŐρŎԪО̏ʴ"],
                encodeOffsets: [[124380, 54630]]
            }
        }, {
            type: "Feature",
            id: "gan_su",
            properties: {name: "甘肃", cp: [95.7129, 40.166], childNum: 14},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ڔôԮࢯ@ō̑ŋ݈ӑ@̑ɞôóôɜŋƦƨôóƨƦנŐɜ̑óͩԩͧѶõѺ̏ɚ@ƨНɜôöəςóɜȀƧȂԮŐѶŏ̒ȄמòƪρړԫôȃƧŋôƩ݈ͩɚ@@ǿɜ@φͩóŏɜӑƧōôǿ̎ôƥƪóõö@ôƨôƧƦôó̒ɜ@ɞŌõʶ̏Ő@ȀóôƨȂ@ʶע@@ƥ୾ӑó̑óŋôʵóɛړ@@ƩöóƩóρɛƨ̑@óʷƥƥ̎ɛƧôōƧǿôͩѵôɝȃɞȁõƧρóó@ōƧŏړŐóŎôƨóƨôòƧôóȄ߻ƦõͬƧŎםͩɜНԭ̑ô̒óŌóƥ@óƨɝσԬƨôעəςƦöŐɝȀ@Ȃφ̒óȀƨƨ̎@ƥƪɚŌ@ƨôƪƧôəͪôôƧŌôȂυɜƧɞƧóəɜ̑ρͪɛ̑Ȃóƨƥ̍ôסӐ̍ŐƧŏɝôƧȁॡͪòԩρŏ@əɝƧŋѵɜɝóρŌυɛͪρƩȂѵ@Ȁڕó@ȄɜʶφࡔڔƨͪѶͪԬʶôƩעʶɚʶƥôóƨςȂ"],
                encodeOffsets: [[98730, 43740]]
            }
        }, {
            type: "Feature",
            id: "yun_nan",
            properties: {name: "云南", cp: [101.8652, 25.1807], childNum: 16},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôɞôɝ̒öôŌƧƨôͪôô@ŋƦ@ʶƨŐô߻ƪŏ@̐ɜʶѶНƧȁɜͧöô̐ςן@ŋɞʵ@ò@ȁɜǿóōɚƧɜφɞôƩ̎ƪóޠѺО@̐̎ƪô̎ѺƧƩƨƧ@ōóóôóςƪƨƨóôɛó̑ԭƥŌɛǿɝƨɛͩô@ǿƨȁѺŌɚɛ̍ןѶНɛƧôóƥȁƦͩôŎɞƨ̑ɜòôφ@ƨʵ@ɛѹōóȃəƨυǿóʵρƧƧŌƩɛ̏ȄñƧƧȀɝ̍ԩʶƧ̑υóŌƥʳɚӑóНƥô̑óӒѵʵѹƧӐןôƪφõŌƪ̒ԫŌƧؼƨƨסρȁƧƨȂóʶó@@ʴƨôôφ̎Ŏ@ȀƨƪɚƨóƨôôôςóޤƧŌƩŋƧԪ"],
                encodeOffsets: [[100530, 28800]]
            }
        }, {
            type: "Feature",
            id: "guang_xi",
            properties: {name: "广西", cp: [108.2813, 23.6426], childNum: 14},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƦŋѺ̎ڔʵƨŐ@ƦמȄƪôóȂɜŌɚͩɜ@öóɜôôȂƦôɜȁ@ɞφóȄ̎ƨʶɞŋƨʴɚǿ̐̎Ԭ@ôñ@̏ƨρ۫ôɚƨƨНƪŐ̎ƥóƦʵƥŋ@ȃóƥƧ@@ŏɝǿôυƧȁѵɛ@əóŏ̑@@ə̍óƧó@ȁƩρóòНƥô@Ӓ̑@óŎ̍ƥσŎυ@̍ƨ@Ō̑ôóͪƨ̒óŌړ̏Ŏ@ŌôȄѺŎ@ɜƧʶυ@ñóɛƧ̒ɝóōƥͪ"],
                encodeOffsets: [[107011, 25335]]
            }
        }, {
            type: "Feature",
            id: "hu_nan",
            properties: {name: "湖南", cp: [111.5332, 27.3779], childNum: 14},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@քɜОƨ@öŐמóƪôƩɚ̒Ő߼ȁςͩɜòƪɜȀòñɝòѺͪ@ŏƨŋóɝôǿƨɚȃóəƨȃѵͩó̍@ȃƨóóƥƨƧ@ʵƦóͩɜɛóñԭɛōυȂ̍ƧƦō@ɛƥɛȀ̑óʷóō̍ƩŏƧОəƧóς۬Ƨ@̐óòԫ@̏̍əȀƧʳɝŌóɞƧƨɜóŐƨò@ȄƧŌρŋóôԪОóʶ@̎óȄ"],
                encodeOffsets: [[111870, 29161]]
            }
        }, {
            type: "Feature",
            id: "shan_xi_1",
            properties: {name: "陕西", cp: [109.5996, 35.6396], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ςôöƨɝȂɞȄѶóóͪƨȀóŎƨ̍ɜƦƦôʸ̒@ɜƧςƪôõô@ƪڔ@ôɜóʶôŌô̒୽Ӓ@Ʀ@Ѻ̎ɜѺɛѶôöʶôƨóʴ߼۰óô̎ñƪѸƩτʶ@ȁòŋəѹóǿ̑ʵ@ȁ̒ʷυփô݉ôН̏ط@ȁƨóô̏ƪõ@ʳ̐ʵ@ɝɛŋƩŌɛóןôƧŋ̒ó@ŏ̐ƥ@ŏυ@ƧƧôן̏@ƥȂѹɜəɛóԭ̎ƥóóóȀןɛô@ŎѹōñƦ"],
                encodeOffsets: [[108001, 33705]]
            }
        }, {
            type: "Feature",
            id: "guang_dong",
            properties: {name: "广东", cp: [113.4668, 22.8076], childNum: 21},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@Ȃôôƨ̎@ɚ̒@ôŐ@ɚѶɜƨȂóφɞȀ@Őƨ@ôƦ@ȄƦŌƥʶƦôôŎôʸ̒ɜǿƦ@ɜƥŎ̎ƨφȁɜŎòƥԮŎƨōóŏɛƧɝəɞƧ߼ɜςȃñȄƦŎ̒ōôòƨəƨɚН@əƨ̏ƪʵυŌəɛóəԭŏəóŏѹρʵɝƦ̏ƥʳѶöō̑óóŋρȀυƧƥɛѹōƧôןɛŏѵ@óŋôʵɝƪԩõ@Ƨō̍@Ƨ@@ƦɝԮƪО@@", "@@X¯aWĀ@l"],
                encodeOffsets: [[112411, 21916], [116325, 22697]]
            }
        }, {
            type: "Feature",
            id: "ji_lin",
            properties: {name: "吉林", cp: [126.4746, 43.5938], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@נ@ôН̎ʵѺòƨōԬŎôȁɜŋѶô̒ŏƦōñǿòƧφ@ƨН̎@@Ȁ̐Őöʷ̐ԫ̎ôȂѺôòŌôƧ̒Őƨ̏̎ȁφ@ŋƩͩםȃƨ@ȁ̑ʶ@Ōóôɛƥѹ̑συ݇@ɜρƧȃࢯƨôəȂɛōƩɛ̏υρóõƪʴυφ@ʶôŌóρք@ɜƧ@ɝǿƧͪρȀƩó̏ŐƨȂ̍غړȃɛԮƨͪ̏ςƩôɚφȁƦôɜƧôʶφȄ"],
                encodeOffsets: [[126181, 47341]]
            }
        }, {
            type: "Feature",
            id: "he_bei",
            properties: {name: "河北", cp: [115.4004, 37.9688], childNum: 11},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@Ʃ̒̏ŌѺ̒ƩóȄƧŌƥͪòôñȂ̎ŐóȂ̒̐̎ôНɜנ̎ôŋɞȀѶ@ôͪφƨŌɚɜȃóƧƨƥƪ@ʳƩɞρ݈@υНφʵɜƦρƨƧ̍ɝóɛѹ̍ρŏ̑ôóƨ@ƧƦôƨɛ@ƥƨ@ȂƦ@@ôəŐƧʶƨŌυ̍̎ɛŋôōɝ@óƧ̍ƦʵѵʳôʵɜŏςôƪŋƨŌɚ@ôНƥƧ@ōѸɛ̐ô̎ʵѵНԭ@̍̍Ƨò@ȁɝ@əρυͩƪ̏ƩõƧŎƧōóॡȄɛʶɜȀ@ɞςѶƧƥςɛŐ@ɚɜɜ@Ŏôôςƪς"], ["@@õə@Ƨɛ@ŐóƦφô"]],
                encodeOffsets: [[[117271, 40455]], [[120061, 41040]]]
            }
        }, {
            type: "Feature",
            id: "hu_bei",
            properties: {name: "湖北", cp: [112.2363, 31.1572], childNum: 17},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ñȄυƦöŐƩóנƨƨφ@@Ő̏Ʀ@Ő̑ôƨŌנóɜôƪŋɜŌѶօڔə݈òɞōɜŎôӏƦóƨô̒óôȃƨó̎ŐôƧƪ@ƨȁςƧə̑̎Н@̍Ƨŏρôԭͩԫ̍ʵƧóȀôɞƧŌ@ŐѹͩñòɞñɛǿƩɛñρͪ߻Ȃ̑ŏƪəƩóםôõŏƧ@ɛНƥȄó̑ѺƧôφóƨƨƦƪóɜŐôóòôƨóφ̐ƨóƦ̎"],
                encodeOffsets: [[112860, 31905]]
            }
        }, {
            type: "Feature",
            id: "gui_zhou",
            properties: {name: "贵州", cp: [106.6113, 26.9385], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɜȀƦŋԮô̒ɚôōעƪƧʴɝ@ɛʶ̒ʶ̐ȁƦóȂô@ôŏ@ōôƨʶѸô@ʶƨɞó@ōτöòυƨ@@əƨô@ɛ̒@Ʀɜôȃ@̍ôʵԩНôóςŌƨŋ@ȃƧñôŏƧɛƨôɝƧʵ̍ôȃυ@ɝɛȂƥóóȁɛóõôɛ@əͪɛŋôȁƩóםȃ@ƥƧŏړʶѹ̍ƥŌƦȂóôɜƨѵО̎נəɜѹŋƧȂ@ȀóɜͪɞƧ"],
                encodeOffsets: [[106651, 27901]]
            }
        }, {
            type: "Feature",
            id: "shan_dong",
            properties: {name: "山东", cp: [118.7402, 36.4307], childNum: 17},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ʃ̐φͪɚςɞ@@Ȃƨñ̎̎Ԯ@ѶОƨƧڔ@φН̑ŋ@Ʃ̒ǿ̎@ƨɜԬςôʶ̐ʶöԫƨƧנƥɜŎôō̎@ôŏóρƧŏԫôóƧԩó@ƥɜƧԭóƨʵɛƨ߻ӑɜНԩóô̑óƧʳəóɛƧ@õȀƧ̍ȃɛŐóŏυО̍óɝƩԩ@ƧɚԫȄɚʶƨɞʶԪ̐ړɛƪ̒"],
                encodeOffsets: [[118261, 37036]]
            }
        }, {
            type: "Feature",
            id: "jiang_xi",
            properties: {name: "江西", cp: [116.0156, 27.29], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƧȄôɚəȄ̎ʶԬԮͪςóƨŐƪτɞƦōƥƧ@ŏςôóŐôô̒ʷѶƪƩƩǿ@ō̒ɛôυ@Ƨȁѹɛəƨѹ̑ƨ̏óƥѵʷô̍ɛȁôŏɝǿƧԫƧôʳƥōòȃρȄ߻ɛɝƨɞɚɜƨôŐƧŎԭōñƦòԮɜôɛôͪƥ@ʶƧƨôƦƧô@Ȅô̎Ѷͪ"],
                encodeOffsets: [[117e3, 29025]]
            }
        }, {
            type: "Feature",
            id: "he_nan",
            properties: {name: "河南", cp: [113.4668, 33.8818], childNum: 17},
            geometry: {
                type: "Polygon",
                coordinates: ["@@φ̎ƪ̐ɞȄɚ@@Ȃעó̎ŌѺ̒ôֆॢȃôƨŎƨōƪöƩ̑ڔɜԩ̏ɝʵƧəʵԬȃƨəԪ@@Ƨ̒ŏô̍υȁƧɚ̍ôóŋ@ɝƧŋõ̑σ@ŏɜŋôɝ̒ƧɚôôطρóóɛƩ@óƨ̍ŏƧôóȄ̑ôƧóƥôóӐɛōɝŎ݇ñړɚѵֆ@ɞ̏ʶ@ʴƩöó̐"],
                encodeOffsets: [[113040, 35416]]
            }
        }, {
            type: "Feature",
            id: "liao_ning",
            properties: {name: "辽宁", cp: [122.3438, 41.0889], childNum: 14},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƨʴƧôôӔƨô̎ƩɞН̎ͪ߼ͪɜɞɚ̐@ƨςŏ̒ôƦƨɜô̎ƪôςǿƨͩɞȀƨ@@ɛςփôóŋ@ʵφυƩʳö॥փρѹס@əɛ@ͩࢯ@ѹʵρƩʶφȀƧ݈̒۬óʸɝŎѵ@ԭԫןɛƧƨƥςɛυʶφО"],
                encodeOffsets: [[122131, 42301]]
            }
        }, {
            type: "Feature",
            id: "shan_xi_2",
            properties: {name: "山西", cp: [112.4121, 37.6611], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɚѺñŌɚôȄѺ̎ֆφóςȂ̒ɜƨɚ@@Ȁƨŋôȃƪѹ̑̐ŋƪ̑Ʃρρóó@ōɛɛ@əɜŏƦρƨρѵ@ɝɛǿɜʵóօѹ̑̍ŋסô@ȁə@ɝȃ̏̍ƩυƧô@Ȃ̐ظóОó݊φք̑ʸ@Ȃ̒ʶôȀ"],
                encodeOffsets: [[113581, 39645]]
            }
        }, {
            type: "Feature",
            id: "an_hui",
            properties: {name: "安徽", cp: [117.2461, 32.0361], childNum: 17},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ó̎̑Ő@ƨƪѶǿɜ̑φƦʵ̐ƧѵôóƪôôυςƨȂɞŏ@̍ԫôò̑ƥóȃѶͩƧƥôŏѺôŏƦ@ƥͩƧôȁυó@̑ƧɛѵʵƩƪѵ̑ʸóóôŏρó@ŐƦƨƥŎσɝƩ@̎̍Оɚ̒ρƨƧȂôɜςôóظəó̑ƨóɞɛŌ@Őτö̒ƨŌ@ɞôŌ̎óƨəφȂ"],
                encodeOffsets: [[119431, 34741]]
            }
        }, {
            type: "Feature",
            id: "fu_jian",
            properties: {name: "福建", cp: [118.3008, 25.9277], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@̎óȁƨӑ̒̎ɚƨͩφŐƨɝ̎ŋóŏρ@ōƨòʳəóƨō̏õɛƧ@ƨѵƧōəŏóŋƧô̑ɝɛʳƥ@@óɛõ@Ƨ̑ƧóȁəƧ̑Ƨ̐@ɚəОƧƧɚóñ̑ŎóʴƨƨԬɞȀóŐɜȂó̎ѶʸôƦƧ̐Ѻ̒ɚƧѺɜƨȂ"],
                encodeOffsets: [[121321, 28981]]
            }
        }, {
            type: "Feature",
            id: "zhe_jiang",
            properties: {name: "浙江", cp: [120.498, 29.0918], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ѷʶƨɜ@̒φôóȂƨƦͪ@̐Ѹ̍τȂ̒̑נŐמôƪƧôӑ̑@ƥρͩƨօ̏@@υɝó@ŋɛ@ôƩəóƧѵυó@ƩɜŋƧ@̍ŌƧɞυŏƧͪ̍ə̑ƧӒôȂ̍@óφ̑ɜ@ŎƪȀ"],
                encodeOffsets: [[121051, 30105]]
            }
        }, {
            type: "Feature",
            id: "jiang_su",
            properties: {name: "江苏", cp: [120.0586, 32.915], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôɞ̎φНôŐɜŏ̎Ȅƨöǿƨ@ôɜɚƨʴ̒ôôó@Ƨ̎əԮȃԪૉöͩ̐ƧòʵφƧôʵ@óړɜóŏɜǿƧɝρσȁѷ̎̏ƥóŐѹóŐƨƦѵͪôȄƦñ̒Ԭó@̎ɝŐƧȁρóφƩóóôƨѶ̏ƥʶυɛ̒ѵȀ"],
                encodeOffsets: [[119161, 35460]]
            }
        }, {
            type: "Feature",
            id: "chong_qing",
            properties: {name: "重庆", cp: [107.7539, 30.1904], childNum: 40},
            geometry: {
                type: "Polygon",
                coordinates: ["@@əȂòɜƨѺɛƦȁ̐@ƪõŏφƥòȃƥ̍Ƨôυ̏ƧôñóóôɛŏƩôƧƥôƧóυƨ̒ѹôƦȃ@փƥɛ̑@@ɜƧó@ɚƧ@ñφσõ@ŎɝôƧ@ʵѷóƧʵó@ŎóŐó@ôȁƥó̒υôóʶəƧȄς̎ƧȂôƨƨƨφɛ̎Őƨʷɞ@ςԮóŌôôφ@ɜֈ̎ƨ"],
                encodeOffsets: [[111150, 32446]]
            }
        }, {
            type: "Feature",
            id: "ning_xia",
            properties: {name: "宁夏", cp: [105.9961, 37.3096], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ల̒ôޠφӒςôƪͧυևɜŋѺó̎ȁ̍ɛ@ѹס@@ʵƧȁôó@ǿ̐ŏöʵɝŋɛ@ô̑ƥóóƨƧóôó@ƩôóƦ̍óȀƨŎɛӒôŐυͪɛ@@Ȁə@"],
                encodeOffsets: [[106831, 38340]]
            }
        }, {
            type: "Feature",
            id: "hai_nan",
            properties: {name: "海南", cp: [109.9512, 19.2041], childNum: 18},
            geometry: {
                type: "Polygon",
                coordinates: ["@@φɜƦʶ̐ôφô̎@ƨŎö@τʵƦԩ۫õН̏óƥȃƧ@Ʃəםƨ̑Ʀ@ޤ"],
                encodeOffsets: [[111240, 19846]]
            }
        }, {
            type: "Feature",
            id: "tai_wan",
            properties: {name: "台湾", cp: [121.0254, 23.5986], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôƩɝöƧɝѵəޣ̏ρƩԭóōóͪρɞƧОôԪ݈ଦѶɜ̒ɛ"],
                encodeOffsets: [[124831, 25650]]
            }
        }, {
            type: "Feature",
            id: "bei_jing",
            properties: {name: "北京", cp: [116.4551, 40.2539], childNum: 19},
            geometry: {
                type: "Polygon",
                coordinates: ["@@óóóυóôƥ@ŏóóə@ƧŋƩŌρóɛŐóʶѶʴƥʶ̎ôƨɞ@óŎɜŌ̎̍φƧŋƨʵ"],
                encodeOffsets: [[120241, 41176]]
            }
        }, {
            type: "Feature",
            id: "tian_jin",
            properties: {name: "天津", cp: [117.4219, 39.4189], childNum: 18},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôôɜ@ƨöɚôôôɚŏ@óƥ@@ȁƦƧɜ@óƧƨƥ@ƧóəН̏óѷɜ@ŎƦƨóО"],
                encodeOffsets: [[119610, 40545]]
            }
        }, {
            type: "Feature",
            id: "shang_hai",
            properties: {name: "上海", cp: [121.4648, 31.2891], childNum: 19},
            geometry: {type: "Polygon", coordinates: ["@@ɞςƨɛȀôŐڔɛóυô̍ןŏ̑̒"], encodeOffsets: [[123840, 31771]]}
        }, {
            type: "Feature",
            id: "xiang_gang",
            properties: {name: "香港", cp: [114.2578, 22.3242], childNum: 1},
            geometry: {type: "Polygon", coordinates: ["@@óɛƩ@ρ@óôȀɚŎƨ@ö@@ōƨ@"], encodeOffsets: [[117361, 22950]]}
        }, {
            type: "Feature",
            id: "ao_men",
            properties: {name: "澳门", cp: [113.5547, 22.1484], childNum: 1},
            geometry: {type: "Polygon", coordinates: ["@@X¯aWĀ@l"], encodeOffsets: [[116325, 22697]]}
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/chong_qing_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "500242",
            properties: {name: "酉阳土家族苗族自治县", cp: [108.8196, 28.8666], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XJ°lJX@lbl@XbV@VLnJlxnbUU@IVK@lVIVwnJlU@n@J@L@Jn@l_nWVLVln@@blLmV@@xÔ`nxVÈLlxLVxVVV_U»VWn_m¥XwVmnX°lmUUVwÞaVk@a@mmIUa@mwk@m@@U¯a@UV@@K@ykkmwkV@kU@ÑVkKWLÅamaUm@kyU@WkU@UaIUaVaUUmUUa@aVLXKWa¯UUbmJXnWnX`l@@xkzWÆ@VLU¦x@b@JkIkJ@LmbUamJwm@óxnk@V@xVnUVmVUVUbVlUbkXW"],
                encodeOffsets: [[110914, 29695]]
            }
        }, {
            type: "Feature",
            id: "500236",
            properties: {name: "奉节县", cp: [109.3909, 30.9265], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@WVXbUnK@x@b²kxmKkl¯_VV°VU@bnKVVV@@nk@nbn@°@VLČU@°WV@VnU@InKVl@nUbKnXWlknLlKUwnalLaVlUXmWk@UU@UWWIUyķ¹XaWW@XKUIVmU@W@UVU@KV@n»VkUkÇmUmVIUmULUbm@wUaKkkm¯ÑUL@bWVnx@VmxUI@klmkkK@aK@IlJ@I¯k@mak@mnkJVL@bV@UbW`UUUVI@VU@VVbUJVLUVVbUXVVxk¦VJUnVxnVVUJV@Ubl@@bXV@L"],
                encodeOffsets: [[111781, 31658]]
            }
        }, {
            type: "Feature",
            id: "500238",
            properties: {name: "巫溪县", cp: [109.3359, 31.4813], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nLWbXVLVUV@KIVl@b@lbUVnU@JÆU@V@n°KĢUl@VbÞKV@_VKXUU@KX@wlkkU@mWKUU@UôJ@XV@aVmÞIVaVL@»km@UkLU@aU@WWLUUUKkbwWa@KU@kaXmWLamVk@UmL@JmVUU@¯X@ċVUK¯@ÅnWKLkKULWK@UXK@wW@LkV@bVLlXn`¯xU°LnlV@n°Lnl"],
                encodeOffsets: [[111488, 32361]]
            }
        }, {
            type: "Feature",
            id: "500234",
            properties: {name: "开县", cp: [108.4131, 31.2561], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@n@naIw@@VVKLVbVxnVÆUnanKWXamKmk¯K@mkUm¯KV°w@Wm@UIUUlKUU@a¯KWanwmUXamKkUWUnU@KkUwWKXaWLUWkImaUUUKka±k@l¯wwmbUkXm@UJkIWXXbmUJXUV@°KllVXV@xmbnV@blV@VU`UL@Va@bULlb°VXbÜ@V@bL@JxnLVb@lVb@V@@zbXWXKVLV@@bUVVL@blVna@ll@zl@@J"],
                encodeOffsets: [[111150, 32434]]
            }
        }, {
            type: "Feature",
            id: "500243",
            properties: {name: "彭水苗族土家族自治县", cp: [108.2043, 29.3994], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Jlb@nVV@bXb@ÆlLUl`nVKU¼VxkbWnlUxlXX@°°WnnJ@VUn@Jk°L@VlV@nUJx@bVVVz@VnLlaKnalVlIU¼@nV@@anKUwVal@UlJlI@akU@UWXKVI¯Uak@@KmkXWÜkXWykIWwXw@laXamkVUUym_XmlkkmmakwmIUKU@Wak@kaW@kI¯WIk¦VUUmaUV@XkVUV±aUb¯b¯¥m@@ImJ@mmL@kUKUkkJbV¦"],
                encodeOffsets: [[110408, 29729]]
            }
        }, {
            type: "Feature",
            id: "500235",
            properties: {name: "云阳县", cp: [108.8306, 31.0089], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lbLVVVnblJVXXKWbXLVxl@LmVXVVlnLWbnVmxXb°L@bVVkLVVVJn@@X_WmkUK@alUKX@@xWL@VXLVKlLKXLÆm@ma@ml@mU@UUmL@aVUU¯U°`lknLlw±@a@wmLVWaXU@KWU@ak@VaU@IUVmUUwVmUIl¥UwUVWUaVUUKVIUa@UUUUJUUmknl@@VWV@L¯aUbUlx@@b@VULUx@VUxVVU@bU@mxUU@mUVklkk@WxknlxK@amLKUK"],
                encodeOffsets: [[111016, 31742]]
            }
        }, {
            type: "Feature",
            id: "500101",
            properties: {name: "万州区", cp: [108.3911, 30.6958], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ĸĊVInaWWXlJVIn@lWVnax°xkl@²LVLnK@bLkwlmXw@lllkUnVV@VnwV@@aVUUVw@UVwVK@U@a@kwVVa°b@KXU@U@mkÇÑamlkUVmn@VULUm@kUVkUawUWm@Uw¯mKUUmVUUULUKUW@XbWVkaWwkUUk@maUbmbVlk¦xUVUIWVUkJVVkL@UmJUUVU@lLUVUlx@@VbJUL¯¤@V"],
                encodeOffsets: [[110464, 31551]]
            }
        }, {
            type: "Feature",
            id: "500229",
            properties: {name: "城口县", cp: [108.7756, 31.9098], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VK@w¯L@m@UÅV@ImVUVka@@aUkJ@LUUVUKmLmbÅVmUUwUaKUL@U@xJmbm@nVJ@X@VkVnlLXx@b@bUVLU`UnbU@@mVVX@JX@VLVVklV`@bUL@VLVKn@U@UJkn@lmLmK@X@Jn@mbnÞWVXnJkKČÑÆ@VK@knaÜmXlUČW°kôÇÆ@a@yÞ_VmUnU@K"],
                encodeOffsets: [[111893, 32513]]
            }
        }, {
            type: "Feature",
            id: "500116",
            properties: {name: "江津区", cp: [106.2158, 28.9874], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@InWUUlU@LValX@°²lÒXxlK@Ul@@Un@UaVJ@I@W@UUUVUwVIUKUaUUVwn@Üx@XUlnnbJ@¥VklKUUlk@ynU@kVUUVWnI@¥V£VWVIUKU@UVa@n@Vm@@nlUaVkUwJ@blLkLW@XWmXkmmLn@m@U@UVm@UVUUlakUVaVkV@@wnaWUk@VwklmVIkUUxmJ@U@KIkx±V@IUm@K@IUKkbWKUbnm@bmVnbmb@xkxUJ@ULW`@bX@WVXL@V¯mk¯@UJ@VmLUaWnX@WJ@nkKkxW@UIV@@KkImmkK@UW@XaWIU@UIkbWbxXlLVbnV@bWlX@VxVLnl@nÆÞVÜ"],
                encodeOffsets: [[108585, 30032]]
            }
        }, {
            type: "Feature",
            id: "500240",
            properties: {name: "石柱土家族自治县", cp: [108.2813, 30.1025], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@kl@¼UbmVXJ@bV@nxVIVJULVVk@@LWbnJVU@bVbUJ@blLXnWV@mbnV@Vbn@VJVLnaVanbl@VlVXxlbXUWaX@VUUVwUUVm@I@WmI@amlLlK@alwnUV@kóVaÝk@UlbVK@VU»VUUVWU@U`ULkwm@@KmU@knK»VkJkUmbLkbmK@UUyUU@awm@@XXJ@VVLVVUbVnUJVX@Kk`WXXJWXUbmW@bkLUm`Xnb@JVL@LU@°VVXKVnUxVLUbmJ"],
                encodeOffsets: [[110588, 30769]]
            }
        }, {
            type: "Feature",
            id: "500237",
            properties: {name: "巫山县", cp: [109.8853, 31.1188], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@kVUbkKmbVxkLmKkllbV@@LXbxlaLVVVKXXV@@bVlKV@ln@¼°KXaU@Ulw°JXalIUaÝWXW@kVU@VUVWUUUamUw@aVamwn@VUUlLXWm£@wÇĉkKklmLUÒ¯Wn@ğ±kwmaWm¼U@@LUV@V@XVUnVJLW@XXWbĸºVzXJVXV@@VXlWn"],
                encodeOffsets: [[112399, 31917]]
            }
        }, {
            type: "Feature",
            id: "500102",
            properties: {name: "涪陵区", cp: [107.3364, 29.6796], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nèVblĖVVnL@xVn@nJ@LUVVX@lbUJV@@nn@VVVK@zV@nzVJVUlmX@@_VVVbnaVal@@knW@wnaVK@aVIJ@£kUVW@wXUVJam@Ik_X¥@WwkKkwmkUxnÅmm¥WV@Um@UlVL@JU@@X@UVkKVkKVkKkb@bmJVXUVVUbU@@`W_UV¯b"],
                encodeOffsets: [[109508, 30207]]
            }
        }, {
            type: "Feature",
            id: "500230",
            properties: {name: "丰都县", cp: [107.8418, 29.9048], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Þè@XUK@LlV@blbUJ@V@bnV@VVVXU@lbXal@VXnKV@maXUÞ@amk@aVKXVanb£°mnIVaUKVwUmWLUU¯V@@KUK@IaWmn_VlK@anXVaXWWIXWl_@LUWVIUmVaUUUK@UWI@Wn@VI@mkU@U¯Kl@ImVÅLwU¤óbUU@wWXkmm@LU@@VUIWVUL@JUnax@JnbUIWVx@UXlV@¤IUJ@bULmb@xmX@lk@UbmbUaUU@`W@kn"],
                encodeOffsets: [[110048, 30713]]
            }
        }, {
            type: "Feature",
            id: "500232",
            properties: {name: "武隆县", cp: [107.655, 29.35], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lwbVm@IVKXUVJ@UV@@KnnWlX@xVVôaV£xÆKnUVm@UmIXm¯¯@WkWVwmkXlaUwV»ULmk_VkK@ÅWa@aUU@mkaIb@n¼nm_@mmK@ULUVVmI@aUJ@XWJ@U`UIkm±kk@@lULmUmKUnVnlUVmI@VkVlxbkIVmLUxkKUXn¦ÆnmVwlnlxlLXx@W¦`"],
                encodeOffsets: [[110262, 30291]]
            }
        }, {
            type: "Feature",
            id: "500119",
            properties: {name: "南川区", cp: [107.1716, 29.1302], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VUbVJVUn@VLX@WVXVVI@VUVWxU@m@ĊX@@¼V°aVUX`@_V@VaUUVUWnI@alaLUlLUllLVU@@WV@@IUKVkn@@VlLVwnKUlJakwlU@UnJVUmkUVmXa@wVK@UUw@VVI@ak@alInwlKXUmaUW@wWLkKVak_ÇaUV@XbLVxUlWIk@UK@V@kU@VbUVUlVnLUV@lVXmxkV@L@V@Vk@WbUwmL@JUI@xVxkx"],
                encodeOffsets: [[109463, 29830]]
            }
        }, {
            type: "Feature",
            id: "500241",
            properties: {name: "秀山土家族苗族自治县", cp: [109.0173, 28.5205], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XlV@lzn@VnbÆbXKlLUÒV@@llUnxll@z@LU@@V°b@Vn@l@VÑUnK@UU@aUakVm@K¯wklmnnUl`nI@almkIUwmWVkUakkJmUUa@K@aU@@_m@@wUyVUUa@Um@awl@Wka±UkUykIWVb@bUVk@aU@UXUUIWakUWmUxUV@nUVWb@XXVVmXX@VbVLkVWx"],
                encodeOffsets: [[111330, 29183]]
            }
        }, {
            type: "Feature",
            id: "500114",
            properties: {name: "黔江区", cp: [108.7207, 29.4708], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VX@V@LV@VJUL@lVnnxlb@VXVXV@@W@UIVK@kUKna@£VWUaVUUalIVJVIUW_lm@bXKV@mn@JUUw@KnIVll@VanLVmUkVKXLVKUIVamw@UaU_lwKlwUWV_Ua@aUa@KUwm_Ó@wU@nkK@am@UkUKmXk`m@@I@K@I@mkVmIUxUJ@kUL@JVVlnklWnn`VzUVnlWbkb@WxXxlJXzWÛlWXnl@Ll@Vb°UJWLX@VlV@bkJ"],
                encodeOffsets: [[111106, 30420]]
            }
        }, {
            type: "Feature",
            id: "500117",
            properties: {name: "合川区", cp: [106.3257, 30.108], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XKVXlKVL@UnV@aValXXKU@WVwUaVU@IV@@aVWL@U@anVV@@bVK@UVL@bnJWL@VnUnb@@JnIlVl@@bXIWbn@UKVLVKXLlaV@VVnK@bVLmIV@KmknUUWVI@aVJ@_WU_VmUwU@KVak@am¯mJU_UJUkU@WkIV`UI@JV@LmmU@@mbUzÅ@VK@nUKbakb@UWK@bkVVbVÛ@@`Xk@W@n@lXL@bmb@VVJUn@JnUlnUlmX@`XLlbkJW@kzlb@`@b@b"],
                encodeOffsets: [[108529, 31101]]
            }
        }, {
            type: "Feature",
            id: "500222",
            properties: {name: "綦江县", cp: [106.6553, 28.8171], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@¦@XlVX@@UVKlVUX@lanVlUVbXWVXVVVUnKVUlwUwU@UJ@nmVkUVlwXam@VaUUUw@W@kk»mV@UmKkwVKVUU@@LUKVI@mV@XVWxnXVKUUUK@wWU@UUWnUlLXamUIam@wI@K@amImUUkI@makUkKWUUan@wamLVxk@UVmUUL@Vm@kV@I@ak@@bWVXJlLVbVL@@bn@@`Un@WbUKULWVXb@UVmbXWVb@bVmxUKUV@Un@V@V@nmnKlnnWWXX@lKkK@aIVxUlVbk@mn@@U@mbVUV@VLUJUXU¤"],
                encodeOffsets: [[109137, 29779]]
            }
        }, {
            type: "Feature",
            id: "500233",
            properties: {name: "忠县", cp: [107.8967, 30.3223], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VLÞĊU@W@¼V@lk@w²mlVUllVnI@VlKUUlIVXUVJVUwl¥UkUKUIm@aU@mUna@XUWmkK@aVIUa@aUVmIXa@Kl@UUVKUIUJmwU@@aWInUVa»k@@l¯n¤mabWUUL@bnl@bÝWVnbU@mLUWk@Wbka@WVUU@UmUmVkUULVlVUxl@L@VbÈÒlb"],
                encodeOffsets: [[110239, 31146]]
            }
        }, {
            type: "Feature",
            id: "500228",
            properties: {name: "梁平县", cp: [107.7429, 30.6519], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XLV@VV@b°°nnkb@bnJWVXblIUVxWnUJnVVLVUJlUnLVK@UnUVJ²nKVbVKla@aXlJkKlb@U°£KVIUa@@kwVVUkKV@VUkkUVk±n@xkl@U@»@XVÝĉUJnxWb@UXKkVUbUKWUkVmkkLU`b"],
                encodeOffsets: [[109980, 31247]]
            }
        }, {
            type: "Feature",
            id: "500113",
            properties: {name: "巴南区", cp: [106.7322, 29.4214], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nxnVlJlUXL¦@x@Vl@nKVVX@V_V@@KlVXU@lKlxXIl@ÈĊ@Vl@n_VJlnVlnb²VVVJVVmUUkĕUamçU@»W@@ĉnV@XwVU@UUJWUXUW@UKm@UVUIVaUUVmLUVUUUWWXUakVmUkbW@UVkUL@VW@kUW@mJUXVVU@lmV@zklVVkLUl@¦I"],
                encodeOffsets: [[108990, 30061]]
            }
        }, {
            type: "Feature",
            id: "500223",
            properties: {name: "潼南县", cp: [105.7764, 30.1135], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@a@a@_kalyX@lIkaWK@_nWVkkmmV@IVmUI@Una@aWK@k@mkbWaknmJUk@mk@@kUal@Ua@Wa@aXLlwUKlkk@KmI@VUJ@Lk@@VUUmL@amJU£kKUaWakLmU@bVVUbnbWV@xkL@bUbxUxVbXJVbUVWIUVU@kLWxkKWV@n¯VUbU@@VVX@VmaUL@VUK@VVbn@lVnI@@lnLULm@Ub@l@na@lK@XVVkJ@b@zl@@VnV@bVb@J@bnXV`lXXmVI@W@InbV@@aVKUblKVLUanLlmnLlK"],
                encodeOffsets: [[108529, 31101]]
            }
        }, {
            type: "Feature",
            id: "500118",
            properties: {name: "永川区", cp: [105.8643, 29.2566], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@bÜnWVLXlxVVxXxlVn@@bVblK@a@UnLVJV@@UnLVU@VXaVKVX@n`WUÿ@IUKlaUUUkWyUÛÅÝ@mmkUKUwW@Xk@amUUakKWwXaK@VVLklXVlkxVUL@bm@Vxn`IVxUVkLVUl@@lkXmmVUn@VV@Xb"],
                encodeOffsets: [[108192, 30038]]
            }
        }, {
            type: "Feature",
            id: "500231",
            properties: {name: "垫江县", cp: [107.4573, 30.2454], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ċ°¤nÒ¼aV_lKnllUXVVLValULVW@XamwVIUKkaÇÑa@U@KkVwkUUVKlVnU@aU@VIka@akU@KVL@WÝçUV@VmbÅ¯@LKnnJWVkxlL@VX@VxmnXVWxUb@bkn"],
                encodeOffsets: [[109812, 30961]]
            }
        }, {
            type: "Feature",
            id: "500112",
            properties: {name: "渝北区", cp: [106.7212, 29.8499], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@bVVXLa@lnbWn@L@XVlK@VVLUVlbkLUKVVVL@VnXVL@VV@UbVb@x@¦UxVb@bUJL@LVVxlK@nk@U@WUVLlKXV@VblU@UUKVU@wn@VJVanLlkX@VaVK¯@a@U@U@VaUKkUU±maUkm@UUkbm@@Vk@@JwU@Ub@I@JmwUL@a@@KkVÇLkWk@kUU@@xUVmKUnllUb"],
                encodeOffsets: [[109013, 30381]]
            }
        }, {
            type: "Feature",
            id: "500115",
            properties: {name: "长寿区", cp: [107.1606, 29.9762], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VVUbXlX¥l@XnVmlxUx@@blVnnôĀlm@aVaXwWUnmUwW@@UkKlwUXmImL@KÆ°na@UUImyU@@yULUUm@@mU@VIkaW@UUV@KI@mmUw@mKUnUUIlVLUb@@V@V@b°ULUbW@klmKUbUIm@@xUVVL"],
                encodeOffsets: [[109429, 30747]]
            }
        }, {
            type: "Feature",
            id: "500225",
            properties: {name: "大足县", cp: [105.7544, 29.6136], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XUmaVaUU@anVlKXbValU@aV@@IXK@@bV@VxVK@UXLlUJXa@_@@aVKÅWVkwWawUa@am@kUWLU@kWmX@ykI@W@UV@na@LlLV@UkwWUKmXX`mIVl@bXLWVkbkkx@`VXm@@J@U@UUKUxk@WbUIVl@VXLWJUkUlUImxXlmb@X@VUJUnVbW@UV@@VVX@bnW@LVxUnlJUV@n@VxVIn@l`UVVVL"],
                encodeOffsets: [[108270, 30578]]
            }
        }, {
            type: "Feature",
            id: "500224",
            properties: {name: "铜梁县", cp: [106.0291, 29.8059], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VblLV¤nI@bnKVV@Ul@@KVI@UnJ@LlklVLkxWK@bXb@Vbk@Vb@ll@@nVlnIlmXblaXl@W@_Ü@UUalU@aXL@VlabaVL@mUL@UUÇXUWX_WaU»m_@UWULWb@UUVmK@VU@UImK@V@bkLxXblxXUÆUL@b@@`WbIkVWK@VULUwU@@a@WL@JU@@bkVUb"],
                encodeOffsets: [[108316, 30527]]
            }
        }, {
            type: "Feature",
            id: "500226",
            properties: {name: "荣昌县", cp: [105.5127, 29.4708], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VI@U@WnaWknwVJVkVlIXWK@UUkVJXal@VwVL@V@V@In@UW@_wlllaXUWK@aUknJW_Û@aWaU@@UVmUUaUImJVnÅUmVUm`kUUVWLnVU@VVmXK@nxmULkxImJ@nU`@X@Vkn@`@nlV@nVJVaXVLnK@bVV@nV@lbXW@"],
                encodeOffsets: [[108012, 30392]]
            }
        }, {
            type: "Feature",
            id: "500227",
            properties: {name: "璧山县", cp: [106.2048, 29.5807], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XzVlVVkbVL@JVĀX¼VXbW`XWVÈVVVkV@@UXa@alK@IU@UKWUyUI@wVUUWVak@VUkW¹@WXI@yVIUK@kWwkÑ¯±W@kUb@KkVVVmXJ"],
                encodeOffsets: [[108585, 30032]]
            }
        }, {
            type: "Feature",
            id: "500109",
            properties: {name: "北碚区", cp: [106.5674, 29.8883], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XVLV@@JkL@bWb@VU@UlÆVya@nV@nn@KU@IVJU_lJXV@VlVIV`nIn°@blUbKVI@aUaVw@¥@wUaVaU@@UUKWm@UUKUUVLlKkaVUUK@UkLWU@@KXmma@kbWKUU@aUamLnÞ@VWLk@@Wm@ULU@@UKUVWI"],
                encodeOffsets: [[108855, 30449]]
            }
        }, {
            type: "Feature",
            id: "500110",
            properties: {name: "万盛区", cp: [106.908, 28.9325], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VIV@@wVJ@InKVxXal@@U@U@KlUnwUW@kVUKUmVkUa@I@KW@@bk@@mU@m@k@a@aIUxmJk@wULwkKmVVX@VXV@xVLVVULmWXwWUU@@nUJVL@KV@UVULlxnL@VnUl¼@l@XVxVVUbn@WbkxUlVnU@m"],
                encodeOffsets: [[109452, 29779]]
            }
        }, {
            type: "Feature",
            id: "500107",
            properties: {name: "九龙坡区", cp: [106.3586, 29.4049], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XKL@V@XbV@lW@UV@@VXIV@UVKlL@KnnJ@VV@VU@I@@mVUVWUUmL@V¯LUK@UV@UU@a@U@yU@WLUK@X@KUVmL@@aXI@w@ammVk@WÛwm@UxVVVbVLUJVxVUV@V@X@JUIVbm@@Vk@@VkL@lVLUJ@zWJ@X"],
                encodeOffsets: [[108799, 30241]]
            }
        }, {
            type: "Feature",
            id: "500106",
            properties: {name: "沙坪坝区", cp: [106.3696, 29.6191], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XºlUVl@UbVXUV@xVJVzXJVUL@VV@VKn@@Xl@XK@UmÝnKVbVakkVm@kUK@UmIm@LkKULVU@WJ@UU@@VkXU@Wa@@UKWL"],
                encodeOffsets: [[108799, 30241]]
            }
        }, {
            type: "Feature",
            id: "500108",
            properties: {name: "南岸区", cp: [106.6663, 29.5367], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VVJVL@bUVVnl`XIlwXJlw°nnlIXW@UÇĉk@WJkwkL@WVkU@LU@U`W@UXUV@n"],
                encodeOffsets: [[109092, 30241]]
            }
        }, {
            type: "Feature",
            id: "500105",
            properties: {name: "江北区", cp: [106.8311, 29.6191], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nLVU@wV@lV@XllÈKlU@L@@bVKnx@I@JVaV@x@Il@@Un@laVVn@mkUIm`k@WXJmk¯mkxWIkxWJk_UmVUUK@UU@@l"],
                encodeOffsets: [[109013, 30319]]
            }
        }, {
            type: "Feature",
            id: "500104",
            properties: {name: "大渡口区", cp: [106.4905, 29.4214], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@k@@U@w¥WKkVkImUmwa@b@xWJ@b@nKVU@L@WVLXKV@@z@V@bVVU@@VVL°K@U"],
                encodeOffsets: [[109080, 30190]]
            }
        }, {
            type: "Feature",
            id: "500111",
            properties: {name: "双桥区", cp: [105.7874, 29.4928], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@WwUwU@kK@KmbU@@V@XlJ@znWlXV@XK"],
                encodeOffsets: [[108372, 30235]]
            }
        }, {
            type: "Feature",
            id: "500103",
            properties: {name: "渝中区", cp: [106.5344, 29.5477], childNum: 1},
            geometry: {type: "Polygon", coordinates: ["@@VL@VV@VL@aUKIUU@@JUVU@"], encodeOffsets: [[109036, 30257]]}
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/fu_jian_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "3507",
            properties: {name: "南平市", cp: [118.136, 27.2845], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@knyk@KU¥wV@nkWzUmk@@lKUa@aVI@UKUamKUUVaUI@X@UV@K±IUVVlUbUbUL@KWUXmWk@KkXmmkÅKUa@amUbkUkKWUnwUÇwVUUÝUKV£U@nKWwXLVKm¥@wUXkmWk@@wX@lU@yVImaXwV@knU@mbk@mlUXmU@mV@n@bnW@bUIWJImVUKWbUK@nkKaU@W_VUUmWmL@UU@bUWUL@V@bmVUz@`mUUVVbXL@VL@lmLUxmVamXkW@xWbUVbUxkU±@ÅUmmkLUbW@@`kLknVlV@lbXxlVUXVVUU@UbWkIWVUUUJkI@llbUxVL@VVUU°ULUmWXUV@VULWb@xm@UaVLVKUa@w@VbkmVambUUm@@VkK@@bxlxX@n¤@X@@lkLWV@nVkb@bWJXLWx@nkxmmbXn@VWVUn@VnJ@bVXl@VJXnWbX`lLUlJVI@@VXV@Vl@bn@@Æmn@VxXU@mVIlxVnIl@nVJaXI@mlU@aXkVm°klmnVV_na°@V@xÜ¦XKVnnUlVXbVKLXKV@naV@@VVl@@lXblXWnLlbVK²n@@VLUnlV@lXxô°V@UnaUUlKXLVUVVUbVVlUnJVX@VW@an@lb@nl@VU@anUVW@kaUm@InVVKVU@kUW@Uam@km@kVa@a@nwU@WlI@mVI@WXaW_n@nlkkW@U¥@kV@Uw@wU@@IXK¥VIn@nU@`@Xl@VVLnaWbVaUwnU@VIKlV"],
                encodeOffsets: [[122119, 28086]]
            }
        }, {
            type: "Feature",
            id: "3504",
            properties: {name: "三明市", cp: [117.5317, 26.3013], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lL@Un@VVnabnUla@Ux@VbULUKVbn@w@XaVK@UVUXWVnVKV¯VU@UUKVwka@klJVIVVXUlJXVaV@VUUVWkUWwkaU@UklmlK@_X@ValKnnÆV²@lVVwUaVXa@wlXnWbnUVwnK@kK@UWKUaVUnV@_VynU@a@UVKVXaV@@VnKnXVVUX`V@blL@mVLXaVLnUJXIVJ@amX@a@mnUV@nVWnkl@naV@ml@@KmKUam@UU@@UlKUVkUK@aVaUwVU¥UIkJ@wmI@mbkwkVW@UXKULU`IVKUa@LkkVmUU@WlULUWÅU@I@WWnU@@w@a@Uam_XyVIVWkk@mwVKXUV@nwVXkWÅU@aU¯KUnK@¯mULXVLnWVbVbUVm@Ub¯¼W@am`kbamLUUUaUXV`@x@XmJ@n@L@xkJUU@kU@mWm@kUUwUUVWl@VUkIy@kkaVUUmIWVXbWxU@kmVkK@nWVX¦WxU@@bkx@VU@Wk@kUbmJUUmkUW@_kKWK@knV¤kIUKWLUbV@Wbk@@VWL@VkI@lUXVxUVU@@mWIV@a¯nUaaUV@Jb@bÞ°VbU@XaUVmL@VXblnV°n@Vnx@VUUUlK@InJVb@Vlnn@VL@VWJUx@XlJUVVVl@LUUUJ@L@lUL°¦kVVnV@xVl@blLnlLVaXll@nVUn@xn@nml°X@lb"],
                encodeOffsets: [[119858, 27754]]
            }
        }, {
            type: "Feature",
            id: "3508",
            properties: {name: "龙岩市", cp: [116.8066, 25.2026], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@aI@VUbVb°m@bUXJ@nV@VUUwVW@klJ@UXK@Ul@Xa@UVaXKVLlJU£lm@XLlL@`VXnlVVnIVall@XV@@Ulw@aV@XwW¥XU@mlLnUlV@XwWaXUJVnUVlb@lzlJUVk@UXVVVxlVn@nXV@@lVVlI@w@K@mnI@W@wU_VWbVVVnKbla_nbX@°»Van@VUUaUamXUKWK@a@Uk@wWkXWW@wUUKw@_lywUkU@@U@kamVmXaUVUka@Wk@»UUUVKkbWUVUbk@mkxkKnIVUmW@kUKmXUmVaU@kU@m@KUWVkIWJ@U@UI@wUUUa@KW»nU@mVkUmm@XwWU@UUmL@w@mnVUU@aWak@@amxU@UxULWVXbVLU`mbUImVUbnV@@bVn@bnVWxLmyUbIUK@aVmakbVUXWUlKWbkV@WLUlk@@nbb@lkKmU@UIWJkw¯UUVVxm@@XkbWxXKlUzWJkUUL@bmKkV@@VUIUlWV@XK@VkbWx°xUb@LUbk@@VWb@LXJ@VWXU@@bUVVVVn@VVlLn@l@xk¦Vx@bVJXbn@JlnXxV@@nJ@X@V@lmxbUn@xVL@VVKlL@lnLVaVL@xkl@LxVl°XWVXVlJWnxlJ"],
                encodeOffsets: [[119194, 26657]]
            }
        }, {
            type: "Feature",
            id: "3509",
            properties: {name: "宁德市", cp: [119.6521, 26.9824], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@LVKVaVaUkVU²J@LVU@@WVJUbVVnLVbL@VUJ@bVbkL@l@VnyXmlU@xV¦L@lmz@lnL@bVVbVb@lnKVkVl¤@zXV@l@XJVLVKnXVKVnU@wUm@KU@UlVlw@U@U@UaUKlU@kXKlmXIWKXaVIVUVK@KU@@kJVUnLVJUL@VIVa@VnLKUnl`VbVV@Vbn@Vzn@lKnVlIVVKUalkXJl@XXVWVLVUUmVU@Unm£lK@Uk@WUXK@U@WVwVkĠkĢÇ°aUÅUwmaţɱUÇaw±V¹XalKôx@UVaÜʓͿVóbÅLJm¯Vk¦k@mamXkKUULakbk@mV@LkJWb@VkmXk@UVmaUV@amLUKUamI@KUaU@WbU@UUUUIWJUkm@wKkVJm@kxÇVUK@mUVUkmlkkVm@amwLVWU@UbVLkUb@VmK@XaVWU_VJnwV@@kUmWakx@kwWakIWxnbUJz@kVW@@x@XllnVW@xn¦ULWKXxmL@VU¤VLÞVVUÈxVmxXVlLlVanV@bbVLlÆnnlW@LXlWnXV"],
                encodeOffsets: [[121816, 27816]]
            }
        }, {
            type: "Feature",
            id: "3501",
            properties: {name: "福州市", cp: [119.4543, 25.9222], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lxna@nJ@xlIVJV¦UVxUb@bLVUlVkL@V@VVn@VbLn@LUlJXblx@lwXbVn@lU@mxUIV`UXWb@nLU@ValUKVaV@UXKnxbn@lUkllnUVnV@VLUÈlwn@UIlLxn@VlXIVJVVVV@XaV@Vb@LnJVbVLnK@bVUnbVUl@nWl@UXalI@KnUl@labVKVlLnWnbl@l¥°UnIÆKôa΀Ua@UUwÇWǓIUWUÅVkƨm@@£@KmLU¤ULˣJkUVǟUUķ@ĉVKUk@Ñ°wôÇç@īé@Åţ¥mīÛkm¼Å@VķVó°ō¦U°n@bVJXVVL@bUakLmx@xmxXzW`XbWnXV@bWLÛ@a@aXbWVkaÝwU@mlWKkLWWkLUKULW@kVmVUUÝUamV¤n@xUVUzkJV¦lJU"],
                encodeOffsets: [[121253, 26511]]
            }
        }, {
            type: "Feature",
            id: "3506",
            properties: {name: "漳州市", cp: [117.5757, 24.3732], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@bl@Xb@bVVUm@nx@nKVV@XVWxn@VnUl@nmVX¼@LVbVV@xVJV@@XIlJXUV@Ln@lVV@UbVnnWVL@lnXUVmJLlwnll@VaUXVlaLVUVV@¼Xl@lbUVVWbnnUlb@@VV@aVUmlUaUny@kU@Wkk@WaUVk@@ammk@@U@UlU@aUa@wl@mXLllnLU@anVnU@L@VVV@KlXnWVnVanUw@w@wmnÅ@waUam@UkmUl@@aa@U@¥kôKwÈ¯°w@ŻkwǕaKÑÛk@ĕōřċ£ĵUKW»kÅŻLU@Ulġw@¤VzVUbkKUbmLmlULU¼UxmbXl@bWVb@bUnVUVbULU@@VkbVL@`U@WX@XV@b°@b¯@¤@Xm@@b@`UVVUL"],
                encodeOffsets: [[119712, 24953]]
            }
        }, {
            type: "Feature",
            id: "3505",
            properties: {name: "泉州市", cp: [118.3228, 25.1147], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Vlxkz@`xLVV@xXXWXl@xl@V@bnV°@LVm°LVbV@ÆXWlUmxU@WVULnx@llUXUJWzn`Vb@@b@xV@mXX@@JÆVVXVKXkV@nVlUl@KVbULJV_VKLVWX@lUVkIU¥lIVyVU@wm£nUVWU@am@UmWw@UX@@amVUn@@aUUlUVanaWUXWmUnkK@VUlVVUUw@XLWWXma@knmbVbVXbVL@XJlInlLwmXów@çV»ÇçŋaķƧóƅóKġ°nÅUķƑUÇW@¯xÇ°öÆlVn@lla@Lb`@VXVVx@V@bULVJUkÇ@¼XUKk@mmULkaWbk@x@UkL@a@K@U@UmKmbU@kV@UmVUbUmmXkW@LUU@U@KmVmU@bVmKkkWKnk@@xVb@bkV@V@Vl@nn@bl@VUXbl@XlV@@lmzVVbknUVb"],
                encodeOffsets: [[120398, 25797]]
            }
        }, {
            type: "Feature",
            id: "3503",
            properties: {name: "莆田市", cp: [119.0918, 25.3455], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VbÞVVnUlUX@VKVLlKXXlKXLnkV@ÞxlbXUWab@bÜ@XK@aWUXmWaX_Wynw@wnwlKbV@aUKWUUI@amV¯Ŏ¥ô¯ĸUUÆ@n»¯aƿé@ţ¯nĉĬÝKóó@ÑU¼@èxWônxKmkkJWI@UKWaUUaamn@lnbWXXWK@VxUVkUV@ULmlnVWXXVmbUbkVVV@bm@UVn@bW@@VXxn@Vn@bVUX"],
                encodeOffsets: [[121388, 26264]]
            }
        }, {
            type: "Feature",
            id: "3502",
            properties: {name: "厦门市", cp: [118.1689, 24.6478], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@VlUV@nanL@V@V@L@blK@Vwl@XalbVKnnl@VLW»È@lVUIVK@a@UUwWUU@_aK@bkkm@UkõÅxóLl@¦@Vb@bk@VnVln@Vbb@xmÆn@x@xx"],
                encodeOffsets: [[120747, 25465]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/gan_su_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "6209",
            properties: {name: "酒泉市", cp: [96.2622, 40.4517], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÇnÅaĉ@U¯¥UŹ£WUýUU±JkkUwyÞIČxĊĕĊ¯¥ÆUkţUÅÓ±¼IUx¯UÒƑÝÅ°KÝnğ°ÅU@@Vn@þ¼¯WnŎ°XLWlnVnbWnVXxmbabóUlǕUUaIUmlU¥k¥ĉwkkÝɛa@¯U¯°mVkVnKlōÑÇÑU@klUġkUŻnUW@¯k»mWV£UKnUmUww@UIVaXwm»Èmmwn¯ċ¯LĉUJUalka±Va@Uk@ÛÑ¯WmnUaɝ¤Ûmn¯m±x@wóxÛLġÒUx¯VÈJUbózÝÇKĉ¯ōlÝUÅWl¯nťbÝ@¯ǩLġmV@Æ¯ĢkÆmĊkVťLɃmÝXó°@ĢbVóVÝ¦ɱ@ƧaġUVĠÇÈV¼UVţwmbJÇwˋaXmÇ¯KkkmbXm¼V¼ǬŚ²¤ôŰÆƴô̐ŤǪnɆӨ¼ɆLÆłUĊxŎƞȘǔˎǬǪnƨŮǬö°»ġÞÜÆĸÒĊǀbƾèôÈ@¼¯þŤĸƧ°VĀ¯b@lÈĊʠń̐ȘKǀֲॗţÿǕý@ʊǓƨóÆÑǖŃôw@΋ʈƆÅÈVVĊVóĊÅ@ÞƒĬV@Þī@°V@ĸĢ°XτƜĠ@ÈaÜ¥ŐƅnğóĕVġUůƿŋĕa±VUťÇğÑ"],
                encodeOffsets: [[101892, 40821]]
            }
        }, {
            type: "Feature",
            id: "6207",
            properties: {name: "张掖市", cp: [99.7998, 38.7433], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÈÒŎÒkmLUlU¯nV°@°ɜbÞĠaÈ»ĸlLVUÈ@Ċ@ýUm@@ÆVĠ¯ÞmLÆ¯ÞƒÑ°VVwJ²»ÆÔVlŤÅV¦ĉ°ĉĖċwÝJzVxll²IVVVþX¤źV°¦VĊ@ÆbÈmǔLĸĠ¯Ģaô¯ĸmÆÛUlÇĸk°XyĊUǔVǩnmV»a@ýnK°n@l¥@»żĊ¤mç@£ČU@mmVkÞUƐ±²¹°ĠwÅƑŃU¯V¯aÈŁÇ»ġn_°xŎKlxklx@Þw@Æm²bÇ²LlkWXať¯ĊaÑK±w@wUÅçV±Uk@@¯¯xU±±UU°ōxVxÅÔō°ó¯UÝ¦óbÝþ@ĉÈóUVUx@VUVÝwÅÈÇóVkk¯JÇkmmL@KÇx@bk@U°ķ²ó`mn¯°UwlÅkU`¦ɛôķz@ÅnÇ°U¼¯KmVk²J¼ƏÞķô¤UL@mnğ`ÇnUxÇ@ÛÿU@kŻ@x@móJkÅ¥VŹĉóÒĉlċ°ķUƽÜ@x"],
                encodeOffsets: [[99720, 40090]]
            }
        }, {
            type: "Feature",
            id: "6230",
            properties: {name: "甘南藏族自治州", cp: [102.9199, 34.6893], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÞnKlnwX¥WÝXkxÞUn°aĊVnUUKlÞĶWXnĠ¥ô»@nmVL@¤°VzJanU@aÆwna@kU¯yX_aĉbwéXkWwÅa¯V¥m¯UI@@mb°aÈçU¥@»knwɜƇ°I°ÑÈmVU¯Xa@wW@wV¯Č¥l¯Uwnm@kaUaóKkk@Çab@ÒWa¯IÇxÛam¼VUxÒl@zÝÒ¯bÝaĉVĉwÇWzJmJn²mÜ¯U¯ĉ@ġ¤Åb@²nml@@ULVxVU¼Ålmab@°l@WIU¯@m@ó@UzţyXÇUÇVUUVLkbWakVWmUbkkKUÆ»n°Knk@aUVmnk»l¯Ģlw@_kKVU@na@lUk@¯¥mV@kmbWb¯Åõa@mkU@kÇkU@`@óóbl¼Uxn¼lVÈx@blVkVVn`XÈġÈ@ÇK£ÝJmUUnUĖmlUmKUnVÅaUwUĉ`¯n¯wW¼nxV@bĉnkIċŘkXU±ÒxÈ@X°`lVIÈ¯ĊVVVan@VaUVażVmblkÈWWIXaalL@wVbV¦lL@lĠnÒUnkL@ÆÞkÞKbñþW¦ÛċVULUºkÈlŎUxÆxÞUUxÒx@XbL@lÆ@ÒlXVln@bm¼J@Ånx@bnĠmxVXmbÈè@Ċ£ČWw"],
                encodeOffsets: [[105210, 36349]]
            }
        }, {
            type: "Feature",
            id: "6206",
            properties: {name: "武威市", cp: [103.0188, 38.1061], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@±¯¥@klwU»ÞÝmwKm¯ç@kVÇUL¯lVUKġġm@a@U@X£°l°LŎÇ@aōVÝwÔKUÅWJ¯lm@ÛVWa@klĉUmaLUanak¯J±KkXóÜÅx²Ç@nUÒĊb°@ÆkLXÇÆ@xÝnxWxţ¯¤I@ÆnVVVlU²ÆèV@x²xLÒĉbŦ°WbXklÞ@l¤XĊ`wl@ĢÈŎm@bnVUb@ÈÆÛLèÇUÒÅ¦lĸ`°ĮʟÆǓbĉôϚĊÆĢnŤé΀ÑĸĀĊ¦@@l°l¦Ȯ¦ɆÞĊKŤĵĸů»mŁyġķŭ@Çɱȭ¯mƧUĊķnŁŻ»UaUƛɞÝƨů"],
                encodeOffsets: [[106336, 38543]]
            }
        }, {
            type: "Feature",
            id: "6212",
            properties: {name: "陇南市", cp: [105.304, 33.5632], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÈÞ@l`UmV¼@nnÆwVlnVVaLVÈ_ÿÞ@naxÆ@l_@VxnK@llLnxmÈŎJnbUxI°l@n¦lÈIlmX¥k°@kJk²é@klaUaVaU@@ÝnIWnmnxkºÞaV°V@nwKxôbÞ£VUbþLn»mVwIJ°@nb@°°IġUkÇKV@Å¯»lLnm£@anK@ÑÜn@»mL@£ykUUmbUÞÝ@kyÇbó»XUxWVzb±mÝbXawUamL¯»@wUKVwm¯ĵJ°ÅUWVkKVk°wÈVVÑlU¥kmVamknUw¯¯bċ¥ÅKkKkVċVk£kKVwÑa@kóyÛ¯ÇVkówXō¥Ç¼ów¯U±k@xIĉÒÅVmÈnÜ@n°bUbÝVUnnJ¯Į@m¦nVÜ@L°JXbÑ@aÈb@llôLVbb@lmnVxk°ċ¦U°@xX@xWb°UVÇn¯Ò¯Jɛƈmxl@¼"],
                encodeOffsets: [[106527, 34943]]
            }
        }, {
            type: "Feature",
            id: "6210",
            properties: {name: "庆阳市", cp: [107.5342, 36.2], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@kwĉ»VamV¯wIóVkl¯KmVō¯ÝWkL@bÝKō¦@@Lx@b@la@km@@l¯nm@UaÅ@óWUXm¥nw`@UUxķôÇ°ğ¦@VJ_nIVnalxkXJWn¯nVLxl¤nnVbklVX@xnxmV@bUK@nm@@xV°±aÅnkUWnUax@mn@¯LmUĀlU@lV@blLUblxklkIÇx¯°UXbaVUnV@°LUlnbX@`°nVmbnÆmVkLmK¦U@Xy@kl@U°K@¼XbW@bWnLVaVVz@xlVČ¥lbUxÞlVU@nÆWôn²VJlUƧLnmÜLXan@mw@wlUlV²mblwVÈlLÞ±@lVnUlxnkma@mkJ@kXVU@mn@¼VXUVlLnmVbôaVnWV»ÈUl°È¯ÆInÆU@kk»mKkÆġk¯@»mk¯@óÇlÇ@VykklUml¯Þ@w"],
                encodeOffsets: [[111229, 36383]]
            }
        }, {
            type: "Feature",
            id: "6204",
            properties: {name: "白银市", cp: [104.8645, 36.5076], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VKUÈl@è°nLnxÝÞV¼kx@l¦²°ĊóĠĊ»@ÈxaĊxlwÈVŤa@¯²aÇ£Jk£lnUÞ@°ô@ywl»lIX¥Ǫnw@ÑÞWlaÅlL@Uwĉakl@¯mwna°JV¯nUVÓÞÑm£²óWaUÇ@óÝUçV»ÈkkW@¯xV@XlK@wX@Vmm_@wÈÝKU¯ÇwVwÅK¯VkJXkWVaIm¯UkÇlVĀV°mxók@¼óWxĉÜU@UbzÛJÇk@ÆnVlÔ@kxô@ĬWL¯K@aÛImm@IUa@UÇêU¤VÒÇx¯ÒVlk@Wbĉ¦UbkWV_y¯Laók@b@nmbkx°"],
                encodeOffsets: [[106077, 37885]]
            }
        }, {
            type: "Feature",
            id: "6211",
            properties: {name: "定西市", cp: [104.5569, 35.0848], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@aV²wVJV_@LlanÅllŦçÜÓ_lnWaôkxUbmV@È°lènk°l¦`@nnL@ÈlÜIyVaV@ĊÛXwô@»lônwU¯ÿUÈkl°VnJUblXWIl°UV@aVVVmnL@lUUwmk£bV¥VUVwÛlaÇÝÞmk£LUy¯L@WlkKW_XaWmġU@akakXkmVwmŹVUbWónmwnWW£KÈnV¥¥Æ_klWbU¯V°aôbnaVwmaōInÇmwkK@kmLUw@`kÅ@wb@mÝĀÇ`UKUbmUUkÅxmm@»nUVk_Ý@Ç¦VÇè¯ban@@JV°nU¦°ÆbXxWlêxĊabW`zV°@lmbÅx@bmVbI`¦@ÒUVUI@ÆL@b¼@@lmxnL°ULÞğÞ°kLUL°xVnKVl@zX@"],
                encodeOffsets: [[106122, 36794]]
            }
        }, {
            type: "Feature",
            id: "6205",
            properties: {name: "天水市", cp: [105.6445, 34.6289], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@UyÈVVUnn@VU`UblzJnk@VbKU°lwW°nkVUÈl£°V@n¥VklkU±Unlw¯UkwmKUlmkUmnkym@Å@UmWÈU°l°anlJkUKlU¯Èm@kmWV»kkÝLUWUx±b@¯ma@¯IJUxnm¼KýaVUÝ¤óawLmxU@¯UbÝ¹lmwmnXmJ@ÞV@UbVbkbl@±êlIl¯@lW¦knÇJkm¥k@¯Jmbóa¯bUV°akXlÅ`¦U¦ÇmLX¤mXnxmôXaVźUnUxlnlWbl@bĢVnXWbX`lLXk@°KVzKl¤nÞÝÈkbÜ"],
                encodeOffsets: [[108180, 35984]]
            }
        }, {
            type: "Feature",
            id: "6201",
            properties: {name: "兰州市", cp: [103.5901, 36.3043], childNum: 5},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@lW²L°IlmbVbKnbĊVlk@XbÜU@kn°XIÆVLÓÞxŎUlôb°KzU`lXVaĊ¥Xal@kU°ÑÈwUÑV£ÈéV@VbJ@nnÜJ@bL°XK@īówl@kÓmUÅmK@m_k¥l¯mkçÇ¯@nUaVwólXbmk`ÛÔťèkkmÆkbK@U`UI±xUbWlXmbVbÅÒólkIWJk@zKŻ¼@xUxó¯LWb@ÅÒ±¦U`nbťĀUVbLU"], ["@@¯lwna@mōÈ¯K¯kW¤@@V@bĢnĢVLU°k"]],
                encodeOffsets: [[[105188, 37649]], [[106077, 37885]]]
            }
        }, {
            type: "Feature",
            id: "6208",
            properties: {name: "平凉市", cp: [107.0728, 35.321], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÆLUxÈxV°LÇÞ@xn`Ü@X@nĊÆwnJmwUxaUkw@V@waVmlLXÝl@XVĢmV°@nl@UUUWK@wÿVI²Òlm@nÝĊýVV@nJ°Ułm@kV¼nKĢÈ¤ôKblnKllVk²aĠ¥È¯ĸóVw@V_xmn¦VWôXÆ@Vbn@°m@kn@@lb@ka@wK@@UlKVaWXW²¹lÓw@_°n@@_lKÅķW@mLUWn»Û@l_Ç`Ûmm°ÅbWb@VWbUUKÇÅaġlmkUġl»LlUm¦@¯U¤ÇkVUml¯Xx¯kVLUa@mlIkyVa_UV@mmUVUÇVzUxUVU¦a¤lnVxVk@mKUnUU@bU", "@@@ż@mlkġk"],
                encodeOffsets: [[107877, 36338], [108439, 36265]]
            }
        }, {
            type: "Feature",
            id: "6229",
            properties: {name: "临夏回族自治州", cp: [103.2715, 35.5737], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@ż»Ly@lXIJlôkÆÑUanaWXkW@yk@ULmUw¯KVlK¯ĠÝÝVK¯mKnwk@@»@aK@ÅVJVU@Ñ¥_Uy¯@£UKmn@ó¼ğ¦WmĵXÝkVLmVĉU¯bmÝVwWlXÞW¦xkmmLÝ±U@VÞ@ÅÈW°XÜ¼ƨyUĮnWnXÝxUx°lVXJlôV"],
                encodeOffsets: [[105548, 37075]]
            }
        }, {
            type: "Feature",
            id: "6203",
            properties: {name: "金昌市", cp: [102.074, 38.5126], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ĢÈ¼Çł°bU°VƒńÆǖŰnÆōĬǔaʠÅ¯ĭ_kķÆ¥VÑÈçÜKÅ@ÇVaUm@aōnġÇk@xĉ_Wk£@Ý±KÈ±aÅn@Ýx@kwlkwōL¯wm`"],
                encodeOffsets: [[103849, 38970]]
            }
        }, {
            type: "Feature",
            id: "6202",
            properties: {name: "嘉峪关市", cp: [98.1738, 39.8035], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@llĊx¦l¦kVVnJVbǖVkôVabnaWwUXmmamUXkWKō¯Xm°»ĉÇ@UVKķkÇ¼ğb"],
                encodeOffsets: [[100182, 40664]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/guang_dong_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "4418",
            properties: {name: "清远市", cp: [112.9175, 24.3292], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lÇ¯kÿaV¯VaÈU¥ÆÇIlxmnbUxlUôl°kWl@ôVwUanUl@xVkaX¥kU»a¯±@kka@UwmUkwJk±k@L@ÝWUwVÝxÇU¯ÇX@mÅ@@yĉ£VmUwȗ»ÇUnlUnWU¯`Uk@@x@bÇxX¼VV¯LĀkÝL¯@VĀ¯lnĊW¦kVÇôkUÇUK@ţU@aóÜUU»@¦k@VxKVbn@Æl@xbWnlUlxÈlVÈ°Æ@¼@xWxŎVK°¥nÆkŎ@ÈÑmK@¥k@ô@nôV"],
                encodeOffsets: [[115707, 25527]]
            }
        }, {
            type: "Feature",
            id: "4402",
            properties: {name: "韶关市", cp: [113.7964, 24.7028], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@WXk±Ñ@UwmUwĉwlmn@Æwn£mkI¥ÇÅ@¥aón£nWWw£V`Þ@nVml@xô¼IV¥kUmkamUkVWwÛ»mó£UVÅKmn@x@kbmm¯aXkaVĉaUbÝ²lIlxnVVx@lb@l²°bV¼lW¦bUlwk@mVVbUxó@kX¯lókVkwVmankwJÅÈ¦ÇVUbU°blĀ°kÈ@x¦ÆÜ°@°¦óaVUôlUlbXl@nÜVnKlnIVÞ°W°U@bnm@¥IV²Ul°VnalzXyl_Vyƒ¦lLlx@ÞbKmknVWanwÑVwČº@n_ÞVaVÜIl@KÈVJ@a£È@@kmaV¯W@_a¯KmbkÇkLmw@Å¥"],
                encodeOffsets: [[117147, 25549]]
            }
        }, {
            type: "Feature",
            id: "4408",
            properties: {name: "湛江市", cp: [110.3577, 20.9894], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@kX@aUUċlkJk@wVJXUWk°W@nKnwlUl²blU@lIl@XbWxnm@lW@wwUJX¯VU°`ŎóˋkÝÝkÅ@ÇmğÈřmwaĵVxUÛ»°ĠǷnýmóX¥ɅĵҏÇ@°²ĊUĖ±ĮU¤Ç°Ā¯ɐnżUĊĊĬV@è@ÔÒU¼l¤nĠbêVĠ°ÈyzVaVnUÆLabVlwÆ@"],
                encodeOffsets: [[113040, 22416]]
            }
        }, {
            type: "Feature",
            id: "4414",
            properties: {name: "梅州市", cp: [116.1255, 24.1534], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nÔlW¼x¦@lVllLkèa@z¤Ė¼UxlnUKUbÝlU¼lb@VxVklJÈwV¯@ĠlÛĖnbkÆźÞUÈôklmL¥LWnKUkVa°Vx@IVV@x°bUkaa@mV@@ywLÑUwVUVUbÞVVann@XwÇÿ¯²aVamkXaÆ»@»nw@¥UXakbWa¯KUw@¥m@kwmLU»UUJ@kmU@UUWU@yanwmçÛl¯¯UmKUmwVkmÝXbW@XWÝbk¯@±w@»U@W¯Å@Ç¥UU@IUakJĀê°þXkam@_J°m@X"],
                encodeOffsets: [[118125, 24419]]
            }
        }, {
            type: "Feature",
            id: "4416",
            properties: {name: "河源市", cp: [114.917, 23.9722], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°VlmX¹laĢÒlm@V£@¦Ģklynn¼lW°zW°VbÈV@lÆbnnJkXVÆaÅW@UUw@kaV»ÞkVaVLkmVw»ĕ£@yblçkKkU@k¥wX»kmÓ@Wn¯I`@nlbWý¯éÿlI@XUmWUw@@UJUÇmKUV@xţk¯¯LWnUxK@Å±»Vwa¯@¤WX@Û¦@¤ÇIÈ¼WxX@WxwUnVbÅèmVa±²UWl@klÈ¤nôÜ¼XxlUnVlbVnlU¦Jó»@wnkmUÝ@U_¤XxmXm¤ôb@¦ÈÆ¦lJn"],
                encodeOffsets: [[117057, 25167]]
            }
        }, {
            type: "Feature",
            id: "4412",
            properties: {name: "肇庆市", cp: [112.1265, 23.5822], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@l@¥@V¼VôÛ@bV@ŤVLÈlVÈólUX¥mĉ°kÿU°@ÞKlÿ°KUUW»Èw@aw@@nm@w£kÓVUVnKk¥£Vam@nkKkbÆǫmakmLU¥UmÛwmVUmUJÇaUxÇIn`mb@Þ¯b@nJ@nlUVlVULW¯Û`Ç_¯`m¯IbĉWċzx±Jx¯ÆU_k@J@UmbXôlLn¦@¼ĊxlUXxUbLĠUnVĊwlUb@lWXm²@ÞWxXUnb"],
                encodeOffsets: [[114627, 24818]]
            }
        }, {
            type: "Feature",
            id: "4413",
            properties: {name: "惠州市", cp: [114.6204, 23.1647], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lbW°bnnla@@wnmÆLVUkÇl@XkV²±bnUÆçUaVmxXw@WXwÇ»ÈJ@£Ü¥@XW@£°bUx²¼@ÆLVwmX°K°Ťl@wVUnLÈVVIky±wkKU¯ÅkXġÑÛlwUwlm@mnKWaÅm¯óÇmğb¯alĉUwķbmb@lÞÒVnmĀŹ@VbVUnmakLm`@xĉkklVÔVJVnlVUnmJmaLUblzmkLaō@@zV¦UV²kJnÜU@VXUL@lJL@bÝ¤UnVb@xVnlK²Vx°VxlIlkVl²k¤@n"],
                encodeOffsets: [[116776, 24492]]
            }
        }, {
            type: "Feature",
            id: "4409",
            properties: {name: "茂名市", cp: [111.0059, 22.0221], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@LnÇlkKnkÆLUmÈxlUJló°n@ana@@X_@mÝóóU@aaU¯mL¯kV¯ÇVwkw@V±Ŏ£@@alw±Vk@mÅm¯ÿÅƧIÇ`ōô¯_UVW°IVx@xkX@mnwXWa@kkJ@kVa±kkVmxmL@¯XXlWVUI@xlIklVČV@blW@@nUxVblVxkôlxnynIÆ»Æ°aXwlKbVnXbL¤kLèVV¼²IlĠVXynz°KVx°@VlLlblK"],
                encodeOffsets: [[113761, 23237]]
            }
        }, {
            type: "Feature",
            id: "4407",
            properties: {name: "江门市", cp: [112.6318, 22.1484], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lUXx°JWnnÆXVWX@ºVLV¯nUVnbôxaXmWXIUb°xlKl¯KxXÞ°XÈ¥Ü@ĉÞUç»nóVmax¯UÅU¥Ý¯@ç@ș@çĉÅUmUç±ĉKÝxÝ_ÅJk¯»ó¯nmèkǀWx¼mnUÜġ°@¦@xLkÇaVnUxVVlnIlbnÆÆKX¦"],
                encodeOffsets: [[114852, 22928]]
            }
        }, {
            type: "Feature",
            id: "4417",
            properties: {name: "阳江市", cp: [111.8298, 22.0715], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°nKV°b@bôVÞô@nVlÒôÆUnlnn@lmkmVkaÈkÆÆk¥ÅÞ»ÆKXkW¥ÅLmÅkamJUkUVwUmÈblKw@@¥Ģ¯VÛnm»Xwlƿ@kbWaʵ@óLl¯ƽ@Ln°Æ@nUl²kxb@@ō¤U²@lxUxÈU°l"],
                encodeOffsets: [[114053, 22782]]
            }
        }, {
            type: "Feature",
            id: "4453",
            properties: {name: "云浮市", cp: [111.7859, 22.8516], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@VIl@`V°Åw²IwČyĊXa°Jn°_È`Ü_°XKVkUUVk@mmI@°a@Ýnam_ÈJVwlĉX@lUómaUmVU°UK¹@WXUWmÅXm¯IWwkVWlÅLÝ¼Æl¦ÅÅÇlbUllnknm@kmVmóÅkÑUW`@@bmb@¯mkôIkVÇwnVÅKmlLklmÈKVĊK°²`n¤nUbWlxVxLUx@°nXm`VklVxmnnx"],
                encodeOffsets: [[114053, 23873]]
            }
        }, {
            type: "Feature",
            id: "4401",
            properties: {name: "广州市", cp: [113.5107, 23.2196], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ș¼VxUnĊ¤@z@Æ@nÈW°ÈVwUÞVxÞX@Kl@ÞVaĊbU@ml£k±lUkkJw¯UUw±kLUm@waUVmÞ£@aKkI@KVUW@ÛVmlIU±VU¥@yğzƧÇƽĠřÅnī±m@²¯l°@nÝÆóUll@XnÝVU¦mVV°V¼Jnb@°mbn@²¯¯wVw@@nmxX¤¯L@VLUm@@l"],
                encodeOffsets: [[115673, 24019]]
            }
        }, {
            type: "Feature",
            id: "4415",
            properties: {name: "汕尾市", cp: [115.5762, 23.0438], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@@VxnXWV@bVJV@ÞÅU¥Ċx£UWUwÅUU¥WVUkĊÇnkV`°LVwnU@lbĊ¯Vnal@@çkUÝ¥ġaó¯ÅaÅLŻÆUýmy¯ó@ĉÆóȯwÆXbmL@nknVxkxÜĢÒWÆlV°Ll²xlz"],
                encodeOffsets: [[118193, 23806]]
            }
        }, {
            type: "Feature",
            id: "4452",
            properties: {name: "揭阳市", cp: [116.1255, 23.313], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VÈ¦Æ@X°V@@¼x²°@lÞaWXX@aÞWlnUxVnnL°V@kmĢl@ak@mlk°aX±nwm±²¯JV²@wW_maV»U@m¯ĉUÑJlabVnlĸLlƅÛÇ±wÝ@ĉxó@è@kmbUĉ°ka@mVxU¯KU_mlĉÈVlXUV¦ÆVxVVX¤ĉwV¦ÝÆ"],
                encodeOffsets: [[118384, 24036]]
            }
        }, {
            type: "Feature",
            id: "4404",
            properties: {name: "珠海市", cp: [113.7305, 22.1155], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@è@Þ°V¦VÆ°wnbUÆ»nçÆ@nxÜ¤²llU°VnÈJÞ°UôéķUklô£VVˌKÞV°£n¥£ȗÝy¯¯mÅkw¯bÇĔğ@Ýn¯ĊVğōŁŻķJ@Ț", "@@X¯kmèVbnJ"],
                encodeOffsets: [[115774, 22602], [116325, 22697]]
            }
        }, {
            type: "Feature",
            id: "4406",
            properties: {name: "佛山市", cp: [112.8955, 23.1097], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÈbInVVnUÜxnVV¦nKlnbÅǬlalL@mnUb¤l¦LUmUVlÔ¤@xmnVl°_XVVmkVmÈ@kn@VUK@°KW£nw@m@Ux°x°@±mna@¯amIU»U¯nUV¥ÞUWmk@Vk¯UknÑWÝĊÛ@Ç¦W¯WÝwLk°kL¯wVaWJXWnbwkVW@kĊ"],
                encodeOffsets: [[115088, 23316]]
            }
        }, {
            type: "Feature",
            id: "4451",
            properties: {name: "潮州市", cp: [116.7847, 23.8293], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°Üknèmxbz@VVX@VnV@lIVVV¼nKlxn@@¦Vx°LXblaWbV°£¯W@nW@aUñVwW»@¥ŤÅUÝǓÝóV@ńÇkUVmIUwÅVWÇX¹@W¯bkl@nlb@kġn@l"],
                encodeOffsets: [[119161, 24306]]
            }
        }, {
            type: "Feature",
            id: "4405",
            properties: {name: "汕头市", cp: [117.1692, 23.3405], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@U±°I±n²mx²@WºXÈÆUVxJUnlVÈ@ŃôUǔÞVçn»VyĢÛVm@»kaÝUÇ¼óÛÈķKċ¥X¥Wwğk¯@wķKkUmabkIVÒ°Ċ@nVU¼bn`Xx"],
                encodeOffsets: [[119251, 24059]]
            }
        }, {
            type: "Feature",
            id: "4403",
            properties: {name: "深圳市", cp: [114.5435, 22.5439], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÞL@xbVVK°X°Kô¥Vw@anUèlkĊl@wn_lKnbVmUaUź@nÿUmÝÑ¯Ubk@ÆkxŻ@aÇXwJ¯LķÝUĕóĸóêWº@b²nmĬÆ"],
                encodeOffsets: [[116404, 23265]]
            }
        }, {
            type: "Feature",
            id: "4419",
            properties: {name: "东莞市", cp: [113.8953, 22.901], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ŏ@blKnykVaKnbnIVmUkUmUIUÓçmV@bUxó¦¯LW¯LUUa@wÝKğŚƾƨÈĠy"],
                encodeOffsets: [[116573, 23670]]
            }
        }, {
            type: "Feature",
            id: "4420",
            properties: {name: "中山市", cp: [113.4229, 22.478], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XÒlmV°ôÞÅ@m¯°k±@@aX¹¯VÝÇIUmV¯kk±Û£mw@ÅmèÅ¼mô¼èV"],
                encodeOffsets: [[115887, 23209]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/guang_xi_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "4510",
            properties: {name: "百色市", cp: [106.6003, 23.9227], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lklWXL@VIl@XnJn@VUUalk@mK@kny@UlU@a°UU@VmaU@Ua@UWw@n@KmLm@alkmnIm@an@VIUamWÅImwU@@a@KX@JVLUVmUaVkUa@m@@Ulmkk°UaVUlKXbVwVIkaVmUk@KVk@aaW¯m@w¥laX@KmakVmnUl@nxVKInU@yVaVIV@na°KlxX@@_lmXUV`VIVV@n@lbn@@WUkValK@²yl@VUV@@K°L@KU@@UVaXIVVV@naVkVa@K@UUK@UUaLWaw@m@K@UVV@mVUUVKnLmVLKbVK@UUIkmI@mUIVK@IUK@VkL@WU@mU@WmUk@I@VJk@WwX_@amK@UUWkIK@LVb@mVmakL@J@bU@Ux@xbmI@`Iwm@UbmKUaUWa¯UkJWV@XJUU¯LUmV@ma@kkamKwLUUmWVkkm@aVUUkVKnVVUmXK@UW@km@Ukkm@@W@UkUy@I@aUUmb¤U@kUmL@bmJU@Ua@wkLWWkL@U@VaU@LUakKWbkUWVkKkLVLUV@JVbz@V@VmUU@kVmK¯@VU_VWakVmIUKUaU@@bml@XU@@V@LmKUVmVUKKbkaUXKUL@x@V@l@mxU¦V@lL@V@Ln@@VV@nlKUaV@nLUbmJnL@VWLkbmV@@LWXLlxVVIVV@x@V²blUVmLVUK@kWWXUlV@Xl`LXl@@Vn@VnbV@lVUVUÈVb@@`UXU`l@@XUVm@k@xmVknUJVXUbmKULmbx@VlJ@LVbkKUbVLÇUUVUVmU@VaUkUKVUwmLkUUVVlbkaXmwKUVVU@@V±Uk@VWUUm»XamUbKk`U@UnWW_kKmbUVUVmnUV@nJVUlUbU@UV@n@JmI@VmbnVUXlx¯kKmnVV@L@VbkVUmm@Ub¯LmlUL@VWLkmkLmmn£WmnKU_mWbnbmx@U¦UJU@Xmlk¦@mnUUm@@Jn@lVÔVJnIVWI@aÆK@I@aVKIlÞnnl@nl`nbÆX²l@xV@llbVn²VVl@nnV@IlW@Un@@kVa°KnÈmVaVXUlaVÈUVlwôUlynIVaan@lVXbI@n¥la@K_n@bÆx@XnJVnKVz@`VXVU`@b¦UV@VIlxUnVKXÈbVllbVbnVn@"],
                encodeOffsets: [[109126, 25684]]
            }
        }, {
            type: "Feature",
            id: "4512",
            properties: {name: "河池市", cp: [107.8638, 24.5819], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lLVlbVV@nXVlI@JVXmnW°bIVV@ln@nalVUbnW@kVkÒlbVKn²°bUlV²@X@`nbaUI@°wlU@aXJVI@aVK@wUamIXm@XUV@@bV@VmImnUUwVaVKXUnVK@akVwV@nL@UV`n@@XlnIUJl@X¦V@aUIVm@anV@UwnL@VlbVL@KVVXUWwUUVUka@UVJnUlbnalbVVn@°LV`Þ@XVxV@@bVlUVVbXnWlXnml@XXWVXJmbUI@VllUVkn@@VWV@Vnb@VXUJVnn`lLVka»lVLnw@WV@lInw@WnU@U@mknUVóKwUmUXUU@@wVJVIl@XKVVVbVIJ@Un@lVLnmb@U@Ul@nU°VUVJnnVJV@@mVU@@wkUVwkKWkyUUkU@alkÈ@lJ@xIl@UUWVkUw@Kn@@kmaVUlUULÇUUKl@UUmL@aXU@mlUUwmKkUUVKVUaKUnK@U@Vl@XUWUKlwX@b@K@XkV@UwWJka@aUwmV@U@@U@wUm@»kLWVkIWXnmV@VkbmKLUbkVa@aa@@aVU@aVak£@±UkVU¯VUUJVUI@kxmUmWUbLw@K@aU@@aVU@Kma@aka@_VWkk@UWVUKULWKULU@KUnwVaUKxU@UmaLm@kVmVa@UkmI@@KmIkxU@@KU@mmakI@VLkmWkkJ_U@V@L@nxXbKVb@VVL@V@LUbUlmbU@UUWJUb@VV@@L¯K@LU@UVk@±z@kLUbVl@Xm@akm@U@UUJU_VWkn@`W@kw¯LmbU@UJUb@zmVJULmwk@mVUnlnb@LWkb¦@x°nXb@bUl@LVlUnlbUJUxWakLUVVb¯llkn@V@@nVbUlVbUnVUK@IW@L@bV@nxÆJnXVbUJm@@bnmJnkl@bnnK@Lm@Xx@VVbV@nb@UVV¯@bkV@Vmz@lnLl@kVbUVm@mI@WkJ@UWKkXkl"],
                encodeOffsets: [[109126, 25684]]
            }
        }, {
            type: "Feature",
            id: "4503",
            properties: {name: "桂林市", cp: [110.5554, 25.318], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nU@JX@`XLm¦Vb`lVXXW@VblČnVlanLnmVLK@_Va¥@kUa@VmVbaV@XVVzlVVK@knKVmX£VKLlbn@b@llL@xĊôXaV@°È@¤bnV@@Wl_VU@WnVamwwVbn@KVLX@VmVUxlV@nVV_nK@mI@Wn@@IUĊ@@wVWX@@I°VVm@wmU@m@IUVklkUmmkÅV@@aV@@Wn_UKla@kaVlVanb@k@@KlVn@@aV@nIWWUUaVU@kKmwU@UImKk@UU@w@W@k@UkW@mk_W@Ua@a@¯mV£@mUUam@kWakVama@UUm@nw@alaUmnUlVlIVLVyk£Vm@k@UUJkK@kmKUwKkWK@UXImyVwnI@mkUlkUKkUVmw@kkJWUÈm@_k@@aaW@UUJUwU@@IWKkmUUV@nVl@bVb@bUUXakw@WUkbkKbm@xUlkLm@@wmKUX@UaVWXVmU@@UUUxkmWXkKkUWaUaUbL@`UL@LV`UXmK@VmakLVbkLxUJUIVbUVVb¯KV@Xnl@lVXbmÒnV@L@VWKkVUIWJkIUamUUbm@UkU@JUbW@XWxUam@kbVVUnUJmUUV@bU@UUV@Vk@bmULV¦U@VU`VLUL@xVbn@UJ@nWJXXVVV@bkxVbUxL@x¦@UlXUVVlULV@@nUb@xlnJVnlVknUlVUbmU@bVx"],
                encodeOffsets: [[112399, 26500]]
            }
        }, {
            type: "Feature",
            id: "4501",
            properties: {name: "南宁市", cp: [108.479, 23.1152], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lKnbnU@Ua@KLlJVX@VnL@bW`Xxl@I@UJl@nV@XV@nXV@lK@UVL@JULVJ@nnJlVJ@VULaLUKnmKULVVU@nU`lIXllnK@UlJnb@nV@LV@lwnJ@L@nJl@VUbUn@lnKnbVV@wVLUbxVm@LVVKXLVKVLXU@VllUX@`lb@bnbL@UV@bV@@b@LxKVanXVUUmVUUUaVUkyUUaImK@mUUVUkKU_@W@UVVVIUWUVaVU@UUKn@k@al@ll@bnL@bVUVX@V@@bKnblmn@V_@aUalL@a@akK@kVKUKlwUUnV¥VmU_VWVIVaX@VaalÅK@LVJnalL@LnKwlVUwmX@VXlLUVnblaUmVUVwXU@Wm¯Va@ÞKnw@wmk»UVW²a@_mW@U@IyLVUUKW@@LX@VUV@@yVU@UV@nwUUmJka@IU@mVkaW@UwUX@`@kLWUk@mkUUm@kUUWkUkWxk@@VK@nV@UVaUUJmIkV@UamLUbkVmamLka@kmL¯WI@wJmwx@akU@aUKmbkaW_nW@_U@Wm@a@wkwUKmk@bkbw@mKUkkU@J@bW@kVWz@bVUaVUx@ULkJWbXVVX`@mJUVU@@Lk@WbU@UJlnXlmVx@Ln@b@KLXWJUUW@kaUVUbmV@nnV@n@lVLVmLXmXkV±@kxÅLUbJWIÅJ@ImXalkUamKkkL±aVwKUU@mÞnbWJXm@lbmKULWUUVkabnn@Vl@VVV@VbVbnLWLXJWxXLV@@VV"],
                encodeOffsets: [[109958, 23806]]
            }
        }, {
            type: "Feature",
            id: "4502",
            properties: {name: "柳州市", cp: [109.3799, 24.9774], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@wUaV@nVaUVklmkUUmmIk@waVm@U@VKUkVUkWV@¥@wKVwUalw@aUUUWWXI@mVIm@Ua@wVKUKV_UV@U¥VKnal@U@VU@VV@aVUnVVIVmUUlan@VbXwWX@Va@IlVVn@VanVVblJXIVJlUXL@U@KmUnÑWakU@mkJUI@mk@wUmmUV@JXaWIXWmaUIJkk@WnJ@aUak@kkJ@kUKU_@myUóWUkm¥kUmL@KUKm@k_UmVa@k@@UmU@mm_JWIUVUWLUlbVUJÇVUIVwKUVk@mU@n@lUL@Km@@l@LVzJmUU¤m@UbV²U`U@@¼Vn@x@V@@VnUVx@blbXIVxU@Wl@@LaW@kxLXVWVk@@U@VmLVLbUVULVVlnLVxkV@nWV@bnKVVk@VLVÈVKVVkUnb@lm@@LVxUlVX@VkJ@wkIÇ@kl@blVVVzXllLUxlV@x@UV@nU@UImmUIUV¯mVk@@V@VamnUKkm@@VIUJUaUUWLk@UJUI@xV@VVWVnxLUômVV@VkVVVUnV@UVkL@VVV@bVxla@bkXVJVn`nU@bb@bVL@VnJ@l@VaU@@_lW@UUU@Unlll@XLl@@UX@°bVWVanLlknVV@VVX@VVnUVLmbXJ@nllXX@`VXlmaXVWk@WkwJ@VL@JbnU@bn@@bVKUnVJVIVVVL²a@bV@@Vl@nUVakalmUL@VUL@Va@mXl@nK@UlKL@Vl@@nkllb@Vnn@nVV°lVInwlKXxlU°n@@I@UnVlakUJWkUK@anUWK@_ÞJ@U"],
                encodeOffsets: [[112399, 26500]]
            }
        }, {
            type: "Feature",
            id: "4514",
            properties: {name: "崇左市", cp: [107.3364, 22.4725], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@JVzl@V@Xn@ll@VlnX@@VWLnUVmUULVlUV@blnUlnXVVKxnLlb@lnbU@Vn°KVVI@WXUlI°VXbVVbnLVan@xJ@_nJa@wVwV@@a@IU@UU@WKXwWIXKmKUaa@UUUUk@@UmmalbVUXVVKnLa@knWXImanÝV@VLUx²blKlnLVbklWbn@JÆIXJIVaÆKlw²@lUnWWnKUUK@k@mmU@mnUVaVUb@lVXVXIWK@Lam@@KUwnWkkmVIV@Xal@@KV@VUnI@_UWWUkam@kkm@ka@mk@wkJWIUU@WXkWXkWWLUU@UakLWXV±VIVWUU@anUWaUK@IU@Vak@@UUKWa@m@ak@@wUkla@mUaUklakwV¯¯@WWUkLkKmakLUnV`UxWX@Jkn@bmlakkk@b@l¯bmbJb@VXnbVV@bJUkkKWVU@mÛVUUW@UVUJWXkVkKmUL@WW@UVl@XXKWXJ@XVlmbUxnnm@UlVnV@XVm¦VJb@mLkKÇbXblVkn@l@bWnX`V@@IVV@VV°n@@_naÆVVbUVVbUJnzlVUlXkV@Vlx@XVnxbKUK@b¯VVUVL"],
                encodeOffsets: [[109227, 23440]]
            }
        }, {
            type: "Feature",
            id: "4513",
            properties: {name: "来宾市", cp: [109.7095, 23.8403], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nVlw@VJUIVVUV°lU²V@l¤Ub@bUV@b@b@bUblVaKnLla@UnUWmXlJXUlKV@V_U±Van@V£nVIyU@K@kn@@LVK@k@mnVl@VULUxVJÈUVIUaVkXKVVUXJIn`@nnV@Vl@@UbVnl`n@VL@LnKlVn¦VlôXVnz@V`VL@llIll@Vbb@mIXl@lIVJnbWXXJWb@IUnVVn@xl@nVJI@WU°LUaVUUaVJVIwlKUalKnb@UnLVWU_@KVK@_KVa@VKU¯VLVKn@laaUkU@maVUJ@k@Um@XmbkyVaUIUU@KV@laVn@KXKWUkUk@aWUUVw@aXKmVaUUkmIlUU@wUaxUmmU¯U@WLUmVIUym@UVmUa@wmw@çm@aWLUJUIUamKmL@ax¯¥kU¥U@±kUVmKU_mJUbkKmLÅÇ_@WWUXUmaVUkKUWW@nVxkUxmL@KkKmbUI@KLkÆbUbW@UbUJUXV`UnU¦mVVkxVLUL@llL@b@bkKVb@bU`m@knmaL@a@@UWVUU@amK@akkk@@b@lmVL@VUVUbVVXUJUU@V@XV`lLUVVV@nnLJVbVlzUVVbVVnUVVU"],
                encodeOffsets: [[111083, 24599]]
            }
        }, {
            type: "Feature",
            id: "4509",
            properties: {name: "玉林市", cp: [110.2148, 22.3792], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VJUXVVXlWX@VxVnX@@`ULWUXÅbWK@mULUUmJ@n¯b@l@VULVxxXU`VXXJVIV@nm`@nUVXn@lWVn@b@Jn@nU@Lm`@Xn@WJ¦U@@VnLlV@@Xl`nIlJnkVLw@KVK@UaVL@bVKXlUUKVK@IVLa@U@WLUlVL@bU@@blb@VlbUxVbXUVJ@xVLUlV@VUbVLnKlXJ@Lb@an@VanL@`VLKV_UWl@U_a@WVInlVUUUVm@I@W@wVakIWm@U@XwlaVbnI@m»Va@aXaVLU»@aVa@kKkL@KmU@WzUK@wU@VWUUVUUKUa@mKmbUK@_nWVaUkVaUaVUVLXKVVUVmVI@UkKkLm`UkW@UwWW_UaU@WakXmK@xUXJkUUWUk@WlmJ@km@@aUKzmyVka@kkWVUU¯lmU@@wkkmV@Vk@mÅIUka@Ub@m@UUU`mUbWaWmbXXKWIXUWm@Å@y@UkIUJUUWLUWL@UkVUxW@kaWbKWnXxW¦nm`XLVlUbVbUxI@JmLUKUb@VW@@bkL@b@VlU@xk@L@lxXxWXX°V@VVVbUVV@UVVbULVnVJUb²baUb@VVVVInlV@VnXaVUlIVUb"],
                encodeOffsets: [[112478, 22872]]
            }
        }, {
            type: "Feature",
            id: "4504",
            properties: {name: "梧州市", cp: [110.9949, 23.5052], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VbXblVlLXWlnwVV@VV@UnWUXVb@VWXa@kVKUaVaVkUlyX@VaVmUwUaVU@UÈymI@aU°@nWV@VaVaw@IV@VmnLVK@kmmna@VbVI@aV@XbW`ULUVVx@VbUV@bl@VLXblJn¦lL°°@n@K@UlLnKa°LWbnJ¦UÒVUllLlVnKnbWnnV`w@@Xa±nl@XKV_WVkVa@kVyUa@wU£UW@UIVW@@awWaX_WKkVmUULmak@UJUI@±m»k@m»VyUImnmmwnkUmVaVIUn_mW@»Vk@VwkmmUXa@IaVmm@Wm_U@mIUWóLmUk@laXmmkUK@UmKULUUmWUL@VakU@Ub@b¼VUKWb@bUbn¼@mJUakbWx@@VXnlJUb@x@X@JUnVVUVmkUJ@XbV`k@VXU`LUK@_mKUbm@@b@U`@nlV@bUnbVbn@@`VbUbVV¯bm@@mJXb@bVnUllVXUlbUl@LU¦VVmkLVb@bl@V@XlK@V@nUJUz°mwmLmlXbWVU@UUUlIU@VVmV@@¦bXbWxXWlXVWL@LUmkbU@@LVVVJUblzna@WVn@@lIUVnbV@Vlbkbm@ULUKV°UL@"],
                encodeOffsets: [[112973, 24863]]
            }
        }, {
            type: "Feature",
            id: "4511",
            properties: {name: "贺州市", cp: [111.3135, 24.4006], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nL@xn@lKVkwn@alLlaXV@lxbVWV@aUa@aUk@mVUnVlXL@JV@VxVIVX@b@bl@@`ÇnXVlI@lxUnlVVLkllV@nmJUxnzWJ@VXLlLVxnL@lLlVI@V@lUnl¤UzK@Vl@LlLnb@VnVVU@kaKnxn@VkVJ@ÅUlakmWIUaVanm@_UK@UVWUa@klXamU@VmVIXW@lUVknVlKVLXVXW@b@VlnnVL@KXLKn@lb@UnW°@VaXWVb°aVa@I¯aUkUaVKVwaXk@aa@wkm@alanUVw@alK@Umkw@UaUmU@WXUaUK@UW@UaVWI@¥Xa@w@WWVXwU@mKUXUWVU@a¯kl@akU@UULmK¯VUVW@U_m`U@@xVbUz@lUbUlXU`WLk@m²Wb@@xU_mXmmamLkUkKVkUVÑ¥mIXa¯KbmLkK@V@Lm¯@¯kKm¥kIWaUKk@@aVUUa@UwVUKVX_WaU@@bUJUa@mbnn@lULmKUnU@@JxUbUbU@mX¯@V@bnJÇz@VUVVbVxUnUbW@kzVUlUbVbUL@lWb"],
                encodeOffsets: [[113220, 24947]]
            }
        }, {
            type: "Feature",
            id: "4507",
            properties: {name: "钦州市", cp: [109.0283, 22.0935], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@IlVVlnL@xlaal@nVLlx@x@bXnV@@`mXX`lbnaVL@blV@bwnxI@xXJ°nKl@lbnKnblUVanKVb@lUnJVIVUb@VU@mL@Ul@XwllVVXV@lVnlVnl@XVlK@@_VWVxX@lbUnV@@JlbnIlmnVV@UwVK@U@k°a@mnIVVVK@nXLÆaVWXVK@_W@Umw@UXWWkUUVWUIVaUkJUVWbUmU@mkUJUU@UVab±aVaUIUmVKUaVUU@VUUaUUU@W¯XWWww@k@Kl@wkV@U@alK@aX@@UmIUWUI@mmkXU`U_WJUnUJmUk@@amLU@UVW@UkU@@VbUWVUk@@wmKkUWLUWX@JmIlUkkKWKkLWU@UKWa@bU@@a@_UKWUUUmJmw@nV_@ġğKóLmbU¼VÆ@xUX@Um@wklVnUnlkaUV@lV²WVklWXXbWlkVkIm`UULUU@UWx@XU@@lWLU@kbUbV`UXllUV@bmb@LnKVbULmnVVIV`X@"],
                encodeOffsets: [[110881, 22742]]
            }
        }, {
            type: "Feature",
            id: "4508",
            properties: {name: "贵港市", cp: [109.9402, 23.3459], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@n@VzUJnVK@XV°nVVnwVb@xVVknJlVVUbnWL@bUxVVXbl@lVXkWXwWaa@¥@nUUUV@JVkVVV@XUWanknKxn¯VyVI@m@UkL@W@Uk@aUalKnUUV¥@KVkkaWVkUVkUm@aWanI@n@°aUUVaUa@_m@UamaV@akU@mV_@a@KWIkmLUKaUVU@kVUK@wUIWVUaVwka@Uka@aV@@aUKVkK@X@VbKU@JULVLkVWUL@aUKb@VUL@LxUKmlkImJk_@WU@kmK@UV@¥XIm@@Wn_@KmVm@@I@aUmkXm@UWV@mn_@mUUJWIUWV_WwU@mUknVVmxU@@VUV@zU@UVW@K@X@VLUVKz@J@VnX@`±bUXV¼ln@xmxÝL@Ubn°@XWVUxUVVnkbWVXV@X`ÆÈKnlLVanIV`nLVUl²V@V¦l°¦wb@nKnLVbVJIVXK@bn@ènx@xVbUnV"],
                encodeOffsets: [[112568, 24255]]
            }
        }, {
            type: "Feature",
            id: "4506",
            properties: {name: "防城港市", cp: [108.0505, 21.9287], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XV@X°°UlxkbVlVb@nkbVl@xl@@b@nXbVL@Vl@UbV@@JVLXbmV@bVVUXUJU²WXlKVb@VVXKlXWlXXWV@VXJlI@xl@nlbn@lln@lbXalIVK@VwUVbU@aXylUX@@aW@U_UJmUnVKUamL@Kna@aVUkkVWU_ValaV@XK@kV@@WwVXV@VKVVn_lJlUXkWaXWlkXU±kU@VUlbkVmUmlk¯ÝW@mb@¦VxULmkJUU@ma¯wmkX@VóJ±bUVUXÝWklWXXlxUabIğÇ@U@mVUKkkm@UJm@XnWV@x"],
                encodeOffsets: [[110070, 22174]]
            }
        }, {
            type: "Feature",
            id: "4505",
            properties: {name: "北海市", cp: [109.314, 21.6211], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VaVLnK@IJVwUaVaUkWKn_mX¥WwXmLXalbU£UyVÅ@Ýwm@°lLÅUmkmwÛaƑLÝUUm@ȣÆV_Ó@£UUV¼U°W̄ÞVbXbôx@b@bmV@ÇUÝ@@ĢU`m@nxnIVVVXVL@`@bV@@aXbVL@XVlKXLlLVlknJ@IWVXXKlVnL@xl@UVVXa@UV@VlX@VUV@nK@bl@nVVIVmXIV`V_lWnn@VJVXnJ"],
                encodeOffsets: [[112242, 22444]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/gui_zhou_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "5203",
            properties: {name: "遵义市", cp: [106.908, 28.1744], childNum: 14},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@@UnUlJnwJU°VL@bnVUwlJ@XXVlU@klVUJknlUllL@bUJ@xULUlUblVkblbnwUXmla@wV@VK@L@UXaVKVLXWUVa@U@Im@@W@£UKUakKWIXU@al@@llUnL@W@Un@@VlUV@VIUanKl@Xb@lmxVb@b°bb@nlJVVnnJ@b@LV@ln@LmV@Vx@blnVKnlJXIlwJ@Òb@nlK@Un@UL@VVVVUUUVKl@VUVLJ@UVUUw@Wm@UVÈVlbUb@JLlX@@xLmk@@nlx@bUJUzVJ@@LVxUV@bWxnLnVVK@_K²xVbV@n¥@aVI@b@l@VaKnb@n`nmmýW@U_wV@VlVV@Vn@n@nI@Jn@°¦VaUU@mVVWVaUÅU@aVKnVbVUmmU@a@kUwm@aUUmUUJ¯lakUaXaWUUaVkkamkmUnVlULVlJ@XU@UJWUUwk@aU@WbkWL@U@WU@@XUKmV@aUVwUĕUJUamUUVUÑmnIVJ@kl@XalJVn@KVL¥@UWIXWmU@mVUKnUWLUKUaWUUKVU@U@anUny@UlUkK@w@a@aVU»UkVw@WmkJÅmUUVmwXalLXWWUnam@XkJ@UVU@U@W@@U@I@Wl@Ènlw@KXLWblVUkalKUUVVaV@@wnIlaUmkUKWU@KkUkLWaKUUWUn@VK@LnnWJUIVkUWVnV@V@@XK@VUIUJ@IWJkX@VVJIVkK@I@UVaUWk@m@wnUWKk@mxk@@lV@bxmb@x@VUmLkUJ@nVV@b@VkLVbU`¯Il@U_UW@UU@K¯wm@xL¯¥kI@bkb@Ua@m@kkW@XVbmV@kV@bWbUbV@¦xXlmVk@¦bkaWL@KUImK@wUK@VUIb@bmK@LÅy@akXW@kbWlXblL@ULUb`@UkUymX¯@mUJUUJL@Lm@@WX@lUVlXll@l@Èk°V°X@VU@UVll@XUJVXUVm@@VXLWlnV@Xk@mVULnxV@@bmkL@VWLUbU@UVm@b@ķ¥UnmJ@UUVkkJUlÔU`UIW@°kLUlUI@WVIU@mWKkXk@WU@bXW@J@xX@l@LVl@xLVxXX@xKnxVknbKVV@ULWlXU`@nUlX@llVXVUKlkUKlI@anKVLXKVaUIVWV_VK@VnLlU»VKVLm"], ["@@@KlKkUUVVX"]],
                encodeOffsets: [[[108799, 29239]], [[110532, 27822]]]
            }
        }, {
            type: "Feature",
            id: "5226",
            properties: {name: "黔东南苗族侗族自治州", cp: [108.4241, 26.4166], childNum: 17},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@VV@XkV@bUbWJU¼Vb@Vnb@b@J@bL@LV@UVlUI@aKULVb@bkJmxlLVxknVJkxnKmnnL@bn`WIXlWLU@UxVbUVmKVXI@JVIVJ@UL@W@@UmUXUlVUVJXImm@KL@UVmVXVLXblKlV@LXVLlVVnkbmJ@xnXl@bXa@VanaÒLmVnIlÞ¦°k@b@@lVnJlUnVX_@lVlKVUUxVLVWVIXJUlnnWlI@KUaUUVKn@VaVXV@na@mw¯@mUkJUamI@lk@@am@@IUmVImUUw@anUVaUU@LU@WaWUXWWwV@VwnU@L@ynbl@@X@aJ@nW@@Vn@lVLlxnIl@@UWKUnIlJXIVllIVV¼XK@aVIV@@bn@VKXLVKVVVInwJ@UWI@mX@WKnI@KmUUVJUL@VKW@@k@aU@@W@InJWUXwWI@W@¯wkaVaUIl@nValIXWWI@UUm@anwWkXWWIUbk@UJmIUamKVUUUVVama¯VkIVVUlKnXVwX@@WVaUUVa@IlaVmknawkUU@U@mUVUVwl°LVbnJVU¯la@mX@@UWKXU@aV_V@@JlkU¯@VnK@km¯kU@WUW@mmU@kmlU@wkL@WUkL@VmLJ@b@V@bknUUVK@UVKUK@Uk@Wa@LUVVnUbmVk@@UU@@aV¯K@U@UU@WmUL@aU@WVw@IxXll@UXK@KXXVJna@wWa£naUKVm@UU@mUmalm@@XkVm@U@VLmWU@kkWxU@@bVV@VkXVlV@UUk@@mI@KUwm@UmVUUwU@lwkV@IUa@mUaVIVKVa@w@U@UJkb@n@bmJ@XmlVUxWXkJmUkUUVWxUlU@aULUmbU@@WXkmL@xUV@nUxÇm@XLWbnlnVnnUVUnVVz@lbUVVlULVb@V@nUJkwm@Ux@bWbUK@UULkaJbUU@U@lUK@XUJmnJ@bU@UwWax@zkJWnUJUUVVV@bXn@xVb@JLm@Xw@`@bkb@VmXUV¯L@mW@@n@V@L@KIW@@aaUx¯@Um@XbW@@LV@bnVWVkKUzlV@bÆa@lnI@VV@@LnVVKUaV_VJVbnU@bn@nX@yVIVxXKVLlUVaXU°J", "@@@KlKkUUVVX"], ["@@UUVUkUmV@ln@VXVK@K"]],
                encodeOffsets: [[[110318, 27214], [110532, 27822]], [[112219, 27394]]]
            }
        }, {
            type: "Feature",
            id: "5224",
            properties: {name: "毕节地区", cp: [105.1611, 27.0648], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@UkV@kW@Xn@@KKVIVVIn°@nWVzl@V_VaVK@kKWaXklaX@lW@bÆz@KnL@aaVJ@UVL@xnLVJ@LXKlba¥l@nUWkw¥U@VaXa@amLkUKm¯kmkIUaKUIWkKm@anw@mlwXImUk¯@a@amU`kkKWVkxmUUak_mJmw@wmXUW¯X_@WnI@aVwkWWýÅU@WLkUaUbVV@lUVVnm@kUmV¯kKLwmVUUaWVaaWw¯wÈ@VULUVUUK@nWJkIl@Umxnbm@kbUJa¯bUbVxmLUVaU@VUUWxkVVV@bUV@XWbnlUbbUJlbUV¯b@z`WbXnmbawUwVWUbUxmbU@Uam@VkVawVaUWI@mUKóz@lUlÅ@WIb@xXxml@XklULWKUmwUa¯KUXWJkaULmKkLWbkKUVImWa@kUaULW¯LK¯@kbL@bx@J@bmnnlUlzU`U@@Ub@mn¦°bUVx@bkVm¼mx@mkmVV@bkxVnaVV@bU@mL@b²`lIVV@lXLlbVxn@@bl@XllIVnbVn°°wlbXw@mVa°lVnU@mVLVbn@@b@@WVnUV@Xlxn`VznJVb@L@bV`V@UnwU@WUXKV@UUlmUUlaXalLmbIVbnJVIlVVaUUnWVXnVLk@nWnblnlb²xxVKVXlVXLVWLlUVJna@wVL¼@JVX@`@nnx@nWJU@Vx@XXKUblxU°LVKVVlL@KnbVUnJIlUnKl£VWxIlJ@nVÞUVVnbVX@V_°lnK", "@@@UmWUwkU@Um@@VkL@V@VVkV@nbVa@"],
                encodeOffsets: [[108552, 28412], [107213, 27445]]
            }
        }, {
            type: "Feature",
            id: "5227",
            properties: {name: "黔南布依族苗族自治州", cp: [107.2485, 25.8398], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@V@IöalK@UV@@KUaVIVVLlaVbVWnX@@LnUlxl@naVLXVVaVUJ@lUUanWWI@VlV@Xbb@Vn@VmVVbk@kU@VV@XJ@zn`ULW@kK@_WVUK@LUb@Jlxn@nnWlU@@bx@XVVU@UbVb@n`VI@VVLUlUIUV@KmL@VV@XIV@@lVLVmXV@WLXLW@U`nkb@Vl@UL@VVVLllX@`lIXbJIXWLaVL@XXWĢb@bmK@L@°@VnxmxnK@xVn@VkL@VLakbl`VnnxVnUlV@@VVXV`@k°JV_UalK@U@aUU@mIlVnKV@U@wnaw@akU@l@nwl@XLmV@xnl@VXUb@V@JlLUJUI@UlWUnLVUUaVwV@XKWkXJm_@amKnmmLwlUIlmUwkKnwlI@aUaVKL@bVJkVUU@@KK@a@I@ama@UUaV»XIVa@alU@WUU¯IWVUbkVUKWLUwUJ@zmWm@@amVUaUIU`VbULmU@KU@@UmJ@kÅb@akUVylLXUmU@aU@KX@Wan@V°@Vwb@bX@J@LK@@U@mX@@n°KVUnW@Ula@a@_x@WnK@IUa@wWm@aUUUVVVIXmlI@ywXbVxV@@aInmVI@WVL@k@VVVaIlbVK@VVLXa@aVwn@lxVI@m@UUaVKUkVUka@UymUVVUmmUmmkXaWK@ÈnVw@mVU@wKlnXW@V@naVVKUk@KVIUW@mk@KXU@Um@@lVk@UVJna@UWaL@a@Xa@kmmVUUk@mkkamJImJUUmIm±aUUkambkamVUU@VlbUbVVxXWVUU@VUakU@UmUVU@mnUVVnUbVJ@bUW¥kLVamVkUaWJU_UVWKk@@nlUVVJUXm@Vm@UnVlmbnmJUbULU@@UUKWVIWxnJVb@xUL@bUJWIkxbkb@xVJbmU@kW±LkKUkVa@a¯am¥ULkalÑlKXUWXaVakImV@ka@UUJ¯aXmmbKWU@wUUaUaKmU@UXlWb¼WLUKUb°UlVbkbVL@VJ@nVlUbUXmJ@VX@lbUbU@@bWb@VnLVJ@bVVUzVL@lnL@bVVVULmKUkJkbm@xVb@VkKVnnV@b@WXUnVlVVXVJUXlVXbWV@VU@Ubk@@KWbUUmL@JnXV°XJ@_`UbkXVVlÆkb@VLXVV@V@kKXX@`V@@n"],
                encodeOffsets: [[108912, 26905]]
            }
        }, {
            type: "Feature",
            id: "5222",
            properties: {name: "铜仁地区", cp: [108.6218, 28.0096], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°a@aÈbVUlU@aVKnVVVUlyX¹lWVa@UVnUVU@m@mUl@mÞw@xnIVbna@KVIJ@kwV¥UXÇVkVW@kkKWU@aXUWmnIVa°VXbmL@VVbnVVVUbVbJVbVKXkVKVanU@aWnWUWa@Unk@mVIVK@wXxlLXbVJVlKbl@VI@maXalVVVbX@@aalnkx@b@Vb@Vnx@bVVUXn¤WXn@Vl@Vlzn@`@I@KUU@V£namVkXa@aVKnnU@anVlKa@UUU@amk@»kU¯@aVWnkWmkImU@akaVm@»VUV@UKnkW¯XWlkUKnIWa@nmlIXmWUnwUwWm@wULmaUJkIUaaWaklwkwmJmU@bkJ@XUJ¯W@XbWbUKUkWJUUVKnn@UmmXUWa@mU@@UI@WmXVykwm@kaULWwU@¯lKUUVU@mU@UkmaUbmV@bxVnVUJVn@Jn@@bl@@knJVblInV°@nx@mbU@UWUbm@ULVVVb@LkJmXkmVWIUJUXUKVwVUkLkU@W`UmkVmIU@k@@a¯lÝ¥kmJUnKÑmbUb@Wbak@mWU@UbUVVkLlbUVkXaWK@LkxÇmk@@X@J@V@@X@VUV@VIWln@mbXVWXkKWbnxVUnVÆInl@XUxVl¼UV@b@b@xlLkV@VmzmV@b@VUVVLXVVbVLXKmVVLU@nnVWXXJ@V¦UK@LUmkIWbk@@lUImJnVÒVUnVVbVIVĖUxV@bnUVL@WV@@X@VKlXXaV@@blVxXVVIV@@WkIUVKUkVmlnnbllUVbXVWbblVkb°VInVVV@bnVx@l@bnVVnUUamUL@bVVÆUbUXUn@VVUb"],
                encodeOffsets: [[110667, 29785]]
            }
        }, {
            type: "Feature",
            id: "5223",
            properties: {name: "黔西南布依族苗族自治州", cp: [105.5347, 25.3949], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VL@Vl@@IXW@kVUVbnW@XlKVVnUVlL@baVbb@xX°ÔUxV@kbm@VxkxWJV¦@ÈnVKxWXJmV@nÒ@xVbn@@blLk`VX@bla²JVUlnn@U±lw@wnw@mlwVIX@@m@klKnkaKnwmmXkÆVmU¥l@nb°n@aVwVmVIVnI@a¯@mU°l@@VnI@JV@UV@b@IUbVJmXöºzllUbVa@aXUl@U@llLnKVaUa@UmK@UwVbnKV@VwVK@UXV@Vbn@w@UWnX@a@mI@UUKlaUaVk¯VaVLXK»XaWk¯mkğwmW@mIVkwJUIÇVwUUkVKkm@UkmU@WÅwm£Vm¤¯IkJWa_lUbmJzÝJkUÇVU@bUÝnm¯LUb@`mL@VkL@VUmmk@UU±Umka@kU@ķymUkk@mmkÝmUaUakImV@V@VÅL¦JUXmJXWb@n°Æx¼nV@LlbUUbmL¯@ÞbV¤nbVx@bUVlblI@KVVUnVJUn@VlLUlmLUUUxmK@I@@VW@@bU@UJmUkLVVUl@b@V"],
                encodeOffsets: [[107157, 25965]]
            }
        }, {
            type: "Feature",
            id: "5202",
            properties: {name: "六盘水市", cp: [104.7546, 26.0925], childNum: 5},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ôyVL@nXJVUbxbUlU@nVbV@naVwaVUXVxxbnaWmXa_@y°aVUkaVIaVamkXa@WVU@aUUlUXwVV@UVbVUnKUwVa°abVIlan@manw@VklJXI@mLVVVUVK@UÇk@KUa@UkaVU@UVWV_XWVXVWlLXKlLXaÆKwVL@akKm@Uw@@XUVk@VUI@wWK@aUVI@UkK@mLW@kImJUÅVmkXUW@UJkx@nmx@xkxV²m@kmUV±Ikb@aUWl_kK@am@Ua@wÑ@mnUWIXwULm@ÇU¥XIlwUwn@laU@Vw¯ÓW@waUab@akKUmVUUkL@WmXUaUV@lWX@Jk@@UUKULmLUJmzkKmVX°VUnWKULL@mU@UnVJ@b@UV@X`m_@l@@bmbXJmnn@°wnn@VLX@V@nVl@nk@@bl@nn°WlXzW`XXVKnUlxVbUb@VXb@VxÈbVlnbmn@kVUL@mLUVVL"], ["@@@@UmWUwkU@Um@@VkL@V@@V@VkV@nbVa"]],
                encodeOffsets: [[[107089, 27181]], [[107213, 27479]]]
            }
        }, {
            type: "Feature",
            id: "5204",
            properties: {name: "安顺市", cp: [105.9082, 25.9882], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lL@bUKxÅLWbkKWLkKUXUWWXU`UX@VUVlb@VVb@Ll°xXxbbXUVbVnUxKlL°nUlVn@UmVU@kUUVablVXKV@ÆXþlXUxnU@mVK@_@ml@UU@blU@KnLVyUw@@UmkWVw@UVK@VXzVK@nVVUUW@kVJnla@nKWkaWL@Uõb@JU@mU@@_WWL@lUU@WUUK@lakÅUUlWVa_@`WIU¯mW@InKVVXa@Ll@VaV@@UXUWakUVWUIUWUkUmVXW@@amUUmLl@UUawn@laIVlnLVKUUU@amK@kUKVyUU@aUImK@UXa@aV@VakaW@@UnIVWVaUkb@mWX@Vxm@UaU@W@VULUxU@mLaUx@VnL@VVbUbmLkK@kVk@WV@bUbVakkyõ¹nWUIVa@J@aVUU@@ImJ@Uk@¯V@n°@bmJUUJUnUxbm@¯mak@¦VUnÅWlnnmxLbmlkL@l@nWVnlÆUVnIlJ@XnK@lL@VJVU@bXL@xVJUl@VU@W@Vxn@"],
                encodeOffsets: [[108237, 26792]]
            }
        }, {
            type: "Feature",
            id: "5201",
            properties: {name: "贵阳市", cp: [106.6992, 26.7682], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nlLXVJLVblJn°lnLlVnKlU@nUUa@WlX@ln@Vb@la@alJ°¦Kwn@°xLVkUmmwUmk_labK@UlK@UUm@wLmnwmw@U@¯@KnL@aaġXWW@UKbKWXJIWakJ@_kWkKUU@UVKk@@UlamV_X@WKXK@WUUnUK@kU@WJU@@UnK@LVUVJVkUK@UUJm_@UaVaV@UU@Ww@aV@Xkmmm@kw@IVa@KVLXU@`lLX@VKm_@yI@WU@UlVl@UanU@Um@UaWaU@Uk@XJmXVbkV@IUVUbWUUKmbk@kwmV@K@mWUXUakbKUUUJVb@LU@@VkL@VXKlbXmL@kbmUI@lVXUVU@mULWy@UUL@VUxXnl@V@VxUzmK@LkVa@VVk@@n@`UL@nmV@bmJ@X`WX°WVn@xnxnIl`VbnVlwXUlLl_nV@b@bl°VnWJkx@nmx@b"],
                encodeOffsets: [[108945, 27760]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/hai_nan_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "469003",
            properties: {name: "儋州市", cp: [109.3291, 19.5653], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@à®¼jpnr``pRVHÊÌ¤Zt^JÖA[CâlTébQhRPOhMBcRSQiROE[FYdGNOEIH]MgEAMLLIAG_WMCSL@ED]PCLYC[ZIHgjSxJTMbHNEFCMEE_HSDFHSLECRNSFDRICHNADGPI\\RZGIJTIAHLDQOHG`GTNCOIC@eIGDWHIS[kiE[FMbECZS@KKS[FDWsCeRuU_DUQNOE[LKGUBM¨EDQP@HWHGDImXCog_~I_fGDG|QDUWKBC\\ore|}[KLsISBHVXHCN`lNdQLOnFJSXcUEJMCKSHOUMDIm_DI`kNDIGEYFM\\YPEEIPMSGLIKOVAU_EBGQ@CIk`WGGDUM_XcIOLCJphHT_NCISG_R@V]\\OjSGAQSAKF]@q^mGFKSW^cQUC[]T}SGD@^_aRUTO@OHAT"],
                encodeOffsets: [[111506, 20018]]
            }
        }, {
            type: "Feature",
            id: "469005",
            properties: {name: "文昌市", cp: [110.8905, 19.7823], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@hĲ¤Ī¯LQDaFßL[VQìwGF~Z^Ab[¹ZYöpFº lN®D´INQQk]U[GSU©S_­c}aoSiA£cÅ¡©EiQeU­qWoESKSSOmwćõWkàmJMAAMMCWHGoM]gA[FGZLZCTURFNBncVOXCdGB@TSbk\\gDOKMNKWQHIvXDJ\\VDTXPERHJMFNj@OwX@LOTGzL^GHN^@RPHPE^KTDhhtBjZL[Pg@MNGLEdHV[HbRb@JHEV_NKLBRTPZhERHJcH^HDRlZJOPGdDJPOpXTETaV[GOZXTARQTRLBLWDa^QAF`ENUPBP\\Eji`yºEvåà"],
                encodeOffsets: [[113115, 20665]]
            }
        }, {
            type: "Feature",
            id: "469033",
            properties: {name: "乐东黎族自治县", cp: [109.0283, 18.6301], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ªVLP`@PEdNRAHOPEAKHEVL`GZBJfvdTAXNNTZJFPrHHNpKTD\\ILHbEVd^JOHLh@NNBnHP`\\xH@NBRLJTlNv_^CTLd@bNDVFbxdFVUPBTKOGEOUO@OEBXQP[H_EI\\EbeYa@UO_JMEJ_IEDKJUGMDcNUd_FMTEJSGoZ]EIYGO[YWgEQ]a@WHEDQKUSDUGAbYBUpSCYNiWqOSQEoF[UcQISWWNMSDe_cLQ_UBiKQOOASQAWgS­ā]ZaSPÝZ]XMXS[^oVËNgNKlE RôEø"],
                encodeOffsets: [[111263, 19164]]
            }
        }, {
            type: "Feature",
            id: "4602",
            properties: {name: "三亚市", cp: [109.3716, 18.3698], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@®ĂhTBXTRPBRPjLVAR`dKf`TCNXMTXRJVdE\\FpTRrPjXZMTDVoZABaVHTCLVCRGF@X^bFRhZXP\\ZHHMA[^wBWXJlW¤EJ[bCTOFWWMm@ILMGWQ@DQ^QNWFSHEbF`OXNbOVNKTEPDTLTCCVTREfvfEHNbRAENH^RJXCFHNFRpVGHWISDOTMVCZeGamaLoLÛD¹¹ėgsia{OųETtlÉwr}jR±E{L}j]HąKÃT[P"],
                encodeOffsets: [[111547, 18737]]
            }
        }, {
            type: "Feature",
            id: "469036",
            properties: {name: "琼中黎族苗族自治县", cp: [109.8413, 19.0736], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@bRFnHNbHgN@NPEnbXP@bND`NT\\@\\QZb@`@J]V@XhDpWnCJGHGXO@CR§FANHVKLF\\MPVR`CvVfQtDPKpGHG@S`WJP~^dSTHWX\\RHTFACQTIAUPOU@MG__IaYSFQKNSbORHXCZeTFJgB`YBMNMFi~IVDV[tGJWXGDQRGF]JrALgESLSAYDGIaFeXQLS\\MKSLSQYJY}eKO[EHiGSaK[Yw[bmdURgEK^_kcSGEOHKIAS]aFSU@Y]IWFUTYlkP_CUOUEkmYbSQK@EMWUuAU\\M@EpK^_ZMDQ^OXwC_ZODBrERURGVVZ\\DTXcFWNIAWJWAYUUFYEWLQQaCIZeDM`cLKRGpanJZQd"],
                encodeOffsets: [[112153, 19488]]
            }
        }, {
            type: "Feature",
            id: "469007",
            properties: {name: "东方市", cp: [108.8498, 19.0414], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ºxJYZQIYXLl@dR\\WZEn]bA\\S~F`KXaDeTiNO^EEKWEDQXITBXaWaDQMUJOIaTWf@NJV@dSxGZFu_@WMKAU}AQ@MwG_[GOAmMMg@GKP]IUcaFKG[JSCoLGMqGEOYIMSWMSBucIeYA_HUKGFBLOFGPQBcMOF_@KO©UAtERadwZQ\\@ÊJÒgòUĪRlR°KĮVLJ"],
                encodeOffsets: [[111208, 19833]]
            }
        }, {
            type: "Feature",
            id: "4601",
            properties: {name: "海口市", cp: [110.3893, 19.8516], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ńZƂtĢ¬æßFuz¹j_Fi[AOVOFME_RBb]XCAKQKRSBQWSPY\\HbUFSWSPoIOcCOHIPkYCQ]GdGGIFQYgSOAQLK`MFUIGa@aQ\\GGUFcHKNMh@\\OYKAigsCgLSF]GOQO]@GM]HyKSHKPW@Pxi@EMINYREXWRQ@MQcFGWIAwXGRH\\yDI`KJIdOCGRNPNtd\\UTMbQYi@]JeYOWaL[EcICMUJqWGDNZEXGJWFEXNbZRELFV]XQbAZFrYVUBCLNFCHmJaMIDDHXHEhQNXZ_TARFHVB@DTQIRR@YHAJVnAbKFUEMLd\\c^ÍÞ"],
                encodeOffsets: [[112711, 20572]]
            }
        }, {
            type: "Feature",
            id: "469006",
            properties: {name: "万宁市", cp: [110.3137, 18.8388], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@^J@ZTVbET^JBGLFPTHld]`FLQhcVanx\\\\ZbLHTGj\\FLP~fIZRZPVTQFSVAFJE^NDLEE[~LjsxVTG\\NZZNGlLRRGLJTV@hPZANN^@T\\NEPPbDZXO`d^HSvcJDIV\\XZAJUFCLNP@PQ¤@[ïKLÑIÏ]ÇE±I{u­YśUćFcYUmsVeBSVgB[RO@aYYPO^]@UVaNeDShMLG\\EfFVE\\F`"],
                encodeOffsets: [[112657, 19182]]
            }
        }, {
            type: "Feature",
            id: "469027",
            properties: {name: "澄迈县", cp: [109.9937, 19.7314], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@T\\GJCXJH@fJDDPNCNJENN^NLHBNSx@DDYbBLLDRbjZTj@`XXTlG^Xr@PJLW\\WLTlWR@HDJTD@X_PO@STMDNTMVV@NLDM`M\\XM\\JNBH[PYZúYzŸ`Ċ\\ÎÝd]c[NKVFLEBaUmBIZGQ@JQSR@CUAEGBQ`SWYRMFgWGCGJCbNnIDGMEDKVAZUEqBYRa^WEUFKYQMaFWXEHIFWMYHCrXVIIiaK@aMCUYNSIISTwXALKH@XWXIEIJQCG[IEQDE_XSBaa[AIPW@]RS[FWS[CD]PEBYNGFSaSyJG]@ugEUDQlGHiBKHUIoNSKqHFaPMICK]UUHIPDJMuCA[SCPIDIOILGAEmU[POPBVSJDREBGS[QXWSGcT}]IO_X@TGHoHOLCX\\ELT@LYTDaFENF\\lj"],
                encodeOffsets: [[112385, 19987]]
            }
        }, {
            type: "Feature",
            id: "469030",
            properties: {name: "白沙黎族自治县", cp: [109.3703, 19.211], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@D\\RV]dTXELnHr]^@LETBBRTHPi^[@U`QTHDJ`MGSogDIPKdJ`WVNHCXHl_DJR@AH`FBVPUJLHKNTJOFFZON[ZEHFCJlMJ_Cn`CJVNGPLTNDFIdVTWEIPmRKMc_kDMWGGUTAtJLK~\\f{pqD[LAVXRCH{HC`eJ`}@W^U@I@_Ya[R[@MSC_aMO@aWFmMOM@haGGMEmaQ[@MESHaIQJQMckBIw[AOSKKAMPSDSLOAV_@@`KJRbKRDfMdHZERgAWVsDMTUHqOUr@VQXTT@TfgL^NH\\@heTCZaESNObHPHeZF\\X^ElM^F^"],
                encodeOffsets: [[111665, 19890]]
            }
        }, {
            type: "Feature",
            id: "469002",
            properties: {name: "琼海市", cp: [110.4208, 19.224], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@TP\\pATHTGlZDJGAQjE\\Rb@jVBDCN`JZ[NCNHNXbULPrP\\KNbMTLjJJRFP`pNLZz^FLRHjVPZ@hxVKbHBHMNNJFRlLzGPnNHhIrHHADcPWdUAmEMVQDSKYHY\\EhBN^HpXGNDBNNBnIßÅ_g{³So]Ã£@ORO@KMEDIVYB[WJUICudGTc]P_YWaCOOMFS[]@MMYBgOU@ISHKQQkKMHYY[MSHwUit}KF\\KFMCF]EIUBETSROUKTLT[NKTWREfJbCHBZKTFTKh"],
                encodeOffsets: [[112763, 19595]]
            }
        }, {
            type: "Feature",
            id: "469031",
            properties: {name: "昌江黎族自治县", cp: [109.0407, 19.2137], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@`ZĤd`òü BSPGP@VSbQ`@]HC~T^SE]N]FkW]E[fYGGOPaTMbFDYfS@g[MGK]he@SSSRW@UVqrPVGNStCXUhBFQGYNcCeLQQaLI@_`@EUwcEaCUaMc@SK]Du`MSkKI~BVNL@X`EvYwHcTU@MIe@SXJbIPNVCRXbWbSAWJCRXFFL]FMPSjCfWb_L}E[TaBm^YF[XcQk@WKZJYRIZw¹ "],
                encodeOffsets: [[111208, 19833]]
            }
        }, {
            type: "Feature",
            id: "469028",
            properties: {name: "临高县", cp: [109.6957, 19.8063], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@jD`hNd\\^dZädĒH´Op@ùZY\\OAGIMN[[W_NCNMKU@NUMSNCTSP@`O@WSCCI@GXQSkXKX[IK@OWqH]SkWW@_SiiYQaKCAKZaCCw@MTGAMKM]FMMIMDSM_HGHRPKCBGSJJIYH[QOJCHMBDGQJECMTDQKFGTCEGTF`NFEDMFaGSNwIiTGhYJD\\KZODC^@FTKND`XBHKJNKFBNhG^FJMPcHEZF\\QPRjQTAdgNOPgQaRSê"],
                encodeOffsets: [[112122, 20431]]
            }
        }, {
            type: "Feature",
            id: "469034",
            properties: {name: "陵水黎族自治县", cp: [109.9924, 18.5415], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@R]NC`YL]FoN@V[vBXVFNL@TRZalnVFVP`DlOZkVSXEE_F[EUFeH[NKTgfCbMVU^@P]ZObZP@\\QhATUfAtUasñiāEoI]eYǯ@aKmaeWuCºKÜKpnbHbYfUDSNCPJTRAHJTDJSfDNLHXC``VBNGTYCQDIXMDSP@xLNEFRNXBIpVNLXah@RgF@`qOML@LJNSPLbaHAh@Jdj"],
                encodeOffsets: [[112409, 19261]]
            }
        }, {
            type: "Feature",
            id: "469026",
            properties: {name: "屯昌县", cp: [110.0377, 19.362], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@\\OnVBFKHPJCJOJTDB\\vDINOCGJVVL^JDONEbrGTLpMVJLGjAHGRkVChF@vH^zIbTETMHAZOFC^\\DXT\\EffAP\\PdAV@UIYfS|S@YPICMeM@sC[_A]VQEwyHSMuNcAUlQJMVGMS@mVBZPFO\\CSFQK[LqDMACiUa@[QiFBRIHYCHkGSBS[oSOqBIE^QHCRWHIXsHU\\UC}JEjMNAN_ZAIhSEYfWDQGaPMTLERZTJb``NHV@"],
                encodeOffsets: [[112513, 19852]]
            }
        }, {
            type: "Feature",
            id: "469025",
            properties: {name: "定安县", cp: [110.3384, 19.4698], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@JjDNdJ\\FbKPXfZ^Ij@RZNaVSc[MsMOHQPDJcLIJ_zCG[HQxWJBHXdENRR@XQFWZQQGOFSWUCI[WCJuRGLXNMPLhCl[Ta@SqGgJMGOmyHkKEQMINMAGaGULgwY@UOGiKQ]EYyMKoO_QEIIKiNSMa[LqOKOaVMWMGMDY\\_IKrL\\ERT[DEPYOUA@nNTUHINkRBVMdNvGTxzRF^U`BD\\@tfNDNOJ@Z{TeTJZ@VUcB[OBOeeQT@^OXBJb\\AbWTF`RCJFH\\RDJIJFXW@WLGBKxWTSJJMTVZND@bbL"],
                encodeOffsets: [[112903, 20139]]
            }
        }, {
            type: "Feature",
            id: "469035",
            properties: {name: "保亭黎族苗族自治县", cp: [109.6284, 18.6108], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@FJp@fxpQ\\ApN\\GNPNBM`HLMrXLXj\\PEHnI@WUCEM\\GTc\\GZYHTPBHRCPTdH\\K\\@HXiBJILJJAVNTOZJNtFPC`YxDPWci@IBgbGKaTOIM@KNKrP@_hE@QbgKWUMJoWAQMFEKM@wTONCJWRCZDHSAM_UD_GWMKeCITSCGIQBGXUHQoMEEGWDQIG]FMQBMaFGueFeSQDUSDSKOCSFMLUaPWM_PaEGFETMX]RCRR@HXKN@JNnXXESPaDI\\£FkXWIAX]xB\\GN"],
                encodeOffsets: [[112031, 19071]]
            }
        }, {
            type: "Feature",
            id: "469001",
            properties: {name: "五指山市", cp: [109.5282, 18.8299], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@TCNOLBTLBPx\\AJdlNRRIbJTGNF\\@RcIYbmHoLQdKN_fCJYbDRRXKZFVEZVXBXIJBXMdESW[CUYHUVQFQAqsEIMPYMSBUIIJKAIjGW[@[LGScDOGQOAGSYZ[HSd[HFNVD@XmJFG[OWiWKNqGKN_MAMO[HoM[BoRewo@Y^HpITSFENc`MVCdHNIVCLJFI`NFIP`@VZbaf[FFJG`O\\WRFA@PVPFPPH"],
                encodeOffsets: [[111973, 19401]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/hei_long_jiang_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "2311",
            properties: {name: "黑河市", cp: [127.1448, 49.2957], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VÈÞ@kxnX°VÈa°V@kôwbJVkXlVUx@lL@xkVV°VbxlVUnVxk@KkVbIl@°kVl@lÆnkll@@VVX@V²bUlVlVUVÇn@nkJlkVb@x²V@n°VUnlKUn`@n°bWLnVUblVUVVbknV`°kkl@@V°@nzJ@XxlWXb°n@bĠlbXbbVbJ@Vba@@lbUbVmn@lVmnIW@WbÞ@n@x°@ĢaƐéϚnlČ¯ĠŻÈwm@ôçUmm£Xy°UV@wÈ£Ǫ¯kõÝçUÑUķĢkVÑÆÞU°nŎ¥ČUĊx°m°¦żVƐx°Ç£@yUônÞÆ@Èĉ°Kô¦WkWUbÇ»@ÈĕWÇÈ£ŤU@n£ÆUUKVamanwÅmÝJ¯k@JIkaVaUUÇbkaÆÑkWmÝUÛÝ@wnU±@kkV¯KUkJ¼U¦Å@ówķaķůV¥Uaó@Åwm_kVwĉĉmmn_V»a@UVwķóU¦LǫéóXÇmōLǓÇķxÝkĉkmakbUĶ°@W¼@bÈÆ@ĖLl@°J¯mkl¯LÝ±LamJ@¼VƧUóUXċb¯ńVbkÆÝI@llxk°V²V@UxÞL@b@b`ÇzkókÝ¤@ğ¯WLĉÇLmmnċVkbUaL@¯bU°ğLÝÝ@"],
                encodeOffsets: [[127744, 50102]]
            }
        }, {
            type: "Feature",
            id: "2327",
            properties: {name: "大兴安岭地区", cp: [124.1016, 52.2345], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@kϙmƏêġb¯@@wmÝ@XV@Ill@bUxl¯VlVbV@ULVlUV_kxVVVÈÝJ@¯Ulm¯x@xóÒĉ¼m¯Wxţ@Uz¯WwnUwť@knW£óVUUwğyó¦WIVmmI@±kwÇ@@b@ĉ¼ó@¯wó@¯aó¼KÅaUwmWUwÅI@aKó@UaLaVÅwō¼UUÝl±I¤VxÇx@zkJmnnmbnzxll¯ČkJl°@kbmx@x@kêmVnWxôXxU°bWLóJnÇWĵV¦UUbbÆġKk¯VU±aXmċÑUwĉKġkVxkÇKkbIÛXWl¯bX¯KbĊÞVÆnĸ²lxU°n°òÈb¦xVb@¯Vx@¯VķÞČlĊ°KĸȘI°¤ČIôò»ƨnȰKǬ¦ôWŎÈƨwlnKVXmbX`lbwkVWXXL°aƾaĊ£n°@°¥ŎzÞ¥»alwôkƒJa@ĶK£bU°ĊxźVÈUĠ¥ƨVI@XU°x°Ln¥w°UmwXmÝV¥Ģ°@nU@mÆ£¯lKÜw@aÅU¥UaÝIkmV²nn@Ķ»@Uk¥VKÞ@ÞÛ@kVmĢa@_Jómǖ¯ÆwóÇa@alUwwĢřk@wÆWXUWXWam@_ƒ»ÇéXaĸwVa@ÝKkUWkXkKXxn@lĊV@¯m¯nřÆw¥"],
                encodeOffsets: [[130084, 52206]]
            }
        }, {
            type: "Feature",
            id: "2301",
            properties: {name: "哈尔滨市", cp: [127.9688, 45.368], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°`_JlU@@V¦°JUnLôlnŤ@@ÈaUÒVbkbl¤zk°ÇVÛô°IlVUVôUxÆU@bźĀº@¦b@l²UVl@°ÒĠxnXxÆVô¼Þ@Üx²KÞlVÑ°UȰôlwô@²ĸ°lanV@VŎUll@bÈnÜmwĢ@la@ÝÞb°UXblŎ²ÆkVI@nJnĠ°knÜbĢwna@akÞKƒĀaIVbU¥wĠwkôxnLċVçkaU±IUmnġW°WôĉalÞÅĵ¯@W¹XÝab¯a±X¯ºLaVmkLóbkaVUKVkkKV_@aÝykk±L@ÅU@yV_aU¥ówÇx@UkVn@lkÅlwWVwUkĉmkklW@abVwnWWwWL@UUÇLÇm@wJĉL¥@Ý_@a¯yUWw¯¯Uġx¯aÝXVmaU£ó±¯nwa¯óÅVXmanUlUXkWa@mkIğamIklÇUkĊzkKlUōĬl@nX°@llUxŹ²mKĉVWwk@UbUK@bmVmIVmwaWxXlWČmºÞÆbUxV@ĵńWÆĉLkWUbaWzkbĉ`U±LklōwUVÝ£UW`Uwk@mk¯VkaõVX@WbLK@XƧºWzxK@lmX@bkVVÆk¼Vbk@Vn"],
                encodeOffsets: [[128712, 46604]]
            }
        }, {
            type: "Feature",
            id: "2302",
            properties: {name: "齐齐哈尔市", cp: [124.541, 47.5818], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Þ@ÞĠKV¯a°@KVblaČUmnnKĊÈKX°Ġ@Þ£ôllÈy_@a@aKÝVwU@±¯Ulkw@kÞJlÅUa°ŃČaWVôƨVU@»nIb²KÞ°Klkn°¯I@kK@ĕÇÅ@aX»¯@VĵlaÿVamI@aÅÝउýĊȗJôȁÅkmƑÛ@kxġ@@laVk¯»īŹak¥Å¯JUaWU@@wa»KUkÆkUmUmwÛ±±UUbUUXwWwÆÝklkUanaWwnKlkal¯kaƽakÅxa¯@amb¯VlÇwÛĀV@xmêVÆVVaôVwÈx@ˌx¦VÞ¯VlmX@L@¯Ua¯LmV@°XċKV@UÈ@¥@wġIUkm¥Źw¦¯lmn@°kxVV@¦óamn¦l@nxlĉVómxnÒĉĀĊ¼þǔêÞ°ˌĠÞÒ°ĀɲĀƨźˤȤƨĊ°w@£nymwnkUUV¥ôÑVmkÆmUUVamVIkmôlxkXÞþbll@kVƆVxV@¼VÒ@UnnÞJ"],
                encodeOffsets: [[127744, 50102]]
            }
        }, {
            type: "Feature",
            id: "2310",
            properties: {name: "牡丹江市", cp: [129.7815, 44.7089], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@U`lLUlVLUlbaôlKnUbK°¹²W°baÞbknyUlUkamř²L@m°@lm²n`ôÅlKxÜKnxV@l@ÅXyW_k@wmŹĕmX»Ûl°ôÈ»ôô_WW@Ual»wU@@wUV@VXI@wĢ͑ÞȻaU_@mUkly@¯óV»XmWUXUWmnm¥nUUaWLk»Æ²IÇawÅaÝ°¯nUa±a@¦õÆğ@@ÅbxUÜnÇłlb¯¦ôó»m@±Uk@Wwa¯xUV°xXbÇÅUVK@¹KUaȯ@ōÝXallÛkalÇUǫÇÅÇakbÝƆ¯nl¯@¼VUx@x¯W¼Æ¯mĖĬ¯ČVkķÅmx°ô²V¤bUnÞW°bĢw°V°XxV°z@bÞ`@¦KĊI@xnÈÈKV@VXKxXmXUxab@kXllĊnVlUxXkxlÆkm@UVl@ÈwôxV¦bU`@zÆV@²KllÞz@b"],
                encodeOffsets: [[132672, 46936]]
            }
        }, {
            type: "Feature",
            id: "2312",
            properties: {name: "绥化市", cp: [126.7163, 46.8018], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ऊþÆÞ@bnJUbĀnblĊÞlĸwǔÈŎKÈnôWǬêKV¥ĸôUx@VbU¼m`nnĊĊxlUmkaVÿLw@°»UmbKmÝUwUmVknKUUl¯KUUÈnK@ĠkX±lX°L@¯¥@wV_mĵ¯WwL¯UkōÇVUlwVó±¯aVka°wVk°mÞ¯ŦřÆl²ŎkU@mUkb¯ķ±ó@kxȯó¯VUÒkÝ±LÛwÝ@ó»ÅUWwmğw¯Ñ@UkV±@ka@¥¹Źÿ@aÅVwóVVUkU¯JÜóÈUl¯yk£laUaVÑÇb@ţ@kmómKV¯IU¥@@kVI`@ô¼blUlbÈb@xÇKkĢɳaÅɆō@VK@z@@¥ÆKnÜ@@aÛUwwnUķ@_V°@klVnULVVÞbVl@°@nxn°LÅÆlVÈmU²@VmĠLxn¯xkWzJwnLmbXbW°Æ²@x@JVxLĀ²Æ°I¯ºÈ@ÒnÈ"],
                encodeOffsets: [[128352, 48421]]
            }
        }, {
            type: "Feature",
            id: "2307",
            properties: {name: "伊春市", cp: [129.1992, 47.9608], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@K¯kWW²ğl@mLÇVVLk°VVmLUlVnxVnÞLnaV¯¼@xKUĀlbn`nÆxô@VbU¦ĸŰĸbôxÆ@V¥»IVl°LUll@²mVx@ÞÜÞVnlXÅÒlbÈaVVUblbJ@I°lÞInÆmxnbUbVLÅVm¤@ţVÇ¤XÈÇĖ@È¼aXVÜaXbWnzŎařKôbUlw@¯naÆKnUU¯Üa@mkkVUĊmżÝǖK°L²lÆI@¯¥ĉƛVaÞk@ÝVaĠlnUVwóma@wĉ@aVxamX@a@UaÅLaVW_nWm£nWm_ÅV¯m@mó¤Ý¦¯ÅalmX£VWUÅwmÇ@@IVWUw@aI@k@wŎ»WÅVaKIka@¥lUkUlwÅwVyÈwWU@a¯U°mÇ@UçaVa¯mV»ÅwÝUlUkV@kmUkX£w°@@ÇaÝIamÛam¯lğmmI@JUl±ÅōkWa¯VÝa@Þkbġ@xÛnÇm@akkōVōl±kÅťŚÝ°¯nUl¯xlbU°b²ôUxkVÈUŎVl°KXxĶ°nU`@x°¦@"],
                encodeOffsets: [[131637, 48556]]
            }
        }, {
            type: "Feature",
            id: "2308",
            properties: {name: "佳木斯市", cp: [133.0005, 47.5763], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nbÞJb@È¯@xW¤Vln@lUVlkÞVÆxU¼°nUbbVèÈ@nIn@ĢmlUw°żVUn@lnL@VôbwĊlJķĸĢlwôwƨxVVUŦxLźÈ°`nnĠwŎJÞĶwôJ@¤XnÜĸln°¼È°lUbx@l@ÞÞÈm°lôwL°¼ĸ°Þ²nĠ@ôwÞ`ŤIVÒĠU@VJĸbÆ²@°ĊKJĶaĢȰ@ô¥°n¤bČU@VxmUw@aÝţÇķ@ĕķīU¯²@ÆmVÑô¯X¥ċç@ĉ»U¥ÝţKWVÅkUVÝŎUmÇÝx¯aķxÛUóL¯a±óōb¯ÑÅVÿ_Åķa@UK@wm@Van@UmmLVa@VImmXUWÝUÅKUwÝUUkVk@l¯XÅ_J¯kJmÅLa@¥U@¯Vz¯@`@¼mxƥŏKÛk@±laÛ@@Xm@@xƽ@WŎnˣĕÅ@@aÅ@@nÝbÇ¯@_UkUWkbwÝU@çWlw@anI¯lyX°m°VaÛm@mVwÞK°XlaXmm_@UkwÝK@VIXmV»I@a¯ğWbġaU_¯JU¯ġĉkō`±nÝÆkbóĊ¯XĢXmVn²JVlbUèČmKwlóğxxV¦UaJbƑÿÝLl@bmbġx"],
                encodeOffsets: [[132615, 47740]]
            }
        }, {
            type: "Feature",
            id: "2303",
            properties: {name: "鸡西市", cp: [132.7917, 45.7361], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@LKVVnkbVÈb²U°VnklVlaÈL@anU°ÜmXV`nôLèxlLXL²aVVmÈX@ķlnUÈl`È¹@Ť°U@xKnnVmlnnUllVnnaŎwlVÞÒ@n¦LV°lwVkLaÞlnÒ@xmLÞ¤Wn¼WÈLVVUxlÈôWVaU_VKKXUÆbnnôKbÞw°bÆWXamVwKUw¯WUkUlJUwVUa@@kmyzmĉw@kVwkW¯ÅKU_VmxU@aW@@kK@wa@K@@kVUaky°_Vmkna¯K@Lwġk@@IÇóXwVakmV@mwXUWanlĉ@ÇUwKóܛǊÛm°@wÅ@±b¯W¹WVwŹĕ¯kVmōb¯w@awmVUUbVIkaVwķxk¼b@VXXó`ó¼Çó¯kÜ¼WnźĖnxl@X`WzÆ"],
                encodeOffsets: [[133921, 46716]]
            }
        }, {
            type: "Feature",
            id: "2305",
            properties: {name: "双鸭山市", cp: [133.5938, 46.7523], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@UUwómÑÞÑUÝÝUkmmÅyV¯ī¥Uÿĉ¯mÇkaWbÅX¯aÝxaóLmmÅaWVLULV`UbXókÇVwUUÇKX»XmÝ£nK@wmÑkÝbKUlx¯kUKm¥@ÝÑkUōxmbUmkVkmmnkUmmL@w¯Vţ@Çºk_ÇmVk@ĸVxVÈ°lLkllUbōwnVW¼nlUx¯XmWUnÝ@xÝUó¼¯J@LVbkJWnkbW¯ÝLUxn@nÜb¯U¯nWkz°mJ@bkxX@èÞVxlaXlVV`°@ÈÞa@mÆ@@bÆ@ˤĖmXōƾ@@wn@@WÜ@kb@²ÜlŐLƦnw@»_°@y°UV@@¦bÆKnI°lIÆ`°W@kllUVÞVVxLÆÞVXWVnnUJ@UbnKVnm@Ubn@@xL@VbÆĸ`UĀÆÒ°Ŏa²ô°bôKÜVĸw°bÞwÈVnÞōVUÆlXU"],
                encodeOffsets: [[137577, 48578]]
            }
        }, {
            type: "Feature",
            id: "2306",
            properties: {name: "大庆市", cp: [124.7717, 46.4282], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@mÇ@ÑÇ°¹¯J±ÅÿKUwI@w@±ÅX¯WanamKxIylX°wmwğKUn±@nVÇUÅkÆ¯Kmmw@@¯UkÝaUUVKmUlk@¯U`ĸ@VmxVxÜ@bÛ@mÅL@¦@@yLUŎ@ÆɅɴblġÈL@wÇaakkVa»@ó¯_ÝJwÇaÅXnyU¯¥Å@wbÝaLmm@@VUlbğVm¯Xm_`¯_UxmLa¯b@maó¦Çk¤V@bóJknVxVXx±aLUbVxkLVlLWl@nX@VÅbWlÈnxbWÅbm@xbml°bXbWXVmnn`Lmnbmb@k@mwU@@¯Jlbk°lbkmLXxmbVbkllÅÞxXxVWVVa²VÜ²nxVVnÅlVlL¼b@xV@XVbIÆ°¦lźbĬ°¼Ulb@kĢ@lw@ƒÜlnȂÆóȘIĉ"],
                encodeOffsets: [[128352, 48421]]
            }
        }, {
            type: "Feature",
            id: "2304",
            properties: {name: "鹤岗市", cp: [130.4407, 47.7081], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Þ¥ô£nn@°ÆUn`mXn¤mX`UXbÆKVb@@bnWbwUbĊ@x@nbWVm_mm@ó»UmÅWXkĠ»²¯¯nķwŎ@ĊŎK°bĸUnÑKČ¦ĠÈbÆknJÆUĢV°IVƾwaVkÇ¯¯»mķkÛWm@£óIĵxÝōIğxmm¯_ÇŹKwťUVUƧwóxxġkĸķIkĉxóa@UmK@kVmUŻ¯Vxkġn@mmJ¯n°V@bXVÇxUzÆxkxlVkV@¦lbJLUbÆXō¼@xl@J@bVxXU@JÈ@nxVÆUXW¤knÆb°"],
                encodeOffsets: [[132998, 49478]]
            }
        }, {
            type: "Feature",
            id: "2309",
            properties: {name: "七台河市", cp: [131.2756, 45.9558], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@²mŎ_lĊĢV°°IV`ĢbaĠX°@bJU¼WnUJ@ÞLlxV@n`lIUa@K°Iô»ÞVwÞ@VmnX°WVwmkX»UmŎxVaklkkKÇ¯UUwÇWUnU±bKWKkwçóKmU_nW¯ÛmV@bÇKkbkUml¯U±VÇaUamlUULKk@U@mwÛLwkLóÆm_±nk¯@@n±KnŚlbkVVmzlWXº@Ķ°"],
                encodeOffsets: [[133369, 47228]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/he_bei_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "1308",
            properties: {name: "承德市", cp: [117.5757, 41.4075], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lLnlmxnIVVlUnb@VVxXJWL@LÞVnnVJ_@wkmKbxwXkWXXKlb²K@nVVVbL@WlU²lKVnUJVz@VVb@lÅ¼mVUVnbôaVX@°Ub@lWbX@b@bVb°x@VxÈLVlaÆ@Þb²k°@lVU@Xn@VWLXb@¤VXKVVVLnm°_ƨ¤@aUIVaalkX°kV@alwUVyU@kó°na°UVUUmUÆw@mkLVUWVIWLnn@xlVnKmyU@U°UXaV@U¥U@UÆ@aVUkWU¯aU@WLUV@bkbmKULmKkUVUkmVIUwlWV²Uml°U@WLUwVm@UUK@_KUUÜaXw@VKUU@mVIUUlmnIVVVbÈVlKnbVK@nI@nVnwVLVKKVnb@aUIVW@In°@lVnI@lWĢ@°UVL@b@VyUUa@w@WUnU@WÇ¯K@UkkJWaÛbmk@mVaÞU@amkW@mXUKkÿ£@akl@Um°UXwlaal@nmlXnW°znW@awV@akbĉ¥VmU@IVUJkUmWUKbmkUaKkUVU@KV@@klwWaU@kmXVènbmlUUKX¯JkbI@JmIUWU@Lml@XkJ@UkK@aVKwWaIWwmU@mU@J@UaċUaUUVkI±k@UU@UbVVm@UVKLlkIWaULUWXUJU@WbUb@lkXUxm@@JVn@J@bnb@Vkx@bLUÆnJaVXnKVVmzX°V@_lJXxWXK¯bÅamU@lUIbñJ@LÇKkIÇ`kxWL@@@bUVUb¯xWKkÅVlULW@n¦Ul@IlmUUUVm@kWnkKma¯XUKWmnwVwÝLmVUbUVWb@LnxmxVmbXx¦@nb@`V@kbLUmVUlkbVXkºmnm@@xk¦bĢÜl"],
                encodeOffsets: [[118868, 42784]]
            }
        }, {
            type: "Feature",
            id: "1307",
            properties: {name: "张家口市", cp: [115.1477, 40.8527], childNum: 15},
            geometry: {
                type: "Polygon",
                coordinates: ["@@kġÛal¥@wn@nml¹UWlaVknUVKla@U@_ma@¥WwnaUwnmw@KXaVUVaUnmWUk°lnUVUXWVwIWVóKUI@WXxUU@mma@kUKWLkw@yk@aVkUUċaUU@Wk@Unm@UVmLm±IUkJkW@aI@m@UVUla@VXVXmVwnkWKKU_k@m¥mX_JmnU@km@U@KmUVU@U@Umk@@LmW@Û£Wka@wk@aI@mmk@mUa@UmUIwW@aWUbU@kbÇ@kw@makVUkU@am@aU@mxkUbKUXU±KXVWLUK@wkU@V@WXUa@WbUxJI@¦VèVVX@±ê¯KUI`¯UULVx@V@UKIVkLmVkKm@nUJÝbkIUJVXVVxVbUVJUn°bVmlU°XnK@Ul@lVÈVUXx@W@VXVKÞbn@VnbVm`UxkW@UVkLKm¼@lUnUJVnVXV@Vm@@LVklIkl@VWlULWKUL@mJ@blbUVUlmzUJUxm@UUbċÜk@Ub@VLVV¦ôbVmUKUkU@m@VlVn¼WbUJ¯@@°nIllÈl@nXWlLkJ@bkxlxkxlXUlklJXL@bWn`@nÆXxlL@xl@XbLKlVlIXblVUbUJW@lX@VL@VVXJwn@WnL°KbVbl@VI@K@U@nmVmV@XUWI@aXm@VUUkWmn@lmUUk@mUmK@UnwVĉ@mU_V@XJôVVULVUn@llUnJl_n@ml@XlLlw²LVJUL@VmbVblVXmVnl@Ť¦nn@Ü@bl@@XV`Unb@VlLVb²JXn¥ÆÑ@¥Þ@"],
                encodeOffsets: [[118868, 42784]]
            }
        }, {
            type: "Feature",
            id: "1306",
            properties: {name: "保定市", cp: [115.0488, 39.0948], childNum: 23},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VbXW@@UlV@xVLXKWU²LVVWLalVnwV@@bn@bVVllUnb@lxÈ@laV@aXV@bXxJnV@VVb@nnl@nJ@bll@aU_VWUwVUkUmUkb±mVwU@VIUW@UWk@VU@ynLm@IV@bnKLVaVmnIlaXwV@@WVL°@@xnX@V`V@VbUVVLVKnwnL@ll@@_V@VVnaÆ@KVXÆ@n@wKmUWm@km@kÜKXU@ÑW±nIUwVKla@I°wU±kkmm¯m_JnawW@IVaUama@wUmU@mVw@aXk@mWa@£km@a_kVmUnWW@¯bkUmk@VÇm@@kUUKUU@UVUamVUaWIkb@xU@@amUkKVkam@@kVUkUWmKmUkLUb@xmJU@UImVÛVmnUwJU@VX@UWm@Ub°¦UmxklmX@`ULU@@UW@@xkn¯@makVUmxUb°lUbUbnUJUUVaLkbUUJUU@mUUUJka@xUIWJUnJ@Vz@kb@`@bln@lb@X@@@XlbnbVb@VJlInlbVw@UKl@lbnan@VbJôLnUzlV@lÈLVbVK@LVxVWXX`WxXzbV`UXV¤nx@bVlVnVlUL"],
                encodeOffsets: [[117304, 40512]]
            }
        }, {
            type: "Feature",
            id: "1302",
            properties: {name: "唐山市", cp: [118.4766, 39.6826], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@VVl@²lJUVVbČVVb@@InV@VnXxJXbxUL@bLl@VlI@WnkKV@VXnJ@IJla°IWLVVnkmaUçWVkôaÜ¯@nV°wnJlaV@VUnUUaW¯wXWWwna@£UaWKU¯¯@aVUkKUamUUn»anIVwUWlk@LlWVakU@K_lbÞU°@y°n@KÈkWWţ¥ĉōkġWUw¯£¯Çwţw@kK@k¥ÝwÅbÇ¤ÛťVlW°@ĸx@VVVULVLkl@V@X`Ub@Xm@UWbk@ÆVbnLWV@lnXUbl@X¯lmUVkKWLkK@_UK@U@UmmUxmVXLWVULkU@`W@ULUK@XlJXzV@@xml@VU@UX@Kk@WbUK@Xn`XmJnmkxUVbUVlVVxUbV@nKlLkVKÞbVKXI°KVmVUIUKULVxVJVLkV@V@UbU@WUU@UbUK@b@nV@VkLmb@b"],
                encodeOffsets: [[120398, 41159]]
            }
        }, {
            type: "Feature",
            id: "1309",
            properties: {name: "沧州市", cp: [116.8286, 38.2104], childNum: 15},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@ln@UÈl@Vnl°aX@mXnVlU`@bln@¤Xb@nWl@bUx@nnVV@xnbVbUb@JXxbmXa@kUVwlWkKôVm@wkkK@kl»ÈmVKXkla°@XVV@VI@ml@@Vn@VX@V@J@VxUzVV²blVk¦@Ġ@@»@VK@VÈLlK@XnJ@alIUlaVVb@n@aU@WUIV@mUn@mKXml@lL@LnWb@XV@@aVVbV@VVIVWÈbIÈ»ƒǟlWaVUÅUUm@kVUWVkaUwmaóUJUU¯ÑU¥mk¯UaKÅnÇyóXmWÛX¯aċbÛaJWÝU¯»aóóUm@IVVl@bLUJWLX@@xXUxl¤V@VnVUVXVbV@@@VVn°V@ţU¯VUmUWV@mUXabUKUwUaÇKnVk¦Wb@VnLmV@bkV@nxW`Å_UVV@bUklVX@VmlUx@VVL@xVWVL@VW@UUm@"],
                encodeOffsets: [[118485, 39280]]
            }
        }, {
            type: "Feature",
            id: "1301",
            properties: {name: "石家庄市", cp: [114.4995, 38.1006], childNum: 19},
            geometry: {
                type: "Polygon",
                coordinates: ["@@la@y@UImVXIVJw@lbIVVnV@VVIVVlaKbVUVVImVaaVk¯VanwVlUnb°@lm@wX@@VV@VK@_nWlknwV¯¥Van@VX@W@UVIVxnmÜUnUVJV@nI@wValKnV@kmU£na@mVk°KLVa@UU@UmknWWkXU@aWW@@km@UaU@@klK@UkaWaUnamm@Ua¯wWU@UkL@Un@xVlUXVJUbLmU@aUWUkmKkLUUm@mWXammkkWUm@@U¯JUUmkU¯@mKĉxÝwÝ¥LUómwkUUUWVkKmkKmLXlxVLVxXJ@nVJnz@VWL@`nX@x@kVUUmJmIXxJVnUV@UVV@LU`UXVVlXL@l@b@VmX@bxn°UbkKWLXlW@@bKmKULmakLUlmb@Xb@xmXU`Vb@`lLx@nWVXL@°WlXnlbKVKXVb@X@l_lJ@V@XnI"],
                encodeOffsets: [[116562, 39691]]
            }
        }, {
            type: "Feature",
            id: "1305",
            properties: {name: "邢台市", cp: [114.8071, 37.2821], childNum: 18},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nKlLnlLXUVVlVnxôVKÞ¦ÞxĊwnL°@lVnVV°I@Vn@VlXnlnbWnXn@VVlKnLVlVX@bnVKVaUIVWkU@wVm@¯@U¥VmU_°lKkw@LXVaU@wUUUKlUóW@UVUUl°KwlKU_naKVnlKkkWWa@IJVa@IlJnU@KVUUmVlaXUl@lm@kXWÝÑnk±k@wğ@@U@mKĉLmVJ@zmlnWLUÝJU_@@mJkXUVlbklÝ@Ýab¯@¯±JÅwġaUU@kU@mVI±bUKLWUXJkaLóKULWbUVkKmnk@@bmLUl@b@mnmJkUULabnmn@lVV@¦n@l@bznx@`Vz@bxnV@xllbnKVx"],
                encodeOffsets: [[116764, 38346]]
            }
        }, {
            type: "Feature",
            id: "1304",
            properties: {name: "邯郸市", cp: [114.4775, 36.535], childNum: 18},
            geometry: {
                type: "Polygon",
                coordinates: ["@@bVKlVnInm@@akVnK@al@nmlLVUXaVKôLKlbIVWXKVL²aJnU@lV@VVĢbÆx²I°°@aÞbÞ@lkkaVUlWnI@@V`ÞIVXKmnk@yInUĊKÇkUUamUUk@aU@Uk@WUwVkVJVkkw°a@mK@UX@VVLVW@wwVa@¯Xm@@lUIWaU@UWkXWmU@UwmUkKmn@lkV²VaULUVmJUUUwLma@UmkIUmLmVmx@bLUamKÅL@VmbkU¯KÝamzkJUb±VkbL@lU@WIkJzkKmKnUalWkkKW@@nkbk@WW¯XUVUJ@XlJ@X@XlWLkU`VUnaWaUV@UVIaUxUUmVK@I@W@ÇU@@U@b@nmKXmx@UxkVWUX@`VLlL@`zXÝb@b@VUVkIUJVz°KVlnLlKnLxlLVVUVlXUJ@nnI@mVUlbn@@m@bVnV"],
                encodeOffsets: [[116528, 37885]]
            }
        }, {
            type: "Feature",
            id: "1303",
            properties: {name: "秦皇岛市", cp: [119.2126, 40.0232], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lnV@Xbkx@lU@@LUVlVLVbnlaLXVVnlIVUJV@UnĊ¦lab@nJ°UmV@wn@VUJVI°bnWlXnWVLVK²bakklI@aUaVUwVUUalaVwnUVak¥X@WkLVÓmmUK@_lW@n_UK@alÅ@ğÅƑŃÝm@ÑţÇlL@¯mz¯@ÝVak`@LlVUbkXK@klVXUxJmbm¼VnVVblLUV@b°V°XLVb@¤mbXxWX°xXVbmVUVU@kbmI¯xmU@Û°óbUl"],
                encodeOffsets: [[121411, 41254]]
            }
        }, {
            type: "Feature",
            id: "1311",
            properties: {name: "衡水市", cp: [115.8838, 37.7161], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@KVlV@X°xb@VnnmbVXblb@VkL@lV@Vbn@@l@XX@bWVXlmXnlVV@@VUbK¯LUl@nmbV¤n@lLXnlVUV@ln@lbUlLnV@bV@@wlaXJVbnUVbVU@VVLVVn@VVX@@UKXUU@wUK@UwVnk@UUWlkV@aUVUÆ`X_w@mlU@anUmK@UXal¥UmÈLVbVxVLabVW@nXUVnV°UŤV@U¯Um@U@@UUaWVUmUUU@k£VwW@wW@XKIUa@wU@@al@UK@_mKXKbUU@aVKm@Xm±@kbÇakLğVaUw@a@mkUJk@ykw@£WX@lknk@WVkbUVnUVL@mVkI@JUbI@JXbXllkLUmLmbV`kLx¯LkVUV@VôXkVVLVV@xVUbW@KxlL¯kV`UnV¦°@"],
                encodeOffsets: [[118024, 38549]]
            }
        }, {
            type: "Feature",
            id: "1310",
            properties: {name: "廊坊市", cp: [116.521, 39.0509], childNum: 9},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@laU@UnL@VWbklWxnIVVV@XJlbUlXVbn@@KmV@@X°WVInJmn²@lmVbnL@amKV_kwlmX@@LVamaXaaVU@UnJVanLlUkaW@UaVakK@IlKUU@an@ln@alKUkIVa@a@klaUKUV@UkUV¯KVV@kUmU@@a¯ImJUU@VV@UL@U@@WXUWa@Ukwm@X@@w@al@@aVIUmVUUUVWUknK@I@l¥kU±aUUVyUw@@I@UUWm@@Uk@@nUJU@WU¯@kbWlULnÇk¼@llLl@xUnóLlkXUxV@lWbI`°nnnllV²¯x@JkbLUVxmJX²@ÒWVÛL@lln@XnnVL"], ["@@@kX@Valaa@KWI@UXW@WanaUIW@UaUKķk_W@UVUKU@b@UamxVXnJUbWVXLVbn@W°kb@U@Wó¼mIU¼k`V@bVbl@lX@lUôVlUIV`lXVn@lUlVn@l@UVaIUWl£UmVWU@@UUKlUUUnVL@KUnLVWUa@U"]],
                encodeOffsets: [[[119037, 40467]], [[119970, 40776]]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/he_nan_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "4113",
            properties: {name: "南阳市", cp: [112.4011, 33.0359], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lKl@nVV@bn@VVnmnLLXx@VLlKVUIXWÜ@Člbl@XUĊUlwnWLÞwm@ÞUVmnVl@nXJXLm@VnnJlaI@VkxVb@VlnJ@knKVn@°aVanal@XK°b@¯VJXIVK@al@nVk@nKab@XL@blVVKVLXK@VaVI°mVaX@V_@a@yUkVwVIVaJ°@anIlaV@nKnXÆm@wUUV±UUWUKnaWwXUWmÅ¯Vam@kakImUK»lan@VXXaW@@UlUUa@a@UlwUV@Xal@@anIVaUK@VXmwVmUmVLXl@nalLnal@nKlkV@@UnJUXnl@nVl¦V@@VnJ@nUVVVVIn@VaJÆn@@K@mka@kmWVaUI@a@k@@aUL@mmaVIUKUV@@IU@mUmmL@K@UUUU@mW@@nU@ğ»mVmbk@klW@UXnV@LJmlUnUJUUUW@UnkKxmLa@@@lUUbmUVWk@@nkUmam@UakJU_Vm@ÅlÇLUVmVUwULKU@k@UVUlU@@U@UaUUWaÅzJaWLklb@bmL@kKabWUV_@mV@b¯JmXUbUK¤ÇLUU@b@JkLWmkUWIkJ@VmX@JUbVXU`¯VV¯blK@LXKlUV@Um@@Uk@kxWkbL@KkbmL@UXmaU@@l@x@blX@xUJ@bULUlULÇ@@VnU`W@@nÛ¼U@@VmKUkm@VVX@@xÇ@bUbVb@VX@@xLUb@l¼XLlbUlVVUUb@n"],
                encodeOffsets: [[113671, 34364]]
            }
        }, {
            type: "Feature",
            id: "4115",
            properties: {name: "信阳市", cp: [114.8291, 32.0197], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VllInJlknJVkVU@mXlUÞ`VnVVU@U@y@nXlKVnJVkXKWaXIb@yVkVUkVwn@K@nW@kKlUXVVUlbnUV`n@V_V@llX@@Vb@bV@@nlVUb¯WLnbmb@nLnKbUbVWnLlaX@VVUX@Vln@`kL@ll@VXVJÈIVl@XÞJ°UnaLlylU@UXKlnn@lanLWWnbVI@KXKVL@LVWVL@UVKUIVWX@@XÆJ@In`@lJVI@aWÛnK@UlK@UU@VKnlmnXalUllLUbVVknJ@nV@Vm@al@@xnVlJVUU@w@ak@XW@_mWnUlŁUmVKV@VXwW»XWaUwnkWUkVUU@@@WlaUkkaIWVkm¯xmIUmLUVaUIó»m@mmwXk@amk¯¯l@wmkLmmU@UbkUWJ@XUbJ@b@l@znÆmK@Xk@Ub@lm@I@akmVKUUVUkU@U±JUbk@IWmkxa@UUVUWVkIUaW@UlLWn@VkJI@VkK@L@bmKkJmUUaUKWXk¼VxnJ@V@@VULV¼@@UkaUlWL@U@W@IkKmL@KULUWULWKUXUJmIbK²UWnWKUUkLUmUUam@UU@mUL@xkV@VV@bmV@Vk@mwkUVUx@mbXÇnVbUL¯WnUVLVb@xnlWnU@UVUVVUbVVlVkn@llVUXUWUXVbUJ@bmLUJnb@nVK@bl@@@bVJUbnX@lb"],
                encodeOffsets: [[116551, 33385]]
            }
        }, {
            type: "Feature",
            id: "4103",
            properties: {name: "洛阳市", cp: [112.0605, 34.3158], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VVUllLXl@LWn@J@bKUVmnL@`VblLnbV@b@JmL@LnV@VV@¯VJVnXL@nm@aÞ@ak@mImVbXLynLk°@°aVJnUV@UVVXk@WJ@VXLlUnJVnn°U@»°Uwl@bWmUXÆ@VLXU@m@Ua@Imkba@naWW@_@WXUV@@U²@K@I±U@¥kKWLóLla@£Um@kWKXU@mlLXUVKUU±J¯_@`UL¯Wmk@WakklUnVUVaU@KUU@mmK@_a@KX@VaUIm±kaVKVUkw@kaW@kbkL±UUaK@UUKVak£@UmmL@lIkmU@Ualw@UJkbmIUmn@WKImWk@mUUnÝV@nÝxKmXkxĉVWVk@kaċÛ@WXJUV@zmVWnbUbVbLlUnlUÒnWVVWnk@@Vm@kxm@Unl@Ll@@V@XnkJVV@nlVXxU@ln@a@VLnWĊ¦nx@lbVKXLl@ÞVLXJl@XXl`lIXVl@XlXUVKwV@lanxzUbVJ@VVX@b"],
                encodeOffsets: [[114683, 35551]]
            }
        }, {
            type: "Feature",
            id: "4117",
            properties: {name: "驻马店市", cp: [114.1589, 32.9041], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@n@b°UÆXnVlnLÜ@VLm@n@na@Jm@k@lVVxXX@V`lLVXVV@VVÞLVV°²@labnxV@@bLmlm_VWnIWUna@lLbnV°VL@KVLVUVaVLXK@mÆXna@wVma@Xw@KlL@a@Va@wUkaWnIVla@Kn@Vn@VUl@nKVnJ@LnK@aVkVUUW@VakUVanI²XW@UUU°KnUVLl@XaVK@aU@KUI@W@_lm@KkLUKV_U@»@UVJ@XV@@mVL@K@U@Kk@VwUUm@kmWL@VkVkzKmb¯VÝI@WUkÇJUIUWk@@klK@_km@UVWUUW@kbmKUXaVamLmK@namaXK°VakU@mU@@aa@UW@kkU@U`m@U_mVkaUVWUkVL@lmX@Lm@UxVlUUl@zaWJXbWLUlmIUkLmW@@z@VUVUUmÝ_kVW@nUVUlmIklmIkJUkl@n@Lm@ÅIUbm@UJUUVU@mmI@UU@k¥mUk@WmVmI@VU@klmLk@mbkKmb@WkKUVnUnnxW@UVLUbmJ@bk@WbU@Vkx@V@bVbkV@V@XWbUWm@kb¼VLnlJlb"],
                encodeOffsets: [[115920, 33863]]
            }
        }, {
            type: "Feature",
            id: "4116",
            properties: {name: "周口市", cp: [114.873, 33.6951], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lnb@xlJ@UnLlKXUlJl_KnV@xVL@bkbVVUè@Wb@UbmkVmbXVJnUl@a°@@bLVblXxInmnLVwanJÆw²IlmnXVl°VVbÈaVb@lkn@VWnLlUVmÞUUklkVkUaVaVaUwK@kkaVWmw_l@nUVVb@baV@VV@zXJl@@kl@lk°WVnÆbnbUVJI@VKVm@kK@_kK@a@aU@@wW@@k@aUW@IUWVUnLlUlVXKVwmk@W@VWa¥@k@lnUIÇKUaU@UUVmIUVUk¥Vma@¯k@Wanwm@@n@@m@UIVkUVamUXWaVU_@mUVUImW@aUIĉK@VmIb@lU@@nJkU@KIUmmLk@UVm@Um@@LkbUmJXlbV@xUb@@bkK@LWx@bUn@xmbÅW@nWLUKUbUVKU@LUK¯mU@VV@xULUVL@bU`WUz¯aUamKUa@@xkX@x"],
                encodeOffsets: [[116832, 34527]]
            }
        }, {
            type: "Feature",
            id: "4114",
            properties: {name: "商丘市", cp: [115.741, 34.2828], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XVl@lLÈ@VkV@V»UanWX@VaÆÇô@ÈaVX@xVJXUÞUaVLĸbXKlV@m°Vn_nyXX»mUk¥lK@a_@yInaVKVa°_@WXI@@KVnIlbnaV@l@a@_w@lwUKmXa@UV@»Vw@kUKVUUm@w±VUXUKUwmJUU@km@@±mXkmUI@mmKUwkbWakLWaUIkJmX@l@@VUX@JWbX@VbULWblUVULknlV@bVJkmb¯KknWmk@@nmVkx@VmU¯KUnUL@JUIVmaÅaUm¯Xlkk@@lk@WI@yUUU@b@aUaUmVk@`nxUXlb@lLVxUbUbVbUllkVlÝVUnkVmKUXm@kl@nUx@xnxn@`VX@V²x@V@b@Wl@zU`VUVVbL@VbW@bkXllkLWV@V@VVÈwlV@@XK²LlbWnnÆL@VnJWn"],
                encodeOffsets: [[118024, 35680]]
            }
        }, {
            type: "Feature",
            id: "4112",
            properties: {name: "三门峡市", cp: [110.8301, 34.3158], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@WKUmUI°U@@UmU@KnK@IaU@makKUa@_KnmVUL@a@IXm@KWkkKVkUU@aUW@UUIVaymwkbU@xLVUWWkk@WUkJk_WWk@WIUKÝk@WKULka@mwĉ¥mXUK@@bm@kVWwkU@mUUlIWm@@Uk@@KkVmn@lwn@@Ul@XmUXUmVÑkmkVKUaVamaUXn@ykLUK@WwKmKnUm@UmaU@mUk@kL@lxċxUnkVmnXxWb@`kzWJ@VLmVUnlmUL@lW@Ub@VXUb`VLUbUJ@nmnUlUUm@@bUJlnUU@lxkb@@XJUn@kb¯VVVmlXXlJlzn@VlkVW@bkKbmkUbVblXVxKÈnwÞlĊKlVnKlwX@lL@xlUnVn@l@lmX@ÆÈb°¼ÈwVJlx_°xalUÈxlUnbVxnL@lllbmn@nb@@VL@V@@VLJnIVVlKnV_"],
                encodeOffsets: [[114661, 35911]]
            }
        }, {
            type: "Feature",
            id: "4107",
            properties: {name: "新乡市", cp: [114.2029, 35.3595], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XVlLK°bUblbUbl@nX@WXVVKVk@@mb@UbnW`kLLV@VVLnKlVXIlV@@a@l£nWlkVa@°bnUlLVlnabnUVUXKlU@@lk@aI°y@ôkUU@wmônkWakmlUkVmkUlmUUm@nkUKWanamULXW@UVnUln`lblL°KXV@ĠJ@L°JUVwanK@UUImmkK@¯±Um@IVmUmmÅnWaUK¯aUkw@W±kVxUVwnÅJUIWaÝJóIbm`ÝbÅImJUI¯¥¯@mU¯UJmnUVóUkl±V@zXlbWVXL@bmmº@@XmJUXU°llk@nWJk@U@¦U`m¯Wx"],
                encodeOffsets: [[116100, 36349]]
            }
        }, {
            type: "Feature",
            id: "4104",
            properties: {name: "平顶山市", cp: [112.9724, 33.739], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@l¤UbVL@VLVb²VlKlaX@lb@lxUVULbln²VJUbW@@Lb@`nL@nVV@LVUbUVmkVllXbl@Xn°VK@_°`²IVVV@VUVJnInaWK@U@KLÆ@nmlXXWVUUw@klKVa@knyVkVanIJXUl@XbVUl@@aa@mXkbnK@UlK@UUUVaXaWmkUm¥nWmXaWakl@VmÞbKVL@aVI@mUwVm@KÅméULKVaUk@kUK@UWXI@VlKXU@VVnInVV@VLlK@UUkKU_@WWUwU@kln@@Imb@@mnUKÛ@mKUkWVXxmbVLXVVU²VV@xÅnmWmLU@kbmJ@b¯IUbJUUxVl@z@bU`W@Ub¯nUJUb@WLUKULkU@aWK@abmL@lmUk@@bULWJUI°@¯aWLk@mbUb¯b"],
                encodeOffsets: [[114942, 34527]]
            }
        }, {
            type: "Feature",
            id: "4101",
            properties: {name: "郑州市", cp: [113.4668, 34.6234], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@nWVUKÅ@WnVnIV@kÆwV@nn@lxÞlnôJzXJl@nalUČVll@²UlkôVVUnmI°VnV°@°¦VJnIÆJÞan_VmU@ama@kU¥kaUklw@UIV¥kVUI@mmUÅmUlwVU@amUJWbUakVVé¯Im`k@wVWmLkU¯XkWmLmx@UUbm@@xJ@LbW@UUVWUkVK@kaIUamKUkkmmLUkJUVWXkWmnÅ@KL@@VXLmbmJUIUVU@ULWVkK@nWVXL@lVn@¤bkôKXKlL@¦²V@JL±@@VU@WV@X@`XXmb@blan@Jb@V"],
                encodeOffsets: [[115617, 35584]]
            }
        }, {
            type: "Feature",
            id: "4105",
            properties: {name: "安阳市", cp: [114.5325, 36.0022], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°kVaV¥kVmUkWkWVkVKUwkkmKUU@awWWXWakKWkXmlaIVmX¥U@a@WnK@kVI¯@KğI@WU¯LkKak_kmmVU@VWXKnVmbXbVLmln@VVknlVUnVlklnXbmlmlXblnÈlWbn@@nK@VLbVV°VVzln@VxIbU@WLUa¯VUkWõ@¯kkmxk¼lXUlVbVLnlULmU@lLkVUlX@xW@¯mU@UmIUWL@aXakU¯anWk°@kkKmmUIWaambUkkKmV¯a@UblkmXk¤@@b@UbULWVnb@lUVVnmnVVUJ@bWXX@WJkL@blVU°UV@XlWnXUbW@UVkVVWbnLUJWLUK@Lnn@blVUnUblxVUVJXUa@UbLnUVV@mVIVVn@UbV@XbmbUV_lVXUWanJVI@WkI@WVIVU°WXXl@la@mX@lLXlkVbmXylIXJV@@kKla²UVaIVyÞb°LlVna@UÆKnLVbK@anwU"],
                encodeOffsets: [[117676, 36917]]
            }
        }, {
            type: "Feature",
            id: "4102",
            properties: {name: "开封市", cp: [114.5764, 34.6124], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lUVbXaInV@bUVxknVVÆnn@VJlUU¦VJ@kxVllb¦lV@nb@bVUnaôJÞIXbVJÆImxUVwU²l@XxVl°bVLXb`XklUnmVblL@lmx°LVK@UXIVaWlL@Uk°KkVaVUXmmI@UÅKmmXka±KL@W@kUÇxUU@@UXUlKkklW@aXa@UKUaVUUV_@yXk@@a@U±w@UUW@_mmw@wVwmUaÇbUa¯UUkmWkn±JÅxmIbUxmKmnJWwkUaK@a¯@bk@mVUIWLmwm@Ua@WJUb@LUl@UUmLUbWJ@VL@VmXWWzUJUê"],
                encodeOffsets: [[116641, 35280]]
            }
        }, {
            type: "Feature",
            id: "4108",
            properties: {name: "焦作市", cp: [112.8406, 35.1508], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@V@VL@x@bXWV@XklUWX@J@nI@KlLKUVaV@JlL@KUk@KÞLl²_@nWlLUVV@nLWVUJVn@anV@awÞUVLVxb@lW@lbXnVn@@¼L°mKVn@bnl@nVK@blbLWU@VWLXV@nlKn@lVVbXw°nV_@¥Vl@XI@mlkkV¯VWnI@W@n¹n@aWKXUaWk@yk@kċUkVmbk@WIyóImÝkkwm@mU@xÅlU@mJXak@x¯V@¼¯VmUmmIkVWK@UXIl@UWVUU@mVUI¯b¯@lmKzWKUanJ@nlbÝ@@b"],
                encodeOffsets: [[114728, 35888]]
            }
        }, {
            type: "Feature",
            id: "4110",
            properties: {name: "许昌市", cp: [113.6975, 34.0466], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lIVnKlnVlnLVbJlb@ULVlUXVVX@a@KI@wn@aVV@nwnKlXW°lVnKUXx@ln_°JVIXyXnW@UK@UXIVanKVV@Vk@KVaXI@Vbn@nxKnaUlnVa@Xa@VçUUla@aUK@wmULk`kIWVkLmK@V@XUln@JXV@nmbUóImUa±@@ÑóVUUk@UlKVU@akWVUUlUUaUK@UUKWbUkÅJ@XWa@XbmJ@nUJ@bUKLÝaUnk@lXbWbXnmn¦lVXnWbUbVV@VkL@VmLaWl@nb@bk@UVWak@WVImJUbUlmz@lUbkL@lVx"],
                encodeOffsets: [[115797, 35089]]
            }
        }, {
            type: "Feature",
            id: "4109",
            properties: {name: "濮阳市", cp: [115.1917, 35.799], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lLXbWXXx@bVVnLllVxULUlXXlVlUnlU¦Ub¯lnK@VbVb@XbVLKVxVVnIlaba¥lU@wnalLnVVlVLXnlWVXn@@lVI@WnU@mÅW¥aW_k@WwXy@km@wUm¦lUxVLV@UwJ°x@VX@Vb@`VX@VX@llIVbnJlIbVlJ@mÑ¯Lóa@KUakX@UK@wU@lWUUÝ¯ImW¯aLUKU@k»k@mwa@UnKWI@UU@akVWKk@a±bóUWKXUmkKUmLbUx@lmLX@@bVW¦UnJkbWnXl"],
                encodeOffsets: [[117642, 36501]]
            }
        }, {
            type: "Feature",
            id: "4111",
            properties: {name: "漯河市", cp: [113.8733, 33.6951], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@LUnVxnIWa@Xb@WÆIVlXaVL@VVLVbkVVUVlX@bUVkLVl@VVôU@Ò²@VbnôJVan@mWU@ImVk@WkI@wmak@wlW@w@VbnLVb°bVyXV_@aUKVVK@wUU@aK@kmbXVmJUX`knnK@aU@mwakb±@¯UUÝKUUU@WU@VkLUKU@mUmJUU@WVkL@UWJX@VVL@lVlUbLVKnêÆ"],
                encodeOffsets: [[116348, 34431]]
            }
        }, {
            type: "Feature",
            id: "4106",
            properties: {name: "鹤壁市", cp: [114.3787, 35.744], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ón@xVVól@¯zJ@bkl@@kVWLUVmVXbVJnnlLl¯@Xlm°bVlWb@bKVXnJ@VV°nX@@wWVklUK@knVVKmkUKUaVkWkl»nwl°lö@lXV°UVbXKV@aJw@UmkUy¯UUUaK@UL@mm@XaÇkkmWank"],
                encodeOffsets: [[117158, 36338]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/hu_bei_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "4228",
            properties: {name: "恩施土家族苗族自治州", cp: [109.5007, 30.2563], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VKbX@lbUVnL°@VlVnUl@VUX@aVmaXlaUUU@wmaVUn@Vnmmk@mU@knaaU¥VamX_@WUmW@_kVaVKnLl@VVal@k¥@kUW@kUKVUlUVÑW@kÇaU»ValmkUVUVak@aV¯_@WUkmVUlU@aalI@akkVWUaWXUWwWVbÆ@lalIVK@Um@UUW@al²a¯UağÇm@bkk@w@@WaULmxIUb¯@U`UXJmL¯aKXWUL@aknmK@aWUXaWm@I@UÅmVU@aUV@bVI@WkUbXkm@VakwUKULWKXmJ@XUK@mL@KUwVaUI@KU@mmnmXka@»V@@UUaw¯yVk@UUVmmkÛÈU@mWUnmxmlUbV¦UlbWVUL@UUIUmÇKVVbUVVxknLUxV`VX@kJVVUXWaUVVlUnmKUbkI@WULmK@L@LVlLnmUIWV@akn`VXUJIVlUVVbUX@¤mbnLmm@UXk@mm@Uka¥@kV@@KkU@aUKWbkLWVkIVk@UbVlmX@bU@@mmL@bn`@Ln@llVLVk@XVVU@`VXU¼k`VULka@VllVIn¤VU@@blÜbkx@bkLkKn@bn@@b@JUnV`UnVbVKlVXUlbn@°Vx@@bnVbUllVn@VVK@UnW@UVUlnkVÈÞxVbVVIxVaÆ@@aka@UVaU@@ak@Wl@nbVIÆ@Jk@L@VlXnlla@VJnw@UmwXU@aVK°ÒnllnLlbxnKVaV@l¦²nVl@llLx@XVVĶ@nax@U@alXUVaLÈþV°XxWXkK@mLnlUb@bxnLVlVVkb@UJ@xWXX"],
                encodeOffsets: [[112816, 32052]]
            }
        }, {
            type: "Feature",
            id: "4203",
            properties: {name: "十堰市", cp: [110.5115, 32.3877], childNum: 9},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@@a@w@kV@nbVK@nUla@laÅl@nlVakwWX@WkLaVmwV@anK@UlIXmWkk@@mmLkWlwk@U_mKXwWK@U¯K@UU@VUakmkIyUUVUmanU@mlwk@_mWXaUWU@Ç@U@aUaVwUKUIVkK@UWIXmaV@k@Vm@UnwlUamk@V@ULUamxUJkU@I`WkkK¯XWak@@W@IUVLWJkXkaÇVUK@kUmbmUUUKbkKWUkI@kKÝ@@aUm»nI@mU@UnWV_@aUmWbkLUl¯b@akkk@WkkJm_k@UV±@J@bnU@@WÝIUJVbXL@nlJkx@Wn@VkJmbLmU`VbUL@xVn@XV@mVVnnJVbUx@VnVUbVVx@nbUK@b@bJm²VUlbXzVJVJVbn@@Xmb@V@bVJÈ@Vnkn@°aVVV@XKnalLVmUnnVKVlnLWlXXKlk°XWkLUVVV@nU@ml¯nmbk@W`Å@mbLWm¯UxnêVèk@mbVnUK@kKmXk@@JUIlÛLllnbVnlJ@LULnlÆaVLnV@nkVJ@lkô@²bÆm°wLWV@VXKVXI@W°ÆVKb°UJVIVV¦XKVL@lInaVÝnUl@@bX@nmVL@lVLlVLVUnbVW@xXnbU°¤V@a@kWKUUn@VlnL@UV@Ü»@mX@V_akaÞ@VK¯@kkW"], ["@@mUkUUm@nllVKXXVK"]],
                encodeOffsets: [[[113918, 33739]], [[113817, 32811]]]
            }
        }, {
            type: "Feature",
            id: "4205",
            properties: {name: "宜昌市", cp: [111.1707, 30.7617], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°`U@blUbUVlVknUbV¼Èb@lXUÒkVUVVL@lVX@ll¦k@UbU@kmKULUbl@`nXV@XW`nUbV¦bmb@lV@nnlmnUm@UVnb@xVVVkbWnbVnVa@an@UaVUJXnWlXX@l¦@lKÆXbXV@VV@°¯°xXxXV@nV°UVWU_VWXkmaVnWVkn@lln@lb@UVLXWlnX@aXUmaVK@UXUU@WVIWXXVU@¥VK@UÞa²LlV@kV@UanKma@UVUnK@UVLXyVLknJ@UV@@UXKWUXaV@Vb@mVLnKWm@aUUm@@UkK@UlaLXKWaXI@alKlmUk@wVKXL@m@WWn@UVa@K@wna@aW_XWWkXbVW@k@U¯WWwka@UUaVIVkU@m±@U@@wVKka_@VV@XUVwU¥yUkm@V±ÈUKk»ÇLmmLk@ó£kmWwm@UIkWKXwWU@kLwkbmabkK@VLkmWIUKkUUÇIǫJXÅJULVÇLUV@UK@kI@WVI@UaWmXVVUL`±kÅLmKkkÅ@UaXXxWVXVbUXll@bkJb@bkVUVlnV@X"],
                encodeOffsets: [[112906, 30961]]
            }
        }, {
            type: "Feature",
            id: "4206",
            properties: {name: "襄樊市", cp: [111.9397, 31.9263], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@Xl@Xb°WlLXl_@JlVVInwVbVK@@UnlVbkmx@VUnl@U@nbWXJ@VlLUVJVLUxVb@b@VÈ@XVVWbnX@`lkx@nmVnbUVVVzlJnlVbUV@@V°L@VXLWxnLV`l@kxlXnK@nl@XlWn`Xnl@@UVa@VÈK£VLVanW°U@UVU@`VInmV@nV@Xa@aVW@UalkXKblIyÆXnlJXbl@@VV@nklU@`nVKLVKVb@VU@UÈKUVKIlUX@V`lIVbn@nblVVmV@@XXJUVV@knKVn@`@XVnKwlLVmUUU@U@aXL@WlU@UUW@UmU@KkLWaXkWmXUWm@U@nk@UmK@U@UaUVUUKV_@al@namWUI@KUK@aV@WUIb¥ULUJkImK@U@KV@U@a@UkU@K@wVaUwlU@mUULmKUkV@@anIWmUK@I¯mKkl@LUb±lUakLmk@WwUKÝVUIm`¯n@Uk@makJU_@Jma¯ImwUVkKbaUÅ@wWaU@VU@mXIVmmUkJkwm@mIlUKWzUK@VmLUV@VnbmLVbU@@lkU±KbÝV@UL@¦VWUWXUJ@XVWV@VULnbWVbW@kmWXUK@Vkam@kkm@UlmXUnbWlUXV`UX¯VmUU@Ul@Lll@nnJ@LnWmbm@b`", "@@kUUm@nllVKXXVKmU"],
                encodeOffsets: [[113423, 32597], [113794, 32800]]
            }
        }, {
            type: "Feature",
            id: "4211",
            properties: {name: "黄冈市", cp: [115.2686, 30.6628], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VVUnWVXnVJ@U@V@VXV@@IVJUn@V@L@KlIVlVanLVbnVlIn@@a@Kl@@IJlI@aXU@KlKkVblJXUVlU@VbVkVKXn@VlxVa²I@VlVUxln@bJXklaVWnLmÅ@y@k@aI@W@aXIlVVaV@nnlKnLVW@IUa@a@KUVVlI@wXKVV@IUla@lUXwWnnalLlxXLll°@XwVKVaXIlnb@nln@Va@U@k°UmÆUVaXIJV¯ÇUmmkU@WaKmakVm@U@aVKkkmKkVmIkÇ°£@aUUVaVVnKlkXmk@lUVaX@@Um@UmlUXVUVU@wK²¥Ua@I@UVl@UV±UIUÇ°»VkUmVI@a@Umĉ¯V±bŹĖğaÇL¯lmkX@óĀ@mÝêb±WkLn@xXx@@b@V@LW@UblţX`kxWnXô¯¦ÆV@L@JVLxkK@V@bkz°llXz@JUlVla@XUVbVKXnW`XXV@laVV@VX@V¯xx@xULVbUJ@n@LU@VmmakbUK@bIWWUUVkUmkLm@VJkb@nUJ@`V@kXaUaVmmLkUmJ@Uk@U±lkzmJUb@bVUxVXU¤L@JX@VlL@JkLUVU@mnUl¦@V"],
                encodeOffsets: [[117181, 32063]]
            }
        }, {
            type: "Feature",
            id: "4210",
            properties: {name: "荆州市", cp: [113.291, 30.0092], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÈJVlVVLXJlnK@UlLanblaxlK@XVWxXLlJ@VnXxlnô¤l@nKnÈKl¼VL²ÇUn@VlzV¦UxWVU@@U`lbUL@xV@²@@nlVUUJVb@VlbXx°XVWX_VKUwVKVa@UVKUUVk@KnblaUU@wnWl@UX@lÆ@@aIVmUkxVJUbÜ@Uk@WWnk@VVm@I@m@Un@mXUlVlUnJ@knJVU°@@aÆLX@llL@¦nJV@XblLVa²U@UlW@VX@`@LV@@bXJlIXml_lJU°bKÆLnVVl@öVmXaVIĢllUlVnLVlX@@bannxVLbn@°ÆXmmkĉ¯w±Uċ@KÝÅƧŃÝçUw¯m¯k@WkV@¯UIUJW¼kbUwk@W`@¦Uônb@VÆlÈ@VU@£UWWnUÆUnmJkUÇ£VWUI@aUU@WkI@Ua@JW@k£kaWVUKmnkKbkkVWbVmUUmwU@kk@UakUUa@V@nlx@lUb±lUbnnWLUyk@UamUK@mlk@Wb@VXL@x@xWI@a¯¯V@bVn@LkKmL@`XmKmVU@@bkL@V±bk@UaaLKUVIWXamVVbUK@b@Lm@UWkxULWVUnm@UlUX"],
                encodeOffsets: [[113918, 30764]]
            }
        }, {
            type: "Feature",
            id: "4208",
            properties: {name: "荆门市", cp: [112.6758, 30.9979], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@n@lxlInVUnWJ@nUVV@Xb@xVÆbalLVUnx°JnbI@V`lInbl@@V°mn_VJÞUVLXx@nllKVb²kVa@KlknL°@JVLXnmJ@bU@VlnLVKV@nX@lUKVaXal@VKn@¥°L@UnwbnaV@KV@VUX@lVXI@KW@@IXWV@laVLKlaXUVVnkVWV@lwXblIXWVkVmaU£VaUmVIkU@y@WakKUamU@UUK@kmK@w@@mK@LV¯U@WwkmULamVVUU@IbUKUakmm@UakLmxU@UÒWlULţÿmwkIUm@akÈblW@UVUUk@JW@XkWWUkUKUIlw@aUWknWUUmnIWaUwVaÛaVUIwVlUnJ@bÅ@@kVWk@mX@xVVkbma@LUlVVUL@VUbULVxULW`UX@V@lUXWaXlWXX`@bmb@x@LUb@VmXX@@nWKUL@xVlknkL@bWJXbWLKkb@VlL@Vn@VV@bnXmLUK@nUaU@WbXVWL@VU@@V"],
                encodeOffsets: [[114548, 31984]]
            }
        }, {
            type: "Feature",
            id: "4212",
            properties: {name: "咸宁市", cp: [114.2578, 29.6631], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÞÆLČ@V²°xĊnlWnÅ¯m@aK@°nJwnVIUaÆJÅ@wwVXW@aV_l@²V°lĊwlaXLwlUkalVVaX@lVXI@aUXJ@U°UU¥VIVKVklanLVa@VÈIVV@nk@aVa@mV_@aK@klKUa@UnKWk@@lU@@UW@@nUWUwmaVIXlV@mLXblJ@kV@kk@KU@WkUWVÅwkLmW@UmL@lULKULak@maUUÝwUJIbKUU@aWK@kUWVkUwVw@mÝ@I@wkW@aww@LU¥kJ@nVJIkVVnkVUkyUIUl@xWUkaW@@°kzWxkLUWmzk@@bVVVb@@XlV@Vl@bVbUn`Wn@WbVVI@`LVbXLV`mnU@@lL@LUak@Lk@WbUJn¦@lVb@xVb@n"],
                encodeOffsets: [[116303, 30567]]
            }
        }, {
            type: "Feature",
            id: "4213",
            properties: {name: "随州市", cp: [113.4338, 31.8768], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@n`lwkUmUVWX@lk@VanUĠ¼V@@mX@@nVVVXLmJVLnK@bV@@J@VUn@VaVUUUVWVLV@@Kk_@almaVkUU@WVVUVLXmmk@wUaUKUV@°@kmaUaÈmWmUVklaX@lVnxl@@UnaUk@VUVwVKn@VVn@VbVJUknUmmVmk_VwKUUmVak¥@UVKVIkW@UmIVWkIVkmmLkwmVU@LUU@VVXL@JmLUbmK@UUKmkKUUmVUaUnÇlk¯mJUnmLUaUJUaWL@UkJU@aklkU@¯@KWLUmUUWVkbLUKkbU@WX@JX@@LWJkUW@UVU@@LUmbamx@V¯K@¦mULk@WbUbLkVW@kVVxUb@x@LlV@V@b@VU@L@VLnlJVIVK¦aVJ@XU@bLV@LVJnXmbk@@bU`VLUVVb@V@VnL@Vml@@VXnWVXnWlXblK@LnV@VVX@VkV@XWK@bVV@VV"],
                encodeOffsets: [[115830, 33154]]
            }
        }, {
            type: "Feature",
            id: "4209",
            properties: {name: "孝感市", cp: [113.9502, 31.1188], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VnXK@L@°lVlkb@VlI@VXKVbVIVbnKVmnI°lÈkVmVbnUVVlLnVL@VnLVanK@IWKUUV@V@KVnUlxnKlnUlJUXnJ@VlXUJUL@Vl¦UbnVVLUxl`UnnnmVVlnVKbmVX@a°Ý°LaXJV@VUnKVXVK@LnKlLUbVVX@VwVJVn@@UU¥V@@UUK@maUVUkkJ@L@K@UmVUI@JU@W@U@UV@UIWmXUVmUUÇ@UVmIlmnmakK@akaW@UwVUkKVnUlKVwkVU_WKUkVW@UXaWkUa@w@VU@XaW±@IkbKb¯L@WXkW@UakL@UV@UmVUmL@UXWVL@aUVUUUVU@yUUIUa@wUKWVU@kWk¯UkwVKLUxK@nVxUlUUWVUmw@wUUyXWlX¦WbUV@U@blbUVVbXXl@lVL@bk@lxkVVnVx¦`UnkL@V@L@@@xnL@lVL@VnVVblLXb@@zlVUJVnUbV¤bUnUlWXkJWakxU@UXml"],
                encodeOffsets: [[116033, 32091]]
            }
        }, {
            type: "Feature",
            id: "4201",
            properties: {name: "武汉市", cp: [114.3896, 30.6628], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nbnmknJVUÈ@@U¥VknmV@VUlK@IkK@UW@IKV£UWVwU@aVanIly²kVl@@VnIlVnKUnVbblWU@@_VI@mlaUIn@lKVnUlVVXXJ@aVLlanbUnV@@K@mVIUaVK@ww°w@UW@UUUkbU@WWX_WmULaVU@WkbkUV@IWyk¯kly@a@UlLwUK@I@KÅUW@Å±Um@wl¥ka@@_Vw@ķa@akw@kKW£XVUVwVwUaU@VUUxWKkbĉx¯k±Uk@U`@bWXUx@xÆÅIVbUJmxIm¯@UmxnUVVbnJV@L@@kV@bVn@UVULlx°VXllV@XUVL@xVbJVV@zUVVVUVV@bUKWX@VnKUVVnU@@VlKVb@lXW@X°KaLla@JX²Wb@UV@@xVbXlWb@VUXVlXLV`UlUxkLmVUlLUVVxX@lb@blL"],
                encodeOffsets: [[117e3, 32097]]
            }
        }, {
            type: "Feature",
            id: "4202",
            properties: {name: "黄石市", cp: [115.0159, 29.9213], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VUVV@VbUxaWUblUVmnKlX@bXJVIlVUxVVVIUzlx¯@VbnL@xx@UVaXKb@XkWU_Vm²klWXVKl@nXV@@wmlK²XaÞén@ôÿ@lWn°kUKmmUÑUmm@wkImWU@UakL@bVLUVċ@bUK@alIXKWK@nXnKmkUVw@¯b@LlUL±Wn@KULUaW@kL@lL@bU`@nUb@bmlU@UÇJ@UUbmKkblUULUJV¦¯V@VWIV@bWJkUW@UbkUlbkV"],
                encodeOffsets: [[117282, 30685]]
            }
        }, {
            type: "Feature",
            id: "429021",
            properties: {name: "神农架林区", cp: [110.4565, 31.5802], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@n`lIXll@ll@b°aVklKXaVn@bU`mX@VV@nmJn¼V@bÞ@lL@lJXVlLaVLVnVnalV@VLÈUlblWXIKVU@J_@annaXm@KmI@mkk@KVkWWw¯w¯°@UUU@WaÅWkL@¥@kWWXkWmIUVVbm@@bUbmUUbW@UVk@mVkU@U¯mKVUkaW@aULÆVbb@VÅ@Un@VLWl¯L"],
                encodeOffsets: [[112624, 32266]]
            }
        }, {
            type: "Feature",
            id: "429006",
            properties: {name: "天门市", cp: [113.0273, 30.6409], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@K@UlKVm_¥UwUmlUkwl@@aUK@kkWWUaVUka@aV@VUXaW¥Xk@WWIklm@ÅxmIVÝUkxka@bWJaUL@W@l¯UULUbkVUa¯bm¤UnÇUkmUUxb@VkXÇal@bVnlJnxŤĀVKXkVÑV@nwlKVbn@nlVbVLaJ@VVUnUbVKlnXxV@°U@KnL"],
                encodeOffsets: [[116056, 31636]]
            }
        }, {
            type: "Feature",
            id: "429004",
            properties: {name: "仙桃市", cp: [113.3789, 30.3003], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VK°VkX@@VKbXI@alblwÞVUnJÆwn@lkXJ@XWVzV@xnxVXUVVVkUw@mLVwKVU@Um@alU@@@KUmIUaVUmnwmwmb@aW@UkmKkUkVġkUJWbnUõ@UkmUÅKL¯aVkIk`WnkJ@xVLUVVbUbk@WlXbmVxnxUblbUV@@VUV@nVL"],
                encodeOffsets: [[115662, 31259]]
            }
        }, {
            type: "Feature",
            id: "429005",
            properties: {name: "潜江市", cp: [112.7637, 30.3607], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@UbVxbXmJVnXVlmVX@bkxVJVLVlXXWlX@@IVlVUaVwVlnÈVVmn£°aVbUlaVUK@mVU@U@VUkaVamwUwnWaXkl@VaUaVUUK@wWI@aU@@K@_UW@kX@V±VUbkKWaU@mI@¥kKkW@ÅK@b¯@UVmI@lmIkVkUWVnm@@V@n@JUnU@mlXXl@@V"],
                encodeOffsets: [[115234, 31118]]
            }
        }, {
            type: "Feature",
            id: "4207",
            properties: {name: "鄂州市", cp: [114.7302, 30.4102], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°¥WóXmlw_ŤWkVaX@@K@U@a@WwU@mWk@ULWkX±lUnV`XWl@aWLUb@Vw@wmKUa@°kwyVUJUUVwkUUJWI@akWmLUnkVaXVbUxUVWX¤lL@lx@bb@ĸUx@`@lbk¦@xn²VÆX@"],
                encodeOffsets: [[117541, 31349]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/hu_nan_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "4312",
            properties: {name: "怀化市", cp: [109.9512, 27.4438], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@n@b@XnJ@k°x@aVUnlUXnV@@VnJWUJVnIVV°UbVVVL@²LUVa°V@aV@nmUXblLXWVXVmVLVK@an_`@X@l°VlXXW`nX@Jmn@b@nV@Lm`bUbn@VUVl@nIVbUlV@LkJUnVV@xVblVUbU@zUKU@mx@xUnn@@WV@lbUb@nVWXXV@VIV@VUnJ@VUz@JWbXllI@VXVVL@Vn@Wlb@lXVlLaV@VJ@XX`kVwVl@bkbUlVXIlnLVamVwV@@nV@XaVJVbX@lwV@n@nV@VWnIVVUÆ@Xxa@IUUKmk@mVIXmWUVJnUVU@anaVwkU@UXa@W@m_@a¯@@K@UVbnK@blIlbXa@WW_n@VU@¯bmyUkUJÇÅ@WU@kWKÅwnm°KVkmankVWnXVWV@UwXkV@mUlLnaVaX@VUn@VnVK@xlnXWU@a@@klakVwmUaV@wmIÛ`m@mVUXmlIXVI@K@aU@UaV_UK@wkUmmUKWXmVkUL@mU_nK@aVU@Ukak»@U@ymU¯UUVKkam@nka@mwkLWb¯mka_VaVKUIUw@kKmU@WK@UnmaULkU@wUalWV¹U@@WUI@WU@_@W@U@mU@WbbUK@Um@@UmbUwWWkk@WUa@anUUwlWUwUU@wlJVUnnV@@mnI@mK@U@wa@wUm@_mVUUaVUk_kċUkVWL@mlU@kn¥W@UwUWV@VÝU@lXLWVUbVLXlVIlknmU@VUJk@@@kVmwmVkxU@@XmVUb@xnKVLl@VxUxkIU`@bWVXX@JWL@bkb¤@bmUUU¯Kkmb@VVUVVn@@Vb@`lnxmblUnbk@xUmV@bmWbUV@VJIl@nVUbK@nn@VbnJVIlJVkXJ@X@lmx@bnnWVXJWXU@UlU@mk@@llb°xIUbnJ@VWbXVmI@JVX@bk@bWL@JUXUK@U@U`n@@Xm@XVW@@nX@@`ImxU@@JUI@KLmK@UÅUUV@VW@¯kUU@UamVUUmJ@nxmLKkmJkwkKm_mKXU@aU@b@Wk@ma@zUJVUmbUlU@xnXlWlXXblK¤V@@nUVVLkVl@Xb@VVKnXKVx@znW@X@@lVK@X@JXbWbnn@JUamLVVXIVxnK@aWUX@x@VnI@WlI@anVIVxkl@lbXXxVVVJVInbV@@ln¦ml@XXVWbkJWb", "@@XLVKVXVKUa@UUUmV@l"],
                encodeOffsets: [[112050, 28384], [112174, 27394]]
            }
        }, {
            type: "Feature",
            id: "4311",
            properties: {name: "永州市", cp: [111.709, 25.752], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lxUXVlXUVnlVĢJVbUXVJV@XUW¯VIUK@klW@Un@nl@V`XUVL@l@Vx@XXW`UnUbxUlVnUVlb@VnJUVVVInJlUVnwVklKnwLVJVV@nIV@nbVa@KVVVUUaKV_nVVJ@_VWnV@n¥lI@anl¥X_VKlwVlULUVVV@U@VXL@IUmn@VU@wmKXUWU@m²l@VIXWWkWUkWlkIVamUXamUnmWUU@@UnlK@XJl@kVUk@mWKXkl@@aVU@UVWUUVaIn`VUVLnw@U@K@U@w@UVmUU°K@UnV@bV@Xk@KVm@amkaU£VWUUmUUwm`UbULkaKXU@kVmU@aV_UWVIn@yXXK@klmVV_kWVUn@WUU@UmaU@wnwWanUmmXkam@UakLmK@bxUUUU@Km¥Va¯@kUaVUlmUU@mUUÇmUkUybbUaXUWWbÅLmL@VaL@WWXUKmmk@a@UUKXW¥kU@VUkxmVkUWbUJnVJ@nVJXzWxk@lVbUX@VVL@`mbUnUnVV¼k@Ulm@mwLb@lmLUK@UamWkK@£Ua@UkJkUmbVlkX@bWbUVnnUVl@bbVK@VX@lbV@nU¤x²Knblb@xVô@l@b@l@XWxnVl@VV@XLVlLUUXV`bXXmJU@@bm@UUkLW@UlUKWUUbwUmL@nklVVmVXXm@@bUKlÆnXkllVUVVL@nUbV@V@nnV@xUn¯U@JW@UX@xĉ@`m@@LV@b"],
                encodeOffsets: [[113671, 26989]]
            }
        }, {
            type: "Feature",
            id: "4305",
            properties: {name: "邵阳市", cp: [110.9619, 26.8121], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XIlJIVVK@n@VVVKnLVwVmnLVK@U@wJ@wVIÆ°X@ÜÈUÈxll@kn@VwVaXJWXn@@WVL@UUKVKV_U@@aVKx@UaV@lk@XylbUaV_Vnal@WU@aI@aV@@aVUl@XmUXWaXml@@kk@ma@V_UnUVUUWJUa@kkaWLUmk@@LUVWUkJWkK@¼UnWJIkV@b@JUIm@UlVm@Uw@a@kWXWKUknW@WUU@kmxUkVmIUJUUVmI@UkaUVUmVkwVaVmX_WW@Uw@@kUKWVU_k@mm@@VkX@lVLUJX°WVU@UIVWUaIUġmkVUkWUVWkwWXk`mI@¥kUVUUn±@mXkWknVUVmmU@@XVUk`@Xk@¥¯»mbĉó@mkU@kUKmX@UnmL@lULkKUWUU@bUaUn@Vb@l¦Ub@l@UKmnKUnlUVVbUVn@`Vn@xb@x@VL@nmJ@nU@mmUVkI@xVVVxkXVxmV@bbXVl@XlXVxna@Vn@@VVLaXaV@n@@V@X`V@@XVJ@XV@UºkXVb@xlVVKnbm@VXLV@nlL@VxJVULUb`lb°nXalKnx@lbmn@lbULVV°nV@z@Vl¼lb@VUV@bmLV`@nKlVnUXWVLnnlV@xVLU`VbV@"],
                encodeOffsets: [[113535, 28322]]
            }
        }, {
            type: "Feature",
            id: "4310",
            properties: {name: "郴州市", cp: [113.2361, 25.8673], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@²zVaVlnVl@nVkJl_XJlIVmnL@mV@VXn@lV@XzV@lyV¯²U@UlJ@XVKnVVIXl@UVLV`@n@JI@mlIKVLnUlVUVVLXaKVLl@nb@WXV°KUnVVL@xVJL@b@LUVVVUVXbmbVbn@@lUbm@x@XVVV@@@bkImx@Vm@Xbb@l°XU¤aLmnL@bl@@VUX@VxnVanLnW¥XKVwnUWXmVIUWÆLVxLw@wVmlU@¥XWUkwlÇn_UwWV@VU°wnUy@aVkVlnL@lVnw@VlJ@bXx@bVKnb@U@WVUl@@Vnbl@XLlK@aVLVKnxÞn@aLlmUaVUm@ÅknUmaUKmVk@mkk@UlWUkVm@w@kUU@WU¯¥@wÇ@aVIlUV@kUWU@UUm»@k@mKVkUKUwaUaUa@kkUWJkImaU@UK@maUzk`@zy@XmJkL@UUJmUkV@z@kkVmK@¦UbWL@a@UbmKmwUKXkVUUkmVkw@UUKmL@WUIWaJW_k@@WmI@mk@WkWULUUVKUUVm@Ub@nUÇ@U@wV@Ua@aL@akl@kUJwó@@L@V@`@J@xnnmV@bkJmUó@nJWUUmU@UV@LkWlnnmVXbmxxV@nbVV@XVm@UVlXU`Ukn@lWLWzm@UJVXU`@bVUn@lWVLlbVKVan_VxnVVVUXV¤bnl@bUn@LWlU@@amU@V¯LVVUn@V@x@V@L@VmxUKUVm_JUbVV"],
                encodeOffsets: [[114930, 26747]]
            }
        }, {
            type: "Feature",
            id: "4307",
            properties: {name: "常德市", cp: [111.4014, 29.2676], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lUmkUwUyV@VW@¯VaVmUU@KVUVUVLnaWnkUÓV_@mVU@Ýw@ka@kVmUmK@IkaUamKkXWaUW@WUk@@KVU@aU@L@J@XÇVUKVak_mWkLWakVUbmLUUmlUVKUU@kUWW@UImJ@xkLkKm@@X@óÝ@UUk@UKVULKXkWWbkaIUWU@mUk@WLaUJġ@@XÈÆVIlVnz°aV@Um@X`@XWbkakJ@amLaU@V@L°@@bn`@@XWb@VVlUxmb@bUVmVUIXVWnJU@nnlVLV@JbWzk`m@UVK²VxkLVl@Vn@V°xVKVkVVlUblx@bUÆ@@nVnUllkx@VW@@VkLWxUL@bÝ@kKkVõV@bkXVVUV@VkUkVLkVa@@¯xUxmX@JVb°WXkK@Vm@kVbbn¤xUXkJblxnXÆK²l_@Wnan@UL@bJnIlV@lU@@¯ô@lWȂIVKVmU@aXaV@lwVXn@@K@UVKUUnUbn@lWXlJnULKV@l@²a@UlK@aV@naVXWV_nKlL@KUm@a°U°@VXL@a@wWmXal@k@VLnV@@bl@VnX@mwVa²aVU@mk@"],
                encodeOffsets: [[114976, 30201]]
            }
        }, {
            type: "Feature",
            id: "4331",
            properties: {name: "湘西土家族苗族自治州", cp: [109.7864, 28.6743], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@KL@wnK±nnm@WUkÜÈn@n»@mVamkmUl@VnmmU@wUan¯VKLnVWlInyWUI@WWk@KXUn@mnUmU@WmkV@kXaaVaUmIk@kaX@Um@UKWU@UkJWkXa@IVy@UmIUVU@UJU@WXWmU@VakaU@@Xm@Vm@wnwV@VLyV@VakUUa@wUUVmlI@KUVkUamJk@VU@UmVaan_@KmU@@anm@ImWX_WWUk¯@k@W_m`@bULUKUnUWWXkKWaVmnU@@b¯UUbV±K@UKUUVa¯UUmJUVIXmI@UU@WmVmkUV@b¯w@lmI@W@a@m¯LXbmJVLklWL@V@XXmbVVU@@VU²Ul@VlX@b`XxzUmkUVÒl@bXLWxXVl@VbkLma@nmVmULVbmVUb@lnzmbUÒVl@°nLVlJkn@bmJk_VmmkblxÈx@LUbxVb@Vn@JmLVU@nV@¦VbnJ@lVVbkxbm@UxVLV@n`UnVVVkl°zxVb@VU@@ÆlXnWm¦nbVK@XVVUVVl@XKUV@nVL@WnIWXLVKVLlxUbVKXVWbn@@UnKVLVbJU@aVU°b"],
                encodeOffsets: [[112354, 30325]]
            }
        }, {
            type: "Feature",
            id: "4304",
            properties: {name: "衡阳市", cp: [112.4121, 26.7902], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lV@XV@mXVlXLWX@l@bVxn@UVkn@VJ@I@alUJXIVm@»LXllIXVVU@Kl@VnXKlb@lVbXIVVUmVVU`@nbl@@lXLVVVKVbnXWJ@VXbWxXbUlVK¦nLVVUVVbbK@ULnK@Un@VxlUV`UnnL@VVL@JV@VUnxnKVbV@@VIVUnJUVUl@nWXllIUaKVbÞLV¼²`V@VIUwlaVmXa@IWanK@U@mkVVUVaX@lnaVLÈ@¥@kkJUWJUaXkaUmwVXJ@_lWUU@¥n_KkamUK@amKnKbV£¯W@kaWan@@UnwlJ@a@@UUU@Wwn@Va@km@UanaWaUVUUVU@K@aKUI@wKUUVm¯LWUX@mak@UKLWbUKVUkUmVUKLkJ@nJ@I@mU_UK@VWkUJmUUL@WkI@V±VU°kzU@Wy@kUm@UWU@@nmKUnkJWIk`IUlmk@mUUkUb±yUX@VUV@bk@WlXL@nVlUlk@WI@kLm@VV@XVmnnVWbnVUblJXkVlXXlWXUJk@±@nXVWVnL@xUVm@Vn@JWK@UV@UUVUVKUkkxULW`k¦m@bkJm¦U@mUX@`UImUU`LVbUVUU@LUbmaU@mJU@UUIKmxkLUl"],
                encodeOffsets: [[114222, 27484]]
            }
        }, {
            type: "Feature",
            id: "4306",
            properties: {name: "岳阳市", cp: [113.2361, 29.1357], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@wUklmUUmU@@UVm@wUaV_mmUKmwkIkJmUUnm@@UUbUKUmÛamm¯xVLkbÇÆUVUzkVUlUUKWLX¦W@VUUUaKUbmLKm@akU@amVaUUVIVWkk@wk@@xmLlmÅwmbVlXlÝIWVkK@kkVL@VWKU@Ublnam@b@bnW`@XUJk@UUWKk@UKnn@xmLUVm@kbVbVnV@Vb@KnVLWXÆVĢ¦VblnUJWz@ÆVóUVbkVaÅx@¦lVUbVVknWKk@wKVUÅl@zkb@`m_mJ@xXmbVb@llV@n@llbXLUXalUlalVnwnLVKlVbX@@IV@blJ@bVL@VVVUXÈ¤VnkVÑXmlbnVKkÑÅ@UmaVç@±XUlIxlV@VaX¯lUVVUVJnV@°°n°Vxĸł°¦b²¦lJ@U@aUK@kUm@_m±VIXal@Kl@bV@KK@km@UmUUaK@_UJaXU@Xm_VmUk@WUk@kU@a@m@UaUUU@al@nyXXWWwkly@¯n@@bnV@k@mVIVlUUmlUJUwIbXVaUal@Kb@VKVkXVl@VkUU@ylUVVaVL"],
                encodeOffsets: [[116888, 29526]]
            }
        }, {
            type: "Feature",
            id: "4309",
            properties: {name: "益阳市", cp: [111.731, 28.3832], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÆxXL@lV@ĢVIbXKl@nVV@XVJlbXalXWLVKUVLl@VV@ôÞ@@Wn@lLlK@wnIVJX@VX@lVVULVnkVVnKValUXblKnXl`UbVLÈU@W@IKV@@bUV@L@lXV@VXXblWnLVblb@JnLVUn@llb@x@ÞUV@nU`VÔmlXmbUKUVUV@LVVUnUb@°UX@UVzVxnlVkVnlVnaW@wnIn`@_la@ykÆVULxl@XLlmUUVakU@¥ÆwblUUaôVU@ÅXyVImkUaġ¥ÅUWXKmU@La@UmUUUalan@VUnK@wmmL@VlXLVVl@VI@WX_m@a¯mKUkwW¥UK@_UWWLUVkUWL@WUIkVU@JwkLUUmJVI@WkXm@VmkKUIU@mmm_@VUV@kJċwUU@KUWkkW@IWW@km@klwkWVkkUV¯m@kWLU`mIkmkXm@@`@L@xUKWkU@VL@JUU@mbUKVa¯WVnL@`lXUVkU@xW@UbUWVU@UJ@lnU@mnÈmVa@bULwUb@@VkxmUUUVK@IUmk@akm@wmIkK@bVWXkm@wULUmm@UVW@UbmbkKVnU@WlxVU@UXmWUXmlnbUl¯Lmn"],
                encodeOffsets: [[113378, 28981]]
            }
        }, {
            type: "Feature",
            id: "4301",
            properties: {name: "长沙市", cp: [113.0823, 28.2568], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lVUllXkx@lln@XX@JlXXlV@LVVČxlI@VU@Un`nnV@VJlLUnn@lW@XUJnIVVlKx@IVlUVJ@XXKlVVUXKVX@`VLX¦lxVnL°an@bkmVaV@XL@UKlU@llLXUÞJWkUknaÆxnknK@w@l@xllUXUJVVUbn@blV@bnLnKVaLVbVVUX@W¥XKVLVVklUVyUVÈÅlaUK°wnnÜbnVVLaVV@n@VmnVlIlJna@Valkn@na@amwm@UXwK@aUUVUUaVawWK@kU@UaW@kKUU@kW¯XWan@kmmÅ@@I@U@KmLkaVUKkLWVUk@UVmU@am@kkk¥UVUKmaUb@UbI@aKkkWm@W¯K¯b@VmaULVxUXlVk@UxVJVbUb@xUL@ULWWLĕmxVVL@VbKUwaÅ²WwX@@WUWLU@VbkV@aU@@VUnmJ@VUn@VLUK@UmUIk@UÇmU@@UW@J@LbUmVI@aUmW@@bkXUx@lmLUbm@UbkJ@V@XmlUbkKm@ma@kUaVU@aUK@mImJUIkVUVUakbWwka@UWKkLUamKUXm`Å_UULmaU@@lUV@X"],
                encodeOffsets: [[114582, 28694]]
            }
        }, {
            type: "Feature",
            id: "4302",
            properties: {name: "株洲市", cp: [113.5327, 27.0319], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XUnwĖKXXVK@VK@wVaUaUIVwl@kUVWUwVKnb@U°a°LX@XnllL@bJVa@VanbVLUV@al@@UV¯ÅÇ@Ummkw@¯yVwnUVVVUkmWVnKVUa@WXkVKn@lUVUVVVXIlV°VnI@VlKnV@mwVm@LXKWkU¥wWw@k@mX@KX¯V@VUVa@VnKWkV@VUkm@aWa@wkUWwkmV£VÿXUVL@mVIXaò@nW@aU@@am@aUUUmXmWUk@nUW@_maVmwUkamaUL@awW@akI@UxUm@kmKUklU@bzVm¯xUVU@XVxm`kÈlxXVW@¦kVUn@xxKUwÅKVXUJWnXmVUxWL¦XmmKbmUUwW@UV@k@VLnlbLm`@¦VVkX@`WIUxVnlbWVbXIVlI@l¦Ç@UKmbkW@UbUVUl@n@VmLXb@JWbUnkbVxUJUxWXXlWL@V@V@XXJWxzUVVVVKnXW`@bkIUlnLVJUbUIWVXlWV@XklVbnn@xl"],
                encodeOffsets: [[115774, 28587]]
            }
        }, {
            type: "Feature",
            id: "4308",
            properties: {name: "张家界市", cp: [110.5115, 29.328], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@InWVw°w@@blUKlUlVU@VUUUlW@aöUlUlLÞ@@aVKXwlK@UX@@UlwkVkUm@m@ÅV@akwVaUkUUlUL¯w@UUm@UkKlw±UULVn@l_XyWwÅ@VUUmJUXU@@mmU@kxW@UaUIWbU@@mU@UxnUbmKkWJkUVal@aUkUxlW_@WUIU@bkKWUJVnUbbWblU@nl@XnVmV@nmWV@LXl@XJXVmzkJUXmKULm°Vb@xnVmnUk@VnnlUb@nm¼m@ÛÇVl@Xmnm²mL@xK@LUl@nULÆx@V@VXVWbXXl@nLlm@bVKXWL°bnU@VaVU@mVwJnwVK°zn@VVba@Ċ¼"],
                encodeOffsets: [[113288, 30471]]
            }
        }, {
            type: "Feature",
            id: "4313",
            properties: {name: "娄底市", cp: [111.6431, 27.7185], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lLnJ@xln@bnlV@JLVUVnVlw@U@VaxVK@abnUmÇnV@km@I@VUVVXVaX@@wlVVUkW@_mKXU°UbVLnaV@V@IUKV@XlVL@w@K@_n@lWlnnJV_XK@l°nU@WVU@kV@nbVKVl@nLlLXU@lmkw@nW@UKVa¯IVn@@aVUUKl@nXVKVn²aXblKnLlmVI@KUU@akLUaVaUXm@a@wVUVKnLnWlXln@@U@anUVm@UInm@IUK@UmKVmU_kVUwm@@VmLK@VLaUaVUUUmK¥ULkVWaXwWa@UXImWUaULUUWKk@WnXbWVWnk@UV@bU@@bJ@bV@XkmbUU`VbkaWz@klU@b@VwUL@bV@U`ULVL@VUK@Xm@XWWIUbUxm@@lkkÇwVÛÇW@¯ÅUJ@xIx@@VULmKUnUxmKULUUm@@ULUJkIWJ@b@LJUWkJWnUV@nnÜ_nJxU@VbnUxlkb@l@"],
                encodeOffsets: [[113682, 28699]]
            }
        }, {
            type: "Feature",
            id: "4303",
            properties: {name: "湘潭市", cp: [112.5439, 27.7075], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Æ`n_VWnLVblKXL@VlbXxlaVbUVlUVJnInJ@VL@bUVVb@lnbn@lLVank@W@UlIVan@VanK@kVwlW@aX@Vn@bUJVna@KIX@@VV@nVÈl@VJn@VVLK@UVm@UnIVm@UV@@blUUaV@XKV@XW@XxÆ±bVxLUa@UKWk@wmmUalk@WXUWkXUVJVaUImKVklJ@aX_mWULUUVUyXwWI@W@U@UXKWkXWVwU@±_U»ÝKUaLVbkJkWmXk@UVVmIUVJ@UU@UamLmwUVU@mnJ@VUnmV@b@Vm@kkWmXmKULUV@x@bWnVUbVblK@bVV@LUJknmKkLWa±bUmULmWk@VLUV@bm@U°JUbVLX@@mlxkn@WVKkmK@k"],
                encodeOffsets: [[114683, 28576]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/jiang_su_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "3209",
            properties: {name: "盐城市", cp: [120.2234, 33.5577], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@n@°ĀÞ°@¦ULWKkx@bkLWb@lUlVXXJVbnUKmxXV@bm@@XLÞÜ¦XlVnmzVJ@n@²ÞôkÆÞaȰĉwnǉÜóéVÛnĊīČǉĉ@ō@KÞUlU@kklÇÈÑÑlġXɛ@UġaU@U_W@n@kaUL@VW@kKmkUV@bkbWW@bkzma@JWI@KUKUL@U¦`@XUJU@KmXw¯KXkmy@aUIWJXXmV@K¯UU@@bVL@¤VLXbV@@JVXVK@JVn@bkKmakVVXUVVVlI@`U@nzVVb@¤n@@UlKXLVVI@V@nV@V@ÈUx@óVōkÅWó@mU@bk@Ýwk@WbXxm@@J@zV@kVbVnLWVUXWUXUWLU@Wl°z@VkxU@UVWIxWJkbĬnW@@bUl"],
                encodeOffsets: [[122344, 34504]]
            }
        }, {
            type: "Feature",
            id: "3203",
            properties: {name: "徐州市", cp: [117.5208, 34.3268], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XKVX@WnIVx@K°Lnll@@I°KnVaU°x²mlx@VanU@ak@akmV@@w@Ua@aUwVwUw@w@UK@£kaĉlóIÇVk±@@kUKmVkIkxW@Ua¯UUm@UVI@WVIJV@@Um@UanaU@mI@J@XV@XaVlkXVaUUWLUyVIXmWak@XkJókJUL@KWkk@ULU@WalUIkJmImkVbV@lV°kXUKWKULUmb@VUlVnb@VV@IVKUUmU@ak@@bmV@xklUU@UKmV@nJVbkXKUamLUJ¯UUVmIbVVLl`@LLU`m@kXUVU@VlxUK@xkIWbUKx@VkVVnb¯@@U@xkmbkLÇKb@@XnJ@LmVkl@@XlUVkxakVVb@bVnUbU@@xVUVb@nIĊ`XVVôJ_K@xlU²KlkU@VaVVÈm@kVUVmnamUUaVXIVJ@ç@¥nkVLn@@XVK@VUX@JVUV@UnVJVLUJVLUVlnIbKnU@m°VanI@anVKVLanlKblKÞk@¦@¤@VKnLVKLKVzlWLX@VmV@VbnU°@UalkWXLVUKWkUUW@£Wa"],
                encodeOffsets: [[121005, 35213]]
            }
        }, {
            type: "Feature",
            id: "3206",
            properties: {name: "南通市", cp: [121.1023, 32.1625], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VJ@bnzWl°LxnW@LVVI@W_V¥@VKVL@LXJI@nbly@aXXla@aVUnllLX@@UVKlb@@mXV`V@bĢlkČÇÆȘ¯wnĕVĉVÿUƒUĠŦğlXÑVǵ@±ōLʵĖ¯lÇbÝÞ¯xk@Çkķén¯@ğġƴǫ@kVVlUbL@xULÇóLUl¤@nkVV°VLkxVb@laUXUKWĖklVX@¤UUkb"],
                encodeOffsets: [[123087, 33385]]
            }
        }, {
            type: "Feature",
            id: "3208",
            properties: {name: "淮安市", cp: [118.927, 33.4039], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nźUôÒɴèl¦nĖVkbmX@xVlVL@xUb@bUJVnUxlKVLÈxmzXV@lW@XVb@bÈVxnbVIXa°LaÆVVaXUlK@aXIÆVlXKVUlIXalK@alwXLVK@¥Ý¯¯ÿ@mVk@aX@mīlaXIwXJVUV@lw@U¯ybUaUġUÅaUKVknaġm@kUm@wÆIV±nLÆwÇnUUk@ƅÝU¯JÝI¯¦Ul@b@@VVL@l@LLÅmL@b@UaVaUWmLUKV¹KLWKX¥WI@mXk@UmaUVUU@VmL@WbkIUWUmVóIkbmm@UbVLUxmJkU@bkJWbnXU`WzKUÞÈlVbLmx@kè@Æ"],
                encodeOffsets: [[121062, 33975]]
            }
        }, {
            type: "Feature",
            id: "3205",
            properties: {name: "苏州市", cp: [120.6519, 31.3989], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôèĊVnX°¤²lxƒÈÜ@²x@J@b@X`nIUÆUUV@bl@VVnL@L@xJ@X@blJXnW@@`XbWkV@UbVxXUxkV@LóxVbUVW²VJĸklUǬ@ĢƳĠ°@mƒī°»ÈÇ¥ULUU±a@bU@¯U@KnImUVWUkmXUVU@lIVaUUVWKUbUkWKU¥n£WakJUkULK¯LKkVIn@VaUVUUUkVk@U@amUkJ@UUlwX¥W@@UkVmk@JUakL@kk¯ÝmJUn@nmVXlmbVVkn@UJ@±WUxV¯a¯KōbÅ¼ÇxUxUUlWL"],
                encodeOffsets: [[122794, 31917]]
            }
        }, {
            type: "Feature",
            id: "3213",
            properties: {name: "宿迁市", cp: [118.5535, 33.7775], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XbWnUJVzXKVVUbWklUWbU@@W@IJ@nVmbVbn@@V@UIUJ@XUJ@VVn°VVbX@lwlJnUVL@l²@lÈUôJĊklb@¤VL@@xVxUxVx@bVb@@xU@lnmnXmXLVmV@X@lxVnVJôLLXax@b@@KVL@bn@@m@@alLUUVaU¥nIV±I@mXI@aWWXU@LlUXWW_XWmaUwÇ@aaWUX@@kWUynÇwUKkLVwUmVI@aVa@wUKUk@wWnlaUmĕk¥ɳçóÑŹVmmzkVmm@a@Iók@@LWU@`WbXLWlkImJVn@`nXVbXmL@Vn@l@nUVl°Xx°U@LVĠ@z°@¦UV@Xn@VJmV"],
                encodeOffsets: [[121005, 34560]]
            }
        }, {
            type: "Feature",
            id: "3207",
            properties: {name: "连云港市", cp: [119.1248, 34.552], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@lzXxmÆV@@¦@l`XnlKXXmKnLlab@xmbm@kL@V@Vl@@VUXJXmb@@°Æ@èÈzlW°XĢJlÈ`lInbWV_@m@UUķnôw°ÆmnaVVÛVmĸ»Ģw±Ý@@mUInyUmWkÛ¥ÝK@Wn@@aWUnwVLmUaWIUWVk@kkJUVWLUkÅWJ@bkLWVUbÅUb¯KWbUJWXX`WXkV@KWVXX@bWJ@nJU²mJV¦UbVVkK@b@@nm@@aUK@L@@awWbKóKUIUmkwW@U@UnWKnmWn@bl@bmVUb@kw±n¯wVUb"],
                encodeOffsets: [[121253, 35264]]
            }
        }, {
            type: "Feature",
            id: "3210",
            properties: {name: "扬州市", cp: [119.4653, 32.8162], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VUXblVVVb@xV@kzV@lwVLUbVV@VU@VbUblb@nkĶ°IÞV@ƆVlmVÈÅxmKU²ÅJ@xVn@lĢnmbUlVLÆbĢVVbVaXk@VXKVVWXVWXUmKUaWaU@¥@£XWUUV@@ynam_VWkUVUna@ÆV@mnkWmXkWUW@k@@akkllWUI@UnKl¥I@VVma@a@I@U@a@anK@UmK@ÅVUnJlkI@aVwka@mVIUW@UWL@WÅbmIULkaUWUxkLUKWlXL@VImÅVUmĉLUól¯I±l@ÒUbVbUVVXUJUnVV@lnbl@"],
                encodeOffsets: [[121928, 33244]]
            }
        }, {
            type: "Feature",
            id: "3201",
            properties: {name: "南京市", cp: [118.8062, 31.9208], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@k@ma@kUUVmVIUWVUUaVa@Ñ²k°Jôk@Wmk¯KmX¯aUakKWU@XULXaV@@mUaVUUl@VmkaUXm@WUUna°IlmVmIUW@Uk@@aV@VVX@VI°»nmU@VKVan@m»UaU@U_@WlIUaaVaUala@¯n@kaUkUUWKU@mwkUUmmL@K@LmUUVKVÅImUJVkVVLèVLVU@WLV@nVÜULVUL@bW@XbWbkJUUVUxVXmVk@WUUkVmIV@nbnVWbJUkUULa@Jma@XkK@VVL@L@JLUVU@V¼nXlbm@kbUKmn@lVb@VXXVUV@b@LVbÆxXbl@@lV@UVV@XVK²VlI`UbVbUlVVn@WXn@@VUV@@KmbVLXÒLkKV@nX@VVUV@bnVllbmnbIWVXU@`lLlknVnmlLlbUmVInK°nUU@l@VU@Vn@@alI`VIXaVaVa"],
                encodeOffsets: [[121928, 33244]]
            }
        }, {
            type: "Feature",
            id: "3212",
            properties: {name: "泰州市", cp: [120.0586, 32.5525], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lUU@@y@In@WwXal@Þxl@@anVô@ÆXlŎôU@Vw@ÇUU@@m@UJUUWKkL@Vm@@£aUUmyV@@_kJUUVUUWlUnblL@aUmI@ULUW@IU@WaUK@£UK@aV@°V@LnUWWXIlaVV@£UWlkXĕVLVWb@kUalwUKU¯lU@mk£VôKÈVK@wKVaUkķlUI±ğ¥ÝUŹ¯ôm¦ĸ@XXK@VVXUJ@nlbUx@blJkmIUV@ÆnL@VmL@b@b@V@J@bnbU@UJk¦mL@VVJkXkll@b@@lXXVWlXnml@nÅU@mbUVlVUXn`mb@zU@VVWX@¤¦V@Xb"],
                encodeOffsets: [[122592, 34015]]
            }
        }, {
            type: "Feature",
            id: "3202",
            properties: {name: "无锡市", cp: [120.3442, 31.5527], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nLÒlxUVkLam@kVWUULUxVVVbUV@bVLUnnźÞVĠ¦XVUUaôw@KlUVwWUwVa@lUXWa@_X@WmkI@a@WI@w@KmKUUk@@aVUVVÅmJ_@W@a@I±wÛ@ƑÇkw±¯£mWĉUóçK¯VkUWK@XkV¯UWabmUaUUblln@b@xbXWX`@VxUblL@bn@Vb@`m@XbWnn@l¤n@xnVlUVLÆWkV@VbÞJ_nl@nKVU@aUU@mVk°WVLUV¯bVXbXlVn@VmL@xV@bl@nW@X@VVJ@²VJVU"],
                encodeOffsets: [[123064, 32513]]
            }
        }, {
            type: "Feature",
            id: "3204",
            properties: {name: "常州市", cp: [119.4543, 31.5582], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@LnxUbVVL@xnnWnn@VVXn@yImx°La¥n@VkKVwW@nXVJ@b@UVn@UnUV@Lb@`VLklVÞnÆ@VaXLlÈJmmVUK@aVUUaUUVwVKXVlUn@blKVUkwÑmKUVUI@±UI@U@WmX@k@aU@wnK@UUmWkaWU°aVUUK¯XUl@nVV@bUVmLk@m`ÝIUaU@lÅXUKkVmU@wmk£m@XmWan@@_Uam@@akKVaUw@W_XWa@w@akmm@mL@UJmnUK@@XnJWLkKUb@VxkWLaWVUImVULUK@L@lkLVVVllbm@@°kbVbUbbVbkJ@XV`V@Vbn¼"],
                encodeOffsets: [[122097, 32389]]
            }
        }, {
            type: "Feature",
            id: "3211",
            properties: {name: "镇江市", cp: [119.4763, 31.9702], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VĊKnVÆUnJ@UWKXkVLlKVwXVlbVKnJÆaķn¥°óÇIkWKUbÅ@mUÝlkUK@_a@KVUVm@mVU@@aUIW@mXUxLUlm@¦bK¯nwJzm@UW@UmmXmm@wKUUVamwKm@UbUL@Vmn¯¼JUW@UUU@@bl@@VVXJnnUk¯JmbVVXn@VWlbUnk@VVUVb@nU@WbKWV@XVlLVb°bnW°Lnl@X"],
                encodeOffsets: [[122097, 32997]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/jiang_xi_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "3607",
            properties: {name: "赣州市", cp: [115.2795, 25.8124], childNum: 18},
            geometry: {
                type: "Polygon",
                coordinates: ["@@`l@Èbln@KVLl@V@bÈlnKXkVlVL@lJnb¦VKVVnXW@w°@VUmlnUV`UbVUV@xnKVI°KXKVkVL@al@XaLVlULWVVVL@bx@VXVmb@x@VVV@nn¤lb°b°KXXWbX`lbXxz@x`VIVUnKLxWXLVKVbVLVU@wnW°b@nalXmXVJn@U²mKkVlU@@xlnaVmlKn@JVLlnVl@XXÆèVlUX@xVLXVb°W@wnUWmXk@KLVwUmUkUKUw@wVaVK@k@WnkUKWkwlmXL@KVUlLVKXmWUL@aL@malaVk@aaanX@VVUblbJnXaVwn£K@UWmUk@UaWIV@bJW@KmmU@aUUUkmKkVKlUUnKVUlVaV£Å¥WUUK@UkUUw@m@mIkUUWLK¯Uw°¯@wUKUbKm@kkKUL@UUKV¥U@manw@k@U@Wm@@U@WwkmwWaUU@UUmV¯kw@@kmkKkUW@UK@ÅV@XWWkXa@Ul@Va@KVaUUU@aXwla@UkVWaXk@K@lmkUmV@Vmbk@»XI¥VUkVUVU@anKVUKUalU@wX@@a@K@ÝwL@UnÇlUIkJmn@bVVb@VmnkLV¯U@±lIWm@kaUI@aÇU@K@KUIkbWbJUIUyX¯UbU@méUUmUkWKxWIkJm@V¥U_UJUwmVkUU@@knwm@UmkWJkL@n@VW@@U@knm@kUml@xÅx@@XUJlb@VXJVxn@lbV@lULnV@VlnV@bWV@bXL@lVLVbV@blLn@VlK@xln@bX@laLVbnKUVVbKlXVVkxV@nnVUblV@@z°WWkbIkWL@LUJ@bUI@b`@UmI@mkK¯XWmUV¯@UUVUUam@@VULWUJIm`IUJKUkW@UxnWbnnmlXbmIUVmV@Vnb@VLUKWLnÒVVV@VUL@kJUV@bÈ@V°@XVV@l@xUz"],
                encodeOffsets: [[116753, 26596]]
            }
        }, {
            type: "Feature",
            id: "3608",
            properties: {name: "吉安市", cp: [114.884, 26.9659], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lxnb@V@bV@ln@nlIn@blVXKnk¼@VUKWL@bL@`UXU`@V¦XLĠ@lJ¦@nV@l°nn@mVXna@nbKn@lIV@VanJ@_lKVVnL@LK@Vn@VbUVanKlLnbnJVbnWVnVVanI@Vb@LbVKVanXVbVJVU@aXLllbôlÆ¼XxVLVK@Xn@xnVVVmb@LnVVKVXV@@mnaVXUVnVK@_UaUmwnKV_anKVL»K@¯ÝU@U@kWlUnlknKVnaUkma@UIUwl»Åw@VwV@nn@ÈXlKVmna@kVw@anm@n_WWk@mUkUK@ImkLUnbkm@wV@klUnLV±m@UInWkWmb@¯amX@xUVUKUaULWKXwKmLUVUJ_@wyWwkaW_XaWW¯L¯akam£@mUU@U@wnaWU@Uw@aUKUXUVKUkKWbk@@bUKUlWL¯LUJmLwU@UVaVU_VkmnUV¯@@xXmWUUUL¥makI@UKUkWlLkmÇ@aUk@UKL@kmÇak@_VlkL@`lbnlLVanLnbmVÆln@kJlbknmKUbÝmmwULUK@bkLWKULUUma@Kk@UV@L@llbVzxUxnl@bVLm@IVJXVlLV`@bn²@J@V@Xmbñ@WbUJ@bm@@LUĬU¦lV@xXb@blnUV"],
                encodeOffsets: [[116652, 27608]]
            }
        }, {
            type: "Feature",
            id: "3611",
            properties: {name: "上饶市", cp: [117.8613, 28.7292], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@VI°`nm¤²@bVJUVVXUl@Vmb@xV@XbmVV@lkLmbn`VbnU@VaUnbVllUXVa@w°VW@_VWLnVlbLVbnlKnVK@IUW@_@am@ÑUólK@U@WU@VwU@UI@aUUaX@kwmJV@yX@kan@mkwVmmI@aUU@aUUW@kVkV@@anK»XVWnIVUl`@_W@wlUV@UWKnUbn°InJlUV@VnIbWn@VklL@l@Vn²m@U`kI@bWJnV@°VXnJmXVmx@VVL@bkLmWULUmU@bWXb@llnX@xkxVVnVV@¤nLnVxnJVXX@bn`VIb@blmlLnaV@blWXnlUnbl@KVanUVmm_XK@kWWnaU@UnaWUXaXamUkKmXUWLX¯WakKmnUWwXa@KW_aXWW_@WnIVl@XULnWVknK@ImyUUÆbXKÛ@W@IÆUnVÝlkVK@mUIVwkUVaUm@aVIVyXIaÈwmmk@UnanVUmÅaó»lwW@kkUVmUK@WKLUmWULkamKLk@Wa@wk@UU@U@mbUIWVKUXWmkUmVmU@LkakKw@w@U¯UUn¯l@bmn@xkJWxkL@VkI@mkmJUI@V@b@VVxnbWlkÈkVLbkKmVL@V@²nxWkLUL@xlKVxbXmVnWJ@Þ°@nxUKUw±`UImVmnU@kalm@akwU@UUJmxU@@U@kU@Um@@KnVm@kKmkU@@WUnkLWxkVUwmKmLkUbmKUbV@xUnkJ@n±UxVXUWJ@LUblUnm@W@nknUJUVm@kXllknVbÆKVVb¼V@Ul"],
                encodeOffsets: [[119194, 29751]]
            }
        }, {
            type: "Feature",
            id: "3604",
            properties: {name: "九江市", cp: [115.4224, 29.3774], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@WUkVUkmaVUb@mVUam_nalK@kUnUWaU@@wna@UVkUWVUUI@a±n£m¯_JU@ĉ¦Ul@UVKmmLlm@ğ¹m`Uk¯@@UVK¯@UUK@amkmKkVVUa@UkUKUaL@VVXUJ@n@WUbnVb¯V@LÅlÝIJÅkÝm@UaWUU@UmUXmmwVUUKWUX±mUam@kWzUaVmÇw@aÅLmKXUWKkL@W¯IwVwlkUJ@Um@ÛÈWKUxWkaUU@KkLVl@UKUX±KUb@nVVUbUVmaUlUL@aUL@@nUlWzX`@V@lx²@Vlb@bVÞ@°nl@UxVL@lUbVV@n²xVUVmnUÞbaJ@IV°xnbl@nbÆ@VwnK@VnXlK°xnUlVXV@Vl@L@lk@W_XK@KkWxUL@JnVx@aX@VVUaIXlmL@bVVX@VbnKa²XVWk°a@UnV¤nbmLmW@XbmJUbVLaÞKL@K@U@aVKlbV@nXlJxV@VnVÈÞKôbźĕČmV@Ċ²xÆIV@Þ¦ĸ¼ÞVlVÞnxln°JkLXWVUVUVwnJVI@yn@lXlaXmWI@w»ma@UmK@akKkXmW@_kaWakKWk@@K@IWkUa"],
                encodeOffsets: [[119487, 30319]]
            }
        }, {
            type: "Feature",
            id: "3610",
            properties: {name: "抚州市", cp: [116.4441, 27.4933], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°V°UnÜ@n@lnLlV@bV°LlnLllVzVVXlVV@@L@xX@WlXm@UVL@V@n°kVmVUnKlaXxVbnlU@lVVnaVI@aX@VJ@V@bb@Vb@X@lUL@@VlIVm@wUVanLalVnKnLVxlUXwlKVm@k@Una@mWIXKWUÛVk@a@UVWn@@kl@@WXlW@_Um@UVK@aLnalInWV@@xnI@¥Km@kKmnk@mlI¤laXbVblknV@UKXVlUXa@@Unw@±mU@ak_±a@UJUIVKW_Xa@aWUK@mmUVa@IXa@UWmannlmX¯WKXwVUVw@XUlK@klJXa@kkmm@Uww@¯W¯kw@WmbULaUUU@mVUUWmkUbKmkkK@akU¯¥Ulm@akU@m@KVIVV@KUkUVUkaUWbmIkaVaUU@mWbb@bUlkbb@nK@bKXVWnULkKUV@LWKknlxXVLml@X@lULUb@xVxVLVlVnUxK@LWlXnmV@x¯XaWUUK@wVWUkÅçm`@mn@bUx@lmbUnkLÇWm@mU@Ux@Æxk¼VxVJ@nbVlmbUmLklmkVlX@VV@°Þ"],
                encodeOffsets: [[118508, 28396]]
            }
        }, {
            type: "Feature",
            id: "3609",
            properties: {name: "宜春市", cp: [115.0159, 28.3228], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@VlbnK@b@JLlUnx±ĀXxÆWX@l@V@@blJ@nX@xUbVVUbVV@bVmnmJ@bmbm@klUbLmb@lVb@xUX@bVVVbV¤@LVVbXlVwLXÜÇn@@VIlVkUxx°J@XlKXLVWnLÆK@bÈxUnVbylXn@VbnW²XVLVVUnxWnnV@VVVXVbn@ÞÆlIÞJÆk@K°UUamVa@UUU»@wV@VkkUKUVW£U@UmW@@aXkVUnVlKVVUUkVmU@kWaUanUVVamIX¥W@@aUaUVW@_mW@UnIVVn@VbVm@bVL@anKVUkWKUXVIkx@nabVKb@nVJ_V@VwVUVVXUlUUaV@X@VblabnKlkVaXa¯@m@UKVUn@WXkW@@w@KU@UWkUUUykkmKk¯KU@akUmK@k@mmÛ¯V¯U@L¼UKmLbU`mLxVnVb@`LmUVUUWmb@nU@UWULmU@KnaUUmUwmJ¯IUJWIkVkaWVUIUlWaUIUVkKmbUIÒlVUnn@VlLUJ@bUX¯@aWVUKUXKUbm@UwKWa@a@VkUWn@Uak@mbXWJXbm@mLaWVk@wL@WmanU@knwWmkaWLKWUXaU@¥lUVVVbnw¥nKV»@aUk@a@UJ@kmLma@mbUWnm@ULÇº@LXnmxUm@UbkbW@@akLmWk@UXmJmUkV@VUXVlULmKUxkL@lmXnJ@Xl°Vnb@bU@WbKUX@VmKUX"],
                encodeOffsets: [[116652, 28666]]
            }
        }, {
            type: "Feature",
            id: "3601",
            properties: {name: "南昌市", cp: [116.0046, 28.6633], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@X@m@VIUW@UKVbLlV@VVbUlUnLnl@bVL@V°UL@V°@Vln_Ġºn@knKnLVU@VkĊ¥Vk@U»UaUÅLUalmkklWn@VUVIlm@mXn@VmkVa@KXIVUWVw²@m@U@VK@k@WUa@a@aU@IUW@@bUJmbUU@kkVmUaWwkbmLUVUnlWbUbklmLakbUaW@U@VbkVWVUUUVUx@U`UI@maULamb@lwJWUVXLlUVmL@bUK@aUnUam@UUmJ@VnX@`UXVVb@bX@W¦nJUbUmVVbXb@lVUnVlVUUkLmUUVWl@bX@VnV@X¤VUVLllUU@@x¼VV@V"],
                encodeOffsets: [[118249, 29700]]
            }
        }, {
            type: "Feature",
            id: "3602",
            properties: {name: "景德镇市", cp: [117.334, 29.3225], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VVX@VbmzxUlU@mbmL@V²xVbUVVblbX@VkVykValKVI@bn@n`lVWnX@lL@WKnVIVa@¯nK@alIXJVIVWUwn@nUnK@alI@a@anKm_aW@UWmIUwmmK@£UUmUUlwwW@km@kWaXaV@VnVKnXlK@aUK@UnwWUnmIUW@¯mUXI@alJV_n@m±@U@kkKUlm@XamJ@UVUkmI¯JmamVXL@VUkV@xX@`k_UVmJUXW¼mL@bU@UllX@VV@bVV@bnJUnlx@nmb@lW@zUnIlx@WbVV@bVJV@UxV@@X@VkLVôÒn@@b@`VX@J"],
                encodeOffsets: [[119903, 30409]]
            }
        }, {
            type: "Feature",
            id: "3603",
            properties: {name: "萍乡市", cp: [113.9282, 27.4823], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VWnL@UVWLXaV@@ama¯Uk@WmInW@klKVwnLVKUkVW@UlUnVnIVWl@nXlK@bX@laVan@VnwWm@KÈ¹VK¯m@kmU@¥kIğ@WKU¥@V_VW@_K@aXKVL@Ul»mWLkU@amkJm@kmU@@a@UmakwU@Xl@VXk`UIW¼kWWX@@lxV¦XlW@Ubn@mUkL@UmJ¯UkUWVUaUlm@UXWlnUJ@LmLUnXll@bUVUUmVUn@¦xlnn@VÆÈU°kbVVxllnL@VnVVUl@VanL"],
                encodeOffsets: [[116652, 28666]]
            }
        }, {
            type: "Feature",
            id: "3606",
            properties: {name: "鹰潭市", cp: [117.0813, 28.2349], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@XV@nlL@lUnm@Ln@@VlV@@VV@nwVI@VVlx@bknlbV@nmnUVJ_²VxVLw@m¯@ÝXImnUWaUwkL@wVKlKXmw@±@UKnUlLaKlUlÇXkmaUw@U@a@UUkwUJ@zWJw@WbkVWUL@VmUklUaWakb£kJ@nmlnlL@nL@¦mJ@wU@mXkJmbK@bUL@VVn@`kXW@Xk@@lm@UX@V@blÜUXVWLXJ@nmb@V@l"],
                encodeOffsets: [[119599, 29025]]
            }
        }, {
            type: "Feature",
            id: "3605",
            properties: {name: "新余市", cp: [114.95, 27.8174], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@m@@WULUKWwÅ»ókakkWK@bUVUIUamWUbULa@KUa@mJUbmUXUmUamImakKmLUbVUam@@UL@KKmUUkL@`mIUb@U@V@bVl@b¼UmL¦mxUaUUVk@¦VWbXVLXKlbXnmx@lmVnb@XKxl@XUbnKn@WaXIWnal@Vb@XmlV@U@bXbLVxn@VaLVWVLXUb°@VW@aVIkK@UmVmkUÑVJnalLVUVJXbVkVJXUlblUXJVI°JnI"],
                encodeOffsets: [[118182, 28542]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/ji_lin_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "2224",
            properties: {name: "延边朝鲜族自治州", cp: [129.397, 43.2587], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Wxĵm@ó¤VX@@xÜ¼ƨ²xWxVV@XVbWXllaÞU°Ċ@ô¼LôÝWanV¥Ñnĉ°¥ÅX¥°¯@w°w@»°k£°mÈŹmÈbÆŎ¦K°z@kxl¦UbU¤klVKŤÞȰ@@bV@nVVUlÞ¦lUllVlU°ÑU¯V°wbXxl@V²@nô¼ó°kmVk²ĕw@wVÞÞ@@Ġö»¯@bnb°mÞ¯°V°ÈJmX¥mamUÅUlaU¯@wKkl±n@@wkÝVUUl±¯I¯bal@kLmakb@ġŹé°Þb°ékLmwXaÅb@bVlbVbÒVbUbUUanwakbVUVak¯ULmxV°UxnôŻX@JXklbkbĉabWU@kWUU¯@@klm@@Å@awWXlKkI@WbUaVIUanU@ĕ¯KmUnWUwm@£ċèkUmbUmm@@nkJUalwk@@nmWUan_óaWmnw±KIwl@UmI@an@@mlUÅmV_KUk@U`@_KUmU@U¯mmb¯@kbImV¯LkbKÛ@ÇnɱJóaÝĢkb@xÒÇll@²VÆUVVUÇ°XóxlV¯lV@bV@nx@¤@șŎnxV¼knJnKX°¦UlnVbUbÆVnÞWVX¦llb@l°VJôÒnLVbbX"],
                encodeOffsets: [[131086, 44798]]
            }
        }, {
            type: "Feature",
            id: "2202",
            properties: {name: "吉林市", cp: [126.8372, 43.6047], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôlzaÈV°K@mLWlnVxUVÈ@ÝĬUÈnôLa²VmĀkV@ĠĊnU@bV@b@nl°UVnÞaôJ@bV¦mlkbmVXx¯@VxmnbbÈKV@bÈLwĠyônmnbÜ@nnVx@n²KJ@kal@nxÞULź±Vwkw¯LWWUkŎīVww°yVĕ°wÈVlkÛ»@wW@Uô£@nĶXwWaUamKóÑUI¯@kakkW¥XUmÝÅUVaUamVk¥W¯LmIlmU»mwȚō@£kJUÇk@am¯y¯UVwa@wġx¦K¯X°Ċ¯¦U°ċWULÅa±b¯@UkÅWmVkIUlóċ¹`óIlXWXxmbULÝbƧ@x¯bÈl@x¯zaÝ¤@nmVWb²bmn¯J¯Ò@n"],
                encodeOffsets: [[128701, 44303]]
            }
        }, {
            type: "Feature",
            id: "2208",
            properties: {name: "白城市", cp: [123.0029, 45.2637], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@óǩŁ@WlwUaƑwÛÅÇéĉamKōÇ@IôġVȁÑŹçÝUƧċĉwóóÝ@Ƒ»ğL¯ll²@ƆÅV@¦mÅb@nmlU²VxlUn@VbnWbÇbkÒn@èlnlUÒ°Lx@¼ĉb@ÒUċxÅènLVxÒbÅJ±a@_ÅJÅnVbKlnUÜĊ@UxXVÆnmVJÞ¯VĠwXw°xWLxKV¦ôUwVÝǬóÞÞ¼ÞkVôȘxÞUlVn¦ÞĊa°wb°@bÆwlŤL²`z°@V@@nJVnl@@¥nUmmn@mwnmmUnk@mlwUaLnwn¯°anWakIÇmXwÆamUXUlJXaUUklKUknmÞV@K@VWÞ@VkUwV"],
                encodeOffsets: [[127350, 46553]]
            }
        }, {
            type: "Feature",
            id: "2207",
            properties: {name: "松原市", cp: [124.0906, 44.7198], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@źèȂÒUóĢ@JŎÈLnĊbÈêÜÆƒxVbkx@XǪłôkÞ`Wb@n°abKnVw°`_X`W¦ĊIkmVakwKx°UÞbU@ll@°¦VWaÞbxÞI@mVI@VkÅUWK¥nLa@@È@°Æ@nU@KÞalkUwVékUWwkUVkkJk¯@»ókV¯ÆÇI@bĉô¯@ķw¯nmmÅL¯wVUÞy@UówÇLkmm@@UóxkkĉmL¯wVwkWWXmLõm@kÅ±V_ô»ÛÆ¯@VaVaĠVlmğwķUóÝƽ£ÇJkbǫaƽLW@nxÝ¤kzy¯XɅm@VôÇX¯Ė¯ºÝnUnLVlUÔmV"],
                encodeOffsets: [[126068, 45580]]
            }
        }, {
            type: "Feature",
            id: "2201",
            properties: {name: "长春市", cp: [125.8154, 44.2584], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@U°xÆKnn°mĸx°@Ċó@aÈJ°ÅUôl@¼l°IllUlVXxlVUêVxkllnÈUVll@Vx²IÞ¤VUlVnIôlÞlwô_bVaĶLXÅÞÇ@K¯@wÛaçn¥¯WXyW¯XwUmmÛ@manómğzxÇK@aUÇLamanUw°@WwnUalnk¥U@aóIÝbUm¯Vmk@@aU@amVğĉ@lUnÿ±UbóKmVÇÞī@ÇVUUwmXkKn@L¯ÇUbyókōè@bn@lÝX@x¯ô@ÆUV_maXm@aóJWxnX@VVnĖVnUJ@nōÆÇ¼V¼kxLklÝw@xx@zV`ÅbmxU±xUnnmknğUbUUb@Å°Üó¼U`Æ²@lönKnXWlXUx°xnKĊllôw@Vn@lnÈKôx@VÝzV"],
                encodeOffsets: [[128262, 45940]]
            }
        }, {
            type: "Feature",
            id: "2206",
            properties: {name: "白山市", cp: [127.2217, 42.0941], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ul¦kÒÆ°IlÒU¤ôz¼lJUnÆXVl°@²aÆbVKČXV¯°¥¯ĉ°WL¥Ģw@xbUx°V°znb@ÈlVlI@w@mU@akU°kUôwWÈ¯VUVUÅ±U@kÈkÑw@laÞġUÞ£@ƅKnÑĢ¯@WaUaVUVkkw@a¯@¯ÝVXnW@@WkXmK@xkKUb@bW@Uw¯mmb@WKUbmUbUaWbJĉIVW@Il±LkmUbUm@nkKWa¯n@`UbmaĉL@bÆ@W`L@n¯Xb@kb@xL@VkL±mlUIU¥mL@lÅx@_la@UaV@kmmK£LmKUnÅKVbmXVlèĉUUbmlĢÅ¤Il¯bÇ¦l@ô¼Ģ@x°l¤nal@xb"],
                encodeOffsets: [[129567, 43262]]
            }
        }, {
            type: "Feature",
            id: "2205",
            properties: {name: "通化市", cp: [125.9583, 41.8579], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÆlXnĠxĢ°lÈ°K°kXm@¦VbkŤJnÝ¤kVÞVVkÈb°y@wkÇ°awƨ@aÞKVnaWwXWkôJ_ČºôVk»óyV£kÑJÅ¯lÑk¥Va@wkbmk£¯@wġó»@kÈ¥°akJÆ£ġnkVaĊVkçWUnUaÆLVmnLKU±@m@a¯UbmV¯m@_KUaÅWó¹@UanmWak@@wmI@y@mkJVa@UaIkJ@n@Um±kkxmIkbÇm@°bXnV@°ÈmlÞ¼¯XVº¯LmkWWXLmVVlkn@@lnWÆVxbmnm¯lÝaVÈè@¼VbÆ°ÞUVJkxIxIV¤ÒXxmn"],
                encodeOffsets: [[128273, 43330]]
            }
        }, {
            type: "Feature",
            id: "2203",
            properties: {name: "四平市", cp: [124.541, 43.4894], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ɇn°WzlyÞ£mwX@ƾKǬblaÈIƾ¤ôÞĸVĠxnmmV²wVnwÆaU_@yw@wÞxlkKlwU»È»ŎÅ@mVIUmmĕUU@mWXwIô@bWnnbU`V@Å°ó@wÞW@km@aŎç@m°Ñ°Inm±aXaUn@mƑU¦@Ç¯aU£aUġ¦ÅÒJōUŻókUÇ@¥¯ak¯mUVak@@aċçÅaUm¦Ý`XbÆ@n`IxĊÞōÞml@Ub@Wl_¯JkÇUÝÆÅb@nllUb¯±a@WĉJġĀ¯Unóm¤xôaVnxôI@xV@bmÆ@lnLmÞ¯ÞxVb¯þ"],
                encodeOffsets: [[126293, 45124]]
            }
        }, {
            type: "Feature",
            id: "2204",
            properties: {name: "辽源市", cp: [125.343, 42.7643], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@żôŎVIÆÑĢ¥VbV¤°bÈ@V¥ƒÞ£lÇUUUÝlÞ£mţIlUa@¥nlW¯L¯kÇġ¯ğwWmÅk¯UVUbWlXlmnbUx¯xVVknlUbVÇKUb@VnbmlnzUº±bmJUbWÈnèmÒ@X`WL"],
                encodeOffsets: [[127879, 44168]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/liao_ning_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "2102",
            properties: {name: "大连市", cp: [122.2229, 39.4409], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@IÞmVk@wXWÜbnwlLnU@nLlbXW@awnbl@XLa@Ċ¥@LULnJ@xVnmV@VXXV@VJkn@VÜKXXôJlbxl@IVbnJVLUbnlnVwJVU@XUaUUlwn@°nVKnV°_VJwl@nwlVIXWlIVVnK@IWmkIVaVU@WÈUlmU@UWUalkXġŻ@kI»mmakUmĉUŁV»²ġVĕ@aUU؍IɃ`ȃ@kw@Umwĉ@WķÑIĉÇbÝLkymbIwÇmÛbmbU¯ÜõÈkÆVbŎxnXVÆnǪ¦b¤UxÝnĉÒmĊVÈ¤ÈbÆ¼ĀÆÆÞźbVVbX°²¤"],
                encodeOffsets: [[124786, 41102]]
            }
        }, {
            type: "Feature",
            id: "2113",
            properties: {name: "朝阳市", cp: [120.0696, 41.4899], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@na@UVI@mÑWkaV¥UI@wl@aÈbm@wVak@@K@k@a@UUmUUalmU@KÇUÅ±¯@±kUKVkUaaU@¥m@@¯k@WLUmkn@mmIkm@amU@wVmkU@Klk@UmaXIWWULaULVbmk@UUmUk±_Uym@mbkImaX¯WWxWKzU@WkJWwkV@Um@UbVVVVXb@VWX@W@Vkb@VnUK±aUUlwXÇWKknU@mmUkLUVVUUVUawbkKmwnIkJ@nmb`kmVkLWwUm@UUUK@UmaUa@UUaWK@mU¯Wkk¯VmUUxVXUVmL¯ymXkWUbmXUKVknWx¯JVnkLl@VVxnxlĀVL²WlXl@bÝVUn@bnlÜaXblIVl@@È¦@VmbXV@@xVVnUn@`°@VnXU@K@VV@VmbnVn@ln@bx°Ub@bLV`ÅnW@@lUnnWVU@Vbkl@Xl`XxVUblkX@°¦VUVVbUlkV@UbVbkLUxmJkX@bbxVKÆlXXbnnala@Uk@UVVklKVUXKVU°KVan@VUnLKVLWVaU_@mmUXa@mwXwVkVWXkk@k@klm@wXKl@U@KVUUUVaUV@alLxUx@b°°VnnVxlIXJmxLUVlV@bnX@VbaVx@XJ@bn@VVXÈl@llX@lUVô°°@ÞVbn@Vk@VW"],
                encodeOffsets: [[123919, 43262]]
            }
        }, {
            type: "Feature",
            id: "2106",
            properties: {name: "丹东市", cp: [124.541, 40.4242], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lzXJU@²x@@V@bUVmKUn°n@lnVKnV@n@VlV°WbXn@VzJ@¦@bkbbUl@bkbJ¯zWULWbklVnb¦VJ@K°Ukl@@WbVn°@Vm²UnX`UÜLXmVXlKVbUVVnUbnX@VUL@lUbWx@²kl`n@Vlb@nUVWVLVU@aV@²bl@ÈmxWXVÈUJVl@laWnXKÈkÈ@Va°bÆm@XV°IVV°UnalVUn@UwVU@@VVJI@bl@XK@wWmXUUVbkJVXnJVI@mknwlKXL@`l@VI@UUaVKÞnaVm@aÇ£XWU@aÇUU@mbkKm£@WWL@@Kk@klUbWKUkUU¯UõÛmUUaVUU@WU_W@kVkJ_WKkV@bUL¯¯±mk¯ġğÑ@UmwKUaka@am¥ÝIUWmk@wmţLKʝbȗKWĢklVbX@VVknÇV@XUVUblJXn@J"],
                encodeOffsets: [[126372, 40967]]
            }
        }, {
            type: "Feature",
            id: "2112",
            properties: {name: "铁岭市", cp: [124.2773, 42.7423], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XJm@¯mXUlnVbUJU@bV@UJWL@VXLmJVbkXlJXxVL@b@V@n@b@`Vbk@lxknV@VVV@bUL@bV@@bVK@VXLWLXJ@LV@nbWJ@IUVx@LVJUXVxVx@VV@@LXJWL@VU@@L@VnL@bVVmVX@@VVInJmbnLWVnVULVVU@VVmX@@JVzl@nVVKVXÞ@mk_lmUUWV_nJlUÞÑÞVVUVVLUVJ@IVna@@KV@XwWknwnKlalUwaĉÝwJl_@aUaKUUU@WU@WXUÆ@@UVK@n@UnVVblK@bllb@bbW@Xbl@UlnLl°°b¦nKlVnIV@UWU@WXkw@am@nm@aVw@I@KUaVIm±XÑlknJVnVJaX_VaUaVKmwnkmmn@lU@U@mnaXlKUmUIVmklaUK@UlUVUW@UkVma@UUU@JmUU@@bmbKWV¯XUKm@ka@UVKVk@aUKmLkKUUÝUmbXbÇJ@k@WU_@m@klm@UXKVaUI@KWUXaÇWkaWUkWUL±U@lUU@UJI@V¯JmIm@@aU@Uwa@UV@VkIV¯aUkWkb@bVL@@VVVUXW@Ua@@bÝbUVÝ@LmUkVUbVllLUV@LXWbUXm@U`@kxlnnJlbnIllLXlVlUXmVKnV@L"],
                encodeOffsets: [[126720, 43572]]
            }
        }, {
            type: "Feature",
            id: "2101",
            properties: {name: "沈阳市", cp: [123.1238, 42.1216], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ȚĊÜ°bLlÞxUbUn±@ÈnVÆL@xnLlUVbxkImJkn@V±LUxkV@bbKVKnzVl@L°@VaxÞUlbôxVV@@V±bn@llXLöXĶnal@nkVJVI@aU@@aVK@aUUUU@lmkwl@Ua@_@a@m@U@aUKWwkIlWUanIWK@UXKVIU@@aVVIUamVknW°n@WI@KUmULWnkVkUWKkkmJkamIkmlw@V_n@VWXaW@KVUkKUkValUnVK@ÞVUÞa@a@VbX@VWUU@U@UK@ala@IkKmUUa@U@VkkWVwU_@KÜUXbl@V¥XUVmXakÅlUUkIm`UIUJW@UIKmkm@UUJImmU@VUXU`mIUbUK@LJUUl@X@UbJkU@nm@Uam@@aUmLKwmWXUK@kUaÇa@JUIUa@aKVUUXmUy_@lmbkLUKWLX`n@bVL@JXLWX@Vnb@Vm@UbnVmL@V@x@LUbVV@V@LUVl@mb¯U@xU@UVVV@X@VVblJ@bnVKUnx@llnL±¤b@k`VXÆK@kV@¼kl@bWIUl@VmLnbm@@JXXmb"],
                encodeOffsets: [[125359, 43139]]
            }
        }, {
            type: "Feature",
            id: "2104",
            properties: {name: "抚顺市", cp: [124.585, 41.8579], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XVl°bUlJ@UVU@bVxV@@bn@nJ°I@UJIVV@V@k²VVKlXXVblÈXWbXV@LVJUbWL@Vkn@l@nV`@X@lÈIWanaÞVVVlLnKVL@bUlUL@Vlbn@VL°WXULna@aV@nV@IVV@VbUnl@VXnKVa@UUnyWkXaaVk@aabnm@_WKXmWanU@alaUl@XJVLVxX@wnKnVlw@V_@a¯¥@UkKWUaUUanK@IaU@WUaVw@klUVyUUVUUÇ@Iôba@mnUma@kXa@UWak@Wal@a@WULmU@U`mIUU`mUk@@UUK±nkJbUam@kwm@@a@UU@Ua@@K@VK@kmKU_UKUUaĉWmkkL@`LnmlkLkbmK@k@Ulmb@b@xUVIUlmVXXxm@JUUk@WUk@akx±@¯x¯UmbKUUVmUU¯UmVVnWkÆlWbUnWVU¦k@WaÛV@LV`UxXllU@@VVbnVlL@J"],
                encodeOffsets: [[126754, 42992]]
            }
        }, {
            type: "Feature",
            id: "2114",
            properties: {name: "葫芦岛市", cp: [120.1575, 40.578], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ll°XnV@XLVb@VVbnb@VLVV@VVnXxlKnUl_na@mlImJnxlLaxVbUVVUVUKVlnnV@lmXLÈWkxVV²bVLm@Ula@UX@XW@UWaUUUUVan@V@lUXxlIXV@yXLwXXW°nblJnan@Vz`l²nVVVl@nUaVKbVKnXVaUaVUynXK@kVK@X@m@mLXaLWU¯w@a@UVw¥°ó¯¯y¯UÇ¯»w¯Im¯ÇUUl¯»ţKċÑţķm¯w@mU_ómk¼VnU`±IkbVlnnU¼±Lk`@XWl¦UbmVUxkXVlkbllUVb@bkVmx@XVV@Jb±aULkKWXkWmX¯aUJmIkVm@xU@n"],
                encodeOffsets: [[122097, 41575]]
            }
        }, {
            type: "Feature",
            id: "2109",
            properties: {name: "阜新市", cp: [122.0032, 42.2699], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Xnb°lVlnXVJLlVnl@zÆxnK@bblKVLn@@VaVLVK@L@Vl@XVVInVVKVwlUXwlKLVVb@aV@XlUXbVW@nlWnXKV@@V@XUVVLUVV@@bVVV@@ln@VbVUXVIxVanJ@UIVWL@UV@@¤V@nInwWklnIVxlnzUVÇJ¦VVÜLĸUnW@aV_WĊXXaKnkl@nmLa@alUVw²K@UlmnIlJwaVUkmK@wÅKmU@Ç²VmVaÝwkKaÛ¯șĉķ¥ğ¥@kUWkƏīÝ@@akUK@KWIUm¯nU¯JmwUVmIkJÇLm@UImJUU@aW@U@@nUbJabXVWn@UVmX@V@b@l@L@lUb@xnÇabk@@xVJU¦lbXÒ@nUJ@Vmb"],
                encodeOffsets: [[123919, 43262]]
            }
        }, {
            type: "Feature",
            id: "2107",
            properties: {name: "锦州市", cp: [121.6626, 41.4294], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nJ@nlmVnXKl@@°n@@¦VbVbUlVL²l°@Æ²ÈV@LVknVbVVnnWVU@XmWUabIVa@mV@X@@bVVnIVJ@nÈKlInJVUnx°IV°mVnXJ@LLlV@b@ÞƐĬXllV@Ġ¦ĸ¦naWW@In@manK@UVkXJ@alk@»lU@ÅLUWl_@a²£Kkm@kwVmULm@akIUa@U@WUUVUaÝ@ğwkmĉ£UW@@bÇL@ma@_mKlXUwKLţÓ@UWw@K@UI@mU@UV¥@°UnJ°@@_KUwW@UnaWUmmI@mķwUaÇLóVĵwÝUUW¯¦Ux@Vb@xV°XKWbK@n@nW@UL@lWLmzUVVbUbmWXXWJbn@Vkl@LlVUn@xnV@bln"],
                encodeOffsets: [[123694, 42391]]
            }
        }, {
            type: "Feature",
            id: "2103",
            properties: {name: "鞍山市", cp: [123.0798, 40.6055], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lxĠÞ@bV@@w°Vna@UkV@K@UUUVa@K@w@UnKmUVan@@Uma@UXWWK@IUK@amW_XKVLlKna@kmKVak@VU@VmU@anIÆan@aUVnb@blLV`ÞLlUbnaKn@naVU@¥°IVK@anUUKVaUVak@mJkXUVwkVUUa°U@W@WlkXWlIXUlJlaxIVVXLll@nLV@lLXlKĊz¥maUlkXaVKX°yIla@aVkala@a@¥IUy@WmXa¯kU@U@mmUULkmm@¯VmnLVU@a@U@±w@VWIkymLUUkJWXJkUmxk@xUI¯`mUULm¯m@kxVVbWV@UVIUx@bkVVVxUbVV@V@zJVXUlnk@@lkLlLUU±Jkm@UIUVLUVU@K@UnnV@l@LlaUJ@zn`@nWlIUVUUUV±Ln@nmL@VUVkLVlUxVLVlÅXma@@akLmWUX@JUnVJVkXJ@X@`WXVUVUIlbW@bVUVL@`Un@¦U`@bUV@z@Jm@@XV`LUL¯J@IVKmKÅI@JnWVnLnVxV¤z@bmV@VUV@bUL"],
                encodeOffsets: [[125123, 42447]]
            }
        }, {
            type: "Feature",
            id: "2105",
            properties: {name: "本溪市", cp: [124.1455, 41.1987], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lb@VnlnVVUb@VJ@nnJ@bmXUx@xVbkbkWLUxnl@Ul@xWx@nUV@¼UllknkK@bmbnlLVJX@VIVJn_lJVVXUmnU°VVVUnVVLna°V°w²@lwbl@XVl@VVIn@wWWnUVkJVUw@@anaVk@@lnLlalKnkmK@_lKnlĊXVbVVLV`nL@lUL@@L@VbV@@V@bn@lxn@VbalI²mVL@Vl@nV_VVnJV_@nVKV@X@bkXbl@XblylUUk@Xa@UVIlK@UUWVULlm@UUUnKWU@K@UXmXVa@U°KVUUWUk@aUVKkaWkKUknaWa@U@m@mk@aUJk@@_WKkLmxl@nUJmIUWlIUaVWVXn@xWLk@@aJUI@U@UVVxm@UVkmb¯VUU¯JWU@Ån¯aUbÇ@ÇlLmWXkbk@UIÇVUXWwÇnk@±aU@@bUVUKUXmV@kaUm@k_±l@XwVa@kVK@UWmVaUmVUUakLUWWnÛKVW_m±VnU¯@Uma@Xk@l¯V"],
                encodeOffsets: [[126552, 41839]]
            }
        }, {
            type: "Feature",
            id: "2108",
            properties: {name: "营口市", cp: [122.4316, 40.4297], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ĊĖÆn¤°Ċ¯ŎWô@xXbwnKl@nX@VUVKmL@VU@UxÝ@VlbxU@VUb@bk`IUlVUnV@@UV@@JnXlK@b@nbÆWUkUKVwUklKVU@UnK@mm²KVUVVVUJXk@mm_@yVIbk@K@kmUm@VLV@VUKVUVJn@l²IVVKklK@kl@kmVUWI@y@UUUVawUUUl@akmmVaUKmIUaJk@wkaóIWWÛL@UlmUIU@WW@UnUUm@wmIVK@Kĉ¦@bWKk@max@bWXkamK@mVkKmxÛaWX@xUlÝnJ"],
                encodeOffsets: [[124786, 41102]]
            }
        }, {
            type: "Feature",
            id: "2110",
            properties: {name: "辽阳市", cp: [123.4094, 41.1383], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@`VzWnVUVL@bVbVJ@IÈbVb@lVLXWnxLnKVb@n@Vbn@mV@lIVa@@WkVVI@KVLVanJV_VWUV@nnJVIVn@na@alLlmkVk@»VU@mXwwk@@VmkVwXKllaUa@wVwnW@amI@mUI@VaUUkmm@UkaL@UIĉyLWkkKU@mKk@kWKUUJwkbkIWVkJWXkl@X@X¯VVbUVlUxVWlnI@lUbVUbVLmV@bUL¯J@¦UVmbm@LmbakVÝKU_kK@amaVUbm@ÅbmJ@bVUn@UVl@UbnL"],
                encodeOffsets: [[125562, 42194]]
            }
        }, {
            type: "Feature",
            id: "2111",
            properties: {name: "盘锦市", cp: [121.9482, 41.0449], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Vbĸx@nnJVnXmb@VXVxL@`¯@mI¯V@U¦@VV@nJ@V@LXx@VŤÔKLVxWknL@`b@nÈK@a@VXĊ¤nVK@aVU@UnU@ayU£UwmmKXUm@IÆJnLUL@J°IVKKU_@Wn@@I@yVU@aV_@¥Vm@_UKUV@aXkaVJVUUXW@_@WWIUlUIVm@IVW@IU@@VU@mUVVkJ_l@aVa@UVwka@UÞVwV@@UnKLVU@UmWk@mLxWa@wóUVUIÇÆĉ¦¯¦¯xʟJ"],
                encodeOffsets: [[124392, 41822]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/nei_meng_gu_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "1507",
            properties: {name: "呼伦贝尔市", cp: [120.8057, 50.2185], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@m@Łkklô@£kJ°ýɅķÑó¤ğLĉÅlÇğŁW¯¯ƥóÿlwkţÈéÝƛó°ÞÅxV¤ĉĖWƒ¯lȭţυ̃ɱÿķƅˋğɱřÝţϙȍƧĊţ@¯kWKUKm¹Å@ķJU@ƧÑƧō¥˹Ɔ@L@ÞVLn@VōČWJX¦@JŻbU@ţÞmVU@ȁýóbkWWLÅ¯UWġkmó±UŹôV¼ƽ¼ł̥ĖƽǬʉxĉŻȗKΕ̛ʵƨʟÞ˹»Ƨţ»Ǖō˷Ȍ±ȚʊĠUɾɜɨmÜ֞߼˸ƅȂ¯ǖKˢğÈÒǔnƾŎŐ@Ċbôô̐¼ƒ@ĊôĊÞĀxĖƧL±U°U°ĬƒČ°ÜêɴȂVł°@nxŎèbÈÞȌ΀Ǹl²IlxĊl²ÒmôĖÈlĵºmÈêVþxɛČʉÇĵVmÒÈɆôƐŰǀĊ°ÆǬĮƾbyĊ@ĠƒXǀċm»ôw°Ûk¥Çm¯çkkÇǫţǕéX_ĶWǖīŎaÆĵĸĊ@ȚȘĊLĢĉVÆĉʊÇĕóaU¥ĉ°mkÅ°ġUĠřk°mÑČÿÛƒWĸ£ʠÆxÈÞŎÞ»ʈ²ĊÇČalÒ°Ť±ĸzĊKÈ²m¤Ŏ@Ò°¼nyȂUźīǖƳÈē°@ÝĶ@Èkl¥ÇçkxkJXÇUÅ@£k»óƿīÛ@lÅJl¥óý@¯ƽġÆÅanċ°é¯¹"],
                encodeOffsets: [[128194, 51014]]
            }
        }, {
            type: "Feature",
            id: "1529",
            properties: {name: "阿拉善盟", cp: [102.019, 40.1001], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƏnǟƨʫŹɆÿ°¯ÆV²ˢżÿ@ÝÆŁȰ¯ȀƳĉó@ğky¹@īwl£Ź¯Ŧé@ÇÇxŋĉƩUUŃōLÇĵóÝnóç@ó@ġƱ¥çWUçÆō@éçťKçȭVһƽ̻aW¥ȁ£ʵǊǓƲɳÞǔlżÞmĠóĬȂɲȮ@ÈĢŮźÔnĶŻǠŎȭгŃċóȭţΗÆƑÞƧÅΫóȘǫɱȁġlÛkÇ°ȁÈnõl¯ôÞɛÝkĢóWĊzÇɼʝ@ÇÈķlUČÅÜķnέƒǓKȮŎŎb°ĢǀŌ@ȼôĬmĠğŰōĖƧbЇƧōx@ķó£Ål±ĀƧīXÝġÆêĉK°Ýʇƅ@ΌʉżÅÒϱʈ@˺ƾ֛।࡬ţશóЈèʞU¤Ґ_޸Ƒʠɽ̦ÝɜL׈ɛϜóȂJϚÈ@ǟͪaÞ»Ȯź"],
                encodeOffsets: [[107764, 42750]]
            }
        }, {
            type: "Feature",
            id: "1525",
            properties: {name: "锡林郭勒盟", cp: [115.6421, 44.176], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ʶĬĊIȘƨƨ@ĬÛĢșŤĉĬĀóUÈŚÜènŦƐȤȄłϰUƨťƾÑ܆ğɲƜǔÈèʈƲĊƞƒɆ¯̼V˺Ò˺ȂŤVĢêUÜxĀˌ˘ƨÆ°ѢmÞżU¼ÆlŎ@ĊçŎnÈÒͪŎźĸU°lżwUb°°°V£ÞlĠĉĊLÞɆnźÞn¦ĊaȂīġŃ¯Iĉůl»kÇý¥Ŏ¯én£ġÑÝȭxÇ@Åçķ»óƱŎ¥çWÿmlóa£ÇbyVÅČÇV»ÝU¯KĉýǕċţnġ¯»ÇōUm»ğÑwƏbċÇÅċwˋÈÛÿʉÑ°Łkw@óÇ»ĉw¥VÑŹUmW»ğğǉVÿŤÅźī@ř¯ğnõƐ@ÞÅnŁVǉóJwĊÑkĕÝw¯nk¥ŏaó¦ĉV¦Å`ğÑÑÝ@mwn¯m±@óƒÛKˍƏǓ±UÝa¯lōșkèĬÞn@ŤġŰk°ċx@ĉ`Ƨĕ°@ţÒĉwmĉ@na¥ķnÞĉVóÆókĉķ@ÝkƧƧÛa°Ç@ÝÈUóbÝ¼@ÛÒV°@V¼ˋLÞɅŤŹǠVÞȗŤÇĖÅōbȁƜ"],
                encodeOffsets: [[113817, 44421]]
            }
        }, {
            type: "Feature",
            id: "1506",
            properties: {name: "鄂尔多斯市", cp: [108.9734, 39.2487], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ĶL²ĬVłƑkkl@ȎŘWńÈĬȗ¯ºlz@ĠĊôŦôÒĠ°kÞÜn@¤UĸèĸbŌÈXĸLlÒĢxɲÆ¤ÈÛƾJÈÝ°UÅĶ»²VW¯ĸJôbkV@ôlbnĊyÈzVôab@ĸÞUl°yǬ²Ǭm°k±lbn°@È»JXVŎÑÆJ@kLÆl²Ġ²ʊůĊġřóƛÞÅ@mmLUÿóĉƧ@»L@`ČĸmȗÑţů±ĉğl¯ĀwÇçƧŤÛI@±ÜĉǓçō°UwôǫůķƳÅ±bÅ£ÓÇwnÑó@ȁƽ@ÇƧĢón»ŏĕóĊ¯bÅVȯÅImōKULǓ±ÝxċŋV±Āȗ°Źl±Û@WÒȁŚŹНŚÅèŌô¼°ȰɞȂVĊ"],
                encodeOffsets: [[109542, 39983]]
            }
        }, {
            type: "Feature",
            id: "1504",
            properties: {name: "赤峰市", cp: [118.6743, 43.2642], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɲŁĢǉĊwƾōÞĭ°_ŎŃźȹƒUČÿl»¯ôķVÿǬƽɅġÅÑǫ»̐ʟȣU¯wVWÝÈġW»Þ¹mÝƒɛŎÿŎōͩůV¹ōéċóŹÅVVĢǩʈ@Ėċ@ķÛV°¯xÇÅţ¥»°Ûôĉʟ¥WýČ¥wç»±mnÅķ¥ˋVbUÒġ»ÅxğLƧbWĖÅx¦U°ÝVóŰlô²@¥ÜÞÛôV@²±`¦¯Ý@ÅVÒō¼ô¤V²ŹĬÇĊƑţxç¯Lk»ʟlƽýmłÝÆƏ@mö°Ġ@ŚŹĬţÆUĀĠǊĠX¼nźVUÒ¦ĊxÈ¼@ôlx¯łʊÒÜĀˌÇČxÆČÈƐaxÒĠn¼ŎVÈ¼Ģ°ŤmǖČĊþLV°ÞU¼ċÈUÆzÈa¤ôbknXĀè"],
                encodeOffsets: [[122232, 46328]]
            }
        }, {
            type: "Feature",
            id: "1508",
            properties: {name: "巴彦淖尔市", cp: [107.5562, 41.3196], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@²@Ζǀݴʶհĸƒ¦Ķ̒Uˌ¼ӾÇƾ¼̨UÞĉƧéÝ»ĕĉƐȍōǪakóó¯a@ôţaV¯Þ¯°@²él¥ĵğťwōxó¯k±Vó@aóbUÇyĉzmkaóU@laóķIX°±Uĵ¼Æ¯VÇÞƽIÇÜÅ£ɱġwkÑķKWŋÇķaķçV@£mÛlÝğ¯Ñťóǿƴȯ°Åł@ÞŻĀˡ±ÅU¯°ɅĀźƧʬmǠƐ"],
                encodeOffsets: [[107764, 42750]]
            }
        }, {
            type: "Feature",
            id: "1505",
            properties: {name: "通辽市", cp: [121.4758, 43.9673], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôƲĸ¼Æè@ÈȮwƾ»ʠĢ¥VÆ@²¥@»ŎÑ¯ĊJŤ£k»ÆÇX¯̼ōī°aX£ôƾȁź¥aôŤĢL°ĸ@Ȯ¼ÈÒʈŚôVXůÆaĠƛÈKķĉôÿ@ğÈĉ»ÇVnĉVwXĠÝ°ČÿĸwV¯¯ǵ±ĉǫÅÅm»²Ż±ƽIm¥ţÈķ@¯ƧJV»ÞUÝç¯UġºU£ţóaÅÅlƧī¯K¯ÞÝğL̑ȍƽ@ōŎōĀƑɜnÞÝºX¼ÇĢÞUX°xVʠȤ̏Ǭ¼ÆÒɆĢǫƾUĀóĸ°k¼ċĀƑVŹȺōń¯`ÝĮƽŎĉxġǊɱłō¦"],
                encodeOffsets: [[122097, 46379]]
            }
        }, {
            type: "Feature",
            id: "1509",
            properties: {name: "乌兰察布市", cp: [112.5769, 41.77], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ʠǠÞĸɲȺƒÒȂƛŎaÆÈĕȘţUÝźǟɆţÝˌKU»@U¯ÜÑ@Þ»ôaVÞÇÈ@¯ÜbƨƨÞlĸ@ĊôlôÅĊUÝĸm¦bmĊ@nĊxŤÑ@¯ƨĖĊ_@Čwl¯ȭLÝ»ƽ¯ķůǓ@ÇǓbċÅÅÆwÿĠÇU£óa¥¯aŎğĠţkw°»¯ůlÝĵkÇ»Ý°ɱƧǫaóôɱ»Çk¯ŃóʇŐŻĉǊŻĢ¯ÒÈUl°x°nÒĬónĊğ°ÇŚĉ¦ʵV°°ĬÛżÇJȁńʇʹó˂ƽŎÆţ¦"],
                encodeOffsets: [[112984, 43763]]
            }
        }, {
            type: "Feature",
            id: "1522",
            properties: {name: "兴安盟", cp: [121.3879, 46.1426], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÆXnlŎ°@LVLĠþxĊUȮĊnUĠV@żaW¯XIŎġ¥Ý@K@w@K@I˺ŻŎ¦ƨƨÒŎIÆ@X@VºnX°lŎ@ƾĉˤƒȘǷȘÑÝÝÞbVţĸÿŤxÈĖƐêÇKnĸ¥ô@ķÞUnÒl@UÅaīˋ¯ÑƧx@±kXřƐƏÛéVˋ»lō¯ĉÅÇÓǫÞĖġV@ğ»°ĵÇÞǓ¼¯mÛÅŃĉĠÇƾb²çéż¯VğÞml»ōÑVç»V¯¯ĕÆU¯y°k¯¯V»ôÇÑ°a@ŹkġKţóbŹ¦ƽȂóW¤¯bĬ̻ŎW°ÅÈl¼ţ¤ĉI°ōÒ@¼±¦Å@Uġ¦ʟƽ¼ÞĢÒm¤êō°¦Èþlk¼ĊŰ°JĢńȁĬ°żnÇbVÝ¼@¼óĸţ¤@°Ånl"],
                encodeOffsets: [[122412, 48482]]
            }
        }, {
            type: "Feature",
            id: "1502",
            properties: {name: "包头市", cp: [110.3467, 41.4899], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@źxżĀǔÆǬVȘĀŤ¥ÅƾōôˁʈͳȂŃÈIÜŻ¯ī¯ōm¯ɱĖ¯ķÒÝIÝ»ÅVlÅôÑġğVmÞnnWçkWÜXƝÆwU»Șĕ£ĉÑğ±±ÅkK@lÅIōÒUWIÇ¼¯@mka²l¯ǫnǫ±¯zkÝVķUôl²ô°ŎwŦxĶĠk¦±ê¯@Ý°U°bóŤ@°bôlôǩbŎƏȎĊĖÞ¼êƨÝĊ"],
                encodeOffsets: [[112017, 43465]]
            }
        }, {
            type: "Feature",
            id: "1501",
            properties: {name: "呼和浩特市", cp: [111.4124, 40.4901], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ʶUĊ¥ÈřĠ¯ĉômīÑ¯mwk¯ÇV°ÑżġĊǉǓɱţǓƝóX¯ɛÒóa@nÝÆôƜŚĉĢʉŰĊÒ¤ȗĖV¼ÅxWƞÛlXXèmÝmUnĠĢóÒkÆÆUÞ¼ÞJĸÑ°ɲĕ°Ŏn"],
                encodeOffsets: [[114098, 42312]]
            }
        }, {
            type: "Feature",
            id: "1503",
            properties: {name: "乌海市", cp: [106.886, 39.4739], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ș°ÇīXŃŗ@ȍlkƒlUŁ±īĵKō¼VÇôXĸ¯@ťê°źk¤x@Ĭ"],
                encodeOffsets: [[109317, 40799]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/ning_xia_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "6403",
            properties: {name: "吴忠市", cp: [106.853, 37.3755], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nLV@VLaÞbn@@l@bUVlUVzVx¤kÞVèXn@nm°a@UÑ@VXnV@VaUVKUUU@@U@@KVa@U²@wXkWnk±lLnU@UmmVKnIVWnI@UK@UK@@UVKXkmWLWUXmlkVwUyVa@ww@aVIK@aVÈwKlLVV@LnVVVnUÜ²°WÈIUÆ@nÞ¼@¦@UÞUVW@UxUxVnbKb¯ÞU`VbǬV@XXÆVVl°InmnUô°¯anam£WVXKXmkôaVU@Vak@@wman@K@UÛUWKXUÇ@UIb@alW@akLUKV@@Ukw±InL@kmwkWmk@JUIůVmnnU@m@UKVKlkUwknVUKmbkI±KkmVkKb@U@aVkUmn`kIlaUK@UUKmbUIÝUa@mUa@am@UUULUK@bmKkbWI@WXwlkXWa@k@kKLVkkK@L@JUVmzUKlwUUnW£XVlKUwVU@aXI@aWaUw@W@_nam@¯UkWVkUWaU@nwmJkUVkWVUmUkJ@ImbUa@@WÅ_mJknmak@@mXaUV@xU@@VUnkV@Vn@`ULUbWLXVW@kbUJ@XW`@nÅĖWJ@m°@xxbnUaw²lÞ°xŤIVVULÛWbbkVVXÆ`UbVL@kx°LlV@VWbJn@bl¤ULV°@lmL@£U@@aUwmKULVxUVVx@@kU@mK¯LÇa¯@"],
                encodeOffsets: [[108124, 38605]]
            }
        }, {
            type: "Feature",
            id: "6405",
            properties: {name: "中卫市", cp: [105.4028, 36.9525], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°@Èb°KnL@lV@@UwVUUwVKnLVx@bV@¤@nK@k¯UVKk£@amIXa@UkU¯Klw@UKVaÅ_UWlUaXaÜVKUUţJ¯wÝ±kxVbmaw@wn¯@XIÆĕm@X_@WVIlaX@WUXKVaVK@_Um@lUVm@U@Vw@VUÛwm@@W@ImKUkU@UaaX@wWaUKkw@UVaUamLUnk@»±`¯@kW@UaykbI@VWJkLWUkJwU@n¤mL¯wm@Um²XVWbnV@bmxVkxUblLUV@kVWKU¼kU@mn@JnV@bUnmJUn@k@XlxLVVnKlLVV@@LkKULVbk`WL@lkXW@kV@UÞUlÇXlkaUbmV¯@@L@V@bkb@xlWbbW@±@UJ@IU@mVkVxV@@lIlln@Vm@VUbl@JLmKÛXmVkUKULU`@LĉwKUXlVUl@VbJX¦̼bÞxŎxɜĖĠŎaô@"],
                encodeOffsets: [[108124, 38605]]
            }
        }, {
            type: "Feature",
            id: "6404",
            properties: {name: "固原市", cp: [106.1389, 35.9363], childNum: 6},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@Vnn@°xnK£mV@xlIXVlKXI@UJlazVbX@l°@²_@¼mlVnKVbUb@VlxVLXb@xWbVbV@VlnL@J@Xn@ÜxbW@nl@nblmnIÆ`@X@Vbna@aVUUWVk@kbWakbU@VwW@_l@nmn@@alVlk@UkmVak@@aUXaL@¯@KVa@axWI@KnkVaVJn_lJ@X@m@nVanUVb@mXLlJVWnLlaVVaVX@KXVVkVKlknKVa@aVU@KXb@klJUknUm@K@_UW@alIUamaU¯kJma@IUK@U@@UW@@aXLVVJVaXIKlaUkUV@ambUUJkIWJ@wUIV@JU@UwV@@Um@nU`@UkUmVUxWUUV@aÅb@aWXkKUUUUaWK@wnm@IVU@aXwm@UmVaUalk@anKUwlUwlkK@wmaUkmmIk@VmkUUbW@UVUnW@kV@xkVmbVnU@UbUV@ak@kkW@kLW¤@nV@VU@W_UVUU`VLUV@IUVõVULU@UUUJ@wmkUJ@WI@l@bkKkbVVbVbUL@UUJ@Vm@@L@xbVVVLVlVwX@Vb@bmUkbk@@JWIUVÅw@Km@UkWKXxWLÅ@UVUnWK@xkVW@KULwWVXVWzXVVKVXkVV@VUbV@UVV@@LXxVL@VbLnKVLVxXVmb@l"], ["@@@J@aU@LWK¯UUxVVn@ĠLUW@UbUUUa@KUX"]],
                encodeOffsets: [[[108023, 37052]], [[108541, 36299]]]
            }
        }, {
            type: "Feature",
            id: "6401",
            properties: {name: "银川市", cp: [106.3586, 38.1775], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@UwVK@UVWÞUbwV@knV@@KU_VK@Kn@W_XWlL@Vn@Ċw@Ula@Wanamī@a»ŋó@aÆÅɲÿUaV_°ÝaLaUmVwVwX@VUVÝ@@¥Ý»@mVÅÇJ¯XÛ±VUmUmU@KUUkKLÇxU@bLUJ@bx@xUbVzUxklWnXVKnXWlUL@V@VL@VL@mJUXmJULnn@VmVkK²mlXWlx±@@VUb@L@@VV@VVULVUbU@WmU@Ò@V¯bmn@V@lVnUnVWXVl@¦VVUn@x@XL@¦lXxVb"],
                encodeOffsets: [[108563, 39803]]
            }
        }, {
            type: "Feature",
            id: "6402",
            properties: {name: "石嘴山市", cp: [106.4795, 39.0015], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@U¯ķó±ÇÛ¯ķmbXb@kb@Vĉxm@@UkKWXX`m@@LULV`@L@mU@lUxaÝVUX@VULxVkLWV@JnVLXVlUV@zlVL@V@bn@lU²WVLlLVbUVxUx@xǀLxôÒkK²VaU@wXa@WÈĉUa@bÈkm@¯"],
                encodeOffsets: [[109542, 39938]]
            }
        }],
        UTF8Encoding: !0
    }
}), define("echarts/util/mapData/geoJson/qing_hai_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "6328",
            properties: {name: "海西蒙古族藏族自治州", cp: [94.9768, 37.1118], childNum: 7},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@V£°@laXô±źwô@UlżaÜnKw@Uaa²LmÈLÆÈxlaUawÞmÜbÞUnJ°akôÑkwÝVğwÇ@ÝkkV¯¥@ò»nŤ¥XImw@mVwa@ÅwmLkaWw¥l»kçó»@WÑĉğ@ĉŃUwóřVómĵ»Ý@VǕ¯kÝĊÅk°ÓUklkU±IÇÞk±@ƽJ@UġIk@W¦VÑșÓÅnťKULn¯X@¯mUÛ@WÅmóKknōbxÝ@U@kw@ÿÇLţÝUkmwklċVÅU¦LkUWlÅÑ@a@ÅÑ±UóġŹ¼ÈĉmŻ@@wkwKl¯Uġ@lÇUÓ¯_Waĉ²Åló¼VbknKÇÅ@ƧĢō°Ý@ğWÅxUUm@ÝXÛWULUè¯@mbUaLbUWġxIUJWza¯by@ōÈóLU`ÇXUlUĉV¯nmÛbǕLklUĉVóaġƏbġKţnkbÝmmnÝWȭÈÝXţWókUÇl¯U¯ġUɅĀ@°¯¯VÆnmJ@ĊķnóJUbÝXUlVkL@lVxnnmb@¤Vz`ÞÞŤ@VnÆJV°bUôJkzlkl@²ó@ÆÇ°kĖÇbÛU@lmbXVkzVɅĀXˢlńĬŹ@éÅ@ĉńÆ°ğbUlɜ_°@xŦkbVbƒKĢŤVŎ°@żÈźlĊôKôb@nôxŦÆ@ôŎL@þÆb@nnWˌbÈxInaŎxlU@Ñ²±ğVUĢƨbɲ@Þ¥ôUUķWVô¯ĊWʶnôaŤˁ@£nmnIôǪK°xUXô@Ŧa°mkXÆÞVŎkĊ°ÞLÈôyVaIlwX°UVwĢÑÜKôw@nV@m°nmnÜɞ£VbmXn°ÜÒ@xx@Vb²UlbkxVnJUnVVĊ°KČm°nxÇnn¤±¦@UXVV@lVbmVVÈVxÒ°IbźaČbVw@VLƾÑ@Ŧô¯ĊkôÑ"], ["@@@@nòVaw²bVxxÜaČVô_ĊJIVmLa°@Ŏ¥XlK@klKVbUb@nUĢnaÈ@lmǬ»Ġ¯nmnƨVyÑǖĠ»ɲIn@@ÅĢƳ@¯°ôVKÈbVIÇ¥¯@Ýó@ÑnīWKkk@¥¯ÅaX±VÅw@±Ġ¯@»nWmw@@¯VUUWçKĉa±VkkV¯wx@UJx@bknÇbmÅ@Uw±U¯¦UKm¯I¯ť¼ğĊ@ÇŹÈ¯@Ý»ÇnˡJbÛèÇnÅK¯ġĠŹW¼Ålm@¤n²Ýb@b¯l¯@Å¤W¼nV@x°@Vx@lbUblbX¼WÇ²lU@¼V¦@bÇlVxUbVxÞbVbm¦VV"]],
                encodeOffsets: [[[100452, 39719]], [[91980, 35742]]]
            }
        }, {
            type: "Feature",
            id: "6327",
            properties: {name: "玉树藏族自治州", cp: [93.5925, 33.9368], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɆÿĢV°°VÈklVôŤXÞWȮÇÞXnmÞnlaŤmĢLƐaĢôbĊUVlkǖKÜan°mĊUVVkÈWV_ôKŎÇ@z°abXyVIJĢwVXaKVbna°@VçVKXÜÞWn@VVÆwXĠÞ@Ŏ¯ƨġÆ@ÈLlmUaô»ÆkĊ±Xb°`ÔVkÈĢ@Vk°Llx@xż@ĊnÇź»ôĢ²VÆÒ@@bÆÒXklVKV¥ÆČUklnxlç¥ċç@±m¥wÅJ@VmÈIléÈa°U¥@kÞVK²ÑW°w²ÑK²ñyÆÝVmw»kkWĉJWUVÅwLmÅ@@mwkn¥VÑ»°°@@»¯LlaJônVUÅ¯U@W¯UmÑ¯¯k@WykU@¯wV¥kVwţk»wWÇĉĶçKÞÇaĉbIlU@kwWXU°w±@UKn£WĉKWxkĕVamwXw@Wmnk@aVkbĉLlImmwUÇWxnÝJn@¥ÆkwaXÜĉ¯ÅV¯¤mkx¯kķÜ²VWôŹVU@V£¥@°wn@m@¯@UbUôķmn@ÆÛ@ÇýVaUÇĊV@Çlğ¯xÝŤlVÈÈVx¤VxkK@@x@kVĖġ¥kIWbXŎx@nxÅUW`_@±UaLUxK¯WbkVlbbmLÛÆWIUwWkwÝV@kIéUbUUkV¯Km¯k@UmÝ¯m¯mLÞĉÛUmġ£UxkKm°Lwk@kVmKVUk@¯a¯ĢmóKUUxImlÅnÇbXèVVU°@@xXnm@¼ğ°@²ÆxU²WÆb°@¦llXLmĬ@ÒÞô°@È¦UJÇaLóU¯@°ġƴ@Æ@mɱJğ¼ǕÒUzƧmnmğ°ǫ¼knÇ@bġmmV@VaUaLkl@kLWō¦¯@bKUnJĉIó`ċUÛbwUw±axbñUm@@babÇÅXmƒÝÅôVbÞblUÞVÞU°VUx@UV@l`¼nL@ĊLW¤kXķWġXUVVVķUbVb@°kVVxÈa@Č¦ĊbaźJU@ÈVl@XkôaWĢÞ@laĸUÆb²mÞLĠÞÑôbÒĊaJVbm¦"],
                encodeOffsets: [[93285, 37030]]
            }
        }, {
            type: "Feature",
            id: "6326",
            properties: {name: "果洛藏族自治州", cp: [99.3823, 34.0466], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÞVŤÈK@ĀlxV@Þ@wŎalmôLnXÆÜ@nV°@°WmVKŦLÆmȚÔÒUX¥l@ĢJV@ƾI@wW°Ån¥kÅÝVwôÈç@lÑĊĕaJnaÆLVw°kny°UnkÆVČĊll¦Vƾ@@nUźÈÇIn°XwÞKô¦VWV£@£°ókċ±Iam¯Va»ČĉV¥°@mk¥l@Ċm@aUmwX@wÆxmĢ_`VnÆbKVw@@nUVğVmVVöIll@@çÛm£UÇw°@VU¯»m¯JōĖÅLa@»ĉĢ±`U_k`ÇçókXlK@akÝÞ£WċkÝkxJÝ¯ÅwxķxmIÅx@k±J@ýŋ¤UkmV°ÅÝxkwmġnÝVU¦ŤlmóXk¤UKç@mVkK@klī£m¯VUbW¯¼ċb¯ĵam¼mVXm@k¤ÇXÇbU¯J¯¯È@bVXVÒ¤V¼kxÝV@lVWxÛ¦W¯mKnlkU@nƑUĉÝ@ÇºÛċUĉ¥UÞÅz±òL±Ò¯xX±ÒLÝU@lV¦¯ÇbkêÇJnU@ÆIxn¦@²Čè¦è"],
                encodeOffsets: [[99709, 36130]]
            }
        }, {
            type: "Feature",
            id: "6325",
            properties: {name: "海南藏族自治州", cp: [100.3711, 35.9418], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Vxń@ĊĠĊXÒ°UƾĕÞm°£nb@@LUUWÛº@nlÆǬĠ£ÞV°UXbVȂǵé@kWanm°@xzK°¯ĠVVkwLnm°kÞxÆa¥@wnĉÆ@_l_VwmĸèŤÅČU@Wn@ÑmKUnğK@°¯UÿV£nmLlUUÛé±óókkmnakV@Ç°óÝXWəÞťIţxmmVÛUVȂÓnWyȁĉkV°WnkĊa¥_K°ÿWna@mU¯wlÝIU¤UXó¥ÝLx¯WmJÇÈŹmV@ƽ@Uk¥ĉkċÅUml¯Vmz¯lUxÅKmbIbĉĖkÒ@ÇèóUxÆÞlm¦Æ¯X@x@²ÝlÈJV²klVl¯ÔlĉÆÞ°lUǖÞ@Ķ¼nUôôŚ"],
                encodeOffsets: [[101712, 37632]]
            }
        }, {
            type: "Feature",
            id: "6322",
            properties: {name: "海北藏族自治州", cp: [100.3711, 37.9138], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ōmġxƽUm±LǿþġÔ@kxmWb¯I¯mIUx@bbŹVÇkĵblĉI¯¥Um@Æ¯È@aóUlČ»@w»wXaó°ţçÝkUaV¥ÅbÝw¯lmnKlxUğU¯°Lyw¯@mnXbl@êȁǶUWa¯VÝUğ¤ǫkÅ@mÜ¹XVV@K@ma¯¤ÝnƽĖ¯V@¼ôlèk¼¦xXlbnKÆx@bUx@nnxWJţ¦m¼ñ@°¦lUÞlÈ@ĠxÞUlxÒól¯bmIÝVÛaÝnxVbkbÇwÅÇKn±Kbb@VxLmÛŻbkVó@Źxó²Wkb@¯U¤źĊ@lUX°lÆôUlLXaV°wxUb°xÜôÈKVkÈmlwkÈKwKVUŤĉŎ»»Il¥na°LV»²¯Üy@wĢ°ĸwlwĢw°±_lVk@°bÆ¯z@l_@Ģ±lÅVlUaÞLVnKlnÈ°IllČawÞÑ°xUU@wVkmĠLô»KÞýôaÞ¥ôĀÞmÆmUŎV¥Èl°²°a²¥V@@wamm@Ñn@Æ£żVĠ£@W¯Þl@»@Uk@"],
                encodeOffsets: [[105087, 37992]]
            }
        }, {
            type: "Feature",
            id: "6323",
            properties: {name: "黄南藏族自治州", cp: [101.5686, 35.1178], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôl²ôÜêVVkKmnU¤VĀ¯°@LmĠVnLÈL@alb@al@n°V_XmWUÈamaVIn@naV£óVWU£°axÈ¥@aĊwÈ¹@óağbm@kw@maÆw@In¯mm@UkkWÑÅ@@kċÅçVkÝJÅkVykŹl¥@¯ĢUÜX¥òýmmXÝÅlmU@£WlyXW»Åbl@aI»k@klm@UxUUV¼¯XlaUnķI@x@¯KĉUU`ólČ¯ô@¤ÞJk°xVn@mbX¯ĀL`¦ĉbml¯XUlȂĊXzmȁÔUÜVUnnŤwŦJɚÝXÞW¯ô@ÈlUbmln"],
                encodeOffsets: [[103984, 36344]]
            }
        }, {
            type: "Feature",
            id: "6321",
            properties: {name: "海东地区", cp: [102.3706, 36.2988], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@Òb¤ÆI°ôU¼°UnnWx@b¯L@lUUWbXxWlƨnxVUllXVUnL@lȀý²KVnƾĢwV»@mÞ£nÆÞÑmLKUaVżĕWVk²ÆÝ@Xw°@ô@a°wóUUmIkaVmÞwmkny¹VÿƧnÅm£X»naV±Ýw@ab@am¯ĉVó¦kÝWKUU@WanUb@ôÇºĉxb@Ç¦w¯bV¤UXôU¤bmm@UJnbÇbXVWn`¯Umk@@bka@bÇK"],
                encodeOffsets: [[104108, 37030]]
            }
        }, {
            type: "Feature",
            id: "6301",
            properties: {name: "西宁市", cp: [101.4038, 36.8207], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@kmKVUWkVkUmwƧXkWwXaVV@k°K@aXwmmV¯V»¯óÅJ£amX@ċVţÆķçnUx`k`@ÅmĊx@¦U¦blVÞŤèô¯Wbx¼@xċ¼kVôbÇ@Å°@nV°¦ĊJkĶalÈźUa@aVwnJ°°JanXlw@ĢÓ"],
                encodeOffsets: [[104356, 38042]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/shang_hai_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "310230",
            properties: {name: "崇明县", cp: [121.5637, 31.5383], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@uŏu»GPIV±ÐɃŜ{\\qJmC[W\\t¾ÕjÕpnÃ±Â|ěÔe`² nZzZ~V|B^IpUbU{bs\\a\\OvQKªsMň£RAhQĤlA`GĂA@ĥWĝO"],
                encodeOffsets: [[124908, 32105]]
            }
        }, {
            type: "Feature",
            id: "310119",
            properties: {name: "南汇区", cp: [121.8755, 30.954], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@`yĉNǕDwǏ»ÖLxCdJ`HB@LBTD@CPFXANC@@PGBKNECCBB@EBFHEDDDSNKAUNBDMNqf[HcDCCcF@EFGLEBa@ACoCCDDD@LGHD@DJFBBJED@BGAEGGFKIGDBDLBAD@FHBEF@RFDMLE@SGANFFJBANPH@@E@FJjRIACDMDOEKLFD@DbDAJI@AP@BGHFBCBGDCC@DCA@CECGH@FKCEHFJGBFDIHACEDNJDCVFBDCRKRLDLITB@CjNJI^DBCfNVDHDFKHAFGDIICDWBIF@@CFAjFJNJBBHD@CJ@AEFJ@@DH@BFBCPDBMFEQGDIFCNDHIP@HDABFACBJFHEBSZC@DP@@JDBƤ~"],
                encodeOffsets: [[124854, 31907]]
            }
        }, {
            type: "Feature",
            id: "310120",
            properties: {name: "奉贤区", cp: [121.5747, 30.8475], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@~T~JjZdDbLXDLCB_J@@FHFZJJIAGH@HGR@BENBLID@@LFCDF\\FpDBDb@FAHKFE@dEDDdC\\GreNMACVMLBTMCCFCEGFAA@DAFDLMHA@OD@BMEWDOC@AS@KGAI_DcKwÕísƝåĆctKbMBQ@EGEBEJ@@MBKL@BJB@FIBGKE@ABG@@FMFCPL@AjCD@ZOFCJIDICIlKJHNGJALH@@FPDCTJDGDBNCn"],
                encodeOffsets: [[124274, 31722]]
            }
        }, {
            type: "Feature",
            id: "310115",
            properties: {name: "浦东新区", cp: [121.6928, 31.2561], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@EN@JJLNHjLJNR^GRYVBNZJRBV@PDvbLNDN@LGNER@nCNQNuT_TIVFV\\Z\\XnDrI|[Ʉś²ÏJUHOƣ}CA@IO@@CYDATGFIEDAEBBAGCO@GJMCEDCJRHEFANOCADAEG@@CI@FE@BDIC@AGIAIMiEEB@DE@AJCXJDCJEHGBELGCUCeMAD]CIJiM@DSAKJKCLQDQACUECDMIFCBDJGECHAEIWCK@GLMCCGEACNKCEJG@MMBMC@@CIJUINT@JAJSTEPZZCP"],
                encodeOffsets: [[124383, 31915]]
            }
        }, {
            type: "Feature",
            id: "310116",
            properties: {name: "金山区", cp: [121.2657, 30.8112], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@L@BIHFN@@EE@@EFBDGDAADVDD@@EF@CA@IIsRE@GDAF@BF@CV@|FBCHBLCNHAFCADBMDCFZXHILBVEEQA@MWFARJJ@DCX@@TEFBLHAAERE@AJABRPBNK\\BrJ\\VHGND@CNADKDADQjGAGNC@GJ@FCFFHC@JF@@dLBDSFADHVG\\DTEPDDHJALIJkJDJCDIPE@YDCBiK@DONE@EH@BAF@HLJA@EIA@ALKNA@@FIFAFHR@NALadsæąyQY@A±DŉXUVI^BF@FFF@HBJEDFFGFEBSRkVEXGHFBMFIVW@GAEEFOIAIPKABGWEKFSCQLQBSEIBC\\FdBLRR@JGACFDDEF@AWB@LJJYNABBA@CUEGPaO_AIE@MYMFIGAEFECHSAAKAO\\[JEDB@E@MMA@@AGBKMGDFFCDDFEDFJF@NPBAFLHFH@EDDHBADDC@DDCDHHCDDFDABDAD@FEFOBCJ[D@HEDDNJBDDHABJIBBvGLBJAH"],
                encodeOffsets: [[123901, 31695]]
            }
        }, {
            type: "Feature",
            id: "310118",
            properties: {name: "青浦区", cp: [121.1751, 31.1909], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@RUNKdOFDJCbRFMLAHPLDN@JGL@@APBWYCKN@TU@SHGCEJIDIJKVIZVNM`iNY@CIE@CA@KBOEGEUFCCSADEIEFCDDDIDDHC@CKIeDCG@IG@DHWFEEGCH@@GO@@O]CNpeEQDBFME[JC]DGF@CKOA@QSB@GB@@GW@@ED@AQIJIAAFE@@DO@CFI@KNG@CDACAFEGKGBEGBDCCAIFCCLIECFI@MBCLDHGNAHSF@DMB@EEKBA@@C]DEICFG@ADBHGFKCDAKKHKD@@FHGAANGEEFCHKCECBCKG@ADKCNE\\[A[I@@mGBDQQEO@BCE@AI[AML@JGACLOAFKEMM@EQKC@CUCBCCBCHEA@FF@@FM@GEAJK@GNF@EXPH@FD@M^@HIADJCFDBER@DK@@DE@CAKFOCCBDHIBCNSB@GFC@GQEEOWFICGDUAEJIDBTAHJHEB@DIF@NE@H|HBDBEH@DKBAHEF@HEEUB@FGFGCCCE@AHOB@NH@PRLVNNFBX@RCPbAvMtBfH@DJF@ELBFA@EH@HNED@FFB@HLC@CJ@@DJ@PIRf@HE@CFF@GPHD@DKE@FFBEFFD@DEFCA@DD@IjCRFBAHFDKD@HF@@PM@H@BlbDJDBFEF@DLXB@HCD@@IFCBIFEJD@FDC@FBALLF@PAACJERACAJCBD@EL@JD"],
                encodeOffsets: [[124061, 32028]]
            }
        }, {
            type: "Feature",
            id: "310117",
            properties: {name: "松江区", cp: [121.1984, 31.0268], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@DLDFRN@FNELPBDKHB@INK\\BBJF@ADP@RFCRHA@nJ@B\\[\\MFLDBCH@DLDADFGLEDFFMHBBGH@EC@GLLLCBLDHEAGBCH@DEFJ^C@DB@LAFFA@CNE@GTMBGHKCAD@NEJFDKJDDJEDBCDHAAFLHFHBEBDDCH@LMJ@DEP@@CF@BEJBJIBRC@@FX@@HA@@HTA@RPBDLE@CHD^\\INFAERCfFMo^D@PP@@HG@HDFFXECGH@@JDHfCLJ@DGDCCCJCCEDJFCFTBDDVEHFPFLAB@NBFCFKFC@CHIACNOHWHCAAFIDD@CDAGEI@ACFMF@R@R_@GQED@EGFEQEDE_IAHKAEXCQUOQCUDEN@ZI\\DDmAMHCICDSOC@EG@BKHIGMIBCGOCSF[CUHCGEBCTKA@cE@@IGDEEEDI@@HMDBHiHCRCBCLMB@DMCGH[UqI[AMLOAAQIB@BQFBFGBAKFE@SW@CDI@QIEBNXB@FRUFKAGJYWDENCCADBBEMGKDGAAD{EU@@DAEE@CB@HQFJt@JDBE@@FC@"],
                encodeOffsets: [[123933, 31687]]
            }
        }, {
            type: "Feature",
            id: "310114",
            properties: {name: "嘉定区", cp: [121.2437, 31.3625], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@F@LI@IDKJADKIEJICADGACFECCJ@HKCAFOHAJI@aCBEE@ICAEB[GFGCKL@FGEIFADMLCAEJM@ELQECEIG@BE^QKKLQCA@EHBIGQ[GEHOMGGDHKH@JOECFCjCBEFDNCACMBCILGTABDLEEOEIG@GFIMM@CGKFBFCDE@@GEAGEEACIcGaHMFITIHDN[AKF@FS@OA@BK@IHM@KCGOKBENaQIDECcPMLQVFHFB@BFBKLGD@FAJOVGIACQ@A`LPCB@JEF@RU@ANS@@RCL\\HIFpRBFRBBDKLLDADJDGBFDABHBEDNF@DGBBBADKDAHC@\\JJFBDEH[DEFDH\\LX@XLBLbT@DNJLDCEL@VJABJNDHB@HBHYFBAA@GNFB@@AFB@AFABFLFBHFCL@HJBAFBLC@DN@HN"],
                encodeOffsets: [[124213, 32254]]
            }
        }, {
            type: "Feature",
            id: "310113",
            properties: {name: "宝山区", cp: [121.4346, 31.4051], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@mÖoÖi½[s[YEUJU`SCIEBCCWJY_LIICDWU@@FaBCJIB[ICH[@@CDKEE@MK@@IMCAEBCH@AMFI@SMGEFGB@FK@BHCAIFJNQD@FEBDFMBKGACG@ECWH@@CDDTOEEBGEK@GC@EE@GPHFR\\JHGA@FDBKRLL]RAFH@FJFDKR@FINBFKDCNEBFJEHK@DLEH\\HFADB@JFFDA@bIJGBEPDBGLI@DDEFBDCHDBIJJFCLIBCL@JKJE@ADHDBHJ@HIBBDFHBBAEIJ@BJFAVL¢"],
                encodeOffsets: [[124300, 32302]]
            }
        }, {
            type: "Feature",
            id: "310112",
            properties: {name: "闵行区", cp: [121.4992, 31.0838], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@T@@ELE\\BCMJGJSNEbGdHDJFBJAFIEIFCEWG@@gMENSFCVJFAxR~B@IH@AIiI@GE@FGEAFQPDRiV[\\DFSGMHAXHDOMCJCDETBBNVJJI@DD@ANNNH@FILDDMFBDHNDHKL@XDFGLD@EHGFD@DDB@CDDHCDAEAHG@ABOJ@BIaC@CECLKPFNCDCJBiQEIF@@@OGBMIAEEBMTHF@NKEC@QFEGA@EBCKAACHCLJHEFHHB@AFCAIEACIC@HG@KCCDC[ECEED@KC@KJMAAFQ@GHG@BHIJYIGE@EI@A`KDWCaKcCiY}I}S[CYJM@CFDVPRRVWDFLBBG`JCFRFEFFHC@RF@HQ`Q@E@ENBDJ@HFCB@DCCEJBBGDGXMPBDGJ@DEDELEDMA@DJF@DMZ_jMNYUUJILCJIJDFGH@TSVM@DLXZ"],
                encodeOffsets: [[124165, 32010]]
            }
        }, {
            type: "Feature",
            id: "310110",
            properties: {name: "杨浦区", cp: [121.528, 31.2966], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@V@CXJDKJZ`XIDDFADJvSRMDM@mFQHM@KCMKMuaOCU@BDAJSX@HKJGD@PNJCJWAGT@R"],
                encodeOffsets: [[124402, 32064]]
            }
        }, {
            type: "Feature",
            id: "310107",
            properties: {name: "普陀区", cp: [121.3879, 31.2602], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@F@@FHDL@HFFAPFCSDC@@XGFDH@BDLHNACEFA@ERCIMJEDBAGL@@EHAFENHHJ\\ONQBQCIBC[MKACKI@GGGH@I_G@CW@[DMHCDIBMTDHN@JNHEH@FJFPKFACSBKHDJNABDMDECAFiDEDFDIPG@GLHCNH"],
                encodeOffsets: [[124248, 32045]]
            }
        }, {
            type: "Feature",
            id: "310104",
            properties: {name: "徐汇区", cp: [121.4333, 31.1607], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@RADL\\NCPHFfLJaJ@FWLGMGIK@IFMDOYYFOTSBI@IMSAMSACFIDNDCPWGGBHNET[CU\\QjOCERFBEHF@@HjJBJG@@J"],
                encodeOffsets: [[124327, 31941]]
            }
        }, {
            type: "Feature",
            id: "310105",
            properties: {name: "长宁区", cp: [121.3852, 31.2115], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@HFFB@HF@DCAELENSJADCNG\\CX@@D`H@JHGHHJ@BINBFUGEDO[MCKQB}AwQEBUIEDMTNF@hH@FXEDFJEJIB"],
                encodeOffsets: [[124250, 31987]]
            }
        }, {
            type: "Feature",
            id: "310108",
            properties: {name: "闸北区", cp: [121.4511, 31.2794], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@CSG@BQGODUPWTOBQAAFMECKBGEMFKEOHADDJARMR[PGI@TEJBNG@ADBFND@JL@@NFFCL@D\\@DG\\JJADI"],
                encodeOffsets: [[124385, 32068]]
            }
        }, {
            type: "Feature",
            id: "310109",
            properties: {name: "虹口区", cp: [121.4882, 31.2788], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@bA@E@QHSXBDIMI@OHCLI@GTWBIACQAYIOFGCENBBARSPOXCVHPARH@DT"],
                encodeOffsets: [[124385, 32068]]
            }
        }, {
            type: "Feature",
            id: "310101",
            properties: {name: "黄浦区", cp: [121.4868, 31.219], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@NEHFLAFDHDPEAMZUHQQ]IMKJG@EPERABHBGRUCCNGV"],
                encodeOffsets: [[124379, 31992]]
            }
        }, {
            type: "Feature",
            id: "310103",
            properties: {name: "卢湾区", cp: [121.4758, 31.2074], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VDHQGABAFQFOH@LIiKKHEXI@IbAFZB"],
                encodeOffsets: [[124385, 31974]]
            }
        }, {
            type: "Feature",
            id: "310106",
            properties: {name: "静安区", cp: [121.4484, 31.2286], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@DLLB\\NPGLFHUDMYABEeKEVMAAJ"],
                encodeOffsets: [[124343, 31979]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/shan_dong_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "3706",
            properties: {name: "烟台市", cp: [120.7397, 37.5128], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ŤLLllVń²è°xżĢĠÆlÒŤbV¤ĊXnlĢVĊÒÈ°ĊŰÞèL±@џn»VUźċ²»ÆkôVɆkĊŃ²kŤVVwUUVmUa@KkU@mUmmk@UwUkmW@UVIXa@mw@aKULax@Uk@UbWU@yULmK¯@kXVUwm@@JUUknWKUVLUbU@wWykIa@w@mUI@aUVynIWak@@Wbl@@knmK@wnIl°Kna@V¥ğ@ġUķ»¥@UōJX¯¤k@wmI¯k@mwak@@lX@bUJ@VbknWxkLkxlLVlkLmb@bU@bU@VbU`Vb@nL@mbU@VnUVmnU@mm@kIUWVIUKVkkUJUnmL@VmLUaVWaXamU@U@KUUmVUJUVÇwğnm@mXĉV@l¯xnô"],
                encodeOffsets: [[122446, 38042]]
            }
        }, {
            type: "Feature",
            id: "3713",
            properties: {name: "临沂市", cp: [118.3118, 35.2936], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@bXll@zlV@lXXmkbVVlU@Vn@@Vmb@XKVXWJ@XXl@ÈbVLUl`@XXV@VVUxVbUxVb¦@WnXVJ@bnVUzl@°ÆxUKlU@mUUnUlUVWVUnVV@XX°V@Vll@VkaXVl@Ux@bmbXLlKlb@b@bUJn@@b@n°x°K@an@@UlLVKVbXb@bVVnK°LVa@UVa@XwKVxnLU°@naV@UWUkWULmVwÝKUUla@aó_@mK@aUU@WUkwVm@aVI°W@@IUw@a±¯@¥kUVUm@awkw@K@kVKk@maXalI@alLWXblaVLVUV@LnK@l@waXaLlnUlLmV@n°J@_VmnIVym£UKmI@WnIVm@anUVmÇ_kġIÅWUXÇm@U@Ý¯Å@@naWIVW@IkK@klKn@naWImk@abkKkLWnWkLWmk_@UaVUKmLUw@mn£WwUmUaóV@UkUm@UKULUwmJUX@WW@XÒzVblJXWXk@UVWKX¤UL@xU@@VUaU@@XmVkLmWkXUyÝLmKXnV@n@lx@bWLnVVn`knULmxUlWLXVb@VK@z¯x¯¼WxKUn@bk@lVVVz"],
                encodeOffsets: [[120241, 36119]]
            }
        }, {
            type: "Feature",
            id: "3707",
            properties: {name: "潍坊市", cp: [119.0918, 36.524], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@l@@UK@@L@bX@@VlL@JLUVnX@`ÜXn`V²mJ@bU@@nb@l°xnnĸVÆ°@Ċ£Þ@lWnÑnkʶJmó°w@kk»V@»¥k@V@kw@wVmaÅmaô£ŎXI@mlnKla@mV_UK@kUkw@alWIU»m@WUIl±UUÅUbkJ@a@wUKUaVIÆmXIWaka@m@Ul£XKVw@UIJUkmJVkU@aWKImV@UxmL@bX`WXU@U`ÇkUak@@°UblXkmLUKmL@VULóVk@@Vlbn@Ub@ċaUJUbIUlVLUVVbVKXVlVXU@mb¯@VmKUwLWx@Ub@VUb¯KmLUU@aWaUaULkK@Vm@@b¯L¯w@ma@m@UUU@U¦lJUXVmkb@nmXVWkbIVxUV@VUbWLXVLW`Ux@nk@Vn@x@VkJ@V`mXk@VxV@lVI@VULVUIV`°bVXXxV@VWVnL@xVUb"],
                encodeOffsets: [[121332, 37840]]
            }
        }, {
            type: "Feature",
            id: "3702",
            properties: {name: "青岛市", cp: [120.4651, 36.3373], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@nUJXL@blVUnIVlIVJ@UxWLk¤@V@nlbXbWJÅnUJVbVL@x@blIaÆVVVk²VJ@XnV¼JkX@blxlV@VLU`@nkbLkm@nWJōó¤bnÆbUn@xlxU@l@¦@¼Ul¼ĊUnW@nĠmÈxUVIVnUVV@LV@nVWbXbUVbnK@UnKVmVIllUVLUJVXlJ@nnV@nmVUUm@Vna@K@mUaV_UaV@aV@@aanlKUkKklwlKXwlma@UVI@akW@l@bnxl@°nJxl@°£WŎIUÑn»lamô¹Ŏ¥VaUUkmkġWɱIUUŹ`@kk@ĉƨřV¥_Ç@Ĭ¤ÝL¯m¯£ƽóķwUW±ī¯kōaĉĕkğmó°bW@UKkLUaVmz@V@UxVn"],
                encodeOffsets: [[122389, 36580]]
            }
        }, {
            type: "Feature",
            id: "3717",
            properties: {name: "菏泽市", cp: [115.6201, 35.2057], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@¥IVUÈmÞ»@UlU@Un@VW@UVmkk@aVUUKVÝ@UVknK@UV@VVnIV@wnmwmKXaWaXI@UV@Vy²blkVKkamU@kb@Um@VmUkmKmkXKWwkU@Ul@UnK@UVUUmKXwUVLwKU@@Wl@@wUkV¥@@I@W@_V@VWUw@UUa@aaWa@@_mKUwl¯amzmV@WKnU@kWLķaUKbÝVmV@UWÇbÛ@X°UbW@XmVlk²UJUbmLÇxÅWUzl¯Ll@VkKXUbWJ@bU@¯@kbLmKka@l_WXºVbUz@Jn²V@¤lXnV°Ln`WbXLôVlKVUxXnlXLlU@bVV@XJWLUVnVV@@nl°nnVKÈbVXÆJU°VnXVkV@@xVL@Wlb"],
                encodeOffsets: [[118654, 36726]]
            }
        }, {
            type: "Feature",
            id: "3708",
            properties: {name: "济宁市", cp: [116.8286, 35.3375], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nam_nKlVLXaIl`_@KVVXI@m@w@@k@Knô@n`VbV@@LL@KVVn@VX@VLJl@VUUU@Uam@UkwKWaXamkJmIUVUÈblaUnV@kVKl@@lXL°kVJ@VÈnVJUX@VLXl@xVLnU@VKV@aIUaV@bĊUxKkVJXUlVUVaI@WUI@KlUnwmWk@WXIWUL@Wna@Um@@UVkUUlanWW@kkU@ykWkaWVUlÝbUU@kJUIU@@JmaókLKÇUUkKWLk@WbkUUabmKn¯°¥V@XwV@VanaVaU_@Wlk@WÈ@VUÈVVÛmaklKÈ¯lLVUX@lK@aX@@kV@VmV@VwnJV_UWUwXam@kW@wVUkKVIUUVmU@UV@IVK@aUL@aV@LmUKmx@ômLkUWJ@nXmlUxUL@VknVUU@VL`Ub±LkV@kUKÇbÛ@UWó_mJ@Wk@@X@VLxUKVWxLVnUV@VmL@Vk@VlVXxWLnlLnVlUnn@@VlaV@nlbULkl±aUzU@@VWJXbWbnLnxm@xUmJUUU@@VmLUl@VUÞVLUV@bllUn@VUXm@@VkV@VÝ¼ÇnUVJ@¦nnlnVlL@Þb°KVV"],
                encodeOffsets: [[118834, 36844]]
            }
        }, {
            type: "Feature",
            id: "3714",
            properties: {name: "德州市", cp: [116.6858, 37.2107], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@¤@VmbVXnVVbVJX@ll@zlVInl@@bVxUbĠl@ÈblaIxXVWb@L@nULWVXXWWLnL@`@LUVVL@lVnJU@UUkanVôôb°¼VÞXIÜbČabôWXÞWÈzÆmnLVJ°ÈnlV²lbnW@@UUVmnwmkkKWkla@mVIUKUaaUwmnJU@@amIk@@bVlkX@mmUklUUa@_UaUUV@wwWkXmW@I@WUaÝU@UXaWUU@UUVW@UUUWUn¥nUVa@m@k@alU@wkLWa@UUm@@wnmUwla@anKn_@alK@Ý_@@WUUUmlkaIyU@UwU_Wa¯yU_mWUwkImm@InWWUk@@UVWVkW¯U@VL@b¯b@l±¦@VV@lUbV@kxVnUl¼XV@b@lV@nIWxnb@UULxÅxm¯aUwU@mUÅVÝKULm@bmKUXó@"],
                encodeOffsets: [[118542, 37801]]
            }
        }, {
            type: "Feature",
            id: "3716",
            properties: {name: "滨州市", cp: [117.8174, 37.4963], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Vb@`bVkVlnV@nlWUk@al@nJ@bV@InmVxbVbVLUJ@nkblXlLnlmxnUV@V@mXnlbĸ@nnVxb@lnXV@UJ@nVxxnxVbÆVn¯ƒĕ@@wÈçUÇlķVIb@Çmk@¥k@UkUK@aWakUóJW_UW@wkkWK@U@K@XUUkmUUalKXala@U@kkWlkÈl@kVmVIVmU_awnwVW@wwU@wU£wkJWIyUI±bkVUJ@nmVUklXmx@lnbWkVUkLWxkKUUmUkbJ±LÇxUKmkUmkkWamUaVkJÆ_²KĠ@UW@wU¥nUWwK@aÝUkÅVaVK@akLW¯I@bnbVx¯JWñWbUL@nV@VmbkUUV@IÇak@@bWak@WJUJWL@bXV@@VJlb@zUlUUImnbVmz@°UV@VbV@@V@L@xLmKUnmJVXJ@VkLW@UVUL@b"],
                encodeOffsets: [[120083, 38442]]
            }
        }, {
            type: "Feature",
            id: "3715",
            properties: {name: "聊城市", cp: [115.9167, 36.4032], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ô@VWnLan@VKÞLÆUnVV@xVbn°ÆwwKVV@maXwmJU@@k@aWUk»VUmlw@UVa@kUU@²¥@k°a@aK@UU@mmm@ówÑ±¥¯@@wKmwI¥kU¯UmakJmIUaVkKUkm@VUUaU@UaKUK¯@wUVUIUKVwk¥wbV@xn@lWnXxlL@`XlJX¦l°XxW¦@¦Uln@@@Um@@VXVmx@¯bllUnUJ@VULVn@bxVVL@bVlnVVblVÈnVlIVJLôlJ@xl²"],
                encodeOffsets: [[118542, 37801]]
            }
        }, {
            type: "Feature",
            id: "3705",
            properties: {name: "东营市", cp: [118.7073, 37.5513], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ͬUǪlô@°Uw°ōĠ¯»Ģç»XÇ@wwƑaÇkwVƑ¯@ÅķUmm¯w@ka@mV@@anIU±m_ÛW@_mWVUK@IkK@UW@@a@K@L@Vk@±U@UV@lm@mUU@kLmxV¤@xVx@xUXmxxbV`UnUJnU@lÇkkllX@l@VkbWbkLVbnVVlWV@@L@VXLll@xVXX`ôIlVXb@bVLVll@@¦nlÈ@aUJkĸVÈÇè@x"],
                encodeOffsets: [[121005, 39066]]
            }
        }, {
            type: "Feature",
            id: "3701",
            properties: {name: "济南市", cp: [117.1582, 36.8701], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@²¦Òôxn@nn@V°VlXUUX@Vl@XVmX@JnnlJVxnXV`°zXbV`VxV@zJlbkVnVV@X@`@ÞkL@bm`mL@bkbxnVm@xn@VV@XbKl@xkV@b@l@nUbmVm¦XVVV@VUXVVV@XVWb@VÞVVb@X@JnXlWXx@xUVV@aVKVUX@lK@UIUWnIVmnLK@w@K@UU@a@UVU@¯nyUmanVJVVk@ykaIU@@WU@aXKIVXIl@Xb@al@Èb@JVUlVna@UmU@VKXaòX°IUwma@aU@UU@wVW@Ñw@aI±`kbUkwUmJ@UkmÇUUkmKknUV@mJUkaWka@KmKkULmyXa¯_@WmImmbLmUkVUbUVJbUkkWJkUlIUmkLlK@knaVmkI@mWaLUKUU@@VmLUVLWK@UUUWUkkVmx@Vl¦"],
                encodeOffsets: [[119014, 37041]]
            }
        }, {
            type: "Feature",
            id: "3709",
            properties: {name: "泰安市", cp: [117.0264, 36.0516], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@n¼WnxL@x°@¥Uk@nwlUVlXVV@VXLKVUnK@UV@VVLKXb@nlJUnmb@lkLKlVnJklVXIllVaIVUValUnVKannnJ@X°`WbnzKlVnL@LbXlbVlnI@VUU@UmV@U@U¥@VmV@@_Ua@m°@@kmUUm@UVmn@nX@@aanJVUVLmlIVJn@nkVLVa@KVmVLXVVL@@U°bn@VaV@@K@aVkbWaXUVymU@aUImWX@¥UaVwUaVwUUU@WW@k_VUKÇa@nmxkV@LVJ@XJUbVkUWVUIlLwĉVaU@VbJ@bUUL@mVUK@wWkK@UVWUIÇm@UUI¯lWK@kk@UL@lmUVkbÇaUVVnJlInWbXbLxVln@VbV@VUV@kIUK@UWm@UU@LK@KU@Uam_ó@m@L@l@@x@nWJUU@L`k_JWbUKkmLn`mb"],
                encodeOffsets: [[118834, 36844]]
            }
        }, {
            type: "Feature",
            id: "3710",
            properties: {name: "威海市", cp: [121.9482, 37.1393], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VbUnVVUxĊ¼¼ô@ÞÑ¯WǬLŎUÆW¹UÇō¯ÑÝkţţóġóLł̥Uwm¥kÝmkkKóbÝ@U¦@mb¯LkmJ@xLmn@lk@a@X@lXbmJUzV@bVJ@n@xblJXzxV@VaKVUXLlmVV@In@VxUlW°@nLVK@zXVVal@@VwbVKL@bnx@WbUJ@VnXVlVxl@nnnV@lV@L"],
                encodeOffsets: [[124842, 38312]]
            }
        }, {
            type: "Feature",
            id: "3711",
            properties: {name: "日照市", cp: [119.2786, 35.5023], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@UaVUUKVkJVaVIČb@Vam@ka@Ul@UôVK@UnKVLnKlkWVa@¯l@VbÈlV_V@XWW_@anKVwUmVw@@UnyUVblKVLX@aô¯ó¥mÛĊÿÈ¥Þ¹lUī¯Kĉ¼ʟbÇVUUXmakJUnmV@bUnmJ@XnJVLn¤UzmJUn@`¯ImU@nKVkkmKWbb@xk@mL@KUUVUKkbWaXkK@bkJWbnbl@UL@lL@lxx@bnUVlV@¦²°@bVx@J@¯XUJ@bUnlxVX@VV@bL@nô`@bkbVVÞLxnU"],
                encodeOffsets: [[121883, 36895]]
            }
        }, {
            type: "Feature",
            id: "3703",
            properties: {name: "淄博市", cp: [118.0371, 36.6064], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nlKV@nVn@@kVU@²VVaU@wmKXU@UUWwUW¯aU_JUVVK@UJU@kUw@UlnWU_@lI@U@wUml@@mVwX_KWUXKVa@UVUUwJlaXWUn@mlanUVWkIV¥V@VVVI@a@akakLWKna@aVwk@WUbUlk@k@U¯UWWU@mUUVUXkVmVVV@nkVLVÅw¯k@WVXbaUl@bV@@b@xkVVXVxkJ@nk@@VLUlVbVXUVVUzVLVbUbVVWVkLmkJ@n±@UxUVVkV@bx@ÒUX@xVVV@°JXlK@bULUblÆÞV@bLXxmV¦V@xXVğ@±LÅ`IUlVbnbXllVnnlVLÈwK²IlanVVVlLwXlKVlUXma@knwWlkVnU@mVIUl²aVJzXJlI"],
                encodeOffsets: [[121129, 37891]]
            }
        }, {
            type: "Feature",
            id: "3704",
            properties: {name: "枣庄市", cp: [117.323, 34.8926], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@yUUUkl@@aVmLXw°»°w@yL@UUaWXKVknwVKlm_UmmUXK@aw@k@mUWmUL@@@£@KbÝV@akwaULmbUKLUU@lm@°mL@nUJVxVXU`mIUxU@UnU@@lW@@bkLW@UVkKÇ°kLlbnUÜÇUUVÇ@@Xkl@XV`UbmbUbU@WxU@¯¦m°nLaVblVXal@XKlLVVÈLKôlnbI@V@VJI@lVVÞaVkXU"],
                encodeOffsets: [[120241, 36119]]
            }
        }, {
            type: "Feature",
            id: "3712",
            properties: {name: "莱芜市", cp: [117.6526, 36.2714], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lmnLVlÈVln@VnIVlxVla²_JlUUUVVw²@@mlInlKXUUUVaUaKUVyUUWVUUaVkUK@l@@mlIUwUWlU@w@aU@@LU@Ubm@¯a@V@UKWUUKUn@LUbUKmlm@UIkJnUKUVmIb@b@mWm@Un@VVnnVl@¯@@nVb@`U@Un@¦@V@VUVnV@"],
                encodeOffsets: [[120173, 37334]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/shan_xi_1_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "6108",
            properties: {name: "榆林市", cp: [109.8743, 38.205], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ýVnIW»W@»kUÇLÝU¯¥ÇIUWWÑUWwX¯m@»n@ÜÈķô@a±kČ±wÑmwçċmU»ÆkkVyImĉÿ@Ý¹WnwÇVÅazmmĉ¦ókVmxxU¼VkVm_UlVlk°IVkmJa¦kLmmV@XmKnlUôVXbb@UaÇLğÜÅw£mKnmċwÅ@UkbmaVn@m¯aUJm_k@kWXyl@@kÅamwLUÞmWÅzUKUk±@b@nnKbX¤mzVVxÇn¯@ÒknWVUbkķÈÑWkk@VaU@mUkbÝÅ@Ý¥ÇbkĬXV`kLÇVmalUUanV±nwmkJ@In°KVw¯UnÅ@¥U±bUU±mWbÛKWnUm`UƒVK@bmnmÈÅ¼@VL@xxmŤ°n@VmK²VllKkô@êÜV@VXLlm¦UV°Ș¯²ÿ@¥@ÆĊ²ImĶnnb°bKVĸLlÞ@UȮÜ°IVÞÝÞlx@ķĀWUxèÆ@°XnlĊĖ°mnV²V°ÒÆ¦aÞ@zll@bÞĀl¼nKĊ¼óÈb²±IǪÒ¯ĖV@lxnVlkJlaXwŌĉ@VnlÆĕUÆLèŌŤôxÈlU@xlaUċĕXmIWmnkVVVW_@aÈWUUmk@¯çVm»±W¯n¥VmkXw±ÇVw"],
                encodeOffsets: [[113592, 39645]]
            }
        }, {
            type: "Feature",
            id: "6106",
            properties: {name: "延安市", cp: [109.1052, 36.4252], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@kkÇmImUwVkUU²WmVkm@m`mIĢĕUVa@mXÿVVkyUýĕ@l_UmnWKVkţ¥awğ@@aôWakUma¯¯a±£kxmmxUwÝ@xmUb¯KwóÝ@kmm¹Ub@lklVbmnnVUV@xUknƧJUX@LÇWkwLķƧÅwWJkLkþĉxWzJUnÇk@Ɛk¼ÜÔÈKè@°lÈÆk¦ln@l¼@l¯L°UUVÇ°¹`m¼mXkbUaV@U¯x@¦ÇUUmlmUVmnnmlkw@@¦ÅÇLmx¯Ikl@¦mÆ°VUx¯Lm@JInlmxU²mVbkVbUnÈlKU_WlīÈaÞ¦Æ@ÞlanV@VUbl@XlÇÒĸlVaUXlm@Ñ°ÈmUwUnyW£amL@ma²@lVVLÆynXÝVKnxÆb@lk@WzX@lln`IV°b@nmUnbaVlÆ@ČxmnnL¤ÆxĠÛÈKVb@aWaUókVmnL@WUnnKl¥bnIlU¯JlUkVkn`lUUV»wnwlUôĊ¥nnyÆb"],
                encodeOffsets: [[113074, 37862]]
            }
        }, {
            type: "Feature",
            id: "6107",
            properties: {name: "汉中市", cp: [106.886, 33.0139], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lKnb@nlWb°bkxĸwVb@łnlĊ¥L@XlÈVblÈKbakVwôml²`n@nVKlk²xŎ°¦VUJĊw@çnWçÞVkUóÛ@¥kwUmX¯WÑk@UymIUwlUn¥mUk²a°¯V»@ÝVÈÝċÅÅVl»@l@a°±@_kammÅba@m@Å¼KknõĠ@m¯LÅwLVxmb@¼kV@mw¯wVakKW»X±¼¯Vkxb¼W@nx@x±bóakb@ÝmU@ķÓÛLkVUmk¯¤ÝLUlÝ@Ýzx@x°bmX¯aUJW¯k@bÇWwÛwWx@XWlb@VÈUlwLnl°VlUô¦U°¤VUxVXUxlbkVVlI°ÅVlU°m@kÇU¯xUlLUlVL@b°ĠInĠ°ÈnK@xÞa²naUyXUKVkWô¼Èaz°JXUVÇV_JVz@nb"],
                encodeOffsets: [[109137, 34392]]
            }
        }, {
            type: "Feature",
            id: "6109",
            properties: {name: "安康市", cp: [109.1162, 32.7722], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@bĊaƨèwôô¼b°aXVÞVUÞ@aXm¥kImx¯¯V@anU@UÇéğL@¯¥V£m@ÝÈbKX°wČÿb@xÈblxÈ¯ĊmÆUVnÈ@ƨÜLĢ¥Źn°VnnKaô_ÈwUaXmnW¯klLXÇō¦ÝaÅVmbğUn¥±wÅéVan¥U»°am¥£Ý@wVw¥nUÑUmmVwmķIÅaóVWxkblb@ból@ğÒĉ¤ċX¯XxkÇ@óÆÅx@xķ_kmÝÇ£kblb@`¯²@bk@k¼ÆUČÆÞÇÞU@U¼¯°±bVlnm¦kVVxnJVz@lÒXW°nVlx@¦ôÜVUlÝXèm@è"],
                encodeOffsets: [[110644, 34521]]
            }
        }, {
            type: "Feature",
            id: "6110",
            properties: {name: "商洛市", cp: [109.8083, 33.761], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@²nlôb°aVwnKÞI`°wXôw°VĊ°@ÅÞÆVzÞK@x@aLÅ@b@nLl@lnmnLVwabVVnbU¼V°blbÈ@ĶŦb@nÇ@amIyUI@ĠVmôUVwkwlanJ¯lwó¥@an°J_@nóƒó@£l¥UwmaÑ@Um±V_J£JUW¥¯@_k¯¼mUVUè¯b@wmL»ğVmağI¯¤ċIUWXKĵ¦ķaJUbIlUóVmk@WÅÅÇ@mUÅVnĉÇ°kwÇa@waċĀ¯xWLÇa@ÞnU¤°¦@ĠKÈê@VmV@bU°°nwlJn¦WbÝ@V"],
                encodeOffsets: [[111454, 34628]]
            }
        }, {
            type: "Feature",
            id: "6103",
            properties: {name: "宝鸡市", cp: [107.1826, 34.3433], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@£@°Ib@¯°ynŹaUlU£Umĵĉ@@ylUÞ@@£kWU¯WaU£¯ÇV¥@kb¯wn¥ÇkUÇnU@¯±kULm@m±_kónUxlbaÇLkUaÇkW@Kĉ¦km@ŁUaķxlw¯aXak@mmakL@mÛ@¼m@lXV`nKU°°@²¤UÈ@VxmôxKlVV²aVwXlaVlx@UVnÇnk°VVLlkIJÇk¯V@knÆn@lznmlVkzVVVx@Uxz@x±¼VxxUlkb@¼ČkVXlĠkôV²wLUKlwJ@aIV¥Þn¯Ün@nkl²kÆ@°aVbnI@Ťn"],
                encodeOffsets: [[110408, 35815]]
            }
        }, {
            type: "Feature",
            id: "6105",
            properties: {name: "渭南市", cp: [109.7864, 35.0299], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@ÈôLxU°Þ@mÈnl¤nUôLwX`@ÞÝLUmLôôbVbnºlnÞ@ôx°LanVwÞ@Vxnwnlw²¤b°°bVnlXbó@bĠ@xb¦ŤVXġ£W¥ƽɽó@ýóƝÝ»£XmƅĊkU@ókťaĵÇ@aka¯UV»maUUabUxmKnkm@kmK@xó@¯n¯KÇ¦@ôÅèlxkx°nƾ¯KU¯WķL@VÝIUbyWbX¼Ç°"],
                encodeOffsets: [[111589, 35657]]
            }
        }, {
            type: "Feature",
            id: "6104",
            properties: {name: "咸阳市", cp: [108.4131, 34.8706], childNum: 14},
            geometry: {
                type: "Polygon",
                coordinates: ["@@IXyĊwlýKlXIVaķ»a£¯aVU@awÈōaL²»VUln°WÈ¯W»XazVaÞJ@U»@¯Ýbğwly@£kÑţ±WÑ@kaIUn@¯ómţUbU¯lÇIÝb@¤Ý@kV@zĊ@ĶnVV¤kVbmź¯z@°a¯J@¤@bUxb@`xUÔ±ºVXWUnUJLĢ¯ÈKlblmÈXŎ°U°LlkÞK@Èxl_°ĶUÒkbl"],
                encodeOffsets: [[111229, 36394]]
            }
        }, {
            type: "Feature",
            id: "6101",
            properties: {name: "西安市", cp: [109.1162, 34.2004], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°²@mVVÈÈl¦m°xla@U¦°ÈV¤XbV°lXÞaÈJ°kVaŤVôn°@mVJlb@XÒŤ²lÒ@¤kzĠxÞa@°¼ĸK°XV°Lƽ¯mlwkwÆç@óÈ¥°L°mô@w@aÆK@b@wÝLyÅUÝÆ@ĉ¯¯UóxW¯x_ÝJmLUx¯bóak±mÝUUW¯ba»óóxƧçĉbaĉxIUV¯¥ō±wl"],
                encodeOffsets: [[110206, 34532]]
            }
        }, {
            type: "Feature",
            id: "6102",
            properties: {name: "铜川市", cp: [109.0393, 35.1947], childNum: 2},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÆxĸƨKlxÈXK@VWƨIlmV@wVUmUnmUalk@kVaUaóaónKVÞK@ÝW_xóKmVk£ÇmnÝ@¯VwóK@Ç¯XkmVU±¼KbÇŎx@bUV°b¤b¼ĸUb"],
                encodeOffsets: [[111477, 36192]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/shan_xi_2_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "1409",
            properties: {name: "忻州市", cp: [112.4561, 38.8971], childNum: 14},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Vx@lnbn¦WlnnUm°²VVVVVnUnºlz@l@J@kXWVXl@La@KULlbnKlLnKLnKÆXn°bVV@bUVl°Un@LnaVJUbW@UX²l@ČwlVVIWnkÆa°anVKn°UW¯@aVUVk@Un@aV@ValwUanmWUk@WVUUanaVwnLVl°@nk@mVU@UVK@wLVKVU@K@UUKVUV@@bnLaVaôlIXmlKX_°KVV@bVV@zV`kblIVUlL@bnV@VĊllVlIXW@kaU²blKVnIlJalbXXlWVn°JnnL@l@XlJlaX@XW²@l_VmnKUblU@mnkVK¯@U@ma@kX¥VmakkLa@a@WIUUVXWWnk@a°a@kkm@kUUmJm@WUUUIk`m@VkaWWkXKmXk¯@WKLkak@±bw@aa@aka@ma¯@LKÇÅkKWbkmġ±ÅULUKVVkm¯LUVVbUwUW¯bmULxWJ@klmkUm@@KnwVkVK@akw@@a¯bKknVUIb¯mmbk@UbmKUL@xUU@klmLUlVXIVVVUVUU`mLXVWbXnW`Å²°xmxU@mĉwU@mbU@UmbkVW¦kJ@X@`¯Im@UlUVVnb@bWJXnmbJUUUUa@UamIkax@@x@b"],
                encodeOffsets: [[113614, 39657]]
            }
        }, {
            type: "Feature",
            id: "1411",
            properties: {name: "吕梁市", cp: [111.3574, 37.7325], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@a@w@wlbnJVb@VbVVVInaWmXI@aaUmVUVkn@°J@_W@lIX¥lUnaVV@naV@xĊnV@wn¯wÆ±X_WmXaWUnKV_VVUUUUWJkUVnKlk¯@@kmKUaÅ±KkU@WmI@WUIlUUmVwXw@UlUVwV@LnbW@anU@UaVkô@l»n@naJnUÈLVaÆUUVmVKV²L@mU_lK@UVWkUa@a@U¯aUaÑóÑUbKk@@ak¯mVaUwVÑkWUmK@UUKmXUWÝwUaLUU@aWJUUU@UaÝU@WL@VKVaVI@WnU@alIVK@kImIkJ@m@@@_K@x@kaW@U@Vmn@UK@mIJUXV¤XXWlkKkkK@XmJVakImJU@ó¯LWKUV@nUVLkxmKkLma@kXKmmLabLmK@V@mXVÆUxX@`nLaV@@VmLUVnLlLb@°²nx@bVUxlb@V¯bUV@zVXVĊXVx@lVn@VnnmU@LlJXVz¯VWVXbV@bmnVUVkÇþÅ@XVxmbUlVUlnW@Xl@VLXÒ@bÞJ°¦Lò@nUb@°X@XbmVUVnb@xx"],
                encodeOffsets: [[113614, 39657]]
            }
        }, {
            type: "Feature",
            id: "1410",
            properties: {name: "临汾市", cp: [111.4783, 36.1615], childNum: 17},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nW@@UnLKabKnnWL@lnblKnLlwKVU@mVUXL°KôV@nIlJUbnI@WlLllLXkWWU£VWInJ@VL@nm@UVX@lb@@wL@`@n@V@lw@nVmVXWmwnUla@_lKwVlUn°xVKVXXWlUVVI@K@Kn°KwlVlU@kna@V_WnmUVm@kXml_@mLlKXw°m@_ôJVUV@Xl@UaV@Va°Ilk»VwUkVmwUmmVn@V¯@KUwmK@U¯wUVÝ@mJUnWK@@UnKVa_lykUmKÛnm@x@UUlwVkXW@a@U@@K@kIVnammVakUl@wX@@k¯@VVbml@°UbULmlVbnbÅK±VKVXUJWa@ULWaUU@@U@aWK@UkxUKLUUUJ±UkL@V±kk@kam@UV@l@LWl@n@VVUxLlUUx@VUVU@aIUlL@°mLUbkUUaWUUaUU@aWKLWJ@bUL@VUVVbU@m@a@kmKmnĉlUKXWUblbxmIkU@xWb@lkVxLXmzVV@bklVVUzm@bk@Vx@xlU@lUbVnl@Wxnl@n@UbVmLmb@`X@lUX@@xlnkLWaUJnnWVVn@l@bULVV@lV@XnJVX"],
                encodeOffsets: [[113063, 37784]]
            }
        }, {
            type: "Feature",
            id: "1407",
            properties: {name: "晋中市", cp: [112.7747, 37.37], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@lInJlJ@ULkJ@bmV@XUJUbL@UXKV@ÞVbV@VVXI@bVVKVbÞxVXnWVL@VnLVlXÒUVxUb°nl@bl@LVaôÒÒVb°b@VnLnnV@lmn@lbUV@JUVVXkl@lUzmJ@xXklbUnJVUbnUlbV@nlLX@lakV`Ub°@XVJnUL²KlxnI@KV@lbUbVVKnVl@zlm@U@nI@WUaVl@@mVU@XkW@nkVKV_Vwy@knwVa@XalU@Vnml@X@VLKVaÞbnnlJImVKnVVVInVlU@m@mXK@UmyUI@mWUUakamw@wUwmLkakwVmKw@wUam£y@am_W@UU@knmmamU@WUa@knw@UUUUV@nJm@mVUkKVUUUkKmwKULKUImV@lUnnm@mbUK@°bUnmbUmkkWUb@am@UXkK@a±@V@ĉÅVUXVxUVkLWl¯@@bULUlm@@nm`XlWakIkmVUbUL@Vm@kI@@Km@VaXI@W@aU@kUVU_KbJkkÇb@nkKmLwÅW@kVUUVU@WUIJmIXmma@_kyVaUUlkUm@kUx¯Lm@L@LUJUkVWXUWUL¯wVmUkxkL@`bkmVnxXUWUnm@kxU@"],
                encodeOffsets: [[114087, 37682]]
            }
        }, {
            type: "Feature",
            id: "1408",
            properties: {name: "运城市", cp: [111.1487, 35.2002], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VlnJwkaVaXWVLĊknmnLl@@bnV@UaVU@UVK@aXIKXL@bVVVbXVVblVaVnK@¯KVkJ@bVVU@UVwkVKVwUUm@@Xk@K@kVUn@lbl@²l@UlK²VVIVVKVLlw@VXL@b@VV@VXbVK@XbVIUWLU²ÆLmaUankVKVa¯@nkUaU°@n@@kWaUVaXUW@IXKVw@UWU@W@@UUU@mn@`m@UUULkUmJIU@@UK@U@anak_@wmKUwmakVkmKVk¯bw`kwUIÇx¯»ÇaÅmn@@mmUkV@wkKW@kxmLUkĉLÝkxÝw¯lóVUmV@ĀVVX¦W¤kz@`Vx°²ĸ@Ul@xêĸǊ°¤VVlXLWnXxmV@nUl@"],
                encodeOffsets: [[113232, 36597]]
            }
        }, {
            type: "Feature",
            id: "1402",
            properties: {name: "大同市", cp: [113.7854, 39.8035], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@²£yl@ČĖ@bĸĢbĸXaKŤnn@ŎôllÈxnVnÞÇV@bnXllL°KbVb@J@b@UxlKXLlKlXk@UlkJlkUVKXUÇVIVm@_nÇLalwVnU@UUwma@aaÝaLmUk@@W@U@@XwVWÝUUUk@@VmLKV»nwUwaUL@`mzJUIVUaUwKUaVIlJôanÑlLVUn@a@VV@@UUwVK°Vn_lJÆLéW@UUUÅ@»lm@aÞIVwXWUUkkm@U@aU@mwU£VWU_kWmXwW_°yUkkK@UÇK@kkUVymóKU@KWIbUak@mJ@bkbmLkUmkVUW¦@lnb@@V°ULml@nkVaVmLUnk`±@XWW@kbÇ¦X¯WxI@xmbmxXlWV@bÅUz@Jb@bÞbU@Wbk@xk@WX¯VÛWÝbÝUkVUU@alI@a@akLWam@U¯UUmÇL@K@aU@¯VUkKmX@`@kJ@nVUb@lbVÆXVWULU`VbkLUV@XWl@bXJ@VbV@Vl"],
                encodeOffsets: [[115335, 41209]]
            }
        }, {
            type: "Feature",
            id: "1404",
            properties: {name: "长治市", cp: [112.8625, 36.4746], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@UkLky@IJVa@mÞaWy@_W@_WXVlUVw@nw°K@mUVamVkU@mmmnLVUmKXaU@IlKVUnK@UmWkX@WV_V@akU@aKWIXyIUVmUnUa@WaXUVKVmkUWVkULU@@VbKbIUm@mbVLxWUUkn±V¯wbÅJUbmLkbmKÅKbVnUbVKUbKUbmLKmbaKkUm@UnnVnxUVlUxl¼k¯JUbU@Vbk@WU@UVóI@`¯nWxkLK@nk`Wn@lUnVnmXU`@mb@lkV@VnklVVUblz@`nbWnnJIVJ@XUVVUV@lÆXxnKlL@maÈllIaLV`UlVV@@b@XJWUb@n@L@lJn@@UVKVaUlnlJXbkWn_@mn@VkVK@a°@XklKVUUwVWUĊÆ@U²@@blLVWn@@bVaXllVnnaVma@¯VLnan@mVm@knUVJ"],
                encodeOffsets: [[116269, 37637]]
            }
        }, {
            type: "Feature",
            id: "1406",
            properties: {name: "朔州市", cp: [113.0713, 39.6991], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XXWVXVWnnlnn@èÆ¼@xlVnblVÈUVl@blnLÜĊmUkU@Ua@WI@aXk@WVUlKUaV_VKXWUUÅka@VaU@mlI@@_nWLVl°UV@@b@LÈKVn°V@VnXblK@b@bkJ@bVVlUÞVÞaXÜ°UXWl@wl@XaV@Ýa@aa@IVyÆ@aXUWknwna@wJXw°WÈ¥kI@W@kmKm¯IUmkXWWkabkImJUkL±aVb@lWXkJUkĉk@UmU@aKkVUkJlaU_y@UU@aUU¯LW`kLWnkJóbUbmK@aU@UVVL@VL@UVULK@xUL@VUV@nml¯@UkmKUxmbVbUV@XlXVmnVbkxUbU@bm@@VUlUVb°@VX¯m"],
                encodeOffsets: [[114615, 40562]]
            }
        }, {
            type: "Feature",
            id: "1405",
            properties: {name: "晋城市", cp: [112.7856, 35.6342], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lVLbanLnKVaLVaLUVaUmaÆLnLlanKVaÆIa°x²UlmVVXwUKna@VnJaLa@UV@@alUkKVKnkmmVwUkw@@kxWUXW@@mk@aUa@a¯aLkKmwkUm@kL@K@aWIXmVXWkUVakL@UVKw@aUK@UUKmLU@¯nKUwVUIWJUWmka@UXJk@UkmW@kLWKVx@bmI@VUaVU@a¯@UUmVKmX@±`kÝKVxUL±akL@VbLkKmV@XWVUbVXb@lm@@lW@@xklVUbnnmbUlJ@@L@@Vb@WXUlkxVV@wn@ÜmnLlVkz`UbmL@V@XLmVnIÞ@VU°x@VnLxV@LU°"],
                encodeOffsets: [[115223, 36895]]
            }
        }, {
            type: "Feature",
            id: "1401",
            properties: {name: "太原市", cp: [112.3352, 37.9413], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@VV@wVKnLVal@na°naVJUlmL°a@b@lx@bULUlmx@Ln@lVknl@XIwKVn°aVXVxUaVU°KnUlUVLKÆV²ĢlnXalLÈÆLKUaVkUanmWUa@WwkUWU¯y¯Ñ@anIl@@aVUmIymULUUVakaU@@LmJkw±LKmVUI@W¯VaU_lkbW@kK@mUkaVmVaUIVmalkW@wnIVy@klkWUUVI@UVkam@knU@mmmK@bblVUX@VkLV`@n±KUULUnVVÅUbÇKmVImbm@k¼ó@Ulb@VmV@bXmaK@UUxkVV@xWUxVnkVVJ@XnJ@XlV²LÆVbnL@l@°"],
                encodeOffsets: [[114503, 39134]]
            }
        }, {
            type: "Feature",
            id: "1403",
            properties: {name: "阳泉市", cp: [113.4778, 38.0951], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°@nb@lb@bbb@x²al@lbKXU@mkUWkkmUUVwV@XUW@naVklKXblKnLnLVanImaXKlLaV@U@KUKWalXK@£WKXUV@VUUUVW_V@W@@K@UIWmXUmULnJkImmÝaUbLK@UWk@mnU@kVWb@Ubmx@lzUx`UULml@XWl@UV@nk@UVb@XJm@@Vknyk@zJnUV@bk@mJ@b°Ò°zXVlVXx@bXVmnVbUlVb"],
                encodeOffsets: [[115864, 39336]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/si_chuan_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "5133",
            properties: {name: "甘孜藏族自治州", cp: [99.9207, 31.0803], childNum: 18},
            geometry: {
                type: "Polygon",
                coordinates: ["@@aXam¯wm@±°wUwV@UaVw²KU@UU¥a@£ÞôxKnkmX¥IUÝUwlk°V@ÈKUwlkUyV¹mx²XllÑW»lw°UŎnJl¯°V@wôIVÇnnUllLVÇLô¼XW£@±@¥k_ÇJkUékwXa@Llw²Vxbm¼ÈxlLÈVWÞn¯mÇÑUÝlÛkwlĉmULmwUJç@wkm@ÑlUXÑôġVaUÑ¯@wķÓkbVmnU@@y¯IķKV@¹aé@kmÞU°¥@a¯@anKlblU¥@óğç@Çw@wklaçÝ±k¯±@ğÝUÛmÝ¯w@kb±¯akXWÜkXUÆÇU¤X_ƐwV@¤XUbUIUlÇUkġ@aXČmlUlèUV@mVk¦Vx@¦±¯¯¯anlW¯nÅw@w°KVak£m@klKknÇU»óKīlaUaV£@¯@ÆUVÛÝÇXÇlÓlŹ»WUğJ¯£mxLĵôºXVlUll²bllxónn°ÝU¼mJU¯nV@êĉ°Uĸw@m@¯kmXamÑ¯aUwÝKU¥mÅn¥Wmn¹n±ƑƆÇôXê±ǊnUôlĖkȂVÒ¯¼VnȮ¯ĀnƆĢ@k°V°¯ĢVlkVxm¼X²Ŏ@VxknWÜ°U¯nÆÝ@`ôÝ²ÒÇznmX@xè°K°ÅUČĬóĖÝó¼ÅêÒbmk@V@Òl@nĉÜêx@ĖmlÅJ¯¦óxȭ°Ým¯LĵèĀ@Æl°żX@xmkV@z@°blnÞ°J@bn@Æ¼UVUóóL°X°ÝLxUn°Ĭn@lnL@Æ@nKÆxnUnVInĬmÆnxŎ¼ĊIĢóÞ@ĊƨbUmV¥lkwnLmÅÆ¥XwU@wwUÞ@alUUÅUVkkm°aU°Ó°w°Ub°a²K¯ĕ@ÈbÞĊa»XVm°InĬk¼VbaJô£VĊankůnÜU@anKnĮbÈmÆ»nIé£Ġ"],
                encodeOffsets: [[103073, 33295]]
            }
        }, {
            type: "Feature",
            id: "5132",
            properties: {name: "阿坝藏族羌族自治州", cp: [102.4805, 32.4536], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@l@@þ²I@lVL°wnJ°UĸŎèIlwV°¤nĮ¤ÝlèL@@xlè²ôĊ_ĊġVÈôJżīlbXÆÈVkxÇVn°¦Üb@è@nn@@°UÈ¥WÇ_Uala¯¯UÇk»mVwk»k²°VxlL@¤_@x`ÈĖöb@l²alXa@bnK°¦VK@nnWmx@nUnl@@llĉk°l°UXkmW@Un`kÇLWÛÈVxVVlVk@lIXb@ylXÈWĮWŤzy@mI²J@n°@VJ°aÅ@ŎkVÇkaUwKVwV@nkm@±ôkôĊJ¼InÑm±nIÞXÈĊxĊUÈbÜyÈ£Vkw@kVUVm@a»ÜbÈmUXwÝxUn¥@°ġÅaJVkaW¯Û@W¥UŏĶ@¯kUŃ@aI@mmanwÞW@mw°»Uřk¹±WxVx¯¦U°zţWw@°ÇVÑk¯@y°a£@mnl¼aÝÝakwU±aĉImlĵn@m@kkV¯Ñmĸ°xl@XVÞmlÛÝĉUÅ¥mwÅ¥VaUwXġċaVůÛŹlwU¯Uó±xÛV±¯¯n¯mċLmnĊm@_kJWaXmwUĉK»@mwXÝUÇkKÇw»naUw±kxK@WbxlVêlÈIl`@¦@²X¤Wó»KUÈKkkmVmUÈóJ@x¯Uk°Imō¯VxkX¼Òkk±WwnUºVzklVxLÇ@¯UklVxÞVJW¦nmlLówÝ@¤b¦V@VV±LUxVbU@Vx¯x@²n°xnWbb"],
                encodeOffsets: [[103073, 33295]]
            }
        }, {
            type: "Feature",
            id: "5134",
            properties: {name: "凉山彝族自治州", cp: [101.9641, 27.6746], childNum: 17},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ĶóKnw°¤ĠIXV¼kźÔkÈWÞÈÜUVÅ°@@U¤VbkbĬôL¼ÈVlmLlkn@l¤Ub¯L@xÆx°mXmk°b°°²@¥Uwl¥nU@VUkçVnkWċbĢ@lÈVVkJVaVW@£UƏxW`£ÈVVÅlWXÛlW°b²la@°xnÞVÜĠÞ²@l°Þ²èkbl@xÈx@Ġènal£nUÇ²@ÞKnn¤@¼°U¼nVXUbnĠUVbUlV°LX@lVèÜUnK@_yXVyUwmIU»VkÇ¥ÿkkV¯m±n@n¯ÜanVVÆz@bwÜbm@wa@kmk»@a@VUUów@nb°mXmnVbÞVôanwJak£lwLÅnÝ@wl¥IÇÓ@UL¼kVÇÅó¯kVmmw@n_Vn»°LÅ»@éÇçŹīVÇÝ@ÝğUaVÝ¯ķlŭġl@óÞÛċ@¯nkUÓm±IVġUwóKUn±¯Kw»KÝVnl@óxUwţ£ĉUmÅÇÝKÝUlmK£UV@ÞÈW¦Ò@Ĭnny@nÒmV¼@°Vbl@VlnUUwl°a@@llnk°lbnKWĀnUVxU²Åm¦ÛÇÅaUVb@¦m`móXUmmxÅ@±Þnè²U¯»mVm@wU@wÝÝmLa@VÇUkl°¯VlkV¦UmxaULUèVx@kIUxmWV¼¯VmÈ¯UnlÈ@m»ÅVWxÅbÅğW@km@kVV¦mlnn@ōl¦ÅÆxk"],
                encodeOffsets: [[102466, 28756]]
            }
        }, {
            type: "Feature",
            id: "5107",
            properties: {name: "绵阳市", cp: [104.7327, 31.8713], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ńlV°@ŐĵVX»ÆUĊÑJw@È»m»£°Kk@ÇnÑÆ@w°JUwnw@wbVb@VlźLUwa»aUklyUUVakwWXwWUxkLmn¥mwkUXlJw@aIk°X¥W²l¥aUIlmkklÈL@m°nlWUaW@V@UaV¥@ak@Çk¹K@aK@kKkÇX@VU@kx±VèkIWwUVUkkKÇ@a@wkml¯@kUWn£WaaVwnaVÝw¯@UaWxnJÅUxUma@L@mbUU±VVnkxUÆVm@kkKW°X@¤ÇUkÆÇnU¦¯kmLVwÅK@UóbÇÆV¦L@±êX¦mVÞkÜÝnWU@k¯wķn°ÒUlln@@ĶmnkĊJ²bVlxÞbÞbk»mn@¤¯bz@l°UÒ¯È@xŤXyV¯°¥Uww²XlºVŚ¯¼nx@XÝmxnb@nJ@b"],
                encodeOffsets: [[106448, 33694]]
            }
        }, {
            type: "Feature",
            id: "5117",
            properties: {name: "达州市", cp: [107.6111, 31.333], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Uxn°bnlUnÒÆnn@n¤LnxlUV@Ælx°XXxl`XVWLè±nÈb°b@²x°KÜ¼°ĉV¦lJnU@¦ÞJÞğmLÞ»xUlbVÆannalVÆX@lnŎVmUmaÅXa@aWm@£@wĉJVkkkkmnk@mna@alKJ@ÞwmÅÅ@ambkU@KUġKU@mak¯±a@aĉÑÅaVwXlw±V¥l@@ak@@£mĉÝónWV@nÝÇÇxUmbaVkkk@m@m°ÝýXmakÅī@@mb@@xmnb@mxkWL@¯b@WUXmWWKkbm@kxXmm@LUlxlêóKnUallLlLó°m¯JVUK@xK²Āô¦l°"],
                encodeOffsets: [[109519, 31917]]
            }
        }, {
            type: "Feature",
            id: "5108",
            properties: {name: "广元市", cp: [105.6885, 32.2284], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÆLĊx°»Ŧ¦WLÈ@xÞKÜ°ÞnVxÅĀlÒnJ°a@wV¯l@XWknKnwVÈ°XXalX°VI°bWna¥@w°n@yÆ@nkÞ@°¯lJn°IÈlUlXÅ@ķlUV¥VUUÝÞUU@UwJUkĉm@ýlkWUwVwWJk@VUKlUkaVUmLkm@@UIk`@UmlUkV¯ÇXKÝ_mm¯@U`kwml¼±KV¯¯Vk±Vk±kzmaKUnÇ±bk¦±X¦¯WlJ@bxkIWVlxnm¦nlKVwXWxXlxUbVVkzVlb¼bVxŹKUk@Uaa@xmxVx¯Ix@ÅmÒ@Èl¯L¤n¼"],
                encodeOffsets: [[107146, 33452]]
            }
        }, {
            type: "Feature",
            id: "5118",
            properties: {name: "雅安市", cp: [102.6672, 29.8938], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ln@xèVInxVKnĊklxkÜVÞÒnÈm°nx@¼ĊLVnxWXblI`@nmĉnKČôÅlUÑmUK²¹@ÇÅVÓÅ¯VýÞWUVmXÆbnwKUÿ@UmmIUb¯¥Uw¯ÇmçmanUm»UUlk¤a¯bVU_WĕmÇÅ±ĢUlUlÛVçkU@W¯KUVkUağVmaVWUmV»¯@»m£mÝL±@ÈmVk¤mb@ô¦kVkamL@b°@b¯¦ÝVn@lêb@ºUĸL°J@zV@nmUlaĸÔ@x°VÒUbóĢÒWkV@Ò"],
                encodeOffsets: [[104727, 30797]]
            }
        }, {
            type: "Feature",
            id: "5115",
            properties: {name: "宜宾市", cp: [104.6558, 28.548], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VlÈnlXnWLX`m²nV@b°xĢçlnVmnn@@°UzlV°nÞÒkxlw`UnVbmL@albÞKÈÛmÜ¼°@XÇ@wmW@ÅKĊLlVLVŎçÞL²±ğkw@Uy@¹lKXlKVa@wČ@w@aÇU¯n@@wġakaōK@Å»VakUWmķwkbğ¥mLak@ġÞ°¯xVVÞ@VxVVWxXlxU@k²WVÅULmèULVĊklĠVJVx±nÅ¯¦mwğ@mlğkkl±@kUk@¯±ÇKkxl¤bImx"],
                encodeOffsets: [[106099, 29279]]
            }
        }, {
            type: "Feature",
            id: "5111",
            properties: {name: "乐山市", cp: [103.5791, 29.1742], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@kVkÆkV²UlºÈIlxLXèÜlUXUmkbVèx°@@¼°Knnn@mÆIUbnJ@bVI°b°±@nK@mVakkKl¯nbmĸèl@VnÈlUUwwmwnm°¥LlLnU@VaImbkmKnk@mbLVJVUUVnkVmb@a¯JUaÆkk¥IW¥KlwÑmÝU¯kVy¯@@mmnUkmġè¯w@aU±mnW_XKWmkÇmUkóbUÝUanmW¯nma@xVôUV@b@l¼n@lb@xnÛaxa@yUÅmUÛbm°@mn²U°llĀÈ¦lUV¼nJVxUzWz@`mL"],
                encodeOffsets: [[105480, 29993]]
            }
        }, {
            type: "Feature",
            id: "5113",
            properties: {name: "南充市", cp: [106.2048, 31.1517], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@È²VmLnblyl²²UUl°U°²L»knlx_V°@nnÞ`WL°ÈUVlnkV@l_JV@n@lnKV£ÇUV¯m@laXUUbVx@VkôJU°Jn@wUk°wnUV_nJmknmm¯Vwk¯ó¥±ÿL@wLVUkUbX¯mykI@a±Kk¦ULmaXVm¯Kz±klUIVbÇJkL¯lUÿUlUkJUmUUkVVklKk@@aU@J²x¦kĬ@¼±ºXnWbxU@xx@lL@bLlº@Èl@bU¦Vb@U@XbVkX¯m@nÇKkllknJV"],
                encodeOffsets: [[107989, 32282]]
            }
        }, {
            type: "Feature",
            id: "5119",
            properties: {name: "巴中市", cp: [107.0618, 31.9977], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VUlbkVVLUl@XIUxVxXkl@þĊnVlIVx@VVÝVÞUVU¦kV@ĸWÆô²@VÞn@Vaôb²W@K@XUmÑUW°¯°Ina@y_lWn¼lLUbô¼Kla@nkUyôÆx°@n£Ý@¥mVkIU¥Ċ¯Û»¯L±w@¯aÇa²mçKXUWk_Ww¯WwÅk@UkVmwK£@mmmÅmÑkVmamnnlmIU`Vm¯xVlx@m¯IVóIUl@UwVaVWkb@nU°VÈU¤"],
                encodeOffsets: [[108957, 32569]]
            }
        }, {
            type: "Feature",
            id: "5105",
            properties: {name: "泸州市", cp: [105.4578, 28.493], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VVXwVKnwnVnl@b¯xmKUbVn°°X°@blLènV@Vnl@ULnmmUnaVV_ĶV@wnJl@@kkKVólaUwnJmwUlm@aUaôKVnJWbÞ@VwVLX¥VV_Þ`wWÞŹmmnIn¥W@kWV¯@°kILk¼Ç@k¤±XknmÝ¯UlÅÛKWV¯klUwkLÓ@U@w@ġXVWX@UbVbV_kÇVlU°lnwŎ¦ÞaÆ¯nmm¯Um¥nkVmkl_ó¥¯UÇl¯@Lk`¯ķLUy¯@mw¼ķ°ġ_ÅU°mlnÇVUÞ@_JUnVUXblĢb@x@mV°Èb@xċ@@xUbkLWkL@ºzV@lxĠ±²"],
                encodeOffsets: [[107674, 29639]]
            }
        }, {
            type: "Feature",
            id: "5101",
            properties: {name: "成都市", cp: [103.9526, 30.7617], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°n°m²°ÜUw²ôV°VkxÜźUŰČbĢlaÈL»@kwVÇ@nÛÆ»ÈUÝ°Kl_V°U`Vbn@VbÈLaVU@ƨ»VnIlUUa±lIk±@VnKmÅ@WaK¦lVōkKÝ@maXÇmw¯IU@kVwUmVIçÿU±Å@¯È@xK@wLUbÇKÅ@mÝ£@yóUóóUxkI@WlIUabaVĀLmxÅaWUnVÝXUþÆ°UÔÈÆ@±ºLnVVÒkóÆ"],
                encodeOffsets: [[105492, 31534]]
            }
        }, {
            type: "Feature",
            id: "5120",
            properties: {name: "资阳市", cp: [104.9744, 30.1575], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@èUJVnxU@lV°JnxWÈnbÞ@lLŎUk¥LXbÆ@nmLU@zlbXmlnVynLçJVbUnómUnamUan¥lKV_²aValWôn@nbVK°¯VblW@kklUnlV£°W@wUXk°KVwmVkwVyVI@wkmVÅ_Umm@Uÿmbk£xUaVw±V¼V¤kLWxU@UkbyXóm°V@@zÝÒkKn±U@@_VVkÇaVwnLWalm@@kkVVl¦kIV`±n@wKk²aVUUV¤nkxmUkVWVnLUbVb`kUUmLUmX@`ÅbÇXbWLXn"],
                encodeOffsets: [[106695, 31062]]
            }
        }, {
            type: "Feature",
            id: "5104",
            properties: {name: "攀枝花市", cp: [101.6895, 26.7133], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@bKÞnÞ@xV@xnUn°¼V±mç²ÝÆ@wnnVWnôn_@¥UaVbÆÈÜn¥Æ±VUwVmXÿmLkal¯km@k@¯bkVxmVUkk@Ua@¯»UnmÑ@mzm@īÑX¥Ç@ÝxU¦ÅÇUkx@lbUWVXmV@xĵĖ±@@¯xUÆLnÆmx@nXL±lUUVwKWak@WxkbÞĉbUn@@@xó¦Ŏ"],
                encodeOffsets: [[103602, 27816]]
            }
        }, {
            type: "Feature",
            id: "5114",
            properties: {name: "眉山市", cp: [103.8098, 30.0146], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Vx°¦VanJVn@baVbkJ@XlJVwôôôV@zÞ¤@nÆÈLVaK@xL@w°ÇÆ@²VĀmWXKWaÈÆa@_nWVnKVlV_UaVamKXUWwnmmwÑm£@ynUkWĉUkWVkkV±çkJmkKK¯¦mnnxxVxVÇkUmk@çķnmak°LllUb@nmL@¯²¯aUJ@amIVaÅJnm@mm¯L@»¯@wUçanlVWVÛkWçKkwÇJk¹±VUÅlġV²ÈÆnXĖV`U°ab£lkVVn¼mVnbèÈn°"],
                encodeOffsets: [[105683, 30685]]
            }
        }, {
            type: "Feature",
            id: "5116",
            properties: {name: "广安市", cp: [106.6333, 30.4376], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VlIVkVĀVk°lKÈIUaVJlk²yLn°UWnbVKl¥²L@blJnzW°alV°Inô¯KkKkkbVmôLkéwVk@KnnWlwn@laXLnXVW@X°a@XKlnw@man@w@na@@wĕġġwUkUWb@mk@¦¥mUÛb±yÅn@bml@kV@lknVbmVnlmbÇk¯bWyk@V_UamJ@I@WaVXamIVWkUkbVaUUx@VnkVU¼bkKUxmK@WxnV@n"],
                encodeOffsets: [[108518, 31208]]
            }
        }, {
            type: "Feature",
            id: "5106",
            properties: {name: "德阳市", cp: [104.48, 31.1133], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nUW¥²é@K¥UÈÅôa@VÆLUxnKl°V¥ÈmlÅÈV@£WX¯lLln@UVÅlwUm²UVVna@@KnbVVwÆImXwWkIVwÝĕVUaIèmKUzkmWnka@y@l²kJ²VbVkmJUƧ¼@UVbÇKUam@Ua_¯VUk`¯LVÞÇÅ¼mÜ@UÈx@l¼ÇKkbWVxUbÆ¦nxÆ¦ĊV"],
                encodeOffsets: [[106594, 32457]]
            }
        }, {
            type: "Feature",
            id: "5110",
            properties: {name: "内江市", cp: [104.8535, 29.6136], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@²èlUUllXĊVXlmV@zn¤ÒnxmnXxlUnVlwmU£VVUbl±L@x²mU_lJ¥UklU@ln@kXbmKUxÈblUU@`V@²mlLÞÑ@yU@¯ônWzaVlV@XwlKU£»aVaUwm@mwUVUwklVÇ²LlKVm_@ykUm@mUçkKmxkIUÝ@LUJ@n±kºLXb¼@mmIXa@mamnkWKUx_U`UklwUwmUbV²akbmkn@`UmÒVxUbI`UaÝÈ"],
                encodeOffsets: [[106774, 30342]]
            }
        }, {
            type: "Feature",
            id: "5109",
            properties: {name: "遂宁市", cp: [105.5347, 30.6683], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÞĖUxlJXVb°@xUÞmbUxbXbm¤VX@lk°ln@xbÈ@lLVlVUXxlJç²UlwV@@UÈWlLw@wVwXaWm²¹@»lī¥w±I@V@bl@kLUllUVVn@mmUwXċbVb@VUkbmamW@ka@k@laUa@¯b@mmwó@@lkXUa¯°LUamm@ókXUb±bU`kLm¦bnVmbnVmô"],
                encodeOffsets: [[107595, 31270]]
            }
        }, {
            type: "Feature",
            id: "5103",
            properties: {name: "自贡市", cp: [104.6667, 29.2786], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lIÞÇbV_JVaUwnÑV@_lmnlab±UVanVxkxVlV_`wVLlXnmnb@WbnJ@n»WaKl¹²@mVI@KÞVlJnw@aW¯¯¯UmVanL°w@akmmUxmULWxUUÝKōèUKUkĉKL@ÆnX@xWÈ¯@Û»nÇÜÝLka@bKnUaVm_xkLX¦Jl¦ÅlVb°I@bnaUmlUVUVIUKa@nmlnLlnaJUbV@"],
                encodeOffsets: [[106752, 30347]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/tai_wan_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "7100",
            properties: {name: "台湾", cp: [121.0295, 23.6082], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@\\s@pS}aekgKSuSsMß`¡CqZ·be@Q^o@gieMp]}}Ľc_Kk{ùA¡r[uom@ÑĥJiq©mŉq¯Bq]ÙYgSåk_gwU­isTEĕiqiUEkue_OSsZaWKo¡­qycY£w}ĩĕS§Z©SN¥SyLÑ¡±Ks^IYPdY[UoFp}´\\¬\\j]eÜò¤¡ā a\\bnUãº¹Ìs¼j®[cíȈEĝĆ`ļf¶®K|VØDdKGpVnUFjpHF`B[pMºxÖjbpÎxp¬|ÎŸÜÒC²®ÜApZG~dÞàV¨|¸`|²tx~\\~|dFf^zGĄŚhdL\\hĸ¼OªP®lV`p\\]Xpllæ¤CpQ|oF}fMRiNSon_²qämMNM\\"],
                encodeOffsets: [[124853, 25650]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/tian_jin_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "120225",
            properties: {name: "蓟县", cp: [117.4672, 40.004], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@EUDAEI@WNMNCBFAHFFNACDJDPBD@@GD@DIFFHEFGDBDEQOFG@EI_KG@OcJQM]RMEKBGPG@[LaCIICBWKCEEG@WBQHCDFD@HSLEJI@IHWECFGAAEKCGDBFCBSBIDCKKHEADMJMFABKOKEQAA@IEEG@GIQAEK@OZEESMOLlu@SLUTYFQCMG@@SQUAYKAACA@IB@BDB@B@DC@@BGAEFAA@BEGKJCC@AGAIHA@@JC@QEIP@@A@EGIDC@O@C@@@@CJCWKABFLBBEBSQGBAAMIEM@AKBcJEN@BEBCFMAEFEF@J@BG@BFABECKFG@AFQ@@F@BEB@@A@@AAAKAE@GFGDECEFEECBKIKDELDFEDYH@EIACDCHKBEB@BAAC@ADBHABKJIAIJICEDGDCD@@A@A@DHCHJHDFEFGBKRKBGIK@GIMHSBCH_BOJECCJCFKKMD@DNJEDEGC@OJCJHRUL@HRJ@H[DCNKDZHCTFDHCFFKR`TANVDFZRDLFARB@HPAPG`ILAR@TERNDFNHDLCLDDCXDYbHF@FEB@LDDVE@JPNfXPINCVDJJD@NJPAJHLXHDNANHhB@DPNLRMTBFRBHHr@`NBFEBOCCBIAQJDHCHLHFA@HSDCRLFTB@HEFLNF@PELBDJALFLTC@EPFLLP@tUHQJDfIHGTB^JTCPDLKAIBATFPADIEGECEMJ@JIAIHGECFEAGDI\\SPOXAFCL@BQTQBBTMZECYGAHA@GJAE@HCAEME@IECFKJADDBABLTHHG@ILEAMNDJCDHEBF@@JNFJELDFKTOT@JETBFFHBHEHKI@@IJEJ@XKEOUMS@AF@CEB"],
                encodeOffsets: [[120575, 41009]]
            }
        }, {
            type: "Feature",
            id: "120114",
            properties: {name: "武清区", cp: [117.0621, 39.4121], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@FWôµ@IFCLIB@EHNBp]AGEAKAEDMGZKFGBGME@ILGP@HEFB@BXMEAHUGC@IHCLOD@X[NWHWPKAEF[@EKIOL@EKGBNMJ@EIEHKBIC@BAKMIACCFQZCF]DB@ERAKADIHGEIBCGIIECFaGLZO@EFCNGAGDGAKL@BMG@IE@ADSDEH[JGC@CGA@BMDeK@EIACFE@@GG@FIAMM@CCGC@EM@ADE@CFMAAGHBDKIEAJG@DOGCDEKAGIS@KFCHKAEHIE]BeKNO[IFIOELC@A]GMBKVYCDDgGAICARc@MW@AQE@DGI@@AQ@@BKBAIQQYEFW@CEADIGGBCEIiMEMF_LGEKMBBDWEBGRC@E_CHYGCH_IAED@FFBQh@FGJaJ}AHRAREF@bE\\C@CT`FHC@\\BBF@BID@HGDDJ@@FAHKBARECKDAZBJIVNHCTA@EREAMLHDAFFBVFFC@RNRETHD@FOJMACH@CAB@P@DF@@FGDWE@FFSIEMKQDYCCHKb^JADOCIDGNDBdBCFJB@EC\\A@BJEA@JAAAD@HHD@LFBCFF@BERDHNhZQHMBGHOACCEBWEGD@PSJKCGEUD@CINLFGHE@AJK@HDABBHTB@F`DBFLBBHEDARCFG@ABJBAPVFE^FBGLGCFG_BMLEXGAAFE@@JNRVJHFALFBEHQJCTbNDHCF@PlFLJSXCHFHfVBTNJ\\BPJXC^FAVNFCHFB@FFH@JF@\\ABCFD\\BDMCAAJKQBGAILOEGHILECQLWFENJHADC@QxNHFJNLDFA@CBA@DUÂmR@FBL@BD"],
                encodeOffsets: [[119959, 40574]]
            }
        }, {
            type: "Feature",
            id: "120115",
            properties: {name: "宝坻区", cp: [117.4274, 39.5913], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@TZbB@JHD@DODCLM@AP@LL@BNH@ETFN@`E@DNG@CHLBCJA@AICFKDDBKA@\\N@AFNAGRBFjFFFL@DHLBLFQPcXAZMJ]GAVHAIZJFNE@JpDRRDCLFDGXA@EFF@CFFPDfEBDB@DCHCFCJDJIJBLI@I@CB@@ADBB@FALADGDC@@H@BB@FZGFCCE@@FMLALJDAFFFEFDFCB@@AHCF@L@@BBB@BB@FC@E@@R@BEL@HEFD@G@AH@AIB@@@FEFEBALDDEFAFO^IF@JCBBFPNJJ@D@PRDCEKBAXL@BIFD@T@JE@BHHJORFDI@@B@JGH@@B@BDDLIFFHCD@D@DEE@BAAAB@DAF@B@H@NGLJLMRDNMfGIEPMI@GDAKK@KIDIJ@GE@CFDN@FE@GFEPGV@TCDFKHBBF@RW@DD@@ID@TJFKIKLI@EP@IGBCLAEKLEN@KSHIGYACSD@SEAMBBMGEBMQBCMIGKFB[D@HDLPHDBC@IFITDLG@IIIFGVBNJDLN@VIRI@YIAIHIC@CLKZCBEE@JECEIHEAKGDGECBGEEM@@DA@CCCBBEGA[GEDBBoNAAH]MKiIAWKQoIIPMFQAEEDMH@FMSUYIeF@EK@BIOEKJEBICFKaKPFAFSE@LWCCFMHDDEKESBOGBKIEIODLG@CCDEQCEDWEMDIEIB@EHGEEDAEAa@@HqDEJGF[AECCFa@WCEIKAAEQB@FCAE^YDERDDJBLNABD@AJGLJF@FNIAMLH@FPKLJ@FE\\BFOLGXMXW\\C@KPGD@JHDGVFBWN@AEAGFO@KH@JNFAHEHYLNHFCLBFBBHo^MAFGA@KJED@Jó¶EX"],
                encodeOffsets: [[119959, 40574]]
            }
        }, {
            type: "Feature",
            id: "120223",
            properties: {name: "静海县", cp: [116.9824, 38.8312], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@NGFMDATCNDR@CCbINEHNJA@C\\EEGVE@IhE[wepc¢·²^QEKIEKIgiQDkehY£uSDBMkUDOJDHC@GF@CAFBFEN@CQ@BeP@@G@HD@@MHQKi@[IGCOCESE@GMA_OcCGDu`a@VZzKDkJBLNXGDqKEWE@cFEFA@ISIi@@KMABJGBcMuFEzGVH\\ATSEUBeALCEMG@CEBUHUCGXaBPtUBBFIBFTDFF@DDKBFNGBJPHXDDMDCLJ^mBIHIL@LR\\@LCR[@@z@NFD@LLBNb@RHDBNTPT\\F@BJF@BXCFBHHBDLFB@HODADE@@JHVXCPDHCFTLBBFNCDCCCU@@GAABEHHZHBCAEdEjFDD@GfD@DXFCHF@ERFDLBH@"],
                encodeOffsets: [[119688, 40010]]
            }
        }, {
            type: "Feature",
            id: "120221",
            properties: {name: "宁河县", cp: [117.6801, 39.3853], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@BFLBFJXDb@DEFD\\BHEFIrC@Gb@FBCBFFGH@FJAJFNCXFFCRDCFDDH@CKJPJFALPHTALFCFGCENDDKXF@ETEBObLELJDFALIPFAJL@@FfEZJTVENG@CNFFRBNEJOpJLRBXjJNLG^BBpMAAFC\\HHBAFDADDB@@CN@FFAHFDCHLHFBJGFCFUNKJJTD\\XUXF\\^F@DDDQXXBRLRCBDFEVCDLVDpUl@LEDJHAPRFGL@CETGPBTCDDVI@CFF@GFDCCVGLKEK[Y@MECISG@BKNSCGCKWEAaEBEKNGFSECO@GGM@GYI@DÅCMLHPTF@DJHAVVNKEGDETJ^[TJNNd@NOAMFYJ@@GFANDPEJB^aOadSTQSI@MHBDIEOKCG@EEFCKCqXO@@DMFENCDDHCCGJ]AKFoDaGGHYFDHKJiCMFGC@EQ@AEHGAC@IEAATKOHGIC@IXIFEoGE[JCFCDHNmRADFZMF[EEBMO{GU@AOW@@]ZeHBDEHBKEfQkuIWBs@EC@d[@[^EDMTKCEEcI@cDAB@FCBCACmOCG{PYHeBgPwPFDDALFFFCHQGSD@BHFAR[TaFYXMASUiGFL@DQNCJI@@D@PLDN`ETEFIGMCGBCE~CAIFDPEHGEQPHJADFJGHCJLB"],
                encodeOffsets: [[120145, 40295]]
            }
        }, {
            type: "Feature",
            id: "120109",
            properties: {name: "大港区", cp: [117.3875, 38.757], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@JFFL°_`ONJKDDFIFZN xlb~yFVNRrdJGzDPVFBCTNND\\UR@E`F@@Ip@IWGUoawOEE@ÏDgK{İEEMFëCb@KwOCDHHKBDJCDEEEAGHOABFABMCgDLSQ@CFEBMgYIDQINE@AUSwSAdYEHQMEyK[KI@GRMLE@@OqOoBOnpJ@BmEAFHL^FDB[C@BBDVFAHFJENB@sNEjQAMYsUgCSBGDJH@\\LjGR@NC@@G@HO@AfR@DM@EFEADBE@@HGDICCPlVANTC¤vgZlfRChjLJ"],
                encodeOffsets: [[120065, 39771]]
            }
        }, {
            type: "Feature",
            id: "120107",
            properties: {name: "塘沽区", cp: [117.6801, 38.9987], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@|ODHnPBDADEDA@CB@ddJFFLDNSFC\\]\\@@cFD@nACOMW@M@ITURBRZNHNWRQoOj½fcqAqeiDÿÍyÓįFL|Ch@ÐFFxPpbHVJXo@@JCTR^BPABQA]^MB@bE@@FQBFVJRH@FXtPNZSBAja@@NDTLJrQTHFXZFB`"],
                encodeOffsets: [[120391, 40118]]
            }
        }, {
            type: "Feature",
            id: "120111",
            properties: {name: "西青区", cp: [117.1829, 39.0022], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@LHAHRHATh`LHNHDG`HDGZ`D@FQDAHXFACNAFLVRTBFOfHDCVBFQH@HSXHEPFB@LDBF[bDbLFKJBFLADBDjLvCPEI]FGEIGCBEUSjcFiBIVWfaHCjN^HtwBBFGPBJGjFBEGECGDONMFAP]TDHQOWCMGAMHKIJEIGQ]aDlUG]VGEGDC{PEbBZmE@@GH@BCA@FMQCFMYMJECELCMI_P¯`]R±¡¸odfx\\gF@JUFFH[F@DIBGMMFaJDDQ@MCSDCBENMH"],
                encodeOffsets: [[119688, 40010]]
            }
        }, {
            type: "Feature",
            id: "120113",
            properties: {name: "北辰区", cp: [117.1761, 39.2548], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ROHFFGCOJEDB}DFHANDJHFEFSM_KC@O@CJ@DIRM@CEKKALFKACHoLSJSIBETDJaEIIE]E]K[MYUYQILC@GF[MGNKEK@A@BCWECAIFEFYAGFOMI[OFuDiKACBCEKIAELaKaCE\\CA@KEAFOWGGTG@ERUACDeGEPSAUQKHE`FNjNFJADHHCJFB@DEXZFRRBJLA@AR@@BJ@CHF@BRX@@NQdDBBJhHCCZDLUNA^H@BKDPFEJ\\JMPfL^AJFFGLBDGLET@HJLBCFHDCPH@BIJFCLGABHNBDEF@BCN@@FHDDDN@BNEJH@@HF@DEJB@FfLNC@AHB@DHD\\IFGTCBCF@@JNH@ALKHBHCHBDMFEP@KYbHDEJF"],
                encodeOffsets: [[120139, 40273]]
            }
        }, {
            type: "Feature",
            id: "120110",
            properties: {name: "东丽区", cp: [117.4013, 39.1223], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ZV\\N^L^FJFFJIbSCAFTJTIpKDGLBEKLBjHTVNBZWbE\\SBQGE@ATCRHDGEEKECBECxOhOfAZGA_YEEWSGqRKISC@Mb@BiTAMYsOEWG@IQEURA@EF@@acUOXQRYCUDCHDTEF[SUEgAYDcVGJM`iAWDWLQRMHUHgDsDBLHJFCFDFGHBFFVEAGHCJN@RJFPIhBD\\FENCPWA@LFBAFHBEJUEARCDIAEDQBRNa^"],
                encodeOffsets: [[120048, 40134]]
            }
        }, {
            type: "Feature",
            id: "120108",
            properties: {name: "汉沽区", cp: [117.8888, 39.2191], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@LMEI\\MTABKN@FCDMH@COAcH[AoēAM¡Wa[MeqpQRMXMGQYQASV@J@NNXDPmBAtJXlveRLFGACFGAYf@^X@BPV@|HNPFA\\FNEEYBCnQGMDCDE\\IHFpEFWJ@JJDGHLPBSFB@JBDGHBFR@@FHDNEjDLICGZEHGbHpCLE^BHIDDCGDCFMNE@CP@rWLDEDFFH@"],
                encodeOffsets: [[120859, 40235]]
            }
        }, {
            type: "Feature",
            id: "120112",
            properties: {name: "津南区", cp: [117.3958, 38.9603], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@TLv@CNHFFBHGZFETNPhCVGNGRQXKXCjBN_HIdUZChBVF\\TFECSDGVCZDRQPWdVNA^]RBBAAOQ]DSE@F_Q@[VMCSMADUECOHycIqMQEU}zkawENRDENB@ADG@@HF@YnaAOF|CDFHUHH^kVbCR^JHIFLJNGHBDNPXGRSCO^EBMNCPDHHFAFiEIHOAEH"],
                encodeOffsets: [[120045, 39982]]
            }
        }, {
            type: "Feature",
            id: "120103",
            properties: {name: "河西区", cp: [117.2365, 39.0804], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@d@hZNFdcLYXKRCtCMOFSYEGHEAGEDMu@SKAAsx]GMTGt"],
                encodeOffsets: [[119992, 40041]]
            }
        }, {
            type: "Feature",
            id: "120102",
            properties: {name: "河东区", cp: [117.2571, 39.1209], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ZBVFFIGABEEA@KXBDOFM[EACJgOIE@QIMGDBHUFEEGAEHECEDGIAKQDWLKZcdQPEP@FOFBJTJ@HNORJf@DBCN"],
                encodeOffsets: [[120063, 40098]]
            }
        }, {
            type: "Feature",
            id: "120104",
            properties: {name: "南开区", cp: [117.1527, 39.1065], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@NMVDCG\\E^B@HlB@YEDS@CHsNSiMGDebUXAJEjidVTAFHDFJ"],
                encodeOffsets: [[119940, 40093]]
            }
        }, {
            type: "Feature",
            id: "120105",
            properties: {name: "河北区", cp: [117.2145, 39.1615], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@DBXFADB@L@LFHM\\NHED@JKZRb]QMRAFCJBDCBQYADMCAe@QIMP@GSIAIPE@E[EGH@ZEF]^HJAXK@KF"],
                encodeOffsets: [[119980, 40125]]
            }
        }, {
            type: "Feature",
            id: "120106",
            properties: {name: "红桥区", cp: [117.1596, 39.1663], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@J\\PNHEZBFEJELEL@BWGI^]FEkA@G]A[FDHUCMNEHJ^"],
                encodeOffsets: [[119942, 40112]]
            }
        }, {
            type: "Feature",
            id: "120101",
            properties: {name: "和平区", cp: [117.2008, 39.1189], childNum: 1},
            geometry: {type: "Polygon", coordinates: ["@@DT@FCHG\\FFOROMEgYc@"], encodeOffsets: [[119992, 40041]]}
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/world_geo", [], function () {
    return {
        type: "FeatureCollection",
        offset: {x: 170, y: 90},
        features: [{
            type: "Feature",
            id: "AFG",
            properties: {name: "Afghanistan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ࡪ͇وŐǬϠڐŶӂʮǚڦ۾ǌƀ̚ІɣʪҴMوǯʲĹ،˒˰ǋ˖ϪԈiżŬĘͺβ̈Ҕȏĝʱʪ¡ý۷ͪ˟̊ǰώĊԼϖׂ×ࢀAƬʋӧĥяƹ७ĭࣗǭӫλȤΣĪллΛ͑ɳ̡ߛͦ։՗ɅΥԕ²ԋ͡ɿ̳þٝŋğɻسDҵӇ܍થΓבôǝȁԇņ࠿űටіހހåզُƚßՔ˟ڢάҢιŮɲؒ΂ਸ"],
                encodeOffsets: [[62680, 36506]]
            }
        }, {
            type: "Feature",
            id: "AGO",
            properties: {name: "Angola"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ȸصʌԋȘ˕͐ѯ֊æˤŠҬşŲɀɂӨԶ®ƤіHñ̡৴RfՉǞ͕ūԑÖԫ˪̷­ৃȼüκsԴŴϦ¹ĘʹĩСƨϿů̿î́ყZᦵ֤ۋպԽ໳΁᎝Š׋Ж₭ŵÏԃϞկ~ԉƝЙǅÿՈŜ݊̂ޒªΰ˚ݶȨΆӘռːϐĘج«ӊʣ̜ɡԚȵԎ®Ǩʶͬʭ߼ǣ֚сՐĄǎΌŔʒg̎ĸៜ["], ["@@ɉėɣلͼδʪƘ̀˽̩ǯƍɍλ"]],
                encodeOffsets: [[[16719, -6018]], [[12736, -5820]]]
            }
        }, {
            type: "Feature",
            id: "ALB",
            properties: {name: "Albania"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ń˷ŢέΒȳiə˗ŧ»˙ϷСƛÐgȂү˰ñАîֶŖʼƗƂÉˌθаÂƿɨôǴɥȪďȨ̂"],
                encodeOffsets: [[21085, 42860]]
            }
        }, {
            type: "Feature",
            id: "ARE",
            properties: {name: "United Arab Emirates"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ƭ¤ɱڂƂ۞uԖ{ֺ֪ظՠՎԮǆ˹ŖڑѕGçճƪŝϝǑE΅ʓΏuͷǝǱᡋъ͏࡚Ț"],
                encodeOffsets: [[52818, 24828]]
            }
        }, {
            type: "Feature",
            id: "ARG",
            properties: {name: "Argentina"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ߗ§ѓ̔ԑx࣑@Aሞ͒ϵрؿનԋ୲ȿϙп"], ["@@Ӵ؇͠ڰॠƊǷ໶ോۊŷਆاࡾ͡Ŧχࠡ౧ࡒɭ़ŷڔƈނ٢Ǝݐжǈфӝiڣۻҩ֟΁ॅࠃ૭ଧȽڥɣࡹT࠷ǽȇÝիËѫ੨ܙŗ׃Հν§Ч߯ઁఛ҉။ǩउĎǰԅǣػƺщԋ̏ࡱř̪͕߱ɗŜ࠳֨ʧҠˆʢѧޛʻڭԹūࡋȣ҇ߏEڃљʋؿؙࠞߦǝ˿ݭ঳Ӄձটލͧ΅Ͽ˔ࢍ֔ӡΟ¨ީƀ᎓ŒΑӪhؾ֓Ą̃̏óࢺ٤φˈՒĭьѾܔ̬૘ěӲξǄę̈́ϵǚˢΜϛ͈ȝॺ͸Ǣƙ਀ȠࡲɤݢԊ̨ʭࠐEޚَոo۰ӒࠎDޜɓƶϭฐԬࡺÿࠀ̜ބռ߂צԺʥ͢Ǭ˔ඔࣶд̀ࢎĹɂ۬ݺશȱ"]],
                encodeOffsets: [[[-67072, -56524]], [[-66524, -22605]]]
            }
        }, {
            type: "Feature",
            id: "ARM",
            properties: {name: "Armenia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@୞ƀǨə͌ƣǛɁ҄˽ʁˋΦɫϘƏl׋̣}΃ӢHżχCʝɤǩuͧʖرȼĄФƛ̒"],
                encodeOffsets: [[44629, 42079]]
            }
        }, {
            type: "Feature",
            id: "ATF",
            properties: {name: "French Southern and Antarctic Lands"},
            geometry: {type: "Polygon", coordinates: ["@@ը˃ߐĿǅɽϣಇÃq҂ŮΎÊǢ"], encodeOffsets: [[70590, -49792]]}
        }, {
            type: "Feature",
            id: "AUS",
            properties: {name: "Australia"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ߺ́ҜŘپǊԎÉÐঽ˽́ēگ̉ɰ׍בǧ®ԫԭܘŗֈӝܸtϬռõ"], ["@@̢ڇբ̈́˦ΡЖ͟đϋǴܛŸнɄĹɬܕąѥ˖֭࣬ѭצЋ֞λŋȯӔՃࣧ͜ͲȂ;ηȴźƢࢹ׬ԩϸ͋ڀڹʀڭtӏËԳА܋µݓơϵɩݡjӕǕ׻χއثЭ̫ٱ˫гʝܧ͕нɅػŉׁªˇӕ̇वޡ·ϫ͙ԕέ۟ψԥƪżѬҝǃ݁؉ܩɪӉƄӑÔ߿ʐիԮƻْțьЭ;߱ĸˢРȯزЧ׉ݝƷѮҬŶӞ͘ЬãجہܑԿ˽͏ڛٽΊ~ҀԿ،ѹ̀ǂȘઃԚןz߯Цຓāછ̝ख़˫ߡÈࢻљܯȗǉѱ̳Ϳ܉qՅõݑƶ׿ğֽԁ҃ʕуʁЗˋؕ֛Bࢽ՜ҋǄlӖкŘƚȒ̠ĺאģӼѻࡖƏӒӎͭնsʚϋͰĽڄӓڔřΪτε˳ެиʑʞ͗aјеڎă˄țʦĠӠǢȸŘрęӮΎ؀Úٕ΢׀ۀˬЦΪٜ̰ϤàɴĻڎ̺ԚĤŶȀɞüҬoࢨʖҚώɊ҆ӲѐͲvҘט܎ΠܩΦǚ̗Ј˂ТψǻĸٖҠаȮͨцƜ`ɼτĭdɂτŦОŔبϫҲӽՂMՖÿǱҦДڪϜɘſȾκӒԘ̒јıۺǂeі؛ˢ҂Ū֎ȻҀ·ۼɋʈĐԶʵӬʊ͂ñȠǊϬеɡ͉҇ͻ˿Įͱʙп̗ЭÔʁڜҫ٨ˏѠ́؈ӻʂBѰɍŶʷߤ˵ֈ˼ǐҊǠόľҤʰڞŝОÔʔīӔŌنǈǠŽˬȮѾǆҦtʈ̸̾ʂЩÎՃȾķΛ̨ёÚӇ̥"]],
                encodeOffsets: [[[148888, -41771]], [[147008, -14093]]]
            }
        }, {
            type: "Feature",
            id: "AUT",
            properties: {name: "Austria"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Û΃ӁCǎǻ˧էǇƗܽsщȏۛÞயɐȉ̊ࠧƣĭǅԗŢѕxϝƶźȴƬʪ²ьɹŤɜݎ׸ƮЖ}ˀǣþƜšո̠ń̒ϰز˓ӀΆ̐ÚٶʱЂªϰǁãŃČ̅"],
                encodeOffsets: [[17388, 49279]]
            }
        }, {
            type: "Feature",
            id: "AZE",
            properties: {name: "Azerbaijan"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ʞɣψDGŻ΄ӡֽŒщϰƃ͆Ǫv"], ["@@ϊËƞɈԈͺѴѵђ׭ϺʸɧۗãƣٵƟ̭̍ȝvзȽ¥ԻѲ̂дʝʚ̿×যإk׌ϗƐΥɬʂˌ҃˾ǜɂ͋ƤǧɚȶƎضʍҐ¹ŘĲбҔɔŚʀ׀ԙ"]],
                encodeOffsets: [[[46083, 40694]], [[48511, 42210]]]
            }
        }, {
            type: "Feature",
            id: "BDI",
            properties: {name: "Burundi"},
            geometry: {type: "Polygon", coordinates: ["@@Á০ɃϢԜßʲӎҀÅ¸ͧǸȏT˗ȹǭ͛ѫ̧̥΍"], encodeOffsets: [[30045, -4607]]}
        }, {
            type: "Feature",
            id: "BEL",
            properties: {name: "Belgium"},
            geometry: {type: "Polygon", coordinates: ["@@؜áުǪՐοҦȝħ֧ɕĝһܿϦћßדІϷͶϷ`ũ̒ڪǔ"], encodeOffsets: [[3395, 52579]]}
        }, {
            type: "Feature",
            id: "BEN",
            properties: {name: "Benin"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ۛįȹ׆ኞǛǦЮ̇̌ʱʞņѶ̀ĨǠξЪĀȀʤˮʘ̠F٘ә˩ȎӽǓͷĘɧСԳʵʳǁՉt՗µണ"],
                encodeOffsets: [[2757, 6410]]
            }
        }, {
            type: "Feature",
            id: "BFA",
            properties: {name: "Burkina Faso"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ֹɐϽ̍Ƀϗǰƥ˦ϙǾÅӦɮΤo˴ښۢŬּɲȴОœΚǢŘɎٴϖǆˀ޼ΒҦŢɀǇՠJáСŔϣӀչНॺȏmֻǿʣЩÿǟν˿ħ݁lϳâ˓ƉωÖร¡qӉŘم"],
                encodeOffsets: [[-2895, 9874]]
            }
        }, {
            type: "Feature",
            id: "BGD",
            properties: {name: "Bangladesh"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@i׽̉ŶÆگʉѬµєǅКΕӨޟü΋˃ҳΧǠũƵʃĠ͗øŽۖ̅لƜԒԫɤȆ̪Հ̼؅Ѽ֮̔ږεВ£ô׏ߞřު^Ӟƛϯ܅ϕµʷӍҢѥƎ՞ɶFѶ೯"],
                encodeOffsets: [[94897, 22571]]
            }
        }, {
            type: "Feature",
            id: "BGR",
            properties: {name: "Bulgaria"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ʎΉ͚Ö٦ſ௾«иɌবȜ̩ؒӴĕѥΏ̫׹˔ӏܣŒࡥ˃Uлޅÿס̊ڧɱة|Ñ֊сːƒŢĝĴƘˌ͌ˀСδ÷̬ȸȐ"],
                encodeOffsets: [[23201, 45297]]
            }
        }, {
            type: "Feature",
            id: "BHS",
            properties: {name: "The Bahamas"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ȵ£ɇӜ̿ʐǾՔʨۣ̎Jӥ"], ["@@ࣷƅÏ̴Ђäֈ{~ɕ"], ["@@ƟׯƷņ`ѮϓͪCĪڐϗ"]],
                encodeOffsets: [[[-79395, 24330]], [[-79687, 27218]], [[-78848, 27229]]]
            }
        }, {
            type: "Feature",
            id: "BIH",
            properties: {name: "Bosnia and Herzegovina"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@̦FȿσМ͓ūЃȡƽû˙țūҥݓ͈ͅΘ͋Ȅϭ̾ǻʺЩϾǬΒ̞ȕǼǨϾnܠƓ׈\\Ϟȅ"],
                encodeOffsets: [[19462, 45937]]
            }
        }, {
            type: "Feature",
            id: "BLR",
            properties: {name: "Belarus"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@߼Mࣰ̈́ȚӄېːÿϔԜƚ͖ࣘࢮɁŢȻѲĴࠒȧĊЁǷɧՄս΂Ƴ»Ʊ֦Ʃʎɡ͝ǿڳǉÿȠ˧ȸ՝ܝ¹ʵȁÃхͭĆݷ¡əȞ̿ƥ́ŨڍjफȬࡕàٱmҡɩГeϐʷϴԌǢLͰɷ͌ϊ"],
                encodeOffsets: [[24048, 55207]]
            }
        }, {
            type: "Feature",
            id: "BLZ",
            properties: {name: "Belize"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@OŮĸƴı̞ԔǄZHūǄGaɭƋεôŻĕ̝ÀăīщǓɟƱǓ̅ʣ@àॆPژ"],
                encodeOffsets: [[-91282, 18236]]
            }
        }, {
            type: "Feature",
            id: "BMU",
            properties: {name: "Bermuda"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@OEMA]NOGNG\\Q^McMOI_OK@CQSGa@WNLVWHFLJXVFGJ`ZRTDLeeWKIHGIK@@[MQNi`]VDTBHCJAPBJLVFjT^LV\\RJZRn^RH`TfJjZHHOTTFJP_NOX[EYQQKMEJOLANJH@HQHAARF@ZEPS[U_IcRQXE@EEKKOCGGCQCOGISKYGUC"],
                encodeOffsets: [[-66334, 33083]]
            }
        }, {
            type: "Feature",
            id: "BOL",
            properties: {name: "Bolivia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@य़͟گӳ؈વȲ۫ݹ؅ŗ͡୆ҋऺˆ߾ѳ΢ŏ؆ЫֲՌ࣢αۺȖ˰ƭ̶͠рh܎¤נǸ˶ܩഠزíѠnȈʪ݀;Ѷ͂સƚęؽļ͓ãࣰ֛ݫऴƑ̻ͦ֨ǕΐʑՈTӦʟӟǐʕZγʓa͒এྖūӟĜͧҞɽȤԹƫڋɯρĄӏʿǥaʶ޳јޭ^ัʓЕ݋sҋͥ৕ƉǸ"],
                encodeOffsets: [[-64354, -22563]]
            }
        }, {
            type: "Feature",
            id: "BRA",
            properties: {name: "Brazil"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@૮ନॆࠄ֠΂ۼҪjڤуӞеǇǒӜŖӼBҦ̡ƴ̿Ƌ̻į͔ýޔƿʤ֥ɪ΃ǏࢱǈÈଜʝҴˀǦăӐɰςƬڌȣԺҝɾěͨŬӠྕ͑ঐʔbYδǏʖӠӥʠՇSΏʒ֧ǖ̼ͥळƒ࣯ݬä֜Ļ͔Ěؾષƙѵ́ܿͽȇʩџmرîӃƟϡĪÈ౨ۏӷݏv҄ͅ֏¶ǲΰұԞΓݴɜƶA΢ԖʎċҔɊ̈Ôϼ०ֲێǊŔŴݴϚᘰpθſӔύ̬LؐӀƒǚē͐ӯĔYՀ࿖k˦̂ɸˉǐӷǂļҨѻٸÆǌʲشȞΊƐĮΤ׸ʆ¯Ǯ܅ðśՊ֞ϓɒǀþجŅڜȿʐȤ؀žल̮͎̾ŏʂѪȜȗŉσ̀ŵȖϷɷ̏ƅ܏ɌыÔϳԬϿЮ¥ĢǒˆϠƦ˚ɢҬíȲҚçøǢƗǘĎʐͺõЈĒӔǱξǥʺɪȊŘɿДÒ͒͊ʴؤӼޒ˺¢ȺҫҼ฽҈Ƒxׅمەʾʩ๤Ɓࡃٔր੐̟ඊԡШӱƏҫ঎ʶ࿐ѹఴఔ۝੸व٪ʏܖ̦˅˸੭Ɣԗͯ൹ёշஅୡՙोثܯȿgɻءÒ༽ɹಓęօˇͧƫ૱࡛઱ƛࢁڹηȟԋ࣯Fೕ͓סύवʗ঩ڝ܅࠯ũطƔҫƽࡓȏЧחҥट๕݉ڗ֯Ͻϥߛ։ӑɷӈψЊӟֲڇҬࡹՠ̹{ࡅٰձę"],
                encodeOffsets: [[-59008, -30941]]
            }
        }, {
            type: "Feature",
            id: "BRN",
            properties: {name: "Brunei"},
            geometry: {type: "Polygon", coordinates: ["@@ͬ̾܎ҢЯ·՛Бǭ˹ϥѦ"], encodeOffsets: [[116945, 4635]]}
        }, {
            type: "Feature",
            id: "BTN",
            properties: {name: "Bhutan"},
            geometry: {type: "Polygon", coordinates: ["@@΂ˍÏԩۇ{ۿÈՇſޅ͊kǚ֌زҒɈ׸șѺqπɥ"], encodeOffsets: [[93898, 28439]]}
        }, {
            type: "Feature",
            id: "BWA",
            properties: {name: "Botswana"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ǜƭ˄ӡॎइήĝD̑ʚՑٰŹ՚ϝ஑أݭع˩֓ʧ́ҙãƧГďʽ՝țہ¤БɾΟĸХșȵГЉʧпϑ׻đȇ̐üԠӽߚɧŲAរࠤ|Ჾشಖ͎̎΍՜ͤʮDӂȎưÙ͔ڣ"],
                encodeOffsets: [[26265, -18980]]
            }
        }, {
            type: "Feature",
            id: "CAF",
            properties: {name: "Central African Republic"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ۜÚƺɎƔgȾȏ੔͐Τ͠Ѭ̌ĉ̐ʂüߺ½߆ϴ؊ࣺю;ՐƜĪΫӜԿF΃ƋΓÄʻ̆ʍٖοҢͻT˗֠ѫΖεɆԋغͩƊˉˣęաpكĘ̹ïųȱ˕}ͧǲधнϥĎŗÝʥԕطǐؙĊ՗̴ۓ˸҉˓͛яùדգ²֩ƘԅѻѯޱėʐϦϧ˔̳Ѡï̠ЇѮæʢċΞÞٴȬƴц࡜"],
                encodeOffsets: [[15647, 7601]]
            }
        }, {
            type: "Feature",
            id: "CAN",
            properties: {name: "Canada"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@؎œުxЯ΅̵Å੥Φȿˬ͆ʸ̎С"], ["@@Хcઝ˂ޯІ̄î૆Ɂ࡮Η|Ʒ"], ["@@хŝൡϢʥ̘ݩ̌Ưʈࡻư͕ҜðȚࢨǿԨŵ߄ė˺̃дЋ࠼΍Όҩ"], ["@@։ܿո˴֠ǵ̏̉ݚɱϰȴ࠼ʵʹ؛טƞņѿʼԷΝ݉ϝփǂǾیɻńইܯԅצЂ߫Ȳࣙ¹࿅~ŹʠԼ̐λɬ۸Ԓࢄ೾Զӎܲ̂϶ǋɫ҅Չ"], ["@@@@@@@@߰äʥ॓ܶگͯDԑϪ̵ϮчʾƻτºˎЂŋ"], ["@@͡ѳχîəʢ Î͖ʦΆkɈǣ"], ["@@ঝҧץnǿɪزϲ଼SiǍ"], ["@@ƼυјżӨɗं˽४ʽöЍؤÞ׶˥ݙ˃ಳȬҽϚ࠭ҁ஡ѣ˿Ӯଗăܴдņڌ˺ޔ؈å"], ["@@ष¥ȿЪΦҼޖŜپɷXέħřձʛ"], ["@@Է̍ଉʬۃğଫϘ݊ʼטζࢼʃԎƯʦǅԠ͍"], ["@@G࡭૰ڄ৐եʡح߾֥࢚؈ؖܨ°ईஞÝఔūૼй¼зس҃פ҇ŃУ࿩חୡŻࢃʨʣуߵ۽ʓοই֩ளÇڏΡÇձ঍Ŀਉڻ࣭ु͙ڏ±উంƕϜ޻ϼّ୲ǔ༞εࡀ͋׺Ѕ੆ɳࢸΟ൶µࣴąƍܫʼࡋ،ळనߗ٨˚ҔࡺѭೢףѶഎЀ॒לҮהç֭֘܌৷لলࢤνݾ˫ಾגȘ෸ɫࡸć۠ɚ޴˵ਚӣʮ͙ຄÛ}۷˪ਜ਼ގſ،ӵ௖Ұߦऔ֌ϸٺݣબੳघ৙͵Յ૤Ӂݰӓംɏբˍͬ܃ټŏͶͅÖऻ؍́׽̏൯̗੏ۑ෇ƋᅛǮుPࢇÍ۱׽ੳω௉૗ॵޡ܌Ɛഘૄᄈ۪సČݔЫߍ֟ˊࣟ˜هતп൸ŨࡆीÎ؍ժ̥ਣսᇷԁ࠯ͽय؁ٓÖ܆ฤ۞഍णĹջӆBନύʐ֛ƛ˧ɚٙىʱٹ̕ϡΥŽˏ¥čȹ໽A౥MϛƷࢵ؃Ŀßˍ͝ޗBࠛGϛƅƊǑøʯeďષлࡽſউ҅Ɂ@˷ƂĥŦnĔȂ̎ЂҦʘӺǙܴǵނ࢕ЂľƬūĺɳ@ǛƆ¥ȤǍēɥ¾ĊȡĊćɚٵːڹ˪ࠑ͘߁̨ݧʃ˝Sਕɔڻŉࠁʺ࡫Ɔו¾ʻƜƫҤ˳IE͓჏BᮝA᭯@ᡃ@ᠿ@៙@ᢡ@ࠛ@᠁@ᛷ@őF྽ࠜ׵δຽΐҳݖŤԨ੻ΨƧڴ৭؎iѠҲКwՌෙ՘࡭ॠՁ׾ޑϚ֣ΈѿѢࡇ˕ࠇҹݛւדπࠋɸࠟ|JⷎNᷲ༬ȭ೘Й࢘û݆ΖৰˀఢĹ఼τ൘Ⱦ־ΑظȠȊЄ׈ęෆݫ૦֬ŖّਔƐ͆ʖৰ·౼Λዸ̭ୄƛࠖÄଊэ஁зຶǷᗘĲܒƦࣆԋࣴьࡩΦժ˼৾ڦĎڴȩࡊҗरä๢ϛಬƄ௬oĭԺݞƦದ˵KߑՖڠڰuϞࡊ࣑԰কͺäघশ؎ૌƇࡘχଞॅݗЭ༠ǝ"], ["@@нϿሎʬୠщॊіސ˟یࠛфΒ׭ࡰ݊Ŭ࠲Ƈश͹ՆࠉʼץථеະЉĝσൡã՚͓˱ູ̯Ƃฃɪঋ»ཅ˷ᒃű๻āҕІଫɮݙģਛږ֔ĚಘƜஈરƦྷȞᅗã஗jѷ̴ዎͲಗ[ืɚ۶ـגͮᖬԠ࡬ǋ"], ["@@݉ևಹך˸Ş૸ٔȁ"], ["@@öɵࢿ|ࣟjࣿőʑ¼ऍѾ̠ИÈነěชң"], ["@@ڎԽޤڴᒆΈ෺ࢅůջဒʒߒͮሀыୄЏŊν༚Ȑ࢘՗᎐ܸͩ͹ߐ޻໯ϹጘչೲȁீޙೖÇʽכ้ঋਗά೓߲ઙĿŁӕࢪӥଜϯΌɟմࠩ́׿੕ɪᑏڨஎܣ࢔ԕƎ̉ᗱͲᅩӤ৳Ц̌ʂయќ௥Т`ʑᝡƅ܃˾ֆؤ཈dႸņ˫̜̊оચࠊɳϊ͕˾౿Рၳ˺՞ɆࢷԺ݋´ڏ˸҇ʛ຿ŅᵝȈᄫʚഹŴۥ̐࢞Ϧ஝Hˉ࡚٦ݨࡺ΄ᓪɢأի"], ["@@৊ǯຄńɖʑ޷Е౜αƱݳ൝͗߳ê׉͎ᐡٮjˎ႖ĽएռসР"], ["@@࣓عय़Խ݆`кѮΨ࠰ɮცྈȱళݟ৉Ǎ"], ["@@ᕍЙѷςኹѺήΤ׌ؘܰւࠑԦᭊƀ஬ǧᒰ±ࠄʑࣖΝ੍ɃᏝןਫי@ν"], ["@@ҙ͙௝Øৱɖ҂Ϛீɨܼ̬̍ˇ"], ["@@ٞϵљϣس൱đࣗƈjӬ൝ÝÁٮࣜౌ˺ஂµÜŎ"], ["@@̙͢ݠƘࢢƪЩԝЋ᭗Žᑯη౩mŅ˜პϊ④ĳ୯Ʈପࠐ߈ɾᛄ˳๶ӻฺÛறߨޔ̪ࢄĭ˲Џ"], ["@@ढ˓ကFܨˡȑ́८ȍՔȧଊ௬ë೸ǼႊðീÏ࣒ͅȊ΍ԽɟభǷ੽ĸᜱŻႫcഫļᖁ˔̃ҦĹжࡇξ჋ĺঅʼ͂ΈႾÁ"], ["@@ŗ٣٩̇޹£༝Ϋ഍ŹଗǼ@@ුؼႮծಆ[ସŬ"], ["@@ϣy༽Âɡɼၜ]מƻĵĩ"], ["@@༩ʋఝ˔ڼˎ௮Đஈſ˩ʥ"], ["@@৽ǏඉBbŤࡴʦҌદǝ"], ["@@కǥۃȚέ͂áΎજӪÅ৐̇ɫ̣"], ["@@͜Ε൏Ĥ൩˘ሏߺʠ৫ȮÕ͐࿶ŕᗢ̫ٞЍ"], ["@@০˕ଽʟ༇ك๥Óდņࣗ΄^̦ڔɢ໡Oए˨ՑϠ׌ώ׊ʲࡴÎοȖዜ¨੶҅මǵ൞ǃڒև"], ["@@ᖢßᅮŅ໤ɫɡᏅη᎙ǟݻȉᆬJጡԙേʃ෯ۇႿƓՙǡᡷěୈĿׇƭ۞бߙ˽ಛʃЋ͡୫ʣŞȏ෬lȳᖟԋᔧɴឿŻధĸཟªĿЖ༊Ȑб؆ԢÐᖤγ଩բഹǈڼ͘๰Ȩʄ̊஋͠ΥѠᘞڒĝ಼̪ቃĬ᰽Á๣˸۩ͼগʘȁ˺దǈঘ࿲ƌం̺ਬ©ࣤɽٔҒૐƈບĢᢲҀĝ᝚ƚᆔÁᆒÁ"]],
                encodeOffsets: [[[-65192, 47668]], [[-63289, 50284]], [[-126474, 49675]], [[-57481, 51904]], [[-135895, 55337]], [[-81168, 63651]], [[-83863, 64216]], [[-87205, 67234]], [[-77686, 68761]], [[-97943, 70767]], [[-92720, 71166]], [[-116907, 74877]], [[-107008, 75183]], [[-78172, 74858]], [[-88639, 74914]], [[-102764, 75617]], [[-95433, 74519]], [[-123351, 73097]], [[-95859, 76780]], [[-100864, 78562]], [[-110808, 78031]], [[-96956, 78949]], [[-118987, 79509]], [[-96092, 79381]], [[-112831, 79562]], [[-112295, 80489]], [[-98130, 79931]], [[-102461, 80205]], [[-89108, 81572]], [[-70144, 85101]]]
            }
        }, {
            type: "Feature",
            id: "CHE",
            properties: {name: "Switzerland"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƫŹȳϞƵіwá΅χƙةŀǻ͹ЏơƄһ˵Л¡αǶ˽ςБſ^ϠؚҾɈϤûɲƞ܎MǦǼ࣒ʱ"],
                encodeOffsets: [[9825, 48666]]
            }
        }, {
            type: "Feature",
            id: "CHL",
            properties: {name: "Chile"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@Bም࣒@Ԓw˧ͻܛʻЭӻä؏ʨ࢟ŨੑҸ࡫Ҏୃशۘǭ୼֗૜̟ѢϬ˘ֺޠΎװı"], ["@@͢୅؆ŘĺɁ˿ࢍࣵгඓǫ˓ʦ͡ץԹջ߁̛ރĀ߿ԫࡹϮฏɔƵCޛӑࠍpۯٍշFޙʮࠏԉ̧ɣݡȟࡱƚ৿ͷǡȞॹϜ͇ˡΛ϶ǙĚ̓νǃĜӱ̫૗ѽܓĮыˇՑ٣υôࢹ̧̐֔ÄgؽΒө᎔őުſݝPЙȷݷ̣Ɖ޹Σoॅ˚१ג@@ਲ਼ӔˁՒʄӰх֒Ņ෤Φ߰ࢴٰౣʔߞݒ˸ඊत̏Ѯგ֝ɠʿ਻ՉŠ˂ல˺༒ϮָʍࠎéूΠԨപ׈എΤబȗ఼ʤۚĵਞӮਆưྺ˒ნˀሤÕ൘ǩ஄ќɌɦњЬֱŐ؅ѴΡ˅߽Ҍह"]],
                encodeOffsets: [[[-70281, -53899]], [[-69857, -22010]]]
            }
        }, {
            type: "Feature",
            id: "CHN",
            properties: {name: "China"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ԑഓΫۏѷ܂ĩخӶࠜڦَϨʡƣԓ", "@@ܩЗۏʺyܢаϠࣾɾӚoȊ͍σσșӟ"], ["@@ฬˍ঺ׯͼ߃౨Cܰͨ൸ʜѳݱ͙̭˽ः֡ࠇ৵ƪܝ̑ɜܙť঳ѕwLяթӺͯһಙαƀѹܩЍ˂ֽ׭ऑҋۃա୭ʑأϽࣝɭ҂ϴǭ͞ږ֠ѹѲܷ̓ॉ׏ԫթ࠙¡ѓϻѸ֩یƏϕڔʕस׶ݚ͝լuƌѱஓɻϻҏࠇућיࣜҥͦࠝԞޓ֮٥_دՅɯȪ҃ӶʻŻۃɇڗҷ÷ؗࣧڹિޭোିޡୟۻृĩԣύ̃˘Ӈй୭сࢵŹ˻ࢱҭ·ə؎Ȧ͘ૻːЇƍࡍɔЏ΀ƄӜޏƶЙܑ̀҃ࠇīڡJ҉ȳѥūŶ॥҃x÷Ȣ}Ύ؝ʓεƸر͂ʔۤՏǎȧޜࢱƓĴাߔۮۚ{٠νȨ˭ӶӭÙࣟŲ˴ΜϿԺ׳Ν۵ȸॷ՗އسڳĿοɦѹr׷Țґɇ֋رëڌԟǭওĈोȖڿτٵǔ˯ЖҽŦࡓոکʴΑȩଢ଼טࠛՒɽऐ׾őіͭјĐۆࣙঠ൧ͼʝ٦ةϼƫʌųӎ͜ԛȔ˟ďɇިʈȔśȠߤЈ׈ǐࢸő͆՜ંĲͮ̚೜ҔŠȐãӐּɔݱฦဘͲјȈ؆ຒဠˡҲϞ¢ࡆۦĀٖ֔͢èɚו۸ѽப̿׆ڱ͕ঙ̢ηূƝଆŝ৪ԻԲġϤޟӲӿऒnჄȉ૤Ŝࠦůఔԛ৮BόʽঐҌബ̈ాঘ̒׾҈ך˰Ƌˤˍ͔ѴըӀùࡺǝ࠸Ѿ౲͚؞֊נʆ௠ŐڐĥĠ̘ݿזګː٥̳ࠣžӇŃɏΆר࠾Цو৚̓ஆՎQτݸࢾҲːWҪңȦۜмਰƲ૜vసʡ݈̱԰ࡏ̀α̊ԩ̶ࠕ"]],
                encodeOffsets: [[[124701, 24980], [112988, 19127]], [[130722, 50955]]]
            }
        }, {
            type: "Feature",
            id: "CIV",
            properties: {name: "Ivory Coast"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ϣUוǒ՟Wহƥ׍ʍ̯ࠫǋvÞۖĄŀ}ͨΣΚˉÈʕɲǾώčО ʔƄB¸ξÝǌĄŜ̸ĶȹڨȗΎæ˸ǘÞŊúɸųٮOƸʖƢgʎĦžΫȞłΌŰϚǽƦ˥Ϙǯ̎ɄϾֺɏɠ஡Ο۷ɕेθܣͧ"],
                encodeOffsets: [[-2924, 5115]]
            }
        }, {
            type: "Feature",
            id: "CMR",
            properties: {name: "Cameroon"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ľ°ӻŇԝŒЋÅ൅nŬڒ͟֊ϧƚǟϖɋŦXɶɎתЎ߸ʒRԄӮ͈bҾΉ־˲ĀΔȌͺžь҆ΊǞךǲȊŢѨɜ՚۾ٲ¬˨ĠƲͫͰˌʂ¶ͮ՟Ê֏֏ҜޅҷTʁÏϥČǻЅӸөμƛŠΏˆ׃ſɩх࡛ȫƳÝٳČΝåʡЈѭð̴̟џϨ˓ϥĘʏÓґڛȤڷɜ੗"],
                encodeOffsets: [[13390, 2322]]
            }
        }, {
            type: "Feature",
            id: "COD",
            properties: {name: "Democratic Republic of the Congo"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@»ঙͶŕˑ̗͓ɟ͍ѫǯϷ±ګț͍OهʍɹԃŗÝýҟɄϡÂ৥ưޝċѧǘӣӤҹҒ੕ͥĒ૿ƙɣĵʇՙȊχƫষĻࡇɨƫט͝ɲƴìٟࣟR·Ҧ̳ΨٟŠȋѰԣ˅ڧŞ˫ϢՕüϽqµʾ́rϥºԳųιtȻû®ৄ˩̸ÕԬŬԒǝ͖eՊ৳Qò̢ѕG­ƣԵɁӧűȿҫŠˣş։å͏Ѱȗ˖ʋԌȷض៛\\̍ķʑh΋œşʼɊĘμƎɎ̪ǰɚđ˼͐ҜSÄʃ̼ƩӶՄӨШɆː۔θࠆϬўքМĪˌt̰Ǝ̆«ӊŀݖǐԾʦ҈¸Ԕúה͜ѐҊ˔۔˷՘ؚ̳ĉظǏʦԖŘÞϦčनоͨǱ˖~ŴȲ̺ðلėբoˤĚԘۙϘķɤƖϲÅҶǲȦΫ݊֏"],
                encodeOffsets: [[31574, 3594]]
            }
        }, {
            type: "Feature",
            id: "COG",
            properties: {name: "Republic of the Congo"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@̿˾ʩƗͻγۏࢸٖҪ̓֌˾ɂ֦ĺäό҆ЗݐʴЈł֒ĝڀЉӺζ঄ȽǘسçɻѢÔξ੘ڸɛڜȣÔҒѰ޲ԆѼ֪Ɨդ±·ԓʥ҇ǏԽĿݕ¬Ӊƍ̅s̯ĩˋփЛϫѝηࠅۓɅˏӧЧӵՃ̻ƪÃʄқT˻͏əĒ"],
                encodeOffsets: [[13308, -4895]]
            }
        }, {
            type: "Feature",
            id: "COL",
            properties: {name: "Colombia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ΫȤЭ˨ʅƅ܉Ŝȱΰƽ_࠿Ӓŕʺ̼ÚтȢ̦иÊΞՆ͐Ѵ̳ȦǄӦȏސǸɚƃ܄ͻ҄ņТ˔ÑǂʠțӶĺŬѢـהΌĚT˦ƺ܂ӖϸՊfäǪڂéڌъ͞ȊОК̖»ɚɛǍ˱գƕɇп͗ʋʓ̷Ĺ׵ɷӭѢÇņϭȄȁâ͹ĳ̵ǫȸéȨ̉ઊĄӦŃעܡͼĚ؂­ӐĪ̔ƟƱҍȇ˯ß׻ǜ֑ʆʟȉэл̨ȃɠ̋ʰ࠹ǁĻǏӸɷˊ˥́࿕lZԿӰē͏ǙĔҿƑK؏ώ̫ƀӓoηϙᘯп҂ʣpժࡤٟϾԍị̈ƤҧɝصŀӵࢤϳɐˍІ֑Њɡā"],
                encodeOffsets: [[-77182, -155]]
            }
        }, {
            type: "Feature",
            id: "CRI",
            properties: {name: "Costa Rica"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@җȆǟǮĬƤȄɷȪͥǔ́ņÅʖəƮÄʑǗȩȓɸˑĊŗǞLʮŎˆʁŠȖǌŴňֆɝȖŊˊéƔǥʜÇȪǲɈҙ͖ͷЂΩ͗õLͷǪűűıƱëǟ©Ǖ"],
                encodeOffsets: [[-84956, 8423]]
            }
        }, {
            type: "Feature",
            id: "CUB",
            properties: {name: "Cuba"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ܨÑڊW߄˹̭ͮ޺Ĩ̔ȡ܈ԳԺϛˢ\\ԆǟÕʁئٌ΅ıȟ֑Ń֡¥׃âளą֜Ҷ΁ɔէÈ̃ʐȥӎӃ޵ɦʥǬભž̋ǐ̀ɀࠗ¨׿ѧΏ[ťȳеğΫĂѺʸǼ̤ϞȈіǎَĄȰĢ"],
                encodeOffsets: [[-84242, 23746]]
            }
        }, {
            type: "Feature",
            id: "-99",
            properties: {name: "Northern Cyprus"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÐJŨȮ؄Yކʢ֧ΧÔƿęǇÙűj¥iĎÑ¾ǋVɫïƿ¬"],
                encodeOffsets: [[33518, 35984]]
            }
        }, {
            type: "Feature",
            id: "CYP",
            properties: {name: "Cyprus"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ãࡱͿЩŊȟͶЎǀ«ɬðǌUÒ½jč¦ŲiǈÚĚ"],
                encodeOffsets: [[34789, 35900]]
            }
        }, {
            type: "Feature",
            id: "CZE",
            properties: {name: "Czech Republic"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ϯǂЁ©ٵʲ̏Ùҿ΅ر˔ӃΰѕȬėΠƧʠؒǾ̸Ⱦ׾ǎɂǆɜīϒĖЊ˓ؼñ¿ɳҘǧŲɒּĥĄʿز»ϮЯʡCŽƯȕÅȑǇ¡wý˹ēϋbšȁ"],
                encodeOffsets: [[17368, 49764]]
            }
        }, {
            type: "Feature",
            id: "DEU",
            properties: {name: "Germany"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@d͗ࡔțS̗ࡢǂҾɰॊͧІˋȞёɹɣ̨̙Ⱥ҅ß́Έ՛ϑĕɛĬɁǅ׽Ǎ̷ȽؑǽƨʟĘΟіȫӄί̑ϯ̟ŃŢշýƛʿǤЕ~׷ƭݍţɛыɺʩ±࣑ʲǥǻ܍Nń״ьֺ௅ƸЇɘ´ςǗȐĨ֨ƗࢢԎ@Ɉ͂Ⱦޔƿ˴ǐǲ۰°Ƽȃ֮вȓ̀ӈٌōՠŸ"],
                encodeOffsets: [[10161, 56303]]
            }
        }, {
            type: "Feature",
            id: "DJI",
            properties: {name: "Djibouti"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ȤʹΑӏȩήɯ̱҇ȅƬȭÏҷb_ʮßɶ˴Ѐ̐ϊήñʪȴ"],
                encodeOffsets: [[44116, 13005]]
            }
        }, {
            type: "Feature",
            id: "DNK",
            properties: {name: "Denmark"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ԋڹ࢟ӄŝΒ௼˨ˎу"], ["@@ȵ̓ʡĞ؁؁ɮХ՟ŷًŎͽҲ}࡬Ɣɪʌʦ݌À̐ɴڮʂѝʟ˙ĶɽҘŵ"]],
                encodeOffsets: [[[12995, 56945]], [[11175, 57814]]]
            }
        }, {
            type: "Feature",
            id: "DOM",
            properties: {name: "Dominican Republic"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ŀƞپIӾɏɜtƴ̕ҠhʡϐЮ̷̯ͿЍǼϫˡ¢ƱƵ͑½ŷȲˣťͳֻɏƆ§ʎjɬɍʦȲƚÞ͒óҜ"],
                encodeOffsets: [[-73433, 20188]]
            }
        }, {
            type: "Feature",
            id: "DZA",
            properties: {name: "Algeria"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ᮩཽᝩ࿷இϑटćU՘ϵƌԹʊȧЀᬻᆴᬻᆴṕᎠfǌ@ÊQ঺ബب࠼Ÿێɦ͎тচͪجӢòϞ̶સƚƸ͜ɛǲ̃ࢲ¹Ԟ́ՠ߰ҠࣦƢՌΎ߶ʰ෎Ƭർæшůߊͨ࣌P΀ȝֺ¾ǟћƄߟȡۙԭҵôمۊԃRȯԮ͹Ϊຝ˖ݏ°ϵƧۇÔϥŃҟòՇͫΗӺؓέ̘ҵϼƸڒϷςՃ"],
                encodeOffsets: [[12288, 24035]]
            }
        }, {
            type: "Feature",
            id: "ECU",
            properties: {name: "Ecuador"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@҂غǻξ͍ϵԉςǞʀƙބ̎ŴƺԼ͆զÍ΄ҢǸ׀Ͱࡀӑƾ`Ȳί܊śʆƆЮ˧άȣŞٓʽճࣷ࢟য়ͧԥܵǃ֣Ӆ΋ΙъͻĞ΍áw̮ʈȨıΔ"],
                encodeOffsets: [[-82229, -3486]]
            }
        }, {
            type: "Feature",
            id: "EGY",
            properties: {name: "Egypt"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɽͷǹىɫѩȝƥ˩˔ϛϒ׵ஸđùΐࢯԪࡋٌವ̴ҙ˒ӃݮछǗƣճ঒ݭƨǣΏ@Ὁ@⁩@@ᶶ@Ჴʥڲɐ԰Żά̤Ж૦b߲ɝ࠲ʛϴſ٨ˊΌʊݎêװŃɮеȜ˜ڨȣټ³аɄւ෽"],
                encodeOffsets: [[35761, 30210]]
            }
        }, {
            type: "Feature",
            id: "ERI",
            properties: {name: "Eritrea"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@˻˖ΉӰϋ˒ɏܷ̄ͶֻXȭǬӯȡԛϢʽط঑ǬęʹβఀĊ֒ˆʴؤƐьӒӦঃɴޗҢУବߏҲӍҖӝˀ˿аʧʩȳέò"],
                encodeOffsets: [[43368, 12844]]
            }
        }, {
            type: "Feature",
            id: "ESP",
            properties: {name: "Spain"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@¦״΃θஒ؆ਊƱ૾NࣂƝۦªമͰ͛໺ϡ̨ǺीϝআŊ®ӥߓ֓ઁǯõ˱ԩү͕ہ͞ӑӟϑǹճىǗש٥੧_ߟhՃ͍̓ͅЩê̵˴ʃӚ޷žé˦̶̀Śɬ̃ʢɶրͳԌδèЈƎŬZپϲɪɻфөƝŁӹCɁЬ΃ū̥ɇ"],
                encodeOffsets: [[-9251, 42886]]
            }
        }, {
            type: "Feature",
            id: "EST",
            properties: {name: "Estonia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ĮӸ̱ŁՓ̘ñӘਫ਼ɼ੔Ũ࣮Ƒࢂ|Ŵƣׯӝʞ޵ΫˉۙDܡ̸ρļ܏Ʃ"],
                encodeOffsets: [[24897, 59181]]
            }
        }, {
            type: "Feature",
            id: "ETH",
            properties: {name: "Ethiopia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ԜϡӰȢȮǫּWܸ͵ɐ̃όˑΊӯ˼˕̏ω˳Ͽàɵ`ʭҸaȮÐȆƫǽ̴̕ҧ̴Й̛͎ᩨঽۺNᛛᡃફݟףաeɯ˅ַB͹˴ލΙʝΓ֕àȃĬȟwˇT੟܌ב@˹ˢ@ҾѧƘӻࣴϥȚƧʹэЦԧÒ˸ӐҀrŲʰ[ݲʞࢠЊɾĎ΄ήٜԔи΀ࠠƆܠ঒ǫʾظ"],
                encodeOffsets: [[38816, 15319]]
            }
        }, {
            type: "Feature",
            id: "FIN",
            properties: {name: "Finland"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ūיಀ֓ޡى঎ख़֡ܛݴس΅յఘֻ́ѓޭӟᅡੵໃá๑̯ൃǯӡҞ߿ˠȈࠢСݶАӪނՆ኎࣮֖Ǭē΢ୟЈ˳͜uಒ಻ֲ૩ЪԊɞतѻલ¦ࣘȭߠϊЬ؞ಬ˶઄ͯΡכ"],
                encodeOffsets: [[29279, 70723]]
            }
        }, {
            type: "Feature",
            id: "FJI",
            properties: {name: "Fiji"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@̂ʍƓѭԳŗҩļąτ͖̀ϤĻȼƐ"], ["@@՛ǯŅ̼оǤˊ°Ӱˀ@ЧՕȷ"], ["@@é­@ШǨĽЗ"]],
                encodeOffsets: [[[182655, -17756]], [[183669, -17204]], [[-184235, -16897]]]
            }
        }, {
            type: "Feature",
            id: "FLK",
            properties: {name: "Falkland Islands"},
            geometry: {type: "Polygon", coordinates: ["@@৘Ԍ܎ȿԌʹڦϙʥ̋ଋʥϙ̌܋ϙпϚ"], encodeOffsets: [[-62668, -53094]]}
        }, {
            type: "Feature",
            id: "FRA",
            properties: {name: "France"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ˣ٭ϡǠș֢ǜ̺ը͎Ɯܛ"], ["@@הЅќà݀ϥȊñʎjЈɗெƷыֹŃ׳ɱƝϣüɇؙҽ]ϟВƀ˾ρʁʚ̿̅ʯɐٱҖŃĩηݿӅစɬ௧˗ĩԑঅŉिϞ̧ǹ໹Ϣͯ͜ѢԎǆူࢁࢤإю౹͒čؖઠǾථɏˇॎߌέዠپʨێܾǞŪ̑ϸ_ϸ͵"]],
                encodeOffsets: [[[9790, 43165]], [[3675, 51589]]]
            }
        }, {
            type: "Feature",
            id: "GAB",
            properties: {name: "Gabon"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ࡹࡔ։ۚԙࢄ˨ǾˎȲؔǜخ˴¶௢SOৠЌÆԞőӼňľ¯ÓνɼѡشèȾǗεঃЊӹĞٿŁ֑ʳЇݏ҅Иãϋ֥Ĺ˽Ɂ̈́֋ٕҩ"],
                encodeOffsets: [[11361, -4074]]
            }
        }, {
            type: "Feature",
            id: "GBR",
            properties: {name: "United Kingdom"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@҉ֽًǦԱ[ǦҊǥ҈۴ࣔԳ"], ["@@࣋ࣧࡦŘऄIɕۅݯݩࢄÃäĕݠ঱ֺƇԬढ़ʈͧৰǅķ՝ѓʗͲѣݱѯ૳Rෝɱϻǒ։ϿޥĪם͍ҁǘ௼ࢨݪǺOBಽƔʃͰ࢜ʺҡҐǆռఢ÷D@ŮӤ֛Ԯ_\\৵ƨȧɬ̨ϒˡɴҍЇ·߶щє̨ࢆٶھڤá০ì"]],
                encodeOffsets: [[[-5797, 55864]], [[-3077, 60043]]]
            }
        }, {
            type: "Feature",
            id: "GEO",
            properties: {name: "Georgia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ųάȿִӟ̲ҭĬ̯ʴĺĲ܄ƝఆƋଦЕƦƻԚƂ޶ǭʴ·Նșɓřвғŗıҏºصʎȵƍଢ଼ſ߳Юࣅ¡"],
                encodeOffsets: [[42552, 42533]]
            }
        }, {
            type: "Feature",
            id: "GHA",
            properties: {name: "Ghana"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@೉ӯҳ˽ݳʑݡʆͨηܤɖैΠ۸ɟ஢ŗنrӊฤ¢ϊÕ˔ƊϴáÕʿΖџC؍Ąڍɂ̫ȅݳäйɢՓȈ̍"],
                encodeOffsets: [[1086, 6072]]
            }
        }, {
            type: "Feature",
            id: "GIN",
            properties: {name: "Guinea"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ʃtǡͷʁJǏǴÈͶΗԨɕħǵmɳ³V̮ƇɘʔǻΜɹ̜ڥDțǁɵoƝǷīɹ҅σρӼ͛͢ɋŊȿǖħϊūȂʓƐώЦʮeɖƘȄDƄŎï˨ĢĖd˶МU؀ȱȄlÚĤҜáŨ´¶̭ƆBɖŒƔɸɇάãɲǺ˖ŒȬŠǚuȈȁĴɳΆΙǣɏ˙ǴĊŀį«ʡʲʍǗÝå˷Ș΍Ⱥڧ̷ĵăśÞǋ·νƃA"],
                encodeOffsets: [[-8641, 7871]]
            }
        }, {
            type: "Feature",
            id: "GMB",
            properties: {name: "Gambia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ņόࣶzȎȦˊ`ͨȷʼIˢƚǞʏεȋιdέǰ̷ȗƭQȫŝއl"],
                encodeOffsets: [[-17245, 13468]]
            }
        }, {
            type: "Feature",
            id: "GNB",
            properties: {name: "Guinea Bissau"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@҅ΘΝÈȕʀLŸʯǴÁǶѼƌ˦ɦĨ༈c˵ġĕð˧ƃōȃCɕƗʭfύХ"],
                encodeOffsets: [[-15493, 11306]]
            }
        }, {
            type: "Feature",
            id: "GNQ",
            properties: {name: "Equatorial Guinea"},
            geometry: {type: "Polygon", coordinates: ["@@ƿŴ़̀െmPয়௡T˳µ"], encodeOffsets: [[9721, 1035]]}
        }, {
            type: "Feature",
            id: "GRC",
            properties: {name: "Greece"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@Ҡ˱ٺ¶شÑqƣҜĶĿʛ௃íTƒਁǎƺΦ"], ["@@ʹՁȥĥԟ|ѫĀৱɓ׌ҿяƋҳAѻўƿȁȊԅрЁ̓ǿҴϯжʑ^ӅޥɠʜѕՓĕ͈ݏ֏Yۍμ̿ڦƧ֒͝ϮљӐÉʆϸТ¼˚˘Ũjɚռö͌ȀҖgƒƦǆت{ڨɲע̉ކĀVмЦɝ"]],
                encodeOffsets: [[[24269, 36562]], [[27243, 42560]]]
            }
        }, {
            type: "Feature",
            id: "GRL",
            properties: {name: "Greenland"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ᬜԆ᱒ੴ̴ᲈĄ䀦Ŀ㉊ڗ༅͕ộ⭏ćшƫᲐĠᡚ́࿈ʴۦ̝इӧᒞ̺✘͚ᠼǋҾΫ⃝ױӃȕ᧑ơወ¡ছؕگկध৚շಽ൧ˇ༂ѽȢ܋࣍ýઞܡህÑঈ΁˟̑இŽ୥E੆֩\\Ϗပΐћɣଌȿ઼ԣ͈ڱກǉ٫͖ਣӘ˼֭উѵᕖ୆¯ᖯܵᗿڏឧ́ओIࢅ͓ୟࢱᅵכׅ૧ȷ஽ȝܛԱ[כыտോڧͺٿϗ۝љࠍஅ½఍ۈဿLࠁҢ֕ࠐฝਲэոŗݮ୓ޢ̢ئ֗̒ࠪচొ̺ͨΘǬڀॡ̕қůݯţਏ˜Éְ͢҂ެ\\႔ɟ෿Քݩ˾࠷ş۫ȼम޴ԝ̺ڗ׈ৡࢼ੯͚XΚᖷӮᄻÖᖟᏅ×ইˌวՈᕂ˄ၚ¬≹ɖ቉΄Ś͜ẊИᶎИ̪͘ᗗ̠ܺͰ᯲ז௢ĚΓϘጲɜᣚƂᣖRࣺʽᕺҨፘ̽୺áპ˙ፅҐŘή"],
                encodeOffsets: [[-47886, 84612]]
            }
        }, {
            type: "Feature",
            id: "GTM",
            properties: {name: "Guatemala"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ћƦԻfϩǖҍΌrʖĮȠšƾКۆ઄Ft˸Ƌ¾ġǺ̵Ț̹ˬϜDBӂ޸BަUOڗßॅʤ@˚ƱòŰʘŃϥ͍ЉɻÏǉâǑǧɇȟ½¬ıƿġ˽Ƀ}ŭ"],
                encodeOffsets: [[-92257, 14065]]
            }
        }, {
            type: "Feature",
            id: "GUF",
            properties: {name: "French Guiana"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@͉͑ГÑŗʀȉʹɩνǦɈΪòϤƢή͛ӸáֺѪܠ˸ğؤȥࢸۿƔ·ӻޑʳأ"],
                encodeOffsets: [[-53817, 2565]]
            }
        }, {
            type: "Feature",
            id: "GUY",
            properties: {name: "Guyana"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ր̯Դյzџ̈́o҈Чͪ̇Ƈݱԛɕ°ȣƹџϊ؏ːAŎӃԢܳȱҫî˙ɡϟƥ˅ġǑЭ¦ԫЀÓϴɋьƆܐɸ̐ȕϸ˿ŶŊτțȘѩْ֩ɬɲiϲԬƊȾƾ˽̸ô̬ږӲ"],
                encodeOffsets: [[-61192, 8568]]
            }
        }, {
            type: "Feature",
            id: "HND",
            properties: {name: "Honduras"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ơˀʭòÐʹŗĞǣÒσĳŔʩƈǷǚʛìǨɈáǒÐǊЊɼϦ͎ĔȂƨʊ\\þåž¦ϸùϲv˒ĢİĦˎ©ȪÉɘnǖòϨśƄkʲƿʐį̏Źɜɳ˽jśŕ̇ŋɃAȅŃǙƛźĕ{ŇȩăRaǥ̉ɳƹıđĽʛǞǹɣǫPȟqlЭūQĿȓʽ"],
                encodeOffsets: [[-89412, 13297]]
            }
        }, {
            type: "Feature",
            id: "HRV",
            properties: {name: "Croatia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ȳ͗ˊʇ͓̓ϝȆׇ[ܟƔϽmǻǧ̝ȖǫΑЪϽǼʹϮ̽͌ȃ͆Ηݔ͇ġƛ߃̶ӣ̢ޑʠ۹ؤǞØϥΞe˲եƄʱγʝˮn̆bגƸƚ˸ƍͤgGɼ̈ĒĈͺڞɠˊĻؼέۜǉ̼Ų"],
                encodeOffsets: [[19282, 47011]]
            }
        }, {
            type: "Feature",
            id: "HTI",
            properties: {name: "Haiti"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ԢܰƁôқÝ͑ȱƙɎʥiɫ֏ƜЅÍԡÔϽƿ҉ʾö˔ޜśيã̢ȈϧθP͎ՋžȌɶ"],
                encodeOffsets: [[-74946, 20394]]
            }
        }, {
            type: "Feature",
            id: "HUN",
            properties: {name: "Hungary"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@˨ըǍǼӂDÜ΄ђɋ̲ğ۸ļäǚͮ~ЦžĜÃЂŀȠȢˠ¼࣒ʭǴĒҲɭÎɣԡǭЉ֫ԕ֭کǁԽ١ə̻űۛǊػήˉļǍ˴ƗV"],
                encodeOffsets: [[16592, 47977]]
            }
        }, {
            type: "Feature",
            id: "IDN",
            properties: {name: "Indonesia"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@Λe૝ך޴ǒѴʭ̎ʭ»ɩ"], ["@@ܙȁĳĶø˸ΰԢࠨͬĐǓfʫշع"], ["@@̢ɣԲèȼΥॿǛ׉őҍP̀ӚҤPɤ̖"], ["@@ūұ౅ʅૣľE̬ښǪՂʥ֔Üݬ̮"], ["@@ྔċȂΌ༘З̪կీƵਐӿय़͋ऍ͸ݻwࢍØ޻ưঅ͎؝ČΓŁ໕ΌƣΰޑØּߤ৶·ڴ͡ΒÛŘ̗"], ["@@ѝֱćنƬ̠Ǭ˴ȒʗCЏ"], ["@@̿˥ׅƸǏΰࡘ¢Ⱦˣ"], ["@@̨ٝۿΌۯìӃÅׇȦҦਠऎʕ"], ["@@ɼയ࢈ԉ۰ࢼ८ԔݜBܘ̉خ̛ࣘǇbᩑbᩑݟې࡟ǜȷʇ੡}ΦۂՈɺɕࣲЕ۸࿃܆ۗêృަʛУ͑óȏ̮GκٛЮ̢ࣞ״gëɠ௵DͩԄݥƺΡдଈȰњ˜ഘ·Ƃ̹"], ["@@ڭ࠭كǉ߱ǐඓ¥ܽŧţٍݪݛҒϠ༪˸çϯλŪιӯ͙݉ߒ੿Ƶ˿ݲॻQտ҅ʙ̐͡Мی࠙͗ȻɶŊ͖؅ӲØࠌ֕ʭîওறՓũίʚʌޜŽ߸ΛPʻֺΎվŤښф౎ǮΎ܎ذپʛ੖śॴࠨ؎Ʀȉ"], ["@@©ܽџĈŷԝΌѷɽĵ͹Ւʟ੺ǚڤ˨̨ÔҝӸóĀ΃"], ["@@सާহį˫ֵݿַ߱u࠷͕౻ŭ̚ॕϙͫԤ׳´лːৃ̟̩Оս¯ۗĬŹૺнɺЕܘŝ݀ĮުԂ֐Ɩָ֗ӅըǠ՜ÑӪъЖôߒɽۆǶњୠ͔̈̆क़ॲ@ܰƙӍݷآߓơϭ"], ["@@छkۻ۰અۊέԚٍۄзؾٕ୴۪݅ʙܠ̳ڀݵՊѭܘمҺࢗऒóђզಢǋݔࠓٮ֫ҪΓߔࣙࡢ_ۺֹӠ۳٘ϥͳۉӖ̞̅sƜו̊ҵؠõФՏɁ਱ಟ"]],
                encodeOffsets: [[[123613, -10485]], [[127423, -10383]], [[120730, -8289]], [[125854, -8288]], [[111231, -6940]], [[137959, -6363]], [[130304, -3542]], [[133603, -3168]], [[137363, -1179]], [[128247, 1454]], [[131777, 1160]], [[120705, 1872]], [[108358, -5992]]]
            }
        }, {
            type: "Feature",
            id: "IND",
            properties: {name: "India"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ࣚটďۅͮїѕ׽ŒɾएࠜՑ୞חՑϟ͛޻ࠀͅߊЭરһସŉӜёٮāৠȝ۪bĪͪŋՖÞβԠǮìڋlǙކ͉Ոƀ܀Çۈ|ÐԪ΁ˎڴŀވشॸ՘۶ȷ״ΞЀԹ˳Λ࣠űÜ͇̍Ʒèԫ׷Ʋછׅ~ӓҩ۵§ХϏۗځȒࢇȏ˹ĚΣгȥѵ೰ɵEƍ՝ҡѦʸӎϖ¶ϰ܆ӝƜީ]ߝŚóאБ¤ڕζ֭̓؆ѻԿ̻ȅ̩Ԭɣƛԑ̆كžەţֱ̫Zਛǩ´ك҃ӻ௃֡ळ঩كՋ࠷ջCϭлȹݳ̝Ͻ«ʥٙǪધ®ۡΣߙI෗ѣ¡ϣٙʰˣދʃ˱֯͵ʍߑ޸ϳ୴͑ࡒ̍Јѿ߰ȻੂơՀޅ଼Α࿀ʣ੾HৰǍ޾௣ԉףĶ઱৲И̤ʝͤড܊֖֔ᇜCǗܞҽюĩ٨ջϘऒࢢঊÙ࢞ࢢՄ࡞ࠄࡈ_״ܒӠڳд֪݂̇̕Ьβ౤ȱपŰߺ۸"],
                encodeOffsets: [[79706, 36346]]
            }
        }, {
            type: "Feature",
            id: "IRL",
            properties: {name: "Ireland"},
            geometry: {type: "Polygon", coordinates: ["@@ƒ׷ًݣ๯ӹ஑Ŷڼ࢚ѭࡢତڄٌϼǦ҇ǥ҉Բ\\ٌǥ"], encodeOffsets: [[-6346, 55161]]}
        }, {
            type: "Feature",
            id: "IRN",
            properties: {name: "Iran"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@݈ǌװӔ֚{τƾװýघэڤğ।ݓظòۻ΁਷ɱؑκŭΫҡˠڡàՓِƙæեݿݿжѵ͸ԓߦυx݉ДƋêϯ௉ѡ̓উཌྷʪࣷȖेŊΧਐЕƪ٣ƭࡑНਇ˦ࡑ٦߳ʈ֗ߘا૪ҍƋՕ˦̻͝ҭѴS҂ˍ@Ɛ،ѝٔ਍Ң׉ߜȜپц̂ÙӬտʨխ৊ҟڨǐʼʿ६ּʈƄͅъϯ־ő̤~রئ̀Øʞʙ́гԼѱȾ¦ˈإߖǩ׎у஠ƟಾɞĄȞ"],
                encodeOffsets: [[55216, 38092]]
            }
        }, {
            type: "Feature",
            id: "IRQ",
            properties: {name: "Iraq"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@րʧÚӫх́țٽ׊ߛ਎ҡўٓƏ؋ˎ@TҁҮѳӿ¤֟ê؝߭༟äᛍၖఫךৡɪ͹৾ᇶ࢔͆৬āؘҢȺјԾΰž঎Ň̐ɉЖƚծ৉"],
                encodeOffsets: [[46511, 36842]]
            }
        }, {
            type: "Feature",
            id: "ISL",
            properties: {name: "Iceland"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@șիॊֵથٙᝓֹܣƵૉŮᚑˈࠠψᆧЪ๪ǎʘᄋȜ֨նౠŰಸ֭౨Ҝ੒ʃൌ҄ආÑ"],
                encodeOffsets: [[-14856, 68051]]
            }
        }, {
            type: "Feature",
            id: "ISR",
            properties: {name: "Israel"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƥ˅̣Ŝǫ֓ɂĥɋřɛЄŖp͛нഉց෾ʔˢË¶ɞϼǠيŤɆzVˬCþƦɤ\\`·ŕŵhM"],
                encodeOffsets: [[36578, 33495]]
            }
        }, {
            type: "Feature",
            id: "ITA",
            properties: {name: "Italy"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@̟ڋŲʹǭѝٝ̈́ёĞ୩ѐŞќজûࡪĠْò"], ["@@Ԍ׭ş૕ϣÂ΁˫͇ɞ২ȓӒҨ¥рʼ"], ["@@ரɏĝЯȬΧڝŪہ̗²зĻʇˠё߀чцۛदڱچLȲȃɽǗݪ̥ؠʩܜѫĔƿƽ̛үϼܳƐΝի؈̷ıѫΗ¹҅ܛΕÝHʲǢҊǼǶ͝ӤʱшΑŀʛδգƴεͶثÆٿϜޑմ֯ӜʿࠪйĮہˤϯŕӝϵΓÕĪθҕńɏٲ̆ʰʙ̀ʂβǵМ¢Ҽ˶ƢƃАǼͺتĿψƚâΆԘšĮǆࠨƤȊ̉"]],
                encodeOffsets: [[[15893, 39149]], [[9432, 42200]], [[12674, 47890]]]
            }
        }, {
            type: "Feature",
            id: "JAM",
            properties: {name: "Jamaica"},
            geometry: {type: "Polygon", coordinates: ["@@֢÷ҀȫƔɯןeʭƗҹƊӑ̪ĶȔΜÎȒ"], encodeOffsets: [[-79431, 18935]]}
        }, {
            type: "Feature",
            id: "JOR",
            properties: {name: "Jordan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ʀˆपͫ࿪ࣆͺ৽ǅų၅у࠸࠿ˣƛƑ˭ٙřȩ̡εʵधƆŨоഊo͜Ůʚ@Ԥ"],
                encodeOffsets: [[36399, 33172]]
            }
        }, {
            type: "Feature",
            id: "JPN",
            properties: {name: "Japan"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ņ˽ҿԕΉːљțɝӭշʈRЊҬԆӌīΊΜؠǹ"], ["@@́ڡƤсѩף੹Ѓ๏½ணॡ͔֡غษȃষЃঝe࡞أ֗෗իΝН͜ȶݶՏʒͿ־ߐʶѲՈࡌѢ؞ָာʤ࣎ǣࢠ๺֔Б௾ࡀӌ͜ՈਈƟा΢ՎࣀƸҞୗ}ڻޥࡍbࢁ"], ["@@נǵרΤȈहఝɯ݁࠱೓ָқँण]ř࠴д٨࣌²ʖ୐ʜټন࢓٤˯"]],
                encodeOffsets: [[[137870, 34969]], [[144360, 38034]], [[147365, 45235]]]
            }
        }, {
            type: "Feature",
            id: "KAZ",
            properties: {name: "Kazakhstan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ӕƹ્דο׹̹KɱЊ੫ǡێХNÚࡆ৓ؘ෷ßডũߣݶۋ͆ಥ׼ƽðᓗӹᶽљ£יچ֧ɼॕǩχ˧±ȲȶΖǅ̊অ˺ϛݮҩɆ˜ࠊāؘ܎ƎܼűƲࠎƭԲ࠿£܍ȴঃσ޵ǭяƌĐўՙ֘دw܉֬ӞِʕǢڢऊࡺӣŀؘჄࣴಾtᇢ׉঺ͻࢼΠ೰j੺ѥʔʠ୼ɂЊഷ׀߮Цƿɮ߮ɔ؅ֺϬ˼Ḯ̈ШȺᑆ̴ݰΒຢǹ˄ࢉ࢚Ȳઆ˹éҝ߮´ᑌߎ̭ˁ੶٭ሠᒑ҄ѰୄӛீɎҪƯКӟטǋΨΥ઎ŒѾԣٕ֓ۥÿ¡ࡅұϝဟˢ؅ຑїȇဗͱݲลֻɓäӏԭŬу̠ఝĖඃx̧ġ஥ΞӉǧŽӹ൩̂փşȉρ"],
                encodeOffsets: [[72666, 43281]]
            }
        }, {
            type: "Feature",
            id: "KEN",
            properties: {name: "Kenya"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ӾۙיͱȹΕ̿Õšףˑ͹Ǐ֑ͷ˥஻ࡀËӤᵁႌƙĢSࢺʊ;а֌̨ؔσ॰įтЉ׎ԬԈ֬ֆѨƗ@ҽ˺ˡג@੠܋ˈSȠxȄī֖ßʞΔގΚͺ˳ָAܽ॑Xᵣ"],
                encodeOffsets: [[41977, -878]]
            }
        }, {
            type: "Feature",
            id: "KGZ",
            properties: {name: "Kyrgyzstan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ȊςքŠ൪́žӺӊǨ஦Ν̨Ģ඄wఞĕф̟Ԯūşȏ೛ғ̙ͭઁıͅ՛ࢷŒׇǏߣЇŜȟʇȓཟŵਡ˘࣫ÝĂӜࣴƕ̮ʸٖĉ੾؂঻ѸױȽإ͂۶ծʟĊ"],
                encodeOffsets: [[72666, 43281]]
            }
        }, {
            type: "Feature",
            id: "KHM",
            properties: {name: "Cambodia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@΁Ѭыࢄȣ২ՠۨઘǆ߀ťۚ͡Ϟׄݖ̱Ȝ֕Ļ৕ඳ٧τԙࢥÓܫͷ۱Ū"],
                encodeOffsets: [[105982, 10888]]
            }
        }, {
            type: "Feature",
            id: "KOR",
            properties: {name: "South Korea"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ܨযȺխPॷ̓ҥݽǉڥΏݳïĥҚƼـχ࢔ذƚֻܘÂúϒ͞Ϝצ¢ΨÈŨȮ"],
                encodeOffsets: [[131431, 39539]]
            }
        }, {
            type: "Feature",
            id: "CS-KM",
            properties: {name: "Kosovo"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ǣŃPĘ́ȩĐǳɦƾȌȪÒŜ˨ư²Ţşƾ¿ŌƅƒǎƻŢLĥȳĳĳ×ȉӹŻ"],
                encodeOffsets: [[21261, 43062]]
            }
        }, {
            type: "Feature",
            id: "KWT",
            properties: {name: "Kuwait"},
            geometry: {type: "Polygon", coordinates: ["@@Ǭχõȓ˔هשuȽАݟĆ؞߮֠é"], encodeOffsets: [[49126, 30696]]}
        }, {
            type: "Feature",
            id: "LAO",
            properties: {name: "Laos"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@˚Ϝ܆ڹܸ¿ٕࠦھٍÎǛ̉ӯyʣƨࢯԅoݬȸࢮ֧³ԎηʸǴ̲ܐնøȡ҄wŵ०ѦŬӮڏϖޅਚO͚ܹ՝ɗʉ̟৔ԉۦ঳Ռ݋َ׏ɄץƵ࠿ݕ̲ϝ׃ۙ͢"],
                encodeOffsets: [[107745, 14616]]
            }
        }, {
            type: "Feature",
            id: "LBN",
            properties: {name: "Lebanon"},
            geometry: {type: "Polygon", coordinates: ["@@ɣ[ýƥ˫D̘ۄмעfϘ§Ɛͣқ̓ȷҟ"], encodeOffsets: [[36681, 34077]]}
        }, {
            type: "Feature",
            id: "LBR",
            properties: {name: "Liberia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɗQࡽАޅٖ܏Ң֣ըȪː¬ʔϜҘϺϺǶnɖĨΘԧÇ͵ǐǳʂIǢ͸ʄsʓĎНǽύʖɱˊÇΤΙ~ͧăĿÝە"],
                encodeOffsets: [[-7897, 4470]]
            }
        }, {
            type: "Feature",
            id: "LBY",
            properties: {name: "Libya"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ק̷ҿҤ೧βρՄڑϸϻƷ̗ҶήӹؔͬΘñՈńҠÓϦƨۈ¯϶˕ݐШȜðΠėΒ־͔ʶːЦʌ´٦দ́ΜðۮƓ૞ϓЀݛݮǍஆΙࣆйЦɔЖϮț٠˂Ф؄ЀׂŘ଒ǣ˺ϑ̺Iˌƛ࠴ıȲˣ̣ЕżΫɏԯʦڱ@Ჳ@ᶵ@့ॱGYΙ‧ྐ‧ྒࡓҟ"],
                encodeOffsets: [[15208, 23412]]
            }
        }, {
            type: "Feature",
            id: "LKA",
            properties: {name: "Sri Lanka"},
            geometry: {type: "Polygon", coordinates: ["@@ų࢓ΙʇܵȓЍڜƫீϠ഼׆ұϺסО࢓"], encodeOffsets: [[83751, 7704]]}
        }, {
            type: "Feature",
            id: "LSO",
            properties: {name: "Lesotho"},
            geometry: {type: "Polygon", coordinates: ["@@̆ʩʳУƛ˛ҳſƹˍ̛ċؿ٨҄ՐҖ͢ϼǠξʵ"], encodeOffsets: [[29674, -29650]]}
        }, {
            type: "Feature",
            id: "LTU",
            properties: {name: "Lithuania"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ãɊĚɲχƄࢡƨǱ۸२ʴඬÁࠜĊŞǩ҂Ã߲СĀϓۏˏșӃ࣯̓߻NȫʶљĜ"],
                encodeOffsets: [[23277, 55632]]
            }
        }, {
            type: "Feature",
            id: "LUX",
            properties: {name: "Luxembourg"},
            geometry: {type: "Polygon", coordinates: ["@@ǘȏ³ρʍiȉòĞҼɖ"], encodeOffsets: [[6189, 51332]]}
        }, {
            type: "Feature",
            id: "LVA",
            properties: {name: "Latvia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@نЮՆߊ˼ڜعڪhǊ٤ܐƪςĻܢ̷ۚCКȕîС˒ӷ͕ࣗԛƙ߱ТҁÄŝǪࠛĉණÂ१ʳ"],
                encodeOffsets: [[21562, 57376]]
            }
        }, {
            type: "Feature",
            id: "MAR",
            properties: {name: "Morocco"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ԒΥߜÎࢊȃκU͂՟ºԝ̄ࢱɜǱƷ͛ષƙϝ̵ӡñثঙ͍ͩсۍɥ࠻ŷഫاRহŷ@@@p҉Ա˓ȑϡ@̥Ŋ۹ě˛ٻʿÕЁ੕ୟ࣡ˣୋ΅ϗĵ̡ቅãaD ϶͒ɮ˞ѪÃ˶̀פҴՖ˲ƊɞӬp҂̤Բ̪֔Ւ࡬f\\ц͔ްĢڎָтɠۮۮȿਸ਼͊ܢŔѶդ֨ࡈϦخΐ֘࢈˄ԪؤI"],
                encodeOffsets: [[-5318, 36614]]
            }
        }, {
            type: "Feature",
            id: "MDA",
            properties: {name: "Moldova"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ȨŮ֒ĊؤʽΊϞɥÑ˵̪ƏŨΗ̊ɇÏűƾčɝ×ӷ|ĉŜǫãÒƭɱˍƥ˽ɁĝƯϦĘΪςӝԂˉΠʹʠʯĈ"],
                encodeOffsets: [[27259, 49379]]
            }
        }, {
            type: "Feature",
            id: "MDG",
            properties: {name: "Madagascar"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɠΥȺ։Ɗঢ়ɒϽĉЗƩʙ˷ӰǁʝǈثõΥɵȗ¿܅ͧওб୅ԯཧ͑ୟϛইہȣܻΡӛɊڙ̜ɳѺÇݘ̑ڠù؂Ʈ؄ϰƢD˪Дِø՚șЈǃՌãޠ̊ҺŔՒмҶǤ̶Ʋτ\\ӐӎۖԮʦцŗάΦĵҪ׎fԐ˦ϔ̊ί"],
                encodeOffsets: [[50733, -12769]]
            }
        }, {
            type: "Feature",
            id: "MEX",
            properties: {name: "Mexico"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@͙݅ƥ؁Õ૷ąЧƤқʺЧǚٳ֎سȞӏ͢бࢾɝΐΙ݄ɾٚĎؼưՊƠՖ΂ȨӬè۸Ƣʖ֬ɚࢶȚݔԚîȬǱЙҋԁȥԝƸƥűγɁٽɅɎǭcǃY̝ԓƳĲķPŭޥV޷AAӁϛC̺˫̶șĢǹƌ½s˷ઃEЙۅŢƽĭȟqʕ्ࣞџ˘ۇɖҷÓګ́чĉץɜؿǄ޹ϬؿŠ्ϸ۱ВɃɤҹº࡯ˈΓϦࣗӊсՌȧЦ˪ĈđʈȖɔJ̄˱Ϙùͮ˭ъ݋࠴ࡋڀУԼܝ΄ƷȴŸԲѓȞӹФȽהҍæӣѸϿФˀҍو̓٠^͔؇ͬ˫ӑɴƇͿƔЕĆف̀΋خׁƒȡŸÓŎ˽Ƭ\\ǜթʮɇǴ̕Նё˨ޯʠρɸϿ²ѷКͶϡ̨ϑqƭΝ̱ƫJɛԞջӎ؃РїɈؚŵҖЏʺֿϒŏŇɃɖԭȰӷӦÖÚΊ³̸̼Ϝ٩׶ӱɶ̱Հ̷վϳڦͿݲॖÞ੪ĞÿǑ౔СኀףဪPژ@DΌผ@̪̕јˇԀσ˨ѭȾҥѢʩۤʥՊڒۊhפͱфֹ̄ӯӸӏȂחɾЃپʹ׮ȁ͞|"],
                encodeOffsets: [[-99471, 26491]]
            }
        }, {
            type: "Feature",
            id: "MKD",
            properties: {name: "Macedonia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ńOǤӺżȊ˺¶ϴbтˏÒ։ǅƑƥҕh͋ǿջõΑȴšήń˸"],
                encodeOffsets: [[21085, 42860]]
            }
        }, {
            type: "Feature",
            id: "MLI",
            properties: {name: "Mali"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@˰ƶƘӶˊpזɻӄǖ͖ÇŴȈ⁚^ȈךƣļЛ⋈Л⋆౾dᬼᆳᬼᆳȨϿԺʉ϶ƋV՗ठĈFካҟ֗íԭݛƃ଩ï̳̗ա՟Iȿǈҥš޻ΑǅʿٳϕŗɍΙǡНŔɱȳūֻڙۡp˳ɭΣÆӥ΋ůȝŁŽάʍĥơhƷʕ٭PɷŴŉùʱʎ¬ʢĿİǳĉ˚Ǥɐ΅ΚĳɴȇȂǙvȫş˕őɱǹΫäɷɈƓɕőƅAµ̮ʾí̽͘ʀǓӔԺ"],
                encodeOffsets: [[-12462, 14968]]
            }
        }, {
            type: "Feature",
            id: "MMR",
            properties: {name: "Myanmar"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ӫηץϥࣥΟƳО݅ՔؗΈօ̭ܵ̃ƹȪу֖ڙĪҷ_ϵ͠ދң޵Сࡷăذʴ٠˯ӼæࣸͽѤ˛৔Ʊਗ਼εۢօуॕ׳ҽöԳȠ̂ਪǫ޾څॺļ̢ӭņ׭ۆÅڰ̊ŵj׾дȦęΤȐ˺࢈ڂȑϐۘ¨ЦҪ۶}Ӕજ׆׸ƱçԬ̎ƸÛ͈ӮÚˮӵξȧ|ٟۙߓۭĳঽࢲƔȨޛՐǍʓۣز́ζƷ؞ʔ~΍܏յǳ̱ӓȗ"],
                encodeOffsets: [[101933, 20672]]
            }
        }, {
            type: "Feature",
            id: "MNE",
            properties: {name: "Montenegro"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÁǀηЯÊˋǫÞɽ˞εǖĢƜŬҦ˚ȜƾüɠƟŬśˠě͌ǧçïƽȋɧó"],
                encodeOffsets: [[20277, 43521]]
            }
        }, {
            type: "Feature",
            id: "MNG",
            properties: {name: "Mongolia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ࢮƢ྄ܤ౬Єܴʳ࢚]֘Ͻ࠼ௐɁࠈגͿӶࢊࢊश΍ނįনɍǈؿஜΛߐƺਫ਼ŌࡆōࠖЗԚѕެT੒Ƌޜȼૈƒ௸פԌĝѰ˭ৌêХهק࠽ɐ΅ӈńࠤŽ٦̴ڬˏހוğ̗ڏĦ௟ŏןʅ؝։౱͙࠷ѽࡹǞҿúѳէˎ͓ƌˣי˯׽҇গ̑ఽഫ̇এҋϋʾ৭AఓԜࠥŰૣśჃȊऑmӱԀϣޠԱĢ৩ԼଅŞুƞ̡θ͖চׅڲன̀۷Ѿəז"],
                encodeOffsets: [[89858, 50481]]
            }
        }, {
            type: "Feature",
            id: "MOZ",
            properties: {name: "Mozambique"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@لæ৞ʁɖńגt̚ʦԌaऀ͜ڞӤƊϕ࠷ľ݅ಿƨЫʣ׷͙׍՗Եޏ͉ृСॉ͓ࣕƵוׯ΋ȗí׳ЌُǔӱZʣƪ¦{ࠗƋϷȤƝűΓΗ̗ۗ˳য়ҕρ̳ðΟɊÉíѵّRïϊůϖí̠ƬपɓװГஂࢬ॔ɜ؆ŶúĨӶƉʞغǐ׌E੠ѥ˒ЏÔǹȼϳǰ۫gÅ̼āװᢈۘӚЕɴüͨɅ¸͵ǯϷØסոԱʲ׌ζǰíઊΙ؈̣˖̅]ɽદɾٔ"],
                encodeOffsets: [[35390, -11796]]
            }
        }, {
            type: "Feature",
            id: "MRT",
            properties: {name: "Mauritania"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@և־ԗؤ֍ɞГʚҵUЧǽйð˽ˏïҐɺаŀߊģࠨĵкČмɑЎѵδǾˬᾔMǃ௎ȴќ߀øᒸ᪂©F౞Ṗ᎟౽cМ⋅М⋇ƤĻȇי⁙]ųȇ͕ÈӃǕוɼˉoƗӵ˯Ƶ"],
                encodeOffsets: [[-12462, 14968]]
            }
        }, {
            type: "Feature",
            id: "MWI",
            properties: {name: "Malawi"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɽٓɾથ̆^̤˕Κ؇îઉεǯʱ׋շԲ×עǰϸ·ͶͧɆɳûәЖѵɔʮޮ˄̈Ǉۢǚڼƞɪɉ܌Ѕϐ࠘ƽǜɵ˶Ϲɾଡ"],
                encodeOffsets: [[35390, -11796]]
            }
        }, {
            type: "Feature",
            id: "MYS",
            properties: {name: "Malaysia"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@àћֈĶ˞ΈȘýӸԓΜ֛¶֣ęϡĆ˿Öӻ̒ɵͤݑe˳׫Éߑخ঵ښįђӟ֚ś̡۠ҜĠؔȃΤƤƮۈρ"], ["@@أ˹ܯƚॱ@̅ॗ͓̇љୟۅǵߑɾЕóөщ՛Òէǟַӆƕ֘؜˽ٮǀǜ܆άǂ৖Ǻ׾ڔЬՐϦѥǮ˺В¸՜а٪אшڀͼHќыιֆɻ۬ʧÑ֝͡¥ƮЧ"]],
                encodeOffsets: [[[103502, 6354]], [[121466, 4586]]]
            }
        }, {
            type: "Feature",
            id: "NAM",
            properties: {name: "Namibia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@رٌؖ͡ȃࠊȷ،˯ಒm৒ŅҞ͛Όѡۜѳ৘ǽՆۃࠐ»٢КǆԊƞհ}ԄϝŶÐ₮׌Е᎞ş໴΂یȒհµͨȍPéӁȍʭC՛͍ͣΎಕ̍س{ᲽࠣBយA᷋ݣѕҋÕՇǄϗÔƗάͩɰГг"],
                encodeOffsets: [[16738, -29262]]
            }
        }, {
            type: "Feature",
            id: "NCL",
            properties: {name: "New Caledonia"},
            geometry: {type: "Polygon", coordinates: ["@@ېԵѨϭ͉ȫҥɪ׹ϚէѼ։פś˶β[Һ˹φ˷ˎɻ"], encodeOffsets: [[169759, -21585]]}
        }, {
            type: "Feature",
            id: "NER",
            properties: {name: "Niger"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nּॹȐОҿպœϤâТբ̴̘ପðݜƄîԮҠ֘Eኬஈϒᝪ࿸᮪ཾ೨αӀңר̸ȸಯ̾ɓ`ˋΔ˽ǻί͕ၻ«ધੳߋγૉΔ̵CեբmčЃʁµˋƻm֩ंȟځҷٱʔҍ¸ʏşӯ~ӷΧѓq৯ѢЉȵѓb̿͆ࡅ̼ࣗıɕǻşӗʋ͹ÍݣٗӚ̟E˭ʗ"],
                encodeOffsets: [[2207, 12227]]
            }
        }, {
            type: "Feature",
            id: "NGA",
            properties: {name: "Nigeria"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ࢍ̡͉¬͓ȉڥl҇Ղˡ؊שֆكYݍB¶തs՘ǂՊʶʴТԴėɨǔ͸ȍӾ˪ÎݤʌͺŠӘɖǼࣘĲࡆ̻̀ͅєaЊȶৰѡєrӸΨӰ}ʐŠҎ·ٲʓڂҸȠ֪ँƼnͬͯğƱ«˧۽ٱɛՙšѧǱȉǝי҅ΉŽыȋ͹ÿΓֽ˱ҽΊ͇aԃӭʑQЍ߷ɍש"],
                encodeOffsets: [[8705, 4887]]
            }
        }, {
            type: "Feature",
            id: "NIC",
            properties: {name: "Nicaragua"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@̃ˆϽͺȁ˲Ο˄сϜĤžƒŵÚÒʾŀȔŬRkЮȠrǬOǺɤʜǝĒľƺĲ̊ɴbǦĄQňȪĖ|ƜŹǚȆńɄB̈ŌŜŖ˾iïă§ȉĐ̫ȗ˹ěͷυ®ɏtϙŹĉýΫÌɛǣɋ ɩźƏȩǱʛÈƓǦˉêȕŉօɞųŇ"],
                encodeOffsets: [[-87769, 11355]]
            }
        }, {
            type: "Feature",
            id: "NLD",
            properties: {name: "Netherlands"},
            geometry: {type: "Polygon", coordinates: ["@@ۦyǀ˳Ƚޓɇ́ԍ@ƘࢡҥȞՏπީǩ؛âѠɲ݀ఆଲΘ"], encodeOffsets: [[6220, 54795]]}
        }, {
            type: "Feature",
            id: "NOR",
            properties: {name: "Norway"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@᥆ؙઍɣऄՅෛ͵ڵû΢לઃͰಫ˵Ы؝ߟωࣗȮ઱¥णѼԉɝԷūփནƊɝҵ߭Hևױ࠿झಫ஁̨˹̇ͫ࠯bձ޿¾૟՞э˥ধֻۧυӛ֝Ԫဋঁ૫ȟ୏є̛ࣚˇ኶ޞզᕠ۶ဌࢂ໤୦፺ྴඦلᘼ੊ᇎπ൪­౮ۢ໖ພǘ"], ["@@ም΅๝Ȝ׆ɐԕˎეǚͮ̿ொȍ"], ["@@᪖صᑟͥұأ݅ǁЍۡৣᅵԢނ̘ఽʐ࿕܂ٷڄᘎ̜Ң̋஦\\͊˼௾੖̋"], ["@@࿮̏ఝҍ᝱ı៙ƖƫɴஹdँϬᣴɼ௞ȫࡘʤᑺȽ"]],
                encodeOffsets: [[[28842, 72894]], [[25318, 79723]], [[18690, 81615]], [[26059, 82338]]]
            }
        }, {
            type: "Feature",
            id: "NPL",
            properties: {name: "Nepal"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ÝαŌՕĩͩ۩aয়Ȟ٭ĂӛђଷŊયҼ߉Ю߿͆͜޼ՒϠΒȪڪʳࡔշҾť˰ЕٶǓۀσौȕঔć"],
                encodeOffsets: [[90236, 28546]]
            }
        }, {
            type: "Feature",
            id: "NZL",
            properties: {name: "New Zealand"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@Ȓ΋װ;ʐΡBΝ̹ϳչإїͷ̴З٭Yܗ̓ɣջӋࡗڇϓнʇޝlխˢࣱÐƗ̰Ҍذ੐ࠦժǀ׾͌ܜѰԎѦώظ͈ɆŰҶלϴȆΧ"], ["@@،ࢫlָϜɯŲًڰ˛֨ãӒ͎юĭȯݗʯӫٛjɡʭþαūƻͅҏзֹ٭ͯƟɘΕŨӞ۔˟ҨࣛͲz̦؈̌ƚ٨լͻ֜vƪБΎڋݔΗת̸àҚұٺɑʂݡ"]],
                encodeOffsets: [[[177173, -41901]], [[178803, -37024]]]
            }
        }, {
            type: "Feature",
            id: "OMN",
            properties: {name: "Oman"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ֹ̻ϟªǩȧƉэļ֗ÿĻϯFԽ̻ćХȓǯԹP͡ɃJͻПɷҩĂ֗˳ϱ³˝טٿ൴ᠾ࠾֖၂ϩתv͸ʔΐFΆϞǒƩŞèմіHϖֵҸ̧؞ŋӼƳϜӕɨ˧̞ŃCȉ̩ԃƅɽΟˏ"], ["@@ŉƳǅ˺ʔ˺ľñā΍"]],
                encodeOffsets: [[[60274, 21621]], [[57745, 26518]]]
            }
        }, {
            type: "Feature",
            id: "PAK",
            properties: {name: "Pakistan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@تϻʞ٥൨ͻ߹۷ऩůౣȲЫα̖݁̈֩ڴгܑӟ`׳ࠃࡇՃ࡝࢝ࢡউÚऑࢡռϗĪ٧ҾэǘܝᇛD֓֕؛Ɇʣ؀٭٘໻ǁിeஃŝ̈́ঊொѢéϰГƌw݊ߥφͷԔеѶඨѕࡀŲԈŅǞȂגóદΔ܎ҶӈشCĠɼٞŌ̴ý͢ʀ±ԌΦԖ՘Ɇͥ֊ߜɴ̢͒мΜĩмȣΤӬμࣘǮ८ĮѐƺӨĦ"],
                encodeOffsets: [[76962, 38025]]
            }
        }, {
            type: "Feature",
            id: "PAN",
            properties: {name: "Panama"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@˫ʎǵҒȺɢɅÎƿˤлɸοÁǝ̇ͻɁǽĉǩВҗɯŅŧŭϷ©ơԈŋƛˡ¸ǝ͸·ÈɓİέCǻĩŶªǖìǠƲŲĲǩŲK͸͘ö̠̝iǱͲĀæɴȵЮÔΨɄԜǞ˺ʤҬ·ĉҶώơ˜ʧ̈́ɵĹūȜӵǁʟ˓ÒŅС"],
                encodeOffsets: [[-79750, 7398]]
            }
        }, {
            type: "Feature",
            id: "PER",
            properties: {name: "Peru"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɥљћɋࡅӘñΈရࡊທࣾ٫԰ΏۜƐʎ܅ાࠣ༄ߍီ΅Ϥ˃ؤٷպױͼ˖ϒПߢʼךڢՎĲΓʇȧx̭ΎâͼĝΚщӆΌǄ֤ԦܶৠͨࣸࢠʾմŝٔɢĂ֒ЉˎЅϴɏӶࢣضĿҨɞ̤ƣԎð٠Ͻթࡣʤoрҁݳ œųۍǉ॥ֱÓϻɉ̇ČғԕʍBΡɛƵΔݳҲԝǱί֐µ͆҃ݐuېӸÇ౧ϢĩӄƠܪടǷ˵£ןg܍͟пƮ̵ȕ˯β۹Ջ࣡"],
                encodeOffsets: [[-71260, -18001]]
            }
        }, {
            type: "Feature",
            id: "PHL",
            properties: {name: "Philippines"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@Đ֏ºҽ˹ޑ̫ࡨϽэˎإʉϿ঩Ӧɿ؊ʰЎՑЈˁΑЃثҵƑʖ͢۾ՌʀҜ̈́̔ϝٔɰƎϒרv·ٰڼЋêхÐ̱"], ["@@̟ˡˁՍ˃ʝԫ׈ǦɤɂɾĢԸҨ¸Ɖ֣جߺāߡ"], ["@@ૣߕЬט؈԰Ԏ׊Ѱ࠲Ʈۅևҧѳֿ"], ["@@Ԏʹ՘BgΗϳΣՕʧϸÒєŽА"], ["@@ʀभ٫ɞj˭ȶԯЍȋעʧªƁԘӶãY͈ԣٜ߮mɴ̻"], ["@@ɟܩέоѓ٘ܚ̡̈"], ["@@ԮʉʶɖüɇƍΑ˼׻ɛۥӷ˥ƁڳȊڝѾġϊĲਾүăҙ˜ȫēϯٻЮ̵Ѵɍ̯՗ԊރůлȆ¨ΎˀɊʣȘŇ̡бӚűμߨͺˡĔೄ˜ހԘA"]],
                encodeOffsets: [[[129410, 8617]], [[126959, 10526]], [[121349, 9540]], [[124809, 12178]], [[128515, 12455]], [[124445, 13384]], [[124234, 18949]]]
            }
        }, {
            type: "Feature",
            id: "PNG",
            properties: {name: "Papua New Guinea"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ɽčε͔ρՔǷ٘ŜĆĜʡʬȏРՑЈ˵ŝɽ"], ["@@ѯçƃɽҟȱћȟѽBۏʔӑɺêʺݬũҠàŶЖŦrĆѽӐÜʂ˼Ҹ̚ġӸԌfǜƏgү˯ԡ"], ["@@ݤտղࢻӖω٬ƛʥǁࣀΝġʏ֋ÏȷɔܟĦࡕŴٷ՚ӉҦѧ݀ભπ܇ʇԡˣńإڇ˿һƖࢅaᩒaᩒภ׃༊ӓׄїҴхŸӵඔԱȲѽޛěȄ֕"], ["@@ʿɡǁӸȝ͘ϝ˞ӍΪ؇ʚɺȮҒɻ˸ȁΜȫʹΛ͊ˏĶѧ"]],
                encodeOffsets: [[[159622, -6983]], [[155631, -5609]], [[150725, -7565]], [[156816, -4607]]]
            }
        }, {
            type: "Feature",
            id: "POL",
            properties: {name: "Poland"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@·՜à̂ȹ̧҆̚ɺɤȝђָʘ಼ϴ੒˴࠼ƙÚȱ߸Yਚħ໶^њěȬʵωɸ͋KͯԋǡʸϳfϏцܻěɽзįރۥɒϗǿ¶ߙ͔؁šЇĒӹǵч̖Ήŕ³¼ϭаر¼ăˀֻĦűɑҗǨÀɴػòЉ˔"],
                encodeOffsets: [[15378, 52334]]
            }
        }, {
            type: "Feature",
            id: "PRI",
            properties: {name: "Puerto Rico"},
            geometry: {type: "Polygon", coordinates: ["@@јõưǕɋɃمLӫ·άŢŬیK"], encodeOffsets: [[-67873, 18960]]}
        }, {
            type: "Feature",
            id: "PRK",
            properties: {name: "North Korea"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Şƥ͉ºη˵ʣ˷׽ѣȅƫƧ̓ʝ֓ƏηɥηįġͰƋӈσŧȭΧÇץ¡͝ϛϑÁùСǆĵƿʙéǀɑüɥƆɰφȤİõƶɆҒÅƎөĠЇɤۄբऒҌ־׮ЎˁܪſѺಚβͰҼժӹ"],
                encodeOffsets: [[133776, 43413]]
            }
        }, {
            type: "Feature",
            id: "PRT",
            properties: {name: "Portugal"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@̦Ɉ΄ŬɂЫӺDƞłӪɼуϱɩYٽƍūЇγçʹԋɵտ̄ʡřɫ̵̿ê˥ͷɓѷŠџġŸڂÿԬϓþȩ͈äռͰ̨ÒͼǪԎkΤǙ̠˲"],
                encodeOffsets: [[-9251, 42886]]
            }
        }, {
            type: "Feature",
            id: "PRY",
            properties: {name: "Paraguay"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ͦ৖tҌЖ݌าʔޮ]޴їbʵʞҳÇଛࢲǇ΄ǐ֦ɩǀʣþޓİ͓̼̀ƌ̢ƳAҥŕӻǑӛƍݏށ١ړƇऻŸࡑɮࠢ౨ťψࡽ͢ਅبۉŸ໵ൌ"],
                encodeOffsets: [[-64189, -22783]]
            }
        }, {
            type: "Feature",
            id: "QAT",
            properties: {name: "Qatar"},
            geometry: {type: "Polygon", coordinates: ["@@ÇؔɨѲɰĜʬˁdӯǽӳɵÑʫǖ"], encodeOffsets: [[52030, 25349]]}
        }, {
            type: "Feature",
            id: "ROU",
            properties: {name: "Romania"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@δǶԴġՠGϸȳ˺źبĄɄȠΠ@ʰćʺʟˊΟӞԁρėΩưϥϒƹЂƊϠƟpɏПǹʯĀɻ৥ӳĖ̪ؑফțзɋ௽¬٥ƀ͙ÕʍΊƵƦȚƘȷŀ˃ȋөʔßΌԟȢĥˌҕͤڪǂԖ֮Њ֬ԢǮ"],
                encodeOffsets: [[23256, 49032]]
            }
        }, {
            type: "Feature",
            id: "RUS",
            properties: {name: "Russia"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ࡌ๫కˤԫ்ࠌࡳyוُԒսٱƻ۸Ĥࠊħ࣢Țٌ૴ӯࠜôରަϮͭϴϐŠɔ։̆ߵuࠟΎࡑ"], ["@@໵]ਙĨȒτ୊˚ࢢƧψƃęɱäɉ"], ["@@֦Ƚțؐᗸű࿨޻࠭λ൛ēsࠑͳǩ޽~ٗ̊ૣʖȉθ࡟Ǝॗŉҗ̎Ǽ̸৓ȥϚЃӉΣ@„Ꮪٛᔺ࠳ïԷ"], ["@@ः©ƭˌੲΖ@ַ"], ["@@ળ»@ָň܈E௒ʉïŗࡽȩ"], ["@@ౡMႣĤƧ¬ߘͪੀþஞ͏ĸə"], ["@@ॿͩഉø༛ͨȪ˖༨ųᑔɗ"], ["@@ډرᶽzඃȣမղҎ׀૎ǂᕞᴬѽ"], ["@@ӹóᩣŊɟώູɦūҒ࡮ǶҞသܒޙĺ፨݆ɩϢሤѺ᪪բ᫠ǀ෴̸࿐Ŋאͩ֟ʻᲗз᢭Џᤙߝఫࠍ೉߱Ǡۥྎۏ"], ["@@ɨгސȲឤYቈЧڬ̿ȽѧङʝᕅүفʟਬşఖɃݴǄєաτɔഊƂ᧪ƑȴϽ↲ů´ٜᄼƥഄLബѷϮ՝ӹΙੌڋ೔Ϳ߸ࢦഖϙ෢ɦྼʵؤʀൖş؅ޮૐζ䢀ձܐӿᔲٛ₎ǄာƑ۪΍Ĺؙਜʇ૴Ǥ๰vཚǑཪĢะݛਪˎڷ՞ϐώᧆɻფºᝂБ୲ν@”MKઇσઝÖݶҁԄەϲɧĮΏɑɝ༧Ǿ᚝مݛĭ౽ן௛ԧ̱ϣய׊ᔗڇϣ̸ߵΫ૱Ř˓ց৙߽ͻड़ȋő௣ޭΫ۱Δα฽ѕ̅ॡభȳʥ࡟ே޳ׂ̳έ௬ҵለИ୘܀ԆªϾರȊຊ੒คࡺຢڢڮஆ৷ëԍۗᒉइۍਖᓧ˷ᑃටۚԧሙɕಝēÔ؊ಯŶ਩ЭᢵƠ᪏ʟᨩ࿛ủጝ೚ŁаՃࠄȅ՞оईÃௌऍ܍ځ࠽ë্ϛഉ్௓˯ׇଙ঑ଇॻթӹ૩ӱՉYՇФૻؙſ˩ŝƦKѐіxŦ঴ɛܚܞ̒৶Ʃ֢ࠈ˾ऄ͚̮Ѵݲ൷ʛܯͧ౧Dͻ߄হװหˎ̵ࠖ̉Ԫ̿βԯࡐ̲݇షʢ૛uਯƱۛлҤȥXҩұˑݷࢻRσஅՍ৙̈́োéѯˮԋĞ௷ףેƑޛȻੑƌޫSԙіࠕИࡅŎ੝ŋߏƹ஛ΜǇـধɎށİवΎࢉࢉ΀ӵࠇב௏ɂ࠻֗Ͼ࢙^ܳʴ౫Ѓྃܣࢭơ͡çѽԤઍőΧΦחǌЙӠҩưிɍୃӜ҃ѯሟᒒੵٮ̮˂ᑋߍ߭³êҞઅ˺࢙ȱ˃ࢊມǺݯΑᑅ̳Чȹḭ̇ϫ˻؆ֹ߭ɓǀɭ߭ХസֿɁЉ୻ʓʟ੹Ѧ೯iࢻΟহͼᇡ׊ಽsჃࣳĿؗࡹӤڡउʖǡӝُ܊֫ذx՚֗ďѝѐƋϥӽ߿Ƒ࠳ࢁކߕĉ֣ࣼফԇ͹ƝɇωÌֿԚɿՅȚʳΈ޵ǮԙƁƥƼଥЖఅƌ܃ƞĹıੱ܂य़̈́ܩӴؒƈۤ۰ҹͪఌ΄uȀݯƉώѠɼ߼ÖƄ˪ȅҪ΀ѰWʚఉ˚ӭUԯЀ١ƃ੩̐lǒ̗θڟ¤éʼɀǞ՝ӈࢋąʭ¦Ƀȑ̽ȷ՞ȟ˨ǊĀڴ͞Ȁʍɢ֥ƪ¼Ʋ΁ƴՃվǸɨĉЂࠑȨѱĳšȼࢭɂˑӸíТЙȖάˊʝ޶װӞųƤक़ҬࢡЎᅢ੶ޮӠ͂єగּΆնݳش֢ܜ঍ग़ޢي౿֔ŬךڶüොͶࢀ̈൦ԕᘨȧṺो٤ЋÆ֓टѳ൏ɡ⏷ٔ؟Ńൌ؛ÂϵÆ࡫ઌʯڂɓňРԑΰ՘͈᎖Թ۾Ȳ֣؜ዦࠖޢµ޸̋Ӫ׀۫ԄЪԊءԶᚠˑӔҹ੡ĻNҳڌ˽ಜǼȶ՚ჶАᰪܞي£ࠣԙਬĕ׼˼༾xఢΐफ़ԏॖ֌ࢡӢѪˤ២ʫ୒ʿᴾॣ֚ѰࡡѺ{ǴৣĈˢЌ҅ټ}ː༄ݾրކزǒᕮɛǬұߕڽԺˋ˒חȏଵऒԧέ֕࿫஝०ŭ̢ͮऎɎɞжܮЎөӌϼֈࣿêȫҲڢࡈણۆຒ֦șװмnѴүͧ߷࣐Ƶϥ؄ඤͦლ¬༈ӏݛ۪ċࣆศǞ፾ᆘŌہѮংւॲx࿎иᕠŐ˪ɲᕂþیȋሴҀ໲aɶδߤΨጤΈ෸˗ଥȷበŹ"], ["@@ⵙ͕ໞીےĦقÃᒈӋʟͿ"], ["@@૽ōݱÛśƏঙƑ࣫ȦӐʾል~࿞ƶ౨XǢɧӘȬߊƐఞǿ͗ŷ"], ["@@ᆳĿᚉʎඅ͎٣׾଩ǔᔆָᆎȎ࿌чኬ߻ȹݯ"]],
                encodeOffsets: [[[147096, 51966]], [[23277, 55632]], [[-179214, 68183]], [[184320, 72533]], [[-182982, 72595]], [[147051, 74970]], [[154350, 76887]], [[148569, 77377]], [[58917, 72418]], [[109538, 78822]], [[107598, 80187]], [[52364, 82481]], [[102339, 80775]]]
            }
        }, {
            type: "Feature",
            id: "RWA",
            properties: {name: "Rwanda"},
            geometry: {type: "Polygon", coordinates: ["@@ͬӃµӵʏŁѿÆʱӍԛàþҠŘÞԄʎɺȰďԈʸ"], encodeOffsets: [[31150, -1161]]}
        }, {
            type: "Feature",
            id: "ESH",
            properties: {name: "Western Sahara"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@oҊŸ@@ÉeǋEౝ᪁ªᒷ޿÷ȳћǄ்ᾓNǽ˫΢bCቆäĶ̢ΆϘˤୌୠ࣢Ђ੖ˀÖ˜ټۺĜ̦ŉϢ@˔ȒԲ"],
                encodeOffsets: [[-9005, 27772]]
            }
        }, {
            type: "Feature",
            id: "SAU",
            properties: {name: "Saudi Arabia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ŉΪʩʨÝͲѡ̞҃۴ʁۆׇ׀ϑƐ֋ߠīאӾӕञϿ͠ґǨˡӖ°ȎɹѦʕȊ͝زԟڴѓ־лIžҦ̌ļͲनƅζʶȪ̢ٚŚƒˮˤƜ࠷ࡀ၆фǆŴৢɩబיᛎၕ༠ãݠąȾЏתv͠ܥаȓƠִ̏Λ¼΍ċ˩ł˯ʎɽŐ˟ŲȵʬǕɶÒǆ͍ș࡙͐ᡌщǞǲϪש֕၁ᠽ࠽ᝑ͑޷ϙ׻ࢥϹƕɁˬ͏§߻ĎƷČॹmɫùΉɔɝЭĒΟρˋ"],
                encodeOffsets: [[43807, 16741]]
            }
        }, {
            type: "Feature",
            id: "SDN",
            properties: {name: "Sudan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@śhdмĵ̀џͨĵ؄ĶبϳÌÍȇԍ©Ȭʕðԍңңл؅џđ۹Ӫͅǥđʓџǃǥ࠵@řǦ؃̡ƝɳîѝӬƟɲ؃ŗɱϵɏݣ˿ǁʳğå ̅ʎÃʼƌΔE΄ӛՀĩάZȰ̱ʜUӦǭ͖̍µĎ̰ɒΖħΐˢʴǫȞɞ԰ϨئܦÏ¥ ZΚॲH@း⁪@Ὂ@ῼ@˔ࠗȁƳŪࡻ্̰͌ȷҠ̳ыӑأƏ˅ʳĉ֑α௿ĚͳƅܟͿࠟԓзέٛč΃Љɽʝ࢟Dĳ"],
                encodeOffsets: [[34779, 9692]]
            }
        }, {
            type: "Feature",
            id: "SDS",
            properties: {name: "South Sudan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Xٽűʯѿq˷ӏԨÑюХƨͳϦșӼࣳ֫օԫԇԫϭסFگȟՕȊ΋ɭ݉֐ȥάҵǱϱÆɣƕϗĸԗۚƉˊعͪɅԌΕζ֟ѬS˘ҡͼ֯͠ʴĠ̀ǂɐݤɲ϶؄ŘƠɱўӫɴí̢ƞ؄Śǥ࠶@ǦѠǄĒʔ͆ǦۺөѠĒм؆ҤҤïԎȫʖԎªÎȈϴËĵاĶ؃ѠͧĶ˿cлŜg"],
                encodeOffsets: [[34779, 9692]]
            }
        }, {
            type: "Feature",
            id: "SEN",
            properties: {name: "Senegal"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@΍ٺн̚φǄРמȦќ˾ːкïШǾҶVДʙ֎ɝԘأֈֽԹǔӓ̾ɿî͗ʽŧ³қâÙģȃk׿ȲЛV༇ɥħ˥ѻƋƏ٢ވkȬŞƮR̸ȘήǯκcζȌǝʐˡƙʻJͧȸˉ_ȍȥࣵy"],
                encodeOffsets: [[-17114, 13922]]
            }
        }, {
            type: "Feature",
            id: "SLB",
            properties: {name: "Solomon Islands"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ɾ˿חN͉ԬԈȯǜ"], ["@@͝mԧĎǫżÀͮֈƁ˜ǭƎə"], ["@@ųƹحܰǫԈ˺@̠ڥʹЗ"], ["@@ǛڅΦҟ̠̿˪ŰĐϮȫېϭȢˉ"], ["@@Ǘ³οȒ·Ί¨ƖԈΡͰ˛"]],
                encodeOffsets: [[[166010, -10734]], [[164713, -10109]], [[165561, -9830]], [[163713, -8537]], [[161320, -7524]]]
            }
        }, {
            type: "Feature",
            id: "SLE",
            properties: {name: "Sierra Leone"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɧØ؁ͺѩ҈Ƨ̬Ĺت҆τĬɺƞǸɶpȜǂڦCɺ̛ǼΛʓƈɗṶɴ´ϹϹϛҗ«ʓȩˏ"],
                encodeOffsets: [[-11713, 6949]]
            }
        }, {
            type: "Feature",
            id: "SLV",
            properties: {name: "El Salvador"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ġȡӡ^̡Ą΍ǘұÀʃǶ~Ů˾ɄǀĢ«ĲȠ¾ʜëǸǙʪƇœτĴǤÑŘĝÏͳ"],
                encodeOffsets: [[-89900, 13706]]
            }
        }, {
            type: "Feature",
            id: "-99",
            properties: {name: "Somaliland"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ϛԩד۫۹Mᩧা͍̜̳К̳ҨǾ̖̲҈˚ƹǒΏϜΗкGߊɌࣴĴ݌ʼиÆ̚ƶӎKaE΋Aࡑ@ѫ"],
                encodeOffsets: [[50113, 9679]]
            }
        }, {
            type: "Feature",
            id: "SOM",
            properties: {name: "Somalia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ѼĎЊ˾͈FpɵýӧHѳǯ̣ʁࣥЙयԱ੷ܝ௷ܓवধ଩ࡁڹష࠯޳ٕँৱȗѷȍȣӽۚWᵤܾ॒ɰˆբfݠפબᛜᡄה۬ϜԪ@ѬBࡒFΌLbːhϰŰ"],
                encodeOffsets: [[50923, 11857]]
            }
        }, {
            type: "Feature",
            id: "SRB",
            properties: {name: "Republic of Serbia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ԡȡà΋Ӫʓ˄ȌȸĿșƗƶƥȷȏø̫Тγ͋ʿƗˋĞĳƑšϳa˹µØĴĴĦȴšKǍƼƑ ŋƆƽÀšŠƯ±ś˧ȩÑèð͋Ǩ˟ĜūŜɟƠȢŬЄЛ͔ɀτ̥Ë͔́ˉʈȱ͘٢ɚԾҖͣĦˋ"],
                encodeOffsets: [[21376, 46507]]
            }
        }, {
            type: "Feature",
            id: "SUR",
            properties: {name: "Suriname"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@৔ǙĞưڶÔࣚɥѩܟâֹͤӽƥίóϩɉΛӓǲЇđ͹öčʏƘǗ÷ǡҙèԡܴōӄˏBωؐƺѠ¯ȤԜɖƈݲ"],
                encodeOffsets: [[-58518, 6117]]
            }
        }, {
            type: "Feature",
            id: "SVK",
            properties: {name: "Slovakia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@´»ΊŖш̕ӺǶЈđ؂Ţߚ͓ɷɓǏ͹ǳđ࣑ʮ˟»ȟȡЁĿěÄХŽͭ}ãǙ۷Ļ̱ĠёɌċ̆äńŢȂόa˺Ĕxþǈ¢ÆȒȖžưʢD"],
                encodeOffsets: [[19306, 50685]]
            }
        }, {
            type: "Feature",
            id: "SVN",
            properties: {name: "Slovenia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ۜÝъȐܾtǈƘƘUǎ˳ڝɟć͹̇đHɻͣh˷ƎƷƙבȈúȫΨĞа"],
                encodeOffsets: [[14138, 47626]]
            }
        }, {
            type: "Feature",
            id: "SWE",
            properties: {name: "Sweden"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ࠁוƀԥ೹ڭྱܡؓஃײףߦүޗॅ࢑ȝ͍තӋ޿৳ĆӅڗঃˉߐ۳॔ٓஐφӜּۨ˦ন՝ю½ૠղ߀࠰ä̧ͬ˺ಬஂࡀञֈײ߮GɞҶཔƉŬքԸ૪Щ಼ֱv಑˴͛ฃʃ"],
                encodeOffsets: [[22716, 67302]]
            }
        }, {
            type: "Feature",
            id: "SWZ",
            properties: {name: "Swaziland"},
            geometry: {type: "Polygon", coordinates: ["@@ǡύӭěԅҖS̄ɰ̀ĂʔʐÒшƵŰϕðω"], encodeOffsets: [[32842, -27375]]}
        }, {
            type: "Feature",
            id: "SYR",
            properties: {name: "Syria"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@࿩ࣅऩͬgNŖŶ_ΈȸҠҜ̈́Əͤϗ¨ÿٞȶΌɤȀɤȀ°Ҹ˞Ǐऎɺ҂ƿۖFॴ̀Ґaक़žїԽҡȹĂؗͅ৫ᇵ࢓"],
                encodeOffsets: [[39724, 34180]]
            }
        }, {
            type: "Feature",
            id: "TCD",
            properties: {name: "Chad"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ĎЄաnDզΓ̶δ૊ੴߌ¬ન͖ၼǼΰΓ˾_ˌ̽ɔȷರࡔҠ…ྑ…ྏ¦ ܥÐϧإɝԯǬȝˡʳĨΏɑΕč̯̎¶Ǯ͕Vӥ̲ʛYȯՏƛэͽ؉ࣹ߅ϳ߹¾ʁûĊ̏ѫ̋Σ͟੓͏ȽȐƓhƹɍۛÙƀɪ˅ׄşΐλƜӷӪǼІϦċʂÐҸSқކ֐É֐ͭՠ"],
                encodeOffsets: [[14844, 13169]]
            }
        }, {
            type: "Feature",
            id: "TGO",
            properties: {name: "Togo"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ڱǳȇ̎ɡՔãкȆݴɁ̬ăڎD؎ΕѠÖˀ݂kŅѵʲʝ̈̋ЭǜǥኝȺׅ"],
                encodeOffsets: [[1911, 6290]]
            }
        }, {
            type: "Feature",
            id: "THA",
            properties: {name: "Thailand"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ݭϬܗeŬڈ݉Káऋґ௯˙ݏÌ؋ն΀ދưܭҶӓԚĭѤѧ˝·ևĵßќۇςƣƭͧ͒ƝжҁӄПЌƏӳǃҲĠԾʚ߬ТࡸҤ޶͟ތ`϶ĩҸ֕ښȩф̄ƺ̮ܶ·ֆՓؘН݆ΠƴϦࣦצӬθӔȘθʷ´ԍ֨ȷࢭpݫࢰԆʤƧӰzǜَ̊ÍٖڽÀࠥںܷ܅˙ϛ޿Ŧગǅ՟ۧȤ১"],
                encodeOffsets: [[105047, 12480]]
            }
        }, {
            type: "Feature",
            id: "TJK",
            properties: {name: "Tajikistan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@̭ʷࣳƖāӛ࣬Þਢ˗འŶɈާˠĐԜȓ͛ŴӍࡿBׁØԻϕύĉ̉ǯͩˠþ۸ʩ¢ĞʲғȐα̇ė͹Żūԇj˕ϩ˯ǌ؋ˑʱĺӀࡘǹض؟ȨɔφۮЌҬˌբ૲ȜǩϵŤɹΎv"],
                encodeOffsets: [[72719, 41211]]
            }
        }, {
            type: "Feature",
            id: "TKM",
            properties: {name: "Turkmenistan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ñۼطॣݔڣĠगюׯþσƽ֙|ׯӓ݇ǋƻרŪ࢞ٽ˶Ɏֺ֏¸Ȇ۾ߊȵ݈ˎؓԎʉӔڱɋď؛ʿհψ˨ॖǪ֨ɻךڅњ¤ॆ\\Əцܖ̂۾ӦଆѹĜڡ͐ǣࣦˮƳаࡽ०ׇոЃ࢞Щ૤ΫwԥʩЅɤſ̙۽ǋǙڥӁʭڏŵǫϟهŏࡩ͈"],
                encodeOffsets: [[62680, 36506]]
            }
        }, {
            type: "Feature",
            id: "TLS",
            properties: {name: "East Timor"},
            geometry: {type: "Polygon", coordinates: ["@@ĲȤܢȌזˀŀ͆Ľ̯ɫ࢕ο۳ʋeʬďǔ"], encodeOffsets: [[127968, -9106]]}
        }, {
            type: "Feature",
            id: "TTO",
            properties: {name: "Trinidad and Tobago"},
            geometry: {type: "Polygon", coordinates: ["@@ӚŊǮصۭġƯúʒɲiͪ"], encodeOffsets: [[-63160, 11019]]}
        }, {
            type: "Feature",
            id: "TUN",
            properties: {name: "Tunisia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ΩພԭͺQȰۉԄóنԮҶȢۚƃߠǠќࣶͺךĵ}ы܊̲ÒǉпЫMϱ̆ȽōܫփхǄқѤaɄЍ͊ſ³٥Хʋʵˏֽ͓ĘΑïΟЧț"],
                encodeOffsets: [[9710, 31035]]
            }
        }, {
            type: "Feature",
            id: "TUR",
            properties: {name: "Turkey"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@஺͗ঐżܤõলѬࣆ¢ߴЭƜ̑ăУزȻͨʕֻʇˀ५ǏʻҠڧЕƙ̏Ɋ঍ňίŽॗŽҏbॳ̿ەEҁǀऍɹ˝ǐ¯ҷɣǿɣǿ̱Ϡ͈͂ԟí۱ȖֿәౣĥڹҊࣟȗΑׇĳ߻҄ࣻeӽ࠶ؗҰЦٸՓВठߨಒΜྀٔŏ৞հ঒ʄർlุף"], ["@@۫ҏ˃Ϻ\\ǦȦĦʺՂХɞࡦ˄ܤőĴ͓ܼ˓Ƶȵি±Ωʷ"]],
                encodeOffsets: [[[37800, 42328]], [[27845, 41668]]]
            }
        }, {
            type: "Feature",
            id: "TZA",
            properties: {name: "United Republic of Tanzania"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƚġᵂႋÌӣ஼࠿ϱਙ¸Ӊՠ̩~ɓɳԓ¶ʭÇГ̌Ճΐ̰ࠡǿڝӣࣿ͛ԋb̙ʥבsɕŃঢ়ʂكåɽଢ˵ϺǛɶࠗƾӉʨՕƘͯƘΗɈґ੖ӣҺǗӤČѨƯޞΎ ̨̦͜ѬȺǮS˘ǷȐ·ͨʐł¶Ӷͫӄ̎Ķऄ[ႎà"],
                encodeOffsets: [[34718, -972]]
            }
        }, {
            type: "Feature",
            id: "UGA",
            properties: {name: "Uganda"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ः\\̍ĵԇʷȯĐPوȜ͎²ڬǰϸ͎Ѭ͔ɠ˒̘͵Ŗ¼চΌɮՖȉڰȠעEԬϮЊ׍İсτ९̧ؓЯ֋ʉͽTࢹႍß"],
                encodeOffsets: [[32631, -1052]]
            }
        }, {
            type: "Feature",
            id: "UKR",
            properties: {name: "Ukraine"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@̾ɄȒʮ¥ࢌĆ՞Ӈȿǝêʻڠ£̘ηkǑ੪̏٢Ƅ԰ϿӮVఊ˙XʙͿѯȆҩƃ˩߻Õџɻύڡã֑˕޽«ܣ̻¸ԹЪȭࡨ¼Ǐ̛ँơଛӟұǠȄЂࣽʘƨǈߪ˪ʑȔಯɆË̼ީĻ̷ҧٱةϟƠЁƉϑƺɂĞƦ˾ɲˎÑƮǬäĊśӸ{ɞØƽĎÐŲ̉ɈŧΘ̩ƐÒ˶ϝɦΉأʾ֑ĉȧŭΟ@Ƀȟاă˹ŹϷȴ՟HԳĢγǵÍɤұɮǐͺɸɔȀµɑϘބۦиİĜɾхܼДҢɪٲnࡖßबȫڎi͂ŧ̀Ʀɚȝݸ¢ͮąÄцʶȂܞº"],
                encodeOffsets: [[32549, 53353]]
            }
        }, {
            type: "Feature",
            id: "URY",
            properties: {name: "Uruguay"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ղĚࡆٯ̺|ࡺ՟ڈҫӠֱχЉɸӇεՇॉұاǚғěޥΰ֫ԟҬÞլǾȈS࠸ɤࡺȾڦ"],
                encodeOffsets: [[-59008, -30941]]
            }
        }, {
            type: "Feature",
            id: "USA",
            properties: {name: "United States of America"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ũƕȽŤ|ɾƓ̨¦ĤƤƎÍǔ¸þÜe͐ƙƬñƌőɊ̍q¯͟ǵˏſ"], ["@@˭ÑƟǮīèQÀĈî̘āɘŹëĵ"], ["@@ĝ҉|Úĸа"], ["@@­µÓŻŃȒɤŚêÃʐ˥"], ["@@ıĉ˱ƴªÖŸĈȘijȝ"], ["@@Ƭңʼƛז½࡬ƅࠂʹڼŊਖɓ˞Tݨʄ߂̧ࠒ͗ں˩ٶˏĈəȢĉ½ĉɦǎĔ¦ȣǜƅɴ@ŬĹĽƫ࢖ЁǶށǚܳʗӹЁҥȁ̍mēĦť˸Ɓɂ@ঊ҆ࡾƀસмfĐ÷ʰƉǒϜƆࠜHޘAˎ͞ŀàࢶ؄ϜƸ౦N໾BĎȺː¦Φž̖Ϣʲٺٚي˨ə֜ƜώʏAଧռӅƢ˝࣋Пࡷ̃ࢱʝѻӿƛȋSѽˤѽΒsė̬ʦȇãʇ֥ƋЗhةƥλ¥ӥ¥۫ʏఀǂʠǃ୳ʥ՗C|ĺʭɷʚǹ׽ؑ٧×Ɏȁª˟ɀǪҍȼƭ^ͅˏ͛ҿڡûʺֲѕ͎įۦǉεǴՑևƀׂ˓ߛʊÍĖ̃ŠࡁՕدࢇʝցӱнÁэ̱ţ˭इձӁЍЅӽŻׯƪ׍ˬܗώשLεЊঅ֥͛ȿԡʣŃЯĺƁς͋ȖѻܢϹٞű͢Ǥ֐ɽҦٻ۲͟źࡑϡƭ¦СϼՃȺोŁݗĤٙÍΏſƲɟaͽǴǓǇō̵Ů́ǃ؍طѺܻĿ؏ȚԹÏۻȝއح࠳γҝБȕϗUׅ¨ЕǄ˹͝{׭ȂٽʺɽЄȁטӷӐ̃ӰуֺףͲۉgՉڑۣʦѡʪȽҦ˧Ѯӿτїˈ̩̖ป@C΋ڗ@ဩOቿפ౓ТĀǒ੩ĝॕÝƙіխӚϻĴğʌһ¦̝ɪޭĊɉƌĹҢࠁࡊ۩ୠȚχˤٯ۴řۆ҃ҞȀۢܜˍ٢͠ߊĸނĺނƱૼˇܘʓ϶ĸǐ௒˷҂ߋȺɜƇې˷ێᛸ@᠂@ࠜ@ᢢ@៚@ᡀ@ᡄ@᭰@ᮞBაAF͔˴J"], ["@@࠽͋ѕɐŽЀބ̘҆Ÿ֐ÉΤʻܫЍ"], ["@@ԧŽսƾԛɮࠦƞښùĂ͑"], ["@@԰ǅԾĒڸɛ࠲őéĝُǱٕǾ͋Ʋݍµȧôº̈́"], ["@@؊ϛώǌහ»¹ȕ౾ƛࡨČᄚ˅ྤā٨ŉ૦Ǝౢʧࣲŝ@@MᷱIⷍࠠ{ࠌɵהρݜցࠈҺࡈ˖Ҁѡ֤·ޒϙՂ׽࡮य़ේ՗xՋұЙҥ͂ݍˌʃܺએںҍߎ߯Ä೷rটʌ჉ࢎߩǄ฽̜୑í࿻ϬৃΨटǯǦ׏ҫÁঁǫ݉˱झǳťӶϚࠚࣀʶɱɂੱҵֵ֑௅ױؚСߏ׿ࣗΗࡁʱȻωಽѡ˅ϿছΫֽÞ޷ɻ࡝˹ۧ˫෹ʉſƘऀϾࠔʸࣆҠਬĨвΈ୘ԊȈǚب̒ƢْђӸॹʫ˓Ơҕ̧շюɧ̝̽м࠿ͳԩBïԄƲ̮ե̚થǇ܁ЀַȬIӈ٩Ϊ͘ӘۆҸ̚њںÖ־ƇڴМ؎ï٘ʼƻϨҹưج͖ԩWࢻǽʯȃڏȄஏĥ௷ȬΛ͸੟Ӧ୾ΘመШ۔@ŕнᄢڽԶਕ͌ױр߫ΨଽˈҺѲ๰ਗ਼ϦȨФ࡬ЎࠊĪཪώޜÉಐ҄ౚǭ"]],
                encodeOffsets: [[[-159275, 19542]], [[-159825, 21140]], [[-160520, 21686]], [[-161436, 21834]], [[-163169, 22510]], [[-97093, 50575]], [[-156678, 58487]], [[-169553, 61348]], [[-175853, 65314]], [[-158789, 72856]]]
            }
        }, {
            type: "Feature",
            id: "UZB",
            properties: {name: "Uzbekistan"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@xԦૣά࢝ЪշЄ॥׈Яࡾ˭ƴࣥ͏ǤěڢଅѺ۽ӥܕ́Ɛхॅ[ᶾᓘӺƾïದ׻یͅߤݵঢŪ෸à৔ؗÙࡅЦMǢۍ੬ɲЉ̺Lπ׺૎הӖƺʠĉ۵խئ́ײȾ়ѷ੽؁ٕĊ΍uţɺǪ϶૱țˋաЋҫۭ ɓυؠȧǺصҿࡗهǰҳN"],
                encodeOffsets: [[68116, 38260]]
            }
        }, {
            type: "Feature",
            id: "VEN",
            properties: {name: "Venezuela"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@yȣӱĭ˜ϡYѭυӥ͆ڙδÆȌ؈ʻ̒§َਸ਼΀řІ̎ˆ̞ןל_մҵ˧ݮQ࣌ĔӖϕٞĻҼʾXɄਨ¼৖\\܉ʛ˼Їڦ×ِЯƆڧѬn͢ȣڕӱó̫˾̷ȽƽԫƉjϱɫɱّ֪Őʁ̭͍ऱ̽׿Žʏȣڛɀثņƿýϔɑ֝ŜՉ܆ï°ǭ׷ʅĭΣΉƏسȝǋʱٷÅҧѼʯ࠺ɟ̧̌ȄюмȊʅʠǛ֒à׼Ȉ˰ƲҎ̓Ơӏĩ؁®ͻęסܢӥńઉăȧ̊ȷêǬĴ̶áͺȃȂŅϮѡÈɸӮĺ׶ʔ̸͘ʌɈрդƖ"],
                encodeOffsets: [[-73043, 12059]]
            }
        }, {
            type: "Feature",
            id: "VNM",
            properties: {name: "Vietnam"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@૭ܗ۫ߍȁ׍٠ࢭ޺ળނԱԞګϪ།ŕ๓۫փ१եۇ۫਷ޱ̧ՠʀ֬دӌܬ͸ࢦÔσԚප٨ļ৖ț֖ƶࡀɃצٍאՋ݌ۥ঴৓Ԋʊ̠՞ɘ͙ܺਙPϕކӭڐҊȴڢIࠈĬܒ҄К̿ސƵƃӛАͿࡎɓ"],
                encodeOffsets: [[110644, 22070]]
            }
        }, {
            type: "Feature",
            id: "VUT",
            properties: {name: "Vanuatu"},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ˣō˭ςɤՆӗ"], ["@@ƌڱɥŀǩ­ťɴi٢Дʵ"]],
                encodeOffsets: [[[171874, -16861]], [[171119, -15292]]]
            }
        }, {
            type: "Feature",
            id: "PSE",
            properties: {name: "West Bank"},
            geometry: {type: "Polygon", coordinates: ["@@@ԣŭʙЃŕɜɌŚɁĦǬ̤֔ś"], encodeOffsets: [[36399, 33172]]}
        }, {
            type: "Feature",
            id: "YEM",
            properties: {name: "Yemen"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@؉ɥǋύo˹࠷Οഇϻݩףυ±ʥºӭΑ՗ǉ۷©ɃµǿɛəÕŻɇеlˍœ׉¨ɓӬzҠƍʜǑتʋΊǚ¤đϨĸǊξςˌđΠɞЮΊɓɬúॺnƸċ߼č͐¨ɂ˫ϺƖ׼ࢦ޸Ϛᝒ͒ڀ൳˞ח"],
                encodeOffsets: [[54384, 17051]]
            }
        }, {
            type: "Feature",
            id: "ZAF",
            properties: {name: "South Africa"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ǏŧΣяɻћӇ׻ोࢁףԋًϣ࢛͙ѓ«ŇɷԛŰеǅ࣫ǊԙĹΏ¬ࡿͩܓƃԱͅϡoΣ̚˳fαϒśŏɦLӰ˙֞˔ƴs٤ս޼х܈AF׽તДдͪɯƘΫϘÓՈǃҌÖݤіB᷌ɨűӾߙûԟȈ̏׼ĒрϒЊʨȶДЦȚΠķВɽۂ£՞ȜĐʾƨДҚäʨ͂˪֔ݮغஒؤ΂UОƛ˲Ķ҂ċД஁ɔׯƫऩî̟чƶʏÑāʓɯ̿T̃ԆҕӮĜǢώْQȿؑıۥɑϛֵщ", "@@νʶϻǟҕ҃͡Տـ٧̜ČƺˎҴƀƜ˜ʴФ̅ʪ"],
                encodeOffsets: [[32278, -29959], [29674, -29650]]
            }
        }, {
            type: "Feature",
            id: "ZMB",
            properties: {name: "Zambia"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ІϏɊ܋ƝɩǙڻǈۡ˃̇ʭޭѶɓᢇۗĂׯٍřӍͯĹ̛̅ßܵۓҭխ˳o˗ĬऱĠƯÚOêͧȎկ¶ۋȑչԾ֣یᦶშYí̂Ű̀ƧЀĪТėʺ̂q¶ʽϾrՖûˬϡڨŝԤˆȌѯ٠ş̴ΧΈҥ٠Që࣠ɱƳח͞ɧƬļࡈƬসȉψʈ՚ɤĶ଀ƚͦđΘɇͰƗՖƗӊʧ"],
                encodeOffsets: [[33546, -9452]]
            }
        }, {
            type: "Feature",
            id: "ZWE",
            properties: {name: "Zimbabwe"},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ҁČ˱ĵНƜ΁VՙϞٯźʙՒC̒έĞ्ई˃ӢǛƮ͓ڤलğ˘ī˴pҮծܶ۔̜àĺ̆ӎͰَŚÆ̻۬hϴǯǺȻАÓѦˑF੟Ǐ׋عƊʝħӵŵùɛ؅ࢫ॓"],
                encodeOffsets: [[31941, -22785]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/xiang_gang_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "8100",
            properties: {name: "香港", cp: [114.2784, 22.3057], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@}ScTʟ@cWuJÁ]l¦RLj¼BĄà H@TOHCTDDDHDNAT@PEHDDNJLX@BABALHFF@DKHADBBLDHHFBLEJB@GDBBFBADDB@@KFAFBBJJA@BB@@FFDDADFF@FADDDBJC@AFBD@@DDD@DAA@D@DB@DHHBFJBBFEHDFAN@DGDC@DLCBDDCFDlAFBFCBEF@BC@GDAB@FD@DZJX´HĐMja@Ý`p_PCZ@lLnRGSDMFK|a\\Y}­§Mën"],
                encodeOffsets: [[117078, 22678]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/xin_jiang_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "6528",
            properties: {name: "巴音郭楞蒙古自治州", cp: [88.1653, 39.6002], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@ÈÒĊanwŎVȮ¦ͪŃĢÜōȂçČéƐżLɆóĊĊaʊŁ±¯²Um»ˌmÈ»VʠţWÑÅ¯ǓéôƑƒğÆīŎī@Ƿwô˺LÞ¯ƨVǪÑƒĢȘV°wĢôk°¯ƒ»΀@Ȃ»ĸǔ@΀͔ôôLɆó̐ÝɜLɲōͪƨóŤK@ī@IU܃ÛmȻţǩÝ˹ÛǉťǓǫō@Ɲ²¯VçōKͿŁΗÇţ»ƽɅƑLÓŏÅÅɱV@ÝĊU¯ÑĊĭÞLÞŎJ±̃XȣˌōlUÈ¯ŎKÆƅ°XÑÜ±nŗġV¯óaUƧUōŁÑ±çɲ¥lĉkğ°k¥nğţL¯ÝÝUƽĬ΁lķ°@ōXÿÝ¯V»ŹLʉÞɱŤĉó°ÝJ¦ÝKÝ£ţÜÈĉ@xǩUċƑ@ky͓¹`U²ĉVġ»ğa¯¥ť@ĉó@ŻÛÛJw¯nó¯ġWƽʩķÝɛwĉĕÝ¼ȭÞķō@ó£Å΀Ƒ¯ôȯÞ¯ȰÆōèĉXÇ¼ó@ÝnºĸÞVƜĸȚUʶõˀĵĖɱŎÝĖVࢰӒѢ°˘nϚVˌÈmɼĵŦW¤öʊõʔ@°ÈXVènŎȁb¯ǫĉ±Èğ`ġwōÔğ»mVVÝ¥ó@ĸķô@bXĶmV²²`Þ_ɴbͪÈ°ÞWĸÈŌmÞkɲÈUÆ»n¼ǬVķĸźô¯°n¦ɄÇÈ"],
                encodeOffsets: [[86986, 44534]]
            }
        }, {
            type: "Feature",
            id: "6532",
            properties: {name: "和田地区", cp: [81.167, 36.9855], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƨ¥èź٨ΘƑᩄbUࢯÞĕɲōĶĕöʿVʵķșUƛÝķm¹Þô@È»ĊWŎçÅ°ȯȰÝ°óƒÆͿĉ»̽çnmɱĵƧºóUƽ@±wóL¯°̻L±Æ¯Vƴķb¯VÇ¥ğ²Ǖbk¥ÇKlÅɱġ@ÑóK@ÇaÝXğţxĉČǫķê¯K@ÑaŹƑK¼¯VóaónġwóÞéUġbóĉğÇl¹aUóğKWVÅ¯nÇŋƑķnʇ»óxĉwçÇ°Åw°ċXób±kÈÇJm²ţx@ÒÝŦÇºnó¼n°ÇbUÒ±¼XĸĠłƽXmwĉºzÈÜmnxmx²ĖmÒbnƧêUºĊêÆVóĖóUĉ¼ÅĬƑ°ɆƆŻŚlłÞL¼nĠ¼@ÞÞź@ŎÞ°VɄɴжϼِ͈Ŏ"],
                encodeOffsets: [[81293, 39764]]
            }
        }, {
            type: "Feature",
            id: "6522",
            properties: {name: "哈密地区", cp: [93.7793, 42.9236], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@WnŐÆĶLĢ¦ţºźlxÅĸƽŚɄĮè@ô²ÞUĔƐńV°¯ĸX¦Ɛm̐bƒ»Ɇa΀ĢƐLˤȘÑnІǉĸÿn¯ĶaŎ¯ĢĕȘ¯°΂la¯¥ǕǔwˤӱlťО̻nŻmɃĕċţUw°WUóƨÅţķ°ýV±óÅǓéʉ¯ƽŁéōǖȁÝƏůǕw˹ǫȗǓƧǕVýé@ĬţLƧôͩɱŎɛK̏ÞɅôóK@²@°ōŘ¼lŦ¯ŰóƜÛlV¼ķ¼°kȰŰĠǬŚÝŎmĖ`@ÇÜn"],
                encodeOffsets: [[93387, 44539]]
            }
        }, {
            type: "Feature",
            id: "6529",
            properties: {name: "阿克苏地区", cp: [82.9797, 41.0229], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VÆxˌŎÞŎ°nȂÒ°²VĊ¯VğƾˍǬƨÞÞKÈÞĊVźôɆÞĢèŌôWČ²ŤVÞĸʶbl¯ôn_VÆĸlmÞnVź_ĸ¼ȮmǖéĸW°°ĸJkʠ¼Æw°¤ÈlxɆzČºĶI²ÆǔU°ô@Þ¦UnUĠ¼ŎÓĢxĠ_²ÇĊǬ°ȂamōçUÇW@¯öʓõʉX£ĶťnɻÇUˋmϙ¯˗ӑѡᩃaΗƒɜ°xWƴUxɃÒˣ¤ɅwğʉōóÝŹ±°ȗ@¯Æƒ²¼", "@@ōгwȁ¥Ƨ°ŹÑķV¼ÞêĊ»lĵm¦ÅW@ĀôÈźaɜxÈbÞÆĶIОŘnIÇŃÛÝĊÑĠƏ"],
                encodeOffsets: [[80022, 41294], [83914, 41474]]
            }
        }, {
            type: "Feature",
            id: "6543",
            properties: {name: "阿勒泰地区", cp: [88.2971, 47.0929], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ɲˣĊIÈ¥ÅU±Ċýkō°ĉƽó»ĶƽXóʵʵȯƑÅȁɅ¯ĉ@ÇሗK֛@@ˤV֜ʵрƒǬVĸƑŎ@ƆϯÑóķ@ʇ»ķ¦έmlÈĸĊX¼WźÛÞÝѸĢČþĀĊôάVö¼ĊUƨ°°èŎČUÜÆóôVôô²êȘlˌç°`n²ǬĊaÛ°±kğmm»@°ÝɆÛÅÇVaÝVm͔ğôÝÈb@n¯ÜUĢÑĊ@źīżWŤÈǖWôŁÆI²ÓƨL@ĊXmmÑÆ»ȰÑkĶō@ý°m¯"],
                encodeOffsets: [[92656, 48460]]
            }
        }, {
            type: "Feature",
            id: "6531",
            properties: {name: "喀什地区", cp: [77.168, 37.8534], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Č@°ĠôÓô@Ŏĉ@Ƴĸ@Ť£ĢlVôWVóřXĉŤêÞ@ƐÒĢÑlèÈV@ĠIk°ÆŘ@ÈÈĀ@ǶťÒğ@@ÒĉlŻ_@ƧĖÅĬōÆ@bźÞnƒlVÝĬWÆ¼ʇÝÅ@ÇÅÈwWóĉ±ğzĬČƨÆÝIĉÝ¯bÇÑĉ¯ʈV°xUŰĊ¤ƪ_ôÓɚI@lȚXȮŎlɴȘ՘¦ɲÆʈ_ɴźôÞʊŎĠɆxˤ£ɄÑVwXƳ¯wɛŹ٧çƧ¦ōُ͇еϻɃɳUÝ¯@ōÝŹ@Ý»mğ»ÝKkŁżřɅƅƒ¯ÆīĊ»ôVôĕÅUĉéV¹ƨémanÑ±ĕnwmwnÇÛyĉ¹ŹlŏkĵèķmōÞġKñÔċKÅèĉzômxȗÿƿI@þÅČÝKÝ°@¼ÈVº@ÅĢÆUċłnÝÆǕČĵJm£ÝJ¦@ĊxV°ƏLċ¼ǩ@m@ÅĢómÇÆğ¹ÇÆĖÞKxwô¦ÆÑÆL²ÆƾU±ŚÅŻĖ@ĬŤÈñ@ǔÇxÈÇƒ", "@@VÇţ°ğUĠ¯mk¯ó¥ķIġÿƏbĉa±ÒĸĀlKU_m»nwm@ÈŤ¦ĉbÞ°±Þżł̦°ĢŁVé"],
                encodeOffsets: [[76624, 39196], [81507, 40877]]
            }
        }, {
            type: "Feature",
            id: "6542",
            properties: {name: "塔城地区", cp: [86.6272, 45.8514], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ήnĸ¥ʈ¼ĸ@ôϰÒ@ƅƒōUķƑǫʶпU֛܃LګK@΋ĸ@Æ£ÞġÅĠċLVÝ»@Å»Ýnm¯»nŻĊ@nķŃ@¯ómóÛÝǟ¯aÝóȭ¥ōUmxĉbÇÑ@bUº¯X¯ÆƧbVÒĉnǕw¯°ƑVÇ@kx±UɱnÅK¯ƒĠǠU°ɜL@°xnĬĀŋŎÇLğϱÞέƜkôÅĀǕłĸĊŤUŰĢ°¦ȂϰÜɨ°x@°żǠÆƈČVĠ»ČL°ÇbĊÑ̐óÞlĶwÞɆVÞwǬxǪţÈ¼ÜLŐĶˢ@", "@@óKĵĀV͈ĉłƾǊÆŤzXl°ÆL²¼źôÈĢǔ¦lô°ɜÞʊĠğÅm»ʵƳƑʝȗīV¥¯ĉ°Ñ@ŃÅI»ĉmğnaċƨbVğwġ¯@UōaĉÝJğÑÆŎkŎÞĀlź¦"],
                encodeOffsets: [[87593, 48184], [86884, 45760]]
            }
        }, {
            type: "Feature",
            id: "6523",
            properties: {name: "昌吉回族自治州", cp: [89.6814, 44.4507], childNum: 7},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@መL@È°ĊȂɆƒÆĊ£ťôWÓɆbĢÅŎÆ¦ČÑW¥°ķU¯ƏŃVē±Ý@óçĭɃƾřÆķkwŹŤ¹ġ¥ĵKŏÅXmˍщwǓ¤Ƒ@wóōVķ£ɱġôÛa±ÒȁóèţIVƽ¼k¤ó¹ġJmx»ÝU²@ÅÆĸǫŎĊmŎǬ՘"], ["@@Þô°bÞǠôÜôn@°ĸńǶkł¼UÞKğČÆÝĢÅ¤ķ@@ΌڬL܄K@ˣȂ˭lĉÅW¥ĵVÆý@ŃÞēUŃȗƅ@ŹƩǕĉ»k»ÇVğóřXŻKƏċêȁèÛŎġͩń"]],
                encodeOffsets: [[[90113, 46080]], [[87638, 44579]]]
            }
        }, {
            type: "Feature",
            id: "6530",
            properties: {name: "克孜勒苏柯尔克孜自治州", cp: [74.6301, 39.5233], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ˎǫĠƽ°UUĉ¯±ȁÑm¯ÝōˋōwUÅ±»ÅƑ°Ș@²¯ɳʇ`ɱÅ¥՗ɳȗōkȭșW@kəJóÔƩ`ĉ£Vů¯wU°ʇĊÈÒ°aĊÞÞJÅċƧīĠyĊ²XôÇxÈÆÆ@ÞʈÅ»XÞīUƑkmŹÝ@aŎÅÆīƨĕ@ż`Ċk@ÑĠ@ŦÑ@ǵÇÿ@ÇÅŗl¯ğJ@ÇUkçġÒƏÑÝ@ţéWĊôŚUóXUġkţ¤ķ@@ƴōĊó@óÔğ¯ċ@@Ò¤kôˣŰ͓k»KX¯ċwƧôğɐÒôIVÆ¯UķǬķn¼ôb°ÒȰVVÈÞ°ĸó¤V¼°V°²êlĢÒUƨ¦ôȰƴĊVV¼ǖIċĊÞɜénČW˸ǸařÈw±īçĸ¤ĊôwĸUĢ¦éǖĬĀô¼lÞkÒ°x°ƆÞxÆV²ǔ»b°wÞȘ¥°nŎV@°ʠèŰȂb"],
                encodeOffsets: [[80269, 42396]]
            }
        }, {
            type: "Feature",
            id: "6521",
            properties: {name: "吐鲁番地区", cp: [89.6375, 42.4127], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ôKĉǪa²¼lÜô@ʠê°ĬôȂ²ÑÜbĢóɲĸ¤ŎUô@xƒǔ£ъxˎmÈÛ@_nĕÞōřǫğůlȯ¯ĸ»U»Ükôƛ°ůkť»Ŏŗ@¯@±͓óͿǓ@ķȁ¼Ϳ@Ƒ¼¯°ólġ¯xȗUġƑǩÒƧUÝ°˹Kóx@ǸōĬÅĬƑĠóƒǔêÆ°XÒʟŤUÇ¼ˋnn¼±V²°ȂUŌÝbʟǔɅô@żǬaҎÈ"],
                encodeOffsets: [[90248, 44371]]
            }
        }, {
            type: "Feature",
            id: "6540",
            properties: {name: "伊犁哈萨克自治州", cp: [82.5513, 43.5498], childNum: 10},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ĉÆŘȁ̐mÞ¯ĀX°±¼@ƾ¯ƴ°ŎÝþŋ¦WÜÞbȂĉźUÇmwVUȂóô@ȰÝ΀nÆJnƾʠŌLČóǪ¯¥ǔaǖŌaôÝĢLxÆLɲm²VlwÈ@Uƒ°¯ǖxĊmUÑƨa°Å°WV¹aÇɃÈm¥°¯ŹóĸķǫUm»Å¼ÇVɱlÝŋnķÇÝX¯ͩÇɳaÝ`±_U±ĵnWa@ĸóķ¯ǓV±ÅĵJċ¹ɅykwÇ¯£Åxʟ»lķI¯X¯ķêǕȭnķ»Ź`±kÞ@Ýô@Þ°xŤŎIƨÆUxō¯²ǔĬǬlUŚ"], ["@@ÞĀlź¦¯ĸŤKÞċƨbVğwġ¯@ţƽJ"]],
                encodeOffsets: [[[82722, 44337]], [[86817, 45456]]]
            }
        }, {
            type: "Feature",
            id: "6527",
            properties: {name: "博尔塔拉蒙古自治州", cp: [81.8481, 44.6979], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ήƛϲÝĠÈKŌōÿmīw@¯ɛKV¯ğǟ°ƑwġKóÞŋbǕǓb¦ǩ°ċôŋKʟƽmÅImͿȯÞó@ȁôUVnxÈŹVȁĊÝabŻ£¯°lóxȂŤĸkĊÞyĊêĊmĢxVƨÈĠXΘÆĠÔźɆţ°LXƾŤŤb"],
                encodeOffsets: [[84555, 46311]]
            }
        }, {
            type: "Feature",
            id: "6501",
            properties: {name: "乌鲁木齐市", cp: [87.9236, 43.5883], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@WôŚUĠÈl¼Ċ¼ƪǖ@źȘƆ@ýlÜXVŘÞ¦V¼kĖóÒèkĊȁˮ֜@ǫ՗nōĉǬōķÆÅ@±ÞV¼nwĢIôºl£ƾ»UŤJôçó¯īʟéó@kÛ±»ǩbĊóLҍÇǫb@ŻɆóʠǓaŋÞȁVʉłĉbĉɅô"],
                encodeOffsets: [[88887, 44146]]
            }
        }, {
            type: "Feature",
            id: "6502",
            properties: {name: "克拉玛依市", cp: [85.2869, 45.5054], childNum: 2},
            geometry: {
                type: "MultiPolygon",
                coordinates: [["@@ɜÞʊĊýVaÅm»ʵƳƑʝȗīV¥¯ĉ°Ñ@ŃÅI»ĉmğnaÝţL°ķóKĵĀV͈ĉłƾǊÆŤzXl°ÆL²¼źôÈĢǔ¦lô°"], ["@@ƾIŤ@UUwōaĉÝJğÑÆŎkŎ"]],
                encodeOffsets: [[[87424, 47245]], [[86817, 45456]]]
            }
        }, {
            type: "Feature",
            id: "659002",
            properties: {name: "阿拉尔市", cp: [81.2769, 40.6549], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nIÇŃÛÝĊÑĠƏōгwȁ¥Ƨ°ŹÑķV¼ÞêĊ»lĵm¦ÅW@ĀôÈźaɜxÈbÞÆĶIОŘ"],
                encodeOffsets: [[83824, 41929]]
            }
        }, {
            type: "Feature",
            id: "659003",
            properties: {name: "图木舒克市", cp: [79.1345, 39.8749], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VéVÇţ°ğUĠ¯mk¯ó¥ķIġÿƏbĉa±ÒĸĀlKU_m»nwm@ÈŤ¦ĉbÞ°±Þżł̦°ĢŁ"],
                encodeOffsets: [[81496, 40962]]
            }
        }, {
            type: "Feature",
            id: "659004",
            properties: {name: "五家渠市", cp: [87.5391, 44.3024], childNum: 1},
            geometry: {
                type: "Polygon",
                coordinates: ["@@çôÑlĕU»¥ÝUŗWkÛ@þVńÝĔ@ńÅþĶUX¦Æ"],
                encodeOffsets: [[89674, 45636]]
            }
        }, {
            type: "Feature",
            id: "659001",
            properties: {name: "石河子市", cp: [86.0229, 44.2914], childNum: 1},
            geometry: {type: "Polygon", coordinates: ["@@lŁǵmĉ@mż¼n°ÞmÆ¼@"], encodeOffsets: [[88178, 45529]]}
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/xi_zang_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "5424",
            properties: {name: "那曲地区", cp: [88.1982, 33.3215], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ƨʔĸbÜºÞwnxźbÞ°ô@ĶĸIÈ¼ĊJŎÈôUÝƒ¤ǔLÞŎ@ĢȘblôLÇźçÈ¤ôL¥ÞIÞ¯ĶxʊťƨƿÑĉXVķŦ¯ȂKÇǕÑ¯IU£¯Óƿ£VĕÅÞÿÆwƑ£ǖxÞĕ±ÇÝaUÑÈU¯UōÈÝwWŁĵ±ÝóĢÿ°IÞ±mÅĢ¯mÿ¥°UnÑŤĢĕĶwǬŻͪwŎ¼źÇĢĠĕˎŁ°óƨ¼Èam@¥°wǔǖ°ƨÇŤġƨŎŃôbÈÛŎĊ°@Ġw²ÑÞJÆÆb²°êĊUÞlÈ²VÈKĊÒĸĉ»ÅôťUÅÇk¯@ÇÑklÇÅlĢVÑó@°@ÛĸV¯ÇĊn¯Uĕƽ¯m¯bÈ@Ò°Ĭbĵ¼kxķýÇJk£ÝaUÑÅóĶǟkÓʉnĉÝ¼Ƒó»Þmn£mČ¯@ȮÿV¯ĸk@Ýów»ğġ±ǓLōV¼Əèķĉè±b@ÒţUÑóakl£Ó@¯L@ÇlUóȁ¯aġÈÅĕÝLķ¯Ė¯@WĬxÒÈnW°ţôU²ǓÓġ²V°¯ôǔÝLċk»Ý»Ý¯ÞVwÛÝÇōͩÈĉċ»ĉm¯£W¥ţKkóġƏW@¯±kōÈb@ÒÇaÆ¯akóÛÇ¦Ýa¯Ýĉ@Ç»ÛmǓxķƛ¯lVĀÅÞġbÇJUÅVĖƑWzō»ōWn@è¯ÞóVkwƩnkźÇÞÒÞ¯ýğÇUxÆÈnè±bĉÝ»ÈŃwwÞ@m»ÈV@ýÇ°ķxaÝ¯Xċ¥ÈóW@ôkxlnxVÈóĊkŤġ¼@°¯ŰƑL̻Ű±ŎÝVÞVÇÞÅÇakƞ@èğŎĸżƾ°ÒLÞôĠKȰĖźVÈÒĠ¤VôUÈþťL@ôǬÞlÜÈnÇÒUŚ@ĊƨW°°X@ČÇþƴĉÒķ¦@ĢôWĀôłUÞĢǬź°¼@ôV°bUÆnzm¤ƽĸÈ"],
                encodeOffsets: [[88133, 36721]]
            }
        }, {
            type: "Feature",
            id: "5425",
            properties: {name: "阿里地区", cp: [82.3645, 32.7667], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Çƾķn£myVÅaU¯ó@¯»ŹġǫVÝóŁXÿġó@ĸ¥ĊÑƳÈý@ċW¯X¯ĉƧ@VřÈÑÇmkÛǫÝ@óŦKÇýVUó£ğÇÑŹUȯĕğLÝóK¯ÑƽķŻĠō@çlƝÈbÆÈÝUÝÞU²ō̼ůƒK°ů@¯UK±ĊƧbōÇmçÈġóÅóbźó¥kīÆ¯ólçKôĵUÅVŃķ¥nÅŏm¯¹Å»@ÑÇóxÝkʇȤU¤ķb@ƒ¯ĊÇx¯ĸĉKm°Āk¦lKnĬȀƾÛ¦WÆÅmǊĉ°ōUţ¤UŎ°ŎKÞłÆǓ¦Þř¯bmUÝl¯Umğl¯£șwÅǫaÝnĉĶk@¯Kō»ĉnaÞ»ťnkmlĸ¥UÅŻkÑťĉVôó°LôīĠUÿĉǕÅz±K¤²ō¤¯Ė¯UÝ¥VĵóÈťÝwķÈÑk¤óWýĵĕVĠVóǓķ°k±VU±ţ¦UǟÝÅJVÑ¥XUċUÅlÛƆǕÆȗƆ¯wŏÞÅ@ĉlÝóÒnUôÅlxólÝôÛ±LÛôÝL@ġ¯X¯ÇUÅ¼óaó¤¼XÒġŎóLk¦ôÅ¼ĸĠ¼KġƆô¦ÆƑÔĉĶ¯ImÒ°¦n°¯ÞlÝČnƒÒKĠÞĕklýƾťôIĖŤÒnƜm¼¯lnżóÞ@Ůó¦ôƽĖċŚn°Ý°ôÈUƜblÞó@ǖô°UÈƆ°XþôôlѢ²Ėm¦°@¤XĊblÜzkºƒĖmXŎWVóÞn°lĠxȚa°»żLźb@Æ°XĠÝȚxĊĕŤaȚ°È@@èŤ¦Ü¼WÞkÈ@V°lŤkŎ±²¦ƐUǉ°aÈÑŎbĢŎbÆ¥ÞIȘlôVÈUbkɲĶnmnXb̼òƾĖŎ@ĢȂÑôÓĠĖʊĊÔ"],
                encodeOffsets: [[88133, 36721]]
            }
        }, {
            type: "Feature",
            id: "5423",
            properties: {name: "日喀则地区", cp: [86.2427, 29.5093], childNum: 18},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ĶĖXþôl£ÒĸÇÞxÇŦôUĶÞ¦°V°ĕŎ£±£²LÆyĊǖĀğVóĬ¯KóôUĊŦlÒżVÆķ¦klnŦmÝ¼bĊmŎ¼L@°lĊĵÞmǬbÆȚx°¤Ġkn°VÞkVn°aŚÝǔ¥ÅÝŁōL¯ōVŤ£ŎVĊ¯nǉÆXÅÜ¥ǿƽmīLkl¥ÿn¯ĊL°ķÈw°ĉ@ƑĸaV£ʈȣÞlôwÈ@Ò¼Æ°ºŐnmÆĸ¦UńÆVóĶLèôkÅ°lĬ¦ŹôôaÆôÇĢnèŎÈƨaĉ²VLĢ»lţôĉUÇwkmlw@óôXÇČ¦°WÞbwĸÈ¯@þÇUn¼Ý@xxÇńÞ¼Ċ²amçÅÇVwĠÈþ°ÝÑÈÝlŹƪmlxôU°Ý@çmXŎŎ¼yƒXĕÆUVÈIĢaÆÝUÿ°kĸƜǔwnÜÈ¼Ċ@Þ°ÞbÈ¥Üôl°bÅÈb@ÑaÇ¯UU¯Vġ»¯aV¯Ç°ÅmnÑŤçǬVǬ±ĉ¯¥Vĕ¯Ýk£ōw@±ġÛ°ÇVÑ@Ûa@ČLƳÇa¯¤ÝIĵ¼U¥ƿōķÅţŻókÝóĕ¥¯U»Æ£X¯ġŃÛkÝ°V°ó¼¯èWôÞĖȎkĀƧĀówm¥¯JÅ¹ÝJÝōVVÅaÝƑ@ğŭÇ¯_ĵVnxÅónĵxÇĖĉVÝÈğVÒó¯±Żĉ£ķÆÅLǈĉýţÛ¯VnV¤ÝÈ@°ÅÞÝ¤ŰğŁm¦ÝxóK¥ɱÈUĠôêVôÛ¼ÇWÝçĵaō¦óĖƧlÇĢƑnŎÇV¼¼ºÛ@m¦ƽĉmm¯ÝKÛç¯bŏłĬb¼ÅLmxť°ÅUÝXkÝmĉ¦W¯KÒknÝaVÝè¯KɅńÝKnÞ¯¼"],
                encodeOffsets: [[84117, 30927]]
            }
        }, {
            type: "Feature",
            id: "5426",
            properties: {name: "林芝地区", cp: [95.4602, 29.1138], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VÈłVôÈk@°K@Ôk¤lôbVÒŤ@Ñ²açĸĊƐçU»ŎǔKĢ²Ġ¼ôx@ÞlƨĬUl¯ÈLVÞJ°ÜnʊwÜbXêVÞ¯°anaU°wÆ¼ɴÑWÑ°mÈýÈam¥Þ£Ť@¥ôblÞĢź¥ôxÈÅmÝĕÅV»ĉōŤōnó»ÈīķIUĠÑ°ġĸLÞ¯VÒÆ@Āb¼WôÈ@V¼ôóŤKÈÑU»wVǫżnWÒÈx¼lŦ£ĊōŤx²¯@ÆU¯çÆ@¤°£é°k°lůÈó@¯ŤÇÈĉkkÿó¥ÝXķÑÜ@ÒóŚÝ¯°ĉówÇ±¦ÅJUÒĉĀķw¯°mĖ¯±akxÝÅn»lÑK@¯lU¯UVÑ¯óĊ¯mōğVǓƅÞWÝÈÛ@ƿô¯ÜġzÅþ¯ólmôʇġĊÅUͿřŏȁˋŁóÇˡōƧÇbw°Ķôk¦ÒnUþġÒÔkǔķèó@²@ŘōńĵyzġaÝ¤ÅI¤Ƀť¦ğÑ¯¤ķbó¯ó±U²°¤ČÜVnÈÆŚŎ°ôĢþÆzèVĀÇĀÇXŹÑ¯¤ówċķk¦łUÒġzÇ@ÆÝx@²Þ@Æ¤Uô¦U°xU"],
                encodeOffsets: [[94737, 30809]]
            }
        }, {
            type: "Feature",
            id: "5421",
            properties: {name: "昌都地区", cp: [97.0203, 30.7068], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@VĖm°ĉÈU°ķÜ¯@@ôUÒġkÆkÈlÒ@Èl°ÈVÆóŦÆ¼aÅĢɄwnōw@¥Ŏ¦°ŹÞmV°wnÿwwÝw@¯mÞŗ°wĠĸkÞğlĔ²¦°@ĕĸwVóal@nĢÇĊn°@¦źUXçǔůĸVÆKÈÝĠ²ÅĔô@lÈ_mzǖlaU¼ôwV°¯¦ĬÈal@ČÇ¼nIxô»ɜ@ƨ¥ɆŁŃǪȁkƛƨȍʊȡóĭ@ÈÇVůÞĸƅmēƨťÅÈʉVǵ°ġVŭÅɧ°ÿnɛ£mķ²ŃóÑUĉ°mÇ»¯@mxUĀ¯èţ°ȁÝçġU¯ÆÇţÈ@°ÇôŰ¯k¯lê¯¤£Å@èV°Å@±°ţwĉŎť¤k»ÇwXÑŻmUǬxV¼ÇÒţLóôU»Ç@Xó»a@ÿÅUÑÝ°ķK¯ĢğÒVĸJÇĬ¼môţŎĊŎU¼ÆĖnÞÇÆówŹ¦ġkÝóa¦ţ@Ý¤n¦ÇbÇþ¯nXÒɳÒÅ»¯xVmbb¯Ý°UWéÛaxʉÛm¯ÝIUÇKk°VƧīķU°ȭĀ@ċ°nm¤Ýnô¼ƒÞ»ĊʊmlÔĵǠÆôVÒÞbl¤ÈIĸþlw»Ķa¯ī@ÑÇ°anƾ°"],
                encodeOffsets: [[97302, 31917]]
            }
        }, {
            type: "Feature",
            id: "5422",
            properties: {name: "山南地区", cp: [92.2083, 28.3392], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°ÞUĖ°¦²ĊôÇÜLǖĀɜȘŰÞLĸźêÞ@UÜUŤ°ɞ¯Ü°WŦĀmŎ¦ĢyVÑŁl¥Čĸôx°£źÒWÈÿÈUÿçÅyýóġō¯řÅmÇÛUċ¯£V±²°ôôĸa°£ĠÒŦ¥Ʉ£ÆJÞ£ĢbyĶzŎŃ@ŗ±ô@ĸçlǓÓĢÑVýmÑl¥ĵó¯̻̥ƛǫÝһÇƧĉyţ¼ҍēVĶĉŎ°ĸmÞVÝĸÒÛaċóŹĖèÈÈl¼k¤ÝX@`Þŏ¼Æō¼ÇçĉKUÝÝ£ğ¤@¦ġl¯Òġĉ¯ómóxÝÞğVƴċK@b@ÜUÒ¯ÈĢÜ@²xŎl¤"],
                encodeOffsets: [[92363, 29672]]
            }
        }, {
            type: "Feature",
            id: "5401",
            properties: {name: "拉萨市", cp: [91.1865, 30.1465], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ŏ²l@°XĢƐlôŤLX¦°¤ĊnČ¼ÇĊŎͪÞÈÜxU°ÝÞÞ¼¼lČÞKǓ°óU¯Ģ±ǔÔV±ŤóX¯ÇmÑwXī°@°ĕĸÞKÆĖĢÇ°bȂÇŁUV¯wVó¥VÅ£Ý@@±ÞwÅÈ@¥nōťÿ¯XÛɝ°ţ¯ÛVVÝ@ŹéķÝKȗůɛǕÿÛKóÈǫǫUţèmÒn¯Æ°ÈU°b¼UĢV°°V"],
                encodeOffsets: [[92059, 30696]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/yun_nan_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "5308",
            properties: {name: "普洱市", cp: [100.7446, 23.4229], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Uô²a@²²Ķ¥V°Ķ²bl¤kVxl@°Ś²@y@ô¦¯@xxVxUVbVÜm¼ŎĢmºXXWÆ@ĀmmXU°ÅÒm¼Þx°w@°XêĠ°»nV°Ul@k@V±ôī@£ČŃÆ£KÞý@¥k@ya@nWVUVwm£Jknm@wmknXX¥mUUlUnb¯°nkVInlIUw°nmk@@mlanXlanmk@wVWUw_@éĠanmUaÜ£mX¥¯@@óUmÝ¯¯ÞÝlKnxô£»»ĠJ°aVUÝÿV¥ÛbI@wmón¯yÛL@WkÅmÈ`IWa¯K@¯mUnmaXmbmak¯ĢÒÝm¯mV¯KÇb¯KÛWWX@aVknċLUWVkXóW@ka@ób¯Uwmb¥UUlaU¥U£maķKXkmÝ@kwmÑ¯k±ċbUUVakaġ¦kL@`a¯xmÅLUW@ċnÅUV°LkL@b°°@¤²nôôkl°kèÒÈzV¤ÈWôônV@¦@¼Ux"],
                encodeOffsets: [[101903, 23637]]
            }
        }, {
            type: "Feature",
            id: "5325",
            properties: {name: "红河哈尼族彝族自治州", cp: [103.0408, 23.6041], childNum: 13},
            geometry: {
                type: "Polygon",
                coordinates: ["@@°°nÞôV@°@¦WnÛ¤Vbmnğb@ê`VxUX@xÆÞUnnWÞĸĢÈ@Çè@zÛÜWÅêl²KnV¯ĖĊx@bk@@°JÆ£Èblnnm°nlUkVUUwVmKnnVÞxVLX¥laX@@xl@VzÈVmk@b°ÈĸmV¦`WXbUbbX¼°x@aVVkn@lþnXUlVxŤÅyIUkaIŎĊ@lXx@bz@ô¥_V@ln@ôy@al_l`nmÈ»@kmXwWKU¯»aÅ@wmUÝKUaUUwW@w²»@kÆV£mm£VKkÑV@@»nw¥@kÆnllIVlnLVakalknJWmnaUaVÑVVÞn¥m@¯Uÿl@VçaXaV¯UyVLVk@nJlXLlkxlbla²Òl@nVJVkxKlkUaVķÝÑU@Åm¯@±Uó°ğńķĠmUÑ@Ç¯¯Å¼@nml@°¯¯`@w£@¯Çk@»nmċ¯U»I¯LÇĶÛn@bó°Uwm¯UmÇ¯aI@ykIVU¯bIğ¼¼ó¤mwkLÝÞ"],
                encodeOffsets: [[104243, 23429]]
            }
        }, {
            type: "Feature",
            id: "5326",
            properties: {name: "文山壮族苗族自治州", cp: [104.8865, 23.5712], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@wô@²¯maUmôUÆx@XbÞInlVUVwJVaUK°¥xmÞXnlKlnna°@ĊČÆwUmnkl@°£nyn@VV@Vak@@kÞÝbmx°Vnw°klÞInĖÞVlKl@Xa°KlVU@JnxU@ÈĢbUKlm@ak_wanWUk°l»k@Wk@lwU_@UalóU¥ÇnkJW@mVXx±bK@nV±a@Åa£ÝK²WknamKknÇk¯aVV¯ĀUÒ¥I@mm¯¯xÅW@@`k@ó»UU¯lm£ÅWlĵw@mmwÅmWU@y±UxmwU¯U¥Ý¥¯£m@kÇVUV°VbklLwUlUImk@±ÑkbkalwkWKkmI@UlUKVzU°WbbUè@kVĀ°@nm¦ÝUUUÒVbmbXnmIkllbUbmKUkkJmkÅ@l¦mx@¼U@lÒULn¤nU¤Å@l±¼@xXxVVVbÞLVn@xÆb°¼V"],
                encodeOffsets: [[106504, 25037]]
            }
        }, {
            type: "Feature",
            id: "5303",
            properties: {name: "曲靖市", cp: [103.9417, 25.7025], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@È¦lKÞĕUV¯Um¯ÇVUnVVUĉnĊÇƾLn°°ÈJÆw@lbÞa¦VXJ°¯W¯aÞJVkUa@lKnÅmWUk¯a¯»@m±@ÑkkbWWX_WÓU»_lkÑm@U»m@l@IWċn¯l@VanVUVUVwVxKÈVmUē@n@VÝÆLwVVwnVlmkUVÑÇ°ka@kÿÝaÞUl£ċĕX±±ĉa@UnVnalónk@wlUVmkÝJaW@ÅwóVVnnb±°@óxXLWxn@lÇ¼nmk_k`@bózm@kU@`¦ó@nW@ÜÅXWw@yb¦@ÒlnUb@xlÜk@²Ç@U¯bmy@kV@bb¦U`lLVx@bLl¼Þ¤@°VVÞU@WÞUbJ@nn@lnnmxUUUbK@ÇwklkUVWakn@lbU@@ULVxkKUn°¯Ò@¼km¦m@klȰ@lUl¦@Vl°wnnþĊUÆbUxbVĖU°annaVal@@b"],
                encodeOffsets: [[106099, 27653]]
            }
        }, {
            type: "Feature",
            id: "5323",
            properties: {name: "楚雄彝族自治州", cp: [101.6016, 25.3619], childNum: 10},
            geometry: {
                type: "Polygon",
                coordinates: ["@@mÒXU`Wn@Xl±¦Uxnbl°knmKUxxVôUx°¼ôÒÈ°JlnÞKĠW°¦Vx²JVw_°¥@UV@@wnymknK¯I@²b°£V¥wUV¤nLkÆJÈwôô°l»Č¯ġVUU@@°ÝXl@U»°Å@U¯@w±¯VmUUlm@mÑnIVyUwmak£Vwm±@Çw@n@UxkwlÇnLmkÅ@±kka@kóJV¯Ç»U£lw¯Xalbl¥¯UX@aUaÈL@ÇVIVkaU¯mmakLWkUJ¯Umxn@kUx¯xmWÅīÝkkbŤbkxWmXwWk¯wKkLÅ¤ċń@¤óĬU²@@lk¯VmU¯¼@xV@k°l°kbU°nmVnU@°UVèÞÆbUÒÞnU¦V¼lô@Vl"],
                encodeOffsets: [[103433, 26196]]
            }
        }, {
            type: "Feature",
            id: "5329",
            properties: {name: "大理白族自治州", cp: [99.9536, 25.6805], childNum: 12},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lbKVIUa@²m@bxôÒÜxXLmbnl@K°¼kUôxôlV¦nJUÆnm@xÆwbXÆôôLUVwôK@wlmaVw@WknmIUmlnJla@_@kÝmKUaÑm¯Xw°aUaVl»²JVbÆJkôĶĀ²VVkmbVwUówVwnLlmk¯maVw²¥Wk@XmV_WnÑUk@kó»UV¥ÝmVÑÅaÝUçV@¯VUmn¯mVlak¯l¯U@@wğWé¯@¯xÝw¯¯Jċa¯U¥mLU¤bÞȤbÇLWUwmIUVW¼kb`UVb¯L±ĊÛkÿÝKkwKţêUĉþÈV¯ÞVbU°KVk²ÝmImV@kmUkVxm¯KXÈķJU¦V°ULWxL@môb@bkx±LnVUVLnkÜWnwlLÅƒmW@kkJU_VWĊÞ"],
                encodeOffsets: [[101408, 26770]]
            }
        }, {
            type: "Feature",
            id: "5309",
            properties: {name: "临沧市", cp: [99.613, 24.0546], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@xĢl`²X°Vx@x°Þ°KXağUÑWbnIl`X²°bxl°V@xVxk¦mbl@xXVÆzX¤Æk°kx@lźêlaX»VUnJVxXÈKaÝȣaV£nKV¦°Čb°I°n»ÆÑV¯nWn@ÿXÅWWn¹ġōn»ÛUaUVUww@w°ó¥@z±@ř¯@kUwlk£±aĵ¯Uĵ¦±±@bó±VÝ@ó¤w¯I@mÅóm±X¯IólK@°UllbzkKlln@@ÔºUmVk²ôÒxŎUVóLbmÈnmbnlax@z@Æ¦k"],
                encodeOffsets: [[101251, 24734]]
            }
        }, {
            type: "Feature",
            id: "5334",
            properties: {name: "迪庆藏族自治州", cp: [99.4592, 27.9327], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@WXw@akk@yk°īX¥Uóķ¯w@n»UaVaUÛ¯mV¼kÞċô@n¯xÛÒmV¯Ô@x@kwmÅa@UaÝ¯VÅyVa@ÿn»ÝVmankmmÞÅô@n£±ğzÇmU¦VmnÜmbn@°nV@xmzÅ@mºV¦k°ln¤¼õôn@xkÆIUxU@Ť¦VmVkmkXW¤XzVx@Æx¼Þ¯b@lVĸÞVm¼Xm¦VÞ@Æ¹Vón¥ÆKnKX¯x@èĊÈ±łXaÆxnlV@UÛlȻkğV¥m²ǉmÅÞĕƒƛm°ÆmX¤mznÆV¦ÞVVb°bnÞWbn°l@VÈ@VĵĊ±@óInxÆw¥@£ÞW¯ĸ£UUKk±akkkbmWmÈķaÆÇUÈÆW@wmknmU¯"],
                encodeOffsets: [[102702, 28401]]
            }
        }, {
            type: "Feature",
            id: "5306",
            properties: {name: "昭通市", cp: [104.0955, 27.6031], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@mnK@wmUÅ¥móXǓŏmX@VmL@xţnk@mlUŻÒğŋ@L@mmLkm@bXÅW¼ka¯lÇŹ¯aÇ»ÝÝ_@m@@a@UklwUm@ak@bUmbmbV¯ĕUaVwÅaĉVmým¯xUk@k¥VUX¤VÈm`@ńÇÜ@ĀknĔkƞÆĠÞUVôƆÞI@UxÆ¦nl@ĊĊnxUÒ°¦Vb¯WUnWIml@xnUbô¤¼ÈxlI»KV@ÈÔJkUĖ±ÆVb@nVÜVUVLwĠlknĠ@nx°¥Æ²mUw@mmÅUl¯UÑÑUmLllIl±@VkwW@w°@U»kUóI°»ĢÑL`nUĠ²lmbôV@nJUxÆ¦X¦l@ŎUV@lVKVÅV£UaÞUnW@¯VU@ó"],
                encodeOffsets: [[107787, 28244]]
            }
        }, {
            type: "Feature",
            id: "5301",
            properties: {name: "昆明市", cp: [102.9199, 25.4663], childNum: 11},
            geometry: {
                type: "Polygon",
                coordinates: ["@@n@VkVUn²°@x°V@¯ÆV¼k@WÞ¯@@VVUĢċ°k¼VĊx¤Ōx°mVkÑÈL°x°X°VmĊLVxUĖ°bX¦VW@kȯlkn@¥ln@»°Ñ¯VmlLUwVK@V@ka@lmXbUlVlkÈx@LVaVVwnmm@km@mIVaÝ@XVUÝ¯U@Ý£k»K@aUwkKV_¥a@alU@nz°aVÈ@@±lÛk@wVakm@Ñ¥az@XxÆW@ÛX@m@y@aWw@kōĉJlbVJzţÆUwVkmWkým@UlU@b¯wVºUVUêĠXUaUbVĊUWXUmkKWnUUUVVVÝ@kk±¯Lk±WkXlVkl@wXbmLVUIVmk@Ubma@kkaVKUkmlXLWnJ¯ÒĊ°@zkºlLUŤn@@nô@lÆnmKkÈlxVw@@mÈx@n²Uxl¤nbVxUzmJÒn"],
                encodeOffsets: [[104828, 25999]]
            }
        }, {
            type: "Feature",
            id: "5307",
            properties: {name: "丽江市", cp: [100.448, 26.955], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@l@@w°ÓUnÜÑ°w@mČóÝlU»n°VÜUbVbm¼@°xôĸVW¦¯Ĭl@zll@bWxXaX@ÆĠÆaXwl@XaÆ¦n¼Jn@mnKW¯È»V¯°akVanXVwl@VyUĕVUbÈīlaUk°k¯l²VUkƛô@I@mVwĊaVakaÆbUVLaXIWKUwaWÑÅKUaVk°@Uw¯¥XğÝLkm¯IÇóÑ¯»anUl±UĵÿlóÅIaU±Ik¼UVb¯bWxn°ÒVbnLlÞ@@`kbmIkVnJmnXl@Uxbkn@xóLUxVKóóÅWaÅxw@nÅmVôXLlVU¤b¦m¼@ĀbUzUÆ°ÞVb@Æbnx"],
                encodeOffsets: [[101937, 28227]]
            }
        }, {
            type: "Feature",
            id: "5328",
            properties: {name: "西双版纳傣族自治州", cp: [100.8984, 21.8628], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@l²°nÒlxÞ@nWlLĸnbV¤V¦kbVV¦nax°Vôa@b@lôXlWUVXČKlmU@bWXXÜ°LÈa°LnU°ÞnÑġ°lnba¯¯KWó@kmK@UĉV@k°VV¹a@y_ċl_nÓlL@anI@óWl£VUlkĕlKVwU@kVam¯ÅL@bÝk@VnUbÇbÝwÅ@ċ¥¯lk¼ÅÒ°b@¦nlUn@ÇVmÆbWôU@ÝÅōm¯aUmkWWw@±n¯UèaL¯mLkwl@°mnÈÒ¯ów@VxĀU¤°Į°Xl"],
                encodeOffsets: [[102376, 22579]]
            }
        }, {
            type: "Feature",
            id: "5305",
            properties: {name: "保山市", cp: [99.0637, 24.9884], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@X°Il@¦È¼m¼ÞaÞÅlÈxV¼lVôÈÆlLÞ£ÈºlkUUw¯UĕVwĊ@n¦mlnVĸIWÇ°LnUwlVn@lnUnJÞl±U¯LVUa°ÝUÇĊýVŤéLlxÞLĀÜl²ĉ°KUaV_Źé@klw¯lÅW£ÅyUW@wknal¥Uw@wUk¯w¯aW±k_mJaXVÒĠWb¯L¯Ý@wwU¯±Wk_ġwwōKmb@¤bk°lĖôUJVnÅlťU¯°VbnbWxXmÞWUĀLyWzÛKmbUxVKknÝkVĀċ¤Ux@¯m@¦"],
                encodeOffsets: [[100440, 25943]]
            }
        }, {
            type: "Feature",
            id: "5304",
            properties: {name: "玉溪市", cp: [101.9312, 23.8898], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lL°xXlWxXnlwaţlaÞlÆĬnX°wVwl@mnw°VVIXllKbnnV°lbUUJ@ÈÇKVb@bW°Vk¦kaWb°kxV¤È¼U°ôI@llbl²@@ó@mm@VţkKl¹@yĉ¯°ÑIXmWKnklVULlb@lnbVal@UnVJUnKWax@lkkUlW²XlK°l²@lÞUUUVVVXmlLVnXWVUĉVaVbWğVéUVU¹W»aVaaWX_U¥nÇķ¯@alUnÇUyk@@wW@kbW¦UKÝwUmmLUnVxUVVlk¯mmnmkÇaÅ¤¯I@l@@aĉw°ĕmUL±kÆéXÜÛ@yÈç@ÇġÝķXmmÝVÅlmnkbmWkb@nl@nm¯VxkJmUJml¯°makVVnV¦WWmnl@xmnlI¤nxUVUmX@b@zl@¦Ýþ"],
                encodeOffsets: [[103703, 24874]]
            }
        }, {
            type: "Feature",
            id: "5333",
            properties: {name: "怒江傈僳族自治州", cp: [99.1516, 26.5594], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@WyX£lWlnnUU¥@ţVVwJlÅ@wmöó»£kml¯U¥n¹Æ@ny@wmU@¯mnamÛnUV¥ÈnĠy²m¤@ÆónÝnmlnbÞU¥aV£kUKWómIU¥ókwVól»¯Lk@mnaWKÛwóÑw@a±n@VbUJLkaÝXĉUV`lI@lnXÆƑkKmxÛXmlUKVmU²Klw@aaó@nKXwVKU¯V¥mUnkm¥ĉ@UxVĖ°VxVklmÞkKWĀkVWnl°Lnm@°UxlV@nk¦JVÈ°VÒ@nX°@ÆlUômlnô²nxmłnVV¯x@Èm°XblVUl°@xkXU¤WXXWXÆmkÅJmÞw±bxUīkKmÅVUĖÝèVkx@lXlnk¤LkĖk¦xUL°¯Ė@LnK@b°xVI¥Ua°Ñ@»nm@¹KŎÞÈWln²n"],
                encodeOffsets: [[101071, 28891]]
            }
        }, {
            type: "Feature",
            id: "5331",
            properties: {name: "德宏傣族景颇族自治州", cp: [98.1299, 24.5874], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@¥n@°@VwČ£ÿUlÞlmULVwnaÜLXyzKVÿXÝnWXwmaUa°¯VŦÆkUmVIókĕl¯a@£nama@¯m¯ó@óyţbġkÅm±ÛammVkLwU`Wk@VkUmÅlUUKmbkkUVUw¦ó°¼bn°ô¦lºz@x¯@U°nU¤ţU°VƆ@ÈmlnzÞl°¦ÆaxUxLkxWƒn@²ŰW@°ÈXl°Llx"],
                encodeOffsets: [[100440, 25943]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/mapData/geoJson/zhe_jiang_geo", [], function () {
    return {
        type: "FeatureCollection",
        features: [{
            type: "Feature",
            id: "3311",
            properties: {name: "丽水市", cp: [119.5642, 28.1854], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@VbVl@XnUXKV@¦nxlUXVnKVmnLUV@bn¤lLXK²`nnlJXIVJIVnn°KnnVll@VLXWV@UkVaVKzV@VVaUK@U»VUl@@WnUU@wVLn@Vwl@XW°LVbn@VU@Xl`@XnKVbkl@XVJlUnlVxlL@lnXl@VUnV°°@aUVLXblWVXn@VVUV@L¤VLVUVbnalLUUVX_laVaWVzXKV@@a@KUmImmXama@kU@yVIUKaVa@kXK@aWU@VIUmW@kkVmU@VwUa@K@k@U`@kUKVk@UV@VaUm²Vy@klUUWUkVmUa@_KVaXaXmU@mUlWkaUX@mmkL@wJnVVÅbWKXa@@I@aJUUÇ@VULW@akLmb@K@aXXw@mVmUVkUy@£@aU@@VkUWm@kUKXUWU_mW@wkkmJUUkLWWUXW@IkJ@k@mW_kÓ_UlLm@I@aUa¯m@ka¯LUJ@mVVxUba@LUKkXbm@Uak@@a@Um`IUbUJ@nUVW@@LnVV@lUbVlUX@`@blXklWUmXlm¦U@@V¯bml@@nUb@llnn@VbX@lV@UVULmU@JVnbVbkbVWxU@@nUVk@"],
                encodeOffsets: [[121546, 28992]]
            }
        }, {
            type: "Feature",
            id: "3301",
            properties: {name: "杭州市", cp: [119.5313, 29.8773], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@X@l°KXXlWb@²`bIX`l@@bWl@n@VnLUV@V@°¦@l@XVlU@@xVbUb@Vkb@@XVJVzJ@LÞ@VmLUxUJ@LUVxbxXUl@VaÈwbaÞa@Vl@XUVx@V@VLlbnVal@lbVnnLnKnL@VlbVJXalIb@KUU@mVInJUVl@xUVLnU@UÞaV@lkV@UanKL@UlKVUnbÆmn@@nUlVnVJl@@UXUL@WVIVJVxVLXV@IÜKnbn@V¥V@@I@y°b@UUwnk°ÆƨVlUçXm£aÇIkV@WV@@aWIUWUIkb@WW@UnK@UU@kaWVkVIVVnU@UWVUV@VmVkKkWIkVWaULU`UImJUImmU@wmwUVIUWVkUamaU@mVkb@KVU@aVU@anKULVJU@kÛUJUVkkVakU@aVwkW@UWkXmWaULUaUK@XJUUmVU@UVUkJ@ImwmKU@k@lUW@@akKmkamIkWl_UwVm@UkaVUUa@UamakbWlkL@aUalU@mkL@U@UlmK@XkKm@Ýakb@xnXb`nUUU@U@wU@@mKkkV¯U@lULUbVbUb@Va@LºÝb@bLmKx@VUL@bk@mxULWl"],
                encodeOffsets: [[121185, 30184]]
            }
        }, {
            type: "Feature",
            id: "3303",
            properties: {name: "温州市", cp: [120.498, 27.8119], childNum: 9},
            geometry: {
                type: "Polygon",
                coordinates: ["@@ll@xnXV`VXWVL@lXnlV@UV@@b@¤VzUlnVU@nWxW@b@LnalK@bXVKUÈ@VVI@b@J@WbXLÆaUUmI@xlKnn@VWlbkXV@nVWnWbUbL@`VbUnVlVXkV@lUz±VnUbU@@VUlVL@l_@V@l@LVbV@XLV`VÈlxn@lU@aaVVk@XJ@nl@@LU`°LVbL°a@aUVy@anI@aanV@²wÜJX@VVV°kna@WVkaWwU@m@kaUĕÝÝŤnÈaaóI»@±XWkUķ@kV±kwUkWwUÝ»ÛkɳlImaUaWóXÿǬkUnWVmmkKţnŏÞğlUlUx@XWbV@JkX°mb@VULVxUVk@@LWWk@WIkUkJmUkVmI@y@UakLmU@mUUUkaVk@mK@UlUU@UmKmbUUUJ@n@KVLUL@VkJWXX`mnULWlkL@JVLVb@°kxkU@LVV@VLV`UL@VUX"],
                encodeOffsets: [[122502, 28334]]
            }
        }, {
            type: "Feature",
            id: "3302",
            properties: {name: "宁波市", cp: [121.5967, 29.6466], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@Ċ¦ĸĀ°nXÞVKkƨƑźÿ°»n@wô¥ÜbU°ÆXÞWóçĉÝ±IUÈ¥@U°wÆ»²mm_@aXVKÞVlk@akk̅@£X»VwÆXWa¯aȗbKƽŰĊxLók@@¯nKUL@xkLÑkWULUUmJUXVU@mUX¯@V`mbXbV@@nn¤WXx@kJ@nVVUVl²UbÝVUVk@Wx@V@VXzmlaL@VlLU`XUVVVUnl@VbnJlnUVVnlUKkbmnnVxlJnxmbU@UL@KUVX@xmb@lk@mnVVUè"],
                encodeOffsets: [[123784, 30977]]
            }
        }, {
            type: "Feature",
            id: "3309",
            properties: {name: "舟山市", cp: [122.2559, 30.2234], childNum: 3},
            geometry: {
                type: "Polygon",
                coordinates: ["@@l΢ƒʠþÆVĢLĊǬXĊÜXôVÑÆwlƏÈóVĭVǓ@ĉwɛkmK@ĉXīWaĉUĵÝm¯ĉwĉ±±nÅ¼¯x@VÇ¦V²JĊÞôèÝXÅW¯VÛaó¦@xm¯¼ŹĀ"],
                encodeOffsets: [[124437, 30983]]
            }
        }, {
            type: "Feature",
            id: "3310",
            properties: {name: "台州市", cp: [121.1353, 28.6688], childNum: 7},
            geometry: {
                type: "Polygon",
                coordinates: ["@@lVIVWVz@bXJl@Xal@°nLll@nVxnVK@UJVb¦°k`UIWJXnÆ@bUJXl@lbWn@UzVV@bVVmVnnJVXnabKUKnUVVUnVLlKVLXaJm£@mU@WanaU_°@VWnV@UVWnIVVVKlXÒlK@wVKL°m@l@ôKwĉƾůUl£@»UVkm@ƅUaÛIŏmUk@mw@a£Wk@ţIm±@ankôUlaUUw¯ōabÇbţmÞÞVĖbl@@nVXxbUl@Xmb¯lUUUW@ÛI±xU@mb@bmJ@bUzV@b¯bKUa¯KV_@Kk@@mWI@lUUb@bkVm@kwUÇU_WKU@Ux@VUnllX@VnJ@UXV@bWL@lUbbVLUJ@zV@lnbWbnnnJV@L"],
                encodeOffsets: [[123312, 29526]]
            }
        }, {
            type: "Feature",
            id: "3307",
            properties: {name: "金华市", cp: [120.0037, 29.1028], childNum: 8},
            geometry: {
                type: "Polygon",
                coordinates: ["@@nbVb@VbUVlb@VUnVxk`lXnJlbnlL@bX@V@klV@nLnx@JlIVU@VUVnVVI@WVLVbVKXbWnXl@VlXUxb@lVUbllVUIÜVnalKX@@bV@@aUUlUwUw@naWWUVaUUaVbLlxXJVk°UlkU¥@ka@LVlXLVlVWznVn@lxJl_@WX_@mVaa@alU@kVVnaKVLlKb@UUaVabnUWmXU@k@yVI@aÅWmXIVJl_¯¥UaVI@LmUUw@mkkmK¯k@Wbk@WI@aUyUXJkU@bU@WLUyXUbkbW`UVVkKmbUaVUUK£@KVUUUm@UWkXWaUKV@b¯¯mUV@UkmW@kkKwUmkkVUI@WlkUamL@Wk_W@UVm@Ua¯KWXk@Uxm@UK@xVmV@Xk@UVV¼@VLUbUU@yULUbVlU@@XlVUVVbU@lXXVW@XUVl@@VUVÈn@VVU@lVa@UmL@`X@`WL@VUX@lUL@xlx"],
                encodeOffsets: [[122119, 29948]]
            }
        }, {
            type: "Feature",
            id: "3308",
            properties: {name: "衢州市", cp: [118.6853, 28.8666], childNum: 5},
            geometry: {
                type: "Polygon",
                coordinates: ["@@XkVKnwl@@aVK@UwnLK@aÞa¹@Kb@UVaUaVaVK@k°VUllnL@V@xV@V@VVm_Wam@wlaÞbn@lL@WnLk@V@VlK@nkVVb@blKXklakw@wVK@kVW@UXK@_W@_nKV@Ub@kVUUm@ÇVU@Uk@VU@WUXWW@kVUaVUkU@WWXUKk@Ukmm¯LmmUJUIWJkImm_±WLkKm£@aVUmKUnLmWUkVmw@¥ULVWm@WUka@UmmLmm@@bUX@@WUIm@UVUK@UVUUUVVJmb@bXnmV¼nnn¦mJUVLV@VW@UzUlVnUbl`UnVl@XU@kl@bmÈUxVk@@J@¼W@ÅaVVnzmV@WJk@kWJ@lXbWbXxmVnlLXb@°lKVXnWbWVXmbV@XlbI@Kn@@x@VLlm"],
                encodeOffsets: [[121185, 30184]]
            }
        }, {
            type: "Feature",
            id: "3306",
            properties: {name: "绍兴市", cp: [120.564, 29.7565], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@x@VnnVJnIVJV_VKXblUXJllLUUnU@UVVX@mVUUUJlXUlbV@@VLVmX@@XlaVJVXXJ@b@XU@lUJÈb¤ŌJçVUUnml@@kna@wWVU@LVKV@namwkIUwmnmlaVLkUmVUkmmIUak@VmUUVUWV_kK@UKbnkWyU@@UXwl@VUÞUVak±VUUU@mlI@wXWIWbUKkLUKVmUUmVVLLambUWmIUmnUU@aUUVym@Xkak@W@z@lWVXnmVaUbVb@VakLUKLmbUU@lkV@bbUb@nW`@Xk`Ikwm@mUXyUUkWKUk@Kb@lV¦klV¯UlWIkwKUabVVUbVXXmb@VxxkVVV@bU@@aW@kLmb@lVUIVKmL@bUV@bUV@LalnUV@nbVbUlVXJVUnx"],
                encodeOffsets: [[122997, 30561]]
            }
        }, {
            type: "Feature",
            id: "3304",
            properties: {name: "嘉兴市", cp: [120.9155, 30.6354], childNum: 6},
            geometry: {
                type: "Polygon",
                coordinates: ["@@@blIX@@VÜVUnn@lklKnI°Þl`²LVKVbnbVaVLUVn@W¦@VkVVb@VI`@blLnLaX@VVb@U@XlVa@@kVaUKV»U_lWXU@albk@VllnLVKn@@UVIUw@y°IVVXU@VV@lwm@wVkƾaJLkΡƧƒlLÝUmW¯ķÿĉ¥IŋWnèkVƧU¯ÅmlVx@V¯az@@JU@U¦m@@nVmn@VLV"],
                encodeOffsets: [[123233, 31382]]
            }
        }, {
            type: "Feature",
            id: "3305",
            properties: {name: "湖州市", cp: [119.8608, 30.7782], childNum: 4},
            geometry: {
                type: "Polygon",
                coordinates: ["@@kLlkm@VmÛU@UW@kJ@aUK@UnmmU@maÛL@JWUUKUwUIUJ@XKWV@Vk@UIUmVk@mm@ÅnmaUVkL@VKmLVbU@klU@ÝbV@mVUKV@wUkVmIUJ@nVV@LakJWbUIka@UmKmLKmmUUVk@@nmLX`WXUV@@nUlkmlU@UbxVVIlVnn@@nUÒ@°n@@xmb@VbnV@@b@`@L@L@x@blVklVbnnV@aXb°VlU@Wb°ULXWVUVVwÈwÜ»ĸaĠnUVw²X@V@lVU@wlaUUVm@knUV"],
                encodeOffsets: [[123379, 31500]]
            }
        }],
        UTF8Encoding: !0
    }
}),define("echarts/util/shape/halfSmoothPolygon", ["require", "zrender/shape", "zrender/shape/base", "zrender/shape"], function (e) {
    function t() {
        this.type = "halfSmoothPolygon"
    }

    return t.prototype = {
        buildPath: function (t, n) {
            var r = n.pointList;
            if (r.length < 2)return;
            if (n.smooth) {
                var i = this.smoothBezier(r.slice(0, -2), n.smooth);
                t.moveTo(r[0][0], r[0][1]);
                var s, o, u, a = r.length;
                for (var f = 0; f < a - 3; f++)s = i[f * 2], o = i[f * 2 + 1], u = r[f + 1], t.bezierCurveTo(s[0], s[1], o[0], o[1], u[0], u[1]);
                t.lineTo(r[a - 2][0], r[a - 2][1]), t.lineTo(r[a - 1][0], r[a - 1][1]), t.lineTo(r[0][0], r[0][1])
            } else e("zrender/shape").get("polygon").buildPath(t, n);
            return
        }
    }, e("zrender/shape/base").derive(t), e("zrender/shape").define("halfSmoothPolygon", new t), t
}),define("echarts/chart/line", ["require", "../component/base", "./calculableBase", "zrender/tool/color", "zrender/shape", "zrender/shape", "../util/shape/halfSmoothPolygon", "../chart"], function (e) {
    function t(t, r, i, s, o) {
        function g() {
            p = {}, l.selectedMap = {};
            var e = {top: [], bottom: [], left: [], right: []}, n, r, s, u;
            for (var a = 0, f = c.length; a < f; a++)c[a].type == l.type && (c[a] = l.reformOption(c[a]), n = c[a].xAxisIndex, r = c[a].yAxisIndex, s = o.xAxis.getAxis(n), u = o.yAxis.getAxis(r), s.type == t.COMPONENT_TYPE_AXIS_CATEGORY ? e[s.getPosition()].push(a) : u.type == t.COMPONENT_TYPE_AXIS_CATEGORY && e[u.getPosition()].push(a));
            for (var h in e)e[h].length > 0 && y(h, e[h]);
            for (var a = 0, f = l.shapeList.length; a < f; a++)l.shapeList[a].id = i.newShapeId(l.type), i.addShape(l.shapeList[a])
        }

        function y(e, t) {
            var n = b(t), r = n.locationMap, i = n.maxDataLength;
            if (i === 0 || r.length === 0)return;
            var s = {};
            switch (e) {
                case"bottom":
                case"top":
                    w(i, r, s);
                    break;
                case"left":
                case"right":
                    E(i, r, s)
            }
            for (var u = 0, a = t.length; u < a; u++)l.buildMark(c[t[u]], t[u], o, {xMarkMap: s})
        }

        function b(e) {
            var t, n = 0, r = {}, s = "__kener__stack__", u, a, f = o.legend, h = [], p = 0, g;
            for (var y = 0, b = e.length; y < b; y++)t = c[e[y]], a = t.name, m[e[y]] = m[e[y]] || l.query(t, "symbol") || v[y % v.length], f ? (l.selectedMap[a] = f.isSelected(a), d[e[y]] = f.getColor(a), g = f.getItemShape(a), g && (g.shape = "icon", g.style.iconType = "legendLineIcon", g.style.symbol = m[e[y]], f.setItemShape(a, g))) : (l.selectedMap[a] = !0, d[e[y]] = i.getColor(e[y])), l.selectedMap[a] && (u = t.stack || s + e[y], typeof r[u] == "undefined" ? (r[u] = n, h[n] = [e[y]], n++) : h[r[u]].push(e[y])), p = Math.max(p, t.data.length);
            return {locationMap: h, maxDataLength: p}
        }

        function w(e, t, n) {
            var r = t[0][0], i = c[r], u = i.xAxisIndex, a = o.xAxis.getAxis(u), f, h, d, v, m, g, y, b, w = {}, E, x;
            for (var N = 0, C = e; N < C; N++) {
                if (typeof a.getNameByIndex(N) == "undefined")break;
                d = a.getCoordByIndex(N);
                for (var k = 0, L = t.length; k < L; k++) {
                    f = c[t[k][0]].yAxisIndex || 0, h = o.yAxis.getAxis(f), g = m = b = y = h.getCoord(0);
                    for (var A = 0, O = t[k].length; A < O; A++) {
                        r = t[k][A], i = c[r], E = i.data[N], x = typeof E != "undefined" ? typeof E.value != "undefined" ? E.value : E : "-", w[r] = w[r] || [], n[r] = n[r] || {
                                min: Number.POSITIVE_INFINITY,
                                max: Number.NEGATIVE_INFINITY,
                                sum: 0,
                                counter: 0,
                                average: 0
                            };
                        if (x == "-") {
                            w[r].length > 0 && (p[r] = p[r] || [], p[r].push(w[r]), w[r] = []);
                            continue
                        }
                        x >= 0 ? (m -= A > 0 ? h.getCoordSize(x) : g - h.getCoord(x), v = m) : x < 0 && (y += A > 0 ? h.getCoordSize(x) : h.getCoord(x) - b, v = y), w[r].push([d, v, N, a.getNameByIndex(N), d, g]), n[r].min > x && (n[r].min = x, n[r].minY = v, n[r].minX = d), n[r].max < x && (n[r].max = x, n[r].maxY = v, n[r].maxX = d), n[r].sum += x, n[r].counter++
                    }
                }
                m = o.grid.getY();
                var M;
                for (var k = 0, L = t.length; k < L; k++)for (var A = 0, O = t[k].length; A < O; A++) {
                    r = t[k][A], i = c[r], E = i.data[N], x = typeof E != "undefined" ? typeof E.value != "undefined" ? E.value : E : "-";
                    if (x != "-")continue;
                    l.deepQuery([E, i, s], "calculable") && (M = l.deepQuery([E, i], "symbolSize"), m += M * 2 + 5, v = m, l.shapeList.push(T(r, N, a.getNameByIndex(N), d, v, "horizontal")))
                }
            }
            for (var _ in w)w[_].length > 0 && (p[_] = p[_] || [], p[_].push(w[_]), w[_] = []);
            for (var k = 0, L = t.length; k < L; k++)for (var A = 0, O = t[k].length; A < O; A++)r = t[k][A], n[r].counter > 0 && (n[r].average = (n[r].sum / n[r].counter).toFixed(2) - 0), v = o.yAxis.getAxis(c[r].yAxisIndex || 0).getCoord(n[r].average), n[r].averageLine = [[o.grid.getX(), v], [o.grid.getXend(), v]], n[r].minLine = [[o.grid.getX(), n[r].minY], [o.grid.getXend(), n[r].minY]], n[r].maxLine = [[o.grid.getX(), n[r].maxY], [o.grid.getXend(), n[r].maxY]];
            S(p, a, "horizontal")
        }

        function E(e, t, n) {
            var r = t[0][0], i = c[r], u = i.yAxisIndex, a = o.yAxis.getAxis(u), f, h, d, v, m, g, y, b, w = {}, E, x;
            for (var N = 0, C = e; N < C; N++) {
                if (typeof a.getNameByIndex(N) == "undefined")break;
                v = a.getCoordByIndex(N);
                for (var k = 0, L = t.length; k < L; k++) {
                    f = c[t[k][0]].xAxisIndex || 0, h = o.xAxis.getAxis(f), g = m = b = y = h.getCoord(0);
                    for (var A = 0, O = t[k].length; A < O; A++) {
                        r = t[k][A], i = c[r], E = i.data[N], x = typeof E != "undefined" ? typeof E.value != "undefined" ? E.value : E : "-", w[r] = w[r] || [], n[r] = n[r] || {
                                min: Number.POSITIVE_INFINITY,
                                max: Number.NEGATIVE_INFINITY,
                                sum: 0,
                                counter: 0,
                                average: 0
                            };
                        if (x == "-") {
                            w[r].length > 0 && (p[r] = p[r] || [], p[r].push(w[r]), w[r] = []);
                            continue
                        }
                        x >= 0 ? (m += A > 0 ? h.getCoordSize(x) : h.getCoord(x) - g, d = m) : x < 0 && (y -= A > 0 ? h.getCoordSize(x) : b - h.getCoord(x), d = y), w[r].push([d, v, N, a.getNameByIndex(N), g, v]), n[r].min > x && (n[r].min = x, n[r].minX = d, n[r].minY = v), n[r].max < x && (n[r].max = x, n[r].maxX = d, n[r].maxY = v), n[r].sum += x, n[r].counter++
                    }
                }
                m = o.grid.getXend();
                var M;
                for (var k = 0, L = t.length; k < L; k++)for (var A = 0, O = t[k].length; A < O; A++) {
                    r = t[k][A], i = c[r], E = i.data[N], x = typeof E != "undefined" ? typeof E.value != "undefined" ? E.value : E : "-";
                    if (x != "-")continue;
                    l.deepQuery([E, i, s], "calculable") && (M = l.deepQuery([E, i], "symbolSize"), m -= M * 2 + 5, d = m, l.shapeList.push(T(r, N, a.getNameByIndex(N), d, v, "vertical")))
                }
            }
            for (var _ in w)w[_].length > 0 && (p[_] = p[_] || [], p[_].push(w[_]), w[_] = []);
            for (var k = 0, L = t.length; k < L; k++)for (var A = 0, O = t[k].length; A < O; A++)r = t[k][A], n[r].counter > 0 && (n[r].average = (n[r].sum / n[r].counter).toFixed(2) - 0), d = o.xAxis.getAxis(c[r].xAxisIndex || 0).getCoord(n[r].average), n[r].averageLine = [[d, o.grid.getYend()], [d, o.grid.getY()]], n[r].minLine = [[n[r].minX, o.grid.getYend()], [n[r].minX, o.grid.getY()]], n[r].maxLine = [[n[r].maxX, o.grid.getYend()], [n[r].maxX, o.grid.getY()]];
            S(p, a, "vertical")
        }

        function S(e, t, n) {
            var r, i, o, u, a, p, v, m, g, y, b;
            for (var w = c.length - 1; w >= 0; w--) {
                m = c[w], y = e[w];
                if (m.type == l.type && typeof y != "undefined") {
                    r = d[w], i = l.query(m, "itemStyle.normal.lineStyle.width"), o = l.query(m, "itemStyle.normal.lineStyle.type"), u = l.query(m, "itemStyle.normal.lineStyle.color"), a = l.getItemStyleColor(l.query(m, "itemStyle.normal.color"), w, -1), p = typeof l.query(m, "itemStyle.normal.areaStyle") != "undefined", v = l.query(m, "itemStyle.normal.areaStyle.color");
                    for (var E = 0, S = y.length; E < S; E++) {
                        b = y[E];
                        for (var T = 0, C = b.length; T < C; T++)g = m.data[b[T][2]], (l.deepQuery([g, m], "showAllSymbol") || t.isMainAxis(b[T][2]) && l.deepQuery([g, m], "symbol") != "none" || l.deepQuery([g, m, s], "calculable")) && l.shapeList.push(N(w, b[T][2], b[T][3], b[T][0], b[T][1], n));
                        l.shapeList.push({
                            shape: "brokenLine",
                            zlevel: h,
                            style: {
                                miterLimit: i,
                                pointList: b,
                                strokeColor: u || a || r,
                                lineWidth: i,
                                lineType: o,
                                smooth: x(m.smooth),
                                shadowColor: l.query(m, "itemStyle.normal.lineStyle.shadowColor"),
                                shadowBlur: l.query(m, "itemStyle.normal.lineStyle.shadowBlur"),
                                shadowOffsetX: l.query(m, "itemStyle.normal.lineStyle.shadowOffsetX"),
                                shadowOffsetY: l.query(m, "itemStyle.normal.lineStyle.shadowOffsetY")
                            },
                            hoverable: !1,
                            _main: !0,
                            _seriesIndex: w,
                            _orient: n
                        }), p && l.shapeList.push({
                            shape: "halfSmoothPolygon",
                            zlevel: h,
                            style: {
                                miterLimit: i,
                                pointList: b.concat([[b[b.length - 1][4], b[b.length - 1][5] - 2], [b[0][4], b[0][5] - 2]]),
                                brushType: "fill",
                                smooth: x(m.smooth),
                                color: v ? v : f.alpha(r, .5)
                            },
                            hoverable: !1,
                            _main: !0,
                            _seriesIndex: w,
                            _orient: n
                        })
                    }
                }
            }
        }

        function x(e) {
            return e ? .3 : 0
        }

        function T(e, n, r, i, s, o) {
            var u = c[e].calculableHolderColor || t.calculableHolderColor, a = N(e, n, r, i, s, o);
            return a.style.color = u, a.style.strokeColor = u, a.rotation = [0, 0], a.hoverable = !1, a.draggable = !1, a.style.text = undefined, a
        }

        function N(e, t, n, r, i, o) {
            var u = c[e], a = u.data[t],
                f = l.getSymbolShape(u, e, a, t, n, r, i, m[e], d[e], "#fff", o == "vertical" ? "horizontal" : "vertical");
            return f.zlevel = h + 1, l.deepQuery([a, u, s], "calculable") && (l.setCalculable(f), f.draggable = !0), f
        }

        function C(e, t, n, r) {
            var i = o.xAxis.getAxis(e.xAxisIndex), s = o.yAxis.getAxis(e.yAxisIndex);
            return !n.type || n.type != "max" && n.type != "min" && n.type != "average" ? [typeof n.xAxis != "string" && i.getCoordByIndex ? i.getCoordByIndex(n.xAxis || 0) : i.getCoord(n.xAxis || 0), typeof n.yAxis != "string" && s.getCoordByIndex ? s.getCoordByIndex(n.yAxis || 0) : s.getCoord(n.yAxis || 0)] : [r.xMarkMap[t][n.type + "X"], r.xMarkMap[t][n.type + "Y"], r.xMarkMap[t][n.type + "Line"], r.xMarkMap[t][n.type]]
        }

        function k(e, t) {
            o = t, L(e)
        }

        function L(e) {
            e && (s = e, c = s.series), l.clear(), g()
        }

        function A(e, t) {
            var n = e.seriesIndex, r = e.dataIndex, i, s, o = n.length;
            while (o--) {
                i = p[n[o]];
                if (i)for (var u = 0, a = i.length; u < a; u++) {
                    s = i[u];
                    for (var f = 0, l = s.length; f < l; f++)r == s[f][2] && t.push(N(n[o], s[f][2], s[f][3], s[f][0], s[f][1], "horizontal"))
                }
            }
        }

        function O(e) {
            var t = {};
            for (var n = 0, r = e.length; n < r; n++)t[e[n][0]] = e[n];
            var s, o, u, a, f, h, p;
            for (var n = l.shapeList.length - 1; n >= 0; n--) {
                f = l.shapeList[n]._seriesIndex;
                if (t[f] && !t[f][3]) {
                    if (l.shapeList[n]._main) {
                        h = l.shapeList[n].style.pointList, o = Math.abs(h[0][0] - h[1][0]), a = Math.abs(h[0][1] - h[1][1]), p = l.shapeList[n]._orient == "horizontal";
                        if (t[f][2]) {
                            if (l.shapeList[n].shape == "polygon") {
                                var d = h.length;
                                l.shapeList[n].style.pointList[d - 3] = h[d - 2], p ? l.shapeList[n].style.pointList[d - 3][0] = h[d - 4][0] : l.shapeList[n].style.pointList[d - 3][1] = h[d - 4][1], l.shapeList[n].style.pointList[d - 2] = h[d - 1]
                            }
                            l.shapeList[n].style.pointList.pop(), p ? (s = o, u = 0) : (s = 0, u = -a)
                        } else {
                            l.shapeList[n].style.pointList.shift();
                            if (l.shapeList[n].shape == "polygon") {
                                var v = l.shapeList[n].style.pointList.pop();
                                p ? v[0] = h[0][0] : v[1] = h[0][1], l.shapeList[n].style.pointList.push(v)
                            }
                            p ? (s = -o, u = 0) : (s = 0, u = a)
                        }
                        i.modShape(l.shapeList[n].id, {style: {pointList: l.shapeList[n].style.pointList}}, !0)
                    } else {
                        if (t[f][2] && l.shapeList[n]._dataIndex == c[f].data.length - 1) {
                            i.delShape(l.shapeList[n].id);
                            continue
                        }
                        if (!t[f][2] && l.shapeList[n]._dataIndex === 0) {
                            i.delShape(l.shapeList[n].id);
                            continue
                        }
                    }
                    i.animate(l.shapeList[n].id, "").when(500, {position: [s, u]}).start()
                }
            }
        }

        function M() {
            var e = l.query(s, "animationDuration"), t = l.query(s, "animationEasing"), n, r, o, u = 0;
            for (var a = 0, f = l.shapeList.length; a < f; a++)l.shapeList[a]._main && (o = c[l.shapeList[a]._seriesIndex], u += 1, n = l.shapeList[a].style.pointList[0][0], r = l.shapeList[a].style.pointList[0][1], l.shapeList[a]._orient == "horizontal" ? i.modShape(l.shapeList[a].id, {scale: [0, 1, n, r]}, !0) : i.modShape(l.shapeList[a].id, {scale: [1, 0, n, r]}, !0), i.animate(l.shapeList[a].id, "").when((l.query(o, "animationDuration") || e) + u * 100, {scale: [1, 1, n, r]}).start(l.query(o, "animationEasing") || t));
            l.animationMark(e, t)
        }

        var u = e("../component/base");
        u.call(this, t, i);
        var a = e("./calculableBase");
        a.call(this, i, s);
        var f = e("zrender/tool/color"), l = this;
        l.type = t.CHART_TYPE_LINE;
        var c, h = l.getZlevelBase(), p = {}, d = {}, v = t.symbolList, m = {};
        e("zrender/shape").get("icon").define("legendLineIcon", n), l.getMarkCoord = C, l.animation = M, l.init = k, l.refresh = L, l.ontooltipHover = A, l.addDataAnimation = O, k(s, o)
    }

    function n(t, n) {
        var r = n.x, i = n.y, s = n.width, o = n.height, u = o / 2;
        n.symbol.match("empty") && (t.fillStyle = "#fff"), n.brushType = "both";
        var a = n.symbol.replace("empty", "").toLowerCase();
        if (a.match("star")) u = a.replace("star", "") - 0 || 5, i -= 1, a = "star"; else if (a == "rectangle" || a == "arrow") r += (s - o) / 2, s = o;
        var f = "";
        a.match("image") && (f = a.replace(new RegExp("^image:\\/\\/"), ""), a = "image", r += Math.round((s - o) / 2) - 1, s = o += 2), a = e("zrender/shape").get("icon").get(a);
        if (a) {
            var l = n.x, c = n.y;
            t.moveTo(l, c + u), t.lineTo(l + 5, c + u), t.moveTo(l + n.width - 5, c + u), t.lineTo(l + n.width, c + u), a(t, {
                x: r + 4,
                y: i + 4,
                width: s - 8,
                height: o - 8,
                n: u,
                image: f
            })
        } else t.moveTo(r, i + u), t.lineTo(r + s, i + u)
    }

    return e("../util/shape/halfSmoothPolygon"), e("../chart").define("line", t), t
}),define("echarts/chart/bar", ["require", "../component/base", "./calculableBase", "../util/ecData", "zrender/tool/color", "../chart"], function (e) {
    function t(t, n, r, i, s) {
        function d() {
            l.selectedMap = {};
            var e = {top: [], bottom: [], left: [], right: []}, n, i, o, u;
            for (var a = 0, f = c.length; a < f; a++)c[a].type == t.CHART_TYPE_BAR && (c[a] = l.reformOption(c[a]), n = c[a].xAxisIndex, i = c[a].yAxisIndex, o = s.xAxis.getAxis(n), u = s.yAxis.getAxis(i), o.type == t.COMPONENT_TYPE_AXIS_CATEGORY ? e[o.getPosition()].push(a) : u.type == t.COMPONENT_TYPE_AXIS_CATEGORY && e[u.getPosition()].push(a));
            for (var h in e)e[h].length > 0 && v(h, e[h]);
            for (var a = 0, f = l.shapeList.length; a < f; a++)l.shapeList[a].id = r.newShapeId(l.type), r.addShape(l.shapeList[a])
        }

        function v(e, t) {
            var n = m(t), r = n.locationMap, i = n.maxDataLength;
            if (i === 0 || r.length === 0)return;
            switch (e) {
                case"bottom":
                case"top":
                    g(i, r, t);
                    break;
                case"left":
                case"right":
                    y(i, r, t)
            }
        }

        function m(e) {
            var t, n = 0, i = {}, o = "__kener__stack__", u, a, f = s.legend, h = [], d = 0, v;
            for (var m = 0, g = e.length; m < g; m++)t = c[e[m]], a = t.name, f ? (l.selectedMap[a] = f.isSelected(a), p[e[m]] = f.getColor(a), v = f.getItemShape(a), v && (t.itemStyle.normal.borderWidth > 0 && (v.style.x += 1, v.style.y += 1, v.style.width -= 2, v.style.height -= 2, v.style.strokeColor = v.highlightStyle.strokeColor = t.itemStyle.normal.borderColor, v.highlightStyle.lineWidth = 3, v.style.brushType = "both"), f.setItemShape(a, v))) : (l.selectedMap[a] = !0, p[e[m]] = r.getColor(e[m])), l.selectedMap[a] && (u = t.stack || o + e[m], typeof i[u] == "undefined" ? (i[u] = n, h[n] = [e[m]], n++) : h[i[u]].push(e[m])), d = Math.max(d, t.data.length);
            return {locationMap: h, maxDataLength: d}
        }

        function g(e, n, r) {
            var o = n[0][0], u = c[o], a = u.xAxisIndex, f = s.xAxis.getAxis(a), h, p, d = b(f, n), v = d.gap,
                m = d.barGap, g = d.barWidthMap, y = d.barWidth, S = d.barMinHeightMap, x, T = {}, N, C, k, L, A, O, M,
                _, D;
            for (var P = 0, H = e; P < H; P++) {
                if (typeof f.getNameByIndex(P) == "undefined")break;
                N = f.getCoordByIndex(P) - v / 2;
                for (var B = 0, j = n.length; B < j; B++) {
                    h = c[n[B][0]].yAxisIndex || 0, p = s.yAxis.getAxis(h), L = k = O = A = p.getCoord(0);
                    for (var F = 0, I = n[B].length; F < I; F++) {
                        o = n[B][F], u = c[o], _ = u.data[P], D = typeof _ != "undefined" ? typeof _.value != "undefined" ? _.value : _ : "-", T[o] = T[o] || {
                                min: Number.POSITIVE_INFINITY,
                                max: Number.NEGATIVE_INFINITY,
                                sum: 0,
                                counter: 0,
                                average: 0
                            };
                        if (D == "-")continue;
                        D > 0 ? (x = F > 0 ? p.getCoordSize(D) : L - p.getCoord(D), I == 1 && S[o] > x && (x = S[o]), k -= x, C = k) : D < 0 ? (x = F > 0 ? p.getCoordSize(D) : p.getCoord(D) - O, I == 1 && S[o] > x && (x = S[o]), C = A, A += x) : (x = 0, k -= x, C = k), M = w(o, P, f.getNameByIndex(P), N, C, g[o] || y, x, "vertical"), T[o][P] = N + (g[o] || y) / 2, T[o].min > D && (T[o].min = D, T[o].minY = C, T[o].minX = T[o][P]), T[o].max < D && (T[o].max = D, T[o].maxY = C, T[o].maxX = T[o][P]), T[o].sum += D, T[o].counter++, l.shapeList.push(M)
                    }
                    for (var F = 0, I = n[B].length; F < I; F++) {
                        o = n[B][F], u = c[o], _ = u.data[P], D = typeof _ != "undefined" ? typeof _.value != "undefined" ? _.value : _ : "-";
                        if (D != "-")continue;
                        l.deepQuery([_, u, i], "calculable") && (k -= t.island.r, C = k, M = w(o, P, f.getNameByIndex(P), N + .5, C + .5, (g[o] || y) - 1, t.island.r - 1, "vertical"), M.hoverable = !1, M.draggable = !1, M.style.lineWidth = 1, M.style.brushType = "stroke", M.style.strokeColor = u.calculableHolderColor || t.calculableHolderColor, l.shapeList.push(M))
                    }
                    N += (g[o] || y) + m
                }
            }
            for (var B = 0, j = n.length; B < j; B++)for (var F = 0, I = n[B].length; F < I; F++)o = n[B][F], T[o].counter > 0 && (T[o].average = (T[o].sum / T[o].counter).toFixed(2) - 0), C = s.yAxis.getAxis(c[o].yAxisIndex || 0).getCoord(T[o].average), T[o].averageLine = [[s.grid.getX(), C], [s.grid.getXend(), C]], T[o].minLine = [[s.grid.getX(), T[o].minY], [s.grid.getXend(), T[o].minY]], T[o].maxLine = [[s.grid.getX(), T[o].maxY], [s.grid.getXend(), T[o].maxY]];
            E(r, T, !0)
        }

        function y(e, n, r) {
            var o = n[0][0], u = c[o], a = u.yAxisIndex, f = s.yAxis.getAxis(a), h, p, d = b(f, n), v = d.gap,
                m = d.barGap, g = d.barWidthMap, y = d.barWidth, S = d.barMinHeightMap, x, T = {}, N, C, k, L, A, O, M,
                _, D;
            for (var P = 0, H = e; P < H; P++) {
                if (typeof f.getNameByIndex(P) == "undefined")break;
                C = f.getCoordByIndex(P) + v / 2;
                for (var B = 0, j = n.length; B < j; B++) {
                    h = c[n[B][0]].xAxisIndex || 0, p = s.xAxis.getAxis(h), L = k = O = A = p.getCoord(0);
                    for (var F = 0, I = n[B].length; F < I; F++) {
                        o = n[B][F], u = c[o], _ = u.data[P], D = typeof _ != "undefined" ? typeof _.value != "undefined" ? _.value : _ : "-", T[o] = T[o] || {
                                min: Number.POSITIVE_INFINITY,
                                max: Number.NEGATIVE_INFINITY,
                                sum: 0,
                                counter: 0,
                                average: 0
                            };
                        if (D == "-")continue;
                        D > 0 ? (x = F > 0 ? p.getCoordSize(D) : p.getCoord(D) - L, I == 1 && S[o] > x && (x = S[o]), N = k, k += x) : D < 0 ? (x = F > 0 ? p.getCoordSize(D) : O - p.getCoord(D), I == 1 && S[o] > x && (x = S[o]), A -= x, N = A) : (x = 0, N = k, k += x), M = w(o, P, f.getNameByIndex(P), N, C - (g[o] || y), x, g[o] || y, "horizontal"), T[o][P] = C - (g[o] || y) / 2, T[o].min > D && (T[o].min = D, T[o].minX = N + x, T[o].minY = T[o][P]), T[o].max < D && (T[o].max = D, T[o].maxX = N + x, T[o].maxY = T[o][P]), T[o].sum += D, T[o].counter++, l.shapeList.push(M)
                    }
                    for (var F = 0, I = n[B].length; F < I; F++) {
                        o = n[B][F], u = c[o], _ = u.data[P], D = typeof _ != "undefined" ? typeof _.value != "undefined" ? _.value : _ : "-";
                        if (D != "-")continue;
                        l.deepQuery([_, u, i], "calculable") && (N = k, k += t.island.r, M = w(o, P, f.getNameByIndex(P), N + .5, C + .5 - (g[o] || y), t.island.r - 1, (g[o] || y) - 1, "horizontal"), M.hoverable = !1, M.draggable = !1, M.style.lineWidth = 1, M.style.brushType = "stroke", M.style.strokeColor = u.calculableHolderColor || t.calculableHolderColor, l.shapeList.push(M))
                    }
                    C -= (g[o] || y) + m
                }
            }
            for (var B = 0, j = n.length; B < j; B++)for (var F = 0, I = n[B].length; F < I; F++)o = n[B][F], T[o].counter > 0 && (T[o].average = (T[o].sum / T[o].counter).toFixed(2) - 0), N = s.xAxis.getAxis(c[o].xAxisIndex || 0).getCoord(T[o].average), T[o].averageLine = [[N, s.grid.getYend()], [N, s.grid.getY()]], T[o].minLine = [[T[o].minX, s.grid.getYend()], [T[o].minX, s.grid.getY()]], T[o].maxLine = [[T[o].maxX, s.grid.getYend()], [T[o].maxX, s.grid.getY()]];
            E(r, T, !1)
        }

        function b(e, t, n) {
            var r = {}, i = {}, s, o = 0, u = 0, a, f, h, p;
            for (var d = 0, v = t.length; d < v; d++) {
                h = !1;
                for (var m = 0, g = t[d].length; m < g; m++) {
                    seriesIndex = t[d][m], p = c[seriesIndex];
                    if (!n)if (!h) {
                        s = l.query(p, "barWidth");
                        if (typeof s != "undefined") {
                            r[seriesIndex] = s, u += s, o++, h = !0;
                            for (var y = 0, w = m; y < w; y++) {
                                var E = t[d][y];
                                r[E] = s
                            }
                        }
                    } else r[seriesIndex] = s;
                    i[seriesIndex] = l.query(p, "barMinHeight"), a = typeof a != "undefined" ? a : l.query(p, "barGap"), f = typeof f != "undefined" ? f : l.query(p, "barCategoryGap")
                }
            }
            var S, x;
            if (t.length != o)if (!n) {
                S = typeof f == "string" && f.match(/%$/) ? Math.floor(e.getGap() * (100 - parseFloat(f)) / 100) : e.getGap() - f, typeof a == "string" && a.match(/%$/) ? (a = parseFloat(a) / 100, x = Math.floor((S - u) / ((t.length - 1) * a + t.length - o)), a = Math.floor(x * a)) : (a = parseFloat(a), x = Math.floor((S - u - a * (t.length - 1)) / (t.length - o)));
                if (x <= 0)return b(e, t, !0)
            } else S = e.getGap(), a = 0, x = Math.floor(S / t.length), x <= 0 && (x = 1); else {
                S = o > 1 ? typeof f == "string" && f.match(/%$/) ? Math.floor(e.getGap() * (100 - parseFloat(f)) / 100) : e.getGap() - f : u, x = 0, a = o > 1 ? Math.floor((S - u) / (o - 1)) : 0;
                if (a < 0)return b(e, t, !0)
            }
            return {barWidthMap: r, barMinHeightMap: i, gap: S, barWidth: x, barGap: a}
        }

        function w(e, t, n, r, s, o, u, d) {
            var v, m = c[e], g = m.data[t], y = p[e], b = [g, m], w = l.deepQuery(b, "itemStyle.normal.color") || y,
                E = l.deepQuery(b, "itemStyle.emphasis.color"), S = l.deepMerge(b, "itemStyle.normal"),
                x = S.borderWidth, T = l.deepMerge(b, "itemStyle.emphasis");
            return v = {
                shape: "rectangle",
                zlevel: h,
                clickable: !0,
                style: {
                    x: r,
                    y: s,
                    width: o,
                    height: u,
                    brushType: "both",
                    color: l.getItemStyleColor(w, e, t, g),
                    radius: S.borderRadius,
                    lineWidth: x,
                    strokeColor: S.borderColor
                },
                highlightStyle: {
                    color: l.getItemStyleColor(E, e, t, g),
                    radius: T.borderRadius,
                    lineWidth: T.borderWidth,
                    strokeColor: T.borderColor
                },
                _orient: d
            }, v.highlightStyle.color = v.highlightStyle.color || (typeof v.style.color == "string" ? f.lift(v.style.color, -0.3) : v.style.color), x > 0 && v.style.height > x && v.style.width > x ? (v.style.y += x / 2, v.style.height -= x, v.style.x += x / 2, v.style.width -= x) : v.style.brushType = "fill", v.highlightStyle.textColor = v.highlightStyle.color, v = l.addLabel(v, m, g, n, d), l.deepQuery([g, m, i], "calculable") && (l.setCalculable(v), v.draggable = !0), a.pack(v, c[e], e, c[e].data[t], t, n), v
        }

        function E(e, t, n) {
            for (var r = 0, i = e.length; r < i; r++)l.buildMark(c[e[r]], e[r], s, {isHorizontal: n, xMarkMap: t})
        }

        function S(e, t, n, r) {
            var i = s.xAxis.getAxis(e.xAxisIndex), o = s.yAxis.getAxis(e.yAxisIndex), u, a;
            return !n.type || n.type != "max" && n.type != "min" && n.type != "average" ? r.isHorizontal ? (u = typeof n.xAxis == "string" && i.getIndexByName ? i.getIndexByName(n.xAxis) : n.xAxis || 0, a = [r.xMarkMap[t][u], o.getCoord(n.yAxis || 0)]) : (u = typeof n.yAxis == "string" && o.getIndexByName ? o.getIndexByName(n.yAxis) : n.yAxis || 0, a = [i.getCoord(n.xAxis || 0), r.xMarkMap[t][u]]) : a = [r.xMarkMap[t][n.type + "X"], r.xMarkMap[t][n.type + "Y"], r.xMarkMap[t][n.type + "Line"], r.xMarkMap[t][n.type]], a
        }

        function x(e, t) {
            s = t, T(e)
        }

        function T(e) {
            e && (i = e, c = i.series), l.clear(), d()
        }

        function N(e) {
            var t = {};
            for (var n = 0, i = e.length; n < i; n++)t[e[n][0]] = e[n];
            var o, u, f, h, p, d, v;
            for (var n = l.shapeList.length - 1; n >= 0; n--) {
                d = a.get(l.shapeList[n], "seriesIndex");
                if (t[d] && !t[d][3] && l.shapeList[n].shape == "rectangle") {
                    v = a.get(l.shapeList[n], "dataIndex"), p = c[d];
                    if (t[d][2] && v == p.data.length - 1) {
                        r.delShape(l.shapeList[n].id);
                        continue
                    }
                    if (!t[d][2] && v === 0) {
                        r.delShape(l.shapeList[n].id);
                        continue
                    }
                    l.shapeList[n]._orient == "horizontal" ? (h = s.yAxis.getAxis(p.yAxisIndex || 0).getGap(), f = t[d][2] ? -h : h, o = 0) : (u = s.xAxis.getAxis(p.xAxisIndex || 0).getGap(), o = t[d][2] ? u : -u, f = 0), r.animate(l.shapeList[n].id, "").when(500, {position: [o, f]}).start()
                }
            }
        }

        function C() {
            var e, t, n, s, o, u, f, c, h;
            for (var p = 0, d = l.shapeList.length; p < d; p++)l.shapeList[p].shape == "rectangle" && (f = a.get(l.shapeList[p], "series"), c = a.get(l.shapeList[p], "dataIndex"), h = a.get(l.shapeList[p], "value"), e = l.deepQuery([f, i], "animationDuration"), t = l.deepQuery([f, i], "animationEasing"), l.shapeList[p]._orient == "horizontal" ? (n = l.shapeList[p].style.width, o = l.shapeList[p].style.x, h < 0 ? (r.modShape(l.shapeList[p].id, {
                style: {
                    x: o + n,
                    width: 0
                }
            }, !0), r.animate(l.shapeList[p].id, "style").when(e + c * 100, {
                x: o,
                width: n
            }).start(t)) : (r.modShape(l.shapeList[p].id, {style: {width: 0}}, !0), r.animate(l.shapeList[p].id, "style").when(e + c * 100, {width: n}).start(t))) : (s = l.shapeList[p].style.height, u = l.shapeList[p].style.y, h < 0 ? (r.modShape(l.shapeList[p].id, {style: {height: 0}}, !0), r.animate(l.shapeList[p].id, "style").when(e + c * 100, {height: s}).start(t)) : (r.modShape(l.shapeList[p].id, {
                style: {
                    y: u + s,
                    height: 0
                }
            }, !0), r.animate(l.shapeList[p].id, "style").when(e + c * 100, {y: u, height: s}).start(t))));
            l.animationMark(e, t)
        }

        var o = e("../component/base");
        o.call(this, t, r);
        var u = e("./calculableBase");
        u.call(this, r, i);
        var a = e("../util/ecData"), f = e("zrender/tool/color"), l = this;
        l.type = t.CHART_TYPE_BAR;
        var c, h = l.getZlevelBase(), p = {};
        l.getMarkCoord = S, l.animation = C, l.init = x, l.refresh = T, l.addDataAnimation = N, x(i, s)
    }

    return e("../chart").define("bar", t), t
}),define("echarts/chart/pie", ["require", "../component/base", "./calculableBase", "../util/ecData", "zrender/tool/math", "zrender/tool/util", "zrender/tool/color", "../util/accMath", "../chart"], function (e) {
    function t(t, n, r, i, s) {
        function g() {
            var e = s.legend;
            h.selectedMap = {}, m = {};
            var n, o, u;
            v = !1;
            var f;
            for (var l = 0, c = p.length; l < c; l++)if (p[l].type == t.CHART_TYPE_PIE) {
                p[l] = h.reformOption(p[l]), f = p[l].name || "", h.selectedMap[f] = e ? e.isSelected(f) : !0;
                if (!h.selectedMap[f])continue;
                n = h.parseCenter(r, p[l].center), o = h.parseRadius(r, p[l].radius), v = v || p[l].selectedMode, m[l] = [], h.deepQuery([p[l], i], "calculable") && (u = {
                    shape: o[0] <= 10 ? "circle" : "ring",
                    zlevel: d,
                    hoverable: !1,
                    style: {
                        x: n[0],
                        y: n[1],
                        r0: o[0] <= 10 ? 0 : o[0] - 10,
                        r: o[1] + 10,
                        brushType: "stroke",
                        lineWidth: 1,
                        strokeColor: p[l].calculableHolderColor || t.calculableHolderColor
                    }
                }, a.pack(u, p[l], l, undefined, -1), h.setCalculable(u), h.shapeList.push(u)), y(l), h.buildMark(p[l], l, s)
            }
            for (var l = 0, c = h.shapeList.length; l < c; l++)h.shapeList[l].id = r.newShapeId(h.type), r.addShape(h.shapeList[l])
        }

        function y(e) {
            var t = p[e], n = t.data, i = s.legend, o, u = 0, a = 0, f = 0, l = Number.NEGATIVE_INFINITY;
            for (var c = 0, d = n.length; c < d; c++)o = n[c].name, i ? h.selectedMap[o] = i.isSelected(o) : h.selectedMap[o] = !0, h.selectedMap[o] && !isNaN(n[c].value) && (+n[c].value !== 0 ? u++ : a++, f += +n[c].value, l = Math.max(l, +n[c].value));
            var v = 100, m, g = 0, y = t.clockWise, w = t.startAngle.toFixed(2) - 0, E, S = t.minAngle || .01,
                x = 360 - S * u - .01 * a, N, C = t.roseType, k, L, A;
            for (var c = 0, d = n.length; c < d; c++) {
                o = n[c].name;
                if (!h.selectedMap[o] || isNaN(n[c].value))continue;
                i ? N = i.getColor(o) : N = r.getColor(c), m = v, v = n[c].value / f, C != "area" ? E = y ? w - v * x - (v !== 0 ? S : .01) : v * x + w + (v !== 0 ? S : .01) : E = y ? w - 360 / d : 360 / d + w, E = E.toFixed(2) - 0, v = (v * 100).toFixed(2), k = h.parseRadius(r, t.radius), L = +k[0], A = +k[1], C == "radius" ? A = n[c].value / l * (A - L) * .8 + (A - L) * .2 + L : C == "area" && (A = Math.sqrt(n[c].value / l) * (A - L) + L);
                if (y) {
                    var O;
                    O = w, w = E, E = O
                }
                c > 0 && v < 4 && m < 4 && T(t, n[c], !1) && h.deepQuery([n[c], t], "itemStyle.normal.label.position") != "center" ? g += v < 4 ? 20 : -20 : g = 0, b(e, c, v, g, n[c].selected, L, A, w, E, N), y || (w = E)
            }
        }

        function b(e, t, n, r, i, s, o, u, f, l) {
            var c = w(e, t, n, i, s, o, u, f, l);
            a.pack(c, p[e], e, p[e].data[t], t, p[e].data[t].name, n), c._lastAddRadius = r, h.shapeList.push(c);
            var d = E(e, t, n, r, u, f, l, !1);
            d && (d._dataIndex = t, h.shapeList.push(d));
            var v = x(e, t, r, s, o, u, f, l, !1);
            v && (v._dataIndex = t, h.shapeList.push(v))
        }

        function w(e, t, n, s, o, u, a, l, g) {
            var y = p[e], b = y.data[t], w = [b, y], E = h.parseCenter(r, y.center),
                S = h.deepMerge(w, "itemStyle.normal") || {}, x = h.deepMerge(w, "itemStyle.emphasis") || {},
                C = h.getItemStyleColor(S.color, e, t, b) || g,
                k = h.getItemStyleColor(x.color, e, t, b) || (typeof C == "string" ? c.lift(C, -0.2) : C), L = {
                    shape: "sector",
                    zlevel: d,
                    clickable: !0,
                    style: {
                        x: E[0],
                        y: E[1],
                        r0: o,
                        r: u,
                        startAngle: a,
                        endAngle: l,
                        brushType: "both",
                        color: C,
                        lineWidth: S.borderWidth,
                        strokeColor: S.borderColor,
                        lineJoin: "round"
                    },
                    highlightStyle: {color: k, lineWidth: x.borderWidth, strokeColor: x.borderColor, lineJoin: "round"},
                    _seriesIndex: e,
                    _dataIndex: t
                };
            if (s) {
                var A = ((L.style.startAngle + L.style.endAngle) / 2).toFixed(2) - 0;
                L.style._hasSelected = !0, L.style._x = L.style.x, L.style._y = L.style.y;
                var O = h.query(y, "selectedOffset");
                L.style.x += f.cos(A, !0) * O, L.style.y -= f.sin(A, !0) * O, m[e][t] = !0
            } else m[e][t] = !1;
            v && (L.onclick = h.shapeHandler.onclick), h.deepQuery([b, y, i], "calculable") && (h.setCalculable(L), L.draggable = !0);
            if (T(y, b, !0) || N(y, b, !0)) L.onmouseover = h.shapeHandler.onmouseover;
            return L
        }

        function E(e, t, n, i, s, o, u, a) {
            var c = p[e], v = c.data[t];
            if (!T(c, v, a))return;
            var m = a ? "emphasis" : "normal",
                g = l.merge(l.clone(v.itemStyle) || {}, c.itemStyle, {overwrite: !1, recursive: !0}), y = g[m].label,
                b = y.textStyle || {}, w = h.parseCenter(r, c.center), E = w[0], x = w[1], N, C,
                k = ((o + s) / 2 + 360) % 360, L = h.parseRadius(r, c.radius), A, O = "middle";
            return y.position = y.position || g.normal.label.position, y.position == "center" ? (L = L[1], N = E, C = x, A = "center") : y.position == "inner" ? (L = (L[0] + L[1]) / 2 + i, N = Math.round(E + L * f.cos(k, !0)), C = Math.round(x - L * f.sin(k, !0)), u = "#fff", A = "center") : (L = L[1] - -g[m].labelLine.length + i, N = E + L * f.cos(k, !0), C = x - L * f.sin(k, !0), A = k >= 90 && k <= 270 ? "right" : "left"), y.position != "center" && y.position != "inner" && (N += A == "left" ? 20 : -20), v.__labelX = N - (A == "left" ? 5 : -5), v.__labelY = C, {
                shape: "text",
                zlevel: d + 1,
                hoverable: !1,
                style: {
                    x: N,
                    y: C,
                    color: b.color || u,
                    text: S(e, t, n, m),
                    textAlign: b.align || A,
                    textBaseline: b.baseline || O,
                    textFont: h.getFont(b)
                },
                highlightStyle: {brushType: "fill"},
                _seriesIndex: e,
                _dataIndex: t
            }
        }

        function S(e, t, n, r) {
            var i = p[e], s = i.data[t], o = h.deepQuery([s, i], "itemStyle." + r + ".label.formatter");
            if (!o)return s.name;
            if (typeof o == "function")return o(i.name, s.name, s.value, n);
            if (typeof o == "string")return o = o.replace("{a}", "{a0}").replace("{b}", "{b0}").replace("{c}", "{c0}").replace("{d}", "{d0}"), o = o.replace("{a0}", i.name).replace("{b0}", s.name).replace("{c0}", s.value).replace("{d0}", n), o
        }

        function x(e, t, n, i, s, o, u, a, c) {
            var v = p[e], m = v.data[t];
            if (N(v, m, c)) {
                var g = c ? "emphasis" : "normal",
                    y = l.merge(l.clone(m.itemStyle) || {}, v.itemStyle, {overwrite: !1, recursive: !0}),
                    b = y[g].labelLine, w = b.lineStyle || {}, E = h.parseCenter(r, v.center), S = E[0], x = E[1],
                    T = s, C = h.parseRadius(r, v.radius)[1] - -b.length + n, k = (u + o) / 2 % 360, L = f.cos(k, !0),
                    A = f.sin(k, !0);
                return {
                    shape: "brokenLine",
                    zlevel: d + 1,
                    hoverable: !1,
                    style: {
                        pointList: [[S + T * L, x - T * A], [S + C * L, x - C * A], [m.__labelX, m.__labelY]],
                        strokeColor: w.color || a,
                        lineType: w.type,
                        lineWidth: w.width
                    },
                    _seriesIndex: e,
                    _dataIndex: t
                }
            }
            return
        }

        function T(e, t, n) {
            return h.deepQuery([t, e], "itemStyle." + (n ? "emphasis" : "normal") + ".label.show")
        }

        function N(e, t, n) {
            return h.deepQuery([t, e], "itemStyle." + (n ? "emphasis" : "normal") + ".labelLine.show")
        }

        function C(e) {
            var n = l.merge;
            return e = n(e || {}, t.pie, {
                overwrite: !1,
                recursive: !0
            }), e.itemStyle.normal.label.textStyle = n(e.itemStyle.normal.label.textStyle || {}, t.textStyle, {
                overwrite: !1,
                recursive: !0
            }), e.itemStyle.emphasis.label.textStyle = n(e.itemStyle.emphasis.label.textStyle || {}, t.textStyle, {
                overwrite: !1,
                recursive: !0
            }), e
        }

        function k(e, t) {
            s = t, L(e)
        }

        function L(e) {
            e && (i = e, p = i.series), h.clear(), g()
        }

        function A(e) {
            var n = {};
            for (var i = 0, s = e.length; i < s; i++)n[e[i][0]] = e[i];
            var o = {}, u = {}, a = {}, f = l.clone(h.shapeList);
            h.shapeList = [];
            var c, d, v, m = {};
            for (var i = 0, s = e.length; i < s; i++)c = e[i][0], d = e[i][2], v = e[i][3], p[c] && p[c].type == t.CHART_TYPE_PIE && (d ? (v || (o[c + "_" + p[c].data.length] = "delete"), m[c] = 1) : v ? m[c] = 0 : (o[c + "_-1"] = "delete", m[c] = -1), y(c));
            var g, b;
            for (var i = 0, s = h.shapeList.length; i < s; i++) {
                c = h.shapeList[i]._seriesIndex, g = h.shapeList[i]._dataIndex, b = c + "_" + g;
                switch (h.shapeList[i].shape) {
                    case"sector":
                        o[b] = h.shapeList[i];
                        break;
                    case"text":
                        u[b] = h.shapeList[i];
                        break;
                    case"line":
                        a[b] = h.shapeList[i]
                }
            }
            h.shapeList = [];
            var w;
            for (var i = 0, s = f.length; i < s; i++) {
                c = f[i]._seriesIndex;
                if (n[c]) {
                    g = f[i]._dataIndex + m[c], b = c + "_" + g, w = o[b];
                    if (!w)continue;
                    if (f[i].shape == "sector") w != "delete" ? r.animate(f[i].id, "style").when(400, {
                        startAngle: w.style.startAngle,
                        endAngle: w.style.endAngle
                    }).start() : r.animate(f[i].id, "style").when(400, m[c] < 0 ? {endAngle: f[i].style.startAngle} : {startAngle: f[i].style.endAngle}).start(); else if (f[i].shape == "text" || f[i].shape == "line")if (w == "delete") r.delShape(f[i].id); else switch (f[i].shape) {
                        case"text":
                            w = u[b], r.animate(f[i].id, "style").when(400, {x: w.style.x, y: w.style.y}).start();
                            break;
                        case"line":
                            w = a[b], r.animate(f[i].id, "style").when(400, {
                                xStart: w.style.xStart,
                                yStart: w.style.yStart,
                                xEnd: w.style.xEnd,
                                yEnd: w.style.yEnd
                            }).start()
                    }
                }
            }
            h.shapeList = f
        }

        function O() {
            var e = h.query(i, "animationDuration"), t = h.query(i, "animationEasing"), n, s, o, u, f, l;
            for (var c = 0, p = h.shapeList.length; c < p; c++)h.shapeList[c].shape == "sector" || h.shapeList[c].shape == "circle" || h.shapeList[c].shape == "ring" ? (n = h.shapeList[c].style.x, s = h.shapeList[c].style.y, o = h.shapeList[c].style.r0, u = h.shapeList[c].style.r, r.modShape(h.shapeList[c].id, {
                rotation: [Math.PI * 2, n, s],
                style: {r0: 0, r: 0}
            }, !0), f = a.get(h.shapeList[c], "series"), l = a.get(h.shapeList[c], "dataIndex"), r.animate(h.shapeList[c].id, "style").when((h.query(f, "animationDuration") || e) + l * 10, {
                r0: o,
                r: u
            }).start("QuinticOut"), r.animate(h.shapeList[c].id, "").when((h.query(f, "animationDuration") || e) + l * 100, {rotation: [0, n, s]}).start(h.query(f, "animationEasing") || t)) : h.shapeList[c]._mark || (l = h.shapeList[c]._dataIndex, r.modShape(h.shapeList[c].id, {scale: [0, 0, n, s]}, !0), r.animate(h.shapeList[c].id, "").when(e + l * 100, {scale: [1, 1, n, s]}).start("QuinticOut"));
            h.animationMark(e, t)
        }

        function M(e) {
            if (!h.isClick || !e.target)return;
            var i, s = e.target, o = s.style, u = a.get(s, "seriesIndex"), l = a.get(s, "dataIndex");
            for (var c = 0, d = h.shapeList.length; c < d; c++)if (h.shapeList[c].id == s.id) {
                u = a.get(s, "seriesIndex"), l = a.get(s, "dataIndex");
                if (!o._hasSelected) {
                    var g = ((o.startAngle + o.endAngle) / 2).toFixed(2) - 0;
                    s.style._hasSelected = !0, m[u][l] = !0, s.style._x = s.style.x, s.style._y = s.style.y, i = h.query(p[u], "selectedOffset"), s.style.x += f.cos(g, !0) * i, s.style.y -= f.sin(g, !0) * i
                } else s.style.x = s.style._x, s.style.y = s.style._y, s.style._hasSelected = !1, m[u][l] = !1;
                r.modShape(s.id, s)
            } else h.shapeList[c].style._hasSelected && v == "single" && (u = a.get(h.shapeList[c], "seriesIndex"), l = a.get(h.shapeList[c], "dataIndex"), h.shapeList[c].style.x = h.shapeList[c].style._x, h.shapeList[c].style.y = h.shapeList[c].style._y, h.shapeList[c].style._hasSelected = !1, m[u][l] = !1, r.modShape(h.shapeList[c].id, h.shapeList[c]));
            n.dispatch(t.EVENT.PIE_SELECTED, e.event, {selected: m}), r.refresh()
        }

        function _(t, n) {
            if (!h.isDrop || !t.target)return;
            var r = t.target, o = t.dragged, u = a.get(r, "seriesIndex"), f = a.get(r, "dataIndex"), l, c = s.legend;
            if (f == -1) l = {
                value: a.get(o, "value"),
                name: a.get(o, "name")
            }, l.value < 0 && (l.value = 0), p[u].data.push(l), c && c.add(l.name, o.style.color || o.style.strokeColor); else {
                var d = e("../util/accMath");
                l = p[u].data[f], c && c.del(l.name), l.name += i.nameConnector + a.get(o, "name"), l.value = d.accAdd(l.value, a.get(o, "value")), c && c.add(l.name, o.style.color || o.style.strokeColor)
            }
            n.dragIn = n.dragIn || !0, h.isDrop = !1;
            return
        }

        function D(e, t) {
            if (!h.isDragend || !e.target)return;
            var n = e.target, r = a.get(n, "seriesIndex"), i = a.get(n, "dataIndex");
            s.legend && s.legend.del(p[r].data[i].name), p[r].data.splice(i, 1), t.dragOut = !0, t.needRefresh = !0, h.isDragend = !1;
            return
        }

        var o = e("../component/base");
        o.call(this, t, r);
        var u = e("./calculableBase");
        u.call(this, r, i);
        var a = e("../util/ecData"), f = e("zrender/tool/math"), l = e("zrender/tool/util"),
            c = e("zrender/tool/color"), h = this;
        h.type = t.CHART_TYPE_PIE;
        var p, d = h.getZlevelBase(), v, m = {};
        h.shapeHandler.onmouseover = function (e) {
            var t = e.target, n = a.get(t, "seriesIndex"), i = a.get(t, "dataIndex"), s = a.get(t, "special"),
                o = t._lastAddRadius, u = t.style.startAngle, f = t.style.endAngle, l = t.highlightStyle.color,
                c = E(n, i, s, o, u, f, l, !0);
            c && r.addHoverShape(c);
            var h = x(n, i, o, t.style.r0, t.style.r, u, f, l, !0);
            h && r.addHoverShape(h)
        }, h.reformOption = C, h.animation = O, h.init = k, h.refresh = L, h.addDataAnimation = A, h.onclick = M, h.ondrop = _, h.ondragend = D, k(i, s)
    }

    return e("../chart").define("pie", t), t
}),define("_chart", ["require", "echarts/chart/scatter", "echarts/chart/k", "echarts/chart/radar", "echarts/chart/chord", "echarts/chart/force", "echarts/chart/map", "echarts/util/mapData/geoJson/an_hui_geo", "echarts/util/mapData/geoJson/ao_men_geo", "echarts/util/mapData/geoJson/bei_jing_geo", "echarts/util/mapData/geoJson/china_geo", "echarts/util/mapData/geoJson/chong_qing_geo", "echarts/util/mapData/geoJson/fu_jian_geo", "echarts/util/mapData/geoJson/gan_su_geo", "echarts/util/mapData/geoJson/guang_dong_geo", "echarts/util/mapData/geoJson/guang_xi_geo", "echarts/util/mapData/geoJson/gui_zhou_geo", "echarts/util/mapData/geoJson/hai_nan_geo", "echarts/util/mapData/geoJson/hei_long_jiang_geo", "echarts/util/mapData/geoJson/he_bei_geo", "echarts/util/mapData/geoJson/he_nan_geo", "echarts/util/mapData/geoJson/hu_bei_geo", "echarts/util/mapData/geoJson/hu_nan_geo", "echarts/util/mapData/geoJson/jiang_su_geo", "echarts/util/mapData/geoJson/jiang_xi_geo", "echarts/util/mapData/geoJson/ji_lin_geo", "echarts/util/mapData/geoJson/liao_ning_geo", "echarts/util/mapData/geoJson/nei_meng_gu_geo", "echarts/util/mapData/geoJson/ning_xia_geo", "echarts/util/mapData/geoJson/qing_hai_geo", "echarts/util/mapData/geoJson/shang_hai_geo", "echarts/util/mapData/geoJson/shan_dong_geo", "echarts/util/mapData/geoJson/shan_xi_1_geo", "echarts/util/mapData/geoJson/shan_xi_2_geo", "echarts/util/mapData/geoJson/si_chuan_geo", "echarts/util/mapData/geoJson/tai_wan_geo", "echarts/util/mapData/geoJson/tian_jin_geo", "echarts/util/mapData/geoJson/world_geo", "echarts/util/mapData/geoJson/xiang_gang_geo", "echarts/util/mapData/geoJson/xin_jiang_geo", "echarts/util/mapData/geoJson/xi_zang_geo", "echarts/util/mapData/geoJson/yun_nan_geo", "echarts/util/mapData/geoJson/zhe_jiang_geo", "echarts/chart/line", "echarts/chart/bar", "echarts/chart/pie"], function (e) {
    e("echarts/chart/scatter"), e("echarts/chart/k"), e("echarts/chart/radar"), e("echarts/chart/chord"), e("echarts/chart/force"), e("echarts/chart/map"), e("echarts/util/mapData/geoJson/an_hui_geo"), e("echarts/util/mapData/geoJson/ao_men_geo"), e("echarts/util/mapData/geoJson/bei_jing_geo"), e("echarts/util/mapData/geoJson/china_geo"), e("echarts/util/mapData/geoJson/chong_qing_geo"), e("echarts/util/mapData/geoJson/fu_jian_geo"), e("echarts/util/mapData/geoJson/gan_su_geo"), e("echarts/util/mapData/geoJson/guang_dong_geo"), e("echarts/util/mapData/geoJson/guang_xi_geo"), e("echarts/util/mapData/geoJson/gui_zhou_geo"), e("echarts/util/mapData/geoJson/hai_nan_geo"), e("echarts/util/mapData/geoJson/hei_long_jiang_geo"), e("echarts/util/mapData/geoJson/he_bei_geo"), e("echarts/util/mapData/geoJson/he_nan_geo"), e("echarts/util/mapData/geoJson/hu_bei_geo"), e("echarts/util/mapData/geoJson/hu_nan_geo"), e("echarts/util/mapData/geoJson/jiang_su_geo"), e("echarts/util/mapData/geoJson/jiang_xi_geo"), e("echarts/util/mapData/geoJson/ji_lin_geo"), e("echarts/util/mapData/geoJson/liao_ning_geo"), e("echarts/util/mapData/geoJson/nei_meng_gu_geo"), e("echarts/util/mapData/geoJson/ning_xia_geo"), e("echarts/util/mapData/geoJson/qing_hai_geo"), e("echarts/util/mapData/geoJson/shang_hai_geo"), e("echarts/util/mapData/geoJson/shan_dong_geo"), e("echarts/util/mapData/geoJson/shan_xi_1_geo"), e("echarts/util/mapData/geoJson/shan_xi_2_geo"), e("echarts/util/mapData/geoJson/si_chuan_geo"), e("echarts/util/mapData/geoJson/tai_wan_geo"), e("echarts/util/mapData/geoJson/tian_jin_geo"), e("echarts/util/mapData/geoJson/world_geo"), e("echarts/util/mapData/geoJson/xiang_gang_geo"), e("echarts/util/mapData/geoJson/xin_jiang_geo"), e("echarts/util/mapData/geoJson/xi_zang_geo"), e("echarts/util/mapData/geoJson/yun_nan_geo"), e("echarts/util/mapData/geoJson/zhe_jiang_geo"), e("echarts/chart/line"), e("echarts/chart/bar"), e("echarts/chart/pie")
});