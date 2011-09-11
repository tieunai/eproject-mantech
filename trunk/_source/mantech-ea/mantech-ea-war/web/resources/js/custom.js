$(document).ready(function() {

    //BOX LOGIN ERROR TEST//
    $("#error").click(function() {
        $("#box-login").show('shake', 55);
        $(".header-login").show('shake', 55);
        return false;
    });
		
    //LANGUAGE //
    $(".flag").hide();
    $(".language_button").click(function() {
        $(".flag").toggle('drop');
    });
		
    //BOX SORTABLE //
    $(".column.half").sortable({
        connectWith: '.column.half',
        handle: '.box-header'
    });
    $(".column.full").sortable({
        connectWith: '.column.full',
        handle: '.box-header'
    });
    $(".box").find(".box-header").prepend('<span class="close"></span>').end();
    $(".box-header .close ").click(function() {
        $(this).parents(".box .box-header").toggleClass("box-header closed").toggleClass("box-header");
        $(this).parents(".box:first").find(".box-content").toggle();
        $(this).parents(".box:first").find(".example").toggle();
    });
		
    //MESSAGE - TAG HIDE //
    $(".message").click(function() {
        $(this).hide('blind', 500);
        return false;
    });
    $(".tag").click(function() {
        $(this).hide('highlight', 500);
        return false;
    });
		
    //SEARCH INPUT//
    $("#search_input").focusin(
        function() {
            $('#search_input').val('');
        });
    $("#search_input").focusout(
        function() {
            $('#search_input').val('Search...');
        });
		
    //VALIDATION FORM//
    var validator = $("#formtest").validate({
        rules: { 
            firstname: {
                required: true, 
                minlength: 2
            },
            lastname: {
                required: true,
                minlength: 2
            },
            username: { 
                required: true, 
                minlength: 2
            }, 
            password: { 
                required: true, 
                minlength: 5 
            }, 
            password_confirm: { 
                required: true, 
                minlength: 5, 
                equalTo: "#form-password" 
            }, 
            email: { 
                required: true, 
                email: true
            }, 
            email_confirm: {
                required: true, 
                minlength: 5, 
                equalTo: "#form-email" 
            }, 
            dateformat: "required", 
            terms: "required" 
        }, 
        messages: { 
            firstname: "Enter your firstname", 
            lastname: "Enter your lastname", 
            username: { 
                required: "Enter a username", 
                minlength: jQuery.format("Enter at least {0} characters"), 
                remote: jQuery.format("{0} is already in use") 
            }, 
            password: { 
                required: "Provide a password", 
                rangelength: jQuery.format("Enter at least {0} characters") 
            }, 
            password_confirm: { 
                required: "Repeat your password", 
                minlength: jQuery.format("Enter at least {0} characters"), 
                equalTo: "Enter the same password as above" 
            }, 
            email: { 
                required: "Please enter a valid email address", 
                minlength: "Please enter a valid email address", 
                remote: jQuery.format("{0} is already in use") 
            }, 
            dateformat: "Choose your preferred dateformat", 
            terms: "Please accept terms of use" 
        }, 
        errorPlacement: function(error, element) { 
            if ( element.is(":radio") ) 
                error.appendTo( element.parent().prev() ); 
            else if ( element.is(":checkbox") ) 
                error.appendTo ( element.parent().prev() ); 
            else 
                error.appendTo( element.prev() ); 
        }, 
        submitHandler: function() { 
            alert("Validate!"); 
        }, 
        success: function(label) { 
            label.html("&nbsp;").addClass("valid_small"); 
        } 
    });
    $("#form-username").focus(function() {
        var firstname = $("#form-firstname").val();
        var lastname = $("#form-lastname").val();
        if(firstname && lastname && !this.value) {
            this.value = firstname + "." + lastname;
        }
    });
    $("#reset").click (function(){
        $("#formtest .form-field").val ("");
    });
		
    //TEXTAREA INPUT//
    $("#form-message").resizable({
        handle: "se",
        containment: '#formtest'
    });
    $("textarea.form-field").resizable({
        handle: "se",
        containment: '.box-content'
    });
			
    //CHECKBOX //
    $(".checkbox").button();
    $(".radiocheck").buttonset();
		
    //WYSIWYG//
    $('.wysiwyg').wysiwyg();
		
    //TABLE//
    oTable = $('.tabledata').dataTable({
        "bJQueryUI": true,
        "sPaginationType": "full_numbers"
    });

    $('.dtview').dataTable( {

        "iDisplayLength": 5,
        "sPaginationType": "scrolling",
        "aaSorting": [],
        "bInfo": false,
        "bFilter": false,
        "bLengthChange": false
    } );

    $('.comview').dataTable( {

        "iDisplayLength": 10,
        "sPaginationType": "scrolling",
        "aaSorting": [],
        "bInfo": false,
        "bFilter": false,
        "bLengthChange": false
    } );

    $("#checkboxall").click(function()
    {
        var checked_status = this.checked;
        $("input[name=checkall]").each(function()
        {
            this.checked = checked_status;
        });
    });
    $("#checkboxalltabs").click(function()
    {
        var checked_status = this.checked;
        $("input[name=checkalltabs]").each(function()
        {
            this.checked = checked_status;
        });
    });
    $("#checkboxalltabs2").click(function()
    {
        var checked_status = this.checked;
        $("input[name=checkalltabs2]").each(function()
        {
            this.checked = checked_status;
        });
    });
		
    //ACCORDION//
    $(".accordion").accordion();
		
    //DIALOG//
    $('.dialog').dialog({
        autoOpen: false,
        width: 800,
        height: 260,
        modal: true
    });
    $('.opener').click(function() {
        $('.dialog').dialog('open');
    });
		
    //DATAPICKER//
    $(".datepicker").datepicker();
		
    //TABS - SORTABLE//
    $(".tabs").tabs();
    $(".tabs.sortable").tabs().find(".ui-tabs-nav").sortable({
        axis:'x'
    });
		
    //SKIN//
    $(".skin_block").hide();
    $('.skin_button').click(function() {
        $(".skin_block").toggle('drop');
    });
		
    //SLIDER//
    $(".slider-vertical").slider({
        orientation: "vertical",
        range: "min",
        min: 0,
        max: 100,
        value: 60,
        slide: function(event, ui) {
            $(".amount-vert").val(ui.value);
        }
    });
    $(".amount-vert").val($(".slider-vertical").slider("value"));
		
    $(".slider-horizontal").slider({
        range: true,
        min: 0,
        max: 500,
        values: [75, 300],
        slide: function(event, ui) {
            $(".amount-hor").val('$' + ui.values[0] + ' - $' + ui.values[1]);
        }
    });
    $(".amount-hor").val('$' + $(".slider-horizontal").slider("values", 0) + ' - $' + $(".slider-horizontal").slider("values", 1));
		
    //PROGRESSBAR//
		
    $(".progressbar").progressbar({
        value:0
    });
    $(".progressbar .ui-progressbar-value").animate({
        width:'5%'
    }, 1500);
		
    $("#prog-10").click(function() {
        $(".progressbar .ui-progressbar-value").animate({
            width:'10%'
        }, 1500);
    });
    $("#prog-30").click(function() {
        $(".progressbar .ui-progressbar-value").animate({
            width:'30%'
        }, 1500);
    });
    $("#prog-50").click(function() {
        $(".progressbar .ui-progressbar-value").animate({
            width:'50%'
        }, 1500);
    });
    $("#prog-70").click(function() {
        $(".progressbar .ui-progressbar-value").animate({
            width:'70%'
        }, 1500);
    });
    $("#prog-100").click(function() {
        $(".progressbar .ui-progressbar-value").animate({
            width:'100%'
        }, 1500);
    });
		
    $(".progressbaractive").progressbar({
        value: 0
    });
    $(".progressbarpending").progressbar({
        value: 0
    });
    $(".progressbarsuspended").progressbar({
        value: 0
    });
		
    $(".progressbaractive .ui-progressbar-value").animate({
        width:'60%'
    }, 1500);
    $(".progressbarpending .ui-progressbar-value").animate({
        width:'30%'
    }, 1500);
    $(".progressbarsuspended .ui-progressbar-value").animate({
        width:'10%'
    }, 1500);

    $('.tip').poshytip({
        className: 'tip-theme',
        showTimeout: 1,
        alignTo: 'target',
        alignX: 'center',
        alignY: 'top',
        offsetX: 0,
        offsetY: 16,
        allowTipHover: false,
        fade: false,
        slide: false
    });

    // Tip that stays
    $('.tip-stay').poshytip({
        className: 'tip-theme',
        showOn:'focus',
        showTimeout: 1,
        alignTo: 'target',
        alignX: 'center',
        alignY: 'top',
        offsetX: 0,
        offsetY: 16,
        allowTipHover: false,
        fade: false,
        slide: false
    });

    //MENU
    $("#navbar li a.main-link").hover(function(){
        $("#navbar li a.close").fadeIn();
        $("#navbar li a.main-link").removeClass("active");
        $(this).addClass("active");
        $("#sub-link-bar").animate({
            height: "40px"
        });
        $(".sub-links").hide();
        $(this).siblings(".sub-links").fadeIn();
    });
    $("#navbar li a.close").click(function(){
        $("#navbar li a.main-link").removeClass("active");
        $(".sub-links").fadeOut();
        $("#sub-link-bar").animate({
            height: "10px"
        });
        $("#navbar li a.close").fadeOut();
    });
});

//-------------------------------------------------------------- */

// Tip for home icon etc.


$.fn.dataTableExt.oPagination.iTweenTime = 100;

$.fn.dataTableExt.oPagination.scrolling = {
    "fnInit": function ( oSettings, nPaging, fnCallbackDraw )
    {
        /* Store the next and previous elements in the oSettings object as they can be very
         * usful for automation - particularly testing
         */
        var nPrevious = document.createElement( 'div' );
        var nNext = document.createElement( 'div' );

        if ( oSettings.sTableId !== '' )
        {
            nPaging.setAttribute( 'id', oSettings.sTableId+'_paginate' );
            nPrevious.setAttribute( 'id', oSettings.sTableId+'_previous' );
            nNext.setAttribute( 'id', oSettings.sTableId+'_next' );
        }

        nPrevious.className = "paginate_disabled_previous";
        nNext.className = "paginate_disabled_next";

        nPrevious.title = oSettings.oLanguage.oPaginate.sPrevious;
        nNext.title = oSettings.oLanguage.oPaginate.sNext;

        nPaging.appendChild( nPrevious );
        nPaging.appendChild( nNext );

        $(nPrevious).click( function() {
            /* Disallow paging event during a current paging event */
            if ( typeof oSettings.iPagingLoopStart != 'undefined' && oSettings.iPagingLoopStart != -1 )
            {
                return;
            }

            oSettings.iPagingLoopStart = oSettings._iDisplayStart;
            oSettings.iPagingEnd = oSettings._iDisplayStart - oSettings._iDisplayLength;

            /* Correct for underrun */
            if ( oSettings.iPagingEnd < 0 )
            {
                oSettings.iPagingEnd = 0;
            }

            var iTween = $.fn.dataTableExt.oPagination.iTweenTime;
            var innerLoop = function () {
                if ( oSettings.iPagingLoopStart > oSettings.iPagingEnd ) {
                    oSettings.iPagingLoopStart--;
                    oSettings._iDisplayStart = oSettings.iPagingLoopStart;
                    fnCallbackDraw( oSettings );
                    setTimeout( function() {
                        innerLoop();
                    }, iTween );
                } else {
                    oSettings.iPagingLoopStart = -1;
                }
            };
            innerLoop();
        } );

        $(nNext).click( function() {
            /* Disallow paging event during a current paging event */
            if ( typeof oSettings.iPagingLoopStart != 'undefined' && oSettings.iPagingLoopStart != -1 )
            {
                return;
            }

            oSettings.iPagingLoopStart = oSettings._iDisplayStart;

            /* Make sure we are not over running the display array */
            if ( oSettings._iDisplayStart + oSettings._iDisplayLength < oSettings.fnRecordsDisplay() )
            {
                oSettings.iPagingEnd = oSettings._iDisplayStart + oSettings._iDisplayLength;
            }

            var iTween = $.fn.dataTableExt.oPagination.iTweenTime;
            var innerLoop = function () {
                if ( oSettings.iPagingLoopStart < oSettings.iPagingEnd ) {
                    oSettings.iPagingLoopStart++;
                    oSettings._iDisplayStart = oSettings.iPagingLoopStart;
                    fnCallbackDraw( oSettings );
                    setTimeout( function() {
                        innerLoop();
                    }, iTween );
                } else {
                    oSettings.iPagingLoopStart = -1;
                }
            };
            innerLoop();
        } );

        /* Take the brutal approach to cancelling text selection */
        $(nPrevious).bind( 'selectstart', function () {
            return false;
        } );
        $(nNext).bind( 'selectstart', function () {
            return false;
        } );
    },

    "fnUpdate": function ( oSettings, fnCallbackDraw )
    {
        if ( !oSettings.aanFeatures.p )
        {
            return;
        }

        /* Loop over each instance of the pager */
        var an = oSettings.aanFeatures.p;
        for ( var i=0, iLen=an.length ; i<iLen ; i++ )
        {
            if ( an[i].childNodes.length !== 0 )
            {
                an[i].childNodes[0].className =
                ( oSettings._iDisplayStart === 0 ) ?
                oSettings.oClasses.sPagePrevDisabled : oSettings.oClasses.sPagePrevEnabled;

                an[i].childNodes[1].className =
                ( oSettings.fnDisplayEnd() == oSettings.fnRecordsDisplay() ) ?
                oSettings.oClasses.sPageNextDisabled : oSettings.oClasses.sPageNextEnabled;
            }
        }
    }
}