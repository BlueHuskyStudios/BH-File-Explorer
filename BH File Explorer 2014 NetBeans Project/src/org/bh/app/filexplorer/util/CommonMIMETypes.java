package org.bh.app.filexplorer.util;

import static org.bh.app.filexplorer.util.MIMEType.STANDARDS_TYPE_APPLICATION;
import static org.bh.app.filexplorer.util.MIMEType.STANDARDS_TYPE_IMAGE;
import static org.bh.app.filexplorer.util.MIMEType.STANDARDS_TYPE_TEXT;

/**
 * FilterDelegate, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-20
 */
public interface CommonMIMETypes
{
	/** A standard application MIME Type {@value} */
	public static final MIMEType
		APP_ATOM         = new Ap("atom","xml"),
		APP_DART         = new Ap("dart"),
		APP_ECMA_SCRIPT  = new Ap("ecmascript"),
		APP_EDI_X12      = new Ap("EDI-X12"),
		APP_EDIFACT      = new Ap("EDIFACT"),
		APP_JSON         = new Ap("json"),
		APP_JAVASCRIPT   = new Ap("javascript"),
		APP_OCTET_STREAM = new Ap("octet-stream"),
		APP_OGG          = new Ap("ogg"),
		APP_PDF          = new Ap("pdf"),
		APP_POSTSCRIPT   = new Ap("postscript"),
		APP_RDF          = new Ap("rdf","xml"),
		APP_RSS          = new Ap("rss","xml"),
		APP_SOAP         = new Ap("soap","xml"),
		APP_WOFF         = new Ap("font-woff"),
		APP_XHTML        = new Ap("xhtml","xml"),
		APP_XML          = new Ap("xml"),
		APP_DTD          = new Ap("xml-dtd"),
		APP_XOP          = new Ap("xop","xml"),
		APP_ZIP          = new Ap("zip"),
		APP_GZIP         = new Ap("gzip"),
		APP_EXAMPLE      = new Ap("example"),
		APP_NACL         = new Ap("x-nacl"),
		APP_PNACL        = new Ap("x-pnacl");
	
	/** A standard audio MIME Type {@value} */
	public static final MIMEType
		AUD_BASIC         = new Au("basic"),
		AUD_LINEAR_PCM_24 = new Au("L24"),
		AUD_MP4           = new Au("mp4"),
		AUD_MPEG          = new Au("mpeg"),
		AUD_MP3           = AUD_MPEG,
		AUD_OGG           = new Au("ogg"),
		AUD_OPUS          = new Au("opus"),
		AUD_VORBIS        = new Au("vorbis"),
		AUD_REAL_AUDIO    = new Au("vnd","rn-realaudio"),
		AUD_WAV           = new Au("vnd","wave"),
		AUD_WEBM          = new Au("webm"),
		AUD_EXAMPLE       = new Au("example")
	;
	
	/** A special example MIME Type */
	public static final MIMEType
		EXAMPLE = new MIMEType(null, "example")
		{
			@Override
			public String toString()
			{
				return "example";
			}
		}
	;
	
	/** A standard image MIME Type {@value} */
	public static final MIMEType
		IMG_GIF     = new I("gif"),
		IMG_JPG     = new I("jpeg"),
		IMG_PJPG    = new I("pjpeg"),
		IMG_PNG     = new I("png"),
		IMG_SVG     = new I(null,"svg","xml"),
		IMG_DJVU    = new I("vnd","djvu",null),
		IMG_EXAMPLE = new I("example")
	;
	
	/*
	Type message
	message/http: Defined in RFC 7230 
	message/imdn+xml: IMDN Instant Message Disposition Notification; Defined in RFC 5438 
	message/partial: Email; Defined in RFC 2045  and RFC 2046 
	message/rfc822: Email; EML files, MIME files, MHT files, MHTML files; Defined in RFC 2045  and RFC 2046 
	message/example: example in documentation, Defined in RFC 4735 


	Type model[edit]
	For 3D models.

	model/iges: IGS files, IGES files; Defined in RFC 2077 
	model/mesh: MSH files, MESH files; Defined in RFC 2077 , SILO files
	model/vrml: WRL files, VRML files; Defined in RFC 2077 
	model/x3d+binary: X3D ISO standard for representing 3D computer graphics, X3DB binary files - never Internet Assigned Numbers Authority approved
	model/x3d+fastinfoset: X3D ISO standard for representing 3D computer graphics, X3DB binary files (application in process, this replaces any use of model/x3d+binary)
	model/x3d-vrml: X3D ISO standard for representing 3D computer graphics, X3DV VRML files (application in process, previous uses may have been model/x3d+vrml)
	model/x3d+xml: X3D ISO standard for representing 3D computer graphics, X3D XML files
	model/example: example in documentation, Defined in RFC 4735 


	Type multipart[edit]
	For archives and other objects made of more than one part.

	multipart/mixed: MIME Email; Defined in RFC 2045  and RFC 2046 
	multipart/alternative: MIME Email; Defined in RFC 2045  and RFC 2046 
	multipart/related: MIME Email; Defined in RFC 2387  and used by MHTML (HTML mail)
	multipart/form-data: MIME Webform; Defined in RFC 2388 
	multipart/signed: Defined in RFC 1847 
	multipart/encrypted: Defined in RFC 1847 
	multipart/example: example in documentation, Defined in RFC 4735 


	Type text[edit]
	For human-readable text and source code.

	text/cmd: commands; subtype resident in Gecko browsers like Firefox 3.5
	text/css: Cascading Style Sheets; Defined in RFC 2318 
	text/csv: Comma-separated values; Defined in RFC 4180 
	text/example: example in documentation, Defined in RFC 4735 
	text/html: HTML; Defined in RFC 2854 
	text/javascript (Obsolete): JavaScript; Defined and made obsolete in RFC 4329 in order to discourage its usage in favor of application/javascript. However, text/javascript is allowed in HTML 4 and 5 and, unlike application/javascript, has cross-browser support. The "type" attribute of the <script> tag in HTML5 is optional and there is no need to use it at all since all browsers have always assumed the correct default (even in HTML 4 where it was required by the specification).
	text/plain: Textual data; Defined in RFC 2046  and RFC 3676 
	text/rtf: RTF; Defined by Paul Lindner 
	text/vcard: vCard (contact information); Defined in RFC 6350 
	text/vnd.abc: ABC music notation; Registered[15]
	text/xml: Extensible Markup Language; Defined in RFC 3023 


	Type video[edit]
	For video.

	video/avi: Covers most Windows-compatible formats including .avi and .divx[16]
	video/example: example in documentation, Defined in RFC 4735 
	video/mpeg: MPEG-1 video with multiplexed audio; Defined in RFC 2045  and RFC 2046 
	video/mp4: MP4 video; Defined in RFC 4337 
	video/ogg: Ogg Theora or other video (with audio); Defined in RFC 5334 
	video/quicktime: QuickTime video; Registered[17]
	video/webm: WebM Matroska-based open media format
	video/x-matroska: Matroska open media format
	video/x-ms-wmv: Windows Media Video; Documented in Microsoft KB 288102 
	video/x-flv: Flash video (FLV files)
	*/

	
	
	
	
	
	static class Ap extends MIMEType
	{
		public Ap(String subtypeName, String suffix)
		{
			super(STANDARDS_TYPE_APPLICATION, subtypeName, suffix);
		}
		public Ap(String subtypeName)
		{
			this(subtypeName, null);
		}
	}
	static class Au extends MIMEType
	{
		public Au(String root, String subtypeName)
		{
			super(null, STANDARDS_TYPE_AUDIO, new String[]{root}, subtypeName, null, null);
		}
		public Au(String subtypeName)
		{
			this(null, subtypeName);
		}
	}
	static class I extends MIMEType
	{
		public I(String root, String subtypeName, String suffix)
		{
			super(null, STANDARDS_TYPE_IMAGE, new String[]{root}, subtypeName, suffix, null);
		}
		
		public I(String subtypeName)
		{
			this(null, subtypeName, null);
		}
	}
	static class T extends MIMEType
	{
		public T(String subtypeName)
		{
			super(STANDARDS_TYPE_TEXT, subtypeName);
		}
	}
}