document.getElementById("orderDownloadExcel").addEventListener("click", function() {
    var workbook = XLSX.utils.table_to_book(document.getElementById('orderDataTables3'), { sheet: 'SheetJS' });
    var wbout = XLSX.write(workbook, { bookType: 'xlsx', bookSST: true, type: 'array' });
    var file = new Blob([wbout], { type: 'application/octet-stream' });
    saveAs(file, '수주현황.xlsx');
});