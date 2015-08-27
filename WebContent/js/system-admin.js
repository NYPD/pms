$(document).ready(function() { 
	
	highlightNavButtons(['#admin','#system-settings']);
	
	//Cached Selectors
	var $adminSmallModal = $('#admin-small-modal');
	var $adminToolContainer = $("#admin-tool-container");
	var $adminSmallModalContent = $adminSmallModal.find(".modal-content");
	var $adminSidebarLinks = $(".admin-sidebar-link");
	
	/* Listeners */
	
	/* For Privileges Tool*/
	$adminToolContainer.on("click", ".priviledge-crud", function(){
		
		var $this = $(this);
		var isAdminPriviledge = $this.hasClass("priviledge-admin");
		var isClanPriviledge = $this.hasClass("priviledge-clan");
		
		if(isAdminPriviledge){
			
			var accountId = $this.closest("tr").find(".admin-account-id").val();
			
			var ajaxUrl = $this.data("ajax-url");
			
			var $getCrudModalContentPromise = $.get(ajaxUrl, {accountId: accountId});
			
		}else if(isClanPriviledge){
			
			var clanId = $this.closest("tr").find(".clan-id").val();
			
			var ajaxUrl = $this.data("ajax-url");
			
			var $getCrudModalContentPromise = $.get(ajaxUrl, {clanId: clanId});
		}
		
		$getCrudModalContentPromise.done(function(data){
			$adminSmallModalContent.empty().html(data);
			$adminSmallModal.modal();
		});
			
	});
});