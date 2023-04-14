$(function () {
    $("#signOut").click(function () {
        $.ajax({
            type: "POST",
            url: "/sign/out",
            success: function () {
                document.location.reload();
            }
        });
    });
});