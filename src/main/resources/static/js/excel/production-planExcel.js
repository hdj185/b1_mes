document.getElementById("production-planDownloadExcel").addEventListener("click", function() {
    var workbook = XLSX.utils.table_to_book(document.getElementById('productionDataTables2'), { sheet: 'SheetJS' });
    var wbout = XLSX.write(workbook, { bookType: 'xlsx', bookSST: true, type: 'array' });
    var file = new Blob([wbout], { type: 'application/octet-stream' });
    saveAs(file, '작업 계획 현황.xlsx');
});