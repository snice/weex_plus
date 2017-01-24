// You can install more packages below to config more as you like:
// eslint
// babel-eslint
// eslint-config-standard
// eslint-loader
// eslint-plugin-html
// eslint-plugin-promise
// eslint-plugin-standard
// postcss-cssnext

var path = require('path')
var fs = require('fs');
var webpack = require('webpack')

var entry = {};
var bannerExcludeFiles = [];

function walk(dir) {
    dir = dir || '.'
    var directory = path.join(__dirname, 'src', 'mobile', dir);
    fs.readdirSync(directory)
        .forEach(function (file) {
            var fullpath = path.join(directory, file);
            var stat = fs.statSync(fullpath);
            var extname = path.extname(fullpath);
            if (stat.isFile() && (extname === '.we' || extname === '.vue')) {
                var name = path.join('dist', 'mobile', dir, path.basename(file, extname));
                entry[name] = fullpath + '?entry=true';
                if (extname === '.we') {
                    bannerExcludeFiles.push(name + '.js')
                }
            } else if (stat.isDirectory() && file !== 'mobile' && file !== 'include') {
                var subdir = path.join(dir, file);
                walk(subdir);
            }
        });
}

walk();

var banner = '// { "framework": "Vue" }\n'

var bannerPlugin = new webpack.BannerPlugin(banner, {
    raw: true,
    exclude: bannerExcludeFiles
})

var webpackPlugins = [bannerPlugin]

var weexExpJsConfig = {
    entry: entry,
    output: {
        path: '.',
        filename: '[name].js'
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                loader: 'babel',
                exclude: /node_modules/
            },
            {
                test: /\.(we|vue)(\?[^?]+)?$/,
                loader: 'weex'
            }
        ]
    },
    plugins: webpackPlugins
}

module.exports = [weexExpJsConfig]