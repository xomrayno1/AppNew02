var glob = require('glob');
var Canvas = require('canvas');
var echarts = require('echarts');
var fs = require('fs');
var path = require('path');

require('echarts/map/js/china');

var options = {
    bar: require('./option/bar'),
    area: require('./option/area'),
    scatter: require('./option/scatter'),
    pie: require('./option/pie'),
    graph: require('./option/graph'),
    map: require('./option/map')
};
var WIDTH = 600;
var HEIGHT = 400;
echarts.setCanvasCreator(function () {
    return createCanvas();
});

var font = new Canvas.Font('Helvetica', '/System/Library/Fonts/Helvetica.dfont');
font.addFace('/System/Library/Fonts/Helvetica.dfont', 'bolder');

glob('../*.js', function (err, themePathList) {
    themePathList.forEach(function (themePath) {
        var themeName = path.basename(themePath, '.js');
        var canvasList = [];
        require(themePath);
        echarts.util.each(options, function (option) {
            var canvas = createCanvas();
            var chart = echarts.init(canvas, themeName);
            var optionNeedFix = option;
            if (option.options) {
                optionNeedFix = option.options[0];
            }
            canvasList.push(canvas);
            optionNeedFix.animation = false;
            optionNeedFix.textStyle = {
                fontFamily: 'Helvetica',
                fontSize: 12
            };
            chart.setOption(option);
            chart.dispose();
        });

        var columnCount = 2;
        var outputCanvas = new Canvas(WIDTH * columnCount, HEIGHT * canvasList.length / columnCount);
        var outputCtx = outputCanvas.getContext('2d');
        canvasList.forEach(function (canvas, idx) {
            outputCtx.drawImage(canvas, idx % columnCount * WIDTH, Math.floor(idx / columnCount) * HEIGHT, WIDTH, HEIGHT);
        });

        fs.writeFileSync('../thumb/' + themeName + '.png', outputCanvas.toBuffer());
    });
});
function createCanvas() {
    var canvas = new Canvas(WIDTH, HEIGHT);
    var ctx = canvas.getContext('2d');
    ctx.addFont(font);
    return canvas;
}