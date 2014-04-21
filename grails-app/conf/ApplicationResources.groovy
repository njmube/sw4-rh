modules = {
    application {
        resource url:'js/application.js'
    }
    luxor {
    	dependsOn 'bootstrap'
    	resource url:'css/luxor.css'

    }
    forms {
        dependsOn 'jquery'
        resource url: 'js/autoNumeric.js'
        resource url: 'js/forms.js'
    }  
    datepicker{
        dependsOn 'jquery-ui'
        resource url:'js/jquery.ui.datepicker-es.js'
    }
    overrides {
        'bootstrap-css' {
            resource id: 'bootstrap-css', url:'/css/superhero.css'
        }
    }
    
}