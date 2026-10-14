// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CaptureSignatureActivity_ViewBinding implements Unbinder {
  private CaptureSignatureActivity target;

  @UiThread
  public CaptureSignatureActivity_ViewBinding(CaptureSignatureActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CaptureSignatureActivity_ViewBinding(CaptureSignatureActivity target, View source) {
    this.target = target;

    target.closeImageView = Utils.findRequiredViewAsType(source, R.id.closeImageView, "field 'closeImageView'", ImageView.class);
    target.clear = Utils.findRequiredViewAsType(source, R.id.clear, "field 'clear'", Button.class);
    target.saveButton = Utils.findRequiredViewAsType(source, R.id.saveButton, "field 'saveButton'", Button.class);
    target.signaturelinearLayout = Utils.findRequiredViewAsType(source, R.id.signaturelinearLayout, "field 'signaturelinearLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CaptureSignatureActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.closeImageView = null;
    target.clear = null;
    target.saveButton = null;
    target.signaturelinearLayout = null;
  }
}
