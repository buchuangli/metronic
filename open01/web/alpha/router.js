define(["backbone"],function(){
	var Router = Backbone.Router.extend({

	  routes: {
	    "home":                 "home",  
	    "manage":               "manage",
	    "analysis":             "analysis", 
	    "serch":                "serch",
	    "help":                 "help", 
	    "dosage":               "dosage",
	    "upload":				"upload",
	    "options":              "options",
	    "resetPassword":        "resetPassword",
	    "recycle":              "recycle",
	    "*actions":				"index"
	  },
	
	  home: function() {
	    require(["mod/home.js"],function(home){
	    	
	    	home.render();
	    })
	  },
	  manage: function() {
	    require(["mod/manage.js"],function(manage){
	    	
	    	manage.render();
	    })
	  },
	  analysis: function() {
	    require(["mod/analysis.js"],function(analysis){
	    	
	    	analysis.render();
	    })
	  },
	  serch: function() {
	    require(["mod/serch.js"],function(serch){
	    	
	    	serch.render();
	    })
	  },
	  help: function() {
	    require(["mod/help.js"],function(help){
	    	
	    	help.render();
	    })
	  },
	  dosage: function() {
	    require(["mod/dosage.js"],function(dosage){
	    	
	    	dosage.render();
	    })
	  },
	  upload: function() {
	    require(["mod/upload.js"],function(upload){
	    	
	    	upload.render();
	    })
	  },
	  options: function() {
	    require(["mod/options.js"],function(options){
	    	
	    	options.render();
	    })
	  },
	  resetPassword: function() {
	    require(["mod/resetPassword.js"],function(resetPassword){
	    	
	    	resetPassword.render();
	    })
	  },
	  recycle: function() {
	    require(["mod/recycle.js"],function(recycle){
	    	
	    	recycle.render();
	    })
	  },
	  index: function() {
	    location.hash="home"
	  }
	
	});
	
	var router=new Router();
	return router;
})