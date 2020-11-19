LIC_FILES_CHKSUM = "file://doc/LICENSE;md5=a846608d090aa64494c45fc147cc12e3"

SRC_URI = "git://github.com/tpm2-software/tpm2-tools.git;branch=master"
SRCREV = "c643ff688834d573772c9cc57fcbdf48a7e7735e"

S = "${WORKDIR}/git"

PV = "5.0.0"

DEPENDS += "binutils gnu-efi util-linux pkgconfig"

inherit autotools-brokensep

do_configure_prepend() {
    (  cd ${S}
    ${S}/bootstrap)
}

do_install_append() {
    install -d ${D}${sbindir}
    install -m 755 ${D}${bindir}/tpm2 ${D}${sbindir}
}
