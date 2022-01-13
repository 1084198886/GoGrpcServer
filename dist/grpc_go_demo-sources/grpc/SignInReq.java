// Code generated by gobind. DO NOT EDIT.

// Java class grpc.SignInReq is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java GoGrpc/grpc
package grpc;

import go.Seq;

public final class SignInReq implements Seq.Proxy {
	static { Grpc.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	SignInReq(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public SignInReq() { this.refnum = __New(); Seq.trackGoRef(refnum, this); }
	
	private static native int __New();
	
	public final native String getDevphyid();
	public final native void setDevphyid(String v);
	
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof SignInReq)) {
		    return false;
		}
		SignInReq that = (SignInReq)o;
		String thisDevphyid = getDevphyid();
		String thatDevphyid = that.getDevphyid();
		if (thisDevphyid == null) {
			if (thatDevphyid != null) {
			    return false;
			}
		} else if (!thisDevphyid.equals(thatDevphyid)) {
		    return false;
		}
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {getDevphyid()});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("SignInReq").append("{");
		b.append("Devphyid:").append(getDevphyid()).append(",");
		return b.append("}").toString();
	}
}

