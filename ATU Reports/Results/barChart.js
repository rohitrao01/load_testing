            $(document).ready(function(){
                var s1 = [0,0,0,0,0,0,0,0,0,0];
                var s2 = [1,1,1,1,0,0,0,1,0,1];
                var s3 = [0,0,0,0,0,0,0,0,0,0];

var ticks = [55,56,57,58,59,60,61,62,63,64];    $.jqplot('bar', [s1, s2, s3], {
        animate: true,axesDefaults:{min:0,tickInterval: 9},        seriesColors: ["#7BB661", "#E03C31", "#21ABCD"],
        stackSeries: false,
        seriesDefaults: {
            renderer: $.jqplot.BarRenderer,
            pointLabels: {show: true}
            , rendererOptions: {barWidth: 12, barMargin: 25, fillToZero: true}
        }
        ,
        grid: {borderColor: '#ffffff', background: '#ffffff',
            borderWidth: 0.5, // pixel width of border around grid.
            shadow: false // draw a shadow for grid.
        }
        ,
        legend: {
            show: true,
            location: 'e',
            placement: 'outside',
            labels: ['Passed', 'Failed', 'Skipped']
        },
        axes: {
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ticks,
                label: "Run Number"
            }
            ,
            yaxis: {
                label: "TC Number",
                tickOptions: {
                    formatString: "%dtc"
                }
            }
        }
    });
});
