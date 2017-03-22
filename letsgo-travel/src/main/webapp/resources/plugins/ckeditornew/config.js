/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
    config.language = 'zh-cn';
    config.removePlugins='elementspath';
    config.enterMode = CKEDITOR.ENTER_BR;//：  屏蔽换行符<br>
    config.shiftEnterMode = CKEDITOR.ENTER_P;//:屏蔽段落<p>
    config.filebrowserUploadUrl = CONTEXTPATH + "/upload/uploadImg";
    config.image_previewText="";
    config.pasteFromWordRemoveFontStyles = false;
    config.pasteFromWordRemoveStyles = false;
   // config.forcePasteAsPlainText = true;
    config.allowedContent=true;
    config.title = false;
};
