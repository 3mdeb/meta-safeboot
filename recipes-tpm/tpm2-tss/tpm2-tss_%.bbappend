LIC_FILES_CHKSUM = "file://LICENSE;md5=500b2e742befc3da00684d8a1d5fd9da"

SRC_URI_remove = "https://github.com/tpm2-software/${BPN}/releases/download/${PV}/${BPN}-${PV}.tar.gz"

SRC_URI += "git://github.com/tpm2-software/tpm2-tss.git;branch=master"
SRCREV = "39e4bfc2041f6a7f271710ff33c17ca13c640465"

PV = "2.4.3"

S = "${WORKDIR}/git"

DEPENDS += "json-c curl"

do_configure:prepend() {
    (  cd ${S}
    ${S}/bootstrap)
}

FILES_${PN} += "${sysconfdir}"
