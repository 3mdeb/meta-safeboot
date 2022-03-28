DESCRIPTION = "Safe Boot: Booting Linux Safely"
HOMEPAGE = "https://github.com/osresearch/safeboot"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/osresearch/safeboot.git;protocol=https;branch=master;"

SRCREV = "fd0aef40912948711986e2e4132c4ba8e30e170d"

S = "${WORKDIR}/git"
CFLAGS += "-I${WORKDIR}/recipe-sysroot/usr/include \
           -I${WORKDIR}/recipe-sysroot/usr/include/efi \
           -I${WORKDIR}/recipe-sysroot/usr/include/efi/x86_64 \
           -I${WORKDIR}/recipe-sysroot/usr/include/efi/protocol"

do_compile[noexec] = "1"

do_install(){
    install -d ${D}${sbindir}
    install -d ${D}${sysconfdir}/safeboot
    install -d ${D}${sysconfdir}/safeboot/certs
    install -d ${D}${sysconfdir}/initramfs-tools/hooks/
    install -d ${D}${sysconfdir}/initramfs-tools/scripts
    install -d ${D}${sysconfdir}/initramfs-tools/scripts/local-premount/
    install -d ${D}${sysconfdir}/initramfs-tools/scripts/init-top/

    install -m 0755 ${S}/sbin/safeboot ${D}${sbindir}
    install -m 0755 ${S}/sbin/safeboot-tpm-unseal ${D}${sbindir}
    install -m 0755 ${S}/sbin/tpm2-attest ${D}${sbindir}
    install -m 0755 ${S}/sbin/tpm2-pcr-validate ${D}${sbindir}

    install -m 0755 ${S}/safeboot.conf ${D}${sysconfdir}/safeboot
    install -m 0755 ${S}/functions.sh ${D}${sysconfdir}/safeboot
    install -m 0755 ${S}/tpm-certs.txt ${D}${sysconfdir}/safeboot
    install -m 0755 ${S}/refresh-certs ${D}${sysconfdir}/safeboot

    for file in ${S}/certs/*;do
        install -m 755 $file ${D}${sysconfdir}/safeboot/certs
    done

    install -m 0755 ${S}/initramfs/hooks/dmverity-root ${D}${sysconfdir}/initramfs-tools/hooks/
    install -m 0755 ${S}/initramfs/hooks/safeboot-hooks ${D}${sysconfdir}/initramfs-tools/hooks/
    install -m 0755 ${S}/initramfs/scripts/dmverity-root ${D}${sysconfdir}/initramfs-tools/scripts/local-premount/
    install -m 0755 ${S}/initramfs/scripts/blockdev-readonly ${D}${sysconfdir}/initramfs-tools/scripts/local-premount/
    install -m 0755 ${S}/initramfs/scripts/safeboot-bootmode ${D}${sysconfdir}/initramfs-tools/scripts/init-top/
}

FILES_${PN} += "\
    ${sysconfdir}/safeboot \
    ${sysconfdir}/safeboot/certs \
    ${sysconfdir}/initramfs-tools/hooks/ \
    ${sysconfdir}/initramfs-tools/scripts/local-premount/ \
    ${sysconfdir}/initramfs-tools/scripts/init-top/ \
    ${bindir} \
"

RDEPENDS_${PN} += "\
    bash \
    gnu-efi \
    openssl \
    efitools \
    tpm2-tools \
    tpm2-totp \
    tpm2-abrmd \
    tpm2-tss \
    libtss2 \
    libtss2-mu \
    libtss2-tcti-device \
    libtss2-tcti-mssim \
    sbsigntool \
    opensc \
"
