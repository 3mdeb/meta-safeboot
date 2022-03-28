SUMMARY = "Signing utility for UEFI secure boot"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE.GPLv3;md5=9eef91148a9b14ec7f9df333daebc746"

SRC_URI = "git://github.com/osresearch/sbsigntools.git;branch=master;name=sbsigntools \
           gitsm://github.com/rustyrussell/ccan.git;protocol=https;destsuffix=git/lib/ccan.git;name=ccan \
           file://configure-Fixup-build-dependencies-for-cross-compili.patch \
           file://disable-man-page-creation.patch \
"

SRCREV_sbsigntools  = "370abb7c49ec2a600f64fcbd441d9297124a5cb7"
SRCREV_ccan         = "b1f28e17227f2320d07fe052a8a48942fe17caa5"
SRCREV_FORMAT       =  "sbsigntools_ccan"
PV = "v0.9.4+git"

inherit autotools-brokensep pkgconfig autotools

BBCLASSEXTEND = "native"

DEPENDS = "binutils openssl gnu-efi util-linux pkgconfig"

S = "${WORKDIR}/git"

EXTRA_OECONF = "SYSROOT=../recipe-sysroot"

do_configure() {
    OLD_CC="${CC}"

    if [ ! -e lib/ccan ]; then
        export CC="${BUILD_CC}"
        lib/ccan.git/tools/create-ccan-tree \
            --build-type=automake lib/ccan \
                endian talloc read_write_all build_assert array_size
    fi

    export CC="${OLD_CC}"
    export CFLAGS="-I${STAGING_INCDIR}/efi -I${STAGING_INCDIR}/efi/x86_64 -Wno-error=cpp -Wno-cpp"
    ./autogen.sh --noconfigure
    oe_runconf
}

do_install:append() {
    install -d ${D}${sbindir}
    install -m 755 ${D}${bindir}/sbsign ${D}${sbindir}/sbsign.safeboot
}
