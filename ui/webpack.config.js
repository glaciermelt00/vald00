/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

const webpack                 = require('webpack');
const path                    = require('path');
const TsconfigPathsPlugin     = require('tsconfig-paths-webpack-plugin');
const UglifyJsPlugin          = require('uglifyjs-webpack-plugin');
const ExtractTextPlugin       = require("extract-text-webpack-plugin");
const OptimizeCssAssetsPlugin = require("optimize-css-assets-webpack-plugin");
const ManifestPlugin          = require("webpack-manifest-plugin");
const PACKAGE                 = require('./package.json');

const environment = process.env.NODE_ENV ? process.env.NODE_ENV : "development";

function getPlugin(mode) {
  if (mode === 'production') {
    return [
      new webpack.DefinePlugin({
        'NODE_ENV': JSON.stringify(environment)
      }),
      new ManifestPlugin(),
      new ExtractTextPlugin({ filename:'[name]/styles.css', allChunks: true }),
      new OptimizeCssAssetsPlugin({
        assetNameRegExp: /\.css$/g,
        cssProcessor: require('cssnano'),
        cssProcessorOptions: { safe: true, autoprefixer: {remove: false}, discardComments: { removeAll: true } },
        canPrint: true
      })
    ];
  } else {
    return [
      new webpack.DefinePlugin({
        'NODE_ENV': JSON.stringify(environment)
      }),
      new ExtractTextPlugin({ filename: '[name]/styles.css', allChunks: true })
    ];
  }
}

function getEntries() {
  return {
    'common':    [ './scss/10-common/index.scss'      ],
    'login':     [ './scss/11-login/index.scss'       ],
    'problem-a': [ './scss/13-problem-a/index.scss'   ],
  };
}

module.exports = (env, argv) => {
  const mode = argv.mode;
  return {
    plugins: getPlugin(mode),
    entry:   getEntries(),
    resolve: {
      plugins: [
        new TsconfigPathsPlugin({ configFile: './tsconfig.json' })
      ],
      extensions:[ '.ts', '.js', '.png' ]
    },
    output:  {
      path:     path.resolve(__dirname, 'dist'),
      filename:  '[name]/scripts.js'
    },
    optimization: { minimizer: [ new UglifyJsPlugin() ] },
    performance: { hints: false },
    module: {
      rules: [
        {
          test: /\.ts$/,
          use: 'ts-loader'
        },
        {
          test: /\.scss$/,
          use: ExtractTextPlugin.extract({
            fallback: 'style-loader',
            use: [
              { loader: 'css-loader' },
              { loader: 'sass-loader', options: {
                includePaths: [ 'node_modules', 'shared/scss' ]
              } }
            ]
          })
        },
        {
          test: /\.(jpg|jpeg|png)$/,
          use: [
            {
              loader: 'file-loader',
              options: {
                name:       mode === 'production' ? '[name].[ext]' : '../images/[name].[ext]',
                outputPath: 'images',
                publicPath: mode === 'production' ? '../images' : './'
              }
            }
          ]
        }
      ]
    }
  };
};
