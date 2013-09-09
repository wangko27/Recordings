define([
	'dijit/layout/BorderContainer',
    'dojo/_base/declare',
    'dijit/_WidgetsInTemplateMixin', 
    'dijit/_TemplatedMixin',
    'dojo/text!./templates/jrrecordings.html'
], function( BorderContainer, declare, WidgetsInTemplateMixin, TemplatedMixin, template ) {
    
    return declare([BorderContainer, TemplatedMixin, WidgetsInTemplateMixin], {
        templateString: template,
        baseClass: 'jrrecordings'
});
