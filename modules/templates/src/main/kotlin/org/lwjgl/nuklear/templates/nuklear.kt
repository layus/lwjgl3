/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.lwjgl.nuklear.templates

import org.lwjgl.generator.*
import org.lwjgl.nuklear.*

val nuklear = "Nuklear".nativeClass(packageName = NUKLEAR_PACKAGE, prefix = "NK", prefixMethod = "nk_", library = "lwjgl_nuklear") {
	initializeAllocator()
	nativeDirective("""#ifdef LWJGL_LINUX
	#pragma GCC diagnostic ignored "-Wunused-function"
	#pragma GCC diagnostic ignored "-Wunused-but-set-variable"
#endif""", beforeIncludes = true)

	nativeDirective(
		"""DISABLE_WARNINGS()
#ifdef LWJGL_WINDOWS
	__pragma(warning(disable : 4711 4738))
#endif
#define NK_PRIVATE
#define NK_INCLUDE_FIXED_TYPES
#define NK_INCLUDE_STANDARD_IO
#define NK_INCLUDE_VERTEX_BUFFER_OUTPUT
#define NK_INCLUDE_COMMAND_USERDATA
#ifdef LWJGL_WINDOWS
	#define NK_BUTTON_TRIGGER_ON_RELEASE
#endif
#define NK_ASSERT(expr)
#define NK_IMPLEMENTATION
#define NK_MEMSET memset
#define NK_MEMCOPY memcpy
#define NK_SQRT sqrt
#define NK_SIN sinf
#define NK_COS cosf
#include <math.h>
#include <string.h>
#include "nuklear.h"
typedef float(*nk_value_getter)(void* user, int index);
typedef void(*nk_item_getter)(void*, int, const char**);
ENABLE_WARNINGS()""")

	documentation =
		"""
		This is a minimal state immediate mode graphical user interface single header toolkit written in ANSI C and licensed under public domain. It was
		designed as a simple embeddable user interface for application and does not have any dependencies, a default renderbackend or OS window and input
		handling but instead provides a very modular library approach by using simple input state for input and draw commands describing primitive shapes as
		output. So instead of providing a layered library that tries to abstract over a number of platform and render backends it only focuses on the actual
		UI.

		<h3>VALUES</h3>
		${ul(
			"Immediate mode graphical user interface toolkit",
			"Single header library",
			"Written in C89 (ANSI C)",
			"Small codebase (~15kLOC)",
			"Focus on portability, efficiency and simplicity",
			"No dependencies (not even the standard library if not wanted)",
			"Fully skinnable and customizable",
			"Low memory footprint with total memory control if needed or wanted",
			"UTF-8 support",
			"No global or hidden state",
			"Customizable library modules (you can compile and use only what you need)",
			"Optional font baker and vertex buffer output"
		)}

        <h3>FEATURES</h3>
		${ul(
			"Absolutely no platform dependent code",
			"Memory management control ranging from/to",
			"Ease of use by allocating everything from the standard library",
			"Control every byte of memory inside the library",
			"Font handling control ranging from/to",
			"Use your own font implementation for everything",
			"Use this libraries internal font baking and handling API",
			"Drawing output control ranging from/to",
			"Simple shapes for more high level APIs which already having drawing capabilities",
			"Hardware accessible anti-aliased vertex buffer output",
			"Customizable colors and properties ranging from/to",
			"Simple changes to color by filling a simple color table",
			"Complete control with ability to use skinning to decorate widgets",
			"Bendable UI library with widget ranging from/to",
			"Basic widgets like buttons, checkboxes, slider, ...",
			"Advanced widget like abstract comboboxes, contextual menus,...",
			"Compile time configuration to only compile what you need",
			"Subset which can be used if you do not want to link or use the standard library",
			"Can be easily modified to only update on user input instead of frame updates"
		)}
		"""

	IntConstant(
		"Constants.",

		"UTF_INVALID"..0xFFFD,
		"UTF_SIZE".."$NK_UTF_SIZE",
		"INPUT_MAX".."16",
		"MAX_NUMBER_BUFFER".."64"
	)

	FloatConstant(
		"Constants.",

		"SCROLLBAR_HIDING_TIMEOUT"..4.0f
	)

	EnumConstant(
		"Boolean values.",

		"nk_false".enum,
		"nk_true".enum
	).noPrefix()

	val Headings = EnumConstant(
		"nk_heading",

		"UP".enum,
		"RIGHT".enum,
		"DOWN".enum,
		"LEFT".enum
	).javaDocLinks

	val ButtonBehaviors = EnumConstant(
		"nk_button_behavior",

		"BUTTON_DEFAULT".enum,
		"BUTTON_REPEATER".enum
	).javaDocLinks

	EnumConstant(
		"nk_modify",

		"FIXED".enum("", "nk_false"),
		"MODIFIABLE".enum("", "nk_true")
	)

	EnumConstant(
		"nk_orientation",

		"VERTICAL".enum,
		"HORIZONTAL".enum
	)

	val CollapseStates = EnumConstant(
		"nk_collapse_states",

		"MINIMIZED".enum("", "nk_false"),
		"MAXIMIZED".enum("", "nk_true")
	).javaDocLinks

	val ShowStates = EnumConstant(
		"nk_show_states",

		"HIDDEN".enum("", "nk_false"),
		"SHOWN".enum("", "nk_true")
	).javaDocLinks

	val ChartTypes = EnumConstant(
		"nk_chart_type",

		"CHART_LINES".enum,
		"CHART_COLUMN".enum,
		"CHART_MAX".enum
	).javaDocLinks

	EnumConstant(
		"nk_chart_event",

		"CHART_HOVERING".enum(0x01),
		"CHART_CLICKED".enum(0x02)
	)

	val ColorFormats = EnumConstant(
		"nk_color_format",

		"RGB".enum,
		"RGBA".enum
	).javaDocLinks

	val PopupTypes = EnumConstant(
		"nk_popup_type",

		"POPUP_STATIC".enum,
		"POPUP_DYNAMIC".enum
	).javaDocLinks

	val LayoutFormats = EnumConstant(
		"nk_layout_format",

		"DYNAMIC".enum,
		"STATIC".enum
	).javaDocLinks

	val TreeTypes = EnumConstant(
		"nk_tree_type",

		"TREE_NODE".enum,
		"TREE_TAB".enum
	).javaDocLinks

	val Antialiasing = EnumConstant(
		"nk_anti_aliasing",

		"ANTI_ALIASING_OFF".enum,
		"ANTI_ALIASING_ON".enum
	).javaDocLinks

	val SymbolTypes = EnumConstant(
		"nk_symbol_type",

		"SYMBOL_NONE".enum,
		"SYMBOL_X".enum,
		"SYMBOL_UNDERSCORE".enum,
		"SYMBOL_CIRCLE".enum,
		"SYMBOL_CIRCLE_FILLED".enum,
		"SYMBOL_RECT".enum,
		"SYMBOL_RECT_FILLED".enum,
		"SYMBOL_TRIANGLE_UP".enum,
		"SYMBOL_TRIANGLE_DOWN".enum,
		"SYMBOL_TRIANGLE_LEFT".enum,
		"SYMBOL_TRIANGLE_RIGHT".enum,
		"SYMBOL_PLUS".enum,
		"SYMBOL_MINUS".enum,
		"SYMBOL_MAX".enum
	).javaDocLinks

	val Keys = EnumConstant(
		"nk_keys",

		"KEY_NONE".enum,
		"KEY_SHIFT".enum,
		"KEY_CTRL".enum,
		"KEY_DEL".enum,
		"KEY_ENTER".enum,
		"KEY_TAB".enum,
		"KEY_BACKSPACE".enum,
		"KEY_COPY".enum,
		"KEY_CUT".enum,
		"KEY_PASTE".enum,
		"KEY_UP".enum,
		"KEY_DOWN".enum,
		"KEY_LEFT".enum,
		"KEY_RIGHT".enum,
		"KEY_TEXT_INSERT_MODE".enum,
		"KEY_TEXT_REPLACE_MODE".enum,
		"KEY_TEXT_RESET_MODE".enum,
		"KEY_TEXT_LINE_START".enum,
		"KEY_TEXT_LINE_END".enum,
		"KEY_TEXT_START".enum,
		"KEY_TEXT_END".enum,
		"KEY_TEXT_UNDO".enum,
		"KEY_TEXT_REDO".enum,
		"KEY_TEXT_WORD_LEFT".enum,
		"KEY_TEXT_WORD_RIGHT".enum,
		"KEY_SCROLL_START".enum,
		"KEY_SCROLL_END".enum,
		"KEY_SCROLL_DOWN".enum,
		"KEY_SCROLL_UP".enum,
		"KEY_MAX".enum // TODO: Update nk_keyboard.keys size if this changes
	).javaDocLinks

	val Buttons = EnumConstant(
		"nk_buttons",

		"BUTTON_LEFT".enum,
		"BUTTON_MIDDLE".enum,
		"BUTTON_RIGHT".enum,
		"BUTTON_MAX".enum
	).javaDocLinks

	val StyleColors = EnumConstant(
		"nk_style_colors",

		"COLOR_TEXT".enum,
		"COLOR_WINDOW".enum,
		"COLOR_HEADER".enum,
		"COLOR_BORDER".enum,
		"COLOR_BUTTON".enum,
		"COLOR_BUTTON_HOVER".enum,
		"COLOR_BUTTON_ACTIVE".enum,
		"COLOR_TOGGLE".enum,
		"COLOR_TOGGLE_HOVER".enum,
		"COLOR_TOGGLE_CURSOR".enum,
		"COLOR_SELECT".enum,
		"COLOR_SELECT_ACTIVE".enum,
		"COLOR_SLIDER".enum,
		"COLOR_SLIDER_CURSOR".enum,
		"COLOR_SLIDER_CURSOR_HOVER".enum,
		"COLOR_SLIDER_CURSOR_ACTIVE".enum,
		"COLOR_PROPERTY".enum,
		"COLOR_EDIT".enum,
		"COLOR_EDIT_CURSOR".enum,
		"COLOR_COMBO".enum,
		"COLOR_CHART".enum,
		"COLOR_CHART_COLOR".enum,
		"COLOR_CHART_COLOR_HIGHLIGHT".enum,
		"COLOR_SCROLLBAR".enum,
		"COLOR_SCROLLBAR_CURSOR".enum,
		"COLOR_SCROLLBAR_CURSOR_HOVER".enum,
		"COLOR_SCROLLBAR_CURSOR_ACTIVE".enum,
		"COLOR_TAB_HEADER".enum,
		"COLOR_COUNT".enum
	).javaDocLinks
	
	val StyleCursor = EnumConstant(
		"nk_style_cursor",

		"CURSOR_ARROW".enum,
		"CURSOR_TEXT".enum,
		"CURSOR_MOVE".enum,
		"CURSOR_RESIZE_VERTICAL".enum,
		"CURSOR_RESIZE_HORIZONTAL".enum,
		"CURSOR_RESIZE_TOP_LEFT_DOWN_RIGHT".enum,
		"CURSOR_RESIZE_TOP_RIGHT_DOWN_LEFT".enum,
		"CURSOR_COUNT".enum // TODO: Update nk_style.cursors size if this changes
	).javaDocLinks
	
	EnumConstant(
		"nk_widget_layout_states",

		"WIDGET_INVALID".enum("The widget cannot be seen and is completely out of view"),
		"WIDGET_VALID".enum("The widget is completely inside the window and can be updated and drawn"),
		"WIDGET_ROM".enum("The widget is partially visible and cannot be updated")
	)

	EnumConstant(
		"nk_widget_states",

		"WIDGET_STATE_MODIFIED".enum("", 1.NK_FLAG),
		"WIDGET_STATE_INACTIVE".enum("widget is neither active nor hovered", 2.NK_FLAG),
		"WIDGET_STATE_ENTERED".enum("widget has been hovered on the current frame", 3.NK_FLAG),
		"WIDGET_STATE_HOVER".enum("widget is being hovered", 4.NK_FLAG),
		"WIDGET_STATE_ACTIVED".enum("widget is currently activated", 5.NK_FLAG),
		"WIDGET_STATE_LEFT".enum("widget is from this frame on not hovered anymore", 6.NK_FLAG),
		"WIDGET_STATE_HOVERED".enum("widget is being hovered", "NK_WIDGET_STATE_HOVER|NK_WIDGET_STATE_MODIFIED"),
		"WIDGET_STATE_ACTIVE".enum("widget is currently activated", "NK_WIDGET_STATE_ACTIVED|NK_WIDGET_STATE_MODIFIED")
	)

	EnumConstant(
		"nk_text_align",

		"TEXT_ALIGN_LEFT".enum(0x01),
		"TEXT_ALIGN_CENTERED".enum(0x02),
		"TEXT_ALIGN_RIGHT".enum(0x04),
		"TEXT_ALIGN_TOP".enum(0x08),
		"TEXT_ALIGN_MIDDLE".enum(0x10),
		"TEXT_ALIGN_BOTTOM".enum(0x20)
	)

	val TextAlignments = EnumConstant(
		"nk_text_alignment",

		"TEXT_LEFT".enum("", "NK_TEXT_ALIGN_MIDDLE|NK_TEXT_ALIGN_LEFT"),
		"TEXT_CENTERED".enum("", "NK_TEXT_ALIGN_MIDDLE|NK_TEXT_ALIGN_CENTERED"),
		"TEXT_RIGHT".enum("", "NK_TEXT_ALIGN_MIDDLE|NK_TEXT_ALIGN_RIGHT")
	).javaDocLinks

	val EditFlags = EnumConstant(
		"nk_edit_flags",

		"EDIT_DEFAULT".enum(0),
		"EDIT_READ_ONLY".enum("", 0.NK_FLAG),
		"EDIT_AUTO_SELECT".enum("", 1.NK_FLAG),
		"EDIT_SIG_ENTER".enum("", 2.NK_FLAG),
		"EDIT_ALLOW_TAB".enum("", 3.NK_FLAG),
		"EDIT_NO_CURSOR".enum("", 4.NK_FLAG),
		"EDIT_SELECTABLE".enum("", 5.NK_FLAG),
		"EDIT_CLIPBOARD".enum("", 6.NK_FLAG),
		"EDIT_CTRL_ENTER_NEWLINE".enum("", 7.NK_FLAG),
		"EDIT_NO_HORIZONTAL_SCROLL".enum("", 8.NK_FLAG),
		"EDIT_ALWAYS_INSERT_MODE".enum("", 9.NK_FLAG),
		"EDIT_MULTILINE".enum("", 11.NK_FLAG),
		"EDIT_GOTO_END_ON_ACTIVATE".enum("", 12.NK_FLAG)
	).javaDocLinks

	EnumConstant(
		"nk_edit_types",

		"EDIT_SIMPLE".enum("", "NK_EDIT_ALWAYS_INSERT_MODE"),
		"EDIT_FIELD".enum("", "NK_EDIT_SIMPLE|NK_EDIT_SELECTABLE|NK_EDIT_CLIPBOARD"),
		"EDIT_BOX".enum("", "NK_EDIT_ALWAYS_INSERT_MODE|NK_EDIT_SELECTABLE|NK_EDIT_MULTILINE|NK_EDIT_ALLOW_TAB|NK_EDIT_CLIPBOARD"),
		"EDIT_EDITOR".enum("", "NK_EDIT_SELECTABLE|NK_EDIT_MULTILINE|NK_EDIT_ALLOW_TAB|NK_EDIT_CLIPBOARD")
	)

	EnumConstant(
		"nk_edit_events",

		"EDIT_ACTIVE".enum("edit widget is currently being modified", 0.NK_FLAG),
		"EDIT_INACTIVE".enum("edit widget is not active and is not being modified", 1.NK_FLAG),
		"EDIT_ACTIVATED".enum("edit widget went from state inactive to state active", 2.NK_FLAG),
		"EDIT_DEACTIVATED".enum("edit widget went from state active to state inactive", 3.NK_FLAG),
		"EDIT_COMMITED".enum("edit widget has received an enter and lost focus", 4.NK_FLAG)
	)

	val PanelFlags = EnumConstant(
		"nk_panel_flags",

		"WINDOW_BORDER".enum("Draws a border around the window to visually separate the window * from the background", 0.NK_FLAG),
		"WINDOW_MOVABLE".enum("The movable flag indicates that a window can be moved by user input or * by dragging the window header", 1.NK_FLAG),
		"WINDOW_SCALABLE".enum("The scalable flag indicates that a window can be scaled by user input * by dragging a scaler icon at the button of the window", 2.NK_FLAG),
		"WINDOW_CLOSABLE".enum("adds a closable icon into the header", 3.NK_FLAG),
		"WINDOW_MINIMIZABLE".enum("adds a minimize icon into the header", 4.NK_FLAG),
		"WINDOW_NO_SCROLLBAR".enum("Removes the scrollbar from the window", 5.NK_FLAG),
		"WINDOW_TITLE".enum("Forces a header at the top at the window showing the title", 6.NK_FLAG),
		"WINDOW_SCROLL_AUTO_HIDE".enum("Automatically hides the window scrollbar if no user interaction", 7.NK_FLAG),
        "WINDOW_BACKGROUND".enum("Keep window always in the background", 8.NK_FLAG)
	).javaDocLinks

	EnumConstant(
		"nk_allocation_type",

		"BUFFER_FIXED".enum,
		"BUFFER_DYNAMIC".enum
	)

	val BufferAllocationTypes = EnumConstant(
		"nk_buffer_allocation_type",

		"BUFFER_FRONT".enum,
		"BUFFER_BACK".enum,
		"BUFFER_MAX".enum
	).javaDocLinks

	EnumConstant(
		"nk_text_edit_type",

		"TEXT_EDIT_SINGLE_LINE".enum,
		"TEXT_EDIT_MULTI_LINE".enum
	)

	EnumConstant(
		"nk_text_edit_mode",

		"TEXT_EDIT_MODE_VIEW".enum,
		"TEXT_EDIT_MODE_INSERT".enum,
		"TEXT_EDIT_MODE_REPLACE".enum
	)

	EnumConstant(
		"nk_font_coord_type",

		"COORD_UV".enum("texture coordinates inside font glyphs are clamped between 0-1"),
		"COORD_PIXEL".enum("texture coordinates inside font glyphs are in absolute pixel")
	)

	EnumConstant(
		"nk_font_atlas_format",

		"FONT_ATLAS_ALPHA8".enum,
		"FONT_ATLAS_RGBA32".enum
	)

	EnumConstant(
		"nk_command_type",

		"COMMAND_NOP".enum,
		"COMMAND_SCISSOR".enum,
		"COMMAND_LINE".enum,
		"COMMAND_CURVE".enum,
		"COMMAND_RECT".enum,
		"COMMAND_RECT_FILLED".enum,
		"COMMAND_RECT_MULTI_COLOR".enum,
		"COMMAND_CIRCLE".enum,
		"COMMAND_CIRCLE_FILLED".enum,
		"COMMAND_ARC".enum,
		"COMMAND_ARC_FILLED".enum,
		"COMMAND_TRIANGLE".enum,
		"COMMAND_TRIANGLE_FILLED".enum,
		"COMMAND_POLYGON".enum,
		"COMMAND_POLYGON_FILLED".enum,
		"COMMAND_POLYLINE".enum,
		"COMMAND_TEXT".enum,
		"COMMAND_IMAGE".enum
	)

	EnumConstant(
		"nk_command_clipping",

		"CLIPPING_OFF".enum("", "nk_false"),
		"CLIPPING_ON".enum("", "nk_true")
	)

	val DrawListStrokes = EnumConstant(
		"nk_draw_list_stroke",

		"STROKE_OPEN".enum("build up path has no connection back to the beginning", "nk_false"),
		"STROKE_CLOSED".enum("build up path has a connection back to the beginning", "nk_true")
	).javaDocLinks

	EnumConstant(
		"nk_draw_vertex_layout_attribute",

		"VERTEX_POSITION".enum,
		"VERTEX_COLOR".enum,
		"VERTEX_TEXCOORD".enum,
		"VERTEX_ATTRIBUTE_COUNT".enum
	)

	EnumConstant(
		"nk_draw_vertex_layout_format",

		"FORMAT_SCHAR".enum,
		"FORMAT_SSHORT".enum,
		"FORMAT_SINT".enum,
		"FORMAT_UCHAR".enum,
		"FORMAT_USHORT".enum,
		"FORMAT_UINT".enum,
		"FORMAT_FLOAT".enum,
		"FORMAT_DOUBLE".enum,

		"FORMAT_R8G8B8".enum,
		"FORMAT_R16G15B16".enum,
		"FORMAT_R32G32B32".enum,

		"FORMAT_R8G8B8A8".enum,
		"FORMAT_R16G15B16A16".enum,
		"FORMAT_R32G32B32A32".enum,
		"FORMAT_R32G32B32A32_FLOAT".enum,
		"FORMAT_R32G32B32A32_DOUBLE".enum,

		"FORMAT_RGB32".enum,
		"FORMAT_RGBA32".enum,
		"FORMAT_COUNT".enum
	)

	EnumConstant(
		"nk_style_item_type",

		"STYLE_ITEM_COLOR".enum,
		"STYLE_ITEM_IMAGE".enum
	)

	EnumConstant(
		"nk_style_header_align",

		"HEADER_LEFT".enum,
		"HEADER_RIGHT".enum
	)

	val WindowFlags = EnumConstant(
		"nk_window_flags",

		"WINDOW_PRIVATE".enum("dummy flag which mark the beginning of the private window flag part", 9.NK_FLAG),
		"WINDOW_ROM".enum("sets the window into a read only mode and does not allow input changes", 10.NK_FLAG),
		"WINDOW_HIDDEN".enum("Hides the window and stops any window interaction and drawing can be set by user input or by closing the window", 11.NK_FLAG),
		"WINDOW_CLOSED".enum("Directly closes and frees the window at the end of the frame", 12.NK_FLAG),
		"WINDOW_MINIMIZED".enum("marks the window as minimized", 13.NK_FLAG),
		"WINDOW_SUB".enum("Marks the window as subwindow of another window", 14.NK_FLAG),
		"WINDOW_GROUP".enum("Marks the window as window widget group", 15.NK_FLAG),
		"WINDOW_POPUP".enum("Marks the window as a popup window", 16.NK_FLAG),
		"WINDOW_NONBLOCK".enum("Marks the window as a nonblock popup window", 17.NK_FLAG),
		"WINDOW_CONTEXTUAL".enum("Marks the window as a combo box or menu", 18.NK_FLAG),
		"WINDOW_COMBO".enum("Marks the window as a combo box", 19.NK_FLAG),
		"WINDOW_MENU".enum("Marks the window as a menu", 20.NK_FLAG),
		"WINDOW_TOOLTIP".enum("Marks the window as a menu", 21.NK_FLAG),
		"WINDOW_REMOVE_ROM".enum("Removes the read only mode at the end of the window", 22.NK_FLAG)
	).javaDocLinks

	val ctx = nk_context_p.IN("ctx", "the nuklear context");
	val cctx = const..nk_context_p.IN("ctx", "the nuklear context");

	{
		intb(
			"init_fixed",
			"",

			ctx,
			void_p.IN("memory", ""),
			AutoSize("memory")..nk_size.IN("size", ""),
			nullable..const..nk_user_font_p.IN("font", "")
		)

		intb(
			"init_custom",
			"",

			ctx,
			nk_buffer_p.IN("cmds", ""),
			nk_buffer_p.IN("pool", ""),
			nullable..const..nk_user_font_p.IN("font", "")
		)

		intb(
			"init",
			"",

			ctx,
			nk_allocator_p.IN("allocator", ""),
			nullable..const..nk_user_font_p.IN("font", "")
		)

		void("clear", "", ctx)
		void("free", "", ctx)

		void(
			"set_user_data",
			"",

			ctx,
			nullable..nk_handle.IN("handle", "")
		)

		intb(
			"begin",
			"",

			ctx,
			nk_panel_p.OUT("panel", ""),
			const..charUTF8_p.IN("title", ""),
			nk_rect.IN("bounds", ""),
			nk_flags.IN("flags", "", WindowFlags, LinkMode.BITFIELD)
		)

		intb(
			"begin_titled",
			"",

			ctx,
			nk_panel_p.OUT("panel", ""),
			const..charUTF8_p.IN("name", ""),
			const..charUTF8_p.IN("title", ""),
			nk_rect.IN("bounds", ""),
			nk_flags.IN("flags", "", WindowFlags, LinkMode.BITFIELD)
		)

		void("end", "", ctx)

		nk_window_p(
			"window_find",
			"",

			ctx,
			const..charUTF8_p.IN("name", "")
		)

		nk_rect("window_get_bounds", "", cctx)
		nk_vec2("window_get_position", "", cctx)
		nk_vec2("window_get_size", "", cctx)

		float("window_get_width", "", cctx)
		float("window_get_height", "", cctx)

		nk_panel_p("window_get_panel", "", ctx)

		nk_rect("window_get_content_region", "", ctx)
		nk_vec2("window_get_content_region_min", "", ctx)
		nk_vec2("window_get_content_region_max", "", ctx)
		nk_vec2("window_get_content_region_size", "", ctx)

		nk_command_buffer_p("window_get_canvas", "", ctx)

		intb("window_has_focus", "", cctx)

		intb(
			"window_is_collapsed",
			"",

			ctx,
			const..charUTF8_p.IN("name", "")
		)

		intb(
			"window_is_closed",
			"",

			ctx,
			const..charUTF8_p.IN("name", "")
		)

		intb(
			"window_is_hidden",
			"",

			ctx,
			const..charUTF8_p.IN("name", "")
		)

		intb(
			"window_is_active",
			"",

			ctx,
			const..charUTF8_p.IN("name", "")
		)

		intb("window_is_hovered", "", ctx)

		intb("window_is_any_hovered", "", ctx)

		intb("item_is_any_active", "", ctx)

		void(
			"window_set_bounds",
			"",

			ctx,
			nk_rect.IN("bounds", "")
		)

		void(
			"window_set_position",
			"",

			ctx,
			nk_vec2.IN("position", "")
		)

		void(
			"window_set_size",
			"",

			ctx,
			nk_vec2.IN("size", "")
		)

		void(
			"window_set_focus",
			"",

			ctx,
			const..charUTF8_p.IN("name", "")
		)

		void(
			"window_close",
			"",

			ctx,
			const..charUTF8_p.IN("name", "")
		)

		void(
			"window_collapse",
			"",

			ctx,
			const..charUTF8_p.IN("name", ""),
			nk_collapse_states.IN("c", "", CollapseStates)
		)

		void(
			"window_show",
			"",

			ctx,
			const..charUTF8_p.IN("name", ""),
			nk_show_states.IN("s", "", ShowStates)
		)

		void(
			"layout_row_dynamic",
			"",

			ctx,
			float.IN("height", ""),
			nk_int.IN("cols", "")
		)

		void(
			"layout_row_static",
			"",

			ctx,
			float.IN("height", ""),
			nk_int.IN("item_width", ""),
			nk_int.IN("cols", "")
		)

		void(
			"layout_row_begin",
			"",

			ctx,
			nk_layout_format.IN("fmt", "", LayoutFormats),
			float.IN("row_height", ""),
			nk_int.IN("cols", "")
		)

		void(
			"layout_row_push",
			"",

			ctx,
			float.IN("value", "")
		)

		void("layout_row_end", "", ctx)

		void(
			"layout_row",
			"",

			ctx,
			nk_layout_format.IN("fmt", "", LayoutFormats),
			float.IN("height", ""),
			nk_int.IN("cols", ""),
			const..float_p.IN("ratio", "")
		)

		void(
			"layout_space_begin",
			"",

			ctx,
			nk_layout_format.IN("fmt", "", LayoutFormats),
			float.IN("height", ""),
			nk_int.IN("widget_count", "")
		)

		void(
			"layout_space_push",
			"",

			ctx,
			nk_rect.IN("rect", "")
		)

		void("layout_space_end", "", ctx)

		nk_rect("layout_space_bounds", "", ctx)

		nk_vec2(
			"layout_space_to_screen",
			"",

			ctx,
			ReturnParam..nk_vec2.IN("ret", "")
		)

		nk_vec2(
			"layout_space_to_local",
			"",

			ctx,
			ReturnParam..nk_vec2.IN("ret", "")
		)

		nk_rect(
			"layout_space_rect_to_screen",
			"",

			ctx,
			ReturnParam..nk_rect.IN("ret", "")
		)

		nk_rect(
			"layout_space_rect_to_local",
			"",

			ctx,
			ReturnParam..nk_rect.IN("ret", "")
		)

		float(
			"layout_ratio_from_pixel",
			"",

			ctx,
			float.IN("pixel_width", "")
		)

		intb(
			"group_begin",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("title", ""),
			nk_flags.IN("flags", "")
		)
		void("group_end", "", ctx)

		intb(
			"tree_push_hashed",
			"",

			ctx,
			nk_tree_type.IN("type", "", TreeTypes),
			const..charUTF8_p.IN("title", ""),
			nk_collapse_states.IN("initial_state", "", CollapseStates),
			const..char_p.IN("hash", ""),
			AutoSize("hash")..nk_int.IN("len", ""),
			nk_int.IN("seed", "")
		)

		intb(
			"tree_image_push_hashed",
			"",

			ctx,
			nk_tree_type.IN("type", "", TreeTypes),
			nk_image.IN("img", ""),
			const..charUTF8_p.IN("title", ""),
			nk_collapse_states.IN("initial_state", "", CollapseStates),
			const..charUTF8_p.IN("hash", ""),
			AutoSize("hash")..nk_int.IN("len", ""),
			nk_int.IN("seed", "")
		)

		void("tree_pop", "", ctx)

		void(
			"text",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..nk_int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		void(
			"text_colored",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..nk_int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments),
			nk_color.IN("color", "")
		)

		void(
			"text_wrap",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..nk_int.IN("len", "")
		)

		void(
			"text_wrap_colored",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..nk_int.IN("len", ""),
			nk_color.IN("color", "")
		)

		void(
			"label",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			nk_flags.IN("align", "", TextAlignments)
		)

		void(
			"label_colored",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			nk_flags.IN("align", "", TextAlignments),
			nk_color.IN("color", "")
		)

		void(
			"label_wrap",
			"",

			ctx,
			const..charUTF8_p.IN("str", "")
		)

		void(
			"label_colored_wrap",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			nk_color.IN("color", "")
		)

		void(
			"image",
			"",

			ctx,
			nk_image.IN("img", "")
		)

		intb(
			"button_text",
			"",

			ctx,
			const..charUTF8_p.IN("title", ""),
			AutoSize("title")..nk_int.IN("len", "")
		)

		intb(
			"button_label",
			"",

			ctx,
			const..charUTF8_p.IN("title", "")
		)

		intb(
			"button_color",
			"",

			ctx,
			nk_color.IN("color", "")
		)

		intb(
			"button_symbol",
			"",

			ctx,
			nk_symbol_type.IN("symbol", "", SymbolTypes)
		)

		intb(
			"button_image",
			"",

			ctx,
			nk_image.IN("img", "")
		)

		intb(
			"button_symbol_label",
			"",

			ctx,
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("text_alignment", "", TextAlignments)
		)

		intb(
			"button_symbol_text",
			"",

			ctx,
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..nk_int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"button_image_label",
			"",

			ctx,
			nk_image.IN("img", ""),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("text_alignment", "", TextAlignments)
		)

		intb(
			"button_image_text",
			"",

			ctx,
			nk_image.IN("img", ""),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..nk_int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		void(
			"button_set_behavior",
			"",

			ctx,
			nk_button_behavior.IN("behavior", "", ButtonBehaviors)
		)

		int(
			"button_push_behavior",
			"",

			ctx,
			nk_button_behavior.IN("behavior", "", ButtonBehaviors)
		)

		int(
			"button_pop_behavior",
			"",

			ctx
		)

		intb(
			"check_label",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			intb.IN("active", "")
		)

		intb(
			"check_text",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			intb.IN("active", "")
		)

		unsigned_int(
			"check_flags_label",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			unsigned_int.IN("flags", ""),
			unsigned_int.IN("value", "")
		)

		unsigned_int(
			"check_flags_text",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			unsigned_int.IN("flags", ""),
			unsigned_int.IN("value", "")
		)

		intb(
			"checkbox_label",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			Check(1)..int_p.INOUT("active", "")
		)

		intb(
			"checkbox_text",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			Check(1)..int_p.INOUT("active", "")
		)

		intb(
			"checkbox_flags_label",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			Check(1)..unsigned_int_p.INOUT("flags", ""),
			unsigned_int.IN("value", "")
		)

		intb(
			"checkbox_flags_text",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			Check(1)..unsigned_int_p.INOUT("flags", ""),
			unsigned_int.IN("value", "")
		)

		intb(
			"radio_label",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			Check(1)..int_p.INOUT("active", "")
		)

		intb(
			"radio_text",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			Check(1)..int_p.INOUT("active", "")
		)

		intb(
			"option_label",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			intb.IN("active", "")
		)

		intb(
			"option_text",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			intb.IN("active", "")
		)

		intb(
			"selectable_label",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			nk_flags.IN("align", "", TextAlignments),
			Check(1)..int_p.INOUT("value", "")
		)

		intb(
			"selectable_text",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			nk_flags.IN("align", "", TextAlignments),
			Check(1)..int_p.INOUT("value", "")
		)

		intb(
			"selectable_image_label",
			"",

			ctx,
			nullable..nk_image.IN("img", ""),
			const..charUTF8_p.IN("str", ""),
			nk_flags.IN("align", "", TextAlignments),
			Check(1)..int_p.INOUT("value", "")
		)

		intb(
			"selectable_image_text",
			"",

			ctx,
			nullable..nk_image.IN("img", ""),
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			nk_flags.IN("align", "", TextAlignments),
			Check(1)..int_p.INOUT("value", "")
		)

		intb(
			"select_label",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			nk_flags.IN("align", "", TextAlignments),
			intb.IN("value", "")
		)

		intb(
			"select_text",
			"",

			ctx,
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			nk_flags.IN("align", "", TextAlignments),
			intb.IN("value", "")
		)

		intb(
			"select_image_label",
			"",

			ctx,
			nullable..nk_image.IN("img", ""),
			const..charUTF8_p.IN("str", ""),
			nk_flags.IN("align", "", TextAlignments),
			intb.IN("value", "")
		)

		intb(
			"select_image_text",
			"",

			ctx,
			nullable..nk_image.IN("img", ""),
			const..charUTF8_p.IN("str", ""),
			AutoSize("str")..int.IN("len", ""),
			nk_flags.IN("align", "", TextAlignments),
			intb.IN("value", "")
		)

		float(
			"slide_float",
			"",

			ctx,
			float.IN("min", ""),
			float.IN("val", ""),
			float.IN("max", ""),
			float.IN("step", "")
		)

		int(
			"slide_int",
			"",

			ctx,
			int.IN("min", ""),
			int.IN("val", ""),
			int.IN("max", ""),
			int.IN("step", "")
		)

		int(
			"slider_float",
			"",

			ctx,
			float.IN("min", ""),
			float_p.OUT("val", ""),
			float.IN("max", ""),
			float.IN("step", "")
		)

		int(
			"slider_int",
			"",

			ctx,
			int.IN("min", ""),
			int_p.OUT("val", ""),
			int.IN("max", ""),
			int.IN("step", "")
		)

		intb(
			"progress",
			"",

			ctx,
			nk_size.p.INOUT("cur", ""),
			nk_size.IN("max", ""),
			intb.IN("modifyable", "")
		)

		nk_size(
			"prog",
			"",

			ctx,
			nk_size.IN("cur", ""),
			nk_size.IN("max", ""),
			intb.IN("modifyable", "")
		)

		nk_color(
			"color_picker",
			"",

			ctx,
			ReturnParam..nk_color.IN("color", ""),
			nk_color_format.IN("fmt", "", ColorFormats)
		)

		intb(
			"color_pick",
			"",

			ctx,
			nk_color.p.INOUT("color", ""),
			nk_color_format.IN("fmt", "", ColorFormats)
		)

		void(
			"property_int",
			"",

			ctx,
			const..charUTF8_p.IN("name", ""),
			int.IN("min", ""),
			Check(1)..int_p.INOUT("val", ""),
			int.IN("max", ""),
			int.IN("step", ""),
			float.IN("inc_per_pixel", "")
		)

		void(
			"property_float",
			"",

			ctx,
			const..charUTF8_p.IN("name", ""),
			float.IN("min", ""),
			Check(1)..float_p.INOUT("val", ""),
			float.IN("max", ""),
			float.IN("step", ""),
			float.IN("inc_per_pixel", "")
		)

		void(
			"property_double",
			"",

			ctx,
			const..charUTF8_p.IN("name", ""),
			double.IN("min", ""),
			Check(1)..double_p.INOUT("val", ""),
			double.IN("max", ""),
			double.IN("step", ""),
			float.IN("inc_per_pixel", "")
		)

		int(
			"propertyi",
			"",

			ctx,
			const..charUTF8_p.IN("name", ""),
			int.IN("min", ""),
			int.IN("val", ""),
			int.IN("max", ""),
			int.IN("step", ""),
			float.IN("inc_per_pixel", "")
		)

		float(
			"propertyf",
			"",

			ctx,
			const..charUTF8_p.IN("name", ""),
			float.IN("min", ""),
			float.IN("val", ""),
			float.IN("max", ""),
			float.IN("step", ""),
			float.IN("inc_per_pixel", "")
		)

		double(
			"propertyd",
			"",

			ctx,
			const..charUTF8_p.IN("name", ""),
			double.IN("min", ""),
			double.IN("val", ""),
			double.IN("max", ""),
			double.IN("step", ""),
			float.IN("inc_per_pixel", "")
		)

		nk_flags(
			"edit_string",
			"",

			ctx,
			nk_flags.IN("flags", "", EditFlags),
			charUTF8_p.IN("memory", ""),
			Check(1)..int_p.OUT("len", ""),
			int.IN("max", ""),
			nullable..nk_plugin_filter.IN("filter", "")
		)

		nk_flags(
			"edit_buffer",
			"",

			ctx,
			nk_flags.IN("flags", "", EditFlags),
			nk_text_edit_p.IN("edit", ""),
			nullable..nk_plugin_filter.IN("filter", "")
		)

		nk_flags(
			"edit_string_zero_terminated",
			"",

			ctx,
			nk_flags.IN("flags", "", EditFlags),
			charUTF8_p.IN("buffer", ""),
			int.IN("max", ""),
			nullable..nk_plugin_filter.IN("filter", "")
		)
	}();

	{
		intb(
			"chart_begin",
			"",

			ctx,
			nk_chart_type.IN("type", "", ChartTypes),
			int.IN("num", ""),
			float.IN("min", ""),
			float.IN("max", "")
		)

		intb(
			"chart_begin_colored",
			"",

			ctx,
			nk_chart_type.IN("type", "", ChartTypes),
			nk_color.IN("color", ""),
			nk_color.IN("active", ""),
			int.IN("num", ""),
			float.IN("min", ""),
			float.IN("max", "")
		)

		void(
			"chart_add_slot",
			"",

			ctx,
			nk_chart_type.IN("type", "", ChartTypes),
			int.IN("count", ""),
			float.IN("min_value", ""),
			float.IN("max_value", "")
		)

		void(
			"chart_add_slot_colored",
			"",

			ctx,
			nk_chart_type.IN("type", "", ChartTypes),
			nk_color.IN("color", ""),
			nk_color.IN("active", ""),
			int.IN("count", ""),
			float.IN("min_value", ""),
			float.IN("max_value", "")
		)

		nk_flags(
			"chart_push",
			"",

			ctx,
			float.IN("value", "")
		)

		nk_flags(
			"chart_push_slot",
			"",

			ctx,
			float.IN("value", ""),
			int.IN("slot", "")
		)

		void("chart_end", "", ctx)

		void(
			"plot",
			"",

			ctx,
			nk_chart_type.IN("type", "", ChartTypes),
			Check("offset + count")..const..float_p.IN("values", ""),
			int.IN("count", ""),
			int.IN("offset", "")
		)

		void(
			"plot_function",
			"",

			ctx,
			nk_chart_type.IN("type", "", ChartTypes),
			voidptr.IN("userdata", ""),
			nk_value_getter.IN("value_getter", ""),
			int.IN("count", ""),
			int.IN("offset", "")
		)

		intb(
			"popup_begin",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			nk_popup_type.IN("type", "", PopupTypes),
			const..charUTF8_p.IN("title", ""),
			nk_flags.IN("flags", "", PanelFlags),
			nk_rect.IN("rect", "")
		)

		void("popup_close", "", ctx)
		void("popup_end", "", ctx)

		intb(
			"combo",
			"",

			ctx,
			const..charUTF8_pp.IN("items", ""),
			AutoSize("items")..int.IN("count", ""),
			intb.IN("selected", ""),
			int.IN("item_height", ""),
			int.IN("max_height", "")
		)

		intb(
			"combo_separator",
			"",

			ctx,
			const..charUTF8_p.IN("items_separated_by_separator", ""),
			int.IN("separator", ""),
			intb.IN("selected", ""),
			int.IN("count", ""),
			int.IN("item_height", ""),
			int.IN("max_height", "")
		)

		intb(
			"combo_string",
			"",

			ctx,
			const..charUTF8_p.IN("items_separated_by_zeros", ""),
			intb.IN("selected", ""),
			int.IN("count", ""),
			int.IN("item_height", ""),
			int.IN("max_height", "")
		)

		intb(
			"combo_callback",
			"",

			ctx,
			nk_item_getter.IN("item_getter", ""),
			voidptr.IN("userdata", ""),
			intb.IN("selected", ""),
			int.IN("count", ""),
			int.IN("item_height", ""),
			int.IN("max_height", "")
		)

		void(
			"combobox",
			"",

			ctx,
			const..charUTF8_pp.IN("items", ""),
			AutoSize("items")..int.IN("count", ""),
			Check(1)..int_p.INOUT("selected", ""),
			int.IN("item_height", ""),
			int.IN("max_height", "")
		)

		void(
			"combobox_string",
			"",

			ctx,
			const..charUTF8_p.IN("items_separated_by_zeros", ""),
			Check(1)..int_p.INOUT("selected", ""),
			int.IN("count", ""),
			int.IN("item_height", ""),
			int.IN("max_height", "")
		)

		void(
			"combobox_separator",
			"",

			ctx,
			const..charUTF8_p.IN("items_separated_by_separator", ""),
			int.IN("separator", ""),
			Check(1)..int_p.INOUT("selected", ""),
			int.IN("count", ""),
			int.IN("item_height", ""),
			int.IN("max_height", "")
		)

		void(
			"combobox_callback",
			"",

			ctx,
			nk_item_getter.IN("item_getter", ""),
			voidptr.IN("userdata", ""),
			Check(1)..int_p.INOUT("selected", ""),
			int.IN("count", ""),
			int.IN("item_height", ""),
			int.IN("max_height", "")
		)

		intb(
			"combo_begin_text",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("selected", ""),
			AutoSize("selected")..int.IN("len", ""),
			int.IN("max_height", "")
		)

		intb(
			"combo_begin_label",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("selected", ""),
			int.IN("max_height", "")
		)

		intb(
			"combo_begin_color",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			nk_color.IN("color", ""),
			int.IN("max_height", "")
		)

		intb(
			"combo_begin_symbol",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			int.IN("max_height", "")
		)

		intb(
			"combo_begin_symbol_label",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("selected", ""),
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			int.IN("height", "")
		)

		intb(
			"combo_begin_symbol_text",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("selected", ""),
			AutoSize("selected")..int.IN("len", ""),
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			int.IN("height", "")
		)

		intb(
			"combo_begin_image",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			nk_image.IN("img", ""),
			int.IN("max_height", "")
		)

		intb(
			"combo_begin_image_label",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("selected", ""),
			nk_image.IN("img", ""),
			int.IN("height", "")
		)

		intb(
			"combo_begin_image_text",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("selected", ""),
			AutoSize("selected")..int.IN("len", ""),
			nk_image.IN("img", ""),
			int.IN("height", "")
		)

		intb(
			"combo_item_label",
			"",

			ctx,
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"combo_item_text",
			"",

			ctx,
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"combo_item_image_label",
			"",

			ctx,
			nk_image.IN("img", ""),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"combo_item_image_text",
			"",

			ctx,
			nk_image.IN("img", ""),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"combo_item_symbol_label",
			"",

			ctx,
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"combo_item_symbol_text",
			"",

			ctx,
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		void("combo_close", "", ctx)
		void("combo_end", "", ctx)

		intb(
			"contextual_begin",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			nk_flags.IN("flags", "", WindowFlags),
			nk_vec2.IN("size", ""),
			nk_rect.IN("trigger_bounds", "")
		)

		intb(
			"contextual_item_text",
			"",

			ctx,
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("align", "", TextAlignments)
		)

		intb(
			"contextual_item_label",
			"",

			ctx,
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("align", "", TextAlignments)
		)

		intb(
			"contextual_item_image_label",
			"",

			ctx,
			nk_image.IN("img", ""),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"contextual_item_image_text",
			"",

			ctx,
			nk_image.IN("img", ""),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"contextual_item_symbol_label",
			"",

			ctx,
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"contextual_item_symbol_text",
			"",

			ctx,
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		void("contextual_close", "", ctx)
		void("contextual_end", "", ctx)

		void(
			"tooltip",
			"",

			ctx,
			const..charUTF8_p.IN("text", "")
		)

		intb(
			"tooltip_begin",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			float.IN("width", "")
		)

		void("tooltip_end", "", ctx)

		void("menubar_begin", "", ctx)
		void("menubar_end", "", ctx)

		intb(
			"menu_begin_text",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("align", "", TextAlignments),
			float.IN("width", "")
		)

		intb(
			"menu_begin_label",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("align", "", TextAlignments),
			float.IN("width", "")
		)

		intb(
			"menu_begin_image",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("text", ""),
			nk_image.IN("img", ""),
			float.IN("width", "")
		)

		intb(
			"menu_begin_image_text",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("align", "", TextAlignments),
			nk_image.IN("img", ""),
			float.IN("width", "")
		)

		intb(
			"menu_begin_image_label",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("align", "", TextAlignments),
			nk_image.IN("img", ""),
			float.IN("width", "")
		)

		intb(
			"menu_begin_symbol",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("text", ""),
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			float.IN("width", "")
		)

		intb(
			"menu_begin_symbol_text",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("align", "", TextAlignments),
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			float.IN("width", "")
		)

		intb(
			"menu_begin_symbol_label",
			"",

			ctx,
			nk_panel_p.IN("layout", ""),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("align", "", TextAlignments),
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			float.IN("width", "")
		)

		intb(
			"menu_item_text",
			"",

			ctx,
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("align", "", TextAlignments)
		)

		intb(
			"menu_item_label",
			"",

			ctx,
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"menu_item_image_label",
			"",

			ctx,
			nk_image.IN("img", ""),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"menu_item_image_text",
			"",

			ctx,
			nk_image.IN("img", ""),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"menu_item_symbol_text",
			"",

			ctx,
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		intb(
			"menu_item_symbol_label",
			"",

			ctx,
			nk_symbol_type.IN("symbol", "", SymbolTypes),
			const..charUTF8_p.IN("text", ""),
			nk_flags.IN("alignment", "", TextAlignments)
		)

		void("menu_close", "", ctx)
		void("menu_end", "", ctx)
	}();

	{
		void(
			"convert",
			"",

			ctx,
			nk_buffer_p.IN("cmds", ""),
			nk_buffer_p.IN("vertices", ""),
			nk_buffer_p.IN("elements", ""),
			const..nk_convert_config.p.IN("config", "")
		)

		void("input_begin", "", ctx)

		void(
			"input_motion",
			"",

			ctx,
			int.IN("x", ""),
			int.IN("y", "")
		)

		void(
			"input_key",
			"",

			ctx,
			nk_keys.IN("key", "", Keys),
			intb.IN("down", "")
		)

		void(
			"input_button",
			"",

			ctx,
			nk_buttons.IN("id", "", Buttons),
			int.IN("x", ""),
			int.IN("y", ""),
			intb.IN("down", "")
		)

		void(
			"input_scroll",
			"",

			ctx,
			float.IN("y", "")
		)

		void(
			"input_glyph",
			"",

			ctx,
			Check(NK_UTF_SIZE)..nk_glyph.IN("glyph", "")
		)

		void(
			"input_unicode",
			"",

			ctx,
			nk_rune.IN("unicode", "")
		)

		void("input_end", "", ctx)

		void("style_default", "", ctx)

		void(
			"style_from_table",
			"",

			ctx,
			Check("NK_COLOR_COUNT")..const..nk_color.p.IN("table", "")
		)

		void(
			"style_load_cursor",
			"",

			ctx,
			nk_style_cursor.IN("style", "", StyleCursor),
			nk_cursor_p.IN("cursor", "")
		)

		void(
			"style_load_all_cursors",
			"",

			ctx,
			Check("NK_CURSOR_COUNT")..nk_cursor_p.IN("cursors", "")
		)

		(const..charUTF8_p)(
			"style_get_color_by_name",
			"",

			nk_style_colors.IN("c", "", StyleColors)
		)

		void(
			"style_set_font",
			"",

			ctx,
			const..nk_user_font_p.IN("font", "")
		)

		int(
			"style_set_cursor",
			"",

			ctx,
			nk_style_cursor.IN("style", "", StyleCursor)
		)

		void("style_show_cursor", "", ctx)
		void("style_hide_cursor", "", ctx)

		int(
			"style_push_font",
			"",

			ctx,
			nk_user_font_p.IN("font", "")
		)

		int(
			"style_push_float",
			"",

			ctx,
			float_p.IN("address", ""),
			float.IN("value", "")
		)

		int(
			"style_push_vec2",
			"",

			ctx,
			nk_vec2.p.IN("address", ""),
			nk_vec2.IN("value", "")
		)

		int(
			"style_push_style_item",
			"",

			ctx,
			nk_style_item.p.IN("address", ""),
			nk_style_item.IN("value", "")
		)

		int(
			"style_push_flags",
			"",

			ctx,
			nk_flags.p.IN("address", ""),
			nk_flags.IN("value", "")
		)

		int(
			"style_push_color",
			"",

			ctx,
			nk_color.p.IN("address", ""),
			nk_color.IN("value", "")
		)

		int("style_pop_font", "", ctx)
		int("style_pop_float", "", ctx)
		int("style_pop_vec2", "", ctx)
		int("style_pop_style_item", "", ctx)
		int("style_pop_flags", "", ctx)
		int("style_pop_color", "", ctx)

		nk_rect("widget_bounds", "", ctx)

		nk_vec2("widget_position", "", ctx)

		nk_vec2("widget_size", "", ctx)

		intb("widget_is_hovered", "", ctx)

		intb(
			"widget_is_mouse_clicked",
			"",

			ctx,
			nk_buttons.IN("btn", "")
		)

		intb(
			"widget_has_mouse_click_down",
			"",

			ctx,
			nk_buttons.IN("btn", "", Buttons),
			intb.IN("down", "")
		)

		void(
			"spacing",
			"",

			ctx,
			int.IN("cols", "")
		)

		nk_widget_layout_states(
			"widget",
			"",

			nk_rect.p.IN("bounds", ""),
			cctx
		)

		nk_widget_layout_states(
			"widget_fitting",
			"",

			nk_rect.p.IN("bounds", ""),
			ctx,
			nk_vec2.IN("item_padding", "")
		)

		nk_color(
			"rgb",
			"",

			int.IN("r", ""),
			int.IN("g", ""),
			int.IN("b", "")
		)

		nk_color(
			"rgb_iv",
			"",

			Check(3)..const..int_p.IN("rgb", "")
		)

		nk_color(
			"rgb_bv",
			"",

			Check(3)..const..nk_byte_p.IN("rgb", "")
		)

		nk_color(
			"rgb_f",
			"",

			float.IN("r", ""),
			float.IN("g", ""),
			float.IN("b", "")
		)

		nk_color(
			"rgb_fv",
			"",

			Check(3)..const..float_p.IN("rgb", "")
		)

		nk_color(
			"rgb_hex",
			"",

			Check(6)..const..charASCII_p.IN("rgb", "")
		)

		nk_color(
			"rgba",
			"",

			int.IN("r", ""),
			int.IN("g", ""),
			int.IN("b", ""),
			int.IN("a", "")
		)

		nk_color(
			"rgba_u32",
			"",

			nk_uint.IN("in", "")
		)

		nk_color(
			"rgba_iv",
			"",

			Check(4)..const..int_p.IN("rgba", "")
		)

		nk_color(
			"rgba_bv",
			"",

			Check(4)..const..nk_byte_p.IN("rgba", "")
		)

		nk_color(
			"rgba_f",
			"",

			float.IN("r", ""),
			float.IN("g", ""),
			float.IN("b", ""),
			float.IN("a", "")
		)

		nk_color(
			"rgba_fv",
			"",

			Check(4)..const..float_p.IN("rgba", "")
		)

		nk_color(
			"rgba_hex",
			"",

			Check(8)..const..charASCII_p.IN("rgba", "")
		)

		nk_color(
			"hsv",
			"",

			int.IN("h", ""),
			int.IN("s", ""),
			int.IN("v", "")
		)

		nk_color(
			"hsv_iv",
			"",

			Check(3)..const..int_p.IN("hsv", "")
		)

		nk_color(
			"hsv_bv",
			"",

			Check(3)..const..nk_byte_p.IN("hsv", "")
		)

		nk_color(
			"hsv_f",
			"",

			float.IN("h", ""),
			float.IN("s", ""),
			float.IN("v", "")
		)

		nk_color(
			"hsv_fv",
			"",

			Check(3)..const..float_p.IN("hsv", "")
		)

		nk_color(
			"hsva",
			"",

			int.IN("h", ""),
			int.IN("s", ""),
			int.IN("v", ""),
			int.IN("a", "")
		)

		nk_color(
			"hsva_iv",
			"",

			Check(4)..const..int_p.IN("hsva", "")
		)

		nk_color(
			"hsva_bv",
			"",

			Check(4)..const..nk_byte_p.IN("hsva", "")
		)

		nk_color(
			"hsva_f",
			"",

			float.IN("h", ""),
			float.IN("s", ""),
			float.IN("v", ""),
			float.IN("a", "")
		)

		nk_color(
			"hsva_fv",
			"",

			Check(4)..const..float_p.IN("hsva", "")
		)

		void(
			"color_f",
			"",

			Check(1)..float_p.OUT("r", ""),
			Check(1)..float_p.OUT("g", ""),
			Check(1)..float_p.OUT("b", ""),
			Check(1)..float_p.OUT("a", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_fv",
			"",

			Check(4)..float_p.OUT("rgba_out", ""),
			nk_color.IN("color", "")
		)

		nk_uint(
			"color_u32",
			"",

			nk_color.IN("color", "")
		)

		void(
			"color_hex_rgba",
			"",

			Check(8)..char_p.OUT("output", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hex_rgb",
			"",

			Check(6)..char_p.OUT("output", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsv_i",
			"",

			Check(1)..int_p.OUT("out_h", ""),
			Check(1)..int_p.OUT("out_s", ""),
			Check(1)..int_p.OUT("out_v", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsv_b",
			"",

			Check(1)..nk_byte_p.OUT("out_h", ""),
			Check(1)..nk_byte_p.OUT("out_s", ""),
			Check(1)..nk_byte_p.OUT("out_v", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsv_iv",
			"",

			Check(3)..int_p.OUT("hsv_out", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsv_bv",
			"",

			Check(3)..nk_byte_p.OUT("hsv_out", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsv_f",
			"",

			Check(1)..float_p.OUT("out_h", ""),
			Check(1)..float_p.OUT("out_s", ""),
			Check(1)..float_p.OUT("out_v", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsv_fv",
			"",

			Check(3)..float_p.OUT("hsv_out", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsva_i",
			"",

			Check(1)..int_p.OUT("h", ""),
			Check(1)..int_p.OUT("s", ""),
			Check(1)..int_p.OUT("v", ""),
			Check(1)..int_p.OUT("a", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsva_b",
			"",

			Check(1)..nk_byte_p.OUT("h", ""),
			Check(1)..nk_byte_p.OUT("s", ""),
			Check(1)..nk_byte_p.OUT("v", ""),
			Check(1)..nk_byte_p.OUT("a", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsva_iv",
			"",

			Check(4)..int_p.OUT("hsva_out", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsva_bv",
			"",

			Check(4)..nk_byte_p.OUT("hsva_out", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsva_f",
			"",

			Check(1)..float_p.OUT("out_h", ""),
			Check(1)..float_p.OUT("out_s", ""),
			Check(1)..float_p.OUT("out_v", ""),
			Check(1)..float_p.OUT("out_a", ""),
			nk_color.IN("color", "")
		)

		void(
			"color_hsva_fv",
			"",

			Check(4)..float_p.OUT("hsva_out", ""),
			nk_color.IN("color", "")
		)

		nk_handle(
			"handle_ptr",
			"",

			voidptr.IN("ptr", "")
		)

		nk_handle(
			"handle_id",
			"",

			int.IN("id", "")
		)

		nk_image(
			"image_handle",
			"",

			nk_handle.IN("handle", "")
		)

		nk_image(
			"image_ptr",
			"",

			voidptr.IN("ptr", "")
		)

		nk_image(
			"image_id",
			"",

			int.IN("id", "")
		)

		intb(
			"image_is_subimage",
			"",

			const..nk_image.p.IN("img", "")
		)

		nk_image(
			"subimage_ptr",
			"",

			voidptr.IN("ptr", ""),
			unsigned_short.IN("w", ""),
			unsigned_short.IN("h", ""),
			nk_rect.IN("sub_region", "")
		)

		nk_image(
			"subimage_id",
			"",

			int.IN("id", ""),
			unsigned_short.IN("w", ""),
			unsigned_short.IN("h", ""),
			nk_rect.IN("sub_region", "")
		)

		nk_image(
			"subimage_handle",
			"",

			nk_handle.IN("handle", ""),
			unsigned_short.IN("w", ""),
			unsigned_short.IN("h", ""),
			nk_rect.IN("sub_region", "")
		)

		nk_hash(
			"murmur_hash",
			"",

			const..void_p.IN("key", ""),
			AutoSize("key")..int.IN("len", ""),
			nk_hash.IN("seed", "")
		)

		void(
			"triangle_from_direction",
			"",

			nk_vec2.p.OUT("result", ""),
			nk_rect.IN("r", ""),
			float.IN("pad_x", ""),
			float.IN("pad_y", ""),
			nk_heading.IN("direction", "", Headings)
		)

		nk_vec2(
			"vec2",
			"",

			float.IN("x", ""),
			float.IN("y", "")
		)

		nk_vec2(
			"vec2i",
			"",

			int.IN("x", ""),
			int.IN("y", "")
		)

		nk_vec2(
			"vec2v",
			"",

			Check(2)..const..float_p.IN("xy", "")
		)

		nk_vec2(
			"vec2iv",
			"",

			Check(2)..const..int_p.IN("xy", "")
		)

		nk_rect(
			"get_null_rect",
			""
		)

		nk_rect(
			"rect",
			"",

			float.IN("x", ""),
			float.IN("y", ""),
			float.IN("w", ""),
			float.IN("h", "")
		)

		nk_rect(
			"recti",
			"",

			int.IN("x", ""),
			int.IN("y", ""),
			int.IN("w", ""),
			int.IN("h", "")
		)

		nk_rect(
			"recta",
			"",

			nk_vec2.IN("pos", ""),
			nk_vec2.IN("size", "")
		)

		nk_rect(
			"rectv",
			"",

			Check(4)..const..float_p.IN("xywh", "")
		)

		nk_rect(
			"rectiv",
			"",

			Check(4)..const..int_p.IN("xywh", "")
		)

		nk_vec2(
			"rect_pos",
			"",

			nk_rect.IN("r", "")
		)

		nk_vec2(
			"rect_size",
			"",

			nk_rect.IN("r", "")
		)
	}();

	{
		int(
			"strlen",
			"",

			const..charUTF8_p.IN("str", "")
		)

		int(
			"stricmp",
			"",

			const..charUTF8_p.IN("s1", ""),
			const..charUTF8_p.IN("s2", "")
		)

		int(
			"stricmpn",
			"",

			const..charUTF8_p.IN("s1", ""),
			const..charUTF8_p.IN("s2", ""),
			int.IN("n", "")
		)

		int(
			"strtoi",
			"",

			const..charUTF8_p.IN("str", ""),
			charUTF8_pp.OUT("endptr", "")
		)

		float(
			"strtof",
			"",

			const..charUTF8_p.IN("str", ""),
			charUTF8_pp.OUT("endptr", "")
		)

		double(
			"strtod",
			"",

			const..charUTF8_p.IN("str", ""),
			charUTF8_pp.OUT("endptr", "")
		)

		intb(
			"strfilter",
			"""
			${ul(
			    "c - matches any literal character c",
			    ". - matches any single character",
			    "^ - matches the beginning of the input string",
			    "$ - matches the end of the input string",
			    "* - matches zero or more occurrences of the previous character"
			)}
			""",

			const..charUTF8_p.IN("str", ""),
			const..charUTF8_p.IN("regexp", "")
		)

		intb(
			"strmatch_fuzzy_string",
			"""
			Returns true if each character in {@code pattern} is found sequentially within {@code str} if found then {@code out_score} is also set. Score value has no
			intrinsic meaning. Range varies with {@code pattern}. Can only compare scores with same search pattern.

			""",

			const..charUTF8_p.IN("str", ""),
			const..charUTF8_p.IN("pattern", ""),
			Check(1)..int_p.OUT("out_score", "")
		)

		int(
			"strmatch_fuzzy_text",
			"",

			const..charUTF8_p.IN("txt", ""),
			AutoSize("txt")..int.IN("txt_len", ""),
			const..charUTF8_p.IN("pattern", ""),
			Check(1)..int_p.OUT("out_score", "")
		)

		int(
			"utf_decode",
			"",

			const..char_p.IN("c", ""),
			Check(1)..nk_rune_p.OUT("u", ""),
			AutoSize("c")..int.IN("clen", "")
		)

		int(
			"utf_encode",
			"",

			nk_rune.IN("u", ""),
			char_p.IN("c", ""),
			AutoSize("c")..int.IN("clen", "")
		)

		int(
			"utf_len",
			"",

			const..char_p.IN("str", ""),
			AutoSize("str")..int.IN("byte_len", "")
		)

		(const..char_p)(
			"utf_at",
			"",

			const..char_p.IN("buffer", ""),
			AutoSize("buffer")..int.IN("length", ""),
			int.IN("index", ""),
			Check(1)..nk_rune_p.OUT("unicode", ""),
			AutoSizeResult..int_p.OUT("len", "")
		)

		void(
			"buffer_init",
			"",

			nk_buffer_p.IN("buffer", ""),
			const..nk_allocator_p.IN("allocator", ""),
			nk_size.IN("size", "")
		)

		void(
			"buffer_init_fixed",
			"",

			nk_buffer_p.IN("buffer", ""),
			void_p.IN("memory", ""),
			AutoSize("memory")..nk_size.IN("size", "")
		)

		void(
			"buffer_info",
			"",

			nk_memory_status.p.OUT("status", ""),
			nk_buffer_p.IN("buffer", "")
		)

		void(
			"buffer_push",
			"",

			nk_buffer_p.IN("buffer", ""),
			nk_buffer_allocation_type.IN("type", "", BufferAllocationTypes),
			const..void_p.IN("memory", ""),
			AutoSize("memory")..nk_size.IN("size", ""),
			nk_size.IN("align", "")
		)

		void(
			"buffer_mark",
			"",

			nk_buffer_p.IN("buffer", ""),
			nk_buffer_allocation_type.IN("type", "", BufferAllocationTypes)
		)

		void(
			"buffer_reset",
			"",

			nk_buffer_p.IN("buffer", ""),
			nk_buffer_allocation_type.IN("type", "", BufferAllocationTypes)
		)

		void(
			"buffer_clear",
			"",

			nk_buffer_p.IN("buffer", "")
		)

		void(
			"buffer_free",
			"",

			nk_buffer_p.IN("buffer", "")
		)

		voidptr(
			"buffer_memory",
			"",

			nk_buffer_p.IN("buffer", "")
		)

		(const..voidptr)(
			"buffer_memory_const",
			"",

			const..nk_buffer_p.IN("buffer", "")
		)

		nk_size(
			"buffer_total",
			"",

			nk_buffer_p.IN("buffer", "")
		)

		void(
			"str_init",
			"",

			nk_str_p.IN("str", ""),
			const..nk_allocator_p.IN("allocator", ""),
			nk_size.IN("size", "")
		)

		void(
			"str_init_fixed",
			"",

			nk_str_p.IN("str", ""),
			void_p.IN("memory", ""),
			AutoSize("memory")..nk_size.IN("size", "")
		)

		void(
			"str_clear",
			"",

			nk_str_p.IN("str", "")
		)

		void(
			"str_free",
			"",

			nk_str_p.IN("str", "")
		)

		int(
			"str_append_text_char",
			"",

			nk_str_p.IN("s", ""),
			const..char_p.IN("str", ""),
			AutoSize("str")..int.IN("len", "")
		)

		int(
			"str_append_str_char",
			"",

			nk_str_p.IN("s", ""),
			NullTerminated..const..char_p.IN("str", "")
		)

		int(
			"str_append_text_utf8",
			"",

			nk_str_p.IN("s", ""),
			const..char_p.IN("str", ""),
			AutoSize("str")..int.IN("len", "")
		)

		int(
			"str_append_str_utf8",
			"",

			nk_str_p.IN("s", ""),
			NullTerminated..const..char_p.IN("str", "")
		)

		int(
			"str_append_text_runes",
			"",

			nk_str_p.IN("s", ""),
			const..nk_rune_p.IN("runes", ""),
			AutoSize("runes")..int.IN("len", "")
		)

		int(
			"str_append_str_runes",
			"",

			nk_str_p.IN("s", ""),
			NullTerminated..const..nk_rune_p.IN("runes", "")
		)

		int(
			"str_insert_at_char",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			const..char_p.IN("str", ""),
			AutoSize("str")..int.IN("len", "")
		)

		int(
			"str_insert_at_rune",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			const..char_p.IN("str", ""),
			AutoSize("str")..int.IN("len", "")
		)

		int(
			"str_insert_text_char",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			const..char_p.IN("str", ""),
			AutoSize("str")..int.IN("len", "")
		)

		int(
			"str_insert_str_char",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			NullTerminated..const..char_p.IN("str", "")
		)

		int(
			"str_insert_text_utf8",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			const..char_p.IN("str", ""),
			AutoSize("str")..int.IN("len", "")
		)

		int(
			"str_insert_str_utf8",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			NullTerminated..const..char_p.IN("str", "")
		)

		int(
			"str_insert_text_runes",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			const..nk_rune_p.IN("runes", ""),
			AutoSize("runes")..int.IN("len", "")
		)

		int(
			"str_insert_str_runes",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			const..nk_rune_p.IN("runes", "")
		)

		void(
			"str_remove_chars",
			"",

			nk_str_p.IN("s", ""),
			int.IN("len", "")
		)

		void(
			"str_remove_runes",
			"",

			nk_str_p.IN("str", ""),
			int.IN("len", "")
		)

		void(
			"str_delete_chars",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			int.IN("len", "")
		)

		void(
			"str_delete_runes",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			int.IN("len", "")
		)

		charUTF8_p(
			"str_at_char",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", "")
		)

		char_p(
			"str_at_rune",
			"",

			nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			Check(1)..nk_rune_p.OUT("unicode", ""),
			AutoSizeResult..int_p.OUT("len", "")
		)

		nk_rune(
			"str_rune_at",
			"",

			const..nk_str_p.IN("s", ""),
			int.IN("pos", "")
		)

		(const..charUTF8_p)(
			"str_at_char_const",
			"",

			const..nk_str_p.IN("s", ""),
			int.IN("pos", "")
		)

		(const..char_p)(
			"str_at_const",
			"",

			const..nk_str_p.IN("s", ""),
			int.IN("pos", ""),
			Check(1)..nk_rune_p.OUT("unicode", ""),
			AutoSizeResult..int_p.OUT("len", "")
		)

		charUTF8_p(
			"str_get",
			"",

			nk_str_p.IN("s", "")
		)

		(const..charUTF8_p)(
			"str_get_const",
			"",

			const..nk_str_p.IN("s", "")
		)

		int(
			"str_len",
			"",

			nk_str_p.IN("s", "")
		)

		int(
			"str_len_char",
			"",

			nk_str_p.IN("s", "")
		)

		intb(
			"filter_default",
			"",

			const..nk_text_edit_p.IN("edit", ""),
			nk_rune.IN("unicode", "")
		)

		intb(
			"filter_ascii",
			"",

			const..nk_text_edit_p.IN("edit", ""),
			nk_rune.IN("unicode", "")
		)

		intb(
			"filter_float",
			"",

			const..nk_text_edit_p.IN("edit", ""),
			nk_rune.IN("unicode", "")
		)

		intb(
			"filter_decimal",
			"",

			const..nk_text_edit_p.IN("edit", ""),
			nk_rune.IN("unicode", "")
		)

		intb(
			"filter_hex",
			"",

			const..nk_text_edit_p.IN("edit", ""),
			nk_rune.IN("unicode", "")
		)

		intb(
			"filter_oct",
			"",

			const..nk_text_edit_p.IN("edit", ""),
			nk_rune.IN("unicode", "")
		)

		intb(
			"filter_binary",
			"",

			const..nk_text_edit_p.IN("edit", ""),
			nk_rune.IN("unicode", "")
		)

		void(
			"textedit_init",
			"",

			nk_text_edit_p.IN("box", ""),
			nk_allocator_p.IN("allocator", ""),
			nk_size.IN("size", "")
		)

		void(
			"textedit_init_fixed",
			"",

			nk_text_edit_p.IN("box", ""),
			void_p.IN("memory", ""),
			AutoSize("memory")..nk_size.IN("size", "")
		)

		void(
			"textedit_free",
			"",

			nk_text_edit_p.IN("box", "")
		)

		void(
			"textedit_text",
			"",

			nk_text_edit_p.IN("box", ""),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("total_len", "")
		)

		void(
			"textedit_delete",
			"",

			nk_text_edit_p.IN("box", ""),
			int.IN("where", ""),
			int.IN("len", "")
		)

		void(
			"textedit_delete_selection",
			"",

			nk_text_edit_p.IN("box", "")
		)

		void(
			"textedit_select_all",
			"",

			nk_text_edit_p.IN("box", "")
		)

		intb(
			"textedit_cut",
			"",

			nk_text_edit_p.IN("box", "")
		)

		intb(
			"textedit_paste",
			"",

			nk_text_edit_p.IN("box", ""),
			const..charUTF8_p.IN("ctext", ""),
			AutoSize("ctext")..int.IN("len", "")
		)

		void(
			"textedit_undo",
			"",

			nk_text_edit_p.IN("box", "")
		)

		void(
			"textedit_redo",
			"",

			nk_text_edit_p.IN("box", "")
		)
	}();

	{
		void(
			"stroke_line",
			"",

			nk_command_buffer_p.IN("b", ""),
			float.IN("x0", ""),
			float.IN("y0", ""),
			float.IN("x1", ""),
			float.IN("y1", ""),
			float.IN("line_thickness", ""),
			nk_color.IN("color", "")
		)

		void(
			"stroke_curve",
			"",

			nk_command_buffer_p.IN("b", ""),
			float.IN("ax", ""),
			float.IN("ay", ""),
			float.IN("ctrl0x", ""),
			float.IN("ctrl0y", ""),
			float.IN("ctrl1x", ""),
			float.IN("ctrl1y", ""),
			float.IN("bx", ""),
			float.IN("by", ""),
			float.IN("line_thickness", ""),
			nk_color.IN("color", "")
		)

		void(
			"stroke_rect",
			"",

			nk_command_buffer_p.IN("b", ""),
			nk_rect.IN("rect", ""),
			float.IN("rounding", ""),
			float.IN("line_thickness", ""),
			nk_color.IN("color", "")
		)

		void(
			"stroke_circle",
			"",

			nk_command_buffer_p.IN("b", ""),
			nk_rect.IN("rect", ""),
			float.IN("line_thickness", ""),
			nk_color.IN("color", "")
		)

		void(
			"stroke_arc",
			"",

			nk_command_buffer_p.IN("b", ""),
			float.IN("cx", ""),
			float.IN("cy", ""),
			float.IN("radius", ""),
			float.IN("a_min", ""),
			float.IN("a_max", ""),
			float.IN("line_thickness", ""),
			nk_color.IN("color", "")
		)

		void(
			"stroke_triangle",
			"",

			nk_command_buffer_p.IN("b", ""),
			float.IN("x0", ""),
			float.IN("y0", ""),
			float.IN("x1", ""),
			float.IN("y1", ""),
			float.IN("x2", ""),
			float.IN("y2", ""),
			float.IN("line_thichness", ""),
			nk_color.IN("color", "")
		)

		void(
			"stroke_polyline",
			"",

			nk_command_buffer_p.IN("b", ""),
			float_p.IN("points", ""),
			AutoSize("points")..int.IN("point_count", ""),
			float.IN("line_thickness", ""),
			nk_color.IN("col", "")
		)

		void(
			"stroke_polygon",
			"",

			nk_command_buffer_p.IN("b", ""),
			float_p.IN("points", ""),
			AutoSize("points")..int.IN("point_count", ""),
			float.IN("line_thickness", ""),
			nk_color.IN("color", "")
		)

		void(
			"fill_rect",
			"",

			nk_command_buffer_p.IN("b", ""),
			nk_rect.IN("rect", ""),
			float.IN("rounding", ""),
			nk_color.IN("color", "")
		)

		void(
			"fill_rect_multi_color",
			"",

			nk_command_buffer_p.IN("b", ""),
			nk_rect.IN("rect", ""),
			nk_color.IN("left", ""),
			nk_color.IN("top", ""),
			nk_color.IN("right", ""),
			nk_color.IN("bottom", "")
		)

		void(
			"fill_circle",
			"",

			nk_command_buffer_p.IN("b", ""),
			nk_rect.IN("rect", ""),
			nk_color.IN("color", "")
		)

		void(
			"fill_arc",
			"",

			nk_command_buffer_p.IN("b", ""),
			float.IN("cx", ""),
			float.IN("cy", ""),
			float.IN("radius", ""),
			float.IN("a_min", ""),
			float.IN("a_max", ""),
			nk_color.IN("color", "")
		)

		void(
			"fill_triangle",
			"",

			nk_command_buffer_p.IN("b", ""),
			float.IN("x0", ""),
			float.IN("y0", ""),
			float.IN("x1", ""),
			float.IN("y1", ""),
			float.IN("x2", ""),
			float.IN("y2", ""),
			nk_color.IN("color", "")
		)

		void(
			"fill_polygon",
			"",

			nk_command_buffer_p.IN("b", ""),
			float_p.IN("points", ""),
			AutoSize("points")..int.IN("point_count", ""),
			nk_color.IN("color", "")
		)

		void(
			"push_scissor",
			"",

			nk_command_buffer_p.IN("b", ""),
			nk_rect.IN("rect", "")
		)

		void(
			"draw_image",
			"",

			nk_command_buffer_p.IN("b", ""),
			nk_rect.IN("rect", ""),
			const..nk_image.p.IN("img", ""),
			nk_color.IN("color", "")
		)

		void(
			"draw_text",
			"",

			nk_command_buffer_p.IN("b", ""),
			nk_rect.IN("rect", ""),
			const..charUTF8_p.IN("string", ""),
			AutoSize("string")..int.IN("length", ""),
			const..nk_user_font_p.IN("font", ""),
			nk_color.IN("bg", ""),
			nk_color.IN("fg", "")
		)

		(const..nk_command.p)(
			"_next",
			"",

			ctx,
			const..nk_command.p.IN("cmd", "")
		)

		(const..nk_command.p)(
			"_begin",
			"",

			ctx
		)

		intb(
			"input_has_mouse_click",
			"",

			const..nk_input_p.IN("i", ""),
			nk_buttons.IN("id", "", Buttons)
		)

		intb(
			"input_has_mouse_click_in_rect",
			"",

			const..nk_input_p.IN("i", ""),
			nk_buttons.IN("id", "", Buttons),
			nk_rect.IN("rect", "")
		)

		intb(
			"input_has_mouse_click_down_in_rect",
			"",

			const..nk_input_p.IN("i", ""),
			nk_buttons.IN("id", "", Buttons),
			nk_rect.IN("rect", ""),
			int.IN("down", "")
		)

		intb(
			"input_is_mouse_click_in_rect",
			"",

			const..nk_input_p.IN("i", ""),
			nk_buttons.IN("id", "", Buttons),
			nk_rect.IN("rect", "")
		)

		intb(
			"input_is_mouse_click_down_in_rect",
			"",

			const..nk_input_p.IN("i", ""),
			nk_buttons.IN("id", "", Buttons),
			nk_rect.IN("b", ""),
			int.IN("down", "")
		)

		intb(
			"input_any_mouse_click_in_rect",
			"",

			const..nk_input_p.IN("i", ""),
			nk_rect.IN("rect", "")
		)

		intb(
			"input_is_mouse_prev_hovering_rect",
			"",

			const..nk_input_p.IN("i", ""),
			nk_rect.IN("rect", "")
		)

		intb(
			"input_is_mouse_hovering_rect",
			"",

			const..nk_input_p.IN("i", ""),
			nk_rect.IN("rect", "")
		)

		intb(
			"input_mouse_clicked",
			"",

			const..nk_input_p.IN("i", ""),
			nk_buttons.IN("id", "", Buttons),
			nk_rect.IN("rect", "")
		)

		intb(
			"input_is_mouse_down",
			"",

			const..nk_input_p.IN("i", ""),
			nk_buttons.IN("id", "", Buttons)
		)

		intb(
			"input_is_mouse_pressed",
			"",

			const..nk_input_p.IN("i", ""),
			nk_buttons.IN("id", "", Buttons)
		)

		intb(
			"input_is_mouse_released",
			"",

			const..nk_input_p.IN("i", ""),
			nk_buttons.IN("id", "", Buttons)
		)

		intb(
			"input_is_key_pressed",
			"",

			const..nk_input_p.IN("i", ""),
			nk_keys.IN("key", "", Keys)
		)

		intb(
			"input_is_key_released",
			"",

			const..nk_input_p.IN("i", ""),
			nk_keys.IN("key", "", Keys)
		)

		intb(
			"input_is_key_down",
			"",

			const..nk_input_p.IN("i", ""),
			nk_keys.IN("key", "", Keys)
		)
	}();

	{
		void(
			"draw_list_init",
			"",

			nk_draw_list_p.IN("list", "")
		)

		void(
			"draw_list_setup",
			"",

			nk_draw_list_p.IN("canvas", ""),
			const..nk_convert_config.p.IN("config", ""),
			nk_buffer_p.IN("cmds", ""),
			nk_buffer_p.IN("vertices", ""),
			nk_buffer_p.IN("elements", "")
		)

		void(
			"draw_list_clear",
			"",

			nk_draw_list_p.IN("list", "")
		)

		(const..nk_draw_command_p)(
			"_draw_list_begin",
			"",

			const..nk_draw_list_p.IN("list", ""),
			const..nk_buffer_p.IN("buffer", "")
		)

		(const..nk_draw_command_p)(
			"_draw_list_next",
			"",

			const..nk_draw_command_p.IN("cmd", ""),
			const..nk_buffer_p.IN("buffer", ""),
			const..nk_draw_list_p.IN("list", "")
		)

		(const..nk_draw_command_p)(
			"_draw_begin",
			"",

			cctx,
			const..nk_buffer_p.IN("buffer", "")
		)

		(const..nk_draw_command_p)(
			"_draw_next",
			"",

			const..nk_draw_command_p.IN("cmd", ""),
			const..nk_buffer_p.IN("buffer", ""),
			cctx
		)

		void(
			"draw_list_path_clear",
			"",

			nk_draw_list_p.IN("list", "")
		)

		void(
			"draw_list_path_line_to",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("pos", "")
		)

		void(
			"draw_list_path_arc_to_fast",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("center", ""),
			float.IN("radius", ""),
			int.IN("a_min", ""),
			int.IN("a_max", "")
		)

		void(
			"draw_list_path_arc_to",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("center", ""),
			float.IN("radius", ""),
			float.IN("a_min", ""),
			float.IN("a_max", ""),
			unsigned_int.IN("segments", "")
		)

		void(
			"draw_list_path_rect_to",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("a", ""),
			nk_vec2.IN("b", ""),
			float.IN("rounding", "")
		)

		void(
			"draw_list_path_curve_to",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("p2", ""),
			nk_vec2.IN("p3", ""),
			nk_vec2.IN("p4", ""),
			unsigned_int.IN("num_segments", "")
		)

		void(
			"draw_list_path_fill",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_color.IN("color", "")
		)

		void(
			"draw_list_path_stroke",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_color.IN("color", ""),
			nk_draw_list_stroke.IN("closed", "", DrawListStrokes),
			float.IN("thickness", "")
		)

		void(
			"draw_list_stroke_line",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("a", ""),
			nk_vec2.IN("b", ""),
			nk_color.IN("color", ""),
			float.IN("thickness", "")
		)

		void(
			"draw_list_stroke_rect",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_rect.IN("rect", ""),
			nk_color.IN("color", ""),
			float.IN("rounding", ""),
			float.IN("thickness", "")
		)

		void(
			"draw_list_stroke_triangle",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("a", ""),
			nk_vec2.IN("b", ""),
			nk_vec2.IN("c", ""),
			nk_color.IN("color", ""),
			float.IN("thickness", "")
		)

		void(
			"draw_list_stroke_circle",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("center", ""),
			float.IN("radius", ""),
			nk_color.IN("color", ""),
			unsigned_int.IN("segs", ""),
			float.IN("thickness", "")
		)

		void(
			"draw_list_stroke_curve",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("p0", ""),
			nk_vec2.IN("cp0", ""),
			nk_vec2.IN("cp1", ""),
			nk_vec2.IN("p1", ""),
			nk_color.IN("color", ""),
			unsigned_int.IN("segments", ""),
			float.IN("thickness", "")
		)

		void(
			"draw_list_stroke_poly_line",
			"",

			nk_draw_list_p.IN("list", ""),
			const..nk_vec2.p.IN("pnts", ""),
			unsigned_int.IN("cnt", ""),
			nk_color.IN("color", ""),
			nk_draw_list_stroke.IN("closed", "", DrawListStrokes),
			float.IN("thickness", ""),
			nk_anti_aliasing.IN("aliasing", "", Antialiasing)
		)

		void(
			"draw_list_fill_rect",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_rect.IN("rect", ""),
			nk_color.IN("color", ""),
			float.IN("rounding", "")
		)

		void(
			"draw_list_fill_rect_multi_color",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_rect.IN("rect", ""),
			nk_color.IN("left", ""),
			nk_color.IN("top", ""),
			nk_color.IN("right", ""),
			nk_color.IN("bottom", "")
		)

		void(
			"draw_list_fill_triangle",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("a", ""),
			nk_vec2.IN("b", ""),
			nk_vec2.IN("c", ""),
			nk_color.IN("color", "")
		)

		void(
			"draw_list_fill_circle",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_vec2.IN("center", ""),
			float.IN("radius", ""),
			nk_color.IN("col", ""),
			unsigned_int.IN("segs", "")
		)

		void(
			"draw_list_fill_poly_convex",
			"",

			nk_draw_list_p.IN("list", ""),
			const..nk_vec2.p.IN("points", ""),
			AutoSize("points")..unsigned_int.IN("count", ""),
			nk_color.IN("color", ""),
			nk_anti_aliasing.IN("aliasing", "", Antialiasing)
		)

		void(
			"draw_list_add_image",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_image.IN("texture", ""),
			nk_rect.IN("rect", ""),
			nk_color.IN("color", "")
		)

		void(
			"draw_list_add_text",
			"",

			nk_draw_list_p.IN("list", ""),
			const..nk_user_font_p.IN("font", ""),
			nk_rect.IN("rect", ""),
			const..charUTF8_p.IN("text", ""),
			AutoSize("text")..int.IN("len", ""),
			float.IN("font_height", ""),
			nk_color.IN("color", "")
		)

		void(
			"draw_list_push_userdata",
			"",

			nk_draw_list_p.IN("list", ""),
			nk_handle.IN("userdata", "")
		)

		nk_style_item(
			"style_item_image",
			"",

			nk_image.IN("img", "")
		)

		nk_style_item(
			"style_item_color",
			"",

			nk_color.IN("color", "")
		)

		nk_style_item(
			"style_item_hide",
			""
		)
	}();
}