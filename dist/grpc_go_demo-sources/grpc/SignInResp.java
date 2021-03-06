// Code generated by gobind. DO NOT EDIT.

// Java class grpc.SignInResp is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java GoGrpc/grpc
package grpc;

import go.Seq;

public final class SignInResp implements Seq.Proxy {
	static { Grpc.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	SignInResp(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public SignInResp() { this.refnum = __New(); Seq.trackGoRef(refnum, this); }
	
	private static native int __New();
	
	public final native String getDevphyid();
	public final native void setDevphyid(String v);
	
	public final native long getRetcode();
	public final native void setRetcode(long v);
	
	public final native String getRetmsg();
	public final native void setRetmsg(String v);
	
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof SignInResp)) {
		    return false;
		}
		SignInResp that = (SignInResp)o;
		String thisDevphyid = getDevphyid();
		String thatDevphyid = that.getDevphyid();
		if (thisDevphyid == null) {
			if (thatDevphyid != null) {
			    return false;
			}
		} else if (!thisDevphyid.equals(thatDevphyid)) {
		    return false;
		}
		long thisRetcode = getRetcode();
		long thatRetcode = that.getRetcode();
		if (thisRetcode != thatRetcode) {
		    return false;
		}
		String thisRetmsg = getRetmsg();
		String thatRetmsg = that.getRetmsg();
		if (thisRetmsg == null) {
			if (thatRetmsg != null) {
			    return false;
			}
		} else if (!thisRetmsg.equals(thatRetmsg)) {
		    return false;
		}
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {getDevphyid(), getRetcode(), getRetmsg()});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("SignInResp").append("{");
		b.append("Devphyid:").append(getDevphyid()).append(",");
		b.append("Retcode:").append(getRetcode()).append(",");
		b.append("Retmsg:").append(getRetmsg()).append(",");
		return b.append("}").toString();
	}
}

