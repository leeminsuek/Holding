package com.br.holding5.base;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.EditText;
import android.widget.TextView;

import com.brainyx.holding5.R;

public class BubbleTagsEditText { 

	private SpannableStringBuilder sb = new SpannableStringBuilder(); 
    private List<String> tags = new ArrayList<String>(); 
    private EditText tag; 
    private static Context mContext;
    public BubbleTagsEditText(EditText tag) { 
        this.tag = tag; 
    } 
    
    public BubbleTagsEditText(EditText tag, Context context) { 
        this.tag = tag; 
        this.mContext = context;
    } 
     
    public void addBubble(String text){ 
        tags.add(text); 
        createBubble(text); 
    } 
     
    //в этом методе вся магия 
    public void createBubble(final String text){ 
        TextView tv = createContactTextView(text); 
        BitmapDrawable bd = (BitmapDrawable) convertViewToDrawable(tv); 
        bd.setBounds(0, 0, bd.getIntrinsicWidth(),bd.getIntrinsicHeight()); 
          
        sb.append(text + " "); 
        sb.setSpan(new ImageSpan(bd),  
                sb.length()-(text.length()+1),  
                sb.length()-1, 
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
         
        ClickableSpan clickSpan = new ClickableSpan() { 
                 
            @Override 
            public void onClick(View view) {  
                 
                deleteText(view, text); 
                 
                if(text.equals("Add new tag")){ 
                    sb.clear(); 
                    for(int i = 0; i < tags.size(); i++){ 
                        createBubble(tags.get(i)); 
                    } 
                     
//                    EditTextDialog editTextDialog = new EditTextDialog(new OnClickButton() { 
//                         
//                        @Override 
//                        public void onClickPositiveButton(String text) { 
//                            addBubble(text); 
//                            addBubble("Add new tag"); 
//                        } 
//                         
//                        @Override 
//                        public void onClickNegativeButton() { 
//                            addBubble("Add new tag"); 
//                        } 
//                    });   
                } 
            } 
        }; 
        sb.setSpan(clickSpan, sb.length() - (text.length()+1), 
                        sb.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
         
        tag.setText(sb);         
        makeLinksFocusable(tag); 
    } 
     
    private void deleteText(View view, String text){ 
        int startIndex = ((EditText) view).getSelectionStart(); 
        int endIndex = ((EditText) view).getSelectionEnd(); 
        tag.getText().replace(Math.min(startIndex, endIndex+1), 
                Math.max(startIndex, endIndex+1), "", 0, "".length()); 
         
        for(int i = 0; i < tags.size(); i++){ 
             
            if(tags.get(i).equals(text)){ 
                tags.remove(i); 
            } 
        }  
    } 
     
    public void makeLinksFocusable(TextView tv) { 
        MovementMethod m = tv.getMovementMethod();   
        if ((m == null) || !(m instanceof LinkMovementMethod)) {   
            if (tv.getLinksClickable()) {   
                tv.setMovementMethod(LinkMovementMethod.getInstance()); 
            }   
        }   
    } 

    public TextView createContactTextView(String text) { 
    	  TextView tv = new TextView(mContext); 
          int padding = pxToDp(10); 
          tv.setPadding(padding, 5, padding, 5); 
          tv.setText(text); 
          tv.setTextColor(Color.parseColor("#191970"));
//          tv.setTextSize(pixelsToSp(14)); 
          tv.setTextSize(tag.getTextSize()); 
          tv.setBackgroundColor(Color.parseColor("#98FB98"));
          //tv.setBackgroundResource(Color.parseColor("#98FB98")); 
//          tv.setHighlightColor(Color.WHITE); 
          if(!text.equals("Add new tag")){ 
           
              /*Drawable dr = mContext.getResources().getDrawable(R.drawable.abc_ab_stacked_solid_light_holo); 
              dr.setBounds(0, 0, pxToDp(dr.getIntrinsicWidth()-5), pxToDp(dr.getIntrinsicHeight()-5)); 
              tv.setCompoundDrawables(null, null, dr, null); 
              tv.setCompoundDrawablePadding(pxToDp(20)); */
          } else{ 
              tv.setTextColor(Color.parseColor("#00acff")); 
          } 
           
          return tv; 
    } 

    public static Object convertViewToDrawable(View view) { 
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED); 
        view.measure(spec, spec); 
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight()); 
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), 
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888); 
        Canvas c = new Canvas(b); 
        c.translate(-view.getScrollX(), -view.getScrollY()); 
        view.draw(c); 
        view.setDrawingCacheEnabled(true); 
        Bitmap cacheBmp = view.getDrawingCache(); 
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true); 
        view.destroyDrawingCache(); 
        return new BitmapDrawable(viewBmp); 

    } 

    private static float pixelsToSp(int scaledPixelSize) { 
        DisplayMetrics dm = new DisplayMetrics(); 
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm); 
        float pixelSize = (int) scaledPixelSize * dm.scaledDensity;  
        return pixelSize; 
    } 
     
    private int pxToDp(int sizeInDp) { 
        float scale = ((Activity)mContext).getResources().getDisplayMetrics().density; 
        int dpAsPixels = (int) (sizeInDp*scale + 0.5f); 
        return dpAsPixels; 
    } 

    public String getTags() { 
        String tag = ""; 
        if(tags.size()-1 < 1) return tag; 
        for(int i = 0; i < tags.size()-1; i++){ 
            tag += tags.get(i) + " "; 
        } 
        return tag; 
    } 
     
    public List<String> getTagsList(){ 
        return tags; 
    }
    
    
//    private SpannableStringBuilder sb = new SpannableStringBuilder(); 
//    private List<String> tags = new ArrayList<String>(); 
//    private EditText tag; 
//    private static Context mContext;
//    
//    public BubbleTagsEditText(EditText tag, Context context) { 
//        this.tag = tag; 
//        this.mContext = context;
//    } 
//     
//    public void addBubble(String text){ 
//    	 tags.clear();
//        tags.add(text); 
//        createBubble(text); 
//    } 
//    
////	textView.setText(c); // set text
////	int image = ((ChipsAdapter) getAdapter()).getImage(c);
////	textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, image, 0);
//	// capture bitmapt of genreated textview
////	int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
////	textView.measure(spec, spec);
////	textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
////	Bitmap b = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(),Bitmap.Config.ARGB_8888);
////	Canvas canvas = new Canvas(b);
////	canvas.translate(-textView.getScrollX(), -textView.getScrollY());
////	textView.draw(canvas);
////	textView.setDrawingCacheEnabled(true);
////	Bitmap cacheBmp = textView.getDrawingCache();
////	Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
////	textView.destroyDrawingCache();  // destory drawable
//	// create bitmap drawable for imagespan
////	BitmapDrawable bmpDrawable = new BitmapDrawable(viewBmp);
////	bmpDrawable.setBounds(0, 0,bmpDrawable.getIntrinsicWidth(),bmpDrawable.getIntrinsicHeight());
//	// create and set imagespan 
////	ssb.setSpan(new ImageSpan(bmpDrawable),x ,x + c.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//	
//      
//    //в этом методе вся магия 
//    public void createBubble(final String text){ 
//        TextView tv = createContactTextView(text); 
//       
//        BitmapDrawable bd = (BitmapDrawable) convertViewToDrawable(tv); 
//    //    bd.setColorFilter(Color.GREEN, Mode.MULTIPLY);
//        bd.setBounds(0, 0, bd.getIntrinsicWidth(),bd.getIntrinsicHeight()); 
////        
////        Editable e = (Editable)tag;
////        CharacterStyle[] styleSpans = e.getSpans(0, e.length(), CharacterStyle.class);
////        Editable prev = "";
////        for(CharacterStyle c : styleSpans) {
////        	prev += c.toString();
////        }
//        sb.append(text  ); 
//        sb.setSpan(new ImageSpan(bd),  
//                sb.length()-(text.length()),  
//                sb.length(), 
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
//         
//        ClickableSpan clickSpan = new ClickableSpan() { 
//                 
//            @Override 
//            public void onClick(View view) {  
//                 
//                deleteText(view, text); 
//                 
//                if(text.equals("Add new tag")){ 
//                    sb.clear(); 
//                    for(int i = 0; i < tags.size(); i++){ 
//                        createBubble(tags.get(i)); 
//                    } 
//                    
//                     
////                    EditTextDialog editTextDialog = new EditTextDialog(new OnClickButton() { 
////                         
////                        @Override 
////                        public void onClickPositiveButton(String text) { 
////                            addBubble(text); 
////                            addBubble("Add new tag"); 
////                        } 
////                         
////                        @Override 
////                        public void onClickNegativeButton() { 
////                            addBubble("Add new tag"); 
////                        } 
////                    });   
//                } 
//            } 
//        }; 
//        sb.setSpan(clickSpan, sb.length() - (text.length()), 
//                        sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
//       
//        sb.append(" ");
//        tag.setText(sb);         
////        makeLinksFocusable(tag);
//        /*SpannableStringBuilder sb2 = new SpannableStringBuilder(); 
//        sb2.append(" ");
//        sb2.setSpan(null,0, 
//        		sb2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
//        tag.setText(sb);  */
//        
//        tag.setSelection(tag.getText().length());
//    } 
//     
//    private void deleteText(View view, String text){ 
//        int startIndex = ((EditText) view).getSelectionStart(); 
//        int endIndex = ((EditText) view).getSelectionEnd(); 
//        tag.getText().replace(Math.min(startIndex, endIndex), 
//                Math.max(startIndex, endIndex), "", 0, "".length()); 
//         
//        for(int i = 0; i < tags.size(); i++){ 
//             
//            if(tags.get(i).equals(text)){ 
//                tags.remove(i); 
//            } 
//        }  
//    } 
//     
//    public void makeLinksFocusable(TextView tv) { 
//        MovementMethod m = tv.getMovementMethod();   
//        if ((m == null) || !(m instanceof LinkMovementMethod)) {   
//            if (tv.getLinksClickable()) {   
//                tv.setMovementMethod(LinkMovementMethod.getInstance()); 
//            }   
//        }   
//    } 
//
//    public TextView createContactTextView(String text) { 
//        TextView tv = new TextView(mContext); 
//        int padding = pxToDp(10); 
//        tv.setPadding(padding, 5, padding, 5); 
//        tv.setText(text); 
//        tv.setTextColor(Color.parseColor("#191970"));
////        tv.setTextSize(pixelsToSp(14)); 
//        tv.setTextSize(tag.getTextSize()); 
//        tv.setBackgroundColor(Color.parseColor("#98FB98"));
//        //tv.setBackgroundResource(Color.parseColor("#98FB98")); 
////        tv.setHighlightColor(Color.WHITE); 
//        if(!text.equals("Add new tag")){ 
//         
//            /*Drawable dr = mContext.getResources().getDrawable(R.drawable.abc_ab_stacked_solid_light_holo); 
//            dr.setBounds(0, 0, pxToDp(dr.getIntrinsicWidth()-5), pxToDp(dr.getIntrinsicHeight()-5)); 
//            tv.setCompoundDrawables(null, null, dr, null); 
//            tv.setCompoundDrawablePadding(pxToDp(20)); */
//        } else{ 
//            tv.setTextColor(Color.parseColor("#00acff")); 
//        } 
//         
//        return tv; 
//    } 
//
//    public static Object convertViewToDrawable(View view) { 
////    	int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
////    	textView.measure(spec, spec);
////    	textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
////    	Bitmap b = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(),Bitmap.Config.ARGB_8888);
////    	Canvas canvas = new Canvas(b);
////    	canvas.translate(-textView.getScrollX(), -textView.getScrollY());
////    	textView.draw(canvas);
////    	textView.setDrawingCacheEnabled(true);
////    	Bitmap cacheBmp = textView.getDrawingCache();
////    	Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
////    	textView.destroyDrawingCache(); 
//    	
//    	view = (TextView) view;
//    	
//        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED); 
//        view.measure(spec, spec); 
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight()+10); 
//        Bitmap b = Bitmap.createBitmap(view.getWidth(), 
//                view.getHeight(), Bitmap.Config.ARGB_8888); 
//        Canvas c = new Canvas(b); 
//        c.translate(-view.getScrollX(), -view.getScrollY()); 
//        view.draw(c); 
//        view.setDrawingCacheEnabled(true); 
//        Bitmap cacheBmp = view.getDrawingCache(); 
//        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true); 
//        view.destroyDrawingCache(); 
//        return new BitmapDrawable(viewBmp); 
//
//    } 
//
//    private static float pixelsToSp(int scaledPixelSize) { 
//        DisplayMetrics dm = new DisplayMetrics(); 
//        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm); 
//        float pixelSize = (int) scaledPixelSize * dm.scaledDensity;  
//        return pixelSize; 
//    } 
//     
//    private int pxToDp(int sizeInDp) { 
//        float scale = mContext.getResources().getDisplayMetrics().density; 
//        int dpAsPixels = (int) (sizeInDp*scale + 0.5f); 
//        return dpAsPixels; 
//    } 
//
//    public String getTags() { 
//        String tag = ""; 
//        if(tags.size()-1 < 1) return tag; 
//        for(int i = 0; i < tags.size()-1; i++){ 
//            tag += tags.get(i) + " "; 
//        } 
//        return tag; 
//    } 
//     
//    public List<String> getTagsList(){ 
//        return tags; 
//    } 
} 