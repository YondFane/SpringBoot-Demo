<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        body {
            font-size: 14px;
            padding: 10px 15%;
        }
        h3 {
            text-align: center;
        }
        .pdf-table table{
            border-right:1px solid #cccccc;border-bottom:1px solid #cccccc;
        }
        .pdf-table tr td{
            min-width:140px;
            padding:10px;
            border-left:1px solid #cccccc;border-top:1px solid #cccccc
        }
        .pdf-table tr:last-of-type td{
            border-bottom:1px solid #cccccc;
        }

        .pdf-table tr td:nth-of-type(2n+2),td.col-4{
            border-right:1px solid #cccccc;

        }
    </style>
</head>
<body>

<div>
    <table class="pdf-table">
        <tr>
            <td colspan="4" class="add-desc col-4" style="text-align: center"><h3>受让资格确认通知书</h3></td>
        </tr>
        <tr>
            <td colspan="4" class="add-desc col-4"><b>基本信息</b></td>
        </tr>
        <tr>
            <td>项目名称</td>
            <td>${xiangMuName}</td>
            <td>项目编号</td>
            <td>${xiangMuBianHao}</td>
        </tr>
    </table>
</div>
</body>
</html>