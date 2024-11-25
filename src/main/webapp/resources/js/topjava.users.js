const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: function () {
        $.get(userAjaxUrl, updateTableData);
    }
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function onClickEnable(checkbox, userId) {
    let isEnable = checkbox.is(':checked')
    $.ajax({
        url: userAjaxUrl + userId,
        type: "POST",
        data: "enabled=" + isEnable
    }).done(function () {
        checkbox.closest("tr").attr("data-user-enabled", isEnable);
        successNoty(isEnable ? "Enabled" : "Disabled");
    }).fail(function () {
        $(checkbox).prop("checked", !isEnable);
    });
}