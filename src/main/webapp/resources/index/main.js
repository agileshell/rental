require(["../require-config"], function() {
    require(['jquery', 'bootstrap', 'common'], function() {

        //Enable sidebar toggle
        $("[data-toggle='offcanvas']").click(function(e) {
            $('.left-side').toggleClass("collapse-left");
            $(".right-side").toggleClass("strech");
        });

        /*
         * ADD SLIMSCROLL TO THE TOP NAV DROPDOWNS
         * ---------------------------------------
         */
        $(".navbar .menu").slimscroll({
            height: "200px",
            alwaysVisible: false,
            size: "3px"
        }).css("width", "100%");

        /* Sidebar tree view */
        $(".sidebar .treeview").tree();

         /*
         * Make sure that the sidebar is streched full height
         * ---------------------------------------------
         * We are gonna assign a min-height value every time the
         * wrapper gets resized and upon page load. We will use
         * Ben Alman's method for detecting the resize event.
         *
         **/
        function _fix() {
            //Get window height and the wrapper height
            var height = $(window).height() - $("body > .header").height() - ($("body > .footer").outerHeight() || 0);
            $(".wrapper").css("min-height", height + "px");
            var content = $(".wrapper").height();
            //If the wrapper height is greater than the window
            if (content > height)
                //then set sidebar height to the wrapper
                $(".left-side, html, body").css("min-height", content + "px");
            else {
                //Otherwise, set the sidebar to the height of the window
                $(".left-side, html, body").css("min-height", height + "px");
            }
        }
        //Fire upon load
        _fix();
        //Fire when wrapper is resized
        $(".wrapper").resize(function() {
            _fix();
            fix_sidebar();
        });

        //Fix the fixed layout sidebar scroll bug
        fix_sidebar();
    });
});