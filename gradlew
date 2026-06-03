#!/bin/sh

set -eu

GRADLE_VERSION=8.10.2
GRADLE_USER_HOME="${GRADLE_USER_HOME:-$HOME/.gradle}"
DIST_DIR="$GRADLE_USER_HOME/wrapper/dists/gradle-${GRADLE_VERSION}-bin"
INSTALL_DIR="$DIST_DIR/gradle-${GRADLE_VERSION}"
ZIP_FILE="$DIST_DIR/gradle-${GRADLE_VERSION}-bin.zip"
DIST_URL="https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip"

if [ -x "$INSTALL_DIR/bin/gradle" ]; then
    exec "$INSTALL_DIR/bin/gradle" "$@"
fi

mkdir -p "$DIST_DIR"

download() {
    if command -v curl >/dev/null 2>&1; then
        curl -fsSL "$DIST_URL" -o "$ZIP_FILE"
    elif command -v wget >/dev/null 2>&1; then
        wget -qO "$ZIP_FILE" "$DIST_URL"
    else
        echo "gradlew: neither curl nor wget is available to download Gradle" >&2
        exit 1
    fi
}

if [ ! -f "$ZIP_FILE" ]; then
    download
fi

TMP_DIR=$(mktemp -d "${TMPDIR:-/tmp}/gradle.XXXXXX")
trap 'rm -rf "$TMP_DIR"' EXIT INT TERM

if command -v unzip >/dev/null 2>&1; then
    unzip -q "$ZIP_FILE" -d "$TMP_DIR"
else
    echo "gradlew: unzip is required to unpack Gradle" >&2
    exit 1
fi

rm -rf "$INSTALL_DIR"
mv "$TMP_DIR/gradle-${GRADLE_VERSION}" "$INSTALL_DIR"
exec "$INSTALL_DIR/bin/gradle" "$@"
