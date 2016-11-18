var Lineindex=0;
var Dataindex=0;
var Mapindex=0;
var VBarindex=0;
var lBarindex=0;
var Areaindex=0;
 function pvBtnx(dom){
      //AJAX与echarts插件引用
   $.ajax({
    type:"get",
//  url:"analysis.open",
 url:"data/hour_data.json",//URL需要传参数进来
    async:true,
    success:function(data){
     loadData(data);
     PVLine(data);
     PVMap(data);
     PVvBar(data);
     /*PVarea(data);*/
    },
    error : function() {
     alert('请求失败');
    }
   })
 }
  //PV折线图
 function PVLine(data,time){
  //var LineClass=document.getElementsByClassName("echarts_line")
  var lineChart=echarts.init(document.getElementsByClassName("echarts_line")[Lineindex]);
  Lineindex+=1;
   if(Lineindex==sessionStorage.length){
    Lineindex=0;
   }
  lineChart.hideLoading();
  var data=data.timeSlot;
  var i=0;
  var option={
      title:{
       text:'页面访问总量统计图表',
       show:false,
       subtext:time,
       left:'center',
       textStyle:{
        color:'#fff'
       }
      },
      color:['#5fb7e5','#11ffe5'],
      legend:{
       left:'10%',
       top:'3%',
       data:[{
        name:"访问次数",
        icon:'roundRect'
       }],
       textStyle:{
        color:'#fff'
       }
      },
      
      tooltip:{
       trigger:'axis',
       axisPointer:{
        type:'line',
        lineStyle:{
         color:'#fff'
        }
       }
      },
      grid:{
       left:'3%',
       right:'4%',
       bottom:'3%',
       containLabel:true
      },
      xAxis:{
       type:'category',
       data:data.map(function(item){
        return item.time;
       }),
       axisLable:{
        formatter:function(value,idx){
         var date=new Date(value);
               return idx === 0 ? value : [date.getMonth() + 1, date.getDate()].join('-');
        }
       },
       axisLine: {
                 lineStyle: {
                     color: '#fff'
                 }
             },
       solitLine:{
        show:false
       },
       boundaryGap:false
      },
      yAxis: {
       name:'访问次数',
       type:'value',
             axisLabel: {
                 formatter: "{value}"
             },
             axisLine: {
                 lineStyle: {
                     color: '#fff'
                 }
             },
             splitNumber: 3,
             splitLine: {
                 show: false
             }
         },
         toolbox:{
          show:true,
          right:"15%",
          iconStyle:{
           normal:{
            borderColor:'#fff'
           },
           emphasis:{
            borderColor:'#3BFFFF'
           }
          },
          feature:{
           myTool1: {
                  show: true,
                  title: '切换为折线面积图',
                  icon: 'image://img/back.png',
                  onclick: function(){
                   for(var i=0;i<option.series.length;i++){
                    option.series[i].type="line"
                    option.series[i].areaStyle={normal: {}}
                   }
                   lineChart.setOption(option)
                  }
              },
              myTool2: {
                  show: true,
                  title: '切换为折线图',
                  icon: 'image://img/back.png',
                  onclick: function(){
                   for(var i=0;i<option.series.length;i++){
                    option.series[i].type="line"
                    option.series[i].areaStyle=false;
                   }
                   lineChart.setOption(option)
                  }
              },
              myTool3: {
               show: true,
                  title: '切换为柱形图',
                  icon: 'image://img/back.png',
                  onclick: function(){
                   for(var i=0;i<option.series.length;i++){
                    option.series[i].type="bar"
                   }
                   option.xAxis={
                    boundaryGap: ['20%', '20%'],
                    axisTick:{
                     alignWithLabel:true
                    }
                   }
                   lineChart.setOption(option)
                   option.xAxis={
                    boundaryGap: 0,
                    axisTick:{
                     alignWithLabel:true
                    }
                   }
                  }
              },
              myTool4: {
               show: true,
                  title: '切换为散点图',
                  icon: 'image://img/back.png',
                  onclick: function(){
                   for(var i=0;i<option.series.length;i++){
                    option.series[i].type="scatter"
                   }
                   option.xAxis={
                    boundaryGap: ['10%', '10%'],
                    axisTick:{
                     alignWithLabel:true
                    }
                   }
                   lineChart.setOption(option)
                   option.xAxis={
                    boundaryGap: 0,
                    axisTick:{
                     alignWithLabel:true
                    }
                   }
                  }
              },
           saveAsImage:{
            
           },
              myTool5: {
               show: true,
                  title: '返回上一层',
                  icon: 'image://img/back.png',
                  onclick: function (){
                   lineChart.showLoading();
                   var rushDate1=data[0].time.toString().replace(/-/g,"");
                         var rushDate2=rushDate1.replace(' ',"");
                         var rushDate3=rushDate2.replace(':',"");
                         var startTime=parseInt(Number(rushDate3)/10000)*100;
                         var endTime=((parseInt(Number(rushDate3)/10000))+1)*100;
                         console.log(startTime+"|"+endTime)
                   if(startTime.toString().length>=6){
                    var time = 'free';
                    if(startTime.toString().length == 6){
            selectMonth(time,startTime,endTime,'analysis.open');
           }else if(startTime.toString().length == 8){
            selectDaily(time,startTime,endTime,'analysis.open');
           }else if(startTime.toString().length == 10){
            selectHour(time,startTime,endTime,'analysis.open');
           }else if(startTime.toString().length == 12){
            selectMinute(time,startTime,endTime,'analysis.open');
           }
                   }
                  }
              }
          }
         },
         dataZoom: [
             {
                 type: 'inside',
                 start: 0,
                 end: 100,
              throttle:0
             },
      ],
         series:[{
          name:"访问次数",
          type:'line',
          data:data.map(function(item){
           return item["pvNum"];
          }),
          lineStyle:{
           normal:{
            color:'#5fb7e5'
           }
          },
          areaStyle: {normal: {}},
          symbolSize:10,
          showSymbol:false
         }
         ]
     }
  lineChart.setOption(option)
  //点击选择时间段
  lineChart.on('click', function (params) {
      var rushDate1=params.name.replace(/-/g,"")
      var rushDate2=rushDate1.replace(' ',"")
      var rushDate3=rushDate2.replace(':',"")
      var startTime=rushDate3+'00'
      var endTime=(Number(rushDate3)+1)*100
      if(startTime.length<=12){
       lineChart.showLoading();
       var time = 'free';
       if(startTime.length == 8){
        selectDaily(time,startTime,endTime,'analysis.open');
       }else if(startTime.length == 10){
        selectHour(time,startTime,endTime,'analysis.open');
       }else if(startTime.length == 12){
        selectMinute(time,startTime,endTime,'analysis.open');
       }
   };
  });
 }
 //PV地图
 function PVMap(data){
  //var MapClass=document.getElementsByClassName("echarts_map")
  var mapChart=echarts.init(document.getElementsByClassName("echarts_map")[Mapindex]);
  Mapindex+=1;
   if(Mapindex==sessionStorage.length){
    Mapindex=0;
   }
  function randomData() {
      return Math.round(Math.random()*1000);
  }
  var option={
   title: {
          text: 'PV/IP/流量 地理视图',
          show:false,
          subtext: 'MAP DEMO',
          left: 'center',
          textStyle:{
           color:"#fff"
          }
      },
      color:['#5fb7e5','#11ffe5','#ffa800'],
      tooltip: {
          trigger: 'item'
      },
      legend: {
          orient: 'vertical',
          left: '5%',
          data:['访问次数','数据流量','IP数量'],
          textStyle:{
           color:"#fff"
          }
      },
      visualMap: {
          min: 0,
          max: 2500,
          left: '5%',
          top: 'bottom',
          calculable: true,
          inRange: {
              color: ['#fff', '#0075f8']
          },
          textStyle:{
           color:"#fff"
          }
      },
      toolbox: {
          show:true,
       right:"5%",
       iconStyle:{
        normal:{
         borderColor:'#fff'
        },
        emphasis:{
         borderColor:'#3BFFFF'
        }
       },
          orient: 'vertical',
          top: 'center',
          feature: {
              restore: {},
              saveAsImage: {}
          }
      },
      series: [
          {
              name: '访问次数',
              type: 'map',
              mapType: 'china',
              roam: false,
              label: {
                  normal: {
                      show: true,
                      textStyle:{
                       color:'#999'
                      }
                  },
                  emphasis: {
                      show: true,
                      textStyle:{
                       color:'#000'
                      }
                  }
              },
              itemStyle: {
               normal: {
                   areaColor: '#fff',
                   borderColor: '#111'
               },
                  emphasis: {
                   areaColor: '#3BFFFF',
                      borderColor: '#fff',
                      borderWidth: 1
                  }
              },
              data:[
                  {name: '北京',value: randomData() },
                  {name: '天津',value: randomData() },
                  {name: '上海',value: randomData() },
                  {name: '重庆',value: randomData() },
                  {name: '河北',value: randomData() },
                  {name: '河南',value: randomData() },
                  {name: '云南',value: randomData() },
                  {name: '辽宁',value: randomData() },
                  {name: '黑龙江',value: randomData() },
                  {name: '湖南',value: randomData() },
                  {name: '安徽',value: randomData() },
                  {name: '山东',value: randomData() },
                  {name: '新疆',value: randomData() },
                  {name: '江苏',value: randomData() },
                  {name: '浙江',value: randomData() },
                  {name: '江西',value: randomData() },
                  {name: '湖北',value: randomData() },
                  {name: '广西',value: randomData() },
                  {name: '甘肃',value: randomData() },
                  {name: '山西',value: randomData() },
                  {name: '内蒙古',value: randomData() },
                  {name: '陕西',value: randomData() },
                  {name: '吉林',value: randomData() },
                  {name: '福建',value: randomData() },
                  {name: '贵州',value: randomData() },
                  {name: '广东',value: randomData() },
                  {name: '青海',value: randomData() },
                  {name: '西藏',value: randomData() },
                  {name: '四川',value: randomData() },
                  {name: '宁夏',value: randomData() },
                  {name: '海南',value: randomData() },
                  {name: '台湾',value: randomData() },
                  {name: '香港',value: randomData() },
                  {name: '澳门',value: randomData() }
              ]
          },
          {
              name: '数据流量',
              type: 'map',
              mapType: 'china',
              label: {
                  normal: {
                      show: true
                  },
                  emphasis: {
                      show: true
                  }
              },
              itemStyle: {
               normal: {
                   areaColor: '#fff',
                   borderColor: '#111',
                      textStyle:'#fff'
               },
                  emphasis: {
                   areaColor: '#3BFFFF',
                      borderColor: '#fff',
                      borderWidth: 1
                  }
              },
              data:[
                  {name: '北京',value: randomData() },
                  {name: '天津',value: randomData() },
                  {name: '上海',value: randomData() },
                  {name: '重庆',value: randomData() },
                  {name: '河北',value: randomData() },
                  {name: '安徽',value: randomData() },
                  {name: '新疆',value: randomData() },
                  {name: '浙江',value: randomData() },
                  {name: '江西',value: randomData() },
                  {name: '山西',value: randomData() },
                  {name: '内蒙古',value: randomData() },
                  {name: '吉林',value: randomData() },
                  {name: '福建',value: randomData() },
                  {name: '广东',value: randomData() },
                  {name: '西藏',value: randomData() },
                  {name: '四川',value: randomData() },
                  {name: '宁夏',value: randomData() },
                  {name: '香港',value: randomData() },
                  {name: '澳门',value: randomData() }
              ]
          },
          {
              name: 'IP数量',
              type: 'map',
              mapType: 'china',
              label: {
                  normal: {
                      show: true,
                      color:'#fff',
                      textStyle:'#fff'
                  },
                  emphasis: {
                      show: true
                  }
              },
              itemStyle: {
               normal: {
                   areaColor: '#fff',
                   borderColor: '#111',
               },
                  emphasis: {
                   areaColor: '#3BFFFF',
                      borderColor: '#fff',
                      borderWidth: 1
                  }
              },
              data:[
                  {name: '北京',value: randomData() },
                  {name: '天津',value: randomData() },
                  {name: '上海',value: randomData() },
                  {name: '广东',value: randomData() },
                  {name: '台湾',value: randomData() },
                  {name: '香港',value: randomData() },
                  {name: '澳门',value: randomData() }
              ]
          }
      ]
  }
  option.legend.selectedMode = 'single';
  mapChart.setOption(option)
 };
 //PV横向条形图
 function PVvBar(data){
  var data=data.timeSlot;
  //var VBarClass=document.getElementsByClassName("echarts_Vbar");
  var vBarChart=echarts.init(document.getElementsByClassName("echarts_Vbar")[VBarindex]);
  VBarindex+=1;
   if(VBarindex==sessionStorage.length){
    VBarindex=0;
   }
  var option = {
     title: {
          text: '页面访问总量统计表',
          show:false,
          subtext: 'BAR DEMO',
          left: 'center',
          textStyle:{
           color:"#fff"
          }
      },
  color:['#5fb7e5'],
     tooltip: {
         trigger: 'axis',
         axisPointer: {
             type: 'shadow'
         }
     },
     legend:{
       data:[{
        name:"访问次数",
        icon:'roundRect'
       }],
       left:'10%',
       top:'3%',
       textStyle:{
        color:'#fff'
       }
      },
  dataZoom: [
         {
             type: 'slider',
             show: true,
             yAxisIndex: [0],
             top:'9.7%',
             left: '93%',
             start: 50,
             end: 100,
          textStyle:{
           color:"#fff"
          }
         }
     ],
     grid: {
         left: '3%',
         right: '4%',
         bottom: '3%',
         containLabel: true
     },
     xAxis: {
         type: 'value',
         boundaryGap: [0, 0.01],
   axisLine: {
                 lineStyle: {
                     color: '#fff'
                 }
             }
     },
     yAxis: {
         type: 'category',
         data: data.map(function(item){
           return "url";
          }),
   axisLine: {
                 lineStyle: {
                     color: '#fff'
                 }
             }
 },
     series: [
         {
             name: '访问次数',
             type: 'bar',
             data: data.map(function(item){
           return item.pvNum;
          })
         }
     ]
 }
  vBarChart.setOption(option)
  vBarChart.on('click', function (params) {
   var thisDom=this._dom;
   var url=params.name;
   console.log(params.name)
      $('.echarts_Vbar').css("width","40%")
      $('.echarts_lBar').css({"width":"59%","height":"100%"})
   vBarChart=echarts.init(this._dom);
   vBarChart.setOption(option)
   PVBar_l(data,url);
   vBarChart.on('click', function (params) {
    var url=params.name;
    PVBar_l(data,url);
   })
  });
 }

 //PV矩形树图（面积图）
 /*function PVarea(data){
  //var areaClass=document.getElementsByClassName("echarts_area")
  var areaChart=echarts.init(document.getElementsByClassName("echarts_area")[Areaindex]);
  Areaindex+=1;
   if(Areaindex==sessionStorage.length){
    Areaindex=0;
   }
  var data=data.timeSlot;
  var dataArr=[];
  for(var i=0;i<data.length;i++){
   dataArr.push({});
   dataArr[i].name=data[i].time;
   dataArr[i].value=data[i].pvNum;
  }
  areaChart.setOption(option = {
         title : {
             text: '访问量TOP50 URL统计表',
             subtext: 'areaDemo',
             left: 'center',
          textStyle:{
           color:"#fff"
          }
         },
         tooltip : {
             trigger: 'item',
             formatter: "{b}</br> 访问次数：{c}"
         },
         toolbox: {
          show:true,
       right:"15%",
       iconStyle:{
        normal:{
         borderColor:'#fff'
        },
        emphasis:{
         borderColor:'#3BFFFF'
        }
       },
          feature: {
              restore: {},
              saveAsImage: {}
          }
      },
         calculable : false,
         series : [
             {
                 name:'页面访问量统计',
                 type:'treemap',
                 itemStyle: {
                     normal: {
                         label: {
                             show: true,
                             formatter: "{b}"
                         },
                         borderWidth: 1
                     },
                     emphasis: {
                         label: {
                             show: true
                         }
                     }
                 },
                 data:dataArr
             }
         ]
     })
 }*/


