<%@ page import="org.grails.plugin.easygrid.JsUtils" defaultCodec="none" %>

<jq:jquery>
    var oTable = $('#${attrs.id}_datatable').dataTable({
    ${JsUtils.convertToJs(gridConfig.dataTables, true)},
    "sAjaxSource": '${g.createLink(controller: attrs.controller, action: "${gridConfig.id}Rows", params: params)}',
        "fnInitComplete":easygrid.initComplete('${attrs.id}',${gridConfig.fixedColumns == true}, ${gridConfig.noFixedColumns?:-1}),
        "fnRowCallback": function (nRow, aData, iDisplayIndex, iDisplayIndexFull) { },
        "fnServerParams": easygrid.serverParams('${attrs.id}'),
        "aoColumns": [
    <grid:eachColumn gridConfig="${gridConfig}">
        <g:if test="${col.render}">
            {${JsUtils.convertToJs(col.dataTables + [sName: col.name, bSearchable: col.enableFilter, bSortable: col.sortable], true)}
            <g:if test="${col.otherProperties}">
                ,${col.otherProperties}
            </g:if>
            },
        </g:if>
    </grid:eachColumn>
    ]

});

/* Add the events etc before DataTables hides a column */
$("tfoot input").keyup(function () {
    /* Filter on the column (the index) of this element */
    oTable.fnFilter(this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("tfoot input").index(this)));
});

/*
 * Support functions to provide a little bit of 'user friendlyness' to the textboxes
 */
$("tfoot input").each(function (i) {
    this.initVal = this.value;
});

$("tfoot input").focus(function () {
    if (this.className == "search_init") {
        this.className = "";
        this.value = "";
    }
});

$("tfoot input").blur(function (i) {
    if (this.value == "") {
        this.className = "search_init";
        this.value = this.initVal;
    }
});
</jq:jquery>


<table id="${attrs.id}_datatable" cellpadding="0" cellspacing="0" border="0"
       class="display">%{--width="${gridConfig.datatable.width}">--}%
    <thead>
    <tr>
        <g:each in="${gridConfig.columns}" var="col">
            <th>${g.message(code: col.label, default: col.label)}</th>
        </g:each>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td colspan="${gridConfig.columns.size()}" class="dataTables_empty">Loading data from server</td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
        <grid:eachColumn gridConfig="${gridConfig}">
            <td>
                <g:if test="${(gridConfig.fixedColumns != 'true') && gridConfig.enableFilter && col.enableFilter}">
                %{--todo - add variables--}%
                    <input type="text" name="search_${col.name}" class="search_init" size="10"/>
                </g:if>
                <g:else>
                    &nbsp;
                </g:else>
            </td>
        </grid:eachColumn>
    </tr>
    </tfoot>
</table>

